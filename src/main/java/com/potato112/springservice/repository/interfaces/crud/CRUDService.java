package com.potato112.springservice.repository.interfaces.crud;


import com.potato112.springservice.repository.EntityDeleteException;

/**
 * Interface for all DAO services to perform CRUD operations
 */
public interface CRUDService<E> extends CreateReadService<E> {

    E update(E e);

    void delete(E e);

    void tryDelete(E e) throws EntityDeleteException;

    int updateWithNameQuery(String namedQueryName);
}
