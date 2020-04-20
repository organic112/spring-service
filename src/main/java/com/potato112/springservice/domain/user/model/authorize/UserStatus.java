package com.potato112.springservice.domain.user.model.authorize;

import java.util.Optional;

public enum UserStatus {

    DISABLED("Locked"), ENABLED("Unlocked");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public static Optional<UserStatus> findByType(String name) {
        UserStatus[] values = values();
        for (UserStatus type : values) {
            if (type.name().equalsIgnoreCase(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

}
