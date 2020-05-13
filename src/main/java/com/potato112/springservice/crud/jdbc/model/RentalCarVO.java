package com.potato112.springservice.crud.jdbc.model;


public class RentalCarVO extends BaseVO {

    private String brand;
    private String color;
    private String payloadKg;
    private String pricePerHour;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPayloadKg() {
        return payloadKg;
    }

    public void setPayloadKg(String payloadKg) {
        this.payloadKg = payloadKg;
    }

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public String toString() {
        return "RentalCarVO{" +
                "brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", payloadKg='" + payloadKg + '\'' +
                ", pricePerHour='" + pricePerHour + '\'' +
                '}';
    }
}
