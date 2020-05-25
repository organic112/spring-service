package com.potato112.springservice.domain.common.interceptor;

import com.potato112.springservice.domain.user.context.UserContext;
import com.potato112.springservice.domain.user.context.UserLoginContextService;
import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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

/*        if (null != baseEntity.getCreateUser() && !"".equals(baseEntity.getCreateUser())) {
            return;
        }*/

        if (null == baseEntity.getCreateUser() || "".equals(baseEntity.getCreateUser())) {
            UserContext userContext = userLoginContextService.getUserContext();
            String userLogin = userContext.getContextUserLogin();
            baseEntity.setCreateUser(userLogin);
        }
    }

    @PreUpdate
    public void preUpdate(final Object entity) {

        System.out.println("PreUpdate Entity Interceptor");

        BaseEntity baseEntity = checkEntityAndCastToBaseEntity(entity);
        if (null == baseEntity) {
            return;
        }
        LocalDateTime updateDate = baseEntity.getUpdateDate();

        if (Objects.isNull(updateDate)) {
            baseEntity.setUpdateDate(LocalDateTime.now());
        }

/*        if (null != baseEntity.getCreateUser() && !"".equals(baseEntity.getCreateUser())) {
            System.out.println("EMPTY CREATE USER");
            return;
        }*/

        UserContext userContext = userLoginContextService.getUserContext();
        String userLogin = userContext.getContextUserLogin();
        baseEntity.setUpdateUser(userLogin);
    }



    private BaseEntity checkEntityAndCastToBaseEntity(final Object entity) {

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


