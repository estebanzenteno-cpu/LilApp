package mx.lilapp.facade;

import mx.lilapp.delegate.DelegateProveedor;
import mx.lilapp.entity.Proveedor;
import mx.lilapp.delegate.DelegateTipoProducto;
import mx.lilapp.entity.TipoProducto;
import mx.lilapp.exception.NegocioException;
import java.util.ArrayList;

import java.util.List;

public class FacadeProveedor {

    public static final String MSG_PROVEEDOR_INEXISTENTE = "El proveedor ya no existe";

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
        Proveedor actual = null;
        if (proveedor != null && proveedor.getIdProveedor() != null) {
            actual = delegateProveedor.buscarPorId(proveedor.getIdProveedor());
        }
        if (actual == null) {
            throw new NegocioException(MSG_PROVEEDOR_INEXISTENTE);
        }
        delegateProveedor.eliminar(actual);
    }

    /** Busca un proveedor por su id. */
    public Proveedor buscarPorId(Integer id) {
        return delegateProveedor.buscarPorId(id);
    }

    /** Devuelve todos los proveedores. */
    public List<Proveedor> listar() {
        return delegateProveedor.listar();
    }

    public List<Proveedor> listarOrdenados() {
        return delegateProveedor.listarOrdenados();
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        return delegateProveedor.buscarPorNombre(nombre);
    }

    public boolean existeNombre(String nombre) {
        return delegateProveedor.existeNombre(nombre);
    }

    public void registrar(Proveedor proveedor, Integer[] idsProductos) {
        if (idsProductos == null || idsProductos.length == 0) {
            throw new IllegalArgumentException(
                    "El proveedor debe tener al menos un tipo de producto.");
        }

        DelegateTipoProducto delegateTipoProducto = new DelegateTipoProducto();
        proveedor.getTiposProducto().clear();

        for (Integer id : idsProductos) {
            TipoProducto tipo = delegateTipoProducto.buscarPorId(id);

            if (tipo == null) {
                throw new IllegalArgumentException(
                        "Uno de los tipos de producto ya no existe.");
            }

            proveedor.getTiposProducto().add(tipo);
        }

        delegateProveedor.guardar(proveedor);
    }

    public List<String> validarRegistro(Proveedor proveedor, Integer[] idsProductos) {
        List<String> errores = new ArrayList<>();

        if (proveedor == null
                || vacio(proveedor.getNombreProveedor())
                || vacio(proveedor.getTelefono())
                || vacio(proveedor.getDireccion())) {
            errores.add("Favor de llenar todos los campos.");
            return errores;
        }

        String nombre = proveedor.getNombreProveedor().trim();
        String telefono = proveedor.getTelefono().trim();
        String direccion = proveedor.getDireccion().trim();

        if (nombre.length() > 50) {
            errores.add("El nombre no puede exceder 50 caracteres.");
        }

        if (!telefono.matches("\\d{10}")) {
            errores.add("El teléfono debe tener 10 caracteres numéricos.");
        }

        if (direccion.length() > 100) {
            errores.add("La dirección no puede exceder 100 caracteres.");
        }

        if (idsProductos == null || idsProductos.length == 0) {
            errores.add("Elija al menos un tipo de producto.");
        }

        if (nombre.length() <= 50 && existeNombre(nombre)) {
            errores.add("Ya existe un proveedor registrado con ese nombre.");
        }

        return errores;
    }

    private boolean vacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}
