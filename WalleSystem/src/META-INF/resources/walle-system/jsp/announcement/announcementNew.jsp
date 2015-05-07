<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>CKEditor</title>

	<script type="text/javascript" src="../../../ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="../../../ckfinder/ckfinder.js"></script>
    <script type="text/javascript" src="../../../ckfinder/enhancingForms.js"></script>
    <script type="text/javascript" src="../../../ckeditor/dialogDefinition.js"></script>
   
</head>
<%@include file="/common/include.jsp" %>
<%
        String loginName = SessionContextUserEntity.currentUser().getUserModel().getLoginName();
        if("Admin".equals(loginName)){
        	request.getSession().setAttribute("CKFinder_UserRole", "Admin");
        }else{
        	request.getSession().setAttribute("CKFinder_UserRole", "RegUser");
        }

%>
<% 
  String announcementId=request.getParameter("announcementId"); 
%>
<script type="text/javascript">
var isEdit=false;
var editor =""; 
var announcementId="<%=announcementId%>";
 window.onload = function(){
	 DWREngine.setAsync(false);
	 editor = CKEDITOR.replace("content",{
		height:345
	});
	editor.on('instanceReady', function(event){
		//editor.getCommand('maximize').exec();
		editor.initialed = true;
	});
    CKFinder.setupCKEditor( editor, contextPath+'/ckfinder/' );
	//编辑
	if(announcementId!=""&&announcementId!="null"&&announcementId!=null){
		
		initForm(announcementId);
		isEdit=true;
	}
	DWREngine.setAsync(true);
	
}
 
 function initForm(announcementId){
	 
	 OaAnnouncementManager.get(announcementId,function(data){
			if(data!=null&&data.exception){
				return;
			}
			else{
				$("#formAnnouncement").form("setData",data);
				editor.setData(data.content);
			}
	 });
 }
 
 
 function saveAnnouncement(){
	 if (! $("#formAnnouncement").form("validate")) {
			$.messager.alert("提示", "数据验证错误", "warning");
			return;
	 }
	 var formData = $("#formAnnouncement").form("getData");
	 if(isEdit){
		 formData.announcementId=announcementId;
	 }
	 var content= editor.getData();
	 formData.content= content
	 OaAnnouncementManager.saveAnnouncement(formData,function(){
		 $.messager.alert("提示", "保存成功!", "info");
		 // refreshAnnouncementGrid();
		 parent.refreshTabByTitle("公告发布");
		 parent.colseTabByTitle("公告信息录入");
		 parent.colseTabByTitle("公告信息编辑");
	 });
	 
 }
 function cancleEvent(){
	 $("#formAnnouncement").form("clear");
	 editor.setData("");
 }
 

		
	</script>
<body class="easyui-layout" fit="true">


      <div region="north"  style="height: 200px"  border="false" >
      <div class="datagrid-toolbar">
         <span class="panel-header panel-title" style="float: left; border-style: none; width: 230px;">公告信息(在文本编辑器中编辑公告的内容)</span>
		<a onclick="saveAnnouncement()" style="margin-left:750px" class="easyui-linkbutton" iconCls="icon-save">保存送审批</a>
		<a onclick="cancleEvent()"  class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	  </div>
         <form id="formAnnouncement" class="easyui-form wp-config" columns="3" i18nRoot="OaAnnouncementModel">
            <input name="title" title="标题" class="easyui-validatebox" style="width: 500px;" required="true" validType='length[0,20]' tooltip="标题不能超过20字"/>
           <!--  <input name="publishtime" title="发布时间"  class="easyui-datebox" class="easyui-validatebox"  required="true" validType='minLength[0]'/> -->
          
            <input name="endtime" title="失效时间"  class="easyui-datebox" class="easyui-validatebox"  required="true" validType='minLength[0]'/>
              <input name="isEmergent"  style="width: 70px;" title="紧急程度"class="easyui-combobox" codetype="isEmergent" class="easyui-validatebox" required="true" validType='minLength[0]'/>
           <div>
            <input name="attachment" id="attachment"  title="附件"  style="width: 400px;" disabled="disabled"/> 
            <a onclick='BrowseServer("attachment")'  class="easyui-linkbutton" >浏览服务器</a>
            </div>
         </form>
         
     </div>
     <div region="center" fit="true"  border="false" >
       <textarea name="content" rows="30" cols="50" ></textarea>
     </div>
  
</body>
</html>