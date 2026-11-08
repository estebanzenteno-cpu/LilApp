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
    public static final String REGISTRAR_PROVEEDOR = "registrar-proveedor";

    @Inject
    private ClienteBeanUI clienteUI;

    private String pagina = MENU;

    /** Cambia a la pantalla indicada y deja sus formularios limpios. */
    public void ir(String destino) {
        clienteUI.limpiarRegistro();
        clienteUI.cancelarModificacion();
        pagina = destino;
    }

    /** Regresa a la lista si se esta editando un cliente, o al menu principal en otro caso. */
    public void regresar() {
        if (MODIFICAR.equals(pagina) && clienteUI.getClienteSeleccionado() != null) {
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
            case REGISTRAR_PROVEEDOR:
                return "REGISTRAR PROVEEDOR";
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
