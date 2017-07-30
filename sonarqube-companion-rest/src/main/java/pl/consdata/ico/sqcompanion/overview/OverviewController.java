package pl.consdata.ico.sqcompanion.overview;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.consdata.ico.sqcompanion.repository.RepositoryService;

@RestController
@RequestMapping("/api/v1/overview")
public class OverviewController {

    private final RepositoryService repositoryService;
    private final OverviewService overviewService;

    public OverviewController(final RepositoryService repositoryService, final OverviewService overviewService) {
        this.repositoryService = repositoryService;
        this.overviewService = overviewService;
    }

    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Returns system overview.",
            notes = "<p>Returns groups tree with names, hierarchy and health status.</p>"
    )
    public GroupOverview getOverview() {
        return overviewService.getOverview(repositoryService.getRootGroup());
    }

}
