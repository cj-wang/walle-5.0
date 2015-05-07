package cn.walle.system.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.CommonQueryManager;
import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlNotificationHisModel;
import cn.walle.system.model.WlNotificationModel;
import cn.walle.system.service.WlNotificationHisManager;
import cn.walle.system.service.WlNotificationManager;


@Service
public class WlNotificationManagerImpl extends BaseManagerImpl implements WlNotificationManager {

	@Autowired
	private WlNotificationHisManager notificationHisManager;
	
	@Autowired
	private CommonQueryManager commonQueryManager;
	
	public WlNotificationModel get(String id) {
		return this.dao.get(WlNotificationModel.class, id);
	}

	public List<WlNotificationModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlNotificationModel.class, orderBy, pagingInfo);
	}

	public List<WlNotificationModel> findByExample(WlNotificationModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlNotificationModel save(WlNotificationModel model) {
		Date date=new Date();
		model.setCreateTime(date);
		return this.dao.save(model);
	}

	public List<WlNotificationModel> saveAll(Collection<WlNotificationModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlNotificationModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlNotificationModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlNotificationModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlNotificationModel.class, ids);
	}

	/**
	 * 获取提醒记录列表
	 */
	public List<WlNotificationModel> getCurrentUserNotice() {
		String userId=SessionContextUserEntity.currentUser().getUserModel().getUserId();
		if("".equals(userId)||null==userId){
			throw new ApplicationException("用户ID为空");
		}
		List<WlNotificationModel> list=
			this.dao.createCommonQuery(WlNotificationModel.class)
			.addCondition(Condition.le("notification_time", new Date()))
			.addCondition(Condition.eq("user_id", userId))
			.query();
		return list;
	}

	/**
	 * 将已读提示转到历史记录当中
	 */
	public void moveNotificationToHis(String notificationId) {
		WlNotificationHisModel notificationHisModel=new WlNotificationHisModel();
		WlNotificationModel notificationModel=new WlNotificationModel();
		
		notificationModel=this.get(notificationId);
		
		Date date=new Date();
		notificationHisModel.setUserId(notificationModel.getUserId());
		notificationHisModel.setContent(notificationModel.getContent());
		notificationHisModel.setSubject(notificationModel.getSubject());
		notificationHisModel.setType(notificationModel.getType());
		notificationHisModel.setSourceId(notificationModel.getSourceId());
		notificationHisModel.setActionTarget(notificationModel.getActionTarget());
		notificationHisModel.setReadTime(date);
		notificationHisModel.setCreateTime(notificationModel.getCreateTime());
		
		notificationHisManager.save(notificationHisModel);
		
		this.removeByPk(notificationId);
		
	}

	/**
	 * 获取当前用户所拥有的提醒数目
	 */
	public int getNotificationNum() {
		String userId=SessionContextUserEntity.currentUser().getUserModel().getUserId();
		
		if("".equals(userId)||null==userId){
			throw new ApplicationException("用户ID为空");
		}
		String sqlCondition = "user_id=? and notification_time<=?";
		return this.dao.getRowCountByExample(new WlNotificationModel(), sqlCondition, new Object[]{userId, new Date()});
	}

}
