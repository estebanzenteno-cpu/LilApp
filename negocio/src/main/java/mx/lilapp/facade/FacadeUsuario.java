package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateUsuario;
import mx.lilapp.entity.Usuario;

import java.util.List;

public class FacadeUsuario {

    private final DelegateUsuario delegateUsuario = new DelegateUsuario();

    /** Registra un usuario nuevo. */
    public void guardar(Usuario usuario) {
        delegateUsuario.guardar(usuario);
    }

    /** Actualiza la informacion de un usuario. */
    public Usuario actualizar(Usuario usuario) {
        return delegateUsuario.actualizar(usuario);
    }

    /** Elimina un usuario. */
    public void eliminar(Usuario usuario) {
        delegateUsuario.eliminar(usuario);
    }

    /** Busca un usuario por su id. */
    public Usuario buscarPorId(Integer id) {
        return delegateUsuario.buscarPorId(id);
    }

    /** Devuelve todos los usuarios. */
    public List<Usuario> listar() {
        return delegateUsuario.listar();
    }
}
