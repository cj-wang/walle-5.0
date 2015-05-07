package ${packageName};

<#if hasDateParams>
import java.util.Date;

</#if>
import cn.walle.framework.core.query.BaseQueryCondition;

public class ${className}QueryCondition extends BaseQueryCondition {

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
