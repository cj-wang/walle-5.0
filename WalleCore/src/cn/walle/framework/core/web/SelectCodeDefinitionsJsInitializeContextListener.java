package cn.walle.framework.core.web;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import cn.walle.framework.common.service.SelectCodeManager;
import cn.walle.framework.common.support.QueryField;
import cn.walle.framework.common.support.SelectCodeDefinition;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.JSONDataUtils;

/**
 * 应用初始化时加载，在服务器的应用主目录生成
 * @author cj
 *
 */
public class SelectCodeDefinitionsJsInitializeContextListener implements ServletContextListener {
	
	private static String realPath;
	
	public static void generateFiles() {
		try {
			Properties p = new Properties();
			p.load(SelectCodeDefinitionsJsInitializeContextListener.class.getResourceAsStream("/basenames.properties"));
			String[] locales = p.getProperty("locales").split(",");
			
			for (String locale : locales) {
				LocaleContextHolder.setLocale(StringUtils.parseLocaleString(locale));

				Map<String, SelectCodeDefinition> allSelectCodeDefinitions = ContextUtils.getBeanOfType(SelectCodeManager.class)
						.getAllSelectCodeDefinitions();
				for (String codeType : allSelectCodeDefinitions.keySet()) {
					SelectCodeDefinition def = allSelectCodeDefinitions.get(codeType);
					if (def.getQueryFields() != null) {
						for (QueryField queryField : def.getQueryFields()) {
							if (QueryField.FIELD_TYPE_SESSION_CONTEXT_PROPERTY.equals(queryField.getFieldType())) {
								allSelectCodeDefinitions.put(codeType, null);
								break;
							}
						}
					}
				}
				String json = JSONDataUtils.buildJSONString("SELECT_CODE_DEFINITIONS", allSelectCodeDefinitions, true);
				
				StringBuilder sb = new StringBuilder();
				sb.append("var SELECT_CODE_DEFINITIONS = ");
				sb.append(json.substring(28, json.length() - 1));
				sb.append(";");

				FileUtils.writeStringToFile(new File(realPath + "select_code_definitions_" + locale + ".js"), sb.toString(), "UTF-8");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void contextInitialized(ServletContextEvent event) {
		try {
			realPath = event.getServletContext().getRealPath("/");
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
			
			generateFiles();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
