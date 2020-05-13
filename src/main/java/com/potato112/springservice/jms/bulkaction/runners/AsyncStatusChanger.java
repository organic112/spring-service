package com.potato112.springservice.jms.bulkaction.runners;

import com.potato112.springservice.jms.bulkaction.model.init.ChangeStatusBAInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysDocument;
import com.potato112.springservice.jms.bulkaction.model.interfaces.SysStatus;

/**
 * Async operation of this class executes 'business logic' that changes status of processed Item document
 * This class provides default implementation of runner usage.
 */
public abstract class AsyncStatusChanger<OBJTYPE extends SysDocument, STATUS extends SysStatus>
        extends AbstractAsyncBAProcessor<OBJTYPE, ChangeStatusBAInit<OBJTYPE, STATUS>, ChangeStatusBARunner<OBJTYPE, STATUS>> {

    @Override
    protected OBJTYPE getDocumentById(String id, ChangeStatusBARunner<OBJTYPE, STATUS> runner) {
        return runner.getDocumentById(id);
    }

    @Override
    protected void processSingleDocument(OBJTYPE document, ChangeStatusBAInit<OBJTYPE, STATUS> init, ChangeStatusBARunner<OBJTYPE, STATUS> runner) {
        runner.changeStatusOfSingleDocument(document, init);
    }
}
