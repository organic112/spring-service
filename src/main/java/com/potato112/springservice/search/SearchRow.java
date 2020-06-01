package com.potato112.springservice.search;

import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(schema = "demo-db", name = "search_rows")
public class SearchRow extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "search_row_id", length = 80)
    @GeneratedValue(generator = "system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "sorting_number", length = 10)
    private Integer sortingNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "logic_operation", length = 10)
    private LogicOperationType logicOperation;

    @Column(name = "search_field")
    private String searchField;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "search_operator", length = 20)
    private SearchOperator searchOperator;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "search_field_type", length = 20)
    private SearchFieldType searchFieldType;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "search_row_id")
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    private List<SearchValue> searchValues = new ArrayList<>();


}
