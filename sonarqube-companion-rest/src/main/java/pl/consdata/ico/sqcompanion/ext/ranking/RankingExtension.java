package pl.consdata.ico.sqcompanion.ext.ranking;

import lombok.Builder;
import lombok.Data;
import pl.consdata.ico.sqcompanion.ext.Extension;

@Data
@Builder
public class RankingExtension implements Extension {
    private String mode;
    private String title;
    private Integer limit;
}
