package net.lipecki.sqcompanion.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gregorry on 26.09.2015.
 */
public class Issues {

    public List<Issue> getAll() {
        return Collections.unmodifiableList(all);
    }

    public List<Issue> getBlockers() {
        return Collections.unmodifiableList(blockers);
    }

    public List<Issue> getCriticals() {
        return Collections.unmodifiableList(criticals);
    }

    public List<Issue> getMajors() {
        return Collections.unmodifiableList(majors);
    }

    public List<Issue> getMinors() {
        return Collections.unmodifiableList(minors);
    }

    public List<Issue> getInfos() {
        return Collections.unmodifiableList(infos);
    }

    public List<Issue> getSignificant() {
        return Collections.unmodifiableList(significant);
    }

    public List<Issue> getNonSignificant() {
        return Collections.unmodifiableList(nonSignificant);
    }

    private final List<Issue> all = new ArrayList<>();
    private final List<Issue> blockers = new ArrayList<>();
    private final List<Issue> criticals = new ArrayList<>();
    private final List<Issue> majors = new ArrayList<>();
    private final List<Issue> minors = new ArrayList<>();
    private final List<Issue> infos = new ArrayList<>();
    private final List<Issue> significant = new ArrayList<>();
    private final List<Issue> nonSignificant = new ArrayList<>();

    Issues() {
    }

    public static Issues of(final Project project, final List<Issue> allIssues) {
        final Issues issues = new Issues();

        allIssues.forEach(issue -> {
            issue.setProject(project);
            issues.all.add(issue);
            switch (issue.getSeverity()) {
                case BLOCKER:
                    issues.blockers.add(issue);
                    break;
                case CRITICAL:
                    issues.criticals.add(issue);
                    break;
                case MAJOR:
                    issues.majors.add(issue);
                    break;
                case MINOR:
                    issues.minors.add(issue);
                    break;
                case INFO:
                    issues.infos.add(issue);
                    break;
            }
            if (issue.getSeverity().isSignificant()) {
                issues.significant.add(issue);
            } else {
                issues.nonSignificant.add(issue);
            }
        });

        return issues;
    }

    public static Issues of(final List<Issue> allIssues) {
        final Issues issues = new Issues();

        allIssues.forEach(issue -> {
            issues.all.add(issue);
            switch (issue.getSeverity()) {
                case BLOCKER:
                    issues.blockers.add(issue);
                    break;
                case CRITICAL:
                    issues.criticals.add(issue);
                    break;
                case MAJOR:
                    issues.majors.add(issue);
                    break;
                case MINOR:
                    issues.minors.add(issue);
                    break;
                case INFO:
                    issues.infos.add(issue);
                    break;
            }
            if (issue.getSeverity().isSignificant()) {
                issues.significant.add(issue);
            } else {
                issues.nonSignificant.add(issue);
            }
        });

        return issues;
    }
}
