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

    public List<TipoProducto> listarTiposProducto(){
        return ServiceFacadeLocator
                .getInstanceFacadeTipoProducto()
                .listar();
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
