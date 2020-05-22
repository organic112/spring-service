package com.potato112.springservice.config;

import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import com.potato112.springservice.jms.bulkaction.BulkActionExecutor;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentProductStatus;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
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
        userCRUDService.save(user);

        // create group with permissions
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setGroupName("new_test_user_group_name");
        GroupPermission groupPermission1 = new GroupPermission();
        groupPermission1.setViewName(ViewName.FOO_OVERVIEW_VIEW);
        groupPermission1.setCanCreate(true);
        groupPermission1.setCanUpdate(true);
        groupPermission1.setCanDelete(true);
        groupPermission1.setUserGroup(userGroup1);

        GroupPermission groupPermission2 = new GroupPermission();
        groupPermission2.setViewName(ViewName.USER_VIEW);
        groupPermission2.setCanCreate(true);
        groupPermission2.setCanUpdate(true);
        groupPermission2.setCanDelete(true);
        groupPermission2.setUserGroup(userGroup1);

        GroupPermission groupPermission3 = new GroupPermission();
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
        userGroupMapping.setUser(user);
        userGroupMapping.setUserGroup(userGroup1);
        mappingCRUDService.create(userGroupMapping);

        InvestmentDocument investmentDocument = new InvestmentDocument();
        InvestmentDocument investmentDocument2 = new InvestmentDocument();
        InvestmentDocument investmentDocument3 = new InvestmentDocument();

        InvestmentProduct investmentProduct = new InvestmentProduct();
        InvestmentProduct investmentProduct1 = new InvestmentProduct();
        InvestmentProduct investmentProduct2 = new InvestmentProduct();
        InvestmentProduct investmentProduct3 = new InvestmentProduct();
        InvestmentProduct investmentProduct4 = new InvestmentProduct();
        InvestmentProduct investmentProduct5 = new InvestmentProduct();
        InvestmentProduct investmentProduct6 = new InvestmentProduct();
        InvestmentProduct investmentProduct7 = new InvestmentProduct();

        List<IntInvestmentItem> itemList1 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem1 = new IntInvestmentItem();
        intInvestmentItem1.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem1.setVariant(125484);
        intInvestmentItem1.setItemNumber("ITEM-01A");
        intInvestmentItem1.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem1);
        intInvestmentItem1.setInvestmentProducts(Arrays.asList(investmentProduct));

        IntInvestmentItem intInvestmentItem2 = new IntInvestmentItem();
        intInvestmentItem2.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem2.setVariant(125485);
        intInvestmentItem2.setItemNumber("ITEM-02A");
        intInvestmentItem2.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem2);
        intInvestmentItem2.setInvestmentProducts(Arrays.asList(investmentProduct1));

        IntInvestmentItem intInvestmentItem3 = new IntInvestmentItem();
        intInvestmentItem3.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem3.setVariant(125486);
        intInvestmentItem3.setItemNumber("ITEM-03A");
        intInvestmentItem3.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem3);
        intInvestmentItem3.setInvestmentProducts(Arrays.asList(investmentProduct2));

        List<IntInvestmentItem> itemList2 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem4 = new IntInvestmentItem();
        intInvestmentItem4.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem4.setVariant(125487);
        intInvestmentItem4.setItemNumber("ITEM-04A");
        intInvestmentItem4.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem4);
        intInvestmentItem4.setInvestmentProducts(Arrays.asList(investmentProduct3));

        IntInvestmentItem intInvestmentItem5 = new IntInvestmentItem();
        intInvestmentItem5.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem5.setVariant(125488);
        intInvestmentItem5.setItemNumber("ITEM-05A");
        intInvestmentItem5.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem5);
        intInvestmentItem5.setInvestmentProducts(Arrays.asList(investmentProduct4));

        IntInvestmentItem intInvestmentItem6 = new IntInvestmentItem();
        intInvestmentItem6.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem6.setVariant(125489);
        intInvestmentItem6.setItemNumber("ITEM-06A");
        intInvestmentItem6.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem6);
        intInvestmentItem6.setInvestmentProducts(Arrays.asList(investmentProduct5));

        List<IntInvestmentItem> itemList3 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem7 = new IntInvestmentItem();
        intInvestmentItem7.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem7.setVariant(125410);
        intInvestmentItem7.setItemNumber("ITEM-07A");
        intInvestmentItem7.setInvestmentDocument(investmentDocument3);
        itemList3.add(intInvestmentItem7);
        intInvestmentItem7.setInvestmentProducts(Arrays.asList(investmentProduct6, investmentProduct7));

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

        investmentProduct.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct.setIsValidFlag(true);
        investmentProduct.setIntInvestmentItem(intInvestmentItem1);
        investmentProduct.setProductNumber("INV_NUM_000");

        investmentProduct1.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct1.setIsValidFlag(true);
        investmentProduct1.setIntInvestmentItem(intInvestmentItem2);
        investmentProduct1.setProductNumber("INV_NUM_001");

        investmentProduct2.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct2.setIsValidFlag(false);
        investmentProduct2.setIntInvestmentItem(intInvestmentItem3);
        investmentProduct2.setProductNumber("INV_NUM_002");

        investmentProduct3.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct3.setIsValidFlag(false);
        investmentProduct3.setIntInvestmentItem(intInvestmentItem4);
        investmentProduct3.setProductNumber("INV_NUM_003");

        investmentProduct4.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct4.setIsValidFlag(true);
        investmentProduct4.setIntInvestmentItem(intInvestmentItem5);
        investmentProduct4.setProductNumber("INV_NUM_004");

        investmentProduct5.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct5.setIsValidFlag(true);
        investmentProduct5.setIntInvestmentItem(intInvestmentItem6);
        investmentProduct5.setProductNumber("INV_NUM_005");

        investmentProduct6.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct6.setIsValidFlag(false);
        investmentProduct6.setIntInvestmentItem(intInvestmentItem7);
        investmentProduct6.setProductNumber("INV_NUM_006");

        investmentProduct7.setInvestmentProductStatus(InvestmentProductStatus.CREATED);
        investmentProduct7.setIsValidFlag(true);
        investmentProduct7.setIntInvestmentItem(intInvestmentItem7);
        investmentProduct7.setProductNumber("INV_NUM_007");

        investmentCRUDService.create(investmentDocument);
        investmentCRUDService.create(investmentDocument2);
        investmentCRUDService.create(investmentDocument3);

        System.out.println("RUNNING SPRING INTEGRATION TESTS MODE...");
    }
}
