package cn.walle.framework.core.aop;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.support.Condition;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.framework.core.support.SqlLogger;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetails;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.update.BaseUpdateCondition;
import cn.walle.framework.core.util.EntityUtils;
import cn.walle.framework.core.util.SqlUtils;

/**
 * Aspect for the log sqls
 * @author cj
 *
 */
@Aspect
public class SqlLogDaoAspect extends BaseAspect {
	
	@Around("execution(* *..dao.*Dao.get(..)) && args(modelClass, id)")
	public Object aroundGet(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass, Serializable id) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(modelClass)
		.addCondition(Condition.eq(EntityUtils.getIdFieldName(modelClass), id))
		.getRunableSql();
		return proceed(pjp, "get " + modelClass.getSimpleName() + " by id " + id, sql);
	}
	
	@Around("execution(* *..dao.*Dao.getForUpdate(..)) && args(modelClass, id)")
	public Object aroundGetForUpdate(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass, Serializable id) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(modelClass)
		.addCondition(Condition.eq(EntityUtils.getIdFieldName(modelClass), id))
		.getRunableSql();
		sql = sql + " for update";
		return proceed(pjp, "get " + modelClass.getSimpleName() + " by id " + id + " for update", sql);
	}
	
	@Around("execution(* *..dao.*Dao.exists(..)) && args(modelClass, id)")
	public Object aroundExists(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass, Serializable id) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(modelClass)
		.addCondition(Condition.eq(EntityUtils.getIdFieldName(modelClass), id))
		.getRunableSql();
		return proceed(pjp, "exists " + modelClass.getSimpleName() + " by id " + id, sql);
	}
	
	@Around("execution(* *..dao.*Dao.getAll(..)) && args(modelClass)")
	public Object aroundGetAll(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass) throws Throwable {
		return aroundGetAll(pjp, modelClass, null, null);
	}
	
	@Around("execution(* *..dao.*Dao.getAll(..)) && args(modelClass, orderBy)")
	public Object aroundGetAll(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass, String orderBy) throws Throwable {
		return aroundGetAll(pjp, modelClass, orderBy, null);
	}
	
	@Around("execution(* *..dao.*Dao.getAll(..)) && args(modelClass, orderBy, pagingInfo)")
	public Object aroundGetAll(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass, String orderBy, PagingInfo pagingInfo) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(modelClass)
		.setOrderBy(orderBy)
		.setPagingInfo(pagingInfo)
		.getRunableSql();
		return proceed(pjp, "get all " + modelClass.getSimpleName(), sql);
	}
	
	@Around("execution(* *..dao.*Dao.findBySqlCondition(..)) && args(modelClass, sqlCondition, parameterValues)")
	public Object aroundFindBySqlCondition(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass,
			String sqlCondition, Object[] parameterValues) throws Throwable {
		return aroundFindBySqlCondition(pjp, modelClass, sqlCondition, parameterValues, null, null);
	}
	
	@Around("execution(* *..dao.*Dao.findBySqlCondition(..)) && args(modelClass, sqlCondition, parameterValues, orderBy)")
	public Object aroundFindBySqlCondition(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass,
			String sqlCondition, Object[] parameterValues, String orderBy) throws Throwable {
		return aroundFindBySqlCondition(pjp, modelClass, sqlCondition, parameterValues, orderBy, null);
	}
	
	@Around("execution(* *..dao.*Dao.findBySqlCondition(..)) && args(modelClass, sqlCondition, parameterValues, orderBy, pagingInfo)")
	public Object aroundFindBySqlCondition(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass,
			String sqlCondition, Object[] parameterValues, String orderBy, PagingInfo pagingInfo) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(modelClass)
		.addCondition(Condition.sql(sqlCondition, parameterValues))
		.setOrderBy(orderBy)
		.setPagingInfo(pagingInfo)
		.getRunableSql();
		return proceed(pjp, "find " + modelClass.getSimpleName() + " by sql condition", sql);
	}
	
	@Around("execution(* *..dao.*Dao.findByExample(..)) && args(example)")
	public Object aroundFindByExample(ProceedingJoinPoint pjp, BaseModel example) throws Throwable {
		return aroundFindByExample(pjp, example, null, null, null, null);
	}
	
	@Around("execution(* *..dao.*Dao.findByExample(..)) && args(example, orderBy)")
	public Object aroundFindByExample(ProceedingJoinPoint pjp, BaseModel example, String orderBy) throws Throwable {
		return aroundFindByExample(pjp, example, null, null, orderBy, null);
	}
	
	@Around("execution(* *..dao.*Dao.findByExample(..)) && args(example, orderBy, pagingInfo)")
	public Object aroundFindByExample(ProceedingJoinPoint pjp, BaseModel example, String orderBy, PagingInfo pagingInfo) throws Throwable {
		return aroundFindByExample(pjp, example, null, null, orderBy, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.findByExample(..)) && args(example, sqlCondition, parameterValues, orderBy, pagingInfo)")
	public Object aroundFindByExample(ProceedingJoinPoint pjp, BaseModel example,
			String sqlCondition, Object[] parameterValues, String orderBy, PagingInfo pagingInfo) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(example)
		.addCondition(Condition.sql(sqlCondition, parameterValues))
		.setOrderBy(orderBy)
		.setPagingInfo(pagingInfo)
		.getRunableSql();
		return proceed(pjp, "find " + example.getClass().getSimpleName() + " by example", sql);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(condition, itemClass)")
	public Object aroundQuery(ProceedingJoinPoint pjp, BaseQueryCondition condition, Class<? extends BaseModel> itemClass) throws Throwable {
		return aroundQuery(pjp, condition, itemClass, null, null, null, null);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(condition, itemClass, orderBy)")
	public Object aroundQuery(ProceedingJoinPoint pjp, BaseQueryCondition condition, Class<? extends BaseModel> itemClass,
			String orderBy) throws Throwable {
		return aroundQuery(pjp, condition, itemClass, null, null, orderBy, null);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(condition, itemClass, pagingInfo)")
	public Object aroundQuery(ProceedingJoinPoint pjp, BaseQueryCondition condition, Class<? extends BaseModel> itemClass,
			PagingInfo pagingInfo) throws Throwable {
		return aroundQuery(pjp, condition, itemClass, null, null, null, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(condition, itemClass, orderBy, pagingInfo)")
	public Object aroundQuery(ProceedingJoinPoint pjp, BaseQueryCondition condition, Class<? extends BaseModel> itemClass,
			String orderBy, PagingInfo pagingInfo) throws Throwable {
		return aroundQuery(pjp, condition, itemClass, null, null, orderBy, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(condition, itemClass, extraSqlCondition, parameterValues, orderBy, pagingInfo)")
	public Object aroundQuery(ProceedingJoinPoint pjp, BaseQueryCondition condition, Class<? extends BaseModel> itemClass,
			String extraSqlCondition, Object[] parameterValues,
			String orderBy, PagingInfo pagingInfo) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(condition, itemClass)
		.addCondition(Condition.sql(extraSqlCondition, parameterValues))
		.setOrderBy(orderBy)
		.setPagingInfo(pagingInfo)
		.getRunableSql();
		return proceed(pjp, EntityUtils.getSqlQueryName(condition), sql);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(queryName, parameters)")
	public Object aroundQuery(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters) throws Throwable {
		return aroundQuery(pjp, queryName, parameters, null, null, null, null);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(queryName, parameters, orderBy)")
	public Object aroundQuery(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters,
			String orderBy) throws Throwable {
		return aroundQuery(pjp, queryName, parameters, null, null, orderBy, null);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(queryName, parameters, pagingInfo)")
	public Object aroundQuery(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters,
			PagingInfo pagingInfo) throws Throwable {
		return aroundQuery(pjp, queryName, parameters, null, null, null, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(queryName, parameters, orderBy, pagingInfo)")
	public Object aroundQuery(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters,
			String orderBy, PagingInfo pagingInfo) throws Throwable {
		return aroundQuery(pjp, queryName, parameters, null, null, orderBy, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.query(..)) && args(queryName, parameters, extraSqlCondition, parameterValues, orderBy, pagingInfo)")
	public Object aroundQuery(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters,
			String extraSqlCondition, Object[] parameterValues,
			String orderBy, PagingInfo pagingInfo) throws Throwable {
		String sql = ((UniversalDao) pjp.getTarget()).createCommonQuery(queryName, parameters)
		.addCondition(Condition.sql(extraSqlCondition, parameterValues))
		.setOrderBy(orderBy)
		.setPagingInfo(pagingInfo)
		.getRunableSql();
		return proceed(pjp, queryName, sql);
	}
	
	@Around("execution(* *..dao.*Dao.update(..)) && args(condition)")
	public Object aroundUpdate(ProceedingJoinPoint pjp, BaseUpdateCondition condition) throws Throwable {
		String sql = SqlUtils.getNamedSql(EntityUtils.getSqlUpdateName(condition));
		Map<String, Object> beanPropertiesMap = new HashMap<String, Object>();
		CustomBeanWrapper beanWrapper = new CustomBeanWrapper(condition);
		for (PropertyDescriptor propertyDescriptor : beanWrapper.getPropertyDescriptors()) {
			beanPropertiesMap.put(propertyDescriptor.getName(), beanWrapper.getPropertyValue(propertyDescriptor.getName()));
		}
		sql = SqlUtils.getRunableSql(sql, beanPropertiesMap);
		return proceed(pjp, EntityUtils.getSqlUpdateName(condition), sql);
	}
	
	@Autowired(required = false)
	private SqlLogger sqlLogger;
	
	private Object proceed(ProceedingJoinPoint pjp, String comments, String sql) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			Object result = pjp.proceed();
			return result;
		} finally {
			long endTime = System.currentTimeMillis();
			int timeUsed = (int) (endTime - startTime);
			String userName;
			AcegiUserDetails user = SessionContext.getUser();
			if (user == null) {
				userName = "N/A";
			} else {
				userName = user.getUsername();
			}
			log.info("[" + AccessLogServiceAspect.serviceAccessIndex.get() + "] [user:" + userName + "]\n/* " + comments + " */\n" + sql + "\n[" + timeUsed + " ms]");
			if (sqlLogger != null) {
				sqlLogger.logSql(sql, timeUsed);
			}
		}
	}

}
