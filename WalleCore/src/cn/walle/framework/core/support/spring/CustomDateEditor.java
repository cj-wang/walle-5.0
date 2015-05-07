package cn.walle.framework.core.support.spring;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import cn.walle.framework.core.util.DateUtils;

public class CustomDateEditor extends PropertyEditorSupport {

	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.trim().length() == 0) {
			setValue(null);
		}
		try {
			setValue(DateUtils.parse(text));
		} catch (ParseException ex) {
			throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
		}
	}

	public String getAsText() {
		Date date = (Date) getValue();
		if (date == null) {
			return "";
		} else {
			return DateUtils.formatDateTime(date);
		}
	}

}
