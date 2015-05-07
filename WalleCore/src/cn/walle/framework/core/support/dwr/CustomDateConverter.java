package cn.walle.framework.core.support.dwr;

import org.directwebremoting.convert.DateConverter;
import org.directwebremoting.extend.Converter;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;

public class CustomDateConverter extends DateConverter implements Converter {

	@Override
	public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
		String value = iv.getValue();
		if (value == null || value.trim().length() == 0) {
			return null;
		}
		return super.convertInbound(paramType, iv, inctx);
	}
	
}
