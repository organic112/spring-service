package com.potato112.springservice.jms.doclock;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
import com.potato112.springservice.repository.interfaces.crud.CRUDServiceBean;
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
public class DocumentLockManagerTest {

    @Autowired
    private CRUDServiceBean<InvestmentDocument> crudServiceBean;

    @Autowired
    private DocumentLockManager documentLockManager;

    @Before
    public void init() {
    }

    @Test
    public void shouldCreateDocumentLock() {
        // given
        InvestmentDocument investmentDocument = new InvestmentDocument();
        investmentDocument.setCreateUser("app-context-user");
        investmentDocument.setInvestmentNumber("INV001-TEST01");
        investmentDocument.setInvestmentStatus(InvestmentStatus.IMPORTED);
        crudServiceBean.create(investmentDocument);
        System.out.println("document id:" + investmentDocument.getDocumentId());
        // when
        DocLock docLock = documentLockManager.lockDocument(investmentDocument);
        boolean isLocked = documentLockManager.isDocumentLocked(investmentDocument);
        // then
        Assertions.assertNotEquals(null, docLock.getId());
        Assertions.assertNotEquals(false, isLocked);
    }

    @Test
    public void shouldCreateAndRemoveDocumentLock() {
        // given
        InvestmentDocument investmentDocument = new InvestmentDocument();
        investmentDocument.setCreateUser("app-context-user");
        investmentDocument.setInvestmentNumber("INV001-TEST01");
        investmentDocument.setInvestmentStatus(InvestmentStatus.IMPORTED);
        crudServiceBean.create(investmentDocument);
        // when
        DocLock docLock = documentLockManager.lockDocument(investmentDocument);
        Assertions.assertNotEquals(null, docLock);
        documentLockManager.unlockDocument(investmentDocument);
        // then
        boolean isLocked = documentLockManager.isDocumentLocked(investmentDocument);
        Assertions.assertEquals(false, isLocked);
    }

    @Test(expected = AlreadyLockedException.class)
    public void shouldThrowAlreadyLockExceptionWhenSecondLockAttemptOnLockedDocument() {
        // given
        InvestmentDocument investmentDocument = new InvestmentDocument();
        investmentDocument.setCreateUser("app-context-user");
        investmentDocument.setInvestmentNumber("INV001-TEST01");
        investmentDocument.setInvestmentStatus(InvestmentStatus.IMPORTED);
        crudServiceBean.create(investmentDocument);
        // when
        System.out.println("document id:" + investmentDocument.getDocumentId());
        DocLock docLock = documentLockManager.lockDocument(investmentDocument);
        System.out.println("docLock: " + docLock.getDocId() + " - " + docLock.getLogin());
        // then (second attempt on lock)
        documentLockManager.lockDocument(investmentDocument);
        Assertions.assertNotEquals(null, docLock.getId());
    }
}