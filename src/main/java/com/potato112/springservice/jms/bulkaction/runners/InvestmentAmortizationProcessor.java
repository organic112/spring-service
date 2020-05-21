package com.potato112.springservice.jms.bulkaction.runners;

import com.potato112.springservice.jms.bulkaction.dao.InvestmentDao;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.exception.StatusManagerException;
import com.potato112.springservice.jms.bulkaction.model.exception.checked.CustomExplicitBussiesException;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


// Processing wrapper

@Component
public class InvestmentAmortizationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvestmentAmortizationProcessor.class);
    private final InvestmentDao investmentDao;
    private final ProductProcessor productProcessor;

    public InvestmentAmortizationProcessor(InvestmentDao investmentDao, ProductProcessor productProcessor) {
        this.investmentDao = investmentDao;
        this.productProcessor = productProcessor;
    }
    // TODO IMPLEMENT ESSENTIAL PROCESSING LOGIC HERE

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String processInvestmentsAndCreateAmortizationRecords(IntInvestmentItem intInvestmentItem, InvestmentStatus newStatus) {

        LOGGER.info("Starting amortization processing in ...");

        try {

           InvestmentDocument document = intInvestmentItem.getInvestmentDocument();
           String investmentId = document.getId();

            // Fetch investment document from DB to get persistence context
            InvestmentDocument investmentDocument = investmentDao.getInvestmentDocumentById(investmentId);
            investmentDocument.setInvestmentStatus(InvestmentStatus.PROCESSED);

            intInvestmentItem.setInvestmentDocument(investmentDocument);


            // some business logic, create objects etc.
            InvestmentProduct investmentProduct = new InvestmentProduct();

            productProcessor.processProduct(investmentProduct, newStatus);
            investmentProduct.setInvestmentProductStatus(InvestmentProductStatus.PROCESSED);

            // NOTE to have failure logic should set status to <> PROCESSED
            intInvestmentItem.setInvestmentStatus(InvestmentStatus.PROCESSED);
            investmentDao.update(intInvestmentItem);

            LOGGER.info("Amortization processing ended");

        } catch (CustomExplicitBussiesException e) {

            String message = "Failed to process product. Business rule violated";

            LOGGER.debug(message + e.getMessage());
        } catch (StatusManagerException e) {

            LOGGER.debug(e.getMessage());
        }

        return "FIXME processing message from Investment Amortization Processor";
    }
}
