package com.potato112.springservice.domain.user.model;

import org.springframework.security.core.GrantedAuthority;

public class GroupAuthority implements GrantedAuthority {

    private final GroupType groupType;
    private final String role;

    public GroupAuthority(GroupType groupType, String role) {
        this.groupType = groupType;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return String.format("%s_%s_%s", "ROLE", groupType.name(), role);
    }
}
