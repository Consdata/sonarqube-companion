package pl.consdata.ico.sqcompanion.violation.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectHistoryRepository extends JpaRepository<UserProjectHistoryEntryEntity, Long> {

    List<UserProjectHistoryEntryEntity> findByUserId(String userId);

}
