package com.potato112.springservice.jms.bulkaction.model.interfaces;

import com.potato112.springservice.jms.bulkaction.dao.BulkActionResultDao;
import com.potato112.springservice.jms.bulkaction.model.entity.BulkActionResult;
import com.potato112.springservice.jms.bulkaction.model.entity.BulkActionResultMessage;
import com.potato112.springservice.jms.bulkaction.model.enums.BulkActionStatus;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionFutureResultDto;
import com.potato112.springservice.jms.bulkaction.model.results.BulkActionsRunResultVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BulkActionManager implements BulkActionInitiator, BulkActionResultManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BulkActionManager.class);
    private static final String RESULT_PROPERTY = "RESULT_ID";

    private BulkActionResultDao bulkActionResultDao;

    // UserManager
    private static final String DESTINATION_NAME = "investmentChangeStatusBulkAction";

    private final ApplicationContext applicationContext;
    private final JmsTemplate jmsTemplate;

    public BulkActionManager(ApplicationContext applicationContext, JmsTemplate jmsTemplate, BulkActionResultDao bulkActionResultDao) {
        this.applicationContext = applicationContext;
        this.jmsTemplate = jmsTemplate;
        this.bulkActionResultDao = bulkActionResultDao;
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
            log.info("ZZ_ECHO_01 " + id);
            sendBulkActionToJMS(bulkActionInit, id, customDestination);
        } catch (JMSException e) {
            log.info("ZZ_ECHO_01 " + e.getMessage());
            completeBulkAction(id, BulkActionStatus.FINISHED_ERROR, e.getLocalizedMessage());
        }
    }


    private void sendBulkActionToJMS(BulkActionInit bulkActionInit, String id, String customDestination) throws JMSException {

        LOGGER.info("Sending BA message to JMS...");

        String type = bulkActionInit.getType().name();
        String initUser = bulkActionInit.getLoggedUser();

        //JmsTemplate jmsTemplate = new JmsTemplate();

        try {
            jmsTemplate.send(customDestination, session -> {
                ObjectMessage objectMessage = session.createObjectMessage();
                objectMessage.setStringProperty(RESULT_PROPERTY, id);
                objectMessage.setStringProperty("BULK_ACTION_TYPE", type);
                objectMessage.setStringProperty("userName", initUser);
                objectMessage.setObject(bulkActionInit);

                LOGGER.info("Sending JMS Message Object with Id: " + objectMessage.getStringProperty(RESULT_PROPERTY));

                return objectMessage;
            });
        } catch (Exception e) {
            log.info("failed to send JMS message " + e.getLocalizedMessage());
            throw new JMSException(e.getLocalizedMessage());
        }
    }

    private String createBulkActionResultInDatabase(BulkActionInit bulkActionInit) {

        BulkActionResult bulkActionResult = new BulkActionResult();
        bulkActionResult.setBulkActionType(bulkActionInit.getType());
        bulkActionResult.setCreateUser(bulkActionInit.getLoggedUser());
        bulkActionResultDao.create(bulkActionResult);
        return bulkActionResult.getId();
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

        return bulkActionResultDao.find(BulkActionResult.class, id);
    }

    @Override
    public void markInProgress(String id) {

        // get Bulk action by id BulkActionResult bar = dao.getBulkActionResultById(id)
        BulkActionResult bulkActionResult = new BulkActionResult(); // FIXME
        bulkActionResult.setBulkActionStatus(BulkActionStatus.PENDING);
        bulkActionResult.setEndProcessingDateTime(LocalDateTime.now());
    }

    /**
     * Completes bulk action
     *
     */
    @Override
    public void completeBulkAction(String bulkActionResultId, BulkActionsRunResultVo bulkActionsRunResultVo) {

        BulkActionResult bulkActionResult = getBulkActionResultById(bulkActionResultId);
        boolean bulkActionSuccess = bulkActionsRunResultVo.getSuccess();
        bulkActionResult.setBulkActionStatus(convertStatus(bulkActionSuccess));
        bulkActionResult.setEndProcessingDateTime(LocalDateTime.now());
        bulkActionResult.setProcessingDetails(bulkActionsRunResultVo.getDetails());

        List<BulkActionResultMessage> resultMessages = bulkActionsRunResultVo.getResultList().stream()
                .map(this::convertSingleResultMessage)
                .collect(Collectors.toList());

        resultMessages.forEach( message ->{
            bulkActionResult.getResultMessages().add(message);
            message.setBulkActionResult(bulkActionResult);
        });

        bulkActionResultDao.update(bulkActionResult);
        logProcessingResult(bulkActionsRunResultVo);
    }

    private void logProcessingResult(BulkActionsRunResultVo bulkActionsRunResultVo){
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

    private BulkActionResultMessage convertSingleResultMessage(BulkActionFutureResultDto resultVo){

        BulkActionResultMessage singleResultMessage  = new BulkActionResultMessage();
        String detailMessage =  String.join(" ", resultVo.getProcessedObjectCode(), resultVo.getResultDetails());
        singleResultMessage.setBulkActionStatus(convertStatus(resultVo.isSuccess()));
        singleResultMessage.setMessageContent(detailMessage);
        return singleResultMessage;
    }

    private BulkActionStatus convertStatus(boolean bulkActionSuccess) {
        if(bulkActionSuccess)
            return BulkActionStatus.FINISHED_SUCCESS;
        else
            return BulkActionStatus.FINISHED_ERROR;
    }

    /**
     * FIXME: Actually this part not executed: check why, what case should it handle
     */
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
