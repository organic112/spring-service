package com.potato112.springservice.crud.jpa.services;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.crud.model.RentalCar;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.hibernate.cfg.Configuration;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class RentalCarCRUDServiceTest {


    @Autowired
   private RentalCarCRUDService rentalCarCRUDService;

    @Test
    public void shouldPersistHistoryOfChangesOfAuditedEntity(){

        RentalCar rentalCar = new RentalCar();
        rentalCar.setBrand("Fiat");
        rentalCar.setColor("red");
        rentalCar.setPayloadKG(100);
        rentalCarCRUDService.save(rentalCar);

        rentalCar.setColor("yellow");
        rentalCarCRUDService.update(rentalCar);

        rentalCar.setColor("blue");
        rentalCarCRUDService.update(rentalCar);

        rentalCar.setColor("green");
        rentalCarCRUDService.update(rentalCar);
    }
}