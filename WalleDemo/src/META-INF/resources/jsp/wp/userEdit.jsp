<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {

	    //查询
	    $("#search").searchbox("options").searcher = function(value, name) {
	        $("#gridResult").datagrid("commonQuery", {
	            queryFields : [{
	                fieldName : "[username,fullname,email,tel,mobile,fax,im]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            }]
	        });
	    };
		
		//新增
		$("#btnAppend").click(function() {
			$("#gridResult").datagrid("appendRow", {});
		});
		
		//删除
		$("#btnDelete").click(function() {
			$("#gridResult").datagrid("deleteSelectedRows");
		});
		
		//重置密码
		$("#btnResetPassword").click(function() {
			var ids = [];
			$.each($("#gridResult").datagrid("getSelections"), function(index, row) {
				ids.push(row.uuid);
			});
			WpUserManager.resetPassword(ids, function() {
				$.messager.alert("提示", "重置密码成功", "info");
				$("#gridResult").datagrid("reload");
			});
		});
		
		//保存
		$("#btnSave").click(function() {
			if (! $("#gridResult").datagrid("validate")) {
				$.messager.alert("提示", "数据验证错误", "warning");
				return;
			}
			var rows = $("#gridResult").datagrid("getChanges");
			if (rows.length == 0) {
				$.messager.alert("提示", "未修改数据", "warning");
				return;
			}
			WpUserManager.saveAll(rows, function(result) {
				$("#gridResult").datagrid("refreshSavedData", result);
				$.messager.alert("提示", "保存成功。<br/>新增用户默认密码与登录名相同", "info");
			});
		});
		
		//取消
		$("#btnReload").click(function() {
			$("#gridResult").datagrid("reload");
		});
		
		
		$("#gridResult").datagrid("load");
		
	});
	
</script>

<div class="easyui-layout" fit="true">
	
<!-- 	<div region="west" title="组织机构" iconCls="icon-search" style="width:250px;"> -->
<!-- 		<ul id="treeOffice" class="easyui-tree" queryType="WpOrgModel" -->
<!-- 				idField="uuid" textField="orgName" parentField="parentOrgId"/> -->
<!-- 	</div> -->
	
	<div region="center">
		<div class="easyui-layout" fit="true">
		
			<div region="center" title="查询结果" iconCls="icon-edit">
				<div class="datagrid-toolbar">
					<a id="btnAppend" class="easyui-linkbutton" iconCls="icon-add">新增</a>
					<a id="btnDelete" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
					<a id="btnResetPassword" class="easyui-linkbutton" iconCls="icon-edit">重置密码</a>
					<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save">保存</a>
					<a id="btnReload" class="easyui-linkbutton" iconCls="icon-reload">取消</a>
					
		            <div style="float:right; margin:2px 50px 0px 0px;">
		                <input id="search" class="easyui-searchbox" prompt="登录名、全名、邮箱、电话" style="width:250px;"/>
		            </div>
				</div>
				<table id="gridResult" class="easyui-datagrid" fit="true"
						queryType="WpUserModel" paramForm="formQuery" orderby='username'>
					<thead>
						<tr>
							<th field="username" title="登录名" editor="{type:'validatebox', options:{required:true}}"/>
							<th field="fullname" title="全名" editor="{type:'validatebox', options:{required:true}}"/>
							<th field="orgId" title="组织" editor="text"/>
							<th field="email" title="EMAIL" editor="{type:'validatebox', options:{required:true}}"/>
							<th field="tel" title="电话" editor="text"/>
							<th field="mobile" title="手机" editor="text"/>
							<th field="fax" title="传真" editor="text"/>
							<th field="im" title="即时通讯" editor="text"/>
							<th field="locked" title="锁定" editor="combobox" codetype="WP_YESNO"/>
						</tr>
					</thead>
				</table>
			</div>
		
		</div>
	</div>
	
</div>