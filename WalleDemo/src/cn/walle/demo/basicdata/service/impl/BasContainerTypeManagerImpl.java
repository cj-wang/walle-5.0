package cn.walle.demo.basicdata.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.basicdata.model.BasContainerTypeModel;
import cn.walle.demo.basicdata.service.BasContainerTypeManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class BasContainerTypeManagerImpl extends BaseManagerImpl implements BasContainerTypeManager {

	public BasContainerTypeModel get(String id) {
		return this.dao.get(BasContainerTypeModel.class, id);
	}

	public List<BasContainerTypeModel> getAll() {
		return this.dao.getAll(BasContainerTypeModel.class);
	}

	public List<BasContainerTypeModel> findByExample(BasContainerTypeModel example) {
		return this.dao.findByExample(example);
	}

	public BasContainerTypeModel save(BasContainerTypeModel model) {
		return this.dao.save(model);
	}

	public List<BasContainerTypeModel> saveAll(Collection<BasContainerTypeModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(BasContainerTypeModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<BasContainerTypeModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(BasContainerTypeModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(BasContainerTypeModel.class, ids);
	}

}
