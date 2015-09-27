package net.lipecki.sqcompanion.repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by gregorry on 26.09.2015.
 */
public class History {

    private final Map<LocalDate, HistoryPoint> historyPoints = new HashMap<>();

    private final List<HistoryPoint> orderedHistoryPoints = new ArrayList<>();

    public Map<LocalDate, HistoryPoint> getHistoryPoints() {
        return Collections.unmodifiableMap(historyPoints);
    }

    public List<HistoryPoint> getOrderedHistoryPoints() {
        return Collections.unmodifiableList(orderedHistoryPoints);
    }

    History() {
    }

    public static History of(final Map<LocalDate, HistoryPoint> historyPoints) {
        final History history = new History();
        history.historyPoints.putAll(historyPoints);
        history.orderedHistoryPoints.addAll(history.historyPoints
                .values()
                .stream()
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .collect(Collectors.toList()));
        return history;
    }

}
