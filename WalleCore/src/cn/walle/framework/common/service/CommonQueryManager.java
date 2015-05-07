package cn.walle.framework.common.service;

import java.util.List;

import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.common.support.QueryData;
import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.FileToDownload;

/**
 * A bean to handle common queries.
 * @author cj
 *
 */
public interface CommonQueryManager extends BaseManager {

	QueryData query(QueryInfo queryInfo);
	
	List<String> getQueryDataItemFields(String queryType);
	
	FileToDownload exportExcel(String title, QueryInfo queryInfo, List<FieldDefinition> fieldDefinitions);
	
}
