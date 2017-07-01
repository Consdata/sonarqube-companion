package pl.consdata.ico.sqcompanion.repository;

import pl.consdata.ico.sqcompanion.config.AppConfig;
import pl.consdata.ico.sqcompanion.group.Group;

public class RepositoryService {

    private final AppConfig appConfig;

    public RepositoryService(final AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    // TODO: getGroupDetails

    public Group getGroupSummary() {
        final Group group = Group.fromDefinition(appConfig.getRootGroup());
        // TODO: link with projects
        // TODO: fill projects with data and issues
        return group;
    }

}
