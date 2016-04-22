package cn.walle.framework.common.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.service.BaseManager;

public interface CommonSaveManager<M extends BaseModel> extends BaseManager {

	M save(M model);

	List<M> saveAll(Collection<M> models);

	List<M> saveTreeData(Collection<M> models, String idField, String parentField);

	void remove(M model);

	void removeAll(Collection<M> models);

}
