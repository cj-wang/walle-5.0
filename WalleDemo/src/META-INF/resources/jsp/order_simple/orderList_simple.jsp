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
		parent.addTab("订单录入-简版", "jsp/order_simple/orderEdit_simple.jsp", "icon icon-nav", true);
		if (parent.getTabIframe("订单录入-简版").reset) {
			parent.getTabIframe("订单录入-简版").reset();
		}
	};
	
	//编辑
	function editRow() {
		var selected = $("#gridResult").datagrid("endEdit").datagrid("getSelected");
		if (selected) {
			parent.addTab("订单录入-简版", "jsp/order_simple/orderEdit_simple.jsp", "icon icon-nav", true);
			if (parent.getTabIframe("订单录入-简版").loadByOrderNo) {
				parent.getTabIframe("订单录入-简版").loadByOrderNo(selected.logisticsOrderNo);
			} else {
				parent.getTabIframe("订单录入-简版").orderNo = selected.logisticsOrderNo;
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

	<div region="north" style="height: 87px;">
		<div class="datagrid-toolbar">
			<button onclick="query()">查询</button>
			<button onclick="clearQueryForm()">重置</button>
		</div>
		
		<form id="formQuery" class="easyui-form">
			<table>
				<tr>
					<td align="right">　作业单号:</td>
					<td>
						<input name="logisticsOrderNo" operator="ilikeAnywhere"/>
					</td>
					<td align="right">　SO号:</td>
					<td>
						<input name="soNo" operator="ilikeAnywhere"/>
					</td>
					<td align="right">　提柜地点:</td>
					<td>
						<input name="pickLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
					</td>
					<td align="right">　还柜地点:</td>
					<td>
						<input name="backLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
					</td>
				</tr>
				<tr>
					<td align="right">　委托人:</td>
					<td>
						<input name="consignee" class="easyui-combogrid" codetype="CUSTOMER"/>
					</td>
					<td align="right">　提/还吉重:</td>
					<td>
						<input name="logisticsOrderType" class="easyui-combobox" codetype="SERVICE_ITEM" multiple="true" operator="in"/>
					</td>
					<td align="right">　申请日期:</td>
					<td colspan="3">
						<div colspan="2">
							<input name="applicationDate" class="easyui-datebox" operator="dateBegin" style="width:85px;"/> -
							<input name="applicationDate" class="easyui-datebox" operator="dateEnd" style="width:85px;"/>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div region="center">
		<div class="datagrid-toolbar">
			<button onclick="appendRow()">新增</button>
			<button onclick="editRow()">编辑</button>
			<button onclick="deleteSelectedRows()">删除</button>
			<button onclick="reload()">刷新</button>
		</div>
		<table id="gridResult" class="easyui-datagrid" i18nRoot="LogisticsOrder" fit="true" border="false">
			<thead>
				<tr>
					<th field="logisticsOrderNo" width="100"/>
					<th field="soNo" width="100"/>
					<th field="pickLocation" codetype="DATA_SOURCE" width="100"/>
					<th field="backLocation" codetype="DATA_SOURCE" width="100"/>
					<th field="consignee" codetype="CUSTOMER" width="100"/>
					<th field="consigneeName" width="100"/>
					<th field="logisticsOrderType" codetype="SERVICE_ITEM" width="100"/>
					<th field="applicationDate" width="100"/>
				</tr>
			</thead>
		</table>
	</div>
		
</body>
</html>