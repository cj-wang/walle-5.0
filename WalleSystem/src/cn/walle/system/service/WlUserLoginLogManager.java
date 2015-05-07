package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlUserLoginLogModel;

public interface WlUserLoginLogManager extends BaseManager {

	WlUserLoginLogModel get(String id);

	List<WlUserLoginLogModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlUserLoginLogModel> findByExample(WlUserLoginLogModel example, String orderBy, PagingInfo pagingInfo);

	WlUserLoginLogModel save(WlUserLoginLogModel model);

	List<WlUserLoginLogModel> saveAll(Collection<WlUserLoginLogModel> models);

	void remove(WlUserLoginLogModel model);

	void removeAll(Collection<WlUserLoginLogModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
