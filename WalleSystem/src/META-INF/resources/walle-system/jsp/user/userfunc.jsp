<%@page language='java' pageEncoding='utf-8'%>
	<script language="javascript">
	$(function(){
		//折叠树
		$("#collapseBtn").click(function(){
			$("#funcTree").tree("collapseAll");
		});
		
		//展开树
		$("#expandBtn").click(function(){
			$("#funcTree").tree("expandAll");
		});
		
		//保存用户功能树
		$("#saveUserFuncBtn").click(function(){
			var row = $("#orgUsersGrid").datagrid("getSelected");
			var userId="";
			if(row){
				userId = row.userId;
			}else{
				$.messager.alert("提示","请选择赋权的用户","warning");
				return;
			}
			var checkedNodes = $("#funcTree").tree("getChecked");
			var checkedArray = new Array();
			for(var i=0;i<checkedNodes.length;i++){
				checkedArray.push(checkedNodes[i].id);
			}
			WlUserFuncManager.saveFunc(userId,checkedArray,function(){
					$.messager.alert("提示","保存用户功能成功！","info");
					return;
			});
		});
	
		$("#orgUsersGrid").datagrid("load");
		
		//设置用户grid级联查询
		$("#orgUsersGrid").datagrid("options").onSelect = function(rowIndex,rowData){
			$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex,rowData]);
			//清空选中的上次角色功能
			var checkedNodes = $("#funcTree").tree("getChecked");
			for(var i=0;i<checkedNodes.length;i++){
				$("#funcTree").tree("uncheck",checkedNodes[i].target);
			}
			//查找到用户所拥有的功能
			WlUserFuncManager.getUserFunc(rowData.userId,function(data){
				if(data==null){
					return;
				}
				for(var i=0;i<data.length;i++){
					if(data[i]=="1"){continue;}
					var node = $("#funcTree").tree("find",data[i]);
					$("#funcTree").tree("check",node.target);
				};
			});
		};
		
		//查询用户btn
		$("#queryUserBtn").click(function(){
			$("#orgUsersGrid").datagrid("load");
		});
		
		//清空查询用户btn
		$("#resetUserBtn").click(function(){
			$("#queryUserForm").form("clear");
		});
		
	});
	</script>
	<div class="easyui-layout" fit="true">
		<div region="west" class="easyui-layout" style="width:650px;">
			<div region="north"  style="height:250px;">
				<div class="datagrid-toolbar">
					<a id="queryUserBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
					<a id="resetUserBtn" class="easyui-linkbutton" iconCls="icon-reload">清空</a>
				</div>
				<div id="queryUserForm" class="easyui-form" columns="2" i18nRoot="queryUserFormSYS">
					<input name="state" class="easyui-combobox" codeType="user_status"/>
					<input name="name" operator="ilikeAnyWhere"/>
					<input name="loginName" operator="ilikeAnyWhere"/>
					<input name="organizeId" class="easyui-combotree" codeType="org">
				</div>
			</div>
			<div region="center" title="用户列表" iconCls="icon-reload"  border="false">
				<table id="orgUsersGrid" class="easyui-datagrid" singleSelect="true" fit="true"
						paramForm="queryUserForm" queryType="UserOrganizeQuery" i18nRoot="orgUsersGridSYS" border='false'>
					<thead>
						<tr>
							<th field="state" class="easyui-combobox" codeType="user_status"></th>
							<th field="loginName"></th>
							<th field="name"></th>
							<th field="email"></th>
							<th field="officeTel"></th>
							<th field="mobileTele"></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<div region="center"  >
				 <div class="datagrid-toolbar">
			    	 <span class="panel-header panel-title"  style="float: left; border-style: none; width: 70px;">系统功能树</span>
			    	 <a id="collapseBtn" class="easyui-linkbutton" iconCls="icon_left" >折叠</a>
					 <a id="expandBtn" class="easyui-linkbutton" iconCls="icon_right">展开</a>
					 <a id="saveUserFuncBtn" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		   		 </div>
				<ul id="funcTree" class="easyui-tree"  queryType="WlFunctionModel"  orderBy="funSeq"
						idField="funcId" textField="name" animate="true" parentField="parentId"  seqField="funSeq" checkbox="true"/>
		</div>
	</div>