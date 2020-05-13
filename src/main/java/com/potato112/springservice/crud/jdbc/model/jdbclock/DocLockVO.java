package com.potato112.springservice.crud.jdbc.model.jdbclock;

import com.potato112.springservice.crud.jdbc.model.BaseVO;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;

import java.time.LocalDateTime;

/**
 * Stores document lock in database
 */
public class DocLockVO extends BaseVO {

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String createUser;
    private String updateUser;
    private String documentCode;
    private String documentId;
    private SysDocumentType sysDocumentType;
    private String login;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public SysDocumentType getSysDocumentType() {
        return sysDocumentType;
    }

    public void setSysDocumentType(SysDocumentType sysDocumentType) {
        this.sysDocumentType = sysDocumentType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
