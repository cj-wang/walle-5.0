package cn.walle.system.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class FindOrgByNameOrManageQueryItem extends BaseQueryItem {

	private String organizeId;
	private String name;
	private Integer districtId;
	private Integer organizeTypeId;
	private String parentOrganizeId;
	private String manage;
	private String contact;
	private String contactTel;
	private String fax;
	private String email;
	private String leve;
	private String ex1;
	private String ex2;
	private String ex3;
	private String ex4;
	private String ex5;
	private String ex6;
	private String state;
	private String remarks;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Long recVer;

	@Column(name = "ORGANIZE_ID")
	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
		addValidField("organizeId");
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		addValidField("name");
	}

	@Column(name = "DISTRICT_ID")
	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
		addValidField("districtId");
	}

	@Column(name = "ORGANIZE_TYPE_ID")
	public Integer getOrganizeTypeId() {
		return organizeTypeId;
	}

	public void setOrganizeTypeId(Integer organizeTypeId) {
		this.organizeTypeId = organizeTypeId;
		addValidField("organizeTypeId");
	}

	@Column(name = "PARENT_ORGANIZE_ID")
	public String getParentOrganizeId() {
		return parentOrganizeId;
	}

	public void setParentOrganizeId(String parentOrganizeId) {
		this.parentOrganizeId = parentOrganizeId;
		addValidField("parentOrganizeId");
	}

	@Column(name = "MANAGE")
	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
		addValidField("manage");
	}

	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
		addValidField("contact");
	}

	@Column(name = "CONTACT_TEL")
	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
		addValidField("contactTel");
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
		addValidField("fax");
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		addValidField("email");
	}

	@Column(name = "LEVE")
	public String getLeve() {
		return leve;
	}

	public void setLeve(String leve) {
		this.leve = leve;
		addValidField("leve");
	}

	@Column(name = "EX1")
	public String getEx1() {
		return ex1;
	}

	public void setEx1(String ex1) {
		this.ex1 = ex1;
		addValidField("ex1");
	}

	@Column(name = "EX2")
	public String getEx2() {
		return ex2;
	}

	public void setEx2(String ex2) {
		this.ex2 = ex2;
		addValidField("ex2");
	}

	@Column(name = "EX3")
	public String getEx3() {
		return ex3;
	}

	public void setEx3(String ex3) {
		this.ex3 = ex3;
		addValidField("ex3");
	}

	@Column(name = "EX4")
	public String getEx4() {
		return ex4;
	}

	public void setEx4(String ex4) {
		this.ex4 = ex4;
		addValidField("ex4");
	}

	@Column(name = "EX5")
	public String getEx5() {
		return ex5;
	}

	public void setEx5(String ex5) {
		this.ex5 = ex5;
		addValidField("ex5");
	}

	@Column(name = "EX6")
	public String getEx6() {
		return ex6;
	}

	public void setEx6(String ex6) {
		this.ex6 = ex6;
		addValidField("ex6");
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
		addValidField("state");
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
		addValidField("remarks");
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
	public Long getRecVer() {
		return recVer;
	}

	public void setRecVer(Long recVer) {
		this.recVer = recVer;
		addValidField("recVer");
	}

}
