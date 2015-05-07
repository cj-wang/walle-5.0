package cn.walle.platform.service;

import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.platform.model.WpDynamicServiceModel;

public interface GroovyServiceManager extends BaseManager {

	List<WpDynamicServiceModel> saveTreeData(List<WpDynamicServiceModel> models);
	
	Class<?> compile(String serviceName, String text);
	
	Object invoke(String serviceName, String methodName, Object parameters);

}
