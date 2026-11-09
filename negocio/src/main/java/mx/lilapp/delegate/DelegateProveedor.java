package mx.lilapp.delegate;

import mx.lilapp.entity.Proveedor;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateProveedor {

    /** Registra un proveedor nuevo. */
    public void guardar(Proveedor proveedor) {
        ServiceLocator.getInstanceProveedorDAO().save(proveedor);
    }

    /** Actualiza la informacion de un proveedor. */
    public Proveedor actualizar(Proveedor proveedor) {
        return ServiceLocator.getInstanceProveedorDAO().update(proveedor);
    }

    /** Elimina un proveedor. */
    public void eliminar(Proveedor proveedor) {
        ServiceLocator.getInstanceProveedorDAO().delete(proveedor);
    }

    /** Busca un proveedor por su id. */
    public Proveedor buscarPorId(Integer id) {
        return ServiceLocator.getInstanceProveedorDAO().find(id);
    }

    /** Devuelve todos los proveedores. */
    public List<Proveedor> listar() {
        return ServiceLocator.getInstanceProveedorDAO().findAll();
    }

    public List<Proveedor> listarOrdenados() {
        return ServiceLocator.getInstanceProveedorDAO().listarOrdenados();
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        return ServiceLocator.getInstanceProveedorDAO().buscarPorNombre(nombre);
    }
}
