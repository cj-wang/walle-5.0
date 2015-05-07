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
 * Model class for 系统代码表
 */
@Entity
@Table(name = "WL_SYS_CODE")
@DynamicInsert
@DynamicUpdate
public class WlSysCodeModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlSysCode";

	public static final class FieldNames {
		/**
		 * 代码主键
		 */
		public static final String codeId = "codeId";
		/**
		 * 类型名称
		 */
		public static final String codeTypeId = "codeTypeId";
		/**
		 * 代码名称
		 */
		public static final String codeName = "codeName";
		/**
		 * 代码值
		 */
		public static final String codeValue = "codeValue";
		/**
		 * 图片
		 */
		public static final String icon = "icon";
		/**
		 * 父代码值
		 */
		public static final String parentCodeValue = "parentCodeValue";
		/**
		 * 代码描述
		 */
		public static final String codeDesc = "codeDesc";
		/**
		 * 代码组
		 */
		public static final String codeGroup = "codeGroup";
		/**
		 * 顺序
		 */
		public static final String sort = "sort";
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

	//代码主键
	private String codeId;
	//类型名称
	private String codeTypeId;
	//代码名称
	private String codeName;
	//代码值
	private String codeValue;
	//图片
	private String icon;
	//父代码值
	private String parentCodeValue;
	//代码描述
	private String codeDesc;
	//代码组
	private String codeGroup;
	//顺序
	private Long sort;
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
	 * Get 代码主键
	 */
	@Column(name = "CODE_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getCodeId() {
		return codeId;
	}

	/**
	 * Set 代码主键
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
		addValidField(FieldNames.codeId);
	}

	/**
	 * Get 类型名称
	 * selectCode.syscodetype
	 */
	@Column(name = "CODE_TYPE_ID")
	public String getCodeTypeId() {
		return codeTypeId;
	}

	/**
	 * Set 类型名称
	 * selectCode.syscodetype
	 */
	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
		addValidField(FieldNames.codeTypeId);
	}

	/**
	 * Get 代码名称
	 */
	@Column(name = "CODE_NAME")
	public String getCodeName() {
		return codeName;
	}

	/**
	 * Set 代码名称
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
		addValidField(FieldNames.codeName);
	}

	/**
	 * Get 代码值
	 */
	@Column(name = "CODE_VALUE")
	public String getCodeValue() {
		return codeValue;
	}

	/**
	 * Set 代码值
	 */
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
		addValidField(FieldNames.codeValue);
	}

	/**
	 * Get 图片
	 */
	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	/**
	 * Set 图片
	 */
	public void setIcon(String icon) {
		this.icon = icon;
		addValidField(FieldNames.icon);
	}

	/**
	 * Get 父代码值
	 */
	@Column(name = "PARENT_CODE_VALUE")
	public String getParentCodeValue() {
		return parentCodeValue;
	}

	/**
	 * Set 父代码值
	 */
	public void setParentCodeValue(String parentCodeValue) {
		this.parentCodeValue = parentCodeValue;
		addValidField(FieldNames.parentCodeValue);
	}

	/**
	 * Get 代码描述
	 */
	@Column(name = "CODE_DESC")
	public String getCodeDesc() {
		return codeDesc;
	}

	/**
	 * Set 代码描述
	 */
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
		addValidField(FieldNames.codeDesc);
	}

	/**
	 * Get 代码组
	 */
	@Column(name = "CODE_GROUP")
	public String getCodeGroup() {
		return codeGroup;
	}

	/**
	 * Set 代码组
	 */
	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
		addValidField(FieldNames.codeGroup);
	}

	/**
	 * Get 顺序
	 */
	@Column(name = "SORT")
	public Long getSort() {
		return sort;
	}

	/**
	 * Set 顺序
	 */
	public void setSort(Long sort) {
		this.sort = sort;
		addValidField(FieldNames.sort);
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
