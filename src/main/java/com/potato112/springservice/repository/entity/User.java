package com.potato112.springservice.repository.entity;


import com.potato112.springservice.domain.user.UserStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(schema = "demo-db", name = "user")
public class User implements Serializable {

    // TODO add annotaions
    @Id
    @NotNull
    @Column(name = "pk_user", length = 80)
/*    @GeneratedValue(generator = "seq_id")
    @GenericGenerator(name = "seq_id", strategy = "identity")*/
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Email
    @Size(max = 60)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 128)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    //@NotNull
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
   // @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "lock_flag")
    private UserStatus locked;

    @Column(name = "last_loggedin_date")
    private LocalDate lastLoggedInDate;

    // TODO
    //private List<UserGroup> userGroups;


}
