<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){
		//初始化
		$("#sysCodeTypeGrid").datagrid("options").onSelect = function(rowIndex,rowData){
			$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
			$("#sysCodeGrid").datagrid("setQueryFields",[{
				"fieldName": "codeTypeId",
				"fieldValue": rowData.typeCode
			}]);
			$("#sysCodeGrid").datagrid("commonQuery",{queryType:"WlSysCodeModel"});
		};
		$("#sysCodeTypeGrid").datagrid("reload");
		
		//查询
		$("#queryBtn").click(function(){
			$("#sysCodeTypeGrid").datagrid("reload");
		});
		
		//重置
		$("#queryResetBtn").click(function(){
			$("#sysCodeTypeQueryForm").form("clear");
		});
	
		//系统代码类型——新增
		$("#addSysCodeTypeBtn").click(function(){
			$("#typeCode").removeAttr("disabled"); 
			$("#sysCodeTypeDialog").dialog("open");
		});
		
		//系统代码类型-编辑
		$("#editSysCodeTypeBtn").click(function(){
			$("#typeCode").attr("disabled",true); 
			var row = $("#sysCodeTypeGrid").datagrid("getSelected");
			if(row){
				$("#sysCodeTypeDialog").dialog("open");
				$("#sysCodeTypeForm").form("load",row);
			}else{
				$.messager.alert("提示","请选择要编辑的系统代码类型！","warning");
			}
		});
		//系统代码类型--删除
		$("#deleteSysCodeTypeBtn").click(function(){
			var row =$("#sysCodeTypeGrid").datagrid("getSelected");
			if(row){
				$.messager.confirm("提示","是否确定删除所选的系统代码？",function(flag){
					if(flag){
						WlSysCodeTypeManager.delByPk(row.codeTypeId,function(data){
							if(data.exception){
								$.messager.alert("提示","删除系统代码失败！","warning");
								return;
							}else{
								$.messager.alert("提示","删除系统代码成功！","info");
								$("#sysCodeTypeGrid").datagrid("reload");
							}
						});
					}
				});
			}else{
				$.messager.alert("提示","请选择要删除的系统代码类型！","warning");
			}
		});
		
		//系统代码类型---Dialog保存
		$("#sysCodeTypeOkBtn").click(function(){
			if(!$("#sysCodeTypeForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}
			if($("#codeTypeId").val()==""){
			 	$("#state").val("U");
			}
			var sysCodeTypeInfo = $("#sysCodeTypeForm").form("getData");
			WlSysCodeTypeManager.saveModel(sysCodeTypeInfo,function(data){
				if(data.exception){
					$.messager.alert("提示","保存失败！","warning");
					return;
				}else{
					$("#sysCodeTypeDialog").dialog("close");
					$("#sysCodeTypeGrid").datagrid("reload");
					$("#sysCodeTypeForm").form("clear");
					$.messager.alert("提示","保存成功！","info");
				}
			});
		});
		
		//系统代码类型---Dialog取消
		$("#sysCodeTypeCancelBtn").click(function(){
			$("#sysCodeTypeDialog").dialog("close");
			$("#sysCodeTypeForm").form("clear");
		});
		
		//系统代码--新政
		$("#addSysCodeBtn").click(function(){
			var row = $("#sysCodeTypeGrid").datagrid("getSelected");
			if(row){
				$("#typeId").val(row.typeCode);
				$("#sysCodeDialog").dialog("open");
			}else{
				$.messager.alert("提示","请选择系统代码类型!","warning");
			}
		});
		
		//系统代码--编辑
		$("#editSysCodeBtn").click(function(){
			var changerows = $("#sysCodeGrid").datagrid("getChanges");
			if(changerows.length!=0){
				$.messager.alert("提示","请先保存修改的代码数据！","warning");
				return;
			}
			var row = $("#sysCodeGrid").datagrid("getSelected");
			if(row){
				$("#sysCodeDialog").dialog("open");
				$("#sysCodeForm").form("load",row);
			}else{
				$.messager.alert("提示","请选择系统代码！","warning");
			}
		});
		
		//系统代码--插入
		$("#insertSysCodeBtn").click(function(){
			var row = $("#sysCodeTypeGrid").datagrid("getSelected");
			if(row){
				$("#sysCodeGrid").datagrid("insertRow",{
					index:$("#sysCodeTypeGrid").datagrid("getRowIndex",{}),
					row:{ "codeTypeId":row.typeCode}
				});
			}else{
				$.messager.alert("提示","请选择代码类型","warning");
			}
		});
		
		//系统代码--保存
		$("#saveSysCodeBtn").click(function(){
			var rows = $("#sysCodeGrid").datagrid("getChanges");
			if(rows.length==0){
				$.messager.alert("提示","未修改数据！","warning");
				return;
			}
			if(!$("#sysCodeGrid").datagrid("validate")){
				$.messager.alert("提示","数据验证错误!","warning");
				return;
			}else{
				WlSysCodeManager.saveGrid(rows,function(result){
					$("#sysCodeGrid").datagrid("refreshSavedData",result);
					$.messager.alert("提示","保存成功！","info");
				});
			}
		});
		
		//系统代码类---取消
		$("#cancelSysCodeBtn").click(function(){
			var rows = $("#sysCodeGrid").datagrid("getChanges");
			if(rows.length==0){
				$.messager.alert("提示","未修改数据,取消失败！","warning");
				return;
			}else{
				$("#sysCodeGrid").datagrid("reload");
				$("#sysCodeForm").from("clear");
			}
		});	
		
		//系统代码--删除
		$("#deleteSysCodeBtn").click(function(){
			var changerows = $("#sysCodeGrid").datagrid("getChanges");
			if(changerows.length!=0){
				$.messager.alert("提示","请先保存修改的系统代码！","warning");
				return;
			}
			var row = $("#sysCodeGrid").datagrid("getSelected");
			var index = $("#sysCodeGrid").datagrid("getRowIndex",row);
			if(index < 0 ){
				$.messager.alert("提示","请选择删除的系统代码！","warning");
				return;
			}else{
				$.messager.confirm("提示","是否确定删除所选的系统代码",function(flag){
					if(flag){
						WlSysCodeManager.removeByPk(row.codeId,function(data){
							$.messager.alert("提示","删除成功！","info");
							refreshCodeGridBy(row.codeTypeId);
						});
					}
				});
			}
		});
		
		//系统代码----Dialog保存
		$("#sysCodeOkBtn").click(function(){
			if(!$("#sysCodeForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}
			var row  = $("#sysCodeTypeGrid").datagrid("getSelected");
			var syscodeinfo = $("#sysCodeForm").form("getData");
			WlSysCodeManager.saveModel(syscodeinfo,function(data){
					$.messager.alert("提示","保存系统代码成功！","info");
					$("#sysCodeDialog").dialog("close");
					$("#sysCodeForm").form("clear");
					refreshCodeGridBy(row.typeCode);
			});
		});
		
		//系统代码---Dialog取消保存
		$("#sysCodeCancelBtn").click(function(){
			$("#sysCodeDialog").dialog("close");
		});
		
		//刷新sysCodeGrid
		function refreshCodeGridBy(id){
			$("#sysCodeGrid").datagrid("setQueryFields",[
			 	{
			 		"fieldName":"codeTypeId",
			 		"fieldValue":id
			 	}                                           
			 ]);
			$("#sysCodeGrid").datagrid("commonQuery",{queryType:"WlSysCodeModel"});
		}
	});
	</script>
	<div  class='easyui-layout' fit="true">
		<!-- 查询条件form -->
		<div id='sysCodeTypeQueryDiv' region="north" title='查询条件' split="true" iconCls='icon-search'>
			<div class='datagrid-toolbar'>
				<a id='queryBtn' class='easyui-linkbutton' iconCls='icon-search' key='F'>查询</a> 
				<a id='queryResetBtn' class='easyui-linkbutton' iconCls='icon-reload' key='R'>重置</a>
			</div>
			<form id="sysCodeTypeQueryForm" class='easyui-form' columns='3' i18nRoot='sysCodeTypeQueryFormSYS'>
				<input name="codeTypeName" operator="ilikeAnywhere"/>
				<input name="typeCode" operator='ilikeAnywhere'/>
			</form>
			<br></br>
		</div>
		
		<!-- 系统代码类型grid -->
		<div id='sysCodeTypeDiv' region='west' title='系统代码类型' style="width:500px;" class="easyui-layout" >
			 <div region="north" class="datagrid-toolbar">
			 	<a id='addSysCodeTypeBtn' class='easyui-linkbutton' iconCls='icon-add'>新增</a>
			 	<a id='editSysCodeTypeBtn' class='easyui-linkbutton' iconCls='icon-edit'>编辑</a>
			 	<a id="deleteSysCodeTypeBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			 </div>
			 <div region="center">
				 <table id='sysCodeTypeGrid' class='easyui-datagrid' fit='true' queryType='WlSysCodeTypeModel' paramForm='sysCodeTypeQueryForm' singleSelect="true"  i18nRoot='sysCodeTypeGridSYS' >
				 	<thead>
				 		<tr>
				 			<th field='codeTypeName'/>
				 			<th field='typeCode'/>
				 		</tr>
				 	</thead>
				 </table>
			 </div>
		</div>
		
		<!-- 系统代码grid-->
		<div id='sysCodeDiv' region='center' title='系统代码' style="with:500px;" class="easyui-layout">
			<div region="north" class="datagrid-toolbar">
				<a id='addSysCodeBtn' class='easyui-linkbutton' iconCls='icon-add'>新增</a>
				<a id='editSysCodeBtn' class='easyui-linkbutton' iconCls='icon-edit'>编辑</a>
				<a id="insertSysCodeBtn" class="easyui-linkbutton" iconCls="icon-add" >插入</a>
			 	<a id="saveSysCodeBtn" class="easyui-linkbutton" iconCls="icon-save" >保存</a>
			 	<a id="cancelSysCodeBtn" class="easyui-linkbutton" iconCls="icon-undo" >取消</a>
			 	<a id="deleteSysCodeBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			</div>
			<div region="center">
				<table  id='sysCodeGrid' class='easyui-datagrid'  fit='true' queryType='WlSysCodeModel'  i18nRoot='sysCodeGridSYS' init="false" >
					 <thead>
					 	<tr>
					 		<th field='codeName'  editor="{type:'validatebox',options:{required:true,validType:'length[0,20]'}}"></th>
					 		<th field='codeValue' editor="{type:'validatebox',options:{required:true,validType:'length[0,20]'}}"></th>
					 		<th field='sort'  editor="text"></th>
					 		<th field="codeTypeId"></th>
					 	</tr>
					 </thead>
				</table>
			</div>
		</div>
		
		<!-- 弹出系统代码类型 新增/编辑 窗口 -->
		<div id='sysCodeTypeDialog' class="easyui-dialog" title='系统代码类型详细信息' iconCls='icon-help' style="width:530px;height:200px;" closed="true" modal="true">	
			<form id="sysCodeTypeForm" class='easyui-form' columns='2' il8Root='sysCodeTypeFormSYS'>
				<input name="codeTypeName" class="easyui-validatebox" required="true" validType="length[0,20]"></input>
				<input id="typeCode"  name="typeCode"     class="easyui-validatebox" required="true"  validType="length[0,20]" />
				<input name="system"  class="easyui-combobox" codeType="yesNo"/>
				<input name="codeTypeDesc"/>
				<input name="dateType"/>
				<input name="remarks"/>
				<input id="codeTypeId" name="codeTypeId" type="hidden"/>
				<input id="state" name="state"  type="hidden"/>
			</form>
			<div class="dialog-buttons">
				<a id="sysCodeTypeOkBtn" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
				<a id="sysCodeTypeCancelBtn" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</div>
		</div>
		
		<!-- 弹出系统代码  新增/编辑 按钮 -->
		<div id="sysCodeDialog" class="easyui-dialog" title="系统代码详细信息" iconCls="icon-help" style="width:530px;height:200px;" closed="true" modal="true">
			<form id="sysCodeForm" class="easyui-form" columns="2" il8Root="sysCodeFormSYS">
				<input id="typeId" name="codeTypeId" required="true" disabled="disabled" />
				<input name="codeName" class="easyui-validatebox" required="true" validType="length[0,20]"/>
				<input name="codeValue" class="eayui-validatebox" required="true" validType="length[0,20]"/>
				<input name="sort"/>
				<input name="codeDesc"/>
				<input name="parentCodeValue"/>
				<input name="codeId" type="hidden"/>
			</form>
			<div class="dialog-buttons">
				<a id="sysCodeOkBtn" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
				<a id="sysCodeCancelBtn" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</div>
		</div>
	</div>