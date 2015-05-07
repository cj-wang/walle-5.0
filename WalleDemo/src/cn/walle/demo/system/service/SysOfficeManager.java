package cn.walle.demo.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.system.model.SysOfficeModel;
import cn.walle.framework.core.service.BaseManager;

public interface SysOfficeManager extends BaseManager {

	SysOfficeModel get(String id);

	List<SysOfficeModel> getAll();

	List<SysOfficeModel> findByExample(SysOfficeModel example);

	SysOfficeModel save(SysOfficeModel model);

	List<SysOfficeModel> saveAll(Collection<SysOfficeModel> models);

	void remove(SysOfficeModel model);

	void removeAll(Collection<SysOfficeModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
