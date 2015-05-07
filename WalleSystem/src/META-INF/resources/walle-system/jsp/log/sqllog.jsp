<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">

$(function() {
	//查询
	$("#btnQuery").click(function() {
		$("#sqllogGrid").datagrid("load");
	});
	
	//重置
	$("#btnReset").click(function() {
		$("#formQuery").form("clear");
	});
	
});

</script>


<div class="easyui-layout" fit="true">
	
	<div region="north">
		<div class="datagrid-toolbar">
			<a id="btnQuery" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a id="btnReset" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
		</div>
		<form id="formQuery" class="easyui-form wp-config" columns="4" i18nRoot="WlSqlLogModel">
			<input name="userId" class="easyui-combobox" codetype="vss_userId" />
			<div colspan="2">
				<input name="logTime" class="easyui-datetimebox" operator="dateBegin"/> -
				<input name="logTime" class="easyui-datetimebox" operator="dateEnd"/>
			</div>
		</form>
	</div>
	
	<div region="center" title="资源调取日志信息" iconCls="icon-edit">
		<table id="sqllogGrid" class="easyui-datagrid wp-config" fit="true"
				queryType="WlSqlLogModel" paramForm="formQuery" i18nRoot="WlSqlLogModel">
			<thead>
				<tr>
				    <th field="userId"/>
				    <th field="sqlStatement">
					<th field="logTime"/>
					<th field="timeUsed">
					<th field="serviceAccessIndex">
				</tr>
			</thead>
		</table>
	</div>
</div>
