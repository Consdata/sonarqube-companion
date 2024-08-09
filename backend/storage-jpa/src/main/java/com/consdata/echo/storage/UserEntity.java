package com.consdata.echo.storage;


import com.consdata.echo.organization.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users") //, schema = "organization"
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String lastName;

    public static UserEntity of(User user) {
        UserEntity entity = new UserEntity();
        entity.id = user.id();
        entity.name = user.name();
        entity.lastName = user.lastName();
        return entity;
    }

    public static User toUser(UserEntity entity) {
        return new User(
                entity.id,
                entity.name,
                entity.lastName
        );
    }
}
