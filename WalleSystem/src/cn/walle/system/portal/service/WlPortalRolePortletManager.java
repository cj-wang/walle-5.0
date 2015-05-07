package cn.walle.system.portal.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.portal.model.WlPortalRolePortletModel;
import cn.walle.system.portal.query.HavePortalRoleQueryItem;
import cn.walle.system.portal.query.NoPortalRoleQueryItem;

public interface WlPortalRolePortletManager extends BaseManager {

	WlPortalRolePortletModel get(String id);

	List<WlPortalRolePortletModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlPortalRolePortletModel> findByExample(WlPortalRolePortletModel example, String orderBy, PagingInfo pagingInfo);

	WlPortalRolePortletModel save(WlPortalRolePortletModel model);

	List<WlPortalRolePortletModel> saveAll(Collection<WlPortalRolePortletModel> models);

	void remove(WlPortalRolePortletModel model);

	void removeAll(Collection<WlPortalRolePortletModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	/**
	 * 授权
	 * @param portletId
	 * @param roles
	 */
	public void savePortletRoles(String portletId, Collection<NoPortalRoleQueryItem> noPortalRoleQueryItems);
	
	/**
	 * @param portletId
	 * @param roles
	 */
	public void removePortletRoles(String portletId, Collection<HavePortalRoleQueryItem> havePortalRoleQueryItem);

}
