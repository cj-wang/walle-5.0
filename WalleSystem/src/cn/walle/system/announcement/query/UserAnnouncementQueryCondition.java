package cn.walle.system.announcement.query;

import java.util.Date;

import cn.walle.framework.core.query.BaseQueryCondition;


public class UserAnnouncementQueryCondition extends BaseQueryCondition {

	private String readFlag;
	private String userid;
	private String title;
	private Date startime;
	private Date endtime;

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartime() {
		return startime;
	}

	public void setStartime(Date startime) {
		this.startime = startime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

}
