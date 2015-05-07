package cn.walle.framework.tools.generator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.SqlUtils;
import cn.walle.framework.tools.util.ConnectionUtils;

public class QueryGenerator extends Generator {
	
	public void generateQuery(String projectName, String packageName, String queryName, String sql,
			Map<String, String> paramTypes, Map<String, Boolean> paramDynamics) throws Exception {
		if (queryName.toLowerCase().endsWith("query")) {
			queryName = queryName.substring(0, queryName.length() - 5);
		}
		String className = queryName.substring(0, 1).toUpperCase() + queryName.substring(1);
		createDataModel(packageName, className, sql, paramTypes, paramDynamics);
		String fileName = SystemConfig.SOURCE_DIR + "/" + packageName.replace('.', '/') + "/" + className + "Query.hbm.xml";
		super.generateFile(projectName, fileName, "/template/query/QueryHbmXml.ftl");
		super.generateJava(projectName, packageName, className + "QueryItem", "/template/query/QueryItem.ftl");
		super.generateJava(projectName, packageName, className + "QueryCondition", "/template/query/QueryCondition.ftl");
	}
	
	private void createDataModel(String packageName, String className, String sql,
			Map<String, String> paramTypes, Map<String, Boolean> paramDynamics) throws Exception {
		try {
			dataModel.clear();
			dataModel.put("packageName", packageName);
			dataModel.put("className", className);
			
			Connection conn = ConnectionUtils.getConnection();

			sql = SqlUtils.convertParametersToJavaStyle(sql);
			TreeMap<Integer, String> parameters = SqlUtils.getSqlParameters(sql);

			Statement stmt = null;
			ResultSet rs = null;
			List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
			Set<String> columnNames = new HashSet<String>();
			boolean hasDateColumns = false;
			boolean hasLobColumns = false;
			try {
				String runableSql = SqlUtils.getDefaultRunableSql(sql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(runableSql);
				ResultSetMetaData meta = rs.getMetaData();
				
				for (int i = 0; i < meta.getColumnCount(); i++) {
					Map<String, Object> column = new HashMap<String, Object>();
					String columnName = meta.getColumnName(i + 1);
					
					if (columnNames.contains(columnName)) {
						throw new Exception("Duplicated column " + columnName);
					} else {
						columnNames.add(columnName);
					}
					
					if (! columnName.matches("\\D\\w*")) {
						throw new Exception("Invalid column name: " + columnName);
					}
					
					String fieldName = SqlUtils.dbNameToJavaName(columnName, false);
					String getterMethodName = "get" + SqlUtils.dbNameToJavaName(columnName, true);
					String setterMethodName = "set" + SqlUtils.dbNameToJavaName(columnName, true);

					column.put("columnName", columnName);
					column.put("fieldName", fieldName);
					column.put("getterMethodName", getterMethodName);
					column.put("setterMethodName", setterMethodName);

					int sqlType = meta.getColumnType(i + 1);
					String columnType = null;
					String fieldType = null;
					if (sqlType == Types.DECIMAL
							|| sqlType == Types.NUMERIC) {
						columnType = "DECIMAL";
						if (meta.getScale(i + 1) == 0) {
							if (meta.getPrecision(i + 1) < 10) {
								fieldType = "Integer";
							} else {
								fieldType = "Long";
							}
						} else {
							fieldType = "Double";
						}
					} else if (sqlType == Types.DOUBLE) {
						columnType = "DOUBLE";
						fieldType = "Double";
					} else if (sqlType == Types.INTEGER
							|| sqlType == Types.BIGINT) {
						columnType = "INTEGER";
						fieldType = "Integer";
					} else if (sqlType == Types.CHAR) {
						columnType = "CHAR";
						fieldType = "String";
					} else if (sqlType == Types.VARCHAR) {
						columnType = "VARCHAR";
						fieldType = "String";
					} else if (sqlType == Types.TIMESTAMP
							|| sqlType == Types.DATE
							|| sqlType == Types.TIME) {
						columnType = "TIMESTAMP";
						fieldType = "Date";
						hasDateColumns = true;
					} else if (sqlType == Types.BLOB) {
						columnType = "BLOB";
						fieldType = "byte[]";
						hasLobColumns = true;
					} else if (sqlType == Types.LONGVARBINARY) {
						columnType = "LONGVARBINARY";
						fieldType = "byte[]";
					} else if (sqlType == Types.CLOB) {
						columnType = "CLOB";
						fieldType = "String";
						hasLobColumns = true;
					} else if (sqlType == Types.LONGVARCHAR) {
						columnType = "LONGVARCHAR";
						fieldType = "String";
					} else {
						throw new Exception("Not supported column type: " + sqlType);
					}
					column.put("columnType", columnType);
					column.put("fieldType", fieldType);

					columns.add(column);
				}
			} finally {
				try {
					rs.close();
				} catch (Exception ex) {
				}
				try {
					stmt.close();
				} catch (Exception ex) {
				}
			}

			dataModel.put("columns", columns);
			dataModel.put("hasDateColumns", hasDateColumns);
			dataModel.put("hasLobColumns", hasLobColumns);
			
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
