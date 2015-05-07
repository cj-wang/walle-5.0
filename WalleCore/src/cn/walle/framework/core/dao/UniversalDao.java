package cn.walle.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.support.CommonQuery;
import cn.walle.framework.core.support.PagingInfo;
import cn.walle.framework.core.support.StoredProcedure;
import cn.walle.framework.core.update.BaseUpdateCondition;

/**
 * Data Access Object (DAO) interface. 
 * 
 * Implementations of this class are not intended for subclassing. You most
 * likely would want to subclass GenericDao.
 * 
 * @author cj
 *
 */
public interface UniversalDao {

	void flush();
	
	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param id
	 * @return
	 */
	<MODEL extends BaseModel> MODEL get(Class<MODEL> modelClass, Serializable id);

	/**
	 * Generic method to get an object based on class and identifier for update. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param id
	 * @return
	 */
	<MODEL extends BaseModel> MODEL getForUpdate(Class<MODEL> modelClass, Serializable id);

	/**
	 * Checks for existence of an object of type modelClass using the id arg.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param id
	 * @return
	 */
	<MODEL extends BaseModel> boolean exists(Class<MODEL> modelClass, Serializable id);

	/**
	 * Generic method used to get all objects of a particular type.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> getAll(Class<MODEL> modelClass);

	/**
	 * Generic method used to get all objects of a particular type.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param orderBy
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> getAll(Class<MODEL> modelClass, String orderBy);

	/**
	 * Generic method used to get all objects of a particular type.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param orderBy
	 * @param pagingInfo
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> getAll(Class<MODEL> modelClass, String orderBy, PagingInfo pagingInfo);

	/**
	 * Get objects count of a particular type.
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @return
	 */
	<MODEL extends BaseModel> int getRowCount(Class<MODEL> modelClass);

	/**
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param sqlCondition
	 * @param parameterValues
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues);
	
	/**
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param sqlCondition
	 * @param parameterValues
	 * @param orderBy
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues, String orderBy);
	
	/**
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param sqlCondition
	 * @param parameterValues
	 * @param orderBy
	 * @param pagingInfo
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues, String orderBy, PagingInfo pagingInfo);
	
	/**
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param sqlCondition
	 * @param parameterValues
	 * @return
	 */
	<MODEL extends BaseModel> int getRowCountBySqlCondition(Class<MODEL> modelClass,
			String sqlCondition, Object[] parameterValues);

	/**
	 * Find objects by an example model.
	 * 
	 * @param <MODEL>
	 * @param example
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findByExample(MODEL example);

	/**
	 * Find objects by an example model.
	 * 
	 * @param <MODEL>
	 * @param example
	 * @param orderBy
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findByExample(MODEL example, String orderBy);

	/**
	 * Find objects by an example model.
	 * 
	 * @param <MODEL>
	 * @param example
	 * @param orderBy
	 * @param pagingInfo
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findByExample(MODEL example, String orderBy, PagingInfo pagingInfo);

	/**
	 * Find objects by an example model.
	 * 
	 * @param <MODEL>
	 * @param example
	 * @param sqlCondition
	 * @param parameterValues
	 * @param orderBy
	 * @param pagingInfo
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> findByExample(MODEL example, String sqlCondition, Object[] parameterValues, String orderBy, PagingInfo pagingInfo);
	
	/**
	 * Get objects count by an example model.
	 * 
	 * @param <MODEL>
	 * @param example
	 * @return
	 */
	<MODEL extends BaseModel> int getRowCountByExample(MODEL example);

	/**
	 * Get objects count by an example model.
	 * 
	 * @param <MODEL>
	 * @param example
	 * @param sqlCondition
	 * @param parameterValues
	 * @return
	 */
	<MODEL extends BaseModel> int getRowCountByExample(MODEL example, String sqlCondition, Object[] parameterValues);
	
	/**
	 * Generic method to save an object - handles both update and insert.
	 * 
	 * @param <MODEL>
	 * @param model
	 * @return
	 */
	<MODEL extends BaseModel> MODEL save(MODEL model);

	/**
	 * Generic method to save objects - handles both update and insert.
	 * 
	 * @param <MODEL>
	 * @param models
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> saveAll(Collection<MODEL> models);
	
	/**
	 * 
	 * @param models
	 * @param parentField
	 * @return
	 */
	<MODEL extends BaseModel> List<MODEL> saveTreeData(Collection<MODEL> models, String idField, String parentField);

	/**
	 * Generic method to delete an object
	 * 
	 * @param <MODEL>
	 * @param model
	 */
	<MODEL extends BaseModel> void remove(MODEL model);

	/**
	 * Generic method to delete objects
	 * 
	 * @param <MODEL>
	 * @param models
	 */
	<MODEL extends BaseModel> void removeAll(Collection<MODEL> models);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param id
	 */
	<MODEL extends BaseModel> void removeByPk(Class<MODEL> modelClass, Serializable id);

	/**
	 * Generic method to delete objects based on class and ids
	 * 
	 * @param <MODEL>
	 * @param modelClass
	 * @param ids
	 */
	<MODEL extends BaseModel> void removeAllByPk(Class<MODEL> modelClass, Collection<? extends Serializable> ids);

	/**
	 * Type safe delegate method for query.query()
	 * 
	 * @param <ITEM>
	 * @param condition
	 * @param itemClass
	 * @return  List of itemClass type
	 */
	<ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass);
	
	/**
	 * Type safe delegate method for query.query()
	 * 
	 * @param <ITEM>
	 * @param condition
	 * @param itemClass
	 * @param orderBy
	 * @return  List of itemClass type
	 */
	<ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass, String orderBy);
	
	/**
	 * Type safe delegate method for query.query()
	 * 
	 * @param <ITEM>
	 * @param condition
	 * @param itemClass
	 * @param pagingInfo
	 * @return  List of itemClass type
	 */
	<ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass, PagingInfo pagingInfo);

	/**
	 * Type safe delegate method for query.query()
	 * 
	 * @param <ITEM>
	 * @param condition
	 * @param itemClass
	 * @param orderBy
	 * @param pagingInfo
	 * @return  List of itemClass type
	 */
	<ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass,
			String orderBy, PagingInfo pagingInfo);
	
	/**
	 * Type safe delegate method for query.query()
	 * 
	 * @param <ITEM>
	 * @param condition
	 * @param itemClass
	 * @param extraSqlCondition
	 * @param parameterValues
	 * @param orderBy
	 * @param pagingInfo
	 * @return
	 */
	<ITEM extends BaseModel> List<ITEM> query(
			BaseQueryCondition condition, Class<ITEM> itemClass,
			String extraSqlCondition, Object[] parameterValues,
			String orderBy, PagingInfo pagingInfo);
	
	/**
	 * Get row count by query
	 * 
	 * @param <ITEM>
	 * @param condition
	 * @param itemClass
	 * @param extraSqlCondition
	 * @param parameterValues
	 * @return
	 */
	<ITEM extends BaseModel> int queryRowCount(
			BaseQueryCondition condition, String extraSqlCondition, Object[] parameterValues);
	
	
	List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters);
	
	List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters, String orderBy);
	
	List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters, PagingInfo pagingInfo);

	List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters,
			String orderBy, PagingInfo pagingInfo);
	
	List<DynamicModelClass> query(
			String queryName, Map<String, Object> parameters,
			String extraSqlCondition, Object[] parameterValues,
			String orderBy, PagingInfo pagingInfo);
	
	int queryRowCount(
			String queryName, Map<String, Object> parameters, String extraSqlCondition, Object[] parameterValues);
	
	/**
	 * Delegate method for update.update()
	 * 
	 * @param condition
	 * @return
	 */
	int update(BaseUpdateCondition condition);

	/**
	 * get sequence value
	 * @param sequenceName
	 * @return
	 */
	long getSequenceValue(String sequenceName);
	
	Date getSysDate();
	
	<MODEL extends BaseModel> CommonQuery<MODEL> createCommonQuery(Class<MODEL> modelClass);
	
	<MODEL extends BaseModel> CommonQuery<MODEL> createCommonQuery(MODEL example);

	<QUERYITEM extends BaseModel> CommonQuery<QUERYITEM> createCommonQuery(
			BaseQueryCondition condition, Class<QUERYITEM> itemClass);
	
	CommonQuery createCommonQuery(
			String queryName, Map<String, Object> parameters);
	
	StoredProcedure storedProcedure(String name);
	
}