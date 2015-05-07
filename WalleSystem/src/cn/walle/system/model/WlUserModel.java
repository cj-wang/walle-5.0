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
 * Model class for 员工
 */
@Entity
@Table(name = "WL_USER")
@DynamicInsert
@DynamicUpdate
public class WlUserModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlUser";

	public static final class FieldNames {
		/**
		 * 员工内部编码
		 */
		public static final String userId = "userId";
		/**
		 * 员工工号
		 */
		public static final String code = "code";
		/**
		 * 登录名
		 */
		public static final String loginName = "loginName";
		/**
		 * 员工姓名
		 */
		public static final String name = "name";
		/**
		 * 所属组织
		 */
		public static final String organizeId = "organizeId";
		/**
		 * 直接领导
		 */
		public static final String reportToUserId = "reportToUserId";
		/**
		 * 证件类型
		 */
		public static final String certifiTypeId = "certifiTypeId";
		/**
		 * 证件号码
		 */
		public static final String certifiCode = "certifiCode";
		/**
		 * 登录密码
		 */
		public static final String password = "password";
		/**
		 * 是否允许修改密码
		 */
		public static final String allowChangePassword = "allowChangePassword";
		/**
		 * 职务
		 */
		public static final String workTypeId = "workTypeId";
		/**
		 * 员工类型
		 */
		public static final String userTypeId = "userTypeId";
		/**
		 * 考核标志
		 */
		public static final String checkFlag = "checkFlag";
		/**
		 * 学历
		 */
		public static final String educationTypeId = "educationTypeId";
		/**
		 * 家庭电话
		 */
		public static final String homeTel = "homeTel";
		/**
		 * 办公电话
		 */
		public static final String officeTel = "officeTel";
		/**
		 * 移动电话
		 */
		public static final String mobileTele = "mobileTele";
		/**
		 * 员工住址
		 */
		public static final String addrId = "addrId";
		/**
		 * 电子邮件地址
		 */
		public static final String email = "email";
		/**
		 * 创建日期
		 */
		public static final String createDate = "createDate";
		/**
		 * 账号生效日期
		 */
		public static final String effectDate = "effectDate";
		/**
		 * 账号失效日期
		 */
		public static final String expireDate = "expireDate";
		/**
		 * 允许同时登录次数
		 */
		public static final String multiLoginFlag = "multiLoginFlag";
		/**
		 * 最后一次登录日志
		 */
		public static final String lastLoginLogId = "lastLoginLogId";
		/**
		 * 失败登录尝试次数
		 */
		public static final String tryTimes = "tryTimes";
		/**
		 * 账号锁定状态
		 */
		public static final String lockFlag = "lockFlag";
		/**
		 * 登录标志
		 */
		public static final String isLogin = "isLogin";
		/**
		 * 备注
		 */
		public static final String remarks = "remarks";
		/**
		 * 员工状态
		 */
		public static final String state = "state";
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
		 * QQ
		 */
		public static final String qq = "qq";
		/**
		 * 头像
		 */
		public static final String icon = "icon";
		/**
		 * 小头像
		 */
		public static final String iconSmall = "iconSmall";
		/**
		 * MSN
		 */
		public static final String msn = "msn";
		/**
		 * 在线状态
		 */
		public static final String onlineStatus = "onlineStatus";
		/**
		 * 所属单位
		 */
		public static final String unitsId = "unitsId";
		/**
		 * 是否发送信息
		 */
		public static final String issendmsg = "issendmsg";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//员工内部编码
	private String userId;
	//员工工号
	private String code;
	//登录名
	private String loginName;
	//员工姓名
	private String name;
	//所属组织
	private String organizeId;
	//直接领导
	private Integer reportToUserId;
	//证件类型
	private Integer certifiTypeId;
	//证件号码
	private String certifiCode;
	//登录密码
	private String password;
	//是否允许修改密码
	private String allowChangePassword;
	//职务
	private Integer workTypeId;
	//员工类型
	private Integer userTypeId;
	//考核标志
	private String checkFlag;
	//学历
	private Integer educationTypeId;
	//家庭电话
	private String homeTel;
	//办公电话
	private String officeTel;
	//移动电话
	private String mobileTele;
	//员工住址
	private String addrId;
	//电子邮件地址
	private String email;
	//创建日期
	private Date createDate;
	//账号生效日期
	private Date effectDate;
	//账号失效日期
	private Date expireDate;
	//允许同时登录次数
	private Integer multiLoginFlag;
	//最后一次登录日志
	private Long lastLoginLogId;
	//失败登录尝试次数
	private Long tryTimes;
	//账号锁定状态
	private String lockFlag;
	//登录标志
	private String isLogin;
	//备注
	private String remarks;
	//员工状态
	private String state;
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
	//QQ
	private String qq;
	//头像
	private String icon;
	//小头像
	private String iconSmall;
	//MSN
	private String msn;
	//在线状态
	private String onlineStatus;
	//所属单位
	private String unitsId;
	//是否发送信息
	private String issendmsg;
	//tenantId
	private String tenantId;

	/**
	 * Get 员工内部编码
	 */
	@Column(name = "USER_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getUserId() {
		return userId;
	}

	/**
	 * Set 员工内部编码
	 */
	public void setUserId(String userId) {
		this.userId = userId;
		addValidField(FieldNames.userId);
	}

	/**
	 * Get 员工工号
	 */
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	/**
	 * Set 员工工号
	 */
	public void setCode(String code) {
		this.code = code;
		addValidField(FieldNames.code);
	}

	/**
	 * Get 登录名
	 */
	@Column(name = "LOGIN_NAME")
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Set 登录名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
		addValidField(FieldNames.loginName);
	}

	/**
	 * Get 员工姓名
	 */
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	/**
	 * Set 员工姓名
	 */
	public void setName(String name) {
		this.name = name;
		addValidField(FieldNames.name);
	}

	/**
	 * Get 所属组织
	 * selectCode.org
	 */
	@Column(name = "ORGANIZE_ID")
	public String getOrganizeId() {
		return organizeId;
	}

	/**
	 * Set 所属组织
	 * selectCode.org
	 */
	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
		addValidField(FieldNames.organizeId);
	}

	/**
	 * Get 直接领导
	 */
	@Column(name = "REPORT_TO_USER_ID")
	public Integer getReportToUserId() {
		return reportToUserId;
	}

	/**
	 * Set 直接领导
	 */
	public void setReportToUserId(Integer reportToUserId) {
		this.reportToUserId = reportToUserId;
		addValidField(FieldNames.reportToUserId);
	}

	/**
	 * Get 证件类型
	 */
	@Column(name = "CERTIFI_TYPE_ID")
	public Integer getCertifiTypeId() {
		return certifiTypeId;
	}

	/**
	 * Set 证件类型
	 */
	public void setCertifiTypeId(Integer certifiTypeId) {
		this.certifiTypeId = certifiTypeId;
		addValidField(FieldNames.certifiTypeId);
	}

	/**
	 * Get 证件号码
	 */
	@Column(name = "CERTIFI_CODE")
	public String getCertifiCode() {
		return certifiCode;
	}

	/**
	 * Set 证件号码
	 */
	public void setCertifiCode(String certifiCode) {
		this.certifiCode = certifiCode;
		addValidField(FieldNames.certifiCode);
	}

	/**
	 * Get 登录密码
	 */
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	/**
	 * Set 登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
		addValidField(FieldNames.password);
	}

	/**
	 * Get 是否允许修改密码
	 */
	@Column(name = "ALLOW_CHANGE_PASSWORD")
	public String getAllowChangePassword() {
		return allowChangePassword;
	}

	/**
	 * Set 是否允许修改密码
	 */
	public void setAllowChangePassword(String allowChangePassword) {
		this.allowChangePassword = allowChangePassword;
		addValidField(FieldNames.allowChangePassword);
	}

	/**
	 * Get 职务
	 */
	@Column(name = "WORK_TYPE_ID")
	public Integer getWorkTypeId() {
		return workTypeId;
	}

	/**
	 * Set 职务
	 */
	public void setWorkTypeId(Integer workTypeId) {
		this.workTypeId = workTypeId;
		addValidField(FieldNames.workTypeId);
	}

	/**
	 * Get 员工类型
	 */
	@Column(name = "USER_TYPE_ID")
	public Integer getUserTypeId() {
		return userTypeId;
	}

	/**
	 * Set 员工类型
	 */
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
		addValidField(FieldNames.userTypeId);
	}

	/**
	 * Get 考核标志
	 */
	@Column(name = "CHECK_FLAG")
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * Set 考核标志
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
		addValidField(FieldNames.checkFlag);
	}

	/**
	 * Get 学历
	 */
	@Column(name = "EDUCATION_TYPE_ID")
	public Integer getEducationTypeId() {
		return educationTypeId;
	}

	/**
	 * Set 学历
	 */
	public void setEducationTypeId(Integer educationTypeId) {
		this.educationTypeId = educationTypeId;
		addValidField(FieldNames.educationTypeId);
	}

	/**
	 * Get 家庭电话
	 */
	@Column(name = "HOME_TEL")
	public String getHomeTel() {
		return homeTel;
	}

	/**
	 * Set 家庭电话
	 */
	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
		addValidField(FieldNames.homeTel);
	}

	/**
	 * Get 办公电话
	 */
	@Column(name = "OFFICE_TEL")
	public String getOfficeTel() {
		return officeTel;
	}

	/**
	 * Set 办公电话
	 */
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
		addValidField(FieldNames.officeTel);
	}

	/**
	 * Get 移动电话
	 */
	@Column(name = "MOBILE_TELE")
	public String getMobileTele() {
		return mobileTele;
	}

	/**
	 * Set 移动电话
	 */
	public void setMobileTele(String mobileTele) {
		this.mobileTele = mobileTele;
		addValidField(FieldNames.mobileTele);
	}

	/**
	 * Get 员工住址
	 */
	@Column(name = "ADDR_ID")
	public String getAddrId() {
		return addrId;
	}

	/**
	 * Set 员工住址
	 */
	public void setAddrId(String addrId) {
		this.addrId = addrId;
		addValidField(FieldNames.addrId);
	}

	/**
	 * Get 电子邮件地址
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	/**
	 * Set 电子邮件地址
	 */
	public void setEmail(String email) {
		this.email = email;
		addValidField(FieldNames.email);
	}

	/**
	 * Get 创建日期
	 */
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Set 创建日期
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
		addValidField(FieldNames.createDate);
	}

	/**
	 * Get 账号生效日期
	 */
	@Column(name = "EFFECT_DATE")
	public Date getEffectDate() {
		return effectDate;
	}

	/**
	 * Set 账号生效日期
	 */
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
		addValidField(FieldNames.effectDate);
	}

	/**
	 * Get 账号失效日期
	 */
	@Column(name = "EXPIRE_DATE")
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * Set 账号失效日期
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
		addValidField(FieldNames.expireDate);
	}

	/**
	 * Get 允许同时登录次数
	 */
	@Column(name = "MULTI_LOGIN_FLAG")
	public Integer getMultiLoginFlag() {
		return multiLoginFlag;
	}

	/**
	 * Set 允许同时登录次数
	 */
	public void setMultiLoginFlag(Integer multiLoginFlag) {
		this.multiLoginFlag = multiLoginFlag;
		addValidField(FieldNames.multiLoginFlag);
	}

	/**
	 * Get 最后一次登录日志
	 */
	@Column(name = "LAST_LOGIN_LOG_ID")
	public Long getLastLoginLogId() {
		return lastLoginLogId;
	}

	/**
	 * Set 最后一次登录日志
	 */
	public void setLastLoginLogId(Long lastLoginLogId) {
		this.lastLoginLogId = lastLoginLogId;
		addValidField(FieldNames.lastLoginLogId);
	}

	/**
	 * Get 失败登录尝试次数
	 */
	@Column(name = "TRY_TIMES")
	public Long getTryTimes() {
		return tryTimes;
	}

	/**
	 * Set 失败登录尝试次数
	 */
	public void setTryTimes(Long tryTimes) {
		this.tryTimes = tryTimes;
		addValidField(FieldNames.tryTimes);
	}

	/**
	 * Get 账号锁定状态
	 */
	@Column(name = "LOCK_FLAG")
	public String getLockFlag() {
		return lockFlag;
	}

	/**
	 * Set 账号锁定状态
	 */
	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
		addValidField(FieldNames.lockFlag);
	}

	/**
	 * Get 登录标志
	 */
	@Column(name = "IS_LOGIN")
	public String getIsLogin() {
		return isLogin;
	}

	/**
	 * Set 登录标志
	 */
	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
		addValidField(FieldNames.isLogin);
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
	 * Get 员工状态
	 * 使用用户 U
	 * 禁用用户 F
	 * 停用用户 S  selectCode.user_status
	 */
	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	/**
	 * Set 员工状态
	 * 使用用户 U
	 * 禁用用户 F
	 * 停用用户 S  selectCode.user_status
	 */
	public void setState(String state) {
		this.state = state;
		addValidField(FieldNames.state);
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
	 * Get QQ
	 */
	@Column(name = "QQ")
	public String getQq() {
		return qq;
	}

	/**
	 * Set QQ
	 */
	public void setQq(String qq) {
		this.qq = qq;
		addValidField(FieldNames.qq);
	}

	/**
	 * Get 头像
	 */
	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	/**
	 * Set 头像
	 */
	public void setIcon(String icon) {
		this.icon = icon;
		addValidField(FieldNames.icon);
	}

	/**
	 * Get 小头像
	 */
	@Column(name = "ICON_SMALL")
	public String getIconSmall() {
		return iconSmall;
	}

	/**
	 * Set 小头像
	 */
	public void setIconSmall(String iconSmall) {
		this.iconSmall = iconSmall;
		addValidField(FieldNames.iconSmall);
	}

	/**
	 * Get MSN
	 */
	@Column(name = "MSN")
	public String getMsn() {
		return msn;
	}

	/**
	 * Set MSN
	 */
	public void setMsn(String msn) {
		this.msn = msn;
		addValidField(FieldNames.msn);
	}

	/**
	 * Get 在线状态
	 * 默认是离线LX;ZX在线状态 ML忙碌状态 LK离开状态
	 */
	@Column(name = "ONLINE_STATUS")
	public String getOnlineStatus() {
		return onlineStatus;
	}

	/**
	 * Set 在线状态
	 * 默认是离线LX;ZX在线状态 ML忙碌状态 LK离开状态
	 */
	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
		addValidField(FieldNames.onlineStatus);
	}

	/**
	 * Get 所属单位
	 */
	@Column(name = "UNITS_ID")
	public String getUnitsId() {
		return unitsId;
	}

	/**
	 * Set 所属单位
	 */
	public void setUnitsId(String unitsId) {
		this.unitsId = unitsId;
		addValidField(FieldNames.unitsId);
	}

	/**
	 * Get 是否发送信息
	 * ：0否，1系统，2短信，3系统和短信
	 */
	@Column(name = "ISSENDMSG")
	public String getIssendmsg() {
		return issendmsg;
	}

	/**
	 * Set 是否发送信息
	 * ：0否，1系统，2短信，3系统和短信
	 */
	public void setIssendmsg(String issendmsg) {
		this.issendmsg = issendmsg;
		addValidField(FieldNames.issendmsg);
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
