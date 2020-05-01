package com.potato112.springservice.domain.user.model;

import com.potato112.springservice.domain.common.SysMapper;
import com.potato112.springservice.domain.user.api.GroupDto;
import com.potato112.springservice.domain.user.api.GroupPermissionDto;
import com.potato112.springservice.domain.user.model.authorize.GroupPermissionMapper;
import com.potato112.springservice.repository.entities.auth.GroupPermission;
import com.potato112.springservice.repository.entities.auth.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserGroupMapper implements SysMapper<UserGroup, GroupDto> {

    @Override
    public GroupDto mapToVo(UserGroup userGroup) {

        GroupDto userGroupVo = new GroupDto();
        userGroupVo.setId(userGroup.getId());
        userGroupVo.setGroupName(userGroup.getGroupName());

        List<GroupPermission> groupPermissions = userGroup.getGroupPermissions();

        List<GroupPermissionDto> groupPermissionVOS = getGroupPermissionVOS(groupPermissions);
        userGroupVo.setGroupPermissions(groupPermissionVOS);
        return userGroupVo;
    }

    private List<GroupPermissionDto> getGroupPermissionVOS(List<GroupPermission> groupPermissions) {

        return groupPermissions.stream()
                .map(groupPermission -> new GroupPermissionMapper().mapToVo(groupPermission))
                .collect(Collectors.toList());
    }

    @Override
    public UserGroup mapToEntity(GroupDto modelVo) {

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName(modelVo.getGroupName());
        userGroup.setId(modelVo.getId());

        List<GroupPermissionDto> groupPermissions = modelVo.getGroupPermissions();

        List<GroupPermission> groupPermissionEntities = groupPermissions.stream()
                .map(permissionDto -> new GroupPermissionMapper().mapToEntity(permissionDto))
                .collect(Collectors.toList());

        userGroup.setGroupPermissions(groupPermissionEntities);
        return userGroup;
    }

    @Override
    public UserGroup mapToEntity(GroupDto modelVo, CrudRepository<UserGroup, String> crudRepository) {


        return null;
    }
}
