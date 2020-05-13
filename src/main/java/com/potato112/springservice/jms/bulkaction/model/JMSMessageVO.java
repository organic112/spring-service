package com.potato112.springservice.jms.bulkaction.model;

import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;

import java.io.Serializable;

public class JMSMessageVO implements Serializable {

    private String id;

    private BulkActionInit bulkActionInit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BulkActionInit getBulkActionInit() {
        return bulkActionInit;
    }

    public void setBulkActionInit(BulkActionInit bulkActionInit) {
        this.bulkActionInit = bulkActionInit;
    }
}
