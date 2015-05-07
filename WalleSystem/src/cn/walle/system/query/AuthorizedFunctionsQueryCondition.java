package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class AuthorizedFunctionsQueryCondition extends BaseQueryCondition {

	private String userId;
	private String[] funcTypes;
	private String tenantId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getFuncTypes() {
		return funcTypes;
	}

	public void setFuncTypes(String[] funcTypes) {
		this.funcTypes = funcTypes;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
