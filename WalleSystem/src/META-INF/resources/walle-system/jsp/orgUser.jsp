<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){

		$("#orgUsersGrid").datagrid("load");
		
		/*
		//组织树     查询
		$("#searchOrg").searchbox("options").searcher = function(value, name){
			 $("#orgTree").tree("commonQuery", {
	            queryFields : [{
	                fieldName : "[name,manage]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"         
	            }]
	        });
		};
		*/
		
		//选择org显示用户信息
		$("#orgTree").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			$("#orgUsersGrid").datagrid("commonQuery",{
	            queryFields : [{
	                fieldName : "organizeId",
	                fieldStringValue : node.id
	            }]
			});
		};
		
		//新增
		$("#btnNewOrg").click(function(){
			var node = $("#orgTree").tree("getSelected");
			$("#sysOrgInfoDialogForm").form("setData", {
				parentOrganizeId : node ? node.attributes.data.parentOrganizeId : "0"
			});
			$("#orgInfoDialog").dialog("open");
		});
		
		//新增子节点
		$("#btnNewChildOrg").click(function(){
			var node = $("#orgTree").tree("getSelected");
			if (node) {
				$("#sysOrgInfoDialogForm").form("setData", {
					parentOrganizeId : node.id
				});
				$("#orgInfoDialog").dialog("open");
			}
		});
		
		//编辑
		$("#btnEditOrg").click(function(){
			var node = $("#orgTree").tree("getSelected");
			if (node) {
				$("#sysOrgInfoDialogForm").form("setData", node.attributes.data);
				$("#orgInfoDialog").dialog("open");
			}
		});
		
		//删除
		$("#btnDeleteOrg").click(function(){
			var node = $("#orgTree").tree("getSelected");
			if (node) {
				$.messager.confirm("提示","是否删除所选择的组织？",function(deleteFlag){
					if(deleteFlag){
						WlOrganizeManager.delByPk(node.id,function(){
							$("#orgTree").tree("reload");
							$("#combotreeOrg").combotree("reload");
						});
					}
				});
			}
		});
		
		//dnd后保存
		$("#orgTree").tree("options").onDrop = function(target, source, point) {
			$.fn.tree.defaults.onDrop.apply(this, [target, source, point]);
			var orgs = $("#orgTree").tree("getChanges");
			if (orgs.length > 0) {
				CommonSaveManager.saveTreeData(orgs, "organizeId", "parentOrganizeId", function() {
					$("#orgTree").tree("reload");
					$("#combotreeOrg").combotree("reload");
				});
			}
		};
		
		//新增org保存
		$("#saveOrgInfoBtn").click(function(){
			if(! $("#sysOrgInfoDialogForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}else{
				var orgInfo = $("#sysOrgInfoDialogForm").form("getData");
				WlOrganizeManager.saveModel(orgInfo,function(){
					$("#orgInfoDialog").dialog("close");
					$("#orgTree").tree("reload");
					$("#combotreeOrg").combotree("reload");
				});
			};
		});
		
		//新增org取消
		$("#cancelOrgInfoBtn").click(function(){
			$("#orgInfoDialog").dialog("close");
		});
		


		//用户     查询
		$("#searchUser").searchbox("options").searcher = function(value, name){
			 $("#orgUsersGrid").datagrid("commonQuery", {
	            queryFields : [{
	                fieldName : "[loginName,name,email]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            }]
	        });
		};
		
		//用户列表      新增
		$("#addUserBtn").click(function(){
			var node = $("#orgTree").tree("getSelected");
			$("#userInfoForm").form("setData", {
				state : "U",
				organizeId : node ? node.id : null
			});
			$("#userInfoWin").dialog("open");
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
					$("#editUserInfoWin").dialog("open");
				}
			}
		});
		
		//用户列表      密码重置
		$("#resetPasswordBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			if(row){
				$.messager.confirm("提示","是否重置所选择用户的登陆密码？",function(flag){
					if(flag){
						WlUserManager.passwordReset(row.userId,function(){
							$("#orgUsersGrid").datagrid("reload");
							$.messager.alert("提示","密码重置成功，默认密码为[123456]","info");
						});
					}
				});
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
							});
						}
					});
				}
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
							});
						}
					});
				}
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
						});
					}
				});	
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
					$("#userInfoWin").dialog("close");
			});
		});
		
		//新增用户Win 取消
		$("#userInfoCancel").click(function(){
			$("#userInfoWin").dialog("close");
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
				$("#editUserInfoWin").dialog("close");
			});
		});
		
		//编辑用户win 取消
		$("#editUserInfoCancel").click(function(){
			$("#editUserInfoWin").dialog("close");
		});
		

		var roleTreeLoading = false;
		var funcTreeLoading = false;
		
		function showUserRoles() {
			CommonQueryManager.query({
				queryType : "WlUserRoleModel",
				queryFields : [{
					fieldName : "userId",
					fieldStringValue : $("#orgUsersGrid").datagrid("getSelected").userId
				}]
			}, function(result) {
				roleTreeLoading = true;
				var rolesMap = {};
				$.each(result.dataList, function(index, userRole) {
					rolesMap[userRole.roleId] = userRole;
				});
				$.each($("#roleTree").tree("getChecked"), function(index, node) {
					if (rolesMap[node.id]) {
						delete rolesMap[node.id];
					} else {
						//不处理父节点
						if (! $("#roleTree").tree("isLeaf", node.target)) {
							return;
						}
						$("#roleTree").tree("uncheck", node.target);
					}
				});
				$.each(rolesMap, function(roleId, v) {
					var node = $("#roleTree").tree("find", roleId);
					if (node) {
						//不处理父节点
						if (! $("#roleTree").tree("isLeaf", node.target)) {
							return;
						}
						$("#roleTree").tree("check", node.target);
					}
				});
				roleTreeLoading = false;
				
				showRoleGrantedFuncs();
			});
		};
		
		function showUserFuncs() {
			CommonQueryManager.query({
				queryType : "WlUserFuncModel",
				queryFields : [{
					fieldName : "userId",
					fieldStringValue : $("#orgUsersGrid").datagrid("getSelected").userId
				}]
			}, function(result) {
				funcTreeLoading = true;
				var funcsMap = {};
				$.each(result.dataList, function(index, userFunc) {
					funcsMap[userFunc.funcId] = 1;
				});
				$.each($("#funcTree").tree("getChecked"), function(index, node) {
					if (funcsMap[node.id]) {
						delete funcsMap[node.id];
					} else {
						//不处理父节点
						if (! $("#funcTree").tree("isLeaf", node.target)) {
							return;
						}
						$("#funcTree").tree("uncheck", node.target);
					}
				});
				$.each(funcsMap, function(funcId, v) {
					var node = $("#funcTree").tree("find", funcId);
					if (node) {
						//不处理父节点
						if (! $("#funcTree").tree("isLeaf", node.target)) {
							return;
						}
						$("#funcTree").tree("check", node.target);
					}
				});
				funcTreeLoading = false;
			});
		};
		
		function showRoleGrantedFuncs() {
			//高亮显示通过role授权的func
			var roleIdsString = "";
			$.each($("#roleTree").tree("getChecked"), function(index, node) {
				roleIdsString += node.id + ","
			});
			if (roleIdsString.length == 0) {
				$("#funcTree").find(".tree-title").css("color", "");
				return;
			}
			CommonQueryManager.query({
				queryType : "WlRoleFuncModel",
				queryFields : [{
					fieldName : "roleId",
					operator : "in",
					fieldStringValue : roleIdsString,
					fieldType : "String[]"
				}]
			}, function(result) {
				$("#funcTree").find(".tree-title").css("color", "");
				$.each(result.dataList, function(index, roleFunc) {
					var node = $("#funcTree").tree("find", roleFunc.funcId);
					if (node) {
						$(node.target).find(".tree-title").css("color", "blue");
					}
				});
			});
		};
		
		//当选中用户后显示该用户的拥有的权限和角色
		$("#orgUsersGrid").datagrid("options").onSelect = function(rowIndex,rowData){
			$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
			
			//显示：禁用、启用状态
			if(rowData.state=="F"){
				$("#disableBtn").hide();//禁用按钮隐藏
				$("#startBtn").show();//显示启用
			}else{
				$("#startBtn").hide();//启用按钮隐藏
				$("#disableBtn").show();//禁用按钮现显示
			}
			
			showUserRoles();
			showUserFuncs();
		};

		//save userRole
		$("#roleTree").tree("options").onCheck = function(node, checked) {
			$.fn.tree.defaults.onCheck.apply(this, [node, checked]);
			if (roleTreeLoading) {
				return;;
			}
			var selectedUser = $("#orgUsersGrid").datagrid("getSelected");
			var roleIds = [];
			$.each($("#roleTree").tree("getChecked"), function(index, node) {
				roleIds.push(node.id);
			});
			$.each($("#roleTree").tree("getHalfChecked"), function(index, node) {
				roleIds.push(node.id);
			});
			WlUserRoleManager.saveRolesForUser(selectedUser.userId, roleIds, function() {
				showRoleGrantedFuncs();
			});
		};

		//save userFunc
		$("#funcTree").tree("options").onCheck = function(node, checked) {
			$.fn.tree.defaults.onCheck.apply(this, [node, checked]);
			if (funcTreeLoading) {
				return;;
			}
			var selectedUser = $("#orgUsersGrid").datagrid("getSelected");
			var funcIds = [];
			$.each($("#funcTree").tree("getChecked"), function(index, node) {
				funcIds.push(node.id);
			});
			$.each($("#funcTree").tree("getHalfChecked"), function(index, node) {
				funcIds.push(node.id);
			});
			WlUserFuncManager.saveFuncsForUser(selectedUser.userId, funcIds);
		};

		//没有选用户不能操作
		$("#roleTree").tree("options").onBeforeCheck = function(node, checked) {
			$.fn.tree.defaults.onBeforeCheck.apply(this, [node, checked]);
			var selectedUser = $("#orgUsersGrid").datagrid("getSelected");
			if (! selectedUser) {
				$.messager.toast("提示","请先选择用户","warning");
				return false;;
			}
		};

		//没有选用户不能操作
		$("#funcTree").tree("options").onBeforeCheck = function(node, checked) {
			$.fn.tree.defaults.onBeforeCheck.apply(this, [node, checked]);
			var selectedUser = $("#orgUsersGrid").datagrid("getSelected");
			if (! selectedUser) {
				$.messager.toast("提示","请先选择用户","warning");
				return false;;
			}
		};
	});
	</script>
	
	<div class="easyui-layout" fit="true">
		<div region="west" title="组织机构" iconCls="icon-tree" style="width:320px;">
			<div class="easyui-layout" fit="true">
			    <div region="north" class="datagrid-toolbar" style="height: 55px;">
					<a id="btnNewOrg" class="easyui-linkbutton" iconCls="icon-add">新增</a>
					<a id="btnNewChildOrg" class="easyui-linkbutton" iconCls="icon-add">新增下级</a>
					<a id="btnEditOrg" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
					<a id="btnDeleteOrg" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
					<!-- 
		            <div style="float:right; margin:2px 2px 0px 0px;">
						<input id="searchOrg" class="easyui-searchbox" prompt='组织名称，负责人' style="width:250px" /> 
		            </div>
		             -->
			    </div>
			    <div region="center" border="false">
				    <!-- 组织树 -->
					<ul id="orgTree" class="easyui-tree" queryType="WlOrganizeModel" orderBy="name" 
							idField="organizeId" textfield="name" parentField="parentOrganizeId"
							queryFields="[{fieldName:'state',fieldStringValue:'U'}]"
							dnd="true"
							title="可拖动节点到其它节点内改变组织归属关系"/>
			    </div>
			</div>
		</div>
		<!-- 用户列表 -->
		<div region="center" border="false">
			<div class="easyui-layout" fit="true" border="false">
				<!-- 用户列表 -->
				<div region="center" title="用户" iconCls="icon-edit">
					<div class="datagrid-toolbar" border="false">
				    	<a id="addUserBtn"  class="easyui-linkbutton" iconCls="icon-add">新增</a>
						<a id="editUserBtn" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
						<a id="resetPasswordBtn" class="easyui-linkbutton" iconCls="icon-tip">密码重置</a>
						<a id="disableBtn" class="easyui-linkbutton" iconCls="icon-no" style="display:none">禁用</a>
						<a id="startBtn" class="easyui-linkbutton" iconCls="icon-ok">启用</a>
						<a id="deleteBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
						
			            <div style="float:right; margin:2px 50px 0px 0px;">
			                <input id="searchUser" class="easyui-searchbox" prompt="登录名、姓名、邮箱" style="width:250px;"/>
			            </div>
		    		</div>
					<table id="orgUsersGrid" class="easyui-datagrid" singleSelect="true"
							queryType="WlUserModel" orderby="name"
							queryFields="[{fieldName:'state',operator:'<>',fieldStringValue:'S'}]"
							i18nRoot="orgUsersGridSYS" fit="true">
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
				<div region = "south" border="false" style="height:250px;">
					<div class="easyui-layout" fit="true">
						<div region="center" title="角色授权" iconCls="icon-tree" >
							<ul id="roleTree" class="easyui-tree" queryType="WlRoleModel" orderBy="name" 
									idField="roleId" textfield="name"
									checkbox="true"
									title="通过角色授权的功能在右侧列表中显示为蓝色"/>
						</div>
						<div region="east" title="功能授权" iconCls="icon-tree" style="width:450px;" >
							<ul id="funcTree" class="easyui-tree" queryType="TenantFunctionsQuery" orderBy="funSeq" 
									idField="funcId" textfield="name" parentField="parentId"
									checkbox="true"
									title="通过角色授权的功能显示为蓝色"/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 组织信息窗口 -->
	<div id="orgInfoDialog" class="easyui-dialog" title="组织信息" iconCls="icon-edit" style="width:700px;"  modal="true" resizable="false" closable="false" closed="true">
	    <form id="sysOrgInfoDialogForm" class="easyui-form" columns="2" i18nRoot="sysOrgInfoDialogFormSYS">
			<input name="name" class="easyui-validatebox" required="true" />
			<input name="manage"  />
			<input name="contact"  />
			<input name="contactTel"  />
			<input name="fax"  />
			<input name="email" colspan="2"/>
			<input name="remarks"  colspan="2" style="height:40px;"/>
		</form>
	    <div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" id="saveOrgInfoBtn">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" id="cancelOrgInfoBtn">取消</a>
		</div>		
	</div>
	
	<!-- 用户信息Window -->
	<div id="userInfoWin" class="easyui-dialog" title="新增用户信息" iconCls="icon-edit" style="width:700px;" closed="true" resizable="false" closable="true" maximizable="false" minimizable="false" collapsible="false" modal="true"> 
		<div id="userInfoForm" class="easyui-form" columns="2" i18nRoot="userInfoFormSYS">
			<input name="loginName"   class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="name"   class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="password"  type="password" class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="reWritePassword" title="确定密码" type="password" required="true"/>
			<input id="combotreeOrg" name="organizeId"  class="easyui-combotree" codeType="org" required="true" />
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
	    <div class="dialog-buttons">
			<a id="userInfoSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="userInfoCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>
	
	
	<!-- 编辑用户信息Window -->
	<div id="editUserInfoWin" class="easyui-dialog" title="编辑用户信息" iconCls="icon-edit" style="width:700px;" closed="true" resizable="false" closable="true" maximizable="false" minimizable="false" collapsible="false" modal="true"> 
		<div id="editUserInfoForm" class="easyui-form" columns="2" i18nRoot="userInfoFormSYS">
			<input name="loginName"   class="easyui-validatebox" required="true"  validType="length[0,20]"/>
			<input name="name"   disabled="disabled"/>
			<input id="combotreeOrg" name="organizeId"  class="easyui-combotree" codeType="org" required="true" />
			<input name="homeTel"/>
			<input name="officeTel"/>
			<input name="mobileTele"/>
			<input name="mobileTele"/>
			<input name="email"/>
			<input name="qq"/>
			<input name="msn"/>
			<input name="addrId" colspan="2" />
			<input name="remarks" colspan="2" />
		 </div>
	    <div class="dialog-buttons">
			<a id="editUserInfoSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="editUserInfoCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>