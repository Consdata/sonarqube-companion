package pl.consdata.ico.sqcompanion.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsEntry;

import java.util.List;

public interface UserStatisticsRepository extends JpaRepository<UserStatisticsEntry, Long> {

    List<UserStatisticsEntry> findAllByProjectKey(final String projectKey);

    boolean existsByUser(final String user);
}
