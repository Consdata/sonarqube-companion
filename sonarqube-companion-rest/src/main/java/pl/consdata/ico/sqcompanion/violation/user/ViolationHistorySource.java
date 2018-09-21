package pl.consdata.ico.sqcompanion.violation.user;

import java.time.LocalDate;

public interface ViolationHistorySource {

    LocalDate getDate();
    Integer getBlockers();
    Integer getCriticals();
    Integer getMajors();
    Integer getMinors();
    Integer getInfos();

}
