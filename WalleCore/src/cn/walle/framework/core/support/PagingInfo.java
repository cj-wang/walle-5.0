package cn.walle.framework.core.support;

import cn.walle.framework.core.model.BaseObject;

/**
 * Object to hold paging parameters.
 * @author cj
 *
 */
public class PagingInfo extends BaseObject {
	
	private int pageSize;
	
	private int currentPage;
	
	private int totalRows;
	
	public PagingInfo() {
	}
	
	public PagingInfo(int pageSize, int currentPage) {
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}
	
	public int getCurrentRow() {
		if (currentPage <= 0) {
			return 0;
		}
		return pageSize * (currentPage - 1);
	}
	
	public int getTotalPages() {
		if (totalRows <= 0 || pageSize <= 0) {
			return 0;
		}
		return (totalRows - 1) / pageSize + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}
