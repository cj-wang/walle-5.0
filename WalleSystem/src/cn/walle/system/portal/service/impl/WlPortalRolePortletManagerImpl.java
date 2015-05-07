package cn.walle.system.portal.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlUserRoleModel;
import cn.walle.system.portal.model.WlPortalMyportletModel;
import cn.walle.system.portal.model.WlPortalPortletModel;
import cn.walle.system.portal.model.WlPortalRolePortletModel;
import cn.walle.system.portal.query.HavePortalRoleQueryItem;
import cn.walle.system.portal.query.NoPortalRoleQueryItem;
import cn.walle.system.portal.service.WlPortalMyportletManager;
import cn.walle.system.portal.service.WlPortalPortletManager;
import cn.walle.system.portal.service.WlPortalRolePortletManager;
import cn.walle.system.service.WlUserRoleManager;

@Service
public class WlPortalRolePortletManagerImpl extends BaseManagerImpl implements WlPortalRolePortletManager {

	@Autowired
	private WlPortalPortletManager portalManager;
	@Autowired
	private WlPortalMyportletManager myPortalManager;
	@Autowired
	private WlUserRoleManager userRoleManger;
	
	public WlPortalRolePortletModel get(String id) {
		return this.dao.get(WlPortalRolePortletModel.class, id);
	}

	public List<WlPortalRolePortletModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlPortalRolePortletModel.class, orderBy, pagingInfo);
	}

	public List<WlPortalRolePortletModel> findByExample(WlPortalRolePortletModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlPortalRolePortletModel save(WlPortalRolePortletModel model) {
		return this.dao.save(model);
	}

	public List<WlPortalRolePortletModel> saveAll(Collection<WlPortalRolePortletModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlPortalRolePortletModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlPortalRolePortletModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlPortalRolePortletModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlPortalRolePortletModel.class, ids);
	}
	
	/**
	 * 授权
	 * @param portletId
	 * @param roles
	 */
	public void savePortletRoles(String portletId, Collection<NoPortalRoleQueryItem> noPortalRoleQueryItems) {
		WlPortalPortletModel portal = portalManager.get(portletId);
		List<WlPortalRolePortletModel> list = new ArrayList<WlPortalRolePortletModel>();
		WlPortalRolePortletModel example = new WlPortalRolePortletModel();
		example.setPortletId(portletId);
		List<WlPortalRolePortletModel> listE = new ArrayList<WlPortalRolePortletModel>();
		
		WlUserRoleModel userRoleExample = new WlUserRoleModel();
		List<WlUserRoleModel> listRoleUser = new ArrayList<WlUserRoleModel>();
		
		WlPortalMyportletModel myPortalExample = new WlPortalMyportletModel();
		List<WlPortalMyportletModel> myPortalListE = new ArrayList<WlPortalMyportletModel>();
		for (NoPortalRoleQueryItem item : noPortalRoleQueryItems) {
			example.setRoleId(item.getRoleId());
			listE = this.findByExample(example, null, null);
			if(listE.size() > 0){
				continue;
			}else{
				if(portal.getDefaultDisplay().equals("1")){//显示
					userRoleExample.setRoleId(item.getRoleId());
					listRoleUser = userRoleManger.findByExample(userRoleExample, null, null);
					for(WlUserRoleModel roleUser : listRoleUser){
						myPortalExample.setPortletId(portletId);
						myPortalExample.setUserId(roleUser.getUserId());
						myPortalListE = myPortalManager.findByExample(myPortalExample, null, null);
						if(myPortalListE.size() > 0){
							continue;
						}else{
							WlPortalMyportletModel myPortal = new WlPortalMyportletModel();
							myPortal.setColumnIndex(portal.getColumnIndex());
							myPortal.setCreateTime(new Date());
							myPortal.setCreator(SessionContextUserEntity.currentUser().getUserId());
							myPortal.setHeight(portal.getHeight());
							myPortal.setPortletId(portletId);
							myPortal.setSeq(portal.getSeq());
							myPortal.setUserId(roleUser.getUserId());
							myPortal.setWidth(portal.getWidth());
							myPortalManager.save(myPortal);
						}
					}
				}
				WlPortalRolePortletModel userRoleModel = new WlPortalRolePortletModel();
				userRoleModel.setPortletId(portletId);
				userRoleModel.setRoleId(item.getRoleId());
				list.add(userRoleModel);
			}
		}
		if(list.size()>0){
			this.saveAll(list);
		}
	}
	
	/**
	 * 解除授权
	 * @param portletRoleIds
	 */
	public void removePortletRoles(String portletId, Collection<HavePortalRoleQueryItem> havePortalRoleQueryItem){
		WlPortalRolePortletModel model = new WlPortalRolePortletModel();
		model.setPortletId(portletId);
		WlPortalMyportletModel example = new WlPortalMyportletModel();
		List<WlPortalMyportletModel> list = new ArrayList<WlPortalMyportletModel>();
		
		WlUserRoleModel userRoleExample = new WlUserRoleModel();
		List<WlUserRoleModel> listRoleUser = new ArrayList<WlUserRoleModel>();
		
		for(HavePortalRoleQueryItem item : havePortalRoleQueryItem){
			userRoleExample.setRoleId(item.getRoleId());
			listRoleUser = userRoleManger.findByExample(userRoleExample, null, null);
			//删除myportal中的
			for(WlUserRoleModel roleUser : listRoleUser){
				example.setPortletId(model.getPortletId());
				example.setUserId(roleUser.getUserId());
				list = myPortalManager.findByExample(example, null, null);
				if(list.size()>0){
					myPortalManager.removeAll(list);
				}
			}
			//删除rolePortal
			model.setRoleId(item.getRoleId());
			List<WlPortalRolePortletModel> rolePortletModels =this.findByExample(model,null, null);
			for (WlPortalRolePortletModel wlPortalRolePortletModel : rolePortletModels) {
				this.remove(wlPortalRolePortletModel);
			}
			
		}
	    
	}
	

}
