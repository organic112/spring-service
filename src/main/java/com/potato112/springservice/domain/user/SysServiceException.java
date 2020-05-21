package com.potato112.springservice.domain.user;

import lombok.Data;

@Data
public class SysServiceException extends RuntimeException {

    public SysServiceException(String message) {
        super(message);
    }

    public SysServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
