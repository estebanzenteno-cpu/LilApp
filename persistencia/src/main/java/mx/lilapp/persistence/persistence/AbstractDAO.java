package mx.lilapp.persistence.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * CRUD generico. Cada DAO concreto solo extiende esta clase:
 *
 * <pre>
 * public class UsuarioDAO extends AbstractDAO&lt;Usuario&gt; {
 *     public UsuarioDAO() { super(Usuario.class); }
 * }
 * </pre>
 */
public abstract class AbstractDAO<T> {

    private final Class<T> entityClass;

    protected AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public void save(T entity) {
        runInTransaction(em -> em.persist(entity));
    }

    public T update(T entity) {
        return executeInTransaction(em -> em.merge(entity));
    }

    public void delete(T entity) {
        runInTransaction(em -> em.remove(em.contains(entity) ? entity : em.merge(entity)));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    public long count() {
        EntityManager em = getEntityManager();
        CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        cq.select(em.getCriteriaBuilder().count(cq.from(entityClass)));
        return em.createQuery(cq).getSingleResult();
    }

    /** Ejecuta una operacion de escritura dentro de una transaccion. */
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

    protected void runInTransaction(Consumer<EntityManager> action) {
        executeInTransaction(em -> {
            action.accept(em);
            return null;
        });
    }
}
