package mx.lilapp.ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.lilapp.entity.Cliente;
import mx.lilapp.exception.NegocioException;
import mx.lilapp.helper.ClienteHelper;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Named("eliminarClienteUI")
@ViewScoped
public class EliminarClienteBeanUI implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(EliminarClienteBeanUI.class.getName());
    private static final Pattern ACENTOS = Pattern.compile("\\p{M}+");

    private ClienteHelper helper;
    private List<Cliente> clientes = List.of();
    private String busqueda;
    private Cliente clienteSeleccionado;
    private boolean errorCarga;

    @PostConstruct
    public void init() {
        helper = new ClienteHelper();
        cargarClientes();
    }

    private void cargarClientes() {
        try {
            List<Cliente> encontrados = helper.listarClientes();
            clientes = encontrados == null ? List.of() : encontrados.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(this::nombreCompleto, String.CASE_INSENSITIVE_ORDER))
                    .collect(Collectors.toList());
            errorCarga = false;
        } catch (RuntimeException | LinkageError e) {
            clientes = List.of();
            errorCarga = true;
            LOGGER.log(Level.SEVERE, "No se pudo cargar la lista de clientes para eliminar.", e);
        }
    }

    public List<Cliente> getClientesFiltrados() {
        String termino = normalizar(busqueda == null ? "" : busqueda.trim());
        if (termino.isEmpty()) {
            return clientes;
        }
        return clientes.stream()
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

    public String getMensajeListaVacia() {
        if (errorCarga) {
            return "No se pueden cargar los clientes. Por el momento no hay clientes por eliminar.";
        }
        return busqueda == null || busqueda.isBlank()
                ? "No hay clientes que eliminar por el momento."
                : "No se encontraron clientes con ese criterio.";
    }

    public String getMensajeConfirmacion() {
        if (clienteSeleccionado == null) {
            return "¿Confirmas que deseas eliminar este cliente?";
        }
        return "¿Confirmas que deseas eliminar a "
                + nombreCompleto(clienteSeleccionado)
                + "? Esta acción no se puede deshacer.";
    }

    public void seleccionarCliente(Cliente cliente) {
        clienteSeleccionado = cliente;
    }

    public void confirmarEliminacion() {
        PrimeFaces.current().ajax().addCallbackParam("eliminacionFinalizada", true);
        PrimeFaces.current().ajax().addCallbackParam("eliminacionExitosa", false);

        if (clienteSeleccionado == null || clienteSeleccionado.getIdCliente() == null) {
            mostrarError("Selecciona un cliente para eliminar.");
            return;
        }

        Integer idCliente = clienteSeleccionado.getIdCliente();
        try {
            Cliente clienteActual = helper.buscarClientePorId(idCliente);
            if (clienteActual == null) {
                clientes.removeIf(cliente -> Objects.equals(cliente.getIdCliente(), idCliente));
                clienteSeleccionado = null;
                mostrarError("El cliente ya no existe. Actualiza la lista.");
                return;
            }

            helper.eliminarCliente(clienteActual);
            clientes.removeIf(cliente -> Objects.equals(cliente.getIdCliente(), idCliente));
            clienteSeleccionado = null;
            PrimeFaces.current().ajax().addCallbackParam("eliminacionExitosa", true);
        } catch (NegocioException e) {
            mostrarError(e.getMessage());
        } catch (RuntimeException | LinkageError e) {
            LOGGER.log(Level.SEVERE, "No se pudo eliminar el cliente.", e);
            mostrarError("No se pudo eliminar el cliente. Verifica la conexión con la base de datos.");
        }
    }

    private String normalizar(String texto) {
        String sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return ACENTOS.matcher(sinAcentos).replaceAll("").toLowerCase(Locale.ROOT);
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }

    private void mostrarError(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, null));
    }

    public List<Cliente> getClientes() {
        return clientes;
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

    public boolean isErrorCarga() {
        return errorCarga;
    }
}
