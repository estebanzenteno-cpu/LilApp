package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Rol;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class RolDAO extends AbstractDAO<Rol> {

    /** Indica que este DAO trabaja con la entidad Rol. */
    public RolDAO() {
        super(Rol.class);
    }

}
