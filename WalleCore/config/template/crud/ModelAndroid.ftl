package ${packageName}.model;

<#if hasDateColumns>
import java.util.Date;

</#if>
import javax.persistence.Column;
import javax.persistence.Entity;
<#if (pkFieldType == "String" && pkFieldLength?number >= 36) || pkFieldType == "Integer" || pkFieldType == "Long">
import javax.persistence.GeneratedValue;
</#if>
<#if pkFieldType == "Integer" || pkFieldType == "Long">
import javax.persistence.GenerationType;
</#if>
import javax.persistence.Id;
<#if hasLobColumns>
import javax.persistence.Lob;
</#if>
<#if pkFieldType == "Integer" || pkFieldType == "Long">
import javax.persistence.SequenceGenerator;
</#if>
import javax.persistence.Table;
<#if hasVersionColumn>
import javax.persistence.Version;
</#if>

/**
 * Model class for ${label}
<#list remarkLines as remarkLine>
 * ${remarkLine}
</#list>
 */
public class ${className}Model{



<#list columns as column>
	//${column.label}
	private ${column.fieldType} ${column.fieldName};
</#list>

<#list columns as column>
	/**
	 * Get ${column.label}
<#list column.remarkLines as remarkLine>
	 * ${remarkLine}
</#list>
	 */
	public ${column.fieldType} ${column.getterMethodName}() {
		return ${column.fieldName};
	}

	/**
	 * Set ${column.label}
<#list column.remarkLines as remarkLine>
	 * ${remarkLine}
</#list>
	 */
	public void ${column.setterMethodName}(${column.fieldType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}

</#list>
}
