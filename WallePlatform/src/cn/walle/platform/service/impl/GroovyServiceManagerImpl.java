package cn.walle.platform.service.impl;

import groovy.lang.GroovyClassLoader;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.service.impl.BaseManagerImpl;
import cn.walle.platform.model.WpDynamicServiceModel;
import cn.walle.platform.query.DuplicatedDynamicServiceQueryCondition;
import cn.walle.platform.query.DuplicatedDynamicServiceQueryItem;
import cn.walle.platform.service.GroovyServiceManager;

@Service
public class GroovyServiceManagerImpl extends BaseManagerImpl implements
		GroovyServiceManager {

    @Autowired
    protected UniversalDao dao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

	@Override
	public List<WpDynamicServiceModel> saveTreeData(List<WpDynamicServiceModel> models) {
		for (WpDynamicServiceModel model : models) {
			this.compile(model.getServiceName(), model.getContent());
		}
		List<WpDynamicServiceModel> result = this.dao.saveTreeData(models, "uuid", "parentUuid");
		List<DuplicatedDynamicServiceQueryItem> duplicatedServices =
				this.dao.query(new DuplicatedDynamicServiceQueryCondition(), DuplicatedDynamicServiceQueryItem.class);
		if (duplicatedServices.size() > 0) {
			List<String> duplicatedServiceNames = new ArrayList<String>();
			for (DuplicatedDynamicServiceQueryItem duplicatedService : duplicatedServices) {
				duplicatedServiceNames.add(duplicatedService.getServiceName());
			}
			throw new ApplicationException("deplicated service name : " + StringUtils.join(duplicatedServiceNames, ","));
		}
		return result;
	}
	
	@Override
	public Class<?> compile(String serviceName, String text) {
		return groovyClassLoader.parseClass(text);
	}
	
	@Override
	public Object invoke(String serviceName, String methodName, Object parameters) {
		WpDynamicServiceModel example = new WpDynamicServiceModel();
		example.setServiceName(serviceName);
		example.setContentType("groovy");
		List<WpDynamicServiceModel> models = this.dao.findByExample(example);
		if (models.size() == 0) {
			throw new SystemException("service \"" + serviceName + "\" not found");
		}
		String text = models.get(0).getContent();
		
		try {
			Class<?> c = new GroovyClassLoader().parseClass(text);
			Method m = c.getMethod(methodName, Object.class);
			Object service = c.newInstance();
			return m.invoke(service, parameters);
		} catch (Exception ex) {
			throw new SystemException(ex);
		}
	}


}
