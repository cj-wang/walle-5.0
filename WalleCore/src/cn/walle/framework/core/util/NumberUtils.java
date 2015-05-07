package cn.walle.framework.core.util;

/**
 * Number utility methods.
 * @author cj
 *
 */
public class NumberUtils {

	public static String toString(Number number) {
		return toString(number, null);
	}
	
	public static String toString(Number number, String defaultValue) {
		if (number == null) {
			return defaultValue;
		}
		return number.toString();
	}

	public static Integer parseInteger(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		return Integer.parseInt(str);
	}
	
	public static Long parseLong(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		return Long.parseLong(str);
	}
	
	public static Double parseDouble(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		return Double.parseDouble(str);
	}
	
}
