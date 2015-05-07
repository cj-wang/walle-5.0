package cn.walle.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.platform.entity.HtmlTagEntity;
import cn.walle.platform.model.WpHtmlTagAttrModel;
import cn.walle.platform.model.WpHtmlTagModel;
import cn.walle.platform.query.HtmlTagAttrQueryCondition;
import cn.walle.platform.query.HtmlTagQueryCondition;
import cn.walle.platform.service.WpHtmlTagManager;

@Service
public class WpHtmlTagManagerImpl extends BaseManagerImpl implements WpHtmlTagManager {

	public List<HtmlTagEntity> getTags(String url, String tagId) {
		//查询tags
		HtmlTagQueryCondition htmlTagQueryCondition = new HtmlTagQueryCondition();
		htmlTagQueryCondition.setUrl(url);
		htmlTagQueryCondition.setTagId(tagId);
		List<HtmlTagEntity> tags  = this.dao.query(htmlTagQueryCondition, HtmlTagEntity.class);
		if (tags.size() == 0) {
			return tags;
		}

		Map<String, HtmlTagEntity> tagMap = new HashMap<String, HtmlTagEntity>();
		for (HtmlTagEntity tag : tags) {
			tagMap.put(tag.getUuid(), tag);
		}
		
		//组装children
		for (HtmlTagEntity tag : tags) {
			if (! "0".equals(tag.getParentUuid())) {
				tagMap.get(tag.getParentUuid()).getChildren().add(tag);
			}
		}
		
		//查询attrs
		HtmlTagAttrQueryCondition htmlTagAttrQueryCondition = new HtmlTagAttrQueryCondition();
		htmlTagAttrQueryCondition.setUrl(url);
		htmlTagAttrQueryCondition.setTagId(tagId);
		List<WpHtmlTagAttrModel> attrs  = this.dao.query(htmlTagAttrQueryCondition, WpHtmlTagAttrModel.class);
		
		//组装attrs
		for (WpHtmlTagAttrModel attr : attrs) {
			tagMap.get(attr.getTagUuid()).getAttrs().add(attr);
		}
		
		//找到roots
		List<HtmlTagEntity> roots = new ArrayList<HtmlTagEntity>();
		for (HtmlTagEntity tag : tags) {
			if ("0".equals(tag.getParentUuid())) {
				roots.add(tag);
			}
		}
		return roots;
	}

	public String generateTagContent(String url, String tagId) {
		List<HtmlTagEntity> tags = this.getTags(url, tagId);
		StringBuilder sb = new StringBuilder();
		for (HtmlTagEntity tag : tags) {
			sb.append(tag.toString());
		}
		return sb.toString();
	}

	public void saveTags(String url, String tagId, List<HtmlTagEntity> tags) {
		Set<String> tagIds = new HashSet<String>();
		Set<String> attrIds = new HashSet<String>();

		//保存
		for (int i = 0; i < tags.size(); i++) {
			HtmlTagEntity tag = tags.get(i);
			tag.setUrl(url);
			tag.setTagId(tagId);
			tag.setParentUuid("0");
			tag.setSeq(i);
			saveTag(tag, tagIds, attrIds);
		}

		//删除旧数据attrs
		HtmlTagAttrQueryCondition htmlTagAttrQueryCondition = new HtmlTagAttrQueryCondition();
		htmlTagAttrQueryCondition.setUrl(url);
		htmlTagAttrQueryCondition.setTagId(tagId);
		List<WpHtmlTagAttrModel> oldattrs  = this.dao.query(htmlTagAttrQueryCondition, WpHtmlTagAttrModel.class);
		for (WpHtmlTagAttrModel oldattr : oldattrs) {
			if (! attrIds.contains(oldattr.getUuid())) {
				this.dao.remove(oldattr);
			}
		}

		//删除旧数据tags
		HtmlTagQueryCondition htmlTagQueryCondition = new HtmlTagQueryCondition();
		htmlTagQueryCondition.setUrl(url);
		htmlTagQueryCondition.setTagId(tagId);
		List<WpHtmlTagModel> oldtags  = this.dao.query(htmlTagQueryCondition, WpHtmlTagModel.class);
		for (WpHtmlTagModel oldtag : oldtags) {
			if (!tagIds.contains(oldtag.getUuid())) {
				this.dao.remove(oldtag);
			}
		}
	}
	
	private void saveTag(HtmlTagEntity tag, Set<String> tagIds, Set<String> attrIds) {
		tag = this.dao.save(tag);
		tagIds.add(tag.getUuid());
		for (int i = 0; i < tag.getChildren().size(); i++) {
			HtmlTagEntity child = tag.getChildren().get(i);
			child.setUrl(null);
			child.setTagId(null);
			child.setParentUuid(tag.getUuid());
			child.setSeq(i);
			saveTag(child, tagIds, attrIds);
		}
		for (int i = 0; i < tag.getAttrs().size(); i++) {
			WpHtmlTagAttrModel attr = tag.getAttrs().get(i);
			attr.setTagUuid(tag.getUuid());
			attr.setSeq(i);
			attr = this.dao.save(attr);
			attrIds.add(attr.getUuid());
		}
	}
}
