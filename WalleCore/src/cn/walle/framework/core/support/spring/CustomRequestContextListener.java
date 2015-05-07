package cn.walle.framework.core.support.spring;

import java.util.Locale;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cn.walle.framework.core.util.ContextUtils;

public class CustomRequestContextListener implements ServletRequestListener {
	
	public void requestInitialized(ServletRequestEvent requestEvent) {
		if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {
			throw new IllegalArgumentException(
					"Request is not an HttpServletRequest: " + requestEvent.getServletRequest());
		}
		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();
		CookieLocaleResolver cookieLocaleResolver = ContextUtils.getBeanOfType(CookieLocaleResolver.class);
		Locale locale = cookieLocaleResolver.resolveLocale(request);
		LocaleContextHolder.setLocale(locale);
	}

	public void requestDestroyed(ServletRequestEvent requestEvent) {

	}

}
