package com.potato112.springservice.domain.common.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ErrorResult {

    private String mainMessage;
    private Map<String, String> validationError;

    public ErrorResult(String mainMessage, Map<String, String> validationError) {
        this.mainMessage = mainMessage;
        this.validationError = validationError;
    }

    public ErrorResult(String mainMessage) {
        this.mainMessage = mainMessage;
    }
}
