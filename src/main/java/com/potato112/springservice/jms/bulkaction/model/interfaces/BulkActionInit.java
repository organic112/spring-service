package com.potato112.springservice.jms.bulkaction.model.interfaces;

import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionType;

import java.io.Serializable;
import java.util.Set;


/**
 * Current bulk action MDB should implement this Interface and provide such data
 * (common for all bulk actions)
 */
public interface BulkActionInit extends Serializable {

    // type of bulk action
    BulkActionType getType();

    // user that initiated bulk action
    String getLoggedUser();

    // affected objects ids
    Set<String> getDocumentIds();

}
