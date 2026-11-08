package mx.lilapp.persistence.dao;

import mx.lilapp.entity.TipoProducto;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class TipoProductoDAO extends AbstractDAO<TipoProducto> {

    /** Indica que este DAO trabaja con la entidad TipoProducto. */
    public TipoProductoDAO() {
        super(TipoProducto.class);
    }

}
