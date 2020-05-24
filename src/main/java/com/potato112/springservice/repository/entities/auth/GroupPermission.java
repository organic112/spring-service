package com.potato112.springservice.repository.entities.auth;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Audited
@Data
@Entity
@Table(schema = "demo-db", name = "group_permission")
public class GroupPermission {

    @Id
    @NotNull
    @Column(name = "pk_permission", length = 80)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    String id;

    @ManyToOne
    @JoinColumn(name = "group_id")
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
