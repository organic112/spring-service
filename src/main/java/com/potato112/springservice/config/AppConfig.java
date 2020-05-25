package com.potato112.springservice.config;

import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import com.potato112.springservice.jms.bulkaction.BulkActionExecutor;
import com.potato112.springservice.jms.bulkaction.dao.InvestmentDao;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.interfaces.BulkActionManager;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentProduct;
import com.potato112.springservice.repository.entities.auth.*;
import com.potato112.springservice.repository.interfaces.crud.CRUDService;
import com.potato112.springservice.repository.interfaces.crud.CRUDServiceBean;
import com.potato112.springservice.repository.services.UserCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableAsync
@EnableJms
@Configuration
@EnableWebSecurity
@ComponentScan({"com.potato112.springservice"})
public class AppConfig implements CommandLineRunner {

    @Autowired
    private UserCRUDService userCRUDService;

    @Autowired
    private CRUDServiceBean<UserGroup> userGroupCRUDService;

    @Autowired
    private CRUDService<UserGroupMapping> mappingCRUDService;

    @Autowired
    private CRUDService<InvestmentDocument> investmentCRUDService;

    @Autowired
    private BulkActionExecutor bulkActionExecutor;

    @Autowired
    private InvestmentDao investmentDao;

    @Autowired
    private BulkActionManager bulkActionInitiator;

    @PostConstruct
    public void init() {

    }

    @Override
    public void run(String... args) throws Exception {

        //create user
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPassword("98ACDA0612B5263009C0E9F605F6844B8DAFF5AE");
        user.setEmail("admin@temp.com"); //
        user.setLocked(UserStatus.ENABLED);
        user.setPhone("52 5556 6565 54");
        user.setCreateUser("app-context-user");
        userCRUDService.save(user);

        // create group with permissions
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setCreateUser("app-context-user");
        userGroup1.setGroupName("new_test_user_group_name");
        GroupPermission groupPermission1 = new GroupPermission();
        groupPermission1.setCreateUser("app-context-user");
        groupPermission1.setViewName(ViewName.FOO_OVERVIEW_VIEW);
        groupPermission1.setCanCreate(true);
        groupPermission1.setCanUpdate(true);
        groupPermission1.setCanDelete(true);
        groupPermission1.setUserGroup(userGroup1);

        GroupPermission groupPermission2 = new GroupPermission();
        groupPermission2.setCreateUser("app-context-user");
        groupPermission2.setViewName(ViewName.USER_VIEW);
        groupPermission2.setCanCreate(true);
        groupPermission2.setCanUpdate(true);
        groupPermission2.setCanDelete(true);
        groupPermission2.setUserGroup(userGroup1);

        GroupPermission groupPermission3 = new GroupPermission();
        groupPermission3.setCreateUser("app-context-user");
        groupPermission3.setViewName(ViewName.GROUP_VIEW);
        groupPermission3.setCanCreate(true);
        groupPermission3.setCanUpdate(true);
        groupPermission3.setCanDelete(true);
        groupPermission3.setUserGroup(userGroup1);

        List<GroupPermission> permissions = new ArrayList<>();
        permissions.add(groupPermission1);
        permissions.add(groupPermission2);
        permissions.add(groupPermission3);

        userGroup1.setGroupPermissions(permissions);
        userGroupCRUDService.create(userGroup1);

        // create user - group mapping
        UserGroupMapping userGroupMapping = new UserGroupMapping();
        userGroupMapping.setCreateUser("app-context-user");
        userGroupMapping.setUser(user);
        userGroupMapping.setUserGroup(userGroup1);
        mappingCRUDService.create(userGroupMapping);

        // CREATE IntInvestments
        InvestmentDocument investmentDocument = new InvestmentDocument();
        InvestmentDocument investmentDocument2 = new InvestmentDocument();
        InvestmentDocument investmentDocument3 = new InvestmentDocument();

        InvestmentProduct product = new InvestmentProduct();
        InvestmentProduct product1 = new InvestmentProduct();
        InvestmentProduct product2 = new InvestmentProduct();
        InvestmentProduct product3 = new InvestmentProduct();
        InvestmentProduct product4 = new InvestmentProduct();
        InvestmentProduct product5 = new InvestmentProduct();
        InvestmentProduct product6 = new InvestmentProduct();
        InvestmentProduct product7 = new InvestmentProduct();

        List<IntInvestmentItem> itemList1 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem1 = new IntInvestmentItem();
        intInvestmentItem1.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem1.setVariant(125484);
        intInvestmentItem1.setItemNumber("ITEM-01A");
        intInvestmentItem1.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem1);
        intInvestmentItem1.setInvestmentProducts(Arrays.asList(product));

        IntInvestmentItem intInvestmentItem2 = new IntInvestmentItem();
        intInvestmentItem2.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem2.setVariant(125485);
        intInvestmentItem2.setItemNumber("ITEM-02A");
        intInvestmentItem2.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem2);
        intInvestmentItem2.setInvestmentProducts(Arrays.asList(product1));

        IntInvestmentItem intInvestmentItem3 = new IntInvestmentItem();
        intInvestmentItem3.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem3.setVariant(125486);
        intInvestmentItem3.setItemNumber("ITEM-03A");
        intInvestmentItem3.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem3);
        intInvestmentItem3.setInvestmentProducts(Arrays.asList(product2));

        List<IntInvestmentItem> itemList2 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem4 = new IntInvestmentItem();
        intInvestmentItem4.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem4.setVariant(125487);
        intInvestmentItem4.setItemNumber("ITEM-04A");
        intInvestmentItem4.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem4);
        intInvestmentItem4.setInvestmentProducts(Arrays.asList(product3));

        IntInvestmentItem intInvestmentItem5 = new IntInvestmentItem();
        intInvestmentItem5.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem5.setVariant(125488);
        intInvestmentItem5.setItemNumber("ITEM-05A");
        intInvestmentItem5.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem5);
        intInvestmentItem5.setInvestmentProducts(Arrays.asList(product4));

        IntInvestmentItem intInvestmentItem6 = new IntInvestmentItem();
        intInvestmentItem6.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem6.setVariant(125489);
        intInvestmentItem6.setItemNumber("ITEM-06A");
        intInvestmentItem6.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem6);
        intInvestmentItem6.setInvestmentProducts(Arrays.asList(product5));

        List<IntInvestmentItem> itemList3 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem7 = new IntInvestmentItem();
        intInvestmentItem7.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem7.setVariant(125410);
        intInvestmentItem7.setItemNumber("ITEM-07A");
        intInvestmentItem7.setInvestmentDocument(investmentDocument3);
        itemList3.add(intInvestmentItem7);
        intInvestmentItem7.setInvestmentProducts(Arrays.asList(product6, product7)); // false, true

        // create 3 Investments for bulk action processing
        investmentDocument.setInvestmentStatus(InvestmentStatus.IMPORTED);
        investmentDocument.setInvestmentNumber("INV_001");
        investmentDocument.setInvestmentItemList(itemList1);

        investmentDocument2.setInvestmentStatus(InvestmentStatus.IMPORTED);
        investmentDocument2.setInvestmentNumber("INV_002");
        investmentDocument2.setInvestmentItemList(itemList2);

        investmentDocument3.setInvestmentStatus(InvestmentStatus.IMPORTED);
        investmentDocument3.setInvestmentNumber("INV_003");
        investmentDocument3.setInvestmentItemList(itemList3);

        product.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product.setIsValidFlag(true);
        product.setIntInvestmentItem(intInvestmentItem1);
        product.setProductNumber("INV_NUM_000");

        product1.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product1.setIsValidFlag(true);
        product1.setIntInvestmentItem(intInvestmentItem2);
        product1.setProductNumber("INV_NUM_001");

        product2.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product2.setIsValidFlag(false);
        product2.setIntInvestmentItem(intInvestmentItem3);
        product2.setProductNumber("INV_NUM_002");

        product3.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product3.setIsValidFlag(false);
        product3.setIntInvestmentItem(intInvestmentItem4);
        product3.setProductNumber("INV_NUM_003");

        product4.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product4.setIsValidFlag(true);
        product4.setIntInvestmentItem(intInvestmentItem5);
        product4.setProductNumber("INV_NUM_004");

        product5.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product5.setIsValidFlag(true);
        product5.setIntInvestmentItem(intInvestmentItem6);
        product5.setProductNumber("INV_NUM_005");

        product6.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product6.setIsValidFlag(true);
        product6.setIntInvestmentItem(intInvestmentItem7);
        product6.setProductNumber("INV_NUM_006");

        product7.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        product7.setIsValidFlag(false);
        product7.setIntInvestmentItem(intInvestmentItem7);
        product7.setProductNumber("INV_NUM_007");

        investmentDocument.setCreateUser("app-context-user");
        investmentDocument2.setCreateUser("app-context-user");
        investmentDocument3.setCreateUser("app-context-user");

        investmentCRUDService.create(investmentDocument);
        investmentCRUDService.create(investmentDocument2);
        investmentCRUDService.create(investmentDocument3);

        System.out.println("RUNNING SPRING INTEGRATION TESTS MODE...");

        // equivalent of multiple tests

       /* SysStatus targetStatus = InvestmentStatus.CLOSED;
        List<IntInvestmentItem> investmentDocumentList = investmentDao.getAllInvestmentItems();
        Set<String> set1 = new HashSet<>();
        set1.add(investmentDocumentList.get(0).getId());

        Set<String> set2 = new HashSet<>();
        set2.add(investmentDocumentList.get(0).getId());

        Set<String> set3 = new HashSet<>();
        set3.add(investmentDocumentList.get(0).getId());

        Set<String> set4 = new HashSet<>();
        set4.add(investmentDocumentList.get(0).getId());

        Set<String> set5 = new HashSet<>();
        set5.add(investmentDocumentList.get(0).getId());

        String cancelationMessage = "";
        String loggedUser = "testUserFromExecutor";

        // FIXME Changle to simple bulk action

        // adds several times the same Id to execute in async action in parallel on same object and throw lock error
        BulkActionInit bulkActionInit1 = new InvestmentChangeStatusBAInit(targetStatus, set1, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit2 = new InvestmentChangeStatusBAInit(targetStatus, set2, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit3 = new InvestmentChangeStatusBAInit(targetStatus, set3, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit4 = new InvestmentChangeStatusBAInit(targetStatus, set4, cancelationMessage, loggedUser);
        BulkActionInit bulkActionInit5 = new InvestmentChangeStatusBAInit(targetStatus, set5, cancelationMessage, loggedUser);

        bulkActionInitiator.initiateBulkAction(bulkActionInit1);
        bulkActionInitiator.initiateBulkAction(bulkActionInit2);
        bulkActionInitiator.initiateBulkAction(bulkActionInit3);
        bulkActionInitiator.initiateBulkAction(bulkActionInit4);
        bulkActionInitiator.initiateBulkAction(bulkActionInit5);*/

    }
}
