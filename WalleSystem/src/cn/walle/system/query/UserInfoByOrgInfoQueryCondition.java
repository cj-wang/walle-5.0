package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class UserInfoByOrgInfoQueryCondition extends BaseQueryCondition {

	private String name;
	private String manage;
	private String organizeId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

}
