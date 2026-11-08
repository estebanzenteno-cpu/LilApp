package mx.lilapp.persistence.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDAO<T> {

    private final Class<T> entityClass;

    /** Recibe la clase de la entidad que maneja el DAO. */
    protected AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /** Devuelve el EntityManager del hilo actual. */
    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    /** Guarda una entidad nueva. */
    public void save(T entity) {
        runInTransaction(em -> em.persist(entity));
    }

    /** Actualiza una entidad existente y la devuelve. */
    public T update(T entity) {
        return executeInTransaction(em -> em.merge(entity));
    }

    /** Elimina una entidad. */
    public void delete(T entity) {
        runInTransaction(em -> em.remove(em.contains(entity) ? entity : em.merge(entity)));
    }

    /** Busca una entidad por su id. */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /** Devuelve todos los registros de la tabla. */
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    /** Cuenta los registros de la tabla. */
    public long count() {
        EntityManager em = getEntityManager();
        CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        cq.select(em.getCriteriaBuilder().count(cq.from(entityClass)));
        return em.createQuery(cq).getSingleResult();
    }

    /** Ejecuta una operacion de escritura dentro de una transaccion y devuelve su resultado. */
    protected <R> R executeInTransaction(Function<EntityManager, R> action) {
        try {
            HibernateUtil.beginTransaction();
            R result = action.apply(getEntityManager());
            HibernateUtil.commit();
            return result;
        } catch (RuntimeException e) {
            HibernateUtil.rollback();
            throw e;
        }
    }

    /** Ejecuta una operacion de escritura sin resultado dentro de una transaccion. */
    protected void runInTransaction(Consumer<EntityManager> action) {
        executeInTransaction(em -> {
            action.accept(em);
            return null;
        });
    }
}
