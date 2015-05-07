package cn.walle.platform.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.platform.model.WpDynamicPageHistoryModel;
import cn.walle.platform.model.WpDynamicPageModel;
import cn.walle.platform.model.WpDynamicPageReleaseModel;
import cn.walle.platform.service.WpDynamicPageManager;

@Service
public class WpDynamicPageManagerImpl extends BaseManagerImpl implements WpDynamicPageManager {

	public List<WpDynamicPageModel> saveAll(Collection<WpDynamicPageModel> models) {
		return this.dao.saveAll(models);
	}

	public List<WpDynamicPageModel> release(Collection<WpDynamicPageModel> models) {
		//save release
		Collection<WpDynamicPageReleaseModel> releaseModels = new ArrayList<WpDynamicPageReleaseModel>();
		for (WpDynamicPageModel model : models) {
			WpDynamicPageReleaseModel releaseModel = new WpDynamicPageReleaseModel();
			releaseModel.setUrl(model.getUrl());
			releaseModel.setContentType(model.getContentType());
			List<WpDynamicPageReleaseModel> list = this.dao.findByExample(releaseModel);
			if (list.size() > 0) {
				releaseModel = list.get(0);
			}
			releaseModel.setContent(model.getContent());
			releaseModels.add(releaseModel);
		}
		this.dao.saveAll(releaseModels);
		
		//save his
		Collection<WpDynamicPageHistoryModel> hisModels = new ArrayList<WpDynamicPageHistoryModel>();
		for (WpDynamicPageModel model : models) {
			WpDynamicPageHistoryModel hisModel = new WpDynamicPageHistoryModel();
			hisModel.setUrl(model.getUrl());
			hisModel.setContentType(model.getContentType());
			hisModel.setContent(model.getContent());
			hisModels.add(hisModel);
		}
		this.dao.saveAll(hisModels);
		
		//save
		return this.dao.saveAll(models);
	}
	
}
