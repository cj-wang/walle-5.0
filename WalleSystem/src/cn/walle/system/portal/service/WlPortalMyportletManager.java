package cn.walle.system.portal.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.portal.model.PortalInf;
import cn.walle.system.portal.model.WlPortalMyportletModel;
import cn.walle.system.portal.model.WlPortalPortletModel;
import cn.walle.system.portal.query.QueryMyPortalQueryItem;

public interface WlPortalMyportletManager extends BaseManager {

	WlPortalMyportletModel get(String id);

	List<WlPortalMyportletModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlPortalMyportletModel> findByExample(WlPortalMyportletModel example, String orderBy, PagingInfo pagingInfo);

	WlPortalMyportletModel save(WlPortalMyportletModel model);

	List<WlPortalMyportletModel> saveAll(Collection<WlPortalMyportletModel> models);

	void remove(WlPortalMyportletModel model);

	void removeAll(Collection<WlPortalMyportletModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void removeMyPortletByPortletId(String portletId);
	
	List<WlPortalPortletModel> getMyPortlets();
	
	List<WlPortalPortletModel> getMyPrivPortlets();
	
	void addDefaultPortletToMyPortlet();
	
	WlPortalMyportletModel saveById(String portletId);
	
	/**
	 * 查询我的组件
	 * @return
	 */
	public List<QueryMyPortalQueryItem> queryMyPortal();
	
	/**
	 * 修改组件位置信息
	 * @param portalInfs
	 */
	public void updatePortalInf(List<PortalInf> portalInfs);
	
	/**判断是否用户已经启用该组件
	 * @param portletId
	 * @return
	 */
	Map<String, Boolean> isHasPortal(String portletId);

	
	/**启用按扭
	 * @param portalId
	 * @param userId
	 * @return
	 */
	public WlPortalMyportletModel  savePortletModel (String portalId,String userId);
	/**禁用按钮
	 * @param portalId
	 * @param userId
	 */
	public void  deleUsrportalModel(String portalId,String userId);
}
