package com.potato112.springservice.jms.bulkaction.runners;

import com.potato112.springservice.jms.bulkaction.dao.InvestmentDao;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.exception.StatusManagerException;
import com.potato112.springservice.jms.bulkaction.model.exception.checked.CustomExplicitBusinessException;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This service provides essential business logic:
 * - validates business logic conditions, creates objects etc.
 * - transactions are not supported here (supported in embed ProductProcessor)
 * - handles business exceptions thrown in ProductProcessor
 * - returns processing messages (warn / info / error)
 */
@Service
public class InvestmentAmortizationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentAmortizationProcessor.class);
    private final InvestmentDao investmentDao;
    private final ProductProcessor productProcessor;

    public InvestmentAmortizationProcessor(InvestmentDao investmentDao, ProductProcessor productProcessor) {
        this.investmentDao = investmentDao;
        this.productProcessor = productProcessor;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String processInvestment(IntInvestmentItem intInvestmentItem, InvestmentStatus newStatus) {

        LOGGER.info("Starting amortization processing in ...");
        String resultMessage = "";

        resultMessage = processInvestmentProducts(intInvestmentItem, newStatus);
        otherRelatedLogic(intInvestmentItem);

        LOGGER.info("Amortization processing ended");
        return resultMessage;
    }

    private void otherRelatedLogic(IntInvestmentItem intInvestmentItem) {
        InvestmentDocument document = intInvestmentItem.getInvestmentDocument();
        String investmentId = document.getId();
        InvestmentDocument investmentDocument = investmentDao.getInvestmentDocumentById(investmentId);
        // investmentDocument.setInvestmentStatus(InvestmentStatus.PROCESSED);
        intInvestmentItem.setInvestmentDocument(investmentDocument);
    }

    /**
     * Catch is on level of Product List loop.
     * Single Product fail in loop (thrown exception) rollbacks current single Product db operations.
     * In catch this failed product has updated status to NOT_PROCESSED and later is persisted
     * as member of list in IntInvestmentItem
     */
    private String processInvestmentProducts(IntInvestmentItem investmentItem, InvestmentStatus newStatus) {

        String resultMessage = "";
        List<InvestmentProduct> investmentProducts = investmentItem.getInvestmentProducts();
        try {
            for (InvestmentProduct product : investmentProducts) {
                resultMessage = productProcessor.processProduct(product, newStatus);
                investmentItem.setInvestmentStatus(InvestmentStatus.PROCESSED);
            }
        } catch (CustomExplicitBusinessException e) {

            // get failed product by reference and set new failure NOT_PROCESSED status
            e.getInvestmentProduct().setInvestmentProductStatus(InvestmentProductStatus.NOT_PROCESSED);
            investmentItem.setInvestmentStatus(InvestmentStatus.NOT_PROCESSED);
            resultMessage = e.getMessage();

            LOGGER.debug(resultMessage);
        } catch (StatusManagerException e) {
            LOGGER.debug(e.getMessage());
        }
        return resultMessage;
    }
}
