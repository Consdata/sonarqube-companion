package pl.consdata.ico.sqcompanion.statistics;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.consdata.ico.sqcompanion.SQCompanionException;
import pl.consdata.ico.sqcompanion.repository.Project;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/statistics")
public class StatisticsController {


    private UserStatisticsService userStatisticsService;
    private RepositoryService repositoryService;

    public StatisticsController(UserStatisticsService userStatisticsService,
                                RepositoryService repositoryService) {
        this.userStatisticsService = userStatisticsService;
        this.repositoryService = repositoryService;
    }


    @RequestMapping(
            value = "/users/{uuid}/{projectKey:.+}/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<UserStatisticsResponse> getUsersStatistics(
            @PathVariable final String uuid,
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate
    ) {

        final Optional<Project> project = repositoryService.getProject(uuid, projectKey);
        if (project.isPresent()) {
            UserStatisticsResponse resp = UserStatisticsResponse.of(userStatisticsService.getProjectUserStatisticsDiff(project.get(), fromDate, toDate));
            return ResponseEntity.ok(resp);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }


    @RequestMapping(
            value = "/users/agregate/{uuid}/{projectKey:.+}/{fromDate}/{toDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<UserStatisticsResponse> getAgregatedUsersStatistics(
            @PathVariable final String uuid,
            @PathVariable final String projectKey,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate toDate
    ) {

        final Optional<Project> project = repositoryService.getProject(uuid, projectKey);
        if (project.isPresent()) {
            UserStatisticsResponse resp = UserStatisticsResponse.agregateByUserOf(userStatisticsService.getProjectUserStatisticsDiff(project.get(), fromDate, toDate));
            return ResponseEntity.ok(resp);
        } else {
            throw new SQCompanionException("Can't find project: " + projectKey + " in group: " + uuid);
        }
    }
}
