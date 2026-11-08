package mx.lilapp.delegate;

import mx.lilapp.entity.TipoProducto;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateTipoProducto {

    /** Registra un tipo de producto nuevo. */
    public void guardar(TipoProducto tipoProducto) {
        ServiceLocator.getInstanceTipoProductoDAO().save(tipoProducto);
    }

    /** Actualiza la informacion de un tipo de producto. */
    public TipoProducto actualizar(TipoProducto tipoProducto) {
        return ServiceLocator.getInstanceTipoProductoDAO().update(tipoProducto);
    }

    /** Elimina un tipo de producto. */
    public void eliminar(TipoProducto tipoProducto) {
        ServiceLocator.getInstanceTipoProductoDAO().delete(tipoProducto);
    }

    /** Busca un tipo de producto por su id. */
    public TipoProducto buscarPorId(Integer id) {
        return ServiceLocator.getInstanceTipoProductoDAO().find(id);
    }

    /** Devuelve todos los tipos de producto. */
    public List<TipoProducto> listar() {
        return ServiceLocator.getInstanceTipoProductoDAO().findAll();
    }
}
