package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Usuario;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class UsuarioDAO extends AbstractDAO<Usuario> {

    public UsuarioDAO() {
        super(Usuario.class);
    }

    // Consultas propias de Usuario aqui
}
