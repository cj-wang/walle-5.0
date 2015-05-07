package cn.walle.platform.query;

import cn.walle.framework.core.query.BaseQueryCondition;

public class HtmlTagAttrQueryCondition extends BaseQueryCondition {

	private String url;
	private String tagId;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

}
