package com.potato112.springservice.jms.doclock;


import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.Lockable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class DocumentLockManager {

    @Autowired
    private JpaDocLockDao docLockDao;

    // FIXME
    // @Autowired
    // private UserLoginManager userLoginManager;

    /**
     * Creates lock with new transaction
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DocLock lockDocument(Lockable lockable) {

        return lockDocumentWithoutNewTransaction(lockable);
    }

    private DocLock lockDocumentWithoutNewTransaction(Lockable lockable) {

        DocLock docLock = createDocLock(lockable.getDocumentId(), lockable.getDocumentType(), lockable.getCode());

        if (docLockDao.isDocumentLocked((docLock))) {
            throwAlreadyLockedException(docLock.getDocumentType(), docLock.getDocId());
        }
        docLockDao.lockDocument(docLock);
        return docLock;
    }

    private void throwAlreadyLockedException(SysDocumentType sysDocumentType, String docId) {

        String lockingUserLogin = docLockDao.findLockingUser(sysDocumentType, docId);
        String message = String.format("Document id: %s already locked by: %s", docId, lockingUserLogin);
        throw new AlreadyLockedException(message, lockingUserLogin);
    }

    private DocLock createDocLock(String documentId, SysDocumentType documentType, String docCode) {

        validateCaller();
        DocLock docLock = new DocLock();
        docLock.setLogin("Fixme_mocked_login");
        docLock.setDocId(documentId);
        docLock.setDocumentType(documentType);
        docLock.setDocCode(docCode);
        return docLock;
    }

    private void validateCaller() {

        // FIXME
        /*
        if (null == userLoginManager.getLoggedUser()) {
            throw new SecurityException("Not authorized user tries lock document!");
        }
        */
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void unlockDocument(Lockable lockable) {

        if (null == lockable) {
            log.info("Document is null and cannot be locked" +lockable.getDocumentId());
            return;
        }
        DocLock docLock = createDocLock(lockable.getDocumentId(), lockable.getDocumentType());
        docLockDao.removeDocumentLock(docLock);
    }

    private DocLock createDocLock(String documentId, SysDocumentType documentType) {
        return createDocLock(documentId, documentType, null);
    }

    public boolean isDocumentLocked(Lockable lockable) {
        DocLock docLock = createDocLock(lockable.getDocumentId(), lockable.getDocumentType());
        return docLockDao.isDocumentLocked(docLock);
    }


}
