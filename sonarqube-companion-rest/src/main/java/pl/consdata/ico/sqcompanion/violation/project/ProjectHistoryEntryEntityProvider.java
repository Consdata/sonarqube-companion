package pl.consdata.ico.sqcompanion.violation.project;

import pl.consdata.ico.sqcompanion.repository.Project;

import java.time.LocalDate;
import java.util.Optional;

public interface ProjectHistoryEntryEntityProvider {
    Optional<ProjectHistoryEntryEntity> getEntity(Project project, LocalDate date);
}
