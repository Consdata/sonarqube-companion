package pl.consdata.ico.sqcompanion.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class LocalDateUtil {

    public static Date asDate(final LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(final Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
