package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserStatistics {
    private String after;
    private List<String> severity;
    private List<String> exclude;
    private String period;
}
