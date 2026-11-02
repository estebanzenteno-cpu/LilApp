package mx.lilapp.delegate;

import mx.lilapp.entity.Rol;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateRol {

    public void guardar(Rol rol) {
        ServiceLocator.getInstanceRolDAO().save(rol);
    }

    public Rol actualizar(Rol rol) {
        return ServiceLocator.getInstanceRolDAO().update(rol);
    }

    public void eliminar(Rol rol) {
        ServiceLocator.getInstanceRolDAO().delete(rol);
    }

    public Rol buscarPorId(Integer id) {
        return ServiceLocator.getInstanceRolDAO().find(id);
    }

    public List<Rol> listar() {
        return ServiceLocator.getInstanceRolDAO().findAll();
    }
}
