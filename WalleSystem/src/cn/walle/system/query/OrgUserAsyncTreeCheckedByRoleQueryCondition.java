package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class OrgUserAsyncTreeCheckedByRoleQueryCondition extends BaseQueryCondition {

	private String parentId;
	private String roleId;
	private String tenantId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
