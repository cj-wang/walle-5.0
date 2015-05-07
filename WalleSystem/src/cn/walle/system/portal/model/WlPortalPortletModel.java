package cn.walle.system.portal.model;

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
 * Model class for 组件表
 */
@Entity
@Table(name = "WL_PORTAL_PORTLET")
@DynamicInsert
@DynamicUpdate
public class WlPortalPortletModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "WlPortalPortlet";

	public static final class FieldNames {
		/**
		 * 编号
		 */
		public static final String portletId = "portletId";
		/**
		 * 名称
		 */
		public static final String portletName = "portletName";
		/**
		 * 标题
		 */
		public static final String title = "title";
		/**
		 * 组件描述
		 */
		public static final String description = "description";
		/**
		 * 截图
		 */
		public static final String screenshot = "screenshot";
		/**
		 * 类型
		 */
		public static final String type = "type";
		/**
		 * 地址
		 */
		public static final String src = "src";
		/**
		 * 参数
		 */
		public static final String parameters = "parameters";
		/**
		 * 宽度
		 */
		public static final String width = "width";
		/**
		 * 高度
		 */
		public static final String height = "height";
		/**
		 * 顺序号
		 */
		public static final String seq = "seq";
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
		 * 是否默认显示
		 */
		public static final String defaultDisplay = "defaultDisplay";
		/**
		 * 状态
		 */
		public static final String status = "status";
		/**
		 * 第三方组件
		 */
		public static final String thirdpartyPortlet = "thirdpartyPortlet";
		/**
		 * 0
		 */
		public static final String columnIndex = "columnIndex";
		/**
		 * 小图标16*16
		 */
		public static final String icon = "icon";
		/**
		 * tenantId
		 */
		public static final String tenantId = "tenantId";
	}

	//编号
	private String portletId;
	//名称
	private String portletName;
	//标题
	private String title;
	//组件描述
	private String description;
	//截图
	private String screenshot;
	//类型
	private String type;
	//地址
	private String src;
	//参数
	private String parameters;
	//宽度
	private String width;
	//高度
	private String height;
	//顺序号
	private Integer seq;
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
	//是否默认显示
	private String defaultDisplay;
	//状态
	private String status;
	//第三方组件
	private String thirdpartyPortlet;
	//0
	private Integer columnIndex;
	//小图标16*16
	private String icon;
	//tenantId
	private String tenantId;

	/**
	 * Get 编号
	 */
	@Column(name = "PORTLET_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getPortletId() {
		return portletId;
	}

	/**
	 * Set 编号
	 */
	public void setPortletId(String portletId) {
		this.portletId = portletId;
		addValidField(FieldNames.portletId);
	}

	/**
	 * Get 名称
	 */
	@Column(name = "PORTLET_NAME")
	public String getPortletName() {
		return portletName;
	}

	/**
	 * Set 名称
	 */
	public void setPortletName(String portletName) {
		this.portletName = portletName;
		addValidField(FieldNames.portletName);
	}

	/**
	 * Get 标题
	 */
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	/**
	 * Set 标题
	 */
	public void setTitle(String title) {
		this.title = title;
		addValidField(FieldNames.title);
	}

	/**
	 * Get 组件描述
	 */
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	/**
	 * Set 组件描述
	 */
	public void setDescription(String description) {
		this.description = description;
		addValidField(FieldNames.description);
	}

	/**
	 * Get 截图
	 */
	@Column(name = "SCREENSHOT")
	public String getScreenshot() {
		return screenshot;
	}

	/**
	 * Set 截图
	 */
	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
		addValidField(FieldNames.screenshot);
	}

	/**
	 * Get 类型
	 */
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	/**
	 * Set 类型
	 */
	public void setType(String type) {
		this.type = type;
		addValidField(FieldNames.type);
	}

	/**
	 * Get 地址
	 */
	@Column(name = "SRC")
	public String getSrc() {
		return src;
	}

	/**
	 * Set 地址
	 */
	public void setSrc(String src) {
		this.src = src;
		addValidField(FieldNames.src);
	}

	/**
	 * Get 参数
	 */
	@Column(name = "PARAMETERS")
	public String getParameters() {
		return parameters;
	}

	/**
	 * Set 参数
	 */
	public void setParameters(String parameters) {
		this.parameters = parameters;
		addValidField(FieldNames.parameters);
	}

	/**
	 * Get 宽度
	 */
	@Column(name = "WIDTH")
	public String getWidth() {
		return width;
	}

	/**
	 * Set 宽度
	 */
	public void setWidth(String width) {
		this.width = width;
		addValidField(FieldNames.width);
	}

	/**
	 * Get 高度
	 */
	@Column(name = "HEIGHT")
	public String getHeight() {
		return height;
	}

	/**
	 * Set 高度
	 */
	public void setHeight(String height) {
		this.height = height;
		addValidField(FieldNames.height);
	}

	/**
	 * Get 顺序号
	 */
	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	/**
	 * Set 顺序号
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
		addValidField(FieldNames.seq);
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
	 * Get 是否默认显示
	 * ，如果客户未定制，则显示此组件，1显示 0不显示
	 */
	@Column(name = "DEFAULT_DISPLAY")
	public String getDefaultDisplay() {
		return defaultDisplay;
	}

	/**
	 * Set 是否默认显示
	 * ，如果客户未定制，则显示此组件，1显示 0不显示
	 */
	public void setDefaultDisplay(String defaultDisplay) {
		this.defaultDisplay = defaultDisplay;
		addValidField(FieldNames.defaultDisplay);
	}

	/**
	 * Get 状态
	 * U-使用 F-禁用
	 */
	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	/**
	 * Set 状态
	 * U-使用 F-禁用
	 */
	public void setStatus(String status) {
		this.status = status;
		addValidField(FieldNames.status);
	}

	/**
	 * Get 第三方组件
	 * 1:是  0:否
	 */
	@Column(name = "THIRDPARTY_PORTLET")
	public String getThirdpartyPortlet() {
		return thirdpartyPortlet;
	}

	/**
	 * Set 第三方组件
	 * 1:是  0:否
	 */
	public void setThirdpartyPortlet(String thirdpartyPortlet) {
		this.thirdpartyPortlet = thirdpartyPortlet;
		addValidField(FieldNames.thirdpartyPortlet);
	}

	/**
	 * Get 0
	 * ：左 1：中 2：右
	 */
	@Column(name = "COLUMN_INDEX")
	public Integer getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Set 0
	 * ：左 1：中 2：右
	 */
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
		addValidField(FieldNames.columnIndex);
	}

	/**
	 * Get 小图标16*16
	 */
	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	/**
	 * Set 小图标16*16
	 */
	public void setIcon(String icon) {
		this.icon = icon;
		addValidField(FieldNames.icon);
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
