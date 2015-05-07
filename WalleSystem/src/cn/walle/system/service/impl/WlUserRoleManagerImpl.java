package cn.walle.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlRoleModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.model.WlUserRoleModel;
import cn.walle.system.service.WlRoleManager;
import cn.walle.system.service.WlSysLogManager;
import cn.walle.system.service.WlUserManager;
import cn.walle.system.service.WlUserRoleManager;

@Service
public class WlUserRoleManagerImpl extends BaseManagerImpl implements WlUserRoleManager {

	@Autowired
	WlUserManager userManager;
	
	@Autowired
	WlSysLogManager sysLogManager;
	
	@Autowired
	WlRoleManager roleManager;
	
	public WlUserRoleModel get(String id) {
		return this.dao.get(WlUserRoleModel.class, id);
	}

	public List<WlUserRoleModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlUserRoleModel.class, orderBy, pagingInfo);
	}

	public List<WlUserRoleModel> findByExample(WlUserRoleModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlUserRoleModel save(WlUserRoleModel model) {
		return this.dao.save(model);
	}

	public List<WlUserRoleModel> saveAll(Collection<WlUserRoleModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlUserRoleModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlUserRoleModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlUserRoleModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlUserRoleModel.class, ids);
	}

	public boolean checkIfAdmin(String userId){
		WlUserRoleModel wlUserRoleModel = new WlUserRoleModel();
		wlUserRoleModel.setUserId(userId);
		List<WlUserRoleModel> modeList = this.dao.findByExample(wlUserRoleModel, null, null);
		for(int i=0;i<modeList.size();i++){
			WlUserRoleModel model = (WlUserRoleModel)modeList.get(i);
			String roleId = model.getRoleId();
			if(roleId.equals("e1bf71ba-70c5-4325-b3e0-c492800e28e7")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取在某一角色下的用户
	 * @param roleId
	 * @return
	 */
	public List<WlUserModel> getUsersByRoleId(String roleId){
		List<WlUserModel> userList = new ArrayList<WlUserModel>(); 
		WlUserModel userModel = null;
		WlUserRoleModel example = new WlUserRoleModel();
		example.setRoleId(roleId);
		List<WlUserRoleModel> list = this.findByExample(example, null, null);
		if(list.size() == 0){
			return null;
		}
		for(WlUserRoleModel model : list){
			if(model.getUserId() == null || "".equals(model.getUserId())){
				continue;
			}
			userModel = userManager.get(model.getUserId());
			if(userModel != null){
				if("U".equals(userModel.getState())){
					userList.add(userModel);
				}
			}
		}
		return userList ;
	}

	public void saveUserRoles(String userId, String roles) {
		roles = roles.substring(0, roles.length()-1);
		String[] rolesArray = roles.split(",");
		for(int i=0; i<rolesArray.length; i++){
			WlUserRoleModel userRoleModel = new WlUserRoleModel();
			userRoleModel.setUserId(userId);
			userRoleModel.setRoleId(rolesArray[i]);
			this.save(userRoleModel);
			WlRoleModel roleModel = new WlRoleModel();
			roleModel.setRoleId(userRoleModel.getRoleId());
			roleModel = this.roleManager.findByExample(roleModel, null, null).get(0);
			WlUserModel userModel = null;
			userModel = this.userManager.get(userId);
			this.sysLogManager.saveSysLog("user", "授予用户角色", "1", "授予[" + userModel.getName() + "]用户[" + roleModel.getName() + "]角色", "授权成功",  null);
			//sysLogManager.saveSysLog("user", "用户角色授权", 1, "授权成功", "授权成功", null);
		}
	}
	
	public void removeUserRoles(String userRoleIds){
		userRoleIds = userRoleIds.substring(0, userRoleIds.length()-1);
		String[] userRoleIdsArray = userRoleIds.split(",");
		for(int i=0; i<userRoleIdsArray.length; i++){
			WlRoleModel roleModel = null;
			roleModel = this.roleManager.get(this.get(userRoleIdsArray[i]).getRoleId());
			WlUserModel userModel = null;
			userModel = this.userManager.get(this.get(userRoleIdsArray[i]).getUserId());
			
			this.sysLogManager.saveSysLog("user", "解除用户角色", "1",  "解除[" + userModel.getName() + "]用户的[" + roleModel.getName() + "]角色", "收回权限成功", null);
			this.removeByPk(userRoleIdsArray[i]);
		}
		//sysLogManager.saveSysLog("user", "用户角色解除", 1, "解除成功", "解除成功", null);
	}


	public void saveRolesForUser(String userId, String[] roleIds) {
		this.removeAll(this.dao.createCommonQuery(WlUserRoleModel.class)
				.addCondition(Condition.eq(WlUserRoleModel.FieldNames.userId, userId))
				.addDynamicCondition(Condition.notIn(WlUserRoleModel.FieldNames.roleId, roleIds))
				.query());
		
		WlUserRoleModel example = new WlUserRoleModel();
		example.setUserId(userId);
		Set<String> roleIdsSet = new HashSet<String>();
		for (WlUserRoleModel userRole : this.findByExample(example, null, null)) {
			roleIdsSet.add(userRole.getRoleId());
		}
		
		for (String roleId : roleIds) {
			if (! roleIdsSet.contains(roleId)) {
				WlUserRoleModel userRole = new WlUserRoleModel();
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				this.save(userRole);
			}
		}
	}

	public void saveUsersForRole(String roleId, String[] checkedUserIds, String[] uncheckedUserIds) {
		this.removeAll(this.dao.createCommonQuery(WlUserRoleModel.class)
				.addCondition(Condition.eq(WlUserRoleModel.FieldNames.roleId, roleId))
				.addCondition(Condition.in(WlUserRoleModel.FieldNames.userId, uncheckedUserIds))
				.query());
		
		WlUserRoleModel example = new WlUserRoleModel();
		example.setRoleId(roleId);
		Set<String> userIdsSet = new HashSet<String>();
		for (WlUserRoleModel userRole : this.findByExample(example, null, null)) {
			userIdsSet.add(userRole.getUserId());
		}
		
		for (String userId : checkedUserIds) {
			if (! userIdsSet.contains(userId)) {
				WlUserRoleModel userRole = new WlUserRoleModel();
				userRole.setUserId(userId);
				userRole.setRoleId(roleId);
				this.save(userRole);
			}
		}
	}
}
