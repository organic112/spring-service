package com.potato112.springservice.search;

import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


//@Entity
@Data
@Table(schema = "demo-db", name = "query_filters")
public class Filter extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "filter_id", length = 80)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String filterId;

    @Column
    private String propertyId;

    @Column(name = "filter_value", length = 2000)
    @Lob
    private Serializable value;

    @Column
    @Enumerated(EnumType.STRING)
    private FilterType filterType;

    @Transient
    private List<? extends Serializable> values;


    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {

    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    public void setValue(Object value) {
        if (value == null) {
            setValue(null);
        } else if (value instanceof Serializable) {
            setValue((Serializable) value);
        } else {
            throw new IllegalStateException("Not serializable object!");
        }
    }

    public Filter clone(){
        Filter filter = new Filter();
        filter.setFilterType(filterType);
        filter.setPropertyId(propertyId);
        filter.setValue(value);
        filter.setValues(values);
        return filter;
    }

}
