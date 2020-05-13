package com.potato112.springservice.jms.bulkaction.model.interfaces;

import com.potato112.springservice.jms.bulkaction.model.entity.BulkActionResult;
import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionStatus;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.time.LocalDateTime;

@Component
public class BulkActionManager implements BulkActionInitiator, BulkActionResultManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkActionManager.class);
    private static final String RESULT_PROPERTY = "RESULT_ID";

    // @Autowire
    // BulkActionDAO
    // UserManager
    private static final String DESTINATION_NAME = "investmentChangeStatusBulkAction";

    private final ApplicationContext applicationContext;
    private final JmsTemplate jmsTemplate;

    public BulkActionManager(ApplicationContext applicationContext, JmsTemplate jmsTemplate) {
        this.applicationContext = applicationContext;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void initiateBulkAction(BulkActionInit bulkActionInit) {
        initiateBulkAction(bulkActionInit, DESTINATION_NAME);
    }

    @Override
    public void initiateBulkAction(BulkActionInit bulkActionInit, String customDestination) {
        validateBAInit(bulkActionInit);

        String id = createBulkActionResultInDatabase(bulkActionInit);

        if (null == id) {
            throw new IllegalStateException("bulk action result Id should not be null");
        }
        try {
            sendBulkActionToJMS(bulkActionInit, id, customDestination);
        } catch (JMSException e) {
            completeBulkAction(id, BulkActionStatus.FINISHED_ERROR, e.getLocalizedMessage());
        }
    }

    private void sendBulkActionToJMS(BulkActionInit bulkActionInit, String id, String customDestination) throws JMSException {

        LOGGER.info("Sending BA message to JMS...");

        String type = bulkActionInit.getType().name();
        String initUser = bulkActionInit.getLoggedUser();

        try {
            jmsTemplate.send(customDestination, session -> {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setStringProperty("RESULT_ID", id);
                objectMessage.setStringProperty("BULK_ACTION_TYPE", type);
                objectMessage.setStringProperty("userName", initUser);
                objectMessage.setObject(bulkActionInit);

                LOGGER.info("Sending JMS Message Object with Id: " + objectMessage.getStringProperty(RESULT_PROPERTY));

                return objectMessage;
            });
        } catch (Exception e) {
            throw new JMSException(e.getLocalizedMessage());
        }
    }

    private String createBulkActionResultInDatabase(BulkActionInit bulkActionInit) {
        // Implement DAO and FIXME
        return "fixmeId";
    }

    private void validateBAInit(final BulkActionInit bulkActionInit) {
        if (null == bulkActionInit) {
            throw new IllegalArgumentException("BulkActionInit should not be null.");
        }
        if (null == bulkActionInit.getType()) {
            throw new IllegalArgumentException("BulkActionInit type should not be null.");
        }
    }


    @Override
    public BulkActionResult getBulkActionResultById(String id) {

        // get Bulk action by id BulkActionResult bar = dao.getBulkActionResultById(id)
        // FIXME implement DAO
        BulkActionResult bulkActionResult = new BulkActionResult();

        return bulkActionResult;
    }

    @Override
    public void markInProgress(String id) {

        // get Bulk action by id BulkActionResult bar = dao.getBulkActionResultById(id)
        BulkActionResult bulkActionResult = new BulkActionResult(); // FIXME
        bulkActionResult.setBulkActionStatus(BulkActionStatus.PENDING);
        bulkActionResult.setEndProcessingDateTime(LocalDateTime.now());
    }

    @Override
    public void completeBulkAction(String bulkActionResultId, BulkActionsRunResultVo bulkActionsRunResultVo) {

        bulkActionsRunResultVo.getResultList(); // TODO concat to one message and set as bulkActionMessage
        // get Bulk action by id BulkActionResult bar = dao.getBulkActionResultById(id)
        // setStatus(bulkActionStatus)
        // setEndProcessingDate(new Date())
        // setDetails(bulkActionMessage)


        bulkActionsRunResultVo.getResultList().forEach(result -> {

            String cause = "";
            if (null != result.getException()) {
                cause = result.getException().getMessage();
            }

            String message = "FINAL Single action - is success: " + result.isSuccess() + " | cause: " + cause + " | details: "
                    + result.getResultDetails() + " | single document code: " + result.getProcessedObjectCode();
            LOGGER.info(message);
        });
    }

    @Override
    public void completeBulkAction(String bulkActionResultId, BulkActionStatus bulkActionStatus, String bulkActionMessage) {

        // get Bulk action by id BulkActionResult bar = dao.getBulkActionResultById(id)
        BulkActionResult bulkActionResult = new BulkActionResult(); // FIXME
        bulkActionResult.setBulkActionStatus(bulkActionStatus);
        bulkActionResult.setEndProcessingDateTime(LocalDateTime.now());
        bulkActionResult.setProcessingDetails(bulkActionMessage);

        String message = "FINAL action:" + "result Id" + bulkActionResultId + " - " + bulkActionStatus.name() + " - " + LocalDateTime.now() + " end time:"
                + " - " + bulkActionMessage;
        LOGGER.info(message);
    }
}
