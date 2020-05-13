package com.potato112.springservice.config;

import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import com.potato112.springservice.jms.bulkaction.BulkActionExecutor;
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

        System.out.println("RUNNING SPRING INTEGRATION TESTS MODE...");
    }
}
