package mx.lilapp.persistence.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Crea una sola EntityManagerFactory para toda la aplicacion y
 * entrega un EntityManager por hilo (una peticion web = un hilo).
 */
public final class HibernateUtil {

    private static final String PERSISTENCE_UNIT = "persistencePU";

    private static final EntityManagerFactory EMF = buildEntityManagerFactory();
    private static final ThreadLocal<EntityManager> EM_THREAD = new ThreadLocal<>();

    private HibernateUtil() {
    }

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
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
            if (em.isOpen()) {
                em.close();
            }
            EM_THREAD.remove();
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
