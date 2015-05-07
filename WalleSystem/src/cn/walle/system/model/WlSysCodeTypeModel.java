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
 * Model class for 系统代码类型表
 */
@Entity
@Table(name = "WL_SYS_CODE_TYPE")
@DynamicInsert
@DynamicUpdate
public class WlSysCodeTypeModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlSysCodeType";

	public static final class FieldNames {
		/**
		 * 类型ID
		 */
		public static final String codeTypeId = "codeTypeId";
		/**
		 * 类型名称
		 */
		public static final String codeTypeName = "codeTypeName";
		/**
		 * 类型值
		 */
		public static final String typeCode = "typeCode";
		/**
		 * 类型描述
		 */
		public static final String codeTypeDesc = "codeTypeDesc";
		/**
		 * 数据类型
		 */
		public static final String dateType = "dateType";
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
		 * 系統
		 */
		public static final String system = "system";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//类型ID
	private String codeTypeId;
	//类型名称
	private String codeTypeName;
	//类型值
	private String typeCode;
	//类型描述
	private String codeTypeDesc;
	//数据类型
	private String dateType;
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
	//系統
	private String system;
	//tenantId
	private String tenantId;

	/**
	 * Get 类型ID
	 */
	@Column(name = "CODE_TYPE_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getCodeTypeId() {
		return codeTypeId;
	}

	/**
	 * Set 类型ID
	 */
	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
		addValidField(FieldNames.codeTypeId);
	}

	/**
	 * Get 类型名称
	 */
	@Column(name = "CODE_TYPE_NAME")
	public String getCodeTypeName() {
		return codeTypeName;
	}

	/**
	 * Set 类型名称
	 */
	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
		addValidField(FieldNames.codeTypeName);
	}

	/**
	 * Get 类型值
	 */
	@Column(name = "TYPE_CODE")
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * Set 类型值
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
		addValidField(FieldNames.typeCode);
	}

	/**
	 * Get 类型描述
	 */
	@Column(name = "CODE_TYPE_DESC")
	public String getCodeTypeDesc() {
		return codeTypeDesc;
	}

	/**
	 * Set 类型描述
	 */
	public void setCodeTypeDesc(String codeTypeDesc) {
		this.codeTypeDesc = codeTypeDesc;
		addValidField(FieldNames.codeTypeDesc);
	}

	/**
	 * Get 数据类型
	 */
	@Column(name = "DATE_TYPE")
	public String getDateType() {
		return dateType;
	}

	/**
	 * Set 数据类型
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
		addValidField(FieldNames.dateType);
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
	 * Get 系統
	 * selectCode.sys
	 */
	@Column(name = "SYSTEM")
	public String getSystem() {
		return system;
	}

	/**
	 * Set 系統
	 * selectCode.sys
	 */
	public void setSystem(String system) {
		this.system = system;
		addValidField(FieldNames.system);
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
