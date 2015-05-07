package cn.walle.framework.core.support.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.walle.framework.core.util.ContextUtils;

public class ApplicationContextRegister implements ApplicationContextAware {

	private Log log = LogFactory.getLog(getClass());
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ContextUtils.setApplicationContext(applicationContext);
		log.debug("ApplicationContext registed");
	}

}
