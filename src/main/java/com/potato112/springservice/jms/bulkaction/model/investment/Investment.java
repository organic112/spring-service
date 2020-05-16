package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

// TODO entity structure with interfaces

@Entity
@Table(schema = "demo-db", name = "investment")
public class Investment extends BaseEntity implements SysDocument, Lockable {

    @Id
    @NotNull
    @Column(name = "investment_id", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "investment_number", nullable = false, length = 50)
    private String InvestmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_status", nullable = false, length = 30)
    private InvestmentStatus investmentStatus;

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
        return InvestmentNumber;
    }

    public void setInvestmentNumber(String carNumber) {
        this.InvestmentNumber = carNumber;
    }

    public InvestmentStatus getInvestmentStatus() {
        return investmentStatus;
    }

    public void setInvestmentStatus(InvestmentStatus investmentStatus) {
        this.investmentStatus = investmentStatus;
    }

}
