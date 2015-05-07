package cn.walle.framework.core.support;

import java.io.InputStream;

import cn.walle.framework.core.model.BaseObject;

public class FileToDownload extends BaseObject {
	
	private String fileName;
	private String contentType;
	private InputStream content;
	private boolean forceDownload = true;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public InputStream getContent() {
		return content;
	}
	public void setContent(InputStream content) {
		this.content = content;
	}
	public boolean isForceDownload() {
		return forceDownload;
	}
	public void setForceDownload(boolean forceDownload) {
		this.forceDownload = forceDownload;
	}

}
