package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSqlLogModel;
import cn.walle.system.service.WlSqlLogManager;

@Service
public class WlSqlLogManagerImpl extends BaseManagerImpl implements WlSqlLogManager {
	
	public WlSqlLogModel get(String id) {
		return this.dao.get(WlSqlLogModel.class, id);
	}

	public List<WlSqlLogModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlSqlLogModel.class, orderBy, pagingInfo);
	}

	public List<WlSqlLogModel> findByExample(WlSqlLogModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlSqlLogModel save(WlSqlLogModel model) {
		return this.dao.save(model);
	}

	public List<WlSqlLogModel> saveAll(Collection<WlSqlLogModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlSqlLogModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlSqlLogModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlSqlLogModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlSqlLogModel.class, ids);
	}

	@Override
	public void deleAll() {
		// TODO Auto-generated method stub
		Collection<WlSqlLogModel> models=this.getAll(null, null);
		this.removeAll(models);
	}

}
