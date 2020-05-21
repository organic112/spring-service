package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.repository.entities.BaseEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;



// TODO entity structure with interfaces
@NamedQueries({
        @NamedQuery(
                name = "getAllInvestmentDocuments",
                query = "select inv from InvestmentDocument inv order by inv.investmentNumber DESC"
        )
})
@Entity
@Table(schema = "demo-db", name = "investment_header")
public class InvestmentDocument extends BaseEntity implements SysDocument, Lockable {

    @Id
    @NotNull
    @Column(name = "investment_id", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "investment_number", nullable = false, length = 50)
    private String investmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_status", nullable = false, length = 30)
    private InvestmentStatus investmentStatus;

    @OneToMany(mappedBy = "investmentDocument")
    @Cascade(CascadeType.ALL)
    private List<IntInvestmentItem> investmentItemList;

    @Override
    public String getCode() {
        return getInvestmentNumber();
    }

    @Override
    public String getDocumentId() {
        return getId();
    }

    @Override
    @Transient
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvestmentNumber() {
        return investmentNumber;
    }

    public void setInvestmentNumber(String carNumber) {
        this.investmentNumber = carNumber;
    }

    public InvestmentStatus getInvestmentStatus() {
        return investmentStatus;
    }

    public void setInvestmentStatus(InvestmentStatus investmentStatus) {
        this.investmentStatus = investmentStatus;
    }

    public List<IntInvestmentItem> getInvestmentItemList() {
        return investmentItemList;
    }

    public void setInvestmentItemList(List<IntInvestmentItem> investmentItemList) {
        this.investmentItemList = investmentItemList;
    }
}
