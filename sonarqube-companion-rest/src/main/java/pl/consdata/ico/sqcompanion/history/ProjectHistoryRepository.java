package pl.consdata.ico.sqcompanion.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistoryEntryEntity, Long> {

    Optional<ProjectHistoryEntryEntity> findFirstByProjectKeyOrderByDateDesc(String projectKey);

    List<ProjectHistoryEntryEntity> findAllByProjectKey(final String projectKey);

    List<ProjectHistoryEntryEntity> findAllByProjectKeyAndDateGreaterThanEqual(final String projectKey, final LocalDate date);

}
