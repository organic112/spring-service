package com.potato112.springservice.jdbc.datasource;

import com.potato112.springservice.crud.jdbc.datasource.DataSourceBuilder;
import org.junit.Test;

import javax.sql.DataSource;


public class DataSourceBuilderTest {



    @Test
    public void shouldBuildDataSource() {

        DataSource basicDataSource = DataSourceBuilder.buildDataSource();


    }

}