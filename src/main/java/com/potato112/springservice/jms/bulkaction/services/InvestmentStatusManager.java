package com.potato112.springservice.jms.bulkaction.services;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.doclock.AlreadyLockedException;
import com.potato112.springservice.jms.bulkaction.model.exception.StatusManagerException;
import com.potato112.springservice.jms.bulkaction.model.init.ChangeStatusParams;
import com.potato112.springservice.jms.bulkaction.model.interfaces.StatusManager;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.runners.InvestmentAmortizationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InvestmentStatusManager implements StatusManager<IntInvestmentItem, InvestmentStatus> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentStatusManager.class);

    @Autowired
    private InvestmentAmortizationProcessor investmentAmortizationProcessor;

    @Override
    public boolean canChangeStatus(IntInvestmentItem document, InvestmentStatus newStatus) {
        return isLegalStatusChange(document, newStatus);
    }

    @Override
    public boolean canChangeStatus(IntInvestmentItem document, InvestmentStatus newStatus, String cancelationMessage) {

        return isLegalStatusChange(document, newStatus);
    }

    /**
     * Handles simple status change, alternative to 'sophisticated investment amortization' status change (different runner)
     * e.g. operations with no thread resource collisions
     */
    @Override
    public void changeStatus(ChangeStatusParams<IntInvestmentItem, InvestmentStatus> params) throws AlreadyLockedException {

        LOGGER.info("Change status start...");

        System.out.println("ECHO01 Simple status change...");

        IntInvestmentItem investmentItem = params.getDocument();

        InvestmentStatus newStatus = params.getNewStatus();
        String loggedUser = params.getLoggedUser();

        canChangeStatus(investmentItem, newStatus);

        changeStatus(investmentItem, newStatus, loggedUser);
    }

    private void changeStatus(IntInvestmentItem intInvestmentItem, InvestmentStatus newStatus, String loggedUser) {

        // TODO
        // lock item
        // set new status

        // to have failure, change this status to different than target status
        intInvestmentItem.setInvestmentStatus(newStatus);
        // update item in dB
        // remove item lock

        validateProcessingResult("FIXME processing message from status manager ", intInvestmentItem);
        System.out.println("STATUS MANAGER: DOCUMENT PROCESSED - STATUS CHANGED: old: " + intInvestmentItem.getInvestmentStatus()
                + " new:" + newStatus);
    }

    private boolean isLegalStatusChange(IntInvestmentItem intInvestmentItem, InvestmentStatus newStatus) {
        InvestmentStatus actualStatus = intInvestmentItem.getInvestmentStatus();
        Set<SysStatus> exitStatuses = actualStatus.getExitStatuses();

        try {
            if (!exitStatuses.isEmpty() && exitStatuses.contains(newStatus)) {
                return exitStatuses.contains(newStatus);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    /**
     * Handles sophisticated status change, alternative to 'simple investment amortization' status change (different runner)
     * e.g. operations with possible thread resource collisions
     */
    public void changeAmortizationProcessingStatus(IntInvestmentItem intInvestmentItem, InvestmentStatus newStatus) {

        System.out.println("ECHO03 Sophisticated status change in processor..." + intInvestmentItem.getDocumentId());

        String processingMessage = investmentAmortizationProcessor.processInvestment(intInvestmentItem, newStatus);

        validateProcessingResult(processingMessage, intInvestmentItem);
    }

    private void validateProcessingResult(String message, IntInvestmentItem intInvestmentItem) {

        if (intInvestmentItem.getInvestmentStatus().equals(InvestmentStatus.NOT_PROCESSED)) {
            throw new StatusManagerException(message);
        }
    }
}
