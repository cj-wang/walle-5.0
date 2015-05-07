package cn.walle.system.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class UserInfoByOrgInfoQueryItem extends BaseQueryItem {

	private String organizeId;
	private String orgname;
	private String manage;
	private String loginName;
	private String username;
	private Integer reportToUserId;
	private String homeTel;
	private String officeTel;
	private String mobileTele;
	private String email;
	private String addrId;
	private String state;

	@Column(name = "ORGANIZE_ID")
	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
		addValidField("organizeId");
	}

	@Column(name = "ORGNAME")
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
		addValidField("orgname");
	}

	@Column(name = "MANAGE")
	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
		addValidField("manage");
	}

	@Column(name = "LOGIN_NAME")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
		addValidField("loginName");
	}

	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		addValidField("username");
	}

	@Column(name = "REPORT_TO_USER_ID")
	public Integer getReportToUserId() {
		return reportToUserId;
	}

	public void setReportToUserId(Integer reportToUserId) {
		this.reportToUserId = reportToUserId;
		addValidField("reportToUserId");
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

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		addValidField("email");
	}

	@Column(name = "ADDR_ID")
	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
		addValidField("addrId");
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		addValidField("state");
	}

}
