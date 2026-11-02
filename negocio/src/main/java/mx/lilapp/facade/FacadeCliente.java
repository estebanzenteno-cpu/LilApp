package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateCliente;
import mx.lilapp.entity.Cliente;

import java.util.List;

public class FacadeCliente {

    private final DelegateCliente delegateCliente = new DelegateCliente();

    public void guardar(Cliente cliente) {
        delegateCliente.guardar(cliente);
    }

    public Cliente actualizar(Cliente cliente) {
        return delegateCliente.actualizar(cliente);
    }

    public void eliminar(Cliente cliente) {
        delegateCliente.eliminar(cliente);
    }

    public Cliente buscarPorId(Integer id) {
        return delegateCliente.buscarPorId(id);
    }

    public List<Cliente> listar() {
        return delegateCliente.listar();
    }
}
