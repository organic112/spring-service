package com.potato112.springservice.domain.user.model.authorize;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDetailsVO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String selectedOrganizationId;
    private List<UserGroupVO> userGroupMappings = new ArrayList<>();


}
