package com.potato112.springservice.jms.bulkaction.model.interfaces;

import com.potato112.springservice.jms.bulkaction.model.entity.BulkActionResult;
import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionStatus;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;

/**
 * methods to operate on bulk action results
 * covers complete bulk action live-cycle
 */
public interface BulkActionResultManager {

    /**
     * provides single bulk action result from db
     */
    BulkActionResult getBulkActionResultById(String id);

    void markInProgress(String id);

    /**
     * marks as completed
     * sets proper status
     * sets proper message based on result
     */

    void completeBulkAction(String bulkActionResultId, BulkActionsRunResultVo bulkActionsRunResultVo);

    void completeBulkAction(String bulkActionResultId, BulkActionStatus bulkActionStatus, String bulkActionMessage);

}
