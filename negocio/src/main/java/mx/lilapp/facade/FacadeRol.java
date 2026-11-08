package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateRol;
import mx.lilapp.entity.Rol;

import java.util.List;

public class FacadeRol {

    private final DelegateRol delegateRol = new DelegateRol();

    /** Registra un rol nuevo. */
    public void guardar(Rol rol) {
        delegateRol.guardar(rol);
    }

    /** Actualiza la informacion de un rol. */
    public Rol actualizar(Rol rol) {
        return delegateRol.actualizar(rol);
    }

    /** Elimina un rol. */
    public void eliminar(Rol rol) {
        delegateRol.eliminar(rol);
    }

    /** Busca un rol por su id. */
    public Rol buscarPorId(Integer id) {
        return delegateRol.buscarPorId(id);
    }

    /** Devuelve todos los roles. */
    public List<Rol> listar() {
        return delegateRol.listar();
    }
}
