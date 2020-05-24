package com.potato112.springservice.jms;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.jms.bulkaction.BulkActionExecutor;
import com.potato112.springservice.jms.bulkaction.dao.InvestmentDao;
import com.potato112.springservice.jms.bulkaction.model.entity.BulkActionResult;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.init.InvestmentChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionManager;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
import com.potato112.springservice.repository.interfaces.crud.CRUDServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.net.ssl.SSLEngineResult;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;

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

    @Autowired
    private InvestmentDao investmentDao;

    private CRUDServiceBean<BulkActionResult> bulkActionResult;

    /*
    BULK ACTION EXECUTION FLOW (sophisticated processing path)
    1. BulkActionExecutor (this service may represent UI component or API endpoint - triggering BA)
        - with ExecutorService (provided java concurrent executor)
        - with BulkActionRunner
		    - creates BulkActionInit
		    - uses BulkActionManager and initiates bulk action

    2. BulkActionManager
	    - initiates bulk action (BulkActionInit, JMS destinationName)
	    - creates bulk action result in database
	    - sends BA message to JMS:
		    - message contains database resultId
		    - provides BA type
		    - provides init user login

    3a.(ASYNC) dedicated destination MDC(Message Driven Component) e.g. InvestmentChangeStatusMDC
	    - recives BA message
	    - uses dedicated Runner e.g. InvestmentAmortizationBARunner
	    - using runner executes run on BulkActionInit from message
	    (returns BulkActionsRunResultVo contains List<BulkActionFutureResultVo>)

    3b.(ASYNC) dedicated Runner e.g. InvestmentAmortizationBARunner (processes single BA item)
	    - fetches Investment object form DB wrapped in IntInvestmentItem
	    - uses InvestmentStatusManager to change status

    3c.(ASYNC) InvestmentStatusManager
	    - executes InvestmentAmortizationProcessor (transaction Propagation.NOT_SUPPORTED)
		    - executes ProductProcrssor.processProduct()  (transaction Propagation.REQUIRES_NEW)
			    - may be stopped by bussiness Exceptions (transaction rollback)
			    - bussines Exceptions are handled and result is fetched
			    - based on result BA exception may be rethrown

    4. BulkActionManager collects BA results
	    - gets BulkActionResults from DAO by Id
	    - gets data from BulkActionsRunResultVo
	    - updates BulkActionResults with BulkActionsRunResultVo(BulkActionFutureResultVo list)
	    - persists updated BulkActionResults in database
     */
    @Test
    public void shouldRunSophisticatedInvestmentChangeStatusBulkAction() {

        SysStatus targetStatus = InvestmentStatus.PROCESSED;
        // selected ids
        Set<String> selectedDocumentIds = new HashSet<>();
        List<IntInvestmentItem> investmentDocumentList = investmentDao.getAllInvestmentItems();
        investmentDocumentList.forEach(inv -> selectedDocumentIds.add(inv.getId()));
        selectedDocumentIds.add("not_existing"); // this will fail

        String cancelationMessage = "";
        String loggedUser = "testUserFromExecutor";
        BulkActionInit bulkActionInit = new InvestmentChangeStatusBAInit(targetStatus, selectedDocumentIds, cancelationMessage, loggedUser);

        bulkActionInitiator.initiateBulkAction(bulkActionInit);
    }

    /**
     * TODO simple BA status change
     */
    @Test
    public void shouldRunSimpleInvestmentChangeStatusBulkAction() {

        SysStatus targetStatus = InvestmentStatus.CLOSED;
        Set<String> selectedDocumentIds = new HashSet<>();
        List<IntInvestmentItem> investmentDocumentList = investmentDao.getAllInvestmentItems();
        investmentDocumentList.forEach(inv -> selectedDocumentIds.add(inv.getId()));

        String cancelationMessage = "";
        String loggedUser = "testUserFromExecutor";

        // FIXME Changle to simple bulk action
        BulkActionInit bulkActionInit = new InvestmentChangeStatusBAInit(targetStatus, selectedDocumentIds, cancelationMessage, loggedUser);
        bulkActionInitiator.initiateBulkAction(bulkActionInit);
    }

    @Test
    public void shouldRunSeveralSimpleInvestmentChangeStatusBulkActionsInParallel() {

        SysStatus targetStatus = InvestmentStatus.CLOSED;
        List<IntInvestmentItem> investmentDocumentList = investmentDao.getAllInvestmentItems();
        Set<String> set1 = new HashSet<>();
        set1.add(investmentDocumentList.get(0).getId());

        Set<String> set2 = new HashSet<>();
        set2.add(investmentDocumentList.get(1).getId());

        Set<String> set3 = new HashSet<>();
        set3.add(investmentDocumentList.get(2).getId());

        Set<String> set4 = new HashSet<>();
        set4.add(investmentDocumentList.get(3).getId());

        Set<String> set5 = new HashSet<>();
        set5.add(investmentDocumentList.get(4).getId());

        String cancelationMessage = "";
        String loggedUser = "testUserFromExecutor";

        // FIXME Changle to simple bulk action

        // adds several times the same Id to execute in async action in parallel on same object and throw lock error
        BulkActionInit bulkActionInit1 = new InvestmentChangeStatusBAInit(targetStatus, set1, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit2 = new InvestmentChangeStatusBAInit(targetStatus, set2, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit3 = new InvestmentChangeStatusBAInit(targetStatus, set3, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit4 = new InvestmentChangeStatusBAInit(targetStatus, set4, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit5 = new InvestmentChangeStatusBAInit(targetStatus, set5, cancelationMessage, loggedUser);

        // fixme JMS MDB is closed before process finishes on message process
        bulkActionInitiator.initiateBulkAction(bulkActionInit1);
        bulkActionInitiator.initiateBulkAction(bulkActionInit2);
        bulkActionInitiator.initiateBulkAction(bulkActionInit3);
        bulkActionInitiator.initiateBulkAction(bulkActionInit4);
        bulkActionInitiator.initiateBulkAction(bulkActionInit5);

        // waits with 'test shutdown' until condition is true
        // in this case all async results (of JMS on message processes) are inserted to database
        await().until(() ->
        {
            return investmentDao.getAllInvestmentItems().stream()
                    .filter(item -> item.getInvestmentStatus().equals(InvestmentStatus.CLOSED)).count() >= 5;
        });
    }


    @Test
    public void doNothingInTestJustForAppConfigExecutionReference() {

    }

    /**
     * should send bulk action message
     */
    @Test
    public void shouldRunExecutorService() {
        bulkActionExecutor.executeBulkAction();
    }
}