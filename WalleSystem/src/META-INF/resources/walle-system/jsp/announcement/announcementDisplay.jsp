<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.framework.core.util.ContextUtils"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="cn.walle.system.announcement.service.OaAnnouncementManager"%>
<%@page import="cn.walle.system.announcement.model.OaAnnouncementModel"%>
<%@page import="cn.walle.system.service.WlOrganizeManager"%>
<%@page import="cn.walle.system.model.WlOrganizeModel"%>
<%@page import="cn.walle.system.service.WlUserManager"%>
<%@page import="cn.walle.system.model.WlUserModel"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>

<%@include file="/common/include.jsp" %>
<html>
<% 

  String announcementId=request.getParameter("announcementId"); 
  String orgname="";
  String username="";
  String filePath="无";
  OaAnnouncementManager manager=ContextUtils.getBeanOfType(OaAnnouncementManager.class);
  
	try{
		OaAnnouncementModel model = manager.get(announcementId);
	
		if(model!=null){
			if(!"".equals(model.getAttachment())&&model.getAttachment()!=null){
				String arr[]=model.getAttachment().split("/");
				filePath=arr[arr.length-1];
			}
			
			WlOrganizeManager wlOrganizeManager = ContextUtils.getBeanOfType(WlOrganizeManager.class);
			WlOrganizeModel wlOrganizeModel = wlOrganizeManager.get(model.getOrgId());
			if(wlOrganizeModel!=null){
				orgname = wlOrganizeModel.getName();
			}
			WlUserManager userManager = ContextUtils.getBeanOfType(WlUserManager.class);
			WlUserModel usermodel = userManager.get(model.getUserId());
			if(usermodel!=null){
				username = usermodel.getName();
			}
			 String endtime="无";
			 String startime="无";
			 SimpleDateFormat sf=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss",Locale.ENGLISH); 
			if(model.getEndtime()!=null){
				endtime=model.getEndtime().toLocaleString();
			}if(model.getPublishtime()!=null){
				startime=sf.format(model.getPublishtime());
			}
			String content="";
			if(model.getContent()!=null){
				content=model.getContent();
			}
		   
		%>
<head>
<style type="text/css">
	.fujian{
	 height:16px;
	 width:16px;
	 float:left;
	 background:url('img/fujian.png') no-repeat;
	}
	.return{
	 float:left;
	 margin-top:10px;
	}

	.key{
	 color:rgb(127,127,127);
	 font-size:12px;
	}
	._button{
	 float:left;
	 margin-top:10px;
	 width:100%;
	}
	.button_link{
	 cursor: pointer;
	}
	#return{
	 height:25px;
	 width:61px;
	 
	}
</style>
<script>
var contextPath = "<%=contextPath%>";
var content='<%=content.replaceAll("\n\r", "").replaceAll("\n","")%>';
$(function(){
	
	$("#content").append("<div style='padding:20px'>"+content+"</div>");
});
 

//下载
function downLoadAttachment(){
	var uuid= "<%=model.getAnnouncementId()%>";
	
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

function feedBack(){
	var feedInfo= $("#feedInfo").val();
	if(feedInfo.length==0){
		$.messager.alert("提示", "请您填写反馈内容!", "warning");
		return;
	}
	var uuid= "<%=model.getAnnouncementId()%>";
	OaUserAnnouncementManager.feedBackSave(uuid,feedInfo,function(data){
		if(data){
			$.messager.alert("提示", "反馈成功!", "info");
		}
	});
	
}
function goBack(){
	parent.colseTabByTitle("公告信息详情");
}
</script>
</head>

<body class="easyui-layout" fit="true">

		  <div region="north" border="false" style="height:80px;padding: 20px 20px 0px 20px;">
		      
		       <div >
		           <img src='img/notice.png'style="width:16px;height: 16px"/>
		           <span style="font-size: 14px;font-weight: bold;"><%=model.getTitle() %></span> 
		           <span style="font-size: 12px;width:200px; float:right;color:#C2C2C2;">结束时间：<%=endtime%></span>
		           <span style="font-size: 12px;width:200px; float:right;color:#C2C2C2;">发布时间：<%=startime%></span>
		       
		       </div>
		       <div style="padding-left:20px;padding-top: 5px;">
		           <span style="font-size: 12px;width:120px;float:left; color:#C2C2C2;" >发布人：<%=username %></span>
		           <span style="width:150px;float:left;;font-size: 12px; color:#C2C2C2;">|&nbsp;&nbsp; 所属单位：<%=orgname %></span>
		       </div>
		       <div style="clear: both; margin-top: 20px">
		          <hr></hr>
		       </div>
		  </div>
		 
		  <div region="center"  border="false" id="content">
		     
	        
    
    
    


		    
		  </div>
		  <div region="south" border="false" style="height:160px;padding-left: 20px;padding-right: 20px">
		     
		      <div>
		           <img src='img/fujian.png'style="width:16px;height: 16px;float:left"/>
		           <span style="font-size: 14px;font-weight: bold; width: 30px;float:left">附件</span> 
		           
		      </div>
		      <hr></hr>
		      <div style="height:110px; background:rgb(238,243,250); margin-top:15px;padding: 5px 20px 20px 20px">
		        <div style="height:30px;width:350px; background-color: white; padding: 5px 10px 5px 5px">
		            <img src='img/zipfile.png'style="width:28px;height: 30px;float:left"/> 
		            <% if(filePath.equals("无")){
		            	%>
		            	 <a href="#" style="text-decoration:none !important;margin-left: 20px"><%=filePath %></a>
		            	<%
		            }else{
		            	%>
		            	 <a href="#" onclick='downLoadAttachment()' style="margin-left:10px;  text-decoration:none !important;"><%=filePath %><span style="width: 24px;color: blue; float: right;">下载</span></a>
		            	<%
		            }
		            %>
		           
		            
		        </div>
		        <div class="return">
					<div class="key">反馈内容</div>
					<input style="width:1000px;margin-top: 5px" name="feedInfo" id="feedInfo"/>
		     	</div>
		      	<div class="_button">
				 <a class="button_link"><img src="img/_return.png" alt="反馈" id="return" onclick="feedBack()"/></a>
				 <a class="button_link" style="margin-left:20px;"><img src="img/_close.png" onclick="goBack()" alt="关闭" id="close"/></a>
		     	</div>
		      </div>
		  </div>
		

		
		<%
			
		}
	}catch(Exception ex){}
%>
     <iframe id="downloadIframe" style="display: none;">
    </iframe>
</body>
</html>