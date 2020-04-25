package com.potato112.springservice.domain.user.model;

import com.potato112.springservice.domain.common.SysMapper;
import com.potato112.springservice.domain.user.model.authorize.GroupPermissionMapper;
import com.potato112.springservice.domain.user.model.authorize.GroupPermissionVO;
import com.potato112.springservice.repository.entities.auth.GroupPermission;
import com.potato112.springservice.repository.entities.auth.UserGroup;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserGroupMapper implements SysMapper<UserGroup, UserGroupVo> {

    @Override
    public UserGroupVo mapToVo(UserGroup userGroup) {

        UserGroupVo userGroupVo = new UserGroupVo();
        userGroupVo.setId(userGroup.getId());
        userGroupVo.setGroupName(userGroup.getGroupName());

        List<GroupPermission> groupPermissions = userGroup.getGroupPermissions();
        List<GroupPermissionVO> groupPermissionVOS = getGropuPermissionVOS(groupPermissions);
        userGroupVo.setGroupPermissions(groupPermissionVOS);

        return userGroupVo;
    }

    private List<GroupPermissionVO> getGropuPermissionVOS(List<GroupPermission> groupPermissions) {

        return groupPermissions.stream()
                .map(groupPermission -> new GroupPermissionMapper().mapToVo(groupPermission))
                .collect(Collectors.toList());
    }

    @Override
    public UserGroup mapToEntity(UserGroupVo modelVo) {


        return null;
    }

    @Override
    public UserGroup mapToEntity(UserGroupVo modelVo, CrudRepository<UserGroup, String> crudRepository) {


        return null;
    }
}
