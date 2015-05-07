package cn.walle.framework.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utility methods.
 * @author cj
 *
 */
public class DateUtils {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

	public static Date parse(String source) throws ParseException {
		if (source == null || source.trim().length() == 0) {
			return null;
		}
		if (source.length() <= dateTimeFormat.toPattern().length()
				&& source.length() >= dateTimeFormat.toPattern().length() - 5) {
			try {
				return dateTimeFormat.parse(source);
			} catch (ParseException ex) {
			}
		}
		if (source.length() <= dateFormat.toPattern().length()
				&& source.length() >= dateFormat.toPattern().length() - 2) {
			try {
				return dateFormat.parse(source);
			} catch (ParseException ex) {
			}
		}
		if (source.length() <= monthFormat.toPattern().length()
				&& source.length() >= monthFormat.toPattern().length() - 1) {
			try {
				return monthFormat.parse(source);
			} catch (ParseException ex) {
			}
		}
		return dateTimeFormat.parse(source);
	}
	
    public static String format(Date date) {
		String result = formatDateTime(date);
		if (result.endsWith(" 00:00:00")) {
			result = result.substring(0, 10);
		}
		return result;
    }

    /**
	 * Parse the Date using pattern "yyyy-MM-dd"
	 * @param source
	 * @return
	 * @throws ParseException
	 */
    public static Date parseDate(String source) throws ParseException {
		if (source == null || source.trim().length() == 0) {
			return null;
		}
    	return dateFormat.parse(source);
    }

    /**
     * Format the Date using pattern "yyyy-MM-dd"
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
		return formatDate(date, null);
    }

    public static String formatDate(Date date, String defaultValue) {
		if (date == null) {
			return defaultValue;
		}
    	return dateFormat.format(date);
    }

    /**
	 * Parse the Date using pattern "yyyy-MM-dd HH:mm:ss"
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String source) throws ParseException {
		if (source == null || source.trim().length() == 0) {
			return null;
		}
    	return dateTimeFormat.parse(source);
    }

    /**
	 * Format the Date using pattern "yyyy-MM-dd HH:mm:ss"
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
		return formatDateTime(date, null);
    }

    public static String formatDateTime(Date date, String defaultValue) {
		if (date == null) {
			return defaultValue;
		}
    	return dateTimeFormat.format(date);
    }

    /**
	 * Parse the Date using pattern "yyyy-MM"
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parseMonth(String source) throws ParseException {
		if (source == null || source.trim().length() == 0) {
			return null;
		}
    	return monthFormat.parse(source);
    }

    /**
	 * Format the Date using pattern "yyyy-MM"
     * @param date
     * @return
     */
    public static String formatMonth(Date date) {
		return formatMonth(date, null);
    }

    public static String formatMonth(Date date, String defaultValue) {
		if (date == null) {
			return defaultValue;
		}
    	return monthFormat.format(date);
    }

}
