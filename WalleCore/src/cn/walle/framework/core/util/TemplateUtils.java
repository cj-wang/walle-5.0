package cn.walle.framework.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtils {

	private static Configuration templateConfiguration = new Configuration();
	
	static {
		templateConfiguration.setClassForTemplateLoading(TemplateUtils.class, "/");
		templateConfiguration.setObjectWrapper(new DefaultObjectWrapper());
		templateConfiguration.setDefaultEncoding("UTF-8");
	}

	public static String process(String templateName, Map<String, Object> dataModel) throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		Template template = templateConfiguration.getTemplate(templateName);
		template.process(dataModel, stringWriter);
		return stringWriter.toString();
	}
}
