<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include.jsp" %>

<script type="text/javascript">
	
	//查询
	function query() {
		$("#gridResult").datagrid("commonQuery", {
			queryType : "LogisticsOrderModel",
			paramForm : "formQuery"
		});
	};
	
	//重置
	function clearQueryForm() {
		$("#formQuery").form("clear");
	};
	
	//新增
	function appendRow() {
		parent.addTab("订单录入", "jsp/order/orderEdit.jsp", "icon icon-nav", true);
		if (parent.getTabIframe("订单录入").reset) {
			parent.getTabIframe("订单录入").reset();
		}
	};
	
	//编辑
	function editRow() {
		var selected = $("#gridResult").datagrid("endEdit").datagrid("getSelected");
		if (selected) {
			parent.addTab("订单录入", "jsp/order/orderEdit.jsp", "icon icon-nav", true);
			if (parent.getTabIframe("订单录入").loadByOrderNo) {
				parent.getTabIframe("订单录入").loadByOrderNo(selected.logisticsOrderNo);
			} else {
				parent.getTabIframe("订单录入").orderNo = selected.logisticsOrderNo;
			}
		}
	};

	//删除
	function deleteSelectedRows() {
		var selections = $("#gridResult").datagrid("getSelections");
		if (selections.length > 0) {
			$.messager.confirm("提示", "确认删除？", function(b) {
				if (b) {
					$("#gridResult").datagrid("deleteSelectedRows");
					var pks = [];
					$.each(selections, function(index, selected) {
						pks.push(selected.logisticsOrderId)
					});
					LogisticsOrderManager.removeAllByPk(pks, function(result) {
						$.messager.alert("提示", "删除成功", "info");
					});
				}
			})
		};
	};
	
	//取消
	function reload() {
		$("#gridResult").datagrid("reload");
	};

	$(function() {
		$("#gridResult").datagrid("options").onDblClickRow = function(rowIndex, rowData) {
			$.fn.datagrid.defaults.onDblClickRow.apply(this, [rowIndex, rowData]);
			editRow();
		};
	});
	
</script>
</head>

<body class="easyui-layout">
	
	<div region="north" title="查询条件" iconCls="icon-search">
		<div class="datagrid-toolbar">
			<a class="easyui-linkbutton" iconCls="icon-search" onclick="query()">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="clearQueryForm()">重置</a>
		</div>
		<form id="formQuery" class="easyui-form" columns="4" i18nRoot="LogisticsOrder">
			<input name="logisticsOrderNo" operator="ilikeAnywhere"/>
			<input name="soNo" operator="ilikeAnywhere"/>
			<input name="pickLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
			<input name="backLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
			<input name="consignee" class="easyui-combogrid" codetype="CUSTOMER"/>
			<input name="logisticsOrderType" class="easyui-combobox" codetype="SERVICE_ITEM" multiple="true" operator="in"/>
			<div colspan="2">
				<input name="applicationDate" class="easyui-datebox" operator="dateBegin" style="width:85px;"/> -
				<input name="applicationDate" class="easyui-datebox" operator="dateEnd" style="width:85px;"/>
			</div>
		</form>
	</div>
	
	<div region="center" title="订单信息" iconCls="icon-edit">
		<div class="datagrid-toolbar">
			<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow()">新增</a>
			<a class="easyui-linkbutton" iconCls="icon-edit" onclick="editRow()">编辑</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows()">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()">刷新</a>
		</div>
		<table id="gridResult" class="easyui-datagrid" i18nRoot="LogisticsOrder" fit="true">
			<thead>
				<tr>
					<th field="logisticsOrderNo"/>
					<th field="soNo"/>
					<th field="pickLocation" codetype="DATA_SOURCE"/>
					<th field="backLocation" codetype="DATA_SOURCE"/>
					<th field="consignee" codetype="CUSTOMER"/>
					<th field="consigneeName"/>
					<th field="logisticsOrderType" codetype="SERVICE_ITEM"/>
					<th field="applicationDate"/>
					<th field="activity"/>
				</tr>
			</thead>
		</table>
	</div>
		
</body>
</html>