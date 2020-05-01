package com.potato112.springservice.domain.user.model.authorize;

import com.potato112.springservice.domain.common.SysMapper;
import com.potato112.springservice.domain.user.api.GroupPermissionDto;
import com.potato112.springservice.repository.entities.auth.GroupPermission;
import org.springframework.data.repository.CrudRepository;


public class GroupPermissionMapper implements SysMapper<GroupPermission, GroupPermissionDto>  {


    @Override
    public GroupPermissionDto mapToVo(GroupPermission groupPermission) {

        GroupPermissionDto groupPermissionVO = new GroupPermissionDto();
        groupPermissionVO.setId(groupPermission.getId());
        groupPermissionVO.setViewName(groupPermission.getViewName());
        groupPermissionVO.setCanCreate(groupPermission.isCanCreate());
        groupPermissionVO.setCanUpdate(groupPermission.isCanUpdate());
        groupPermissionVO.setCanDelete(groupPermission.isCanDelete());

        return groupPermissionVO;
    }

    @Override
    public GroupPermission mapToEntity(GroupPermissionDto modelVo) {

        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setId(modelVo.getId());
        groupPermission.setViewName(modelVo.getViewName());
        groupPermission.setCanCreate(modelVo.isCanCreate());
        groupPermission.setCanUpdate(modelVo.isCanUpdate());
        groupPermission.setCanDelete(modelVo.isCanDelete());
        return groupPermission;
    }

    @Override
    public GroupPermission mapToEntity(GroupPermissionDto modelVo, CrudRepository<GroupPermission, String> crudRepository) {
        return null;
    }
}
