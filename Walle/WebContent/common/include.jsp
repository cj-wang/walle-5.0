<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>

<%
	String contextPath = request.getContextPath();
	String locale = LocaleContextHolder.getLocale().toString();
	if (locale.equalsIgnoreCase("zh") || locale.equalsIgnoreCase("cn")
			|| locale.equalsIgnoreCase("zh_HANS_CN")) {
		locale = "zh_CN";
	}
	if (! locale.equalsIgnoreCase("zh_CN")) {
		locale = "en";
	}
%>

<script type="text/javascript">
	var contextPath = "<%=contextPath%>";
	var locale = "<%=locale%>";
	var requestURI = "<%= request.getRequestURI() %>";
	var DWR_SERVICE_PATH = contextPath + '/dwr';

	var debugMode = <%=request.getParameter("_debug") != null%>;
	var devMode = <%=request.getParameter("_dev") != null%>;
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.tooltip.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/themes-1.3.2/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/walle-easyui/easyui.walle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/walle-easyui/js/walle-easyui/easyui.walle-1.3.2-fix.css" />

<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/dwr-2.0.5/dwr_engine.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/dwr-2.0.5/dwr_util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/dwr_interfaces.js"></script>

<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-1.7.1/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jshashtable-2.1_src.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.iframe-post-form.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.numberformatter-1.2.4.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.tooltip.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.placeholder.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.prettydate/jquery.prettydate.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-plugins/jquery.prettydate/i18n/jquery.prettydate-<%=locale%>.js"></script>

<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/jquery.easyui-1.2.4.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-easyui-1.2.4/locale/easyui-lang-<%=locale%>.js"></script>

<script type="text/javascript" src="<%=contextPath%>/select_code_definitions_<%=locale%>.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/combogridOptions.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/walle-easyui/jquery.easyui.walle.js"></script>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/walle-easyui/locale/easyui.walle-lang-<%=locale%>.js"></script>
<script type="text/javascript" src="<%=contextPath%>/i18n_<%=locale%>.js"></script>

<script type="text/javascript" src="<%=contextPath%>/walle-platform/js/walle-platform.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/enterAsTab.js"></script>

