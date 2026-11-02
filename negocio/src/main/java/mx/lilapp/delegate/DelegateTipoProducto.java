package mx.lilapp.delegate;

import mx.lilapp.entity.TipoProducto;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateTipoProducto {

    public void guardar(TipoProducto tipoProducto) {
        ServiceLocator.getInstanceTipoProductoDAO().save(tipoProducto);
    }

    public TipoProducto actualizar(TipoProducto tipoProducto) {
        return ServiceLocator.getInstanceTipoProductoDAO().update(tipoProducto);
    }

    public void eliminar(TipoProducto tipoProducto) {
        ServiceLocator.getInstanceTipoProductoDAO().delete(tipoProducto);
    }

    public TipoProducto buscarPorId(Integer id) {
        return ServiceLocator.getInstanceTipoProductoDAO().find(id);
    }

    public List<TipoProducto> listar() {
        return ServiceLocator.getInstanceTipoProductoDAO().findAll();
    }
}
