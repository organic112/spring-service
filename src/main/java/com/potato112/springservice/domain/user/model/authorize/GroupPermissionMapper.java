package com.potato112.springservice.domain.user.model.authorize;

import com.potato112.springservice.domain.common.SysMapper;
import com.potato112.springservice.repository.entities.auth.GroupPermission;
import org.springframework.data.repository.CrudRepository;

public class GroupPermissionMapper implements SysMapper<GroupPermission, GroupPermissionVO>  {

    @Override
    public GroupPermissionVO mapToVo(GroupPermission groupPermission) {

        GroupPermissionVO groupPermissionVO = new GroupPermissionVO();
        groupPermissionVO.setId(groupPermission.getId());
        groupPermissionVO.setViewName(groupPermission.getViewName());
        groupPermissionVO.setCanCreate(groupPermission.isCanCreate());
        groupPermissionVO.setCanUpdate(groupPermission.isCanUpdate());
        groupPermissionVO.setCanDelete(groupPermission.isCanDelete());

    }

    @Override
    public GroupPermission mapToEntity(GroupPermissionVO modelVo) {
        return null;
    }

    @Override
    public GroupPermission mapToEntity(GroupPermissionVO modelVo, CrudRepository<GroupPermission, String> crudRepository) {
        return null;
    }
}
