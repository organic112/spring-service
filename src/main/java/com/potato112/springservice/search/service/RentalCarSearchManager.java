package com.potato112.springservice.search.service;

import com.potato112.springservice.crud.model.RentalCar;
import com.potato112.springservice.search.interfaces.DBSearchManager;
import com.potato112.springservice.search.interfaces.SearchManager;
import com.potato112.springservice.search.model.query.QueryMeta;
import com.potato112.springservice.search.model.RentalCarTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements specific search operations for current Entity
 */
@Service
public class RentalCarSearchManager implements SearchManager<RentalCarTO>, DBSearchManager<RentalCarTO> {

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public int count(QueryMeta queryMeta) {
        return baseDAO.count(queryMeta, RentalCar.class);
    }

    @Override
    public List<RentalCarTO> find(QueryMeta queryMeta) {

        List<RentalCar> items = (List<RentalCar>) baseDAO.getByQueryMeta(queryMeta, RentalCar.class);
        List<RentalCarTO> result = convertEntityObjectsToTO(items);
        return result;
    }

    /**
     * Converts list of entities to list of transfer objects
     */
    private List<RentalCarTO> convertEntityObjectsToTO(List<RentalCar> items) {
        List<RentalCarTO> result = new ArrayList<>();
        for (RentalCar rc : items) {
            RentalCarTO newItem = new RentalCarTO();
            newItem.setId(rc.getId());
            newItem.setBrand(rc.getBrand());
            newItem.setColor(rc.getColor());
            result.add(newItem);
        }
        return result;
    }
}
