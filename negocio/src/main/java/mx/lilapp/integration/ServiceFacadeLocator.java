package mx.lilapp.integration;

import mx.lilapp.facade.*;

/**
 * Punto unico de acceso a los Facade desde la capa vista (los usan los Helper).
 */
public final class ServiceFacadeLocator {

    private static FacadeCliente facadeCliente;
    private static FacadeProveedor facadeProveedor;
    private static FacadeTipoProducto facadeTipoProducto;
    private static FacadeRol facadeRol;
    private static FacadeUsuario facadeUsuario;

    private ServiceFacadeLocator() {
    }

    public static synchronized FacadeCliente getInstanceFacadeCliente() {
        if (facadeCliente == null) {
            facadeCliente = new FacadeCliente();
        }
        return facadeCliente;
    }

    public static synchronized FacadeProveedor getInstanceFacadeProveedor() {
        if (facadeProveedor == null) {
            facadeProveedor = new FacadeProveedor();
        }
        return facadeProveedor;
    }

    public static synchronized FacadeTipoProducto getInstanceFacadeTipoProducto() {
        if (facadeTipoProducto == null) {
            facadeTipoProducto = new FacadeTipoProducto();
        }
        return facadeTipoProducto;
    }

    public static synchronized FacadeRol getInstanceFacadeRol() {
        if (facadeRol == null) {
            facadeRol = new FacadeRol();
        }
        return facadeRol;
    }

    public static synchronized FacadeUsuario getInstanceFacadeUsuario() {
        if (facadeUsuario == null) {
            facadeUsuario = new FacadeUsuario();
        }
        return facadeUsuario;
    }
}
