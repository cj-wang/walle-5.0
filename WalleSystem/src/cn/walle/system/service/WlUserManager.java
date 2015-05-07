package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlFunctionModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.query.ChatUsersQueryCondition;
import cn.walle.system.query.ChatUsersQueryItem;
import cn.walle.system.query.RoleGrantedUserQueryItem;
import cn.walle.system.query.UserOrganizeQueryItem;
import cn.walle.system.query.UsersByNameQueryItem;


public interface WlUserManager extends BaseManager {


	WlUserModel get(String id);

	List<WlUserModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlUserModel> findByExample(WlUserModel example, String orderBy, PagingInfo pagingInfo);

	WlUserModel save(WlUserModel model);

	List<WlUserModel> saveAll(Collection<WlUserModel> models);

	void remove(WlUserModel model);

	void removeAll(Collection<WlUserModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

	WlUserModel saveModel(WlUserModel model);
	
	void delByPk(String id);
	
	void forbidUser(String id);
	
	void changePassword(String oldPassword,String password);
	
	void passwordReset(String id);
	
	void passwordResetAllUsers();
	
	void saveUserLoginLog(WlUserModel model);
	
	void userLogoutLog(String userLogId);
	
	/**
	 * 获取当前用户Id的公用方法 页面中使用
	 * @return
	 */
	String getCurrentUserId();
	
	SessionContextUserEntity getCurrentUser();
	
	/**
	 * 通过组织编号来获取在该组织下的所有用户
	 * @param orgId
	 * @return
	 */
	List<WlUserModel> getUsersByOrgId(String orgId);
	
	/**
	 * 通过用户姓名获取可用用户
	 * @param userName
	 * @return
	 */
	List<UsersByNameQueryItem> getUsersByUserName(String userName);
	
	/**
	 * 将禁用的用户启用
	 * @param id
	 * @author inn
	 */
	void enableUser(String id);
	
	/**
	 * 通过组织Id查询组织内的成员
	 * @param organizeId
	 * @return
	 */
	List<UserOrganizeQueryItem> getUsersByOrganizeId(String organizeId);
	
	/**
	 * 通过角色Id查询角色内的成员
	 * @param roleId
	 * @return
	 */
	List<RoleGrantedUserQueryItem> getUsersByRoleId(String roleId);
	
	/**
	 * 获取用户权限列表 function list
	 * @param userId
	 * @return
	 */
	List<WlFunctionModel> getGrantedFuncListByUserId(String userId);
	
	
	/**
	 * 在聊天模块中，根据条件查询用户
	 * @param hql
	 * @param obj
	 * @return
	 */
	List<ChatUsersQueryItem> findUsersByCondition(ChatUsersQueryCondition condition);
	
	/**
	 * 用于Moblie 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	String mobileLogin(String userName,String password);

}
