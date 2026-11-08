package mx.lilapp.persistence.integration;

import mx.lilapp.persistence.dao.*;

public final class ServiceLocator {

    private static ClienteDAO clienteDAO;
    private static ProveedorDAO proveedorDAO;
    private static TipoProductoDAO tipoProductoDAO;
    private static RolDAO rolDAO;
    private static UsuarioDAO usuarioDAO;

    /** Evita que se creen instancias de esta clase. */
    private ServiceLocator() {
    }

    /** Devuelve la instancia unica de ClienteDAO. */
    public static synchronized ClienteDAO getInstanceClienteDAO() {
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAO();
        }
        return clienteDAO;
    }

    /** Devuelve la instancia unica de ProveedorDAO. */
    public static synchronized ProveedorDAO getInstanceProveedorDAO() {
        if (proveedorDAO == null) {
            proveedorDAO = new ProveedorDAO();
        }
        return proveedorDAO;
    }

    /** Devuelve la instancia unica de TipoProductoDAO. */
    public static synchronized TipoProductoDAO getInstanceTipoProductoDAO() {
        if (tipoProductoDAO == null) {
            tipoProductoDAO = new TipoProductoDAO();
        }
        return tipoProductoDAO;
    }

    /** Devuelve la instancia unica de RolDAO. */
    public static synchronized RolDAO getInstanceRolDAO() {
        if (rolDAO == null) {
            rolDAO = new RolDAO();
        }
        return rolDAO;
    }

    /** Devuelve la instancia unica de UsuarioDAO. */
    public static synchronized UsuarioDAO getInstanceUsuarioDAO() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }
}
