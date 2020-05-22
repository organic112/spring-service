package com.potato112.springservice.jms.bulkaction.runners;


import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;
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
     * - database operations
     * - transaction handling for single investment Product
     * - sets status and returns result message
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {StatusManagerException.class, CustomExplicitBussiesException.class})
    public String processProduct(InvestmentProduct investmentProduct, InvestmentStatus newStatus) throws CustomExplicitBussiesException {

        LOGGER.info("Start of embed processing in new transaction... Target status:" + newStatus.name());

        if (!investmentProduct.getIsValidFlag()) {
            throw new CustomExplicitBussiesException("Custom Business rule violated");
        }
        // some investment product logic
        // Product messages first created in db
        investmentProduct.setInvestmentProductStatus(InvestmentProductStatus.PROCESSED);
        // some logic with throw new exceptions (processing some collections based on product)
        // exceptions handling

        return "TODO should return message";
    }
}
