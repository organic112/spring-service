package com.potato112.springservice.jms.bulkaction.services;


import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.init.InvestmentChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionResultManager;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;
import com.potato112.springservice.jms.bulkaction.runners.InvestmentAmortizationBARunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;

@Component
public class InvestmentChangeStatusMDC extends AbstractBulkActionMDC<InvestmentChangeStatusBAInit> {

    private static final String DESTINATION_NAME = "investmentChangeStatusBulkAction";
    private static final String FACTORY_BEAN_NAME = "customFactory";

    // simple  processing runner
    private final InvestmentChangeStatusBARunner investmentChangeStatusBARunner;
    // sophisticated processing runner
    private final InvestmentAmortizationBARunner investmentAmortizationBARunner;

    public InvestmentChangeStatusMDC(BulkActionResultManager bulkActionResultManager, InvestmentAmortizationBARunner investmentAmortizationBARunner,
                                     InvestmentChangeStatusBARunner investmentChangeStatusBARunner) {
        super(bulkActionResultManager);
        this.investmentAmortizationBARunner = investmentAmortizationBARunner;
        this.investmentChangeStatusBARunner = investmentChangeStatusBARunner;
    }

    @JmsListener(destination = DESTINATION_NAME, containerFactory = FACTORY_BEAN_NAME)
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onMessage(Message message) {
        super.onMessage(message);
    }

    @Override
    protected BulkActionsRunResultVo runBulkAction(InvestmentChangeStatusBAInit bulkActionInit) {

        if (bulkActionInit.getTargetStatus().equals(InvestmentStatus.PROCESSED)) {
            System.out.println("ECHO02 received message runs sophisticated processing change status runner");
            return investmentAmortizationBARunner.run(bulkActionInit);
        } else {
            System.out.println("ECHO01 received message runs simple change status runner");
            return investmentChangeStatusBARunner.run(bulkActionInit);
        }
    }
}
