package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Usuario;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    /** Indica que este DAO trabaja con la entidad Usuario. */
    public UsuarioDAO() {
        super(Usuario.class);
    }

}
