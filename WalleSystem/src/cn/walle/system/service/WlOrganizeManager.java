package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlOrganizeModel;
import cn.walle.system.query.FindOrgByNameOrManageQueryItem;
import cn.walle.system.query.OrganizeQueryItem;


public interface WlOrganizeManager extends BaseManager {

	WlOrganizeModel get(String id);

	List<WlOrganizeModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlOrganizeModel> findByExample(WlOrganizeModel example, String orderBy, PagingInfo pagingInfo);

	WlOrganizeModel save(WlOrganizeModel model);

	List<WlOrganizeModel> saveAll(Collection<WlOrganizeModel> models);

	void remove(WlOrganizeModel model);

	void removeAll(Collection<WlOrganizeModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

	void delByPk(String id);
	
	WlOrganizeModel saveModel(WlOrganizeModel model);
	
	WlOrganizeModel getOrganize(WlOrganizeModel model);
	
	List<OrganizeQueryItem> getAllOrgByEx();

}
