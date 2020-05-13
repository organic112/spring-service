package com.potato112.springservice.jms.bulkaction.model.exception;

/**
 * unchecked exception
 */
public class StatusManagerException extends RuntimeException {

    public StatusManagerException(String message) {
        super(message);
    }
}
