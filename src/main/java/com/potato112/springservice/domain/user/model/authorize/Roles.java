package com.potato112.springservice.domain.user.model.authorize;

/**
 * Stores possible user roles in system
 */
public enum Roles {

    ADMIN,
    MANAGER,
    USER



/*    ADMIN("System administrator"),
    MANAGER("Manager"),
    USER("User");

    private String name;

    Roles(String name) {
        this.name = name;
    }

    public String getEnumValue() {
        return this.name();
    }

    public static Optional<Roles> get(String roleName) {
        return findByType(roleName);
    }

    public String getName() {
        return name;
    }

    public static Optional<Roles> findByType(String name) {

        Roles[] values = values();
        for (Roles type : values) {
            if (type.getName().equalsIgnoreCase(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }*/

}
