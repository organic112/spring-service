package com.potato112.springservice.crud.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "getAllRentalCars",
                query = "select rce from RentalCar rce order by rce.brand DESC"
        ),
        @NamedQuery(
                name = "deleteAllRentalCars",
                query = "delete from RentalCar rce"
        ),
        @NamedQuery(
                name = "deleteRentalCarsById",
                query = "delete from RentalCar rce where rce.carId = :carId"
        )
})
@Audited
//@Data
@Entity
@Table(schema = "demo-db", name = "rental_car")
public class RentalCar implements Serializable {

    @Id
    @NotNull
    @Column(name = "car_id", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String carId;
    private String brand;
    private int payloadKG;
    private String color;

    @Column(columnDefinition = "DECIMAL(19,6)") // accuracy of big decimal stored in db
    private BigDecimal pricePerHour;

    @NotAudited
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalCar")
    private List<RentalAgreement> rentalAgreements = new ArrayList<>();

    public String getCarId() {
        return carId;
    }
    public void setCarId(String carId) {
        this.carId = carId;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPayloadKG() {
        return payloadKG;
    }

    public void setPayloadKG(int payloadKG) {
        this.payloadKG = payloadKG;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
