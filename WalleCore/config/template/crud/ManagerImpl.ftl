package ${packageName}.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import ${packageName}.model.${className}Model;
import ${packageName}.service.${className}Manager;

@Service
public class ${className}ManagerImpl extends BaseManagerImpl implements ${className}Manager {

	public List<${className}Model> saveAll(Collection<${className}Model> models) {
		return this.dao.saveAll(models);
	}

}
