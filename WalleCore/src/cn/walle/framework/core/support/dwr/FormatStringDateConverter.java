package cn.walle.framework.core.support.dwr;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.directwebremoting.convert.BaseV20Converter;
import org.directwebremoting.dwrp.SimpleOutboundVariable;
import org.directwebremoting.extend.Converter;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.util.JavascriptUtil;
import org.directwebremoting.util.LocalUtil;

import cn.walle.framework.core.util.DateUtils;

public class FormatStringDateConverter extends BaseV20Converter implements Converter {

	public Object convertInbound(Class paramType, InboundVariable data, InboundContext inctx) throws MarshallException {
		try {
	        String value = data.getValue();
	        value = LocalUtil.decode(value);
			return DateUtils.parse(value);
		} catch (ParseException pex) {
			throw new MarshallException(paramType, pex);
		}
	}

	public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException {
		Date date;
		if (data instanceof Date) {
			date = (Date) data;
		} else if (data instanceof Calendar) {
			date = ((Calendar) data).getTime();
		} else {
			throw new MarshallException(data.getClass());
		}
		String value = DateUtils.format(date);
        String escaped = JavascriptUtil.escapeJavaScript(value);
        return new SimpleOutboundVariable('\"' + escaped + '\"', outctx, true);
	}

}
