package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;

import javax.persistence.Id;
import java.io.Serializable;

// TODO make this entity

public class InvestmentProduct implements Serializable {

    @Id
    private String id;

    private InvestmentProductStatus investmentProductStatus;

    private String productNumber;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvestmentProductStatus getInvestmentProductStatus() {
        return investmentProductStatus;
    }

    public void setInvestmentProductStatus(InvestmentProductStatus investmentProductStatus) {
        this.investmentProductStatus = investmentProductStatus;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }
}
