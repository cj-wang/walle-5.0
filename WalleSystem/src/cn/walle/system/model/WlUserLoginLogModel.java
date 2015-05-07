package cn.walle.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.model.OperationLog;

/**
 * Model class for 用户登录日志
 */
@Entity
@Table(name = "WL_USER_LOGIN_LOG")
@DynamicInsert
@DynamicUpdate
public class WlUserLoginLogModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlUserLoginLog";

	public static final class FieldNames {
		/**
		 * 员工登录日志内部部门
		 */
		public static final String userLoginLogId = "userLoginLogId";
		/**
		 * 用户编码
		 */
		public static final String userId = "userId";
		/**
		 * 登录时间
		 */
		public static final String loginTime = "loginTime";
		/**
		 * 退出时间
		 */
		public static final String logoutTime = "logoutTime";
		/**
		 * 登录成功标志
		 */
		public static final String tryTimes = "tryTimes";
		/**
		 * 登录IP
		 */
		public static final String userIp = "userIp";
		/**
		 * 登录主机名
		 */
		public static final String hostName = "hostName";
		/**
		 * creator
		 */
		public static final String creator = "creator";
		/**
		 * createTime
		 */
		public static final String createTime = "createTime";
		/**
		 * modifier
		 */
		public static final String modifier = "modifier";
		/**
		 * modifyTime
		 */
		public static final String modifyTime = "modifyTime";
		/**
		 * recVer
		 */
		public static final String recVer = "recVer";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//员工登录日志内部部门
	private String userLoginLogId;
	//用户编码
	private String userId;
	//登录时间
	private Date loginTime;
	//退出时间
	private Date logoutTime;
	//登录成功标志
	private Long tryTimes;
	//登录IP
	private String userIp;
	//登录主机名
	private String hostName;
	//creator
	private String creator;
	//createTime
	private Date createTime;
	//modifier
	private String modifier;
	//modifyTime
	private Date modifyTime;
	//recVer
	private Long recVer;
	//tenantId
	private String tenantId;

	/**
	 * Get 员工登录日志内部部门
	 */
	@Column(name = "USER_LOGIN_LOG_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getUserLoginLogId() {
		return userLoginLogId;
	}

	/**
	 * Set 员工登录日志内部部门
	 */
	public void setUserLoginLogId(String userLoginLogId) {
		this.userLoginLogId = userLoginLogId;
		addValidField(FieldNames.userLoginLogId);
	}

	/**
	 * Get 用户编码
	 */
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	/**
	 * Set 用户编码
	 */
	public void setUserId(String userId) {
		this.userId = userId;
		addValidField(FieldNames.userId);
	}

	/**
	 * Get 登录时间
	 */
	@Column(name = "LOGIN_TIME")
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * Set 登录时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
		addValidField(FieldNames.loginTime);
	}

	/**
	 * Get 退出时间
	 */
	@Column(name = "LOGOUT_TIME")
	public Date getLogoutTime() {
		return logoutTime;
	}

	/**
	 * Set 退出时间
	 */
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
		addValidField(FieldNames.logoutTime);
	}

	/**
	 * Get 登录成功标志
	 */
	@Column(name = "TRY_TIMES")
	public Long getTryTimes() {
		return tryTimes;
	}

	/**
	 * Set 登录成功标志
	 */
	public void setTryTimes(Long tryTimes) {
		this.tryTimes = tryTimes;
		addValidField(FieldNames.tryTimes);
	}

	/**
	 * Get 登录IP
	 */
	@Column(name = "USER_IP")
	public String getUserIp() {
		return userIp;
	}

	/**
	 * Set 登录IP
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
		addValidField(FieldNames.userIp);
	}

	/**
	 * Get 登录主机名
	 */
	@Column(name = "HOST_NAME")
	public String getHostName() {
		return hostName;
	}

	/**
	 * Set 登录主机名
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
		addValidField(FieldNames.hostName);
	}

	/**
	 * Get creator
	 */
	@Column(name = "CREATOR")
	public String getCreator() {
		return creator;
	}

	/**
	 * Set creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
		addValidField(FieldNames.creator);
	}

	/**
	 * Get createTime
	 */
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Set createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		addValidField(FieldNames.createTime);
	}

	/**
	 * Get modifier
	 */
	@Column(name = "MODIFIER")
	public String getModifier() {
		return modifier;
	}

	/**
	 * Set modifier
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
		addValidField(FieldNames.modifier);
	}

	/**
	 * Get modifyTime
	 */
	@Column(name = "MODIFY_TIME")
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Set modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
		addValidField(FieldNames.modifyTime);
	}

	/**
	 * Get recVer
	 */
	@Column(name = "REC_VER")
	@Version
	public Long getRecVer() {
		return recVer;
	}

	/**
	 * Set recVer
	 */
	public void setRecVer(Long recVer) {
		this.recVer = recVer;
		addValidField(FieldNames.recVer);
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
