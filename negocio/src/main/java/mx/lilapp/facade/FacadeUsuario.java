package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateUsuario;
import mx.lilapp.entity.Usuario;

import java.util.List;

public class FacadeUsuario {

    private final DelegateUsuario delegateUsuario = new DelegateUsuario();

    public void guardar(Usuario usuario) {
        delegateUsuario.guardar(usuario);
    }

    public Usuario actualizar(Usuario usuario) {
        return delegateUsuario.actualizar(usuario);
    }

    public void eliminar(Usuario usuario) {
        delegateUsuario.eliminar(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return delegateUsuario.buscarPorId(id);
    }

    public List<Usuario> listar() {
        return delegateUsuario.listar();
    }
}
