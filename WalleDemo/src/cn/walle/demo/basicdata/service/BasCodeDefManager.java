package cn.walle.demo.basicdata.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.basicdata.model.BasCodeDefModel;
import cn.walle.framework.core.service.BaseManager;

public interface BasCodeDefManager extends BaseManager {

	BasCodeDefModel get(String id);

	List<BasCodeDefModel> getAll();

	List<BasCodeDefModel> findByExample(BasCodeDefModel example);

	BasCodeDefModel save(BasCodeDefModel model);

	List<BasCodeDefModel> saveAll(Collection<BasCodeDefModel> models);

	void remove(BasCodeDefModel model);

	void removeAll(Collection<BasCodeDefModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
