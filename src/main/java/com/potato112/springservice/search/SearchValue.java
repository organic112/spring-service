package com.potato112.springservice.search;

import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Holds single comparing value for search row in adv. search
 */
@Data
@Entity
@Table(schema = "demo-db", name = "search_values")
public class SearchValue extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "search_value_id", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "value_position")
    private Integer position;

    @Column(name = "search_position")
    private String value;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

}
