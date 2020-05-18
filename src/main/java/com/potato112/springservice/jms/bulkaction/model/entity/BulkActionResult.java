package com.potato112.springservice.jms.bulkaction.model.entity;

import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Bulk Action is operation performed on many objects, but is initiated with one user action.
 * This is entity that represents result single performed bulk action.
 * This entity stores information on a single action already performed
 * and might have involved multiple objects processing in one run.
 */
@Entity
@Table(schema = "demo-db", name = "bulkactions")
public class BulkActionResult extends BaseEntity {

    @Id
    @NotNull
    @Column(name = "bulkactions_id", length = 80)
    @GeneratedValue(generator="system-uuid") // auto generated as String to Varchar
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Enumerated(EnumType.STRING)
    private BulkActionType bulkActionType;

    @Enumerated(EnumType.STRING)
    private BulkActionStatus bulkActionStatus;

    private LocalDateTime startProcessingDateTime;
    private LocalDateTime endProcessingDateTime;
    private String processingDetails;
    private Boolean isDeleted = false;
     //mappedBy = "bulkActionResult",
    @OneToMany(mappedBy = "bulkActionResult", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BulkActionResultMessage> resultMessages = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BulkActionType getBulkActionType() {
        return bulkActionType;
    }

    public void setBulkActionType(BulkActionType bulkActionType) {
        this.bulkActionType = bulkActionType;
    }

    public BulkActionStatus getBulkActionStatus() {
        return bulkActionStatus;
    }

    public void setBulkActionStatus(BulkActionStatus bulkActionStatus) {
        this.bulkActionStatus = bulkActionStatus;
    }

    public LocalDateTime getStartProcessingDateTime() {
        return startProcessingDateTime;
    }

    public void setStartProcessingDateTime(LocalDateTime startProcessingDateTime) {
        this.startProcessingDateTime = startProcessingDateTime;
    }

    public LocalDateTime getEndProcessingDateTime() {
        return endProcessingDateTime;
    }

    public void setEndProcessingDateTime(LocalDateTime endProcessingDateTime) {
        this.endProcessingDateTime = endProcessingDateTime;
    }

    public String getProcessingDetails() {
        return processingDetails;
    }

    public void setProcessingDetails(String processingDetails) {
        this.processingDetails = processingDetails;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<BulkActionResultMessage> getResultMessages() {
        return resultMessages;
    }

    public void setResultMessages(List<BulkActionResultMessage> resultMessages) {
        this.resultMessages = resultMessages;
    }

    @Override
    public void transferStateToPersistentFields(Object stateProvider) {

    }
}
