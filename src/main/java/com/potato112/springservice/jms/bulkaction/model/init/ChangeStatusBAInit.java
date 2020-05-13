package com.potato112.springservice.jms.bulkaction.model.init;

import com.potato112.springservice.jms.bulkaction.model.exception.BulkActionEmptyListException;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionSingleDocumentChangeStatusCallback;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;

import java.io.Serializable;
import java.util.Set;

public abstract class ChangeStatusBAInit<DOCTYPE extends SysDocument, STATUS extends SysStatus> extends AbstractBulkActionInit implements Serializable {

    private STATUS targetStatus;
    private Set<String> documentIds;
    private String cancellationMessage;


    public ChangeStatusBAInit(STATUS targetStatus, Set<String> documentIds, String cancellationMessage, String loggedUser) {

        if (null != documentIds && documentIds.isEmpty()) {
            throw new BulkActionEmptyListException();
        }

        setTargetStatus(targetStatus);
        setDocumentIds(documentIds);
        setCancellationMessage(cancellationMessage);
        setLoggedUser(loggedUser);
    }

    public STATUS getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(STATUS targetStatus) {
        this.targetStatus = targetStatus;
    }

    public Set<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(Set<String> documentIds) {
        this.documentIds = documentIds;
    }

    public String getCancellationMessage() {
        return cancellationMessage;
    }

    public void setCancellationMessage(String cancellationMessage) {
        this.cancellationMessage = cancellationMessage;
    }

    BulkActionSingleDocumentChangeStatusCallback<DOCTYPE, STATUS> getSingleDocumentChangeStatusCallback() {

        return new DefaultBulkActionStatusCallback();
    }

    private class DefaultBulkActionStatusCallback implements BulkActionSingleDocumentChangeStatusCallback<DOCTYPE, STATUS> {

        @Override
        public void onSuccess(DOCTYPE document, STATUS actualStatus, STATUS newStatus) {

        }

        @Override
        public void onFailure(DOCTYPE document) {

        }
    }
}
