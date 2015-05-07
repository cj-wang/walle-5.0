package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlServiceLogModel;
import cn.walle.system.service.WlServiceLogManager;

@Service
public class WlServiceLogManagerImpl extends BaseManagerImpl implements WlServiceLogManager {

	public WlServiceLogModel get(String id) {
		return this.dao.get(WlServiceLogModel.class, id);
	}

	public List<WlServiceLogModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlServiceLogModel.class, orderBy, pagingInfo);
	}

	public List<WlServiceLogModel> findByExample(WlServiceLogModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlServiceLogModel save(WlServiceLogModel model) {
		return this.dao.save(model);
	}

	public List<WlServiceLogModel> saveAll(Collection<WlServiceLogModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlServiceLogModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlServiceLogModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlServiceLogModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlServiceLogModel.class, ids);
	}
    public void deleAll()
    {
    	Collection<WlServiceLogModel> models=this.getAll(null, null);
    	this.removeAll(models);
    }

}
