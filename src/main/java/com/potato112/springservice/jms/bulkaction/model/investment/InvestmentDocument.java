package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// TODO entity structure with interfaces

public class InvestmentDocument extends BaseInterfaceTable implements SysDocument, Lockable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String carNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String getCode() {
        return getCarNumber();
    }

    @Override
    public String getDocumentId() {
        return getId();
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
