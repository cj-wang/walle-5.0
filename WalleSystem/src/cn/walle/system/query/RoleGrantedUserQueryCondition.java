package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class RoleGrantedUserQueryCondition extends BaseQueryCondition {

	private String roleId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
