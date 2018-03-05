package pl.consdata.ico.sqcompanion.users.metrics;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class UserStatistics {
    private String username;
    private Map<Date, String> issues;
}
