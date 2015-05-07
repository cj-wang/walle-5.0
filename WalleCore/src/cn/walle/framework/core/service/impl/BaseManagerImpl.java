package cn.walle.framework.core.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.service.BaseManager;

/**
 * Base class for all Manager implements.
 * @author cj
 *
 */
public class BaseManagerImpl implements BaseManager {
    /**
     * Log variable for all child classes.
     */
    protected final Log log = LogFactory.getLog(getClass());

    
    @Autowired
    protected UniversalDao dao;

    protected void setRollbackOnly() {
    	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
