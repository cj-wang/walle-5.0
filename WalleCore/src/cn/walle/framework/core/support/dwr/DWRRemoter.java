package cn.walle.framework.core.support.dwr;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.directwebremoting.extend.Call;
import org.directwebremoting.extend.Reply;
import org.directwebremoting.impl.DefaultRemoter;

/**
 * @author cs
 * 
 */
public class DWRRemoter extends DefaultRemoter {

	@Override
	public Reply execute(Call call) {
		Reply reply = super.execute(call);
		if (reply.getThrowable() != null) {
			Throwable ex = ExceptionUtils.getRootCause(reply.getThrowable());
			if (ex == null) {
				ex = reply.getThrowable();
			}
			return new Reply(reply.getCallId(), reply.getReply(), ex);
		}
		return reply;
	}

}
