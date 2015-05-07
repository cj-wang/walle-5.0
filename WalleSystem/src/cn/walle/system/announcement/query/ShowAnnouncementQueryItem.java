package cn.walle.system.announcement.query;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import cn.walle.framework.core.query.BaseQueryItem;
import cn.walle.system.announcement.model.OaAnnouncementModel.FieldNames;


@Entity
public class ShowAnnouncementQueryItem extends BaseQueryItem {

	private String announcementId;
	private String userId;
	private String orgId;
	private Date addtime;
	private Date endtime;
	private String title;
	private String creator;
	private Date createTime;
	private String modifier;
	private Date modifyTime;
	private Long recVer;
	private Date publishtime;
	private String content;
	private String attachment;

	@Column(name = "ANNOUNCEMENT_ID")
	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
		addValidField("announcementId");
	}

	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
	}

	@Column(name = "ORG_ID")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
		addValidField("orgId");
	}

	@Column(name = "ADDTIME")
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
		addValidField("addtime");
	}

	@Column(name = "ENDTIME")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
		addValidField("endtime");
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		addValidField("title");
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

	@Column(name = "PUBLISHTIME")
	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
		addValidField("publishtime");
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		addValidField("content");
	}

	@Column(name = "ATTACHMENT")
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
		addValidField("attachment");
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
