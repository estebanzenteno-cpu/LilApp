package mx.lilapp.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.lilapp.entity.Cliente;
import mx.lilapp.exception.NegocioException;
import mx.lilapp.helper.ClienteHelper;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("clienteUI")
@ViewScoped
public class ClienteBeanUI implements Serializable {

    private ClienteHelper helper;

    private Cliente cliente;
    private String nombreCompleto;
    private byte[] constanciaPdf;
    private String nombreConstancia;

    private List<Cliente> clientes;
    private String busqueda;
    private Cliente clienteSeleccionado;
    private String nombreCompletoSeleccionado;
    private byte[] nuevaConstanciaPdf;
    private String nombreNuevaConstancia;

    @PostConstruct
    /** Inicializa el helper y el formulario de registro. */
    public void init() {
        helper = new ClienteHelper();
        limpiarRegistro();
    }

    /** Recibe el PDF de la constancia fiscal del formulario de registro. */
    public void subirConstanciaFiscal(FileUploadEvent event) {
        UploadedFile archivo = event.getFile();
        String errorConstancia = helper.validarConstancia(archivo.getContent(), archivo.getSize());
        if (errorConstancia != null) {
            error(errorConstancia);
            return;
        }
        constanciaPdf = archivo.getContent();
        nombreConstancia = archivo.getFileName();
    }

    /** Valida y registra el cliente capturado. */
    public void registrarCliente() {
        List<String> errores = helper.validar(nombreCompleto, cliente, constanciaPdf == null);
        if (!errores.isEmpty()) {
            errores.forEach(this::error);
            return;
        }
        try {
            helper.asignarNombreCompleto(cliente, nombreCompleto);
            helper.guardar(cliente, constanciaPdf);
            info(ClienteHelper.MSG_REGISTRO_EXITOSO);
            limpiarRegistro();
        } catch (NegocioException e) {
            error(e.getMessage());
        } catch (IOException e) {
            error("No se pudo registrar el cliente: no se pudo guardar la constancia fiscal");
        } catch (RuntimeException e) {
            error("No se pudo registrar el cliente: " + e.getMessage());
        }
    }

    /** Limpia el formulario de registro. */
    public void limpiarRegistro() {
        cliente = new Cliente();
        nombreCompleto = null;
        constanciaPdf = null;
        nombreConstancia = null;
    }

    /** Carga los datos del cliente seleccionado en el formulario de modificacion. */
    public void seleccionarCliente(Cliente c) {
        clienteSeleccionado = helper.copiar(c);
        nombreCompletoSeleccionado = helper.nombreCompleto(c);
        nuevaConstanciaPdf = null;
        nombreNuevaConstancia = null;
    }

    /** Recibe el PDF que reemplazara la constancia actual del cliente. */
    public void subirNuevaConstanciaFiscal(FileUploadEvent event) {
        UploadedFile archivo = event.getFile();
        String errorConstancia = helper.validarConstancia(archivo.getContent(), archivo.getSize());
        if (errorConstancia != null) {
            error(errorConstancia);
            return;
        }
        nuevaConstanciaPdf = archivo.getContent();
        nombreNuevaConstancia = archivo.getFileName();
    }

    /** Valida y guarda los cambios del cliente seleccionado. */
    public void actualizarCliente() {
        if (clienteSeleccionado == null) {
            error("Seleccione un cliente para modificar");
            return;
        }
        boolean faltaConstancia = nuevaConstanciaPdf == null
                && !helper.existeConstancia(clienteSeleccionado.getConstanciaFiscal());
        List<String> errores = helper.validar(nombreCompletoSeleccionado, clienteSeleccionado, faltaConstancia);
        if (!errores.isEmpty()) {
            errores.forEach(this::error);
            return;
        }
        try {
            helper.asignarNombreCompleto(clienteSeleccionado, nombreCompletoSeleccionado);
            helper.actualizar(clienteSeleccionado, nuevaConstanciaPdf);
            info(ClienteHelper.MSG_MODIFICACION_EXITOSA);
            cancelarModificacion();
        } catch (NegocioException e) {
            error(e.getMessage());
        } catch (IOException e) {
            error("No se pudo modificar el cliente: no se pudo guardar la constancia fiscal");
        } catch (RuntimeException e) {
            error("No se pudo modificar el cliente: " + e.getMessage());
        }
    }

    /** Limpia los campos del formulario de modificacion. */
    public void limpiarModificacion() {
        if (clienteSeleccionado != null) {
            nombreCompletoSeleccionado = null;
            clienteSeleccionado.setTelefono(null);
            clienteSeleccionado.setCorreo(null);
        }
        nuevaConstanciaPdf = null;
        nombreNuevaConstancia = null;
    }

    /** Regresa a la lista de clientes. */
    public void cancelarModificacion() {
        clienteSeleccionado = null;
        nombreCompletoSeleccionado = null;
        nuevaConstanciaPdf = null;
        nombreNuevaConstancia = null;
        clientes = null;
        busqueda = null;
    }

    /** Muestra un aviso de exito. */
    private void info(String msg) {
        agregar(FacesMessage.SEVERITY_INFO, msg);
    }

    /** Muestra un aviso de error y marca la validacion como fallida. */
    private void error(String msg) {
        agregar(FacesMessage.SEVERITY_ERROR, msg);
        FacesContext.getCurrentInstance().validationFailed();
    }

    /** Agrega un mensaje para mostrarlo en pantalla. */
    private void agregar(FacesMessage.Severity severidad, String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, msg, null));
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreConstancia() {
        return nombreConstancia;
    }

    /** Devuelve la lista de clientes, cargandola la primera vez que se pide. */
    public List<Cliente> getClientes() {
        if (clientes == null) {
            clientes = helper.listarClientes();
        }
        return clientes;
    }

    /** Devuelve los clientes que coinciden con el texto del buscador. */
    public List<Cliente> getClientesFiltrados() {
        return helper.filtrar(getClientes(), busqueda);
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public Cliente getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public String getNombreCompletoSeleccionado() {
        return nombreCompletoSeleccionado;
    }

    public void setNombreCompletoSeleccionado(String nombreCompletoSeleccionado) {
        this.nombreCompletoSeleccionado = nombreCompletoSeleccionado;
    }

    public String getNombreNuevaConstancia() {
        return nombreNuevaConstancia;
    }
}
