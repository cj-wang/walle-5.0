package cn.walle.framework.common.support;

import java.text.ParseException;
import java.util.Date;

import cn.walle.framework.core.model.BaseObject;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.util.DateUtils;

public class QueryField extends BaseObject {
	
	public static final String FIELD_TYPE_STRING = "String";
	public static final String FIELD_TYPE_INT = "int";
	public static final String FIELD_TYPE_LONG = "long";
	public static final String FIELD_TYPE_DOUBLE = "double";
	public static final String FIELD_TYPE_DATE = "Date";
	
	public static final String FIELD_TYPE_STRING_ARRAY = "String[]";
	public static final String FIELD_TYPE_INT_ARRAY = "int[]";
	
	public static final String FIELD_TYPE_SESSION_CONTEXT_PROPERTY = "session";

	private String fieldName;
	private String fieldType;
	private String fieldStringValue;
	private Object fieldValue;
	private String operator;

	public QueryField() {
	}
	
	public QueryField(String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public QueryField(String fieldName, String operator, Object fieldValue) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.fieldValue = fieldValue;
	}
	
	public QueryField(String fieldName, String operator, String fieldType, String fieldStringValue) {
		this.fieldName = fieldName;
		this.operator = operator;
		this.fieldType = fieldType;
		this.fieldStringValue = fieldStringValue;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldStringValue() {
		return fieldStringValue;
	}
	public void setFieldStringValue(String fieldStringValue) {
		this.fieldStringValue = fieldStringValue;
	}
	public Object getFieldValue() {
		if (fieldValue != null) {
			return fieldValue;
		}
		if (fieldStringValue == null) {
			return null;
		}
		if (FIELD_TYPE_STRING.equals(fieldType)) {
			return fieldStringValue;
		} else if (FIELD_TYPE_INT.equals(fieldType)) {
			return Integer.parseInt(fieldStringValue);
		} else if (FIELD_TYPE_LONG.equals(fieldType)) {
			return Long.parseLong(fieldStringValue);
		} else if (FIELD_TYPE_DOUBLE.equals(fieldType)) {
			return Double.parseDouble(fieldStringValue);
		} else if (FIELD_TYPE_DATE.equals(fieldType)) {
			if (fieldStringValue.matches("\\d+")) {
				return new Date(Long.parseLong(fieldStringValue));
			} else {
				try {
					return DateUtils.parse(fieldStringValue);
				} catch (ParseException pex) {
					throw new RuntimeException(pex);
				}
			}
		} else if (FIELD_TYPE_STRING_ARRAY.equals(fieldType)) {
			return fieldStringValue.split(",");
		} else if (FIELD_TYPE_INT_ARRAY.equals(fieldType)) {
			String[] stringValues = fieldStringValue.split(",");
			int[] intValues = new int[stringValues.length];
			for (int i = 0; i < stringValues.length; i++) {
				intValues[i] = Integer.parseInt(stringValues[i]);
			}
			return intValues;
		} else if (FIELD_TYPE_SESSION_CONTEXT_PROPERTY.equals(fieldType)) {
			if (SessionContext.getUser() != null) {
				return new CustomBeanWrapper(SessionContext.getUser()).getPropertyValueRecursively(fieldStringValue);
			} else {
				return null;
			}
		} else {
			return fieldStringValue;
		}
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
