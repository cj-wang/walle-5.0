package cn.walle.framework.core.util;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.beans.BeanUtils;

import cn.walle.framework.common.support.QueryDataSource;
import cn.walle.framework.core.model.BaseModel;
import cn.walle.framework.core.query.BaseQueryCondition;
import cn.walle.framework.core.query.BaseQueryItem;
import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.update.BaseUpdateCondition;

public class EntityUtils {
	
	public static Class<? extends BaseModel> getEntityClass(String entityName) throws Exception {
		String entityClassName = entityName + "Model";
		List<Class<?>> modelClasses = ClassUtils.getModelClasses();
		for (Class<?> clazz : modelClasses) {
			if (clazz.getSimpleName().equals(entityClassName)) {
				return (Class<? extends BaseModel>) clazz;
			}
		}
		return null;
	}

	public static Class<? extends BaseModel> getEntityClass(Class<? extends BaseModel> entityClass) {
		Class<?> clazz = entityClass;
		while (clazz != null && ! clazz.isAnnotationPresent(Entity.class)) {
			clazz = clazz.getSuperclass();
		}
		if (clazz == null) {
			return entityClass;
		} else {
			return (Class<? extends BaseModel>) clazz;
		}
	}
	
	public static <TYPE extends BaseModel> TYPE convertEntityType(Object entity, Class<TYPE> type) {
		if (type.isAssignableFrom(entity.getClass())) {
			return (TYPE) entity;
		} else {
			try {
				TYPE result = type.newInstance();
				BeanUtils.copyProperties(entity, result);
				return result;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public static String getTableName(Class<? extends BaseModel> entityClass) {
		entityClass = getEntityClass(entityClass);
		if (entityClass.isAnnotationPresent(Table.class)) {
			return entityClass.getAnnotation(Table.class).name();
		} else {
			return null;
		}
	}
	
	public static int getFieldCount(Class<? extends BaseModel> entityClass) {
		int count = 0;
		for (Method method : entityClass.getMethods()) {
			if (method.isAnnotationPresent(Column.class)) {
				count++;
			}
		}
		return count;
	}
	
	public static List<String> getFieldNames(Class<? extends BaseModel> entityClass) {
		List<String> fieldNames = new ArrayList<String>();
		CustomBeanWrapper beanWrapper = new CustomBeanWrapper(entityClass);
		for (PropertyDescriptor propertyDescriptor : beanWrapper.getPropertyDescriptorsInDeclaringOrder()) {
			if (beanWrapper.isReadableProperty(propertyDescriptor.getName())
					&& propertyDescriptor.getReadMethod().isAnnotationPresent(Column.class)) {
				fieldNames.add(propertyDescriptor.getName());
			}
		}
		return fieldNames;
	}
	
	public static String getIdFieldName(Class<? extends BaseModel> entityClass) {
		for (Method method : entityClass.getMethods()) {
			if (method.isAnnotationPresent(Id.class)) {
				String methodName = method.getName();
				return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
			}
		}
		return null;
	}
	
	public static Class<?> getIdFieldType(Class<? extends BaseModel> entityClass) {
		for (Method method : entityClass.getMethods()) {
			if (method.isAnnotationPresent(Id.class)) {
				return method.getReturnType();
			}
		}
		return null;
	}
	
	public static String getIdFieldNameByConditionObject(Object conditionObject) {
		Class<?> itemClass = getItemClassByConditionObject(conditionObject);
		if (itemClass != null && BaseModel.class.isAssignableFrom(itemClass)) {
			return getIdFieldName((Class<? extends BaseModel>) itemClass);
		} else {
			return null;
		}
	}

	public static String getRecVerFieldName(Class<? extends BaseModel> entityClass) {
		for (Method method : entityClass.getMethods()) {
			String methodName = method.getName();
			if (method.isAnnotationPresent(Version.class)) {
				return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
			}
		}
		return null;
	}

	public static String getRecVerFieldNameByConditionObject(Object conditionObject) {
		Class<?> itemClass = getItemClassByConditionObject(conditionObject);
		if (itemClass != null && BaseModel.class.isAssignableFrom(itemClass)) {
			return getRecVerFieldName((Class<? extends BaseModel>) itemClass);
		} else {
			return null;
		}
	}

	public static Serializable getId(BaseModel entity) {
		if (entity.getClass().isAnnotationPresent(IdClass.class)) {
			Class<?> idClass = entity.getClass().getAnnotation(IdClass.class).value();
			try {
				Serializable id = (Serializable) idClass.newInstance();
				BeanUtils.copyProperties(entity, id);
				return id;
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		String idFieldName = getIdFieldName(entity.getClass());
		if (idFieldName != null) {
			return (Serializable) new CustomBeanWrapper(entity).getPropertyValue(idFieldName);
		} else {
			return null;
		}
	}
	
	public static Class<?> getItemClassByConditionObject(Object conditionObject) {
		if (conditionObject instanceof String) {
			try {
				return Class.forName((String) conditionObject);
			} catch (ClassNotFoundException cnfex) {
				return null;
			}
		} else if (conditionObject instanceof Class) {
			return (Class<?>) conditionObject;
		} else if (conditionObject instanceof BaseModel) {
			return conditionObject.getClass();
		} else if (conditionObject instanceof BaseQueryCondition) {
			return getQueryItemClass((BaseQueryCondition) conditionObject);
		} else if (conditionObject instanceof QueryDataSource) {
			return ((QueryDataSource) conditionObject).getDataItemClass();
		} else {
			return null;
		}
	}

	public static String getSqlQueryName(Class<? extends BaseQueryCondition> conditionClass) {
		String queryName = conditionClass.getSimpleName();
		if (queryName.endsWith("QueryCondition")) {
			queryName = queryName.substring(0, queryName.length() - 9);
		}
		return queryName;
	}
	
	public static String getSqlQueryName(BaseQueryCondition condition) {
		return getSqlQueryName(condition.getClass());
	}
	
	public static Class<? extends BaseQueryItem> getQueryItemClass(BaseQueryCondition condition) {
		String queryConditionClassName = condition.getClass().getName();
		if (queryConditionClassName.endsWith("QueryCondition")) {
			String queryItemClassName = queryConditionClassName.substring(0, queryConditionClassName.length() - 9) + "Item";
			try {
				return (Class<? extends BaseQueryItem>) Class.forName(queryItemClassName);
			} catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(cnfe);
			}
		} else {
			throw new RuntimeException("Item class for " + queryConditionClassName + " not found");
		}
	}

	public static String getSqlUpdateName(BaseUpdateCondition condition) {
		String updateName = condition.getClass().getSimpleName();
		if (updateName.endsWith("UpdateCondition")) {
			updateName = updateName.substring(0, updateName.length() - 9);
		}
		return updateName;
	}
	
}
