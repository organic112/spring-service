package com.potato112.springservice.jms.bulkaction.services;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.init.ChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.investment.*;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionFutureResultVo;
import com.potato112.springservice.jms.bulkaction.runners.AbstractBARunner;
import com.potato112.springservice.jms.bulkaction.runners.AsyncStatusChanger;
import com.potato112.springservice.jms.bulkaction.runners.ChangeStatusBARunner;
import com.potato112.springservice.jms.bulkaction.runners.InvestmentAmortizationBARunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * Current implementation of 'business logic - Investment amortization' that ends with changed status
 * - transaction is not supported here, but embeded service handles transactions
 */
@Component
public class AsyncInvestmentAmortizationStatusChanger extends AsyncStatusChanger {


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    protected void processSingleDocument(SysDocument document, ChangeStatusBAInit init, ChangeStatusBARunner runner) {

        InvestmentAmortizationBARunner amortizationBARunner = (InvestmentAmortizationBARunner) runner;

        InvestmentStatus newStatus = (InvestmentStatus) init.getTargetStatus();
        String loggedUser = init.getLoggedUser();
        IntInvestmentItem sysDocument = (IntInvestmentItem) document;
        String documentId = sysDocument.getDocumentId();

        amortizationBARunner.investmentDocumentAmortizationProcess(documentId, newStatus, loggedUser);
    }

    /**
     * Overrides base processor transaction propagation form REQUIRES_NEW to: NOT_SUPPORTED
     * At this level processing is async, but should not be in transaction yet
     * Dedicated Investment amortization processing transaction management is implemented in
     * embed services called when external process is processed
     *
     * if async collisions  you can synchronize here with 'synchronized':
     *
     * public synchronized Future<BulkActionFutureResult> processSingleItemAsync()
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Future<BulkActionFutureResultVo> processSingleItemAsync(String id, BulkActionInit bulkActionInit, AbstractBARunner parentRunner) {
        return super.processSingleItemAsync(id, bulkActionInit, parentRunner);
    }
}
