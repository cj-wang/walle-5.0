package cn.walle.framework.common.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.service.BaseManager;

public interface MongoCommonSaveManager extends BaseManager {

	DynamicModelClass save(DynamicModelClass model);

	List<DynamicModelClass> saveAll(Collection<DynamicModelClass> models);
	
	List<DynamicModelClass> saveTreeData(Collection<DynamicModelClass> models, String idField, String parentField);

	void remove(DynamicModelClass model);

	void removeAll(Collection<DynamicModelClass> models);

}
