package cn.walle.system.service;

import java.util.Collection;
import java.util.List;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.system.model.WlSysCodeTypeModel;

public interface WlSysCodeTypeManager extends BaseManager {

	List<WlSysCodeTypeModel> saveAll(Collection<WlSysCodeTypeModel> models);
	
	WlSysCodeTypeModel get(String id);

	List<WlSysCodeTypeModel> getAll(String orderBy, PagingInfo pagingInfo);

	List<WlSysCodeTypeModel> findByExample(WlSysCodeTypeModel example, String orderBy, PagingInfo pagingInfo);

	WlSysCodeTypeModel save(WlSysCodeTypeModel model);

	void remove(WlSysCodeTypeModel model);

	void removeAll(Collection<WlSysCodeTypeModel> models);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

	String saveModel(WlSysCodeTypeModel model);
	
	String delByPk(String id);

}
