package cn.walle.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.support.QueryData;
import cn.walle.framework.common.support.QueryInfo;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.platform.service.GroovyCommonQueryManager;
import cn.walle.platform.service.GroovyServiceManager;

/**
 * A bean to handle common queries with groovy services.
 * @author cj
 *
 */
@Service
public class GroovyCommonQueryManagerImpl extends BaseManagerImpl implements GroovyCommonQueryManager {

	@Autowired
	private GroovyServiceManager groovyServiceManager;
	
	public QueryData query(QueryInfo queryInfo) {
		String serviceName = queryInfo.getQueryType();
		return (QueryData) groovyServiceManager.invoke(serviceName, "query", queryInfo);
	}

}
