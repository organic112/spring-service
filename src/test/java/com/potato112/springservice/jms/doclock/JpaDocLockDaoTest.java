package com.potato112.springservice.jms.doclock;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class JpaDocLockDaoTest {

    @Autowired
    private JpaDocLockDao jpaDocLockDao;

    private DocLock docLock;

    @Before
    public void init() {
        docLock = new DocLock();
        docLock.setDocumentType(SysDocumentType.INVESTMENT_DOCUMENT);
        docLock.setDocId("document_id");
        docLock.setDocCode("document_code");
        docLock.setLogin("test_user_login");
    }

    @Test
    public void shouldCreateDocumentLock() {
        // given
        // when
        jpaDocLockDao.lockDocument(docLock);
        // then
        Boolean isLocked = jpaDocLockDao.isDocumentLocked(docLock);
        System.out.println("isLocked:" + isLocked);
        Assertions.assertEquals(true, isLocked);
    }

    @Test
    public void shouldNotFindDocumentLock() {
        // given
        DocLock docLock = new DocLock();
        docLock.setDocumentType(SysDocumentType.INVESTMENT_DOCUMENT);
        docLock.setDocId("not_existing_document_id");
        docLock.setDocCode("document_code");
        docLock.setLogin("test_user_login");
        // when
        // then
        Boolean isLocked = jpaDocLockDao.isDocumentLocked(docLock);
        System.out.println("isLocked:" + isLocked);
        Assertions.assertEquals(false, isLocked);
    }

    @Test
    public void shouldFindDocumentLock() {
        // given
        jpaDocLockDao.lockDocument(docLock);
        // when
        boolean isLocked = jpaDocLockDao.isDocumentLocked(docLock);
        System.out.println("isLocked:" + isLocked);
        // then
        Assertions.assertEquals(true, isLocked);
    }

    @Test
    public void shouldRemoveDocumentLock() {
        // given
        jpaDocLockDao.lockDocument(docLock);
        boolean isLocked = jpaDocLockDao.isDocumentLocked(docLock);
        Assertions.assertEquals(true, isLocked);
        System.out.println("isLocked:" + isLocked);
        // when
        jpaDocLockDao.removeDocumentLock(docLock);
        // then
        boolean isLockedAfterRemove = jpaDocLockDao.isDocumentLocked(docLock);
        Assertions.assertEquals(false, isLockedAfterRemove);
        System.out.println("isLocked:" + isLocked);
    }
}