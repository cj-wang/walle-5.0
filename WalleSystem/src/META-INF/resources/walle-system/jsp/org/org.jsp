<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<script type="text/javascript">
	$(function(){
		//组织列表
		$("#orgTree").tree({
				onContextMenu:function(e,node){
					e.preventDefault(),
					$(this).tree("select",node.target);
					$("#treeActionMenu").menu('show',{
						left:e.pageX,
						top:e.pageY
					});
				},
				onClick:function(node){
					//form 表单刷新
					WlOrganizeManager.get(node.id,function(data){
						if(data.exception){
							$.messager.alert("提示","获取组织信息失败！","warning");return;
						}else{
							$("#sysOrgInfoForm").form("load",data);
						}
					});
					
			     	 //权限用户GRID
					$("#orgUserGrid").datagrid("setQueryFields",[{
						fieldName : "organizeId",
						fieldValue :node.id
					}]);
					$("#orgUserGrid").datagrid("commonQuery",{
						queryType:"UserInfoByOrgInfoQuery"
					});
				}
		});
		
		//组织查询
		$("#queryBtn").searchbox("options").searcher = function(value, name){
			 $("#orgTree").tree("commonQuery", {
	            queryFields : [{
	                fieldName : "[name,manage]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            },{
	            	fieldName: "state",
	            	fieldValue: "U"
	            }]
	        });
		};
		
		//折叠
		$("#collapseBtn").click(function(){
			$("#orgTree").tree("collapseAll");
		});
		
		//展开
		$("#expandBtn").click(function(){
			$("#orgTree").tree("expandAll");
		});
		
		//新增
		$("#addBtn").click(function(){
			var node = $("#orgTree").tree("getSelected");
			$("#sysOrgInfoDialogForm").form("clear");
			$("#diaPreOrgId").combotree('setValue',node.id);
			$("#orgInfoDialog").dialog("open");
		});
		
		//插入
		$("#insertBtn").click(function(){
			var node = $("#orgTree").tree("getSelected");
			var preNode = $("#orgTree").tree("getParent",node.target);
			$("#sysOrgInfoDialogForm").form("clear");
			$("#diaPreOrgId").combotree('setValue',preNode.id);
			$("#orgInfoDialog").dialog("open");
		});
		
		//编辑
		$("#editBtn").click(function(){
			var node = $("#orgTree").tree("getSelected");
			WlOrganizeManager.get(node.id,function(data){
				if(data.exception){
					$.messager.alert("提示","获取组织信息失败！","warning");return;
				}else{
					$("#sysOrgInfoDialogForm").form("load",data);
					$("#orgInfoDialog").dialog("open");
				}
			});
		});
		
		//删除
		$("#deleteBtn").click(function(){
			$.messager.confirm("提示","是否删除所选择的组织？",function(deleteFlag){
				if(deleteFlag){
					var node = $("#orgTree").tree("getSelected");
					WlOrganizeManager.delByPk(node.id,function(){
						$.messager.alert("提示","删除成功！");
						$("#orgTree").tree("reload");
					});
				}
			});
		});
		
		//新增保存
		$("#saveOrgInfoBtn").click(function(){
			if(! $("#sysOrgInfoDialogForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}else{
				var orgInfo = $("#sysOrgInfoDialogForm").form("getData");
				$("#saveOrgInfoBtn").attr('disabled',true);
				WlOrganizeManager.saveModel(orgInfo,function(){
					$("#orgInfoDialog").dialog("close");
					$("#orgTree").tree("reload");
					$.messager.alert("提示","保存成功!");
					$("#saveOrgInfoBtn").attr('disabled',false);
				});
			};
		});
		
		//新增取消
		$("#cancelOrgInfoBtn").click(function(){
			$("#sysOrgInfoDialogForm").form("clear");
			$("#orgInfoDialog").dialog("close");
		});

		//左移
		$("#leftBtn").click(function(){
			var node = $("#orgTree").tree("getSelected");
			var parentNode =$("#orgTree").tree("getParent",node.target);
			if(parentNode.id=="1"){
				$.messager.alert("提示","不能移动一级组织菜单！","warning");
				return;
			}
			$("#orgTree").tree("moveSelectedLeft");
			saveOrgMove();
		});

		//右移
		$("#rightBtn").click(function(){
			$("#orgTree").tree("moveSelectedRight");
			saveOrgMove();
		});
		
		//保存移动
		function saveOrgMove(){
			var menus = $("#orgTree").tree("getChanges");
			if (menus.length == 0) {
				$.messager.alert("提示", "未修改数据", "warning");
				return;
			}
			CommonSaveManager.saveTreeData(menus, "organizeId", "parentOrganizeId", function() {
						$("#orgTree").tree("reload");
			});
		};
	});
	</script>
	
	<div class="easyui-layout" fit="true" border="false">
		<div region="west" style="width:255px;">
		    <div class="datagrid-toolbar">
		    	 <span class="panel-header panel-title"  style="float: left; border-style: none; width: 50px;">组织树</span>
		    	 <a id="collapseBtn" class="easyui-linkbutton" iconCls="icon_left" >折叠</a>
				 <a id="expandBtn" class="easyui-linkbutton" iconCls="icon_right">展开</a>
		    </div>
			 <div id="dlg-toolbar" style="padding:2px 0">  
		        <table cellpadding="0" cellspacing="0" style="width:100%">  
		            <tr>  
		                <td style="padding-left:2px"> 
					      <input id="queryBtn" class="easyui-searchbox" prompt='组织名称，负责人' style="width:230px" /> 
		                </td>   
		            </tr>  
		        </table>  
		    </div> 
			<ul id="orgTree" class="easyui-tree" queryType="WlOrganizeModel"  queryFields="[{fieldName:'state',fieldValue:'U'}]"
				idField="organizeId" textfield="name" animate="false" parentField="parentOrganizeId" orderby="districtId" ></ul>
		</div>
		<div region="center" class="easyui-layout"  fit="true" border="false">
			<!-- 组织信息 -->
			<div region="north" title="详细信息" iconCls="icon-reload" border="true">
			  	<div id="sysOrgInfoForm" class="easyui-form" columns="3" i18nRoot="sysOrgInfoFormSYS" border="false">
					<input id="preOrgId" name="parentOrganizeId"  class="easyui-combobox" codeType="org" readonly="true" />
					<input name="name"  readonly="true"/>
					<input name="manage"  readonly="true"/>
					<input name="contact"  readonly="true"/>
					<input name="contactTel"  readonly="true"/>
					<input name="fax"  readonly="true"/>
					<input name="email" readonly="true"/>
					<input name="remarks"  colspan="3"  readonly="true"/>
					<input name="organizeId" type="hidden" />
				</div>
			</div>
			<!-- 组织用户 -->
			<div  region="center" title="组织用户" iconCls="icon-reload" border="true">
				<table id="orgUserGrid" class="easyui-datagrid" singleSelect="true" i18nRoot="orgUserGridSYS"
						queryType="UserInfoByOrgInfoQuery" border="false" fit="true">
					<thead>
						<tr>
							<th field="state" class="easyui-combobox" codeType="user_status"></th>
							<th field="loginName"></th>
							<th field="name"></th>
							<th field="email"></th>
							<th field="reportToUserId"></th>
							<th field="homeTel"></th>
							<th field="officeTel"></th>
							<th field="mobileTele"></th>
							<th field="addrId"></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 组织操作菜单 -->
	<div id="treeActionMenu" class="easyui-menu" style="width:120px;">
		<div id="addBtn" iconCls="icon-add">新增</div>
		<div id="insertBtn" iconCls="icon-redo">插入</div>
		<div id="editBtn" iconCls="icon-edit">编辑</div>
		<div id="deleteBtn" iconCls="icon-remove">删除</div>
		<div class="menu-sep"></div>
		<div id="leftBtn" iconCls="icon_left">左移</div>
		<div id="rightBtn" iconCls="icon_right">右移</div>
	</div>
	
	<!-- 组织信息窗口 -->
	<div id="orgInfoDialog" class="easyui-dialog" title="组织信息" iconCls="icon-reload" style="width:500px;"  modal="true" resizable="false" closable="false" closed="true">
	    <form id="sysOrgInfoDialogForm" class="easyui-form" columns="2" i18nRoot="sysOrgInfoDialogFormSYS">
			<input id="diaPreOrgId" name="parentOrganizeId"  class="easyui-combotree" codeType="org"/>
			<input name="name"  />
			<input name="manage"  />
			<input name="contact"  />
			<input name="contactTel"  />
			<input name="fax"  />
			<input name="email" colspan="2"/>
			<input name="remarks"  colspan="2" style="height:40px;"/>
			<input name="organizeId" type="hidden" />
		</form>
	    <div class="dialog-buttons">
				<a class="easyui-linkbutton" iconCls="icon-ok" id="saveOrgInfoBtn">保存</a>
				<a class="easyui-linkbutton" iconCls="icon-cancel" id="cancelOrgInfoBtn">取消</a>
		</div>		
	</div>
