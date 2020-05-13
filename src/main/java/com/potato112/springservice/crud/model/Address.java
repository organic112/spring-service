package com.potato112.springservice.crud.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "demo-db", name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private String street;
    private String townName;
    private String zip;

}
