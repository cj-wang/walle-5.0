package cn.walle.framework.core.support.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;

import cn.walle.framework.core.util.ClassUtils;

/**
 * Bean Factory to scan all the Hibernate Entity classes.
 * Used by org.springframework.orm.hibernate4.annotation.AnnotationSessionFactoryBean
 * to obtain annotatedClasses.
 * @author cj
 *
 */
public class HibernateEntityClassesFactoryBean implements FactoryBean {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Return all the Hibernate Entity classes.
	 */
	public Object getObject() throws Exception {
		
		log.debug("Find hibernate entity classes");

		List<Class<?>> result = new ArrayList<Class<?>>();
		result.addAll(ClassUtils.getModelClasses());
		result.addAll(ClassUtils.getQueryItemClasses());
		if (log.isDebugEnabled()) {
			log.debug("Add hibernate entity classs: " + result);
		}

		return result.toArray();
	}

	/**
	 * Return the type of Class[]
	 */
	public Class<?> getObjectType() {
		return new Class[]{}.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

}
