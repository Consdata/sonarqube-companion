package pl.consdata.ico.sqcompanion.statistics;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsEntry;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsEntryEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class UserStatisticsResponse {
    @Singular
    private List<UserStatisticsEntry> entries;


    public static UserStatisticsResponse of(List<UserStatisticsEntryEntity> entities) {
        UserStatisticsResponse response = UserStatisticsResponse.builder().entries(entities.stream().map(UserStatisticsEntry::fromEntity).collect(Collectors.toList())).build();
        return response;
    }

    public static UserStatisticsResponse agregateByUserOf(List<UserStatisticsEntryEntity> entities) {
        List<UserStatisticsEntry> responseEntries = entities.stream().map(UserStatisticsEntry::fromEntity).collect(Collectors.toList());
        Map<String, List<UserStatisticsEntry>> groups = responseEntries.stream().collect(Collectors.groupingBy(UserStatisticsEntry::getUser));
        List<UserStatisticsEntry> output = groups.keySet().stream().map(user -> agregateEntries(groups.get(user))).collect(Collectors.toList());
        return UserStatisticsResponse.builder().entries(output).build();
    }

    private static UserStatisticsEntry agregateEntries(List<UserStatisticsEntry> entries) {
        UserStatisticsEntry output = UserStatisticsEntry.empty();
        for (UserStatisticsEntry entry : entries) {
            output.setUser(entry.getUser());
            output.setProjectKey(entry.getProjectKey());
            output.setBlockers(output.getBlockers() + entry.getBlockers());
            output.setCriticals(output.getCriticals() + entry.getCriticals());
            output.setMajors(output.getMajors() + entry.getMajors());
            output.setMinors(output.getMinors() + entry.getMinors());
            output.setInfo(output.getInfo() + entry.getInfo());
        }

        return output;
    }
}
