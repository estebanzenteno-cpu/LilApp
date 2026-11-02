package mx.lilapp.persistence.integration;

import mx.lilapp.persistence.dao.*;

/**
 * Punto unico de acceso a los DAO (una instancia por DAO).
 */
public final class ServiceLocator {

    private static ClienteDAO clienteDAO;
    private static ProveedorDAO proveedorDAO;
    private static TipoProductoDAO tipoProductoDAO;
    private static RolDAO rolDAO;
    private static UsuarioDAO usuarioDAO;

    private ServiceLocator() {
    }

    public static synchronized ClienteDAO getInstanceClienteDAO() {
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAO();
        }
        return clienteDAO;
    }

    public static synchronized ProveedorDAO getInstanceProveedorDAO() {
        if (proveedorDAO == null) {
            proveedorDAO = new ProveedorDAO();
        }
        return proveedorDAO;
    }

    public static synchronized TipoProductoDAO getInstanceTipoProductoDAO() {
        if (tipoProductoDAO == null) {
            tipoProductoDAO = new TipoProductoDAO();
        }
        return tipoProductoDAO;
    }

    public static synchronized RolDAO getInstanceRolDAO() {
        if (rolDAO == null) {
            rolDAO = new RolDAO();
        }
        return rolDAO;
    }

    public static synchronized UsuarioDAO getInstanceUsuarioDAO() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }
}
