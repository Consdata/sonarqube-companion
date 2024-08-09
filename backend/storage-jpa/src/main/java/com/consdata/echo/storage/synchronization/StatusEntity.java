package com.consdata.echo.storage.synchronization;

import com.consdata.echo.synchronization.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "status")
public class StatusEntity {
    @Id
    String id;
    int progress;
    boolean pending;

    public static StatusEntity of(Status status) {
        StatusEntity entity = new StatusEntity();
        entity.id = "0";
        entity.pending = status.pending();
        entity.progress = status.progress();
        return entity;
    }

    public static Status toStatus(StatusEntity entity) {
        return new Status(entity.progress, entity.pending);
    }

}
