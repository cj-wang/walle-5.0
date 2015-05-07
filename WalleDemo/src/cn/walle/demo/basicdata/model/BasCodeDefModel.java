package cn.walle.demo.basicdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Model class for 数据字典表
 */
@Entity
@Table(name = "BAS_CODE_DEF")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BasCodeDefModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "BasCodeDef";

	public static final class FieldNames {
		/**
		 * 主键ID
		 */
		public static final String basCodeDefId = "basCodeDefId";
		/**
		 * Phisical
		 */
		public static final String basCodeTypeId = "basCodeTypeId";
		/**
		 * 代码值
		 */
		public static final String codeValue = "codeValue";
		/**
		 * 编码内容（中文）
		 */
		public static final String displayValue = "displayValue";
		/**
		 * 编码内容（英文）
		 */
		public static final String displayValueEn = "displayValueEn";
		/**
		 * 是否允许修改
		 */
		public static final String modifiable = "modifiable";
		/**
		 * 录入人
		 */
		public static final String createdByUser = "createdByUser";
		/**
		 * 创建组织
		 */
		public static final String createdOffice = "createdOffice";
		/**
		 * 创建时间
		 */
		public static final String createdDtmLoc = "createdDtmLoc";
		/**
		 * CREATED_TIME_ZONE
		 */
		public static final String createdTimeZone = "createdTimeZone";
		/**
		 * 修改人
		 */
		public static final String updatedByUser = "updatedByUser";
		/**
		 * 修改组织
		 */
		public static final String updatedOffice = "updatedOffice";
		/**
		 * 修改时间
		 */
		public static final String updatedDtmLoc = "updatedDtmLoc";
		/**
		 * UPDATED
		 */
		public static final String updatedTimeZone = "updatedTimeZone";
		/**
		 * RECORD
		 */
		public static final String recordVersion = "recordVersion";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
		/**
		 * 组织编码
		 */
		public static final String officeCode = "officeCode";
		/**
		 * seq
		 */
		public static final String seq = "seq";
	}

	//主键ID
	private String basCodeDefId;
	//Phisical
	private String basCodeTypeId;
	//代码值
	private String codeValue;
	//编码内容（中文）
	private String displayValue;
	//编码内容（英文）
	private String displayValueEn;
	//是否允许修改
	private Integer modifiable;
	//录入人
	private String createdByUser;
	//创建组织
	private String createdOffice;
	//创建时间
	private Date createdDtmLoc;
	//CREATED_TIME_ZONE
	private String createdTimeZone;
	//修改人
	private String updatedByUser;
	//修改组织
	private String updatedOffice;
	//修改时间
	private Date updatedDtmLoc;
	//UPDATED
	private String updatedTimeZone;
	//RECORD
	private Long recordVersion;
	//公司代码
	private String principalGroupCode;
	//组织编码
	private String officeCode;
	//seq
	private Long seq;

	/**
	 * Get 主键ID
	 */
	@Column(name = "BAS_CODE_DEF_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getBasCodeDefId() {
		return basCodeDefId;
	}

	/**
	 * Set 主键ID
	 */
	public void setBasCodeDefId(String basCodeDefId) {
		this.basCodeDefId = basCodeDefId;
		addValidField(FieldNames.basCodeDefId);
	}

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "BAS_CODE_TYPE_ID")
	public String getBasCodeTypeId() {
		return basCodeTypeId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setBasCodeTypeId(String basCodeTypeId) {
		this.basCodeTypeId = basCodeTypeId;
		addValidField(FieldNames.basCodeTypeId);
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
	 * Get 编码内容（中文）
	 */
	@Column(name = "DISPLAY_VALUE")
	public String getDisplayValue() {
		return displayValue;
	}

	/**
	 * Set 编码内容（中文）
	 */
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
		addValidField(FieldNames.displayValue);
	}

	/**
	 * Get 编码内容（英文）
	 */
	@Column(name = "DISPLAY_VALUE_EN")
	public String getDisplayValueEn() {
		return displayValueEn;
	}

	/**
	 * Set 编码内容（英文）
	 */
	public void setDisplayValueEn(String displayValueEn) {
		this.displayValueEn = displayValueEn;
		addValidField(FieldNames.displayValueEn);
	}

	/**
	 * Get 是否允许修改
	 */
	@Column(name = "MODIFIABLE")
	public Integer getModifiable() {
		return modifiable;
	}

	/**
	 * Set 是否允许修改
	 */
	public void setModifiable(Integer modifiable) {
		this.modifiable = modifiable;
		addValidField(FieldNames.modifiable);
	}

	/**
	 * Get 录入人
	 */
	@Column(name = "CREATED_BY_USER")
	public String getCreatedByUser() {
		return createdByUser;
	}

	/**
	 * Set 录入人
	 */
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
		addValidField(FieldNames.createdByUser);
	}

	/**
	 * Get 创建组织
	 */
	@Column(name = "CREATED_OFFICE")
	public String getCreatedOffice() {
		return createdOffice;
	}

	/**
	 * Set 创建组织
	 */
	public void setCreatedOffice(String createdOffice) {
		this.createdOffice = createdOffice;
		addValidField(FieldNames.createdOffice);
	}

	/**
	 * Get 创建时间
	 */
	@Column(name = "CREATED_DTM_LOC")
	public Date getCreatedDtmLoc() {
		return createdDtmLoc;
	}

	/**
	 * Set 创建时间
	 */
	public void setCreatedDtmLoc(Date createdDtmLoc) {
		this.createdDtmLoc = createdDtmLoc;
		addValidField(FieldNames.createdDtmLoc);
	}

	/**
	 * Get CREATED_TIME_ZONE
	 */
	@Column(name = "CREATED_TIME_ZONE")
	public String getCreatedTimeZone() {
		return createdTimeZone;
	}

	/**
	 * Set CREATED_TIME_ZONE
	 */
	public void setCreatedTimeZone(String createdTimeZone) {
		this.createdTimeZone = createdTimeZone;
		addValidField(FieldNames.createdTimeZone);
	}

	/**
	 * Get 修改人
	 */
	@Column(name = "UPDATED_BY_USER")
	public String getUpdatedByUser() {
		return updatedByUser;
	}

	/**
	 * Set 修改人
	 */
	public void setUpdatedByUser(String updatedByUser) {
		this.updatedByUser = updatedByUser;
		addValidField(FieldNames.updatedByUser);
	}

	/**
	 * Get 修改组织
	 */
	@Column(name = "UPDATED_OFFICE")
	public String getUpdatedOffice() {
		return updatedOffice;
	}

	/**
	 * Set 修改组织
	 */
	public void setUpdatedOffice(String updatedOffice) {
		this.updatedOffice = updatedOffice;
		addValidField(FieldNames.updatedOffice);
	}

	/**
	 * Get 修改时间
	 */
	@Column(name = "UPDATED_DTM_LOC")
	public Date getUpdatedDtmLoc() {
		return updatedDtmLoc;
	}

	/**
	 * Set 修改时间
	 */
	public void setUpdatedDtmLoc(Date updatedDtmLoc) {
		this.updatedDtmLoc = updatedDtmLoc;
		addValidField(FieldNames.updatedDtmLoc);
	}

	/**
	 * Get UPDATED
	 * TIME ZONE
	 */
	@Column(name = "UPDATED_TIME_ZONE")
	public String getUpdatedTimeZone() {
		return updatedTimeZone;
	}

	/**
	 * Set UPDATED
	 * TIME ZONE
	 */
	public void setUpdatedTimeZone(String updatedTimeZone) {
		this.updatedTimeZone = updatedTimeZone;
		addValidField(FieldNames.updatedTimeZone);
	}

	/**
	 * Get RECORD
	 * TIME STAMP
	 */
	@Column(name = "RECORD_VERSION")
	@Version
	public Long getRecordVersion() {
		return recordVersion;
	}

	/**
	 * Set RECORD
	 * TIME STAMP
	 */
	public void setRecordVersion(Long recordVersion) {
		this.recordVersion = recordVersion;
		addValidField(FieldNames.recordVersion);
	}

	/**
	 * Get 公司代码
	 */
	@Column(name = "PRINCIPAL_GROUP_CODE")
	public String getPrincipalGroupCode() {
		return principalGroupCode;
	}

	/**
	 * Set 公司代码
	 */
	public void setPrincipalGroupCode(String principalGroupCode) {
		this.principalGroupCode = principalGroupCode;
		addValidField(FieldNames.principalGroupCode);
	}

	/**
	 * Get 组织编码
	 */
	@Column(name = "OFFICE_CODE")
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * Set 组织编码
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
		addValidField(FieldNames.officeCode);
	}

	/**
	 * Get seq
	 */
	@Column(name = "SEQ")
	public Long getSeq() {
		return seq;
	}

	/**
	 * Set seq
	 */
	public void setSeq(Long seq) {
		this.seq = seq;
		addValidField(FieldNames.seq);
	}

}
