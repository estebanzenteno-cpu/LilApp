package mx.lilapp.delegate;

import mx.lilapp.entity.Usuario;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateUsuario {

    public void guardar(Usuario usuario) {
        ServiceLocator.getInstanceUsuarioDAO().save(usuario);
    }

    public Usuario actualizar(Usuario usuario) {
        return ServiceLocator.getInstanceUsuarioDAO().update(usuario);
    }

    public void eliminar(Usuario usuario) {
        ServiceLocator.getInstanceUsuarioDAO().delete(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return ServiceLocator.getInstanceUsuarioDAO().find(id);
    }

    public List<Usuario> listar() {
        return ServiceLocator.getInstanceUsuarioDAO().findAll();
    }
}
