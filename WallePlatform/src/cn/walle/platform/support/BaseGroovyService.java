package cn.walle.platform.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.util.ContextUtils;

public abstract class BaseGroovyService {

    protected final Log log = LogFactory.getLog(getClass());
    protected final ApplicationContext context = ContextUtils.getApplicationContext();

    protected UniversalDao dao = ContextUtils.getBeanOfType(UniversalDao.class);
    protected MongoTemplate mongoTemplate = ContextUtils.getBeanOfType(MongoTemplate.class);

    
    protected void setRollbackOnly() {
    	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

    
}
