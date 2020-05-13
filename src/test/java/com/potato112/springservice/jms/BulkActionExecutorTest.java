package com.potato112.springservice.jms;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.jms.bulkaction.BulkActionExecutor;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.init.InvestmentChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionManager;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class BulkActionExecutorTest {

    @Autowired
    private BulkActionExecutor bulkActionExecutor;

    @Autowired
    private BulkActionManager bulkActionInitiator;

    /**
     * should send and receive bulk action message
     */
    @Test
    public void shouldRunInvestmentChangeStatusBulkAction() {

        SysStatus targetStatus = InvestmentStatus.PROCESSED;
        Set<String> documentIds = new HashSet<>();
        documentIds.add("1");
        documentIds.add("2");
        documentIds.add("3");
        String cancelationMessage = "";
        String loggedUser = "testUserFromExecutor";
        BulkActionInit bulkActionInit = new InvestmentChangeStatusBAInit(targetStatus, documentIds, cancelationMessage, loggedUser);

        bulkActionInitiator.initiateBulkAction(bulkActionInit);
    }

    /**
     * should send bulk action message
     */
    @Test
    public void shouldRunExecutorService(){
        bulkActionExecutor.executeBulkAction();
    }
}