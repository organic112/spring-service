package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@NamedQueries({
        @NamedQuery(
                name = "getAllInvestmentItems",
                query = "select inv from IntInvestmentItem inv order by inv.itemNumber DESC"
        )
})
@Entity
@Table(schema = "demo-db", name = "int_investment_item")
public class IntInvestmentItem extends BaseInterfaceTable implements Lockable {

    @Id
    @NotNull
    @Column(name = "int_investment_item_id", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "investment_id")
    private InvestmentDocument investmentDocument;

    @Column(name = "item_number")
    private String itemNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_status", nullable = false, length = 30)
    private InvestmentStatus investmentStatus;

    @Column(name = "product_number")
    private Integer productNumber;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public InvestmentStatus getInvestmentStatus() {
        return investmentStatus;
    }

    public void setInvestmentStatus(InvestmentStatus investmentStatus) {
        this.investmentStatus = investmentStatus;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer clientNumber) {
        this.productNumber = clientNumber;
    }

    @Override
    public String getCode() {

        String code = itemNumber;
        Integer clientNumber = getProductNumber();
        return code.concat(" - ").concat(clientNumber.toString());
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

    public InvestmentDocument getInvestmentDocument() {
        return investmentDocument;
    }

    public void setInvestmentDocument(InvestmentDocument investmentDocument) {
        this.investmentDocument = investmentDocument;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }
}
