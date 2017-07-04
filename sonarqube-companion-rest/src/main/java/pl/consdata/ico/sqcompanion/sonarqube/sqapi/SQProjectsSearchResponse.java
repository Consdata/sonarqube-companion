package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
public class SQProjectsSearchResponse extends SQPaginatedResponse {

	private List<SQComponent> components;

}
