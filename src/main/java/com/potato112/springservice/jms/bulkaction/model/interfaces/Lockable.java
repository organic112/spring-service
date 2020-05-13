package com.potato112.springservice.jms.bulkaction.model.interfaces;

import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;

public interface Lockable extends SysDocument {

    String getDocumentId();

    SysDocumentType getDocumentType();

    boolean isEditable();

    default String getBusinessCode(){
        return null;
    }

}
