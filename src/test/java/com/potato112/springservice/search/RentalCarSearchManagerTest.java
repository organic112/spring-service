package com.potato112.springservice.search;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.crud.jpa.services.RentalCarCRUDService;
import com.potato112.springservice.crud.model.RentalCar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class RentalCarSearchManagerTest {

    @Autowired
    private RentalCarCRUDService rentalCarCRUDService;

    @Autowired
    private RentalCarSearchManager rentalCarSearchManager;

    private RentalCar rentalCar1;
    private RentalCar rentalCar2;
    private RentalCar rentalCar3;
    private RentalCar rentalCar4;

    @Before
    public void init() {

        rentalCar1 = new RentalCar();
        rentalCar1.setBrand("Fiat");
        rentalCar1.setColor("red");
        rentalCar1.setPayloadKG(100);
        rentalCarCRUDService.save(rentalCar1);

        rentalCar2 = new RentalCar();
        rentalCar2.setBrand("Tesla");
        rentalCar2.setColor("blue");
        rentalCar2.setPayloadKG(200);
        rentalCarCRUDService.save(rentalCar2);

        rentalCar3 = new RentalCar();
        rentalCar3.setBrand("Fiat");
        rentalCar3.setColor("red");
        rentalCar3.setPayloadKG(100);
        rentalCarCRUDService.save(rentalCar3);

        rentalCar4 = new RentalCar();
        rentalCar4.setBrand("Ford");
        rentalCar4.setColor("yellow");
        rentalCar4.setPayloadKG(150);
        rentalCarCRUDService.save(rentalCar4);
    }

    @After
    public void cleanup() {
        rentalCarCRUDService.delete(rentalCar1);
        rentalCarCRUDService.delete(rentalCar2);
        rentalCarCRUDService.delete(rentalCar3);
        rentalCarCRUDService.delete(rentalCar4);
    }

    @Test
    public void shouldReturnCountResultWhenInStatement() {

        QueryMeta queryMeta = new QueryMeta();
        Filter filter = new Filter();
        filter.setFilterType(FilterType.IN);
        filter.setPropertyId("payloadKG"); // propertyName

        List<Serializable> values = Arrays.asList(100);
        filter.setValues(values);
        queryMeta.setFilters(Arrays.asList(filter));

        int numberOfIds = rentalCarSearchManager.count(queryMeta);
        System.out.println("ECHO01 RENTAL CAR number of items:" + numberOfIds);

        Assertions.assertEquals(2, numberOfIds);
    }

    @Test
    public void shouldReturnOneResultWhenEqualsStatement() {

        QueryMeta queryMeta = new QueryMeta();
        Filter filter = new Filter();
        filter.setFilterType(FilterType.EQUALS);
        filter.setPropertyId("color"); // propertyName
        filter.setValue("blue");

        queryMeta.setFilters(Arrays.asList(filter));

        int numberOfIds = rentalCarSearchManager.count(queryMeta);
        queryMeta.setNumberOfIds(numberOfIds); // this should be provided from count?

        List<RentalCarTO> rentalCarTOS = rentalCarSearchManager.find(queryMeta);

        System.out.println("ECHO01 RENTAL CAR:" + rentalCarTOS.size());
        System.out.println("fetched car brand:" + rentalCarTOS.get(0).getBrand());

        Assertions.assertEquals(1, rentalCarTOS.size());
    }

    @Test
    public void shouldReturnOneResultWhenInStatement() {

        QueryMeta queryMeta = new QueryMeta();
        Filter filter = new Filter();
        filter.setFilterType(FilterType.IN);
        filter.setPropertyId("payloadKG"); // propertyName

        List<Serializable> values = Arrays.asList(100);
        filter.setValues(values);
        queryMeta.setFilters(Arrays.asList(filter));

        int numberOfIds = rentalCarSearchManager.count(queryMeta);
        queryMeta.setNumberOfIds(numberOfIds); // this should be provided by previous count query

        List<RentalCarTO> rentalCarTOS = rentalCarSearchManager.find(queryMeta);

        System.out.println("ECHO01 RENTAL CAR:" + rentalCarTOS.size());
        System.out.println("fetched car brand:" + rentalCarTOS.get(0).getBrand());

        Assertions.assertEquals(2, rentalCarTOS.size());
    }

    @Test
    public void shouldReturnEmptyResultWhenValueCharSizeBiggerThanDbColumnSize() {

        // varchar(256) in db
        String stringLength300 =
                "dasdfadfasdfasdfasdfasdfasdfadsfasdfasdfasdfasdfasdfasdfadfasdfadsasdfadsfasdfadsfasdasdfasddasfdsd" +
                        "dasdfadfasdfasdfasdfasdfasdfadsfasdfasdfasdfasdfasdfasdfadfasdfadsasdfadsfasdfadsfasdasdfasddasfdsd" +
                        "dasdfadfasdfasdfasdfasdfasdfadsfasdfasdfasdfasdfasdfasdfadfasdfadsasdfadsfasdfadsfasdasdfasddasfdsd";

        List<Serializable> values = Arrays.asList(stringLength300);

        QueryMeta queryMeta = new QueryMeta();
        Filter filter = new Filter();
        filter.setFilterType(FilterType.IN);
        filter.setPropertyId("color"); // propertyName
        filter.setValues(values);

        queryMeta.setFilters(Arrays.asList(filter));
        queryMeta.setOffset(5);
        queryMeta.setPageSize(5);
        queryMeta.setStartIndex(0);

        int numberOfIds = rentalCarSearchManager.count(queryMeta);
        queryMeta.setNumberOfIds(numberOfIds);

        List<RentalCarTO> rentalCarTOS = rentalCarSearchManager.find(queryMeta);

        System.out.println("ECHO01 RENTAL CAR:" + rentalCarTOS.size());

        Assertions.assertEquals(0, rentalCarTOS.size());
    }
}