<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Walle</title>

<!-- Making the page work with Google Chrome Frame -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">

<%@include file="/common/include.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/index.css"/>
<script type="text/javascript" src="<%=contextPath%>/js/index.js"></script>

<script type="text/javascript">
	$(function() {
		addTab("首页", "walle-system/jsp/portal/portal.jsp", null, false, "iframe");
	});
</script>

</head>

<body class="easyui-layout">
	
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt="抱歉，请开启脚本支持！" />
		</div>
	</noscript>
	
    <div region="north" split="false" border="false" style="overflow: hidden; height: 25px; background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:left; padding-left:10px; font-size: 16px; "><img src="images/blocks.gif" width="20" height="20" align="absmiddle" />Walle 应用开发平台</span>
        <span style="float:right; padding-right:20px;" class="head">
        	欢迎,
        	<a href="#" id="btnEdituser"><%= SessionContextUserEntity.currentUser().getFullname() %></a>.
        	<a href="#" id="btnEditpass">修改密码</a>
			<a href="logout.action" id="loginOut">安全退出</a>
			主题
			<select id="cb-theme" onChange="onChangeTheme(this.value);">
				<option value="default" selected>Default</option>
				<option value="gray" >Gray</option>
				<option value="black" >Black</option>
				<option value="bootstrap" >Bootstrap</option>
				<option value="metro" >Metro</option>
			</select>
		</span>
    </div>
    <div id="navPanel" region="west" hide="true" split="true" title="导航菜单" style="width:180px;">
		<div id="nav" fit="true" border="false" animate="false">
		</div>
    </div>
    <div id="mainPanel" region="center">
        <div id="maintabs" class="easyui-tabs" fit="true" border="false">
		</div>
    </div>
    
	<div id="mm" class="easyui-menu" style="width:170px;display:none;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-newwindow">在新窗口中打开</div>
		<div class="menu-sep"></div>
		<div id="mm-cancel">取消</div>
	</div>

</body>
</html>