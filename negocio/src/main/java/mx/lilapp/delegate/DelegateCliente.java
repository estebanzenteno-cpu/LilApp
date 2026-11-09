package mx.lilapp.delegate;

import mx.lilapp.entity.Cliente;
import mx.lilapp.entity.EstatusCliente;
import mx.lilapp.exception.NegocioException;
import mx.lilapp.persistence.dao.ClienteDAO;
import mx.lilapp.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateCliente {

    public static final String MSG_DATOS_DUPLICADOS = "El correo electrónico ya está registrado en otro cliente";

    /** Registra un cliente nuevo si su correo no pertenece a otro cliente. */
    public void guardar(Cliente cliente) {
        ClienteDAO dao = ServiceLocator.getInstanceClienteDAO();

        if (dao.findByCorreo(cliente.getCorreo()) != null) {
            throw new NegocioException(MSG_DATOS_DUPLICADOS);
        }
        if (cliente.getEstatus() == null) {
            cliente.setEstatus(EstatusCliente.activo);
        }
        dao.save(cliente);
    }

    /** Actualiza un cliente existente si su correo no pertenece a otro cliente. */
    public Cliente actualizar(Cliente cliente) {
        ClienteDAO dao = ServiceLocator.getInstanceClienteDAO();

        if (cliente.getIdCliente() == null || dao.find(cliente.getIdCliente()) == null) {
            throw new NegocioException("El cliente que intenta modificar no existe");
        }
        Cliente conMismoCorreo = dao.findByCorreo(cliente.getCorreo());
        if (conMismoCorreo != null && !conMismoCorreo.getIdCliente().equals(cliente.getIdCliente())) {
            throw new NegocioException(MSG_DATOS_DUPLICADOS);
        }
        return dao.update(cliente);
    }

    /** Elimina un cliente. */
    public void eliminar(Cliente cliente) {
        ServiceLocator.getInstanceClienteDAO().delete(cliente);
    }

    /** Busca un cliente por su id. */
    public Cliente buscarPorId(Integer id) {
        return ServiceLocator.getInstanceClienteDAO().find(id);
    }

    /** Devuelve todos los clientes. */
    public List<Cliente> listar() {
        return ServiceLocator.getInstanceClienteDAO().findAll();
    }
}
