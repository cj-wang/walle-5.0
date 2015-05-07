<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">

	$(function() {
		WlUserManager.get(currentUser.userId, function(result) {
			$("#formEdituser").form("setData", result);
		});
		
		//用户信息确定
		$("#btnEdituserOk").click(function() {
		    WlUserManager.save($("#formEdituser").form("getData"), function(result) {
		    	$.messager.toast("提示", "保存成功", "info");
				currentUser.fullname = result.name;
				currentUser.userModel = result;
		    	$("#btnEdituserCancel").click();
		    });
		});
		
		//用户信息取消
		$("#btnEdituserCancel").click(function() {
			$(this).closest(".window-body").dialog("close");
		});
	});

</script>

<div class="easyui-layout" fit="true">
    
    <div region="center" border="false">
		<form id="formEdituser" class="easyui-form" columns="1" style="padding:20px 70px 20px 70px;">
			<input name="loginName" title="登录名" readonly="readonly"/>
			<input name="name" title="姓名"/>
			<input name="email" title="EMAIL"/>
			<input name="officeTel" title="电话"/>
			<input name="mobileTele" title="手机"/>
			<input name="qq" title="即时通讯"/>
		</form>
    </div>
    
    <div region="south" border="false">
		<div class="dialog-button">
			<a id="btnEdituserOk" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
			<a id="btnEdituserCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
    </div>
    
</div>

	
