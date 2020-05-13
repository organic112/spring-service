package com.potato112.springservice.crud.jdbc.services;

import com.potato112.springservice.crud.jdbc.model.BaseVO;
import com.potato112.springservice.utils.DBQueryMapUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A2 Crud jdbc DAO
 */
public abstract class BaseDAO<T extends BaseVO> {

    /**
     * Every extending DAO should provide conversion implementation:
     * 'row result set' to 'VO' (representing result row)
     *  Note! 'row result set' is java.sql interface
     */
    public abstract T resultSetToVO(ResultSet resultSet) throws SQLException;

    public List<T> selectList(Connection connection, String query) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return convertResultSetVoToVoList(resultSet);
            }
        }
    }

    public List<T> selectList(Connection connection, String query, Object... params) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            addParameters(preparedStatement, params);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return convertResultSetVoToVoList(resultSet);
            }
        }
    }

    public T selectOne(Connection connection, String query, Object... params) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            addParameters(preparedStatement, params);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return convertResultSetVoToVo(resultSet);
            }
        }
    }

    public void executeInsert(Connection connection, String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            addParameters(preparedStatement, params);
            preparedStatement.executeUpdate(); // insert with update? TODO check!
        }
    }

    public void executeUpdate(Connection connection, String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            addParameters(preparedStatement, params);
            preparedStatement.executeUpdate();
        }
    }

    public void executeDelete(Connection connection, String query, Object... params) throws SQLException {

        executeInsert(connection, query, params);  // delete with insert? TODO check!
    }

    public int executeCount(Connection connection, String query, Object... params) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            addParameters(preparedStatement, params);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                int numberOfMappings = 0;
                if (resultSet.next()) {
                    numberOfMappings = resultSet.getInt("COUNT");
                }
                if (resultSet.next()) {
                    throw new RuntimeException("Invalid result (more than one row fetched");
                }
                return numberOfMappings;
            }
        }
    }

    private void addParameters(PreparedStatement preparedStatement, Object[] params) throws SQLException {

        if (params.length > 0) {
            int paramNo = 1;
            for (Object parameter : params) {
                preparedStatement.setObject(paramNo++, parameter);
            }
        }
    }

    protected T convertResultSetVoToVo(ResultSet resultSet) throws SQLException {

        T vo = null;
        while (resultSet.next()) {
            vo = resultSetToVO(resultSet);
        }
        return vo;
    }

    protected List<T> convertResultSetVoToVoList(ResultSet resultSet) throws SQLException {

        List<T> voList = new ArrayList<>();

        while (resultSet.next()) {
            T vo = resultSetToVO(resultSet);
            voList.add(vo);
        }
        return voList;
    }

    protected String getSQLQuery(String queryId) throws SQLException {

        String sql = DBQueryMapUtil.getSqlQueryVo(queryId);

        if (null == sql) {
            throw new SQLException("Resource not found. Missing query in xml file.");
        }
        return sql;
    }

    /**
     * Adds 'in parameters' from params list to sql query
     */
    public String replaceInParameter(String sql, String paramName, List<String> paramList) {

        String inParams = String.join("', '", paramList);
        StringBuilder sb = new StringBuilder();
        inParams = sb.append("'").append(inParams).append("'").toString();
        return sql.replace(paramName, inParams);
    }
}
