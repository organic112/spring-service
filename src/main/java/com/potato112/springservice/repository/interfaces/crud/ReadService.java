package com.potato112.springservice.repository.interfaces.crud;


import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;


/**
 * Read operations for DAO services.
 */
public interface ReadService<E> {


    E find(Class<E> type, Object id);

    List<E> findWithNamedQuery(String queryName);

    List<E> findWithNamedQuery(String queryName, int resultLimit);

    E findOneWithNamedQuery(String queryName);

    E findOneWithNamedQuery(String queryName, Map<String, Object> params);

    E findOneWithJPQLQuery(String queryJPQL, Class<E> type, Map<String, Object> params);


    List<E> findWithNamedQuery(String queryName, Map<String, Object> params);

    List<E> findWithNamedQuery(String queryName, Map<String, Object> params, int resultLimit);

    List<E> findWithNamedQuery(String queryName, Map<String, Object> params, int startPosition, int resultLimit);

    List<E> findWithJPQLQuery(String queryJPQL, Class<E> type);

    List<E> findWithJPQLQuery(String queryJPQL, Class<E> type, Map<String, Object> params);

    List<E> findWithCriteriaQuery(CriteriaQuery<E> query);

    void detach(E e);

    long countWithNamedQuery(String queryName, Map<String, Object> params);

    long countWithNamedQuery(String queryName);
}
