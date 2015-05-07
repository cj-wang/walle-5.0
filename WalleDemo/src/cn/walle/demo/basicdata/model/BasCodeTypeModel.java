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
 * Model class for 数据字典类型
 */
@Entity
@Table(name = "BAS_CODE_TYPE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BasCodeTypeModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "BasCodeType";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String basCodeTypeId = "basCodeTypeId";
		/**
		 * 类型编码
		 */
		public static final String codeType = "codeType";
		/**
		 * 类型名称
		 */
		public static final String codeName = "codeName";
		/**
		 * 类型等级
		 */
		public static final String codeGrade = "codeGrade";
		/**
		 * 编码宽度
		 */
		public static final String codeWidth = "codeWidth";
		/**
		 * 类型描述
		 */
		public static final String codeDesc = "codeDesc";
		/**
		 * 组织编码
		 */
		public static final String officeCode = "officeCode";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
		/**
		 * 来源类型
		 */
		public static final String sourceType = "sourceType";
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
	}

	//Phisical
	private String basCodeTypeId;
	//类型编码
	private String codeType;
	//类型名称
	private String codeName;
	//类型等级
	private Integer codeGrade;
	//编码宽度
	private Long codeWidth;
	//类型描述
	private String codeDesc;
	//组织编码
	private String officeCode;
	//公司代码
	private String principalGroupCode;
	//来源类型
	private String sourceType;
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

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "BAS_CODE_TYPE_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
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
	 * Get 类型编码
	 */
	@Column(name = "CODE_TYPE")
	public String getCodeType() {
		return codeType;
	}

	/**
	 * Set 类型编码
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
		addValidField(FieldNames.codeType);
	}

	/**
	 * Get 类型名称
	 */
	@Column(name = "CODE_NAME")
	public String getCodeName() {
		return codeName;
	}

	/**
	 * Set 类型名称
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
		addValidField(FieldNames.codeName);
	}

	/**
	 * Get 类型等级
	 */
	@Column(name = "CODE_GRADE")
	public Integer getCodeGrade() {
		return codeGrade;
	}

	/**
	 * Set 类型等级
	 */
	public void setCodeGrade(Integer codeGrade) {
		this.codeGrade = codeGrade;
		addValidField(FieldNames.codeGrade);
	}

	/**
	 * Get 编码宽度
	 */
	@Column(name = "CODE_WIDTH")
	public Long getCodeWidth() {
		return codeWidth;
	}

	/**
	 * Set 编码宽度
	 */
	public void setCodeWidth(Long codeWidth) {
		this.codeWidth = codeWidth;
		addValidField(FieldNames.codeWidth);
	}

	/**
	 * Get 类型描述
	 */
	@Column(name = "CODE_DESC")
	public String getCodeDesc() {
		return codeDesc;
	}

	/**
	 * Set 类型描述
	 */
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
		addValidField(FieldNames.codeDesc);
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
	 * Get 来源类型
	 */
	@Column(name = "SOURCE_TYPE")
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * Set 来源类型
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
		addValidField(FieldNames.sourceType);
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

}
