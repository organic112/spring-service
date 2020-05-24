package com.potato112.springservice.crud.audit;

import org.hibernate.envers.RevisionListener;

/**
 * Sets custom field values to Hibernate Audit (entity history) revision table
 */
public class CustomRevisionEntityListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity entity = (CustomRevisionEntity) revisionEntity;
        // FIXME provide username from context
        entity.setUserName("FIXME user name");
    }
}
