package com.potato112.springservice.search.model;

import com.potato112.springservice.search.interfaces.BaseTO;
import lombok.Data;

import java.io.Serializable;

/**
 * Result TO for RentalCar entity
 */
@Data
public class RentalCarTO implements Serializable, BaseTO {

    private String id;
    private String brand;
    private String color;

    @Override
    public String getId() {
        return id;
    }
}
