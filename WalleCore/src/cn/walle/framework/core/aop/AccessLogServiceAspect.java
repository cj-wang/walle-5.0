package cn.walle.framework.core.aop;

import java.util.UUID;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import cn.walle.framework.core.support.ServiceAccessLogger;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetails;
import cn.walle.framework.core.support.springsecurity.SessionContext;

/**
 * Aspect for the Service layer.
 * @author cj
 *
 */
@Aspect
public class AccessLogServiceAspect extends BaseAspect {

	private static int index = 0;
	
	public static final ThreadLocal<Integer> serviceAccessIndex = new ThreadLocal<Integer>();
	
	public static final ThreadLocal<String> serviceAccessIndexUUID = new ThreadLocal<String>();

	public static final ThreadLocal<Integer> invokeLevel = new ThreadLocal<Integer>();
	
	@Autowired(required = false)
	private ServiceAccessLogger serviceAccessLogger;
	
	/**
	 * Aroud *Manager.*()
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* *..service.*Manager.*(..))")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		int currentIndex = index++;
		Integer previousServiceAccessIndex = serviceAccessIndex.get();
		serviceAccessIndex.set(currentIndex);
		String previousServiceAccessIndexUUID = serviceAccessIndexUUID.get();
		serviceAccessIndexUUID.set(UUID.randomUUID().toString());
		invokeLevel.set((invokeLevel.get() == null ? 0 : invokeLevel.get()) + 1);
		String userName;
		AcegiUserDetails user = SessionContext.getUser();
		if (user == null) {
			userName = "N/A";
		} else {
			userName = user.getUsername();
		}
		log.info("[" + currentIndex + "] [user:" + userName + "] " + pjp.getSignature().toLongString());
		long startTime = System.currentTimeMillis();
		Object result = null;
		try {
			try {
				result = pjp.proceed();
				return result;
			} catch (Exception ex) {
				result = ExceptionUtils.getRootCause(ex);
				if (result == null) {
					result = ex;
				}
				throw ex;
			}
		} finally {
			try {
				long endTime = System.currentTimeMillis();
				int timeUsed = (int) (endTime - startTime);
				log.info("[" + currentIndex + "] " + timeUsed + " ms" + (invokeLevel.get() == 1 ? "\n" : ""));
				if (serviceAccessLogger != null) {
					String serviceName = pjp.getSignature().getDeclaringTypeName();
					String methodName = pjp.getSignature().getName();
					Object[] args = pjp.getArgs();
					serviceAccessLogger.logAccess(serviceName, methodName, args, result, timeUsed);
				}
			} finally {
				serviceAccessIndex.set(previousServiceAccessIndex);
				serviceAccessIndexUUID.set(previousServiceAccessIndexUUID);
				invokeLevel.set(invokeLevel.get() - 1);
			}
		}
	}
	
}
