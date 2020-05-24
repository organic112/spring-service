package com.potato112.springservice.repository.entities.auth;


import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(
                name = "getAllUsers",
                query = "select u from User u order by u.lastLoggedInDate DESC"
        ),
        @NamedQuery(
                name = "deleteAllUserGroups",
                query = "delete from User u"
        ),
        @NamedQuery(
                name = "deleteUserGroupById",
                query = "delete from User u where u.id = :userId"
        )
})
@Audited
@Data
@Entity
@Table(schema = "demo-db", name = "user")
public class User extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "pk_user", length = 80)
    @GeneratedValue(generator = "system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Email
    @Size(max = 60)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @NotNull   //
    @Size(min = 1, max = 128)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @NotNull  //
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
    @NotNull //
    @Enumerated(EnumType.STRING)
    @Column(name = "lock_flag")
    private UserStatus locked;

    @Column(name = "last_loggedin_date")
    private LocalDate lastLoggedInDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserGroupMapping> userGroupMappings = new ArrayList<>();


}
