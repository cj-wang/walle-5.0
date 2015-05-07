package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class OrgUserAsyncTreeCheckedByFuncQueryCondition extends BaseQueryCondition {

	private String parentId;
	private String funcId;
	private String tenantId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
