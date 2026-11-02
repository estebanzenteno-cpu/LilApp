package mx.lilapp.delegate;

import mx.lilapp.entity.Proveedor;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateProveedor {

    public void guardar(Proveedor proveedor) {
        ServiceLocator.getInstanceProveedorDAO().save(proveedor);
    }

    public Proveedor actualizar(Proveedor proveedor) {
        return ServiceLocator.getInstanceProveedorDAO().update(proveedor);
    }

    public void eliminar(Proveedor proveedor) {
        ServiceLocator.getInstanceProveedorDAO().delete(proveedor);
    }

    public Proveedor buscarPorId(Integer id) {
        return ServiceLocator.getInstanceProveedorDAO().find(id);
    }

    public List<Proveedor> listar() {
        return ServiceLocator.getInstanceProveedorDAO().findAll();
    }
}
