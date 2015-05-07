package cn.walle.framework.common.service;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.FileToDownload;
import cn.walle.framework.core.support.FileUploadHandler;

public interface MongoCommonFileManager extends BaseManager, FileUploadHandler {

	FileToDownload downloadFile(String id);
	
	FileToDownload viewFile(String uuid);
	
	void deleteFile(String id);
	
}
