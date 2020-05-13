package com.potato112.springservice.crud.jdbc.model;

public class RentalClientVO {


    private String rentalAgreementId;

    private String name;
    private String surname;
    private String clientBirthDate;
    private String country;
    private String address;


    public String getRentalAgreementId() {
        return rentalAgreementId;
    }

    public void setRentalAgreementId(String rentalAgreementId) {
        this.rentalAgreementId = rentalAgreementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getClientBirthDate() {
        return clientBirthDate;
    }

    public void setClientBirthDate(String clientBirthDate) {
        this.clientBirthDate = clientBirthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
