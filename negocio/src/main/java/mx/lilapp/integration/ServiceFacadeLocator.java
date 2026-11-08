package mx.lilapp.integration;

import mx.lilapp.facade.*;

public final class ServiceFacadeLocator {

    private static FacadeCliente facadeCliente;
    private static FacadeProveedor facadeProveedor;
    private static FacadeTipoProducto facadeTipoProducto;
    private static FacadeRol facadeRol;
    private static FacadeUsuario facadeUsuario;

    /** Evita que se creen instancias de esta clase. */
    private ServiceFacadeLocator() {
    }

    /** Devuelve la instancia unica de FacadeCliente. */
    public static synchronized FacadeCliente getInstanceFacadeCliente() {
        if (facadeCliente == null) {
            facadeCliente = new FacadeCliente();
        }
        return facadeCliente;
    }

    /** Devuelve la instancia unica de FacadeProveedor. */
    public static synchronized FacadeProveedor getInstanceFacadeProveedor() {
        if (facadeProveedor == null) {
            facadeProveedor = new FacadeProveedor();
        }
        return facadeProveedor;
    }

    /** Devuelve la instancia unica de FacadeTipoProducto. */
    public static synchronized FacadeTipoProducto getInstanceFacadeTipoProducto() {
        if (facadeTipoProducto == null) {
            facadeTipoProducto = new FacadeTipoProducto();
        }
        return facadeTipoProducto;
    }

    /** Devuelve la instancia unica de FacadeRol. */
    public static synchronized FacadeRol getInstanceFacadeRol() {
        if (facadeRol == null) {
            facadeRol = new FacadeRol();
        }
        return facadeRol;
    }

    /** Devuelve la instancia unica de FacadeUsuario. */
    public static synchronized FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
        }
        return facadeUsuario;
    }
}
