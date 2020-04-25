package com.potato112.springservice.repository.interceptors;

import com.potato112.springservice.repository.interfaces.BaseTable;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Objects;

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

        BaseTable baseTable = checkEntityAndCastToBaseTable(entity);
        if(null == baseTable){
            return;
        }

        LocalDateTime createDate= baseTable.getCreateDate();

        if(Objects.isNull(createDate)){
            baseTable.setCreateDate(createDate);
        }

        if(null != baseTable.getCreateUser() && "".equals(baseTable.getCreateUser())){
            return;
        }

        // FIXME from user service
        // baseTable.setCreateUser();
        baseTable.setCreateUser("fixme_user_name");
    }

    private BaseTable checkEntityAndCastToBaseTable(final Object entity){

        if(null == entity){
            throw new IllegalStateException("Hibernate tries to save or update NULL reference. Should never happened");
        }

        // if not Base table do nothing
        if(!(entity instanceof BaseTable)){
            return null;
        }

        return (BaseTable) entity;
    }

}
