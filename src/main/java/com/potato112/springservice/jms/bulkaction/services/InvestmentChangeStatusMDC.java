package com.potato112.springservice.jms.bulkaction.services;


import com.potato112.springservice.jms.bulkaction.model.init.InvestmentChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionResultManager;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;
import com.potato112.springservice.jms.bulkaction.runners.InvestmentAmortizationBARunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class InvestmentChangeStatusMDC extends AbstractBulkActionMDC<InvestmentChangeStatusBAInit> {

    private static final String DESTINATION_NAME = "investmentChangeStatusBulkAction";
    private static final String FACTORY_BEAN_NAME = "customFactory";

    private final InvestmentAmortizationBARunner investmentAmortizationBARunner;

    public InvestmentChangeStatusMDC(BulkActionResultManager bulkActionResultManager, InvestmentAmortizationBARunner investmentAmortizationBARunner) {
        super(bulkActionResultManager);
        this.investmentAmortizationBARunner = investmentAmortizationBARunner;
    }

    //@Autowired FIXME add alternative runner
    //private InvestmentChangeStatusRunner investmentChangeStatusRunner;

    @JmsListener(destination = DESTINATION_NAME, containerFactory = FACTORY_BEAN_NAME)
    @Override
    public void onMessage(Message message) {
        super.onMessage(message);
    }

    @Override
    protected BulkActionsRunResultVo runBulkAction(InvestmentChangeStatusBAInit bulkActionInit) {

        return investmentAmortizationBARunner.run(bulkActionInit);
    }
}
