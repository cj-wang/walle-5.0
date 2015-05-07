package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlUserLoginLogModel;
import cn.walle.system.service.WlUserLoginLogManager;

@Service
public class WlUserLoginLogManagerImpl extends BaseManagerImpl implements WlUserLoginLogManager {

	public WlUserLoginLogModel get(String id) {
		return this.dao.get(WlUserLoginLogModel.class, id);
	}

	public List<WlUserLoginLogModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlUserLoginLogModel.class, orderBy, pagingInfo);
	}

	public List<WlUserLoginLogModel> findByExample(WlUserLoginLogModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlUserLoginLogModel save(WlUserLoginLogModel model) {
		return this.dao.save(model);
	}

	public List<WlUserLoginLogModel> saveAll(Collection<WlUserLoginLogModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlUserLoginLogModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlUserLoginLogModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlUserLoginLogModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlUserLoginLogModel.class, ids);
	}

}
