package cn.walle.framework.core.support;

import java.util.Date;

import cn.walle.framework.core.model.BaseObject;

public class FileDesc extends BaseObject {
	
	private String fileName;
	private long fileSize;
	private Date createTime;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
