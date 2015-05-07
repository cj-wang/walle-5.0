package cn.walle.system.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class RoleGrantedUserQueryItem extends BaseQueryItem {

	private String roleUserId;
	private String userId;
	private String code;
	private String loginName;
	private String name;
	private String organizeId;
	private Integer reportToUserId;
	private Integer certifiTypeId;
	private String certifiCode;
	private String password;
	private String allowChangePassword;
	private Integer workTypeId;
	private Integer userTypeId;
	private String checkFlag;
	private Integer educationTypeId;
	private String homeTel;
	private String officeTel;
	private String mobileTele;
	private String addrId;
	private String email;
	private Date createDate;
	private Date effectDate;
	private Date expireDate;
	private Integer multiLoginFlag;
	private Integer lastLoginLogId;
	private Double tryTimes;
	private String lockFlag;
	private String isLogin;
	private String remarks;
	private String state;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Integer recVer;

	@Column(name = "ROLE_USER_ID")
	public String getRoleUserId() {
		return roleUserId;
	}

	public void setRoleUserId(String roleUserId) {
		this.roleUserId = roleUserId;
		addValidField("roleUserId");
	}

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		addValidField("code");
	}

	@Column(name = "LOGIN_NAME")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
		addValidField("loginName");
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		addValidField("name");
	}

	@Column(name = "ORGANIZE_ID")
	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
		addValidField("organizeId");
	}

	@Column(name = "REPORT_TO_USER_ID")
	public Integer getReportToUserId() {
		return reportToUserId;
	}

	public void setReportToUserId(Integer reportToUserId) {
		this.reportToUserId = reportToUserId;
		addValidField("reportToUserId");
	}

	@Column(name = "CERTIFI_TYPE_ID")
	public Integer getCertifiTypeId() {
		return certifiTypeId;
	}

	public void setCertifiTypeId(Integer certifiTypeId) {
		this.certifiTypeId = certifiTypeId;
		addValidField("certifiTypeId");
	}

	@Column(name = "CERTIFI_CODE")
	public String getCertifiCode() {
		return certifiCode;
	}

	public void setCertifiCode(String certifiCode) {
		this.certifiCode = certifiCode;
		addValidField("certifiCode");
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		addValidField("password");
	}

	@Column(name = "ALLOW_CHANGE_PASSWORD")
	public String getAllowChangePassword() {
		return allowChangePassword;
	}

	public void setAllowChangePassword(String allowChangePassword) {
		this.allowChangePassword = allowChangePassword;
		addValidField("allowChangePassword");
	}

	@Column(name = "WORK_TYPE_ID")
	public Integer getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Integer workTypeId) {
		this.workTypeId = workTypeId;
		addValidField("workTypeId");
	}

	@Column(name = "USER_TYPE_ID")
	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
		addValidField("userTypeId");
	}

	@Column(name = "CHECK_FLAG")
	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
		addValidField("checkFlag");
	}

	@Column(name = "EDUCATION_TYPE_ID")
	public Integer getEducationTypeId() {
		return educationTypeId;
	}

	public void setEducationTypeId(Integer educationTypeId) {
		this.educationTypeId = educationTypeId;
		addValidField("educationTypeId");
	}

	@Column(name = "HOME_TEL")
	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
		addValidField("homeTel");
	}

	@Column(name = "OFFICE_TEL")
	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
		addValidField("officeTel");
	}

	@Column(name = "MOBILE_TELE")
	public String getMobileTele() {
		return mobileTele;
	}

	public void setMobileTele(String mobileTele) {
		this.mobileTele = mobileTele;
		addValidField("mobileTele");
	}

	@Column(name = "ADDR_ID")
	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
		addValidField("addrId");
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		addValidField("email");
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		addValidField("createDate");
	}

	@Column(name = "EFFECT_DATE")
	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
		addValidField("effectDate");
	}

	@Column(name = "EXPIRE_DATE")
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
		addValidField("expireDate");
	}

	@Column(name = "MULTI_LOGIN_FLAG")
	public Integer getMultiLoginFlag() {
		return multiLoginFlag;
	}

	public void setMultiLoginFlag(Integer multiLoginFlag) {
		this.multiLoginFlag = multiLoginFlag;
		addValidField("multiLoginFlag");
	}

	@Column(name = "LAST_LOGIN_LOG_ID")
	public Integer getLastLoginLogId() {
		return lastLoginLogId;
	}

	public void setLastLoginLogId(Integer lastLoginLogId) {
		this.lastLoginLogId = lastLoginLogId;
		addValidField("lastLoginLogId");
	}

	@Column(name = "TRY_TIMES")
	public Double getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(Double tryTimes) {
		this.tryTimes = tryTimes;
		addValidField("tryTimes");
	}

	@Column(name = "LOCK_FLAG")
	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
		addValidField("lockFlag");
	}

	@Column(name = "IS_LOGIN")
	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
		addValidField("isLogin");
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
		addValidField("remarks");
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		addValidField("state");
	}

	@Column(name = "CREATOR")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
		addValidField("creator");
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		addValidField("createTime");
	}

	@Column(name = "MODIFIER")
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
		addValidField("modifier");
	}

	@Column(name = "MODIFY_TIME")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
		addValidField("modifyTime");
	}

	@Column(name = "REC_VER")
	public Integer getRecVer() {
		return recVer;
	}

	public void setRecVer(Integer recVer) {
		this.recVer = recVer;
		addValidField("recVer");
	}

}
