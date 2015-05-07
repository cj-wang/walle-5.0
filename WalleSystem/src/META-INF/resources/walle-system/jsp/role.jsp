<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){

		/*
		//查询
		$("#searchRole").searchbox("options").searcher= function(value,name){
			$("#roleTree").tree("commonQuery",{
				queryFields:[{
					fieldName:"[name,code]",
					fieldStringValue:value,
					operator:"ilikeAnywhere"
				}]
			});
		};
		*/
		//新增
		$("#btnNewRole").click(function(){
			$("#addRoleInfoForm").form("clear");
			$("#addRoleInfoDlg").dialog("open");
		});
		
		//编辑
		$("#btnEditRole").click(function(){
			var node = $("#roleTree").tree("getSelected");
			if (node) {
				$("#addRoleInfoForm").form("setData",node.attributes.data);
				$("#addRoleInfoDlg").dialog("open");
			}
		});
		
		//删除
		$("#btnDeleteRole").click(function(){
			var node = $("#roleTree").tree("getSelected");
			if (node) {
				$.messager.confirm("提示","是否删除所选的角色？",function(flag){
					if(flag){
						WlRoleManager.delByPk(node.id,function(data){
							$("#roleTree").tree("reload");
						});
					}
				});
			}
		});

		//新增角色        保存
		$("#addRoleInfoSure").click(function(){
			if(!$("#addRoleInfoForm").form("validate")){
				$.messager.alert("提示","数据校验失败！","warning");
				return;
			}else{
				var roleInfo = $("#addRoleInfoForm").form("getData");
				WlRoleManager.saveModel(roleInfo ,function(data){
					$("#roleTree").tree("reload");
					$("#addRoleInfoDlg").dialog("close");
				});
			}
		});
		
		//新增角色     取消
		$("#addRoleInfoCancel").click(function(){
			$("#addRoleInfoDlg").dialog("close");
		});
		
		
		var funcTreeLoading = false;
		var userTreeLoading = false;
		
		function showRoleFuncs() {
			CommonQueryManager.query({
				queryType : "WlRoleFuncModel",
				queryFields : [{
					fieldName : "roleId",
					fieldStringValue : $("#roleTree").tree("getSelected").id
				}]
			}, function(result) {
				funcTreeLoading = true;
				var funcsMap = {};
				$.each(result.dataList, function(index, roleFunc) {
					funcsMap[roleFunc.funcId] = roleFunc;
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
		
		function showRoleUsers() {
			$("#userTree").tree("commonQuery", {
				queryFields : [{
					fieldName : "roleId",
					fieldStringValue : $("#roleTree").tree("getSelected").id
				}]
			});
		};
		
		$("#userTree").tree("options").onBeforeLoad = function(node, param) {
			$.fn.tree.defaults.onBeforeLoad.apply(this, [node, param]);
			userTreeLoading = true;
		};
		
		$("#userTree").tree("options").onLoadSuccess = function(node, data) {
			$.fn.tree.defaults.onLoadSuccess.apply(this, [node, data]);
			userTreeLoading = false;
		};
		
		//当选中角色后显示该角色的功能和用户
		$("#roleTree").tree("options").onSelect = function(node){
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			showRoleFuncs();
			showRoleUsers();
		};

		//save roleFunc
		$("#funcTree").tree("options").onCheck = function(node, checked) {
			$.fn.tree.defaults.onCheck.apply(this, [node, checked]);
			if (funcTreeLoading) {
				return;;
			}
			var roleNode = $("#roleTree").tree("getSelected");
			var funcIds = [];
			$.each($("#funcTree").tree("getChecked"), function(index, node) {
				funcIds.push(node.id);
			});
			$.each($("#funcTree").tree("getHalfChecked"), function(index, node) {
				funcIds.push(node.id);
			});
			WlRoleFuncManager.saveFuncsForRole(roleNode.id, funcIds);
		};

		//save userUser
		$("#userTree").tree("options").onCheck = function(node, checked) {
			$.fn.tree.defaults.onCheck.apply(this, [node, checked]);
			if (userTreeLoading) {
				return;;
			}
			var roleNode = $("#roleTree").tree("getSelected");
			var checkedUserIds = [];
			var uncheckedUserIds = [];
			$.each($("#userTree").tree("getChildren"), function(index, node) {
				if ($("#userTree").tree("isLeaf", node.target)) {
					uncheckedUserIds.push(node.id);
				}
			});
			$.each($("#userTree").tree("getChecked"), function(index, node) {
				if ($("#userTree").tree("isLeaf", node.target)) {
					checkedUserIds.push(node.id);
					uncheckedUserIds.remove(node.id);
				}
			});
			WlUserRoleManager.saveUsersForRole(roleNode.id, checkedUserIds, uncheckedUserIds);
		};

		//没有选角色不能操作
		$("#funcTree").tree("options").onBeforeCheck = function(node, checked) {
			$.fn.tree.defaults.onBeforeCheck.apply(this, [node, checked]);
			var roleNode = $("#roleTree").tree("getSelected");
			if (! roleNode) {
				$.messager.toast("提示","请先选择角色","warning");
				return false;;
			}
		};

		//没有选角色不能操作
		$("#userTree").tree("options").onBeforeCheck = function(node, checked) {
			$.fn.tree.defaults.onBeforeCheck.apply(this, [node, checked]);
			var roleNode = $("#roleTree").tree("getSelected");
			if (! roleNode) {
				$.messager.toast("提示","请先选择角色","warning");
				return false;;
			}
		};
	});
	</script>
	
	<div class="easyui-layout" fit="true">
		<div region="west" title="角色" iconCls="icon-tree" style="width:320px;">
			<div class="easyui-layout" fit="true">
			    <div region="north" class="datagrid-toolbar" style="height:55px;">
					<a id="btnNewRole" class="easyui-linkbutton" iconCls="icon-add">新增</a>
					<a id="btnEditRole" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
					<a id="btnDeleteRole" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
					<!-- 
		            <div style="float:right; margin:2px 2px 0px 0px;">
						<input id="searchRole" class="easyui-searchbox" prompt='角色名称，角色代码' style="width:250px" /> 
		            </div>
		             -->
			    </div>
			    <div region="center" border="false">
				    <!-- 角色树 -->
					<ul id="roleTree" class="easyui-tree" queryType="WlRoleModel" orderBy="name" 
							idField="roleId" textfield="name"
							dnd="false"/>
			    </div>
			</div>
		</div>
		<div region="center" title="角色功能" iconCls="icon-tree" >
			<ul id="funcTree" class="easyui-tree" queryType="TenantFunctionsQuery" orderBy="funSeq" 
					idField="funcId" textfield="name" parentField="parentId"
					checkbox="true"/>
		</div>
		<div region="east" title="分配用户" iconCls="icon-tree" style="width:450px;">
			<ul id="userTree" class="easyui-tree" queryType="OrgUserAsyncTreeCheckedByRoleQuery"
					idField="id" textfield="name" parentField="parentId"
					checkbox="true" checkedField="checked" stateField="nodeState"/>
		</div>
	</div>
	
	<!-- 角色信息窗口 -->
	<div id="addRoleInfoDlg" class="easyui-dialog" title="角色信息" iconCls="icon-edit" style="width:400px;" closed="true" modal="true">
		<form id="addRoleInfoForm" class="easyui-form" columns="1" i18nRoot="roleInfoFromSYS">
		 	<input name="name"   class="easyui-validatebox" required="true"  validType="length[0,30]">
			<input name="code"   class="easyui-validatebox" required="true"  validType="length[0,20]">
		 	<textarea rows="2" cols="2" name="remarks" style="hieght:50px;width:160px; resize:none " ></textarea>
		</form>
		<div class="dialog-buttons">
			<a id="addRoleInfoSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="addRoleInfoCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
	</div>
