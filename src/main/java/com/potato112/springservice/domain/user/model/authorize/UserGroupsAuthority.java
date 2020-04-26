package com.potato112.springservice.domain.user.model.authorize;

import org.springframework.security.core.GrantedAuthority;

public class UserGroupsAuthority implements GrantedAuthority {

    GroupPermissionVO groupPermissions;

    public UserGroupsAuthority(GroupPermissionVO groupPermissionsVO) {
        this.groupPermissions = groupPermissionsVO;
    }

    String getAuthorityAsString(GroupPermissionVO groupPermissionVO) {

        boolean canCreate = groupPermissionVO.isCanCreate();
        boolean canUpdate = groupPermissionVO.isCanUpdate();
        boolean canDelete = groupPermissionVO.isCanDelete();
        String viewName = groupPermissionVO.getViewName().getName();
/*        String permissions = String.format("c:%s-u:%s-d:%s", canCreate, canUpdate, canDelete);
        String viewNameToPermissions = String.join("_", viewName, permissions);
        return viewNameToPermissions;*/

        return viewName;

    }

    @Override
    public String getAuthority() {
        return getAuthorityAsString(groupPermissions);
    }
}


    /*private final GroupType groupType;
    private final String role;

    public GroupAuthority(GroupType groupType, String role) {
        this.groupType = groupType;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return String.format("%s_%s_%s", "ROLE", groupType.name(), role);
    }*/

