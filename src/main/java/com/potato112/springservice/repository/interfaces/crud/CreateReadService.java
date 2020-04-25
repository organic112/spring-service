package com.potato112.springservice.repository.interfaces.crud;

import com.potato112.springservice.repository.interfaces.crud.ReadService;
import org.hibernate.FlushMode;

/**
 * Create and read operations for DAO services.
 */
public interface CreateReadService<E> extends ReadService<E> {

    E create(E e);

    void createNoFlush(E e);

    void flush();

    void setFlushMode(FlushMode flushMode);
}
