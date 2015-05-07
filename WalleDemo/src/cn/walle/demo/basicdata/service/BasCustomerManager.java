package cn.walle.demo.basicdata.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.basicdata.model.BasCustomerModel;
import cn.walle.framework.core.service.BaseManager;

public interface BasCustomerManager extends BaseManager {

	BasCustomerModel get(String id);

	List<BasCustomerModel> getAll();

	List<BasCustomerModel> findByExample(BasCustomerModel example);

	BasCustomerModel save(BasCustomerModel model);

	List<BasCustomerModel> saveAll(Collection<BasCustomerModel> models);

	void remove(BasCustomerModel model);

	void removeAll(Collection<BasCustomerModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
