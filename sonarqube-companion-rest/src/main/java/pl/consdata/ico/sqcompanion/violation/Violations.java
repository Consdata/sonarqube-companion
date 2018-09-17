package pl.consdata.ico.sqcompanion.violation;

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

    public void addBlockers(int blockers) {
        this.blockers += blockers;
    }

    public void addCriticals(int criticals) {
        this.criticals += criticals;
    }

    public void addMajors(int majors) {
        this.majors += majors;
    }

    public void addMinors(int minors) {
        this.minors += minors;
    }

    public void addInfos(int infos) {
        this.infos += infos;
    }

}
