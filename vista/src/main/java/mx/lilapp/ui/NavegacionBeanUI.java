package mx.lilapp.ui;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("navegacionUI")
@ViewScoped
public class NavegacionBeanUI implements Serializable {

    public static final String MENU = "menu";
    public static final String REGISTRAR = "registrar";
    public static final String MODIFICAR = "modificar";
    public static final String CONSULTAR_CLIENTE = "consulta-cliente";
    public static final String ELIMINAR_CLIENTE = "eliminar-cliente";
    public static final String REGISTRAR_PROVEEDOR = "registrar-proveedor";
    public static final String CONSULTAR_PROVEEDOR = "consulta-proveedor";
    public static final String MODIFICAR_PROVEEDOR = "modificar-proveedor";

    @Inject
    private ClienteBeanUI clienteUI;

    @Inject
    private ProveedorBeanUI proveedorUI;

    private String pagina = MENU;

    /** Cambia a la pantalla indicada y deja sus formularios limpios. */
    public void ir(String destino) {
        clienteUI.limpiarRegistro();
        clienteUI.cancelarModificacion();
        proveedorUI.cancelarEdicion();
        if (CONSULTAR_PROVEEDOR.equals(destino) || MODIFICAR_PROVEEDOR.equals(destino)) {
            proveedorUI.limpiarBusqueda();
        }
        pagina = destino;
    }

    /** Abre la consulta de clientes sin modificar el estado de los otros formularios. */
    public void consultarCliente() {
        pagina = CONSULTAR_CLIENTE;
    }

    /** Abre la eliminación de clientes sin modificar el estado de los otros formularios. */
    public void eliminarCliente() {
        pagina = ELIMINAR_CLIENTE;
    }

    /** Regresa a la lista si se esta editando un cliente, o al menu principal en otro caso. */
    public void regresar() {
        if (MODIFICAR_PROVEEDOR.equals(pagina) && proveedorUI.getProveedorEdicion() != null) {
            proveedorUI.cancelarEdicion();
        } else if (MODIFICAR.equals(pagina) && clienteUI.getClienteSeleccionado() != null) {
            clienteUI.cancelarModificacion();
        } else {
            ir(MENU);
        }
    }

    /** Devuelve el titulo que se muestra a la derecha del encabezado. */
    public String getTitulo() {
        switch (pagina) {
            case REGISTRAR:
                return "REGISTRAR CLIENTE";
            case MODIFICAR:
                return "MODIFICAR CLIENTE";
            case CONSULTAR_CLIENTE:
                return "CONSULTAR CLIENTE";
            case ELIMINAR_CLIENTE:
                return "ELIMINAR CLIENTE";
            case REGISTRAR_PROVEEDOR:
                return "REGISTRAR PROVEEDOR";
            case CONSULTAR_PROVEEDOR:
                return "CONSULTAR PROVEEDOR";
            case MODIFICAR_PROVEEDOR:
                return "MODIFICAR PROVEEDOR";
            default:
                return "";
        }
    }

    /** Indica si la pantalla actual es el menu principal. */
    public boolean isEnMenu() {
        return MENU.equals(pagina);
    }

    public String getPagina() {
        return pagina;
    }
}
