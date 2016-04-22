package cn.walle.framework.common.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.CommonSaveManager;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class CommonSaveManagerImpl extends BaseManagerImpl implements CommonSaveManager<BaseModel> {

	@Override
	public BaseModel save(BaseModel model) {
		return this.dao.save(model);
	}

	@Override
	public List<BaseModel> saveAll(Collection<BaseModel> models) {
		return this.dao.saveAll(models);
	}

	@Override
	public List<BaseModel> saveTreeData(Collection<BaseModel> models, String idField, String parentField) {
		return this.dao.saveTreeData(models, idField, parentField);
	}

	@Override
	public void remove(BaseModel model) {
		this.dao.remove(model);
	}

	@Override
	public void removeAll(Collection<BaseModel> models) {
		this.dao.removeAll(models);
	}
	
}
