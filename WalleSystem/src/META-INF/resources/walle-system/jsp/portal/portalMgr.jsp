<%@page language='java' pageEncoding='utf-8'%>
<%@page import="cn.walle.system.entity.SessionContextUserEntity"%>

<%
	String userId = SessionContextUserEntity.currentUser().getUserId();
%>

<style>
</style>
<script language="javascript">
	$(function(){
		
		$("#queryPortalForm").form('readonly','readonly');
		
		//组件查询
		$("#queryBtn").searchbox("options").searcher = function(value, name){
			 $("#portalTree").tree("commonQuery", {
	            queryFields : [{
	                fieldName : "[portletName]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            }]
	        });
		};
		//树的onselect
		   $("#portalTree").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			WlPortalPortletManager.get(node.id,function(data){
				$("#queryPortalForm").form("setData",data);
			});
			queryGrantGrid(node.id);
			queryUnGrantGrid(node.id);
			
			
		};
		//查询授权角色
		function queryGrantGrid(portletId){
			var param = [{
		          "fieldName": "portletId",
		          "fieldValue": portletId
		      }];
			$("#hadGivenRoleGrid").datagrid("setQueryFields", param);
			$("#hadGivenRoleGrid").datagrid("commonQuery", {
				queryType : "HavePortalRoleQuery"
	        });
		}
		//查询未授权角色
		function queryUnGrantGrid(portletId){
			var param = [{
		          "fieldName": "portletId",
		          "fieldValue": portletId
		      }];
			$("#notGivenRoleGrid").datagrid("setQueryFields", param);
			$("#notGivenRoleGrid").datagrid("commonQuery", {
				queryType : "NoPortalRoleQuery"
	        });
		}
		$("#addRoleBtn").click(function(){
			
			var selections = $("#notGivenRoleGrid").datagrid("getSelections");
			var roleIds=new Array();
			$(selections).each(function(i,item){
				roleIds.push(item.roleId);
			});
			var portletId=$("#queryPortalForm").form("getData").portletId;
			WlPortalRolePortletManager.savePortletRoles(portletId,selections,function(){
				$("#notGivenRoleGrid").datagrid("deleteSelectedRows");
				$("#notGivenRoleGrid").datagrid("acceptChanges");
				queryGrantGrid(portletId);
				//queryUnGrantGrid(portletId);
			});
			
			
		});
		$("#delRoleBtn").click(function(){
			
			var selections=$("#hadGivenRoleGrid").datagrid("getSelections");
			var roleIds=new Array();
			$(selections).each(function(i,item){
				roleIds.push(item.roleId);
			});
			var portletId=$("#queryPortalForm").form("getData").portletId;
			WlPortalRolePortletManager.removePortletRoles(portletId,selections,function(){
				$("#hadGivenRoleGrid").datagrid("deleteSelectedRows");
				$("#hadGivenRoleGrid").datagrid("acceptChanges");
				//queryGrantGrid(portletId);
				queryUnGrantGrid(portletId);
			});
			
		});
		
		//新增
		$('#newBtn').click(function(){
			
			$("#portalDialogForm").form("clear");
			$("#portalDialog").dialog("open");
			
		});
		
		
		//编辑
		$('#editBtn').click(function(){
			
			$("#portalDialogForm").form("clear");
			
			var node=$("#portalTree").tree("getSelected");
			if(node){
				$("#portalDialogForm").form("setData",node.attributes.data);
				
			}else{
				$.messager.alert("提示","请先选择要编辑的组件！","warning");
			}
			
			$("#portalDialog").dialog("open");
			
		});
		
		//保存
		$('#saveBtn').click(function(){
			if(! $("#portalDialogForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}else{
				var formData = $("#portalDialogForm").form("getData");
				
				WlPortalPortletManager.save(formData,function(data){
					$.messager.alert("提示","保存成功!");
					$("#portalTree").tree("reload");
					$("#portalDialog").dialog("close");
				});
			}
			
		});
		
		$('#cancelBtn').click(function(){
			
			$("#portalDialog").dialog("close");
			
		});
		$('#delBtn').click(function(){
			
			
			var node=$("#portalTree").tree("getSelected");
			if(node){
				$.messager.confirm("提示","是否删除该组件？",function(deleteFlag){
					if(deleteFlag){
						
						WlPortalPortletManager.disablePortlet(node.id,function(data){
							
							$.messager.alert("提示","删除成功!");
							$("#portalTree").tree("reload");
							
						});
					}
					
				});
				
				
			}else{
				$.messager.alert("提示","请先选择要删除的组件！","warning");
			}
			
			
			
		});
		
		
	});
</script>
<div class="easyui-layout" fit="true" border="false">
		<!-- portal列表 -->
		<div region="west" style="width:255px;">
		    <div class="datagrid-toolbar">
		    	 <span class="panel-header panel-title"  style="float: left; border-style: none; width: 50px;">首页组件</span>
		    	 <a id="newBtn" class="easyui-linkbutton"  iconCls="icon-add" >新增</a>
				 <a id="delBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
				 <a id="editBtn" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
		    </div>
			 <div id="dlg-toolbar" style="padding:2px 0">  
		        <table cellpadding="0" cellspacing="0" style="width:100%">  
		            <tr>  
		                <td style="padding-left:2px"> 
					      <input id="queryBtn"  class="easyui-searchbox" prompt='组件名称' style="width:200px" /> 
		                </td>   
		            </tr>  
		        </table>  
		    </div> 
			<ul id="portalTree" class="easyui-tree" queryType="WlPortalPortletModel" queryFields="[{fieldName:'status',fieldValue:'U'}]"
				idField="portletId" textfield="portletName" animate="false" parentField=""></ul>
		</div>
		<!-- 组件信息 -->
		<div region="center" title="组件信息 " class="easyui-layout" border="false">
			<div region="north" border="true"> 
				<div id="queryPortalForm" class="easyui-form"  columns="4" i18nRoot="queryUsersFormSYS" border="false" fit="true" >
			
			        <input type="hidden" name="portletId"/>
				    <input name="portletName" title="组件名称" />
					<input name="title" title="组件标题" />
					<input name="type" title="组件类型" />
					<input name="seq" title="顺序号" />
					<input name="status" title="状态"  class="easyui-combobox" codetype='user_status' />
					<input name="height" title="组件高度" />
					<input id="portal_show_new" name="defaultDisplay" title="默认显示"class="easyui-combobox" codetype='DISPLAY' />
				    <input name="screenshot" title="缩略图"/>
					<input name="thirdpartyPortlet" title="第三方插件" class="easyui-combobox" codeType="yesNo"/> 
					<input name="columnIndex" title="归属列"  class="easyui-combobox" codeType="COLUMN_INDEX"/>
					<input name="src" title="组件地址"  style="width:300px;" colspan="2"/>
					<textarea name="description" title="描述" colspan="4"  style="resize:none" ></textarea> 
					
				</div>
			</div>
			
		
			
			
			<!-- 用户列表 -->
			<div region="center" border="false"  class="easyui-layout">
					<div region="west" style="width:320px;" border="true">
							<table id="hadGivenRoleGrid"  title="已授权角色" queryType="HavePortalRoleQuery" iconCls="icon-reload"  class="easyui-datagrid" checkOnSelect="true"  fit="true" i18nRoot="hadGivenRoleGridSYS" border="false" >
								<thead>
									<tr >
										<th field="roleName"  style="width:200px;"></th>
									</tr>
								</thead>
							</table>
					</div>
					<div region="center" border="true">
						<div class="easyui-panel" style="margin-top: 100px;" align="center" border="false">
								<a type="button" id="delRoleBtn"  class="easyui-linkbutton" iconCls="icon_right">解除</a>
								<br>
								<br>
								<a type="button" id="addRoleBtn"  class="easyui-linkbutton" iconCls="icon_left">授权</a>
						</div>
					</div>
					<div region="east"  style="width:320px;" border="true">
							<table id="notGivenRoleGrid" title="可授权角色"  queryType="NoPortalRoleQuery" iconCls="icon-reload"  class="easyui-datagrid" fit="true" checkOnSelect="true" i18nRoot="notGivenRoleGridSYS" border="fasle">
								<thead>
									<tr>
										<th field="roleName"  style="width:200px;"></th>
									</tr>
								</thead>
							</table>
					</div>
				</div>
			</div>
			
			
			
				<!-- 组件信息窗口 -->
			<div id="portalDialog" class="easyui-dialog" title="组件信息" iconCls="icon-reload" style="width:800px;"  modal="true" resizable="false" closable="false" closed="true">
			    <form id="portalDialogForm" class="easyui-form" columns="3" i18nRoot="sysOrgInfoDialogFormSYS">
					<input type="hidden" name="portletId"/>
				    <input name="portletName" title="组件名称" />
					<input name="title" title="组件标题" />
					<input name="type" title="组件类型" />
					<input name="seq" title="顺序号" />
					<input  name="status" title="状态"  class="easyui-combobox" codetype='user_status'  />
					<input name="height" title="组件高度" />
					<input  name="defaultDisplay" title="默认显示" class="easyui-combobox" codetype='DISPLAY' />
					<input name="screenshot" title="缩略图" />
					<input name="thirdpartyPortlet" title="第三方插件" class="easyui-combobox" codeType="yesNo" /> 
					<input name="columnIndex" title="归属列" class="easyui-combobox" codeType="COLUMN_INDEX" />
					<input name="src" title="组件地址"  style="width:300px;" colspan="2"/>
					<textarea name="description" title="描述" colspan="3"  style="resize:none" ></textarea> 
				</form>
			    <div class="dialog-buttons">
						<a class="easyui-linkbutton" iconCls="icon-ok" id="saveBtn">保存</a>
						<a class="easyui-linkbutton" iconCls="icon-cancel" id="cancelBtn">取消</a>
				</div>		
			</div>
	</div>