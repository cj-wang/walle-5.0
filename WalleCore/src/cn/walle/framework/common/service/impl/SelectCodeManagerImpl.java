package cn.walle.framework.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.CommonQueryManager;
import cn.walle.framework.common.service.SelectCodeManager;
import cn.walle.framework.common.support.QueryData;
import cn.walle.framework.common.support.QueryField;
import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.common.support.SelectCodeData;
import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.common.support.SelectCodeDefinitionLoader;
import cn.walle.framework.core.dao.NativeSqlDao;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.util.ContextUtils;

@Service
public class SelectCodeManagerImpl extends BaseManagerImpl implements SelectCodeManager {

	@Autowired
	private NativeSqlDao nativeSqlDao;
	
	@Autowired
	private CommonQueryManager commonQueryManager;
	
	@Autowired(required = false)
	private Map<String, SelectCodeDefinition> selectCodeDefinitionBeans;
	
	@Autowired(required = false)
	@Qualifier("systemCodeTypesSelectSql")
	private String systemCodeTypesSelectSql;
	
	private Map<String, SelectCodeDefinition> systemCodeDefinitions = new HashMap<String, SelectCodeDefinition>();
	
	@Autowired(required = false)
	private SelectCodeDefinitionLoader selectCodeDefinitionLoader;
	
	public SelectCodeDefinition getSelectCodeDefinition(String codeType) {
		if (selectCodeDefinitionBeans != null
				&& selectCodeDefinitionBeans.containsKey(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX + codeType)) {
			return selectCodeDefinitionBeans.get(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX + codeType);
		}
		if (selectCodeDefinitionLoader != null) {
			SelectCodeDefinition selectCodeDefinition = selectCodeDefinitionLoader.getSelectCodeDefinition(codeType);
			if (selectCodeDefinition != null) {
				return selectCodeDefinition;
			}
		}
		if (! systemCodeDefinitions.containsKey(codeType)) {
			synchronized (systemCodeDefinitions) {
				if (! systemCodeDefinitions.containsKey(codeType)) {
					SelectCodeDefinition selectCodeDefinition = (SelectCodeDefinition) ContextUtils.getBean(
							SelectCodeDefinition.SYSTEM_CODE_TEMPLATE_BEAN_ID);
					List<QueryField> queryFields = selectCodeDefinition.getQueryFields();
					for (QueryField queryField : queryFields) {
						if ("$codeType".equals(queryField.getFieldValue())) {
							queryField.setFieldValue(codeType);
						}
					}
					systemCodeDefinitions.put(codeType, selectCodeDefinition);
					return selectCodeDefinition;
				}
			}
		}
		return systemCodeDefinitions.get(codeType);
	}

	public Map<String, SelectCodeDefinition> getSelectCodeDefinitions(List<String> codeTypes) {
		Map<String, SelectCodeDefinition> selectCodeDefinitions = new HashMap<String, SelectCodeDefinition>();
		for (String codeType : codeTypes) {
			selectCodeDefinitions.put(codeType, getSelectCodeDefinition(codeType));
		}
		return selectCodeDefinitions;
	}
	
	public SelectCodeData getSelectCodeData(QueryInfo queryInfo) {
		SelectCodeDefinition selectCodeDefinition = getSelectCodeDefinition(queryInfo.getQueryType());
		queryInfo.setQueryType(selectCodeDefinition.getQueryType());
		if (queryInfo.getQueryFields() == null) {
			queryInfo.setQueryFields(new ArrayList<QueryField>());
		}
		if (selectCodeDefinition.getQueryFields() != null) {
			queryInfo.getQueryFields().addAll(selectCodeDefinition.getQueryFields());
		}
		if (queryInfo.getOrderBy() == null || queryInfo.getOrderBy().trim().length() == 0) {
			queryInfo.setOrderBy(selectCodeDefinition.getOrderBy());
		}
		QueryData queryData = commonQueryManager.query(queryInfo);
		SelectCodeData selectCodeData = new SelectCodeData();
		try {
			BeanUtils.copyProperties(queryData, selectCodeData);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		selectCodeData.setKeyFieldName(selectCodeDefinition.getKeyFieldName());
		selectCodeData.setLabelFieldName(selectCodeDefinition.getLabelFieldName());
		return selectCodeData;
	}
	
	public Map<String,SelectCodeData> getSelectCodeDatas(List<QueryInfo> queryInfos) {
		Map<String,SelectCodeData> result = new HashMap<String, SelectCodeData>();
		for (QueryInfo queryInfo : queryInfos) {
			String selectCode = queryInfo.getQueryType();
			result.put(selectCode, this.getSelectCodeData(queryInfo));
		}
		return result;
	}
	
	public String getSelectCodeLabel(String codeType, Object key) {
		SelectCodeDefinition selectCodeDefinition = getSelectCodeDefinition(codeType);
		QueryInfo selectCodeQueryInfo = new QueryInfo();
		selectCodeQueryInfo.setQueryType(selectCodeDefinition.getQueryType());
		List<QueryField> selectCodeQueryFields = new ArrayList<QueryField>();
		if (selectCodeDefinition.getQueryFields() != null) {
			selectCodeQueryFields.addAll(selectCodeDefinition.getQueryFields());
		}
		QueryField queryField = new QueryField();
		queryField.setFieldName(selectCodeDefinition.getKeyFieldName());
		queryField.setFieldValue(key);
		selectCodeQueryFields.add(queryField);
		selectCodeQueryInfo.setQueryFields(selectCodeQueryFields);
		List<?> codeValuesDataList = commonQueryManager.query(selectCodeQueryInfo).getDataList();
		if (codeValuesDataList.size() == 1) {
			CustomBeanWrapper codeValueDataWrapper = new CustomBeanWrapper(codeValuesDataList.get(0));
			return "" + codeValueDataWrapper.getPropertyValue(selectCodeDefinition.getLabelFieldName());
		} else {
			return null;
		}
	}
	
	public Map<String, Map<Object, String>> getSelectCodeValuesByKeys(Map<String, String> selectCodeKeys){
		Map<String, Map<Object, String>> selectCodeValues = new HashMap<String, Map<Object, String>>();
		for (String codeType : selectCodeKeys.keySet()) {
			Map<Object, String> codeValues = new HashMap<Object, String>();
			selectCodeValues.put(codeType, codeValues);
			SelectCodeDefinition selectCodeDefinition = getSelectCodeDefinition(codeType);
			QueryInfo selectCodeQueryInfo = new QueryInfo();
			selectCodeQueryInfo.setQueryType(selectCodeDefinition.getQueryType());
			List<QueryField> selectCodeQueryFields = new ArrayList<QueryField>();
			if (selectCodeDefinition.getQueryFields() != null) {
				selectCodeQueryFields.addAll(selectCodeDefinition.getQueryFields());
			}
			if(selectCodeKeys.get(codeType) != null && ! "".equals(selectCodeKeys.get(codeType))){
				QueryField queryField = new QueryField();
				queryField.setFieldName(selectCodeDefinition.getKeyFieldName());
				queryField.setOperator("in");
				queryField.setFieldValue(selectCodeKeys.get(codeType).split(","));
				selectCodeQueryFields.add(queryField);
			
				selectCodeQueryInfo.setQueryFields(selectCodeQueryFields);
				List<?> codeValuesDataList = commonQueryManager.query(selectCodeQueryInfo).getDataList();
				for (Object codeValueDataItem : codeValuesDataList) {
					CustomBeanWrapper codeValueDataWrapper = new CustomBeanWrapper(codeValueDataItem);
					codeValues.put(codeValueDataWrapper.getPropertyValue(selectCodeDefinition.getKeyFieldName()),
							"" + codeValueDataWrapper.getPropertyValue(selectCodeDefinition.getLabelFieldName()));
				}
				for (Object codeValue : codeValues.keySet().toArray()) {
					if (codeValues.get(codeValue) == null) {
						codeValues.remove(codeValue);
					}
				}
			}
		}
		return selectCodeValues;
	}
	
	public Map<String, Map<Object, String>> getSelectCodeValues(List<?> dataList, Map<String, String> fieldCodeTypes) {
		List<CustomBeanWrapper> dataWrappers = new ArrayList<CustomBeanWrapper>();
		for (Object dataItem : dataList) {
			dataWrappers.add(new CustomBeanWrapper(dataItem));
		}
		Map<String, Map<Object, String>> selectCodeValues = new HashMap<String, Map<Object,String>>();
		for (String fieldName : fieldCodeTypes.keySet()) {
			String codeType = fieldCodeTypes.get(fieldName);
			if (fieldName == null || fieldName.trim().length() == 0
					|| codeType == null || codeType.trim().length() == 0) {
				continue;
			}
			Map<Object, String> codeValues;
			if (selectCodeValues.containsKey(codeType)) {
				codeValues = selectCodeValues.get(codeType);
			} else {
				codeValues = new HashMap<Object, String>();
				selectCodeValues.put(codeType, codeValues);
			}
			for (CustomBeanWrapper dataWrapper : dataWrappers) {
				Object codeValue = dataWrapper.getPropertyValue(fieldName);
				if (codeValue != null) {
					for (String value : (codeValue + "").split(",")) {
						codeValues.put(value, value);
					}
				}
			}
		}
		
		for (String codeType : selectCodeValues.keySet()) {
			Map<Object, String> codeValues = selectCodeValues.get(codeType);
			if (codeValues.size() == 0) {
				continue;
			}

			SelectCodeDefinition selectCodeDefinition = getSelectCodeDefinition(codeType);
			QueryInfo selectCodeQueryInfo = new QueryInfo();
			selectCodeQueryInfo.setQueryType(selectCodeDefinition.getQueryType());
			
			List<QueryField> selectCodeQueryFields = new ArrayList<QueryField>();
			if (selectCodeDefinition.getQueryFields() != null) {
				selectCodeQueryFields.addAll(selectCodeDefinition.getQueryFields());
			}
			QueryField queryField = new QueryField();
			queryField.setFieldName(selectCodeDefinition.getKeyFieldName());
			queryField.setOperator("in");
			queryField.setFieldValue(codeValues.keySet().toArray());
			selectCodeQueryFields.add(queryField);
			selectCodeQueryInfo.setQueryFields(selectCodeQueryFields);
			
			List<?> codeValuesDataList = commonQueryManager.query(selectCodeQueryInfo).getDataList();
			
			for (Object codeValueDataItem : codeValuesDataList) {
				CustomBeanWrapper codeValueDataWrapper = new CustomBeanWrapper(codeValueDataItem);
				codeValues.put(codeValueDataWrapper.getPropertyValue(selectCodeDefinition.getKeyFieldName()),
						"" + codeValueDataWrapper.getPropertyValue(selectCodeDefinition.getLabelFieldName()));
			}
		}
		return selectCodeValues;
	}

	public List<String> getSystemCodeTypes() {
		List<String> systemCodeTypes = new ArrayList<String>();
		if (systemCodeTypesSelectSql != null) {
			List<Object[]> result = nativeSqlDao.find(systemCodeTypesSelectSql);
			for (Object[] resultRow : result) {
				systemCodeTypes.add((String) resultRow[0]);
			}
		}
		return systemCodeTypes;
	}
	
	public Map<String, SelectCodeDefinition> getAllSelectCodeDefinitions() {
		Map<String, SelectCodeDefinition> allSelectCodeDefinitions = new HashMap<String, SelectCodeDefinition>();
		if (selectCodeDefinitionBeans != null) {
			for (String beanName : selectCodeDefinitionBeans.keySet()) {
				allSelectCodeDefinitions.put(beanName.substring(SelectCodeDefinition.DEFINITION_BEAN_ID_PREFIX.length()), selectCodeDefinitionBeans.get(beanName));
			}
		}
		if (selectCodeDefinitionLoader != null) {
			allSelectCodeDefinitions.putAll(getSelectCodeDefinitions(selectCodeDefinitionLoader.getAllCodeTypes()));
		}
		allSelectCodeDefinitions.putAll(getSelectCodeDefinitions(getSystemCodeTypes()));
		return allSelectCodeDefinitions;
	}
	
}
