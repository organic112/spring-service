package com.potato112.springservice.search.model.query.advanced;

import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * Stores Advanced logical search filtering meta information
 */
//@Entity
@Data
@Table(schema = "demo-db", name = "saved_search")
public class SavedAdvSearch extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "saved_search_id", length = 80)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "search_object_type", length = 30)
    private SearchObjectType objectType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "saved_search_id")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<SearchRow> searchRows = new ArrayList<>();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
