package cn.walle.system.portal.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.portal.model.WlPortalPortletModel;

public interface WlPortalPortletManager extends BaseManager {

	WlPortalPortletModel get(String id);

	List<WlPortalPortletModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlPortalPortletModel> findByExample(WlPortalPortletModel example, String orderBy, PagingInfo pagingInfo);

	WlPortalPortletModel save(WlPortalPortletModel model);

	List<WlPortalPortletModel> saveAll(Collection<WlPortalPortletModel> models);

	void remove(WlPortalPortletModel model);

	void removeAll(Collection<WlPortalPortletModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void disablePortlet(String id);
	

}
