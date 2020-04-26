package com.potato112.springservice.domain.user.model.authorize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class UserDetailsVO {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String selectedOrganizationId;
    private List<UserGroupVO> userGroups = new ArrayList<>();

}
