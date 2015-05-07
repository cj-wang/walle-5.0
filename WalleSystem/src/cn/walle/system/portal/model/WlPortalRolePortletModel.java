package cn.walle.system.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Model class for 角色portlet对应表
 */
@Entity
@Table(name = "WL_PORTAL_ROLE_PORTLET")
@DynamicInsert
@DynamicUpdate
public class WlPortalRolePortletModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlPortalRolePortlet";

	public static final class FieldNames {
		/**
		 * rolePortletId
		 */
		public static final String rolePortletId = "rolePortletId";
		/**
		 * 角色
		 */
		public static final String roleId = "roleId";
		/**
		 * 组件
		 */
		public static final String portletId = "portletId";
		/**
		 * 默认显示
		 */
		public static final String defaultDisplay = "defaultDisplay";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//rolePortletId
	private String rolePortletId;
	//角色
	private String roleId;
	//组件
	private String portletId;
	//默认显示
	private String defaultDisplay;
	//tenantId
	private String tenantId;

	/**
	 * Get rolePortletId
	 */
	@Column(name = "ROLE_PORTLET_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getRolePortletId() {
		return rolePortletId;
	}

	/**
	 * Set rolePortletId
	 */
	public void setRolePortletId(String rolePortletId) {
		this.rolePortletId = rolePortletId;
		addValidField(FieldNames.rolePortletId);
	}

	/**
	 * Get 角色
	 */
	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	/**
	 * Set 角色
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
		addValidField(FieldNames.roleId);
	}

	/**
	 * Get 组件
	 */
	@Column(name = "PORTLET_ID")
	public String getPortletId() {
		return portletId;
	}

	/**
	 * Set 组件
	 */
	public void setPortletId(String portletId) {
		this.portletId = portletId;
		addValidField(FieldNames.portletId);
	}

	/**
	 * Get 默认显示
	 */
	@Column(name = "DEFAULT_DISPLAY")
	public String getDefaultDisplay() {
		return defaultDisplay;
	}

	/**
	 * Set 默认显示
	 */
	public void setDefaultDisplay(String defaultDisplay) {
		this.defaultDisplay = defaultDisplay;
		addValidField(FieldNames.defaultDisplay);
	}

	/**
	 * Get tenantId
	 */
	@Column(name = "TENANT_ID")
	public String getTenantId() {
		return tenantId;
	}

	/**
	 * Set tenantId
	 */
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
		addValidField(FieldNames.tenantId);
	}

}
