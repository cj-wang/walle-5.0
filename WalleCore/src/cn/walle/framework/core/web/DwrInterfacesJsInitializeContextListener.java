package cn.walle.framework.core.web;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.util.ContextUtils;

/**
 * 应用初始化时加载，在服务器的应用主目录生成dwr客户端js文件，包含所有manager的调用接口。
 * 文件名为 /dwr_interfaces.js
 * @author cj
 *
 */
public class DwrInterfacesJsInitializeContextListener implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event) {
		try {
			String servletContextName = event.getServletContext().getServletContextName();
			String realPath = event.getServletContext().getRealPath("/");
			if (realPath == null) {
				realPath = event.getServletContext().getResource("/").getPath();
			}
			if (realPath == null) {
				realPath = "";
			}
			String fileSeparator = System.getProperty("file.separator");
			if (! realPath.endsWith(fileSeparator)) {
				realPath = realPath + fileSeparator;
			}

			StringBuilder sb = new StringBuilder();
			sb.append("if (dwr == null) var dwr = {};\n");
			sb.append("if (dwr.engine == null) dwr.engine = {};\n");
			sb.append("if (DWREngine == null) var DWREngine = dwr.engine;\n");
			sb.append("var contextPath;\n");
			sb.append("var DWR_SERVICE_PATH;\n");
			sb.append("if (! DWR_SERVICE_PATH) {\n");
			sb.append("\tDWR_SERVICE_PATH = (contextPath ? contextPath : '/" + servletContextName + "') + '/dwr';\n");
			sb.append("}\n");
			
			Map<String, BaseManager> managers = ContextUtils.getBeansOfType(BaseManager.class);
			for (String beanName : managers.keySet()) {
				if (! beanName.endsWith("Manager")) {
					continue;
				}
				String managerName = beanName.substring(0, 1).toUpperCase() + beanName.substring(1);
				sb.append("if (" + managerName + " == null) var " + managerName + " = {};\n");
				
				Class<?> managerInterface = null;
				for (Class<?> interfaceClass : managers.get(beanName).getClass().getInterfaces()) {
					if (BaseManager.class.isAssignableFrom(interfaceClass)
							&& BaseManager.class != interfaceClass) {
						managerInterface = interfaceClass;
						break;
					}
				}
				if (managerInterface == null) {
					continue;
				}
				Method[] methods = managerInterface.getMethods();
				for (Method method : methods) {
					String methodName = method.getName();
					int argsCount = method.getParameterTypes().length;
					StringBuilder argsSb = new StringBuilder();
					for (int i = 0; i < argsCount; i++) {
						argsSb.append("p" + i + ", ");
					}
					sb.append(managerName + "." + methodName + " = function("
							+ argsSb + "callback) {dwr.engine._execute(DWR_SERVICE_PATH, '"
							+ managerName + "', '" + methodName + "', " + argsSb + "callback);}\n");
				}
			}
			FileUtils.writeStringToFile(new File(realPath + "dwr_interfaces.js"), sb.toString(), "UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
