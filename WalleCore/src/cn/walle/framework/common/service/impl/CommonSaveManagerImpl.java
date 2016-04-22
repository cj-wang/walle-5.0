package cn.walle.framework.common.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.CommonSaveManager;
import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.service.impl.BaseManagerImpl;

@Service
public class CommonSaveManagerImpl extends BaseManagerImpl implements CommonSaveManager<BaseModelClass> {

	@Override
	public BaseModelClass save(BaseModelClass model) {
		return this.dao.save(model);
	}

	@Override
	public List<BaseModelClass> saveAll(Collection<BaseModelClass> models) {
		return this.dao.saveAll(models);
	}

	@Override
	public List<BaseModelClass> saveTreeData(Collection<BaseModelClass> models, String idField, String parentField) {
		return this.dao.saveTreeData(models, idField, parentField);
	}

	@Override
	public void remove(BaseModelClass model) {
		this.dao.remove(model);
	}

	@Override
	public void removeAll(Collection<BaseModelClass> models) {
		this.dao.removeAll(models);
	}
	
}
