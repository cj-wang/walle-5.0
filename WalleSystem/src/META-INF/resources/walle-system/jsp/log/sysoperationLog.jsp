<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">

	$(function(){
		$("#query").click(function() {
			$("#sysOperationGrid").datagrid("load");
		});
		//重置
		$("#formClear").click(function() {
			$("#search_form").form("clear");
		});
		//
		$("#showInfo").click(function(){
			 var row = $("#sysOperationGrid").datagrid("getSelected");
			 if(row){
				 $("#info_form").form("load", row);
				 $("#infoDig").dialog("open");
			 }else{
				 $.messager.alert("提示","请选择要查看的数据！","warning");
			 }
		});
	});
	
</script>
	<div class="easyui-layout" fit="true">
		
		<div region="north">
			<div class="datagrid-toolbar">
			    <a class="easyui-linkbutton" iconCls="icon-search" id="query">查询</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" id="formClear">重置</a>
			 </div>
	   		<form id="search_form" class="easyui-form wp-config"  columns="4" i18nRoot=WlSysLogModel>
	   		    <input name="operUserId" title="操作者" class="easyui-combobox" codeType="vss_userId" />
	   			<input name="operOject" title="操作对象"  class="easyui-combobox" codeType="vss_operOject" /> 
	   		 <div colspan="2">
				<input name="logDate" class="easyui-datetimebox" operator="dateBegin"/> -
				<input name="logDate" class="easyui-datetimebox" operator="dateEnd"/>
			 </div>
	   		</form>
			 
		</div>
				
		<div region="center" id="layout_crenter">
	        <div class="datagrid-toolbar">
	         <span class="panel-header panel-title" style="float: left; border-style: none; width: 100px;">系统操作日志信息</span>
			 <a class="easyui-linkbutton" iconCls="icon-convert" id="showInfo">详情</a> 
		    </div>
			<table id="sysOperationGrid" class="easyui-datagrid wp-config" i18nRoot="WlSysLogModel" querytype="WlSysLogModel" paramForm="search_form"
    		 fitColumns="true" fit="true" singleSelect = "true" sortName="logDate" sortOrder="desc" >
				<thead>
					<tr>
					    <th field="operUserId" title="操作者" codeType="vss_userId"/>
						<th field="operOject"  title="操作对象" codeType="vss_operOject"/>
					    <th field="operAction" title="操作动作"/>
						<th field="logDate" title="记录日期"/>
						<th field="logDesc" title="日志描述"/>
						<th field="remarks" title="备注" />
					</tr>
				</thead>
			</table>
		</div>
		
		 <!-- xiang qing-->
   		 <div id="infoDig" class="easyui-dialog" title="详情" iconCls="icon-save"
			style="width:650px;padding:10px" closed="true" modal="true">
		    <form id="info_form" class="easyui-form" orientation="horizontal" columns="2" i18nRoot="WlSysLogModel" >
			    <input name="operUserId"  class="easyui-combobox" codeType="vss_userId"  title="操作者" disabled="disabled"/>
				<input name="operOject" title="操作对象" class="easyui-combobox" codeType="vss_operOject" disabled="disabled"/>
				<input name="operAction" title="操作动作"  disabled="disabled"/>
				<input name="logDate" title="记录日期"  disabled="disabled"/>
				<input name="logDesc" title="日志描述"  disabled="disabled"/>
				<div></div>
				<textarea colspan="2" name="remarks"  title="备注" style="width: 380px; height:100px;" id="maintenanceDetail" disabled="disabled"></textarea>
			</form>
		</div>
	</div>
