package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class UserGrantedRoleQueryCondition extends BaseQueryCondition {

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
