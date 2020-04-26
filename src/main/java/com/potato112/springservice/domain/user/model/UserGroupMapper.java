package com.potato112.springservice.domain.user.model;

import com.potato112.springservice.domain.common.SysMapper;
import com.potato112.springservice.domain.user.model.authorize.GroupPermissionMapper;
import com.potato112.springservice.domain.user.model.authorize.GroupPermissionVO;
import com.potato112.springservice.domain.user.model.authorize.UserGroupVO;
import com.potato112.springservice.repository.entities.auth.GroupPermission;
import com.potato112.springservice.repository.entities.auth.UserGroup;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserGroupMapper implements SysMapper<UserGroup, UserGroupVO> {

    @Override
    public UserGroupVO mapToVo(UserGroup userGroup) {

        UserGroupVO userGroupVo = new UserGroupVO();
        userGroupVo.setId(userGroup.getId());
        userGroupVo.setGroupName(userGroup.getGroupName());

        List<GroupPermission> groupPermissions = userGroup.getGroupPermissions();

        List<GroupPermissionVO> groupPermissionVOS = getGroupPermissionVOS(groupPermissions);
        userGroupVo.setGroupPermissions(groupPermissionVOS);
        return userGroupVo;
    }

    private List<GroupPermissionVO> getGroupPermissionVOS(List<GroupPermission> groupPermissions) {

        return groupPermissions.stream()
                .map(groupPermission -> new GroupPermissionMapper().mapToVo(groupPermission))
                .collect(Collectors.toList());
    }

    @Override
    public UserGroup mapToEntity(UserGroupVO modelVo) {


        return null;
    }

    @Override
    public UserGroup mapToEntity(UserGroupVO modelVo, CrudRepository<UserGroup, String> crudRepository) {


        return null;
    }
}
