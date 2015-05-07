package cn.walle.system.announcement.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.announcement.model.OaUserAnnouncementModel;
import cn.walle.system.announcement.query.UserAnnouncementQueryCondition;
import cn.walle.system.announcement.query.UserAnnouncementQueryItem;


public interface OaUserAnnouncementManager extends BaseManager {

	OaUserAnnouncementModel get(String id);

	List<OaUserAnnouncementModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<OaUserAnnouncementModel> findByExample(OaUserAnnouncementModel example, String orderBy, PagingInfo pagingInfo);

	OaUserAnnouncementModel save(OaUserAnnouncementModel model);

	List<OaUserAnnouncementModel> saveAll(Collection<OaUserAnnouncementModel> models);

	void remove(OaUserAnnouncementModel model);

	void removeAll(Collection<OaUserAnnouncementModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	List<UserAnnouncementQueryItem> getAnnouncementByParam(UserAnnouncementQueryCondition param,PagingInfo pagingInfo);

	OaUserAnnouncementModel  findInfo(String announcementId,String userId);
	
    boolean feedBackSave(String announcementId,String feedInfo);	
}
