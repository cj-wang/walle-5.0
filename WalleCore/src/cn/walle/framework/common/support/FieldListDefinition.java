package cn.walle.framework.common.support;

import java.util.List;

import cn.walle.framework.core.model.BaseObject;

public class FieldListDefinition extends BaseObject {

	List<FieldDefinition> fieldDefinitions;
	
	public FieldListDefinition() {
	}

	public FieldListDefinition(List<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}

	public List<FieldDefinition> getFieldDefinitions() {
		return fieldDefinitions;
	}

	public void setFieldDefinitions(List<FieldDefinition> fieldDefinitions) {
		this.fieldDefinitions = fieldDefinitions;
	}
}
