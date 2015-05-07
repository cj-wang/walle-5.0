package cn.walle.framework.common.service.impl;

import org.springframework.stereotype.Service;

import cn.walle.framework.common.service.FieldDefinitionManager;
import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.framework.core.util.ContextUtils;

@Service
public class FieldDefinitionManagerImpl extends BaseManagerImpl implements FieldDefinitionManager {

	public FieldDefinition getFieldDefinition(String entityName, String fieldName) {
		return getFieldDefinitionByBeanName(entityName + "." + fieldName);
	}

	public FieldDefinition getFieldDefinitionByBeanName(String beanName) {
		return (FieldDefinition) ContextUtils.getBean(beanName);
	}

}
