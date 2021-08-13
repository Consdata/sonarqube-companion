package pl.consdata.ico.sqcompanion.violation.user;

import java.time.LocalDate;

public interface ViolationHistorySource extends ViolationSource {

    LocalDate getDate();
}
