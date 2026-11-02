import jakarta.persistence.EntityManager;
import mx.lilapp.entity.*;
import mx.lilapp.persistence.persistence.HibernateUtil;

/**
 * Ejecuta este main para comprobar la conexion con Lil_App y el mapeo de las entidades.
 */
public class TestConexion {

    public static void main(String[] args) {
        try {
            EntityManager em = HibernateUtil.getEntityManager();
            System.out.println("Conexion OK. Base de datos: "
                    + em.createNativeQuery("SELECT DATABASE()").getSingleResult());

            for (Class<?> entidad : new Class<?>[]{
                    Cliente.class, Proveedor.class, TipoProducto.class, Rol.class, Usuario.class}) {
                Long total = em.createQuery("SELECT COUNT(e) FROM " + entidad.getSimpleName() + " e", Long.class)
                        .getSingleResult();
                System.out.println(entidad.getSimpleName() + ": " + total + " registros");
            }
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
