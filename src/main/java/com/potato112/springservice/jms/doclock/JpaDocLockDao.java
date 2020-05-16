package com.potato112.springservice.jms.doclock;

import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.repository.interfaces.crud.CRUDService;
import com.potato112.springservice.repository.interfaces.crud.CRUDServiceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Part doc lock mechanism intended to be used in parallel
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class JpaDocLockDao extends CRUDServiceBean<DocLock> {

    // @Autowired
    // UserLoginManager TODO implemet this


    // TODO implement, but check another impl of DocLockDAO !

    @Autowired
    private CRUDService<String> stringCRUDService;

    /**
     * Creates document lock
     */
    public DocLock lockDocument(DocLock docLock) {

        validateDocLockParameter(docLock);
        removeDocumentLock(docLock);
        return create(docLock);
    }

    public void removeDocumentLock(DocLock docLock) {
        if (!contains(docLock)) {

            List<DocLock> locks = findLocks(docLock);
            for (DocLock lock : locks) {
                delete(lock);
                flush();
            }
            return;
        }
        delete(docLock);
        flush();
    }

    public void unlockDocumentByLogin(String login) {

        EntityManager em = getEntityManager();
        Query deleteByLoginQuery = em.createNamedQuery("DocLock.deleteByLogin");
        deleteByLoginQuery.setParameter("userLogin", login);
        deleteByLoginQuery.executeUpdate();
    }

    public boolean isDocumentLocked(DocLock docLock) {

        validateDocLockParameter(docLock);
        List<DocLock> locks = findLocks(docLock);
        for (DocLock lock : locks) {
            if (lock.getLogin().equals("userLogin from service FIXME")) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean isDocumentLockedWithoutCurrentUserLoginLocksIgnore(DocLock docLock) {

        validateDocLockParameter(docLock);
        List<DocLock> locks = findLocks(docLock);
        return locks.size() > 0;
    }

    private void validateDocLockParameter(DocLock docLock) {

        if (null == docLock) {
            throw new IllegalArgumentException("DocLock parameter is null");
        }

        if (null == docLock.getLogin() || null == docLock.getDocId() || null == docLock.getDocumentType()) {
            String pattern = "DocLock parameter do not contain all requried information. Login %s, docId %s" +
                    "docType %s";
            String msg = String.format(pattern, docLock.getLogin(), docLock.getDocId(), docLock.getDocumentType());
            throw new IllegalArgumentException(msg);
        }
    }

    private List<DocLock> findLocks(DocLock docLock) {

        Map<String, Object> params = new HashMap<>();
        params.put("documentType", docLock.getDocumentType());
        params.put("documentId", docLock.getDocId());
        List<DocLock> locks = findWithNamedQuery("DocLock.findDocLock", params);
        return locks;
    }

    private DocLock findLock(SysDocumentType documentType, String documentId) {

        Map<String, Object> params = new HashMap<>();
        params.put("documentType", documentType);
        params.put("documentId", documentId);
        DocLock locks = findOneWithNamedQuery("DocLock.findDocLock", params);
        return locks;
    }

    public boolean checkDocumentLock(DocLock docLock) {

        validateDocLockParameter(docLock);

        long locks = countLocks(docLock);

        if (locks > 0) {
            return true;
        }
        return false;
    }

    private long countLocks(DocLock docLock) {

        Map<String, Object> params = new HashMap<>();
        params.put("documentType", docLock.getDocumentType());
        params.put("documentId", docLock.getDocId());
        params.put("userLogin", docLock.getLogin());
        return countWithNamedQuery("DocLock.countDocLock", params);
    }

    public void lockDocuments(List<DocLock> docLocks) {

        docLocks.forEach(this::lockDocumentWithoutFlush);
        flush();
    }

    public void unlockDocuments(List<DocLock> docLocks) {

        docLocks.forEach(this::removeDocumentLockWithoutFlush);
        flush();
    }

    private void lockDocumentWithoutFlush(DocLock docLock) {

        validateDocLockParameter(docLock);
        removeDocumentLockWithoutFlush(docLock);
        createNoFlush(docLock);
    }

    private void removeDocumentLockWithoutFlush(DocLock docLock) {

        if (!contains(docLock)) {
            List<DocLock> docLocks = findLocks(docLock);
            docLocks.forEach(this::delete);
            return;
        }
        delete(docLock);
    }

    public void removeAll() {
        Query deleteAllQuery = getEntityManager().createNamedQuery("DocLock.deleteAll");
        deleteAllQuery.executeUpdate();
    }


    // FIXME transaction here? why?
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String findLockingUser(SysDocumentType documentType, String docId) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("documentType", documentType);
        parameters.put("documentId", docId);
        String lockingUser = stringCRUDService.findOneWithNamedQuery("DocLock.findLockingUser", parameters);
        return lockingUser;
    }

}
