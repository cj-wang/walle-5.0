package cn.walle.system.service;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.system.model.WlTenantModel;
import cn.walle.system.model.WlUserModel;

public interface WlTenantManager extends BaseManager {
	
	WlTenantModel get(String id);

	void register(WlTenantModel tenant, WlUserModel adminUser);

}
