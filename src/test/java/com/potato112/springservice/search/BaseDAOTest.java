package com.potato112.springservice.search;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.search.service.BaseDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class BaseDAOTest {

    @Autowired
    private BaseDAO baseDAO;

    @Test
    public void shouldFetchDataWithHibernateQuery(){

        // TODO
/*
        QueryMeta queryMeta = new QueryMeta();

        Criterion projection = Restrictions.eq("brand", "Tesla");
        baseDAO.getPropertyValuesByQueryMeta(queryMeta, RentalCar.class, "brand");*/




    }




}