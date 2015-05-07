<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>
<%@include file="/common/include.jsp" %>
<html>
<head>
<style>
	div.datagrid-body tr{
		height:70px;
	} 
	a.redlink:link{
    color:#4D4D4D !important;
   /*  text-decoration:none !important; */
	}
	a.redlink:hover{
		/* text-decoration:none !important; */
	}
</style>

<%
String userId=SessionContextUserEntity.currentUser().getUserId();
%>

<script type="text/javascript">

	$(function() {
		
		var userId="<%=userId%>";
		$("#oa_announcement_grid").datagrid("load");
		 $("#oa_announcement_grid").datagrid({frozenColumns : {}})
		//查询
		$("#btnQuery").click(function() {
			$("#oa_announcement_grid").datagrid("load");
		});
		
		//重置
		$("#btnReset").click(function() {
			$("#formQuery").form("clear");
		});
		
	

		//grid双击
		$("#oa_announcement_grid").datagrid("options").onDblClickRow = function(rowIndex, rowData) {
			$.fn.datagrid.defaults.onDblClickRow.apply(this, [rowIndex, rowData]);
			$("#btnExamine").click();
		};
		 $("#oa_announcement_grid").datagrid("getColumnOption", "title").formatter=function(value,rowData,rowIndex){
			 if(value!=null&&value.length>22){
				 value =value.substring(0,22)+"...";
			 }
			 return "<a href='#' onclick=viewAnnouncementDetail(\""+rowData.announcementId+"\") title='查看详细信息'>"+value+"</a>";
		 };
	/* 	 $("#oa_announcement_grid").datagrid("getColumnOption", "content").formatter=function(value,rowData,rowIndex){
			 if(value!=null&&value.length>96){
					return "<a href='#' title='"+value+"' class='redlink' style='color:#4D4D4D;'>"+value.substring(0,90)+"...</a>";
				}else{
					return value;
				}
		 }  */
		 
		 $("#oa_announcement_grid").datagrid("getColumnOption", "suggestion").formatter=function(value,rowData,rowIndex){
			
			 if(rowData.examineStatus=="1"){
				 return "该公告待审核";
			 }
			 else{
				 if(value!=null&&value.length>18){
					 value =value.substring(0,15)+"...";
					 return "<a href='#' onclick=viewAnnouncementSuggestionDetail(\""+rowData.announcementId+"\") title='查看建议详情'>"+value+"</a>";
				 }else{
					 return "<a href='#' title='查看建议详情'style='text-decoration:none !important; cursor: default;'>"+value+"</a>";
				 }
			   }
		};
		//取消
		$("#btnReload").click(function() {
			$("#oa_announcement_grid").datagrid("reload");
		});
		 
		 $("#oa_announcement_grid").datagrid("getColumnOption","attachment").formatter=function(value,rowData,rowIndex){
			 
			 
			 if(value!=null&&""!=value){
				 
				var arr =value.split("/");
				
				 
				 return "<a href='#' title='"+value+"' class='redlink' style='color:#4D4D4D;'  onclick=downLoadAttachment(\""+rowData.announcementId+"\")>"+arr[arr.length-1]+"</a>";
			 }else{
				 return "无附件";
			 }
			 
		 };
	   
		 $("#btnExamine").click(function (){
			 
			 var selected = $("#oa_announcement_grid").datagrid("endEdit").datagrid("getSelected");
			 if (selected) {
				 if(selected.examineStatus=="3"||selected.examineStatus=="2"){
					 $.messager.alert("提示", "该公告已审核！不能在次审核", "warning");
						return;
				 }
				    
				    $("#checker").combobox("setValue",userId);
				    $("#isEmergent").combobox("setValue",selected.isEmergent);
                  	$("#examineDialog").dialog("open");				
				}else{
					$.messager.alert("提示", "请选择要审核的公告！", "warning");
				}
		 });
		 
		 
		 $("#okBtn").click(function(){
			 if (! $("#examineForm").form("validate")) {
					$.messager.alert("提示", "数据验证错误", "warning");
					return;
			 }
			$.messager.confirm("慎重！", "确认审核通过该公告？", function(b) {
				if (b) {
					 var selected = $("#oa_announcement_grid").datagrid("endEdit").datagrid("getSelected");
			    	 $("#examineForm").form("load",{"announcementId":selected.announcementId,"examineStatus":"3"});
					 var form = $("#examineForm").form("getData");
					 OaAnnouncementManager.saveExamineAnnouncement(form,function(){
						 $.messager.alert("提示", "操作成功!", "info");
						 $("#oa_announcement_grid").datagrid("load");
						 $("#cancleBtn").click();
					 });
				}
			});
			
		 });
		 
	     $("#noBtn").click(function(){
	    	 if (! $("#examineForm").form("validate")) {
					$.messager.alert("提示", "数据验证错误", "warning");
					return;
			 }
	 		$.messager.confirm("慎重！", "确认审核不通过该公告？", function(b) {
				if (b) {
					 var selected = $("#oa_announcement_grid").datagrid("endEdit").datagrid("getSelected");
			    	 $("#examineForm").form("load",{"announcementId":selected.announcementId,"examineStatus":"2"});
			    	 var form = $("#examineForm").form("getData");
					 OaAnnouncementManager.saveExamineAnnouncement(form,function(){
						 $.messager.alert("提示", "操作成功!", "info");
						 $("#oa_announcement_grid").datagrid("load");
						  $("#cancleBtn").click();
					 });
				}
	 		});
			 
		 });
		 
	     $("#cancleBtn").click(function(){
			 
	    	 $("#examineDialog").dialog("close");
		 });
		 
		 //详情
		 $("#btnDisplay").click(function(){
			 parent.colseTabByTitle("公告信息详情");
			 var selected = $("#oa_announcement_grid").datagrid("endEdit").datagrid("getSelected");
				if (selected) {
					parent.addTab("公告信息详情", "walle-system/jsp/announcement/announcementDisplay.jsp?announcementId=" + selected.announcementId, "icon icon-nav", true, "iframe");
				}else{
					$.messager.alert("提示", "请选择要查看的公告！", "error");
				}
			 
		 }); 
	     
		 
   /*   $("#oa_announcement_grid").datagrid("getColumnOption","checker").formatter=function(value,rowData,rowIndex){
			 
			 
			 if(value!=null&&""!=value){
				 
				 return value;
			 }else{
				 return "该公告待审核";
			 }
			 
		 }; */
	
		
		 
	 $("#oa_announcement_grid").datagrid("getColumnOption","publishtime").formatter=function(value,rowData,rowIndex){
			 
			 
			 if(value!=null&&""!=value){
				 
				 return value;
			 }else{
				 return "该公告未发布";
			 }
			 
	     };
	     
	});
	
	//下载
	function downLoadAttachment(uuid){
		
		
		if(uuid!=""){
			OaAnnouncementManager.ishasFile(uuid,function(data){
				if(data){
				    var jsonParam = {serviceName:"oaAnnouncementManager", methodName:"downloadFile", parameters:{uuid:uuid}};
				 	$("#downloadIframe")[0].src = contextPath + "/JsonFacadeServlet?json_parameters=" + $.toJSON(jsonParam);
				}
			});
		}else{
			$.messager.alert("提示", "请选择要下载的文件！", "error");
		}
		
	};
	
	//内容详情
	function viewAnnouncementDetail(announcementId){
		OaAnnouncementManager.get(announcementId,function(data){
			if(data!=null&&data.exception){
				return;
			}
			if(data==""||data=="null"||data==null){
				data.content = "<center><font color='red'>无详细内容</font></center>";
			}
			var dialogId = 'dialog'+announcementId;
			var win = document.getElementById(dialogId);
		  	if(win!=null ){
					$('#'+dialogId).remove();
		  	}
		  	var myDiv = $('<div id="'+dialogId+'" class="easyui-window" collapsible="false" minimizable="false" maximizable="false" style="padding:10px" ></div>').appendTo('body');
			$(data.content).appendTo(myDiv);
		  	$('#'+dialogId).dialog({  
				    title: data.title+"详细内容",  
				    width: 600,  
				    height: 400,  
				    closed: true,  
				    cache: false,  
				    modal: true  
				});
		  	$('#'+dialogId).dialog('open');
			
		});
		
		 
	 }
	
	function viewAnnouncementSuggestionDetail(announcementId){
		  
		OaAnnouncementManager.get(announcementId,function(data){
			
			var dialogId = 'dialog_suggestion'+announcementId;
			var win = document.getElementById(dialogId);
		  	if(win!=null ){
					$('#'+dialogId).remove();
		  	}
		  	var myDiv = $('<div id="'+dialogId+'" class="easyui-window" collapsible="false" minimizable="false" maximizable="false" style="padding:10px" ></div>').appendTo('body');
			$('<div>'+data.suggestion+'</div>').appendTo(myDiv);
		  	$('#'+dialogId).dialog({  
				    title: "该公告审核建议",  
				    width: 400,  
				    height: 200,  
				    closed: true,  
				    cache: false,  
				    modal: true  
				});
		  	$('#'+dialogId).dialog('open');
			
		
			
		});
		
	}
	
	
</script>
</head>
<body class="easyui-layout" fit="true">
	
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<a id="btnQuery" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a id="btnReset" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
		</div>
		<form id="formQuery" class="easyui-form wp-config" columns="4" i18nRoot="OaAnnouncementModel">
		    <input name="title" operator="likeAnywhere" style="width:400px"/>
	        <input name="userId" title="用户" class="easyui-combotree" stateField="nodeState" codetype="orgUserId" />
	        <div colspan="2">
				<input name="addtime"  class="easyui-datebox" operator="dateBegin"/> -
				<input name="addtime"  class="easyui-datebox" operator="dateEnd"/>
		    </div>
		    <input name="examineStatus" class="easyui-combobox" codetype="examineStatus" />
	
		</form>
	</div>
	
	<div region="center"  title="审核公告列表" iconCls="icon-edit" border="false">
	 <div class="easyui-layout" fit="true">
		 <div region="north" border="false">
		 <div class="datagrid-toolbar">
				<a id="btnExamine" class="easyui-linkbutton" iconCls="icon_shengcheng">审核</a>
				<a id="btnDisplay" class="easyui-linkbutton" iconCls="icon_shengcheng">详情</a>
				<a id="btnReload" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
			</div>
		 </div>
		 <div region="center" border="false" ><!-- queryFields="[{fieldName:'examineStatus',fieldStringValue:'1'}]" -->
			  <table id="oa_announcement_grid" class="easyui-datagrid wp-config" checkbox="false" fitColumns="true" fit="true" nowrap="false" pageSize="5" pageList="[5,10,15,20]"
					queryType="OaAnnouncementModel" paramForm="formQuery"   i18nRoot="OaAnnouncementModel" orderby="examineStatus,isEmergent desc">
				<thead>
					<tr >
				    	<th field="title" width="200" />
					    <th field="userId" codetype="orgUserId"/>
					    <th field="orgId" codetype="org"/>
					    <th field="isEmergent" codetype="isEmergent" title="紧急程度" />
						<th field="examineStatus" codetype="examineStatus" />
						<th field="attachment"/>
						<th field="addtime" title ="创建时间"  width="130" />
						<th field="publishtime" width="130"/>
						<th field="endtime"/>
						<th field="suggestion"width="190"/>
						<th field="checker" codetype="orgUserId"/>
					
					</tr>
				</thead>
			 </table>
		 </div>
	 </div>
	</div>
	 <iframe id="downloadIframe" style="display: none;">
    </iframe>
    <!-- s审核dialog -->
    <div id="examineDialog" class="easyui-dialog" title="审核填写" iconCls="icon-edit" style="width:420px;" closed="true" modal="true">
		<form id="examineForm" class="easyui-form" columns="1" i18nRoot="OaAnnouncementModel">
			<input name="announcementId" id="announcementId" type="hidden"/>
			<textarea name="suggestion" cols="52" rows="5" class="easyui-validatebox" required="true" validType="length[0,300]"></textarea>
			<div>
				<input name="checker" id="checker"   readonly="readonly"  class="easyui-combobox" codetype="orgUserId"class="eayui-validatebox"  required="true" validType='minLength[0]'/>紧急程度：
				<input name="isEmergent" id="isEmergent" class="easyui-combobox" codetype="isEmergent"class="eayui-validatebox"  required="true" validType='minLength[0]' style="width:70px"/>
			</div>
			<input name="examineStatus" id="examineStatus" type="hidden"/>
		</form>
		<div class="dialog-buttons">
			<a id="okBtn" class="easyui-linkbutton" iconCls="icon-ok">通过</a>
			<a id="noBtn" class="easyui-linkbutton" iconCls="icon-cancel">不通过</a>
			<a id="cancleBtn" class="easyui-linkbutton" iconCls="icon-redo">取消</a>
		</div>
	</div>
</body>
</html>