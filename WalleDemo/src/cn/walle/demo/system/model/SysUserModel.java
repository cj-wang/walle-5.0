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
 * Model class for 用户信息表
 */
@Entity
@Table(name = "SYS_USER")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SysUserModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "SysUser";

	public static final class FieldNames {
		/**
		 * userId
		 */
		public static final String userId = "userId";
		/**
		 * officeId
		 */
		public static final String officeId = "officeId";
		/**
		 * departmentId
		 */
		public static final String departmentId = "departmentId";
		/**
		 * principalCode
		 */
		public static final String principalCode = "principalCode";
		/**
		 * 用户编码
		 */
		public static final String userCode = "userCode";
		/**
		 * 用户名称
		 */
		public static final String userName = "userName";
		/**
		 * 职位描述
		 */
		public static final String position = "position";
		/**
		 * userNameCn
		 */
		public static final String userNameCn = "userNameCn";
		/**
		 * 名字
		 */
		public static final String firstName = "firstName";
		/**
		 * 中间名字
		 */
		public static final String middleName = "middleName";
		/**
		 * 姓
		 */
		public static final String lastName = "lastName";
		/**
		 * 用户类型
		 */
		public static final String userType = "userType";
		/**
		 * department
		 */
		public static final String department = "department";
		/**
		 * 公司（仓库）代码
		 */
		public static final String officeCode = "officeCode";
		/**
		 * 性别
		 */
		public static final String sex = "sex";
		/**
		 * superiorUserId
		 */
		public static final String superiorUserId = "superiorUserId";
		/**
		 * 个人签名
		 */
		public static final String personalInfo = "personalInfo";
		/**
		 * 个人头像
		 */
		public static final String personalPic = "personalPic";
		/**
		 * superiorUserCode
		 */
		public static final String superiorUserCode = "superiorUserCode";
		/**
		 * 身份证号
		 */
		public static final String idCard = "idCard";
		/**
		 * 婚姻状况
		 */
		public static final String marital = "marital";
		/**
		 * 联系地址
		 */
		public static final String contactAddress = "contactAddress";
		/**
		 * 本地名字
		 */
		public static final String nativeAddress = "nativeAddress";
		/**
		 * 联系地址
		 */
		public static final String homeAddress = "homeAddress";
		/**
		 * 教育程度
		 */
		public static final String education = "education";
		/**
		 * 联系电话
		 */
		public static final String telephone1 = "telephone1";
		/**
		 * 联系电话2
		 */
		public static final String telephone2 = "telephone2";
		/**
		 * 传真
		 */
		public static final String fax = "fax";
		/**
		 * 传呼
		 */
		public static final String bpcall = "bpcall";
		/**
		 * 移动电话
		 */
		public static final String mobile = "mobile";
		/**
		 * 邮编
		 */
		public static final String zipCode = "zipCode";
		/**
		 * 雇佣日期
		 */
		public static final String hireDate = "hireDate";
		/**
		 * 离职日期
		 */
		public static final String fireDate = "fireDate";
		/**
		 * 家庭电话
		 */
		public static final String homeTelphone = "homeTelphone";
		/**
		 * 生日
		 */
		public static final String birthDay = "birthDay";
		/**
		 * 电子邮件
		 */
		public static final String email = "email";
		/**
		 * createDate
		 */
		public static final String createDate = "createDate";
		/**
		 * canLogin
		 */
		public static final String canLogin = "canLogin";
		/**
		 * 密码
		 */
		public static final String password = "password";
		/**
		 * canLoginweb
		 */
		public static final String canLoginweb = "canLoginweb";
		/**
		 * canShowinweb
		 */
		public static final String canShowinweb = "canShowinweb";
		/**
		 * active
		 */
		public static final String active = "active";
		/**
		 * deleted
		 */
		public static final String deleted = "deleted";
		/**
		 * isCust
		 */
		public static final String isCust = "isCust";
		/**
		 * 所属公司代码
		 */
		public static final String customerCode = "customerCode";
		/**
		 * 缺省语言
		 */
		public static final String defaultLang = "defaultLang";
		/**
		 * companyCode
		 */
		public static final String companyCode = "companyCode";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
		/**
		 * sysConfigure
		 */
		public static final String sysConfigure = "sysConfigure";
		/**
		 * roleName
		 */
		public static final String roleName = "roleName";
		/**
		 * 内外部用户标识
		 */
		public static final String userRecongnition = "userRecongnition";
		/**
		 * 关联客户方
		 */
		public static final String cimCustId = "cimCustId";
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

	//userId
	private String userId;
	//officeId
	private String officeId;
	//departmentId
	private String departmentId;
	//principalCode
	private String principalCode;
	//用户编码
	private String userCode;
	//用户名称
	private String userName;
	//职位描述
	private String position;
	//userNameCn
	private String userNameCn;
	//名字
	private String firstName;
	//中间名字
	private String middleName;
	//姓
	private String lastName;
	//用户类型
	private String userType;
	//department
	private String department;
	//公司（仓库）代码
	private String officeCode;
	//性别
	private String sex;
	//superiorUserId
	private String superiorUserId;
	//个人签名
	private String personalInfo;
	//个人头像
	private String personalPic;
	//superiorUserCode
	private String superiorUserCode;
	//身份证号
	private String idCard;
	//婚姻状况
	private String marital;
	//联系地址
	private String contactAddress;
	//本地名字
	private String nativeAddress;
	//联系地址
	private String homeAddress;
	//教育程度
	private String education;
	//联系电话
	private String telephone1;
	//联系电话2
	private String telephone2;
	//传真
	private String fax;
	//传呼
	private String bpcall;
	//移动电话
	private String mobile;
	//邮编
	private String zipCode;
	//雇佣日期
	private Date hireDate;
	//离职日期
	private Date fireDate;
	//家庭电话
	private String homeTelphone;
	//生日
	private Date birthDay;
	//电子邮件
	private String email;
	//createDate
	private Date createDate;
	//canLogin
	private Integer canLogin;
	//密码
	private String password;
	//canLoginweb
	private Integer canLoginweb;
	//canShowinweb
	private Integer canShowinweb;
	//active
	private String active;
	//deleted
	private Integer deleted;
	//isCust
	private Integer isCust;
	//所属公司代码
	private String customerCode;
	//缺省语言
	private String defaultLang;
	//companyCode
	private String companyCode;
	//公司代码
	private String principalGroupCode;
	//sysConfigure
	private String sysConfigure;
	//roleName
	private String roleName;
	//内外部用户标识
	private Integer userRecongnition;
	//关联客户方
	private String cimCustId;
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
	 * Get userId
	 */
	@Column(name = "USER_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getUserId() {
		return userId;
	}

	/**
	 * Set userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
		addValidField(FieldNames.userId);
	}

	/**
	 * Get officeId
	 */
	@Column(name = "OFFICE_ID")
	public String getOfficeId() {
		return officeId;
	}

	/**
	 * Set officeId
	 */
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
		addValidField(FieldNames.officeId);
	}

	/**
	 * Get departmentId
	 */
	@Column(name = "DEPARTMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * Set departmentId
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
		addValidField(FieldNames.departmentId);
	}

	/**
	 * Get principalCode
	 */
	@Column(name = "PRINCIPAL_CODE")
	public String getPrincipalCode() {
		return principalCode;
	}

	/**
	 * Set principalCode
	 */
	public void setPrincipalCode(String principalCode) {
		this.principalCode = principalCode;
		addValidField(FieldNames.principalCode);
	}

	/**
	 * Get 用户编码
	 */
	@Column(name = "USER_CODE")
	public String getUserCode() {
		return userCode;
	}

	/**
	 * Set 用户编码
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
		addValidField(FieldNames.userCode);
	}

	/**
	 * Get 用户名称
	 */
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	/**
	 * Set 用户名称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
		addValidField(FieldNames.userName);
	}

	/**
	 * Get 职位描述
	 */
	@Column(name = "POSITION")
	public String getPosition() {
		return position;
	}

	/**
	 * Set 职位描述
	 */
	public void setPosition(String position) {
		this.position = position;
		addValidField(FieldNames.position);
	}

	/**
	 * Get userNameCn
	 */
	@Column(name = "USER_NAME_CN")
	public String getUserNameCn() {
		return userNameCn;
	}

	/**
	 * Set userNameCn
	 */
	public void setUserNameCn(String userNameCn) {
		this.userNameCn = userNameCn;
		addValidField(FieldNames.userNameCn);
	}

	/**
	 * Get 名字
	 */
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set 名字
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		addValidField(FieldNames.firstName);
	}

	/**
	 * Get 中间名字
	 */
	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Set 中间名字
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
		addValidField(FieldNames.middleName);
	}

	/**
	 * Get 姓
	 */
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set 姓
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
		addValidField(FieldNames.lastName);
	}

	/**
	 * Get 用户类型
	 */
	@Column(name = "USER_TYPE")
	public String getUserType() {
		return userType;
	}

	/**
	 * Set 用户类型
	 */
	public void setUserType(String userType) {
		this.userType = userType;
		addValidField(FieldNames.userType);
	}

	/**
	 * Get department
	 */
	@Column(name = "DEPARTMENT")
	public String getDepartment() {
		return department;
	}

	/**
	 * Set department
	 */
	public void setDepartment(String department) {
		this.department = department;
		addValidField(FieldNames.department);
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
	 * Get 性别
	 */
	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	/**
	 * Set 性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
		addValidField(FieldNames.sex);
	}

	/**
	 * Get superiorUserId
	 */
	@Column(name = "SUPERIOR_USER_ID")
	public String getSuperiorUserId() {
		return superiorUserId;
	}

	/**
	 * Set superiorUserId
	 */
	public void setSuperiorUserId(String superiorUserId) {
		this.superiorUserId = superiorUserId;
		addValidField(FieldNames.superiorUserId);
	}

	/**
	 * Get 个人签名
	 */
	@Column(name = "PERSONAL_INFO")
	public String getPersonalInfo() {
		return personalInfo;
	}

	/**
	 * Set 个人签名
	 */
	public void setPersonalInfo(String personalInfo) {
		this.personalInfo = personalInfo;
		addValidField(FieldNames.personalInfo);
	}

	/**
	 * Get 个人头像
	 */
	@Column(name = "PERSONAL_PIC")
	public String getPersonalPic() {
		return personalPic;
	}

	/**
	 * Set 个人头像
	 */
	public void setPersonalPic(String personalPic) {
		this.personalPic = personalPic;
		addValidField(FieldNames.personalPic);
	}

	/**
	 * Get superiorUserCode
	 */
	@Column(name = "SUPERIOR_USER_CODE")
	public String getSuperiorUserCode() {
		return superiorUserCode;
	}

	/**
	 * Set superiorUserCode
	 */
	public void setSuperiorUserCode(String superiorUserCode) {
		this.superiorUserCode = superiorUserCode;
		addValidField(FieldNames.superiorUserCode);
	}

	/**
	 * Get 身份证号
	 */
	@Column(name = "ID_CARD")
	public String getIdCard() {
		return idCard;
	}

	/**
	 * Set 身份证号
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
		addValidField(FieldNames.idCard);
	}

	/**
	 * Get 婚姻状况
	 */
	@Column(name = "MARITAL")
	public String getMarital() {
		return marital;
	}

	/**
	 * Set 婚姻状况
	 */
	public void setMarital(String marital) {
		this.marital = marital;
		addValidField(FieldNames.marital);
	}

	/**
	 * Get 联系地址
	 */
	@Column(name = "CONTACT_ADDRESS")
	public String getContactAddress() {
		return contactAddress;
	}

	/**
	 * Set 联系地址
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
		addValidField(FieldNames.contactAddress);
	}

	/**
	 * Get 本地名字
	 */
	@Column(name = "NATIVE_ADDRESS")
	public String getNativeAddress() {
		return nativeAddress;
	}

	/**
	 * Set 本地名字
	 */
	public void setNativeAddress(String nativeAddress) {
		this.nativeAddress = nativeAddress;
		addValidField(FieldNames.nativeAddress);
	}

	/**
	 * Get 联系地址
	 */
	@Column(name = "HOME_ADDRESS")
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * Set 联系地址
	 */
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
		addValidField(FieldNames.homeAddress);
	}

	/**
	 * Get 教育程度
	 */
	@Column(name = "EDUCATION")
	public String getEducation() {
		return education;
	}

	/**
	 * Set 教育程度
	 */
	public void setEducation(String education) {
		this.education = education;
		addValidField(FieldNames.education);
	}

	/**
	 * Get 联系电话
	 */
	@Column(name = "TELEPHONE1")
	public String getTelephone1() {
		return telephone1;
	}

	/**
	 * Set 联系电话
	 */
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
		addValidField(FieldNames.telephone1);
	}

	/**
	 * Get 联系电话2
	 */
	@Column(name = "TELEPHONE2")
	public String getTelephone2() {
		return telephone2;
	}

	/**
	 * Set 联系电话2
	 */
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
		addValidField(FieldNames.telephone2);
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
	 * Get 传呼
	 */
	@Column(name = "BPCALL")
	public String getBpcall() {
		return bpcall;
	}

	/**
	 * Set 传呼
	 */
	public void setBpcall(String bpcall) {
		this.bpcall = bpcall;
		addValidField(FieldNames.bpcall);
	}

	/**
	 * Get 移动电话
	 */
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	/**
	 * Set 移动电话
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
		addValidField(FieldNames.mobile);
	}

	/**
	 * Get 邮编
	 */
	@Column(name = "ZIP_CODE")
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Set 邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
		addValidField(FieldNames.zipCode);
	}

	/**
	 * Get 雇佣日期
	 */
	@Column(name = "HIRE_DATE")
	public Date getHireDate() {
		return hireDate;
	}

	/**
	 * Set 雇佣日期
	 */
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
		addValidField(FieldNames.hireDate);
	}

	/**
	 * Get 离职日期
	 */
	@Column(name = "FIRE_DATE")
	public Date getFireDate() {
		return fireDate;
	}

	/**
	 * Set 离职日期
	 */
	public void setFireDate(Date fireDate) {
		this.fireDate = fireDate;
		addValidField(FieldNames.fireDate);
	}

	/**
	 * Get 家庭电话
	 */
	@Column(name = "HOME_TELPHONE")
	public String getHomeTelphone() {
		return homeTelphone;
	}

	/**
	 * Set 家庭电话
	 */
	public void setHomeTelphone(String homeTelphone) {
		this.homeTelphone = homeTelphone;
		addValidField(FieldNames.homeTelphone);
	}

	/**
	 * Get 生日
	 */
	@Column(name = "BIRTH_DAY")
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * Set 生日
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
		addValidField(FieldNames.birthDay);
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
	 * Get createDate
	 */
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Set createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		addValidField(FieldNames.createDate);
	}

	/**
	 * Get canLogin
	 */
	@Column(name = "CAN_LOGIN")
	public Integer getCanLogin() {
		return canLogin;
	}

	/**
	 * Set canLogin
	 */
	public void setCanLogin(Integer canLogin) {
		this.canLogin = canLogin;
		addValidField(FieldNames.canLogin);
	}

	/**
	 * Get 密码
	 */
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	/**
	 * Set 密码
	 */
	public void setPassword(String password) {
		this.password = password;
		addValidField(FieldNames.password);
	}

	/**
	 * Get canLoginweb
	 */
	@Column(name = "CAN_LOGINWEB")
	public Integer getCanLoginweb() {
		return canLoginweb;
	}

	/**
	 * Set canLoginweb
	 */
	public void setCanLoginweb(Integer canLoginweb) {
		this.canLoginweb = canLoginweb;
		addValidField(FieldNames.canLoginweb);
	}

	/**
	 * Get canShowinweb
	 */
	@Column(name = "CAN_SHOWINWEB")
	public Integer getCanShowinweb() {
		return canShowinweb;
	}

	/**
	 * Set canShowinweb
	 */
	public void setCanShowinweb(Integer canShowinweb) {
		this.canShowinweb = canShowinweb;
		addValidField(FieldNames.canShowinweb);
	}

	/**
	 * Get active
	 */
	@Column(name = "ACTIVE")
	public String getActive() {
		return active;
	}

	/**
	 * Set active
	 */
	public void setActive(String active) {
		this.active = active;
		addValidField(FieldNames.active);
	}

	/**
	 * Get deleted
	 */
	@Column(name = "DELETED")
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * Set deleted
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
		addValidField(FieldNames.deleted);
	}

	/**
	 * Get isCust
	 */
	@Column(name = "IS_CUST")
	public Integer getIsCust() {
		return isCust;
	}

	/**
	 * Set isCust
	 */
	public void setIsCust(Integer isCust) {
		this.isCust = isCust;
		addValidField(FieldNames.isCust);
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
	 * Get 缺省语言
	 */
	@Column(name = "DEFAULT_LANG")
	public String getDefaultLang() {
		return defaultLang;
	}

	/**
	 * Set 缺省语言
	 */
	public void setDefaultLang(String defaultLang) {
		this.defaultLang = defaultLang;
		addValidField(FieldNames.defaultLang);
	}

	/**
	 * Get companyCode
	 */
	@Column(name = "COMPANY_CODE")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Set companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
		addValidField(FieldNames.companyCode);
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
	 * Get sysConfigure
	 */
	@Column(name = "SYS_CONFIGURE")
	public String getSysConfigure() {
		return sysConfigure;
	}

	/**
	 * Set sysConfigure
	 */
	public void setSysConfigure(String sysConfigure) {
		this.sysConfigure = sysConfigure;
		addValidField(FieldNames.sysConfigure);
	}

	/**
	 * Get roleName
	 */
	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Set roleName
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
		addValidField(FieldNames.roleName);
	}

	/**
	 * Get 内外部用户标识
	 * ，1为内部用户，0为外部用户
	 */
	@Column(name = "USER_RECONGNITION")
	public Integer getUserRecongnition() {
		return userRecongnition;
	}

	/**
	 * Set 内外部用户标识
	 * ，1为内部用户，0为外部用户
	 */
	public void setUserRecongnition(Integer userRecongnition) {
		this.userRecongnition = userRecongnition;
		addValidField(FieldNames.userRecongnition);
	}

	/**
	 * Get 关联客户方
	 */
	@Column(name = "CIM_CUST_ID")
	public String getCimCustId() {
		return cimCustId;
	}

	/**
	 * Set 关联客户方
	 */
	public void setCimCustId(String cimCustId) {
		this.cimCustId = cimCustId;
		addValidField(FieldNames.cimCustId);
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
