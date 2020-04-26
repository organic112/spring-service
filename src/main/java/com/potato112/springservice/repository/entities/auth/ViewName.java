package com.potato112.springservice.repository.entities.auth;

import java.util.Optional;

public enum ViewName {
    USER_VIEW("USER_VIEW"),
    GROUP_VIEW("Group view"),
    FOO_OVERVIEW_VIEW("FOO_OVERVIEW_VIEW");

    private String viewName;

    ViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getEnumValue() {
        return this.name();
    }

    public static Optional<ViewName> get(String roleName) {
        return findByType(roleName);
    }

    public String getName() {
        return viewName;
    }

    public static Optional<ViewName> findByType(String viewName) {

        ViewName[] values = values();
        for (ViewName type : values) {
            if (type.getName().equalsIgnoreCase(viewName)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
