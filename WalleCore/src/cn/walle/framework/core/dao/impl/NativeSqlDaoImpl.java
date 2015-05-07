package cn.walle.framework.core.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.walle.framework.core.dao.NativeSqlDao;

@Repository
public class NativeSqlDaoImpl implements NativeSqlDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void executeDDL(final String sql) {
		this.sessionFactory.getCurrentSession().doWork(new Work() {
			public void execute(Connection conn) throws SQLException {
				Statement stmt = null;
				try {
					stmt = conn.createStatement();
					stmt.execute(sql);
				} finally {
					try {
						stmt.close();
					} catch (Exception ex) {
					}
				}
			}
		});
	}
	
	public List<Object[]> find(final String sql, final Object... parameters) {
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				queryObject.setParameter(i, parameters[i]);
			}
		}
		List<?> dataList = queryObject.list();
		List<Object[]> result = new ArrayList<Object[]>(dataList.size());
		for (Object dataItem : dataList) {
			if (dataItem instanceof Object[]) {
				result.add((Object[]) dataItem);
			} else {
				result.add(new Object[] {dataItem});
			}
		}
		return result;
	}

	public int bulkUpdate(final String sql, final Object... parameters) {
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				queryObject.setParameter(i, parameters[i]);
			}
		}
		return queryObject.executeUpdate();
	}

}
