package com.potato112.springservice.jms.bulkaction.model.entity;

public class TransientBaseEntityState implements TransientEntityState {

    private String updateUser;

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
