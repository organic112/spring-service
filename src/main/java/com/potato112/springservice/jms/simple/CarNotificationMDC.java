package com.potato112.springservice.jms.simple;

import com.potato112.springservice.crud.jdbc.model.RentalCarVO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * example of simple MDC based on object message
 */
@Component
public class CarNotificationMDC extends BaseMDC {

    private static final String DESTINATION_NAME = "carNotificationProcessorQueue";
    private static final String FACTORY_BEAN_NAME = "customFactory";

    @JmsListener(destination = DESTINATION_NAME, containerFactory = FACTORY_BEAN_NAME)
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onMessage(Message message) {
        super.onMessage(message);
    }

    @Override
    public void processMessage(ObjectMessage objectMessage, String userName) {

        try {
            RentalCarVO rentalCarVO = (RentalCarVO) objectMessage.getObject();

            System.out.println("Received jms message in CarNotificationMDB. UserName:" + userName + " RentalCarVO: brand: " + rentalCarVO.getBrand());
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }
    }
}
