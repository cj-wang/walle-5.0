<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>
<%@include file="/common/include.jsp" %>
<%
   String userId=SessionContextUserEntity.currentUser().getUserId();
%>
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


<script type="text/javascript">

	$(function() {
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
			
			//查看详情
	 		parent.colseTabByTitle("公告信息详情");
	        var selected = $("#oa_announcement_grid").datagrid("endEdit").datagrid("getSelected");
			if (selected) {
				parent.addTab("公告信息详情", "walle-system/jsp/announcement/announcementDisplay.jsp?announcementId=" + selected.announcementId, "icon icon-nav", true, "iframe");
				//更改阅读状态
				changeReadFlag(selected.announcementId);
			}else{
				$.messager.alert("提示", "请选择要查看的公告！", "error");
			}
			
		};
		 $("#oa_announcement_grid").datagrid("getColumnOption", "title").formatter=function(value,rowData,rowIndex){
			 if(value!=null&&value.length>22){
				 value =value.substring(0,22)+"...";
			 }
			 return "<a href='#' onclick=viewAnnouncementDetail(\""+rowData.announcementId+"\") title='查看详细信息'>"+value+"</a>";
		 };
		 
		 $("#oa_announcement_grid").datagrid("getColumnOption","attachment").formatter=function(value,rowData,rowIndex){
			 
			 
			 if(value!=null&&""!=value){
				 
				var arr =value.split("/");
				 return "<a href='#' title='"+value+"' class='redlink' style='color:#4D4D4D;'  onclick=downLoadAttachment(\""+rowData.announcementId+"\")>"+arr[arr.length-1]+"</a>";
			 }else{
				 return "无附件";
			 }
			 
		 };
		 
	
		
		 
	 $("#oa_announcement_grid").datagrid("getColumnOption","publishtime").formatter=function(value,rowData,rowIndex){
			 
			 
			 if(value!=null&&""!=value){
				 
				 return value;
			 }else{
				 return "该公告未发布";
			 }
			 
	 };
	 
	 //已读状态
	 $("#oa_announcement_grid").datagrid("getColumnOption","readFlag").formatter=function(value,rowData,rowIndex){
		 
		 
		 if(value!=null&&""!=value){
			 
			 if(value=="1"){
				 return "<img src='img/wd1.png'style='width:14px;height:12px;'></img>";
			 }else{
				 return "<img src='img/yd1.png'style='width:14px;height:12px;'></img>";
			 }
		 }
     };
		 
  });
	
	//更改阅读状态
	function changeReadFlag(uuid){
		OaAnnouncementManager.changeReadFlag(uuid,function(data){
			if(data){
				$("#oa_announcement_grid").datagrid("load");
			}
		});
	}
	
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
	function refreshAnnouncementGrid(){
		$("#oa_announcement_grid").datagrid("reload");
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
	    	<input name="title"  style="width:400px"/>
	        <div colspan="2">
				<input name="startime" title="发布时间" class="easyui-datebox"/> -
				<input name="endtime"  class="easyui-datebox" />
		    </div>
		    <input name="readFlag" title="阅读状态" class="easyui-combobox" codetype="readFlag" />
	
		</form>
	</div>
	
	<div region="center"  title="公告信息" iconCls="icon-edit" border="false">
			  <table id="oa_announcement_grid" class="easyui-datagrid wp-config" checkbox="false" fitColumns="true" fit="true" nowrap="false" pageSize="5" pageList="[5,10,15,20]"
					queryType="UserAnnouncementQuery"  queryFields="[{fieldName:'userid',fieldStringValue:'<%=userId%>'}]"paramForm="formQuery" i18nRoot="OaAnnouncementModel">
				<thead>
					<tr>
					    <th field="readFlag" codetype="readFlag" title="阅读状态" align="center"/>
				    	<th field="title" width="300" />
					    <th field="userId" codetype="orgUserId"/>
					    <th field="orgId" codetype="org"/>
						<th field="attachment"/>
						<th field="addtime" title ="创建时间"  width="130" />
						<th field="publishtime"  width="130"/>
						<th field="endtime" />
					</tr>
				</thead>
			 </table>
	</div>
	 <iframe id="downloadIframe" style="display: none;">
    </iframe>
</body>
</html>