package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.consdata.ico.sqcompanion.group.GroupService;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

@Slf4j
@Service
public class UserStatisticsService {
    private RepositoryService repositoryService;

    public UserStatisticsService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
        GroupService groupService;

    }
}
