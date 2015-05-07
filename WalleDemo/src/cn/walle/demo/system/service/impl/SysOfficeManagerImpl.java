package cn.walle.demo.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.system.model.SysOfficeModel;
import cn.walle.demo.system.service.SysOfficeManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class SysOfficeManagerImpl extends BaseManagerImpl implements SysOfficeManager {

	public SysOfficeModel get(String id) {
		return this.dao.get(SysOfficeModel.class, id);
	}

	public List<SysOfficeModel> getAll() {
		return this.dao.getAll(SysOfficeModel.class);
	}

	public List<SysOfficeModel> findByExample(SysOfficeModel example) {
		return this.dao.findByExample(example);
	}

	public SysOfficeModel save(SysOfficeModel model) {
		return this.dao.save(model);
	}

	public List<SysOfficeModel> saveAll(Collection<SysOfficeModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(SysOfficeModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<SysOfficeModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(SysOfficeModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(SysOfficeModel.class, ids);
	}

}
