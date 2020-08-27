package pl.consdata.ico.sqcompanion.version;

import lombok.*;

/**
 * @author pogoma
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationVersion {

    private String commitId;

    private String branch;

    private String buildTimestamp;

    private String version;

}
