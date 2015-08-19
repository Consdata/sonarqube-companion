package net.lipecki.sqcompanion.sonarqube.timemachine;

import java.util.Arrays;
import java.util.Date;

public class SonarQubeTimeMachineResultCellsDto {

    private Date d;

    private String[] v;

    public Date getD() {
        return d;
    }

    public void setD(final Date d) {
        this.d = d;
    }

    public String[] getV() {
        return v;
    }

    public void setV(final String[] v) {
        this.v = v;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SonarQubeTimeMachineResultCellsDto{");
        sb.append("d=").append(d);
        sb.append(", v=").append(v == null ? "null" : Arrays.asList(v).toString());
        sb.append('}');
        return sb.toString();
    }

}
