package cn.walle.framework.common.support;

import java.util.List;

import cn.walle.framework.core.support.PagingInfo;

public class StaticSelectCodeDataSource implements QueryDataSource {

	private List<StaticSelectCodeDataSourceItem> data;
	
	@Override
	public List<?> getData(List<QueryField> queryFields, String orderBy, PagingInfo pagingInfo) {
		return data;
	}

	@Override
	public Class<?> getDataItemClass() {
		return StaticSelectCodeDataSourceItem.class;
	}
	
	public List<StaticSelectCodeDataSourceItem> getData() {
		return data;
	}

	public void setData(List<StaticSelectCodeDataSourceItem> data) {
		this.data = data;
	}

}
