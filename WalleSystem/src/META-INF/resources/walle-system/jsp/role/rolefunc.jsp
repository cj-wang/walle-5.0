<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
		$(function(){
			//角色查询
			$("#queryRoleBtn").searchbox("options").searcher= function(value,name){
				$("#roleGrid").datagrid("commonQuery",{
					queryFields:[{
						fieldName:"[name,code]",
						fieldStringValue:value,
						operator:"ilikeAnywhere"
					}]
				});
			};
			
			//设置orgGrid
			$("#roleGrid").datagrid("load");
			
			$("#roleGrid").datagrid("options").onSelect = function(rowIndex,rowData){
				$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
				//清空选中的上次角色功能
				var checkedNodes = $("#funcTree").tree("getChecked");
				for(var i=0;i<checkedNodes.length;i++){
					$("#funcTree").tree("uncheck",checkedNodes[i].target);
				}
				//查找到角色所拥有的功能
				WlRoleFuncManager.getRoleFunc(rowData.roleId,function(data){
					if(data==null){
						return;
					}
					for(var i=0;i<data.length;i++){
						if(data[i]=="1"){continue;}
						var node = $("#funcTree").tree("find",data[i]);
						//wcj: 不处理父节点
						if ($("#funcTree").tree("getChildren", node.target).length > 0) {
							continue;
						}
						$("#funcTree").tree("check",node.target);
					};
				});
			};
			
			//折叠树
			$("#collapseTreeBtn").click(function(){
				$("#funcTree").tree("collapseAll");
			});
			
			//展开树
			$("#expendTreeBtn").click(function(){
				$("#funcTree").tree("expandAll");
			});
			
			//保存
			$("#saveRoleFuncBtn").click(function(){
				var row = $("#roleGrid").datagrid("getSelected");
				var roleId='';
				if(row){
					roleId = row.roleId;
				}else{
					$.messager.alert("提示","请选择对应的角色！","warning");
					return;
				}
				var checkedNodes = $("#funcTree").tree("getChecked");
				var checkedArray = new Array();
				for(var i=0;i<checkedNodes.length;i++){
					checkedArray.push(checkedNodes[i].id);
				}
				//wcj: 同时保存半选中状态的父节点
				var halfCheckedNodes = $("#funcTree").tree("getHalfChecked");
				for(var i=0;i<halfCheckedNodes.length;i++){
					checkedArray.push(halfCheckedNodes[i].id);
				}
				WlRoleFuncManager.saveFunc(roleId,checkedArray,function(){
						$.messager.alert("提示","保存成功！","info");
						return;
				});
			});
		});
		
	</script>
	<div class="easyui-layout" fit="true"  border="false">
		<div region="west" class="easyui-layout" style="width:265px;"  border="false">
	   		<div region="center" title="角色列表" iconCls="icon-reload" >
	   			<div id="dlg-toolbar"   style="padding:2px 0" border="false">  
			        <table cellpadding="0" cellspacing="0" style="width:100%">  
			            <tr>  
			                <td style="padding-left:2px"> 
						      <input id="queryRoleBtn" class="easyui-searchbox" prompt='角色名称，角色代码' style="width:240px" /> 
			                </td>   
			            </tr>  
			        </table>  
	   			</div> 
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
		<div region="center" class="easyui-layout" border="false">
				<div region="north" class="datagrid-toolbar" border="true">
			    	<span class="panel-header panel-title"  style="float: left; border-style: none; width: 70px;">系统功能树</span>
			    	<a id="collapseTreeBtn" class="easyui-linkbutton" iconCls="icon_left">折叠</a>
				    <a id="expendTreeBtn" class="easyui-linkbutton" iconCls="icon_right">展开</a>
				    <a id="saveRoleFuncBtn" class="easyui-linkbutton" iconCls="icon-save">保存</a> 
	    		</div>
				<div region="center"  border="true">
						<ul id="funcTree" class="easyui-tree"  queryType="WlFunctionModel"  orderBy="funSeq"
						idField="funcId" textField="name" animate="true" parentField="parentId"  seqField="funSeq" checkbox="true"/>
				</div>
		</div>	
	</div>