package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class FindOrgByNameOrManageQueryCondition extends BaseQueryCondition {

	private String orgName;
	private String orgManage;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgManage() {
		return orgManage;
	}

	public void setOrgManage(String orgManage) {
		this.orgManage = orgManage;
	}

}
