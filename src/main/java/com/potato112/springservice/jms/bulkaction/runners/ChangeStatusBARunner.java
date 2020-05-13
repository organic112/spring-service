package com.potato112.springservice.jms.bulkaction.runners;

import com.potato112.springservice.jms.bulkaction.model.init.ChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.init.ChangeStatusParams;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.StatusManager;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionFutureResultVo;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

public abstract class ChangeStatusBARunner<OBJTYPE extends SysDocument, STATUS extends SysStatus> extends AbstractBARunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeStatusBARunner.class);

    public abstract OBJTYPE getDocumentById(String id);

    public abstract StatusManager<OBJTYPE, STATUS> getStatusManager();

    protected abstract AsyncStatusChanger<OBJTYPE, STATUS> getStatusChanger();

    public BulkActionsRunResultVo run(final BulkActionInit bulkActionInit) {

        @SuppressWarnings("unchecked")
        ChangeStatusBAInit<OBJTYPE, STATUS> init = (ChangeStatusBAInit<OBJTYPE, STATUS>) bulkActionInit;

        LOGGER.info("Start running...");

        boolean totalSuccess = true;
        Future<BulkActionFutureResultVo> bulkActionResult;
        final Map<String, Future<BulkActionFutureResultVo>> bulkActionResults;
        bulkActionResults = new HashMap<>();

        final BulkActionsRunResultVo result = new BulkActionsRunResultVo();


        validateInit(bulkActionInit);

        final Set<String> ids = init.getDocumentIds();

        //starts threads
        LOGGER.info("Starting threads for selected document ids... Ids size:" + ids.size());

        for (final String id : ids) {
            bulkActionResult = changeStatus(id, bulkActionInit);
            bulkActionResults.put(id, bulkActionResult);
        }

        // check results
        String details = generateDetailsMessage(init);

        LOGGER.info("Checking results from threads, details: " + details);

        for (String id : bulkActionResults.keySet()) {

            Future<BulkActionFutureResultVo> future = bulkActionResults.get(id);
            BulkActionFutureResultVo singleProcessingResult = getSingleProcessingResult(id, future);
            totalSuccess = totalSuccess && singleProcessingResult.isSuccess();
            result.getResultList().add(singleProcessingResult);
        }

        LOGGER.info("Setting Threads future results... Total success:" + totalSuccess);

        result.setSuccess(totalSuccess);
        result.setDetails(details);
        return result;
    }

    /**
     * Wrapper for changing status of single sys document.
     * By default method calls AsyncStatusChanger, that runs in separate thread and separate transaction
     */
    protected Future<BulkActionFutureResultVo> changeStatus(String id, BulkActionInit bulkActionInit) {
        AsyncStatusChanger<OBJTYPE, STATUS> statusChanger = getStatusChanger();
        return statusChanger.processSingleItemAsync(id, castInit(bulkActionInit), this);
    }

    protected void changeStatusOfSingleDocument(final OBJTYPE document, final ChangeStatusBAInit<OBJTYPE, STATUS> bulkActionInit) {

        final String loggedUser = bulkActionInit.getLoggedUser();
        final ChangeStatusBAInit<OBJTYPE, STATUS> init = castInit(bulkActionInit);
        final STATUS targetStatus = init.getTargetStatus();
        final Set<String> documentIdSet = init.getDocumentIds();
        final String cancelMessage = init.getCancellationMessage();

        validateUserPermissionsToDocument(loggedUser, document);
        validateSingleDocumentIdAgainstOtherIds(document, documentIdSet, targetStatus);

        ChangeStatusParams<OBJTYPE, STATUS> params = new ChangeStatusParams<>();
        params.setDocument(document);
        params.setNewStatus(targetStatus);
        params.setCancellationMessage(cancelMessage);
        params.setLoggedUser(loggedUser);

        getStatusManager().changeStatus(params);
    }

    protected void validateUserPermissionsToDocument(String loggedUser, OBJTYPE document) {
        // TODO implement if need this check on backend side
    }

    protected void validateSingleDocumentIdAgainstOtherIds(OBJTYPE document, Set<String> allSelectedIds, STATUS targetStatus) {
        // TODO implement if need this check on backend side
    }

    @Override
    protected void validateInit(BulkActionInit bulkActionInit) {

        super.validateInit(bulkActionInit);

        if (!(bulkActionInit instanceof ChangeStatusBAInit<?, ?>)) {
            throw new IllegalArgumentException("Bulk action init should be of type ChangeStatusBulkAction");
        }
    }

    @Override
    protected String generateDetailsMessage(BulkActionInit bulkActionInit) {

        ChangeStatusBAInit<OBJTYPE, STATUS> init = castInit(bulkActionInit);
        STATUS targetStatus = init.getTargetStatus();
        String message = "Status change to:";
        return message.concat(targetStatus.toString());
    }
    @SuppressWarnings("unchecked")
    private ChangeStatusBAInit<OBJTYPE, STATUS> castInit(final BulkActionInit bulkActionInit) {
        return (ChangeStatusBAInit<OBJTYPE, STATUS>) bulkActionInit;
    }
}
