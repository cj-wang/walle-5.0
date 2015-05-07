package cn.walle.framework.core.web;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 应用初始化时加载，根据后台国际化资源文件，在服务器的应用主目录生成ext js国际化文件。
 * 生成的文件为 /i18n_en.js ， /i18n_zh_CN.js等
 * @author cj
 *
 */
public class I18nJsInitializeContextListener implements ServletContextListener {
	
	private static final String BASE_NAMESPACE = "i18n";

	public void contextInitialized(ServletContextEvent event) {
		try {
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

			Properties p = new Properties();
			p.load(getClass().getResourceAsStream("/basenames.properties"));
			String[] basenames = p.getProperty("basenames").split(",");
			String[] locales = p.getProperty("locales").split(",");
			
			for (String locale : locales) {
				StringBuilder sb = new StringBuilder();
				sb.append("var " + BASE_NAMESPACE + "={};\n");
				Set<String> namespaces = new HashSet<String>();
				for (String basename : basenames) {
					ResourceBundle sr = ResourceBundle.getBundle(basename, org.springframework.util.StringUtils.parseLocaleString(locale));
					Enumeration<String> keys = sr.getKeys();
					while (keys.hasMoreElements()) {
						String key = keys.nextElement();
						if (key.lastIndexOf(".") >= 0) {
							String namespace = key.substring(0, key.lastIndexOf("."));
							if (! namespaces.contains(namespace)) {
								String[] namespaceParts = namespace.split("\\.");
								for (int i = 1; i <= namespaceParts.length; i++) {
									String ns = StringUtils.join(namespaceParts, ".", 0, i);
									if (! namespaces.contains(ns)) {
										namespaces.add(ns);
										sb.append(BASE_NAMESPACE + "." + ns + "={};\n");
									}
								}
							}
						}
						String value = sr.getString(key);
						value = value.replace("'", "\\'");
						sb.append(BASE_NAMESPACE + "." + key + "='" + value + "';\n");
					}
				}
				FileUtils.writeStringToFile(new File(realPath + "i18n_" + locale + ".js"), sb.toString(), "UTF-8");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
