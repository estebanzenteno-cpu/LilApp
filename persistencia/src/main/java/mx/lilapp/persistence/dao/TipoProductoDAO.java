package mx.lilapp.persistence.dao;

import mx.lilapp.entity.TipoProducto;
import mx.lilapp.persistence.persistence.AbstractDAO;

public class TipoProductoDAO extends AbstractDAO<TipoProducto> {

    public TipoProductoDAO() {
        super(TipoProducto.class);
    }

    // Consultas propias de TipoProducto aqui
}
