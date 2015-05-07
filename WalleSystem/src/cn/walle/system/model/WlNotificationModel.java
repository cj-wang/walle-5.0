package cn.walle.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.walle.framework.core.model.BaseModelClass;


/**
 * Model class for 提醒表
 */
@Entity
@Table(name = "WL_NOTIFICATION")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class WlNotificationModel extends BaseModelClass {

	//提醒编号
	private String notificationId;
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
	//提醒时间
	private Date notificationTime;

	/**
	 * Get 提醒编号
	 */
	@Column(name = "NOTIFICATION_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getNotificationId() {
		return notificationId;
	}

	/**
	 * Set 提醒编号
	 */
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
		addValidField("notificationId");
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
	 * Get 提醒时间
	 */
	@Column(name = "NOTIFICATION_TIME")
	public Date getNotificationTime() {
		return notificationTime;
	}

	/**
	 * Set 提醒时间
	 */
	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
		addValidField("notificationTime");
	}

}
