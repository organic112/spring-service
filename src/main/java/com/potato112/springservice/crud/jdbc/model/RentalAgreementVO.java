package com.potato112.springservice.crud.jdbc.model;


/**
 * This RentalAgreementVO represents db table row from mapping used as
 * joining table between RentalCar and RentalClient
 * (RentalAgreement is used as mapping table - many to many relation between RentalCar and RentalClient).
 */
public class RentalAgreementVO extends BaseVO {

    private String rentalCarId;
    private String rentalClientId;

    private String validFromDateTime;
    private String notes;

    public String getRentalCarId() {
        return rentalCarId;
    }

    public void setRentalCarId(String rentalCarId) {
        this.rentalCarId = rentalCarId;
    }

    public String getRentalClientId() {
        return rentalClientId;
    }

    public void setRentalClientId(String rentalClientId) {
        this.rentalClientId = rentalClientId;
    }

    public String getValidFromDateTime() {
        return validFromDateTime;
    }

    public void setValidFromDateTime(String validFromDateTime) {
        this.validFromDateTime = validFromDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
