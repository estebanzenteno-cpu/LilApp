package mx.lilapp.delegate;

import mx.lilapp.entity.Cliente;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateCliente {

    public void guardar(Cliente cliente) {
        ServiceLocator.getInstanceClienteDAO().save(cliente);
    }

    public Cliente actualizar(Cliente cliente) {
        return ServiceLocator.getInstanceClienteDAO().update(cliente);
    }

    public void eliminar(Cliente cliente) {
        ServiceLocator.getInstanceClienteDAO().delete(cliente);
    }

    public Cliente buscarPorId(Integer id) {
        return ServiceLocator.getInstanceClienteDAO().find(id);
    }

    public List<Cliente> listar() {
        return ServiceLocator.getInstanceClienteDAO().findAll();
    }
}
