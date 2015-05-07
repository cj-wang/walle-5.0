<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include.jsp" %>

<script type="text/javascript">
	
	//查询
	function query() {
		$("#gridResult").datagrid("load");
	};
	
	//重置
	function clearQueryForm() {
		$("#formQuery").form("clear");
	};
	
	//新增
	function appendRow() {
		$("#gridResult").datagrid("appendRow", {});
		editRow();
	};
	
	//插入
	function insertRow() {
		$("#gridResult").datagrid("insertRow", {
			index : $("#gridResult").datagrid("getSelectedIndex"),
			row : {}
		});
		editRow();
	};
	
	//编辑
	function editRow() {
		var row = $("#gridResult").datagrid("endEdit").datagrid("getSelected");
		if (row) {
			$("#dialogEdit").dialog("open");
			$("#formEdit").form("setData", row);
		}
	};
	
	//删除
	function deleteSelectedRows() {
		$("#gridResult").datagrid("deleteSelectedRows");
	};
	
	//保存
	function save() {
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
	};
	
	//取消
	function reload() {
		$("#gridResult").datagrid("reload");
	};
	
	//编辑窗口确定
	function editDialogOk() {
		if (! $("#formEdit").form("validate")) {
			$.messager.alert("提示", "数据验证错误", "warning");
			return;
		}
		$("#dialogEdit").dialog("close");
		$("#gridResult").datagrid("updateRow", {
			index : $("#gridResult").datagrid("getSelectedIndex"),
			row : $("#formEdit").form("getData")
		});
	};
	
	//编辑窗口取消
	function editDialogCancel() {
		$("#dialogEdit").dialog("close");
	};
	
	$(function() {

	});
	
</script>
</head>

<body class="easyui-layout">
	
	<div region="west" title="组织机构" iconCls="icon-search" style="width:250px;">
		<ul id="treeOffice" class="easyui-tree" queryType="SysOfficeModel"
				idField="officeUuid" textField="officeName" parentField="preOfficeUuid"/>
	</div>
	
	<div region="center">
		<div class="easyui-layout" fit="true">
		
			<div region="north" title="查询条件" iconCls="icon-search">
				<div class="datagrid-toolbar">
					<a class="easyui-linkbutton" iconCls="icon-search" onclick="query()" key="F">查询</a>
					<a class="easyui-linkbutton" iconCls="icon-reload" onclick="clearQueryForm()" key="R">重置</a>
				</div>
				<form id="formQuery" class="easyui-form" columns="3" i18nRoot="SysUser">
					<input name="userCode" operator="ilikeAnywhere"/>
					<input name="userName" operator="ilikeAnywhere"/>
					<input name="email" operator="ilikeAnywhere"/>
					<input name="active" class="easyui-combobox" codetype="YES_NO" multiple="true" operator="in"/>
					<input name="officeId" class="easyui-combogrid" codetype="ALL_ORGS"/>
				</form>
			</div>
			
			<div region="center" title="查询结果" iconCls="icon-edit">
				<div class="datagrid-toolbar">
					<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow()" key="A">新增</a>
					<a class="easyui-linkbutton" iconCls="icon-redo" onclick="insertRow()" key="I">插入</a>
					<a class="easyui-linkbutton" iconCls="icon-edit" onclick="editRow()" key="E">编辑</a>
					<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows()" key="D">删除</a>
					<a class="easyui-linkbutton" iconCls="icon-save" onclick="save()" key="S">保存</a>
					<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()" key="U">取消</a>
				</div>
				<table id="gridResult" class="easyui-datagrid" fit="true"
						queryType="SysUserModel" paramForm="formQuery" i18nRoot="SysUser">
					<thead>
						<tr>
							<th field="userCode" editor="{type:'validatebox', options:{required:true, validType:'length[0,20]'}}"/>
							<th field="userName" editor="{type:'validatebox', options:{required:true}}"/>
							<th field="email" editor="text"/>
							<th field="officeId" editor="combogrid" codetype="ALL_ORGS"/>
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
			<input name="officeId" class="easyui-combogrid" codetype="ALL_ORGS"/>
			<input name="active" class="easyui-combobox" codetype="YES_NO" required="true"/>
			<div></div>
			<textarea name="contactAddress" rowspan="2" colspan="2"></textarea>
		</form>
		<div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" onclick="editDialogOk()" key="S">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="editDialogCancel()" key="U">取消</a>
		</div>
	</div>
	
</body>
</html>