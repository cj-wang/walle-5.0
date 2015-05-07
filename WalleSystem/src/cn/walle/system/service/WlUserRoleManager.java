package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.model.WlUserRoleModel;

public interface WlUserRoleManager extends BaseManager {

	WlUserRoleModel get(String id);

	List<WlUserRoleModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlUserRoleModel> findByExample(WlUserRoleModel example, String orderBy, PagingInfo pagingInfo);

	WlUserRoleModel save(WlUserRoleModel model);

	List<WlUserRoleModel> saveAll(Collection<WlUserRoleModel> models);

	void remove(WlUserRoleModel model);

	void removeAll(Collection<WlUserRoleModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	boolean checkIfAdmin(String userId);
	
	/**
	 * 获取在某一角色下的用户
	 * @param roleId
	 * @return
	 */
	List<WlUserModel> getUsersByRoleId(String roleId);
	
	void saveUserRoles(String userId, String roles);
	
	void removeUserRoles(String userRoleIds);

	void saveRolesForUser(String userId, String[] roleIds);
	
	void saveUsersForRole(String roleId, String[] checkedUserIds, String[] uncheckedUserIds);
}
