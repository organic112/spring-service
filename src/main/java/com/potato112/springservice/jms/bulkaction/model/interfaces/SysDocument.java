package com.potato112.springservice.jms.bulkaction.model.interfaces;

import java.time.LocalDateTime;

/**
 * Document used by Car System. Interface should be used on business entities.
 */
public interface SysDocument {

    String getCode();

    String getCreateUser();

    LocalDateTime getCreateDate();

    String getUpdateUser();

    void setUpdateUser();

    LocalDateTime getUpdateDate();
}
