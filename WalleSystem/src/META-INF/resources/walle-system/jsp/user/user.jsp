<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){
		//组织树     查询
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
		
		//组织树     折叠
		$("#collapseBtn").click(function(){
			$("#orgTree").tree("collapseAll");
		});
		
		//组织树     展开
		$("#expandBtn").click(function(){
			$("#orgTree").tree("expandAll");
		});
		
		//用户查询   查询
		$("#queryUserBtn").click(function(){
			$("#orgUsersGrid").datagrid("reload");
		});
		
		//用户查询    清空
		$("#resetBtn").click(function(){
			var orgId =$("#queryUsersForm").form("getData").organizeId;
			$("#queryUsersForm").form("clear");
			$("#queryUsersForm").form("setData", {
				"organizeId" : orgId
			});
		});
		
		//用户列表      新增
		$("#addUserBtn").click(function(){
			$("#userInfoForm").form("clear");
			$("#winUserState").val("U");
			$("#userInfoWin").window("open");
		});
		
		//用户列表      编辑
		$("#editUserBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			if(row){
				if(row.state=="F"){
					$.messager.alert("提示","用户已经被禁用，不能进行编辑！","warning");
					return;
				}else{
					$("#editUserInfoForm").form("setData",row);
					$("#editUserInfoWin").window("open");
				}
			}else{
				$.messager.alert("提示","请选择要修改的用户！","warning");
				return;
			}
		});
		
		//用户列表      密码重置
		$("#resetPasswordBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			if(row){
				$.messager.confirm("提示","是否重置所选择用户的登陆密码？",function(flag){
					if(flag){
						WlUserManager.passwordReset(row.userId,function(){
							$.messager.alert("提示","密码重置成功，默认密码为[123456]","info");
							return;
						});
					}
				});
			}else{
				$.messager.alert("提示","请选择要重置密码的用户！","warning"	);
				return;
			}
		});
		
		//用户列表      禁用
		$("#disableBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			if(row){
				if(row.state=='F'){
					$.messager.alert("提示","所选用户已被禁用！","warning");return;
				}else{
					$.messager.confirm("提示","是否禁用所选择用户？",function(flag){
						if(flag){
							WlUserManager.forbidUser(row.userId,function(){
								$("#orgUsersGrid").datagrid("reload");
								$.messager.alert("提示","禁用成功！","info");
								return;
							});
						}
					});
				}
			}else{
				$.messager.alert("提示","请选择要禁用的用户！","warning"	);
				return;
			}
		});
		
		//用户列表      启用
		$("#startBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			if(row){
				if(row.state=='U'){
					$.messager.alert("提示","所选用户已被启用！","warning");return;
				}else{
					$.messager.confirm("提示","是否启用所选择的用户？",function(flag){
						if(flag){
							WlUserManager.enableUser(row.userId,function(){
								$("#orgUsersGrid").datagrid("reload");
								$.messager.alert("提示","启用成功！");
								return;
							});
						}
					});
				}
			}else{
				$.messager.alert("提示","请选择要启用用户！","warning");
				return;
			}
		});
		
		//用户列表       删除
		$("#deleteBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			if(row){
				$.messager.confirm("提示","是否删除所选择的用户？",function(flag){
					if(flag){
						WlUserManager.delByPk(row.userId,function(){
							$("#orgUsersGrid").datagrid("reload");
							$.messager.alert("提示","删除用户成功！");
							return;
						});
					}
				});	
			}else{
				$.messager.alert("提示","请选择要删除的用户！","warning");
			}
		});
		
		//新增用户Win 保存
		$("#userInfoSure").click(function(){
			if(!$("#userInfoForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}
			var userInfo = $("#userInfoForm").form("getData");
			if(userInfo.password != userInfo.reWritePassword){
				$.messager.alert("提示","两次输入的密码不一致！","warning");
				return;
			}
			WlUserManager.saveModel(userInfo,function(){
					$("#orgUsersGrid").datagrid("reload");
					$("#userInfoWin").window("close");
					$.messager.alert("提示","新增用户成功！");
					return;
			});
		});
		
		//新增用户Win 取消
		$("#userInfoCancel").click(function(){
			$("#userInfoWin").window("close");
		});
		
		//编辑用户Win  保存
		$("#editUserInfoSure").click(function(){
			if(!$("#editUserInfoForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}
			var userInfo = $("#editUserInfoForm").form("getData");
			WlUserManager.saveModel(userInfo,function(){
					$("#orgUsersGrid").datagrid("reload");
					$("#editUserInfoWin").window("close");
					$.messager.alert("提示","编辑用户信息成功！");
					return;
			});
		});
		
		//编辑用户win 取消
		$("#editUserInfoCancel").click(function(){
			$("#editUserInfoWin").window("close");
		});
		
		
		//组织树
		$("#orgTree").click(function(){
			var node = $("#orgTree").tree("getSelected");
			$("#queryUserOrgId").val(node.id);
			$("#orgUsersGrid").datagrid("reload");
		});
		
		//当选中用户后显示该用户的拥有的权限和角色
		$("#orgUsersGrid").datagrid("options").onSelect = function(rowIndex,rowData){
			$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
			
			$("#userRoleGrid").datagrid("setQueryFields",[{
				"fieldName":"userId",
				"fieldValue":rowData.userId
			}]);
			$("#userRoleGrid").datagrid("commonQuery",{
				queryType:"UserRoleQuery"
			});
			
			/*已授权功能*/
			$("#userFuncGrid").datagrid("setQueryFields",[{
				"fieldName":"userId",
				"fieldValue":rowData.userId
			}]);
			$("#userFuncGrid").datagrid("commonQuery",{
				queryType:"WlUserFuncModel"
			});
			
			/*显示：禁用、启用状态*/
			if(rowData.state=="F"){
				$("#disableBtn").hide();//禁用按钮隐藏
				$("#startBtn").show();//显示启用
			}else{
				$("#startBtn").hide();//启用按钮隐藏
				$("#disableBtn").show();//禁用按钮现显示
			}
		};

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
		    <!-- 组织树 -->
			<ul id="orgTree" class="easyui-tree" queryType="WlOrganizeModel" orderBy="districtId"  queryFields="[{fieldName:'state',fieldValue:'U'}]"
				idField="organizeId" textfield="name" animate="false" parentField="parentOrganizeId"></ul>
		</div>
		<!-- 用户查询、用户列表 -->
		<div region="center" border="false">
			<div class="easyui-layout" fit="true">
				<div region="north">
					<div class="datagrid-toolbar">
						<a id="queryUserBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
						<a id="resetBtn" class="easyui-linkbutton" iconCls="icon-reload">清空</a>
					</div>
					<div id="queryUsersForm" class="easyui-form" columns="3" i18nRoot="queryUsersFormSYS" border="false">
						<input name="state" class="easyui-combobox" codeType="user_status" />
						<input name="name" operator="ilikeAnywhere"/>
						<input name="loginName"  operator="ilikeAnywhere"/>
						<input id="queryUserOrgId" name="organizeId" type="hidden"/>
					</div>
				</div>
				<!-- 用户列表 -->
				<div region="center" border="false">
					<div class="datagrid-toolbar" border="false">
				    	<span class="panel-header panel-title"  style="float: left; border-style: none; width: 50px;">用户列表</span>
				    	<a  id="addUserBtn"  class="easyui-linkbutton" iconCls="icon-add">新增</a>
						<a id="editUserBtn" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
						<a id="resetPasswordBtn" class="easyui-linkbutton" iconCls="icon-tip">密码重置</a>
						<a id="disableBtn" class="easyui-linkbutton" iconCls="icon-remove" style="display:none">禁用</a>
						<a id="startBtn" class="easyui-linkbutton" iconCls="icon-ok">启用</a>
						<a id="deleteBtn" class="easyui-linkbutton" iconCls="icon-no">删除</a>
		    		</div>
					<table id="orgUsersGrid" class="easyui-datagrid" singleSelect="true"  paramForm="queryUsersForm" rownumbers="false" pagination="false"
							 queryType="UserOrganizeQuery" orderby="name" i18nRoot="orgUsersGridSYS" fit="true" border="false">
						<thead>
							<tr>
								<th field="state" class="easyui-combobox" codeType="user_status"></th>
								<th field="loginName"></th>
								<th field="name" ></th>
								<th field="email"></th>
								<th field="homeTel"></th>
								<th field="officeTel"></th>
								<th field="mobileTele"></th>
								<th field="addrId"></th>
								<th field="qq"></th>
								<th field="msn"></th>
							</tr>
						</thead>
					</table>
		    	</div>
				<div region = "south" border="false" style="height:300px;">
					<div class="easyui-layout" fit="true">
						<div region="west" style="width:450px;" >
							<table id="userRoleGrid"  title="已授权角色" iconCls="icon-reload" class="easyui-datagrid"  i18nRoot='userRoleGridSYS' fit="true" border="false">
								<thead >
									<tr>
										<th field="code"></th>
										<th field="name"></th>
									</tr>
								</thead>
							</table>
						</div>
						<div region="center">
							<table id="userFuncGrid" title="已授权功能" iconCls="icon-reload" class="easyui-datagrid" i18nRoot="userFuncGridSYS" border="false" fit="true"  border="false">
								<thead>
									<tr>
										<!-- <th field="userId" class="easyui-combobox" codeType="user"></th>  -->
										<th field="funcId" class="easyui-combobox" codeType="sysfunction"></th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 用户信息Window -->
	<div id="userInfoWin" class="easyui-window" title="新增用户信息" style="height:290px;width:550px;" closed="true" resizable="false" closable="true" maximizable="false" minimizable="false" collapsible="false" modal="true"> 
		<div id="userInfoForm" class="easyui-form" columns="2" style="border:1px solid #33a3dc;" i18nRoot="userInfoFormSYS">
			<input id="winUserState" name="state" type="hidden"/>
			<input name="userId" type="hidden"/>
			<input name="loginName"   class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="name"   class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="password"  type="password" style="width:154px;"  class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="reWritePassword" title="确定密码" type="password" style="width:154px;"  required="true"/>
			<input name="organizeId"  class="easyui-combotree" codeType="org" required="true" />
			<input name="homeTel"/>
			<input name="officeTel"/>
			<input name="mobileTele"/>
			<input name="mobileTele"/>
			<input name="email"/>
			<input name="qq"/>
			<input name="msn"/>
			<input  name="addrId" colspan="2" />
			<input name="remarks" colspan="2" />
		 </div>
		 <div align="right" style="margin-top: 10px;">
			<a id="userInfoSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="userInfoCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>
	
	
	<!-- 编辑用户信息Window -->
	<div id="editUserInfoWin" class="easyui-window" title="编辑用户信息" style="height:267px;width:500px;" closed="true" resizable="false" closable="true" maximizable="false" minimizable="false" collapsible="false" modal="true"> 
		<div id="editUserInfoForm" class="easyui-form" columns="2" style="border:1px solid #33a3dc;" i18nRoot="userInfoFormSYS">
			<input name="loginName"   class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="name"   disabled="disabled"/>
			<input name="organizeId" class="easyui-combotree" codeType="org" required="true"/>
			<input name="homeTel"/>
			<input name="officeTel"/>
			<input name="mobileTele"/>
			<input name="mobileTele"/>
			<input name="email"/>
			<input name="qq"/>
			<input name="msn"/>
			<input name="addrId" colspan="2" />
			<input name="remarks" colspan="2" />
			<input name="userId" type="hidden"/>
		 </div>
		 <div align="right" style="margin-top: 10px;">
			<a id="editUserInfoSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="editUserInfoCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>