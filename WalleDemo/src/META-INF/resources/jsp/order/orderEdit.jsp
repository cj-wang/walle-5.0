<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include.jsp" %>

<script type="text/javascript">

	//orderNo有可能在list页面打开本页面时赋值
	if (typeof orderNo == "undefined") {
		orderNo = null;
	}

	//查询
	function query() {
		var orderNo = $("#logisticsOrderNo").val();
		if (orderNo) {
			loadByOrderNo(orderNo);
		}
	};
	
	//保存
	function save() {
		LogisticsOrderManager.save(getData(), function(result) {
			$.messager.alert("提示", "保存成功", "info");
			setData(result);
		});
	};
	
	//保存并新增
	function saveAndNew() {
		LogisticsOrderManager.save(getData(), function(result) {
			reset();
		});
	};
	
	//同步到仓码
	function saveAndPost() {
		LogisticsOrderManager.saveAndPostToCm(getData(), function(result) {
			$.messager.alert("提示", "同步到仓码成功", "info");
			setData(result);
		});
	};

	//删除
	function remove() {
		var orderNo = $("#logisticsOrderNo").val();
		if (orderNo) {
			$.messager.confirm("提示", "确认删除？", function(b) {
				if (b) {
					LogisticsOrderManager.removeByOrderNo(orderNo, function(result) {
						$.messager.alert("提示", "删除成功", "info");
						reset();
					});
				}
			});
		};
	};

	//重置
	function reset() {
		$("#logisticsOrderNo").focus();
		orderNo = null;
		$("#formOrder").form("setData", {
			pickLocation : "djcm",
			backLocation : "wycm"
		});
		$("#formDetailsOut").form("setData", {
			serviceType : "吉出重回",
			remark : "测试箱号：OOLU1073190, OOLU7669490, CLHU3570668"
		});
		$("#formDetailsIn").form("setData", {
			serviceType : "陆运重进"
		});
		$("#gridOutCargo").datagrid("loadData", {
			rows : []
		});
		$("#gridOutContainer").datagrid("loadData", {
			rows : []
		});
		$("#gridInCargo").datagrid("loadData", {
			rows : []
		});
		$("#gridInContainer").datagrid("loadData", {
			rows : []
		});
	};
	
	function clear() {
		$("#logisticsOrderNo").focus();
		orderNo = null;
		$("#formOrder").form("setData", {});
		$("#formDetailsOut").form("setData", {});
		$("#formDetailsIn").form("setData", {});
		$("#gridOutCargo").datagrid("loadData", {
			rows : []
		});
		$("#gridOutContainer").datagrid("loadData", {
			rows : []
		});
		$("#gridInCargo").datagrid("loadData", {
			rows : []
		});
		$("#gridInContainer").datagrid("loadData", {
			rows : []
		});
	};
	
	function getData() {
		return $.extend($("#formOrder").form("getData"), {
			details : [$.extend($("#formDetailsOut").form("getData"), {
				workActivity : "PK",
				cargos : $("#gridOutCargo").datagrid("getData").rows,
				containers : $("#gridOutContainer").datagrid("getData").rows
			}), $.extend($("#formDetailsIn").form("getData"), {
				workActivity : "GR",
				cargos : $("#gridInCargo").datagrid("getData").rows,
				containers : $("#gridInContainer").datagrid("getData").rows
			})],
			selectCodeValues : null
		});
	};

	function setData(orderEntity) {
		reset();
		orderNo = orderEntity.logisticsOrderNo;
		$("#formOrder").form("setData", orderEntity);
		$.each(orderEntity.details, function(index, detail) {
			if (detail.workActivity == "PK") {
				$("#formDetailsOut").form("setData", detail);
				$("#gridOutCargo").datagrid("loadData", {
					rows : detail.cargos
				});
				$("#gridOutContainer").datagrid("loadData", {
					rows : detail.containers
				});
			} else if (detail.workActivity == "GR") {
				$("#formDetailsIn").form("setData", detail);
				$("#gridInCargo").datagrid("loadData", {
					rows : detail.cargos
				});
				$("#gridInContainer").datagrid("loadData", {
					rows : detail.containers
				});
			}
		});
	};
	
	function loadByOrderNo(orderNo) {
		clear();
		$("#logisticsOrderNo").val(orderNo);
		LogisticsOrderManager.getByOrderNo(orderNo, function(result) {
			//为combogrid控件准备代码值，否则控件再查询影响页面加载速度
			$.extend(true, $(document).data("selectCodeValues"), result.selectCodeValues);
			setData(result);
		});
	};
	
	function reload() {
		if (orderNo) {
			loadByOrderNo(orderNo);
		} else {
			reset();
		}
	};
	
	//新增
	function appendRow(gridname) {
		$(gridname).datagrid("appendRow", {});
	};
	
	//插入
	function insertRow(gridname) {
		$(gridname).datagrid("insertRow", {
			index : $(gridname).datagrid("getSelectedIndex"),
			row : {}
		});
	};
	
	//删除
	function deleteSelectedRows(gridname) {
		$(gridname).datagrid("deleteSelectedRows");
	};
	
	//取消
	function reloadGrid(gridname) {
		$(gridname).datagrid("reload");
	};
	
	//查询箱信息
	function queryContainer() {
		LogisticsOrderManager.verifyContainerNoForOrder({
			inCntNo : $("#gridOutContainer").datagrid("getColumnEditor", "containerNo").val(),
			typeWork : $("#formDetailsOut input[name='serviceType']").val(),
			userCode : "N/A",
			userNameCn : null,
			userNameEn : null,
			dataSource : $("#formOrder input[name='pickLocation']").val()
		}, function(result) {
			$("#gridOutContainer").datagrid("endEdit");
			if (result.errorMessage) {
				$.messager.alert("提箱信息错误", result.errorMessage, "info");
			} else if (result.warningMessage) {
				$.messager.confirm("提示", result.warningMessage, function(b) {
					if (b) {
						setContainer();
					}
				});
			} else {
				setContainer();
			}
			function setContainer() {
				$("#gridOutContainer").datagrid("updateRow", {
					index : $("#gridOutContainer").datagrid("getSelectedIndex"),
					row : $.extend({}, $("#gridOutContainer").datagrid("getSelected"), result.container)
				});
				//复制到还箱信息
				$("#gridInContainer").datagrid("loadData", $("#gridOutContainer").datagrid("getData"));
			};
			$("#gridOutContainer").datagrid("beginEdit", $("#gridOutContainer").datagrid("getSelectedIndex"));
		});
	};
	
	function queryContainerAsyn() {
		//此时行未选中，所以出让线程，先使行选中，再执行查箱
		setTimeout(function() {
			queryContainer();
		}, 0);
	};
	
	function containerNoQueryFormatter(value) {
		return "<a href='#' onclick='queryContainerAsyn()'>查询</a>";
	};
	
	$(function() {
		
		if (orderNo) {
			loadByOrderNo(orderNo);
		} else {
			reset();
		}
		
		$("#logisticsOrderNo").attr("title", "回车查询订单信息")
		.bind("keydown", function(e) {
			if (e.keyCode == 13) {
				query();
			}
		});
		
		$("#gridOutContainer").datagrid("options").onBeforeEdit = function(rowIndex, rowData) {
			$.fn.datagrid.defaults.onBeforeEdit.apply(this, [rowIndex, rowData]);
			$("#gridOutContainer").datagrid("getColumnEditor", "containerNo")
			.attr("title", "回车查询箱信息")
			.unbind("keydown").bind("keydown", function(e) {
				if (e.keyCode == 13) {
					queryContainer();
				}
			});
		};

	});
	
	
</script>
</head>

<body class="easyui-layout">

	<div region="north" noheader="true">
		<div class="datagrid-toolbar">
			<a class="easyui-linkbutton" iconCls="icon-save" onclick="save()">保存</a>
			<a class="easyui-linkbutton" iconCls="icon-add" onclick="saveAndNew()">保存并新增</a>
			<a class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAndPost()">同步到仓码</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reload()">重置</a>
		</div>
		<!-- 主单信息 -->
		<form id="formOrder" class="easyui-form" columns="4" i18nRoot="LogisticsOrder">
			<div>
				<input id="logisticsOrderNo" name="logisticsOrderNo"/>
				<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="query()" title="根据作业单号查询"></a>
			</div>
			<input name="isConfirmed" class="easyui-combobox" codetype="YES_NO"/>
			<input name="isLocked" class="easyui-combobox" codetype="YES_NO"/>
			<input name="isBill" class="easyui-combobox" codetype="YES_NO"/>
			<input name="soNo" required="true" class="easyui-validatebox"/>
			<input name="pickLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
			<input name="backLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
			<input name="logisticsOrderType" class="easyui-combobox" codetype="SERVICE_ITEM"/>
			<input name="consignee" class="easyui-combogrid" codetype="CUSTOMER"/>
<%--			<input name="consigneeName"/>--%>
			<input name="consigner" class="easyui-combogrid" codetype="CUSTOMER"/>
<%--			<input name="consignerName"/>--%>
			<input name="vesselCode"/>
			<input name="voyage"/>
			<input name="estimatedToDeparture" class="easyui-datebox"/>
			<input name="applicationDate" class="easyui-datebox"/>
			<input name="functionary" class="easyui-combobox" codetype="ALL_USERS"/>
			<input name="createdByUser" class="easyui-combobox" codetype="ALL_USERS"/>
<%--			<input name="createdDtmLoc" class="easyui-datebox"/>--%>
		</form>
	</div>
	
	<div region="center" border="false">
		<div class="easyui-tabs" fit="true" border="false">
			
			<div title="提箱信息" iconCls="icon-edit">
				<div fit="true" class="easyui-layout">
				
					<div region="north" noheader="true" border="false">
						<form id="formDetailsOut" class="easyui-form" columns="4" i18nRoot="LogisticsDetails">
							<input name="logisticsDetailsNo"/>
							<input name="serviceType" class="easyui-combobox" codetype="SERVICE_ITEM"/>
							<input name="verificationCode"/>
							<input name="weighted"/>
							<input name="payment" class="easyui-combobox" codetype="PAYMENT_MODE"/>
							<input name="serviceRequirements" class="easyui-combobox" codetype="WORK_CODE"/>
							<input name="special" class="easyui-combobox" codetype="SPECIAL_CASE"/>
							<input name="truckNo"/>
							<input name="remark" colspan="4"/>
						</form>
					</div>
					
					<div region="center" border="false">
						<div fit="true" class="easyui-layout">
						
							<div region="north" style="height: 135px; padding: 0px 5px 5px 5px;" border="false">
								<div class="datagrid-toolbar">
									<span class="panel-header panel-title" style="float: left; border-style: none; width: 60px;">货物信息</span>
									<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow('#gridOutCargo')">新增</a>
									<a class="easyui-linkbutton" iconCls="icon-redo" onclick="insertRow('#gridOutCargo')">插入</a>
									<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows('#gridOutCargo')">删除</a>
									<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadGrid('#gridOutCargo')">取消</a>
								</div>
								<table id="gridOutCargo" class="easyui-datagrid" i18nRoot="LogisticsCargo" fit="true"
										pagination="false">
									<thead>
										<tr>
											<th field="blNo" editor="text"/>
											<th field="cargoName" editor="text"/>
											<th field="hazardCode" editor="text"/>
											<th field="billingCargoType" editor="combobox" codetype="BILL_CARGO_CLASS"/>
											<th field="quantity" editor="numberbox" align="right"/>
											<th field="packaging" editor="text"/>
											<th field="weight" editor="{type:'numberbox',options:{precision:2}}" align="right"/>
											<th field="volume" editor="{type:'numberbox',options:{precision:4}}" align="right"/>
											<th field="insideOutside" editor="text"/>
											<th field="settlementCode" editor="text"/>
											<th field="stackLocation" editor="text"/>
											<th field="marks" editor="text"/>
											<th field="remark" editor="text"/>
											<th field="cardNumber" editor="text"/>
											<th field="isBonded"  editor="combobox" codetype="YES_NO"/>
											<th field="activity"  editor="combobox" codetype="YES_NO"/>
											<th field="cargoOwner" editor="text"/>
											<th field="cargoOwnerName" editor="text"/>
										</tr>
									</thead>
								</table>
							</div>
							
							<div region="center" style="padding: 0px 5px 5px 5px;" border="false">
								<div class="datagrid-toolbar">
									<span class="panel-header panel-title" style="float: left; border-style: none; width: 60px;">箱信息</span>
									<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow('#gridOutContainer')">新增</a>
									<a class="easyui-linkbutton" iconCls="icon-redo" onclick="insertRow('#gridOutContainer')">插入</a>
									<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows('#gridOutContainer')">删除</a>
									<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadGrid('#gridOutContainer')">取消</a>
								</div>
								<table id="gridOutContainer" class="easyui-datagrid" i18nRoot="LogisticsContainer" fit="true"
										pagination="false">
									<thead>
										<tr>
											<th field="containerNo" editor="text"/>
											<th field="containerNoQuery" formatter="containerNoQueryFormatter" width="32" title=" "/>
											<th field="containerType" editor="combobox" codetype="CONTAINER_TYPE"/>
											<th field="status" editor="text"/>
											<th field="sealNo" editor="text"/>
											<th field="picLocation" editor="combobox" codetype="DATA_SOURCE"/>
											<th field="operatorComp" editor="combogrid" codetype="CUSTOMER"/>
											<th field="ownerComp" editor="combogrid" codetype="CUSTOMER"/>
											<th field="backLocation" editor="combobox" codetype="DATA_SOURCE"/>
											<th field="truck" editor="text"/>
											<th field="quantity" editor="numberbox" align="right"/>
											<th field="weight" editor="{type:'numberbox',options:{precision:2}}" align="right"/>
											<th field="remark" editor="text"/>
											<th field="soNo" editor="text"/>
											<th field="effectiveDate" editor="datebox"/>
											<th field="landOrShip" editor="text"/>
											<th field="isFirstinFirstout" editor="text"/>
											<th field="containerWord" editor="text"/>
											<th field="containerWeight" editor="text"/>
											<th field="blNo" editor="text"/>
											<th field="truckTeam" editor="text"/>
											<th field="vesselCode" editor="text"/>
											<th field="vesselName" editor="text"/>
											<th field="containerState" editor="text"/>
											<th field="stackLocation" editor="text"/>
										</tr>
									</thead>
								</table>
							</div>
							
						</div>
					</div>
					
				</div>
			</div>
			
			<div title="还箱信息" iconCls="icon-edit">
				<div fit="true" class="easyui-layout">
				
					<div region="north" noheader="true" border="false">
						<form id="formDetailsIn" class="easyui-form" columns="4" i18nRoot="LogisticsDetails">
							<input name="logisticsDetailsNo"/>
							<input name="serviceType" class="easyui-combobox" codetype="SERVICE_ITEM"/>
							<input name="verificationCode"/>
							<input name="weighted"/>
							<input name="payment" class="easyui-combobox" codetype="PAYMENT_MODE"/>
							<input name="serviceRequirements" class="easyui-combobox" codetype="WORK_CODE"/>
							<input name="special" class="easyui-combobox" codetype="SPECIAL_CASE"/>
							<input name="truckNo"/>
							<input name="remark" colspan="4"/>
						</form>
					</div>
					
					<div region="center" border="false">
						<div fit="true" class="easyui-layout">
						
							<div region="north" style="height: 135px; padding: 0px 5px 5px 5px;" border="false">
								<div class="datagrid-toolbar">
									<span class="panel-header panel-title" style="float: left; border-style: none; width: 60px;">货物信息</span>
									<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow('#gridInCargo')">新增</a>
									<a class="easyui-linkbutton" iconCls="icon-redo" onclick="insertRow('#gridInCargo')">插入</a>
									<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows('#gridInCargo')">删除</a>
									<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadGrid('#gridInCargo')">取消</a>
								</div>
								<table id="gridInCargo" class="easyui-datagrid" i18nRoot="LogisticsCargo" fit="true"
										pagination="false">
									<thead>
										<tr>
											<th field="blNo" editor="text"/>
											<th field="cargoName" editor="text"/>
											<th field="hazardCode" editor="text"/>
											<th field="billingCargoType" editor="combobox" codetype="BILL_CARGO_CLASS"/>
											<th field="quantity" editor="numberbox" align="right"/>
											<th field="packaging" editor="text"/>
											<th field="weight" editor="{type:'numberbox',options:{precision:2}}" align="right"/>
											<th field="volume" editor="{type:'numberbox',options:{precision:4}}" align="right"/>
											<th field="insideOutside" editor="text"/>
											<th field="settlementCode" editor="text"/>
											<th field="stackLocation" editor="text"/>
											<th field="marks" editor="text"/>
											<th field="remark" editor="text"/>
											<th field="cardNumber" editor="text"/>
											<th field="isBonded"  editor="combobox" codetype="YES_NO"/>
											<th field="activity"  editor="combobox" codetype="YES_NO"/>
											<th field="cargoOwner" editor="text"/>
											<th field="cargoOwnerName" editor="text"/>
										</tr>
									</thead>
								</table>
							</div>
							
							<div region="center" style="padding: 0px 5px 5px 5px;" border="false">
								<div class="datagrid-toolbar">
									<span class="panel-header panel-title" style="float: left; border-style: none; width: 60px;">箱信息</span>
									<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow('#gridInContainer')">新增</a>
									<a class="easyui-linkbutton" iconCls="icon-redo" onclick="insertRow('#gridInContainer')">插入</a>
									<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows('#gridInContainer')">删除</a>
									<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadGrid('#gridInContainer')">取消</a>
								</div>
								<table id="gridInContainer" class="easyui-datagrid" i18nRoot="LogisticsContainer" fit="true"
										pagination="false">
									<thead>
										<tr>
											<th field="containerNo" editor="text"/>
											<th field="containerType" editor="combobox" codetype="CONTAINER_TYPE"/>
											<th field="status" editor="text"/>
											<th field="sealNo" editor="text"/>
											<th field="picLocation" editor="combobox" codetype="DATA_SOURCE"/>
											<th field="operatorComp" editor="combogrid" codetype="CUSTOMER"/>
											<th field="ownerComp" editor="combogrid" codetype="CUSTOMER"/>
											<th field="backLocation" editor="combobox" codetype="DATA_SOURCE"/>
											<th field="truck" editor="text"/>
											<th field="quantity" editor="numberbox" align="right"/>
											<th field="weight" editor="{type:'numberbox',options:{precision:2}}" align="right"/>
											<th field="remark" editor="text"/>
											<th field="soNo" editor="text"/>
											<th field="effectiveDate" editor="datebox"/>
											<th field="landOrShip" editor="text"/>
											<th field="isFirstinFirstout" editor="text"/>
											<th field="containerWord" editor="text"/>
											<th field="containerWeight" editor="text"/>
											<th field="blNo" editor="text"/>
											<th field="truckTeam" editor="text"/>
											<th field="vesselCode" editor="text"/>
											<th field="vesselName" editor="text"/>
											<th field="containerState" editor="text"/>
											<th field="stackLocation" editor="text"/>
										</tr>
									</thead>
								</table>
							</div>
							
						</div>
					</div>
					
				</div>
			</div>
			
		</div>
	</div>

</body>
</html>