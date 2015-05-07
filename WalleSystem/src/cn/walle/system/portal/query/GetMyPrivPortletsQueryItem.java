package cn.walle.system.portal.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class GetMyPrivPortletsQueryItem extends BaseQueryItem {

	private String portletId;
	private String portletName;
	private String title;
	private String description;
	private String screenshot;
	private String type;
	private String src;
	private String parameters;
	private String width;
	private String height;
	private Integer seq;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Long recVer;
	private String defaultDisplay;
	private String status;
	private String thirdpartyPortlet;
	private Integer columnIndex;
	private String icon;

	@Column(name = "PORTLET_ID")
	public String getPortletId() {
		return portletId;
	}

	public void setPortletId(String portletId) {
		this.portletId = portletId;
		addValidField("portletId");
	}

	@Column(name = "PORTLET_NAME")
	public String getPortletName() {
		return portletName;
	}

	public void setPortletName(String portletName) {
		this.portletName = portletName;
		addValidField("portletName");
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		addValidField("title");
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		addValidField("description");
	}

	@Column(name = "SCREENSHOT")
	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
		addValidField("screenshot");
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		addValidField("type");
	}

	@Column(name = "SRC")
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
		addValidField("src");
	}

	@Column(name = "PARAMETERS")
	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
		addValidField("parameters");
	}

	@Column(name = "WIDTH")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
		addValidField("width");
	}

	@Column(name = "HEIGHT")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
		addValidField("height");
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
		addValidField("seq");
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

	@Column(name = "DEFAULT_DISPLAY")
	public String getDefaultDisplay() {
		return defaultDisplay;
	}

	public void setDefaultDisplay(String defaultDisplay) {
		this.defaultDisplay = defaultDisplay;
		addValidField("defaultDisplay");
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		addValidField("status");
	}

	@Column(name = "THIRDPARTY_PORTLET")
	public String getThirdpartyPortlet() {
		return thirdpartyPortlet;
	}

	public void setThirdpartyPortlet(String thirdpartyPortlet) {
		this.thirdpartyPortlet = thirdpartyPortlet;
		addValidField("thirdpartyPortlet");
	}

	@Column(name = "COLUMN_INDEX")
	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
		addValidField("columnIndex");
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
		addValidField("icon");
	}

}
