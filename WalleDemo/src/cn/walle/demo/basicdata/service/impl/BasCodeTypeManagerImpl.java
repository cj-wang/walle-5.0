package cn.walle.demo.basicdata.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.basicdata.model.BasCodeTypeModel;
import cn.walle.demo.basicdata.service.BasCodeTypeManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class BasCodeTypeManagerImpl extends BaseManagerImpl implements BasCodeTypeManager {

	public BasCodeTypeModel get(String id) {
		return this.dao.get(BasCodeTypeModel.class, id);
	}

	public List<BasCodeTypeModel> getAll() {
		return this.dao.getAll(BasCodeTypeModel.class);
	}

	public List<BasCodeTypeModel> findByExample(BasCodeTypeModel example) {
		return this.dao.findByExample(example);
	}

	public BasCodeTypeModel save(BasCodeTypeModel model) {
		return this.dao.save(model);
	}

	public List<BasCodeTypeModel> saveAll(Collection<BasCodeTypeModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(BasCodeTypeModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<BasCodeTypeModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(BasCodeTypeModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(BasCodeTypeModel.class, ids);
	}

}
