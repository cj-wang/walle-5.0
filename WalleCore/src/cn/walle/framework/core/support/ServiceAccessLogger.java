package cn.walle.framework.core.support;

public interface ServiceAccessLogger {
	
	void logAccess(String serviceName, String methodName, Object[] args, Object result, int timeUsed);

}
