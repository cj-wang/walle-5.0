<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>
<%@page import="cn.walle.system.service.WlOrganizeManager"%>
<%@page import="cn.walle.framework.core.util.ContextUtils"%>
<%@page import="cn.walle.system.model.WlOrganizeModel"%>

<html>
<head>
<%
	String username = SessionContextUserEntity.currentUser().getUserModel().getName();
	String orgid = String.valueOf(SessionContextUserEntity.currentUser().getUserModel().getOrganizeId());
	//获得登陆用户的组织机构名称
	String orgname = "";
	WlOrganizeManager wlOrganizeManager = ContextUtils.getBeanOfType(WlOrganizeManager.class);
	try{
		WlOrganizeModel wlOrganizeModel = wlOrganizeManager.get(orgid);
		if(wlOrganizeModel!=null){
			orgname = wlOrganizeModel.getName();
		}
	}catch(Exception ex){}
%>
<%@include file="/common/include.jsp" %>

<!-- portal -->

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-easyui-portal/portal.css">
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/locale/easyui-lang-<%= LocaleContextHolder.getLocale() %>.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-portal/jquery.portal.js"></script>

<style type="text/css">
</style>
<script>
var contextPath = "<%=contextPath%>";
$(function(){
	$(document).bind("selectstart",function(){return false;});
	$('#pp').portal({
		border:false,
		fit:true
	});
	$('#pp').portal("options").onStateChange=function(){
		$.fn.portal.defaults.onStateChange.apply(this);
		savePortalPosition();
	};
	createPortal();
});
//初始化组件
function createPortal(){
	
	WlPortalMyportletManager.queryMyPortal(function(data){
		
		 if(data.length > 0){
			for(var i=0; i < data.length; i++){
				var path = "";
				if(data[i].src.indexOf("http") == -1){
					path = contextPath + "/" + data[i].src;
				}else{
					path = data[i].src;
				}
				var p = $('<div/>').appendTo('body');
				p.attr("id",data[i].myportletId);
				p.panel({
					title:data[i].title,
					content:'<div id="body_'+data[i].myportletId+'" class="walle_window_null" style="width:100%;height:100%;"><iframe src="' + path + '" style="border:0px;width:100%;height:100%" frameborder="0"></iframe></div>',
					height:data[i].height==null?auto:data[i].height,
					closable:true,
					collapsible:true,
					onBeforeClose:removePortal
				});
				$('#pp').portal('add', {
					panel:p,
					columnIndex:data[i].columnIndex
				});
			}
			$('#pp').portal('resize');
		} 
	});

}




//添加组件
function addPortal(){
	 
	parent.addTab("添加组件", "walle-system/jsp/portal/portalList.jsp", null, false, "ajax");
	 
}
//保存设置
function savePortalPosition(){
	var portalInf = [];
	for(var i=0;i<3;i++){
		var obs = $.fn.portal.methods.getPanels($('#pp'),'' + i);
		for(var j=0;j<obs.length;j++){
			portalInf.push({"myportletId":obs[j].attr("id"),"columnIndex":i,"seq":((j+1)*(i+1))});
		}
	}
	if(portalInf.length == 0) return;
	var param = {"portalInfs":portalInf};
	WlPortalMyportletManager.updatePortalInf(portalInf,function(){
		//$.messager.alert("提示","设置成功！","info");
	});
}

//删除组件
function removePortal(){
	var myPortletId = $(this).attr("id");
	WlPortalMyportletManager.removeByPk(myPortletId,function(){
		
	});
}

</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" >
	<div class="datagrid-toolbar">
		<a id="btnAppend"  onclick="addPortal()"  style="margin-left:1050px; " class="easyui-linkbutton" iconCls="icon-add">添加组件</a>
		<!-- <a id="btnGsave"   onclick="savePortalPosition()" class="easyui-linkbutton" iconCls="icon-save">保存设置</a> -->
	</div>
	</div>
	<div region="center" border="false">
		<div id="pp" style="position:relative">
			<div style="width:40%;">
				
			</div>
			<div style="width:30%;">
				
			</div>
			<div style="width:30%;">
				
			</div>
		</div>
	</div>
	
</body>
</html>