package cn.walle.system.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;


@Entity
public class OnlineUsersQueryItem extends BaseQueryItem {

	private String userId;

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
	}

}
