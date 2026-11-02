package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Cliente;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class ClienteDAO extends AbstractDAO<Cliente> {

    public ClienteDAO() {
        super(Cliente.class);
    }

    // Consultas propias de Cliente aqui
}
