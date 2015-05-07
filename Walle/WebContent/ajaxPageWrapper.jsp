<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style='width:100%;height:100%;'>
<head>
<%@include file="/common/include.jsp" %>

<title><%= request.getParameter("_title") == null ? "" : URLDecoder.decode(request.getParameter("_title"), "utf-8") %></title>
<%
	String url = request.getParameter("_ajaxPageUrl");
	if (url.startsWith(contextPath)) {
		url = url.substring(contextPath.length());
	}
	if (! url.startsWith("/")) {
		url = "/" + url;
	}
%>

<script type="text/javascript">
	requestURI = "<%= contextPath + url %>";
	
	var currentUser;
	
	var parserOnComplete = $.parser.onComplete;
	
	$.parser.onComplete = function(context) {

		WlUserManager.getCurrentUser({
			async : false,
			callback : function(result) {
				currentUser = result;
				AUTHORIZED_FUNCTIONS = currentUser.authorizedFunctions;
			}
		});

		parserOnComplete.apply(this, arguments);
	};
	
	function openWindow(parent,subtitle,url,type,options) {
		//always use body as parent
		parent = null;
		
		options = options || {};
		var onClose = options.onClose;
		delete options.onClose;
		win = $.extend({}, {
			modal : true,
			title : subtitle,
			iconCls : "myCustomerIcon_searchForm",
			onClose : function() {
				if (onClose) {
					onClose.apply(this);
				}
				$(this).dialog("destroy");
			}
		}, options);
		//type : iframe, ajax, dynamic
		if (type == "iframe") {
			win.content = createTabIframe(url);
		} else {
			if (type == "dynamic") {
				url = getDynamicPageUrl(url);
			}
			if (debugMode) {
				win.content = createTabIframe("ajaxPageWrapper.jsp?_ajaxPageUrl=" + url);
			} else {
				win.href = url;
			}
		}
		return $("<div/>").appendTo(parent || "body").dialog(win);
	}
	
	function createTabIframe(url) {
		
		var _url = url + (/\?/.test(url) ? "&" : "?") + "_=" + $.now();
		return "<iframe scrolling='auto' frameborder='0' src='" + _url + "' _src='" + url + "' style='width:100%;height:100%;'></iframe>";
	}
</script>

</head>

<body style='width:100%;height:100%;'>

	<script type="text/javascript">
		var _ready = jQuery.fn.ready;
		jQuery.fn.ready = function(fn) {
			_ready(function() {
				setTimeout(function() {
					fn.apply(document);
				}, 0);
			});
		};
		
		$(function() {
			PREVENT_REINIT_PLUGINS = true;
			setTimeout(function() {
				PREVENT_REINIT_PLUGINS = false;
			}, 0);
		});
	</script>

	<%
		out.flush();
		request.getRequestDispatcher(url).include(request, response);
	%>

	<script type="text/javascript">
		jQuery.fn.ready = _ready;
	</script>
	
</body>
</html>
