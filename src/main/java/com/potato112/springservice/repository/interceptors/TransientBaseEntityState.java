package com.potato112.springservice.repository.interceptors;

import com.potato112.springservice.repository.interfaces.TransientEntityState;

public class TransientBaseEntityState implements TransientEntityState {

    private String updateUser;

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
