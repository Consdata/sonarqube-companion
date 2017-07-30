package pl.consdata.ico.sqcompanion.sonarqube.sqapi;

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
        int maxPages = paging.getTotal() % paging.getPageSize() == 0 ? paging.getTotal() / paging.getPageSize() : paging.getTotal() / paging.getPageSize() + 1;
        return paging.getPageIndex() < maxPages;
    }

    public int getNextPage() {
        return paging.getPageIndex() + 1;
    }

}
