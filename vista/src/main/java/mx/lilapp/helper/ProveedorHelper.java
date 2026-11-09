package mx.lilapp.helper;

import jakarta.persistence.criteria.CriteriaBuilder;
import mx.lilapp.entity.Proveedor;
import mx.lilapp.entity.TipoProducto;
import mx.lilapp.integration.ServiceFacadeLocator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProveedorHelper implements Serializable {
    public static final String MSG_REGISTRO_EXITOSO =
            "Proveedor resgitrado con exito";

    public List<String> validar(Proveedor proveedor, Integer[] productosSeleccionados){
        List<String> errores = new ArrayList<>();

        if(proveedor == null
        || vacio(proveedor.getNombreProveedor())
        || vacio(proveedor.getTelefono())
        || vacio(proveedor.getDireccion())) {
            errores.add("Favor de llenar todos los campos");
            return errores;
        }

        String nombre = proveedor.getNombreProveedor().trim();

        if (nombre.length() > 50) {
            errores.add("El nombre no puede exceder 50 caracteres.");
        }

        boolean nombreRepetido = ServiceFacadeLocator
                .getInstanceFacadeProveedor()
                .listar()
                .stream()
                .anyMatch(registrado ->
                        registrado.getNombreProveedor() != null
                                && registrado.getNombreProveedor()
                                .trim()
                                .equalsIgnoreCase(nombre));

        if (nombreRepetido) {
            errores.add("Ya existe un proveedor registrado con ese nombre.");
        }

        if(!proveedor.getTelefono().trim().matches("\\d{10}")) {
            errores.add("El telefono debe tener 10 caracteres numericos.");
        }

        if(proveedor.getDireccion().trim().length() > 100) {
            errores.add("La direccion no puede exceder de 100 caracteres.");
        }

        if(productosSeleccionados == null || productosSeleccionados.length == 0){
            errores.add("Elija al menos un tipo de producto.");
        }

        return errores;
    }

    public List<TipoProducto> listarTiposProducto(){
        return ServiceFacadeLocator
                .getInstanceFacadeTipoProducto()
                .listar();
    }

    public void guardar(Proveedor proveedor, Integer[] productosSeleccionados) {
        proveedor.getTiposProducto().clear();

        for(Integer id : productosSeleccionados) {
            TipoProducto tipo = ServiceFacadeLocator
                    .getInstanceFacadeTipoProducto()
                    .buscarPorId(id);

            if(tipo == null) {
                throw new IllegalArgumentException("Uno de los tipos de producto ya no existe");
            }
            proveedor.getTiposProducto().add(tipo);
        }
        proveedor.setNombreProveedor(proveedor.getNombreProveedor().trim());
        proveedor.setTelefono(proveedor.getTelefono().trim());
        proveedor.setDireccion(proveedor.getDireccion().trim());

        ServiceFacadeLocator
                .getInstanceFacadeProveedor()
                .guardar(proveedor);
    }

    private boolean vacio(String valor){
        return valor == null || valor.trim().isEmpty();
    }

    public List<Proveedor> listarProveedoresOrdenados() {
        return ServiceFacadeLocator
                .getInstanceFacadeProveedor()
                .listarOrdenados();
    }

    public List<Proveedor> buscarProveedoresPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return listarProveedoresOrdenados();
        }

        return ServiceFacadeLocator
                .getInstanceFacadeProveedor()
                .buscarPorNombre(nombre.trim());
    }
}
