package com.potato112.springservice.domain.user.model;


import com.potato112.springservice.domain.user.model.authorize.GroupPermissionVO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class UserGroupVo {

    private String id;

    private String groupName;

    private List<GroupPermissionVO> groupPermissions

}
