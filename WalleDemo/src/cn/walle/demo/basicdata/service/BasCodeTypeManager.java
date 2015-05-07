package cn.walle.demo.basicdata.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.basicdata.model.BasCodeTypeModel;
import cn.walle.framework.core.service.BaseManager;

public interface BasCodeTypeManager extends BaseManager {

	BasCodeTypeModel get(String id);

	List<BasCodeTypeModel> getAll();

	List<BasCodeTypeModel> findByExample(BasCodeTypeModel example);

	BasCodeTypeModel save(BasCodeTypeModel model);

	List<BasCodeTypeModel> saveAll(Collection<BasCodeTypeModel> models);

	void remove(BasCodeTypeModel model);

	void removeAll(Collection<BasCodeTypeModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
