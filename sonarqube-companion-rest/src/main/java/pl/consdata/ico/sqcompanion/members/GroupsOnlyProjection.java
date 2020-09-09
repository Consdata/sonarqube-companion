package pl.consdata.ico.sqcompanion.members;

import java.time.LocalDate;

public interface GroupsOnlyProjection {
    String getGroupId();
    LocalDate getDate();
}
