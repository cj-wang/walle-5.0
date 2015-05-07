package cn.walle.system.announcement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.model.OperationLog;


/**
 * Model class for EzUserAnnouncement
 */
@Entity
@Table(name = "OA_USER_ANNOUNCEMENT")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class OaUserAnnouncementModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "OaUserAnnouncement";

	public static final class FieldNames {
		/**
		 * id
		 */
		public static final String id = "id";
		/**
		 * announcementId
		 */
		public static final String announcementId = "announcementId";
		/**
		 * receiveUserId
		 */
		public static final String receiveUserId = "receiveUserId";
		/**
		 * readTime
		 */
		public static final String readTime = "readTime";
		/**
		 * readFlag
		 */
		public static final String readFlag = "readFlag";
		/**
		 * feedInfo
		 */
		public static final String feedInfo = "feedInfo";
		/**
		 * feedOrgId
		 */
		public static final String feedOrgId = "feedOrgId";
		/**
		 * feedStatus
		 */
		public static final String feedStatus = "feedStatus";
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
		 * actionTarget
		 */
		public static final String actionTarget = "actionTarget";
	}

	//id
	private String id;
	//公告ID
	private String announcementId;
	//接收人
	private String receiveUserId;
	//阅读时间
	private Date readTime;
	//阅读标记
	private String readFlag;
	//feedInfo
	private String feedInfo;
	//feedOrgId
	private String feedOrgId;
	//feedStatus
	private String feedStatus;
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
	//执行目标
	private String actionTarget;

	/**
	 * Get id
	 */
	@Column(name = "ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getId() {
		return id;
	}

	/**
	 * Set id
	 */
	public void setId(String id) {
		this.id = id;
		addValidField(FieldNames.id);
	}

	/**
	 * Get announcementId
	 */
	@Column(name = "ANNOUNCEMENT_ID")
	public String getAnnouncementId() {
		return announcementId;
	}

	/**
	 * Set announcementId
	 */
	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
		addValidField(FieldNames.announcementId);
	}

	/**
	 * Get receiveUserId
	 */
	@Column(name = "RECEIVE_USER_ID")
	public String getReceiveUserId() {
		return receiveUserId;
	}

	/**
	 * Set receiveUserId
	 */
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
		addValidField(FieldNames.receiveUserId);
	}

	/**
	 * Get readTime
	 */
	@Column(name = "READ_TIME")
	public Date getReadTime() {
		return readTime;
	}

	/**
	 * Set readTime
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
		addValidField(FieldNames.readTime);
	}

	/**
	 * Get readFlag
	 */
	@Column(name = "READ_FLAG")
	public String getReadFlag() {
		return readFlag;
	}

	/**
	 * Set readFlag
	 */
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
		addValidField(FieldNames.readFlag);
	}

	/**
	 * Get feedInfo
	 */
	@Column(name = "feed_info")
	public String getFeedInfo() {
		return feedInfo;
	}

	/**
	 * Set feedInfo
	 */
	public void setFeedInfo(String feedInfo) {
		this.feedInfo = feedInfo;
		addValidField(FieldNames.feedInfo);
	}

	/**
	 * Get feedOrgId
	 */
	@Column(name = "feed_org_id")
	public String getFeedOrgId() {
		return feedOrgId;
	}

	/**
	 * Set feedOrgId
	 */
	public void setFeedOrgId(String feedOrgId) {
		this.feedOrgId = feedOrgId;
		addValidField(FieldNames.feedOrgId);
	}
	
	/**
	 * Get feedStatus
	 */
	@Column(name = "feed_status")
	public String getFeedStatus() {
		return feedStatus;
	}

	/**
	 * Set feedStatus
	 */
	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
		addValidField(FieldNames.feedStatus);
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
	 * Get actionTarget
	 */
	@Column(name = "ACTION_TARGET")
	public String getActionTarget() {
		return actionTarget;
	}

	/**
	 * Set actionTarget
	 */
	public void setActionTarget(String actionTarget) {
		this.actionTarget = actionTarget;
		addValidField(FieldNames.actionTarget);
	}

}
