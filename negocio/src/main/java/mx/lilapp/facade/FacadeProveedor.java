package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateProveedor;
import mx.lilapp.entity.Proveedor;

import java.util.List;

public class FacadeProveedor {

    private final DelegateProveedor delegateProveedor = new DelegateProveedor();

    public void guardar(Proveedor proveedor) {
        delegateProveedor.guardar(proveedor);
    }

    public Proveedor actualizar(Proveedor proveedor) {
        return delegateProveedor.actualizar(proveedor);
    }

    public void eliminar(Proveedor proveedor) {
        delegateProveedor.eliminar(proveedor);
    }

    public Proveedor buscarPorId(Integer id) {
        return delegateProveedor.buscarPorId(id);
    }

    public List<Proveedor> listar() {
        return delegateProveedor.listar();
    }
}
