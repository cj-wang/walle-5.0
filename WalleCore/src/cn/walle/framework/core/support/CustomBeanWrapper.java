package cn.walle.framework.core.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.support.spring.CustomDateEditor;
import cn.walle.framework.core.support.spring.CustomStringArrayEditor;
import cn.walle.framework.core.util.ReflectionUtils;

public class CustomBeanWrapper extends BeanWrapperImpl {

	public CustomBeanWrapper(Object object) {
		super(object);
		registerCustomEditor(Date.class, new CustomDateEditor());
		registerCustomEditor(Array.newInstance(String.class, 0).getClass(), new CustomStringArrayEditor());
	}

	public CustomBeanWrapper(Class<?> clazz) {
		super(clazz);
		registerCustomEditor(Date.class, new CustomDateEditor());
		registerCustomEditor(Array.newInstance(String.class, 0).getClass(), new CustomStringArrayEditor());
	}

	public PropertyDescriptor[] getPropertyDescriptorsInDeclaringOrder() {
		PropertyDescriptor[] propertyDescriptors = super.getPropertyDescriptors();
		List<Method> methodsInDeclaringOrder = ReflectionUtils.getMethodsInDeclaringOrder(getWrappedClass());
		Set<PropertyDescriptor> resultPropertyDescriptors = new LinkedHashSet<PropertyDescriptor>();
		for (Method method : methodsInDeclaringOrder) {
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if (method.equals(propertyDescriptor.getReadMethod())
						|| method.equals(propertyDescriptor.getWriteMethod())) {
					resultPropertyDescriptors.add(propertyDescriptor);
					break;
				}
			}
		}
		return resultPropertyDescriptors.toArray(new PropertyDescriptor[resultPropertyDescriptors.size()]);
	}

	public void copyPropertiesTo(Object destinationBean, List<String> propertyNames) {
		CustomBeanWrapper destinationBeanWrapper = new CustomBeanWrapper(destinationBean);
		for (String propertyName : propertyNames) {
			destinationBeanWrapper.setPropertyValue(propertyName, getPropertyValue(propertyName));
		}
	}
	
	public Object getPropertyValueRecursively(String propertyName) throws BeansException {
		int dotIndex = propertyName.indexOf(".");
		if (dotIndex == -1) {
			return getPropertyValue(propertyName);
		} else {
			Object propertyBean = getPropertyValue(propertyName.substring(0, dotIndex));
			if (propertyBean == null) {
				return null;
			} else {
				return new CustomBeanWrapper(propertyBean).getPropertyValueRecursively(
						propertyName.substring(dotIndex + 1));
			}
		}
	}
	
	public Class<?> getPropertyTypeRecursively(String propertyName) throws BeansException {
		int dotIndex = propertyName.indexOf(".");
		if (dotIndex == -1) {
			return getPropertyType(propertyName);
		} else {
			Object propertyBean = getPropertyValue(propertyName.substring(0, dotIndex));
			if (propertyBean == null) {
				return null;
			} else {
				return new CustomBeanWrapper(propertyBean).getPropertyTypeRecursively(
						propertyName.substring(dotIndex + 1));
			}
		}
	}
	
	
	@Override
	public Object getPropertyValue(String propertyName) throws BeansException {
		if (this.getWrappedInstance() instanceof DynamicModelClass) {
			return ((DynamicModelClass) this.getWrappedInstance()).get(propertyName);
		} else {
			return super.getPropertyValue(propertyName);
		}
	}
	
	//TODO overwrite other methods to handle DynamicModelClass
	
	
}
