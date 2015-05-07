<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.sinotrans.gd.wlp.util.StringUtil"%>
<%@page import="com.sinotrans.gd.wlp.util.CommonUtil"%>
<%@page import="com.sinotrans.framework.core.util.ContextUtils"%>
<%@page import="com.sinotrans.gd.wlp.basicdata.service.BasCodeDefManager"%>
<%@page import="com.sinotrans.gd.wlp.basicdata.model.BasCodeDefModel"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@ page import="com.sinotrans.gd.wlp.system.entity.SessionContextUserEntity" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include.jsp" %>
<%@include file="/common/buttonAuthorization.jsp" %>

<%
	String sNowDate = StringUtil.DateToString(new Date(), "yyyy-MM-dd");
	String sDate = StringUtil.DateToString(new Date(), "yyyyMMdd");
	String orderNo = request.getParameter("orderno") !=null ? request.getParameter("orderno") : "";
	//InetAddress inet = InetAddress.getLocalHost();
	String computerName = request.getSession().getAttribute("REMOTE_COMPUTER_NAME")!=null ? (String)request.getSession().getAttribute("REMOTE_COMPUTER_NAME"):"获取失败";
	String computerIP = request.getSession().getAttribute("REMOTE_COMPUTER_ADDR")!=null ? (String)request.getSession().getAttribute("REMOTE_COMPUTER_ADDR"):"获取失败";
	String fgno = request.getParameter("fgno")!=null?request.getParameter("fgno"):"";
	String so = request.getParameter("so")!=null?request.getParameter("so"):"";
	//String orderMappingType = CommonUtil.DCS_LOGISTICS_ORDER;
	String detailMappingType = CommonUtil.DCS_LOGISTICS_DETAIL;
	String userEmail = SessionContextUserEntity.currentUser().getEmail();
	String loginUser = SessionContextUserEntity.currentUser().getUserId();
	String checkConfigOrderExpiryDate = "";
	List<BasCodeDefModel> configOrderExpiryDate = new ArrayList<BasCodeDefModel>();
	if(request.getSession().getAttribute("CONFIG_ORDER_EXPIRYDATE") !=null ){
		configOrderExpiryDate = (List<BasCodeDefModel>)request.getSession().getAttribute("CONFIG_ORDER_EXPIRYDATE");
	}else{
		BasCodeDefManager basCodeDefManager = ContextUtils.getBeanOfType(BasCodeDefManager.class);
		configOrderExpiryDate = basCodeDefManager.findByTypeCode("CONFIG_ORDER_EXPIRYDATE",true);
		if(configOrderExpiryDate != null && configOrderExpiryDate.size() >0)
			request.getSession().setAttribute("CONFIG_ORDER_EXPIRYDATE", configOrderExpiryDate);
		else{
			checkConfigOrderExpiryDate = "查找系统默认有效日期配置失败，请联系系统管理员增加有效日期配置！";
		}
	}
	
%>
<style>
.readonlyinput {
	border:1px solid #a4bed4;	
}
</style>
<script type="text/javascript" src="<%=contextPath%>/js/util/Hashtable.class.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/util/date.format.js"></script>
<script type="text/javascript">

$.extend($.fn.validatebox.defaults.rules, { 
	isnumber: { 
		validator: function(value, param) { 
			return /^\d*$/.test(value);
		}, 
		message: '数据类型不正确！'} 
	}); 
	
$.extend($.fn.validatebox.defaults.rules, { 
	isnumber2: { 
		validator: function(value, param) { 
			return /^[1-9]+\d*$/.test(value);
		}, 
		message: '数据类型不正确！'} 
	}); 
	
$.extend($.fn.validatebox.defaults.rules, { 
	iscontainerno: { 
		validator: function(value, param) { 
			if(value.indexOf("箱")>=0) {
				return true;
			}else if(value.length > 11) {
				return false;
			}else {
				return /^\w*$/.test(value);
			}
		}, 
		message: '只允许不超过11位的数字和字母！'} 
	}); 
	
$.extend($.fn.validatebox.defaults.rules, { 
	notchinese: { 
		validator: function(value, param) { 
			return /^[^\u4e00-\u9fa5]{0,}$/.test(value);
		}, 
		message: '不能输入中文！'} 
	}); 
	
$.extend($.fn.validatebox.defaults.rules, { 
	maxLength: { 
		validator: function(value, param) { 
			return /^\d{1,12}(\.\d{1,4})?$/.test(value);
		}, 
		message: '数据不规范，最多允许4位小数！'} 
	}); 
	
$.extend($.fn.validatebox.defaults.rules, { 
	timevalidate: { 
		validator: function(value, param) { 
			//var planDate = $("#plandate").datebox("getValue");
			alert(value);
			if(value != null && value.length >0){
				var planYear = value.substring(0,4);
				var planMonth = value.substring(5,7);
				var planDay = value.substring(8,10);
				var nPlanDate = parseInt(planYear+planMonth+planDay);
				var nNowDate = parseInt("<%=sDate%>");
				return nPlanDate>=nNowDate;
			}else{
				return true;
			}
		}, 
		message: '预出口日期不能在当天之前！'} 
	}); 

var initOrderNo = "<%=orderNo%>";
var initPayType = "全部费用托收";
var controlwordToAdd = "000000000000000";
var checkConfigOrderExpiryDate = "<%=checkConfigOrderExpiryDate%>";
var sum = 0;
var sum20 = 0;
var sum40 = 0;
var sum45 = 0;

var afterEditSum20 = 0;
var afterEditSum40 = 0;
var afterEditSum45 = 0;

var canAddDetailAndContainer = true;
var initAdminCode = "";
var initStatus = "";
var initTransactionType = "";
var initDeliveryType = "";
var deliveryStatus = "";
var pickupcntrstatus = "";
var returncntrstatus = "";
var initHaul="";

var initExpiryDate = new Date();
var initNoExpiryDate = new Date();
var initExpiryDateHash = new Hashtable();
<%
	if(configOrderExpiryDate !=null && configOrderExpiryDate.size() >0){
		for(BasCodeDefModel basCodeDefModel : configOrderExpiryDate){
%>
initExpiryDateHash.put("<%=basCodeDefModel.getCodeValue()%>","<%=basCodeDefModel.getCodeNumber()%>");
<%
		}
	}
%>


//判断输入的箱型个数是否合法
function checkBoxType(val) {
	if(val.search(/^[0-9]*$/)==0) {
		return true;
	}else {
		return false;
	}
}

//检查箱计划数
function checkBox(type) {
	var temp = $("#cun"+type).val();
	if(temp.length>0 && !checkBoxType(temp)) {
		$.messager.alert("提示", "请输入正确的箱型数目!", "warning");
		$("#cun"+type).focus();
		return;
	}else if(temp.length>0) {
		/*if(type == 20) {
			if(parseInt(temp) > sum20) {
				$.messager.alert("提示", "20寸最多允许"+sum20+"个！", "warning");
				$("#cun"+type).val(sum20);
				return;
			}
		}else if(type == 40) {
			if(parseInt(temp) > sum40) {
				$.messager.alert("提示", "40寸最多允许"+sum40+"个！", "warning");
				$("#cun"+type).val(sum40);
				return;
			}
		}else if(type == 45){
			if(parseInt(temp) > sum45) {
				$.messager.alert("提示", "45寸最多允许"+sum45+"个！", "warning");
				$("#cun"+type).val(sum45);
				return;
			}
		}*/
	}
	
}

//获取已经存在的集装箱相应寸数的数量
function getExistContainerBoxSum() {
	var tempSum20 = 0;
	var tempSum40 = 0;
	var tempSum45 = 0;
	var existRows = $("#dcsContainerInfo").datagrid("getData").rows;
	for(var i=0; i<existRows.length; i++) {
		var type = existRows[i].containerType;
		if(type.indexOf("20") >= 0) {
			tempSum20++;
		}else if(type.indexOf("40") >= 0) {
			tempSum40++;
		}else if(type.indexOf("45") >= 0) {
			tempSum45++;
		}
	}
	return [tempSum20, tempSum40, tempSum45]; 
}

//检查数据是否符合限制
function checkExistContainerBoxSum() {
	var temp = getExistContainerBoxSum();
	if(sum > 0) {
		if(temp[0]<=sum20 && temp[1]<=sum40 && temp[2]<=sum45) {
			return true;
		}else {
			return false;
		}
	}else {
		return true;
	}
	
}

//现场选箱时的校验
function checkBoxBeforeAddContainers(toAddRows) {
	if(sum != 0) {//需要做控制
		var tempSum20 = 0;
		var tempSum40 = 0;
		var tempSum45 = 0;
		var existRows = $("#dcsContainerInfo").datagrid("getData").rows;
		for(var i=0; i<existRows.length; i++) {
			var type = existRows[i].containerType;
			if(type.indexOf("20") >= 0) {
				tempSum20++;
			}else if(type.indexOf("40") >= 0) {
				tempSum40++;
			}else if(type.indexOf("45") >= 0) {
				tempSum45++;
			}
		}
		
		for(var i=0; i<toAddRows.length; i++) {
			var type = toAddRows[i].containerType;
			if(type.indexOf("20") >= 0) {
				tempSum20++;
			}else if(type.indexOf("40") >= 0) {
				tempSum40++;
			}else if(type.indexOf("45") >= 0) {
				tempSum45++;
			}
		}
		
		/* mod by DJ ，这里为什么需要提示？
		if(tempSum20 > sum20) {
			$.messager.alert("提示", "20寸箱超出限制数！", "warning");
			return;
		}else if(tempSum40 > sum40) {
			$.messager.alert("提示", "40寸箱超出限制数！", "warning");
			return;
		}else if(tempSum45 > sum45) {
			$.messager.alert("提示", "45寸箱超出限制数！", "warning");
			return;
		} 
		*/
	}
}

//重置
function clearStandbyQueryForm() {
	$("#standbyQueryForm").form("clear");
	$("#standbyQueryForm").form("setData",initStandbyChooseBoxCondition);
	$("#notordered").attr("checked",true);
	$("#notholed").attr("checked",true);
};
function clearOnlineQueryForm() {
	$("#onlineQueryForm").form("clear");
	$("#onlineQueryForm").form("setData",initOnlineChooseBoxCondition);
};

(function($){  
    $.fn.extend({  
        serializeObject:function(){  
            if(this.length>1){  
                return false;  
            }  
            var arr=this.serializeArray();  
            var obj=new Object;  
            $.each(arr,function(k,v){  
                obj[v.name]=v.value;  
            });  
            return obj;  
        }  
    });  
})(jQuery);

//保存，保存之前需要校验各个表单
function saveOrder() {
	//校验作业办单域
	if (!$("#dcsLogisticsOrder").form("validate")) {
		$.messager.alert("提示", "作业办单表单域数据验证错误！", "warning");
		return;
	}
	//校验提单信息域
	if (!$("#dcsLogisticsDetail").datagrid("validate")) {
		$.messager.alert("提示", "提单信息域数据验证错误！", "warning");
		return;
	}
	//校验集装箱信息域
	if (!$("#dcsContainerInfo").datagrid("validate")) {
		$.messager.alert("提示", "集装箱信息域数据验证错误！", "warning");
		return;
	}
	
	var nDetailRows = $("#dcsLogisticsDetail").datagrid("getData").rows;
	var nContainerInfoRows = $("#dcsContainerInfo").datagrid("getData").rows;
	
	//校验集装箱不能重复
	var existRows=$("#dcsContainerInfo").datagrid("getData").rows;
	
	for(var i=0; i<existRows.length-1; i++) {
		var tempNo = existRows[i].containerNo;
		for(var j=i+1; j<existRows.length; j++) {
			var tempNo2 = existRows[j].containerNo;
			
			if(tempNo && tempNo==tempNo2) {
				$.messager.alert("提示", "箱不能重复！", "warning");
				return;
			}
		}
	}

	var weighingchecked = "0";
	var businesstype = "0";
	var arrivaltype = "0";
	if($("#weighingbox").attr("checked") == "checked") {
		weighingchecked = "1";
	}
	if($("#businesstype").combobox("getValue") != "") {
		businesstype = $("#businesstype").combobox("getValue");
	}
	if($("#arrivaltype").combobox("getValue") != "") {
		arrivaltype = $("#arrivaltype").combobox("getValue");
	}
	var controlword = "00"+weighingchecked+businesstype+arrivaltype+controlwordToAdd;
	$("#controlword").val(controlword);
	
	//检验数据是否为空
	/*if(nDetailRows==0 || nContainerInfoRows==0) {
		$.messager.alert("提示", "提单信息、集装箱信息不能为空！", "warning");
		return;
	}*/
	
	//if(checkExistContainerBoxSum()) {
		
	//pickupStatus 、 returnStatus
	$("#pickupstatus").val(pickupcntrstatus);
	$("#returnstatus").val(returncntrstatus);
	
	//校验控箱公司一致问题
	var checkAdminCode = $("#input_cntrAdminCode").combogrid("getValue");
	var checkReturnAdminCode = $("#return_cntrAdminCode").combogrid("getValue");
	for(var i=0; i<existRows.length; i++) {
		var tempAdminCode = existRows[i].cntrAdminCode;
		var tempReturnAdminCode = existRows[i].returnAdminCode;
		if(checkAdminCode && checkAdminCode != tempAdminCode) {
			$.messager.alert("提示", "办单控箱公司与集装箱控箱公司必须保持一致！", "warning");
			return;
		}
		if(checkReturnAdminCode && checkReturnAdminCode != tempReturnAdminCode) {
			$.messager.alert("提示", "办单还柜控箱公司与集装箱还柜控箱公司必须保持一致！", "warning");
			return;
		}
	}
	//提示校验制冷柜
	var hasNoRfContainer = true;
	for(var i=0; i<existRows.length; i++) {
		var tempContainerType = existRows[i].containerType;
		if(tempContainerType.indexOf("RF") > 0)
			hasNoRfContainer = false;
	}
	if(hasNoRfContainer){
		DcsLogisticsOrderManager.saveOrder(getData(), function(result) {
			if(result != null) {
				$.messager.alert("提示", "保存成功！", "info");
				sum20 = result.cun20;
				sum40 = result.cun40;
				sum45 = result.cun45;
				sum = sum20+sum40+sum45;
				$("#sum20").val(result.cun20);
				$("#sum40").val(result.cun40);
				$("#sum45").val(result.cun45);
				controlwordToAdd = result.logisticsControlWord.substring(5,20);
				setData(result);
				isChange = false;
			}else {
				$.messager.alert("警告", "保存出错！", "error");
			}
		});
	}else{
		var hasCheckedRF = false;
		for(var i=0; i<existRows.length; i++) {
			var tempContainerType = existRows[i].containerType;
			var tempRefrigeration = existRows[i].refrigeration;
			if(tempContainerType.indexOf("RF") > 0 && tempRefrigeration != "1" && !hasCheckedRF){
				$.messager.confirm("确定框", "当前办单记录中存在冷藏柜型但未标记为“制冷”，是否继续？",function(r) {
					if(r) {
						hasCheckedRF = true;
						DcsLogisticsOrderManager.saveOrder(getData(), function(result) {
							if(result != null) {
								$.messager.alert("提示", "保存成功！", "info");
								sum20 = result.cun20;
								sum40 = result.cun40;
								sum45 = result.cun45;
								sum = sum20+sum40+sum45;
								$("#sum20").val(result.cun20);
								$("#sum40").val(result.cun40);
								$("#sum45").val(result.cun45);
								controlwordToAdd = result.logisticsControlWord.substring(5,20);
								setData(result);
								isChange = false;
							}else {
								$.messager.alert("警告", "保存出错！", "error");
							}
						});
					}else{
						return;
					}
				});
			}
		}
	}
};


//发送码头
function syncOrder() {
	
	var nDetailRows = $("#dcsLogisticsDetail").datagrid("getData").rows;
	var nContainerInfoRows = $("#dcsContainerInfo").datagrid("getData").rows;
	var weighingchecked = "0";
	var businesstype = "0";
	var arrivaltype = "0";
	DcsLogisticsOrderManager.syncOrder(getData(), function(result) {
		if(result != null) {
			$.messager.alert("提示", "提交成功！", "info");
			sum20 = result.cun20;
			sum40 = result.cun40;
			sum45 = result.cun45;
			sum = sum20+sum40+sum45;
			$("#sum20").val(result.cun20);
			$("#sum40").val(result.cun40);
			$("#sum45").val(result.cun45);
			controlwordToAdd = result.logisticsControlWord.substring(5,20);
			setData(result);
		}else {
			$.messager.alert("警告", "保存出错！", "error");
			return;
		}
	});
		
};


//提柜码头计费
function TXBilling() {
	var url = getUrlByBtnCode("CMV38_TX_JF");
	var txport = $("#txport").combobox("getValue");
	txport = txport.substring(0,4);
	var cmPickupWork = $("#cmPickupWork").val();
	if(cmPickupWork == "" || cmPickupWork == null){
		$.messager.alert("提示", "仓码提箱作业为空！", "warning");
		return;
	}
	url = url.replace("DJCM", txport);
	url = url.replace("none", cmPickupWork);
	parent.addTabs("提柜码头计费", url, "icon icon-nav", true);
}

//还柜码头计费
function JXBilling(){
	var url = getUrlByBtnCode("CMV38_JX_JF");
	var jxport = $("#jxport").combobox("getValue");
	jxport = jxport.substring(0,4);
	var cmReturnWork = $("#cmReturnWork").val();
	if(cmReturnWork == "" || cmReturnWork == null){
		$.messager.alert("提示", "仓码交箱作业为空！", "warning");
		return;
	}
	url = url.replace("DJCM", jxport);
	url = url.replace("none", cmReturnWork);
	parent.addTabs("还柜码头计费", url, "icon icon-nav", true);
}

//提柜码头打印
function printTXOrder(){
	var url = getUrlByBtnCode("CMV38_TX_PRINT");
	var txport = $("#txport").combobox("getValue");
	txport = txport.substring(0,4);
	var cmPickupWork = $("#cmPickupWork").val();
	if(cmPickupWork == "" || cmPickupWork == null){
		$.messager.alert("提示", "仓码提箱作业为空！", "warning");
		return;
	}
	url = url.replace("DJCM", txport);
	url = url.replace("none", cmPickupWork);
	
	parent.addTabs("提柜码头打印", url, "icon icon-nav", true);
}

//还柜码头打印
function printJXOrder(){
	var url = getUrlByBtnCode("CMV38_JX_PRINT");
	var jxport = $("#jxport").combobox("getValue");
	jxport = jxport.substring(0,4);
	var cmReturnWork = $("#cmReturnWork").val();
	if(cmReturnWork == "" || cmReturnWork == null){
		$.messager.alert("提示", "仓码交箱作业为空！", "warning");
		return;
	}
	url = url.replace("DJCM", jxport);
	url = url.replace("none", cmReturnWork);
	parent.addTabs("还柜码头打印", url, "icon icon-nav", true);
}

//删除
function deleteOrder() {
	var orderId = $("#orderid").val();
	if(orderId == "") {
		$.messager.alert("提示", "数据库中不存在该数据！", "warning");
		return;
	}else {
		$.messager.confirm('确定框', '确实要删除该记录？',function(r) {
			if(r) {
				//调用交提箱办单数据提交接口
				DcsLogisticsOrderManager.deleteOrder($("#orderno").val(), function(result){
					$.messager.alert("提示", "成功删除作业单！", "info");
					window.location.href = "dcsOrderEdit.jsp";
				});
			}
		});
	}
}

//获取需要保存的数据
function getData() {

	var containerDataGridChanges = $("#dcsContainerInfo").datagrid("getChanges");

	return $.extend($("#dcsLogisticsOrder").form("getData"), {
		computerIP:"<%=computerIP%>",
		computerName:"<%=computerName%>",
		details:$("#dcsLogisticsDetail").datagrid("getChanges"),
		containerInfos:containerDataGridChanges
	});
};

//隐藏ID列
function hiddenColum(){
	$('#dcsContainerInfo').datagrid('hideColumn','containerInfoId');
}

//数据填充页面
function setData(orderEntity) {
	reset();
	$("#dcsLogisticsOrder").form("setData", orderEntity);
	$("#transactionType").combobox("readonly",true);
	$("#dcsLogisticsDetail").datagrid("loadData", {
		rows : orderEntity.details
	});
	$("#dcsContainerInfo").datagrid("loadData", {
		rows : orderEntity.containerInfos
	});
	
	var temp = getExistContainerBoxSum();
	$("#cun20").val(temp[0]);
	$("#cun40").val(temp[1]);
	$("#cun45").val(temp[2]);
	
	//控制控箱公司、还柜控箱
	var tempRows = orderEntity.containerInfos;
	for(var i=0; i<tempRows.length; i++) {
		DcsContainerInfoManager.checkContainerCanDeleteOrNot($("#txport").combobox("getValue"), tempRows[i].containerNo, tempRows[i].containerInfoId, function(result){
			if(result!=null && result.indexOf("不可删除")>=0) {
				$("#input_cntrAdminCode").combogrid("readonly",true);
				$("#return_cntrAdminCode").combogrid("readonly",true);
			}
		});
	}
	
	
	if((orderEntity.cmPickupWork!=null&&orderEntity.cmPickupWork!="") || (orderEntity.cmReturnWork!=null&&orderEntity.cmReturnWork!="")) {
		$("#txport").combobox("readonly", true);
		$("#jxport").combobox("readonly", true);
	}
	
	var controlWord = orderEntity.logisticsControlWord;
	controlwordToAdd = controlWord.substring(5,20);
	var weighingStatus = controlWord.substring(2,3);
	var businesstype = controlWord.substring(3,4);
	var arrivaltype = controlWord.substring(4,5);
	if(weighingStatus == "1") {
		$("#weighingbox").attr("checked",true);
	}

	if(businesstype != "") {
		$("#businesstype").combobox("setValue", businesstype);
	}

	if(arrivaltype != "") {
		$("#arrivaltype").combobox("setValue", arrivaltype);
	}
	
	if(controlWord.substring(18,19)=="Y") {
		$("#sendstatus").val("已发送");
	}

};


function getUrlByBtnCode(code) {
	var url = "#";
	SysViewButtonManager.getUrlByButtonCode(code, {callback:function(result){
		url = result;
	}, async:false});
	return url;
}

//FRID按钮
function fridClickHandle() {
	var url = getUrlByBtnCode("CMV38_FA_RFID");
	var txport = $("#txport").combobox("getValue");
	txport = txport.substring(0,4);
	if(txport == "DJCM"){
		var cmPickupWork = $("#cmPickupWork").val();
		if(cmPickupWork != null && cmPickupWork != ""){
			url = url.replace("DJCM", txport);
			url = url.replace("none", cmPickupWork);
			parent.addTabs("发FRID卡", url, "icon icon-nav", true);
			return;
		}
	}
	var jxport = $("#jxport").combobox("getValue");
	jxport = jxport.substring(0,4);
	if(jxport == "DJCM"){
		var cmReturnWork = $("#cmReturnWork").val();
		if(cmReturnWork != null && cmReturnWork != ""){
			url = url.replace("DJCM", jxport);
			url = url.replace("none", cmReturnWork);
			parent.addTabs("发FRID卡", url, "icon icon-nav", true);
			return;
		}
	}
	
	$.messager.alert("提示", "提柜地点或交柜地点请选择东江码头，并且仓码提箱作业或仓码交箱作业不能为空！", "warning");
	return;
}

function deliveryTypeRull(initTransactionType, canEdit){
	
	var returnArray = new Hashtable();
	
	if(initTransactionType == "ETI") {//吉箱转入
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "E";
		initDeliveryType = "吉箱转入";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		$("#txport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",true);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "ZFF") {//重进重出
		initStatus = "F";
		pickupcntrstatus = "F";
		returncntrstatus = "F";
		initDeliveryType = "重进重出";
		canAddDetailAndContainer = false;
		$("#standbybnt").linkbutton('disable');
		$("#onlinebnt").linkbutton('disable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",true);
		};
	}else if(initTransactionType == "ZEE") {//吉进吉出
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "E";
		initDeliveryType = "吉进吉出";
		canAddDetailAndContainer = false;
		$("#standbybnt").linkbutton('disable');
		$("#onlinebnt").linkbutton('disable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",true);
		};
	}else if(initTransactionType == "ETO") {//吉箱转出
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "E";
		initDeliveryType = "吉箱转出";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		$("#jxport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",true);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CIF") {//陆运重进
		initStatus = "F";
		pickupcntrstatus = "F";
		returncntrstatus = "F";
		initDeliveryType = "陆运重进";
		canAddDetailAndContainer = false;
		$("#standbybnt").linkbutton('disable');
		$("#onlinebnt").linkbutton('disable');
		$("#payType").combobox("setValue",initPayType);
		$("#txport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",true);
			$("#jxport").combobox("readonly",false);
		}
	}else if(initTransactionType == "CIE") {//陆运吉进;作业项目除"陆运吉进"外，付费方式默认值都未全部费用托收
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "E";
		initDeliveryType = "陆运吉进";
		canAddDetailAndContainer = false;
		$("#standbybnt").linkbutton('disable');
		$("#onlinebnt").linkbutton('disable');
		if(initOrderNo == "") {
			$("#payType").combobox("setValue","全部费用即收");
		}
		$("#txport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",true);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CFN") {//重出不回
		initStatus = "F";
		pickupcntrstatus = "F";
		returncntrstatus = "";
		initDeliveryType = "重出不回";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		$("#jxport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",true);
		};
	}else if(initTransactionType == "CFE") {//重出吉回
		initStatus = "F";
		pickupcntrstatus = "F";
		returncntrstatus = "E";
		initDeliveryType = "重出吉回";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CFF") {//重出重回
		initStatus = "F";
		pickupcntrstatus = "F";
		returncntrstatus = "F";
		initDeliveryType = "重出重回";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CEN") {//吉出不回
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "";
		initDeliveryType = "吉出不回";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		$("#jxport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",true);
		};
	}else if(initTransactionType == "CEF") {//吉出重回
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "F";
		initDeliveryType = "吉出重回";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CEE") {//吉出吉回
		initStatus = "E";
		pickupcntrstatus = "E";
		returncntrstatus = "E";
		initDeliveryType = "吉出吉回";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CYH") {//拆箱验货单
		initStatus = "F";
		pickupcntrstatus = "F";
		returncntrstatus = "F";
		initDeliveryType = "拆箱验货单";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",false);
		};
	}else if(initTransactionType == "CIO") {//原箱复出
		initStatus = "";
		pickupcntrstatus = "";
		returncntrstatus = "";
		initDeliveryType = "原箱复出";
		canAddDetailAndContainer = true;
		$("#standbybnt").linkbutton('enable');
		$("#onlinebnt").linkbutton('enable');
		$("#payType").combobox("setValue",initPayType);
		$("#jxport").combobox("setValue","");
		if(canEdit) {
			$("#txport").combobox("readonly",false);
			$("#jxport").combobox("readonly",true);
		};
	}
	
	returnArray.put("initStatus",initStatus);
	returnArray.put("pickupcntrstatus",pickupcntrstatus);
	returnArray.put("returncntrstatus",returncntrstatus);
	returnArray.put("initDeliveryType",initDeliveryType);
	returnArray.put("canAddDetailAndContainer",canAddDetailAndContainer);

	return returnArray;
	
}

function loadByOrderNo(orderNo) {

		DcsLogisticsOrderManager.getEntityByOrderNo2(orderNo, function(result) {
			var submitStatus = result.logisticsControlWord.substr(17,2);
			//如果有提交仓码则先同步状态
			if(submitStatus.indexOf("Y") >= 0){
				DcsLogisticsOrderManager.checkDcsLogisticsOrderLock(result, {callback:function(reEntity){
					var lockStatus = reEntity.lockStatus;
					if(lockStatus == "0,0,0,0,0") {
						loadEntityForView(reEntity);
					}else if(lockStatus == "0,0,0,0,1"){
						$.messager.confirm("提示", "该作业单原为已经提交仓码状态，但因仓码已将原单删除，本作业单已经被恢复为未提交仓码前状态，请谨慎操作，再次提交会在仓码生成新的作业单！", function(b) {
							if (b) {
								result.cmPickupWork = "";
								result.cmReturnWork = "";
								loadEntityForView(reEntity);
							}
						})
					}else{
						var temps = lockStatus.split(",");
						$.messager.alert("提示", "该作业单已经被锁定！", "warning");
						$(".easyui-linkbutton").linkbutton('disable');
						if(temps[0] == "1"){
							$("#pickupLockStatus").attr("checked",true);
						}
						if(temps[1] == "1"){
							$("#pickupFeeStatus").attr("checked",true);
						}
						if(temps[2] == "1"){
							$("#returnLockStatus").attr("checked",true);
						}
						if(temps[3] == "1"){
							$("#returnFeeStatus").attr("checked",true);
						}
						loadEntityForView(reEntity);
					}
				}, async:false});
			}else{
				loadEntityForView(result);
			}
		});
};

function loadEntityForView(result){
	
	initOrderNo = result.logisticsOrderNo;
	initPayType = result.payType;
	sum20 = result.cun20;
	sum40 = result.cun40;
	sum45 = result.cun45;
	sum = sum20+sum40+sum45;
	$("#sum20").val(sum20);
	$("#sum40").val(sum40);
	$("#sum45").val(sum45);
	
	if(result.toChooseNum > 0) {
		newStandbyRow=result.toChooseNum;
	}
	if(result.toFixNum > 0) {
		newRowNum=result.toFixNum;
	}

	setData(result);

	initAdminCode = $("#input_cntrAdminCode").combogrid("getValue");
	tempReleasedNo = $("#fgno").val();
	
	var initTransactionType = $("#transactionType").combobox("getValue");
	
	var b = true;
	if((result.cmPickupWork!=null&&result.cmPickupWork!="") || (result.cmReturnWork!=null&&result.cmReturnWork!="")) {
		b = false;
	}
	
	var returnArray = deliveryTypeRull(initTransactionType,b);
	var initStatus = returnArray.get("initStatus");
	var pickupcntrstatus = returnArray.get("pickupcntrstatus");
	var returncntrstatus = returnArray.get("returncntrstatus");
	var initDeliveryType = returnArray.get("initDeliveryType");
	var canAddDetailAndContainer = returnArray.get("canAddDetailAndContainer");

}

//新增
var newRowNum = 1;
function appendRow(gridname) {
	if (gridname == "#dcsContainerInfo") {
		//校验集装箱信息域
		if (!$("#dcsContainerInfo").datagrid("validate")) {
			$.messager.alert("提示", "请输入必填项！", "warning");
			return;
		}
		//判断作业项目
		if($("#transactionType").combobox("getText")==null || $("#transactionType").combobox("getText")=="") {
			$.messager.alert("提示", "请先选择作业项目！", "warning");
			return;
		}
		//先判断控箱公司是否为空
		var adminCode = $("#input_cntrAdminCode").combogrid("getValue");
		var returnAdminCode = $("#return_cntrAdminCode").combogrid("getValue");
		var so = $("#so").val();
		if(adminCode==null || adminCode=="") {
			$.messager.alert("提示", "请先选择控箱公司！", "warning");
			return;
		}
		
		//判断箱数限制
		var existRows = $("#dcsContainerInfo").datagrid("getData").rows;
		if(sum>0 && existRows.length==sum) {
			$.messager.alert("提示", "集装箱总数不能超过"+sum+"个！", "warning");
			return;
		}
		
		var toAddNo = "";
		//判断陆运重进和陆运吉进
		if($("#transactionType").combobox("getText")=="陆运重进" || $("#transactionType").combobox("getText")=="陆运吉进") {
			if(newRowNum < 10) {
				toAddNo = "待补箱号00"+newRowNum;
				newRowNum++;
			}else if(newRowNum < 100) {
				toAddNo = "待补箱号0"+newRowNum;
				newRowNum++;
			}else {
				toAddNo = "待补箱号"+newRowNum;
				newRowNum++;
			}
		}
		
		
		//如果是吉出重回，需要自动生成起点和终点
		var pickupDepot = $("#txport").combobox("getValue");
		var returnDepot = $("#jxport").combobox("getValue");
		var addExpiryDate = new Date();
		addExpiryDate = initExpiryDate;

		$(gridname).datagrid("appendRow", {
			containerNo : toAddNo,
			shippingOrderNo : so,
			cntrAdminCode : adminCode,
			returnAdminCode : returnAdminCode, 
			expiryDate : dateFormat(addExpiryDate, "yyyy-mm-dd"),
			pickupDepot : pickupDepot,
			returnedDepot : returnDepot,
			pickupCntrStatus : deliveryStatus,
			haul : initHaul
		});
	} else {
		//校验集装箱信息域
		if (!$("#dcsLogisticsDetail").datagrid("validate")) {
			$.messager.alert("提示", "请输入必填项！", "warning");
			return;
		}
		
		var goodsName = "";
		var goodsKind = "";
		var qtyUnitDesc = "";
		if(initTransactionType=="CEN" || initTransactionType=="CEE" || initTransactionType=="CIE" || initTransactionType=="ZEE") {
			goodsName = "吉柜";
			goodsKind = "吉箱";
			qtyUnitDesc = "箱";
		}else {
			goodsKind = "一般货物";
			qtyUnitDesc = "件";
		}
		
		$(gridname).datagrid("appendRow", {
			goodsDesc : goodsName,
			goodsKind : goodsKind,
			qtyUnitDesc : qtyUnitDesc
		});
	}
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
	if(gridname == "#dcsContainerInfo") {
		//做删除之前先调用接口判断
		var tempRows = $(gridname).datagrid("getSelections");
		for(var i=0; i<tempRows.length; i++) {
			if(tempRows[i].containerInfoId!=null && tempRows[i].containerNo.indexOf("箱")<0 && tempRows[i].infoControlWord!=null && tempRows[i].infoControlWord.substring(18,19)=="Y") {//id 不是 null 同时控制字19位为Y
				DcsContainerInfoManager.checkContainerCanDeleteOrNot($("#txport").combobox("getValue")!=""?$("#txport").combobox("getValue"):$("#jxport").combobox("getValue"), tempRows[i].containerNo, tempRows[i].containerInfoId, function(result){
					if(result != null) {
						$.messager.alert("提示", result, "warning");
						return;
					}else{$(gridname).datagrid("deleteSelectedRows");}
				});
			}else{
				$(gridname).datagrid("deleteSelectedRows");
			}
		}
	}
};

//取消
function reloadGrid(gridname) {
	$(gridname).datagrid("reload");
};


//重置
function reset() {
	$("#dcsLogisticsDetail").datagrid("loadData", {
		rows : []
	});
	$("#dcsContainerInfo").datagrid("loadData", {
		rows : []
	});
};

//中心单号
function getInfoByOrder() {
	$("#orderInputForm").form("clear");
	$("#orderNoDiv").dialog("open");
};
function chooseOrder() {
	if(!$("#orderInputForm").form("validate")) {
		$.messager.alert("提示", "请输入中心单号！", "warning");
		return;
	}else {
		$("#dcsLogisticsOrder").form("clear");
		$("#dcsLogisticsDetail").datagrid("loadData", {
			rows : []
		});
		$("#dcsContainerInfo").datagrid("loadData", {
			rows : []
		});
		var tempOrderNo = $("#input_orderno").val();
		DcsLogisticsOrderManager.checkLogisticsOrderNoExistOrNot(tempOrderNo, function(result){
			if(result) {
				loadByOrderNo($("#input_orderno").val());
				$("#orderNoDiv").dialog("close");
			}else {
				$.messager.alert("提示", "中心单号不存在！", "warning");
				return;
			}
		});
		
	}
};
function chooseOrderCancel() {
	$("#orderNoDiv").dialog("close");
};

//待选箱号
var firstStandbyChoose = true;
var initStandbyChooseBoxCondition = "";
function openStandbyChooseBox() {
	//校验集装箱信息域
	if (!$("#dcsContainerInfo").datagrid("validate")) {
		$.messager.alert("提示", "请先填写完必填项！", "warning");
		return;
	}
	
	//$("#standbyGridResult").datagrid("clear");
	$("#standbyGridResult").datagrid("loadData", {
		rows : []
	});
	
	if(!canAddDetailAndContainer) {
		/*$.messager.alert("提示", "该作业项目下不能进行本操作！", "warning");
		return;*/
	}
	var admincode = $("#input_cntrAdminCode").combogrid("getValue");
	if(admincode==null || admincode=="") {
		$.messager.alert("提示", "请先选择控箱公司！", "warning");
		return;
	}
	
	if(firstStandbyChoose) {
		initStandbyChooseBoxCondition = $("#standbyQueryForm").form("getData");
		firstStandbyChoose = false;
	}else {
		$("#standbyQueryForm").form("clear");
		initStandbyChooseBoxCondition.cntrAdminCode = $("#input_cntrAdminCode").combogrid("getValue");
		$("#standbyQueryForm").form("setData",initStandbyChooseBoxCondition);
		$("#notordered").attr("checked",true);
		$("#notholed").attr("checked",true);
		$("#containerNumber").val(1);
	}
	
	$("#toChooseBoxEdit").dialog("open");
};

//现场选箱
var firstOnlineChoose = true;
var initOnlineChooseBoxCondition = "";
function openOnlineChooseBox() {
	//校验集装箱信息域
	if (!$("#dcsContainerInfo").datagrid("validate")) {
		$.messager.alert("提示", "请先填写完必填项！", "warning");
		return;
	}
	
	//$("#onlineGridResult").datagrid("clear");
	$("#onlineGridResult").datagrid("loadData", {
		rows : []
	});
	
	if(!canAddDetailAndContainer) {
		/*$.messager.alert("提示", "该作业项目下不能进行本操作！", "warning");
		return;*/
		$("#onlinebnt").linkbutton('disable');
	}
	
	var admincode = $("#input_cntrAdminCode").combogrid("getValue");
	var transactionType = $("#transactionType").combobox("getValue");
	if(transactionType==null || transactionType=="") {
		$.messager.alert("提示", "请先选择作业项目！", "warning");
		return;
	}else if(admincode==null || admincode=="") {
		$.messager.alert("提示", "请先选择控箱公司！", "warning");
		return;
	}else {
		$("#input_adminCode").combogrid("setValue", admincode);
		if(transactionType == "ETI") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "ZFF") {
			$("#onlinecontainerstatus").combobox("setValue", "F");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "ZEE") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "ETO") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CIF") {
			$("#onlinecontainerstatus").combobox("setValue", "F");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CIE") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CFN") {
			$("#onlinecontainerstatus").combobox("setValue", "F");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CFE") {
			$("#onlinecontainerstatus").combobox("setValue", "F");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CFF") {
			$("#onlinecontainerstatus").combobox("setValue", "F");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CEN") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CEF") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CEE") {
			$("#onlinecontainerstatus").combobox("setValue", "E");
			$("#onlinecontainerstatus").combobox("readonly");
		}else if(transactionType == "CYH") {
			$("#onlinecontainerstatus").combobox("setValue", "");
			$("#onlinecontainerstatus").combobox("readonly", false);
		}else if(transactionType == "CIO") {
			$("#onlinecontainerstatus").combobox("setValue", "");
			$("#onlinecontainerstatus").combobox("readonly", false);
		}
	}
	
	if(firstOnlineChoose) {
		$("#onlineisInYard").combobox("setValue", "场内");
		/*
		wqs  设置
		*/
		//初始化列表  --begin
		$("#isCompleteYard").attr("checked",true);
		$("#isFIFO").attr("checked",true);
		$("#usePreferredList").attr("checked",true);
		$("#hasDistributionRecord").attr("checked",true);
		$("#hasArrange").attr("checked",true);
		// --end
		
		initOnlineChooseBoxCondition = $("#onlineQueryForm").form("getData");
		firstOnlineChoose = false;
	}else {
		$("#onlineQueryForm").form("clear");
		initOnlineChooseBoxCondition.cntrAdminCode2 = $("#input_cntrAdminCode").combogrid("getValue");
		$("#onlineQueryForm").form("setData",initOnlineChooseBoxCondition);
		$("#onthespot").attr("checked",true);
	}
	
	$("#chooseBoxEdit").dialog("open");
};

var newStandbyRow = 1;
//待选箱号编辑窗口确定
function chooseStandbyBox() {
	//校验
	if (!$("#standbyQueryForm").form("validate")) {
		$.messager.alert("提示", "请选择箱型！", "warning");
		return;
	}
	
	var containerNumber = parseInt($("#containerNumber").val());
	var so = $("#so").val();
	var admincode = $("#input_cntrAdminCode").combogrid("getValue");
	var returnAdminCode = $("#return_cntrAdminCode").combogrid("getValue");
	var containerType = $("#standbyboxtype").combogrid("getValue");
	var liner = $("#liner").combogrid("getValue");

	for(var i=0; i<containerNumber; i++) {
		var toAddNo = "";
		//判断陆运重进和陆运吉进
		if(newStandbyRow < 10) {
			toAddNo = "待选箱号00"+newStandbyRow;
			newStandbyRow++;
		}else if(newRowNum < 100) {
			toAddNo = "待选箱号0"+newStandbyRow;
			newStandbyRow++;
		}else {
			toAddNo = "待选箱号"+newStandbyRow;
			newStandbyRow++;
		}
		
		var validate = $("#validate").datebox("getValue");
		if(toAddNo.length == 0)
			validate = dateFormat(initExpiryDate, "yyyy-mm-dd");
		else
			validate = dateFormat(initNoExpiryDate, "yyyy-mm-dd");

		$("#dcsContainerInfo").datagrid("appendRow", {
			containerNo : toAddNo,
			containerType : containerType,
			pickupCntrStatus : deliveryStatus,
			cntrAdminCode : admincode,
			returnAdminCode : returnAdminCode, 
			linerCode : liner,
			shippingOrderNo : so,
			pickupDepot:$("#txport").combobox("getValue"),
			returnedDepot:$("#jxport").combobox("getValue"),
			expiryDate : validate,
			haul : initHaul
		});
	}
	
	$("#toChooseBoxEdit").dialog("close");
};

//待选箱号编辑窗口取消
function toChooseBoxNoCancel() {
	$("#toChooseBoxEdit").dialog("close");
};

//现场选箱编辑窗口确定
function chooseOnlineBox() {
	var onlineSelectedRows = $("#onlineGridResult").datagrid("getSelections");
	checkBoxBeforeAddContainers(onlineSelectedRows);
	var so = $("#so").val();
	var admincode = $("#input_cntrAdminCode").combogrid("getValue");
	var returnAdminCode = $("#return_cntrAdminCode").combogrid("getValue");
	var linerCode = $("#input_linercode").combogrid("getValue");
	
	var length = onlineSelectedRows.length;
	// LJ add for 301。。
	var lock = "";
	if ($("#transactionType").combobox("getText") =="吉出重回"){
		lock = "Y";
	}
	var chooseOnlineBoxRF = "";
	// .. LJ add for 301
	for(var i=0; i<length; i++) {
		
		$("#dcsContainerInfo").datagrid("appendRow", {
			containerNo:onlineSelectedRows[i].containerNo,
			containerType:onlineSelectedRows[i].containerType,
			pickupCntrStatus:onlineSelectedRows[i].containerStatus,
			//mod by dj 0810 2330
			linerCode:onlineSelectedRows[i].cntrOwnerCode,
			shippingOrderNo : so,
			cntrAdminCode : admincode,
			returnAdminCode : returnAdminCode,
			pickupDepot:$("#txport").combobox("getValue"),
			returnedDepot:$("#jxport").combobox("getValue"),
			expiryDate : dateFormat(initExpiryDate, "yyyy-mm-dd"),
			haul : initHaul
			//statusLock : lock   // LJ add for 301
		});
		
	};
	
	$("#chooseBoxEdit").dialog("close");
};

//现场选箱编辑窗口取消
function chooseBoxCancel() {
	$("#chooseBoxEdit").dialog("close");
};

//通过信息类型、中心码和地点码获取映射码
function getMappingCode(type, centerCode, areaCode) {
	var localCode = "";
	DcsMappingInfoManager.getLocalInfoCodeByMappingTypeDcsInfoCodePortAreaCode(type,centerCode,areaCode, function(result){
		localCode = result;
	});
	return localCode;
}


//待选箱查询
function standbyQuery() {
	
	$("#standbyGridResult").datagrid("loadData", {
		rows : []
	});
	
	//待选箱查询Form校验
	if (!$("#standbyQueryForm").form("validate")) {
		$.messager.alert("提示", "请选择箱型！", "warning");
		return;
	}
	var admincode = $("#input_cntrAdminCode").combogrid("getValue");
	var depot = $("#txport").combobox("getValue");
	if(depot.length > 4) {
		depot = depot.substring(0,4);
	}
	/* //TODO
	//获取映射
	DcsMappingInfoManager.getLocalInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_CUSTOMER",$("#liner").combogrid("getValue"),depot, {callback:function(result){
		$("#local_standbylinercode").val(result);
	}, async:false});
	DcsMappingInfoManager.getLocalInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_CUSTOMER",$("#input_cntrAdminCode").combogrid("getValue"),depot, {callback:function(result){
		$("#standbyadmincode").val(result);
	}, async:false}); */
	
	/*if(admincode!=null && admincode!="") {
		//$("#standbyadmincode").val("00041");
		//$("#standbyadmincode").val(getMappingCode(mappingType,admincode,depot));
		$("#standbyadmincode").val(admincode,depot);
	}*/
	if(depot!=null && depot!="" && depot.length>=4) {
		$("#standbydepot").val(depot.substring(0,4));
	}else {
		$("#standbydepot").val(depot);
	}

	DcsLogisticsOrderManager.queryStandbyContainerForOrder($("#standbyQueryForm").form("getData"),depot, function(result){
		if(result.containerList.length > 0) {
			$("#standbyGridResult").datagrid("loadData", {
				rows : result.containerList
			});
		}
	});
}

//TODO
//现场选箱查询
function onlineQuery() {
	
	$("#onlineGridResult").datagrid("loadData", {
		rows : []
	});
	
	var depot = $("#txport").combobox("getValue");
	if(depot.length > 4) {
		depot = depot.substring(0,4);
	}
	if(depot!=null && depot!="" && depot.length>=4) {
		$("#onlinedepot").val(depot.substring(0,4));
	}else {
		$("#onlinedepot").val(depot);
	}
	//获取映射
	DcsMappingInfoManager.getLocalInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_CUSTOMER",$("#input_cntrAdminCode").combogrid("getValue"),depot, {callback:function(result){
		$("#local_admincode").val(result);
	}, async:false});
	DcsMappingInfoManager.getLocalInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_CUSTOMER",$("#input_linercode").combogrid("getValue"),depot, {callback:function(result){
		$("#local_linercode").val(result);
	}, async:false});
	
	WlpServiceManager.queryOnlineContainerForOrder($("#onlineQueryForm").form("getData"), function(result){
		if(result.containerList!=null && result.containerList.length > 0) {
			for(var i=0; i<result.containerList.length; i++) {
				DcsMappingInfoManager.getDcsInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_CUSTOMER",result.containerList[i].cntrAdminCode,depot, {callback:function(result2){
					result.containerList[i].cntrAdminCode = result2;
				}, async:false});
				
				DcsMappingInfoManager.getDcsInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_CUSTOMER",result.containerList[i].cntrOwnerCode,depot, {callback:function(result2){
					result.containerList[i].cntrOwnerCode = result2;
				}, async:false});
				
				//转船名
				DcsMappingInfoManager.getDcsInfoCodeByMappingTypeDcsInfoCodePortAreaCode("BAS_VESSEL",result.containerList[i].vesselName,depot, {callback:function(result2){
					result.containerList[i].vesselName = result2;
				}, async:false});
			}
			$("#onlineGridResult").datagrid("loadData", {
				rows : result.containerList
			});
		}
	});
	
}

//作业单数据同步
function sendOrderToOtherSystem() {
	BasInterfaceManager.getOnlineContainerForOrder($("#onlineQueryForm").form("getData"), function(result){
		$("#onlineGridResult").datagrid("loadData", {
			rows : result.containerEntityList
		});
	});
}

//获取需要同步的数据
function getToSync(orderNo) {
	var usedConditionList;//选箱条件List
	DcsUsedConditionManager.getByOrderNo(orderNo, function(result){
		usedConditionList = result;
	});

	//DcsUsedConditionManager.getByOrderNo 的返回值里面包含的对象属性应该和接口中的DcsUsedConditionIntEntity对应上才对！！
	
	return $.extend($("#dcsLogisticsOrder").form("getData"), {
		logisticsDetailList:$("#dcsLogisticsDetail").datagrid("getChanges"),
		containerInfoList:$("#dcsContainerInfo").datagrid("getChanges"),
		usedConditionList:usedConditionList
	});
}

var tempReleasedNo = "<%=fgno%>";

//根据放柜编号自动查找ContainerInfo
function findByThisNo(releasedNo) {
	if(releasedNo!=null && releasedNo!="" && tempReleasedNo!=releasedNo) {
		tempReleasedNo = releasedNo;
		
		reset();
		BkgReleasedManager.getBkgReleaseByReleasedNo(releasedNo, function(result) {
			if(result == null) {
				$.messager.alert("提示", "该放柜编号不存在！", "warning");
				return;
			}else if((result.pickupDepot!="HPCM"&&result.pickupDepot!="DJCM") && (result.returnDepot!="HPCM"&&result.returnDepot!="DJCM")) {
				$.messager.alert("提示", "此单号非我司作业！", "warning");
				window.location.href = "dcsOrderEdit.jsp";
				return;
			}else {
				DcsLogisticsOrderManager.getDcsLogisticsOrderEntityByOrderDocumentNo(releasedNo, function(result){
					if(result.containerInfos.length == 0) {
						$("#fgno").val("");
						$("#so").val("");
						$.messager.alert("提示", "该放柜编号下的所有柜都已经办单！", "warning");
						return;
					};
					
					$("#input_cntrAdminCode").combogrid("setValue", result.cntrAdminCode);

					//2012-08-22 xcx  start
					$("#return_cntrAdminCode").combogrid("setValue", result.cntrAdminCode);
					if($("#input_agentCode").combogrid("getValue")=="") {
						$("#input_agentCode").combogrid("setValue", result.cntrAdminCode);
						$("#input_payerCode").combogrid("setValue", result.cntrAdminCode);
					}
				    //2012-08-22 xcx  end	
					
					$("#so").val(result.shippingOrderNo);
					$("#releasedId").val(result.releasedId);
					$("#remarks").val(result.remarks);
					
					if(result.isWeight == "1") {
						$("#weighingbox").attr("checked", true);
					}
					
					$("#totarget").combogrid("setValue", result.toTarget);
					
					// LJ add ..
					//$("#txport").combobox("setValue",result.pickupDepot);
					//$("#jxport").combobox("setValue",result.returnDepot);
					if(result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM"){
						$("#txport").combobox("setValue",result.pickupDepot);
					}else{
						$("#txport").combobox("setValue","");
					}
					
					
					if(result.returnDepot == "HPCM" || result.returnDepot=="DJCM"){
						$("#jxport").combobox("setValue",result.returnDepot);
					}else{
						$("#jxport").combobox("setValue","");
					}
					
					
					// .. LJ add
					sum20 = result.cun20;
					sum40 = result.cun40;
					sum45 = result.cun45;
					sum = sum20+sum40+sum45;
					$("#cun20").val(result.cun20);
					$("#cun40").val(result.cun40);
					$("#cun45").val(result.cun45);
					$("#transactionType").combobox("setValue",result.transactionType);
					initTransactionType = result.transactionType;
					
					if(result.toChooseNum > 0) {
						newStandbyRow=result.toChooseNum;
					}
					if(result.toFixNum > 0) {
						newRowNum=result.toFixNum;
					}
					
					// LJ add ..
					if(initTransactionType == "CFE") {//重出吉回
						if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
							&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
							initTransactionType="CFE";
						}else if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
								&& (result.returnDepot != "HPCM" && result.returnDepot!="DJCM")	){
								initTransactionType="CFN";
						}else if((result.pickupDepot != "HPCM" && result.pickupDepot!="DJCM")
								&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
								initTransactionType = "CIE";
						}
					}else if(initTransactionType == "CFF"){  
						if ((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
					
							&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
							initTransactionType = "CFF";
						}else if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
								&& (result.returnDepot != "HPCM" && result.returnDepot!="DJCM")	){
								initTransactionType = "CFN";
						}else if((result.pickupDepot != "HPCM" && result.pickupDepot!="DJCM")
								&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
								initTransactionType = "CIE";
						}
				
					}else if (initTransactionType == "CEF"){
						if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
								&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
								initTransactionType = "CEF";
							}else if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
									&& (result.returnDepot != "HPCM" && result.returnDepot!="DJCM")	){
									initTransactionType = "CEN";
							}else if((result.pickupDepot != "HPCM" && result.pickupDepot!="DJCM")
									&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
									initTransactionType = "CIF";
							}
					}else if (initTransactionType == "CEE"){
						if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
								&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
								initTransactionType = "CEE";
							}else if((result.pickupDepot == "HPCM" || result.pickupDepot=="DJCM")
									&& (result.returnDepot != "HPCM" && result.returnDepot!="DJCM")	){
									initTransactionType = "CEN";
							}else if((result.pickupDepot != "HPCM" && result.pickupDepot!="DJCM")
									&& (result.returnDepot == "HPCM" || result.returnDepot=="DJCM")	){
									initTransactionType = "CIE";
							}
					}
					$("#transactionType").combobox("setValue", initTransactionType);
					$("#transactionType").combobox("readonly", true);
					
					var goodsName = result.goodsName;
					var goodsKind = "";
					var qtyUnitDesc = "";
					if(initTransactionType=="CEN" || initTransactionType=="CEE" || initTransactionType=="CIE" || initTransactionType=="ZEE") {
						goodsName = "吉柜";
						goodsKind = "吉箱";
						qtyUnitDesc = "箱";
					}else {
						goodsKind = "一般货物";
						qtyUnitDesc = "件";
					}
					//如果有货名，则自动生成一条带货名的提单信息
					$("#dcsLogisticsDetail").datagrid("appendRow", {
						goodsDesc : goodsName,
						goodsKind : goodsKind,
						qtyUnitDesc : qtyUnitDesc
					});

					// DJ add

					var returnArray = deliveryTypeRull(initTransactionType,true);
					initStatus = returnArray.get("initStatus");
					pickupcntrstatus = returnArray.get("pickupcntrstatus");
					returncntrstatus = returnArray.get("returncntrstatus");
					initDeliveryType = returnArray.get("initDeliveryType");
					canAddDetailAndContainer = returnArray.get("canAddDetailAndContainer");
					
					$("#deliveryType").val(initDeliveryType);
					
					/*$("#dcsContainerInfo").datagrid("loadData", {
						rows : result.containerInfos,
					});*/
					for(var n=0; n<result.containerInfos.length; n++) {
						$("#dcsContainerInfo").datagrid("appendRow", result.containerInfos[n]);
					}
				});
			}
		});
		
	}
}

//add by wqs 2012-08-22  页面不刷新初始化数据
function resetPage(){
	
	var initOrderNo = "";
	var initPayType = "全部费用托收";
	controlwordToAdd = "000000000000000";
	checkConfigOrderExpiryDate = "";
	sum = 0;
	sum20 = 0;
	sum40 = 0;
	sum45 = 0;

	afterEditSum20 = 0;
	afterEditSum40 = 0;
	afterEditSum45 = 0;

	canAddDetailAndContainer = true;
	initAdminCode = "";
	initStatus = "";
	initTransactionType = "";
	initDeliveryType = "";
	deliveryStatus = "";
	pickupcntrstatus = "";
	returncntrstatus = "";
	initHaul="";
	initExpiryDate = new Date();
	initNoExpiryDate = new Date();
	//initExpiryDateHash = new Hashtable();
	newRowNum = 1;
	firstStandbyChoose = true;
	initStandbyChooseBoxCondition = "";
	firstOnlineChoose = true;
	initOnlineChooseBoxCondition = "";
	newStandbyRow = 1;
	tempReleasedNo = "";
	isChange = false;
	tempOrderNo = "";
	tempReleasedNo = "";

    $("#dcsLogisticsOrder").form("clear");
	$("#dcsLogisticsOrder").form("setData", {
		pickupDepot :"HPCM",  
		returnDepot  :"HPCM", 
		payType : initPayType,
		businesstype :"0", 
		arrivaltype: "0"
	});
    $("#dcsLogisticsDetail").datagrid("loadData", {
		rows : []
	});
	
    $("#dcsContainerInfo").datagrid("loadData", {
		rows : []
	});  
    //初始化
	$("#fgno").focus();
	$("#inorout").combobox("setValue","I");
	$("#transactionType").combobox("readonly", false);
	$("#txport").combobox("readonly", false);
	$("#jxport").combobox("readonly", false);
	$("#standbybnt").linkbutton('enable');
	$("#onlinebnt").linkbutton('enable');
	
}

var isChange = false;
function addNewOrder() {
	var detailChange = $("#dcsLogisticsDetail").datagrid("getChanges");
	var containerChange = $("#dcsContainerInfo").datagrid("getChanges");
    
	if(detailChange.length > 0) {
		isChange = true;
	}
	if(containerChange.length > 0) {
		isChange = true;
	}
	/* //检查页面那个input修改过    by add wqs 2012-08-22
	if($("#dcsLogisticsOrder").form("dataChanged")){
		isChange = true;
	} */
	//先判断要不要先保存修改过的数据
	if(isChange) {
		//定义了全局变更所以要再设回值
		$.messager.confirm('确定框', '离开本页面编辑过的数据将丢失，确实要离开么？',function(r) {
			if(r) {
				//window.location.href = "dcsOrderEdit.jsp";
				//页面提示不再刷新
				isChange=false;
				resetPage();
			}
		});
	}else {
		//window.location.href = "dcsOrderEdit.jsp";	
		//页面提示不再刷新
		resetPage();
	}
	
}

$(function() {
	
	$("#dcsLogisticsOrder :input").change(function(){
		isChange = true;
	});
	
	$(".uppercase").keyup(function(e){
		var str = $(this).val();
		str = str.toLocaleUpperCase();
		$(this).val(str);
	});
	
	$("#fgno").focus();
	$("#inorout").combobox("setValue","I");
	$("#onlineQueryForm > table").css("width","920px");
	
	$("#cargoConsigneeCode").combogrid("options").onHidePanel = function() {
		$("#cargoConsigneeDesc").val($("#cargoConsigneeCode").combogrid("getText"));
	};
	
	$("#standbyboxtype").combogrid("options").onHidePanel = function() {
		standbyQuery();
	};
	//remark
	$("#remarkGridResult").datagrid("options").onDblClickRow = function(index,data){  
		$.fn.datagrid.defaults.onDblClickRow.apply(this, [index,data]);
		var remark=$("#remarks").val();
		if(remark==null||remark==""){
			$("#remarks").val(data.parameterValue);
		}else{
			$("#remarks").val(remark+";"+data.parameterValue);
		}
		
		remarkConfigCancel();
	};
	
	$("#transactionType").combobox("options").onHidePanel = function() {
		var transactionType = $(this).combo("getValue");
		var returnArray = deliveryTypeRull(transactionType,true);
		var initStatus = returnArray.get("initStatus");
		var pickupcntrstatus = returnArray.get("pickupcntrstatus");
		var returncntrstatus = returnArray.get("returncntrstatus");
		var initDeliveryType = returnArray.get("initDeliveryType");
		var canAddDetailAndContainer = returnArray.get("canAddDetailAndContainer");

		//设置pickupCntrStatus 和 returnCntrStatus
		$("#pickupstatus").val(pickupcntrstatus);
		$("#returnstatus").val(returncntrstatus);
		$("#deliveryType").val($("#transactionType").combobox("getText"));
		
		//根性集装箱信息的重吉状态
		if(initStatus != deliveryStatus) {
			var existRows = $("#dcsContainerInfo").datagrid("getData").rows;
			if(existRows.length > 0) {
				$.messager.confirm('确定框', '改变作业项目将会清空已选择的集装箱，确定要继续吗？',function(r) {
					if(r) {
						initStatus = deliveryStatus;
						initTransactionType = transactionType;
						$("#dcsContainerInfo").datagrid("loadData", {
							rows : []
						});
					}else {
						$("#transactionType").combobox("setValue", initTransactionType);
						return;
					}
				});
			}else {
				initStatus = deliveryStatus;
				initTransactionType = transactionType;
			};
		};
		
		$("#transactionType").combobox("readonly",true);
		var goodsName = "";
		var goodsKind = "";
		var qtyUnitDesc = "";
		if(initTransactionType=="CEN" || initTransactionType=="CEE" || initTransactionType=="CIE" || initTransactionType=="ZEE") {
			goodsName = "吉柜";
			goodsKind = "吉箱";
			qtyUnitDesc = "箱";
		}else {
			goodsKind = "一般货物";
			qtyUnitDesc = "件";
		}
		//如果有货名，则自动生成一条带货名的提单信息
		$("#dcsLogisticsDetail").datagrid("appendRow", {
			goodsDesc : goodsName,
			goodsKind : goodsKind,
			qtyUnitDesc : qtyUnitDesc
		});
		//需找默认配置
		checkConfig();
		//查找默认有效日期配置
		checkExpiryTimeLimitConfig();
	};
	
	$("#portTranshipCode").combogrid("options").onHidePanel = function() {
		$("#portTranshipDesc").val($("#portTranshipCode").combogrid("getText"));
	};
	
	$("#totarget").combogrid("options").onHidePanel = function() {
		$("#totarget").combogrid("setValue", $("#totarget").combogrid("getText"));
	};
	
	$("#input_cntrAdminCode").combogrid("options").onChange = function(value) {
		$("#input_adminCode").combogrid("setValue", value);
		$("#standbyadmincode").val(value);
	};
	
	$("#dcsLogisticsDetail").datagrid("options").onBeginEdit = function(rowIndex, rowData) {
		$.fn.datagrid.defaults.onBeginEdit.apply(this, [rowIndex, rowData]);
		$("#dcsLogisticsDetail").datagrid("getColumnEditor", "average").attr("readonly", true);
		$("#dcsLogisticsDetail").datagrid("getColumnEditor", "billNo").keyup(function(e){
			var str = $(this).val();
			str = str.toLocaleUpperCase();
			$(this).val(str);
		});
		$("#dcsLogisticsDetail").datagrid("getColumnEditor", "trainCardNo").keyup(function(e){
			var str = $(this).val();
			str = str.toLocaleUpperCase();
			$(this).val(str);
		});
		$("#dcsLogisticsDetail").datagrid("getColumnEditor", "arrangeCfsPlace").keyup(function(e){
			var str = $(this).val();
			str = str.toLocaleUpperCase();
			$(this).val(str);
		});
		$("#dcsLogisticsDetail").datagrid("getColumnEditor", "goodsDesc").combo("options").onHidePanel = function () {
			var unName = $("#dcsLogisticsDetail").datagrid("getColumnEditor", "goodsDesc").combogrid("getValue");
			BasDangerManager.getUncodeByUnnameLikeAnywhere(unName, function(result){
				$("#dcsLogisticsDetail").datagrid("getColumnEditor", "dangerCode").combogrid("setValue",result);
			});
		};
		$("#dcsLogisticsDetail").datagrid("getColumnEditor", "billNo").unbind("blur").bind("blur", function(){
			var value = $("#dcsLogisticsDetail").datagrid("getColumnEditor", "billNo")[0].value;
			var depot = $("#txport").combobox("getValue");
			var queryType = "";
			var queryOrderId = $("#orderid").val();
			if(depot != "" && depot.length >= 4) {
				queryType = "P";
				depot = depot.substring(0,4);				
			}else{
				queryType = "R";
				depot = $("#jxport").combobox("getValue");
				if(depot != "" && depot.length >=4){
					depot = depot.substring(0,4);
				}
			}
			DcsLogisticsOrderManager.queryLogisticsDetailStatus(depot,queryType,queryOrderId,value, function(result){
				if(result.hasOrder == "Y"){
					$.messager.alert("提示", "当前提单号已经存在办单记录，办单号为："+result.depotLogisticsOrderNoOther, "warning");
				}
			});
		});
	};
	
	$("#dcsContainerInfo").datagrid("options").onBeginEdit = function(rowIndex, rowData) {
		$.fn.datagrid.defaults.onBeginEdit.apply(this, [rowIndex, rowData]);
		//alert(rowData.containerType);
		$("#dcsContainerInfo").datagrid("getColumnEditor", "refrigeration").combobox("readonly", true);
		if(rowData.containerType && rowData.containerType.indexOf("RF")>=0 ) {
			$("#dcsContainerInfo").datagrid("getColumnEditor", "refrigeration").combobox("readonly", false);
		}
		//判断制冷可不可编辑
		$("#dcsContainerInfo").datagrid("getColumnEditor", "containerType").combo("options").onHidePanel = function () {
			$.fn.combogrid.defaults.onHidePanel.apply(this);
			var type = $(this).combo("getValue");
			if(type.indexOf("RF") >= 0) {//属于冷藏柜
				$("#dcsContainerInfo").datagrid("getColumnEditor", "refrigeration").combobox("readonly", false);
			}else {
				$("#dcsContainerInfo").datagrid("getColumnEditor", "refrigeration").combobox("setValue","");
				$("#dcsContainerInfo").datagrid("getColumnEditor", "refrigeration").combobox("readonly", true);
			}
			
		};

		$("#dcsContainerInfo").datagrid("getColumnEditor", "containerNo").unbind("blur").bind("blur", function() {
			var value = $("#dcsContainerInfo").datagrid("getColumnEditor", "containerNo")[0].value;
			//alert($("#dcsLogisticsDetail").datagrid("getColumnEditor", "expiryDate")[0].value);
			//$("#dcsLogisticsDetail").datagrid("getColumnEditor", "expiryDate")[0].value = initNoExpiryDate;
			var depot = $("#txport").combobox("getValue");
			if(depot != "" && depot.length >= 4) {
				depot = depot.substring(0,4);
			}else{
				depot = $("#jxport").combobox("getValue");
				if(depot != "" && depot.length >=4){
					depot = depot.substring(0,4);
				}
			}
			
			if(value!="" && value.indexOf("箱") < 0 ) {
				DcsContainerInfoManager.checkContainerNo(value, function(result){
					if(!result){
						$.messager.alert("提示", "集装箱格式错误，请检查输入的箱号！", "warning");
					}
				});
			}
			var transType = $("#transactionType").combobox("getText");
			if(value!="" && value.indexOf("箱")<0) {
				var companyCode = $("#input_cntrAdminCode").combogrid("getValue");
				//只有新增箱的时候才校验 add by DJ 0824
				var selectedRow = $("#dcsContainerInfo").datagrid("getSelected");
				var containerInfoId = selectedRow.containerInfoId;
				if(typeof(containerInfoId) =="undefined" || containerInfoId == ""){
					DcsLogisticsOrderManager.checkAdminCompany(value,depot, function(result){
						if(result!=null && result.returnCode=="success") {
							//将转码放到服务端做了   add by DJ
							var cntrAdminCode = result.cntrAdminCode;
							
							if(transType !="陆运重进" && transType !="陆运吉进" && transType!="重进重出" && transType!="吉进吉出"){
								if(cntrAdminCode != companyCode) {
									$("#dcsContainerInfo").datagrid("deleteSelectedRows");
									$.messager.alert("提示", "箱不属于当前控箱公司！", "warning");
									return;
								}else if(result.dcsLogisticsDesc == "Y") {
									$("#dcsContainerInfo").datagrid("deleteSelectedRows");
									$.messager.alert("提示", "该箱已办单！", "warning");
									return;
								}else if(result.isHold == "Y") {
									$("#dcsContainerInfo").datagrid("deleteSelectedRows");
									$.messager.alert("提示", "该箱已被扣箱！", "warning");
									return;
								}else {//初始值
	 								$("#dcsContainerInfo").datagrid("getColumnEditor", "pickupCntrStatus").combobox("setValue",result.containerStatus);
	 								$("#dcsContainerInfo").datagrid("getColumnEditor", "pickupCntrStatus").combobox("readonly", true);
	 								$("#dcsContainerInfo").datagrid("getColumnEditor", "linerCode").combogrid("setValue",result.cntrOwnerCode);
	 								$("#dcsContainerInfo").datagrid("getColumnEditor", "linerCode").combogrid("readonly", true);
									$("#dcsContainerInfo").datagrid("getColumnEditor", "containerType").combogrid("readonly", true);
									
									$("#dcsContainerInfo").datagrid("getColumnEditor", "currentPilePlace")[0].value=result.arrangeCFSPlace;
									$("#dcsContainerInfo").datagrid("getColumnEditor", "containerType").combogrid("setValue",result.containerType);							

									if(result.dcsShipDesc == "Y"){
										$.messager.alert("提示", "该箱已配船，请检查是否箱号有误！", "warning");
									}
								}
							}else{
								$.messager.alert("提示", "现场表中有此箱，与作业项目["+transType+"]不规范；请您检查输入箱号是否有误！", "warning");
								$("#dcsContainerInfo").datagrid("deleteSelectedRows");
							}						
						}else if(transType !="陆运重进" && transType !="陆运吉进" && transType!="重进重出" && transType!="吉进吉出"){//提示并删除本行
							$("#dcsContainerInfo").datagrid("deleteSelectedRows");
							$.messager.alert("提示", "箱不在现场！", "warning");
							return;
						}
					});
				}
			};
			
		});
		
		//TODO 
		//有效日期控制
		$("#dcsContainerInfo").datagrid("getColumnEditor", "expiryDate").combo("options").onHidePanel = function () {
			$.fn.combogrid.defaults.onHidePanel.apply(this);
			var value = $(this).combo("getValue");
			var containerNo = $("#dcsContainerInfo").datagrid("getColumnEditor", "containerNo")[0].value;
			
			var tempRows = $("#dcsContainerInfo").datagrid("getData").rows;
			var selectIndexRow = $("#dcsContainerInfo").datagrid("getSelectedIndex");
			if(containerNo!=""&&containerNo.indexOf("箱")<0) {//指定箱
				for(var i=0; i<tempRows.length; i++) {
					if(tempRows[i].containerNo!=null && tempRows[i].containerNo!="" && tempRows[i].containerNo.indexOf("箱")<0 && i != selectIndexRow) {
						tempRows[i].expiryDate = value;
						$("#dcsContainerInfo").datagrid("refreshRow",i);
						$("#dcsContainerInfo").datagrid("updateRow", {
							index : i,
							row : tempRows[i]
						});

					} 
				}
			}else {//非指定箱
				for(var i=0; i<tempRows.length; i++) {
					if((tempRows[i].containerNo==null || tempRows[i].containerNo=="" || tempRows[i].containerNo.indexOf("箱")>0) && i != selectIndexRow) {
						tempRows[i].expiryDate = value;
						$("#dcsContainerInfo").datagrid("refreshRow",i);
						$("#dcsContainerInfo").datagrid("updateRow", {
							index : i,
							row : tempRows[i]
						}); 
					} 
				}
			}
		};
		
		//自动转大写
		$("#dcsContainerInfo").datagrid("getColumnEditor", "containerNo").keyup(function(e){
			var str = $(this).val();
			str = str.toLocaleUpperCase();
			$(this).val(str);
		});
		$("#dcsContainerInfo").datagrid("getColumnEditor", "sealNo").keyup(function(e){
			var str = $(this).val();
			str = str.toLocaleUpperCase();
			$(this).val(str);
		});
		$("#dcsContainerInfo").datagrid("getColumnEditor", "shippingOrderNo").keyup(function(e){
			var str = $(this).val();
			str = str.toLocaleUpperCase();
			$(this).val(str);
		});

	};
	
	if(initOrderNo != "" && initOrderNo.length >0 && initOrderNo != "null") {
		loadByOrderNo(initOrderNo);
	}
	
	<%
	if(fgno!=null && !fgno.equals("")) {
	%>
	tempReleasedNo = "";
	findByThisNo("<%=fgno%>");
	<%}%>

	//控箱公司发现改变时
	$("#input_cntrAdminCode").combogrid("options").onHidePanel = function() {
		var adminCode = $("#input_cntrAdminCode").combogrid("getValue");
		if($("#input_agentCode").combogrid("getValue")=="") {
			$("#input_agentCode").combogrid("setValue", adminCode);
			$("#input_payerCode").combogrid("setValue", adminCode);
		}
		if($("#input_payerCode").combogrid("getValue")=="") {
			$("#input_payerCode").combogrid("setValue", $("#input_agentCode").combogrid("getValue"));
		}
		
		var tempRows = $("#dcsContainerInfo").datagrid("getData").rows;
		//mod by dj 0810 2330
		var transactionType = $("#transactionType").combobox("getValue");
		if(transactionType == "CIF" || transactionType =="CIE"){
			$("#return_cntrAdminCode").combogrid("setValue", adminCode);
			for(var i=0; i<tempRows.length; i++) {
				tempRows[i].cntrAdminCode = adminCode;
				tempRows[i].returnAdminCode = adminCode;
				var containerInfoId = tempRows[i].containerInfoId;
				if(containerInfoId != null && containerInfoId.length != ""){
					$("#dcsContainerInfo").datagrid("refreshRow",i);
					$("#dcsContainerInfo").datagrid("updateRow", {
						index : i,
						row : tempRows[i]
					});
				}else{
					$("#dcsContainerInfo").datagrid("refreshRow",i);
				}
			}
		}else{
			var canChange = true;
			for(var i=0; i<tempRows.length; i++) {
				if(tempRows[i].containerNo != "" && tempRows[i].containerNo.indexOf("箱") < 0 ) {
					canChange = false;
					break;
				}
			}
			if(canChange){
				 $("#return_cntrAdminCode").combogrid("setValue", adminCode);
				 for(var i=0; i<tempRows.length; i++) {
					tempRows[i].cntrAdminCode = adminCode;
					tempRows[i].returnAdminCode = adminCode;
					var containerInfoId = tempRows[i].containerInfoId;
					if(containerInfoId != null && containerInfoId.length != ""){
						$("#dcsContainerInfo").datagrid("refreshRow",i);
						$("#dcsContainerInfo").datagrid("updateRow", {
							index : i,
							row : tempRows[i]
						});
					}else{
						$("#dcsContainerInfo").datagrid("refreshRow",i);
					}
				}
			}
			
		}
		//需找默认配置
		checkConfig();
		
	};
	
	//委托人发现改变时
	$("#input_agentCode").combogrid("options").onHidePanel = function() {
		$("#input_payerCode").combogrid("setValue", $("#input_agentCode").combogrid("getValue"));
	};
	
	//提柜点发现改变时，吉出重回、吉出吉回、吉出不回，在待选箱查询到吉柜所在堆场后，返回至办单界面，修改提柜地点，柜信息栏须同步进行默认修改。
	$("#txport").combobox("options").onHidePanel = function() {
		//需找默认配置
		checkConfig();
		//查找默认有效日期配置
		checkExpiryTimeLimitConfig();
	};
	$("#jxport").combobox("options").onHidePanel = function() {
		//需找默认配置
		checkConfig();
		//查找默认有效日期配置
		checkExpiryTimeLimitConfig();
	};
	$("#businesstype").combobox("options").onHidePanel = function() {
		//查找默认有效日期配置
		checkExpiryTimeLimitConfig();
	};
	
	//还柜控箱发现改变时
	$("#return_cntrAdminCode").combogrid("options").onHidePanel = function() {
		var adminCode = $("#return_cntrAdminCode").combogrid("getValue");
		
		var tempRows = $("#dcsContainerInfo").datagrid("getData").rows;
		for(var i=0; i<tempRows.length; i++) {
			//mod by dj 0810 2330
			/* if(tempRows[i].containerNo=="" || tempRows[i].containerNo.indexOf("箱")>0) {
				tempRows[i].returnAdminCode = adminCode;
			} */
			tempRows[i].returnAdminCode = adminCode;
			var containerInfoId = tempRows[i].containerInfoId;
			if(containerInfoId != null && containerInfoId.length != ""){
				$("#dcsContainerInfo").datagrid("refreshRow",i);
				$("#dcsContainerInfo").datagrid("updateRow", {
					index : i,
					row : tempRows[i]
				});
			}else{
				$("#dcsContainerInfo").datagrid("refreshRow",i);
			}
		}

	};
	
	
});

function changeCheckboxCondition(id,checked) {
	if(checked==true) {
		$("#input_standby"+id).val("Y");
	}else {
		$("#input_standby"+id).val("");
	}
};

function changeCheckboxCondition2(id,checked) {
	if(checked==true) {
		$("#input_"+id).val("Y");
	}else {
		$("#input_"+id).val("");
	}
};

//打开备注配置
function openRemarkConfig() {
	remarkConfigQuery();
	$("#remarkConfigDiv").dialog("open");
};
//关闭备注配置
function remarkConfigCancel() {
	$("#remarkConfigDiv").dialog("close");
};
//查询备注配置
function remarkConfigQuery() {
 	/* $("#remarkGridResult").datagrid("commonQuery", {
 		queryType : "DcsParametersModel",
 		paramForm : "remarkFormQuery"
 	}); */
	DcsParametersManager.findRemarks(function(result) {
		$("#remarkGridResult").datagrid("loadData", {
			rows : result
		});
	});
};

function remarkAppendRow() {
	$("#remarkGridResult").datagrid("appendRow", {});
};
function remarkInsertRow() {
	$("#remarkGridResult").datagrid("insertRow", {
		index : $("#remarkGridResult").datagrid("getSelectedIndex"),
		row : {
			
		}
	});
};
function deleteRemarkSelectedRows() {
	$("#remarkGridResult").datagrid("deleteSelectedRows");
};
function saveRemark() {
	if (! $("#remarkGridResult").datagrid("validate")) {
		$.messager.alert("提示", "数据验证错误", "warning");
		return;
	}
	var rows = $("#remarkGridResult").datagrid("getChanges");
	if (rows.length == 0) {
		$.messager.alert("提示", "未修改数据", "warning");
		return;
	}
	DcsParametersManager.saveAll2(rows, function(result) {
		$("#remarkGridResult").datagrid("refreshSavedData", result);
		$.messager.alert("提示", "备注配置保存成功", "info");
	});
	
	//TODO  刷新remarks这个grid里面的数据
	//$("#remarks").combogrid("grid").datagrid("reload");
	
};
function reloadRemark() {
	$("#remarkGridResult").datagrid("reload");
};

function checkConfig(){
	var checkConfigDeliveryType=$("#transactionType").combobox("getValue");
	//var checkConfigBusinessType=$("#businesstype").combobox("getValue");
	var checkConfigCntrAdminCode=$("#input_cntrAdminCode").combogrid("getValue");
	var checkConfigDepot = $("#txport").combobox("getValue");
	if(checkConfigDepot != "" && checkConfigDepot.length >= 4) {
		checkConfigDepot = checkConfigDepot.substring(0,4);
	}else{
		checkConfigDepot = $("#jxport").combobox("getValue");
		if(checkConfigDepot != "" && checkConfigDepot.length >=4){
			checkConfigDepot = checkConfigDepot.substring(0,4);
		}
	}
	if(checkConfigDeliveryType.length>0&&checkConfigCntrAdminCode.length>0&&checkConfigDepot.length>0){
		DcsLogisticsConfigManager.findDcsLogisticsConfig(checkConfigDepot,checkConfigDeliveryType,checkConfigCntrAdminCode,function(result){
			/* if(result.expiryTimeLimit != ""){
				var expiryTimeLimit = new Number(result.expiryTimeLimit);
				initExpiryDate= new Date();
				initExpiryDate.setDate(initExpiryDate.getDate()+expiryTimeLimit);
			} */
			if(result.needWeigh == "是"){
				$("#weighingbox").attr("checked",true);
			}
			if(result.needHaulage == "是"){
				initHaul = "1";
			}
			if(result.businessType != null && result.businessType != ""){
				$("#businesstype").combobox("setValue", result.businessType);
			}
		});
	}
}

function checkExpiryTimeLimitConfig(){
	var checkConfigDeliveryType = $("#transactionType").combobox("getValue");
	var checkConfigBusinessType = $("#businesstype").combobox("getValue");
	var checkConfigDepot = $("#txport").combobox("getValue");
	if(checkConfigDepot != "" && checkConfigDepot.length >= 4) {
		checkConfigDepot = checkConfigDepot.substring(0,4);
	}else{
		checkConfigDepot = $("#jxport").combobox("getValue");
		if(checkConfigDepot != "" && checkConfigDepot.length >=4){
			checkConfigDepot = checkConfigDepot.substring(0,4);
		}
	}

	var getKeysExpiryTime = initExpiryDateHash.get(checkConfigDepot+"-"+checkConfigDeliveryType);
	if(typeof(getKeysExpiryTime) != "undefined" && getKeysExpiryTime != null){
		var expiryTimeLimit = new Number(getKeysExpiryTime);
		initExpiryDate = new Date();
		initExpiryDate.setDate(initExpiryDate.getDate()+expiryTimeLimit);
	}
	var getKeysNoExpiryTime = initExpiryDateHash.get(checkConfigDepot+"-"+checkConfigBusinessType);
	if(typeof(getKeysNoExpiryTime) != "undefined" && getKeysNoExpiryTime != null){
		var expiryNoTimeLimit = new Number(getKeysNoExpiryTime);
		initNoExpiryDate = new Date();
		initNoExpiryDate.setDate(initNoExpiryDate.getDate()+expiryNoTimeLimit);
	}
}

function configOrderParam(){
	var title = "编辑办单配置";
	var url = "jsp/dcs/dcsConfigEdit.jsp";
	parent.addTabs(title, url, "icon icon-nav", true);
}
if(checkConfigOrderExpiryDate.length > 0){
	$.messager.alert("提示", checkConfigOrderExpiryDate, "warning");
}
</script> 
</head>

<body class="easyui-layout" onload="hiddenColum()">
	<form id="remarkFormQuery" style="display:none;">
		<input name="parameterType" value="REMARK" type="hidden"/>
		<input name="parameterName" value="REMARK" type="hidden"/>
	</form>
	<div region="north" title="作业办单" iconCls="icon-search">
		<div class="datagrid-toolbar">
			<a class="easyui-linkbutton" onclick="getInfoByOrder()" key="G">根据单号提取</a>
			<a class="easyui-linkbutton"  onclick="addNewOrder()" key="N">办新单</a>			
<!-- 			<a href="javascript:addNewOrder()" class="easyui-linkbutton" key="N">办新单</a>			 -->	 			
			<a class="easyui-linkbutton" onclick="saveOrder()" key="S">存盘</a>
			<a class="easyui-linkbutton" onclick="syncOrder()" key="W">发送</a>
			<a class="easyui-linkbutton" onclick="printTXOrder()" btnCode="CMV38_TX_PRINT" key="O">提柜打印</a>
			<a class="easyui-linkbutton" onclick="printJXOrder()" btnCode="CMV38_JX_PRINT" key="P">还柜打印</a>
			<a class="easyui-linkbutton" onclick="TXBilling()" btnCode="CMV38_TX_JF" key="E">提柜计费</a>
			<a class="easyui-linkbutton" onclick="JXBilling()" btnCode="CMV38_JX_JF" key="R">还柜计费</a>
			<a id="fridbnt" class="easyui-linkbutton" btnCode="CMV38_FA_RFID" onclick="fridClickHandle()" key="F">发卡</a>
			<a class="easyui-linkbutton" onclick="deleteOrder()" key="D">删除</a>
			<a class="easyui-linkbutton" onclick="configOrderParam()" key="B">配置</a>
			
		</div>
		<form id="dcsLogisticsOrder" class="easyui-form" columns="4" i18nRoot="DcsLogisticsOrder">
			<input id="orderid" name="logisticsOrderId" type="hidden"/>
			<input id="controlword" name="logisticsControlWord" type="hidden"/>
			<input id="pickupstatus" name="pickupCntrStatus" type="hidden"/>
			<input id="returnstatus" name="returnCntrStatus" type="hidden"/>
			<input id="deliveryType" name="deliveryType" type="hidden"/>
			<input id="portTranshipDesc" name="portTranshipDesc" type="hidden"/>
			<input id="releasedId" name="releasedId" type="hidden"/>
			<input id="cargoConsigneeDesc" name="cargoConsigneeDesc" type="hidden"/>
			<!-- 如找到放柜信息，把所有放柜信息尽量填入现有界面，包括集装箱信息 -->
			<input id="fgno" name="orderDocumentNo" title="放柜编号" class="easyui-validatebox uppercase" validType="length[0,50]" value="<%=fgno%>" onblur="findByThisNo(this.value)"/>
			<input id="so" name="shippingOrderNo" title="SO" class="easyui-validatebox uppercase" validType="length[0,50]" value="<%=so%>"/>
			<input id="orderno" name="logisticsOrderNo" title="中心单号" class="easyui-validatebox" validType="length[0,16]"  readonly/>
			<input id="transactionType" name="transactionType" title="作业项目" class="easyui-combobox" codetype="DELIVERY_TYPE" required="true"/>
			<input id="txport" name="pickupDepot" title="提柜地点" value="HPCM" class="easyui-combobox" codetype="DEPOT_TYPE" />
			<input id="jxport" name="returnDepot" title="交柜地点" value="HPCM" class="easyui-combobox" codetype="DEPOT_TYPE" />
			<input id="portTranshipCode" name="portTranshipCode" title="中转港" class="easyui-combogrid" codetype="ALL_PORT"/>
			<input id="totarget" name="toTarget" title="流向" class="easyui-combogrid" codetype="ALL_PORT"/>
			<input id="input_cntrAdminCode" name="cntrAdminCode" title="控箱公司" class="easyui-combogrid" codetype="ALL_CNTR_ADMIN" required="true"/>
			<input id="return_cntrAdminCode" name="returnAdminCode" title="还柜控箱" class="easyui-combogrid" codetype="ALL_CNTR_ADMIN" required="true"/>
			<input id="input_agentCode" name="agentConsigneeCode" title="委托人" class="easyui-combogrid" codetype="ALL_CUSTOMER" required="true"/>
			<input id="input_payerCode" name="payerCode" title="付款人" class="easyui-combogrid" codetype="ALL_CUSTOMER" required="true"/>
			<input id="payType" name="payType" title="付款方式" value="全部费用托收" class="easyui-combobox" codetype="PAY_TYPE" required="true"/>
			
			<input id="cargoConsigneeCode" name="cargoConsigneeCode" title="收货人" class="easyui-combogrid" codetype="ALL_CUSTOMER"/>
			
			<input name="deliveryDesc" title="作业情况" class="easyui-combogrid" pageSize="50" codetype="ALL_WORKTYPES" customValuePermitted="true"/>
			<input id="customerrefno" name="customerRefNo" title ="客户委托号" class="easyui-validatebox uppercase" validType="length[0,50]"/>
			<input id="transactor" name="transactor" title="经办人" class="easyui-validatebox uppercase" validType="length[0,50]"/>
			
			<input id="businesstype" name="businesstype" title="业务类型" value="0" class="easyui-combobox" codetype="BUSINESS_TYPE" required="true"/>
			<input id="arrivaltype"  name="arrivaltype" title="到货方式" value="0" class="easyui-combobox" codetype="ARRIVAL_TYPE" required="true"/>
			<div title="箱计划数">
				20'X<input id="cun20" name="cun20" class="easyui-validatebox readonlyinput" validType="isnumber" style="width:20px;"/><!--  onblur=" checkBox(20)" -->
				40'X<input id="cun40" name="cun40" class="easyui-validatebox readonlyinput" validType="isnumber" style="width:20px;"/><!--  onblur=" checkBox(40)" -->
				45'X<input id="cun45" name="cun45" class="easyui-validatebox readonlyinput" validType="isnumber" style="width:20px;"/><!--  onblur=" checkBox(45)" -->
			</div> 
			<input name="vesselCode" title="出口船名" class="easyui-combogrid" codetype="ALL_VESSEL"/>
			<input name="voyageNo" title="航次" class="easyui-validatebox uppercase" validType="length[0,30]"/>
			<input id="plandate" name="planExportDate" title="预出口日期" class="easyui-datebox"/>
			<input name="workSpecial" title="特约事项" class="easyui-validatebox" validType="length[0,200]"/>
			<div title="提箱作业状态">
				<input type="checkbox" id="pickupLockStatus" disabled="disabled" />锁单
				<input type="checkbox" id="pickupFeeStatus" disabled="disabled" />计费
			</div>
			
			<div title="交箱作业状态">
				<input type="checkbox" id="returnLockStatus" disabled="disabled" />锁单
				<input type="checkbox" id="returnFeeStatus" disabled="disabled" />计费
 			</div>
			<!--remakrs yww -->
			<div title="过磅" colspan="2">
				<input id="weighingbox" type="checkbox"/>
				<font >备注：</font>
				<input id="remarks" name="remarks" class="easyui-combogrid" pageSize="50" sortOrder="asc" sortName="createTime" codetype="ALL_REMARK" customValuePermitted="true" validType="length[0,250]" style="width:180px"/>
				<input type="button" value="配置" onclick="openRemarkConfig()" />
				&nbsp;&nbsp;创建人:
				<input id="creator" name="creator" style="width:45px;" readonly/>
			</div>
			<input id="cmPickupWork" name="cmPickupWork" title="仓码提箱作业" readonly/>
			<input id="cmReturnWork" name="cmReturnWork" title="仓码交箱作业" readonly/>
			<input name="verifyCode" title="验证码" class="easyui-validatebox" validType="length[0,20]" readonly/>
			<input id="sendstatus" name="sendStatus" title="发送仓码状态" readonly/>
		</form>
	</div>
	<div region="center" border="false">
		<div fit="true" class="easyui-layout">
			<div region="north" iconCls="icon-edit" style="height:130px">
				<div class="datagrid-toolbar">
					<span class="panel-header panel-title" style="float: left; border-style: none; width: 70px;">提单信息</span>
					<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow('#dcsLogisticsDetail')" key="T">新增</a>
					<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows('#dcsLogisticsDetail')" key="Y">删除</a>
					<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadGrid('#dcsLogisticsDetail')" key="H">取消</a>
				</div>
				<table id="dcsLogisticsDetail" class="easyui-datagrid" fit="true" pagination="false">
					<thead>
						<tr>
							<th field="billNo" title="提单号" width="160" editor="{type:'validatebox', options:{required:true, validType:'notchinese[]'}}"/>
							<th field="goodsDesc" title="货物名称" editor="{type:'combogrid', options:{customValuePermitted:true}}" codetype="GOODS_NAME"/>
							<th field="qty" title="计划件数" editor="{type:'validatebox', options:{validType:'isnumber[]'}}"/>
							<th field="qtyUnitDesc" title="包装" editor="{type:'combogrid', options:{required:true}}" codetype="ALL_UNIT"/>
							<th field="packageType" title="包装种类" editor="{type:'combogrid'}" codetype="PACKAGE_TYPE"/>
							<th field="dangerCode" title="危品UN号" editor="{type:'combogrid'}" codetype="ALL_DANGER"/>
							<th field="goodsKind" title="计费货类" editor="{type:'combogrid', options:{required:true}}" codetype="GOODS_KIND"/>
							<th field="grossWeight" title="毛重" editor="{type:'validatebox', options:{validType:'maxLength[]'}}"/>
							<th field="volume" title="体积" editor="{type:'validatebox', options:{validType:'maxLength[]'}}"/>
							<th field="marksNumber" title="唛头" editor="text"/>
							<th field="warehouse" title="仓内/外" editor="{type:'combobox'}" codetype="WAREHOUSE_TYPE"/>
							<th field="trainCardNo" title="卡号" editor="{type:'validatebox', options:{validType:'length[0,30]'}}"/>
							<th field="average" title="平均件重" editor="text"/>
							<th field="arrangeCfsPlace" title="堆位" editor="text"/>
							<th field="remark" title="备注" editor="{type:'validatebox', options:{validType:'length[0,1000]'}}" />
							
						</tr>  
					</thead>
				</table>
			</div>
			
				
			<div region="center"  iconCls="icon-edit" >
				<div class="datagrid-toolbar">
					<span class="panel-header panel-title" style="float: left; border-style: none; width: 80px;">集装箱信息</span>
					<a class="easyui-linkbutton" iconCls="icon-add" onclick="appendRow('#dcsContainerInfo')" key="A">新增</a>
					<a id="standbybnt" class="easyui-linkbutton" iconCls="icon-redo" onclick="openStandbyChooseBox()" key="G">待选箱号</a>
					<a id="onlinebnt" class="easyui-linkbutton" iconCls="icon-redo" onclick="openOnlineChooseBox()" key="M">现场选箱</a>
					<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteSelectedRows('#dcsContainerInfo')" key="U">删除</a>
					<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadGrid('#dcsContainerInfo')" key="I">取消</a>
				</div>
				<table id="dcsContainerInfo" class="easyui-datagrid" i18nRoot="DcsContainerInfo" fit="true" pagination="false" showFooter="true">
					<thead>
						<tr>
							<th field="containerNo" title="集装箱号" editor="{type:'validatebox', options:{validType:'iscontainerno[]'}}"/><!-- DCS_CONTAINERS表CONTAINER_NO -->
							<th field="containerType" title="箱型" editor="{type:'easyui-combogrid', options:{required:true}}" codetype="ALL_CTR_TYPE" />
							<th field="sealNo" title="封号" editor="{type:'validatebox', options:{validType:'notchinese[], length[0,30]'}}"/>
							<th field="pickupCntrStatus" title="状态" editor="{type:'combobox'}" codetype="CONTAINER_STATUS"/>
							<th field="cntrAdminCode" title="控箱公司" codetype="ALL_CNTR_ADMIN"/>
							<th field="linerCode" title="箱属公司" editor="{type:'easyui-combogrid'}" codetype="ALL_LINER"/>
							<th field="shippingOrderNo" title="S/O号" width="160" editor="{type:'validatebox', options:{validType:'length[0,50]'}}"/>
							<th field="expiryDate" title="有效日期" editor="datebox" />
							<th field="returnAdminCode" title="还柜控箱" codetype="ALL_CNTR_ADMIN"/>
							<th field="exportarea" title="出口地" editor="{type:'combobox'}" codetype="EXPORTAREA"/>
							<th field="pickupDepot" title="起点" editor="{type:'combobox'}" codetype="DEPOT_TYPE"/>
							<th field="returnedDepot" title="终点" editor="{type:'combobox'}" codetype="DEPOT_TYPE"/>
							<th field="currentPilePlace" title="堆位" editor="text"/>
							<th field="haul" title="包拖" editor="{type:'combobox'}" codetype="HAUL_TYPE"/>
							<th field="refrigeration" title="制冷" editor="{type:'combobox'}" codetype="REFRIGERATION _TYPE"/>
							<th field="workRemark" title="备注" editor="{type:'validatebox', options:{validType:'length[0,200]'}}"/>
							<th field="cntrfifo" title="先进先出" editor="text" />
							<th field="containerInfoId" title="箱作业ID"/>
						</tr>
					</thead>
				</table>
				
			</div>
		</div>
	</div>
	
	<!-- 中心单号弹出窗口编辑 -->
	<div id="orderNoDiv" class="easyui-dialog" title="中心单号" iconCls="icon-edit"
			style="width:300px;padding:10px" closed="true" modal="true">
		<div region="north">
			<form id="orderInputForm" class="easyui-form" columns="1"">
				<input id="input_orderno" class="easyui-validatebox" title="中心单号" required="true"/>
			</form>
		</div>
		
		<div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" onclick="chooseOrder()">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="chooseOrderCancel()">取消</a>
		</div>
	</div>
	
	<!-- 备注配置弹出窗口编辑 -->
	<div id="remarkConfigDiv" class="easyui-dialog" title="备注配置" iconCls="icon-edit"
			style="width:420px;" closed="true" modal="true">
		<div region="north">
			<div class="datagrid-toolbar">
				<a class="easyui-linkbutton" iconCls="icon-add" onclick="remarkAppendRow()">新增</a>
				<a class="easyui-linkbutton" iconCls="icon-redo" onclick="remarkInsertRow()">插入</a>
				<a class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteRemarkSelectedRows()">删除</a>
				<a class="easyui-linkbutton" iconCls="icon-save" onclick="saveRemark()">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" onclick="reloadRemark()">取消</a>
			</div>
			<table id="remarkGridResult" class="easyui-datagrid" fit="true" pageSize="50">
				<thead>
					<tr>
						<th field="parameterValue" width="348px" title="常用备注" editor="{type:'validatebox', options:{required:true, validType:'length[0,400]'}}"/>
					</tr>
				</thead>
			</table>
		</div>
		
		<div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="remarkConfigCancel()">关闭窗口</a>
		</div>
	</div>
	
	<!-- 待选箱号弹出窗口编辑 -->
	<div id="toChooseBoxEdit" class="easyui-dialog" title="待选箱号" iconCls="icon-edit"
			style="width:900px;padding:10px" closed="true" modal="true">
		<div region="north">
			<div class="datagrid-toolbar">
				<a class="easyui-linkbutton" iconCls="icon-search" onclick="standbyQuery()">查询</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" onclick="clearStandbyQueryForm()">重置</a>
			</div>
			<form id="standbyQueryForm" class="easyui-form" columns="3" i18nRoot="BasPort">
				<input id="input_standbyisfifo" name="isFIFO" value="Y" type="hidden"/>
				<input id="input_standbycanuse" name="canUseContainer" value="Y" type="hidden"/>
				<input id="input_standbynotordered" name="notOrdered" value="Y" type="hidden"/>
				<input id="input_standbynotholed" name="notHolded" value="Y" type="hidden"/>
				<input id="input_standbynoteccsrecord" name="notECCSRecord" value="Y" type="hidden"/>
				
				<!-- <input id="local_standbylinercode" name="liner" type="hidden" /> -->
				<input id="standbydepot" name="depot" type="hidden"/>
				<input id="standbyadmincode" name="cntrAdminCode" type="hidden"/>
				<input id="standbyboxtype" name="containerType" title="箱型" class="easyui-combogrid" codetype="ALL_CTR_TYPE" required="true"/>
				<input id="liner" name="liner" title="一程公司" class="easyui-combogrid" codetype="ALL_LINER"/>
				<input id="containerNumber" name="containerNumber" title="个数" value="1" class="easyui-validatebox" validType="isnumber2"/>
				<input name="vesselName" title="进口船名" class="uppercase" operator="ilikeAnywhere"/>
				<input name="vesselNo" title="航次" class="uppercase"/>
				<input name="billLadingNo" title="进口提单号" class="uppercase"/>
				<input name="containerHeadNo" title="箱号字头" class="uppercase"/>
				<input name="landOrShip" title="陆运/船到" class="easyui-combobox" codetype="LANDORSHIP"/>
				<input id="validate" name="validDate" title="有效日期" class="easyui-datebox"/>
				<div title="其他条件" colspan="3">
					<input id="isfifo" type="checkbox" checked onclick="changeCheckboxCondition('isfifo',this.checked)"/>先进先出
					<input id="canuse" type="checkbox" checked onclick="changeCheckboxCondition('canuse',this.checked)"/>非残箱及能用残箱
					<input id="notordered" type="checkbox" checked disabled="disabled"/>未办单
					<input id="notholed" type="checkbox" checked disabled="disabled"/>非扣箱
					<input id="noteccsrecord" type="checkbox" checked onclick="changeCheckboxCondition('noteccsrecord',this.checked)"/>未有吉柜配船记录
				</div>
			</form>
		</div>
		
		<div region="center" title="" iconCls="icon-edit">
			<table id="standbyGridResult" class="easyui-datagrid" fit="true">
				<thead>
					<tr>
						<th field="arrangeCFSPlace" title="堆场"/>
						<th field="containerType" title="箱型" codetype="ALL_CTR_TYPE"/>
						<th field="notDoContainerNumber" title="未办箱数"/>
						<th field="doneContainerNumber" title="已办待选"/>
						<th field="useContainerNumber" title="可用箱数"/>
					</tr>
				</thead>
			</table>
		</div>
		<div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" onclick="chooseStandbyBox()">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="toChooseBoxNoCancel()">取消</a>
		</div>
	</div>
	
	<!--现场选箱弹出窗口编辑 -->
	<div id="chooseBoxEdit" class="easyui-dialog" title="现场选箱" iconCls="icon-edit"
			style="width:960px;padding:10px" closed="true" modal="true">
		<div region="north">
			<div class="datagrid-toolbar">
				<a class="easyui-linkbutton" iconCls="icon-search" onclick="onlineQuery()">查询</a>
				<a class="easyui-linkbutton" iconCls="icon-reload" onclick="clearOnlineQueryForm()">重置</a>
			</div>
			<form id="onlineQueryForm" class="easyui-form" columns="3" i18nRoot="BasPort">
				<input id="input_isCompleteYard" name="isCompleteYard" type="hidden"/>
				<input id="input_isFIFO" name="isFIFO" type="hidden"/>
				<input id="input_usePreferredList" name="usePreferredList" type="hidden"/>
				<input id="input_getLastUnpackDate" name="getLastUnpackDate" type="hidden"/>
				<input id="input_hasDistributionRecord" name="hasDistributionRecord" type="hidden"/>
				<input id="input_hasMechanicalWork" name="hasMechanicalWork" type="hidden"/>
				<input id="input_hasArrange" name="hasArrange" type="hidden"/>
				<input id="input_onTheSpot" name="onTheSpot" value="Y" type="hidden"/>
				
				<input id="local_admincode" name="cntrAdminCode" type="hidden" />
				<input id="local_linercode" name="liner" type="hidden" />
				<input name="maxRecord" type="hidden" value="50"/>
				<input id="onlinedepot" name="depot" type="hidden" codetype="DEPOT_TYPE"/>
				<input id="input_adminCode" name="cntrAdminCode2" title="控箱公司" class="easyui-combogrid" codetype="ALL_CUSTOMER" readonly/>
				<input id="standbyboxtype" name="containerType" title="箱型" class="easyui-combogrid" codetype="ALL_CTR_TYPE"/>
				<input id="onlinecontainerstatus" name="containerStatus" title="箱现状重吉" class="easyui-combobox" codetype="CONTAINER_STATUS"/>
				<input id="input_linercode" name="liner2" title="一程公司" class="easyui-combogrid" codetype="ALL_CUSTOMER"/>
				<input name="dcsLogisticsDesc" title="办单情况" value="未办单" class="easyui-combobox" codetype="LOGISTICS_DESC" editable="false"/>
				<input id="onlineisInYard" name="isInYard" title="场内/场外" class="easyui-combobox" codetype="IS_INYARD"/>
				<input name="isHold" title="是否扣箱" class="easyui-combobox" value="非扣箱" codetype="HOLD_CONTAINER"/>
				<input name="canUseContainer" title="是否残箱" class="easyui-combobox" codetype="CANUSE_CONTAINER"/>
				<input name="isDanger" title="是否危险品箱" class="easyui-combobox" codetype="IS_DANGER"/>
				<input name="containerLevel" title="箱体级别" class="easyui-combobox" codetype="BOX_LEVEL"/>
				<div title="进口日期" colspan="2">
					<input name="validStartDate" class="easyui-datebox"/>至
					<input name="validEndDate" class="easyui-datebox"/>
				</div>
				<input name="vesselName" title="进口船名" class="uppercase"/>
				<input name="vesselNo" title="航次" class="uppercase"/>
				<input name="billLadingNo" title="进口提单号" class="uppercase"/>
				<input id="inorout" name="inOrOut" title="进口/出口" class="easyui-combobox" codetype="INOROUT"/>
				<input name="landOrShip" title="陆运/船到" class="easyui-combobox" codetype="LANDORSHIP"/>
				<input name="tradeType" title="内/外贸" class="easyui-combobox" codetype="TRADE_TYPE"/>
				<input name="containerHead" title="箱号字头" class="uppercase"/>
				<input name="arrangeCFSPlace" title="堆位" class="uppercase"/>
				<input name="dcsLogisticsDetailNo" title="作业单号" class="uppercase"/>
				<div title="其他条件" colspan="3">
					<input id="isCompleteYard" type="checkbox" onclick="changeCheckboxCondition2('isCompleteYard',this.checked)"/>要求堆位完整
					<input id="isFIFO" type="checkbox" onclick="changeCheckboxCondition2('isFIFO',this.checked)"/>先进先出
					<input id="usePreferredList" type="checkbox" onclick="changeCheckboxCondition2('usePreferredList',this.checked)"/>使用“优先列表”
					<input id="getLastUnpackDate" type="checkbox" onclick="changeCheckboxCondition2('getLastUnpackDate',this.checked)"/>取最后拆吉日期
					<input id="hasDistributionRecord" type="checkbox" onclick="changeCheckboxCondition2('hasDistributionRecord',this.checked)"/>未有吉柜配船记录
					<input id="hasMechanicalWork" type="checkbox" onclick="changeCheckboxCondition2('hasMechanicalWork',this.checked)"/>机械作业范围
					<input id="hasArrange" type="checkbox" onclick="changeCheckboxCondition2('hasArrange',this.checked)"/>未有有效排箱单
					<input id="onthespot" type="checkbox" checked disabled="disabled"/>现场箱
				</div>
			</form>
		</div>
		
		<div region="center" title="" iconCls="icon-edit">
			<table id="onlineGridResult" class="easyui-datagrid" i18nRoot="BasPort" fit="true">
				<thead>
					<tr>
						<th field="validDate" title="进口日期"/>
						<th field="unpackDate" title="拆吉日期"/>
						<th field="containerNo" title="集装箱号"/>
						<th field="containerType" title="箱型" codetype="ALL_CTR_TYPE" required="true"/>
						<th field="validContainerStatus" title="进口箱状态"/>
						<th field="containerStatus" title="重吉"/>
						<th field="arrangeCFSPlace" title="堆位"/>
						<th field="isInYard" title="场内/外" codetype="WAREHOUSE_TYPE"/>
						<th field="cntrAdminCode" title="控箱公司" codetype="ALL_CUSTOMER"/>
						<th field="cntrOwnerCode" title="箱属公司" codetype="ALL_CUSTOMER"/>
						<th field="dcsLogisticsDesc" title="办单情况"/>
						<th field="canUseContainer" title="残箱情况"/>
						<th field="isHold" title="扣箱情况" codetype="HOLD_CONTAINER"/>
						<th field="vesselName" title="船名"/>
						<th field="vesselNo" title="航次"/>
					</tr>
				</thead>
			</table>
		</div>
		<div class="dialog-buttons">
			<a class="easyui-linkbutton" iconCls="icon-ok" onclick="chooseOnlineBox()">确定</a>
			<a class="easyui-linkbutton" iconCls="icon-cancel" onclick="chooseBoxCancel()">取消</a>
		</div>
	</div>
</body>	

</html>