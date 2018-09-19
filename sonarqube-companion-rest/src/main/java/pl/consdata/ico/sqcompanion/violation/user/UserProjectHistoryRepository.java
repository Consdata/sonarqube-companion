package pl.consdata.ico.sqcompanion.violation.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectHistoryRepository extends JpaRepository<UserProjectHistoryEntryEntity, Long> {

    List<UserProjectHistoryEntryEntity> findByUserId(String userId);

    Optional<UserProjectHistoryEntryEntity> findFirstByUserIdAndProjectKeyOrderByDateDesc(String key, String userId);

}
