package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateTipoProducto;
import mx.lilapp.entity.TipoProducto;

import java.util.List;

public class FacadeTipoProducto {

    private final DelegateTipoProducto delegateTipoProducto = new DelegateTipoProducto();

    public void guardar(TipoProducto tipoProducto) {
        delegateTipoProducto.guardar(tipoProducto);
    }

    public TipoProducto actualizar(TipoProducto tipoProducto) {
        return delegateTipoProducto.actualizar(tipoProducto);
    }

    public void eliminar(TipoProducto tipoProducto) {
        delegateTipoProducto.eliminar(tipoProducto);
    }

    public TipoProducto buscarPorId(Integer id) {
        return delegateTipoProducto.buscarPorId(id);
    }

    public List<TipoProducto> listar() {
        return delegateTipoProducto.listar();
    }
}
