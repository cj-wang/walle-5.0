package cn.walle.framework.common.support;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.walle.framework.core.model.BaseObject;
import cn.walle.framework.core.support.PagingInfo;

public class QueryInfo extends BaseObject {
	
	private String queryType;
	private Class<?> queryTypeClass;
	private List<QueryField> queryFields;
	private String orderBy;
	private PagingInfo pagingInfo;
	private Map<String, String> fieldCodeTypes;

	public QueryInfo() {
	}

	public QueryInfo(Class<?> queryTypeClass, QueryField... queryFields) {
		this.queryTypeClass = queryTypeClass;
		this.queryFields = Arrays.asList(queryFields);
	}
	
	public QueryInfo(Class<?> queryTypeClass, String orderBy, QueryField... queryFields) {
		this.queryTypeClass = queryTypeClass;
		this.orderBy = orderBy;
		this.queryFields = Arrays.asList(queryFields);
	}
	
	public QueryInfo(Class<?> queryTypeClass, String orderBy, PagingInfo pagingInfo, QueryField... queryFields) {
		this.queryTypeClass = queryTypeClass;
		this.orderBy = orderBy;
		this.pagingInfo = pagingInfo;
		this.queryFields = Arrays.asList(queryFields);
	}
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public Class<?> getQueryTypeClass() {
		return queryTypeClass;
	}
	public void setQueryTypeClass(Class<?> queryTypeClass) {
		this.queryTypeClass = queryTypeClass;
	}
	public List<QueryField> getQueryFields() {
		return queryFields;
	}
	public void setQueryFields(List<QueryField> queryFields) {
		this.queryFields = queryFields;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}
	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}
	public Map<String, String> getFieldCodeTypes() {
		return fieldCodeTypes;
	}
	public void setFieldCodeTypes(Map<String, String> fieldCodeTypes) {
		this.fieldCodeTypes = fieldCodeTypes;
	}

}
