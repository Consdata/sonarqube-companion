package net.lipecki.sqcompanion.sonarqube.sqapi;

import lombok.Data;

/**
 * @author gregorry
 */
@Data
public abstract class SQPaginatedResponse {

	private SQPaging paging;

    public boolean hasMorePages() {
        return paging.getPageIndex() < paging.getTotal();
    }

    public int getNextPage() {
        return paging.getPageIndex() + 1;
    }

}
