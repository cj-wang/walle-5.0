package cn.walle.framework.core.dao.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import cn.walle.framework.core.dao.UniversalDao;
import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.model.OperationLog;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.query.BaseQueryItem;
import cn.walle.framework.core.support.CommonQuery;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.framework.core.support.StoredProcedure;
import cn.walle.framework.core.support.hibernate.DynamicModelClassResultTransformer;
import cn.walle.framework.core.support.springsecurity.AcegiUserDetails;
import cn.walle.framework.core.support.springsecurity.SessionContext;
import cn.walle.framework.core.update.BaseUpdateCondition;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.EntityUtils;
import cn.walle.framework.core.util.ParameterUtils;
import cn.walle.framework.core.util.SqlUtils;

/**
 * This class serves as the a class that can CRUD any object without any
 * Spring configuration.
 * 
 * @author cj
 *
 */
@Repository
public class UniversalDaoImpl implements UniversalDao {

	@Autowired
	private SessionFactory sessionFactory;

	
	private Date dbDate = null;
	private Long timeForLastDbDate = -1L;
	
	
	public void flush() {
		this.sessionFactory.getCurrentSession().flush();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> MODEL get(Class<MODEL> modelClass, Serializable id) {
		Class<? extends BaseModel> rootModelClass = EntityUtils.getEntityClass(modelClass);
		id = convertIdTypeIfNecessary(rootModelClass, id);
		Object entity = this.sessionFactory.getCurrentSession().get(rootModelClass, id);
		if (entity == null) {
			throw new ObjectRetrievalFailureException(modelClass, id);
		}
		evict(entity);
		return EntityUtils.convertEntityType(entity, modelClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> MODEL getForUpdate(Class<MODEL> modelClass, Serializable id) {
		Class<? extends BaseModel> rootModelClass = EntityUtils.getEntityClass(modelClass);
		id = convertIdTypeIfNecessary(rootModelClass, id);
		Object entity = this.sessionFactory.getCurrentSession().get(rootModelClass, id, LockOptions.UPGRADE);
		if (entity == null) {
			throw new ObjectRetrievalFailureException(modelClass, id);
		}
		evict(entity);
		return EntityUtils.convertEntityType(entity, modelClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> boolean exists(Class<MODEL> modelClass, Serializable id) {
		Class<? extends BaseModel> rootModelClass = EntityUtils.getEntityClass(modelClass);
		id = convertIdTypeIfNecessary(rootModelClass, id);
		Object entity = this.sessionFactory.getCurrentSession().get(rootModelClass, id);
		return entity != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> getAll(Class<MODEL> modelClass) {
		return this.getAll(modelClass, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> getAll(Class<MODEL> modelClass, String orderBy) {
		return this.getAll(modelClass, orderBy, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> getAll(Class<MODEL> modelClass, String orderBy, PagingInfo pagingInfo) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(EntityUtils.getEntityClass(modelClass));
		if (pagingInfo != null) {
			criteria.setProjection(Projections.rowCount());
			pagingInfo.setTotalRows(((Long) criteria.uniqueResult()).intValue());
			criteria.setProjection(null);
			setPagingInfo(criteria, pagingInfo);
		}
		addOrderBy(criteria, orderBy);
		List<?> queryResult = criteria.list();
		List<MODEL> result = new ArrayList<MODEL>();
		for (Object entity : queryResult) {
			evict(entity);
			result.add(EntityUtils.convertEntityType(entity, modelClass));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> int getRowCount(Class<MODEL> modelClass) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(EntityUtils.getEntityClass(modelClass));
	    criteria.setProjection(Projections.rowCount());
	    return ((Long) criteria.uniqueResult()).intValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues) {
		return this.findBySqlCondition(modelClass, sqlCondition, parameterValues, null, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues, String orderBy) {
		return this.findBySqlCondition(modelClass, sqlCondition, parameterValues, orderBy, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues, String orderBy, PagingInfo pagingInfo) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(EntityUtils.getEntityClass(modelClass));
		addSqlCondtion(criteria, sqlCondition, parameterValues);
		if (pagingInfo != null) {
			criteria.setProjection(Projections.rowCount());
			pagingInfo.setTotalRows(((Long) criteria.uniqueResult()).intValue());
			criteria.setProjection(null);
			setPagingInfo(criteria, pagingInfo);
		}
		addOrderBy(criteria, orderBy);
		List<?> queryResult = criteria.list();
		List<MODEL> result = new ArrayList<MODEL>();
		for (Object entity : queryResult) {
			evict(entity);
			result.add(EntityUtils.convertEntityType(entity, modelClass));
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> int getRowCountBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(EntityUtils.getEntityClass(modelClass));
		addSqlCondtion(criteria, sqlCondition, parameterValues);
	    criteria.setProjection(Projections.rowCount());
	    return ((Long) criteria.uniqueResult()).intValue();
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findByExample(MODEL example) {
		return this.findByExample(example, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findByExample(MODEL example, String orderBy) {
		return this.findByExample(example, orderBy, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findByExample(MODEL example, String orderBy, PagingInfo pagingInfo) {
		return this.findByExample(example, null, null, orderBy, pagingInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> findByExample(MODEL example, String sqlCondition, Object[] parameterValues, String orderBy, PagingInfo pagingInfo) {
		String entityName = EntityUtils.getEntityClass(example.getClass()).getName();
		Criteria criteria = entityName != null ?
				this.sessionFactory.getCurrentSession().createCriteria(entityName) :
					this.sessionFactory.getCurrentSession().createCriteria(example.getClass());
		addExample(criteria, example);
		addSqlCondtion(criteria, sqlCondition, parameterValues);
		if (pagingInfo != null) {
			criteria.setProjection(Projections.rowCount());
			pagingInfo.setTotalRows(((Long) criteria.uniqueResult()).intValue());
			criteria.setProjection(null);
			setPagingInfo(criteria, pagingInfo);
		}
		addOrderBy(criteria, orderBy);
		List<?> queryResult = criteria.list();
		List<MODEL> result = new ArrayList<MODEL>();
		for (Object entity : queryResult) {
			evict(entity);
			result.add(EntityUtils.convertEntityType(entity, (Class<MODEL>) example.getClass()));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> int getRowCountByExample(MODEL example) {
		return this.getRowCountByExample(example, null, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> int getRowCountByExample(MODEL example, String sqlCondition, Object[] parameterValues) {
		String entityName = EntityUtils.getEntityClass(example.getClass()).getName();
		Criteria criteria = entityName != null ?
				this.sessionFactory.getCurrentSession().createCriteria(entityName) :
					this.sessionFactory.getCurrentSession().createCriteria(example.getClass());
		addExample(criteria, example);
		addSqlCondtion(criteria, sqlCondition, parameterValues);
		criteria.setProjection(Projections.rowCount());
	    return ((Long) criteria.uniqueResult()).intValue();
	}

	
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> MODEL save(MODEL model) {
		try {
			if (BaseModel.ROW_STATE_DELETED.equals(model.getRowState())) {
				this.remove(model);
				return null;
			} else {
				if (model.validFields().size() != 0
						&& model.validFields().size() != EntityUtils.getFieldCount(model.getClass())) {
					Serializable id = EntityUtils.getId(model);
					if (id != null) {
						Object persistantModel = this.sessionFactory.getCurrentSession().get(
								EntityUtils.getEntityClass(model.getClass()), id);
						if (persistantModel != null) {
							evict(persistantModel);
							new CustomBeanWrapper(model).copyPropertiesTo(persistantModel, model.validFields());
							if (model.getClass() == persistantModel.getClass()) {
								model = (MODEL) persistantModel;
							} else {
								BeanUtils.copyProperties(persistantModel, model);
							}
						}
					}
				}
				if (model instanceof OperationLog) {
					AcegiUserDetails userDetails = SessionContext.getUser();
					String userID = userDetails == null ? null : userDetails.getUserId();
					Date sysDate = getSysDate();
					OperationLog ol = (OperationLog) model;
					if (ol.getCreator() == null && ol.getCreateTime() == null) {
						ol.setCreator(userID);
						ol.setCreateTime(sysDate);
					} else {
						ol.setModifier(userID);
						ol.setModifyTime(sysDate);
					}
				}
				Object entity = this.sessionFactory.getCurrentSession().merge(EntityUtils.getEntityClass(model.getClass()).getName(), model);
				flush();
				evict(entity);
				if (model.getClass() == entity.getClass()) {
					return (MODEL) entity;
				} else {
					BeanUtils.copyProperties(entity, model);
					return model;
				}
			}
		} catch (StaleObjectStateException sosex) {
			throw new ApplicationException("Data has been modified by another user. Please reload and try again.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> saveAll(Collection<MODEL> models) {
		try {
			List<MODEL> modelsToDelete = new ArrayList<MODEL>();
			List<MODEL> modelsToMerge = new ArrayList<MODEL>();
			for (MODEL model : models) {
				if (BaseModel.ROW_STATE_DELETED.equals(model.getRowState())) {
					modelsToDelete.add(model);
				} else {
					if (model.validFields().size() != 0
							&& model.validFields().size() != EntityUtils.getFieldCount(model.getClass())) {
						Serializable id = EntityUtils.getId(model);
						if (id != null) {
							Object persistantModel = this.sessionFactory.getCurrentSession().get(
									EntityUtils.getEntityClass(model.getClass()), id);
							if (persistantModel != null) {
								evict(persistantModel);
								new CustomBeanWrapper(model).copyPropertiesTo(persistantModel, model.validFields());
								if (model.getClass() == persistantModel.getClass()) {
									model = (MODEL) persistantModel;
								} else {
									BeanUtils.copyProperties(persistantModel, model);
								}
							}
						}
					}
					modelsToMerge.add(model);
				}
			}
			this.removeAll(modelsToDelete);
			flush();
			if (modelsToMerge.size() == 0) {
				return new ArrayList<MODEL>();
			} else {
				if (modelsToMerge.iterator().next() instanceof OperationLog) {
					AcegiUserDetails userDetails = SessionContext.getUser();
					String userID = userDetails == null ? null : userDetails.getUserId();
					Date sysDate = getSysDate();
					for (MODEL model : modelsToMerge) {
						OperationLog ol = (OperationLog) model;
						if (ol.getCreator() == null && ol.getCreateTime() == null) {
							ol.setCreator(userID);
							ol.setCreateTime(sysDate);
						} else {
							ol.setModifier(userID);
							ol.setModifyTime(sysDate);
						}
					}
				}
				String entityName = EntityUtils.getEntityClass(modelsToMerge.iterator().next().getClass()).getName();
				List<Object> mergeResult = new ArrayList<Object>();
				for (MODEL model : modelsToMerge) {
					mergeResult.add(this.sessionFactory.getCurrentSession().merge(entityName, model));
				}
				flush();
				List<MODEL> result = new ArrayList<MODEL>();
				for (int i = 0; i < modelsToMerge.size(); i++) {
					MODEL model = modelsToMerge.get(i);
					Object entity = mergeResult.get(i);
					evict(entity);
					if (model.getClass() == entity.getClass()) {
						result.add((MODEL) entity);
					} else {
						BeanUtils.copyProperties(entity, model);
						result.add(model);
					}
				}
				return result;
			}
		} catch (StaleObjectStateException sosex) {
			throw new ApplicationException("Data has been modified by another user. Please reload and try again.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> List<MODEL> saveTreeData(Collection<MODEL> models, String idField, String parentField) {
		try {
			List<MODEL> modelsToDelete = new ArrayList<MODEL>();
			List<MODEL> modelsToMerge = new ArrayList<MODEL>();
			for (MODEL model : models) {
				if (BaseModel.ROW_STATE_DELETED.equals(model.getRowState())) {
					modelsToDelete.add(model);
				} else {
					if (model.validFields().size() != 0
							&& model.validFields().size() != EntityUtils.getFieldCount(model.getClass())) {
						Serializable id = EntityUtils.getId(model);
						if (id != null) {
							Object persistantModel = this.sessionFactory.getCurrentSession().get(
									EntityUtils.getEntityClass(model.getClass()), id);
							if (persistantModel != null) {
								evict(persistantModel);
								new CustomBeanWrapper(model).copyPropertiesTo(persistantModel, model.validFields());
								if (model.getClass() == persistantModel.getClass()) {
									model = (MODEL) persistantModel;
								} else {
									BeanUtils.copyProperties(persistantModel, model);
								}
							}
						}
					}
					modelsToMerge.add(model);
				}
			}
			this.removeAll(modelsToDelete);
			flush();
			if (modelsToMerge.size() == 0) {
				return new ArrayList<MODEL>();
			} else {
				if (modelsToMerge.iterator().next() instanceof OperationLog) {
					AcegiUserDetails userDetails = SessionContext.getUser();
					String userID = userDetails == null ? null : userDetails.getUserId();
					Date sysDate = getSysDate();
					for (MODEL model : modelsToMerge) {
						OperationLog ol = (OperationLog) model;
						if (ol.getCreator() == null && ol.getCreateTime() == null) {
							ol.setCreator(userID);
							ol.setCreateTime(sysDate);
						} else {
							ol.setModifier(userID);
							ol.setModifyTime(sysDate);
						}
					}
				}
				String entityName = EntityUtils.getEntityClass(modelsToMerge.iterator().next().getClass()).getName();
				List<MODEL> mergeResult = new ArrayList<MODEL>();
				for (int i = 0; i < modelsToMerge.size(); i++) {
					MODEL model = modelsToMerge.get(i);
					Object oldId = new CustomBeanWrapper(model).getPropertyValue(idField);
					MODEL entity = (MODEL) this.sessionFactory.getCurrentSession().merge(entityName, model);
					mergeResult.add(entity);
					Object newId = new CustomBeanWrapper(entity).getPropertyValue(idField);
					if (oldId != null && ! oldId.equals(newId)) {
						for (int j = i + 1; j < modelsToMerge.size(); j++) {
							if (oldId.equals(new CustomBeanWrapper(modelsToMerge.get(j)).getPropertyValue(parentField))) {
								new CustomBeanWrapper(modelsToMerge.get(j)).setPropertyValue(parentField, newId);
							}
						}
					}
				}
				flush();
				List<MODEL> result = new ArrayList<MODEL>();
				for (int i = 0; i < modelsToMerge.size(); i++) {
					MODEL model = modelsToMerge.get(i);
					Object entity = mergeResult.get(i);
					evict(entity);
					if (model.getClass() == entity.getClass()) {
						result.add((MODEL) entity);
					} else {
						BeanUtils.copyProperties(entity, model);
						result.add(model);
					}
				}
				return result;
			}
		} catch (StaleObjectStateException sosex) {
			throw new ApplicationException("Data has been modified by another user. Please reload and try again.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> void remove(MODEL model) {
		try {
			this.sessionFactory.getCurrentSession().delete(EntityUtils.getEntityClass(model.getClass()).getName(), model);
			flush();
		} catch (StaleObjectStateException sosex) {
			throw new ApplicationException("Data has been modified by another user. Please reload and try again.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> void removeAll(Collection<MODEL> models) {
		try {
			if (models.size() == 0) {
				return;
			} else {
				String entityName = EntityUtils.getEntityClass(models.iterator().next().getClass()).getName();
				for (MODEL model : models) {
					this.sessionFactory.getCurrentSession().delete(entityName, model);
				}
				flush();
			}
		} catch (StaleObjectStateException sosex) {
			throw new ApplicationException("Data has been modified by another user. Please reload and try again.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> void removeByPk(Class<MODEL> modelClass, Serializable id) {
		this.remove(this.get(modelClass, id));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <MODEL extends BaseModel> void removeAllByPk(Class<MODEL> modelClass, Collection<? extends Serializable> ids) {
		List<MODEL> models = new ArrayList<MODEL>();
		for (Serializable id : ids) {
			models.add(this.get(modelClass, id));
		}
		this.removeAll(models);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass) {
		return this.query(condition, itemClass, null, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass, String orderBy) {
		return this.query(condition, itemClass, orderBy, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass, PagingInfo pagingInfo) {
		return this.query(condition, itemClass, null, pagingInfo);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public <ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass,
			String orderBy, PagingInfo pagingInfo) {
		return this.query(condition, itemClass, null, null, orderBy, pagingInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	public <ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass,
			String extraSqlCondition, Object[] parameterValues,
			String orderBy, PagingInfo pagingInfo) {
		Class<?> resultEntityClass = EntityUtils.getEntityClass(itemClass);
		if (! BaseModel.class.isAssignableFrom(resultEntityClass)
				|| BaseModel.class.equals(resultEntityClass)
				|| BaseQueryItem.class.equals(resultEntityClass)) {
			resultEntityClass = EntityUtils.getQueryItemClass(condition);
		}
		
		String sql = SqlUtils.getDynamicNamedSql(EntityUtils.getSqlQueryName(condition), condition);
		
		String sqlCondition = convertOrdinalParametersToNamedParameters(extraSqlCondition);
		String runSql = SqlUtils.addExtraConditions(sql, sqlCondition);
		runSql = SqlUtils.addDataFilterConditions(runSql);

		Dialect dialect = ((SessionFactoryImplementor) this.sessionFactory).getDialect();
		String selectGuidString = dialect.getSelectGUIDString();
		selectGuidString = selectGuidString.replace("select ", "");
		selectGuidString = selectGuidString.replace(" from dual", "");
		runSql = "select T__UUID__.*, " + selectGuidString + " as UUID__ from (" + runSql + ") T__UUID__";
		
		runSql = addOrderBy(runSql, orderBy);
		
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(runSql);
		if (parameterValues != null) {
			queryObject.setProperties(convertOrdinalParametersToNamedParameters(parameterValues));
		}
		queryObject.setProperties(convertSqlParameterValueBeanToNamedParameters(condition));
		queryObject.addEntity(resultEntityClass);
		if (pagingInfo != null) {
			pagingInfo.setTotalRows(this.queryRowCount(condition, extraSqlCondition, parameterValues));
			setPagingInfo(queryObject, pagingInfo);
		}
		List<Object> queryResult = queryObject.list();
		for (int i = 0; i < queryResult.size(); i++) {
			Object resultItem = queryResult.get(i);
			if (resultItem instanceof BaseQueryItem) {
				((BaseQueryItem) resultItem).setRownum(i + 1);
			}
		}

		List<ITEM> result = new ArrayList<ITEM>();
		for (Object entity : queryResult) {
			evict(entity);
			result.add(EntityUtils.convertEntityType(entity, itemClass));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public <ITEM extends BaseModel> int queryRowCount(
			BaseQueryCondition condition, String extraSqlCondition, Object[] parameterValues) {
		String sql = SqlUtils.getDynamicNamedSql(EntityUtils.getSqlQueryName(condition), condition);
		String sqlCondition = convertOrdinalParametersToNamedParameters(extraSqlCondition);
		String runSql = SqlUtils.addExtraConditions(sql, sqlCondition);
		runSql = SqlUtils.addDataFilterConditions(runSql);

		runSql = "select count(*) as COUNT__ from (" + runSql + ") T__COUNT__";
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(runSql);
		if (parameterValues != null) {
			queryObject.setProperties(convertOrdinalParametersToNamedParameters(parameterValues));
		}
		queryObject.setProperties(convertSqlParameterValueBeanToNamedParameters(condition));
		queryObject.addScalar("COUNT__", StandardBasicTypes.INTEGER);
	    return (Integer) queryObject.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters) {
		return this.query(queryName, parameters, null, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters, String orderBy) {
		return this.query(queryName, parameters, orderBy, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters, PagingInfo pagingInfo) {
		return this.query(queryName, parameters, null, pagingInfo);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters,
			String orderBy, PagingInfo pagingInfo) {
		return this.query(queryName, parameters, null, null, orderBy, pagingInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters,
			String extraSqlCondition, Object[] parameterValues,
			String orderBy, PagingInfo pagingInfo) {
		String sql = SqlUtils.getDynamicNamedSql(queryName, parameters);
		
		String sqlCondition = convertOrdinalParametersToNamedParameters(extraSqlCondition);
		String runSql = SqlUtils.addExtraConditions(sql, sqlCondition);
		runSql = SqlUtils.addDataFilterConditions(runSql);

		Dialect dialect = ((SessionFactoryImplementor) this.sessionFactory).getDialect();
		String selectGuidString = dialect.getSelectGUIDString();
		selectGuidString = selectGuidString.replace("select ", "");
		selectGuidString = selectGuidString.replace(" from dual", "");
		runSql = "select T__UUID__.*, " + selectGuidString + " as UUID__ from (" + runSql + ") T__UUID__";
		
		runSql = addOrderBy(runSql, orderBy);
		
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(runSql);
		if (parameterValues != null) {
			queryObject.setProperties(convertOrdinalParametersToNamedParameters(parameterValues));
		}
		queryObject.setProperties(parameters);
		queryObject.setResultTransformer(DynamicModelClassResultTransformer.getInstance());
		if (pagingInfo != null) {
			pagingInfo.setTotalRows(this.queryRowCountBySql(sql, parameters, extraSqlCondition, parameterValues));
			setPagingInfo(queryObject, pagingInfo);
		}
		List<DynamicModelClass> queryResult = queryObject.list();
		for (int i = 0; i < queryResult.size(); i++) {
			DynamicModelClass resultItem = queryResult.get(i);
			resultItem.put("rownum", i + 1);
		}

		return queryResult;
	}

	/**
	 * {@inheritDoc}
	 */
	public int queryRowCount(
			String queryName, Map<String, Object> parameters, String extraSqlCondition, Object[] parameterValues) {
		String sql = SqlUtils.getDynamicNamedSql(queryName, parameters);
		return this.queryRowCountBySql(sql, parameters, extraSqlCondition, parameterValues);
	}
	
	private int queryRowCountBySql(
			String sql, Map<String, Object> parameters, String extraSqlCondition, Object[] parameterValues) {
		String sqlCondition = convertOrdinalParametersToNamedParameters(extraSqlCondition);
		String runSql = SqlUtils.addExtraConditions(sql, sqlCondition);
		runSql = SqlUtils.addDataFilterConditions(runSql);

		runSql = "select count(*) as COUNT__ from (" + runSql + ") T__COUNT__";
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(runSql);
		if (parameterValues != null) {
			queryObject.setProperties(convertOrdinalParametersToNamedParameters(parameterValues));
		}
		queryObject.setProperties(parameters);
		queryObject.addScalar("COUNT__", StandardBasicTypes.INTEGER);
	    return (Integer) queryObject.uniqueResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int update(BaseUpdateCondition condition) {
		String sql = SqlUtils.getNamedSql(EntityUtils.getSqlUpdateName(condition));
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		queryObject.setProperties(condition);
		return queryObject.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public long getSequenceValue(String sequenceName) {
		Dialect dialect = ((SessionFactoryImplementor) this.sessionFactory).getDialect();
		String sql = dialect.getSequenceNextValString(sequenceName);
		int fromIndex = sql.indexOf(" from ");
		if (fromIndex == -1) {
			sql = sql + " as SEQ__";
		} else {
			sql = sql.substring(0, fromIndex) + " as SEQ__" + sql.substring(fromIndex);
		}
		SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		queryObject.addScalar("SEQ__", StandardBasicTypes.LONG);
		return (Long) queryObject.uniqueResult();
	}
	
	public Date getSysDate() {
		if (System.currentTimeMillis() - this.timeForLastDbDate > 1000) {
			synchronized (this.timeForLastDbDate) {
				if (System.currentTimeMillis() - this.timeForLastDbDate > 1000) {
					Dialect dialect = ((SessionFactoryImplementor) this.sessionFactory).getDialect();
					String sql = dialect.getCurrentTimestampSelectString();
					int fromIndex = sql.indexOf(" from ");
					if (fromIndex == -1) {
						sql = sql + " as SYSDATE__";
					} else {
						sql = sql.substring(0, fromIndex) + " as SYSDATE__" + sql.substring(fromIndex);
					}
					SQLQuery queryObject = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
					queryObject.addScalar("SYSDATE__", StandardBasicTypes.TIMESTAMP);
					this.dbDate = new Date(((Timestamp) queryObject.uniqueResult()).getTime());
					this.timeForLastDbDate = System.currentTimeMillis();
				}
			}
		}
		return this.dbDate;
	}
	
	public <MODEL extends BaseModel> CommonQuery<MODEL> createCommonQuery(Class<MODEL> modelClass) {
		return new CommonQuery<MODEL>(ContextUtils.getBeanOfType(UniversalDao.class), modelClass);
	}
	
	public <MODEL extends BaseModel> CommonQuery<MODEL> createCommonQuery(MODEL example) {
		return new CommonQuery<MODEL>(ContextUtils.getBeanOfType(UniversalDao.class), example);
	}
	
	public <QUERYITEM extends BaseModel> CommonQuery<QUERYITEM> createCommonQuery(
			BaseQueryCondition queryCondition, Class<QUERYITEM> queryItemClass) {
		return new CommonQuery<QUERYITEM>(ContextUtils.getBeanOfType(UniversalDao.class), queryCondition, queryItemClass);
	}

	public CommonQuery createCommonQuery(
			String queryName, Map<String, Object> parameters) {
		return new CommonQuery<BaseModel>(ContextUtils.getBeanOfType(UniversalDao.class), queryName, parameters);
	}
	
	public StoredProcedure storedProcedure(String name) {
		return new StoredProcedure(SessionFactoryUtils.getDataSource(this.sessionFactory), name);
	}
	
	
	private void evict(Object entity) {
		this.sessionFactory.getCurrentSession().evict(entity);
	}
	
	private Serializable convertIdTypeIfNecessary(Class<? extends BaseModel> modelClass, Serializable id) {
		if (id.getClass() == Integer.class && EntityUtils.getIdFieldType(modelClass) == Long.class) {
			return ((Integer) id).longValue();
		}
		return id;
	}
	
	private void addExample(Criteria criteria, Object exampleEntity) {
		CustomBeanWrapper exampleWrapper = new CustomBeanWrapper(exampleEntity);
		PropertyDescriptor[] propertyDescriptors = exampleWrapper.getPropertyDescriptors();
		List<String> fieldNames = EntityUtils.getFieldNames((Class<? extends BaseModel>) exampleEntity.getClass());
		for (String fieldName : fieldNames) {
			Object value = exampleWrapper.getPropertyValue(fieldName);
			if (ParameterUtils.isParamValid(value)) {
				criteria.add(Restrictions.eq(fieldName, value));
			}
		}
	}

	private void addSqlCondtion(Criteria criteria, String sqlCondition, Object[] parameterValues) {
		if (sqlCondition == null || sqlCondition.trim().length() == 0) {
			return;
		}
		if (parameterValues == null || parameterValues.length == 0) {
			criteria.add(Restrictions.sqlRestriction(sqlCondition));
		} else {
			boolean hasArrayParameters = false;
			for (Object value : parameterValues) {
				if (value != null && value.getClass().isArray()) {
					hasArrayParameters = true;
					break;
				}
			}
			if (hasArrayParameters) {
				int[] parameterLocations = SqlUtils.getOrdinalParameterLocations(sqlCondition);
				StringBuilder sqlConditionSb = new StringBuilder(sqlCondition);
				List<Object> parameterValuesList = new ArrayList<Object>();
				for (int i = parameterValues.length - 1; i >= 0; i--) {
					Object value = parameterValues[i];
					if (value != null && value.getClass().isArray()
							&& Array.getLength(value) == 0) {
						value = null;
					}
					if (value != null && value.getClass().isArray()) {
						int size = Array.getLength(value);
						if (size > 1) {
							sqlConditionSb.insert(parameterLocations[i], StringUtils.repeat("?, ", size - 1));
						}
						for (int j = size - 1; j >= 0; j--) {
							parameterValuesList.add(0, Array.get(value, j));
						}
					} else {
						parameterValuesList.add(0, value);
					}
				}
				parameterValues = parameterValuesList.toArray();
				sqlCondition = sqlConditionSb.toString();
			}
			
			Type[] parameterTypes = this.getParameterTypes(parameterValues);
			criteria.add(Restrictions.sqlRestriction(sqlCondition, parameterValues, parameterTypes));
		}
	}
	
	private Type[] getParameterTypes(Object[] parameterValues) {
		Type[] parameterTypes = new Type[parameterValues.length];
		for (int i = 0; i < parameterValues.length; i++) {
			Object value = parameterValues[i];
			if (value instanceof String) {
				parameterTypes[i] = StandardBasicTypes.STRING;
			} else if (value instanceof Double) {
				parameterTypes[i] = StandardBasicTypes.DOUBLE;
			} else if (value instanceof Integer) {
				parameterTypes[i] = StandardBasicTypes.INTEGER;
			} else if (value instanceof Long) {
				parameterTypes[i] = StandardBasicTypes.LONG;
			} else if (value instanceof Date) {
				parameterTypes[i] = StandardBasicTypes.TIMESTAMP;
			} else {
				parameterTypes[i] = StandardBasicTypes.STRING;
			}
		}
		return parameterTypes;
	}
	
	private static final String NAMED_PARAMETER_PREFIX = "$param$";
	
	private String convertOrdinalParametersToNamedParameters(String sql) {
		if (sql == null || sql.trim().length() == 0) {
			return sql;
		}
		int[] ordinalParameterLocations = SqlUtils.getOrdinalParameterLocations(sql);
		StringBuilder resultSql = new StringBuilder(sql);
		for (int i = ordinalParameterLocations.length - 1; i >= 0; i--) {
			int location = ordinalParameterLocations[i];
			resultSql.delete(location, location + 1);
			resultSql.insert(location, ":" + NAMED_PARAMETER_PREFIX + i);
		}
		return resultSql.toString();		
	}
	
	private Map<String, Object> convertOrdinalParametersToNamedParameters(Object[] parameterValues) {
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		for (int i = 0; i < parameterValues.length; i++) {
			Object value = parameterValues[i];
			if (value == null) {
				value = "";
			}
			if (value.getClass().isArray()) {
				List<Object> notNullArrayItems = new ArrayList<Object>();
				for (int j = 0; j < Array.getLength(value); j++) {
					Object itemValue = Array.get(value, j);
					if (itemValue != null) {
						notNullArrayItems.add(itemValue);
					}
				}
				value = notNullArrayItems.toArray();
				if (Array.getLength(value) == 0) {
					value = "";
				}
			}
			namedParameters.put(NAMED_PARAMETER_PREFIX + i, value);
		}
		return namedParameters;
	}
	
	private Map<String, Object> convertSqlParameterValueBeanToNamedParameters(Object valueBean) {
		CustomBeanWrapper beanWrapper = new CustomBeanWrapper(valueBean);
		PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			if ("class".equals(propertyName)) {
				continue;
			}
			Object value = beanWrapper.getPropertyValue(propertyName);
			if (value == null) {
				value = "";
			}
			if (value.getClass().isArray()) {
				List<Object> notNullArrayItems = new ArrayList<Object>();
				for (int i = 0; i < Array.getLength(value); i++) {
					Object itemValue = Array.get(value, i);
					if (itemValue != null) {
						notNullArrayItems.add(itemValue);
					}
				}
				value = notNullArrayItems.toArray();
				if (Array.getLength(value) == 0) {
					value = "";
				}
			}
			namedParameters.put(propertyName, value);
		}
		return namedParameters;
	}
	
	private void addOrderBy(Criteria criteria, String orderBy) {
		if (orderBy != null && orderBy.trim().length() != 0) {
			Order[] orders = SqlUtils.parseOrderByToHibernateOrders(orderBy);
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
	}
	
	private String addOrderBy(String sql, String orderBy) {
		if (orderBy != null && orderBy.trim().length() != 0) {
			return "select * from (" + sql + ") T__ORDER__ order by " + SqlUtils.parseOrderByToSqlStyle(orderBy);
		} else {
			return sql;
		}
	}
	
	private void setPagingInfo(Criteria criteria, PagingInfo pagingInfo) {
		if (pagingInfo != null) {
			if (pagingInfo.getPageSize() <= 0) {
				pagingInfo.setPageSize(10);
			}
			if (pagingInfo.getCurrentPage() > pagingInfo.getTotalPages()) {
				pagingInfo.setCurrentPage(pagingInfo.getTotalPages());
			}
			if (pagingInfo.getCurrentPage() <= 0) {
				pagingInfo.setCurrentPage(1);
			}
			criteria.setFirstResult(pagingInfo.getCurrentRow());
			criteria.setMaxResults(pagingInfo.getPageSize());
		}
	}
	
	private void setPagingInfo(Query queryObject, PagingInfo pagingInfo) {
		if (pagingInfo != null) {
			if (pagingInfo.getPageSize() <= 0) {
				pagingInfo.setPageSize(10);
			}
			if (pagingInfo.getCurrentPage() > pagingInfo.getTotalPages()) {
				pagingInfo.setCurrentPage(pagingInfo.getTotalPages());
			}
			if (pagingInfo.getCurrentPage() <= 0) {
				pagingInfo.setCurrentPage(1);
			}
			queryObject.setFirstResult(pagingInfo.getCurrentRow());
			queryObject.setMaxResults(pagingInfo.getPageSize());
		}
	}
	
}
