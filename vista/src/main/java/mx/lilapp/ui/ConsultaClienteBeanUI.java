package mx.lilapp.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.lilapp.entity.Cliente;
import mx.lilapp.entity.EstatusCliente;
import mx.lilapp.helper.ClienteHelper;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named("consultaClienteUI")
@ViewScoped
public class ConsultaClienteBeanUI implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ConsultaClienteBeanUI.class.getName());
    private static final Pattern ACENTOS = Pattern.compile("\\p{M}+");

    private List<Cliente> clientesActivos = List.of();
    private ClienteHelper helper;
    private String busqueda;
    private Cliente clienteConsulta;
    private boolean hayClientesRegistrados;
    private boolean consultaFallida;

    @PostConstruct
    public void init() {
        helper = new ClienteHelper();
        try {
            List<Cliente> registrados = helper.listarClientes();
            if (registrados == null) {
                registrados = List.of();
            }

            hayClientesRegistrados = !registrados.isEmpty();
            clientesActivos = registrados.stream()
                    .filter(cliente -> cliente != null
                            && cliente.getEstatus() == EstatusCliente.activo)
                    .sorted(Comparator.comparing(
                            this::nombreCompleto,
                            String.CASE_INSENSITIVE_ORDER
                    ))
                    .collect(Collectors.toList());
        } catch (RuntimeException | LinkageError e) {
            consultaFallida = true;
            hayClientesRegistrados = false;
            clientesActivos = List.of();
            LOGGER.log(Level.SEVERE, "No se pudo cargar la consulta de clientes.", e);
        }
    }

    public List<Cliente> getClientesFiltrados() {
        String termino = normalizar(busqueda == null ? "" : busqueda.trim());
        if (termino.isEmpty()) {
            return clientesActivos;
        }

        return clientesActivos.stream()
                .filter(cliente -> {
                    String datos = nombreCompleto(cliente) + " "
                            + valorSeguro(cliente.getCorreo()) + " "
                            + valorSeguro(cliente.getTelefono());
                    return normalizar(datos).contains(termino);
                })
                .collect(Collectors.toList());
    }

    public String nombreCompleto(Cliente cliente) {
        if (cliente == null) {
            return "";
        }
        return helper.nombreCompleto(cliente);
    }

    public void verCliente(Cliente cliente) {
        clienteConsulta = cliente;
    }

    private String normalizar(String texto) {
        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return ACENTOS.matcher(sinAcentos).replaceAll("").toLowerCase(Locale.ROOT);
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }

    public String getMensajeEstadoVacio() {
        return "No hay clientes que consultar por el momento.";
    }

    public String getTituloAvisoInicial() {
        return hayClientesRegistrados ? "Sin clientes activos" : "Sin clientes registrados";
    }

    public String getMensajeAvisoInicial() {
        return getMensajeEstadoVacio();
    }

    public boolean isMostrarAvisoInicial() {
        return !consultaFallida && clientesActivos.isEmpty();
    }

    public List<Cliente> getClientesActivos() {
        return clientesActivos;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public Cliente getClienteConsulta() {
        return clienteConsulta;
    }
}
