package cn.walle.framework.common.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.service.BaseManager;

public interface CommonSaveManager extends BaseManager {

	 BaseModelClass save(BaseModelClass model);

	 List<BaseModelClass> saveAll(Collection<BaseModelClass> models);
	
	List<BaseModelClass> saveTreeData(Collection<BaseModelClass> models, String idField, String parentField);

	 void remove(BaseModelClass model);

	 void removeAll(Collection<BaseModelClass> models);

}
