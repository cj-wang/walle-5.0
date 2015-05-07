package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class TenantFunctionsQueryCondition extends BaseQueryCondition {

	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
