package mx.lilapp.delegate;

import mx.lilapp.entity.Usuario;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateUsuario {

    /** Registra un usuario nuevo. */
    public void guardar(Usuario usuario) {
        ServiceLocator.getInstanceUsuarioDAO().save(usuario);
    }

    /** Actualiza la informacion de un usuario. */
    public Usuario actualizar(Usuario usuario) {
        return ServiceLocator.getInstanceUsuarioDAO().update(usuario);
    }

    /** Elimina un usuario. */
    public void eliminar(Usuario usuario) {
        ServiceLocator.getInstanceUsuarioDAO().delete(usuario);
    }

    /** Busca un usuario por su id. */
    public Usuario buscarPorId(Integer id) {
        return ServiceLocator.getInstanceUsuarioDAO().find(id);
    }

    /** Devuelve todos los usuarios. */
    public List<Usuario> listar() {
        return ServiceLocator.getInstanceUsuarioDAO().findAll();
    }
}
