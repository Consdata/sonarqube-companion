package pl.consdata.ico.sqcompanion.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findAllByServerId(final String serverId);

    void deleteAllByServerId(final String serverId);

}
