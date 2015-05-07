<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans-3.1.xsd">

	<bean id="${className}.ALL" parent="fieldListDefinition">
		<constructor-arg>
			<list>
<#list columns as column>
<#if ! column.isPK 
		&& column.fieldName != "creator" 
		&& column.fieldName != "createTime" 
		&& column.fieldName != "modifier" 
		&& column.fieldName != "modifyTime" 
		&& column.fieldName != "recVer">
				<ref bean="${className}.${column.fieldName}" />
</#if>
</#list>
			</list>
		</constructor-arg>
	</bean>

<#list columns as column>
	<bean id="${className}.${column.fieldName}" parent="fieldDefinition">
		<property name="fieldName" value="${column.fieldName}" />
		<property name="label" value="${column.label}" />
		<property name="fieldType" value="${column.uiFieldType}" />
		<property name="sortable" value="${column.sortable}" />
		<property name="nullable" value="${column.nullable}" />
<#if column.uiFieldType == "string">
		<property name="length" value="${column.length}" />
</#if>
<#if column.uiFieldType == "int">
		<property name="precision" value="${column.precision}" />
</#if>
<#if column.uiFieldType == "double">
		<property name="precision" value="${column.precision}" />
		<property name="scale" value="${column.scale}" />
</#if>
		<property name="width" value="${column.width}" />
	</bean>

</#list>
</beans>
