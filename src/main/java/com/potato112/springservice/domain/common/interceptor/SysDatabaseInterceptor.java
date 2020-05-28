package com.potato112.springservice.domain.common.interceptor;

import com.potato112.springservice.domain.user.context.UserContext;
import com.potato112.springservice.domain.user.context.UserContextService;
import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Database interceptor (entity listener)
 * Performs operations on entities
 */
@Component
@AllArgsConstructor
public class SysDatabaseInterceptor {

    private UserContextService userContextService;

    public SysDatabaseInterceptor() {
    }

    /**
     * When Entity is created in other than Request scope, create user has to be set manually
     */
    @PrePersist
    public void prePersist(final Object entity) {

        System.out.println("Create Entity Interceptor");

        BaseEntity baseEntity = checkEntityAndCastToBaseEntity(entity);
        if (null == baseEntity) {
            return;
        }
        LocalDateTime createDate = baseEntity.getCreateDate();

        if (Objects.isNull(createDate)) {
            baseEntity.setCreateDate(LocalDateTime.now());
        }
        if (null == baseEntity.getCreateUser() || "".equals(baseEntity.getCreateUser())) {
            UserContext userContext = userContextService.getRequestUserContext();
            String userLogin = userContext.getContextUserLogin();
            baseEntity.setCreateUser(userLogin);
        }
    }

    @PreUpdate
    public void preUpdate(final Object entity) {

        BaseEntity baseEntity = checkEntityAndCastToBaseEntity(entity);
        if (null == baseEntity) {
            return;
        }
        System.out.println("PreUpdate Entity Interceptor, create user check:" + baseEntity.getCreateUser());

        baseEntity.setUpdateDate(LocalDateTime.now());
        UserContext userContext = userContextService.getNotStrictRequestUserContext();  //FIXME
        String userLogin = userContext.getContextUserLogin();
        baseEntity.setUpdateUser(userLogin);
    }

    private BaseEntity checkEntityAndCastToBaseEntity(final Object entity) {

        System.out.println("Update Entity Interceptor");

        if (null == entity) {
            throw new IllegalStateException("Hibernate tries to save or update NULL reference. Should never happened");
        }
        if (!(entity instanceof BaseEntity)) {
            return null;
        }
        return (BaseEntity) entity;
    }

   /* String createUser = baseEntity.getCreateUser();
    String updateUser = baseEntity.getUpdateUser();

    boolean bothUsersAreNull = null == createUser && null == updateUser;
    boolean createNotNullUpdateNull = null != createUser && null == updateUser;
    boolean createNotNullUpdateNotNull = null != createUser && null != updateUser;

            if (bothUsersAreNull) {
        entity.setUserName("anonymous");
    } else if (createNotNullUpdateNull) {
        entity.setUserName(baseEntity.getCreateUser());
    } else if (createNotNullUpdateNotNull) {
        entity.setUserName(baseEntity.getUpdateUser());
    } else {
        UserContext userContext = userContextService.getRequestUserContext();
        String login = userContext.getContextUserLogin();
        entity.setUserName(login);
    }*/
}


