package com.potato112.springservice.repository.entities.auth;


import com.potato112.springservice.repository.entities.auth.UserGroup;
import com.potato112.springservice.repository.entities.auth.ViewName;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@Table(schema = "demo-db", name = "group_permission")
public class GroupPermission {

    @Id
    @NotNull
    @Column(name = "pk_permission", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    String id;

    @ManyToOne
    private UserGroup userGroup;

    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private ViewName viewName;

    @Column(name = "can_create")
    private boolean canCreate;

    @Column(name = "can_update")
    private boolean canUpdate;

    @Column(name = "can_delete")
    private boolean canDelete;
}