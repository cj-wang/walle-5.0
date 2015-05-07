package cn.walle.framework.core.dao;

import java.util.List;

public interface NativeSqlDao {
	
	void executeDDL(String sql);
	
	List<Object[]> find(String sql, Object... parameters);
	
	int bulkUpdate(String sql, Object... parameters);

}
