package ru.fancytraffic.masterway.repository;

import ru.fancytraffic.masterway.domain.RepositoryEntity;
import ru.fancytraffic.masterway.specification.Specification;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class JpaRepository<I, T extends RepositoryEntity<I>>{

    private EntityManagerFactory emf;

    private Class<T> type;

    public JpaRepository(Class<T> type, String persistenceUnitName) {
        this.type = type;
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    protected <R> R run(Function<EntityManager, R> function) {
        final EntityManager entityManager = emf.createEntityManager();
        try {
            return function.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    protected void run(Consumer<EntityManager> function) {
        run(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }


    protected <R> R runInTransaction(Function<EntityManager, R> function) {
        return run(entityManager -> {
            entityManager.getTransaction().begin();
            final R result = function.apply(entityManager);
            entityManager.getTransaction().commit();
            return result;
        });
    }

    protected void runInTransaction(Consumer<EntityManager> function) {
        runInTransaction(entityManager -> {
            function.accept(entityManager);
            return null;
        });
    }

    public Optional<T> get(I id) {
        return Optional.ofNullable(
                run(entityManager -> {
                    return entityManager.find(type, id);
                })
        );
    }

    public Set<T> get() {
        List<T> resultList = run(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);
            final Root<T> root = criteria.from(type);
            criteria.select(root);
            final TypedQuery<T> query = entityManager.createQuery(criteria);
            return query.getResultList();
        });
        return new HashSet<>(resultList);
    }

    public Set<T> get(Specification<T> specification) {
        List<T> resultList = run(entityManager -> {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaQuery<T> criteria = criteriaBuilder.createQuery(type);
            final Root<T> root = criteria.from(type);
            final Predicate predicate = specification.toPredicate(root, criteriaBuilder);
            criteria.where(predicate);
            return entityManager.createQuery(criteria).getResultList();
        });
        return new HashSet<>(resultList);
    }

    public void persist(T entity) {
        runInTransaction(entityManager -> {
            entityManager.merge(entity);
        });
    }


    public void persist(Collection<T> entities) {
        runInTransaction(entityManager -> {
            entities.forEach(entityManager::merge);
        });
    }

    public void remove(T entity) {
        remove(entity.getId());
    }

    public void remove(I id) {
        runInTransaction(entityManager -> {
            final T managedEntity = entityManager.find(type, id);
            if (managedEntity != null) {
                entityManager.remove(managedEntity);
            }
        });
    }

    public void remove(Specification<T> specification) {
        runInTransaction(entityManager -> {
            get(specification).forEach(entityManager::remove);
        });
    }

    public void remove(Collection<T> entities) {
        runInTransaction(entityManager -> {
            entities
                    .stream()
                    .map(T::getId)
                    .map(id -> entityManager.find(type, id))
                    .filter(Objects::nonNull)
                    .forEach(entityManager::remove);
        });
    }




}
