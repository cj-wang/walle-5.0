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
 * Model class for 服务日志
 */
@Entity
@Table(name = "WL_SERVICE_LOG")
@DynamicInsert
@DynamicUpdate
public class WlServiceLogModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlServiceLog";

	public static final class FieldNames {
		/**
		 * 主键
		 */
		public static final String serviceLogId = "serviceLogId";
		/**
		 * 用户
		 */
		public static final String userId = "userId";
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
		 * 服务名
		 */
		public static final String serviceName = "serviceName";
		/**
		 * 方法名
		 */
		public static final String methodName = "methodName";
		/**
		 * 参数
		 */
		public static final String args = "args";
		/**
		 * 结果
		 */
		public static final String result = "result";
		/**
		 * 访问时间
		 */
		public static final String logTime = "logTime";
		/**
		 * 用时（毫秒）
		 */
		public static final String timeUsed = "timeUsed";
		/**
		 * 访问标识
		 */
		public static final String accessIndex = "accessIndex";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//主键
	private String serviceLogId;
	//用户
	private String userId;
	//服务器地址
	private String serverName;
	//客户端地址
	private String remoteAddress;
	//客户端用户
	private String remoteUser;
	//服务名
	private String serviceName;
	//方法名
	private String methodName;
	//参数
	private String args;
	//结果
	private String result;
	//访问时间
	private Date logTime;
	//用时（毫秒）
	private Long timeUsed;
	//访问标识
	private String accessIndex;
	//tenantId
	private String tenantId;

	/**
	 * Get 主键
	 */
	@Column(name = "SERVICE_LOG_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getServiceLogId() {
		return serviceLogId;
	}

	/**
	 * Set 主键
	 */
	public void setServiceLogId(String serviceLogId) {
		this.serviceLogId = serviceLogId;
		addValidField(FieldNames.serviceLogId);
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
	 * Get 服务名
	 */
	@Column(name = "SERVICE_NAME")
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Set 服务名
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
		addValidField(FieldNames.serviceName);
	}

	/**
	 * Get 方法名
	 */
	@Column(name = "METHOD_NAME")
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Set 方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
		addValidField(FieldNames.methodName);
	}

	/**
	 * Get 参数
	 */
	@Column(name = "ARGS")
	public String getArgs() {
		return args;
	}

	/**
	 * Set 参数
	 */
	public void setArgs(String args) {
		this.args = args;
		addValidField(FieldNames.args);
	}

	/**
	 * Get 结果
	 */
	@Column(name = "RESULT")
	public String getResult() {
		return result;
	}

	/**
	 * Set 结果
	 */
	public void setResult(String result) {
		this.result = result;
		addValidField(FieldNames.result);
	}

	/**
	 * Get 访问时间
	 */
	@Column(name = "LOG_TIME")
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * Set 访问时间
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
		addValidField(FieldNames.logTime);
	}

	/**
	 * Get 用时（毫秒）
	 */
	@Column(name = "TIME_USED")
	public Long getTimeUsed() {
		return timeUsed;
	}

	/**
	 * Set 用时（毫秒）
	 */
	public void setTimeUsed(Long timeUsed) {
		this.timeUsed = timeUsed;
		addValidField(FieldNames.timeUsed);
	}

	/**
	 * Get 访问标识
	 * ，UUID
	 */
	@Column(name = "ACCESS_INDEX")
	public String getAccessIndex() {
		return accessIndex;
	}

	/**
	 * Set 访问标识
	 * ，UUID
	 */
	public void setAccessIndex(String accessIndex) {
		this.accessIndex = accessIndex;
		addValidField(FieldNames.accessIndex);
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
