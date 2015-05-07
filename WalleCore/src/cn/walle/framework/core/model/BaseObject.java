package cn.walle.framework.core.model;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import cn.walle.framework.core.support.CustomBeanWrapper;
import cn.walle.framework.core.util.DateUtils;

public abstract class BaseObject implements Serializable{
	
	public String get_class() {
		return this.getClass().getName();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getClass().getName());
		result.append("{");
		
		CustomBeanWrapper beanWrapper = new CustomBeanWrapper(this);
		PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptorsInDeclaringOrder();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			if ("class".equals(propertyName)) {
				continue;
			}
			if (! beanWrapper.isReadableProperty(propertyName)) {
				continue;
			}
			result.append(propertyName);
			result.append("=");
			Object propertyValue = beanWrapper.getPropertyValue(propertyName);
			if (propertyValue instanceof String) {
				propertyValue = "\"" + propertyValue + "\"";
			} else if (propertyValue instanceof Date) {
				propertyValue = DateUtils.format((Date) propertyValue);
			}
			result.append(propertyValue);
			result.append(", ");
		}
		
		if (propertyDescriptors.length > 0) {
			result.setLength(result.length() - 2);
		}
		
		result.append("}");
		return result.toString();
	}
	
    public boolean equals(Object obj) {
    	return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public int hashCode() {
    	 return HashCodeBuilder.reflectionHashCode(this);
    }
    
}
