package cn.walle.framework.core.support;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import cn.walle.framework.core.support.spring.MultiDataSource;
import cn.walle.framework.core.util.ContextUtils;

/**
 * Template class that simplifies running a TransactionCallback in NEW transaction.
 * Also support using a specific dataSourceName in the new transaction.
 * 
 * @author cj
 *
 */
public class NewTransactionTemplate extends TransactionTemplate {

	public NewTransactionTemplate() {
		setTransactionManager(ContextUtils.getBeanOfType(PlatformTransactionManager.class));
		setPropagationBehavior(PROPAGATION_REQUIRES_NEW);
	}
	
	/**
	 * Run a TransactionCallback in a new transaction, using the specific dataSourceName.
	 * @param dataSourceName
	 * @param action
	 * @return
	 * @throws TransactionException
	 */
	public Object execute(String dataSourceName, TransactionCallback action) throws TransactionException {
		try {
			MultiDataSource.useDataSource(dataSourceName);
			return super.execute(action);
		} finally {
			MultiDataSource.useDefaultDataSource();
		}
	}

}
