package com.potato112.springservice.crud.jdbc.services;

import com.potato112.springservice.crud.jdbc.model.BusinessRuleException;
import com.potato112.springservice.crud.jdbc.model.jdbclock.DocLockVO;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Represents Document Lock Data access object (with usage of JDBC)
 */
@Repository
public class JdbcDocLockDao extends BaseDAO<DocLockVO> {

    // TODO move proper column names to constants
    private static final String LOGIN_COLUMN = "LOGIN";

    @Override
    public DocLockVO resultSetToVO(ResultSet resultSet) throws SQLException {

        // FIXME conversion of LocalDateTime

        DocLockVO docLockVO = new DocLockVO();
        docLockVO.setId(resultSet.getString("DOC_LOCKS_ID"));
        // FIXME get resultSet.get(....)
        docLockVO.setCreateDate(LocalDateTime.now());
        docLockVO.setCreateUser(resultSet.getString("CREATE_USER"));
        docLockVO.setUpdateDate(LocalDateTime.now());
        // FIXME get resultSet.get(....)
        docLockVO.setUpdateUser(resultSet.getString("UPDATE_USER"));
        docLockVO.setDocumentCode(resultSet.getString("DOC_CODE"));
        docLockVO.setDocumentId(resultSet.getString("DOC_ID"));
        docLockVO.setSysDocumentType(SysDocumentType.valueOf(resultSet.getString("DOC_TYPE")));
        docLockVO.setLogin(resultSet.getString(LOGIN_COLUMN));

        return docLockVO;
    }

    public DocLockVO getDocLock(Connection connection, String documentId) throws SQLException, BusinessRuleException {

        String sql = getSQLQuery("select.docLock");
        DocLockVO docLockVO = selectOne(connection, sql, documentId);
        return docLockVO;
    }

    public void insertDocLock(Connection connection, DocLockVO docLockVO) throws SQLException, BusinessRuleException {

        String sql = getSQLQuery("insert.docLock");
        executeInsert(connection, sql, docLockVO.getCreateDate(), docLockVO.getDocumentCode(), docLockVO.getDocumentId(),
                docLockVO.getSysDocumentType().name());
    }

    public void deleteDocLock(Connection connection, String documentId) throws SQLException, BusinessRuleException {

        String sql = getSQLQuery("delete.docLock");
        executeDelete(connection, sql, documentId);
    }
}
