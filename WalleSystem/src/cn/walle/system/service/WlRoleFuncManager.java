package cn.walle.system.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlRoleFuncModel;

public interface WlRoleFuncManager extends BaseManager {

	WlRoleFuncModel get(String id);

	List<WlRoleFuncModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlRoleFuncModel> findByExample(WlRoleFuncModel example, String orderBy, PagingInfo pagingInfo);

	WlRoleFuncModel save(WlRoleFuncModel model);

	List<WlRoleFuncModel> saveAll(Collection<WlRoleFuncModel> models);

	void remove(WlRoleFuncModel model);

	void removeAll(Collection<WlRoleFuncModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	ArrayList getRoleFunc(String roleId);
	
	void saveFunc(String roleId,List<String> funcIds);
	
	void saveFuncsForRole(String roleId, String[] funcIds);
	
	void saveRolesForFunc(String funcId, String[] roleIds);

}
