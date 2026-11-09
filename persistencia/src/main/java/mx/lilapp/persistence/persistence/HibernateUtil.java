package mx.lilapp.persistence.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class HibernateUtil {

    private static final String PERSISTENCE_UNIT = "persistencePU";

    private static final EntityManagerFactory EMF = buildEntityManagerFactory();
    private static final ThreadLocal<EntityManager> EM_THREAD = new ThreadLocal<>();

    /** Evita que se creen instancias de esta clase. */
    private HibernateUtil() {
    }

    /** Crea la EntityManagerFactory de la unidad de persistencia. */
    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        } catch (Throwable ex) {
            System.err.println("No se pudo crear la EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /** Devuelve el EntityManager del hilo actual, creandolo si no existe. */
    public static EntityManager getEntityManager() {
        EntityManager em = EM_THREAD.get();
        if (em == null || !em.isOpen()) {
            em = EMF.createEntityManager();
            EM_THREAD.set(em);
        }
        return em;
    }

    /** Cierra el EntityManager del hilo actual, revirtiendo cualquier transaccion pendiente. */
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

    /** Inicia una transaccion si no hay una activa. */
    public static void beginTransaction() {
        EntityManager em = getEntityManager();
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    /** Confirma la transaccion activa. */
    public static void commit() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    /** Revierte la transaccion activa. */
    public static void rollback() {
        EntityManager em = getEntityManager();
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    /** Cierra el EntityManager y la EntityManagerFactory. */
    public static void shutdown() {
        closeEntityManager();
        if (EMF.isOpen()) {
            EMF.close();
        }
    }
}
