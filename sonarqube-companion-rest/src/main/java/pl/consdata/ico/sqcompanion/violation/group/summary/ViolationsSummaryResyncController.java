package pl.consdata.ico.sqcompanion.violation.group.summary;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/violations/summary/resync")
@RequiredArgsConstructor
public class ViolationsSummaryResyncController {
    private final SummaryResyncService summaryResyncService;

    @GetMapping(
            value = "{date}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Resyncs summaries for given date",
            notes = "<p>Resyncs summaries for given date.</p>"
    )
    public void resync(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        summaryResyncService.resync(date);
    }
}
