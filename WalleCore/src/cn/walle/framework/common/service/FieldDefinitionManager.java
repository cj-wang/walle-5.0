package cn.walle.framework.common.service;

import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.core.service.BaseManager;

public interface FieldDefinitionManager extends BaseManager {
	
	FieldDefinition getFieldDefinition(String entityName, String fieldName);
	
	FieldDefinition getFieldDefinitionByBeanName(String beanName);
	
}
