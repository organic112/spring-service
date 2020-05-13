package com.potato112.springservice.crud.jdbc.model;

import java.io.Serializable;

/**
 * provides base fields for SQL result row
 */
public abstract class BaseVO implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
