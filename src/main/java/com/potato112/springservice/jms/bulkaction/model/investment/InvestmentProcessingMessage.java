package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.repository.entities.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Represents message (info/warning) about single Investment item processing
 */
@Data
@Entity
@Table(schema = "tms", name = "investment_processing_messages")
public class InvestmentProcessingMessage extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "investment_processing_message_id", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "investment_product_id", nullable = false)
    private InvestmentProduct investmentProduct;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "investment_processing_message_type", nullable = false, length = 30)
    private InvestmentProcessingMessageType investmentProcessingMessageType;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
