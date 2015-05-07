<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {
		
		//查询
		$("#btnQuery").click(function() {
			$("#gridResult").datagrid("load");
		});
		
		//重置
		$("#btnReset").click(function() {
			$("#formQuery").form("clear");
		});
		
		//新增
		$("#btnAppend").click(function() {
			$("#gridResult").datagrid("appendRow", {});
			$("#btnEdit").trigger("click");
		});
		
		//插入
		$("#btnInsert").click(function() {
			$("#gridResult").datagrid("insertRow", {
				index : $("#gridResult").datagrid("getSelectedIndex"),
				row : {}
			});
			$("#btnEdit").trigger("click");
		});
		
		//编辑
		$("#btnEdit").click(function() {
			var row = $("#gridResult").datagrid("endEdit").datagrid("getSelected");
			if (row) {
				$("#dialogEdit").dialog("open");
				$("#formEdit").form("setData", row);
			}
		});
		
		//删除
		$("#btnDelete").click(function() {
			$("#gridResult").datagrid("deleteSelectedRows");
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
			SysUserManager.saveAll(rows, function(result) {
				$("#gridResult").datagrid("refreshSavedData", result);
				$.messager.alert("提示", "保存成功", "info");
			});
		});
		
		//取消
		$("#btnReload").click(function() {
			$("#gridResult").datagrid("reload");
		});
		
		//编辑窗口确定
		$("#btnEditFormOk").click(function() {
			if (! $("#formEdit").form("validate")) {
				$.messager.alert("提示", "数据验证错误", "warning");
				return;
			}
			$("#dialogEdit").dialog("close");
			$("#gridResult").datagrid("updateRow", {
				index : $("#gridResult").datagrid("getSelectedIndex"),
				row : $("#formEdit").form("getData")
			});
		});
		
		//编辑窗口取消
		$("#btnEditFormCancel").click(function() {
			$("#dialogEdit").dialog("close");
		});
		
		
	});
	
</script>

<div class="easyui-layout" fit="true">
	
	<div region="west" title="组织机构" iconCls="icon-search" style="width:250px;">
		<ul id="treeOffice" class="easyui-tree" queryType="SysOfficeModel" orderby="officeName"
				idField="officeUuid" textField="officeName" parentField="preOfficeUuid"/>
	</div>
	
	<div region="center">
		<div class="easyui-layout" fit="true">
		
			<div region="north" title="查询条件" iconCls="icon-search">
				<div class="datagrid-toolbar">
					<a id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" key="F">查询</a>
					<a id="btnReset" class="easyui-linkbutton" iconCls="icon-reload" key="R">重置</a>
				</div>
				<form id="formQuery" class="easyui-form" columns="3" i18nRoot="SysUser">
					<input name="userCode" operator="ilikeAnywhere"/>
					<input name="userName" operator="ilikeAnywhere"/>
					<input name="email" operator="ilikeAnywhere"/>
					<input name="active" class="easyui-combobox" codetype="YES_NO" multiple="true" operator="in"/>
					<input name="officeId" class="easyui-combotree" codetype="ALL_ORGS" multiple="true" operator="in" />
				</form>
			</div>
			
			<div region="center" title="查询结果" iconCls="icon-edit">
				<div class="datagrid-toolbar">
					<a id="btnAppend" class="easyui-linkbutton" iconCls="icon-add" key="A">新增</a>
					<a id="btnInsert" class="easyui-linkbutton" iconCls="icon-redo" key="I">插入</a>
					<a id="btnEdit" class="easyui-linkbutton" iconCls="icon-edit" key="E">编辑</a>
					<a id="btnDelete" class="easyui-linkbutton" iconCls="icon-remove" key="D">删除</a>
					<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save" key="S">保存</a>
					<a id="btnReload" class="easyui-linkbutton" iconCls="icon-reload" key="U">取消</a>
				</div>
				<table id="gridResult" class="easyui-datagrid" fit="true"
						queryType="SysUserModel" paramForm="formQuery" i18nRoot="SysUser">
					<thead>
						<tr>
							<th field="userCode" editor="{type:'validatebox', options:{required:true, validType:'length[0,20]'}}"/>
							<th field="userName" editor="{type:'validatebox', options:{required:true}}"/>
							<th field="email" editor="text"/>
							<th field="officeId" editor="combotree" codetype="ALL_ORGS"/>
							<th field="active" editor="{type:'combobox', options:{required:true}}" codetype="YES_NO"/>
							<th field="contactAddress" editor="text" width="200"/>
						</tr>
					</thead>
				</table>
			</div>
		
		</div>
	</div>
	
	<!-- 弹出窗口编辑 -->
	<div id="dialogEdit" class="easyui-dialog" title="编辑" iconCls="icon-edit"
			style="width:500px;padding:10px" closed="true" modal="true">
		<form id="formEdit" class="easyui-form" columns="2" i18nRoot="SysUser">
			<input name="userCode" class="easyui-validatebox" required="true" validType="length[0,20]"/>
			<input name="userName" class="easyui-validatebox" required="true"/>
			<input name="email"/>
			<input name="officeId" class="easyui-combotree" codetype="ALL_ORGS"/>
			<input name="active" class="easyui-combobox" codetype="YES_NO" required="true"/>
			<div></div>
			<textarea name="contactAddress" rowspan="2" colspan="2"></textarea>
		</form>
		<div class="dialog-buttons">
			<a id="btnEditFormOk" class="easyui-linkbutton" iconCls="icon-ok" key="S">确定</a>
			<a id="btnEditFormCancel" class="easyui-linkbutton" iconCls="icon-cancel" key="U">取消</a>
		</div>
	</div>
</div>