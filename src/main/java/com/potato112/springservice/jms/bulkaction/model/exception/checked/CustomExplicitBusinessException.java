package com.potato112.springservice.jms.bulkaction.model.exception.checked;

/**
 * Exception for explicit business rule violation
 */
public class CustomExplicitBusinessException extends Exception {

    public CustomExplicitBusinessException(String message) {
        super(message);
    }

    public CustomExplicitBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
