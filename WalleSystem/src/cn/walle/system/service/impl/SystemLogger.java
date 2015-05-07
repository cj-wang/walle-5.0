package cn.walle.system.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.walle.framework.core.aop.AccessLogServiceAspect;
import cn.walle.framework.core.support.ServiceAccessLogger;
import cn.walle.framework.core.support.SqlLogger;
import cn.walle.system.entity.SessionContextUserEntity;
import cn.walle.system.model.WlServiceLogModel;
import cn.walle.system.model.WlSqlLogModel;
import cn.walle.system.service.WlServiceLogManager;
import cn.walle.system.service.WlSqlLogManager;


//wcj: 改在 applicationContext-aop.xml 里定义该bean, 方便关闭日志功能
//@Service
public class SystemLogger implements ServiceAccessLogger, SqlLogger {

	@Autowired 
	WlServiceLogManager serviceLogManager;
	@Autowired
	WlSqlLogManager SqlLogManager;
	public void logAccess(String serviceName, String methodName, Object[] args, Object result, int timeUsed) {
		
			//UniversalDao dao = ContextUtils.getBeanOfType(UniversalDao.class);
		   if(serviceName.endsWith("WlServiceLogManager")){
			   return;
		   }
			WlServiceLogModel serviceLogModel = new WlServiceLogModel();
			if (SessionContextUserEntity.currentUser() != null) {
				serviceLogModel.setUserId(SessionContextUserEntity.currentUser().getUserModel().getUserId());
			}
			HttpServletRequest request = getHttpRequest();
			if (request != null) {
				serviceLogModel.setServerName(request.getServerName() + ":" + request.getServerPort());
				serviceLogModel.setRemoteAddress(request.getRemoteAddr());
				serviceLogModel.setRemoteUser(request.getRemoteUser());
			}
			serviceLogModel.setServiceName(serviceName);
			serviceLogModel.setMethodName(methodName);
			String argsString = StringUtils.join(args, ", ");
			if (argsString.getBytes().length > 1000) {
				argsString = new String(argsString.getBytes(), 0, 1000);
			}
			serviceLogModel.setArgs(argsString);
			String resultString = "" + result;
			if (resultString.getBytes().length > 1000) {
				resultString = new String(resultString.getBytes(), 0, 1000);
			}
			serviceLogModel.setResult(resultString);
			serviceLogModel.setLogTime(new Date());
			serviceLogModel.setTimeUsed((long) timeUsed);
			serviceLogModel.setAccessIndex(AccessLogServiceAspect.serviceAccessIndexUUID.get());
			serviceLogManager.save(serviceLogModel);
		
		
	}

	public void logSql(String sql, int timeUsed) {
		//UniversalDao dao = ContextUtils.getBeanOfType(UniversalDao.class);
		WlSqlLogModel sqlLogModel = new WlSqlLogModel();
		if (SessionContextUserEntity.currentUser() != null) {
			sqlLogModel.setUserId(SessionContextUserEntity.currentUser().getUserModel().getUserId());
		}
		if (sql.getBytes().length > 1000) {
			sql = new String(sql.getBytes(), 0, 1000);
		}
		sqlLogModel.setSqlStatement(sql);
		sqlLogModel.setLogTime(new Date());
		sqlLogModel.setTimeUsed((long) timeUsed);
		sqlLogModel.setServiceAccessIndex(AccessLogServiceAspect.serviceAccessIndexUUID.get());
		SqlLogManager.save(sqlLogModel);
	}
	
	private static HttpServletRequest getHttpRequest()
	  {
	    ServletRequestAttributes a;
	    if ((a = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes()) == null)
	    {
	      return null;
	    }
	    return a.getRequest();
	  }
}
