package com.potato112.springservice.jms.bulkaction.dao;

import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.Investment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvestmentDao {


    //TODO implement as real DAO
    private Map<String, IntInvestmentItem> repositoryInvestmentItemMap = new HashMap<>();
    private List<Investment> investmentList = new ArrayList<>();

    public IntInvestmentItem getInvestmentById(String id) {
        init();
        return repositoryInvestmentItemMap.get(id);
    }

    public Investment getInvestmentDocumentById(String id) {
        init();
        return investmentList.stream().filter(doc -> doc.getId().equals(id)).findFirst().get();
    }

    private void init() {
        IntInvestmentItem item1 = new IntInvestmentItem();
        IntInvestmentItem item2 = new IntInvestmentItem();
        IntInvestmentItem item3 = new IntInvestmentItem();

        createInvestementItem(item1, 1);
        createInvestementItem(item2, 2);
        createInvestementItem(item3, 3);

        repositoryInvestmentItemMap.put("1", item1);
        repositoryInvestmentItemMap.put("2", item2);
        repositoryInvestmentItemMap.put("3", item3);
    }

    private void createInvestementItem(IntInvestmentItem intInvestmentItem, Integer nuber) {
        intInvestmentItem.setId(nuber.toString());
        intInvestmentItem.setClientNumber(1000 + nuber);
        intInvestmentItem.setInvestmentStatus(InvestmentStatus.IMPORTED);

        Investment investment = new Investment();
        Integer docNum = 55 + nuber;
        Integer carNum = 69 + docNum;
        investment.setId(docNum.toString());
        investment.setInvestmentNumber(carNum.toString());

        investmentList.add(investment);

        intInvestmentItem.setInvestment(investment);
    }
}
