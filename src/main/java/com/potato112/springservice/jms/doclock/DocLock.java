package com.potato112.springservice.jms.doclock;

import com.potato112.springservice.jms.bulkaction.model.entity.BaseEntity;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(schema = "demo-db", name = "doc_locks")
@NamedQueries({
        @NamedQuery(name = "DocLock.findDocLock", query = "select d from DocLock d " +
                "where d.documentType = :documentType and d.docId = :documentId"),
        @NamedQuery(name = "DocLock.findLockingUser", query = "select d.login from DocLock d " +
                "where d.documentType = :documentType and d.docId = :documentId"),
        @NamedQuery(name = "DocLock.countDocLock", query = "select count(d.id) from DocLock d " +
                "where d.documentType = :documentType and d.docId = :documentId and d.login = :userLogin"),
        @NamedQuery(name = "DocLock.findDocLocksForLogin", query = "select d from DocLock d where d.login = :userLogin"),
        @NamedQuery(name = "DocLock.deleteByLogin", query = "delete from DocLock d where d.login = :userLogin"),
        @NamedQuery(name = "DocLock.deleteAll", query = "delete from DocLock d"),
})
public class DocLock extends BaseEntity {

    @Id
    @Column(name = "doc_locks_id", length = 50)
    @GeneratedValue(generator = "seq_id")
    @GenericGenerator(name = "seq_id", strategy = "identity")
    private String id;

    @Column(nullable = false)
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", nullable = false, length = 30)
    private SysDocumentType documentType;

    @Column(name = "doc_id", nullable = false, length = 30)
    private String docId;

    @Column(name = "doc_code", nullable = false, length = 50)
    private String docCode;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public SysDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(SysDocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
