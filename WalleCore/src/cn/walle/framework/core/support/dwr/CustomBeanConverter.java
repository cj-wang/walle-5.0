package cn.walle.framework.core.support.dwr;

import groovy.lang.MetaClass;

import java.util.Map;

import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.dwrp.ProtocolConstants;
import org.directwebremoting.dwrp.SimpleOutboundVariable;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;

public class CustomBeanConverter extends BeanConverter {
	
	public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
        String value = iv.getValue();
        if (value.trim().equals(ProtocolConstants.INBOUND_NULL)) {
            return null;
        }
        if (! value.startsWith(ProtocolConstants.INBOUND_MAP_START) || ! value.endsWith(ProtocolConstants.INBOUND_MAP_END)) {
        	return converterManager.convertInbound(String.class, iv, inctx, null);
        }
        if (paramType == Object.class) {
        	return converterManager.convertInbound(Map.class, iv, inctx, inctx.getCurrentTypeHintContext());
        }
		return super.convertInbound(paramType, iv, inctx);
	}

	public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException {
		if (data instanceof MetaClass) {
			return new SimpleOutboundVariable(null, outctx, true);
		}
		return super.convertOutbound(data, outctx);
	}

	
}
