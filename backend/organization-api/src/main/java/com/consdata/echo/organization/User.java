package com.consdata.echo.organization;

public record User(
        String id,
        String name,
        String lastName
) {
    @Override
    public boolean equals(Object obj) {
        return switch (obj) {
            case User user -> id.equals(user.id());
            default -> false;
        };
    }
}
