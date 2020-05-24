package com.potato112.springservice.services;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.crud.jpa.services.RentalAgreementCRUDService;
import com.potato112.springservice.crud.jpa.services.RentalCarCRUDService;
import com.potato112.springservice.crud.jpa.services.RentalClientCRUDService;
import com.potato112.springservice.crud.model.RentalAgreement;
import com.potato112.springservice.crud.model.RentalCar;
import com.potato112.springservice.crud.model.RentalClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class CrudServiceTest {

    @Autowired
    private RentalCarCRUDService rentalCarCRUDService;

    @Autowired
    private RentalClientCRUDService rentalClientCRUDService;

    @Autowired
    private RentalAgreementCRUDService rentalAgreementCRUDService;

    /**
     * Creates RentalCars and RentalClients with mapping table RentalAgreements
     * (for db constraints behaviour test purposes)
     */
    @Test
    public void shouldCreateRentalCarAndRentalClientAgreementRecordsInDatabase() {

        clearAllClientCarAgreementRecords();

        // sequence: create car, create client, create mapping record

        // 1) create cars

        RentalCar rentalCar1 = new RentalCar();
        rentalCar1.setBrand("Tesla");
        rentalCar1.setColor("black");
        rentalCar1.setPayloadKG(350);
        rentalCar1.setPricePerHour(new BigDecimal("37.0"));

        RentalCar rentalCar2 = new RentalCar();
        rentalCar2.setBrand("Ford");
        rentalCar2.setColor("red");
        rentalCar2.setPayloadKG(450);
        rentalCar2.setPricePerHour(new BigDecimal("40.0"));

        RentalCar rentalCar3 = new RentalCar();
        rentalCar3.setBrand("Doge");
        rentalCar3.setColor("yellow");
        rentalCar3.setPayloadKG(550);
        rentalCar3.setPricePerHour(new BigDecimal("45.0"));

        // 2) create clients
        RentalClient rentalClient1 = new RentalClient();
        rentalClient1.setClientBirthDate(LocalDateTime.now().minusYears(25));
        rentalClient1.setName("John");
        rentalClient1.setSurname("Doe");

        RentalClient rentalClient2 = new RentalClient();
        rentalClient2.setClientBirthDate(LocalDateTime.now().minusYears(24));
        rentalClient2.setName("Johny");
        rentalClient2.setSurname("Bravo");

        RentalClient rentalClient3 = new RentalClient();
        rentalClient3.setClientBirthDate(LocalDateTime.now().minusYears(28));
        rentalClient3.setName("Tom");
        rentalClient3.setSurname("Maverick");

        RentalClient rentalClient4 = new RentalClient();
        rentalClient4.setClientBirthDate(LocalDateTime.now().minusYears(28));
        rentalClient4.setName("Alicia");
        rentalClient4.setSurname("Smith");

        RentalClient rentalClient5 = new RentalClient();
        rentalClient5.setClientBirthDate(LocalDateTime.now().minusYears(28));
        rentalClient5.setName("Dominica");
        rentalClient5.setSurname("Kale");

        // 3) persist create car and client
        rentalCarCRUDService.save(rentalCar1);
        rentalCarCRUDService.save(rentalCar2);
        rentalCarCRUDService.save(rentalCar3);

        rentalClientCRUDService.save(rentalClient1);
        rentalClientCRUDService.save(rentalClient2);
        rentalClientCRUDService.save(rentalClient3);
        rentalClientCRUDService.save(rentalClient4);
        rentalClientCRUDService.save(rentalClient5);

        // exception impact on persistence (experiments)
/*        if (rentalCar != null) {
            throw new RuntimeException("transaction check");
        }*/

        // dont use car3  and client2

        // 4) create rental agreement
        RentalAgreement rentalAgreement1 = new RentalAgreement();
        rentalAgreement1.setRentalCar(rentalCar1);
        rentalAgreement1.setRentalClient(rentalClient3);
        rentalAgreement1.setValidFrom(LocalDateTime.now());
        rentalAgreement1.setNotes("some notes");

        RentalAgreement rentalAgreement2 = new RentalAgreement();
        rentalAgreement2.setRentalCar(rentalCar1);
        rentalAgreement2.setRentalClient(rentalClient1);
        rentalAgreement2.setValidFrom(LocalDateTime.now());
        rentalAgreement2.setNotes("some notes");

        RentalAgreement rentalAgreement3 = new RentalAgreement();
        rentalAgreement3.setRentalCar(rentalCar2);
        rentalAgreement3.setRentalClient(rentalClient4);
        rentalAgreement3.setValidFrom(LocalDateTime.now());
        rentalAgreement3.setNotes("some notes");

        RentalAgreement rentalAgreement4 = new RentalAgreement();
        rentalAgreement4.setRentalCar(rentalCar1);
        rentalAgreement4.setRentalClient(rentalClient5);
        rentalAgreement4.setValidFrom(LocalDateTime.now());
        rentalAgreement4.setNotes("some notes");

        RentalAgreement rentalAgreement5 = new RentalAgreement();
        rentalAgreement5.setRentalCar(rentalCar2);
        rentalAgreement5.setRentalClient(rentalClient5);
        rentalAgreement5.setValidFrom(LocalDateTime.now());
        rentalAgreement5.setNotes("some notes");

        RentalAgreement rentalAgreement6 = new RentalAgreement();
        rentalAgreement6.setRentalCar(rentalCar2);
        rentalAgreement6.setRentalClient(rentalClient4);
        rentalAgreement6.setValidFrom(LocalDateTime.now());
        rentalAgreement6.setNotes("some notes");

        RentalAgreement rentalAgreement7 = new RentalAgreement();
        rentalAgreement7.setRentalCar(rentalCar1);
        rentalAgreement7.setRentalClient(rentalClient1);
        rentalAgreement7.setValidFrom(LocalDateTime.now());
        rentalAgreement7.setNotes("some notes");

        List<RentalAgreement> rentalAgreements1 = new ArrayList<>();
        rentalAgreements1.add(rentalAgreement1);
        rentalAgreements1.add(rentalAgreement2);
        rentalAgreements1.add(rentalAgreement4);
        rentalAgreements1.add(rentalAgreement7);

        List<RentalAgreement> rentalAgreements2 = new ArrayList<>();
        rentalAgreements2.add(rentalAgreement3);
        rentalAgreements2.add(rentalAgreement5);
        rentalAgreements2.add(rentalAgreement6);

        // 5) add rental agreements by update of Car (new Hibernate session)
        String id1 = rentalCar1.getCarId();
        RentalCar carFromDb1 = rentalCarCRUDService.findById(id1).get();
        carFromDb1.setRentalAgreements(rentalAgreements1);
        //rentalCarCRUDService.update(carFromDb1);

        String id2 = rentalCar2.getCarId();
        RentalCar carFromDb2 = rentalCarCRUDService.findById(id2).get();
        carFromDb2.setRentalAgreements(rentalAgreements2);
       // rentalCarCRUDService.update(carFromDb2);

        // get persisted rental Car
        System.out.println("Check persited car, client, agreement (mapping).");
        System.out.println("rental car from db id" + carFromDb1.getCarId());
        System.out.println("rental car agreements from db size:" + carFromDb1.getRentalAgreements().size());
        System.out.println("TEST END");
    }


    private void clearAllClientCarAgreementRecords() {

        rentalAgreementCRUDService.deleteAll();
        rentalClientCRUDService.deleteAll();
        rentalCarCRUDService.deleteAll();
    }

    //@Test
    public void shouldDeleteRentalCarAndRentalClientAgreementRecordsInDatabase() {

        // if mapping (agreement exists) should throw exception

        Optional<RentalCar> carFromDb = rentalCarCRUDService.findById("2");

        carFromDb.ifPresent(rentalCar -> rentalCarCRUDService.delete(rentalCar));

        Optional<RentalCar> carFromDb2 = rentalCarCRUDService.findById("2");

        System.out.println("After delete, object is present in DB: " + carFromDb2.isPresent());

    }
}






