package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateRol;
import mx.lilapp.entity.Rol;

import java.util.List;

public class FacadeRol {

    private final DelegateRol delegateRol = new DelegateRol();

    public void guardar(Rol rol) {
        delegateRol.guardar(rol);
    }

    public Rol actualizar(Rol rol) {
        return delegateRol.actualizar(rol);
    }

    public void eliminar(Rol rol) {
        delegateRol.eliminar(rol);
    }

    public Rol buscarPorId(Integer id) {
        return delegateRol.buscarPorId(id);
    }

    public List<Rol> listar() {
        return delegateRol.listar();
    }
}
