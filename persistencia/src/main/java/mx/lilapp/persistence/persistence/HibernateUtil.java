package mx.lilapp.persistence.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public final class HibernateUtil {

    private static final String PERSISTENCE_UNIT = "persistencePU";
    private static final String ENV_DB_PASSWORD = "LILAPP_DB_PASS";

    private static final EntityManagerFactory EMF = buildEntityManagerFactory();
    private static final ThreadLocal<EntityManager> EM_THREAD = new ThreadLocal<>();

    private HibernateUtil() {
    }
    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            String contrasena = System.getenv(ENV_DB_PASSWORD);
            if (contrasena == null || contrasena.isEmpty()) {
                throw new IllegalStateException(
                        "Falta definir la variable de entorno "
                                + ENV_DB_PASSWORD
                                + " con la contrasena de MySQL");
            }
            Map<String, Object> propiedades = new HashMap<>();
            propiedades.put("jakarta.persistence.jdbc.password", contrasena);
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, propiedades);
        } catch (Throwable ex) {
            System.err.println("No se pudo crear la EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager em = EM_THREAD.get();
        if (em == null || !em.isOpen()) {
            em = EMF.createEntityManager();
            EM_THREAD.set(em);
        }
        return em;
    }

    public static void closeEntityManager() {
        EntityManager em = EM_THREAD.get();
        if (em != null) {
            try {
                if (em.isOpen()) {
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    em.close();
                }
            } finally {
                EM_THREAD.remove();
            }
        }
    }

    public static void beginTransaction() {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    public static void commit() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    public static void rollback() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    public static void shutdown() {
        closeEntityManager();
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
