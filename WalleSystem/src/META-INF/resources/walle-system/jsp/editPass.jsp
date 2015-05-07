<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">

	$(function() {
		//修改密码确定
		$("#btnEditpassOk").click(function() {
			var oldPass = $("#formEditpass input[name='oldPass']")[0].value;
			var newPass = $("#formEditpass input[name='newPass']")[0].value;
			var newPass2 = $("#formEditpass input[name='newPass2']")[0].value;
		    if ($.trim(oldPass) == "") {
		    	$.messager.alert("提示", "请输入原密码", "warning");
				return;
			}
		    if ($.trim(newPass) == "") {
		    	$.messager.alert("提示", "请输入新密码", "warning");
				return;
			}
		    if (newPass != newPass2) {
		    	$.messager.alert("提示", "确认新密码错误", "warning");
				return;
			}
		    WlUserManager.changePassword(oldPass, newPass, function() {
		    	$.messager.toast("提示", "密码修改成功", "info");
		    	$("#btnEditpassCancel").click();
		    });
		});
		
		//修改密码取消
		$("#btnEditpassCancel").click(function() {
			$(this).closest(".window-body").dialog("close");
		});
	});

</script>

<div class="easyui-layout" fit="true">
    
    <div region="center" border="false">
	    <form id="formEditpass" class="easyui-form" columns="1" style="padding:20px 70px 20px 70px;">
	        <input name="oldPass" title="原密码" type="password"/>
	        <input name="newPass" title="新密码" type="password"/>
	        <input name="newPass2" title="确认新密码" type="password"/>
		</form>
    </div>
    
    <div region="south" border="false">
		<div class="dialog-button">
			<a id="btnEditpassOk" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="btnEditpassCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
    </div>
    
</div>

	
