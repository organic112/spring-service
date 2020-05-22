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

import java.util.Arrays;
import java.util.List;


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
    public String processInvestment(IntInvestmentItem intInvestmentItem, InvestmentStatus newStatus) {

        LOGGER.info("Starting amortization processing in ...");

        String reaultMessage = "";

        String itemId = intInvestmentItem.getId();

        // init hibernate session
        IntInvestmentItem dbItem = investmentDao.getInvestmentById(itemId);


        reaultMessage = processInvestmentProducts(dbItem, newStatus);

        investmentDao.update(dbItem);

        InvestmentDocument document = intInvestmentItem.getInvestmentDocument();
        String investmentId = document.getId();

        // Fetch investment document from DB to get persistence context
        InvestmentDocument investmentDocument = investmentDao.getInvestmentDocumentById(investmentId);
        // some logic
        //investmentDocument.setInvestmentStatus(InvestmentStatus.PROCESSED);
        intInvestmentItem.setInvestmentDocument(investmentDocument);

        LOGGER.info("Amortization processing ended");
        return reaultMessage;
    }

    private String processInvestmentProducts(IntInvestmentItem dbItem, InvestmentStatus newStatus) {

        String resultMessage = "";
        List<InvestmentProduct> investmentProducts = dbItem.getInvestmentProducts();
        try {

            for (InvestmentProduct product : investmentProducts) {
                resultMessage = productProcessor.processProduct(product, newStatus);
                dbItem.setInvestmentStatus(InvestmentStatus.PROCESSED);
            }

        } catch (CustomExplicitBussiesException e) {

            dbItem.setInvestmentStatus(InvestmentStatus.NOT_PROCESSED);
            dbItem.getInvestmentProducts().forEach(prod -> prod.setInvestmentProductStatus(InvestmentProductStatus.NOT_PROCESSED));

            String message = "Failed to process product. Business rule violated. ";
            LOGGER.debug(message + e.getMessage());

        } catch (StatusManagerException e) {
            LOGGER.debug(e.getMessage());
        }
        return resultMessage;
    }
}
