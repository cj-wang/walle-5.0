package cn.walle.framework.core.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class ContextUtils {
	
	private static ApplicationContext applicationContext;
	
	private static Log log = LogFactory.getLog(ContextUtils.class);

	public static void setApplicationContext(ApplicationContext applicationContext) {
		synchronized (ContextUtils.class) {
			log.debug("setApplicationContext, notifyAll");
			ContextUtils.applicationContext = applicationContext;
			ContextUtils.class.notifyAll();
		}
	}
	
	public static ApplicationContext getApplicationContext() {
		synchronized (ContextUtils.class) {
			while (applicationContext == null) {
				try {
					log.debug("getApplicationContext, wait...");
					ContextUtils.class.wait(60000);
					if (applicationContext == null) {
						log.warn("Have been waiting for ApplicationContext to be set for 1 minute", new Exception());
					}
				} catch (InterruptedException ex) {
					log.debug("getApplicationContext, wait interrupted");
				}
			}
			return applicationContext;
		}
	}
	
	
	
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public static <T> T getBean(String name, Class<T> type) {
		return (T) getApplicationContext().getBean(name);
	}

	public static Class<?> getType(String name) {
		return getApplicationContext().getType(name);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return getApplicationContext().getBeansOfType(type);
	}
	
	public static <T> T getBeanOfType(Class<T> type) {
		Map<String, T> beans = getBeansOfType(type);
		if (beans.size() == 0) {
			throw new NoSuchBeanDefinitionException(type,
					"Unsatisfied dependency of type [" + type + "]: expected at least 1 matching bean");
		}
		if (beans.size() > 1) {
			throw new NoSuchBeanDefinitionException(type,
					"expected single matching bean but found " + beans.size() + ": " + beans.keySet());
		}
		return beans.values().iterator().next();
	}
	
}
