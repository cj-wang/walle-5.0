package cn.walle.system.announcement.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.announcement.model.OaUserAnnouncementModel;
import cn.walle.system.announcement.query.UserAnnouncementQueryCondition;
import cn.walle.system.announcement.query.UserAnnouncementQueryItem;
import cn.walle.system.announcement.service.OaUserAnnouncementManager;
import cn.walle.system.entity.SessionContextUserEntity;


@Service
public class OaUserAnnouncementManagerImpl extends BaseManagerImpl implements OaUserAnnouncementManager {

	public OaUserAnnouncementModel get(String id) {
		return this.dao.get(OaUserAnnouncementModel.class, id);
	}

	public List<OaUserAnnouncementModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(OaUserAnnouncementModel.class, orderBy, pagingInfo);
	}

	public List<OaUserAnnouncementModel> findByExample(OaUserAnnouncementModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public OaUserAnnouncementModel save(OaUserAnnouncementModel model) {
		return this.dao.save(model);
	}

	public List<OaUserAnnouncementModel> saveAll(Collection<OaUserAnnouncementModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(OaUserAnnouncementModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<OaUserAnnouncementModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(OaUserAnnouncementModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(OaUserAnnouncementModel.class, ids);
	}
	
	public List<UserAnnouncementQueryItem> getAnnouncementByParam(UserAnnouncementQueryCondition param,PagingInfo pagingInfo){
		return  this.dao.query(param, UserAnnouncementQueryItem.class, pagingInfo);
	}

	/**
	 * 根据通告的主键和当前用户的id在[OA_USER_ANNOUNCEMENT]表中寻找对应的相关信息
	 */
	public OaUserAnnouncementModel findInfo(String announcementId, String userId) {
		OaUserAnnouncementModel mo=new OaUserAnnouncementModel();
		mo.setAnnouncementId(announcementId);
		mo.setReceiveUserId(userId);
		List<OaUserAnnouncementModel> model=this.dao.findByExample(mo);
		if(model.size()==0){
			return null;
		}
		return model.get(0);
	}

	@Override
	public boolean feedBackSave(String announcementId,String feedInfo) {
		
		OaUserAnnouncementModel mo=new OaUserAnnouncementModel();
		mo.setAnnouncementId(announcementId);
		mo.setReceiveUserId(SessionContextUserEntity.currentUser().getUserId());
		List<OaUserAnnouncementModel> model=this.dao.findByExample(mo);
		if(model.size()!=0){
			if("1".equals(model.get(0).getFeedStatus())){
				throw new ApplicationException("此通告您已经反馈过、无需此操作!");
			}else{
				model.get(0).setFeedInfo(feedInfo);
				model.get(0).setFeedOrgId(SessionContextUserEntity.currentUser().getUserModel().getOrganizeId());
				model.get(0).setFeedStatus("1");
				this.save(model.get(0));
				return true;
			}
		}else{
			throw new ApplicationException("此通告还未审核不能此操作!");
		}
		
	}
}
