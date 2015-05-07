package cn.walle.system.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class OrgUserQueryCondition extends BaseQueryCondition {

	private String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
