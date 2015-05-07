package cn.walle.demo.basicdata.model;

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
 * Model class for 客户表
 */
@Entity
@Table(name = "BAS_CUSTOMER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BasCustomerModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "BasCustomer";

	public static final class FieldNames {
		/**
		 * basCustomerUuid
		 */
		public static final String basCustomerUuid = "basCustomerUuid";
		/**
		 * 客户代码
		 */
		public static final String customerCode = "customerCode";
		/**
		 * 中文名称
		 */
		public static final String customerName = "customerName";
		/**
		 * 客户名称
		 */
		public static final String customerNameEn = "customerNameEn";
		/**
		 * 地址1
		 */
		public static final String address1 = "address1";
		/**
		 * 地址1
		 */
		public static final String address2 = "address2";
		/**
		 * 地址3
		 */
		public static final String address4 = "address4";
		/**
		 * 地址2
		 */
		public static final String address3 = "address3";
		/**
		 * 邮政编码
		 */
		public static final String postalCode = "postalCode";
		/**
		 * 电话号码1
		 */
		public static final String telNo1 = "telNo1";
		/**
		 * 电话号码2
		 */
		public static final String telNo2 = "telNo2";
		/**
		 * 传真1
		 */
		public static final String faxNo1 = "faxNo1";
		/**
		 * 传真2
		 */
		public static final String faxNo2 = "faxNo2";
		/**
		 * 电子邮件
		 */
		public static final String email = "email";
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
		 * 状态
		 */
		public static final String status = "status";
		/**
		 * 中心代码
		 */
		public static final String centerCode = "centerCode";
		/**
		 * 控制字
		 */
		public static final String controlWord = "controlWord";
		/**
		 * remark
		 */
		public static final String remark = "remark";
		/**
		 * 所属船公司（舱单默认收货人）
		 */
		public static final String aux1 = "aux1";
		/**
		 * 船东公司（报关单登记）
		 */
		public static final String aux2 = "aux2";
		/**
		 * 自定义字段3
		 */
		public static final String aux3 = "aux3";
		/**
		 * 自定义字段4
		 */
		public static final String aux4 = "aux4";
		/**
		 * 自定义字段5
		 */
		public static final String aux5 = "aux5";
		/**
		 * 公司（仓库）代码
		 */
		public static final String officeCode = "officeCode";
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
		/**
		 * 01表示办事处
		 */
		public static final String customerType = "customerType";
	}

	//basCustomerUuid
	private String basCustomerUuid;
	//客户代码
	private String customerCode;
	//中文名称
	private String customerName;
	//客户名称
	private String customerNameEn;
	//地址1
	private String address1;
	//地址1
	private String address2;
	//地址3
	private String address4;
	//地址2
	private String address3;
	//邮政编码
	private Long postalCode;
	//电话号码1
	private String telNo1;
	//电话号码2
	private String telNo2;
	//传真1
	private String faxNo1;
	//传真2
	private String faxNo2;
	//电子邮件
	private String email;
	//国家代码
	private String countryCode;
	//省份代码
	private String provinceCode;
	//城市代码
	private String cityCode;
	//地点代码
	private String siteCode;
	//状态
	private String status;
	//中心代码
	private String centerCode;
	//控制字
	private String controlWord;
	//remark
	private String remark;
	//所属船公司（舱单默认收货人）
	private String aux1;
	//船东公司（报关单登记）
	private String aux2;
	//自定义字段3
	private String aux3;
	//自定义字段4
	private String aux4;
	//自定义字段5
	private String aux5;
	//公司（仓库）代码
	private String officeCode;
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
	//01表示办事处
	private String customerType;

	/**
	 * Get basCustomerUuid
	 */
	@Column(name = "BAS_CUSTOMER_UUID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getBasCustomerUuid() {
		return basCustomerUuid;
	}

	/**
	 * Set basCustomerUuid
	 */
	public void setBasCustomerUuid(String basCustomerUuid) {
		this.basCustomerUuid = basCustomerUuid;
		addValidField(FieldNames.basCustomerUuid);
	}

	/**
	 * Get 客户代码
	 */
	@Column(name = "CUSTOMER_CODE")
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * Set 客户代码
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
		addValidField(FieldNames.customerCode);
	}

	/**
	 * Get 中文名称
	 */
	@Column(name = "CUSTOMER_NAME")
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Set 中文名称
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
		addValidField(FieldNames.customerName);
	}

	/**
	 * Get 客户名称
	 */
	@Column(name = "CUSTOMER_NAME_EN")
	public String getCustomerNameEn() {
		return customerNameEn;
	}

	/**
	 * Set 客户名称
	 */
	public void setCustomerNameEn(String customerNameEn) {
		this.customerNameEn = customerNameEn;
		addValidField(FieldNames.customerNameEn);
	}

	/**
	 * Get 地址1
	 */
	@Column(name = "ADDRESS1")
	public String getAddress1() {
		return address1;
	}

	/**
	 * Set 地址1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
		addValidField(FieldNames.address1);
	}

	/**
	 * Get 地址1
	 */
	@Column(name = "ADDRESS2")
	public String getAddress2() {
		return address2;
	}

	/**
	 * Set 地址1
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
		addValidField(FieldNames.address2);
	}

	/**
	 * Get 地址3
	 */
	@Column(name = "ADDRESS4")
	public String getAddress4() {
		return address4;
	}

	/**
	 * Set 地址3
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
		addValidField(FieldNames.address4);
	}

	/**
	 * Get 地址2
	 */
	@Column(name = "ADDRESS3")
	public String getAddress3() {
		return address3;
	}

	/**
	 * Set 地址2
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
		addValidField(FieldNames.address3);
	}

	/**
	 * Get 邮政编码
	 */
	@Column(name = "POSTAL_CODE")
	public Long getPostalCode() {
		return postalCode;
	}

	/**
	 * Set 邮政编码
	 */
	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
		addValidField(FieldNames.postalCode);
	}

	/**
	 * Get 电话号码1
	 */
	@Column(name = "TEL_NO_1")
	public String getTelNo1() {
		return telNo1;
	}

	/**
	 * Set 电话号码1
	 */
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
		addValidField(FieldNames.telNo1);
	}

	/**
	 * Get 电话号码2
	 */
	@Column(name = "TEL_NO_2")
	public String getTelNo2() {
		return telNo2;
	}

	/**
	 * Set 电话号码2
	 */
	public void setTelNo2(String telNo2) {
		this.telNo2 = telNo2;
		addValidField(FieldNames.telNo2);
	}

	/**
	 * Get 传真1
	 */
	@Column(name = "FAX_NO_1")
	public String getFaxNo1() {
		return faxNo1;
	}

	/**
	 * Set 传真1
	 */
	public void setFaxNo1(String faxNo1) {
		this.faxNo1 = faxNo1;
		addValidField(FieldNames.faxNo1);
	}

	/**
	 * Get 传真2
	 */
	@Column(name = "FAX_NO_2")
	public String getFaxNo2() {
		return faxNo2;
	}

	/**
	 * Set 传真2
	 */
	public void setFaxNo2(String faxNo2) {
		this.faxNo2 = faxNo2;
		addValidField(FieldNames.faxNo2);
	}

	/**
	 * Get 电子邮件
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	/**
	 * Set 电子邮件
	 */
	public void setEmail(String email) {
		this.email = email;
		addValidField(FieldNames.email);
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
	 * Get 中心代码
	 */
	@Column(name = "CENTER_CODE")
	public String getCenterCode() {
		return centerCode;
	}

	/**
	 * Set 中心代码
	 */
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
		addValidField(FieldNames.centerCode);
	}

	/**
	 * Get 控制字
	 * 字：默认20个0
	 */
	@Column(name = "CONTROL_WORD")
	public String getControlWord() {
		return controlWord;
	}

	/**
	 * Set 控制字
	 * 字：默认20个0
	 */
	public void setControlWord(String controlWord) {
		this.controlWord = controlWord;
		addValidField(FieldNames.controlWord);
	}

	/**
	 * Get remark
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * Set remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
		addValidField(FieldNames.remark);
	}

	/**
	 * Get 所属船公司（舱单默认收货人）
	 */
	@Column(name = "AUX1")
	public String getAux1() {
		return aux1;
	}

	/**
	 * Set 所属船公司（舱单默认收货人）
	 */
	public void setAux1(String aux1) {
		this.aux1 = aux1;
		addValidField(FieldNames.aux1);
	}

	/**
	 * Get 船东公司（报关单登记）
	 */
	@Column(name = "AUX2")
	public String getAux2() {
		return aux2;
	}

	/**
	 * Set 船东公司（报关单登记）
	 */
	public void setAux2(String aux2) {
		this.aux2 = aux2;
		addValidField(FieldNames.aux2);
	}

	/**
	 * Get 自定义字段3
	 */
	@Column(name = "AUX3")
	public String getAux3() {
		return aux3;
	}

	/**
	 * Set 自定义字段3
	 */
	public void setAux3(String aux3) {
		this.aux3 = aux3;
		addValidField(FieldNames.aux3);
	}

	/**
	 * Get 自定义字段4
	 */
	@Column(name = "AUX4")
	public String getAux4() {
		return aux4;
	}

	/**
	 * Set 自定义字段4
	 */
	public void setAux4(String aux4) {
		this.aux4 = aux4;
		addValidField(FieldNames.aux4);
	}

	/**
	 * Get 自定义字段5
	 */
	@Column(name = "AUX5")
	public String getAux5() {
		return aux5;
	}

	/**
	 * Set 自定义字段5
	 */
	public void setAux5(String aux5) {
		this.aux5 = aux5;
		addValidField(FieldNames.aux5);
	}

	/**
	 * Get 公司（仓库）代码
	 */
	@Column(name = "OFFICE_CODE")
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * Set 公司（仓库）代码
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
		addValidField(FieldNames.officeCode);
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

	/**
	 * Get 01表示办事处
	 * ，02表示国际中转代理，03表示机械作业单位，04表示船公司，05表示一程船公司，06表示控箱公司，07表示货代，08表示付货人，09表示收货人，10表示工厂，11表示贸易公司，12表示代理，13表示车队，14表示其他
	 */
	@Column(name = "CUSTOMER_TYPE")
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Set 01表示办事处
	 * ，02表示国际中转代理，03表示机械作业单位，04表示船公司，05表示一程船公司，06表示控箱公司，07表示货代，08表示付货人，09表示收货人，10表示工厂，11表示贸易公司，12表示代理，13表示车队，14表示其他
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
		addValidField(FieldNames.customerType);
	}

}
