package com.consdata.echo.api;

import lombok.Getter;

public enum Role {

    UNIT_LEAD(Authorities.VIEW_SUB_UNITS, Authorities.VIEW_PARENT_UNITS),

    MANAGER(Authorities.VIEW_ALL_UNITS),

    ADMIN(Authorities.VIEW_ALL_UNITS, Authorities.TRIGGER_SYNCHRONIZATION),

    USER(Authorities.VIEW_PARENT_UNITS);

    @Getter
    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String asRole() {
        return "ROLE_" + name().toUpperCase();
    }
}
