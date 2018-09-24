package pl.consdata.ico.sqcompanion.violation.user.summary;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.violation.ViolationsHistory;

// TODO: filter by start and or end date
// TODO: filter by project
// TODO: filter by group
@RestController
@RequestMapping("/api/v1/violations/history/user")
public class UserViolationHistoryController {

    private final UserViolationSummaryHistoryService summaryService;

    public UserViolationHistoryController(final UserViolationSummaryHistoryService summaryService) {
        this.summaryService = summaryService;
    }

    @RequestMapping(
            value = "/{user}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns user violations history"
    )
    public ViolationsHistory userViolationsHistory(@PathVariable final String user) {
        return summaryService.userViolationsHistory(user);
    }

}
