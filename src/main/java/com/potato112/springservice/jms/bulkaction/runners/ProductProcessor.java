package com.potato112.springservice.jms.bulkaction.runners;


import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.exception.StatusManagerException;
import com.potato112.springservice.jms.bulkaction.model.exception.checked.CustomExplicitBussiesException;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Runs inside InvestmentAmortizationProcessor
 * - handles transactions
 * - returns processing messages
 * - handles status change of Product
 */
@Component
public class ProductProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProcessor.class);

    // TODO IMPLEMENT EMBEDED PROCESSING LOGIC HERE

    /**
     * Handles processing Products (processing embed in Amortization Processing).
     *  - database operations
     *  - transaction handling for single investment Product
     *  - sets status and returns result message
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {StatusManagerException.class, CustomExplicitBussiesException.class})
    public void processProduct(InvestmentProduct investmentProduct, InvestmentStatus newStatus) throws CustomExplicitBussiesException {

        LOGGER.info("Start of embed processing in new transaction... Target status:" + newStatus.name());

        // Product messages first created in db

        // convert Investement target status to product status
        // some logic with exceptions (processing some collections based on product)

        // exceptions handling
        // in exceptions handling settning product status

        // FIXME (condition)

        if (false) {
            throw new CustomExplicitBussiesException("Custom Business rule violated");
        }

    }

}
