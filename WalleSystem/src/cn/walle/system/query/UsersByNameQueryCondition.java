package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;


public class UsersByNameQueryCondition extends BaseQueryCondition {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
