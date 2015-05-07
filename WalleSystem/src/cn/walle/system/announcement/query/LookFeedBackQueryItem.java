package cn.walle.system.announcement.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;
import cn.walle.system.announcement.model.OaAnnouncementModel.FieldNames;


@Entity
public class LookFeedBackQueryItem extends BaseQueryItem {

	private String id;
	private String announcementId;
	private String receiveUserId;
	private Date readTime;
	private String readFlag;
	private String feedInfo;
	private String feedOrgId;
	private String feedStatus;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Long recVer;
	private String actionTarget;
	

	@Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		addValidField("id");
	}

	@Column(name = "ANNOUNCEMENT_ID")
	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
		addValidField("announcementId");
	}

	@Column(name = "RECEIVE_USER_ID")
	public String getReceiveUserId() {
		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
		addValidField("receiveUserId");
	}

	@Column(name = "READ_TIME")
	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
		addValidField("readTime");
	}

	@Column(name = "READ_FLAG")
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
		addValidField("readFlag");
	}

	@Column(name = "feed_info")
	public String getFeedInfo() {
		return feedInfo;
	}

	public void setFeedInfo(String feedInfo) {
		this.feedInfo = feedInfo;
		addValidField("feedInfo");
	}

	@Column(name = "feed_org_id")
	public String getFeedOrgId() {
		return feedOrgId;
	}

	public void setFeedOrgId(String feedOrgId) {
		this.feedOrgId = feedOrgId;
		addValidField("feedOrgId");
	}

	@Column(name = "feed_status")
	public String getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
		addValidField("feedStatus");
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

	@Column(name = "ACTION_TARGET")
	public String getActionTarget() {
		return actionTarget;
	}

	public void setActionTarget(String actionTarget) {
		this.actionTarget = actionTarget;
		addValidField("actionTarget");
	}
	//紧急
	private String isEmergent;
	/**
	 * Get 紧急
	 * 1是，0是否
	 */
	@Column(name = "IS_EMERGENT")
	public String getIsEmergent() {
		return isEmergent;
	}

	/**
	 * Set 紧急
	 * 1是，0是否
	 */
	public void setIsEmergent(String isEmergent) {
		this.isEmergent = isEmergent;
		addValidField(FieldNames.isEmergent);
	}

}
