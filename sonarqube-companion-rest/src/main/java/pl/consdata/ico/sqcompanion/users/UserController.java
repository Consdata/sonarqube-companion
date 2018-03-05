package pl.consdata.ico.sqcompanion.users;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatistics;
import pl.consdata.ico.sqcompanion.users.metrics.UserStatisticsService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;
    private UserStatisticsService userStatisticsService;

    public UserController(
            final UserService userService,
            final UserStatisticsService userStatisticsService) {
        this.userService = userService;
        this.userStatisticsService = userStatisticsService;
    }

    @RequestMapping(
            value = "/{name}/stats",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns user statistics"
    )
    public UserStatistics getUserStatistics(@PathVariable String name) {
        return null;
    }

}
