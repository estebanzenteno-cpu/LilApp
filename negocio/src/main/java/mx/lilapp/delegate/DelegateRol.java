package mx.lilapp.delegate;

import mx.lilapp.entity.Rol;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateRol {

    /** Registra un rol nuevo. */
    public void guardar(Rol rol) {
        ServiceLocator.getInstanceRolDAO().save(rol);
    }

    /** Actualiza la informacion de un rol. */
    public Rol actualizar(Rol rol) {
        return ServiceLocator.getInstanceRolDAO().update(rol);
    }

    /** Elimina un rol. */
    public void eliminar(Rol rol) {
        ServiceLocator.getInstanceRolDAO().delete(rol);
    }

    /** Busca un rol por su id. */
    public Rol buscarPorId(Integer id) {
        return ServiceLocator.getInstanceRolDAO().find(id);
    }

    /** Devuelve todos los roles. */
    public List<Rol> listar() {
        return ServiceLocator.getInstanceRolDAO().findAll();
    }
}
