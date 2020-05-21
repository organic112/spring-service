package com.potato112.springservice.domain.user;

import lombok.Data;

import java.util.Map;

@Data
public class SysValidationException extends SysServiceException {

    private Map<String, String> validationErrors;

    public SysValidationException(String message) {
        super(message);
    }

    public SysValidationException(String message, Map<String, String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }
}
