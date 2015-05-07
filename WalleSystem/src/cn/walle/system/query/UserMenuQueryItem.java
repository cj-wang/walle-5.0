package cn.walle.system.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class UserMenuQueryItem extends BaseQueryItem {

	private String userId;
	private String funcId;
	private String funcCode;
	private String name;
	private String parentId;
	private Double funcLevel;
	private Double funSeq;
	private String viewname;
	private String funcImg;
	private String funcType;
	private String sys;
	private String remarks;

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
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

	@Column(name = "FUNC_IMG")
	public String getFuncImg() {
		return funcImg;
	}

	public void setFuncImg(String funcImg) {
		this.funcImg = funcImg;
		addValidField("funcImg");
	}

	@Column(name = "FUNC_TYPE")
	public String getFuncType() {
		return funcType;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
		addValidField("funcType");
	}

	@Column(name = "SYS")
	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
		addValidField("sys");
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
		addValidField("remarks");
	}

}
