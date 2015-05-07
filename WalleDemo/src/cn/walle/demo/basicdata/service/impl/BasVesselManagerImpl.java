package cn.walle.demo.basicdata.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.basicdata.model.BasVesselModel;
import cn.walle.demo.basicdata.service.BasVesselManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class BasVesselManagerImpl extends BaseManagerImpl implements BasVesselManager {

	public BasVesselModel get(String id) {
		return this.dao.get(BasVesselModel.class, id);
	}

	public List<BasVesselModel> getAll() {
		return this.dao.getAll(BasVesselModel.class);
	}

	public List<BasVesselModel> findByExample(BasVesselModel example) {
		return this.dao.findByExample(example);
	}

	public BasVesselModel save(BasVesselModel model) {
		return this.dao.save(model);
	}

	public List<BasVesselModel> saveAll(Collection<BasVesselModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(BasVesselModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<BasVesselModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(BasVesselModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(BasVesselModel.class, ids);
	}

}
