package cn.walle.system.portal.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class HavePortalRoleQueryCondition extends BaseQueryCondition {

	private String portletId;

	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
	}

}
