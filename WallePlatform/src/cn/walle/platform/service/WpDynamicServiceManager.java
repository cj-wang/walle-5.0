package cn.walle.platform.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.platform.model.WpDynamicServiceModel;

public interface WpDynamicServiceManager extends BaseManager {

	List<WpDynamicServiceModel> saveAll(Collection<WpDynamicServiceModel> models);

	List<WpDynamicServiceModel> release(Collection<WpDynamicServiceModel> models);
	
}
