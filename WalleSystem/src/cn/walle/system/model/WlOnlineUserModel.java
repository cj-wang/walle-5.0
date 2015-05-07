package cn.walle.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Model class for 在线用户
 */
@Entity
@Table(name = "WL_ONLINE_USER")
@DynamicInsert
@DynamicUpdate
public class WlOnlineUserModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlOnlineUser";

	public static final class FieldNames {
		/**
		 * 主键
		 */
		public static final String onlineUserId = "onlineUserId";
		/**
		 * 用户
		 */
		public static final String userId = "userId";
		/**
		 * session
		 */
		public static final String sessionId = "sessionId";
		/**
		 * 服务器地址
		 */
		public static final String serverName = "serverName";
		/**
		 * 客户端地址
		 */
		public static final String remoteAddress = "remoteAddress";
		/**
		 * 客户端用户
		 */
		public static final String remoteUser = "remoteUser";
		/**
		 * 登陆时间
		 */
		public static final String loginTime = "loginTime";
		/**
		 * 最后访问时间
		 */
		public static final String lastRequestTime = "lastRequestTime";
		/**
		 * expired
		 */
		public static final String expired = "expired";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//主键
	private String onlineUserId;
	//用户
	private String userId;
	//session
	private String sessionId;
	//服务器地址
	private String serverName;
	//客户端地址
	private String remoteAddress;
	//客户端用户
	private String remoteUser;
	//登陆时间
	private Date loginTime;
	//最后访问时间
	private Date lastRequestTime;
	//expired
	private String expired;
	//tenantId
	private String tenantId;

	/**
	 * Get 主键
	 */
	@Column(name = "ONLINE_USER_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getOnlineUserId() {
		return onlineUserId;
	}

	/**
	 * Set 主键
	 */
	public void setOnlineUserId(String onlineUserId) {
		this.onlineUserId = onlineUserId;
		addValidField(FieldNames.onlineUserId);
	}

	/**
	 * Get 用户
	 */
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	/**
	 * Set 用户
	 */
	public void setUserId(String userId) {
		this.userId = userId;
		addValidField(FieldNames.userId);
	}

	/**
	 * Get session
	 */
	@Column(name = "SESSION_ID")
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Set session
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
		addValidField(FieldNames.sessionId);
	}

	/**
	 * Get 服务器地址
	 */
	@Column(name = "SERVER_NAME")
	public String getServerName() {
		return serverName;
	}

	/**
	 * Set 服务器地址
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
		addValidField(FieldNames.serverName);
	}

	/**
	 * Get 客户端地址
	 */
	@Column(name = "REMOTE_ADDRESS")
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * Set 客户端地址
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
		addValidField(FieldNames.remoteAddress);
	}

	/**
	 * Get 客户端用户
	 */
	@Column(name = "REMOTE_USER")
	public String getRemoteUser() {
		return remoteUser;
	}

	/**
	 * Set 客户端用户
	 */
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
		addValidField(FieldNames.remoteUser);
	}

	/**
	 * Get 登陆时间
	 */
	@Column(name = "LOGIN_TIME")
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * Set 登陆时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
		addValidField(FieldNames.loginTime);
	}

	/**
	 * Get 最后访问时间
	 */
	@Column(name = "LAST_REQUEST_TIME")
	public Date getLastRequestTime() {
		return lastRequestTime;
	}

	/**
	 * Set 最后访问时间
	 */
	public void setLastRequestTime(Date lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
		addValidField(FieldNames.lastRequestTime);
	}

	/**
	 * Get expired
	 */
	@Column(name = "EXPIRED")
	public String getExpired() {
		return expired;
	}

	/**
	 * Set expired
	 */
	public void setExpired(String expired) {
		this.expired = expired;
		addValidField(FieldNames.expired);
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
