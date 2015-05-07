package cn.walle.framework.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.engine.query.spi.ParameterMetadata;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionFactoryImpl;

import cn.walle.framework.common.support.QueryDefinitionLoader;
import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.DataFilterConditionsService;

public class SqlUtils {

	public static final String SQL_EXTRA_CONDITIONS_MACRO = "##CONDITIONS##";
	
	public static final String SQL_DATA_FILTER_CONDITIONS_MACRO = "##CONDITIONS\\$\\w+\\$\\w+##";
	
	private static final Pattern SQL_DATA_FILTER_CONDITIONS_MACRO_PATTERN = Pattern.compile(SQL_DATA_FILTER_CONDITIONS_MACRO);
	
	public static TreeMap<Integer, String> getSqlParameters(String sql) {
		SessionFactory sessionFactory = ContextUtils.getBeanOfType(SessionFactory.class);
		ParameterMetadata parameterMetaData = ((SessionFactoryImplementor) sessionFactory)
				.getQueryPlanCache().getSQLParameterMetadata(sql);
		Set<String> parameterNames = parameterMetaData.getNamedParameterNames();

		TreeMap<Integer, String> parameters = new TreeMap<Integer, String>();
		for (String parameterName : parameterNames) {
			int[] locations = parameterMetaData.getNamedParameterSourceLocations(parameterName);
			for (int location : locations) {
				parameters.put(location, parameterName);
			}
		}
		return parameters;
	}
	
	public static String convertParametersToJavaStyle(String sql) {
		TreeMap<Integer, String> parameters = getSqlParameters(sql);
		List<Integer> paramLocationsList = new ArrayList<Integer>(parameters.keySet());
		Collections.reverse(paramLocationsList);
		StringBuilder resultSql = new StringBuilder(sql);
		for (Integer location : paramLocationsList) {
			String paramName = parameters.get(location);
			resultSql.delete(location + 1, location + paramName.length() + 1);
			resultSql.insert(location + 1, dbNameToJavaName(paramName, false));
		}
		return resultSql.toString();
	}
	
	public static String getDefaultRunableSql(String sql) {
		sql = sql.replace(SQL_EXTRA_CONDITIONS_MACRO, "0=0");
		sql = sql.replaceAll(SQL_DATA_FILTER_CONDITIONS_MACRO, "0=0");
		sql = sql.replaceAll("<<.*?>>", "");
		TreeMap<Integer, String> parameters = getSqlParameters(sql);
		List<Integer> paramLocationsList = new ArrayList<Integer>(parameters.keySet());
		Collections.reverse(paramLocationsList);
		StringBuilder resultSql = new StringBuilder(sql);
		for (Integer location : paramLocationsList) {
			String paramName = parameters.get(location);
			resultSql.delete(location, location + paramName.length() + 1);
			resultSql.insert(location, "null");
		}
		
		int[] ordinalParameterLocations = getOrdinalParameterLocations(resultSql.toString());
		for (int i = ordinalParameterLocations.length - 1; i >= 0 ; i--) {
			resultSql.delete(ordinalParameterLocations[i], ordinalParameterLocations[i] + 1);
			resultSql.insert(ordinalParameterLocations[i], "null");
		}
		
		return "select * from(" + resultSql.toString() + ") T__ROW__ where 1=0";
	}
	
	public static String toSqlValue(Object value) {
		if (value == null) {
			return "null";
		} else if (value instanceof String) {
			return "'" + value + "'";
		} else if (value instanceof Number) {
			return value.toString();
		} else if (value instanceof Date) {
			return "to_date('" + DateUtils.formatDateTime((Date) value) + "', 'yyyy-mm-dd hh24:mi:ss')";
		} else if (value.getClass().isArray()) {
			String[] sqlValues = new String[Array.getLength(value)];
			if (sqlValues.length == 0) {
				return "null";
			}
			for (int i = 0; i < sqlValues.length; i++) {
				sqlValues[i] = toSqlValue(Array.get(value, i));
			}
			return StringUtils.join(sqlValues, ", ");
		} else {
			return value.toString();
		}
	}
	
	public static String getRunableSql(String sql, List<Object> parameterValues) {
		int[] ordinalParameterLocations = getOrdinalParameterLocations(sql);
		if (ordinalParameterLocations.length == 0) {
			return sql;
		}
		if (ordinalParameterLocations.length != parameterValues.size()) {
			throw new RuntimeException("Wrong number of parameters");
		}
		StringBuilder resultSql = new StringBuilder(sql);
		for (int i = ordinalParameterLocations.length - 1; i >= 0 ; i--) {
			resultSql.delete(ordinalParameterLocations[i], ordinalParameterLocations[i] + 1);
			resultSql.insert(ordinalParameterLocations[i], toSqlValue(parameterValues.get(i)));
		}
		
		return resultSql.toString();
	}
	
	public static String getRunableSql(String sql, Map<String, Object> parameterValues) {
		TreeMap<Integer, String> parameters = getSqlParameters(sql);
		if (parameters.size() == 0) {
			return sql;
		}
		List<Integer> paramLocationsList = new ArrayList<Integer>(parameters.keySet());
		Collections.reverse(paramLocationsList);
		StringBuilder resultSql = new StringBuilder(sql);
		for (Integer location : paramLocationsList) {
			String paramName = parameters.get(location);
			resultSql.delete(location, location + paramName.length() + 1);
			resultSql.insert(location, toSqlValue(parameterValues.get(paramName)));
		}
		
		return resultSql.toString();
	}
	
	public static int[] getOrdinalParameterLocations(String sql) {
		SessionFactory sessionFactory = ContextUtils.getBeanOfType(SessionFactory.class);
		ParameterMetadata parameterMetaData = ((SessionFactoryImplementor) sessionFactory)
				.getQueryPlanCache().getSQLParameterMetadata(sql);
		int count = parameterMetaData.getOrdinalParameterCount();
		int[] ordinalParameterLocations = new int[count];
		for (int i = 0; i < ordinalParameterLocations.length; i++) {
			ordinalParameterLocations[i] = parameterMetaData.getOrdinalParameterSourceLocation(i + 1);
		}
		return ordinalParameterLocations;
	}
	
	/**
	 * Convert sql column name to java field name
	 * 
	 * @param dbName  Name looks like "COL_A"
	 * @param firstCharUppered
	 * @return  Name looks like "colA"
	 */
	public static String dbNameToJavaName(String dbName, boolean firstCharUppered) {
		if (dbName == null || dbName.trim().length() == 0) {
			return "";
		}
		if (dbName.indexOf("_") >= 0 || dbName.equals(dbName.toUpperCase())) {
			dbName = dbName.toLowerCase();
		}
		String[] parts = dbName.split("_");
		StringBuilder sb = new StringBuilder();
		for (String part : parts) {
			if (part.length() == 0) {
				continue;
			}
			sb.append(part.substring(0, 1).toUpperCase());
			sb.append(part.substring(1));
		}
		if (firstCharUppered) {
			return sb.toString();
		} else {
			return sb.substring(0, 1).toLowerCase() + sb.substring(1);
		}
	}
	
	/**
	 * Convert java field name to sql column name
	 * 
	 * @param name  Name looks like "colA"
	 * @return  Name looks like "COL_A"
	 */
	public static String javaNameToDbName(String name) {
		if (name == null || name.trim().length() == 0) {
			return "";
		}
		if (name.indexOf("_") >= 0) {
			return name;
		}
		if (Character.isUpperCase(name.charAt(0))) {
			name = name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		StringBuilder sb = new StringBuilder();
		char[] chars = name.toCharArray();
		for (char c : chars) {
			if (Character.isUpperCase(c)) {
				sb.append("_");
				sb.append(c);
			} else {
				sb.append(Character.toUpperCase(c));
			}
		}
		return sb.toString();
	}
	
	private static Pattern javaNamePattern = Pattern.compile("\\p{Lower}\\w*\\p{Upper}\\w*");
	
	public static String convertAllJavaNamesToDbNames(String sql) {
		StringBuffer sb = new StringBuffer();
		Matcher javaNameMatcher = javaNamePattern.matcher(sql);
		while (javaNameMatcher.find()) {
			String javaName = javaNameMatcher.group();
			javaNameMatcher.appendReplacement(sb, javaNameToDbName(javaName));
		}
		javaNameMatcher.appendTail(sb);
		return sb.toString();
	}

	public static Order[] parseOrderByToHibernateOrders(String orderBy) {
		if (orderBy == null || orderBy.trim().length() == 0) {
			return null;
		}
		List<Order> result = new ArrayList<Order>();
		String[] orders = orderBy.split(",");
		for (String order : orders) {
			order = order.trim();
			if (order.length() == 0) {
				continue;
			}
			if (order.toLowerCase().endsWith(" asc")) {
				result.add(Order.asc(dbNameToJavaName(order.substring(0, order.length() - 4).trim(), false)));
			} else if (order.toLowerCase().endsWith(" desc")) {
				result.add(Order.desc(dbNameToJavaName(order.substring(0, order.length() - 5).trim(), false)));
			} else {
				result.add(Order.asc(dbNameToJavaName(order.trim(), false)));
			}
		}
		return result.toArray(new Order[result.size()]);
	}

    /**
     * Convert hql style order by clause to sql style
     * 
     * @param orderBy  Clause looks like "colA, colB desc"
     * @return  Clause looks like "COL_A, COL_B desc"
     */
	public static String parseOrderByToSqlStyle(String orderBy) {
		if (orderBy == null || orderBy.trim().length() == 0) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		String[] orders = orderBy.split(",");
		for (String order : orders) {
			order = order.trim();
			if (order.length() == 0) {
				continue;
			}
			if (order.toLowerCase().endsWith(" asc")) {
				result.append(javaNameToDbName(order.substring(0, order.length() - 4).trim()));
			} else if (order.toLowerCase().endsWith(" desc")) {
				result.append(javaNameToDbName(order.substring(0, order.length() - 5).trim()));
				result.append(" desc");
			} else {
				result.append(javaNameToDbName(order.trim()));
			}
			result.append(", ");
		}
		result.setLength(result.length() - 2);
		return result.toString();
	}

	public static String addExtraConditions(String sql, String sqlCondition) {
		if (sqlCondition != null && sqlCondition.trim().length() != 0) {
			sqlCondition = "(" + sqlCondition + ")";
			if (sql.indexOf(SQL_EXTRA_CONDITIONS_MACRO) >= 0) {
				sql = sql.replace(SQL_EXTRA_CONDITIONS_MACRO, sqlCondition);
			} else {
				sql = "SELECT T__CONDITION__.* FROM (" + sql + ") T__CONDITION__ WHERE " + sqlCondition;
				//throw new RuntimeException("No extra conditions macro in the sql. Can not add condition: \"" + sqlCondition + "\"");
			}
		} else {
			sql = sql.replace(SQL_EXTRA_CONDITIONS_MACRO, "0=0");
		}
		return sql;
	}

	public static String addDataFilterConditions(String sql) {
		StringBuffer sb = new StringBuffer();
		Matcher matcher = SQL_DATA_FILTER_CONDITIONS_MACRO_PATTERN.matcher(sql);
		while (matcher.find()) {
			String macro = matcher.group();
			String tableName = macro.substring(macro.indexOf("$") + 1, macro.lastIndexOf("$"));
			String alias = macro.substring(macro.lastIndexOf("$") + 1, macro.length() - 2);
			String sqlCondition = ContextUtils.getBeanOfType(DataFilterConditionsService.class).getDataFilterConditions(tableName, alias);
			if (sqlCondition == null || sqlCondition.trim().length() == 0) {
				sqlCondition = "0=0";
			} else {
				sqlCondition = "(" + sqlCondition + ")";
			}
			matcher.appendReplacement(sb, sqlCondition);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String getNamedSql(String sqlName) {
		SessionFactory sessionFactory = ContextUtils.getBeanOfType(SessionFactory.class);
		NamedSQLQueryDefinition sqlQuery = ((SessionFactoryImpl) sessionFactory).getNamedSQLQuery(sqlName);
		if (sqlQuery != null) {
			return sqlQuery.getQueryString();
		} else {
			if (ContextUtils.getBeansOfType(QueryDefinitionLoader.class).size() > 0) {
				return ContextUtils.getBeanOfType(QueryDefinitionLoader.class).getQueryString(sqlName);
			} else {
				throw new SystemException("Query " + sqlName + " not found");
			}
		}
	}
	
	private static Pattern dynamicConditionPattern = Pattern.compile("<<.*?>>");
	
	public static String getDynamicNamedSql(String sqlName, Object valueBean) {
		String sql = getNamedSql(sqlName);
		
		CustomBeanWrapper valueBeanWrapper = new CustomBeanWrapper(valueBean);
		StringBuffer sb = new StringBuffer();
		Matcher dynamicConditionMatcher = dynamicConditionPattern.matcher(sql);
		while (dynamicConditionMatcher.find()) {
			String dynamicCondition = dynamicConditionMatcher.group();
			boolean parametersValid = false;
			for (String parameterName : getSqlParameters(dynamicCondition).values()) {
				if (ParameterUtils.isParamValid(valueBeanWrapper.getPropertyValue(parameterName))) {
					parametersValid = true;
				} else {
					parametersValid = false;
					break;
				}
			}
			if (parametersValid) {
				dynamicConditionMatcher.appendReplacement(sb, dynamicCondition.substring(2, dynamicCondition.length() - 2));
			} else {
				dynamicConditionMatcher.appendReplacement(sb, "");
			}
		}
		dynamicConditionMatcher.appendTail(sb);
		sql = sb.toString();
		
		sql = sql.replaceAll("([ \t]*\n\t*)+", "\n");
		return sql;
	}
	
	public static String getDynamicNamedSql(String sqlName, Map<String, Object> parameters) {
		String sql = getNamedSql(sqlName);
		StringBuffer sb = new StringBuffer();
		Matcher dynamicConditionMatcher = dynamicConditionPattern.matcher(sql);
		while (dynamicConditionMatcher.find()) {
			String dynamicCondition = dynamicConditionMatcher.group();
			boolean parametersValid = false;
			for (String parameterName : getSqlParameters(dynamicCondition).values()) {
				if (ParameterUtils.isParamValid(parameters.get(parameterName))) {
					parametersValid = true;
				} else {
					parametersValid = false;
					break;
				}
			}
			if (parametersValid) {
				dynamicConditionMatcher.appendReplacement(sb, dynamicCondition.substring(2, dynamicCondition.length() - 2));
			} else {
				dynamicConditionMatcher.appendReplacement(sb, "");
			}
		}
		dynamicConditionMatcher.appendTail(sb);
		sql = sb.toString();
		
		sql = sql.replaceAll("([ \t]*\n\t*)+", "\n");
		return sql;
	}
	
}
