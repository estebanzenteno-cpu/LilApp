package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Cliente;
import mx.lilapp.persistence.persistence.AbstractDAO;

import java.util.List;

public class ClienteDAO extends AbstractDAO<Cliente> {


    public ClienteDAO() {
        super(Cliente.class);
    }

    /** Devuelve todos los clientes registrados. */
    public List<Cliente> listarClientes() {
        return findAll();
    }

    /** Busca un cliente por su identificador. */
    public Cliente buscarClientePorId(Integer idCliente) {
        return idCliente == null ? null : find(idCliente);
    }

    /** Elimina un cliente existente. */
    public void eliminarCliente(Cliente cliente) {
        delete(cliente);
    }

    /** Busca un cliente por su correo sin distinguir mayusculas; devuelve null si no existe. */
    public Cliente findByCorreo(String correo) {
        List<Cliente> resultado = getEntityManager()
                .createQuery("SELECT c FROM Cliente c WHERE LOWER(c.correo) = LOWER(:correo)", Cliente.class)
                .setParameter("correo", correo)
                .setMaxResults(1)
                .getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
