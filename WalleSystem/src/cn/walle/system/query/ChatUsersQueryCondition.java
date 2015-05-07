package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;


public class ChatUsersQueryCondition extends BaseQueryCondition {

	private String userId;
	private String organizeId;
	private String onlineStatus;
	private String offLine;
	private String userName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getOffLine() {
		return offLine;
	}

	public void setOffLine(String offLine) {
		this.offLine = offLine;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
