package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author gregorry
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SQComponentSearchResponse extends SQPaginatedResponse {

    private List<SQComponent> components;

}
