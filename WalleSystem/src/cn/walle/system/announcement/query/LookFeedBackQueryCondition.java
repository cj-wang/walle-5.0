package cn.walle.system.announcement.query;

import cn.walle.framework.core.query.BaseQueryCondition;


public class LookFeedBackQueryCondition extends BaseQueryCondition {

	private String announcementId;

	public String getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(String announcementId) {
		this.announcementId = announcementId;
	}

}
