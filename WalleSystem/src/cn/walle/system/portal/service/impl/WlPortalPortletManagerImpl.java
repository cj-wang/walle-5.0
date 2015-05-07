package cn.walle.system.portal.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.portal.model.WlPortalMyportletModel;
import cn.walle.system.portal.model.WlPortalPortletModel;
import cn.walle.system.portal.model.WlPortalRolePortletModel;
import cn.walle.system.portal.service.WlPortalMyportletManager;
import cn.walle.system.portal.service.WlPortalPortletManager;
import cn.walle.system.portal.service.WlPortalRolePortletManager;

@Service
public class WlPortalPortletManagerImpl extends BaseManagerImpl implements WlPortalPortletManager {
	
	@Autowired
	WlPortalRolePortletManager wlPortalRolePortletManager;
	
	@Autowired
	WlPortalMyportletManager wlPortalMyportletManager;
	
	public WlPortalPortletModel get(String id) {
		return this.dao.get(WlPortalPortletModel.class, id);
	}

	public List<WlPortalPortletModel> getAll(String orderBy, PagingInfo pagingInfo) {
		return this.dao.getAll(WlPortalPortletModel.class, orderBy, pagingInfo);
	}

	public List<WlPortalPortletModel> findByExample(WlPortalPortletModel example, String orderBy, PagingInfo pagingInfo) {
		return this.dao.findByExample(example, orderBy, pagingInfo);
	}

	public WlPortalPortletModel save(WlPortalPortletModel model) {
		return this.dao.save(model);
	}

	public List<WlPortalPortletModel> saveAll(Collection<WlPortalPortletModel> models) {
		return this.dao.saveAll(models);
	}

	public void remove(WlPortalPortletModel model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<WlPortalPortletModel> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(String id) {
		this.dao.removeByPk(WlPortalPortletModel.class, id);
	}

	public void removeAllByPk(Collection<String> ids) {
		this.dao.removeAllByPk(WlPortalPortletModel.class, ids);
	}

	/**
	 * 禁用portlet，逻辑删除
	 * 删除portlet同时，删除role_portlet记录以及my_portlet记录
	 */
	public void disablePortlet(String portletId) {
		WlPortalPortletModel model = new WlPortalPortletModel();
		model.setPortletId(portletId);
		model.setStatus("F");//禁用状态
		this.save(model);
		
		//删除role_portlet记录
		WlPortalRolePortletModel wlPortalRolePortletModel = new WlPortalRolePortletModel();
		wlPortalRolePortletModel.setPortletId(portletId);
		List<WlPortalRolePortletModel> rolePortletModels = wlPortalRolePortletManager.findByExample(wlPortalRolePortletModel, null, null);
		wlPortalRolePortletManager.removeAll(rolePortletModels);
		
		//删除my_portlet中所有记录
		WlPortalMyportletModel wlPortalMyportletModel = new WlPortalMyportletModel();
		wlPortalMyportletModel.setPortletId(portletId);
		List<WlPortalMyportletModel> myPortletModels = wlPortalMyportletManager.findByExample(wlPortalMyportletModel, null, null);
		wlPortalMyportletManager.removeAll(myPortletModels);
	}


}
