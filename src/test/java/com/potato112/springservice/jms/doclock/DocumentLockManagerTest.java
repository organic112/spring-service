package com.potato112.springservice.jms.doclock;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.Investment;
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
    private CRUDServiceBean<Investment> crudServiceBean;

    @Autowired
    private DocumentLockManager documentLockManager;

    @Before
    public void init() {
    }

    @Test
    public void shouldCreateDocumentLock() {
        // given
        Investment investment = new Investment();
        investment.setInvestmentNumber("INV001-TEST01");
        investment.setInvestmentStatus(InvestmentStatus.IMPORTED);
        crudServiceBean.create(investment);
        System.out.println("document id:" + investment.getDocumentId());
        // when
        DocLock docLock = documentLockManager.lockDocument(investment);
        boolean isLocked = documentLockManager.isDocumentLocked(investment);
        // then
        Assertions.assertNotEquals(null, docLock.getId());
        Assertions.assertNotEquals(false, isLocked);
    }

    @Test
    public void shouldCreateAndRemoveDocumentLock() {
        // given
        Investment investment = new Investment();
        investment.setInvestmentNumber("INV001-TEST01");
        investment.setInvestmentStatus(InvestmentStatus.IMPORTED);
        crudServiceBean.create(investment);
        // when
        DocLock docLock = documentLockManager.lockDocument(investment);
        Assertions.assertNotEquals(null, docLock);
        documentLockManager.unlockDocument(investment);
        // then
        boolean isLocked = documentLockManager.isDocumentLocked(investment);
        Assertions.assertEquals(false, isLocked);
    }

    @Test(expected = AlreadyLockedException.class)
    public void shouldThrowAlreadyLockExceptionWhenSecondLockAttemptOnLockedDocument() {
        // given
        Investment investment = new Investment();
        investment.setInvestmentNumber("INV001-TEST01");
        investment.setInvestmentStatus(InvestmentStatus.IMPORTED);
        crudServiceBean.create(investment);
        // when
        System.out.println("document id:" + investment.getDocumentId());
        DocLock docLock = documentLockManager.lockDocument(investment);
        System.out.println("docLock: " + docLock.getDocId() + " - " + docLock.getLogin());
        // then (second attempt on lock)
        documentLockManager.lockDocument(investment);
        Assertions.assertNotEquals(null, docLock.getId());
    }
}