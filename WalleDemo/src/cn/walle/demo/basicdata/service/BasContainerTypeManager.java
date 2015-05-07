package cn.walle.demo.basicdata.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.basicdata.model.BasContainerTypeModel;
import cn.walle.framework.core.service.BaseManager;

public interface BasContainerTypeManager extends BaseManager {

	BasContainerTypeModel get(String id);

	List<BasContainerTypeModel> getAll();

	List<BasContainerTypeModel> findByExample(BasContainerTypeModel example);

	BasContainerTypeModel save(BasContainerTypeModel model);

	List<BasContainerTypeModel> saveAll(Collection<BasContainerTypeModel> models);

	void remove(BasContainerTypeModel model);

	void removeAll(Collection<BasContainerTypeModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
