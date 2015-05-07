package cn.walle.platform.service;

import cn.walle.framework.common.support.QueryData;
import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.core.service.BaseManager;

/**
 * A bean to handle common queries.
 * @author cj
 *
 */
public interface GroovyCommonQueryManager extends BaseManager {

	QueryData query(QueryInfo queryInfo);
	
}
