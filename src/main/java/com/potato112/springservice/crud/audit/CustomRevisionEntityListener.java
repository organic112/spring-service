package com.potato112.springservice.crud.audit;

import com.potato112.springservice.domain.user.context.UserContext;
import com.potato112.springservice.domain.user.context.UserContextService;
import com.potato112.springservice.repository.entities.BaseEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Sets custom field values to Hibernate Audit (entity history) revision table
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

        boolean isRequestScopeAvailable = null != RequestContextHolder.getRequestAttributes();

        System.out.println("ECHO request scope available:" + isRequestScopeAvailable);

        if (isRequestScopeAvailable) {

            // FIXME provide username from context (not works in Application context)
            String login = userContextService.getUserContext().getContextUserLogin();
            entity.setUserName(login);
        } else {
            entity.setUserName("FIXME user name");
        }
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
