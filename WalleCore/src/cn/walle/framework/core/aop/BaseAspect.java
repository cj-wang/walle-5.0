package cn.walle.framework.core.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;

/**
 * Base class for all Aspects.
 * @author cj
 *
 */
public class BaseAspect implements Ordered {

    /**
     * Log variable for all child classes.
     */
    protected final Log log = LogFactory.getLog(getClass());


	private int order;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
