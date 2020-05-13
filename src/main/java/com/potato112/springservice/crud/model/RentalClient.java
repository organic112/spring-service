package com.potato112.springservice.crud.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "getAllRentalClient",
                query = "select rc from RentalClient rc order by rc.clientId DESC"
        ),
        @NamedQuery(
                name = "deleteAllRentalClient",
                query = "delete from RentalClient rc"
        ),
        @NamedQuery(
                name = "deleteRentalClientById",
                query = "delete from RentalClient rc where rc.clientId = :clientId"
        )
})

@Entity
@Table(schema = "demo-db", name = "rental_client")
public class RentalClient implements Serializable {

    @Id
    @NotNull
    @Column(name = "client_id", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String clientId;

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime clientBirthDate;

    private String surname;
    private String country;
    private String address;

    // storage of some 'big string' (some additional serialized json, xml etc.)
    @Lob
    @Column
    private String veryBigString;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createdDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime updateDateTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rentalClient", cascade = CascadeType.ALL)
    private List<RentalAgreement> rentalAgreements;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getClientBirthDate() {
        return clientBirthDate;
    }

    public void setClientBirthDate(LocalDateTime clientBirthDate) {
        this.clientBirthDate = clientBirthDate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVeryBigString() {
        return veryBigString;
    }

    public void setVeryBigString(String veryBigString) {
        this.veryBigString = veryBigString;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }

    public void setRentalAgreements(List<RentalAgreement> rentalAgreements) {
        this.rentalAgreements = rentalAgreements;
    }
}
