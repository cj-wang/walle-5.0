package ${packageName};

<#if hasDateColumns>
import java.util.Date;

</#if>
import javax.persistence.Column;
import javax.persistence.Entity;
<#if hasLobColumns>
import javax.persistence.Lob;
</#if>

public class ${className}QueryItem {

<#list columns as column>
	private ${column.fieldType} ${column.fieldName};
</#list>

<#list columns as column>
	public ${column.fieldType} ${column.getterMethodName}() {
		return ${column.fieldName};
	}

	public void ${column.setterMethodName}(${column.fieldType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}

</#list>
}
