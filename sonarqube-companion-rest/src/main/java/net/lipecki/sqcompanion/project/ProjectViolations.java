package net.lipecki.sqcompanion.project;

import lombok.Builder;
import lombok.Data;

/**
 * @author gregorry
 */
@Data
@Builder
public class ProjectViolations {

	private int blockers;
	private int criticals;
	private int majors;
	private int minors;
	private int infos;

}
