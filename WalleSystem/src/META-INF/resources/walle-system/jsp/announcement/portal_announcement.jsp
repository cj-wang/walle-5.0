<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.framework.core.util.ContextUtils"%>
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
   String userId=SessionContextUserEntity.currentUser().getUserId();
%>
<head>
<style type="text/css">
</style>
<script>



$(function(){
	var userId="<%=userId%>";
	init(userId);
	
});
function init(userId){
	OaAnnouncementManager.getUserAnnouncement(userId,function(data){
		var flag=false;
		$(data).each(function(i,item){
			flag=true;
			var title=item.title;
			var publishtime=item.publishtime;
			
			var img="wd.png";
			var color="#2A526C";
			if(title!=null&&title.length>15){
				title =title.substring(0,13)+"...";
			}if(publishtime!=null&&publishtime!=""){
				publishtime =publishtime.split(" ")[0];
			}if(item.readFlag!=null&&item.readFlag!=""){
				//未读且加急
				if(item.isEmergent=="1"&&item.readFlag=="1"){
					img="jj.gif";
				}
				//已读
				else if(item.readFlag=="2"){
					img="yd.png";
					color="#848484";
				}
			}
			
			var announcementDiv="<div id='announcementDiv'><img src='img/"+img+"'style='width:11px;height: 12px'/><span onclick='btnDisplay(\""+item.announcementId+"\")' style='cursor: pointer;margin-left:3px;width:150px; height:20px;word-spacing:10px;font-size:14px;color:"+color+"'title='"+item.title+"'>"+title+"</span><span style='font-size:14px;color:"+color+";width:80px;float:right;'title='"+item.publishtime+"'>"+publishtime+"</span><br></div>";
			$(announcementDiv).appendTo($("#content"));
		});
		if(flag){
			var moreDiv= "<div   onclick='openMoreAnnouncement()' style='width:60px;height:16px;float:right;margin-top:10px;cursor:pointer;text-decoration: underline;color:blue'>更多>>></div>"
			$(moreDiv).appendTo($("#content"));
		}
		
	});
}
function btnDisplay(uuid){
	 parent.parent.colseTabByTitle("公告信息详情");
	 parent.parent.addTab("公告信息详情", "walle-system/jsp/announcement/announcementDisplay.jsp?announcementId="+uuid, "icon icon-nav", true, "iframe");
	 changeReadFlag(uuid);
}
//更改阅读状态
function changeReadFlag(uuid){
	OaAnnouncementManager.changeReadFlag(uuid,function(data){
		if(data){
			$("#content").html("");
			init();
		}
	});
}
//打开更多的公告
function openMoreAnnouncement(){
	parent.parent.colseTabByTitle("用户公告列表");
	 parent.parent.addTab("用户公告列表", "walle-system/jsp/announcement/announcementUser.jsp","icon icon-nav", true, "iframe");
	 
}
</script>
</head>

<body class="easyui-layout" fit="true">

		 
		  <div region="center"  border="false" id="content" style='padding:10px'>
		     
               
    


		    
		  </div>
</body>
</html>