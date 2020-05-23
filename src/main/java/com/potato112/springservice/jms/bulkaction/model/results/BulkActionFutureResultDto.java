package com.potato112.springservice.jms.bulkaction.model.results;

import java.io.Serializable;


/**
 * Bulk Action runs multiple parallel async processing operations.
 * This class represents result of single such async operation.
 */
public class BulkActionFutureResultDto implements Serializable {

    private String processedObjectCode;
    private boolean success = true;
    private String resultDetails;
    private Exception exception;

    public BulkActionFutureResultDto() {
    }

    public static BulkActionFutureResultDto makeSuccess(final String processedObjectCode, final String resultDetails) {

        BulkActionFutureResultDto result = new BulkActionFutureResultDto();
        result.processedObjectCode = processedObjectCode;
        result.success = true;
        result.resultDetails = resultDetails;
        return result;
    }

    public static BulkActionFutureResultDto makeFailure(final String processedObjectCode, final String resultDetails, final Exception exception) {

        BulkActionFutureResultDto result = new BulkActionFutureResultDto();
        result.processedObjectCode = processedObjectCode;
        result.success = false;
        result.resultDetails = resultDetails;
        result.exception = exception;
        return result;
    }

    public String getProcessedObjectCode() {
        return processedObjectCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResultDetails() {
        return resultDetails;
    }

    public Exception getException() {
        return exception;
    }
}
