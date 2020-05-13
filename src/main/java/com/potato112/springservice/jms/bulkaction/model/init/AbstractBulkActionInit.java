package com.potato112.springservice.jms.bulkaction.model.init;

import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;

/**
 * this abstract class provides partial default implementation of BulkActionInit interface
 * (middleware between interface and current specific implementations
 */
public abstract class AbstractBulkActionInit implements BulkActionInit {

    protected String loggedUser;

    @Override
    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }
}
