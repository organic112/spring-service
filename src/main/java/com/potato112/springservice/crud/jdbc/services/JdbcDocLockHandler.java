package com.potato112.springservice.crud.jdbc.services;

import com.potato112.springservice.crud.jdbc.model.BusinessRuleException;
import com.potato112.springservice.crud.jdbc.model.DocLockException;
import com.potato112.springservice.crud.jdbc.model.RentalAgreementVO;
import com.potato112.springservice.crud.jdbc.model.jdbclock.DocLockVO;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Executes locking logic on Document locks, using locks DAO
 * In this example locking logic is related to rentalAgreementVO, that is mapping object.
 */
public class JdbcDocLockHandler {

    private JdbcDocLockDao jdbcDocLockDao;

    public JdbcDocLockHandler(JdbcDocLockDao jdbcDocLockDao) {
        this.jdbcDocLockDao = jdbcDocLockDao;
    }

    /**
     * Example of locking logic (tries to create locks on all objects related to mappings List<RentalAgreementVO>).
     * RentalAgreement in this case is a mapping object.
     */
    public void lockDocuments(Connection connection, List<RentalAgreementVO> rentalAgreementVOList) throws SQLException, DocLockException, BusinessRuleException {

        List<String> rentalCarIdList = rentalAgreementVOList.stream().map(RentalAgreementVO::getRentalCarId).distinct().collect(Collectors.toList());
        List<String> rentalClientIdList = rentalAgreementVOList.stream().map(RentalAgreementVO::getRentalClientId).collect(Collectors.toList());

        checkLocks(connection, rentalCarIdList);
        checkLocks(connection, rentalClientIdList);

        for (String id : rentalCarIdList) {
            createDocLock(connection, id, SysDocumentType.CAR_DOCUMENT);
        }
        for (String id : rentalClientIdList) {
            createDocLock(connection, id, SysDocumentType.CLIENT_DOCUMENT);
        }
    }

    public void removeDocumentLocks(Connection connection, List<RentalAgreementVO> rentalAgreementVOList) throws SQLException, BusinessRuleException {

        for (RentalAgreementVO rentalAgreementVO : rentalAgreementVOList) {
            jdbcDocLockDao.deleteDocLock(connection, rentalAgreementVO.getRentalCarId());
            jdbcDocLockDao.deleteDocLock(connection, rentalAgreementVO.getRentalClientId());
        }
    }

    private void checkLocks(Connection connection, List<String> documentIdList) throws SQLException, DocLockException, BusinessRuleException {

        for (String docId : documentIdList) {
            DocLockVO docLock = jdbcDocLockDao.getDocLock(connection, docId);

            if (docLock != null) {
                String user = docLock.getCreateUser();
                SysDocumentType documentType = docLock.getSysDocumentType();
                String lockedDocType = documentType.toString();
                String LockedExceptionMessage = String.format("%s is locked by user: %s", lockedDocType.toUpperCase(), user);
                throw new DocLockException(LockedExceptionMessage);
            }
        }
    }

    private void createDocLock(Connection connection, String id, SysDocumentType documentType) throws SQLException,
            BusinessRuleException {

        // document code is not used in simple case
        DocLockVO docLockVO = createDocLock(documentType, "FIXME code", id);
        jdbcDocLockDao.insertDocLock(connection, docLockVO);
    }

    private DocLockVO createDocLock(SysDocumentType documentType, String documentCode, String documentId) {

        DocLockVO docLockVO = new DocLockVO();
        docLockVO.setSysDocumentType(documentType);
        docLockVO.setDocumentCode(documentCode);
        docLockVO.setDocumentId(documentId);
        return docLockVO;
    }
}
