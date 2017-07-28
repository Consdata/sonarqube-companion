package net.lipecki.sqcompanion.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistoryEntry, Long> {

	Optional<ProjectHistoryEntry> findFirstByProjectKeyOrderByDateDesc(String projectKey);

	List<ProjectHistoryEntry> findAllByProjectKey(final String projectKey);

}
