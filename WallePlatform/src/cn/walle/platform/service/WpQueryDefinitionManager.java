package cn.walle.platform.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.common.support.QueryDefinitionLoader;
import cn.walle.framework.core.service.BaseManager;
import cn.walle.platform.model.WpQueryDefinitionModel;

public interface WpQueryDefinitionManager extends BaseManager, QueryDefinitionLoader {

	List<WpQueryDefinitionModel> saveTreeData(Collection<WpQueryDefinitionModel> models);

}
