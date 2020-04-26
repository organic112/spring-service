package com.potato112.springservice.repository.services;

import com.potato112.springservice.config.AppConfig;
import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import com.potato112.springservice.repository.entities.auth.*;
import com.potato112.springservice.repository.interfaces.crud.CRUDService;
import com.potato112.springservice.repository.interfaces.crud.CRUDServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@EnableTransactionManagement
@Rollback
@SpringBootTest
public class UserCRUDServiceTest {

    @Autowired
    private UserCRUDService userCRUDService;

    @Autowired
    private CRUDServiceBean<UserGroup> userGroupCRUDService;

    @Autowired
    private CRUDService<UserGroupMapping> mappingCRUDService;


    @Test
    public void shouldAddUser() {

        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPassword("98ACDA0612B5263009C0E9F605F6844B8DAFF5AE");
        user.setEmail("admin@temp.com");
        user.setLocked(UserStatus.ENABLED);
        user.setPhone("52 5556 6565 54");

        userCRUDService.save(user);

        UserGroup userGroup1 = new UserGroup();
        userGroup1.setGroupName("new_test_user_group_name");

        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setViewName(ViewName.FOO_OVERVIEW_VIEW);
        userGroup1.setGroupPermissions(Arrays.asList(groupPermission));

        userGroupCRUDService.create(userGroup1);

        UserGroupMapping userGroupMapping = new UserGroupMapping();
        userGroupMapping.setUser(user);
        userGroupMapping.setUserGroup(userGroup1);

        mappingCRUDService.create(userGroupMapping);


        System.out.println("user table size: "+ userCRUDService.findAll().size());

    }

}