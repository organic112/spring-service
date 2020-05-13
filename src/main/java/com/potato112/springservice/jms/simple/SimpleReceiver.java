package com.potato112.springservice.jms.simple;


import com.potato112.springservice.crud.jdbc.model.RentalCarVO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Example of simple MDC (message driven component - receiver)
 */
@Component
public class SimpleReceiver {

    @JmsListener(destination = "carProcessor", containerFactory = "customFactory")
    public void receiveMessage(RentalCarVO rentalCarVO) {
        System.out.println("Received simple jms message in Simple Receiver (text). RentalCarVO: brand: " + rentalCarVO.getBrand());
    }
}
