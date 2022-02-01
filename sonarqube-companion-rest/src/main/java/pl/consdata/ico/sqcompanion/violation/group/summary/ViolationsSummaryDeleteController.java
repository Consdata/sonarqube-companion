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
@RequestMapping("/api/v1/violations/summary/delete")
@RequiredArgsConstructor
public class ViolationsSummaryDeleteController {
    private final RemoveSummariesService removeSummariesService;

    @GetMapping(
            value = "{date}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation(
            value = "Deletes summaries for given date",
            notes = "<p>Deletes summaries for given date.</p>"
    )
    public void delete(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) {
        removeSummariesService.delete(date);
    }
}
