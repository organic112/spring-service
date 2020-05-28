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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionNullLoginInTestMockedSession() {



  /*      RentalCar rentalCar = new RentalCar();
        rentalCar.setBrand("Fiat");
        rentalCar.setColor("red");
        rentalCar.setPayloadKG(100);
        System.out.println("before save");
        rentalCarCRUDService.save(rentalCar);*/

        throw new IllegalStateException();
    }

    @Test
    public void checkIfRequestIsPresent() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (null == requestAttributes) {
            System.out.println("requestAttributes not present:" + requestAttributes);
        } else {
            System.out.println("requestAttributes is present:" + requestAttributes);
        }
    }

    @Test
    public void shouldPersistHistoryOfChangesOfAuditedEntity(){

        RentalCar rentalCar = new RentalCar();
        rentalCar.setBrand("Fiat");
        rentalCar.setColor("red");
        rentalCar.setPayloadKG(100);
        rentalCar.setCreateUser("testCreateUser");


        System.out.println("before save");
        rentalCarCRUDService.save(rentalCar);
        rentalCar.setUpdateUser("testUpdateUser");

        rentalCar.setColor("before yellow");

        rentalCar.setUpdateUser("test-update-user");

        System.out.println("before update");
        rentalCarCRUDService.update(rentalCar);

        rentalCar.setColor("blue");

        System.out.println("before update");
        rentalCarCRUDService.update(rentalCar);

        rentalCar.setColor("green");

        System.out.println("before update");
        rentalCarCRUDService.update(rentalCar);
    }
}