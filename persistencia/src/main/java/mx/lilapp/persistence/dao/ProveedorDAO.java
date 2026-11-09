package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Proveedor;
import mx.lilapp.persistence.persistence.AbstractDAO;
import java.util.List;

public class ProveedorDAO extends AbstractDAO<Proveedor> {

    /** Indica que este DAO trabaja con la entidad Proveedor. */
    public ProveedorDAO() {

        super(Proveedor.class);
    }

    public List<Proveedor> listarOrdenados() {
        return getEntityManager()
                .createQuery(
                        "SELECT DISTINCT p FROM Proveedor p "
                                + "LEFT JOIN FETCH p.tiposProducto "
                                + "ORDER BY LOWER(p.nombreProveedor)",
                        Proveedor.class)
                .getResultList();
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        return getEntityManager()
                .createQuery(
                        "SELECT DISTINCT p FROM Proveedor p "
                                + "LEFT JOIN FETCH p.tiposProducto "
                                + "WHERE LOWER(p.nombreProveedor) LIKE LOWER(:nombre) "
                                + "ORDER BY LOWER(p.nombreProveedor)",
                        Proveedor.class)
                .setParameter("nombre", "%" + nombre.trim() + "%")
                .getResultList();
    }

    public boolean existeNombre(String nombre) {
        Long cantidad = getEntityManager()
                .createQuery(
                        "SELECT COUNT(p) FROM Proveedor p "
                                + "WHERE LOWER(TRIM(p.nombreProveedor)) = LOWER(:nombre)",
                        Long.class)
                .setParameter("nombre", nombre.trim())
                .getSingleResult();

        return cantidad > 0;
    }
}
