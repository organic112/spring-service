package com.potato112.springservice.jms.bulkaction.dao;

import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentProduct;
import com.potato112.springservice.repository.interfaces.crud.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class InvestmentDao {

    private CRUDService<IntInvestmentItem> intInvestmentItemCRUDService;
    private CRUDService<InvestmentDocument> investmentDocumentCRUDService;
    private CRUDService<InvestmentProduct> investmentProductCRUDService;

    public InvestmentDao(CRUDService<IntInvestmentItem> intInvestmentItemCRUDService,
                         CRUDService<InvestmentDocument> investmentDocumentCRUDService,
                         CRUDService<InvestmentProduct> investmentProductCRUDService) {
        this.intInvestmentItemCRUDService = intInvestmentItemCRUDService;
        this.investmentDocumentCRUDService = investmentDocumentCRUDService;
        this.investmentProductCRUDService = investmentProductCRUDService;
    }


    //TODO implement as real DAO
/*    private Map<String, IntInvestmentItem> repositoryInvestmentItemMap = new HashMap<>();
    private List<InvestmentDocument> investmentDocumentList = new ArrayList<>();*/

    public IntInvestmentItem getInvestmentById(String id) {
        //init();
        return intInvestmentItemCRUDService.find(IntInvestmentItem.class, id);
    }

    public InvestmentDocument getInvestmentDocumentById(String id) {
        //init();
        return investmentDocumentCRUDService.find(InvestmentDocument.class, id);
    }

    public List<InvestmentDocument> getAllInvestmentDocuments() {

        return investmentDocumentCRUDService.findWithNamedQuery("getAllInvestmentDocuments");
    }

    public List<IntInvestmentItem> getAllInvestmentItems() {

        return intInvestmentItemCRUDService.findWithNamedQuery("getAllInvestmentItems");
    }

    public void updateItem(IntInvestmentItem item){

        intInvestmentItemCRUDService.update(item);
    }

    public void updateDocument(InvestmentDocument document){

        investmentDocumentCRUDService.update(document);
    }

    public InvestmentProduct getProductById(String id){

       return investmentProductCRUDService.find(InvestmentProduct.class, id);
    }

    public void updateProduct(InvestmentProduct product){

        investmentProductCRUDService.update(product);
    }

    /*private void init() {
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
        intInvestmentItem.setProductNumber(1000 + nuber);
        intInvestmentItem.setInvestmentStatus(InvestmentStatus.IMPORTED);

        InvestmentDocument investmentDocument = new InvestmentDocument();
        Integer docNum = 55 + nuber;
        Integer carNum = 69 + docNum;
        investmentDocument.setId(docNum.toString());
        investmentDocument.setInvestmentNumber(carNum.toString());

        investmentDocumentList.add(investmentDocument);

        intInvestmentItem.setInvestmentDocument(investmentDocument);
    }*/
}
