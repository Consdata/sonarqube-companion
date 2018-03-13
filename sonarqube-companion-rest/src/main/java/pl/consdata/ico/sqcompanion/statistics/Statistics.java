package pl.consdata.ico.sqcompanion.statistics;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatistics;

@Data
@Builder
public class Statistics {
    private UserStatistics userStatistics;
}
