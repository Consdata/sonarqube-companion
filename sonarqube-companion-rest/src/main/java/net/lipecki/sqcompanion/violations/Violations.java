package net.lipecki.sqcompanion.violations;

import lombok.Builder;
import lombok.Data;

/**
 * @author gregorry
 */
@Data
@Builder
public class Violations {

	private int blockers;
	private int criticals;
	private int majors;
	private int minors;
	private int infos;

}
