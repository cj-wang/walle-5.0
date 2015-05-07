package cn.walle.demo.basicdata.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.basicdata.model.BasVesselModel;
import cn.walle.framework.core.service.BaseManager;

public interface BasVesselManager extends BaseManager {

	BasVesselModel get(String id);

	List<BasVesselModel> getAll();

	List<BasVesselModel> findByExample(BasVesselModel example);

	BasVesselModel save(BasVesselModel model);

	List<BasVesselModel> saveAll(Collection<BasVesselModel> models);

	void remove(BasVesselModel model);

	void removeAll(Collection<BasVesselModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
