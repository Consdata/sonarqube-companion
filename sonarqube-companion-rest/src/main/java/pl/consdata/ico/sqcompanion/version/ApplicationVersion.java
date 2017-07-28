package pl.consdata.ico.sqcompanion.version;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author pogoma
 */
@Data
@Builder
@ToString
public class ApplicationVersion {

    private String commitId;

    private String branch;

    private String buildTimestamp;

    private String version;

}
