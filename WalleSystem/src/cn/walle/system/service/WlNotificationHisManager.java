package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlNotificationHisModel;


public interface WlNotificationHisManager extends BaseManager {

	WlNotificationHisModel get(String id);

	List<WlNotificationHisModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlNotificationHisModel> findByExample(WlNotificationHisModel example, String orderBy, PagingInfo pagingInfo);

	WlNotificationHisModel save(WlNotificationHisModel model);

	List<WlNotificationHisModel> saveAll(Collection<WlNotificationHisModel> models);

	void remove(WlNotificationHisModel model);

	void removeAll(Collection<WlNotificationHisModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

}
