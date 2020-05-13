package com.potato112.springservice.jms.bulkaction.model.interfaces;

import java.io.Serializable;

/**
 * Provides callback functions to execute after status change
 */
public interface BulkActionSingleDocumentChangeStatusCallback<DOCTYPE extends SysDocument, STATUS extends SysStatus>
        extends Serializable {

    /**
     * action executed after success in status change
     */
    void onSuccess(DOCTYPE document, STATUS actualStatus, STATUS newStatus);

    /**
     * action executed after failure in status change
     */
    void onFailure(DOCTYPE document);
}
