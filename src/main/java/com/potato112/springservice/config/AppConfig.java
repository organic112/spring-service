package com.potato112.springservice.config;

import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import com.potato112.springservice.jms.bulkaction.BulkActionExecutor;
import com.potato112.springservice.jms.bulkaction.model.enums.InvestmentStatus;
import com.potato112.springservice.jms.bulkaction.model.investment.IntInvestmentItem;
import com.potato112.springservice.jms.bulkaction.model.investment.InvestmentDocument;
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

        List<IntInvestmentItem> itemList1 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem1 = new IntInvestmentItem();
        intInvestmentItem1.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem1.setProductNumber(125484);
        intInvestmentItem1.setItemNumber("ITEM-01A");
        intInvestmentItem1.setInvestmentDocument(investmentDocument);

        itemList1.add(intInvestmentItem1);
        IntInvestmentItem intInvestmentItem2 = new IntInvestmentItem();
        intInvestmentItem2.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem2.setProductNumber(125485);
        intInvestmentItem2.setItemNumber("ITEM-02A");
        intInvestmentItem2.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem2);
        IntInvestmentItem intInvestmentItem3 = new IntInvestmentItem();
        intInvestmentItem3.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem3.setProductNumber(125486);
        intInvestmentItem3.setItemNumber("ITEM-03A");
        intInvestmentItem3.setInvestmentDocument(investmentDocument);
        itemList1.add(intInvestmentItem3);

        List<IntInvestmentItem> itemList2 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem4 = new IntInvestmentItem();
        intInvestmentItem4.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem4.setProductNumber(125487);
        intInvestmentItem4.setItemNumber("ITEM-04A");
        intInvestmentItem4.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem4);

        IntInvestmentItem intInvestmentItem5 = new IntInvestmentItem();
        intInvestmentItem5.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem5.setProductNumber(125488);
        intInvestmentItem5.setItemNumber("ITEM-05A");
        intInvestmentItem5.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem5);

        IntInvestmentItem intInvestmentItem6 = new IntInvestmentItem();
        intInvestmentItem6.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem6.setProductNumber(125489);
        intInvestmentItem6.setItemNumber("ITEM-06A");
        intInvestmentItem6.setInvestmentDocument(investmentDocument2);
        itemList2.add(intInvestmentItem6);

        List<IntInvestmentItem> itemList3 = new ArrayList<>();
        IntInvestmentItem intInvestmentItem7 = new IntInvestmentItem();
        intInvestmentItem7.setInvestmentStatus(InvestmentStatus.IMPORTED);
        intInvestmentItem7.setProductNumber(125410);
        intInvestmentItem7.setItemNumber("ITEM-07A");
        intInvestmentItem7.setInvestmentDocument(investmentDocument3);
        itemList3.add(intInvestmentItem7);

        // create 3 Investments for bulk action processing

        investmentDocument.setInvestmentStatus(InvestmentStatus.IMPORTED);
        investmentDocument.setInvestmentNumber("INV_001");
        investmentDocument.setInvestmentItemList(itemList1);
        investmentCRUDService.create(investmentDocument);

        investmentDocument2.setInvestmentStatus(InvestmentStatus.IMPORTED);
        investmentDocument2.setInvestmentNumber("INV_002");
        investmentDocument2.setInvestmentItemList(itemList2);
        investmentCRUDService.create(investmentDocument2);

        investmentDocument3.setInvestmentStatus(InvestmentStatus.IMPORTED);
        investmentDocument3.setInvestmentNumber("INV_003");
        investmentDocument3.setInvestmentItemList(itemList3);

        investmentCRUDService.create(investmentDocument3);

        System.out.println("RUNNING SPRING INTEGRATION TESTS MODE...");
    }
}
