package cn.walle.framework.tools.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.SqlUtils;

public class UpdateGenerator extends Generator {
	
	public void generateUpdate(String projectName, String packageName, String updateName, String sql,
			Map<String, String> paramTypes) throws Exception {
		if (updateName.toLowerCase().endsWith("update")) {
			updateName = updateName.substring(0, updateName.length() - 6);
		}
		String className = updateName.substring(0, 1).toUpperCase() + updateName.substring(1);
		createDataModel(packageName, className, sql, paramTypes);
		String fileName = SystemConfig.SOURCE_DIR + "/" + packageName.replace('.', '/') + "/" + className + "Update.hbm.xml";
		super.generateFile(projectName, fileName, "/template/update/UpdateHbmXml.ftl");
		super.generateJava(projectName, packageName, className + "UpdateCondition", "/template/update/UpdateCondition.ftl");
	}
	
	private void createDataModel(String packageName, String className, String sql,
			Map<String, String> paramTypes) throws Exception {
		try {
			dataModel.clear();
			dataModel.put("packageName", packageName);
			dataModel.put("className", className);

			sql = SqlUtils.convertParametersToJavaStyle(sql);
			TreeMap<Integer, String> parameters = SqlUtils.getSqlParameters(sql);

			List<Map<String, Object>> params = new ArrayList<Map<String, Object>>();
			boolean hasDateParams = false;
			for (String paramName : new LinkedHashSet<String>(parameters.values())) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("fieldName", paramName);
				param.put("fieldType", paramTypes.get(paramName));
				if (paramTypes.get(paramName).equals("Date")
						|| paramTypes.get(paramName).equals("Date[]")) {
					hasDateParams = true;
				}
				paramName = paramName.substring(0, 1).toUpperCase() + paramName.substring(1);
				param.put("getterMethodName", "get" + paramName);
				param.put("setterMethodName", "set" + paramName);
				params.add(param);
			}
			dataModel.put("params", params);
			dataModel.put("hasDateParams", hasDateParams);
			
			String[] lines = sql.replace("\r", "").split("\n");
			List<Map<String, Object>> sqlLines = new ArrayList<Map<String, Object>>();
			for (String line : lines) {
				Map<String, Object> sqlLine = new HashMap<String, Object>();
				sqlLine.put("line", line);
				sqlLines.add(sqlLine);
			}
			dataModel.put("sqlLines", sqlLines);

		} catch (Exception ex) {
			dataModel.clear();
			throw ex;
		}
	}
	
}
