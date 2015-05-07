package cn.walle.system.entity;

import java.util.ArrayList;
import java.util.List;

import cn.walle.framework.core.support.springsecurity.AcegiDefaultUserDetails;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.system.model.WlTenantModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.query.AuthorizedFunctionsQueryItem;
import cn.walle.system.service.LoginManager;
import cn.walle.system.service.WlTenantManager;
import cn.walle.system.service.WlUserManager;

@SuppressWarnings("serial")
public class SessionContextUserEntity extends AcegiDefaultUserDetails {

	transient private WlUserModel wlUserModel;
	transient private WlTenantModel wlTenantModel;
	transient private List<String> authorizedFunctions;
	
	/**
	 * 取得当前登陆用户信息
	 * 
	 * @return
	 */
	public static SessionContextUserEntity currentUser() {
		return (SessionContextUserEntity) SessionContext.getUser();
	}

	
	public WlUserModel getUserModel() {
		if (this.wlUserModel == null) {
			this.wlUserModel = ContextUtils.getBeanOfType(WlUserManager.class).get(this.getUserId());
		}
		return this.wlUserModel;
	}

	public WlTenantModel getTenantModel() {
		if (this.wlTenantModel == null && this.getTenantId() != null) {
			this.wlTenantModel = ContextUtils.getBeanOfType(WlTenantManager.class).get(this.getTenantId());
		}
		return this.wlTenantModel;
	}

	public List<String> getAuthorizedFunctions() {
		if (this.authorizedFunctions == null) {
			this.authorizedFunctions = new ArrayList<String>();
			for (AuthorizedFunctionsQueryItem func : ContextUtils.getBeanOfType(LoginManager.class).getAuthorizedFunctions()) {
				this.authorizedFunctions.add(func.getFuncCode());
			}
		}
		return this.authorizedFunctions;
	}
	
	public boolean checkFunctionAuthorization(String funcCode) {
		if (this.authorizedFunctions == null) {
			return ContextUtils.getBeanOfType(LoginManager.class).checkFunctionAuthorization(funcCode);
		} else {
			return this.authorizedFunctions.contains(funcCode);
		}
	}
	
}
