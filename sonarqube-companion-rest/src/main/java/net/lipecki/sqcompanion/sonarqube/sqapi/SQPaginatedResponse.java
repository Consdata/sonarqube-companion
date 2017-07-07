package net.lipecki.sqcompanion.sonarqube.sqapi;

/**
 * @author gregorry
 */
public abstract class SQPaginatedResponse {

	private SQPaging paging;

    public SQPaging getPaging() {
        return paging;
    }

    public void setPaging(SQPaging paging) {
        this.paging = paging;
    }

    public boolean hasMorePages() {
        return paging.getPageIndex() < paging.getTotal();
    }

    public int getNextPage() {
        return paging.getPageIndex() + 1;
    }

}
