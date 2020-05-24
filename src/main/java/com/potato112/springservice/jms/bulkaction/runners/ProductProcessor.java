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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Runs inside InvestmentAmortizationProcessor
 * - handles transactions
 * - returns processing messages
 * - handles status change of Product by status manager
 */
// TODO IMPLEMENT EMBEDED PROCESSING LOGIC HERE
@Component
public class ProductProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProcessor.class);


    private final InvestmentDao investmentDao;

    public ProductProcessor(InvestmentDao investmentDao) {
        this.investmentDao = investmentDao;
    }

    /**
     * Handles processing Products (processing embed in Amortization Processing).
     * - database operations
     * - transaction handling for single investment Product
     * - sets status and returns result message
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {CustomExplicitBusinessException.class})
    public String processProduct(InvestmentProduct investmentProduct, InvestmentStatus newStatus) throws CustomExplicitBusinessException {

        LOGGER.info("Start of embed processing in new transaction... Target status:" + newStatus.name());

        IntInvestmentItem intInvestmentItem = investmentProduct.getIntInvestmentItem();

        // FIXME prove other independent document db operation will be rolled back with the failed product rollback
        // suppose this investment document will be rolled back when exception is thrown
        InvestmentDocument investmentDocument = intInvestmentItem.getInvestmentDocument();
        investmentDocument.setInvestmentStatus(InvestmentStatus.PROCESSED);
        investmentDao.updateDocument(investmentDocument);

        if (!investmentProduct.getIsValidFlag()) {
            throw new CustomExplicitBusinessException("Failed to process product. Business rule violated. ", investmentProduct);
        }
        // some investment product logic
        // Product messages first created in db
        investmentProduct.setInvestmentProductStatus(InvestmentProductStatus.PROCESSED);
        // some logic with throw new exceptions (processing some collections based on product)
        // exceptions handling

        return "TODO (INFO/WARNING) PROCESSED message";
    }
}
