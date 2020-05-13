package com.potato112.springservice.utils;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class DBQueryMapUtilTest {

    void init(){}

    @Test
    void getSqlQueryVo() throws SQLException {

        DBQueryMapUtil.readQueries();
        String query = DBQueryMapUtil.getSqlQueryVo("select.rentalCars");
        System.out.println(query);
    }

    @Test
    void getNextSqlQueryVo() throws SQLException {

        DBQueryMapUtil.readQueries();
        String query = DBQueryMapUtil.getSqlQueryVo("insert.docLock");
        System.out.println(query);
    }

    @Test
    void shouldSelectSqlQueryVo() throws SQLException {

        DBQueryMapUtil.readQueries();
        String query = DBQueryMapUtil.getSqlQueryVo("select.docLock");
        System.out.println(query);
    }
}