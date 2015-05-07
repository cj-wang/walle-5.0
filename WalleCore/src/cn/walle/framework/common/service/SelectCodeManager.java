package cn.walle.framework.common.service;

import java.util.List;
import java.util.Map;

import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.common.support.SelectCodeData;
import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.core.service.BaseManager;

public interface SelectCodeManager extends BaseManager {
	
	SelectCodeDefinition getSelectCodeDefinition(String codeType);

	Map<String, SelectCodeDefinition> getSelectCodeDefinitions(List<String> codeTypes);
	
	SelectCodeData getSelectCodeData(QueryInfo queryInfo);
	
	String getSelectCodeLabel(String codeType, Object key);
	
	Map<String, Map<Object, String>> getSelectCodeValuesByKeys(Map<String, String> selectCodeKeys);

	Map<String, Map<Object, String>> getSelectCodeValues(List<?> dataList, Map<String, String> fieldCodeTypes);
	
	List<String> getSystemCodeTypes();
	
	Map<String, SelectCodeDefinition> getAllSelectCodeDefinitions();
	
	Map<String,SelectCodeData> getSelectCodeDatas(List<QueryInfo> queryInfos);
	
}
