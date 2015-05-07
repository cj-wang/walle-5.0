<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@include file="/common/include.jsp" %>

<script type="text/javascript">

	//orderNo有可能在list页面打开本页面时赋值
	if (typeof orderNo == "undefined") {
		orderNo = null;
	}
	var orderEntity;
	var currentDetail;

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
		$("#radioOut").get(0).checked = true;
		currentDetail = "out";
		orderNo = null;
		orderEntity = {
			pickLocation : "djcm",
			backLocation : "wycm",
			details : [{
				workActivity : "PK",
				serviceType : "吉出重回",
				remark : "测试箱号：OOLU1073190, OOLU7669490, CLHU3570668",
				cargos : [],
				containers : []
			}, {
				workActivity : "GR",
				serviceType : "陆运重进",
				cargos : [],
				containers : []
			}]
		};
		setData(orderEntity);
	};
	
	function clear() {
		$("#logisticsOrderNo").focus();
		$("#radioOut").get(0).checked = true;
		currentDetail = "out";
		orderNo = null;
		orderEntity = {
			details : [{
				workActivity : "PK",
				cargos : [],
				containers : []
			}, {
				workActivity : "GR",
				cargos : [],
				containers : []
			}]
		};
		setData(orderEntity);
	};
	
	function getData() {
		$.extend(orderEntity, $("#formOrder").form("getData"));
		$.extend(orderEntity.details[$("#radioOut").get(0).checked ? 0 : 1], $("#formDetails").form("getData"), {
			workActivity : $("#radioOut").get(0).checked ? "PK" : "GR",
			cargos : $("#gridCargo").datagrid("getData").rows,
			containers : $("#gridContainer").datagrid("getData").rows
		});
		orderEntity.selectCodeValues = null;
		return orderEntity;
	};

	function setData(entity) {
		orderNo = entity.logisticsOrderNo;
		orderEntity = entity;
		$("#formOrder").form("setData", entity);
		if (entity.details[0].workActivity == "PK") {
			$("#radioOut").get(0).checked = true;
			currentDetail = "out";
		} else {
			$("#radioIn").get(0).checked = true;
			currentDetail = "in";
		}
		$("#formDetails").form("setData", entity.details[0]);
		$("#gridCargo").datagrid("loadData", {
			rows : entity.details[0].cargos
		});
		$("#gridContainer").datagrid("loadData", {
			rows : entity.details[0].containers
		});
	};
	
	function showOut() {
		if (currentDetail == "out") {
			return;
		}
		currentDetail = "out";
		$.extend(orderEntity.details[1], $("#formDetails").form("getData"), {
			workActivity : "GR",
			cargos : $("#gridCargo").datagrid("getData").rows,
			containers : $("#gridContainer").datagrid("getData").rows
		});
		$("#formDetails").form("setData", orderEntity.details[0]);
		$("#gridCargo").datagrid("loadData", {
			rows : orderEntity.details[0].cargos
		});
		$("#gridContainer").datagrid("loadData", {
			rows : orderEntity.details[0].containers
		});
	};
	
	function showIn() {
		if (currentDetail == "in") {
			return;
		}
		currentDetail = "in";
		$.extend(orderEntity.details[0], $("#formDetails").form("getData"), {
			workActivity : "PK",
			cargos : $("#gridCargo").datagrid("getData").rows,
			containers : $("#gridContainer").datagrid("getData").rows
		});
		$("#formDetails").form("setData", orderEntity.details[1]);
		$("#gridCargo").datagrid("loadData", {
			rows : orderEntity.details[1].cargos
		});
		$("#gridContainer").datagrid("loadData", {
			rows : orderEntity.details[1].containers
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
			inCntNo : $("#gridContainer").datagrid("getColumnEditor", "containerNo").val(),
			typeWork : $("#formDetails input[name='serviceType']").val(),
			userCode : "N/A",
			userNameCn : null,
			userNameEn : null,
			dataSource : $("#radioOut").get(0).checked
				? $("#formOrder input[name='pickLocation']").val()
				: $("#formOrder input[name='backLocation']").val()
		}, function(result) {
			$("#gridContainer").datagrid("endEdit");
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
				$("#gridContainer").datagrid("updateRow", {
					index : $("#gridContainer").datagrid("getSelectedIndex"),
					row : $.extend({}, $("#gridContainer").datagrid("getSelected"), result.container)
				});
				//复制到还箱信息
				//TODO
			};
			$("#gridContainer").datagrid("beginEdit", $("#gridContainer").datagrid("getSelectedIndex"));
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
		
		$("#gridContainer").datagrid("options").onBeforeEdit = function(rowIndex, rowData) {
			$.fn.datagrid.defaults.onBeforeEdit.apply(this, [rowIndex, rowData]);
			$("#gridContainer").datagrid("getColumnEditor", "containerNo")
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

	<div region="north" style="height: 261px;">
		<div class="datagrid-toolbar">
			<button onclick="save()">保存</button>
			<button onclick="saveAndNew()">保存并新增</button>
			<button onclick="saveAndPost()">同步到仓码</button>
			<button onclick="remove()">删除</button>
			<button onclick="reload()">重置</button>
		</div>
	
		<!-- 主单信息 -->
		<form id="formOrder" class="easyui-form">
			<table>
				<tr>
					<td align="right">　作业单号:</td>
					<td>
						<div>
							<input id="logisticsOrderNo" name="logisticsOrderNo"/>
							<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="query()" title="根据作业单号查询"></a>
						</div>
					</td>
					<td align="right">　是否已确认:</td>
					<td>
						<input name="isConfirmed" class="easyui-combobox" codetype="YES_NO"/>
					</td>
					<td align="right">　是否锁定:</td>
					<td>
						<input name="isLocked" class="easyui-combobox" codetype="YES_NO"/>
					</td>
					<td align="right">　是否计费:</td>
					<td>
						<input name="isBill" class="easyui-combobox" codetype="YES_NO"/>
					</td>
				</tr>
				<tr>
					<td align="right">　SO号:</td>
					<td>
						<input name="soNo" required="true" class="easyui-validatebox"/>
					</td>
					<td align="right">　提柜地点:</td>
					<td>
						<input name="pickLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
					</td>
					<td align="right">　还柜地点:</td>
					<td>
						<input name="backLocation" class="easyui-combobox" codetype="DATA_SOURCE"/>
					</td>
					<td align="right">　提/还吉重:</td>
					<td>
						<input name="logisticsOrderType" class="easyui-combobox" codetype="SERVICE_ITEM"/>
					</td>
				</tr>
				<tr>
					<td align="right">　委托人:</td>
					<td>
						<input name="consignee" class="easyui-combogrid" codetype="CUSTOMER"/>
					</td>
					<td align="right">　收货人:</td>
					<td>
						<input name="consigner" class="easyui-combogrid" codetype="CUSTOMER"/>
					</td>
					<td align="right">　船舶代码:</td>
					<td>
						<input name="vesselCode"/>
					</td>
					<td align="right">　船舶航次:</td>
					<td>
						<input name="voyage"/>
					</td>
				</tr>
				<tr>
					<td align="right">　预出口日期:</td>
					<td>
						<input name="estimatedToDeparture" class="easyui-datebox"/>
					</td>
					<td align="right">　申请日期:</td>
					<td>
						<input name="applicationDate" class="easyui-datebox"/>
					</td>
					<td align="right">　经办人:</td>
					<td>
						<input name="functionary" class="easyui-combobox" codetype="ALL_USERS"/>
					</td>
					<td align="right">　录入人:</td>
					<td>
						<input name="createdByUser" class="easyui-combobox" codetype="ALL_USERS"/>
					</td>
				</tr>
			</table>
		</form>
	
		<hr />
	
		<div style="padding: 0px 0px 5px 25px;">
			<input id="radioOut" type="radio" name="out_in" onclick="showOut()">提箱信息
			<input id="radioIn" type="radio" name="out_in" onclick="showIn()">还箱信息
		</div>
	
		<form id="formDetails" class="easyui-form">
			<table>
				<tr>
					<td align="right">　作业单号:</td>
					<td>
						<input name="logisticsDetailsNo"/>
					</td>
					<td align="right">　作业项目:</td>
					<td>
						<input name="serviceType" class="easyui-combobox" codetype="SERVICE_ITEM"/>
					</td>
					<td align="right">　验证码:</td>
					<td>
						<input name="verificationCode"/>
					</td>
					<td align="right">　过磅:</td>
					<td>
						<input name="weighted"/>
					</td>
				</tr>
				<tr>
					<td align="right">　付款方式:</td>
					<td>
						<input name="payment" class="easyui-combobox" codetype="PAYMENT_MODE"/>
					</td>
					<td align="right">　作业情况:</td>
					<td>
						<input name="serviceRequirements" class="easyui-combobox" codetype="WORK_CODE"/>
					</td>
					<td align="right">　特约事项:</td>
					<td>
						<input name="special" class="easyui-combobox" codetype="SPECIAL_CASE"/>
					</td>
					<td align="right">　车牌:</td>
					<td>
						<input name="truckNo"/>
					</td>
				</tr>
				<tr>
					<td align="right">　备注:</td>
					<td colspan="7">
						<input name="remark" colspan="4" style="width: 99%"/>
					</td>
				</tr>
			</table>
		</form>
	
	</div>
	
	<div region="center" border="false">
		<div class="easyui-layout" fit="true">
		
			<div region="north" style="height: 135px;">
				<div class="datagrid-toolbar">
					<span class="panel-header panel-title" style="float: left; border-style: none; width: 60px;">货物信息</span>
					<button onclick="appendRow('#gridCargo')">新增</button>
					<button onclick="insertRow('#gridCargo')">插入</button>
					<button onclick="deleteSelectedRows('#gridCargo')">删除</button>
					<button onclick="reloadGrid('#gridCargo')">取消</button>
				</div>
				<table id="gridCargo" class="easyui-datagrid" i18nRoot="LogisticsCargo" fit="true"  border="false"
						pagination="false">
					<thead>
						<tr>
							<th field="blNo" editor="text" width="100"/>
							<th field="cargoName" editor="text" width="100"/>
							<th field="hazardCode" editor="text" width="100"/>
							<th field="billingCargoType" editor="combobox" codetype="BILL_CARGO_CLASS" width="100"/>
							<th field="quantity" editor="numberbox" align="right" width="100"/>
							<th field="packaging" editor="text" width="100"/>
							<th field="weight" editor="{type:'numberbox',options:{precision:2}}" align="right" width="100"/>
							<th field="volume" editor="{type:'numberbox',options:{precision:4}}" align="right" width="100"/>
							<th field="insideOutside" editor="text" width="100"/>
							<th field="settlementCode" editor="text" width="100"/>
							<th field="stackLocation" editor="text" width="100"/>
							<th field="marks" editor="text" width="100"/>
							<th field="remark" editor="text" width="100"/>
							<th field="cardNumber" editor="text" width="100"/>
							<th field="isBonded"  editor="combobox" codetype="YES_NO" width="100"/>
							<th field="activity"  editor="combobox" codetype="YES_NO" width="100"/>
							<th field="cargoOwner" editor="text" width="100"/>
							<th field="cargoOwnerName" editor="text" width="100"/>
						</tr>
					</thead>
				</table>
			</div>
			
			<div region="center">
				<div class="datagrid-toolbar">
					<span class="panel-header panel-title" style="float: left; border-style: none; width: 60px;">箱信息</span>
					<button onclick="appendRow('#gridContainer')">新增</button>
					<button onclick="insertRow('#gridContainer')">插入</button>
					<button onclick="deleteSelectedRows('#gridContainer')">删除</button>
					<button onclick="reloadGrid('#gridContainer')">取消</button>
				</div>
				<table id="gridContainer" class="easyui-datagrid" i18nRoot="LogisticsContainer" fit="true" border="false"
						pagination="false">
					<thead>
						<tr>
							<th field="containerNo" editor="text" width="100"/>
							<th field="containerNoQuery" formatter="containerNoQueryFormatter" width="32" title=" " width="100"/>
							<th field="containerType" editor="combobox" codetype="CONTAINER_TYPE" width="100"/>
							<th field="status" editor="text" width="100"/>
							<th field="sealNo" editor="text" width="100"/>
							<th field="picLocation" editor="combobox" codetype="DATA_SOURCE" width="100"/>
							<th field="operatorComp" editor="combogrid" codetype="CUSTOMER" width="100"/>
							<th field="ownerComp" editor="combogrid" codetype="CUSTOMER" width="100"/>
							<th field="backLocation" editor="combobox" codetype="DATA_SOURCE" width="100"/>
							<th field="truck" editor="text" width="100"/>
							<th field="quantity" editor="numberbox" align="right" width="100"/>
							<th field="weight" editor="{type:'numberbox',options:{precision:2}}" align="right" width="100"/>
							<th field="remark" editor="text" width="100"/>
							<th field="soNo" editor="text" width="100"/>
							<th field="effectiveDate" editor="datebox" width="100"/>
							<th field="landOrShip" editor="text" width="100"/>
							<th field="isFirstinFirstout" editor="text" width="100"/>
							<th field="containerWord" editor="text" width="100"/>
							<th field="containerWeight" editor="text" width="100"/>
							<th field="blNo" editor="text" width="100"/>
							<th field="truckTeam" editor="text" width="100"/>
							<th field="vesselCode" editor="text" width="100"/>
							<th field="vesselName" editor="text" width="100"/>
							<th field="containerState" editor="text" width="100"/>
							<th field="stackLocation" editor="text" width="100"/>
						</tr>
					</thead>
				</table>
			</div>
			
		</div>
	</div>


</body>
</html>