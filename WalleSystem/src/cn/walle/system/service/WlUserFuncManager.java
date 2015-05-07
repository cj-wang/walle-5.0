package cn.walle.system.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlUserFuncModel;

public interface WlUserFuncManager extends BaseManager {


	WlUserFuncModel get(String id);

	List<WlUserFuncModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlUserFuncModel> findByExample(WlUserFuncModel example, String orderBy, PagingInfo pagingInfo);

	WlUserFuncModel save(WlUserFuncModel model);

	List<WlUserFuncModel> saveAll(Collection<WlUserFuncModel> models);

	void remove(WlUserFuncModel model);

	void removeAll(Collection<WlUserFuncModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	ArrayList getUserFunc(String userId);
	
	void saveFunc(String userId,List<String> funcIds);

	void setPersonalHomePage(String id);

	void saveFuncsForUser(String userId, String[] funcIds);

	void saveUsersForFunc(String funcId, String[] checkedUserIds, String[] uncheckedUserIds);
}
