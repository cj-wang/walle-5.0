package cn.walle.framework.core.support;

import java.io.InputStream;
import java.util.Map;

public interface FileUploadHandler {
	
	void uploadFile(Map<String, String> parameters, String fileName, InputStream content);

}
