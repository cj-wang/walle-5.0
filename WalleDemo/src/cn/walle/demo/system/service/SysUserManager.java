package cn.walle.demo.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.demo.system.model.SysUserModel;
import cn.walle.framework.core.service.BaseManager;

public interface SysUserManager extends BaseManager {

	SysUserModel get(String id);

	List<SysUserModel> getAll();

	List<SysUserModel> findByExample(SysUserModel example);

	SysUserModel save(SysUserModel model);

	List<SysUserModel> saveAll(Collection<SysUserModel> models);

	void remove(SysUserModel model);

	void removeAll(Collection<SysUserModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
