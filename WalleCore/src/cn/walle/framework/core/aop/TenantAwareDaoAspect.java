package cn.walle.framework.core.aop;

import java.util.Collection;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetails;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.update.BaseUpdateCondition;
import cn.walle.framework.core.util.ParameterUtils;

@Aspect
public class TenantAwareDaoAspect extends BaseAspect {
	
	private static String TENANT_ID_FIELD = "tenantId";

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
		BaseModel example = modelClass.newInstance();
		addTenantIdCondition(example);
		return ((UniversalDao) pjp.getThis()).findByExample(example, orderBy, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.getRowCount(..)) && args(modelClass)")
	public Object aroundGetRowCount(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass) throws Throwable {
		BaseModel example = modelClass.newInstance();
		addTenantIdCondition(example);
		return ((UniversalDao) pjp.getThis()).getRowCountByExample(example);
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
		BaseModel example = modelClass.newInstance();
		addTenantIdCondition(example);
		return ((UniversalDao) pjp.getThis()).findByExample(example,
				sqlCondition, parameterValues, orderBy, pagingInfo);
	}
	
	@Around("execution(* *..dao.*Dao.getRowCountBySqlCondition(..)) && args(modelClass, sqlCondition, parameterValues)")
	public Object aroundGetRowCountBySqlCondition(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass,
			String sqlCondition, Object[] parameterValues) throws Throwable {
		BaseModel example = modelClass.newInstance();
		addTenantIdCondition(example);
		return ((UniversalDao) pjp.getThis()).getRowCountByExample(example,
				sqlCondition, parameterValues);
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
		addTenantIdCondition(example);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.getRowCountByExample(..)) && args(example)")
	public Object aroundGetRowCountByExample(ProceedingJoinPoint pjp, BaseModel example) throws Throwable {
		return aroundGetRowCountByExample(pjp, example, null, null);
	}
	
	@Around("execution(* *..dao.*Dao.getRowCountByExample(..)) && args(example, sqlCondition, parameterValues)")
	public Object aroundGetRowCountByExample(ProceedingJoinPoint pjp, BaseModel example,
			String sqlCondition, Object[] parameterValues) throws Throwable {
		addTenantIdCondition(example);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.save(..)) && args(model)")
	public Object aroundSave(ProceedingJoinPoint pjp, BaseModel model) throws Throwable {
		addTenantIdCondition(model);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.saveAll(..)) && args(models)")
	public Object aroundSaveAll(ProceedingJoinPoint pjp, Collection<BaseModel> models) throws Throwable {
		for (BaseModel model : models) {
			addTenantIdCondition(model);
		}
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.saveTreeData(..)) && args(models, idField, parentField)")
	public Object aroundSaveTreeData(ProceedingJoinPoint pjp, Collection<BaseModel> models, String idField, String parentField) throws Throwable {
		for (BaseModel model : models) {
			addTenantIdCondition(model);
		}
		return pjp.proceed();
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
		addTenantIdCondition(condition);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.queryRowCount(..)) && args(condition, extraSqlCondition, parameterValues)")
	public Object aroundQueryRowCount(ProceedingJoinPoint pjp, BaseQueryCondition condition,
			String extraSqlCondition, Object[] parameterValues) throws Throwable {
		addTenantIdCondition(condition);
		return pjp.proceed();
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
		addTenantIdCondition(parameters);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.queryRowCount(..)) && args(queryName, parameters, extraSqlCondition, parameterValues)")
	public Object aroundQueryRowCount(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters,
			String extraSqlCondition, Object[] parameterValues) throws Throwable {
		addTenantIdCondition(parameters);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.update(..)) && args(condition)")
	public Object aroundUpdate(ProceedingJoinPoint pjp, BaseUpdateCondition condition) throws Throwable {
		addTenantIdCondition(condition);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.createCommonQuery(..)) && args(modelClass)")
	public Object aroundCreateCommonQuery(ProceedingJoinPoint pjp, Class<? extends BaseModel> modelClass) throws Throwable {
		BaseModel example = modelClass.newInstance();
		addTenantIdCondition(example);
		return ((UniversalDao) pjp.getThis()).createCommonQuery(example);
	}
	
	@Around("execution(* *..dao.*Dao.createCommonQuery(..)) && args(example)")
	public Object aroundCreateCommonQuery(ProceedingJoinPoint pjp, BaseModel example) throws Throwable {
		addTenantIdCondition(example);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.createCommonQuery(..)) && args(condition, itemClass)")
	public Object aroundCreateCommonQuery(ProceedingJoinPoint pjp, BaseQueryCondition condition, Class<? extends BaseModel> itemClass) throws Throwable {
		addTenantIdCondition(condition);
		return pjp.proceed();
	}
	
	@Around("execution(* *..dao.*Dao.createCommonQuery(..)) && args(queryName, parameters)")
	public Object aroundCreateCommonQuery(ProceedingJoinPoint pjp, String queryName, Map<String, Object> parameters) throws Throwable {
		addTenantIdCondition(parameters);
		return pjp.proceed();
	}
	
	
	private void addTenantIdCondition(Object object) {
		AcegiUserDetails currentUser = SessionContext.getUser();
		if (currentUser != null) {
			String currentTenantId = currentUser.getTenantId();
			if (ParameterUtils.isParamValid(currentTenantId)) {
				CustomBeanWrapper wrapper = new CustomBeanWrapper(object);
				if (wrapper.isReadableProperty(TENANT_ID_FIELD) && wrapper.isWritableProperty(TENANT_ID_FIELD)) {
					Object currentTenantIdCondition = wrapper.getPropertyValue(TENANT_ID_FIELD);
					if (! ParameterUtils.isParamValid(currentTenantIdCondition)) {
						wrapper.setPropertyValue(TENANT_ID_FIELD, currentTenantId);
					}
				}
			}
		}
	}
	
	private void addTenantIdCondition(Map<String, Object> parameters) {
		AcegiUserDetails currentUser = SessionContext.getUser();
		if (currentUser != null) {
			String currentTenantId = currentUser.getTenantId();
			if (ParameterUtils.isParamValid(currentTenantId)) {
				Object currentTenantIdCondition = parameters.get(TENANT_ID_FIELD);
				if (! ParameterUtils.isParamValid(currentTenantIdCondition)) {
					parameters.put(TENANT_ID_FIELD, currentTenantId);
				}
			}
		}
	}
}
