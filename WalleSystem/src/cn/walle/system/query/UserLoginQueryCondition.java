package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class UserLoginQueryCondition extends BaseQueryCondition {

	private String[] state;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String[] getState() {
		return state;
	}

	public void setState(String[] state) {
		this.state = state;
	}

}
