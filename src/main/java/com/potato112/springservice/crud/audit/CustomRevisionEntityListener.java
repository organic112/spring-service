package com.potato112.springservice.crud.audit;

import com.potato112.springservice.domain.user.context.UserContext;
import com.potato112.springservice.domain.user.context.UserContextService;
import com.potato112.springservice.repository.entities.BaseEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

/**
 * Sets custom field values to Hibernate Audit (entity history) revision table
 * Handles entities extending BaseEntity
 */
@Component
public class CustomRevisionEntityListener implements RevisionListener {

    private UserContextService userContextService;

    public CustomRevisionEntityListener(UserContextService userContextService) {
        this.userContextService = userContextService;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity entity = checkEntityAndCastToBaseEntity(revisionEntity);

        if (null == entity) {
            return;
        }

        UserContext userContext = userContextService.getNotStrictRequestUserContext();
        String login = userContext.getContextUserLogin();
        entity.setUserName(login);
    }

    private CustomRevisionEntity checkEntityAndCastToBaseEntity(final Object entity) {

        System.out.println("Update Entity Interceptor");

        if (null == entity) {
            throw new IllegalStateException("Hibernate tries to save or update NULL reference. Should never happened");
        }
        if (!(entity instanceof CustomRevisionEntity)) {
            return null;
        }
        return (CustomRevisionEntity) entity;
    }
}
