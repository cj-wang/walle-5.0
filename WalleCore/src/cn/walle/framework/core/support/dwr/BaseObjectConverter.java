package cn.walle.framework.core.support.dwr;

import java.util.HashMap;
import java.util.Map;

import org.directwebremoting.dwrp.ParseUtil;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.hibernate.H3BeanConverter;
import org.directwebremoting.util.LocalUtil;

import cn.walle.framework.core.model.BaseModelClass;
import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.util.ClassUtils;

public class BaseObjectConverter extends H3BeanConverter {

	private Map<String, Class<?>> modelClasses;
	private Map<String, Class<?>> queryItemClasses;

	public BaseObjectConverter() throws Exception {
		modelClasses = new HashMap<String, Class<?>>();
		for (Class<?> modelClass : ClassUtils.getModelClasses()) {
			modelClasses.put(modelClass.getSimpleName(), modelClass);
		}
		queryItemClasses = new HashMap<String, Class<?>>();
		for (Class<?> queryItemClass : ClassUtils.getQueryItemClasses()) {
			queryItemClasses.put(queryItemClass.getSimpleName(), queryItemClass);
		}
	}
	
	public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
		if (BaseModelClass.class.equals(paramType)) {
	        String value = iv.getValue();
	        value = value.substring(1, value.length() - 1);
            Map tokens = extractInboundTokens(paramType, value);
            String val = (String) tokens.get("_class");
            if (val != null) {
                Class propType = String.class;
                String[] split = ParseUtil.splitInbound(val);
                String splitValue = split[LocalUtil.INBOUND_INDEX_VALUE];
                String splitType = split[LocalUtil.INBOUND_INDEX_TYPE];
                InboundVariable nested = new InboundVariable(iv.getLookup(), null, splitType, splitValue);
                String _class = (String) converterManager.convertInbound(propType, nested, inctx, null);
                try {
					paramType = Class.forName(_class);
				} catch (ClassNotFoundException e) {
	        		if (_class.endsWith("Model")) {
	        			paramType = modelClasses.get(_class);
	        		} else if (_class.endsWith("Query")) {
	        			paramType = queryItemClasses.get(_class + "Item");
	        		} else {
	        			paramType = DynamicModelClass.class;
	        		}
				}
            }
		}
		return super.convertInbound(paramType, iv, inctx);
	}

}
