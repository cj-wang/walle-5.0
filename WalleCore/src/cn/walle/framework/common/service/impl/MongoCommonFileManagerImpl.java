package cn.walle.framework.common.service.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.MongoCommonFileManager;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.FileToDownload;

import com.mongodb.gridfs.GridFSDBFile;

@Service
public class MongoCommonFileManagerImpl extends BaseManagerImpl implements MongoCommonFileManager {

	@Autowired
	private GridFsTemplate gridFsTemplate;
	
	@Override
	public void uploadFile(Map<String, String> parameters, String fileName, InputStream content) {
		if (! parameters.containsKey("uuid")) {
			parameters.put("uuid", UUID.randomUUID().toString());
		}
		log.info("mongodb save file " + fileName + ", " + parameters);
		this.gridFsTemplate.store(content, fileName, parameters.get("contentType"), parameters);
	}

	@Override
	public FileToDownload downloadFile(String uuid) {
		log.info("mongodb get file " + uuid);
		GridFSDBFile gridFSDBFile = this.gridFsTemplate.findOne(new Query(GridFsCriteria.whereMetaData("uuid").is(uuid)));
		FileToDownload result = new FileToDownload();
		result.setContent(gridFSDBFile.getInputStream());
		result.setContentType(gridFSDBFile.getContentType());
		result.setFileName(gridFSDBFile.getFilename());
		return result;
	}

	@Override
	public FileToDownload viewFile(String uuid) {
		FileToDownload result = this.downloadFile(uuid);
		result.setForceDownload(false);
		return result;
	}

	@Override
	public void deleteFile(String uuid) {
		log.info("mongodb delete file " + uuid);
		this.gridFsTemplate.delete(new Query(GridFsCriteria.whereMetaData("uuid").is(uuid)));
	}

}
