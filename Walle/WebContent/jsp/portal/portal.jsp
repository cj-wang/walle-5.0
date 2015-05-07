<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="org.springframework.context.i18n.LocaleContextHolder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%
		String contextPath = request.getContextPath();
	%>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/themes-1.3.2/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/walle-easyui/easyui.walle.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-easyui-portal/portal.css">
	<style type="text/css">
		.title{
			font-size:16px;
			font-weight:bold;
			padding:20px 10px;
			background:#eee;
			overflow:hidden;
			border-bottom:1px solid #ccc;
		}
	</style>
	<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-1.7.1/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/jquery.easyui-1.2.4.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/locale/easyui-lang-<%= LocaleContextHolder.getLocale() %>.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-portal/jquery.portal.js"></script>
	<script type="text/javascript">
		var panels = [
			{id:'p1',title:'快速导航',height:250,collapsible:true,href:'p1.jsp'},
			{id:'p2',title:'Clock',href:'p2.html'},
			{id:'p3',title:'PropertyGrid',height:250,collapsible:true,closable:true,href:'p3.html'},
			{id:'p4',title:'DataGrid',height:250,closable:true,href:'p4.html'},
			{id:'p5',title:'Searching',href:'p5.html'},
			{id:'p6',title:'Report',href:'p6.html'},
			{id:'p7',title:'Dashboard',href:'p7.html'}
		];
		function getCookie(name){
			var cookies = document.cookie.split(';');
			if (!cookies.length) return '';
			for(var i=0; i<cookies.length; i++){
				var pair = cookies[i].split('=');
				if ($.trim(pair[0]) == name){
					return $.trim(pair[1]);
				}
			}
			return '';
		}
		function getPanelOptions(id){
			for(var i=0; i<panels.length; i++){
				if (panels[i].id == id){
					return panels[i];
				}
			}
			return undefined;
		}
		function getPortalState(){
			var aa = [];
			for(var columnIndex=0; columnIndex<3; columnIndex++){
				var cc = [];
				var panels = $('#pp').portal('getPanels', columnIndex);
				for(var i=0; i<panels.length; i++){
					cc.push(panels[i].attr('id'));
				}
				aa.push(cc.join(','));
			}
			return aa.join(':');
		}
		function addPanels(portalState){
			var columns = portalState.split(':');
			for(var columnIndex=0; columnIndex<columns.length; columnIndex++){
				var cc = columns[columnIndex].split(',');
				for(var j=0; j<cc.length; j++){
					var options = getPanelOptions(cc[j]);
					if (options){
						var p = $('<div/>').attr('id',options.id).appendTo('body');
						p.panel(options);
						$('#pp').portal('add',{
							panel:p,
							columnIndex:columnIndex
						});
					}
				}
			}
			
		}
		
		$(function(){
			$('#pp').portal({
				fit:true,
				border:false,
				onStateChange:function(){
					var state = getPortalState();
					var date = new Date();
					date.setTime(date.getTime() + 24*3600*1000);
					document.cookie = 'portal-state='+state+';expires='+date.toGMTString();
				}
			});
			var state = getCookie('portal-state');
			if (!state || state.length != 20){
				state = 'p1,p2:p3,p4:p5,p6,p7';	// the default portal state
			}
			addPanels(state);
			$('#pp').portal('resize');
		});
	</script>
</head>

<body class="easyui-layout">
	<div region="center" border="false">
		<div id="pp" style="position:relative">
			<div style="width:30%;">
			</div>
			<div style="width:40%;">
			</div>
			<div style="width:30%;">
			</div>
		</div>
	</div>
</body>
</html>