package cn.walle.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.system.model.WlOrganizeModel;
import cn.walle.system.model.WlTenantModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.model.WlUserRoleModel;
import cn.walle.system.service.WlOrganizeManager;
import cn.walle.system.service.WlTenantManager;
import cn.walle.system.service.WlUserManager;
import cn.walle.system.service.WlUserRoleManager;

@Service
public class WlTenantManagerImpl extends BaseManagerImpl implements WlTenantManager {

	@Autowired
	private WlOrganizeManager wlOrganizeManager;

	@Autowired
	private WlUserManager wlUserManager;
	
	@Autowired
	private WlUserRoleManager wlUserRoleManager;
	
	public WlTenantModel get(String id) {
		return this.dao.get(WlTenantModel.class, id);
	}

	public void register(WlTenantModel tenant, WlUserModel adminUser) {
		//保存租户
		tenant = this.dao.save(tenant);
		
		//根组织
		WlOrganizeModel org = new WlOrganizeModel();
		org.setName(tenant.getTenantName());
		org.setParentOrganizeId("0");
		org.setState("U");
		org.setTenantId(tenant.getUuid());
		org = this.wlOrganizeManager.saveModel(org);
		
		//管理员账户
		adminUser.setOrganizeId(org.getOrganizeId());
		adminUser.setState("U");
		adminUser.setTenantId(tenant.getUuid());
		adminUser = this.wlUserManager.saveModel(adminUser);
		
		//授权
		WlUserRoleModel userRole = new WlUserRoleModel();
		userRole.setUserId(adminUser.getUserId());
		userRole.setRoleId("1");
		userRole.setTenantId("1");  //该授权为平台级，租户内编辑授权不会被删除
		this.wlUserRoleManager.save(userRole);
		
		
	}

}
