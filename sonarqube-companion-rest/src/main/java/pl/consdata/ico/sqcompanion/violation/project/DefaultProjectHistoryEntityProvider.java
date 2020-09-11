package pl.consdata.ico.sqcompanion.violation.project;

import lombok.RequiredArgsConstructor;
import pl.consdata.ico.sqcompanion.repository.Project;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultProjectHistoryEntityProvider implements ProjectHistoryEntryEntityProvider {

    private final ProjectHistoryRepository projectHistoryRepository;

    @Override
    public Optional<ProjectHistoryEntryEntity> getEntity(Project project, LocalDate date) {
        return projectHistoryRepository
                .findByProjectKeyAndDateEquals(project.getKey(), date);
    }
}
