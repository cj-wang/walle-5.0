package cn.walle.demo.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.demo.system.model.SysUserModel;
import cn.walle.demo.system.service.SysUserManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class SysUserManagerImpl extends BaseManagerImpl implements SysUserManager {

	public SysUserModel get(String id) {
		return this.dao.get(SysUserModel.class, id);
	}

	public List<SysUserModel> getAll() {
		return this.dao.getAll(SysUserModel.class);
	}

	public List<SysUserModel> findByExample(SysUserModel example) {
		return this.dao.findByExample(example);
	}

	public SysUserModel save(SysUserModel model) {
		return this.dao.save(model);
	}

	public List<SysUserModel> saveAll(Collection<SysUserModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(SysUserModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<SysUserModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(SysUserModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(SysUserModel.class, ids);
	}

}
