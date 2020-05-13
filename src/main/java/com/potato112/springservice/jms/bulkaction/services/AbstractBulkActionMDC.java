package com.potato112.springservice.jms.bulkaction.services;

import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionType;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionInit;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionResultManager;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;
import com.potato112.springservice.jms.simple.BaseMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

@Component
public abstract class AbstractBulkActionMDC<INIT extends BulkActionInit> extends BaseMDC {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBulkActionMDC.class);
    protected static final String JMS_RESULT_ID = "RESULT_ID";

    private final BulkActionResultManager bulkActionResultManager;

    public AbstractBulkActionMDC(BulkActionResultManager bulkActionResultManager) {
        this.bulkActionResultManager = bulkActionResultManager;
    }

    protected abstract BulkActionsRunResultVo runBulkAction(INIT bulkActionInit);


/*    @Override
    public void onMessage(Message message) {
        super.onMessage(message);
    }*/

    @Override
    public void processMessage(ObjectMessage message, String userName) {

        LOGGER.info("JMS message processing start");

        // get bulk action id
        final String id = getResultId(message);

        // get init object
        final BulkActionInit bulkActionInit = getBulkAction(message, id);

        bulkActionResultManager.markInProgress(id);

        final BulkActionsRunResultVo result = runBulkActionWrapper(bulkActionInit); // fails here
        bulkActionResultManager.completeBulkAction(id, result);

        final BulkActionType type = bulkActionInit.getType();
        // send notification about BA complete
    }

    private BulkActionsRunResultVo runBulkActionWrapper(final BulkActionInit bulkActionInit) {

        try {
            INIT init = (INIT) bulkActionInit;
            return runBulkAction(init);

        } catch (Exception e) {

            LOGGER.debug("Failed to run BulkAction in MDC" + e.getMessage());
            return new BulkActionsRunResultVo(false, e.getLocalizedMessage());
        }
    }

    private String getResultId(ObjectMessage objectMessage) {

        String resultID = getStringPropertyFromMessage(objectMessage, JMS_RESULT_ID);

        if (null == resultID || "".equals(resultID)) {
            StringBuilder errorMessage = new StringBuilder("JMS Message has no valid Id.");
            errorMessage.append("Invalid property: ");
            errorMessage.append(JMS_RESULT_ID);

            throw new IllegalArgumentException(errorMessage.toString());
        }
        return resultID;
    }


    private BulkActionInit getBulkAction(final ObjectMessage objectMessage, final String id) {

        Object object;
        try {
            object = objectMessage.getObject();

        } catch (JMSException e) {
            System.out.println("Failed to get object from message" + e.getMessage());

            // TODO handle this situation with dedicated bulkActionManager
            throw new IllegalStateException("Internal JMS ERROR");
        }
        validateJMSMessageObject(object);

        return (BulkActionInit) object;
    }

    private void validateJMSMessageObject(Object object) {

        if (null == object) {
            throw new IllegalArgumentException("Bulk action failed. JMS ObjectMessage should contain object");
        }
        if (!(object instanceof BulkActionInit)) {
            throw new IllegalArgumentException("Bulk action failed. JMS ObjectMessage doesn't contain bulk action");
        }
    }


}
