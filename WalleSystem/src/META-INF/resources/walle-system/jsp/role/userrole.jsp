<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){
		//组织查询
		$("#queryBtn").searchbox("options").searcher = function(value, name){
			 $("#orgTree").tree("commonQuery", {
	            queryFields : [{
	                fieldName : "[name,manage]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            },{
	            	fieldName: "state",
	            	fieldValue: "U"
	            }]
	        });
		};
		
		//折叠
		$("#collapseBtn").click(function(){
			$("#orgTree").tree("collapseAll");
		});
		
		//展开
		$("#expandBtn").click(function(){
			$("#orgTree").tree("expandAll");
		});
		
		//用户信息     查询
		$("#queryUserBtn").click(function(){
			$("#orgUsersGrid").datagrid("load");	
		});
		
		//用户信息查询     清空
		$("#resetBtn").click(function(){
			var orgId = $("#queryUsersForm").form("getData").organizeId;
			$("#queryUsersForm").form("clear");	
			$("#queryOrganizeId").val(orgId);
		});
		
		
		//组织列表
		$("#orgTree").click(function(){
			var node = $("#orgTree").tree("getSelected");
			$("#queryOrganizeId").val(node.id);
			$("#orgUsersGrid").datagrid("load");
		});
		
		//当选中用户后显示该用户的拥有的角色和未拥有的角色
		$("#orgUsersGrid").datagrid("options").onSelect = function(rowIndex,rowData){
			$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
			$("#hadGivenRoleGrid").datagrid("setQueryFields",[{
				"fieldName":"userId",
				"fieldValue":rowData.userId
			}]);
			$("#hadGivenRoleGrid").datagrid("commonQuery",{
				queryType:"UserGrantedRoleQuery"
			});
			
			/*已授权功能*/
			$("#notGivenRoleGrid").datagrid("setQueryFields",[{
				"fieldName":"userId",
				"fieldValue":rowData.userId
			}]);
			$("#notGivenRoleGrid").datagrid("commonQuery",{
				queryType:"UserNoGrantedRoleQuery"
			});
		};
		
		
		//添加角色
		$("#addRoleToUserBtn").click(function(){
			var userRow = $("#orgUsersGrid").datagrid("getSelected");
			var userId="";
			var roleIdStrs='';
			if(userRow){
				userId = userRow.userId;				
			}else{
				$.messager.alert("提示","请选择用户!","warning");
				return;
			}
			var rows = $("#notGivenRoleGrid").datagrid("getSelections");
			if(rows==''||rows==null){
				$.messager.alert("提示","请选择要添加的角色！","warning");
				return;
			}
			if(rows){
				for(var i=0;i<rows.length;i++){
					roleIdStrs += rows[i].roleId +",";
				}
				WlUserRoleManager.saveUserRoles(userId,roleIdStrs,function(){
					$.messager.alert("提示","添加角色成功!","info");
					$("#hadGivenRoleGrid").datagrid("reload");
					$("#notGivenRoleGrid").datagrid("reload");
					return;
				});
			}else{
				$.messager.alert("提示","请选择要添加的角色！","warning");
				return;
			}
		});
		
		//解除角色
		$("#delRoleFromUserBtn").click(function(){
			var userRow = $("#orgUsersGrid").datagrid("getSelected");
			if(!userRow){
				$.messager.alert("提示","请选择用户!","warning");
				return;
			}
			var rows = $("#hadGivenRoleGrid").datagrid("getSelections");
			if(rows==''||rows==null){
				$.messager.alert("提示","请选择要解除的角色！","warning");
				return;
			}
			if(rows){
				var roleIdStrs='';
				for(var i=0;i<rows.length;i++){
					roleIdStrs += rows[i].roleUserId +",";
				}
				WlUserRoleManager.removeUserRoles(roleIdStrs,function(){
					$.messager.alert("提示","解除角色成功!","info");
					$("#hadGivenRoleGrid").datagrid("reload");
					$("#notGivenRoleGrid").datagrid("reload");
					return;
				});
			}else{
				$.messager.alert("提示","请选择要解除的角色！","warning");
				return;
			}
		});
	});
	</script>
	<div class="easyui-layout" fit="true" border="false">
		<!-- 组织列表 -->
		<div region="west" style="width:255px;">
		    <div class="datagrid-toolbar">
		    	 <span class="panel-header panel-title"  style="float: left; border-style: none; width: 50px;">组织树</span>
		    	 <a id="collapseBtn" class="easyui-linkbutton" iconCls="icon_left" >折叠</a>
				 <a id="expandBtn" class="easyui-linkbutton" iconCls="icon_right">展开</a>
		    </div>
			 <div id="dlg-toolbar" style="padding:2px 0">  
		        <table cellpadding="0" cellspacing="0" style="width:100%">  
		            <tr>  
		                <td style="padding-left:2px"> 
					      <input id="queryBtn" class="easyui-searchbox" prompt='组织名称，负责人' style="width:230px" /> 
		                </td>   
		            </tr>  
		        </table>  
		    </div> 
			<ul id="orgTree" class="easyui-tree" queryType="WlOrganizeModel"  queryFields="[{fieldName:'state',fieldValue:'U'}]"
				idField="organizeId" textfield="name" animate="false" parentField="parentOrganizeId"></ul>
		</div>
		<!-- 用户信息 -->
		<div region="center" class="easyui-layout" border="false">
			<div region="north" border="true"> 
				<div class="datagrid-toolbar" border="false" fit="true">
					<a id="queryUserBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a id="resetBtn" class="easyui-linkbutton" iconCls="icon-reload">清空</a>
				</div>
				<div id="queryUsersForm" class="easyui-form" columns="3" i18nRoot="queryUsersFormSYS" border="false" fit="true" >
					<input name="state" class="easyui-combobox" codeType="user_status"/>
					<input name="name" operator="ilikeAnywhere"/>
					<input name="loginName"  operator="ilikeAnywhere"/>
					<input id="queryOrganizeId" name="organizeId" type="hidden"/>
				</div>
			</div>
			<!-- 用户列表 -->
			<div region="center"class="easyui-layout" border="false">
				<div region="north"   style="height:200px;" border="true">
					<table id="orgUsersGrid" title="用户列表" iconCls="icon-reload" class="easyui-datagrid" singleSelect="true" 
							 paramForm="queryUsersForm" queryType="UserOrganizeQuery" i18nRoot="orgUsersGridSYS" fit="true" border="false">
						<thead>
							<tr>
								<th field="state" class="easyui-combobox" codeType="user_status"></th>
								<th field="loginName" width="100px;"></th>
								<th field="name" width="200px;"></th>
								<th field="email" width="150px;"></th>
								<th field="homeTel" width="100px;"></th>
								<th field="officeTel" width="100px;"></th>
								<th field="mobileTele" width="100px;"></th>
								<th field="addrId" width="100px;"></th>
								<th field="qq" width="100px;"></th>
								<th field="msn" width="100px;"></th>
							</tr>
						</thead>
					</table>
				</div>
				<div region ='center' class="easyui-layout" border="false">
					<div region="west" style="width:320px;" border="true">
							<table id="hadGivenRoleGrid"  title="已授权角色" iconCls="icon-reload"  class="easyui-datagrid" checkOnSelect="true"
														  fit="true" i18nRoot="hadGivenRoleGridSYS" border="false" >
								<thead>
									<tr >
										<th field="name"  style="width:200px;"></th>
									</tr>
								</thead>
							</table>
					</div>
					<div region="center" border="true">
						<div class="easyui-panel" style="margin-top: 80px;" align="center" border="false">
								<a type="button" id="delRoleFromUserBtn"  class="easyui-linkbutton" iconCls="icon_right">解除角色</a>
								<br>
								<br>
								<a type="button" id="addRoleToUserBtn"  class="easyui-linkbutton" iconCls="icon_left">添加角色</a>
						</div>
					</div>
					<div region="east"  style="width:220px;" border="true">
							<table id="notGivenRoleGrid" title="可授权角色" iconCls="icon-reload"  class="easyui-datagrid" fit="true" checkOnSelect="true" 
														 i18nRoot="notGivenRoleGridSYS" border="fasle">
								<thead>
									<tr>
										<th field="name"  style="width:200px;"></th>
									</tr>
								</thead>
							</table>
					</div>
				</div>
			</div>
		</div>
	</div>
