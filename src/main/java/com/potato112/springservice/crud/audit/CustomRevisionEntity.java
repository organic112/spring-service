package com.potato112.springservice.crud.audit;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;


/**
 * Customizes Hibernate Audit (entity history) revision table
 */
@Entity
@RevisionEntity(CustomRevisionEntityListener.class)
@Getter @Setter
@Table(name = "REVINFO", schema = "audit")
@AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "REVTSTMP")),
        @AttributeOverride(name = "id", column = @Column(name = "REV"))
})
public class CustomRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "user_name", nullable = false)
    private String userName;
}
