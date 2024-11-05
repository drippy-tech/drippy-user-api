package com.drippy.drippy_user_api.enums;

import java.util.EnumSet;

public enum Profile {
    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    Profile(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String profile) {
        return EnumSet.allOf(Profile.class)
                .stream()
                .anyMatch(e -> e.name().equals(profile.toUpperCase()));
    }
}
