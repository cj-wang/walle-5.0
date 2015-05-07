package cn.walle.framework.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.support.CustomBeanWrapper;

public class JSONDataUtils {
	
	public static String buildParametersJSONString(Class<?> clazz, Method method, String[] parameterNames) throws Exception {
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (genericParameterTypes.length != parameterNames.length) {
			throw new IllegalArgumentException("Wrong number of parameter names");
		}
		
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < genericParameterTypes.length; i++) {
			addJSONValue(jsonObject, clazz, parameterNames[i], genericParameterTypes[i], false);
		}
		
		return jsonObject.toString(4);
	}
	
	private static void addJSONValue(Object parent, Class<?> clazz, String parameterName, Type genericType,
			boolean addClassAttribute) throws Exception {
		Object jsonValue;
		
		if (genericType instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) genericType).getGenericComponentType();
			JSONArray jsonArray = new JSONArray();
			addJSONValue(jsonArray, clazz, parameterName, componentType, false);
			addJSONValue(jsonArray, clazz, parameterName, componentType, false);
			jsonValue = jsonArray;
		} else if (genericType instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) genericType).getRawType();
			if (rawType instanceof Class) {
				Class<?> rawTypeClass = (Class<?>) rawType;
				if (Class.class == rawTypeClass) {
					Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					JSONObject j = new JSONObject();
					addJSONValue(j, clazz, "type", actualType, true);
					Object type = j.get("type");
					if (type instanceof JSONObject) {
						jsonValue = ((JSONObject) type).getString("_class");
					} else {
						jsonValue = parseJSONValue(clazz, actualType, type).getClass().getName();
					}
				} else if (Collection.class.isAssignableFrom(rawTypeClass)) {
					Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					JSONArray jsonArray = new JSONArray();
					addJSONValue(jsonArray, clazz, parameterName, actualType, false);
					addJSONValue(jsonArray, clazz, parameterName, actualType, false);
					jsonValue = jsonArray;
				} else if (Map.class.isAssignableFrom(rawTypeClass)) {
					Type valueType = ((ParameterizedType) genericType).getActualTypeArguments()[1];
					JSONObject jsonMap = new JSONObject();
					addJSONValue(jsonMap, clazz, "key0", valueType, false);
					addJSONValue(jsonMap, clazz, "key1", valueType, false);
					jsonValue = jsonMap;
				} else {
					addJSONValue(parent, clazz, parameterName, rawTypeClass, true);
					return;
				}
			} else {
				addJSONValue(parent, clazz, parameterName, rawType, true);
				return;
			}
		} else if (genericType instanceof TypeVariable) {
			Type actualTypeArgument = ReflectionUtils.getActualTypeArgument(clazz, (TypeVariable<?>) genericType);
			if (actualTypeArgument == null) {
				Type boundType = ((TypeVariable<?>) genericType).getBounds()[0];
				addJSONValue(parent, clazz, parameterName, boundType, true);
				return;
			} else {
				addJSONValue(parent, clazz, parameterName, actualTypeArgument, false);
				return;
			}
		} else if (genericType instanceof WildcardType) {
			Type boundType = ((WildcardType) genericType).getUpperBounds()[0];
			addJSONValue(parent, clazz, parameterName, boundType, true);
			return;
		} else if (genericType instanceof Class) {
			Class<?> typeClass = (Class<?>) genericType;
			if (typeClass.isArray()) {
				Class<?> componentTypeClass = typeClass.getComponentType();
				JSONArray jsonArray = new JSONArray();
				addJSONValue(jsonArray, clazz, parameterName, componentTypeClass, false);
				addJSONValue(jsonArray, clazz, parameterName, componentTypeClass, false);
				jsonValue = jsonArray;
			} else if (Class.class == typeClass) {
				jsonValue = "java.lang.Object";
			} else if (Collection.class.isAssignableFrom(typeClass)) {
				JSONArray jsonArray = new JSONArray();
				addJSONValue(jsonArray, clazz, parameterName, Object.class, true);
				addJSONValue(jsonArray, clazz, parameterName, Object.class, true);
				jsonValue = jsonArray;
			} else if (Map.class.isAssignableFrom(typeClass)) {
				JSONObject jsonMap = new JSONObject();
				addJSONValue(jsonMap, clazz, "key0", Object.class, true);
				addJSONValue(jsonMap, clazz, "key1", Object.class, true);
				jsonValue = jsonMap;
			} else if (typeClass == String.class
					|| typeClass == Character.class
					|| typeClass == char.class) {
				jsonValue = "";
			} else if (typeClass == Integer.class
					|| typeClass == int.class
					|| typeClass == Long.class
					|| typeClass == long.class
					|| typeClass == Short.class
					|| typeClass == short.class
					|| typeClass == Byte.class
					|| typeClass == byte.class) {
				jsonValue = 0;
			} else if (typeClass == Double.class
					|| typeClass == double.class
					|| typeClass == Float.class
					|| typeClass == float.class) {
				jsonValue = 0.0;
			} else if (typeClass == Boolean.class
					|| typeClass == boolean.class) {
				jsonValue = false;
			} else if (Date.class.isAssignableFrom(typeClass)) {
				jsonValue = "1970-01-01 00:00:00";
			} else {
				if (typeClass.equals(Object.class)) {
					jsonValue = "";
				} else {
					JSONObject jsonObject = new JSONObject();
					try {
						typeClass.newInstance();
					} catch (Exception ex) {
						addClassAttribute = true;
					}
					List<Method> methods = ReflectionUtils.getMethodsInDeclaringOrder(typeClass);
					for (Method method : methods) {
						if (method.getName().startsWith("set")
								&& ! method.getName().equals("set")
								&& method.getGenericParameterTypes().length == 1
								&& method.getReturnType() == Void.TYPE) {
							String methodName = method.getName();
							String propertyName = methodName.substring(3, 4).toLowerCase() +
									methodName.substring(4);
							Type propertyType = method.getGenericParameterTypes()[0];
							addJSONValue(jsonObject, typeClass, propertyName, propertyType, false);
						}
					}
					if (addClassAttribute) {
						jsonObject.put("_class", typeClass.getName());
					}
					jsonValue = jsonObject;
				}
			}
		} else {
			throw new RuntimeException("Unsupported parameter type " + genericType);
		}
		
		if (parent instanceof JSONObject) {
			((JSONObject) parent).put(parameterName, jsonValue);
		} else if (parent instanceof JSONArray) {
			((JSONArray) parent).put(jsonValue);
		} else {
			throw new IllegalArgumentException("parent must be JSONObject or JSONArray");
		}
	}

	public static Object[] parseParametersJSONString(Class<?> clazz, Method method, String jsonString) throws Exception {
		JSONObject jsonObject = new JSONObject(jsonString);
		return parseParametersJSONObject(clazz, method, jsonObject);
	}
	
	public static Object[] parseParametersJSONObject(Class<?> clazz, Method method, JSONObject jsonObject) throws Exception {
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		
		if (jsonObject.length() != genericParameterTypes.length) {
			throw new IllegalArgumentException("Wrong number of parameter jsonObject values");
		}
		
		Object[] result = new Object[genericParameterTypes.length];
		
		Iterator<String> keys = jsonObject.keys();
		for (int i = 0; i < genericParameterTypes.length; i++) {
			result[i] = parseJSONValue(clazz, genericParameterTypes[i], jsonObject.get(keys.next()));
		}
		return result;
	}
	
	public static Object parseJSONObject(Type genericType, String jsonString) throws Exception {
		JSONObject jsonObject = new JSONObject(jsonString);
		return parseJSONValue(null, genericType, jsonObject);
	}
	
	public static Object parseJSONObject(String name, Type genericType, String jsonString) throws Exception {
		JSONObject jsonObject = new JSONObject(jsonString);
		return parseJSONValue(null, genericType, jsonObject.get(name));
	}
	
	private static Object parseJSONValue(Class<?> clazz, Type genericType, Object jsonValue) throws Exception {
		if (JSONObject.NULL.equals(jsonValue)) {
			return null;
		}
		if (genericType instanceof GenericArrayType) {
			if (jsonValue instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) jsonValue;
				Type componentType = ((GenericArrayType) genericType).getGenericComponentType();
				Object result;
				if (componentType instanceof Class) {
					result = Array.newInstance((Class<?>) componentType, jsonArray.length());
				} else {
					JSONObject j = new JSONObject();
					addJSONValue(j, clazz, "type", componentType, true);
					Object type = j.get("type");
					Class<?> componentTypeClass;
					if (type instanceof JSONObject) {
						componentTypeClass = org.springframework.util.ClassUtils.forName(((JSONObject) type).getString("_class"));
					} else {
						componentTypeClass = parseJSONValue(clazz, componentType, type).getClass();
					}
					result = Array.newInstance(componentTypeClass, jsonArray.length());
				}
				for (int i = 0; i < jsonArray.length(); i++) {
					Array.set(result, i, parseJSONValue(clazz, componentType, jsonArray.get(i)));
				}
				return result;
			} else {
				throw new JSONException("JSONArray expected for GenericArrayType");
			}
		} else if (genericType instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) genericType).getRawType();
			if (rawType instanceof Class) {
				Class<?> rawTypeClass = (Class<?>) rawType;
				if (Class.class == rawTypeClass) {
					if (jsonValue instanceof String) {
						String value = ((String) jsonValue).trim();
						if (value.length() == 0) {
							return null;
						} else {
							return org.springframework.util.ClassUtils.forName(value);
						}
					} else {
						throw new JSONException("String expected for Class type");
					}
				} else if (Collection.class.isAssignableFrom(rawTypeClass)) {
					if (jsonValue instanceof JSONArray) {
						JSONArray jsonArray = (JSONArray) jsonValue;
						Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
						Collection<Object> result;
						try {
							result = (Collection<Object>) rawTypeClass.newInstance();
						} catch (Exception ex) {
							result = new ArrayList<Object>();
						}
						for (int i = 0; i < jsonArray.length(); i++) {
							result.add(parseJSONValue(clazz, actualType, jsonArray.get(i)));
						}
						return result;
					} else {
						throw new JSONException("JSONArray expected for Collection type");
					}
				} else if (Map.class.isAssignableFrom(rawTypeClass)) {
					if (jsonValue instanceof JSONObject) {
						JSONObject jsonMap = (JSONObject) jsonValue;
						Type keyType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
						Type valueType = ((ParameterizedType) genericType).getActualTypeArguments()[1];
						Map<Object, Object> result;
						try {
							result = (Map<Object, Object>) rawTypeClass.newInstance();
						} catch (Exception ex) {
							result = new LinkedHashMap<Object, Object>();
						}
						Iterator<String> keys = jsonMap.keys();
						while (keys.hasNext()) {
							String key = keys.next();
							Object keyObject = parseJSONValue(clazz, keyType, key);
							Object value = parseJSONValue(clazz, valueType, jsonMap.get(key));
							result.put(keyObject, value);
						}
						return result;
					} else {
						throw new JSONException("JSONObject expected for Map type");
					}
				} else {
					return parseJSONValue(clazz, rawTypeClass, jsonValue);
				}
			} else {
				return parseJSONValue(clazz, rawType, jsonValue);
			}
		} else if (genericType instanceof TypeVariable) {
			Type actualTypeArgument = ReflectionUtils.getActualTypeArgument(clazz, (TypeVariable<?>) genericType);
			if (actualTypeArgument == null) {
				Type boundType = ((TypeVariable<?>) genericType).getBounds()[0];
				return parseJSONValue(clazz, boundType, jsonValue);
			} else {
				return parseJSONValue(clazz, actualTypeArgument, jsonValue);
			}
		} else if (genericType instanceof WildcardType) {
			Type boundType = ((WildcardType) genericType).getUpperBounds()[0];
			return parseJSONValue(clazz, boundType, jsonValue);
		} else if (genericType instanceof Class) {
			Class<?> typeClass = (Class<?>) genericType;
			String className = null;
			if (jsonValue instanceof JSONObject) {
				className = ((JSONObject) jsonValue).optString("_class", null);
			}
			if (className != null) {
				try {
					typeClass = org.springframework.util.ClassUtils.forName(className);
				} catch (Exception ex) {
				}
			}
			if (typeClass.isArray()) {
				if (jsonValue instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) jsonValue;
					Class<?> componentTypeClass = typeClass.getComponentType();
					Object result = Array.newInstance(componentTypeClass, jsonArray.length());
					for (int i = 0; i < jsonArray.length(); i++) {
						Array.set(result, i, parseJSONValue(clazz, componentTypeClass, jsonArray.get(i)));
					}
					return result;
				} else {
					throw new JSONException("JSONArray expected for array type");
				}
			} else if (Class.class == typeClass) {
				if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return org.springframework.util.ClassUtils.forName(value);
					}
				} else {
					throw new JSONException("String expected for Class type");
				}
			} else if (Collection.class.isAssignableFrom(typeClass)) {
				if (jsonValue instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) jsonValue;
					Collection<Object> result;
					try {
						result = (Collection<Object>) typeClass.newInstance();
					} catch (Exception ex) {
						result = new ArrayList<Object>();
					}
					for (int i = 0; i < jsonArray.length(); i++) {
						result.add(parseJSONValue(clazz, Object.class, jsonArray.get(i)));
					}
					return result;
				} else {
					throw new JSONException("JSONArray expected for Collection type");
				}
			} else if (Map.class.isAssignableFrom(typeClass)) {
				if (jsonValue instanceof JSONObject) {
					JSONObject jsonMap = (JSONObject) jsonValue;
					Map<Object, Object> result;
					try {
						result = (Map<Object, Object>) typeClass.newInstance();
					} catch (Exception ex) {
						result = new LinkedHashMap<Object, Object>();
					}
					Iterator<String> keys = jsonMap.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						Object value = parseJSONValue(clazz, Object.class, jsonMap.get(key));
						result.put(key, value);
					}
					return result;
				} else {
					throw new JSONException("JSONObject expected for Map type");
				}
			} else if (typeClass == String.class) {
				if (jsonValue instanceof String) {
					return (String) jsonValue;
				} else {
					throw new JSONException("String expected");
				}
			} else if (typeClass == Character.class
					|| typeClass == char.class) {
				if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return value.charAt(0);
					}
				} else {
					throw new JSONException("String expected for char type");
				}
			} else if (typeClass == Integer.class
					|| typeClass == int.class) {
				if (jsonValue instanceof Number) {
					return ((Number) jsonValue).intValue();
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Integer.parseInt(value);
					}
				} else {
					throw new JSONException("Number or String expected for int type");
				}
			} else if (typeClass == Long.class
					|| typeClass == long.class) {
				if (jsonValue instanceof Number) {
					return ((Number) jsonValue).longValue();
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Long.parseLong(value);
					}
				} else {
					throw new JSONException("Number or String expected for long type");
				}
			} else if (typeClass == Short.class
					|| typeClass == short.class) {
				if (jsonValue instanceof Number) {
					return ((Number) jsonValue).shortValue();
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Short.parseShort(value);
					}
				} else {
					throw new JSONException("Number or String expected for short type");
				}
			} else if (typeClass == Byte.class
					|| typeClass == byte.class) {
				if (jsonValue instanceof Number) {
					return ((Number) jsonValue).byteValue();
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Byte.parseByte(value);
					}
				} else {
					throw new JSONException("Number or String expected for byte type");
				}
			} else if (typeClass == Double.class
					|| typeClass == double.class) {
				if (jsonValue instanceof Number) {
					return ((Number) jsonValue).doubleValue();
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Double.parseDouble(value);
					}
				} else {
					throw new JSONException("Number or String expected for double type");
				}
			} else if (typeClass == Float.class
					|| typeClass == float.class) {
				if (jsonValue instanceof Number) {
					return ((Number) jsonValue).floatValue();
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Float.parseFloat(value);
					}
				} else {
					throw new JSONException("Number or String expected for float type");
				}
			} else if (typeClass == Boolean.class
					|| typeClass == boolean.class) {
				if (jsonValue instanceof Boolean) {
					return (Boolean) jsonValue;
				} else if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.length() == 0) {
						return null;
					} else {
						return Boolean.parseBoolean(value);
					}
				} else {
					throw new JSONException("Boolean or String expected for boolean type");
				}
			} else if (Date.class.isAssignableFrom(typeClass)) {
				if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.startsWith("date(") && value.endsWith(")")) {
						value = value.substring(5, value.length() - 1);
					}
					if (value.length() == 0) {
						return null;
					} else {
						return DateUtils.parse(value);
					}
				} else {
					throw new JSONException("String expected for Date type");
				}
			} else {
				if (jsonValue instanceof String) {
					String value = ((String) jsonValue).trim();
					if (value.startsWith("date(") && value.endsWith(")")) {
						return parseJSONValue(clazz, Date.class, jsonValue);
					} else {
						return parseJSONValue(clazz, String.class, jsonValue);
					}
				} else if (jsonValue instanceof Integer) {
					return parseJSONValue(clazz, int.class, jsonValue);
				} else if (jsonValue instanceof Double) {
					return parseJSONValue(clazz, double.class, jsonValue);
				} else if (jsonValue instanceof Long) {
					return parseJSONValue(clazz, long.class, jsonValue);
				} else if (jsonValue instanceof Boolean) {
					return parseJSONValue(clazz, boolean.class, jsonValue);
				} else if (jsonValue instanceof JSONArray) {
					return parseJSONValue(clazz, List.class, jsonValue);
				} else if (jsonValue instanceof JSONObject) {
					JSONObject jsonObject = (JSONObject) jsonValue;
					if (typeClass == Object.class) {
						return parseJSONValue(clazz, DynamicModelClass.class, jsonValue);
					}
					Object result;
					try {
						result = typeClass.newInstance();
					} catch (InstantiationException ex) {
						throw new InstantiationException(typeClass.getName());
					}
					CustomBeanWrapper beanWrapper = new CustomBeanWrapper(result);
					PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						String propertyName = propertyDescriptor.getName();
						if (beanWrapper.isWritableProperty(propertyName)) {
							Object childObject = jsonObject.opt(propertyName);
							if (childObject == null) {
								continue;
							}
							Type propertyType = propertyDescriptor.getWriteMethod().getGenericParameterTypes()[0];
							Object value = parseJSONValue(typeClass, propertyType, childObject);
							beanWrapper.setPropertyValue(propertyName, value);
						}
					}
					return result;
				} else {
					throw new RuntimeException("Unsupported json data type " + jsonValue);
				}
			}
		} else {
			throw new RuntimeException("Unsupported parameter type " + genericType);
		}
	}
	
	public static String buildJSONString(String name, Object value, boolean prettyFormat) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(name, buildJSONValue(value));
		if (prettyFormat) {
			return jsonObject.toString(4);
		} else {
			return jsonObject.toString();
		}
	}
	
	public static Object buildJSONValue(Object value) throws Exception {
		if (value == null) {
			return JSONObject.NULL;
		} else if (value.getClass().isArray()) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < Array.getLength(value); i++) {
				jsonArray.put(buildJSONValue(Array.get(value, i)));
			}
			return jsonArray;
		} else if (value instanceof Class) {
			return ((Class<?>) value).getName();
		} else if (value instanceof Collection) {
			JSONArray jsonArray = new JSONArray();
			for (Object item : (Collection<?>) value) {
				jsonArray.put(buildJSONValue(item));
			}
			return jsonArray;
		} else if (value instanceof Map) {
			JSONObject jsonMap= new JSONObject();
			Map<?, ?> map = (Map<?, ?>) value;
			for (Object key : map.keySet()) {
				jsonMap.put(key + "", buildJSONValue(map.get(key)));
			}
			return jsonMap;
		} else if (value instanceof String
				|| value instanceof Integer
				|| value instanceof Long
				|| value instanceof Double
				|| value instanceof Boolean) {
			return value;
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal) value).doubleValue();
		} else if (value instanceof Character) {
			return String.valueOf(value);
		} else if (value instanceof Short
				|| value instanceof Byte) {
			return ((Number) value).intValue();
		} else if (value instanceof Float) {
			return ((Number) value).doubleValue();
		} else if (value instanceof Date) {
			return DateUtils.format((Date) value);
		} else {
			JSONObject jsonObject = new JSONObject();
			CustomBeanWrapper beanWrapper = new CustomBeanWrapper(value);
			PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptorsInDeclaringOrder();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String propertyName = propertyDescriptor.getName();
				if ("class".equals(propertyName)) {
					propertyName = "_class";
				}
				if ("metaClass".equals(propertyName)) {
					continue;
				}
				if (beanWrapper.isReadableProperty(propertyName)) {
					Object propertyValue = beanWrapper.getPropertyValue(propertyName);
					jsonObject.put(propertyName, buildJSONValue(propertyValue));
				}
			}
			return jsonObject;
		}
	}
	
	
	public static void setBeanProperties(Object bean, String jsonString) throws Exception {
		Class<?> beanClass = bean.getClass();
		CustomBeanWrapper beanWrapper = new CustomBeanWrapper(bean);
		JSONObject jsonObject = new JSONObject(jsonString);
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			if (beanWrapper.isWritableProperty(key)) {
				Type type = beanWrapper.getPropertyDescriptor(key).getWriteMethod().getGenericParameterTypes()[0];
				Object value = parseJSONValue(beanClass, type, jsonObject.get(key));
				beanWrapper.setPropertyValue(key, value);
			}
		}
	}
	
	public static String format(String jsonString) throws Exception {
		JSONObject jsonObject = new JSONObject(jsonString);
		return jsonObject.toString(4);
	}
	
}
