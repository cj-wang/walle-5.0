package cn.walle.framework.common.support;

import java.util.List;

import cn.walle.framework.core.support.PagingInfo;

/**
 * @author wangchangjiang
 * 自定义查询接口，实现此接口，可以通过commonquerymanager.query来查询
 *
 */
public interface QueryDataSource {

	List<?> getData(List<QueryField> queryFields, String orderBy, PagingInfo pagingInfo);
	
	Class<?> getDataItemClass();
	
}
