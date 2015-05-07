package cn.walle.framework.core.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtils {

	private static MessageSource messageSource = ContextUtils.getBeanOfType(MessageSource.class);
	
	public static String getMessage(String code, Object... args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

	public static String getMessageDefault(String code, String defaultMessage, Object... args) {
		return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

	public static String getMessage(Locale locale, String code, Object... args) {
		return messageSource.getMessage(code, args, locale);
    }

	public static String getMessageDefault(Locale locale, String code, String defaultMessage, Object... args) {
		return messageSource.getMessage(code, args, defaultMessage, locale);
    }
	
}
