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
 * Model class for sql日志
 */
@Entity
@Table(name = "WL_SQL_LOG")
@DynamicInsert
@DynamicUpdate
public class WlSqlLogModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlSqlLog";

	public static final class FieldNames {
		/**
		 * 主键
		 */
		public static final String sqlLogId = "sqlLogId";
		/**
		 * 用户
		 */
		public static final String userId = "userId";
		/**
		 * sql语句
		 */
		public static final String sqlStatement = "sqlStatement";
		/**
		 * 访问时间
		 */
		public static final String logTime = "logTime";
		/**
		 * 用时（毫秒）
		 */
		public static final String timeUsed = "timeUsed";
		/**
		 * 服务访问标识
		 */
		public static final String serviceAccessIndex = "serviceAccessIndex";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//主键
	private String sqlLogId;
	//用户
	private String userId;
	//sql语句
	private String sqlStatement;
	//访问时间
	private Date logTime;
	//用时（毫秒）
	private Long timeUsed;
	//服务访问标识
	private String serviceAccessIndex;
	//tenantId
	private String tenantId;

	/**
	 * Get 主键
	 */
	@Column(name = "SQL_LOG_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getSqlLogId() {
		return sqlLogId;
	}

	/**
	 * Set 主键
	 */
	public void setSqlLogId(String sqlLogId) {
		this.sqlLogId = sqlLogId;
		addValidField(FieldNames.sqlLogId);
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
	 * Get sql语句
	 */
	@Column(name = "SQL_STATEMENT")
	public String getSqlStatement() {
		return sqlStatement;
	}

	/**
	 * Set sql语句
	 */
	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement;
		addValidField(FieldNames.sqlStatement);
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
	 * Get 服务访问标识
	 */
	@Column(name = "SERVICE_ACCESS_INDEX")
	public String getServiceAccessIndex() {
		return serviceAccessIndex;
	}

	/**
	 * Set 服务访问标识
	 */
	public void setServiceAccessIndex(String serviceAccessIndex) {
		this.serviceAccessIndex = serviceAccessIndex;
		addValidField(FieldNames.serviceAccessIndex);
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
