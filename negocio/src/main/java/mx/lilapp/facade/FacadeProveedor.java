package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateProveedor;
import mx.lilapp.entity.Proveedor;

import java.util.List;

public class FacadeProveedor {

    private final DelegateProveedor delegateProveedor = new DelegateProveedor();

    /** Registra un proveedor nuevo. */
    public void guardar(Proveedor proveedor) {
        delegateProveedor.guardar(proveedor);
    }

    /** Actualiza la informacion de un proveedor. */
    public Proveedor actualizar(Proveedor proveedor) {
        return delegateProveedor.actualizar(proveedor);
    }

    /** Elimina un proveedor. */
    public void eliminar(Proveedor proveedor) {
        delegateProveedor.eliminar(proveedor);
    }

    /** Busca un proveedor por su id. */
    public Proveedor buscarPorId(Integer id) {
        return delegateProveedor.buscarPorId(id);
    }

    /** Devuelve todos los proveedores. */
    public List<Proveedor> listar() {
        return delegateProveedor.listar();
    }
}
