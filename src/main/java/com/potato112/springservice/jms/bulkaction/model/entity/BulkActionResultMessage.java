package com.potato112.springservice.jms.bulkaction.model.entity;

import ch.qos.logback.core.boolex.EvaluationException;
import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "demo-db", name = "bulkactions_results")
public class BulkActionResultMessage extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "bulkactions_results_id", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "bulkactions_id")
    private BulkActionResult bulkActionResult;

    @Enumerated(EnumType.STRING)
    private BulkActionStatus bulkActionStatus;

    @Column(name = "message_content", length = 1000)
    private String messageContent;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public BulkActionResult getBulkActionResult() {
        return bulkActionResult;
    }

    public void setBulkActionResult(BulkActionResult bulkActionResult) {
        this.bulkActionResult = bulkActionResult;
    }

    public BulkActionStatus getBulkActionStatus() {
        return bulkActionStatus;
    }

    public void setBulkActionStatus(BulkActionStatus bulkActionStatus) {
        this.bulkActionStatus = bulkActionStatus;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
