package pl.consdata.ico.sqcompanion.violation;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.violation.user.ViolationHistorySource;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

@Data
@Builder
public class ViolationHistoryEntry implements ViolationHistorySource {

    private LocalDate date;
    private Violations violations;

    public static ViolationHistoryEntry sumEntries(final ViolationHistoryEntry a, final ViolationHistoryEntry b) {
        if (!Objects.equals(a.getDate(), b.getDate())) {
            throw new SQCompanionException("Can't sum violation entries from different dates");
        }
        return ViolationHistoryEntry
                .builder()
                .date(a.getDate())
                .violations(Violations.sumViolations(a.getViolations(), b.getViolations()))
                .build();
    }

    public static ViolationHistoryEntry of(final ViolationHistorySource violationHistorySource) {
        return ViolationHistoryEntry
                .builder()
                .date(violationHistorySource.getDate())
                .violations(
                        Violations
                                .builder()
                                .blockers(ofNullable(violationHistorySource.getBlockers()).orElse(0))
                                .criticals(ofNullable(violationHistorySource.getCriticals()).orElse(0))
                                .majors(ofNullable(violationHistorySource.getMajors()).orElse(0))
                                .minors(ofNullable(violationHistorySource.getMinors()).orElse(0))
                                .infos(ofNullable(violationHistorySource.getInfos()).orElse(0))
                                .build()
                )
                .build();
    }

    public static List<ViolationHistoryEntry> groupByDate(Stream<? extends ViolationHistorySource> violationHistorySources) {
        return violationHistorySources
                .map(ViolationHistoryEntry::of)
                .collect(
                        Collectors.groupingBy(
                                ViolationHistoryEntry::getDate,
                                Collectors.reducing(ViolationHistoryEntry::sumEntries)
                        )
                )
                .values()
                .stream()
                .filter(entry -> entry.isPresent())
                .map(entry -> entry.get())
                .sorted(Comparator.comparing(ViolationHistoryEntry::getDate))
                .collect(Collectors.toList());
    }

    public static List<ViolationHistoryEntry> groupByDate(Collection<? extends ViolationHistorySource> violationHistorySources) {
        return groupByDate(violationHistorySources.stream());
    }

    public String getDateString() {
        return date.toString();
    }

    @Override
    public Integer getBlockers() {
        return violations.getBlockers();
    }

    @Override
    public Integer getCriticals() {
        return violations.getCriticals();
    }

    @Override
    public Integer getMajors() {
        return violations.getMajors();
    }

    @Override
    public Integer getMinors() {
        return violations.getMinors();
    }

    @Override
    public Integer getInfos() {
        return violations.getInfos();
    }

}
