package com.potato112.springservice.jdbc.services;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.crud.jdbc.datasource.DataSourceBuilder;
import com.potato112.springservice.crud.jdbc.model.BusinessRuleException;
import com.potato112.springservice.crud.jdbc.model.jdbclock.DocLockVO;
import com.potato112.springservice.crud.jdbc.services.JdbcDocLockDao;
import com.potato112.springservice.jms.bulkaction.model.enums.SysDocumentType;
import com.potato112.springservice.utils.DBQueryMapUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;


/**
 * #A2
 * Test of jdbc driven document locks DAO
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class JdbcJpaDocLockDaoTest {

    @Autowired
    private JdbcDocLockDao jdbcDocLockDao;

    private DocLockVO docLockVO;

    @Before
    public void init(){
        docLockVO = new DocLockVO();
        // docLockVO.setId("test_unique_varchar_Id_FIXME");  Id is generated in SQL query
        docLockVO.setDocumentId("test_doc_id");
        docLockVO.setDocumentCode("document code");
        docLockVO.setSysDocumentType(SysDocumentType.CAR_DOCUMENT);
        docLockVO.setLogin("TEST_LOGIN");
        docLockVO.setCreateUser("TEST_CREATE_USER");
        docLockVO.setCreateDate(LocalDateTime.now());
    }

    /**
     * Check lock (insert doc lock)
     */
    @Test
    public void shouldInsertLock() throws SQLException, BusinessRuleException {

        DBQueryMapUtil.readQueries();
        DataSource dataSource = DataSourceBuilder.buildDataSource();
        Connection connection = dataSource.getConnection();
        jdbcDocLockDao.insertDocLock(connection, docLockVO);
    }

    /**
     * Check lock (get doc lock)
     */
    @Test
    public void shouldInsertAndSelectDocumentLock() throws SQLException, BusinessRuleException {

        DBQueryMapUtil.readQueries();
        DataSource dataSource = DataSourceBuilder.buildDataSource();
        Connection connection = dataSource.getConnection();
        jdbcDocLockDao.insertDocLock(connection, docLockVO); // FIXME insert atomic data in test db

        // get doc lock
        DocLockVO docLockVO = jdbcDocLockDao.getDocLock(connection, "test_doc_id");
        System.out.println("Fetched id:" + docLockVO.getDocumentId());
    }

    /**
     * Check lock (delete lock)
     */
    @Test
    public void shouldDeleteDocumentLock() throws SQLException, BusinessRuleException {

        DBQueryMapUtil.readQueries();
        DataSource dataSource = DataSourceBuilder.buildDataSource();
        Connection connection = dataSource.getConnection();

        System.out.println("insert lock to db...");
        jdbcDocLockDao.insertDocLock(connection, docLockVO); // FIXME insert atomic data in test db

        System.out.println("get lock from db...");
        DocLockVO docLockVO = jdbcDocLockDao.getDocLock(connection, "test_doc_id");
        System.out.println("Fetched id:" + docLockVO.getDocumentId());

        System.out.println("delete locks...");
        jdbcDocLockDao.deleteDocLock(connection, "test_doc_id");
        DocLockVO docLockVOnull = jdbcDocLockDao.getDocLock(connection, "test_doc_id");
        System.out.println("try fetch lock id (null ok):" + docLockVOnull);
    }
}