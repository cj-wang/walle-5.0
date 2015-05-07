package cn.walle.system.portal.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class HavePortalRoleQueryItem extends BaseQueryItem {

	private String rolePortletId;
	private String roleName;
	private String roleId;
	private String portletId;

	@Column(name = "ROLE_PORTLET_ID")
	public String getRolePortletId() {
		return rolePortletId;
	}

	public void setRolePortletId(String rolePortletId) {
		this.rolePortletId = rolePortletId;
		addValidField("rolePortletId");
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
		addValidField("roleName");
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
		addValidField("roleId");
	}

	@Column(name = "PORTLET_ID")
	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
		addValidField("portletId");
	}

}
