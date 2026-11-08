package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateCliente;
import mx.lilapp.entity.Cliente;

import java.util.List;

public class FacadeCliente {

    private final DelegateCliente delegateCliente = new DelegateCliente();

    /** Registra un cliente nuevo. */
    public void guardar(Cliente cliente) {
        delegateCliente.guardar(cliente);
    }

    /** Actualiza la informacion de un cliente. */
    public Cliente actualizar(Cliente cliente) {
        return delegateCliente.actualizar(cliente);
    }

    /** Elimina un cliente. */
    public void eliminar(Cliente cliente) {
        delegateCliente.eliminar(cliente);
    }

    /** Busca un cliente por su id. */
    public Cliente buscarPorId(Integer id) {
        return delegateCliente.buscarPorId(id);
    }

    /** Devuelve todos los clientes. */
    public List<Cliente> listar() {
        return delegateCliente.listar();
    }
}
