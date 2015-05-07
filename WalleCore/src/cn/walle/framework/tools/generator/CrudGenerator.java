package cn.walle.framework.tools.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

import cn.walle.framework.common.support.FieldDefinition;
import cn.walle.framework.core.support.SystemConfig;
import cn.walle.framework.core.util.SqlUtils;
import cn.walle.framework.tools.util.ConnectionUtils;
import cn.walle.framework.tools.util.UIUtils;

public class CrudGenerator extends Generator {
	
	public void generateModel(String projectName, String packageName, String tableName) throws Exception {
		String className = getClassName(tableName);
		createDataModel(packageName, className, tableName);
		super.generateJava(projectName, packageName + ".model", className + "Model", "/template/crud/Model.ftl");
	}
	
	public void generateManager(String projectName, String packageName, String tableName) throws Exception {
		String className = getClassName(tableName);
		createDataModel(packageName, className, tableName);
		super.generateJava(projectName, packageName + ".service", className + "Manager", "/template/crud/Manager.ftl");
		super.generateJava(projectName, packageName + ".service.impl", className + "ManagerImpl", "/template/crud/ManagerImpl.ftl");
	}
	
	public void generateFieldDef(String projectName, String packageName, String tableName) throws Exception {
		String className = getClassName(tableName);
		createDataModel(packageName, className, tableName);
		String fileName = SystemConfig.SOURCE_DIR + "/" + packageName.replace('.', '/') + "/model/" + className + "Fields.xml";
		super.generateFile(projectName, fileName, "/template/crud/Fields.ftl");
	}
	
	private String getClassName(String tableName) {
		int dotIndex = tableName.indexOf(".");
		if (dotIndex >= 0) {
			tableName = tableName.substring(dotIndex + 1);
		}
		return SqlUtils.dbNameToJavaName(tableName, true);		
	}
	
	private void createDataModel(String packageName, String className, String tableName) throws Exception {
		try {
			Connection conn = ConnectionUtils.getConnection();
			DatabaseMetaData meta = conn.getMetaData();

			String userName = meta.getUserName().toUpperCase();
			int dotIndex = tableName.indexOf(".");
			if (dotIndex >= 0) {
				userName = tableName.substring(0, dotIndex);
				tableName = tableName.substring(dotIndex + 1);
			}
			
			if (tableName.equals(dataModel.get("tableName"))) {
				return;
			}

			String varName = className.substring(0, 1).toLowerCase() + className.substring(1);
			
			dataModel.clear();
			dataModel.put("tableName", tableName);
			dataModel.put("packageName", packageName);
			dataModel.put("className", className);
			dataModel.put("varName", varName);

			ResultSet rs = null;
			List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
			boolean implementsOperationLog = false;
			boolean hasDateColumns = false;
			boolean hasLobColumns = false;
			boolean hasVersionColumn = false;
			try {
				List<String> pks = new ArrayList<String>();
				rs = meta.getPrimaryKeys(null, userName, tableName);
				while (rs.next()) {
					pks.add(rs.getString("COLUMN_NAME"));
				}
				rs.close();
				if (pks.size() == 0) {
					log.info("Table with no PK not supported. Use the first column as default PK");
					UIUtils.showInformation("Warn", "Table " + tableName + " has no PK. Use the first column as default PK");
				}
				if (pks.size() > 1) {
					log.info("Composite PK not supported. Use the first column as default PK");
					UIUtils.showInformation("Warn", "Table " + tableName + " composite PK not supported. Use the first column as default PK");
					pks.clear();
				}

				String tableRemarks = null;
				rs = meta.getTables(null, userName, tableName, null);
				if (rs.next()) {
					tableRemarks = rs.getString("REMARKS");
				}
				rs.close();
				
				String tableLabel = className;
				List<String> tableRemarkLinesList = new ArrayList<String>();
				if (tableRemarks != null && tableRemarks.trim().length() != 0) {
					StringTokenizer st = new StringTokenizer(tableRemarks, " 　,:;，：；\t\n");
					tableLabel = st.nextToken();
					tableRemarks = tableRemarks.substring(tableLabel.length());
					tableRemarkLinesList.addAll(Arrays.asList(StringUtils.tokenizeToStringArray(tableRemarks, "\n")));
				}
				dataModel.put("label", tableLabel);
				dataModel.put("remarkLines", tableRemarkLinesList);
				
				List<String> fieldNames = new ArrayList<String>();

				rs = meta.getColumns(null, userName, tableName, null);
				while (rs.next()) {
					Map<String, Object> column = new HashMap<String, Object>();
					String columnName = rs.getString("COLUMN_NAME");
					String fieldName = SqlUtils.dbNameToJavaName(columnName, false);
					String getterMethodName = "get" + SqlUtils.dbNameToJavaName(columnName, true);
					String setterMethodName = "set" + SqlUtils.dbNameToJavaName(columnName, true);
					
					if (pks.size() == 0) {
						pks.add(columnName);
					}
					
					fieldNames.add(fieldName);

					column.put("columnName", columnName);
					column.put("fieldName", fieldName);
					column.put("getterMethodName", getterMethodName);
					column.put("setterMethodName", setterMethodName);

					int columnSize = rs.getInt("COLUMN_SIZE");
					int decimalDigits = rs.getInt("DECIMAL_DIGITS");

					int sqlType = rs.getInt("DATA_TYPE");
					String columnType;
					String fieldType;
					String uiFieldType;
					int length = 0;
					int precision = 0;
					int scale = 0;
					int width = 0;
					if (sqlType == Types.DECIMAL) {
						columnType = "DECIMAL";
						if (rs.getInt("DECIMAL_DIGITS") == 0) {
							if (columnSize < 10) {
								fieldType = "Integer";
							} else {
								fieldType = "Long";
							}
							uiFieldType = FieldDefinition.FIELD_TYPE_INT;
							precision = columnSize;
							width = columnSize;
						} else {
							fieldType = "Double";
							uiFieldType = FieldDefinition.FIELD_TYPE_DOUBLE;
							precision = columnSize;
							scale = decimalDigits;
							width = columnSize + 1;
						}
					} else if (sqlType == Types.DOUBLE) {
						columnType = "DOUBLE";
						fieldType = "Double";
						uiFieldType = FieldDefinition.FIELD_TYPE_DOUBLE;
						precision = columnSize;
						scale = decimalDigits;
						width = columnSize + 1;
					} else if (sqlType == Types.INTEGER
							|| sqlType == Types.BIGINT) {
						columnType = "INTEGER";
						fieldType = "Integer";
						uiFieldType = FieldDefinition.FIELD_TYPE_INT;
						precision = columnSize;
						scale = decimalDigits;
						width = columnSize + 1;
					} else if (sqlType == Types.CHAR) {
						columnType = "CHAR";
						fieldType = "String";
						uiFieldType = FieldDefinition.FIELD_TYPE_STRING;
						length = columnSize;
						width = columnSize;
					} else if (sqlType == Types.VARCHAR) {
						columnType = "VARCHAR";
						fieldType = "String";
						uiFieldType = FieldDefinition.FIELD_TYPE_STRING;
						length = columnSize;
						width = columnSize;
					} else if (sqlType == Types.TIMESTAMP
							|| sqlType == Types.DATE
							|| sqlType == Types.TIME) {
						columnType = "TIMESTAMP";
						fieldType = "Date";
						hasDateColumns = true;
						if (fieldName.toLowerCase().endsWith("time")) {
							uiFieldType = FieldDefinition.FIELD_TYPE_DATETIME;
							width = 19;
						} else if (fieldName.toLowerCase().endsWith("month")) {
							uiFieldType = FieldDefinition.FIELD_TYPE_MONTH;
							width = 10;
						} else {
							uiFieldType = FieldDefinition.FIELD_TYPE_DATE;
							width = 10;
						}
					} else if (sqlType == Types.BLOB) {
						columnType = "BLOB";
						fieldType = "byte[]";
						hasLobColumns = true;
						uiFieldType = FieldDefinition.FIELD_TYPE_BYTES;
					} else if (sqlType == Types.LONGVARBINARY) {
						columnType = "LONGVARBINARY";
						fieldType = "byte[]";
						uiFieldType = FieldDefinition.FIELD_TYPE_BYTES;
					} else if (sqlType == Types.CLOB) {
						columnType = "CLOB";
						fieldType = "String";
						hasLobColumns = true;
						uiFieldType = FieldDefinition.FIELD_TYPE_TEXT;
						width = 20;
					} else if (sqlType == Types.LONGVARCHAR) {
						columnType = "LONGVARCHAR";
						fieldType = "String";
						uiFieldType = FieldDefinition.FIELD_TYPE_TEXT;
						width = 20;
					} else {
						log.info(tableName + "." + columnName + ", not supported column type: " + sqlType + ". Use String as default");
						UIUtils.showInformation("Warn", tableName + "." + columnName + ", not supported column type: " + sqlType + ". Use String as default");
						columnType = "VARCHAR";
						fieldType = "String";
						uiFieldType = FieldDefinition.FIELD_TYPE_STRING;
						length = columnSize;
						width = columnSize;
					}
					column.put("columnType", columnType);
					column.put("fieldType", fieldType);
					column.put("length", Integer.toString(length));
					column.put("precision", Integer.toString(precision));
					column.put("scale", Integer.toString(scale));

					if (pks.contains(columnName)) {
						column.put("isPK", true);
						dataModel.put("pkColumnName", columnName);
						dataModel.put("pkFieldName", fieldName);
						dataModel.put("pkFieldType", fieldType);
						dataModel.put("pkFieldLength", Integer.toString(length));
					} else {
						column.put("isPK", false);
					}
					
					if ("REC_VER".equalsIgnoreCase(columnName)
							|| "RECORD_VERSION".equalsIgnoreCase(columnName)) {
						column.put("isVersion", true);
						hasVersionColumn = true;
					} else {
						column.put("isVersion", false);
					}
					
					String remarks = rs.getString("REMARKS");
					String label = fieldName;
					List<String> remarkLinesList = new ArrayList<String>();
					if (remarks != null && remarks.trim().length() != 0) {
						StringTokenizer st = new StringTokenizer(remarks, " 　,:;，：；\t\n");
						label = st.nextToken();
						remarks = remarks.substring(label.length());
						remarkLinesList.addAll(Arrays.asList(StringUtils.tokenizeToStringArray(remarks, "\n")));
					}
					column.put("label", label);
					column.put("remarkLines", remarkLinesList);
					
					String[] remarkParts = StringUtils.tokenizeToStringArray(
							remarks, " 　,:;，：；\t\n");
					if (remarkParts != null) {
						for (String part : remarkParts) {
							if (part.startsWith(FieldDefinition.FIELD_TYPE_SELECTCODE)) {
								uiFieldType = part;
								width = 20;
								break;
							}
						}
					}
					
					column.put("uiFieldType", uiFieldType);
					
					if (width < 10) {
						width = 10;
					}
					if (width > 20) {
						width = 20;
					}
					width *= 10;
					column.put("width", Integer.toString(width));

					column.put("nullable", "YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")) ? "true" : "false");
					column.put("sortable", sqlType != Types.BLOB && sqlType != Types.CLOB ? "true" : "false");
					
					columns.add(column);
				}

				if (fieldNames.containsAll(Arrays.asList("creator", "createTime", "modifier", "modifyTime"))) {
					implementsOperationLog = true;
				}
					
			} finally {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			}

			dataModel.put("columns", columns);
			dataModel.put("implementsOperationLog", implementsOperationLog);
			dataModel.put("hasDateColumns", hasDateColumns);
			dataModel.put("hasLobColumns", hasLobColumns);
			dataModel.put("hasVersionColumn", hasVersionColumn);
		} catch (Exception ex) {
			dataModel.clear();
			throw ex;
		}
	}
	
}
