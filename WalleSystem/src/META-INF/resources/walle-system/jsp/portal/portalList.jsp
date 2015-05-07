<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>
<%
String contextPath = request.getContextPath();
			String userId=SessionContextUserEntity.currentUser().getUserId();
%>
 <style type="text/css">  
 .on { 
border:1px outset;
background:url('walle-system/jsp/portal/css/icons/qiyong.png') no-repeat;
}

.off {
border:1px outset;
background:url('walle-system/jsp/portal/css/icons/jinyong.png') no-repeat;
}
 
 </style>  
<script type="text/javascript">

$(function() {	
	var contextPath = "<%=contextPath%>";
	  $('#tt').datagrid({  
         view: $.extend({}, $.fn.datagrid.defaults.view, {  
     	    renderRow: function(target, fields, frozen, rowIndex, rowData){  
    	    	DWREngine.setAsync(false);
    	        var cc = [];  
    	        cc.push('<td colspan=' + fields.length + ' style="padding:10px 5px;border:0; width:50%">');  
    	        if (!frozen){  
    	            var img = rowData.screenshot;  
    	            cc.push('<img src="'+contextPath+'/'+img+'" style="width:15%;float:left">');  
    	            cc.push('<div style="float:left;margin-left:20px; width:75%">');  
    	            for(var i=0; i<fields.length; i++){  
    	                var copts = $(target).datagrid('getColumnOption', fields[i]); 
    	                 
    	                if(copts.title!="操作"){
    	                	 var value="";
    	                	if(rowData[fields[i]]==null||rowData[fields[i]]==""){
    	                		value="无"
    	                	}else if(rowData[fields[i]]!=null&&rowData[fields[i]].length>100){
    	                		 value =rowData[fields[i]].substring(0,100)+"...";
    	                	}else{
    	                		value= rowData[fields[i]];
    	                	} 
    	                	cc.push('<p><span class="c-label" >' + copts.title + ':</span> ' +value+ '</p>');
    	                	
    	                }else{
    	                	 WlPortalMyportletManager.isHasPortal(rowData.portletId,function(data){
    	         	        	if(data[rowData.portletId]){
    	         	        		cc.push("<button   id='btn'  class='on' style='width:100px;  border:0;height:45px;' onclick='togglePortalstyle(this,\""+rowData.portletId+"\")'/>");
    	         	        	}else{
    	         	        		cc.push("<button   id='btn'  class='off' style='width:100px;  border:0;height:45px;' onclick='togglePortalstyle(this,\""+rowData.portletId+"\")'/>");
    	         	        	} 
    	         	        	
    	         	        }); 
    	                }
    	            }  
    	            cc.push('</div>');  
    	        }  
    	        cc.push('</td>'); 
    	       /*  WlPortalMyportletManager.isHasPortal(rowData.portletId,function(data){
    	        	if(data[rowData.portletId]){
    	        		cc.push("<td colspan='2' style='width:50%;' ><button   id='btn'  class='on' style='width:100px;  border:0;height:45px;' onclick='togglePortalstyle(this,\""+rowData.portletId+"\")'/></td>");
    	        	}else{
    	        		cc.push("<td colspan='2' style='width:50%;' ><button   id='btn'  class='off' style='width:100px;  border:0;height:45px;' onclick='togglePortalstyle(this,\""+rowData.portletId+"\")'/></td>");
    	        	} 
    	        	
    	        }); */
    	        
    	        return cc.join('');  
    	        DWREngine.setAsync(true);
    	    }  
        	
    	}),
         frozenColumns : [{}],
         rownumbers:false
      }); 
	 var fields= $('#tt').datagrid("getColumnFields");
	
	 $(fields).each(function(i,item){
	 	$('#tt').datagrid("hideColumn",item);
	 });
	
	
    /*$("#tt").datagrid("getColumnOption", "opertion").formatter = function(value, rowData, rowIndex){
		
		console.log(rowData);
	} */
});  
	
function togglePortalstyle(el,id){
	
	var userId="<%=userId%>";
   if(el.className == "on") {
    	
    	WlPortalMyportletManager.deleUsrportalModel(id,userId,function(){
    		$.messager.alert("提示", "禁用用成功,刷新首页生效！", "info");
    		el.className="off";
    		//refreshTabByTitle("首页");
    	});
    	
    } else {
    	
    	WlPortalMyportletManager.savePortletModel(id,userId,function(data){
    		$.messager.alert("提示", "启用成功,刷新首页生效！", "info");
    		el.className="on";
    		//refreshTabByTitle("首页");
    	});
    	
    }
	
   }
</script>
<div class="easyui-layout" fit="true" border="false">
<div region="center"  border="false">
		<table id="tt"  class="easyui-datagrid wp-config" fit="true" queryType="GetMyPrivPortletsQuery"  queryFields="[{fieldName:'userId',fieldValue:'<%=userId %>'}]" i18nRoot="WlPortalPortletModel" singleSelect="true" fitColumns="true" remoteSort="false"  
		            pagination="false">  
		            
		            
		            <thead>
						<tr>
						    <th field="portletName"/>
							<th field="title" />
							<th field="description"/>
							<th field="type"/>
							<th field="src"/>
							<th field="btn"/>
					</tr>
					</thead>
		</table>
</div>
</div>