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
 * Model class for 系统日志表
 */
@Entity
@Table(name = "WL_SYS_LOG")
@DynamicInsert
@DynamicUpdate
public class WlSysLogModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlSysLog";

	public static final class FieldNames {
		/**
		 * 日志ID
		 */
		public static final String logId = "logId";
		/**
		 * 操作用户ID
		 */
		public static final String operUserId = "operUserId";
		/**
		 * 记录日期
		 */
		public static final String logDate = "logDate";
		/**
		 * 操作对象
		 */
		public static final String operOject = "operOject";
		/**
		 * 操作动作
		 */
		public static final String operAction = "operAction";
		/**
		 * 变更记录数
		 */
		public static final String modiRecords = "modiRecords";
		/**
		 * 日志描述
		 */
		public static final String logDesc = "logDesc";
		/**
		 * 结果
		 */
		public static final String result = "result";
		/**
		 * 状态
		 */
		public static final String state = "state";
		/**
		 * 备注
		 */
		public static final String remarks = "remarks";
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

	//日志ID
	private String logId;
	//操作用户ID
	private String operUserId;
	//记录日期
	private Date logDate;
	//操作对象
	private String operOject;
	//操作动作
	private String operAction;
	//变更记录数
	private Long modiRecords;
	//日志描述
	private String logDesc;
	//结果
	private String result;
	//状态
	private String state;
	//备注
	private String remarks;
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
	 * Get 日志ID
	 */
	@Column(name = "LOG_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getLogId() {
		return logId;
	}

	/**
	 * Set 日志ID
	 */
	public void setLogId(String logId) {
		this.logId = logId;
		addValidField(FieldNames.logId);
	}

	/**
	 * Get 操作用户ID
	 */
	@Column(name = "OPER_USER_ID")
	public String getOperUserId() {
		return operUserId;
	}

	/**
	 * Set 操作用户ID
	 */
	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId;
		addValidField(FieldNames.operUserId);
	}

	/**
	 * Get 记录日期
	 */
	@Column(name = "LOG_DATE")
	public Date getLogDate() {
		return logDate;
	}

	/**
	 * Set 记录日期
	 */
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
		addValidField(FieldNames.logDate);
	}

	/**
	 * Get 操作对象
	 */
	@Column(name = "OPER_OJECT")
	public String getOperOject() {
		return operOject;
	}

	/**
	 * Set 操作对象
	 */
	public void setOperOject(String operOject) {
		this.operOject = operOject;
		addValidField(FieldNames.operOject);
	}

	/**
	 * Get 操作动作
	 */
	@Column(name = "OPER_ACTION")
	public String getOperAction() {
		return operAction;
	}

	/**
	 * Set 操作动作
	 */
	public void setOperAction(String operAction) {
		this.operAction = operAction;
		addValidField(FieldNames.operAction);
	}

	/**
	 * Get 变更记录数
	 */
	@Column(name = "MODI_RECORDS")
	public Long getModiRecords() {
		return modiRecords;
	}

	/**
	 * Set 变更记录数
	 */
	public void setModiRecords(Long modiRecords) {
		this.modiRecords = modiRecords;
		addValidField(FieldNames.modiRecords);
	}

	/**
	 * Get 日志描述
	 */
	@Column(name = "LOG_DESC")
	public String getLogDesc() {
		return logDesc;
	}

	/**
	 * Set 日志描述
	 */
	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
		addValidField(FieldNames.logDesc);
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
	 * Get 状态
	 */
	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	/**
	 * Set 状态
	 */
	public void setState(String state) {
		this.state = state;
		addValidField(FieldNames.state);
	}

	/**
	 * Get 备注
	 */
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set 备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
		addValidField(FieldNames.remarks);
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
