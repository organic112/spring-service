package com.potato112.springservice.crud.jdbc.services;

import com.potato112.springservice.crud.jdbc.model.RentalCarVO;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class RentalCarDAO extends BaseDAO<RentalCarVO> {

    @Override
    public RentalCarVO resultSetToVO(ResultSet resultSet) throws SQLException {

        RentalCarVO vo = new RentalCarVO();
        vo.setId(resultSet.getString("car_id"));
        vo.setBrand(resultSet.getString("brand"));
        vo.setColor(resultSet.getString("color"));
        vo.setPayloadKg(resultSet.getString("payloadkg"));
        vo.setPricePerHour(resultSet.getString("price_per_hour"));

        return vo;
    }

    public List<RentalCarVO> getRentalCarList(Connection connection, List<String> brandNames) throws SQLException {

        String sql = getSQLQuery("select.rentalCars");
        sql = replaceInParameter(sql, ":brandNames", brandNames);
        return selectList(connection, sql);
    }

    public void insertCar(Connection connection, RentalCarVO rentalCarVO) throws SQLException {

        String sql = getSQLQuery("insert.rentalCar");
        executeInsert(connection, sql,
                rentalCarVO.getBrand(),
                rentalCarVO.getColor(),
                rentalCarVO.getPayloadKg(),
                rentalCarVO.getPricePerHour()
        );
    }
}
