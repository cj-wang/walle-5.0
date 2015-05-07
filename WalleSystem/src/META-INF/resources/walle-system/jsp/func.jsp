<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){

		/*
		//查询
		$("#searchFunc").searchbox("options").searcher= function(value,name){
			$("#funcTree").tree("commonQuery",{
				queryFields:[{
					fieldName:"[name,viewname]",
					fieldStringValue:value,
					operator:"ilikeAnywhere"
				}]
			});
		};
		*/
		//新增
		$("#btnNewFunc").click(function(){
			var node = $("#funcTree").tree("getSelected");
			$("#addFuncInfoForm").form("setData", {
				parentId : node ? node.attributes.data.parentId : "0",
				funSeq : 99
			});
			$("#addFuncInfoDlg").dialog("open");
		});
		
		//新增子节点
		$("#btnNewChildFunc").click(function(){
			var node = $("#funcTree").tree("getSelected");
			if (node) {
				$("#addFuncInfoForm").form("setData", {
					parentId : node.id,
					funSeq : 99
				});
				$("#addFuncInfoDlg").dialog("open");
			}
		});
		
		//编辑
		$("#btnEditFunc").click(function(){
			var node = $("#funcTree").tree("getSelected");
			if (node) {
				$("#addFuncInfoForm").form("setData",node.attributes.data);
				$("#addFuncInfoDlg").dialog("open");
			}
		});
		
		//删除
		$("#btnDeleteFunc").click(function(){
			var node = $("#funcTree").tree("getSelected");
			if (node) {
				$.messager.confirm("提示","是否删除所选的功能？",function(flag){
					if(flag){
						WlFunctionManager.delByPk(node.id,function(data){
							$("#funcTree").tree("reload");
						});
					}
				});
			}
		});

		//dnd后保存
		$("#funcTree").tree("options").onDrop = function(target, source, point) {
			$.fn.tree.defaults.onDrop.apply(this, [target, source, point]);
			var funcs = $("#funcTree").tree("getChanges");
			if (funcs.length > 0) {
				CommonSaveManager.saveTreeData(funcs, "funcId", "parentId", function() {
					$("#funcTree").tree("reload");
				});
			}
		};
		
		//新增功能        保存
		$("#addFuncInfoSure").click(function(){
			if(!$("#addFuncInfoForm").form("validate")){
				$.messager.alert("提示","数据校验失败！","warning");
				return;
			}else{
				var funcInfo = $("#addFuncInfoForm").form("getData");
				WlFunctionManager.saveModel(funcInfo ,function(data){
					$("#funcTree").tree("reload");
					$("#addFuncInfoDlg").dialog("close");
				});
			}
		});
		
		//新增功能     取消
		$("#addFuncInfoCancel").click(function(){
			$("#addFuncInfoDlg").dialog("close");
		});
		
		
		var roleTreeLoading = false;
		var userTreeLoading = false;
		
		function showFuncRoles() {
			CommonQueryManager.query({
				queryType : "WlRoleFuncModel",
				queryFields : [{
					fieldName : "funcId",
					fieldStringValue : $("#funcTree").tree("getSelected").id
				}]
			}, function(result) {
				roleTreeLoading = true;
				var rolesMap = {};
				$.each(result.dataList, function(index, funcRole) {
					rolesMap[funcRole.roleId] = funcRole;
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
			});
		};
		
		function showFuncUsers() {
			$("#userTree").tree("commonQuery", {
				queryFields : [{
					fieldName : "funcId",
					fieldStringValue : $("#funcTree").tree("getSelected").id
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
		
		//当选中功能后显示该功能的角色和用户
		$("#funcTree").tree("options").onSelect = function(node){
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			showFuncRoles();
			showFuncUsers();
		};

		//save funcRole
		$("#roleTree").tree("options").onCheck = function(node, checked) {
			$.fn.tree.defaults.onCheck.apply(this, [node, checked]);
			if (roleTreeLoading) {
				return;;
			}
			var funcNode = $("#funcTree").tree("getSelected");
			var roleIds = [];
			$.each($("#roleTree").tree("getChecked"), function(index, node) {
				roleIds.push(node.id);
			});
			$.each($("#roleTree").tree("getHalfChecked"), function(index, node) {
				roleIds.push(node.id);
			});
			WlRoleFuncManager.saveRolesForFunc(funcNode.id, roleIds);
		};

		//save funcUser
		$("#userTree").tree("options").onCheck = function(node, checked) {
			$.fn.tree.defaults.onCheck.apply(this, [node, checked]);
			if (userTreeLoading) {
				return;;
			}
			var funcNode = $("#funcTree").tree("getSelected");
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
			WlUserFuncManager.saveUsersForFunc(funcNode.id, checkedUserIds, uncheckedUserIds);
		};

		//没有选功能不能操作
		$("#roleTree").tree("options").onBeforeCheck = function(node, checked) {
			$.fn.tree.defaults.onBeforeCheck.apply(this, [node, checked]);
			var funcNode = $("#funcTree").tree("getSelected");
			if (! funcNode) {
				$.messager.toast("提示","请先选择功能","warning");
				return false;;
			}
		};

		//没有选功能不能操作
		$("#userTree").tree("options").onBeforeCheck = function(node, checked) {
			$.fn.tree.defaults.onBeforeCheck.apply(this, [node, checked]);
			var funcNode = $("#funcTree").tree("getSelected");
			if (! funcNode) {
				$.messager.toast("提示","请先选择功能","warning");
				return false;;
			}
		};
	});
	</script>
	
	<div class="easyui-layout" fit="true">
		<div region="west" title="功能" iconCls="icon-tree" style="width:320px;">
			<div class="easyui-layout" fit="true">
			    <div region="north" class="datagrid-toolbar" style="height:55px;">
					<a id="btnNewFunc" class="easyui-linkbutton" iconCls="icon-add">新增</a>
					<a id="btnNewChildFunc" class="easyui-linkbutton" iconCls="icon-add">新增下级</a>
					<a id="btnEditFunc" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
					<a id="btnDeleteFunc" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
					<!-- 
		            <div style="float:right; margin:2px 2px 0px 0px;">
						<input id="searchFunc" class="easyui-searchbox" prompt='功能名称，关联页面' style="width:250px" /> 
		            </div>
		             -->
			    </div>
			    <div region="center" border="false">
				    <!-- 功能树 -->
					<ul id="funcTree" class="easyui-tree" queryType="WlFunctionModel" orderBy="funSeq" 
							idField="funcId" textfield="name" parentField="parentId" seqField="funSeq"
							dnd="true"
							title="可拖动节点到其它节点内改变功能层级关系"/>
			    </div>
			</div>
		</div>
		<div region="center" title="分配角色" iconCls="icon-tree" >
			<ul id="roleTree" class="easyui-tree" queryType="WlRoleModel" orderBy="name" 
					idField="roleId" textfield="name" 
					checkbox="true"/>
		</div>
		<div region="east" title="分配用户" iconCls="icon-tree" style="width:450px;">
			<ul id="userTree" class="easyui-tree" queryType="OrgUserAsyncTreeCheckedByFuncQuery"
					idField="id" textfield="name" parentField="parentId"
					checkbox="true" checkedField="checked" stateField="nodeState"/>
		</div>
	</div>
	
	<!--新增/编辑     系统功能信息 -->
	<div id="addFuncInfoDlg" class="easyui-dialog" title="功能信息" iconCls="icon-reload" style="width:700px;" modal="true" resizable="true" closeable="true" closed="true">
		<form id="addFuncInfoForm" class="easyui-form" columns="2" i18nRoot="funcInfoFormSYS"  border="false">
			<input name="funcCode" class="easyui-validatebox" required="true" validType="length[0,20]" />
			<input name="name" class="easyui-validatebox" required="true" validType="lenght[0,30]" />
			<input name="funcLevel" />
			<input name="funSeq" />
			<input name="viewname" colspan="2"/>
			<input name="funcImg" colspan="2"/>
			<input name="funcType" class="easyui-combobox" required="true" codeType="WP_PAGETYPE"/>
			<input name="sys" />
			<input name="state"/>
		</form>
		<div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" id="addFuncInfoSure">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" id="addFuncInfoCancel">取消</a>
		</div>	
	</div>