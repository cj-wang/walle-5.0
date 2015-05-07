package cn.walle.framework.core.support.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import cn.walle.framework.core.util.ClassUtils;

/**
 * Bean Factory to scan all the Hibernate mapping files.
 * Used by org.springframework.orm.hibernate4.annotation.AnnotationSessionFactoryBean
 * to obtain mappings.
 * @author cj
 *
 */
public class HibernateMappingLocationsFactoryBean implements FactoryBean {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Return all the Hibernate Entity classes.
	 */
	public Object getObject() throws Exception {
		
		log.debug("Find hibernate mapping files");

		List<Resource> result = ClassUtils.getHibernateMappingLocations();
		if (log.isDebugEnabled()) {
			log.debug("Add hibernate mapping files: " + result);
		}

		return result.toArray();
	}

	/**
	 * Return the type of Class[]
	 */
	public Class<?> getObjectType() {
		return new Resource[]{}.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

}
