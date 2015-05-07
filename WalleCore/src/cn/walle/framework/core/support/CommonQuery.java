package cn.walle.framework.core.support;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.query.BaseQueryItem;
import cn.walle.framework.core.util.EntityUtils;
import cn.walle.framework.core.util.ParameterUtils;
import cn.walle.framework.core.util.SqlUtils;

public class CommonQuery<ITEM extends BaseModel> {
	
	private UniversalDao dao;
	private Class<ITEM> modelClass;
	private ITEM example;
	private BaseQueryCondition queryCondition;
	private String queryName;
	private Map<String, Object> parameters;
	private Class<ITEM> queryItemClass;
	
	private List<Condition> conditions = new ArrayList<Condition>();
	
	private String orderBy;
	private PagingInfo pagingInfo;
	
	
	public CommonQuery(UniversalDao dao, Class<ITEM> modelClass) {
		this.dao = dao;
		this.modelClass = modelClass;
		this.queryItemClass = modelClass;
	}
	
	public CommonQuery(UniversalDao dao, ITEM example) {
		this.dao = dao;
		this.example = example;
		this.queryItemClass = (Class<ITEM>) example.getClass();
	}
	
	public CommonQuery(UniversalDao dao, BaseQueryCondition queryCondition, Class<ITEM> queryItemClass) {
		this.dao = dao;
		this.queryCondition = queryCondition;
		if (! BaseModel.class.isAssignableFrom(queryItemClass)
				|| BaseModel.class.equals(queryItemClass)
				|| BaseQueryItem.class.equals(queryItemClass)) {
			queryItemClass = (Class<ITEM>) EntityUtils.getQueryItemClass(queryCondition);
		}
		this.queryItemClass = queryItemClass;
	}
	
	public CommonQuery(UniversalDao dao, String queryName, Map<String, Object> parameters) {
		this.dao = dao;
		this.queryName = queryName;
		this.parameters = parameters;
		this.queryItemClass = (Class<ITEM>) DynamicModelClass.class;
	}
	
		
	public CommonQuery<ITEM> addCondition(Condition condition) {
		if (condition.getSql() != null && condition.getSql().trim().length() > 0) {
			this.conditions.add(condition);
		}
		return this;
	}
	
	public CommonQuery<ITEM> addDynamicCondition(Condition condition) {
		if (condition.getSql() != null && condition.getSql().trim().length() > 0) {
			List<Object> parameters = condition.getParameters();
			if (parameters != null) {
				for (Object parameter : parameters) {
					if (! ParameterUtils.isParamValid(parameter)) {
						return this;
					}
				}
			}
			this.conditions.add(condition);
		}
		return this;
	}
	
	public CommonQuery<ITEM> setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}

	public CommonQuery<ITEM> setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
		return this;
	}
	
	
	public List<ITEM> query() {
		Condition condition = Condition.and(conditions);
		String sqlCondition = condition.getSql();
		Object[] parameterValues = condition.getParameters() == null ? null : condition.getParameters().toArray();
		if (modelClass != null) {
			return dao.findBySqlCondition(modelClass, sqlCondition, parameterValues, orderBy, pagingInfo);
		} else if (example != null) {
			return dao.findByExample(example, sqlCondition, parameterValues, orderBy, pagingInfo);
		} else if (queryCondition != null) {
			return dao.query(queryCondition, queryItemClass, sqlCondition, parameterValues, orderBy, pagingInfo);
		} else if (queryName != null) {
			return (List<ITEM>) dao.query(queryName, parameters, sqlCondition, parameterValues, orderBy, pagingInfo);
		} else {
			throw new RuntimeException("CommonQuery error: no condition object");
		}
	}
	
	public Class<ITEM> getDataItemClass() {
		return queryItemClass;
	}
	
	public String getRunableSql() {
		String runableSqlCondition = null;
		if (conditions.size() > 0) {
			runableSqlCondition = Condition.and(conditions).getRunableSql();
		}
		StringBuilder resultSql = new StringBuilder();
		if (modelClass != null) {
			resultSql.append("select * from ");
			resultSql.append(EntityUtils.getTableName(modelClass));
			if (runableSqlCondition != null) {
				resultSql.append(" where ");
				resultSql.append(runableSqlCondition);
			}
			addOrderBy(resultSql, orderBy);
			addPaging(resultSql, pagingInfo);
		} else if (example != null) {
			resultSql.append("select * from ");
			resultSql.append(EntityUtils.getTableName(example.getClass()));
			boolean hasPreviousConditions = false;
			CustomBeanWrapper beanWrapper = new CustomBeanWrapper(example);
			List<String> fieldNames = EntityUtils.getFieldNames(example.getClass());
			for (String fieldName : fieldNames) {
				Object fieldValue = beanWrapper.getPropertyValue(fieldName);
				if (ParameterUtils.isParamValid(fieldValue)) {
					if (hasPreviousConditions) {
						resultSql.append(" and ");
					} else {
						resultSql.append(" where ");
						hasPreviousConditions = true;
					}
					resultSql.append(SqlUtils.javaNameToDbName(fieldName) + " = " + SqlUtils.toSqlValue(fieldValue));
				}
			}
			if (runableSqlCondition != null) {
				if (hasPreviousConditions) {
					resultSql.append(" and ");
				} else {
					resultSql.append(" where ");
				}
				resultSql.append(runableSqlCondition);
			}
			addOrderBy(resultSql, orderBy);
			addPaging(resultSql, pagingInfo);
		} else if (queryCondition != null) {
			String sql = SqlUtils.getDynamicNamedSql(EntityUtils.getSqlQueryName(queryCondition), queryCondition);
			Map<String, Object> parameterValues = new HashMap<String, Object>();
			CustomBeanWrapper beanWrapper = new CustomBeanWrapper(queryCondition);
			for (PropertyDescriptor propertyDescriptor : beanWrapper.getPropertyDescriptors()) {
				parameterValues.put(propertyDescriptor.getName(), beanWrapper.getPropertyValue(propertyDescriptor.getName()));
			}
			sql = SqlUtils.getRunableSql(sql, parameterValues);
			sql = SqlUtils.addExtraConditions(sql, runableSqlCondition);
			sql = SqlUtils.addDataFilterConditions(sql);
			resultSql.append(sql);
			if (orderBy != null && orderBy.trim().length() != 0) {
				resultSql.insert(0, "select * from (");
				resultSql.append(") order by ");
				resultSql.append(SqlUtils.parseOrderByToSqlStyle(orderBy));
			}
			addPaging(resultSql, pagingInfo);
		} else if (queryName != null) {
			String sql = SqlUtils.getDynamicNamedSql(queryName, parameters);
			sql = SqlUtils.getRunableSql(sql, parameters);
			sql = SqlUtils.addExtraConditions(sql, runableSqlCondition);
			sql = SqlUtils.addDataFilterConditions(sql);
			resultSql.append(sql);
			if (orderBy != null && orderBy.trim().length() != 0) {
				resultSql.insert(0, "select * from (");
				resultSql.append(") order by ");
				resultSql.append(SqlUtils.parseOrderByToSqlStyle(orderBy));
			}
			addPaging(resultSql, pagingInfo);
		} else {
			throw new RuntimeException("CommonQuery error: no condition object");
		}
		return resultSql.toString();
	}

	private void addOrderBy(StringBuilder sql, String orderBy) {
		if (orderBy != null && orderBy.trim().length() != 0) {
			sql.append(" order by ");
			sql.append(SqlUtils.parseOrderByToSqlStyle(orderBy));
		}
	}
	
	private void addPaging(StringBuilder sql, PagingInfo pagingInfo) {
		if (pagingInfo == null) {
			return;
		}
		if (pagingInfo.getCurrentPage() == 1) {
			sql.insert(0, "select * from (");
			sql.append(") where rownum <= ");
			sql.append(pagingInfo.getPageSize());
		} else {
			sql.insert(0, "select * from (select row_.*, rownum rownum_ from (");
			sql.append(") row_ where rownum <= ");
			sql.append(pagingInfo.getPageSize() * pagingInfo.getCurrentPage());
			sql.append(") where rownum_ > ");
			sql.append(pagingInfo.getPageSize() * (pagingInfo.getCurrentPage() - 1));
		}
	}

}
