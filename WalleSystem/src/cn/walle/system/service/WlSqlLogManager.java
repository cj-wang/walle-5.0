package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSqlLogModel;

public interface WlSqlLogManager extends BaseManager {

	WlSqlLogModel get(String id);

	List<WlSqlLogModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlSqlLogModel> findByExample(WlSqlLogModel example, String orderBy, PagingInfo pagingInfo);

	WlSqlLogModel save(WlSqlLogModel model);

	List<WlSqlLogModel> saveAll(Collection<WlSqlLogModel> models);

	void remove(WlSqlLogModel model);

	void removeAll(Collection<WlSqlLogModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);
	
	void deleAll();

}
