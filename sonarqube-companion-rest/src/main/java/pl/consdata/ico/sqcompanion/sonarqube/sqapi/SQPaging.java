package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

import lombok.Builder;
import lombok.Data;

/**
 * @author gregorry
 */
@Data
@Builder
public class SQPaging {

    private int total;
    private int pageIndex;
    private int pageSize;

}
