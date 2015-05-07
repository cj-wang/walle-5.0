package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlRoleModel;
import cn.walle.system.query.UserMenuQueryItem;

public interface WlRoleManager extends BaseManager {

	WlRoleModel get(String id);

	List<WlRoleModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlRoleModel> findByExample(WlRoleModel example, String orderBy, PagingInfo pagingInfo);

	WlRoleModel save(WlRoleModel model);

	List<WlRoleModel> saveAll(Collection<WlRoleModel> models);

	void remove(WlRoleModel model);

	void removeAll(Collection<WlRoleModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void delByPk(String id);

	void saveModel(WlRoleModel model);
	
	/**
	 * 根据用户Id查询用户所属角色
	 * @param userId
	 * @return
	 */
	List<UserMenuQueryItem> getRolesByUserId(String userId);

}
