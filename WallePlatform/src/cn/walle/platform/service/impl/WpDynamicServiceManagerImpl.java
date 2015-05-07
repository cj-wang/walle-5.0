package cn.walle.platform.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.platform.model.WpDynamicServiceHistoryModel;
import cn.walle.platform.model.WpDynamicServiceModel;
import cn.walle.platform.model.WpDynamicServiceReleaseModel;
import cn.walle.platform.service.WpDynamicServiceManager;

@Service
public class WpDynamicServiceManagerImpl extends BaseManagerImpl implements WpDynamicServiceManager {

	public List<WpDynamicServiceModel> saveAll(Collection<WpDynamicServiceModel> models) {
		return this.dao.saveAll(models);
	}

	public List<WpDynamicServiceModel> release(Collection<WpDynamicServiceModel> models) {
		//save release
		Collection<WpDynamicServiceReleaseModel> releaseModels = new ArrayList<WpDynamicServiceReleaseModel>();
		for (WpDynamicServiceModel model : models) {
			WpDynamicServiceReleaseModel releaseModel = new WpDynamicServiceReleaseModel();
			releaseModel.setServiceName(model.getServiceName());
			releaseModel.setContentType(model.getContentType());
			List<WpDynamicServiceReleaseModel> list = this.dao.findByExample(releaseModel);
			if (list.size() > 0) {
				releaseModel = list.get(0);
			}
			releaseModel.setContent(model.getContent());
			releaseModels.add(releaseModel);
		}
		this.dao.saveAll(releaseModels);
		
		//save his
		Collection<WpDynamicServiceHistoryModel> hisModels = new ArrayList<WpDynamicServiceHistoryModel>();
		for (WpDynamicServiceModel model : models) {
			WpDynamicServiceHistoryModel hisModel = new WpDynamicServiceHistoryModel();
			hisModel.setServiceName(model.getServiceName());
			hisModel.setContentType(model.getContentType());
			hisModel.setContent(model.getContent());
			hisModels.add(hisModel);
		}
		this.dao.saveAll(hisModels);
		
		//save
		return this.dao.saveAll(models);
	}
}
