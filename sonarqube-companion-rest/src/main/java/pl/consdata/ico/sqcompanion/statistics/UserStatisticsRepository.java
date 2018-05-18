package pl.consdata.ico.sqcompanion.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsEntryEntity;

import java.time.LocalDate;
import java.util.List;

public interface UserStatisticsRepository extends JpaRepository<UserStatisticsEntryEntity, String> {
    List<UserStatisticsEntryEntity> findAllByProjectKeyAndDateIsBetween(final String projectKey, final LocalDate begin, final LocalDate end);
}
