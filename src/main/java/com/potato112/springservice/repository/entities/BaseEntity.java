package com.potato112.springservice.repository.entities;



import com.potato112.springservice.domain.common.interceptor.SysDatabaseInterceptor;
import com.potato112.springservice.repository.interceptors.TransientBaseEntityState;
import com.potato112.springservice.repository.interfaces.BaseTable;
import com.potato112.springservice.repository.interfaces.EntityWithTransientState;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
@EntityListeners(SysDatabaseInterceptor.class)
public abstract class BaseEntity implements Serializable, BaseTable, EntityWithTransientState<TransientBaseEntityState> {


    @Column(nullable = false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @Column(nullable = false)
    private String createUser;

    private String updateUser;

    @Transient
    private TransientBaseEntityState transientState = new TransientBaseEntityState();

    @Override
    public TransientBaseEntityState getTransientState() {
        return transientState;
    }

    @Override
    public void setTransientState(TransientBaseEntityState state) {
        this.transientState = state;
    }

    public String getManuallyProvidedUpdateUser() {

        return this.transientState.getUpdateUser();
    }

    public void setManuallyProvidedUpdateUser(String manuallyProvidedUpdateUser) {

        this.transientState.setUpdateUser(manuallyProvidedUpdateUser);
    }

    @Override
    public abstract String getId();

    public abstract void setId(String id);

    @Override
    public String getCreateUser() {
        return createUser;
    }

    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getUpdateUser() {
        return updateUser;
    }

    @Override
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public void transferStateToPersistentFields(Object stateProvider) {

        // FIXME

    }

}
