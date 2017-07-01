package pl.consdata.ico.sqcompanion.project;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectIssues {

    private List<Issue> blockers;
    private List<Issue> criticals;
    private List<Issue> majors;
    private List<Issue> minors;

}
