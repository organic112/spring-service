package com.potato112.springservice.jms.bulkaction.model.exception.checked;

import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentProduct;

/**
 * Exception for explicit business rule violation
 */
public class CustomExplicitBusinessException extends Exception {

    InvestmentProduct investmentProduct;

    public CustomExplicitBusinessException(String message, InvestmentProduct investmentProduct){
        super(message);
        this.investmentProduct = investmentProduct;
    }


    public CustomExplicitBusinessException(String message) {
        super(message);
    }

    public CustomExplicitBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvestmentProduct getInvestmentProduct() {
        return investmentProduct;
    }
}
