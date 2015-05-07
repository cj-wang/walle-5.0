package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;


public class UserOrganizeQueryCondition extends BaseQueryCondition {

	private String name;
	private String loginName;
	private String organizeId;
	private String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
