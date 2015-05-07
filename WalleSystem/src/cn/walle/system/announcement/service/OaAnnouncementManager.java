package cn.walle.system.announcement.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.FileToDownload;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.announcement.model.OaAnnouncementModel;
import cn.walle.system.announcement.query.ShowAnnouncementQueryItem;
import cn.walle.system.announcement.query.UserAnnouncementQueryItem;



public interface OaAnnouncementManager extends BaseManager {

	OaAnnouncementModel get(String id);

	List<OaAnnouncementModel> getAll(String orderBy, PagingInfo pagingInfo);
	
	List<OaAnnouncementModel> query(String orderBy, PagingInfo pagingInfo);

	List<OaAnnouncementModel> findByExample(OaAnnouncementModel example, String orderBy, PagingInfo pagingInfo);

	OaAnnouncementModel save(OaAnnouncementModel model);

	List<OaAnnouncementModel> saveAll(Collection<OaAnnouncementModel> models);

	void remove(OaAnnouncementModel model);

	void removeAll(Collection<OaAnnouncementModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	boolean getRead(String userAnouncementId);
	
	OaAnnouncementModel editSave(OaAnnouncementModel model);
	
	void saveNotification(OaAnnouncementModel model);
	
	void saveAnnouncement(OaAnnouncementModel model);
	
	List<UserAnnouncementQueryItem> getUserAnnouncement(String userId);
	
	public List<ShowAnnouncementQueryItem> findOaAnnouncementMess() ;
	
	void saveExamineAnnouncement(OaAnnouncementModel model);
	
	//下载附件
	 FileToDownload downloadFile(String uuid);
	//删除操作 连同附件一起删除
	 void deleAnnouncementbyId(String uuid);
	 
	 //改变阅读状态
	 boolean changeReadFlag(String uuid);
	 
	 public boolean ishasFile(String uuid);
	 
}
