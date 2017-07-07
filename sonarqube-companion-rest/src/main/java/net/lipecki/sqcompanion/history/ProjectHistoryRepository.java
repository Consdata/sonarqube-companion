package net.lipecki.sqcompanion.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistoryEntry, Long> {
}
