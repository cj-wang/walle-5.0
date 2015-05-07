package cn.walle.demo.basicdata.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.basicdata.model.BasCodeDefModel;
import cn.walle.demo.basicdata.service.BasCodeDefManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class BasCodeDefManagerImpl extends BaseManagerImpl implements BasCodeDefManager {

	public BasCodeDefModel get(String id) {
		return this.dao.get(BasCodeDefModel.class, id);
	}

	public List<BasCodeDefModel> getAll() {
		return this.dao.getAll(BasCodeDefModel.class);
	}

	public List<BasCodeDefModel> findByExample(BasCodeDefModel example) {
		return this.dao.findByExample(example);
	}

	public BasCodeDefModel save(BasCodeDefModel model) {
		return this.dao.save(model);
	}

	public List<BasCodeDefModel> saveAll(Collection<BasCodeDefModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(BasCodeDefModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<BasCodeDefModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(BasCodeDefModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(BasCodeDefModel.class, ids);
	}

}
