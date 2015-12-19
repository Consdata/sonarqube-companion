package net.lipecki.sqcompanion.sonarqube.issue;

/**
 * Created by gregorry on 27.09.2015.
 */
public class SonarQubeIssuesPagingResultDto {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer total;


    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(final Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

}
