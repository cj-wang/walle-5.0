<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){
		
		$("#roleGrid").datagrid("load");
		
		$("#roleGrid").datagrid("options").onSelect = function(rowIndex,rowData){
			$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
			//角色信息Form
			$("#roleInfoFrom").form("load",rowData);
			
			//已授权用户GRID
			$("#hadRoleUserGrid").datagrid("setQueryFields",[{
				fieldName:"roleId",
				fieldValue:rowData.roleId
			}]);
			$("#hadRoleUserGrid").datagrid("commonQuery",{
				queryType:"RoleGrantedUserQuery"
			});
			
			//已授权功能GRID
			$("#hadRoleFuncGrid").datagrid("setQueryFields",[{
				fieldName:"roleId",
				fieldValue:rowData.roleId
			}]);
			$("#hadRoleFuncGrid").datagrid("commonQuery",{
				queryType:"FuncGrantedRoleQuery"
			});
		};
		
		//查询
		$("#queryRoleBtn").searchbox("options").searcher= function(value,name){
			$("#roleGrid").datagrid("commonQuery",{
				queryFields:[{
					fieldName:"[name,code]",
					fieldStringValue:value,
					operator:"ilikeAnywhere"
				}]
			});
		};
		
		//新增
		$("#addRoleBtn").click(function(){
			$("#addRoleInfoForm").form("clear");
			$("#addRoleInfoDlg").dialog("open");
		});
		
		//编辑 
		$("#editRoleBtn").click(function(){
			var rowData = $("#roleGrid").datagrid("getSelected");
			$("#addRoleInfoForm").form("load",rowData);
			$("#addRoleInfoDlg").dialog("open");
		});
		
		//新增角色        保存
		$("#addRoleInfoSure").click(function(){
			if(!$("#addRoleInfoForm").form("validate")){
				$.messager.alert("提示","数据校验失败！","warning");
				return;
			}else{
				var roleInfo = $("#addRoleInfoForm").form("getData");
				WlRoleManager.saveModel(roleInfo ,function(data){
					if(data.exception){
						$.messager.alert("提示","保存失败，请重新保存！","warning");
						return;
					}else if(data=='保存成功！'){
						$("#roleGrid").datagrid("reload");
						$.messager.alert("提示",data,"info");
						$("#addRoleInfoDlg").dialog("close");
						return;
					}else{
						$.messager.alert("提示",data,"info");
						return;
					}
				});
			}
		});
		
		//新增角色     取消
		$("#addRoleInfoCancel").click(function(){
			$("#addRoleInfoDlg").dialog("close");
		});
		
		//删除
		$("#deleteRoleBtn").click(function(){
			var row = $("#roleGrid").datagrid("getSelected");
			if(row){
				$.messager.confirm("提示","是否删除所选的角色？",function(flag){
					if(flag){
						WlRoleManager.delByPk(row.roleId,function(data){
							if(data.exception){
								$.messager.alert("提示","删除失败，请重新删除！","warning");
								return;
							}else{
								$("#roleGrid").datagrid('reload');
								$.messager.alert("提示",data,"info");
							}
						});
					}
				});
			}else{
				$.messager.alert("提示","请选择要删除的角色！","waring");
				return;
			}
		});
	});
	</script>
	<div class="easyui-layout" fit="true" border="false">
		<div region="west" class="easyui-layout" style="width:260px;" border="true">
			<div region="north" border="false">
				<div class="datagrid-toolbar"  fit="true">
					 <span class="panel-header panel-title"  style="float: left; border-style: none; width: 50px;">角色列表</span>
					 <a id="addRoleBtn" class='easyui-linkbutton' iconCls="icon-add">新增</a>
					 <a id="editRoleBtn" class='easyui-linkbutton' iconCls="icon-edit">编辑</a>
					 <a id="deleteRoleBtn" class="easyui-linkbutton" iconCls="icon-no">删除</a>
				</div>
				<div id="dlg-toolbar" style="padding:2px 0" border="false">  
			        <table cellpadding="0" cellspacing="0" style="width:100%">  
			            <tr>  
			                <td style="padding-left:2px"> 
						      <input id="queryRoleBtn" class="easyui-searchbox" prompt='角色名称，角色代码' style="width:230px" /> 
			                </td>   
			            </tr>  
			        </table>  
		   		</div> 
			</div>
			<div region="center"  border="false">
				<table id="roleGrid" class="easyui-datagrid"  paramForm="queryRoleInfoForm" rownumbers="false" pagination="false"
									 queryType="WlRoleModel" i18nRoot="roleGridSYS"  border="false" fit="true">
					<thead>
				 		<tr>
				 			<th field="name"></th>
				 			<th field="code"></th>
				 		</tr>
				 	</thead>
				</table>
			</div>
		</div>
		
		<div region="center" class="easyui-layout" border="true">
			<div region="north" title="详细信息" border="false">
				 <div id="roleInfoFrom" class="easyui-form" columns="2" i18nRoot="roleInfoFromSYS">
				 	<input name="code"   disabled="disabled">
				 	<input name="name"   disabled="disabled">
				 	<input name="remarks" colspan="2" disabled="disabled">
				 </div>
			</div>
			<div region="west"  style="width:380px;" border="true">
				 <table id="hadRoleUserGrid"  title="已授权用户" iconCls="icon-reload" class="easyui-datagrid" fit="true" i18nRoot="hadRoleUserGridSYS" border="false">
				 	<thead>
				 		<tr>
				 			<th field="organizeId" class="easyui-combobox" codeType="org"></th>
				 			<th field="loginName"></th>
				 			<th field="name"></th>
				 		</tr>
				 	</thead>
				 </table>
			</div>
			<div region="center" border="true">
				 <table id="hadRoleFuncGrid"  title="已授权功能" iconCls="icon-reload" class="easyui-datagrid" fit="true" i18nRoot="hadRoleFuncGridSYS" border="false">
				 	<thead>
				 		<tr>
				 			<th field="funcCode"></th>
				 			<th field="name"></th>
				 		</tr>
				 	</thead>
				 </table>
			</div>
		</div>
	</div>
	
	
	<!-- 弹出系统代码  新增/编辑 按钮 -->
	<div id="addRoleInfoDlg" class="easyui-dialog" title="角色信息列表" iconCls="icon-help" style="height:200px;width:300px;" closed="true" modal="true">
		<form id="addRoleInfoForm" class="easyui-form" columns="1" i18nRoot="roleInfoFromSYS">
		 	<input name="name"   class="easyui-validatebox" required="true"  validType="length[0,30]">
			<input name="code"   class="easyui-validatebox" required="true"  validType="length[0,20]">
		 	<textarea rows="2" cols="2" name="remarks" style="hieght:50px;width:160px; resize:none " ></textarea>
		 	<input name="roleId" type="hidden">
		</form>
		<div class="dialog-buttons">
			<a id="addRoleInfoSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="addRoleInfoCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>