package cn.walle.framework.common.support;

import java.util.List;

public interface SelectCodeDefinitionLoader {

	SelectCodeDefinition getSelectCodeDefinition(String codeType);
	
	List<String> getAllCodeTypes();
	
}
