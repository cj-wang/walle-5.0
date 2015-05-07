package ${packageName};

<#if hasDateParams>
import java.util.Date;

</#if>

public class ${className}QueryCondition{

<#list params as param>
	private ${param.fieldType} ${param.fieldName};
</#list>

<#list params as param>
	public ${param.fieldType} ${param.getterMethodName}() {
		return ${param.fieldName};
	}

	public void ${param.setterMethodName}(${param.fieldType} ${param.fieldName}) {
		this.${param.fieldName} = ${param.fieldName};
	}

</#list>
}
