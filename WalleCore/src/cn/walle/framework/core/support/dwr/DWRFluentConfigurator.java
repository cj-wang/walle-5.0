package cn.walle.framework.core.support.dwr;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.fluent.FluentConfigurator;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.util.ContextUtils;

/**
 * 自动把所有Manager配置成DWR的Creator
 * 在web.xml中，dwr-invoker的参数customConfigurator，配置成本类名
 * @author cj
 *
 */
public class DWRFluentConfigurator extends FluentConfigurator {
	
	private Log log = LogFactory.getLog(getClass());

	private Pattern methodPattern = Pattern.compile("\\p{Upper}\\w*\\.\\w+\\(.*\\)");
	private Pattern classNamePattern = Pattern.compile("(\\w+\\.)+\\p{Upper}\\w*");

	@Override
	public void configure() {
		Set<String> signatureImports = new HashSet<String>();
		List<String> signatureMethods = new ArrayList<String>();
		
		Map<String, BaseManager> managers = ContextUtils.getBeansOfType(BaseManager.class);
		for (String beanName : managers.keySet()) {
			if (! beanName.endsWith("Manager")) {
				continue;
			}
			String scriptName = beanName.substring(0, 1).toUpperCase() + beanName.substring(1);
			FluentConfigurator fluentConfigurator = withCreator("spring", scriptName).addParam("beanName", beanName);
			
			BaseManager manager = managers.get(beanName);
			Class<?>[] interfaces = manager.getClass().getInterfaces();
			Class<?> managerInterface = null;
			for (Class<?> interfaceClass : interfaces) {
				if (BaseManager.class.isAssignableFrom(interfaceClass)
						&& BaseManager.class != interfaceClass) {
					managerInterface = interfaceClass;
					break;
				}
			}
			if (managerInterface == null) { 
				continue;
			}
			
			for (Method method : managerInterface.getMethods()) {
				if (! BaseManager.class.isAssignableFrom(method.getDeclaringClass())) {
					continue;
				}
				fluentConfigurator.include(method.getName());
				boolean needSignature = false;
				FOR_PARAMETER_TYPES:
				for (Type type : method.getGenericParameterTypes()) {
					if (type instanceof ParameterizedType) {
						for (Type actualType : ((ParameterizedType) type).getActualTypeArguments()) {
							if (! (actualType instanceof Class)) {
								needSignature = false;
								break FOR_PARAMETER_TYPES;
							}
						}
						needSignature = true;
					}
				}
				if (needSignature) {
					String methodSignature = method.toGenericString();
					Matcher methodMatcher = methodPattern.matcher(methodSignature);
					if (methodMatcher.find()) {
						methodSignature = methodMatcher.group();
					}
					StringBuffer sb = new StringBuffer();
					Matcher classNameMatcher = classNamePattern.matcher(methodSignature);
					while (classNameMatcher.find()) {
						String className = classNameMatcher.group();
						if (! className.startsWith("java.")) {
							signatureImports.add("import " + className + ";");
						}
						classNameMatcher.appendReplacement(sb, className.substring(className.lastIndexOf(".") + 1));
					}
					classNameMatcher.appendTail(sb);
					sb.append(";");
					signatureMethods.add(sb.toString());
				}
			}
		}

		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("DWR signatures:\n");
			sb.append(StringUtils.join(signatureImports, "\n"));
			sb.append("\n");
			sb.append(StringUtils.join(signatureMethods, "\n"));
			log.debug(sb);
		}
		FluentConfigurator fluentConfigurator = withSignature();
		for (String line : signatureImports) {
			fluentConfigurator.addLine(line);
		}
		for (String line : signatureMethods) {
			fluentConfigurator.addLine(line);
		}
	}

}
