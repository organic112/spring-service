package com.potato112.springservice.search;

import com.potato112.springservice.crud.model.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalCarSearchManager implements SearchManager<RentalCarTO>, DBSearchManager<RentalCarTO> {


    @Autowired
    private BaseDAO baseDAO;

    @Override
    public List<RentalCarTO> find(QueryMeta queryMeta) {

        List<RentalCar> items = (List<RentalCar>) baseDAO.getByQueryMeta(queryMeta, RentalCar.class);
        List<RentalCarTO> result = convertEntityObjectsToTO(items);
        return result;
    }

    private List<RentalCarTO> convertEntityObjectsToTO(List<RentalCar> items) {

        System.out.println("itmes size:" + items.size());

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

    @Override
    public int count(QueryMeta queryMeta) {
        return baseDAO.count(queryMeta, RentalCar.class);
    }

}
