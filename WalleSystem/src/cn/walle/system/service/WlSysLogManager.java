package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSysLogModel;

public interface WlSysLogManager extends BaseManager {

	List<WlSysLogModel> saveAll(Collection<WlSysLogModel> models);
	
	WlSysLogModel get(String id);

	List<WlSysLogModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlSysLogModel> findByExample(WlSysLogModel example, String orderBy, PagingInfo pagingInfo);

	WlSysLogModel save(WlSysLogModel model);

	void remove(WlSysLogModel model);

	void removeAll(Collection<WlSysLogModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void saveSysLog(String operAction,String operOject,String result,String logDesc,String remarks,String state);
	
}
