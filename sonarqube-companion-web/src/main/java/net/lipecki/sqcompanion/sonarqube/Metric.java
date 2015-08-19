package net.lipecki.sqcompanion.sonarqube;

import java.util.Date;

public class Metric {

    private final String metricId;

    private final Date date;

    private final String value;

    public String getMetricId() {
        return metricId;
    }

    public Date getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public Metric(final String metricId, final Date date, final String value) {

        this.metricId = metricId;
        this.date = date;
        this.value = value;
    }

    public static Metric of(final String metricId, final Date date, final String value) {
        return new Metric(metricId, date, value);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Metric{");
        sb.append("metricId='").append(metricId).append('\'');
        sb.append(", date=").append(date);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
