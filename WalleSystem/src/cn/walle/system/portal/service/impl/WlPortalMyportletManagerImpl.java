package cn.walle.system.portal.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.portal.model.PortalInf;
import cn.walle.system.portal.model.WlPortalMyportletModel;
import cn.walle.system.portal.model.WlPortalPortletModel;
import cn.walle.system.portal.query.QueryMyPortalQueryCondition;
import cn.walle.system.portal.query.QueryMyPortalQueryItem;
import cn.walle.system.portal.service.WlPortalMyportletManager;
import cn.walle.system.portal.service.WlPortalPortletManager;

@Service
public class WlPortalMyportletManagerImpl extends BaseManagerImpl implements WlPortalMyportletManager {
	
	@Autowired
	private WlPortalPortletManager wlPortalPortletManager;
	
	public WlPortalMyportletModel get(String id) {
		return this.dao.get(WlPortalMyportletModel.class, id);
	}

	public List<WlPortalMyportletModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlPortalMyportletModel.class, orderBy, pagingInfo);
	}

	public List<WlPortalMyportletModel> findByExample(WlPortalMyportletModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlPortalMyportletModel save(WlPortalMyportletModel model) {
/*		int rowCount = this.dao.getRowCountByExample(model);
		if(rowCount>0){
			return null;
		}*/
		return this.dao.save(model);
	}

	public List<WlPortalMyportletModel> saveAll(Collection<WlPortalMyportletModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlPortalMyportletModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlPortalMyportletModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlPortalMyportletModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlPortalMyportletModel.class, ids);
	}
	
	/**启用按扭
	 * @param portalId
	 * @param userId
	 * @return
	 */
	public WlPortalMyportletModel  savePortletModel (String portalId,String userId){
		
		WlPortalPortletModel portalModel=wlPortalPortletManager.get(portalId);
		WlPortalMyportletModel model= new WlPortalMyportletModel();
		model.setPortletId(portalId);
		model.setUserId(userId);
		model.setHeight(portalModel.getHeight());
		model.setWidth(portalModel.getWidth());
		model.setColumnIndex(portalModel.getColumnIndex());
		model.setSeq(model.getSeq());
		return this.save(model);
	}
	
	/**禁用按钮
	 * @param portalId
	 * @param userId
	 */
	public void  deleUsrportalModel(String portalId,String userId){
		
		WlPortalMyportletModel model= new WlPortalMyportletModel();
		model.setPortletId(portalId);
		model.setUserId(userId);
		List<WlPortalMyportletModel> lists= this.findByExample(model, null, null);
		
		this.removeAll(lists);
		
	}
	
	/**
	 * 获取当前用户的组件
	 */
	public List<WlPortalPortletModel> getMyPortlets() {
		List<WlPortalPortletModel> myPortlets = null;
		//System.out.println("=============================++++++++++++++++" + SessionContextUserEntity.class);
		System.out.println("=============================userId ==== " + SessionContextUserEntity.currentUser().getUserModel().getUserId());
		String curUserId = SessionContextUserEntity.currentUser().getUserId();
		WlPortalMyportletModel wlPortalMyportletModel = new WlPortalMyportletModel();
		wlPortalMyportletModel.setUserId(curUserId);
		//获取客户自定义的portlet
		myPortlets = this.dao.findBySqlCondition(WlPortalPortletModel.class, 
				"portlet_id in "+
				"(select portlet_id from wl_portal_myportlet where user_id=?)",
				new Object[]{curUserId}, "seq asc");
		return myPortlets;
	}

	/**
	 * 删除当前用户已添加的组件中的组件
	 * 根据组件ID删除
	 */
	public void removeMyPortletByPortletId(String portletId) {
		if(portletId==null || "".equals(portletId)){
			throw new ApplicationException("组件ID为空");
		}
		WlPortalMyportletModel wlPortalMyportletModel = new WlPortalMyportletModel();
		wlPortalMyportletModel.setUserId(SessionContextUserEntity.currentUser().getUserModel().getUserId());
		wlPortalMyportletModel.setPortletId(portletId);
		List<WlPortalMyportletModel> list = this.dao.findByExample(wlPortalMyportletModel);
		if(list!=null && list.size()>0){
			this.dao.removeAll(list);
		}
	}

	/**
	 * 将系统默认的组件添加到我的组件库中
	 */
	public void addDefaultPortletToMyPortlet() {
		List<WlPortalPortletModel> myPortlets = null;
		WlPortalPortletModel portlet = new WlPortalPortletModel();
		portlet.setDefaultDisplay("1");//查询默认显示的portlet
		myPortlets = wlPortalPortletManager.findByExample(portlet, "seq asc", null);
		//将默认组件添加到当前用户的组件库中
		for (WlPortalPortletModel wlPortalPortletModel : myPortlets) {
			WlPortalMyportletModel model = new WlPortalMyportletModel();
			model.setUserId(SessionContextUserEntity.currentUser().getUserModel().getUserId());
			model.setPortletId(wlPortalPortletModel.getPortletId());
			this.save(model);
		}
	}

	/**
	 * 获取当前用户有权限的组件
	 */
	public List<WlPortalPortletModel> getMyPrivPortlets() {
		List<WlPortalPortletModel> list = this.dao.findBySqlCondition(WlPortalPortletModel.class, 
				"portlet_id in "+
				"(select portlet_id from wl_portal_role_portlet where role_id in "+
					"(select role_id from wl_user_role where user_id=?)"+
				")",
				new Object[]{SessionContextUserEntity.currentUser().getUserModel().getUserId()}, "seq asc");
		return list;
	}
	public WlPortalMyportletModel saveById(String portletId) {
		String userId= SessionContextUserEntity.currentUser().getUserModel().getUserId().toString();
		WlPortalMyportletModel myPortletModel=new WlPortalMyportletModel();
		myPortletModel.setPortletId(portletId);
		myPortletModel.setUserId(userId);
		return this.dao.save(myPortletModel);
	}
	
	
	/**判断是否用户已经启用该组件
	 * @param portletId
	 * @return 
	 */
	public Map<String, Boolean> isHasPortal(String portletId){
		 
		Map<String, Boolean> map= new HashMap<String, Boolean>();
		List<QueryMyPortalQueryItem> list= this.queryMyPortal(); 
		for (QueryMyPortalQueryItem queryMyPortalQueryItem : list) {
			if(queryMyPortalQueryItem.getPortletId().equals(portletId)){
				map.put(portletId, true);
				break;
			}else
				continue;
			
		}
		return map;
		
	}
	
	
	/**
	 * 查询我的组件
	 * @return
	 */
	public List<QueryMyPortalQueryItem> queryMyPortal(){
		String userId= SessionContextUserEntity.currentUser().getUserId();
		QueryMyPortalQueryCondition condition = new QueryMyPortalQueryCondition();
		condition.setUserId(userId);
		return this.dao.query(condition, QueryMyPortalQueryItem.class);
	}
	
	/**
	 * 修改组件位置信息
	 * @param portalInfs
	 */
	public void updatePortalInf(List<PortalInf> portalInfs){
		if(portalInfs == null || portalInfs.size() == 0){
			throw new ApplicationException("无任何组件信息！");
		}
		WlPortalMyportletModel model = new WlPortalMyportletModel();
		for(PortalInf info : portalInfs){
			model = this.get(info.getMyportletId());
			model.setColumnIndex(info.getColumnIndex());
			model.setSeq(info.getSeq());
			this.dao.save(model);
		}
	}
}
