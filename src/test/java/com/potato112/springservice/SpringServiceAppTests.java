package com.potato112.springservice;


import com.potato112.springservice.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class SpringServiceAppTests {

    @Test
    public void contextLoads() {


        System.out.println("test 1");
        if(true){
            System.out.println("test 3");
            return;
        }
        System.out.println("test text 3");

    }


    private void foo(){

        System.out.println("test text ");

    }

}
