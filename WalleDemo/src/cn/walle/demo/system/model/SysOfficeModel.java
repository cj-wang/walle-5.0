package cn.walle.demo.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.model.OperationLog;

/**
 * Model class for 组织、公司或办事处
 */
@Entity
@Table(name = "SYS_OFFICE")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SysOfficeModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "SysOffice";

	public static final class FieldNames {
		/**
		 * UUID
		 */
		public static final String officeUuid = "officeUuid";
		/**
		 * 组织编码
		 */
		public static final String officeCode = "officeCode";
		/**
		 * 组织类型
		 */
		public static final String officeType = "officeType";
		/**
		 * 组织全称
		 */
		public static final String officeName = "officeName";
		/**
		 * 组织英文名
		 */
		public static final String officeNameEn = "officeNameEn";
		/**
		 * 上一级组织UUID
		 */
		public static final String preOfficeUuid = "preOfficeUuid";
		/**
		 * 组织简称
		 */
		public static final String abbrev = "abbrev";
		/**
		 * 工商登记号
		 */
		public static final String businessRegisterNo = "businessRegisterNo";
		/**
		 * 税务登记号
		 */
		public static final String taxRegisterNo = "taxRegisterNo";
		/**
		 * 联系人
		 */
		public static final String contact = "contact";
		/**
		 * 邮件地址
		 */
		public static final String email = "email";
		/**
		 * 电话
		 */
		public static final String tel = "tel";
		/**
		 * 传真
		 */
		public static final String fax = "fax";
		/**
		 * 地址
		 */
		public static final String address = "address";
		/**
		 * 为内部办事处
		 */
		public static final String isInternal = "isInternal";
		/**
		 * 作为客户
		 */
		public static final String isCustomer = "isCustomer";
		/**
		 * 作为结算公司
		 */
		public static final String isDept = "isDept";
		/**
		 * 汇率体系
		 */
		public static final String xchgrName = "xchgrName";
		/**
		 * 所属公司代码
		 */
		public static final String customerCode = "customerCode";
		/**
		 * 语言
		 */
		public static final String language = "language";
		/**
		 * 国家代码
		 */
		public static final String countryCode = "countryCode";
		/**
		 * 省份代码
		 */
		public static final String provinceCode = "provinceCode";
		/**
		 * 城市代码
		 */
		public static final String cityCode = "cityCode";
		/**
		 * 地点代码
		 */
		public static final String siteCode = "siteCode";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
		/**
		 * 状态
		 */
		public static final String status = "status";
		/**
		 * 作废日期
		 */
		public static final String cancelDate = "cancelDate";
		/**
		 * 控制字
		 */
		public static final String controlWord = "controlWord";
		/**
		 * 并发访问控制
		 */
		public static final String recVer = "recVer";
		/**
		 * 创建人
		 */
		public static final String creator = "creator";
		/**
		 * 创建时间
		 */
		public static final String createTime = "createTime";
		/**
		 * 修改人
		 */
		public static final String modifier = "modifier";
		/**
		 * 修改时间
		 */
		public static final String modifyTime = "modifyTime";
	}

	//UUID
	private String officeUuid;
	//组织编码
	private String officeCode;
	//组织类型
	private String officeType;
	//组织全称
	private String officeName;
	//组织英文名
	private String officeNameEn;
	//上一级组织UUID
	private String preOfficeUuid;
	//组织简称
	private String abbrev;
	//工商登记号
	private String businessRegisterNo;
	//税务登记号
	private String taxRegisterNo;
	//联系人
	private String contact;
	//邮件地址
	private String email;
	//电话
	private String tel;
	//传真
	private String fax;
	//地址
	private String address;
	//为内部办事处
	private Integer isInternal;
	//作为客户
	private Integer isCustomer;
	//作为结算公司
	private Integer isDept;
	//汇率体系
	private String xchgrName;
	//所属公司代码
	private String customerCode;
	//语言
	private String language;
	//国家代码
	private String countryCode;
	//省份代码
	private String provinceCode;
	//城市代码
	private String cityCode;
	//地点代码
	private String siteCode;
	//备注
	private String remark;
	//状态
	private String status;
	//作废日期
	private Date cancelDate;
	//控制字
	private String controlWord;
	//并发访问控制
	private Long recVer;
	//创建人
	private String creator;
	//创建时间
	private Date createTime;
	//修改人
	private String modifier;
	//修改时间
	private Date modifyTime;

	/**
	 * Get UUID
	 */
	@Column(name = "OFFICE_UUID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getOfficeUuid() {
		return officeUuid;
	}

	/**
	 * Set UUID
	 */
	public void setOfficeUuid(String officeUuid) {
		this.officeUuid = officeUuid;
		addValidField(FieldNames.officeUuid);
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
	 * Get 组织类型
	 */
	@Column(name = "OFFICE_TYPE")
	public String getOfficeType() {
		return officeType;
	}

	/**
	 * Set 组织类型
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
		addValidField(FieldNames.officeType);
	}

	/**
	 * Get 组织全称
	 */
	@Column(name = "OFFICE_NAME")
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * Set 组织全称
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
		addValidField(FieldNames.officeName);
	}

	/**
	 * Get 组织英文名
	 */
	@Column(name = "OFFICE_NAME_EN")
	public String getOfficeNameEn() {
		return officeNameEn;
	}

	/**
	 * Set 组织英文名
	 */
	public void setOfficeNameEn(String officeNameEn) {
		this.officeNameEn = officeNameEn;
		addValidField(FieldNames.officeNameEn);
	}

	/**
	 * Get 上一级组织UUID
	 */
	@Column(name = "PRE_OFFICE_UUID")
	public String getPreOfficeUuid() {
		return preOfficeUuid;
	}

	/**
	 * Set 上一级组织UUID
	 */
	public void setPreOfficeUuid(String preOfficeUuid) {
		this.preOfficeUuid = preOfficeUuid;
		addValidField(FieldNames.preOfficeUuid);
	}

	/**
	 * Get 组织简称
	 */
	@Column(name = "ABBREV")
	public String getAbbrev() {
		return abbrev;
	}

	/**
	 * Set 组织简称
	 */
	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
		addValidField(FieldNames.abbrev);
	}

	/**
	 * Get 工商登记号
	 */
	@Column(name = "BUSINESS_REGISTER_NO")
	public String getBusinessRegisterNo() {
		return businessRegisterNo;
	}

	/**
	 * Set 工商登记号
	 */
	public void setBusinessRegisterNo(String businessRegisterNo) {
		this.businessRegisterNo = businessRegisterNo;
		addValidField(FieldNames.businessRegisterNo);
	}

	/**
	 * Get 税务登记号
	 */
	@Column(name = "TAX_REGISTER_NO")
	public String getTaxRegisterNo() {
		return taxRegisterNo;
	}

	/**
	 * Set 税务登记号
	 */
	public void setTaxRegisterNo(String taxRegisterNo) {
		this.taxRegisterNo = taxRegisterNo;
		addValidField(FieldNames.taxRegisterNo);
	}

	/**
	 * Get 联系人
	 */
	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}

	/**
	 * Set 联系人
	 */
	public void setContact(String contact) {
		this.contact = contact;
		addValidField(FieldNames.contact);
	}

	/**
	 * Get 邮件地址
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	/**
	 * Set 邮件地址
	 */
	public void setEmail(String email) {
		this.email = email;
		addValidField(FieldNames.email);
	}

	/**
	 * Get 电话
	 */
	@Column(name = "TEL")
	public String getTel() {
		return tel;
	}

	/**
	 * Set 电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
		addValidField(FieldNames.tel);
	}

	/**
	 * Get 传真
	 */
	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	/**
	 * Set 传真
	 */
	public void setFax(String fax) {
		this.fax = fax;
		addValidField(FieldNames.fax);
	}

	/**
	 * Get 地址
	 */
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	/**
	 * Set 地址
	 */
	public void setAddress(String address) {
		this.address = address;
		addValidField(FieldNames.address);
	}

	/**
	 * Get 为内部办事处
	 */
	@Column(name = "IS_INTERNAL")
	public Integer getIsInternal() {
		return isInternal;
	}

	/**
	 * Set 为内部办事处
	 */
	public void setIsInternal(Integer isInternal) {
		this.isInternal = isInternal;
		addValidField(FieldNames.isInternal);
	}

	/**
	 * Get 作为客户
	 */
	@Column(name = "IS_CUSTOMER")
	public Integer getIsCustomer() {
		return isCustomer;
	}

	/**
	 * Set 作为客户
	 */
	public void setIsCustomer(Integer isCustomer) {
		this.isCustomer = isCustomer;
		addValidField(FieldNames.isCustomer);
	}

	/**
	 * Get 作为结算公司
	 */
	@Column(name = "IS_DEPT")
	public Integer getIsDept() {
		return isDept;
	}

	/**
	 * Set 作为结算公司
	 */
	public void setIsDept(Integer isDept) {
		this.isDept = isDept;
		addValidField(FieldNames.isDept);
	}

	/**
	 * Get 汇率体系
	 */
	@Column(name = "XCHGR_NAME")
	public String getXchgrName() {
		return xchgrName;
	}

	/**
	 * Set 汇率体系
	 */
	public void setXchgrName(String xchgrName) {
		this.xchgrName = xchgrName;
		addValidField(FieldNames.xchgrName);
	}

	/**
	 * Get 所属公司代码
	 */
	@Column(name = "CUSTOMER_CODE")
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * Set 所属公司代码
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
		addValidField(FieldNames.customerCode);
	}

	/**
	 * Get 语言
	 */
	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return language;
	}

	/**
	 * Set 语言
	 */
	public void setLanguage(String language) {
		this.language = language;
		addValidField(FieldNames.language);
	}

	/**
	 * Get 国家代码
	 */
	@Column(name = "COUNTRY_CODE")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Set 国家代码
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		addValidField(FieldNames.countryCode);
	}

	/**
	 * Get 省份代码
	 */
	@Column(name = "PROVINCE_CODE")
	public String getProvinceCode() {
		return provinceCode;
	}

	/**
	 * Set 省份代码
	 */
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
		addValidField(FieldNames.provinceCode);
	}

	/**
	 * Get 城市代码
	 */
	@Column(name = "CITY_CODE")
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * Set 城市代码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
		addValidField(FieldNames.cityCode);
	}

	/**
	 * Get 地点代码
	 */
	@Column(name = "SITE_CODE")
	public String getSiteCode() {
		return siteCode;
	}

	/**
	 * Set 地点代码
	 */
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
		addValidField(FieldNames.siteCode);
	}

	/**
	 * Get 备注
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * Set 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
		addValidField(FieldNames.remark);
	}

	/**
	 * Get 状态
	 * ：Active - 有效； Cancel - 作废
	 */
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	/**
	 * Set 状态
	 * ：Active - 有效； Cancel - 作废
	 */
	public void setStatus(String status) {
		this.status = status;
		addValidField(FieldNames.status);
	}

	/**
	 * Get 作废日期
	 */
	@Column(name = "CANCEL_DATE")
	public Date getCancelDate() {
		return cancelDate;
	}

	/**
	 * Set 作废日期
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
		addValidField(FieldNames.cancelDate);
	}

	/**
	 * Get 控制字
	 * ：默认0
	 */
	@Column(name = "CONTROL_WORD")
	public String getControlWord() {
		return controlWord;
	}

	/**
	 * Set 控制字
	 * ：默认0
	 */
	public void setControlWord(String controlWord) {
		this.controlWord = controlWord;
		addValidField(FieldNames.controlWord);
	}

	/**
	 * Get 并发访问控制
	 */
	@Column(name = "REC_VER")
	@Version
	public Long getRecVer() {
		return recVer;
	}

	/**
	 * Set 并发访问控制
	 */
	public void setRecVer(Long recVer) {
		this.recVer = recVer;
		addValidField(FieldNames.recVer);
	}

	/**
	 * Get 创建人
	 */
	@Column(name = "CREATOR")
	public String getCreator() {
		return creator;
	}

	/**
	 * Set 创建人
	 */
	public void setCreator(String creator) {
		this.creator = creator;
		addValidField(FieldNames.creator);
	}

	/**
	 * Get 创建时间
	 */
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Set 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		addValidField(FieldNames.createTime);
	}

	/**
	 * Get 修改人
	 */
	@Column(name = "MODIFIER")
	public String getModifier() {
		return modifier;
	}

	/**
	 * Set 修改人
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
		addValidField(FieldNames.modifier);
	}

	/**
	 * Get 修改时间
	 */
	@Column(name = "MODIFY_TIME")
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * Set 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
		addValidField(FieldNames.modifyTime);
	}

}
