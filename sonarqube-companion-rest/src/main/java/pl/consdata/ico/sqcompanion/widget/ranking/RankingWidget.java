package pl.consdata.ico.sqcompanion.widget.ranking;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.widget.Widget;

import java.util.List;

@Data
@Builder
public class RankingWidget extends Widget {
    private String mode;
    private Integer limit;
    private String from;
    private List<String> severity;
    private List<String> include;
    private List<String> exclude;
    private String server;
    private List<String> sort;
}
