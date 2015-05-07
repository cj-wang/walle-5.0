package cn.walle.platform.entity;

import java.util.ArrayList;
import java.util.List;

import cn.walle.platform.model.WpHtmlTagAttrModel;
import cn.walle.platform.model.WpHtmlTagModel;

public class HtmlTagEntity extends WpHtmlTagModel {
	
	private List<HtmlTagEntity> children = new ArrayList<HtmlTagEntity>();
	private List<WpHtmlTagAttrModel> attrs = new ArrayList<WpHtmlTagAttrModel>();

	public String toString() {
		if ("TextNode".equals(this.getTagName())) {
			if (this.attrs.size() > 0) {
				String text = this.attrs.get(0).getAttrValue();
				return text == null ? "" : text;
			} else {
				return "";
			}
		}
		if (this.getTagName() == null || this.getTagName().length() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(this.getTagName());
		for (WpHtmlTagAttrModel attr : this.getAttrs()) {
			if (attr.getAttrName() == null || attr.getAttrName().length() == 0) {
				continue;
			}
			sb.append(" ").append(attr.getAttrName()).append("=\"").append(attr.getAttrValue()).append("\"");
		}
		sb.append(">\n");
		for (HtmlTagEntity child : this.children) {
			sb.append(child);
		}
		sb.append("</").append(this.getTagName()).append(">\n");
		return sb.toString();
	}
	
	
	public List<HtmlTagEntity> getChildren() {
		return children;
	}

	public void setChildren(List<HtmlTagEntity> children) {
		this.children = children;
	}

	public List<WpHtmlTagAttrModel> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<WpHtmlTagAttrModel> attrs) {
		this.attrs = attrs;
	}
	
}
