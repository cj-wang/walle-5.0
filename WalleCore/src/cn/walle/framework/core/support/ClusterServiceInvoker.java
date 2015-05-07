package cn.walle.framework.core.support;

import java.lang.reflect.Method;

import org.json.JSONObject;

import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.JSONDataUtils;

public class ClusterServiceInvoker extends MulticastService {
	
	private static final String INVOKE_SERVICE_INSTRUCTION = "#INVOKE_SERVICE#";

	public void invoke(String serviceName, String methodName, Object... parameters) {
		try {
			JSONObject requestJSONObject = new JSONObject();
			requestJSONObject.put("serviceName", serviceName);
			requestJSONObject.put("methodName", methodName);
			JSONObject parametersJSONObject = new JSONObject();
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					parametersJSONObject.put("arg" + i, parameters[i]);
				}
			}
			requestJSONObject.put("parameters", parametersJSONObject);
			String requestJSONString = requestJSONObject.toString();
			super.sendMessage(INVOKE_SERVICE_INSTRUCTION + requestJSONString);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	protected void messageReceived(String message) {
		super.messageReceived(message);
		if (message.startsWith(INVOKE_SERVICE_INSTRUCTION)) {
			try {
		 		String requestJSONString = message.substring(INVOKE_SERVICE_INSTRUCTION.length());
		 		JSONObject requestJSONObject = new JSONObject(requestJSONString);
		 		String serviceName = requestJSONObject.getString("serviceName");
		 		String methodName = requestJSONObject.getString("methodName");
		 		JSONObject parametersJSONObject = requestJSONObject.getJSONObject("parameters");
		 		int parametersCount = parametersJSONObject.length();
		 		
		 		Object bean = ContextUtils.getBean(serviceName);
		 		Class<?> beanClass = ContextUtils.getType(serviceName);
		 		Method beanMethod = null;
		 		for (Method method : beanClass.getMethods()) {
					if (method.getName().equals(methodName) && method.getParameterTypes().length == parametersCount) {
						beanMethod = method;
						break;
					}
				}
		 		
		 		if (beanMethod == null) {
		 			throw new RuntimeException("Method " + methodName + " not found in service " + serviceName + ", or parameters not matched");
		 		}
		 		
		 		Object[] parameters = JSONDataUtils.parseParametersJSONObject(beanClass, beanMethod, parametersJSONObject);
		 		beanMethod.invoke(bean, parameters);
		 		
			} catch (Exception ex) {
				log.error("ClusterServiceInvoker error", ex);
			}
		}
	}
	
}
