package com.potato112.springservice.domain.common.interceptor;

import com.potato112.springservice.domain.user.context.UserContext;
import com.potato112.springservice.domain.user.context.UserLoginContextService;
import com.potato112.springservice.repository.entities.BaseEntity;
import com.potato112.springservice.repository.interfaces.BaseTable;
import lombok.AllArgsConstructor;
import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Database interceptor (entity listener)
 * performs operations on entities
 */

@Component
@AllArgsConstructor
//@EntityListeners(BaseEntity.class)
public class SysDatabaseInterceptor {

    private UserLoginContextService userLoginContextService;

    public SysDatabaseInterceptor() {
    }

    @PreUpdate
    public void preUpdate(final Object entity) {

        System.out.println("PreUpdate Entity Interceptor");

        BaseEntity baseEntity = checkEntityAndCastToBaseTable(entity);
        if (null == baseEntity) {
            return;
        }
        LocalDateTime updateDate = baseEntity.getCreateDate();

        if (Objects.isNull(updateDate)) {
            baseEntity.setUpdateDate(updateDate);
        }

/*        if (null != baseEntity.getCreateUser() && !"".equals(baseEntity.getCreateUser())) {
            System.out.println("EMPTY CREATE USER");
            return;
        }*/

        UserContext userContext = userLoginContextService.getUserContext();

        String userLogin = userContext.getContextUserLogin();
        baseEntity.setUpdateUser(userLogin);
    }

    @PrePersist
    public void prePersist(final Object entity) {

        System.out.println("Create Entity Interceptor");

        BaseEntity baseEntity = checkEntityAndCastToBaseTable(entity);
        if (null == baseEntity) {
            return;
        }

        LocalDateTime createDate = baseEntity.getCreateDate();

        if (Objects.isNull(createDate)) {
            baseEntity.setCreateDate(createDate);
        }

        if (null != baseEntity.getCreateUser() && !"".equals(baseEntity.getCreateUser())) {
            return;
        }

        // FIXME from user service
        // baseTable.setCreateUser();
        UserContext userContext = userLoginContextService.getUserContext();
        String userLogin = userContext.getContextUserLogin();
        baseEntity.setCreateUser(userLogin);
    }

    private BaseEntity checkEntityAndCastToBaseTable(final Object entity) {

        System.out.println("Update Entity Interceptor");

        if (null == entity) {
            throw new IllegalStateException("Hibernate tries to save or update NULL reference. Should never happened");
        }

        // if not BaseEntity do nothing
        if (!(entity instanceof BaseEntity)) {
            return null;
        }
        return (BaseEntity) entity;
    }
}


