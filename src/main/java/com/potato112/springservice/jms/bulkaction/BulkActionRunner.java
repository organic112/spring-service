package com.potato112.springservice.jms.bulkaction;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.init.InvestmentChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionManager;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope("prototype")
public class BulkActionRunner implements Runnable {
    @Autowired
    private BulkActionManager bulkActionInitiator;

        @Override
        public void run() {
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
}
