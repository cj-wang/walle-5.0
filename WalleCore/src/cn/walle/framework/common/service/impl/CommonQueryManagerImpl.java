package cn.walle.framework.common.service.impl;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.CommonQueryManager;
import cn.walle.framework.common.service.SelectCodeManager;
import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.common.support.QueryData;
import cn.walle.framework.common.support.QueryDataSource;
import cn.walle.framework.common.support.QueryField;
import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.query.BaseQueryItem;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.CommonQuery;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.FileToDownload;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.framework.core.util.ClassUtils;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.ParameterUtils;
import cn.walle.framework.core.util.SqlUtils;
import cn.walle.framework.core.util.TemplateUtils;

/**
 * A bean to handle common queries.
 * @author cj
 *
 */
@Service
public class CommonQueryManagerImpl extends BaseManagerImpl implements CommonQueryManager {

	@Autowired
	private SelectCodeManager selectCodeManager;

	@Autowired(required = false)
	private Map<String, QueryDataSource> queryDataSourceBeans;
	
	private Map<String, Class<?>> modelClasses;
	private Map<String, Class<?>> queryConditionClasses;

	@PostConstruct
	public void init() throws Exception {
		modelClasses = new HashMap<String, Class<?>>();
		for (Class<?> modelClass : ClassUtils.getModelClasses()) {
			modelClasses.put(modelClass.getSimpleName(), modelClass);
		}
		queryConditionClasses = new HashMap<String, Class<?>>();
		for (Class<?> queryConditionClass : ClassUtils.getQueryConditionClasses()) {
			queryConditionClasses.put(queryConditionClass.getSimpleName(), queryConditionClass);
		}
	}
	
	public QueryData query(QueryInfo queryInfo) {
		String queryType = queryInfo.getQueryType();
		
		if (queryType != null) {
			int dsIndex = queryType.indexOf("@");
			if (dsIndex > 0) {
				String dsname = queryType.substring(dsIndex + 1);
				queryType = queryType.substring(0, dsIndex);
				queryInfo.setQueryType(queryType);
				Object dsCommonQueryManager = ContextUtils.getBean(dsname + "CommonQueryManager");
				try {
					Method queryMethod = dsCommonQueryManager.getClass().getMethod("query", QueryInfo.class);
					return (QueryData) queryMethod.invoke(dsCommonQueryManager, queryInfo);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		
		Class<?> queryTypeClass = queryInfo.getQueryTypeClass();
		List<QueryField> queryFields = queryInfo.getQueryFields();
		String orderBy = queryInfo.getOrderBy();
		PagingInfo pagingInfo = queryInfo.getPagingInfo();
		Map<String, String> fieldCodeTypes = queryInfo.getFieldCodeTypes();

		List<?> dataList;
		if (queryType != null && queryDataSourceBeans != null && queryDataSourceBeans.containsKey(queryType)) {
			QueryDataSource queryDataSource = queryDataSourceBeans.get(queryType);
			dataList = queryDataSource.getData(queryFields, orderBy, pagingInfo);
		} else if (queryTypeClass != null && QueryDataSource.class.isAssignableFrom(queryTypeClass)) {
			QueryDataSource queryDataSource = (QueryDataSource) ContextUtils.getBeanOfType(queryTypeClass);
			dataList = queryDataSource.getData(queryFields, orderBy, pagingInfo);
		} else {
			CommonQuery<?> commonQuery = createCommonQuery(queryInfo);
			dataList = commonQuery.query();
		}
		
		QueryData queryData = new QueryData();
		queryData.setDataList(dataList);
		queryData.setPagingInfo(pagingInfo);
		
		if (fieldCodeTypes != null && fieldCodeTypes.size() > 0) {
			queryData.setSelectCodeValues(selectCodeManager.getSelectCodeValues(dataList, fieldCodeTypes));
		}
		
		return queryData;
	}
	
	public List<String> getQueryDataItemFields(String queryType) {
		Class<?> dataItemClass;
		if (queryDataSourceBeans != null && queryDataSourceBeans.containsKey(queryType)) {
			QueryDataSource queryDataSource = queryDataSourceBeans.get(queryType);
			dataItemClass = queryDataSource.getDataItemClass();
		} else {
			QueryInfo queryInfo = new QueryInfo();
			queryInfo.setQueryType(queryType);
			CommonQuery<?> commonQuery = createCommonQuery(queryInfo);
			dataItemClass = commonQuery.getDataItemClass();
		}
		PropertyDescriptor[] dataItemPropertyDescriptors = new CustomBeanWrapper(dataItemClass).getPropertyDescriptorsInDeclaringOrder();
		List<String> dataItemFields = new ArrayList<String>();
		for (PropertyDescriptor propertyDescriptor : dataItemPropertyDescriptors) {
			String fieldName = propertyDescriptor.getName();
			if ("class".equals(fieldName)) {
				continue;
			}
			dataItemFields.add(fieldName);
		}
		return dataItemFields;
	}

	private CommonQuery<?> createCommonQuery(QueryInfo queryInfo) {
		String queryType = queryInfo.getQueryType();
		Class<?> queryTypeClass = queryInfo.getQueryTypeClass();
		List<QueryField> queryFields = queryInfo.getQueryFields();
		String orderBy = queryInfo.getOrderBy();
		PagingInfo pagingInfo = queryInfo.getPagingInfo();
		Map<String, String> fieldCodeTypes = queryInfo.getFieldCodeTypes();

		if (queryTypeClass == null && queryType != null) {
			if (queryType.endsWith("Model")) {
				queryTypeClass = modelClasses.get(queryType);
			} else if (queryType.endsWith("Query")) {
				queryTypeClass = queryConditionClasses.get(queryType + "Condition");
			}
		}
		
		CommonQuery<?> commonQuery;
		CustomBeanWrapper conditionObjectWrapper = null;
		
		if (queryTypeClass != null) {
			Object conditionObject;
			try {
				conditionObject = queryTypeClass.newInstance();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
			
			if (conditionObject instanceof BaseModel) {
				commonQuery = dao.createCommonQuery((BaseModel) conditionObject);
			} else if (conditionObject instanceof BaseQueryCondition) {
				commonQuery = dao.createCommonQuery((BaseQueryCondition) conditionObject, BaseQueryItem.class);
			} else {
				throw new RuntimeException("queryType " + queryTypeClass + "not supported");
			}
			conditionObjectWrapper = new CustomBeanWrapper(conditionObject);
		} else {
			Map<String, Object> parameters = new HashMap<String, Object>();
			if (queryFields != null && queryFields.size() > 0) {
				String sql = SqlUtils.getNamedSql(queryType);
				Collection<String> parameterNames = SqlUtils.getSqlParameters(sql).values();
				List<QueryField> queryFieldsLeft = new ArrayList<QueryField>();
				for (QueryField queryField : queryFields) {
					String parameterName = queryField.getFieldName();
					Object parameterValue = queryField.getFieldValue();
					if (! ParameterUtils.isParamValid(parameterName)
							|| ! ParameterUtils.isParamValid(parameterValue)) {
						continue;
					}
					String operator = queryField.getOperator();
					if (! ParameterUtils.isParamValid(operator)) {
						operator = "=";
					}
					parameterName = parameterName.trim();
					if (parameterNames.contains(parameterName) && "=".equals(operator)) {
						parameters.put(parameterName, parameterValue);
					} else {
						queryFieldsLeft.add(queryField);
					}
				}
				queryFields = queryFieldsLeft;
			}
			commonQuery = dao.createCommonQuery(queryType, parameters);
		}
		
		if (queryFields != null) {
			for (QueryField queryField : queryFields) {
				String parameterName = queryField.getFieldName();
				Object parameterValue = queryField.getFieldValue();
				if (! ParameterUtils.isParamValid(parameterName)
						|| ! ParameterUtils.isParamValid(parameterValue)) {
					continue;
				}
				String operator = queryField.getOperator();
				if (! ParameterUtils.isParamValid(operator)) {
					operator = "=";
				}
				parameterName = parameterName.trim();
				if (conditionObjectWrapper != null
						&& conditionObjectWrapper.isWritableProperty(parameterName)
						&& "=".equals(operator)) {
					conditionObjectWrapper.setPropertyValue(parameterName, parameterValue);
				} else if (parameterName.matches("\\[\\w+(,\\w+)*\\]")) {
					List<Condition> orConditions = new ArrayList<Condition>();
					parameterName = parameterName.substring(1, parameterName.length() - 1);
					String[] fieldNames = parameterName.split(",");
					for (String fieldName : fieldNames) {
						if (fieldCodeTypes != null && fieldCodeTypes.containsKey(fieldName)) {
							SelectCodeDefinition selectCodeDefinition = selectCodeManager.getSelectCodeDefinition(fieldCodeTypes.get(fieldName));
							String selectCodeQueryType = selectCodeDefinition.getQueryType();
							List<QueryField> selectCodeQueryFields = new ArrayList<QueryField>();
							if (selectCodeDefinition.getQueryFields() != null) {
								selectCodeQueryFields.addAll(selectCodeDefinition.getQueryFields());
							}
							QueryField selectCodeQueryField = new QueryField();
							selectCodeQueryField.setFieldName(selectCodeDefinition.getLabelFieldName());
							selectCodeQueryField.setOperator(operator);
							selectCodeQueryField.setFieldValue(parameterValue);
							selectCodeQueryFields.add(selectCodeQueryField);
							if (queryDataSourceBeans != null && queryDataSourceBeans.containsKey(selectCodeQueryType)) {
								List<?> selectCodeDataList = queryDataSourceBeans.get(selectCodeQueryType).getData(
										selectCodeQueryFields, null, null);
								if (selectCodeDataList.size() == 0) {
									continue;
								}
								Object[] selectCodeKeyValues = new Object[selectCodeDataList.size()];
								for (int i = 0; i < selectCodeKeyValues.length; i++) {
									selectCodeKeyValues[i] = new CustomBeanWrapper(selectCodeDataList.get(i)).getPropertyValue(
											selectCodeDefinition.getKeyFieldName());
								}
								List<Condition> inConditions = new ArrayList<Condition>();
								for (int i = 0; ; i++) {
									Object[] tmpArray = ArrayUtils.subarray(selectCodeKeyValues, 1000 * i, 1000 * (i + 1));
									if (tmpArray.length == 0) {
										break;
									}
									inConditions.add(Condition.in(fieldName, tmpArray));
								}
								Condition inCondition = Condition.or(inConditions);
								String sqlCondition = SqlUtils.getRunableSql(inCondition.getSql(), inCondition.getParameters());
								orConditions.add(Condition.sql(sqlCondition));
							} else {
								QueryInfo selectCodeQueryInfo = new QueryInfo();
								selectCodeQueryInfo.setQueryType(selectCodeQueryType);
								selectCodeQueryInfo.setQueryFields(selectCodeQueryFields);
								String fieldSelectCodeSql = createCommonQuery(selectCodeQueryInfo).getRunableSql();
								orConditions.add(Condition.sql(
										SqlUtils.javaNameToDbName(fieldName) +
										" in (select " + SqlUtils.javaNameToDbName(selectCodeDefinition.getKeyFieldName()) +
										" from (" + fieldSelectCodeSql + ") T__CODE__ ) "
								));
							}
						} else {
							orConditions.add(Condition.operator(fieldName, operator, parameterValue));
						}
					}
					commonQuery.addCondition(Condition.or(orConditions));
				} else {
					commonQuery.addCondition(Condition.operator(parameterName, operator, parameterValue));
				}
			}
		}
		
		commonQuery.setOrderBy(orderBy);
		commonQuery.setPagingInfo(pagingInfo);
		return commonQuery;
	}

	public FileToDownload exportExcel(String title, QueryInfo queryInfo, List<FieldDefinition> fieldDefinitions) {
		QueryData queryData = this.query(queryInfo);
		List<List<Object>> data = new ArrayList<List<Object>>();
		for (Object dataListItem : queryData.getDataList()) {
			List<Object> dataRow = new ArrayList<Object>();
			CustomBeanWrapper beanWrapper = new CustomBeanWrapper(dataListItem);
			for (FieldDefinition fieldDefinition : fieldDefinitions) {
				Object value = null;
				if (beanWrapper.isReadableProperty(fieldDefinition.getFieldName())) {
					value = beanWrapper.getPropertyValue(fieldDefinition.getFieldName());
					if (queryInfo.getFieldCodeTypes().containsKey(fieldDefinition.getFieldName())) {
						value = queryData.getSelectCodeValues().get(queryInfo.getFieldCodeTypes().get(fieldDefinition.getFieldName())).get(value);
					}
					if (fieldDefinition.getFieldType() == null && value != null) {
						if (value instanceof Number) {
							fieldDefinition.setFieldType(FieldDefinition.FIELD_TYPE_DOUBLE);
						} else if (value instanceof Date) {
							fieldDefinition.setFieldType(FieldDefinition.FIELD_TYPE_DATETIME);
						} else {
							fieldDefinition.setFieldType(FieldDefinition.FIELD_TYPE_STRING);
						}
					}
				}
				dataRow.add(value);
			}
			data.add(dataRow);
		}
		for (FieldDefinition fieldDefinition : fieldDefinitions) {
			if (fieldDefinition.getFieldType() == null) {
				fieldDefinition.setFieldType(FieldDefinition.FIELD_TYPE_STRING);
			}
		}
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("title", title);
		dataModel.put("data", data);
		dataModel.put("fields", fieldDefinitions);

		String content;
		try {
			content = TemplateUtils.process("/template/export/simpleGridExcel.ftl", dataModel);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		FileToDownload fileToDownload = new FileToDownload();
		fileToDownload.setFileName(title + ".xls");
		fileToDownload.setContentType("application/vnd.ms-excel");
		try {
			fileToDownload.setContent(new ByteArrayInputStream(content.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException ueex) {
			throw new RuntimeException(ueex);
		}
		return fileToDownload;
	}

}
