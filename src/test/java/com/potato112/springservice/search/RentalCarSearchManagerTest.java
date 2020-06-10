package com.potato112.springservice.search;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.crud.jpa.services.RentalCarCRUDService;
import com.potato112.springservice.crud.model.RentalCar;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


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


    @Test
    public void foo() {

        RentalCar rentalCar = new RentalCar();
        rentalCar.setBrand("Fiat");
        rentalCar.setColor("red");
        rentalCar.setPayloadKG(100);
        rentalCar.setCreateUser("testCreateUser");
        System.out.println("before save");
        rentalCarCRUDService.save(rentalCar);
       /* rentalCar.setUpdateUser("testUpdateUser");
        rentalCar.setColor("before yellow");
        rentalCar.setUpdateUser("test-update-user");
        System.out.println("before update");
        rentalCarCRUDService.update(rentalCar);
        rentalCar.setColor("blue");
        System.out.println("before update");
        rentalCarCRUDService.update(rentalCar);
        rentalCar.setColor("green");
        System.out.println("before update");
        rentalCarCRUDService.update(rentalCar);*/

        QueryMeta queryMeta = new QueryMeta();
        Filter filter = new Filter();
        filter.setFilterType(FilterType.EQUALS);
        filter.setPropertyId("color"); // propertyName
        filter.setValue("red");

        queryMeta.setFilters(Arrays.asList(filter));
        queryMeta.setOffset(5);
        queryMeta.setPageSize(5);
        queryMeta.setStartIndex(0);
        queryMeta.setNumberOfIds(1); // this should be provided from count?

        List<RentalCarTO> rentalCarTOS = rentalCarSearchManager.find(queryMeta);

        System.out.println("ECHO01 RENTAL CAR:" + rentalCarTOS.size());
        System.out.println("fetched car brand:" + rentalCarTOS.get(0).getBrand());
    }
}