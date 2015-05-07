package cn.walle.framework.core.support.spring;

import java.beans.PropertyEditorSupport;

import cn.walle.framework.core.util.ParameterUtils;

public class CustomStringArrayEditor extends PropertyEditorSupport {

	public void setAsText(String text) throws IllegalArgumentException {
		setValue(ParameterUtils.splitValues(text));
	}

	public String getAsText() {
		return ParameterUtils.joinValues((String[]) getValue());
	}

}
