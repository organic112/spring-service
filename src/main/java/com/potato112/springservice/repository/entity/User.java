package com.potato112.springservice.repository.entity;


import com.potato112.springservice.domain.user.UserStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {


    // TODO add annotaions

    @Id
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "pk_user")
    @GeneratedValue(generator = "fixme")
    @GenericGenerator(name = "", strategy = "")
    private String id;

    @Email
    @Size(max = 60)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 50)
    @Column(name = "phone")
    private String phone;

    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lock_flag")
    private UserStatus locked;

    @Column(name = "last_loggedin_date")
    private LocalDate lastLoggedInDate;

    //private List<UserOrganization> userOrganizations;


}
