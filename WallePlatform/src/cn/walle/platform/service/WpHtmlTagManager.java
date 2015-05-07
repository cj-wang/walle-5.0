package cn.walle.platform.service;

import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.platform.entity.HtmlTagEntity;

public interface WpHtmlTagManager extends BaseManager {

	List<HtmlTagEntity> getTags(String url, String tagId);
	
	String generateTagContent(String url, String tagId);

	void saveTags(String url, String tagId, List<HtmlTagEntity> tags);
	
}
