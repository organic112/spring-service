package com.potato112.springservice.crud.jdbc.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * example of manually provided DataSource
 */
public class DataSourceBuilder {

    private DataSourceBuilder() {
    }

    public static DataSource buildDataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        String url = "jdbc:mysql://localhost:33060/demo-db";
        String user = "root";
        String password = "root";

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        //dataSource.setUrl("jdbc:mysql://<host>:<port>/<database>");
        dataSource.setUrl(url);
        //dataSource.setMaxActive(10);
        dataSource.setMaxIdle(5);
        dataSource.setInitialSize(5);
        dataSource.setValidationQuery("SELECT 1");

        return dataSource;
    }


}
