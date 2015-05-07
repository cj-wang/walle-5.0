package ${packageName}.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import ${packageName}.model.${className}Model;

public interface ${className}Manager extends BaseManager {

	List<${className}Model> saveAll(Collection<${className}Model> models);

}
