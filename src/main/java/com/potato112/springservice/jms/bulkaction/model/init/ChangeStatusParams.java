package com.potato112.springservice.jms.bulkaction.model.init;

import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;

import java.io.Serializable;

public class ChangeStatusParams<DOCTYPE extends SysDocument, STATUSTYPE extends SysStatus> implements Serializable {

    private DOCTYPE document;
    private STATUSTYPE newStatus;
    private String cancellationMessage;
    private String loggedUser;

    public DOCTYPE getDocument() {
        return document;
    }

    public void setDocument(DOCTYPE document) {
        this.document = document;
    }

    public STATUSTYPE getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(STATUSTYPE newStatus) {
        this.newStatus = newStatus;
    }

    public String getCancellationMessage() {
        return cancellationMessage;
    }

    public void setCancellationMessage(String cancellationMessage) {
        this.cancellationMessage = cancellationMessage;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }
}
