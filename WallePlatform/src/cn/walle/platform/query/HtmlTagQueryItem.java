package cn.walle.platform.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class HtmlTagQueryItem extends BaseQueryItem {

	private String uuid;
	private String url;
	private String tagId;
	private String tagName;
	private String parentUuid;
	private Integer seq;

	@Column(name = "UUID")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		addValidField("uuid");
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		addValidField("url");
	}

	@Column(name = "TAG_ID")
	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
		addValidField("tagId");
	}

	@Column(name = "TAG_NAME")
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
		addValidField("tagName");
	}

	@Column(name = "PARENT_UUID")
	public String getParentUuid() {
		return parentUuid;
	}

	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
		addValidField("parentUuid");
	}

	@Column(name = "SEQ")
	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
		addValidField("seq");
	}

}
