package com.potato112.springservice.jms.bulkaction.model.results;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Database interceptor (entity listener)
 * performs operations on entities
 */
public class DatabaseInterceptor {


    @PreUpdate
    public void preUpdate(final Object entity) {
        // FIXME
    }

    @PrePersist
    public void prePersist(final Object entity) {
        // FIXME
    }

}
