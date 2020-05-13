package com.potato112.springservice.jms.bulkaction.model.results;

import java.io.Serializable;


/**
 * Bulk Action runs multiple parallel async processing operations.
 * This class represents result of single such operation.
 */
public class BulkActionFutureResultVo implements Serializable {

    private String processedObjectCode;
    private boolean success = true;
    private String resultDetails;
    private Exception exception;

    public BulkActionFutureResultVo() {
    }

    public static BulkActionFutureResultVo makeSuccess(final String processedObjectCode, final String resultDetails) {

        BulkActionFutureResultVo result = new BulkActionFutureResultVo();
        result.processedObjectCode = processedObjectCode;
        result.success = true;
        result.resultDetails = resultDetails;
        return result;
    }

    public static BulkActionFutureResultVo makeFailure(final String processedObjectCode, final String resultDetails, final Exception exception) {

        BulkActionFutureResultVo result = new BulkActionFutureResultVo();
        result.processedObjectCode = processedObjectCode;
        result.success = false;
        result.resultDetails = resultDetails;
        result.exception = exception;
        return result;
    }

    private static void makeResult(boolean isSuccess){
        // TODO refactor
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
