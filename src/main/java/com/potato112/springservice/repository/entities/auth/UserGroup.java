package com.potato112.springservice.repository.entities.auth;

import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(schema = "demo-db", name = "user_group")
public class UserGroup extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "pk_user_group", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "group_name", length = 200)
    private String groupName;

    @OneToMany(mappedBy = "userGroup")
    private List<UserGroupMapping> userGroups;

    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GroupPermission> groupPermissions;




}
