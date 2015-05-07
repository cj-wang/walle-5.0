package cn.walle.platform.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.platform.model.WpDynamicPageModel;

public interface WpDynamicPageManager extends BaseManager {

	List<WpDynamicPageModel> saveAll(Collection<WpDynamicPageModel> models);
	
	List<WpDynamicPageModel> release(Collection<WpDynamicPageModel> models);
	
}
