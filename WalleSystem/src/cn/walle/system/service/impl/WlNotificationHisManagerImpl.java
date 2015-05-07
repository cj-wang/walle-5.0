package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlNotificationHisModel;
import cn.walle.system.service.WlNotificationHisManager;

@Service
public class WlNotificationHisManagerImpl extends BaseManagerImpl implements WlNotificationHisManager {

	public WlNotificationHisModel get(String id) {
		return this.dao.get(WlNotificationHisModel.class, id);
	}

	public List<WlNotificationHisModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlNotificationHisModel.class, orderBy, pagingInfo);
	}

	public List<WlNotificationHisModel> findByExample(WlNotificationHisModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlNotificationHisModel save(WlNotificationHisModel model) {
		return this.dao.save(model);
	}

	public List<WlNotificationHisModel> saveAll(Collection<WlNotificationHisModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlNotificationHisModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlNotificationHisModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlNotificationHisModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlNotificationHisModel.class, ids);
	}

}
