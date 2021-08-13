package pl.consdata.ico.sqcompanion.violation.user;

public interface ViolationSource {
    Integer getBlockers();

    Integer getCriticals();

    Integer getMajors();

    Integer getMinors();

    Integer getInfos();
}
