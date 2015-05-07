package cn.walle.system.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class UserLoginQueryItem extends BaseQueryItem {

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
	private Long tryTimes;
	private String lockFlag;
	private String isLogin;
	private String remarks;
	private String state;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Integer recVer;
	private String qq;
	private String icon;
	private String iconSmall;
	private String msn;
	private String onlineStatus;
	private String unitsId;
	private String issendmsg;
	private String tenantId;

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		addValidField("code");
	}

	@Column(name = "login_name")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
		addValidField("loginName");
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		addValidField("name");
	}

	@Column(name = "organize_id")
	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
		addValidField("organizeId");
	}

	@Column(name = "report_to_user_id")
	public Integer getReportToUserId() {
		return reportToUserId;
	}

	public void setReportToUserId(Integer reportToUserId) {
		this.reportToUserId = reportToUserId;
		addValidField("reportToUserId");
	}

	@Column(name = "certifi_type_id")
	public Integer getCertifiTypeId() {
		return certifiTypeId;
	}

	public void setCertifiTypeId(Integer certifiTypeId) {
		this.certifiTypeId = certifiTypeId;
		addValidField("certifiTypeId");
	}

	@Column(name = "certifi_code")
	public String getCertifiCode() {
		return certifiCode;
	}

	public void setCertifiCode(String certifiCode) {
		this.certifiCode = certifiCode;
		addValidField("certifiCode");
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		addValidField("password");
	}

	@Column(name = "allow_change_password")
	public String getAllowChangePassword() {
		return allowChangePassword;
	}

	public void setAllowChangePassword(String allowChangePassword) {
		this.allowChangePassword = allowChangePassword;
		addValidField("allowChangePassword");
	}

	@Column(name = "work_type_id")
	public Integer getWorkTypeId() {
		return workTypeId;
	}

	public void setWorkTypeId(Integer workTypeId) {
		this.workTypeId = workTypeId;
		addValidField("workTypeId");
	}

	@Column(name = "user_type_id")
	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
		addValidField("userTypeId");
	}

	@Column(name = "check_flag")
	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
		addValidField("checkFlag");
	}

	@Column(name = "education_type_id")
	public Integer getEducationTypeId() {
		return educationTypeId;
	}

	public void setEducationTypeId(Integer educationTypeId) {
		this.educationTypeId = educationTypeId;
		addValidField("educationTypeId");
	}

	@Column(name = "home_tel")
	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
		addValidField("homeTel");
	}

	@Column(name = "office_tel")
	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
		addValidField("officeTel");
	}

	@Column(name = "mobile_tele")
	public String getMobileTele() {
		return mobileTele;
	}

	public void setMobileTele(String mobileTele) {
		this.mobileTele = mobileTele;
		addValidField("mobileTele");
	}

	@Column(name = "addr_id")
	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
		addValidField("addrId");
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		addValidField("email");
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		addValidField("createDate");
	}

	@Column(name = "effect_date")
	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
		addValidField("effectDate");
	}

	@Column(name = "expire_date")
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
		addValidField("expireDate");
	}

	@Column(name = "multi_login_flag")
	public Integer getMultiLoginFlag() {
		return multiLoginFlag;
	}

	public void setMultiLoginFlag(Integer multiLoginFlag) {
		this.multiLoginFlag = multiLoginFlag;
		addValidField("multiLoginFlag");
	}

	@Column(name = "last_login_log_id")
	public Integer getLastLoginLogId() {
		return lastLoginLogId;
	}

	public void setLastLoginLogId(Integer lastLoginLogId) {
		this.lastLoginLogId = lastLoginLogId;
		addValidField("lastLoginLogId");
	}

	@Column(name = "try_times")
	public Long getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(Long tryTimes) {
		this.tryTimes = tryTimes;
		addValidField("tryTimes");
	}

	@Column(name = "lock_flag")
	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
		addValidField("lockFlag");
	}

	@Column(name = "is_login")
	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
		addValidField("isLogin");
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
		addValidField("remarks");
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		addValidField("state");
	}

	@Column(name = "creator")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
		addValidField("creator");
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		addValidField("createTime");
	}

	@Column(name = "modifier")
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
		addValidField("modifier");
	}

	@Column(name = "modify_time")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
		addValidField("modifyTime");
	}

	@Column(name = "rec_ver")
	public Integer getRecVer() {
		return recVer;
	}

	public void setRecVer(Integer recVer) {
		this.recVer = recVer;
		addValidField("recVer");
	}

	@Column(name = "qq")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
		addValidField("qq");
	}

	@Column(name = "icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
		addValidField("icon");
	}

	@Column(name = "icon_small")
	public String getIconSmall() {
		return iconSmall;
	}

	public void setIconSmall(String iconSmall) {
		this.iconSmall = iconSmall;
		addValidField("iconSmall");
	}

	@Column(name = "msn")
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
		addValidField("msn");
	}

	@Column(name = "online_status")
	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
		addValidField("onlineStatus");
	}

	@Column(name = "units_id")
	public String getUnitsId() {
		return unitsId;
	}

	public void setUnitsId(String unitsId) {
		this.unitsId = unitsId;
		addValidField("unitsId");
	}

	@Column(name = "issendmsg")
	public String getIssendmsg() {
		return issendmsg;
	}

	public void setIssendmsg(String issendmsg) {
		this.issendmsg = issendmsg;
		addValidField("issendmsg");
	}

	@Column(name = "tenant_id")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
		addValidField("tenantId");
	}

}
