package com.potato112.springservice.jms;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.crud.jdbc.datasource.DataSourceBuilder;
import com.potato112.springservice.crud.jdbc.model.RentalCarVO;
import com.potato112.springservice.crud.jdbc.services.RentalCarDAO;
import com.potato112.springservice.utils.DBQueryMapUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class SimpleReceiverTest {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private RentalCarDAO rentalCarDAO;


    /**
     * Simple Receiver test.
     * Sent message is serialized to string by converter.
     * Message is provided and sent by JMSTemplate.
     */
    @Test
    public void shouldReceiveMessage() throws SQLException {

        DBQueryMapUtil.readQueries();
        DataSource dataSource = DataSourceBuilder.buildDataSource();
        Connection connection = dataSource.getConnection();

        List<String> carIds = Arrays.asList("Ford", "Tesla");
        List<RentalCarVO> cars = rentalCarDAO.getRentalCarList(connection, carIds);

        RentalCarVO rentalCarVO = cars.get(0);

        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an CarVO message.");
        jmsTemplate.convertAndSend("carProcessor", rentalCarVO);
        // Receiver will print message with content
    }
}