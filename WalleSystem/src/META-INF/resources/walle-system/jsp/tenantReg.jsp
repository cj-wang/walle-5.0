<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">

	$(function() {
		
		function getCookie(name) {
		    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
		    if (arr != null) {
		    	return unescape(arr[2]);
		    } else {
		    	 return null;
		    }
		}
		
		var regEmail = getCookie("regEmail");
		if (regEmail) {
			$("#formTenant input[name='loginName']").val(regEmail);
		}
		
		//确定
		$("#btnOk").click(function() {
			if(! $("#formTenant").form("validate")){
				$.messager.alert("提示", "数据验证错误！", "warning");
				return;
			}
			var password = $("#formTenant input[name='password']")[0].value;
			var rePassword = $("#formTenant input[name='rePassword']")[0].value;
		    if (password != rePassword) {
		    	$.messager.alert("提示", "确认密码错误", "warning");
				return;
			}
			var tenant = $("#formTenant").form("getData");
			WlTenantManager.register(tenant, tenant, function(result) {
		    	$.messager.alert("提示", "注册成功，欢迎使用！", "info", function() {
					$("#j_username").val(tenant.loginName);
					$("#j_password").val(tenant.password);
					$("#loginForm")[0].submit();
				});
		    });
		});
		
		//取消
		$("#btnCancel").click(function() {
    		location.href = "logout.action";
		});
		
		$("#formTenant input[name='tenantName']").focus();
	});

</script>

<div class="easyui-layout" fit="true">
    
    <div region="center" border="false">
		<form id="formTenant" class="easyui-form" columns="1" style="padding:40px 70px 5px 70px;">
			<input name="tenantName" title="机构名称" class="easyui-validatebox" required="true" />
			<input name="name" title="姓名" value="系统管理员" class="easyui-validatebox" required="true" />
			<input name="loginName" title="登录名" class="easyui-validatebox" required="true" />
	        <input name="password" title="密码" type="password" class="easyui-validatebox" required="true" />
	        <input name="rePassword" title="确认密码" type="password" class="easyui-validatebox" required="true" />
		</form>
    </div>
    
    <div region="south" class="dialog-button">
		<a id="btnOk" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
		<a id="btnCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    </div>
    
    
	<form id="loginForm" method="post" action="login.action" style="display:none;">
		<input type="text" name="j_username" id="j_username"/>
		<input type="password" name="j_password" id="j_password"/>
	</form>
    
</div>

	
