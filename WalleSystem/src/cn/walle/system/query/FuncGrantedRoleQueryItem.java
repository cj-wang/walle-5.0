package cn.walle.system.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class FuncGrantedRoleQueryItem extends BaseQueryItem {

	private String roleFuncId;
	private String funcId;
	private String funcCode;
	private String name;
	private String parentId;
	private Double funcLevel;
	private Double funSeq;
	private String viewname;
	private String dllPath;
	private String funcImg;
	private String funcArg;
	private String funcType;
	private String disabled;
	private String state;
	private String remarks;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Integer recVer;

	@Column(name = "ROLE_FUNC_ID")
	public String getRoleFuncId() {
		return roleFuncId;
	}

	public void setRoleFuncId(String roleFuncId) {
		this.roleFuncId = roleFuncId;
		addValidField("roleFuncId");
	}

	@Column(name = "FUNC_ID")
	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
		addValidField("funcId");
	}

	@Column(name = "FUNC_CODE")
	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
		addValidField("funcCode");
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		addValidField("name");
	}

	@Column(name = "PARENT_ID")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
		addValidField("parentId");
	}

	@Column(name = "FUNC_LEVEL")
	public Double getFuncLevel() {
		return funcLevel;
	}

	public void setFuncLevel(Double funcLevel) {
		this.funcLevel = funcLevel;
		addValidField("funcLevel");
	}

	@Column(name = "FUN_SEQ")
	public Double getFunSeq() {
		return funSeq;
	}

	public void setFunSeq(Double funSeq) {
		this.funSeq = funSeq;
		addValidField("funSeq");
	}

	@Column(name = "VIEWNAME")
	public String getViewname() {
		return viewname;
	}

	public void setViewname(String viewname) {
		this.viewname = viewname;
		addValidField("viewname");
	}

	@Column(name = "DLL_PATH")
	public String getDllPath() {
		return dllPath;
	}

	public void setDllPath(String dllPath) {
		this.dllPath = dllPath;
		addValidField("dllPath");
	}

	@Column(name = "FUNC_IMG")
	public String getFuncImg() {
		return funcImg;
	}

	public void setFuncImg(String funcImg) {
		this.funcImg = funcImg;
		addValidField("funcImg");
	}

	@Column(name = "FUNC_ARG")
	public String getFuncArg() {
		return funcArg;
	}

	public void setFuncArg(String funcArg) {
		this.funcArg = funcArg;
		addValidField("funcArg");
	}

	@Column(name = "FUNC_TYPE")
	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
		addValidField("funcType");
	}

	@Column(name = "DISABLED")
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
		addValidField("disabled");
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
	public Integer getRecVer() {
		return recVer;
	}

	public void setRecVer(Integer recVer) {
		this.recVer = recVer;
		addValidField("recVer");
	}

}
