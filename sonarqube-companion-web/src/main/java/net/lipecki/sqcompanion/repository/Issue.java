package net.lipecki.sqcompanion.repository;

import java.util.Date;
import java.util.Optional;

/**
 * Created by gregorry on 26.09.2015.
 */
public class Issue {

    /**
     * Unique issue key withinSonarQube.
     * <p>
     * "key": "26c3aff8-6250-4b81-9fc2-d21cedbfb307"
     * </p>
     */
    private final String key;

    /**
     * <p>
     * "component": "pl.consdata.eximee.webforms:webforms-desktop-client:src/main/java/pl/consdata/eximee/webforms/client/model/form/statement/StatementFlatDesktopClientModel.java",
     * </p>
     */
    private final String component;

    /**
     * <p>
     * "message": "Nullcheck of StatementFlatDesktopClientModel.statementContainerPanel at line 60 of value previously dereferenced in pl.consdata.eximee.webforms.client.model.form.statement.StatementFlatDesktopClientModel.refreshView()"
     * </p>
     */
    private final String message;

    /**
     * <p>
     * "severity": "CRITICAL"
     * </p>
     */
    private final IssueSeverity severity;

    /**
     * <p>
     * "creationDate": "2015-04-07T15:03:11+0200"
     * </p>
     */
    private final Date creationDate;

    /**
     * Issue project reference.
     * <p>
     * Optional reference.
     * </p>
     */
    private Project project;

    public Issue(final String key, final String component, final String message, final IssueSeverity severity, final Date creationDate) {
        this.key = key;
        this.component = component;
        this.message = message;
        this.severity = severity;
        this.creationDate = creationDate;
    }

    public String getKey() {
        return key;
    }

    public String getComponent() {
        return component;
    }

    public String getMessage() {
        return message;
    }

    public IssueSeverity getSeverity() {
        return severity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }

    public void setProject(final Project project) {
        this.project = project;
    }
}
