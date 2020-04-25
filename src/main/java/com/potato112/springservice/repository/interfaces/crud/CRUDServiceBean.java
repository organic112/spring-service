package com.potato112.springservice.repository.interfaces.crud;


import com.potato112.springservice.repository.EntityDeleteException;
import com.potato112.springservice.repository.interfaces.BaseTable;
import com.potato112.springservice.repository.interfaces.EntityWithTransientState;
import com.potato112.springservice.repository.interfaces.TransientEntityState;
import org.hibernate.FlushMode;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Custom CRUD interfaces implemented here - provides generic crud operations for entity
 * Can be extended to create DAO with more specific queries.
 *
 * example usage - injected for current entity:
 *
 * @Autowire
 * private CRUDServiceBean<UserGroup> userGroupCRUDService;
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CRUDServiceBean<E> implements CRUDService<E> {

    @PersistenceContext //(unitName = "SYSPU")
    protected EntityManager em;


    @Override
    public E find(Class<E> type, Object id) {

        return em.find(type, id);
    }

    @Override
    public List<E> findWithNamedQuery(String queryName) {

        Query query = em.createNamedQuery(queryName);
        return query.getResultList();
    }

    @Override
    public E findOneWithNamedQuery(String queryName) {

        List<E> results = findWithNamedQuery(queryName, 1);

        if (results.size() < 1) {
            return null;
        }

        if (results.size() > 1) {
            throw new PersistenceException("Failed to get entity. Found more than one entity when expected one!");
        }
        return results.get(0);
    }

    @Override
    public E findOneWithNamedQuery(String queryName, Map<String, Object> params) {

        List<E> results = findWithNamedQuery(queryName, params);
        if (results.size() < 1) {
            return null;
        }

        if (results.size() > 1) {
            throw new PersistenceException("Failed to get entity. Found more than one entity when expected one!");
        }
        return results.get(0);
    }

    /**
     * Note! Should be used only for JOIN fetch that may produce more than one result
     */
    public E findFirstWithNamedQuery(String queryName, Map<String, Object> params) {

        List<E> results = findWithNamedQuery(queryName, params);
        if (results.size() < 1) {
            return null;
        }
        return results.get(0);
    }

    @Override
    public List<E> findWithNamedQuery(String queryName, Map<String, Object> params) {

        return findWithNamedQuery(queryName, params, 0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findWithNamedQuery(String queryName, int resultLimit) {

        Query query = em.createNamedQuery(queryName);
        query.setMaxResults(resultLimit);
        return query.getResultList();
    }

    @Override
    public List<E> findWithNamedQuery(String queryName, Map<String, Object> params, int resultLimit) {

        return findWithNamedQuery(queryName, params, 0, resultLimit);
    }

    @Override
    public List<E> findWithNamedQuery(String queryName, Map<String, Object> params, int startPosition,
                                      int resultLimit) {

        return findWithNamedQuery(queryName, params, startPosition, resultLimit, false);
    }

    private List<E> findWithNamedQuery(String queryName, Map<String, Object> params, int startPosition,
                                       int resultLimit, boolean isNativeQuery) {

        Set<Map.Entry<String, Object>> rawParams = params.entrySet();
        Query query = em.createNamedQuery(queryName);

        if (startPosition > 0) {
            query.setFirstResult(startPosition);
        }
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Map.Entry<String, Object> entry : rawParams) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @Override
    public long countWithNamedQuery(String queryName) {
        return countWithNamedQuery(queryName, Collections.emptyMap());
    }

    @Override
    public long countWithNamedQuery(String queryName, Map<String, Object> params) {

        Set<Map.Entry<String, Object>> rawParams = params.entrySet();
        Query query = em.createNamedQuery(queryName, Long.class);

        for (Map.Entry<String, Object> entry : rawParams) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        long result = ((Number) query.getSingleResult()).longValue();
        return result;
    }

    @Override
    public List<E> findWithCriteriaQuery(CriteriaQuery query) {

        TypedQuery<E> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    public List<E> findWithJPQLQuery(String queryJPQL, Class<E> type) {

        TypedQuery<E> typedQuery = em.createQuery(queryJPQL, type);
        return typedQuery.getResultList();
    }


    @Override
    public List<E> findWithJPQLQuery(String queryJPQL, Class<E> type, Map<String, Object> params) {

        TypedQuery<E> typedQuery = em.createQuery(queryJPQL, type);

        if (null != params) {
            for (String paramName : params.keySet()) {
                typedQuery.setParameter(paramName, params.get(paramName));
            }
        }
        return typedQuery.getResultList();
    }


    @Override
    public E findOneWithJPQLQuery(String queryJPQL, Class<E> type, Map<String, Object> params) {

        TypedQuery<E> typedQuery = em.createQuery(queryJPQL, type);
        if (null != params) {
            for (String paramName : params.keySet()) {
                typedQuery.setParameter(paramName, params.get(paramName));
            }
        }
        List<E> results = typedQuery.getResultList();
        if (results.size() < 1) {
            return null;
        }
        if (results.size() > 1) {
            throw new PersistenceException("Failed to get entity. Found more than one entity when expected one!");
        }
        return results.get(0);
    }

    public <T> List<T> executeNamedQuery(String namedName, Map<String, Object> params) {

        Set<Map.Entry<String, Object>> rawParams = params.entrySet();
        Query query = em.createNamedQuery(namedName);
        for (Map.Entry<String, Object> entry : rawParams) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    public <T> T executeSingleResultNamedQuery(String queryName, Map<String, Object> params) {

        List<T> results = executeNamedQuery(queryName, params);

        if (results.size() < 1) {
            return null;
        }
        if (results.size() > 1) {
            throw new PersistenceException("Failed to get entity. Found more than one entity when expected one!");
        }
        return results.get(0);
    }

    @Override
    public void detach(E o) {
        em.detach(o);
    }

    // TODO Implement the rest of other interfaces (below)


    @Override
    public E create(E e) {
        em.persist(e);
        em.flush();
        em.refresh(e);
        return e;
    }

    @Override
    public void createNoFlush(E e) {

        em.persist(e);

    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void setFlushMode(FlushMode flushMode) {

    }

    // TODO ---------------


    @Override
    public E update(E e) {
        if (BaseTable.class.isAssignableFrom(e.getClass())) {

            BaseTable sysEntity = (BaseTable) e;
            sysEntity.setUpdateDate(LocalDateTime.now());
        }

        if (e instanceof EntityWithTransientState) {
            EntityWithTransientState te = (EntityWithTransientState) e;
            TransientEntityState state = te.getTransientState();
            E mergedEntity = em.merge(e);
            ((EntityWithTransientState) mergedEntity).setTransientState(state);
            return mergedEntity;
        }
        return em.merge(e);
    }

    @Override
    public void delete(E e) {

        if (!em.contains(e)) {
            e = em.merge(e);
        }
        em.remove(e);
    }

    @Override
    public void tryDelete(E e) throws EntityDeleteException {

        E entity = em.merge(e);
        em.remove(entity);

        try {
            em.flush();
        } catch (PersistenceException ex) {
            Throwable cause = ex.getCause();
            if ((cause != null) && (cause instanceof ConstraintViolationException)) {
                SQLException sqlException = ((ConstraintViolationException) cause).getSQLException();
                String errMessage = sqlException.getMessage();
                Matcher m = Pattern.compile("fixme_constraint_violation_pattern").matcher(errMessage);
                if (m.find()) {
                    String tableName = m.group();
                    String excDetails = String.format("Unable to delete. Constraint violation %s", tableName);
                    throw new RuntimeException(excDetails);
                }
            }
            // fixme dedicated exception
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public int updateWithNameQuery(String namedQueryName) {
        return 0;
    }


    public boolean contains(E e) {

        return em.contains(e);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
