package pl.consdata.ico.sqcompanion.widget.ranking;

import lombok.Data;
import pl.consdata.ico.sqcompanion.statistics.UserStatisticsDependency;
import pl.consdata.ico.sqcompanion.widget.Widget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
public class RankingWidget extends Widget implements UserStatisticsDependency {
    public static final String TYPE = "ranking";
    private Integer limit;
    private String from = DateTimeFormatter.ISO_DATE.format(LocalDate.now().minusYears(1L));
    private List<String> severity = Arrays.asList("blockers", "criticals", "majors", "minors", "infos");
    private List<String> include;
    private List<String> exclude;
    private String server;
    private List<String> sort = Arrays.asList("-blockers", "-criticals", "-majors", "-minors", "-infos");

}
