package cn.walle.platform.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.util.SqlUtils;
import cn.walle.platform.model.WpQueryDefinitionModel;
import cn.walle.platform.service.WpQueryDefinitionManager;

@Service
public class WpQueryDefinitionManagerImpl extends BaseManagerImpl implements WpQueryDefinitionManager {

	private Map<String, String> queryCache;

	private void initCache() {
		Map<String, String> queryCache = new HashMap<String, String>();
		List<WpQueryDefinitionModel> queries = this.dao.getAll(WpQueryDefinitionModel.class);
		for (WpQueryDefinitionModel query : queries) {
			queryCache.put(query.getQueryName(), query.getQuerySql());
		}
		this.queryCache = queryCache;
	}
	
	public List<WpQueryDefinitionModel> saveTreeData(Collection<WpQueryDefinitionModel> models) {
		List<WpQueryDefinitionModel> result = this.dao.saveTreeData(models, "uuid", "parentUuid");
		this.initCache();
		return result;
	}

	@Override
	public String getQueryString(String queryName) {
		if (queryName.endsWith("Model")) {
			return "select t.* from " + SqlUtils.javaNameToDbName(queryName.substring(0, queryName.length() - 5)) + " t";
		} else if (queryName.endsWith("Query")) {
			if (this.queryCache == null) {
				synchronized (WpQueryDefinitionManagerImpl.class) {
					if (this.queryCache == null) {
						this.initCache();
					}
				}
			}
			String sql = this.queryCache.get(queryName);
			if (sql == null) {
				throw new SystemException("queryName " + queryName + " not found");
			}
			return sql;
		} else {
			throw new SystemException("queryName " + queryName + " not found");
		}
	}

}
