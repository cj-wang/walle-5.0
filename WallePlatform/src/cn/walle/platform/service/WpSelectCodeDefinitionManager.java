package cn.walle.platform.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.common.support.SelectCodeDefinitionLoader;
import cn.walle.framework.core.service.BaseManager;
import cn.walle.platform.model.WpSelectCodeDefinitionModel;

public interface WpSelectCodeDefinitionManager extends BaseManager, SelectCodeDefinitionLoader {

	List<WpSelectCodeDefinitionModel> saveTreeData(Collection<WpSelectCodeDefinitionModel> models);

	void refreshServerCache();
	
}
