package com.potato112.springservice.repository.entities.auth;


import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * maps current User to group
 * if user is mapped to group, he grants group authorities
 */

@Audited
@Data
@Entity
@Table(schema = "demo-db", name = "user_group_mapping")
public class UserGroupMapping extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "pk_user_group_mapping", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup userGroup;


}




