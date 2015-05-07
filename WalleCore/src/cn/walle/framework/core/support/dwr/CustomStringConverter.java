package cn.walle.framework.core.support.dwr;

import java.util.List;
import java.util.Map;

import org.directwebremoting.convert.StringConverter;
import org.directwebremoting.dwrp.ProtocolConstants;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;

public class CustomStringConverter extends StringConverter {
	
	private ConverterManager converterManager;
	
	public void setConverterManager(ConverterManager converterManager) {
		this.converterManager = converterManager;
	}

	public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
        String value = iv.getValue();
        if (value.trim().equals(ProtocolConstants.INBOUND_NULL)) {
            return null;
        }
        if (value.startsWith(ProtocolConstants.INBOUND_MAP_START) && value.endsWith(ProtocolConstants.INBOUND_MAP_END)) {
        	return converterManager.convertInbound(Map.class, iv, inctx, inctx.getCurrentTypeHintContext());
        }
        if (value.startsWith(ProtocolConstants.INBOUND_ARRAY_START) && value.endsWith(ProtocolConstants.INBOUND_ARRAY_END)) {
        	return converterManager.convertInbound(List.class, iv, inctx, inctx.getCurrentTypeHintContext());
        }
		return super.convertInbound(paramType, iv, inctx);
	}

}
