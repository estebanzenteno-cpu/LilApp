package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Proveedor;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class ProveedorDAO extends AbstractDAO<Proveedor> {

    public ProveedorDAO() {
        super(Proveedor.class);
    }

    // Consultas propias de Proveedor aqui
}
