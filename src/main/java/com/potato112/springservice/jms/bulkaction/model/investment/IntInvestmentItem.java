package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;

public class IntInvestmentItem extends BaseInterfaceTable implements Lockable {

    private String id;
    private InvestmentDocument investmentDocument;
    private InvestmentStatus investmentStatus;
    private Integer clientNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvestmentDocument getInvestmentDocument() {
        return investmentDocument;
    }

    public void setInvestmentDocument(InvestmentDocument investmentDocument) {
        this.investmentDocument = investmentDocument;
    }

    public InvestmentStatus getInvestmentStatus() {
        return investmentStatus;
    }

    public void setInvestmentStatus(InvestmentStatus investmentStatus) {
        this.investmentStatus = investmentStatus;
    }

    public Integer getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(Integer clientNumber) {
        this.clientNumber = clientNumber;
    }

    @Override
    public String getCode() {

        String code = investmentDocument.getCode();
        Integer clientNumber = getClientNumber();
        return code.concat(" - ").concat(clientNumber.toString());
    }

    @Override
    public String getDocumentId() {
        return id;
    }

    @Override
    public SysDocumentType getDocumentType() {
        return SysDocumentType.INVESTMENT_DOCUMENT;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public void setUpdateUser() {

    }
}
