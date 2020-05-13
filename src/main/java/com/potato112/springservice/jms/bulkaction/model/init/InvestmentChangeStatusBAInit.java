package com.potato112.springservice.jms.bulkaction.model.init;

import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;

import java.io.Serializable;
import java.util.Set;

public class InvestmentChangeStatusBAInit extends ChangeStatusBAInit<InvestmentDocument, SysStatus> implements Serializable, BulkActionInit {



    public InvestmentChangeStatusBAInit(SysStatus targetStatus, Set<String> documentIds, String cancellationMessage, String loggedUser) {
        super(targetStatus, documentIds, cancellationMessage, loggedUser);
    }

    @Override
    public BulkActionType getType() {
        return BulkActionType.INVEST_PROCESSING;
    }

}
