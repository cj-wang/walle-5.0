package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlFunctionModel;
import cn.walle.system.query.GetFirstFunctionInfoQueryItem;

public interface WlFunctionManager extends BaseManager {

	List<WlFunctionModel> saveAll(Collection<WlFunctionModel> models);

	WlFunctionModel get(String id);

	List<WlFunctionModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlFunctionModel> findByExample(WlFunctionModel example, String orderBy, PagingInfo pagingInfo);

	WlFunctionModel save(WlFunctionModel model);

	void remove(WlFunctionModel model);

	void removeAll(Collection<WlFunctionModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void saveModel(WlFunctionModel model);
	
	void delByPk(String id);
	
	String getFunctionNameByUri(String uri);
	
	void setSysHomePage(String id);
	
	GetFirstFunctionInfoQueryItem getFirstSubFunctionInfo(String funcId);
	
}
