package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class SQProjectsProvisionedResponse extends SQPaginatedResponse {

    private List<SQProject> projects;

}
