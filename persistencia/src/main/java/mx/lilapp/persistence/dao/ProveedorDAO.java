package mx.lilapp.persistence.dao;

import mx.lilapp.entity.Proveedor;
import mx.lilapp.persistence.persistence.AbstractDAO;
import mx.lilapp.persistence.persistence.HibernateUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProveedorDAO extends AbstractDAO<Proveedor> {

    /** Indica que este DAO trabaja con la entidad Proveedor. */
    public ProveedorDAO() {

        super(Proveedor.class);
    }

    public List<Proveedor> listarOrdenados() {
        EntityManager em = HibernateUtil.nuevoEntityManager();
        try {
            return em
                    .createQuery(
                            "SELECT DISTINCT p FROM Proveedor p "
                                    + "LEFT JOIN FETCH p.tiposProducto "
                                    + "ORDER BY LOWER(p.nombreProveedor)",
                            Proveedor.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        EntityManager em = HibernateUtil.nuevoEntityManager();
        try {
            return em
                    .createQuery(
                            "SELECT DISTINCT p FROM Proveedor p "
                                    + "LEFT JOIN FETCH p.tiposProducto "
                                    + "WHERE LOWER(p.nombreProveedor) LIKE LOWER(:nombre) "
                                    + "ORDER BY LOWER(p.nombreProveedor)",
                            Proveedor.class)
                    .setParameter("nombre", "%" + nombre.trim() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
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

    public boolean existeNombreEnOtroProveedor(String nombre, Integer id) {
        return getEntityManager().createQuery(
                        "SELECT COUNT(p) FROM Proveedor p WHERE LOWER(TRIM(p.nombreProveedor)) = LOWER(:nombre) AND p.idProveedor <> :id",
                        Long.class)
                .setParameter("nombre", nombre.trim())
                .setParameter("id", id)
                .getSingleResult() > 0;
    }

    public void modificarExistente(Proveedor datos) {
        runInTransaction(em -> {
            Proveedor existente = em.find(Proveedor.class, datos.getIdProveedor());
            if (existente == null) {
                throw new IllegalArgumentException("El proveedor ya no existe.");
            }
            existente.setNombreProveedor(datos.getNombreProveedor());
            existente.setTelefono(datos.getTelefono());
            existente.setDireccion(datos.getDireccion());
            existente.setTiposProducto(datos.getTiposProducto());
        });
    }
}
