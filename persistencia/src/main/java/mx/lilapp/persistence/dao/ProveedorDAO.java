package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Proveedor;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class ProveedorDAO extends AbstractDAO<Proveedor> {

    /** Indica que este DAO trabaja con la entidad Proveedor. */
    public ProveedorDAO() {
        super(Proveedor.class);
    }

}
