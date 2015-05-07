package cn.walle.system.portal.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class QueryMyPortalQueryItem extends BaseQueryItem {

	private String myportletId;
	private String userId;
	private String portletId;
	private String portletName;
	private String title;
	private Integer seq;
	private Integer columnIndex;
	private String screenshot;
	private String src;
	private String width;
	private String height;
	private String icon;

	@Column(name = "MYPORTLET_ID")
	public String getMyportletId() {
		return myportletId;
	}

	public void setMyportletId(String myportletId) {
		this.myportletId = myportletId;
		addValidField("myportletId");
	}

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
	}

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

	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
		addValidField("seq");
	}

	@Column(name = "COLUMN_INDEX")
	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
		addValidField("columnIndex");
	}

	@Column(name = "SCREENSHOT")
	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
		addValidField("screenshot");
	}

	@Column(name = "SRC")
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
		addValidField("src");
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

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
		addValidField("icon");
	}

}
