package com.potato112.springservice.domain.user.model;


public enum GroupType {

    SPECIALIST("Specialist"),
    DISTRIBUTOR("Product Distributor"),
    OWNER("Product Owner");

    private final String description;

    GroupType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}



