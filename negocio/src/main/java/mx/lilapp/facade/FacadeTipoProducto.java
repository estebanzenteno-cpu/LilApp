package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateTipoProducto;
import mx.lilapp.entity.TipoProducto;

import java.util.List;

public class FacadeTipoProducto {

    private final DelegateTipoProducto delegateTipoProducto = new DelegateTipoProducto();

    /** Registra un tipo de producto nuevo. */
    public void guardar(TipoProducto tipoProducto) {
        delegateTipoProducto.guardar(tipoProducto);
    }

    /** Actualiza la informacion de un tipo de producto. */
    public TipoProducto actualizar(TipoProducto tipoProducto) {
        return delegateTipoProducto.actualizar(tipoProducto);
    }

    /** Elimina un tipo de producto. */
    public void eliminar(TipoProducto tipoProducto) {
        delegateTipoProducto.eliminar(tipoProducto);
    }

    /** Busca un tipo de producto por su id. */
    public TipoProducto buscarPorId(Integer id) {
        return delegateTipoProducto.buscarPorId(id);
    }

    /** Devuelve todos los tipos de producto. */
    public List<TipoProducto> listar() {
        return delegateTipoProducto.listar();
    }
}
