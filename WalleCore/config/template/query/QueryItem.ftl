package ${packageName};

<#if hasDateColumns>
import java.util.Date;

</#if>
import javax.persistence.Column;
import javax.persistence.Entity;
<#if hasLobColumns>
import javax.persistence.Lob;
</#if>

import cn.walle.framework.core.query.BaseQueryItem;

@Entity
public class ${className}QueryItem extends BaseQueryItem {

<#list columns as column>
	private ${column.fieldType} ${column.fieldName};
</#list>

<#list columns as column>
	@Column(name = "${column.columnName}")
<#if column.columnType == "BLOB" || column.columnType == "CLOB">
	@Lob
</#if>
	public ${column.fieldType} ${column.getterMethodName}() {
		return ${column.fieldName};
	}

	public void ${column.setterMethodName}(${column.fieldType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
		addValidField("${column.fieldName}");
	}

</#list>
}
