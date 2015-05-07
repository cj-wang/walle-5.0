package cn.walle.system.announcement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.model.OperationLog;

/**
 * Model class for 通知
 */
@Entity
@Table(name = "OA_ANNOUNCEMENT")
@DynamicInsert
@DynamicUpdate
public class OaAnnouncementModel extends BaseModelClass implements OperationLog {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "OaAnnouncement";

	public static final class FieldNames {
		/**
		 * 公告编号
		 */
		public static final String announcementId = "announcementId";
		/**
		 * 添加的用户
		 */
		public static final String userId = "userId";
		/**
		 * 添加的部门
		 */
		public static final String orgId = "orgId";
		/**
		 * 添加时间
		 */
		public static final String addtime = "addtime";
		/**
		 * 失效时间
		 */
		public static final String endtime = "endtime";
		/**
		 * 标题
		 */
		public static final String title = "title";
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
		 * 发布时间
		 */
		public static final String publishtime = "publishtime";
		/**
		 * 内容
		 */
		public static final String content = "content";
		/**
		 * 附件uuid
		 */
		public static final String attachment = "attachment";
		/**
		 * announcePic
		 */
		public static final String announcePic = "announcePic";
		/**
		 * examineStatus
		 */
		public static final String examineStatus = "examineStatus";
		/**
		 * suggestion
		 */
		public static final String suggestion = "suggestion";
		/**
		 * checker
		 */
		public static final String checker = "checker";
		/**
		 * 紧急
		 */
		public static final String isEmergent = "isEmergent";
	}

	//公告编号
	private String announcementId;
	//添加的用户
	private String userId;
	//添加的部门
	private String orgId;
	//添加时间
	private Date addtime;
	//失效时间
	private Date endtime;
	//标题
	private String title;
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
	//发布时间
	private Date publishtime;
	//内容
	private String content;
	//附件uuid
	private String attachment;
	//announcePic
	private String announcePic;
	//examineStatus
	private String examineStatus;
	//suggestion
	private String suggestion;
	//checker
	private String checker;
	//紧急
	private String isEmergent;

	/**
	 * Get 公告编号
	 */
	@Column(name = "ANNOUNCEMENT_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getAnnouncementId() {
		return announcementId;
	}

	/**
	 * Set 公告编号
	 */
	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
		addValidField(FieldNames.announcementId);
	}

	/**
	 * Get 添加的用户
	 * selectCode.user
	 */
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	/**
	 * Set 添加的用户
	 * selectCode.user
	 */
	public void setUserId(String userId) {
		this.userId = userId;
		addValidField(FieldNames.userId);
	}

	/**
	 * Get 添加的部门
	 * selectCode.org
	 */
	@Column(name = "ORG_ID")
	public String getOrgId() {
		return orgId;
	}

	/**
	 * Set 添加的部门
	 * selectCode.org
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
		addValidField(FieldNames.orgId);
	}

	/**
	 * Get 添加时间
	 */
	@Column(name = "ADDTIME")
	public Date getAddtime() {
		return addtime;
	}

	/**
	 * Set 添加时间
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
		addValidField(FieldNames.addtime);
	}

	/**
	 * Get 失效时间
	 */
	@Column(name = "ENDTIME")
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * Set 失效时间
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
		addValidField(FieldNames.endtime);
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
	 * Get 发布时间
	 */
	@Column(name = "PUBLISHTIME")
	public Date getPublishtime() {
		return publishtime;
	}

	/**
	 * Set 发布时间
	 */
	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
		addValidField(FieldNames.publishtime);
	}

	/**
	 * Get 内容
	 */
	@Column(name = "CONTENT")
	
	public String getContent() {
		return content;
	}

	/**
	 * Set 内容
	 */
	public void setContent(String content) {
		this.content = content;
		addValidField(FieldNames.content);
	}

	/**
	 * Get 附件uuid
	 */
	@Column(name = "ATTACHMENT")
	public String getAttachment() {
		return attachment;
	}

	/**
	 * Set 附件uuid
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
		addValidField(FieldNames.attachment);
	}

	/**
	 * Get announcePic
	 */
	@Column(name = "ANNOUNCE_PIC")
	public String getAnnouncePic() {
		return announcePic;
	}

	/**
	 * Set announcePic
	 */
	public void setAnnouncePic(String announcePic) {
		this.announcePic = announcePic;
		addValidField(FieldNames.announcePic);
	}

	/**
	 * Get examineStatus
	 */
	@Column(name = "EXAMINE_STATUS")
	public String getExamineStatus() {
		return examineStatus;
	}

	/**
	 * Set examineStatus
	 */
	public void setExamineStatus(String examineStatus) {
		this.examineStatus = examineStatus;
		addValidField(FieldNames.examineStatus);
	}

	/**
	 * Get suggestion
	 */
	@Column(name = "SUGGESTION")
	public String getSuggestion() {
		return suggestion;
	}

	/**
	 * Set suggestion
	 */
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
		addValidField(FieldNames.suggestion);
	}

	/**
	 * Get checker
	 */
	@Column(name = "CHECKER")
	public String getChecker() {
		return checker;
	}

	/**
	 * Set checker
	 */
	public void setChecker(String checker) {
		this.checker = checker;
		addValidField(FieldNames.checker);
	}

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
