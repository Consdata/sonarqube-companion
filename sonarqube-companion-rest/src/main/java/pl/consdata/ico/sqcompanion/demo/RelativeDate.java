package pl.consdata.ico.sqcompanion.demo;

import pl.consdata.ico.sqcompanion.SQCompanionException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RelativeDate {

    private static final Pattern relativeDatePattern = Pattern.compile("([+-])([0-9]+)([a-z])");

    private final Function<LocalDate, LocalDate> mapper;

    public static RelativeDate of(final String relativeDate) {
        final Matcher matcher = relativeDatePattern.matcher(relativeDate);
        if (matcher.matches()) {
            final String direction = matcher.group(1);
            final String diff = matcher.group(2);
            final String timeUnit = matcher.group(3);

            return new RelativeDate(getRelativeDateMapper(direction, diff, timeUnit));
        } else {
            throw new SQCompanionException(String.format("Can't match relative date pattern [was=%s]", relativeDate));
        }
    }

    public RelativeDate(Function<LocalDate, LocalDate> mapper) {
        this.mapper = mapper;
    }

    public LocalDate from(LocalDate localDate) {
        return mapper.apply(localDate);
    }

    private static Function<LocalDate, LocalDate> getRelativeDateMapper(final String direction, final String diff, final String timeUnit) {
        if (direction.equals("-")) {
            return (localDate) -> localDate.minus(Long.valueOf(diff), getRelativeDateUnit(timeUnit));
        } else {
            return (localDate) -> localDate.plus(Long.valueOf(diff), getRelativeDateUnit(timeUnit));
        }
    }

    private static ChronoUnit getRelativeDateUnit(final String timeUnit) {
        switch (timeUnit) {
            case "d": return ChronoUnit.DAYS;
            default: throw new SQCompanionException(String.format("Unknown relative date unit [was=%s]", timeUnit));
        }
    }

}
