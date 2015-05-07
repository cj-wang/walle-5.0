package cn.walle.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.walle.framework.core.model.BaseModelClass;


/**
 * Model class for 提醒历史表
 */
@Entity
@Table(name = "WL_NOTIFICATION_HIS")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class WlNotificationHisModel extends BaseModelClass {

	//提醒编号
	private String notificationHisId;
	//用户编号
	private String userId;
	//提醒类型
	private String type;
	//标题
	private String subject;
	//提醒内容
	private String content;
	//执行目标
	private String actionTarget;
	//来源ID
	private String sourceId;
	//创建时间
	private Date createTime;
	//readTime
	private Date readTime;

	/**
	 * Get 提醒编号
	 */
	@Column(name = "NOTIFICATION_HIS_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getNotificationHisId() {
		return notificationHisId;
	}

	/**
	 * Set 提醒编号
	 */
	public void setNotificationHisId(String notificationHisId) {
		this.notificationHisId = notificationHisId;
		addValidField("notificationHisId");
	}

	/**
	 * Get 用户编号
	 */
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}

	/**
	 * Set 用户编号
	 */
	public void setUserId(String userId) {
		this.userId = userId;
		addValidField("userId");
	}

	/**
	 * Get 提醒类型
	 */
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	/**
	 * Set 提醒类型
	 */
	public void setType(String type) {
		this.type = type;
		addValidField("type");
	}

	/**
	 * Get 标题
	 */
	@Column(name = "SUBJECT")
	public String getSubject() {
		return subject;
	}

	/**
	 * Set 标题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
		addValidField("subject");
	}

	/**
	 * Get 提醒内容
	 */
	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	/**
	 * Set 提醒内容
	 */
	public void setContent(String content) {
		this.content = content;
		addValidField("content");
	}

	/**
	 * Get 执行目标
	 */
	@Column(name = "ACTION_TARGET")
	public String getActionTarget() {
		return actionTarget;
	}

	/**
	 * Set 执行目标
	 */
	public void setActionTarget(String actionTarget) {
		this.actionTarget = actionTarget;
		addValidField("actionTarget");
	}

	/**
	 * Get 来源ID
	 */
	@Column(name = "SOURCE_ID")
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * Set 来源ID
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
		addValidField("sourceId");
	}

	/**
	 * Get 创建时间
	 */
	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Set 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
		addValidField("createTime");
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
		addValidField("readTime");
	}

}
