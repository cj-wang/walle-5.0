<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="cn.walle.framework.common.service.CommonQueryManager"%>
<%@page import="cn.walle.framework.common.support.QueryField"%>
<%@page import="cn.walle.framework.common.support.QueryInfo"%>
<%@page import="cn.walle.framework.core.util.ContextUtils"%>
<%@page import="cn.walle.platform.model.WpDynamicPageModel"%>
<%@page import="cn.walle.platform.model.WpDynamicPageReleaseModel"%>

<%
	String staticHtml = null;
	String html = null;
	String js = null;

	String url = request.getParameter("_dynamicPageUrl");
	boolean devMode = "true".equals(request.getParameter("_dev"));

	CommonQueryManager commonQueryManager = ContextUtils.getBeanOfType(CommonQueryManager.class);
	if (devMode) {
		List<WpDynamicPageModel> list = commonQueryManager.query(new QueryInfo(WpDynamicPageModel.class,
				new QueryField(WpDynamicPageModel.FieldNames.url, url)))
				.getDataList(WpDynamicPageModel.class);
		for (WpDynamicPageModel model : list) {
			if ("static".equals(model.getContentType())) {
				staticHtml = model.getContent();
			} else if ("html".equals(model.getContentType())) {
				html = model.getContent();
			} else if ("js".equals(model.getContentType())) {
				js = model.getContent();
			}
		}
	} else {
		List<WpDynamicPageReleaseModel> list = commonQueryManager.query(new QueryInfo(WpDynamicPageReleaseModel.class,
				new QueryField(WpDynamicPageReleaseModel.FieldNames.url, url)))
				.getDataList(WpDynamicPageReleaseModel.class);
		for (WpDynamicPageReleaseModel model : list) {
			if ("static".equals(model.getContentType())) {
				staticHtml = model.getContent();
			} else if ("html".equals(model.getContentType())) {
				html = model.getContent();
			} else if ("js".equals(model.getContentType())) {
				js = model.getContent();
			}
		}
	}
%>

<script type="text/javascript">

(function() {
	
<%
	for (String parameterName : request.getParameterMap().keySet()) {
		String parameterValue = request.getParameter(parameterName);
%>
	var <%=parameterName%> = "<%=parameterValue%>";
<%
	}
%>

<%= js == null ? "" : js %>

})();

</script>

<%
	if (devMode && request.getRequestURI().indexOf("ajaxPageWrapper.jsp") < 0) {
%>
<button title='动态页面配置' onclick="openDynamicPageConfig('<%= url %>')" style='position:absolute; z-index:100; right:40px;'>动态页面配置</button>
<%
	}
%>


<%
	if (staticHtml != null && staticHtml.trim().length() > 0) {
		long now = System.currentTimeMillis();
%>
		<iframe id="staticHtmlIFrame_<%=now %>" style="width:100%; height:100%; border:0px;"></iframe>
		<script id="staticHtmlContent_<%=now %>" type="text/template">
			<html>
				<head>
					<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ckeditor/contents.css" />
				</head>
				<body class="cke_editable">
					<%=staticHtml %>
				</body>
			</html>
		</script>
		<script type="text/javascript">
			var doc = document.getElementById("staticHtmlIFrame_<%=now %>").contentWindow.document;
			doc.open();
			doc.write(document.getElementById("staticHtmlContent_<%=now %>").innerHTML);
			doc.close();
		</script>
<%
	}
%>



<%= html == null ? "" : html %>



