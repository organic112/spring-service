package com.potato112.springservice.jms.bulkaction.model.interfaces;

import java.time.LocalDateTime;

/**
 * Base interface for Base entity
 */
public interface BaseTable {

    String getId();

    String getCreateUser();

    void setCreateUser(String createUser);

    LocalDateTime getCreateDate();

    void setCreateDate(LocalDateTime createDate);

    String getUpdateUser();

    void setUpdateUser(String loggedUser);

    LocalDateTime getUpdateDate();

    void setUpdateDate(LocalDateTime updateDate);

}
