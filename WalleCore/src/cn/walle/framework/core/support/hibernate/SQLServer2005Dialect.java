package cn.walle.framework.core.support.hibernate;

import org.hibernate.dialect.SQLServerDialect;

import cn.walle.framework.core.exception.SystemException;

public class SQLServer2005Dialect extends SQLServerDialect {
	
	@Override
	public String getLimitString(String querySelect, int offset, int limit) {
		int lastIndexOfOrderBy = querySelect.toLowerCase().lastIndexOf("order by ");

		if (lastIndexOfOrderBy < 0) {
			throw new SystemException("order by clause needed");
		}
		
		String orderby = querySelect.substring(lastIndexOfOrderBy, querySelect.length());
		int indexOfSelect = querySelect.toLowerCase().indexOf("select");
		String clumnToWhere = querySelect.substring(indexOfSelect + 6, lastIndexOfOrderBy);
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T__PAGING__.* FROM (SELECT ROW_NUMBER() OVER (")
		.append(orderby)
		.append(") AS ROW_ID__,")
		.append(clumnToWhere)
		.append(") T__PAGING__ WHERE ROW_ID__ BETWEEN ").append(offset + 1).append(" AND ").append(limit);
		return sql.toString();
	}

	@Override
	public boolean supportsLimitOffset() {
		return true;
	}
	
}