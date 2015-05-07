package cn.walle.system.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class SysCodeQueryItem extends BaseQueryItem {

	private String codeId;
	private String codeTypeId;
	private String codeName;
	private String codeValue;
	private String icon;
	private String parentCodeValue;
	private String codeDesc;
	private String codeGroup;
	private Long sort;
	private String state;
	private String remarks;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Long recVer;
	private String system;

	@Column(name = "CODE_ID")
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
		addValidField("codeId");
	}

	@Column(name = "CODE_TYPE_ID")
	public String getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
		addValidField("codeTypeId");
	}

	@Column(name = "CODE_NAME")
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
		addValidField("codeName");
	}

	@Column(name = "CODE_VALUE")
	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
		addValidField("codeValue");
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
		addValidField("icon");
	}

	@Column(name = "PARENT_CODE_VALUE")
	public String getParentCodeValue() {
		return parentCodeValue;
	}

	public void setParentCodeValue(String parentCodeValue) {
		this.parentCodeValue = parentCodeValue;
		addValidField("parentCodeValue");
	}

	@Column(name = "CODE_DESC")
	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
		addValidField("codeDesc");
	}

	@Column(name = "CODE_GROUP")
	public String getCodeGroup() {
		return codeGroup;
	}

	public void setCodeGroup(String codeGroup) {
		this.codeGroup = codeGroup;
		addValidField("codeGroup");
	}

	@Column(name = "SORT")
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
		addValidField("sort");
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

	@Column(name = "SYSTEM")
	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
		addValidField("system");
	}

}
