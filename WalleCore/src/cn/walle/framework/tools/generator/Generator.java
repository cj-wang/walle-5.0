package cn.walle.framework.tools.generator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.TemplateUtils;
import cn.walle.framework.tools.util.UIUtils;

public abstract class Generator {
	
	protected final Log log = LogFactory.getLog(getClass());

	protected Map<String, Object> dataModel = new HashMap<String, Object>();
	
	private boolean promptOverwrite = true;
	
	private int count = 0;
	
	protected void generateFile(String projectName, String fileName, String templateName) throws Exception {
		log.info("Generating " + fileName + "...");
		File file = new File("../" + projectName + "/" + fileName);
		if (file.exists()) {
			if (promptOverwrite) {
				if (UIUtils.showConfirmYesNo("Confirm", "File " + fileName + " already exists, overwrite?")
						!= JOptionPane.YES_OPTION) {
					log.info("Exists. Skipped");
					return;
				}
			}
			FileUtils.copyFile(file, new File("bak/" + fileName + "." + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
			log.info("Exists. Overwrite");
		}
		
		String content = TemplateUtils.process(templateName, dataModel);
		
		FileUtils.writeStringToFile(file, content, "UTF-8");

		log.info("Done");
		this.count++;
	}
	
	protected void generateJava(String projectName, String packageName, String className, String templateName) throws Exception {
		String fullClassName = packageName + "." + className;
		String fileName = SystemConfig.SOURCE_DIR + "/" + fullClassName.replace(".", "/") + ".java";

		this.generateFile(projectName, fileName, templateName);
	}
	
	
	public void reset() {
		this.count = 0;
		this.dataModel.clear();
	}
	
	public int getCount() {
		return this.count;
	}

	public Map<String, Object> getDataModel() {
		return dataModel;
	}

	public void setDataModel(Map<String, Object> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isPromptOverwrite() {
		return promptOverwrite;
	}

	public void setPromptOverwrite(boolean promptOverwrite) {
		this.promptOverwrite = promptOverwrite;
	}
}
