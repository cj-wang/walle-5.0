package cn.walle.platform.query;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class HtmlTagAttrQueryItem extends BaseQueryItem {

	private String uuid;
	private String tagUuid;
	private String attrName;
	private String attrValue;
	private Integer seq;

	@Column(name = "UUID")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
		addValidField("uuid");
	}

	@Column(name = "TAG_UUID")
	public String getTagUuid() {
		return tagUuid;
	}

	public void setTagUuid(String tagUuid) {
		this.tagUuid = tagUuid;
		addValidField("tagUuid");
	}

	@Column(name = "ATTR_NAME")
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
		addValidField("attrName");
	}

	@Column(name = "ATTR_VALUE")
	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
		addValidField("attrValue");
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
