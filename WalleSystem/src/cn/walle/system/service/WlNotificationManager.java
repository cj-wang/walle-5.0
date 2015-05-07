package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlNotificationModel;


public interface WlNotificationManager extends BaseManager {

	WlNotificationModel get(String id);

	List<WlNotificationModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlNotificationModel> findByExample(WlNotificationModel example, String orderBy, PagingInfo pagingInfo);

	WlNotificationModel save(WlNotificationModel model);

	List<WlNotificationModel> saveAll(Collection<WlNotificationModel> models);

	void remove(WlNotificationModel model);

	void removeAll(Collection<WlNotificationModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	//获取当前用户所拥有的提醒
	List<WlNotificationModel> getCurrentUserNotice();
	
	//将已读提醒移至历史表里
	void moveNotificationToHis(String notificationId);
	
	//获取当前用户所拥有的提醒的数目
	int getNotificationNum();

}
