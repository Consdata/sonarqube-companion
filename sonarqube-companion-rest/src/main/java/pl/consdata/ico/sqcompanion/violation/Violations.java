package pl.consdata.ico.sqcompanion.violation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.consdata.ico.sqcompanion.violation.user.ViolationHistorySource;
import pl.consdata.ico.sqcompanion.violation.user.ViolationSource;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Violations implements ViolationSource {

    private Integer blockers;
    private Integer criticals;
    private Integer majors;
    private Integer minors;
    private Integer infos;

    public static Violations sumViolations(final ViolationSource a, final ViolationSource b) {
        return Violations
                .builder()
                .blockers(a.getBlockers() + b.getBlockers())
                .criticals(a.getCriticals() + b.getCriticals())
                .majors(a.getMajors() + b.getMajors())
                .minors(a.getMinors() + b.getMinors())
                .infos(a.getInfos() + b.getInfos())
                .build();
    }

    public static Violations empty(){
        return Violations.builder()
                .blockers(0)
                .criticals(0)
                .majors(0)
                .minors(0)
                .infos(0)
                .build();
    }

    public void addViolations(final ViolationSource violations) {
                setBlockers(getBlockers() + violations.getBlockers());
                setCriticals(getCriticals() + violations.getCriticals());
                setMajors(getMajors() + violations.getMajors());
                setMinors(getMinors() + violations.getMinors());
                setInfos(getInfos() + violations.getInfos());
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
