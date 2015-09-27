package net.lipecki.sqcompanion.sonarqube.timemachine;

import java.util.Arrays;

public class SonarQubeTimeMachineResultsDto {

    private SonarQubeTimeMachineResultColsDto[] cols = new SonarQubeTimeMachineResultColsDto[0];

    private SonarQubeTimeMachineResultCellsDto[] cells = new SonarQubeTimeMachineResultCellsDto[0];

    public SonarQubeTimeMachineResultColsDto[] getCols() {
        return cols;
    }

    public void setCols(final SonarQubeTimeMachineResultColsDto[] cols) {
        this.cols = cols;
    }

    public SonarQubeTimeMachineResultCellsDto[] getCells() {
        return cells;
    }

    public void setCells(final SonarQubeTimeMachineResultCellsDto[] cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SonarQubeTimeMachineResultsDto{");
        sb.append("cols=").append(cols == null ? "null" : Arrays.asList(cols).toString());
        sb.append(", cells=").append(cells == null ? "null" : Arrays.asList(cells).toString());
        sb.append('}');
        return sb.toString();
    }

}
