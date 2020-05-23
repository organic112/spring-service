package com.potato112.springservice.jms.bulkaction.runners;

import com.potato112.springservice.jms.bulkaction.model.exception.StatusManagerException;
import com.potato112.springservice.jms.bulkaction.model.exception.checked.CustomExplicitBusinessException;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionFutureResultDto;
import com.potato112.springservice.jms.doclock.AlreadyLockedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * Abstract asynchronous Bulk Action Processor (responsibilities):
 * - handles single item async processing in new transaction
 * - handles exceptions that may occur when processing single Item
 * - provides future result of processing: success / failure
 *
 * extending processors should provide:
 * - single Item document id
 * - implementation of processing single document (by runner)
 */
public abstract class AbstractAsyncBAProcessor<OBJTYPE extends SysDocument, INIT extends BulkActionInit, RUNNER extends AbstractBARunner> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAsyncBAProcessor.class);

    protected abstract OBJTYPE getDocumentById(String id, RUNNER runner);

    protected abstract void processSingleDocument(OBJTYPE document, INIT init, RUNNER runner) throws CustomExplicitBusinessException;


    @Async
    // note dedicated TaskExecutor bean is defined in config for this annotation, TODO check if it is proper solution
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Future<BulkActionFutureResultDto> processSingleItemAsync(final String id, final INIT bulkActionInit, final RUNNER parentRunner) {

        String code = "";
        OBJTYPE document = null;

        // fetch document for processing (from runner)
        // validate fetched document
        try {

            document = getDocumentById(id, parentRunner);
            if (null == document) {
                throw new IllegalArgumentException("Failed to process single document async. Document is null, Id: " + id);
            }
            code = document.getCode();
        } catch (Exception e) {

            String message = "Async Processor Error. Failed to process single document async.";
            return parentRunner.failure(code, message, e);
        }

        try {
            // processing essential action logic
            processSingleDocument(document, bulkActionInit, parentRunner);

        } catch (AlreadyLockedException e) {

            String message = "Failed process single document async. AlreadyLockedException";
            LOGGER.debug(message);
            return parentRunner.failure(code, message, e);

        } catch (StatusManagerException e) {

            // default exception to pass 'business cause failure' message
            // StringBuilder: append message parts here

            String message = "Failed process single document async. StatusManagerException";
            LOGGER.debug(message);
            return parentRunner.failure(code, message, e);

        } catch (CustomExplicitBusinessException e) {
            String message = "Failed process single document async. CustomExplicitBusinessException";
            LOGGER.debug(message);
            return parentRunner.failure(code, message, e);

        } catch (Exception e) {
            String message = "Failed process single document async. Exception";
            LOGGER.debug(message);
            return handleException(e, parentRunner, code);
        }

        BulkActionFutureResultDto result = BulkActionFutureResultDto.makeSuccess(code, getSuccessMessage(document));
        return new AsyncResult<>(result);
    }

    private String getSuccessMessage(OBJTYPE processedObject) {
        return "OK";
    }

    private Future<BulkActionFutureResultDto> handleException(Exception e, RUNNER parentRunner, String code) {

        if (e instanceof AlreadyLockedException || e.getCause() instanceof AlreadyLockedException) {
            AlreadyLockedException exception = (AlreadyLockedException) e.getCause();
            String message = "Custom Message" + e.getMessage();
            return parentRunner.failure(code, message, exception);
        }

        if (e instanceof StatusManagerException || e.getCause() instanceof StatusManagerException) {
            StatusManagerException exception = (StatusManagerException) e.getCause();
            String message = "Custom Message" + e.getMessage();
            return parentRunner.failure(code, message, exception);
        }

        if (e instanceof CustomExplicitBusinessException || e.getCause() instanceof CustomExplicitBusinessException) {
            CustomExplicitBusinessException exception = (CustomExplicitBusinessException) e.getCause();
            String message = "Custom Message" + e.getMessage();
            return parentRunner.failure(code, message, exception);
        }

        if (e instanceof AlreadyLockedException || e.getCause() instanceof AlreadyLockedException) {
            AlreadyLockedException exception = (AlreadyLockedException) e.getCause();
            String message = "Custom Message" + e.getMessage();
            return parentRunner.failure(code, message, exception);
        }

        String unexpectedErrorMessage = "Unexpected Error: Failed Future Bulk Action result. Unexpected internal error in bulk action processor" + e.getMessage();
        LOGGER.debug(unexpectedErrorMessage);
        return parentRunner.failure(code, unexpectedErrorMessage, e);
    }
}
