package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlOnlineUserModel;

public interface WlOnlineUserManager extends BaseManager {


	WlOnlineUserModel get(String id);

	List<WlOnlineUserModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlOnlineUserModel> findByExample(WlOnlineUserModel example, String orderBy, PagingInfo pagingInfo);

	WlOnlineUserModel save(WlOnlineUserModel model);

	List<WlOnlineUserModel> saveAll(Collection<WlOnlineUserModel> models);

	void remove(WlOnlineUserModel model);

	void removeAll(Collection<WlOnlineUserModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void forceExpired(String onlineUserId);

}
