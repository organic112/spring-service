package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Represents row on overview table
 * (row selected with checkbox is send for BA processing)
 */
@Data
@Entity
@Table(schema = "demo-db", name = "int_investment_item")
@NamedQueries({
        @NamedQuery(
                name = "getAllInvestmentItems",
                query = "select inv from IntInvestmentItem inv order by inv.itemNumber DESC"
        )
})
public class IntInvestmentItem extends BaseInterfaceTable implements Lockable {

    @Id
    @NotNull
    @Column(name = "int_investment_item_id", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "item_number")
    private String itemNumber;

    @Column(name = "product_number")
    private Integer variant;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_status", nullable = false, length = 30)
    private InvestmentStatus investmentStatus;

    @ManyToOne
    @JoinColumn(name = "investment_id")
    private InvestmentDocument investmentDocument;

    @OneToMany(mappedBy = "intInvestmentItem", fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    private List<InvestmentProduct> investmentProducts;

    @Override
    public String getCode() {
        String code = itemNumber;
        Integer variant = getVariant();
        return code.concat(" - ").concat(variant.toString());
    }

    @Override
    public String getDocumentId() {
        return id;
    }

    @Override
    public SysDocumentType getDocumentType() {
        return SysDocumentType.INVESTMENT_DOCUMENT;
    }

    @Override
    public boolean isEditable() {
        return false;
    }

    @Override
    public void setUpdateUser() {
    }
}
