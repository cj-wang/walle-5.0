package cn.walle.framework.tools.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import cn.walle.framework.core.util.ContextUtils;

public class ConnectionUtils {
	
	private static final Log log = LogFactory.getLog(ConnectionUtils.class);

	private static Connection conn;
	
	private static void initConnection() throws SQLException {
		log.debug("Get connection");
		DataSource dataSource = SessionFactoryUtils.getDataSource(ContextUtils.getBeanOfType(SessionFactory.class));
		conn = dataSource.getConnection();
		NativeJdbcExtractor nativeJdbcExtractor = ContextUtils.getBeanOfType(NativeJdbcExtractor.class);
		conn = nativeJdbcExtractor.getNativeConnection(conn);
		if (conn instanceof OracleConnection) {
			log.debug("OracleConnection, enable RemarksReporting");
			((OracleConnection) conn).setRemarksReporting(true);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if (conn == null) {
			synchronized (ConnectionUtils.class) {
				if (conn == null) {
					initConnection();
				}
			}
		}
		return conn;
	}
}
