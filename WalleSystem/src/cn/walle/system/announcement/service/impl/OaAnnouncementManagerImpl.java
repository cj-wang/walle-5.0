package cn.walle.system.announcement.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.FileToDownload;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.announcement.model.OaAnnouncementModel;
import cn.walle.system.announcement.model.OaUserAnnouncementModel;
import cn.walle.system.announcement.query.ShowAnnouncementQueryCondition;
import cn.walle.system.announcement.query.ShowAnnouncementQueryItem;
import cn.walle.system.announcement.query.UserAnnouncementQueryCondition;
import cn.walle.system.announcement.query.UserAnnouncementQueryItem;
import cn.walle.system.announcement.service.OaAnnouncementManager;
import cn.walle.system.announcement.service.OaUserAnnouncementManager;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlNotificationModel;
import cn.walle.system.model.WlUserModel;
import cn.walle.system.service.WlNotificationManager;
import cn.walle.system.service.WlUserManager;
import cn.walle.system.util.GetHttpRequest;


@Service
public class OaAnnouncementManagerImpl extends BaseManagerImpl implements OaAnnouncementManager {

	@Autowired
	private WlNotificationManager notificationManager;
	
	@Autowired
	private WlUserManager wlUserManager;
	@Autowired
	private OaUserAnnouncementManager ezUserAnnouncementManager;
	public OaAnnouncementModel get(String id) {
		return this.dao.get(OaAnnouncementModel.class, id);
	}

	public List<OaAnnouncementModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(OaAnnouncementModel.class, orderBy, pagingInfo);
	}
	
	public List<OaAnnouncementModel> query(String orderBy, PagingInfo pagingInfo){
		Date nowDate = new Date();
		return this.dao.createCommonQuery(OaAnnouncementModel.class)
		.addCondition(Condition.ge("endtime", nowDate))
		.addCondition(Condition.lt("publishtime",nowDate))
		.setOrderBy(orderBy)
		.setPagingInfo(pagingInfo)
		.query();
	}

	public List<OaAnnouncementModel> findByExample(OaAnnouncementModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public OaAnnouncementModel save(OaAnnouncementModel model) {
		OaAnnouncementModel ezAnnouncementModel = this.dao.save(model);
		//saveNotification(ezAnnouncementModel);
		return ezAnnouncementModel;
	}

	public List<OaAnnouncementModel> saveAll(Collection<OaAnnouncementModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(OaAnnouncementModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<OaAnnouncementModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		deleteNotification(id);//删除提醒
		deleteUserAnnousement(id);//删除每个用户的公告
		this.dao.removeByPk(OaAnnouncementModel.class, id);//删除公告
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(OaAnnouncementModel.class, ids);
	}
	
	/**
	 * 公告标志已读
	 */
	public boolean getRead(String userAnouncementId)
	{
		boolean flag=false;
		System.out.println(userAnouncementId+"oaUserAnno中的主键");
		OaUserAnnouncementModel ezUserAnnouncementModel=ezUserAnnouncementManager.get(userAnouncementId);
		System.out.println(ezUserAnnouncementModel.getReadFlag());
		if("1".equals(ezUserAnnouncementModel.getReadFlag())){
			ezUserAnnouncementModel.setReadFlag("2");
			ezUserAnnouncementModel.setReadTime(new Date());
			flag=true;
		}
		ezUserAnnouncementManager.save(ezUserAnnouncementModel);
		return flag;
	}
	/**
	 * 修改后保存
	 */
	public OaAnnouncementModel editSave(OaAnnouncementModel model)
	{
		model.setAddtime(new Date());
		model.setUserId(SessionContextUserEntity.currentUser().getUserId());
		model.setOrgId(SessionContextUserEntity.currentUser().getUserModel().getOrganizeId());
		model.setExamineStatus("1");
		OaAnnouncementModel ezAnnouncementModel = this.dao.save(model);
		saveNotification(ezAnnouncementModel);
		return ezAnnouncementModel;
	}
	/**
	 * 给每个用户添加一条提醒
	 * @param model
	 */
	public void saveNotification(OaAnnouncementModel model){
		
		List <WlUserModel> userList=wlUserManager.getAll(null, null);
		Iterator<WlUserModel> iterator=userList.iterator();  
		
		
		WlNotificationModel notificationModel=new WlNotificationModel();
		//notificationModel.setActionTarget("/oa/announcement/announcement.jsp");
		notificationModel.setSubject("公告："+model.getTitle());
		notificationModel.setSourceId(model.getAnnouncementId());
		notificationModel.setType("notice");
		notificationModel.setContent(model.getTitle());
		notificationModel.setNotificationTime(model.getPublishtime());
		
		while(iterator.hasNext()){
			notificationModel.setUserId(((WlUserModel)iterator.next()).getUserId());
			notificationManager.save(notificationModel);
		}
	}
	/**
	 * 给每个用户添加一条公告
	 * 
	 */
	public void saveUserAnnouncement(OaAnnouncementModel model)
	{
		List <WlUserModel> userList=wlUserManager.getAll(null, null);
		
		OaUserAnnouncementModel ezUserAnnouncementModel= new OaUserAnnouncementModel();
		ezUserAnnouncementModel.setAnnouncementId(model.getAnnouncementId());
		//ezUserAnnouncementModel.setActionTarget("/oa/announcement/announcement.jsp");
		ezUserAnnouncementModel.setReadFlag("1");

		for(int i=0;i<userList.size();i++){
			ezUserAnnouncementModel.setReceiveUserId(userList.get(i).getUserId());
			//后加的组织id
			ezUserAnnouncementModel.setFeedOrgId(userList.get(i).getOrganizeId());
			//后加的反馈状态
			ezUserAnnouncementModel.setFeedStatus("0");
			ezUserAnnouncementManager.save(ezUserAnnouncementModel);
		}
	}
	
	/**
	 * 删除提醒表里的对应的提醒
	 * @param id
	 */
	public void deleteNotification(String id){
		
		WlNotificationModel notificationModel=new WlNotificationModel();
		notificationModel.setSourceId(id);
		notificationModel.setType("notice");
		
		List <WlNotificationModel> notificationList=notificationManager.findByExample(notificationModel, null, null);
		if(notificationList.size()==0){
			return;
		}
		Collection<WlNotificationModel> notificationCollection=(Collection<WlNotificationModel>)notificationList;
		
		notificationManager.removeAll(notificationCollection);
	}
	/**
	 * 删除对应的每个人的公告
	 */
	public void deleteUserAnnousement(String id)
	{
		OaUserAnnouncementModel ezUserAnnouncementModel=new OaUserAnnouncementModel();
		ezUserAnnouncementModel.setAnnouncementId(id);
		List<OaUserAnnouncementModel> userAnnouncementList=ezUserAnnouncementManager.findByExample(ezUserAnnouncementModel, null, null);
		if(userAnnouncementList.size()==0)
		{
			return;
		}
		Collection<OaUserAnnouncementModel> userAnnouncementCollection=(Collection<OaUserAnnouncementModel>)userAnnouncementList;
		ezUserAnnouncementManager.removeAll(userAnnouncementCollection);
	}
	/**
	 * 保存公告到公告表、提醒表、个人公告表中
	 * @param model
	 * @return
	 */
	public void saveAnnouncement(OaAnnouncementModel model) {
	/*	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		
		try {
			model.setAddtime(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		model.setAddtime(new Date());
		model.setUserId(SessionContextUserEntity.currentUser().getUserModel().getUserId());
		model.setOrgId(SessionContextUserEntity.currentUser().getUserModel().getOrganizeId());
		model.setExamineStatus("1");//送审中
		OaAnnouncementModel ezAnnouncementModel = this.save(model);
		//saveNotification(ezAnnouncementModel);
		//saveUserAnnouncement(ezAnnouncementModel);
	} 
	
	public void saveExamineAnnouncement(OaAnnouncementModel model) {
		
		OaAnnouncementModel ezAnnouncementModel=this.get(model.getAnnouncementId());
		ezAnnouncementModel.setChecker(SessionContextUserEntity.currentUser().getUserModel().getUserId());
		if("3".equals(model.getExamineStatus())){
			
			ezAnnouncementModel.setExamineStatus("3");
			ezAnnouncementModel.setSuggestion(model.getSuggestion());
			/*SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			
			try {
				ezAnnouncementModel.setPublishtime(sdf.parse(sdf.format(new Date())));
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
			/*审评者      是否为紧急*/
			ezAnnouncementModel.setIsEmergent(model.getIsEmergent());
			ezAnnouncementModel.setPublishtime(new Date());
		    ezAnnouncementModel = this.save(ezAnnouncementModel);
			saveUserAnnouncement(ezAnnouncementModel);
		}else if("2".equals(model.getExamineStatus())){
			ezAnnouncementModel.setExamineStatus("2");
			ezAnnouncementModel.setSuggestion(model.getSuggestion());
			
			this.save(ezAnnouncementModel);
		}
		
	} 
	
	/**
	 * 获取当前用户的公告
	 */
	public List<UserAnnouncementQueryItem> getUserAnnouncement(String userId) {
		UserAnnouncementQueryCondition con = new UserAnnouncementQueryCondition();
		con.setUserid(userId);
		PagingInfo pagingInfo =new PagingInfo();
		pagingInfo.setPageSize(12);
		List<UserAnnouncementQueryItem> list = this.dao.query(con, UserAnnouncementQueryItem.class, pagingInfo);
		return list;
	}

	/**
	 * 获得公告里面的相关内容，以便在首页上展示
	 */
	public List<ShowAnnouncementQueryItem> findOaAnnouncementMess() {
		ShowAnnouncementQueryCondition con=new ShowAnnouncementQueryCondition();
		return this.dao.query(con, ShowAnnouncementQueryItem.class);
	}
    
	public boolean ishasFile(String uuid){
		OaAnnouncementModel model =this.get(uuid);
		String path=getRealPath(model.getAttachment());
		File file= new File(path);
		if(!file.exists()){
			throw new ApplicationException("文件不存在，请联系管理员！");
		}else
			return true;
	}
	
	@Override
	public FileToDownload downloadFile(String uuid) {
		
	try{	
		OaAnnouncementModel model =this.get(uuid);
		FileToDownload result = new FileToDownload();
		String str[] =model.getAttachment().split("/");
		result.setFileName(str[str.length-1]);
		String path=getRealPath(model.getAttachment());
		result.setContent(FileUtils.openInputStream(new File(path)));
		return result;
	} catch (IOException ex) {
		throw new ApplicationException("文件不存在，请联系管理员！");
	}
	}

	@Override
	public void  deleAnnouncementbyId(String uuid) {
		if(!"".equals(this.get(uuid).getAttachment())&&null!=this.get(uuid).getAttachment()){
			String path=getRealPath(this.get(uuid).getAttachment());
			new File(path).delete();
		}
		this.removeByPk(uuid);
	} 
	
	//返回服务器的相对路径
	private String getRealPath(String AttachmentPath){
		HttpServletRequest request =GetHttpRequest.getHttpRequest();
		String arr[]=AttachmentPath.split("userfiles");
		String filePath=arr[arr.length-1];
		String path =request.getRealPath("")+"\\userfiles"+filePath;
		try {
			path=java.net.URLDecoder.decode(path,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	@Override
	public boolean changeReadFlag(String uuid) {
		OaUserAnnouncementModel userAnnouncementModel= new OaUserAnnouncementModel();
		userAnnouncementModel.setAnnouncementId(uuid);
		userAnnouncementModel.setReceiveUserId(SessionContextUserEntity.currentUser().getUserId());
		List<OaUserAnnouncementModel>list=ezUserAnnouncementManager.findByExample(userAnnouncementModel, null, null);
		return this.getRead(list.get(0).getId());
	}
}
