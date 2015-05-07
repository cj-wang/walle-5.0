package cn.walle.framework.core.support.spring;

import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiDataSource extends AbstractRoutingDataSource {

	private static ThreadLocal<String> dataSourceNameHolder = new ThreadLocal<String>();
	
	public static void useDataSource(String dataSourceName) {
		dataSourceNameHolder.set(dataSourceName);
	}
	
	public static void useDefaultDataSource() {
		dataSourceNameHolder.set(null);
	}
	

	protected Object determineCurrentLookupKey() {
		return dataSourceNameHolder.get();
	}

	public Logger getParentLogger() {
		// TODO Auto-generated method stub
		return null;
	}

}
