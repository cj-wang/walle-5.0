package cn.walle.framework.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import cn.walle.framework.core.support.CustomBeanWrapper;

public class XmlDataUtils {

	public static Element buildParametersXmlElement(Class<?> clazz, Method method, String[] parameterNames) throws Exception {
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (genericParameterTypes.length != parameterNames.length) {
			throw new IllegalArgumentException("Wrong number of parameter names");
		}
		
		Element element = new Element("Parameters");
		for (int i = 0; i < genericParameterTypes.length; i++) {
			element.addContent(buildXmlElement(clazz, parameterNames[i], genericParameterTypes[i], false));
		}
		
		return element;
	}
	
	private static Element buildXmlElement(Class<?> clazz, String parameterName, Type genericType,
			boolean addClassAttribute) throws Exception {
		Element result = new Element(parameterName);
		
		if (genericType instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) genericType).getGenericComponentType();
			result.addContent(buildXmlElement(clazz, parameterName + "_0", componentType, false));
			result.addContent(buildXmlElement(clazz, parameterName + "_1", componentType, false));
		} else if (genericType instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) genericType).getRawType();
			if (rawType instanceof Class) {
				Class<?> rawTypeClass = (Class<?>) rawType;
				if (Class.class == rawTypeClass) {
					Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					Element e = buildXmlElement(clazz, "type", actualType, true);
					result.addContent(e.getAttributeValue("class"));
				} else if (Collection.class.isAssignableFrom(rawTypeClass)) {
					Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					result.addContent(buildXmlElement(clazz, parameterName + "_0", actualType, false));
					result.addContent(buildXmlElement(clazz, parameterName + "_1", actualType, false));
				} else if (Map.class.isAssignableFrom(rawTypeClass)) {
					Type keyType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					Type valueType = ((ParameterizedType) genericType).getActualTypeArguments()[1];
					Element mapEntryElement = new Element(parameterName + "_0");
					mapEntryElement.addContent(buildXmlElement(clazz, "key", keyType, false));
					mapEntryElement.addContent(buildXmlElement(clazz, "value", valueType, false));
					result.addContent(mapEntryElement);
					mapEntryElement = new Element(parameterName + "_1");
					mapEntryElement.addContent(buildXmlElement(clazz, "key", keyType, false));
					mapEntryElement.addContent(buildXmlElement(clazz, "value", valueType, false));
					result.addContent(mapEntryElement);
				} else {
					return buildXmlElement(clazz, parameterName, rawTypeClass, true);
				}
			} else {
				return buildXmlElement(clazz, parameterName, rawType, true);
			}
		} else if (genericType instanceof TypeVariable) {
			Type actualTypeArgument = ReflectionUtils.getActualTypeArgument(clazz, (TypeVariable<?>) genericType);
			if (actualTypeArgument == null) {
				Type boundType = ((TypeVariable<?>) genericType).getBounds()[0];
				return buildXmlElement(clazz, parameterName, boundType, true);
			} else {
				return buildXmlElement(clazz, parameterName, actualTypeArgument, false);
			}
		} else if (genericType instanceof WildcardType) {
			Type boundType = ((WildcardType) genericType).getUpperBounds()[0];
			return buildXmlElement(clazz, parameterName, boundType, true);
		} else if (genericType instanceof Class) {
			Class<?> typeClass = (Class<?>) genericType;
			if (typeClass.isArray()) {
				Class<?> componentTypeClass = typeClass.getComponentType();
				result.addContent(buildXmlElement(clazz, parameterName + "_0", componentTypeClass, false));
				result.addContent(buildXmlElement(clazz, parameterName + "_1", componentTypeClass, false));
			} else if (Class.class == typeClass) {
				result.addContent("java.lang.Object");
			} else if (Collection.class.isAssignableFrom(typeClass)) {
				result.addContent(buildXmlElement(clazz, parameterName + "_0", Object.class, true));
				result.addContent(buildXmlElement(clazz, parameterName + "_1", Object.class, true));
			} else if (Map.class.isAssignableFrom(typeClass)) {
				Element mapEntryElement = new Element(parameterName + "_0");
				mapEntryElement.addContent(buildXmlElement(clazz, "key", Object.class, true));
				mapEntryElement.addContent(buildXmlElement(clazz, "value", Object.class, true));
				result.addContent(mapEntryElement);
				mapEntryElement = new Element(parameterName + "_1");
				mapEntryElement.addContent(buildXmlElement(clazz, "key", Object.class, true));
				mapEntryElement.addContent(buildXmlElement(clazz, "value", Object.class, true));
				result.addContent(mapEntryElement);
			} else if (typeClass == String.class
					|| typeClass == Character.class
					|| typeClass == char.class) {
				result.addContent("");
			} else if (typeClass == Integer.class
					|| typeClass == int.class
					|| typeClass == Long.class
					|| typeClass == long.class
					|| typeClass == Short.class
					|| typeClass == short.class
					|| typeClass == Byte.class
					|| typeClass == byte.class) {
				result.addContent("0");
			} else if (typeClass == Double.class
					|| typeClass == double.class
					|| typeClass == Float.class
					|| typeClass == float.class) {
				result.addContent("0.0");
			} else if (typeClass == Boolean.class
					|| typeClass == boolean.class) {
				result.addContent("false");
			} else if (Date.class.isAssignableFrom(typeClass)) {
				result.addContent("1970-01-01 00:00:00");
			} else {
				try {
					typeClass.newInstance();
				} catch (InstantiationException ex) {
					addClassAttribute = true;
				}
				if (typeClass.equals(Object.class)) {
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
						result.addContent(buildXmlElement(typeClass, propertyName, propertyType, false));
					}
				}
			}
			if (addClassAttribute) {
				result.setAttribute("class", typeClass.getName());
			}
		} else {
			throw new RuntimeException("Unsupported parameter type " + genericType);
		}
		
		return result;
	}

	public static Object[] parseParametersXmlElement(Class<?> clazz, Method method, Element element) throws Exception {
		List<Element> elements = element.getChildren();
		
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		
		if (elements.size() != genericParameterTypes.length) {
			throw new IllegalArgumentException("Wrong number of parameter Element children");
		}
		
		Object[] result = new Object[genericParameterTypes.length];
		
		for (int i = 0; i < genericParameterTypes.length; i++) {
			result[i] = parseXmlElement(clazz, genericParameterTypes[i], elements.get(i));
		}
		return result;
	}
	
	private static Object parseXmlElement(Class<?> clazz, Type genericType, Element element) throws Exception {
		if (element.getChild("null") != null) {
			return null;
		}
		if (genericType instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) genericType).getGenericComponentType();
			List<Element> children = element.getChildren();
			Object result;
			if (componentType instanceof Class) {
				result = Array.newInstance((Class<?>) componentType, children.size());
			} else {
				Element e = buildXmlElement(clazz, "type", componentType, true);
				Class<?> componentTypeClass = org.springframework.util.ClassUtils.forName(e.getAttributeValue("class"));
				result = Array.newInstance(componentTypeClass, children.size());
			}
			for (int i = 0; i < children.size(); i++) {
				Array.set(result, i, parseXmlElement(clazz, componentType, children.get(i)));
			}
			return result;
		} else if (genericType instanceof ParameterizedType) {
			Type rawType = ((ParameterizedType) genericType).getRawType();
			if (rawType instanceof Class) {
				Class<?> rawTypeClass = (Class<?>) rawType;
				if (Class.class == rawTypeClass) {
					String value = element.getTextTrim();
					if (value.length() == 0) {
						return null;
					} else {
						return org.springframework.util.ClassUtils.forName(value);
					}
				} else if (Collection.class.isAssignableFrom(rawTypeClass)) {
					Type actualType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					List<Element> children = element.getChildren();
					Collection<Object> result = new ArrayList<Object>();
					for (int i = 0; i < children.size(); i++) {
						result.add(parseXmlElement(clazz, actualType, children.get(i)));
					}
					return result;
				} else if (Map.class.isAssignableFrom(rawTypeClass)) {
					Type keyType = ((ParameterizedType) genericType).getActualTypeArguments()[0];
					Type valueType = ((ParameterizedType) genericType).getActualTypeArguments()[1];
					List<Element> mapEntryElements = element.getChildren();
					Map<Object, Object> result = new LinkedHashMap<Object, Object>();
					for (Element mapEntryElement : mapEntryElements) {
						Object key = parseXmlElement(clazz, keyType, mapEntryElement.getChild("key"));
						Object value = parseXmlElement(clazz, valueType, mapEntryElement.getChild("value"));
						result.put(key, value);
					}
					return result;
				} else {
					return parseXmlElement(clazz, rawTypeClass, element);
				}
			} else {
				return parseXmlElement(clazz, rawType, element);
			}
		} else if (genericType instanceof TypeVariable) {
			Type actualTypeArgument = ReflectionUtils.getActualTypeArgument(clazz, (TypeVariable<?>) genericType);
			if (actualTypeArgument == null) {
				Type boundType = ((TypeVariable<?>) genericType).getBounds()[0];
				return parseXmlElement(clazz, boundType, element);
			} else {
				return parseXmlElement(clazz, actualTypeArgument, element);
			}
		} else if (genericType instanceof WildcardType) {
			Type boundType = ((WildcardType) genericType).getUpperBounds()[0];
			return parseXmlElement(clazz, boundType, element);
		} else if (genericType instanceof Class) {
			Class<?> typeClass = (Class<?>) genericType;
			String className = element.getAttributeValue("class");
			if (className != null) {
				typeClass = org.springframework.util.ClassUtils.forName(className);
			}
			if (typeClass.isArray()) {
				Class<?> componentTypeClass = typeClass.getComponentType();
				List<Element> children = element.getChildren();
				Object result = Array.newInstance(componentTypeClass, children.size());
				for (int i = 0; i < children.size(); i++) {
					Array.set(result, i, parseXmlElement(clazz, componentTypeClass, children.get(i)));
				}
				return result;
			} else if (Class.class == typeClass) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return org.springframework.util.ClassUtils.forName(value);
				}
			} else if (Collection.class.isAssignableFrom(typeClass)) {
				List<Element> children = element.getChildren();
				Collection<Object> result = new ArrayList<Object>();
				for (int i = 0; i < children.size(); i++) {
					result.add(parseXmlElement(clazz, Object.class, children.get(i)));
				}
				return result;
			} else if (Map.class.isAssignableFrom(typeClass)) {
				List<Element> mapEntryElements = element.getChildren();
				Map<Object, Object> result = new LinkedHashMap<Object, Object>();
				for (Element mapEntryElement : mapEntryElements) {
					Object key = parseXmlElement(clazz, Object.class, mapEntryElement.getChild("key"));
					Object value = parseXmlElement(clazz, Object.class, mapEntryElement.getChild("value"));
					result.put(key, value);
				}
				return result;
			} else if (typeClass == String.class) {
				return element.getTextTrim();
			} else if (typeClass == Character.class
					|| typeClass == char.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return value.charAt(0);
				}
			} else if (typeClass == Integer.class
					|| typeClass == int.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Integer.parseInt(value);
				}
			} else if (typeClass == Long.class
					|| typeClass == long.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Long.parseLong(value);
				}
			} else if (typeClass == Short.class
					|| typeClass == short.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Short.parseShort(value);
				}
			} else if (typeClass == Byte.class
					|| typeClass == byte.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Byte.parseByte(value);
				}
			} else if (typeClass == Double.class
					|| typeClass == double.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Double.parseDouble(value);
				}
			} else if (typeClass == Float.class
					|| typeClass == float.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Float.parseFloat(value);
				}
			} else if (typeClass == Boolean.class
					|| typeClass == boolean.class) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return Boolean.parseBoolean(value);
				}
			} else if (Date.class.isAssignableFrom(typeClass)) {
				String value = element.getTextTrim();
				if (value.length() == 0) {
					return null;
				} else {
					return DateUtils.parse(value);
				}
			} else {
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
						Element child = element.getChild(propertyName);
						if (child == null) {
							continue;
						}
						Type propertyType = propertyDescriptor.getWriteMethod().getGenericParameterTypes()[0];
						Object value = parseXmlElement(typeClass, propertyType, child);
						beanWrapper.setPropertyValue(propertyName, value);
					}
				}
				return result;
			}
		} else {
			throw new RuntimeException("Unsupported parameter type " + genericType);
		}
	}
	
	public static Element buildXmlElement(String name, Object value) throws Exception {
		Element element = new Element(name);
		if (value == null) {
			element.addContent(new Element("null"));
		} else if (value.getClass().isArray()) {
			for (int i = 0; i < Array.getLength(value); i++) {
				element.addContent(buildXmlElement(name + "_" + i, Array.get(value, i)));
			}
		} else if (value instanceof Class) {
			element.addContent(((Class<?>) value).getName());
		} else if (value instanceof Collection) {
			int index = 0;
			for (Object item : (Collection<?>) value) {
				element.addContent(buildXmlElement(name + "_" + index++, item));
			}
		} else if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) value;
			int index = 0;
			for (Object key : map.keySet()) {
				Element mapEntryElement = new Element(name + "_" + index++);
				mapEntryElement.addContent(buildXmlElement("key", key));
				mapEntryElement.addContent(buildXmlElement("value", map.get(key)));
				element.addContent(mapEntryElement);
			}
		} else if (value instanceof String
				|| value instanceof Character
				|| value instanceof Integer
				|| value instanceof Long
				|| value instanceof Short
				|| value instanceof Byte
				|| value instanceof Double
				|| value instanceof Float
				|| value instanceof Boolean) {
			element.addContent(value.toString());
		} else if (value instanceof Date) {
			element.addContent(DateUtils.format((Date) value));
		} else {
			CustomBeanWrapper beanWrapper = new CustomBeanWrapper(value);
			PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptorsInDeclaringOrder();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				String propertyName = propertyDescriptor.getName();
				if ("metaClass".equals(propertyName)) {
					continue;
				}
				if (beanWrapper.isReadableProperty(propertyName)) {
					Object propertyValue = beanWrapper.getPropertyValue(propertyName);
					element.addContent(buildXmlElement(propertyName, propertyValue));
				}
			}
		}
		return element;
	}
	
}
