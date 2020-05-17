package com.potato112.springservice.jms.bulkaction.services;

import com.potato112.springservice.jms.bulkaction.dao.InvestmentDao;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.interfaces.StatusManager;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.runners.AsyncStatusChanger;
import com.potato112.springservice.jms.bulkaction.runners.ChangeStatusBARunner;
import org.springframework.stereotype.Component;

@Component
public class InvestmentChangeStatusBARunner extends ChangeStatusBARunner<IntInvestmentItem, InvestmentStatus> {

    private final InvestmentStatusManager investmentStatusManager; // fixme
    private final AsyncStatusChanger asyncStatusChanger;
    private final InvestmentDao investmentDao;

    public InvestmentChangeStatusBARunner(InvestmentStatusManager investmentStatusManager, AsyncStatusChanger asyncStatusChanger, InvestmentDao investmentDao) {
        this.investmentStatusManager = investmentStatusManager;
        this.asyncStatusChanger = asyncStatusChanger;
        this.investmentDao = investmentDao;
    }

    @Override
    public IntInvestmentItem getDocumentById(String id) {
        return investmentDao.getInvestmentById(id);
    }

    @Override
    public StatusManager<IntInvestmentItem, InvestmentStatus> getStatusManager() {
        return investmentStatusManager;
    }

    @Override
    protected AsyncStatusChanger<IntInvestmentItem, InvestmentStatus> getStatusChanger() {
        return asyncStatusChanger;
    }
}
