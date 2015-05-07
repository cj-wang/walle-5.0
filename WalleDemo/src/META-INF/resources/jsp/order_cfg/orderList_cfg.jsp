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
		$("#btnNew").click(function() {
			parent.addTab("订单录入-cfg", "jsp/order_cfg/orderEdit_cfg.jsp", "icon icon-nav", true, true);
			var editTab = parent.getTab("订单录入-cfg");
			editTab.find("#logisticsOrderNo").val(null);
			if (editTab.find("#btnReload")[0]) {
				editTab.find("#btnReload")[0].click();
			}
		});
		
		//编辑
		$("#btnEdit").click(function() {
			var selected = $("#gridResult").datagrid("endEdit").datagrid("getSelected");
			if (selected) {
				parent.addTab("订单录入-cfg", "jsp/order_cfg/orderEdit_cfg.jsp?orderNo=" + selected.logisticsOrderNo, "icon icon-nav", true, true);
				var editTab = parent.getTab("订单录入-cfg");
				editTab.find("#logisticsOrderNo").val(selected.logisticsOrderNo);
				if (editTab.find("#btnReload")[0]) {
					editTab.find("#btnReload")[0].click();
				}
			}
		});

		//删除
		$("#btnDelete").click(function() {
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
		});
		
		//取消
		$("#btnReload").click(function() {
			$("#gridResult").datagrid("reload");
		});

		//grid双击
		$("#gridResult").datagrid("options").onDblClickRow = function(rowIndex, rowData) {
			$.fn.datagrid.defaults.onDblClickRow.apply(this, [rowIndex, rowData]);
			$("#btnEdit").click();
		};
		
	});
</script>

<div class="easyui-layout" fit="true">
	
	<div region="north" title="查询条件" iconCls="icon-search">
		<div class="datagrid-toolbar">
			<a id="btnQuery" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a id="btnReset" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
		</div>
		<form id="formQuery" class="easyui-form wp-config" columns="4" i18nRoot="LogisticsOrder">
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
			<a id="btnNew" class="easyui-linkbutton" iconCls="icon-add">新增</a>
			<a id="btnEdit" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
			<a id="btnDelete" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			<a id="btnReload" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
		</div>
		<table id="gridResult" class="easyui-datagrid wp-config" fit="true"
				queryType="LogisticsOrderModel" paramForm="formQuery" i18nRoot="LogisticsOrder">
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
	
</div>