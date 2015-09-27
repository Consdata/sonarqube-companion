package net.lipecki.sqcompanion.repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gregorry on 26.09.2015.
 */
public class History {

    private final Map<LocalDate, HistoryPoint> historyPoints = new HashMap<>();

    public Map<LocalDate, HistoryPoint> getHistoryPoints() {
        return Collections.unmodifiableMap(historyPoints);
    }

    History() {
    }

    public static History of(final Map<LocalDate, HistoryPoint> historyPoints) {
        final History history = new History();
        history.historyPoints.putAll(historyPoints);
        return history;
    }

}
