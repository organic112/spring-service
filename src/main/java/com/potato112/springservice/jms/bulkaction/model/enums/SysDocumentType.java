package com.potato112.springservice.jms.bulkaction.model.enums;

public enum SysDocumentType {

    INVESTMENT_DOCUMENT("Investment amortization document"),
    CLIENT_DOCUMENT("Client history document"),
    AGREEMENT_DOCUMENT("Agreement document"),
    CAR_DOCUMENT("Car document");

    private String fullName;

    SysDocumentType(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
