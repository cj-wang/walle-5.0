<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!--LoginPage-->

<title>Walle Login</title>

<!-- Making the page work with Google Chrome Frame -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">

<%@include file="/ChromeFrame/CFInstall.jsp" %>

<%
	String contextPath = request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/login.css"/>
<script type="text/javascript" src="<%=contextPath%>/walle-easyui/js/jquery-1.7.1/jquery-1.7.1.min.js"></script>

<script type="text/javascript">
	$(function(){
		$("#j_username").focus();
		
		$("#language").change(function(){
			//window.location.href="<%=contextPath%>/changeLanguage.do?language="+$(this).val();
		});
		
		$("#vCode").click(function(){
			window.location.reload();
		});
		
		$("#button").unbind("click").click(function(){
			var j_username = $("#j_username").val();
			if(j_username==null||j_username.length<1){
				alert("请输入用户名!");
				return;
			}
			var j_password = $("#j_password").val();
			if(j_password==null||j_password.length<1){
				alert("请输入密码!");
				return;
			}
			$("#form")[0].submit();
		});
	});
</script>
</head>

<body>
	<form id="form" name="form" method="post" action="login.action">
		<div class="login">
			<div class="loginDiv">
				<div class="sysLogo"></div>
				<div class="loginTop"></div>
				<div class="loginBottom">
					<table width="80%" border="0" cellspacing="0" cellpadding="0"
						align="center">
						<tr>
							<th>
								登录名:
							</th>
							<td>
									<label>
										<input type="text" name="j_username" id="j_username"/>
									</label>
							</td>
						</tr>
						<tr>
							<th>
								密码:
							</th>
							<td>
								<label>
									<input type="password" name="j_password" id="j_password"/>
								</label>
							</td>
						</tr>
						<tr>
							<th>
								语言:
							</th>
							<td>
								<label>
									<select id="language" name="language">
										<%
											String language = (String)session.getAttribute("language");
											if(language == null){
												language = java.util.Locale.getDefault().getLanguage();
											}
										 %>
										<option value="zh" <%if("zh".equals(language)){out.print("selected");} %>>中文</option>
										<option value="en" <%if("en".equals(language)){out.print("selected");} %>>English</option>
									</select>
								</label>
							</td>
						</tr>
						<tr>
							<th>
								验证码:
							</th>
							<td>
								<input id="j_validation_code" type="text" name="j_validation_code" disabled="true"/>&nbsp;
								<img src="<%=contextPath %>/ImageValidationCodeServlet" alt="请输入验证码" width="64" height="25" id="vCode"/>
							</td>
						</tr>
						<tr>
							<td>
							</td>
							<td>
								<label>
									<input type="button" name="button" id="button" value="登 录" class="iput_bnt" />
								</label>
								&nbsp;&nbsp;
								<input type="checkbox" name="_spring_security_remember_me"/>
								自动登录
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<br/>
	<div align="center">
		<p>请使用您的姓名全拼小写作为用户名和密码尝试登陆，如登陆成功，请修改默认密码；如登陆失败，请联系 <a href=mailto:wcj3570@gmail.com>wcj3570@gmail.com</a> 新建账户</p>
<!-- 		<p>浏览器下载：<a href="http://www.firefox.com.cn/download/">Firefox</a>&nbsp;&nbsp;||&nbsp;&nbsp;<a href="http://www.google.com/chrome/intl/zh-CN/landing_chrome.html?brand=CHTF&utm_campaign=zh_cn&utm_source=zh_cn-ha-apac-zh_cn-sk&utm_medium=ha">Chrome</a></p> -->
	</div>
	<%
		String errorInfo = request.getParameter("msg");
		String msg = null;
		if("UsernameNotFoundException".equals(errorInfo)){
			msg = "用户名不存在!";
		}else if("BadCredentialsException".equals(errorInfo)){
			msg = "密码输入错误!";
		}else if("ValidationCodeErrorException".equals(errorInfo)){
			msg = "验证码输入错误!";
		}
		if (msg == null && "true".equals(request.getParameter("error"))) {
			msg = "登录失败!";
		}
		if(msg != null){
			out.println("<script language=\"javascript\" type=\"text/javascript\">");
			out.println("alert(\""+msg+"\");");
			out.println("</script>");
		}
	%>
	
	<iframe src="pub/pre_load.jsp" width="0" height="0" frameborder="0"></iframe>
	
</body>
</html>