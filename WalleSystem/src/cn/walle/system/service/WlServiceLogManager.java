package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlServiceLogModel;

public interface WlServiceLogManager extends BaseManager {

	WlServiceLogModel get(String id);

	List<WlServiceLogModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlServiceLogModel> findByExample(WlServiceLogModel example, String orderBy, PagingInfo pagingInfo);

	WlServiceLogModel save(WlServiceLogModel model);

	List<WlServiceLogModel> saveAll(Collection<WlServiceLogModel> models);

	void remove(WlServiceLogModel model);

	void removeAll(Collection<WlServiceLogModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void deleAll();

}
