package com.potato112.springservice.jms.bulkaction.model.investment;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Represents Investment product
 */
@Data
@Entity
@Table(schema = "demo-db", name = "investment_product")
public class InvestmentProduct extends BaseInterfaceTable {

    @Id
    @NotNull
    @Column(name = "investment_product_id", length = 80)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "product_number", nullable = false)
    private String productNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "investment_processing_message_type", nullable = false, length = 30)
    private InvestmentProductStatus investmentProductStatus;

    @Column(name = "isValidFlag", nullable = false)
    private Boolean isValidFlag = true;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "int_investment_item_id")
    private IntInvestmentItem intInvestmentItem;

    @OneToMany(mappedBy = "investmentProduct", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvestmentProcessingMessage> processingMessages;
}
