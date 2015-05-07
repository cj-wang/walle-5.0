package cn.walle.demo.spdc.service;

import java.util.Collection;

import cn.walle.demo.spdc.entity.LogisticsOrderEntity;
import cn.walle.framework.core.service.BaseManager;

public interface LogisticsOrderManager extends BaseManager {

	LogisticsOrderEntity saveAndPostToCm(LogisticsOrderEntity orderEntity);
	
	LogisticsOrderEntity get(String id);

	LogisticsOrderEntity getByOrderNo(String orderNo);
	
	LogisticsOrderEntity save(LogisticsOrderEntity orderEntity);

	void removeByPk(String id);

	void removeAllByPk(Collection<String> ids);

	void removeByOrderNo(String orderNo);
	
	void removeAllByOrderNo(Collection<String> orderNos);
}
