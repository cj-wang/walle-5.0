package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class SysCodeQueryCondition extends BaseQueryCondition {

	private String codeType;
	
	private String tenantId;

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

}
