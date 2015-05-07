package cn.walle.demo.basicdata.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.basicdata.model.BasCustomerModel;
import cn.walle.demo.basicdata.service.BasCustomerManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class BasCustomerManagerImpl extends BaseManagerImpl implements BasCustomerManager {

	public BasCustomerModel get(String id) {
		return this.dao.get(BasCustomerModel.class, id);
	}

	public List<BasCustomerModel> getAll() {
		return this.dao.getAll(BasCustomerModel.class);
	}

	public List<BasCustomerModel> findByExample(BasCustomerModel example) {
		return this.dao.findByExample(example);
	}

	public BasCustomerModel save(BasCustomerModel model) {
		return this.dao.save(model);
	}

	public List<BasCustomerModel> saveAll(Collection<BasCustomerModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(BasCustomerModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<BasCustomerModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(BasCustomerModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(BasCustomerModel.class, ids);
	}

}
