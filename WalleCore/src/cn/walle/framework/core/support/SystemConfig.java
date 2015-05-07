package cn.walle.framework.core.support;

import java.util.Properties;

import cn.walle.framework.core.model.BaseObject;

public class SystemConfig extends BaseObject {
	
	public static final String PROJECT_NAME;
	public static final String SOURCE_DIR;
	public static final String CONFIG_DIR;
	public static final String CLASS_DIR;
	public static final String WEB_DIR;
	public static final String BASE_PACKAGE;
	public static final String USER_HOME_DIR;
	
	static {
		Properties configProperties = new Properties();
		try {
			configProperties.load(SystemConfig.class.getResourceAsStream("/systemConfig.properties"));
		} catch (Exception ex) {
		}
		PROJECT_NAME = configProperties.getProperty("PROJECT_NAME", "Walle");
		SOURCE_DIR = configProperties.getProperty("SOURCE_DIR", "src");
		CONFIG_DIR = configProperties.getProperty("CONFIG_DIR", "config");
		CLASS_DIR = configProperties.getProperty("CLASS_DIR", "WebContent/WEB-INF/classes");
		WEB_DIR = configProperties.getProperty("WEB_DIR", "WebContent");
		BASE_PACKAGE = configProperties.getProperty("BASE_PACKAGE", "cn.walle");
		USER_HOME_DIR = configProperties.getProperty("USER_HOME_DIR", System.getProperty("user.home"));
	}

}
