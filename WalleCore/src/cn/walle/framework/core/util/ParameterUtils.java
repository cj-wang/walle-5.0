package cn.walle.framework.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class ParameterUtils {

	public static boolean isParamValid(Object value) {
		if (value == null) {
			return false;
		}
		if (value instanceof String) {
			if (((String) value).trim().length() == 0) {
				return false;
			}
		}
		if (value.getClass().isArray()) {
			if (ArrayUtils.getLength(value) == 0 || Array.get(value, 0) == null) {
				return false;
			}
		}
		if (value instanceof Collection) {
			if (((Collection<?>) value).isEmpty() || ((Collection<?>) value).iterator().next() == null) {
				return false;
			}
		}
		return true;
	}
	
	private static final char SEPARATOR = ',';
	private static final String SEPARATOR_STRING = String.valueOf(SEPARATOR);

	public static String joinValues(String[] values) {
		if (values == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			sb.append(SEPARATOR);
			sb.append(value.replace(SEPARATOR_STRING, SEPARATOR_STRING + SEPARATOR_STRING));
		}
		return sb.toString();
	}
	
	public static String[] splitValues(String values) {
		if (values == null) {
			return null;
		}
		List<String> valueList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length(); i++) {
			char c = values.charAt(i);
			if (c == SEPARATOR) {
				if (i + 1 < values.length() && values.charAt(i + 1) == SEPARATOR) {
					sb.append(SEPARATOR);
					i++;
				} else {
					if (sb.length() > 0) {
						valueList.add(sb.toString());
						sb.setLength(0);
					}
				}
			} else {
				sb.append(c);
			}
			if (i == values.length() - 1) {
				if (sb.length() > 0) {
					valueList.add(sb.toString());
				}
			}
		}
		return valueList.toArray(new String[valueList.size()]);
	}
}
