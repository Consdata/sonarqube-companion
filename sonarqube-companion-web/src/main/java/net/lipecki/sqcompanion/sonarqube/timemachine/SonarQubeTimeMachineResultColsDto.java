package net.lipecki.sqcompanion.sonarqube.timemachine;

public class SonarQubeTimeMachineResultColsDto {

    private String metric;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SonarQubeTimeMachineResultColsDto{");
        sb.append("metric='").append(metric).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(final String metric) {
        this.metric = metric;
    }
}
