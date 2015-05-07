package cn.walle.framework.common.support;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.i18n.LocaleContextHolder;

import cn.walle.framework.core.model.BaseObject;
import cn.walle.framework.core.util.MessageUtils;

public class FieldDefinition extends BaseObject implements BeanNameAware {

	public static final String FIELD_TYPE_STRING = "string";
	public static final String FIELD_TYPE_TEXT = "text";
	public static final String FIELD_TYPE_BYTES = "bytes";
	public static final String FIELD_TYPE_INT = "int";
	public static final String FIELD_TYPE_DOUBLE = "double";
	public static final String FIELD_TYPE_DATE = "date";
	public static final String FIELD_TYPE_DATETIME = "datetime";
	public static final String FIELD_TYPE_TIME = "time";
	public static final String FIELD_TYPE_MONTH = "month";
	public static final String FIELD_TYPE_SELECTCODE = "selectCode.";
	
	private String beanName;
	
	private String fieldName;
	private String label;
	private String fieldType;
	private boolean sortable;
	private boolean nullable;
	private int length;
	private int precision;
	private int scale;
	private int width;
	
	private Map<Locale, String> localedLabelCache = new HashMap<Locale, String>();
	
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanName() {
		return beanName;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLabel() {
		Locale locale = LocaleContextHolder.getLocale();
		if (! localedLabelCache.containsKey(locale)) {
			synchronized (localedLabelCache) {
				if (! localedLabelCache.containsKey(locale)) {
					String resultLabel = beanName == null ? label : MessageUtils.getMessageDefault(beanName, label);
					localedLabelCache.put(locale, resultLabel);
					return resultLabel;
				}
			}
		}
		return localedLabelCache.get(locale);
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public boolean isSortable() {
		return sortable;
	}
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isSelectCode() {
		if (fieldType == null) {
			return false;
		}
		return fieldType.startsWith(FIELD_TYPE_SELECTCODE);
	}
	
	public String getSelectCodeType() {
		if (isSelectCode()) {
			return fieldType.substring(FIELD_TYPE_SELECTCODE.length());
		} else {
			return null;
		}
	}
	
	public void setSelectCodeType(String codeType) {
		this.fieldType = FIELD_TYPE_SELECTCODE + codeType;
	}

}
