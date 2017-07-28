package pl.consdata.ico.sqcompanion.violations;

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

	public static Violations sumViolations(final Violations a, final Violations b) {
		return Violations
				.builder()
				.blockers(a.getBlockers() + b.getBlockers())
				.criticals(a.getCriticals() + b.getCriticals())
				.majors(a.getMajors() + b.getMajors())
				.minors(a.getMinors() + b.getMinors())
				.infos(a.getInfos() + b.getInfos())
				.build();
	}

}
