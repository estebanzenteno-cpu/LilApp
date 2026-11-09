package mx.lilapp.helper;

import mx.lilapp.entity.Cliente;
import mx.lilapp.integration.ServiceFacadeLocator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class ClienteHelper implements Serializable {

    public static final String MSG_CAMPOS_OBLIGATORIOS = "Favor de llenar todos los campos";
    public static final String MSG_SOLO_PDF = "El campo de constancia fiscal solo debe de permitir archivos en PDF";
    public static final String MSG_CONSTANCIA_PESADA = "La constancia fiscal no debe pesar más de 5 MB";
    public static final String MSG_REGISTRO_EXITOSO = "Cliente registrado con exito";
    public static final String MSG_MODIFICACION_EXITOSA = "Cliente modificado con exito";

    private static final Pattern CORREO = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern TELEFONO = Pattern.compile("^\\d+$");
    private static final int DIGITOS_TELEFONO = 10;
    private static final int MAX_NOMBRE = 50;
    private static final long MAX_CONSTANCIA_BYTES = 5L * 1024 * 1024;
    private static final Pattern ESPACIOS = Pattern.compile("\\s+");
    private static final Pattern ACENTOS = Pattern.compile("\\p{M}+");
    private static final byte[] FIRMA_PDF = {'%', 'P', 'D', 'F'};

    private static final Path CARPETA_CONSTANCIAS = Paths.get(System.getProperty(
            "lilapp.constancias.dir",
            Paths.get(System.getProperty("user.home"), "lilapp", "constancias").toString()));

    /** Valida que los datos obligatorios del cliente esten completos y con formato correcto. */
    public List<String> validar(String nombreCompleto, Cliente cliente, boolean faltaConstancia) {
        List<String> errores = new ArrayList<>();

        List<String> faltantes = new ArrayList<>();
        if (vacio(nombreCompleto)) {
            faltantes.add("nombre");
        }
        if (vacio(cliente.getTelefono())) {
            faltantes.add("teléfono");
        }
        if (vacio(cliente.getCorreo())) {
            faltantes.add("correo electrónico");
        }
        if (faltaConstancia) {
            faltantes.add("constancia fiscal");
        }
        if (!faltantes.isEmpty()) {
            errores.add(MSG_CAMPOS_OBLIGATORIOS + ". Falta: " + String.join(", ", faltantes));
        }

        if (!vacio(nombreCompleto)) {
            String[] partes = palabras(nombreCompleto);
            if (String.join(" ", partes).length() > MAX_NOMBRE) {
                errores.add("El nombre no puede tener más de " + MAX_NOMBRE + " caracteres");
            } else if (partes.length < 2) {
                errores.add("Escriba el nombre y al menos el apellido paterno del cliente");
            }
        }
        if (!vacio(cliente.getTelefono())) {
            String telefono = cliente.getTelefono().trim();
            if (!TELEFONO.matcher(telefono).matches()) {
                errores.add("El teléfono solo puede contener números");
            } else if (telefono.length() != DIGITOS_TELEFONO) {
                errores.add("El teléfono debe tener 10 dígitos");
            }
        }
        if (!vacio(cliente.getCorreo()) && !CORREO.matcher(cliente.getCorreo().trim()).matches()) {
            errores.add("El correo electrónico no tiene un formato válido");
        }
        return errores;
    }

    /** Valida tamano y formato de la constancia; devuelve el mensaje de error o null si es valida. */
    public String validarConstancia(byte[] contenido, long tamano) {
        if (tamano > MAX_CONSTANCIA_BYTES) {
            return MSG_CONSTANCIA_PESADA;
        }
        if (!esPdf(contenido)) {
            return MSG_SOLO_PDF;
        }
        return null;
    }

    /** Verifica que el contenido del archivo sea realmente un PDF. */
    private boolean esPdf(byte[] contenido) {
        if (contenido == null || contenido.length < FIRMA_PDF.length) {
            return false;
        }
        for (int i = 0; i < FIRMA_PDF.length; i++) {
            if (contenido[i] != FIRMA_PDF[i]) {
                return false;
            }
        }
        return true;
    }

    /** Une nombres y apellidos del cliente en un solo texto. */
    public String nombreCompleto(Cliente c) {
        StringBuilder sb = new StringBuilder();
        for (String parte : new String[]{c.getNombres(), c.getApellidoPaterno(), c.getApellidoMaterno()}) {
            if (!vacio(parte)) {
                if (sb.length() > 0) {
                    sb.append(' ');
                }
                sb.append(parte.trim());
            }
        }
        return sb.toString();
    }

    /** Separa el nombre completo en nombres, apellido paterno y apellido materno. */
    public void asignarNombreCompleto(Cliente c, String nombreCompleto) {
        String[] p = palabras(nombreCompleto);
        if (p.length == 0) {
            c.setNombres(null);
            c.setApellidoPaterno(null);
            c.setApellidoMaterno(null);
        } else if (p.length == 1) {
            c.setNombres(p[0]);
            c.setApellidoPaterno(null);
            c.setApellidoMaterno(null);
        } else if (p.length == 2) {
            c.setNombres(p[0]);
            c.setApellidoPaterno(p[1]);
            c.setApellidoMaterno(null);
        } else {
            c.setNombres(String.join(" ", Arrays.copyOfRange(p, 0, p.length - 2)));
            c.setApellidoPaterno(p[p.length - 2]);
            c.setApellidoMaterno(p[p.length - 1]);
        }
    }

    /** Guarda la constancia en disco y registra el cliente; si el registro falla borra el PDF. */
    public void guardar(Cliente cliente, byte[] constanciaPdf) throws IOException {
        normalizar(cliente);
        Path archivo = guardarConstancia(constanciaPdf);
        cliente.setConstanciaFiscal(archivo.toString());
        try {
            ServiceFacadeLocator.getInstanceFacadeCliente().guardar(cliente);
        } catch (RuntimeException e) {
            Files.deleteIfExists(archivo);
            cliente.setConstanciaFiscal(null);
            throw e;
        }
    }

    /** Devuelve todos los clientes registrados. */
    public List<Cliente> listarClientes() {
        return ServiceFacadeLocator.getInstanceFacadeCliente().listar();
    }

    /** Devuelve los clientes cuyo nombre contiene el texto buscado, sin distinguir mayusculas ni acentos. */
    public List<Cliente> filtrar(List<Cliente> clientes, String texto) {
        if (vacio(texto)) {
            return clientes;
        }
        String buscado = paraBusqueda(String.join(" ", palabras(texto)));
        List<Cliente> resultado = new ArrayList<>();
        for (Cliente c : clientes) {
            if (paraBusqueda(nombreCompleto(c)).contains(buscado)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /** Quita acentos y pone el texto en minusculas para compararlo. */
    private static String paraBusqueda(String s) {
        return ACENTOS.matcher(Normalizer.normalize(s, Normalizer.Form.NFD)).replaceAll("").toLowerCase();
    }

    /** Actualiza el cliente y reemplaza su constancia si se adjunto un PDF nuevo. */
    public Cliente actualizar(Cliente cliente, byte[] nuevaConstanciaPdf) throws IOException {
        normalizar(cliente);
        String constanciaAnterior = cliente.getConstanciaFiscal();
        Path nuevoArchivo = null;
        if (nuevaConstanciaPdf != null) {
            nuevoArchivo = guardarConstancia(nuevaConstanciaPdf);
            cliente.setConstanciaFiscal(nuevoArchivo.toString());
        }
        Cliente actualizado;
        try {
            actualizado = ServiceFacadeLocator.getInstanceFacadeCliente().actualizar(cliente);
        } catch (RuntimeException e) {
            if (nuevoArchivo != null) {
                Files.deleteIfExists(nuevoArchivo);
                cliente.setConstanciaFiscal(constanciaAnterior);
            }
            throw e;
        }
        if (nuevoArchivo != null && constanciaAnterior != null) {
            Files.deleteIfExists(Paths.get(constanciaAnterior));
        }
        return actualizado;
    }

    /** Crea una copia del cliente para editarla sin modificar el original. */
    public Cliente copiar(Cliente origen) {
        Cliente copia = new Cliente();
        copia.setIdCliente(origen.getIdCliente());
        copia.setNombres(origen.getNombres());
        copia.setApellidoPaterno(origen.getApellidoPaterno());
        copia.setApellidoMaterno(origen.getApellidoMaterno());
        copia.setCorreo(origen.getCorreo());
        copia.setTelefono(origen.getTelefono());
        copia.setConstanciaFiscal(origen.getConstanciaFiscal());
        copia.setEstatus(origen.getEstatus());
        return copia;
    }

    /** Indica si el archivo de la constancia existe en disco. */
    public boolean existeConstancia(String ruta) {
        return ruta != null && Files.isRegularFile(Paths.get(ruta));
    }

    /** Guarda el PDF en la carpeta de constancias con un nombre unico. */
    private Path guardarConstancia(byte[] contenido) throws IOException {
        Files.createDirectories(CARPETA_CONSTANCIAS);
        Path destino = CARPETA_CONSTANCIAS.resolve(UUID.randomUUID() + ".pdf");
        Files.write(destino, contenido);
        return destino;
    }

    /** Quita espacios del correo y del telefono, y pone el correo en minusculas. */
    private void normalizar(Cliente c) {
        c.setCorreo(c.getCorreo() == null ? null : c.getCorreo().trim().toLowerCase());
        c.setTelefono(c.getTelefono() == null ? null : c.getTelefono().trim());
    }

    /** Divide un texto en palabras. */
    private static String[] palabras(String s) {
        return vacio(s) ? new String[0] : ESPACIOS.split(s.trim());
    }

    /** Indica si un texto es nulo o solo tiene espacios. */
    private static boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }
}
