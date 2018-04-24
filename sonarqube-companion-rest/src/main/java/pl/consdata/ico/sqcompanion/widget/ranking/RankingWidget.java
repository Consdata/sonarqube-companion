package pl.consdata.ico.sqcompanion.widget.ranking;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.widget.Widget;

import java.util.List;

@Data
@Builder
public class RankingWidget implements Widget {
    private String mode;
    private String title;
    private Integer limit;
    private List<String> severity;
}
