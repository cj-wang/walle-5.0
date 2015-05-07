package cn.walle.framework.core.support;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ParameterMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

public class StoredProcedure extends org.springframework.jdbc.object.StoredProcedure {

	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	public StoredProcedure(DataSource ds, String name) {
		super(ds, name);
	}
	
	public void addParameter(String name, String value) {
		super.declareParameter(new SqlParameter(name, Types.VARCHAR));
		parameters.put(name, value);
	}
	
	public void addParameter(String name, int value) {
		super.declareParameter(new SqlParameter(name, Types.INTEGER));
		parameters.put(name, value);
	}
	
	public void addParameter(String name, double value) {
		super.declareParameter(new SqlParameter(name, Types.DOUBLE));
		parameters.put(name, value);
	}
	
	public void addParameter(String name, Date value) {
		super.declareParameter(new SqlParameter(name, Types.TIMESTAMP));
		parameters.put(name, value);
	}
	
	public void addInOutParameter(String name, String value) {
		super.declareParameter(new SqlInOutParameter(name, Types.VARCHAR));
		parameters.put(name, value);
	}
	
	public void addInOutParameter(String name, int value) {
		super.declareParameter(new SqlInOutParameter(name, Types.INTEGER));
		parameters.put(name, value);
	}
	
	public void addInOutParameter(String name, double value) {
		super.declareParameter(new SqlInOutParameter(name, Types.DOUBLE));
		parameters.put(name, value);
	}
	
	public void addInOutParameter(String name, Date value) {
		super.declareParameter(new SqlInOutParameter(name, Types.TIMESTAMP));
		parameters.put(name, value);
	}
	
	public void addOutStringParameter(String name) {
		super.declareParameter(new SqlOutParameter(name, Types.VARCHAR));
	}
	
	public void addOutIntParameter(String name) {
		super.declareParameter(new SqlOutParameter(name, Types.INTEGER));
	}
	
	public void addOutDoubleParameter(String name) {
		super.declareParameter(new SqlOutParameter(name, Types.DOUBLE));
	}
	
	public void addOutDateParameter(String name) {
		super.declareParameter(new SqlOutParameter(name, Types.TIMESTAMP));
	}
	
	public void addOutCursorParameter(String name, Class<?> entityTypeClass) {
		super.declareParameter(new SqlOutParameter(name, OracleTypes.CURSOR,
				ParameterizedBeanPropertyRowMapper.newInstance(entityTypeClass)));
	}

	public Map<String, Object> execute() {
		return super.execute(parameters);
	}
	
	@Override
	public Map<String, Object> execute(Map inParams) throws DataAccessException {
		Map allParams = new HashMap();
		allParams.putAll(parameters);
		allParams.putAll(inParams);
		return super.execute(allParams);
	}

	@Override
	public Map<String, Object> execute(final ParameterMapper inParamMapper)
			throws DataAccessException {
		return super.execute(new ParameterMapper() {
			public Map createMap(Connection con) throws SQLException {
				Map allParams = new HashMap();
				allParams.putAll(parameters);
				allParams.putAll(inParamMapper.createMap(con));
				return allParams;
			}
		});
	}
	
}
