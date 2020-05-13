package com.potato112.springservice.crud.model;

import javax.persistence.*;

@Entity
@Table(schema = "demo-db", name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    private String countryName;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private MarketType marketType;




}
