package com.consdata.echo.storage.synchronization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SynchronizationStatusRepository extends JpaRepository<StatusEntity, String> {
}
