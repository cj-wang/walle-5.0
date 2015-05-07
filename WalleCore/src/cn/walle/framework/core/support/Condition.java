package cn.walle.framework.core.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.Dialect;
import org.hibernate.internal.SessionFactoryImpl;

import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.SqlUtils;

public class Condition {
	
	private String sql;
	private List<Object> parameters;
	
	private Condition(String sql, Object... parameters) {
		this.sql = sql;
		if (parameters != null) {
			this.parameters = Arrays.asList(parameters);
		}
	}
	
	private Condition(String logicOperator, Condition[] conditions) {
		if (conditions != null && conditions.length > 0) {
			List<String> sqlList = new ArrayList<String>();
			this.parameters = new ArrayList<Object>();
			for (Condition condition : conditions) {
				String sql = condition.getSql();
				List<Object> parameters = condition.getParameters();
				if (sql != null && sql.trim().length() > 0) {
					sqlList.add(sql);
				}
				if (parameters != null) {
					this.parameters.addAll(parameters);
				}
			}
			this.sql = "(" + StringUtils.join(sqlList, " " + logicOperator + " ") + ")";
		}
	}
	
	
	public static Condition eq(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " = ?", value);
	}
	
	public static Condition ieq(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		Dialect dialect = ((SessionFactoryImpl) ContextUtils.getBeanOfType(SessionFactory.class)).getDialect();
		String lowercaseFunction = dialect.getLowercaseFunction();
		return new Condition(lowercaseFunction + "(" + propertyName + ") = " + lowercaseFunction + "(?)", value);
	}
	
	public static Condition ne(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " <> ?", value);
	}
	
	public static Condition eqOrIsNull(String propertyName, Object value) {
		return Condition.or(
				Condition.eq(propertyName, value),
				Condition.isNull(propertyName)
				);
	}
	
	public static Condition neOrIsNull(String propertyName, Object value) {
		return Condition.or(
				Condition.ne(propertyName, value),
				Condition.isNull(propertyName)
				);
	}
		
	public static Condition like(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " like ?", value);
	}
	
	public static Condition likeStart(String propertyName, Object value) {
		return like(propertyName, value + "%");
	}
	
	public static Condition likeEnd(String propertyName, Object value) {
		return like(propertyName, "%" + value);
	}
	
	public static Condition likeAnywhere(String propertyName, Object value) {
		return like(propertyName, "%" + value + "%");
	}
	
	public static Condition ilike(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		Dialect dialect = ((SessionFactoryImpl) ContextUtils.getBeanOfType(SessionFactory.class)).getDialect();
		String lowercaseFunction = dialect.getLowercaseFunction();
		return new Condition(lowercaseFunction + "(" + propertyName + ") like " + lowercaseFunction + "(?)", value);
	}
	
	public static Condition ilikeStart(String propertyName, Object value) {
		return ilike(propertyName, value + "%");
	}
	
	public static Condition ilikeEnd(String propertyName, Object value) {
		return ilike(propertyName, "%" + value);
	}
	
	public static Condition ilikeAnywhere(String propertyName, Object value) {
		return ilike(propertyName, "%" + value + "%");
	}
	
//	public static Condition isPartOf(String propertyName, Object value) {
//		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
//		return new Condition(propertyName + " is not null and ? like '%' || " + propertyName + " || '%'", value);
//	}
	
	public static Condition gt(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " > ?", value);
	}
	
	public static Condition ge(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " >= ?", value);
	}
	
	public static Condition lt(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " < ?", value);
	}
	
	public static Condition le(String propertyName, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " <= ?", value);
	}
	
	public static Condition between(String propertyName, Object value1, Object value2) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " between ? and ?", value1, value2);
	}
	
	public static Condition dateBegin(String propertyName, Date value) {
		value = DateUtils.truncate(value, Calendar.DAY_OF_MONTH);
		return Condition.ge(propertyName, value);
	}
	
	public static Condition dateEnd(String propertyName, Date value) {
		value = DateUtils.truncate(value, Calendar.DAY_OF_MONTH);
		value = DateUtils.addDays(value, 1);
		return Condition.lt(propertyName, value);
	}
	
	public static Condition dateBetween(String propertyName, Date value1, Date value2) {
		return Condition.and(Condition.dateBegin(propertyName, value1), Condition.dateEnd(propertyName, value2));
	}
	
	public static Condition in(String propertyName, Object... values) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		if (values.length > 1000) {
			List<Condition> inConditions = new ArrayList<Condition>();
			for (int i = 0; ; i++) {
				Object[] tmpArray = ArrayUtils.subarray(values, 1000 * i, 1000 * (i + 1));
				if (tmpArray.length == 0) {
					break;
				}
				inConditions.add(new Condition(propertyName + " in (?)", (Object) tmpArray));
			}
			return Condition.or(inConditions);
		} else {
			return new Condition(propertyName + " in (?)", (Object) values);
		}
	}
	
	public static Condition notIn(String propertyName, Object... values) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		if (values.length > 1000) {
			List<Condition> andConditions = new ArrayList<Condition>();
			for (int i = 0; ; i++) {
				Object[] tmpArray = ArrayUtils.subarray(values, 1000 * i, 1000 * (i + 1));
				if (tmpArray.length == 0) {
					break;
				}
				andConditions.add(new Condition(propertyName + " not in (?)", (Object) tmpArray));
			}
			return Condition.and(andConditions);
		} else {
			return new Condition(propertyName + " not in (?)", (Object) values);
		}
	}
	
	public static Condition isNull(String propertyName) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " is null");
	}
	
	public static Condition isNotNull(String propertyName) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		return new Condition(propertyName + " is not null");
	}
	
	public static Condition eqProperty(String propertyName1, String propertyName2) {
		propertyName1 = SqlUtils.convertAllJavaNamesToDbNames(propertyName1);
		propertyName2 = SqlUtils.convertAllJavaNamesToDbNames(propertyName2);
		return new Condition(propertyName1 + " = " + propertyName2);
	}
	
	public static Condition neProperty(String propertyName1, String propertyName2) {
		propertyName1 = SqlUtils.convertAllJavaNamesToDbNames(propertyName1);
		propertyName2 = SqlUtils.convertAllJavaNamesToDbNames(propertyName2);
		return new Condition(propertyName1 + " <> " + propertyName2);
	}
	
	public static Condition gtProperty(String propertyName1, String propertyName2) {
		propertyName1 = SqlUtils.convertAllJavaNamesToDbNames(propertyName1);
		propertyName2 = SqlUtils.convertAllJavaNamesToDbNames(propertyName2);
		return new Condition(propertyName1 + " > " + propertyName2);
	}
	
	public static Condition geProperty(String propertyName1, String propertyName2) {
		propertyName1 = SqlUtils.convertAllJavaNamesToDbNames(propertyName1);
		propertyName2 = SqlUtils.convertAllJavaNamesToDbNames(propertyName2);
		return new Condition(propertyName1 + " >= " + propertyName2);
	}
	
	public static Condition ltProperty(String propertyName1, String propertyName2) {
		propertyName1 = SqlUtils.convertAllJavaNamesToDbNames(propertyName1);
		propertyName2 = SqlUtils.convertAllJavaNamesToDbNames(propertyName2);
		return new Condition(propertyName1 + " < " + propertyName2);
	}
	
	public static Condition leProperty(String propertyName1, String propertyName2) {
		propertyName1 = SqlUtils.convertAllJavaNamesToDbNames(propertyName1);
		propertyName2 = SqlUtils.convertAllJavaNamesToDbNames(propertyName2);
		return new Condition(propertyName1 + " <= " + propertyName2);
	}
	
	public static Condition and(Condition... conditions) {
		return new Condition("and", conditions);
	}
	
	public static Condition and(List<Condition> conditions) {
		return new Condition("and", conditions.toArray(new Condition[conditions.size()]));
	}
	
	public static Condition or(Condition... conditions) {
		return new Condition("or", conditions);
	}
	
	public static Condition or(List<Condition> conditions) {
		return new Condition("or", conditions.toArray(new Condition[conditions.size()]));
	}
	
	public static Condition not(Condition condition) {
		return new Condition("not (" + condition.getSql() + ")", condition.getParameters().toArray());
	}
	
	public static Condition operator(String propertyName, String operator, Object value) {
		propertyName = SqlUtils.convertAllJavaNamesToDbNames(propertyName);
		if ("eqOrIsNull".equalsIgnoreCase(operator.trim())) {
			return Condition.eqOrIsNull(propertyName, value);
		} else if ("neOrIsNull".equalsIgnoreCase(operator.trim())) {
			return Condition.neOrIsNull(propertyName, value);
		} else if ("in".equalsIgnoreCase(operator.trim())) {
			if (value instanceof List) {
				value = ((List<?>) value).toArray();
			}
			return Condition.in(propertyName, (Object[]) value);
		} else if ("notIn".equalsIgnoreCase(operator.trim())) {
			if (value instanceof List) {
				value = ((List<?>) value).toArray();
			}
			return Condition.notIn(propertyName, (Object[]) value);
		} else if ("likeStart".equalsIgnoreCase(operator.trim())) {
			return Condition.likeStart(propertyName, value);
		} else if ("likeEnd".equalsIgnoreCase(operator.trim())) {
			return Condition.likeEnd(propertyName, value);
		} else if ("likeAnywhere".equalsIgnoreCase(operator.trim())) {
			return Condition.likeAnywhere(propertyName, value);
		} else if ("ilike".equalsIgnoreCase(operator.trim())) {
			return Condition.ilike(propertyName, value);
		} else if ("ilikeStart".equalsIgnoreCase(operator.trim())) {
			return Condition.ilikeStart(propertyName, value);
		} else if ("ilikeEnd".equalsIgnoreCase(operator.trim())) {
			return Condition.ilikeEnd(propertyName, value);
		} else if ("ilikeAnywhere".equalsIgnoreCase(operator.trim())) {
			return Condition.ilikeAnywhere(propertyName, value);
		} else if ("ieq".equalsIgnoreCase(operator.trim())) {
			return Condition.ieq(propertyName, value);
		} else if ("isNull".equalsIgnoreCase(operator.trim())) {
			return Condition.isNull(propertyName);
		} else if ("isNotNull".equalsIgnoreCase(operator.trim())) {
			return Condition.isNotNull(propertyName);
		} else if ("dateBegin".equalsIgnoreCase(operator.trim())) {
			return Condition.dateBegin(propertyName, (Date) value);
		} else if ("dateEnd".equalsIgnoreCase(operator.trim())) {
			return Condition.dateEnd(propertyName, (Date) value);
		} else {
			return new Condition(propertyName + " " + operator + " ?", value);
		}
	}
	
	public static Condition sql(String sql, Object... parameters) {
		return new Condition(sql, parameters);
	}
	
	
	public String getRunableSql() {
		return SqlUtils.getRunableSql(sql, parameters);
	}
	

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

}
