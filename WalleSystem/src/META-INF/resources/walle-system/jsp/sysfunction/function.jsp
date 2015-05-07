<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	<script type="text/javascript">
	$(function(){
		//组织查询
		$("#queryBtn").searchbox("options").searcher = function(value, name){
			 $("#funcTree").tree("commonQuery", {
	            queryFields : [{
	                fieldName : "[name,viewname]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            }]
	        });
		};
		
		//展开
		$("#expandBtn").click(function(){
			$("#funcTree").tree("expandAll");
		});
		
		//折叠
		$("#collapseBtn").click(function(){
			$("#funcTree").tree("collapseAll");
		});
		
		//新增
		$("#addBtn").click(function(){
			var node = $("#funcTree").tree("getSelected");
			if(node){
				$("#addFuncInfoForm").form("clear");
				$("#funcParentId").combotree("setValue",node.id);
				$("#addFuctionDialog").dialog("open");
			}else{
				$.messager.alert("提示","请选择新增功能的上级模块","warning");
				return;
			}
		});
		
		//编辑
		$("#editBtn").click(function(){
			var node = $("#funcTree").tree("getSelected");
			if(node){
				WlFunctionManager.get(node.id,function(data){
					if(data.exception){
						$.messager.alert("提示","获取系统功能详细信息失败！","warning");return;
					}else{
						$("#addFuncInfoForm").form("load",data);
						$("#addFuctionDialog").dialog("open");
					}
				});
			}else{
				$.messager.alert("提示","请选择要编辑的功能！","warning");
				return;
			}
		});
		
		//保存
		$("#saveFuncInfoBtn").click(function(){
			if(!$("#addFuncInfoForm").form("validate")){
				$.messager.alert("提示","数据校验失败！","warning");
				return;
			}else{
				var addFuncInfoForm = $("#addFuncInfoForm").form("getData");
				WlFunctionManager.saveModel(addFuncInfoForm,function(data){
					if(data.exception){
						$.messager.alert("提示","保存失败！请重新保存","warning");
						return;
					}else{
						$("#funcTree").tree("reload");
						$("#addFuctionDialog").dialog("close");
						$.messager.alert("提示","保存成功！");
						return;
					}
				});
			}
		});
		
		//取消
		$("#cancelFuncInfoBtn").click(function(){
			$("#addFuctionDialog").dialog("close");
		});
		
		//删除
		$("#deleteBtn").click(function(){
			var node = $("#funcTree").tree("getSelected");
			if(node){
				if($.messager.confirm("提示","是否确定删除所选定的系统功能！",function(flag){
					if(flag){
						WlFunctionManager.delByPk(node.id,function(){
							$("#funcTree").tree("reload");
							$.messager.alert("提示","删除成功！");
						});
					}
				}));
			}else{
				$.messager.alert("提示","请选择要删除的系统功能！","warning");
				return;
			}
		});
		
		
		//上移
		$("#upBtn").click(function(){
			$("#funcTree").tree("moveSelectedUp");
			saveFuncMove();
		});
		
		//下移
		$("#downBtn").click(function(){
			$("#funcTree").tree("moveSelectedDown");
			saveFuncMove();
		});
		
		//左移
		$("#leftBtn").click(function(){
			var node = $("#funcTree").tree("getSelected");
			var parentNode =$("#funcTree").tree("getParent",node.target);
			if(parentNode.id=="1"){
				$.messager.alert("提示","不能移动一级功能菜单！","warning");
				return;
			}
			$("#funcTree").tree("moveSelectedLeft");
			saveFuncMove();
		});
		
		//右移
		$("#rightBtn").click(function(){
			$("#funcTree").tree("moveSelectedRight");
			saveFuncMove();
		});
		
		//保存移动
		function saveFuncMove(){
			var menus = $("#funcTree").tree("getChanges");
			if (menus.length == 0) {
				return;
			}
			CommonSaveManager.saveTreeData(menus, "funcId", "parentId", function() {
						$("#funcTree").tree("reload");
			});
		};
		
		$("#funcTree").tree({
			onContextMenu:function(e,node){
				e.preventDefault(),
				$(this).tree("select",node.target);
				$("#funcTreeActionMenu").menu("show",{
					left:e.pageX,
					top:e.pageY
				});
			},
			onSelect:function(node){
				var node = $("#funcTree").tree("getSelected");
				
				//角色Grid
				$("#funcRoleGrid").datagrid("setQueryFields",[{
					fieldName:"funcId",
					fieldValue:node.id
				}]);
				$("#funcRoleGrid").datagrid("commonQuery",{
					queryType:"GrantedRoleByFuncQuery"
				});
				
				//人员角色
				$("#funcPersonGrid").datagrid("setQueryFields",[{
					fieldName:"funcId",
					fieldValue:node.id
				}]);
				$("#funcPersonGrid").datagrid("commonQuery",{
					queryType:"GrantedUserByFuncQuery"
				});
				
				//form 表单刷新
				WlFunctionManager.get(node.id,function(data){
					if(data.exception){
						$.messager.alert("提示","获取系统功能详细信息失败！","warning");return;
					}else{
							$("#funcInfoForm").form("load",data);
					}
				});
			}
		});
	});
	
	</script>
	<div class="easyui-layout" fit="true" border="false">
		<!-- 右侧，查询窗口、功能列表 -->
		<div region="west" class="easyui-layout" style="width:265px;"  border="false">
			<div region="north" style="height:58px;" border="false">
				 <div class="datagrid-toolbar">
			    	 <span class="panel-header panel-title"  style="float: left; border-style: none; width: 70px;">系统功能树</span>
			    	 <a id="collapseBtn" class="easyui-linkbutton" iconCls="icon_left" >折叠</a>
					 <a id="expandBtn" class="easyui-linkbutton" iconCls="icon_right">展开</a>
		   		 </div>
			     <div id="dlg-toolbar" style="padding:2px 0">  
			        <table cellpadding="0" cellspacing="0" style="width:100%">  
			            <tr>  
			                <td style="padding-left:2px"> 
						      <input id="queryBtn" class="easyui-searchbox" prompt='功能名称，关联页面' style="width:230px" /> 
			                </td>   
			            </tr>  
			        </table>  
			    </div> 
			</div>
			<div region="center">
				<ul id="funcTree" class="easyui-tree"  queryType="WlFunctionModel"  orderBy="funSeq"
								  idField="funcId" textField="name" animate="false" parentField="parentId"  seqField="funSeq"/>
			</div>
		</div>	
		<div region="center"  class="easyui-layout" title="功能详细信息" iconCls="icon-reload" >
			<div region="north" class="easyui-panel" border="false">
				<form id="funcInfoForm" class="easyui-form" columns="3" i18nRoot="funcInfoFormSYS"  border="false">
					<input name="funcCode" disabled="disabled"/>
					<input name="name" disabled="disabled"/>
					<input name="parentId"   class="easyui-combotree" codeType="sysfunction"  disabled="disabled"  readonly="true" />
					<input name="funcLevel"  disabled="disabled" />
					<input name="funSeq"  disabled="disabled" />
					<input name="viewname"  disabled="disabled"/>
					<input name="funcImg"  disabled="disabled"/>
					<input name="funcType"  class="easyui-combobox" codeType="WP_PAGETYPE"  disabled="disabled"  readonly="true"/>
					<input name="sys"   disabled="disabled" />
					<input name="state"  disabled="disabled" />
					<input name="funcId" type="hidden"/>
				</form>
			</div>
			<div region="center"  border="true" fit="true">
				<div id="tabsDiv"  class="easyui-tabs" fit="true"  border="false">
					<div title="拥有此功能权限的角色" iconCls="icon-reload" border="false">
						<table id="funcRoleGrid" class="easyui-datagrid"  singSelect="true" i18nRoot = 'funcRoleGridSYS' border="false" fit="true">
							<thead>
								<tr>
									<th field='name'></th>
									<th field='code' ></th>
								</tr>
							</thead>
						</table>
					</div>
					<div title="拥有此功能权限的人员" iconCls="icon-reload" border="false">
						<table id="funcPersonGrid" class="easyui-datagrid"  singSelect="true" i18nRoot = "funcPersonGridSYS" border="false" fit="true">
							<thead>
								<tr>
									<th field="organizeId" class="easyui-combobox" codeType='org' ></th>
									<th field="name"  ></th>
									<th field="loginName"  ></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>
	
<!-- 系统功能操作菜单 -->
<div id="funcTreeActionMenu" class="easyui-menu" style="width:120px;">
	<div id="addBtn" iconCls='icon-add'>新增</div>
	<div id="editBtn" iconCls="icon-edit">编辑</div>
	<div id="deleteBtn" iconCls="icon-remove">删除</div>
	<div class="menu-sep"></div>
	<div id="upBtn" iconCls="icon_up">上移</div>
	<div id="downBtn" iconCls="icon_down">下移</div>
	<div id="leftBtn" iconCls="icon_left">左移</div>
	<div id="rightBtn" iconCls="icon_right">右移</div>
</div>

<!--新增/编辑     系统功能信息 -->
<div id="addFuctionDialog" class="easyui-dialog" title="功能信息" iconCls="icon-reload" style="width:710px;height:200px;" modal="true" resizable="true" closeable="true" closed="true">
	<form id="addFuncInfoForm" class="easyui-form" columns="3" i18nRoot="funcInfoFormSYS"  border="false">
		<input name="funcCode" class="easyui-validatebox" required="true" validType="length[0,20]" />
		<input name="name" class="easyui-validatebox" required="true" validType="lenght[0,30]" />
		<input name="parentId" id="funcParentId"  class="easyui-combotree" codeType="sysfunction" required="true" />
		<input name="funcLevel" />
		<input name="funSeq" />
		<input name="viewname" />
		<input name="funcImg" />
		<input name="funcType" class="easyui-combobox" codeType="WP_PAGETYPE"/>
		<input name="sys" />
		<input name="state"/>
		<input name="funcId" type="hidden"/>
	</form>
	 <div class="dialog-buttons">
		<a class="easyui-linkbutton" iconCls="icon-ok" id="saveFuncInfoBtn">保存</a>
		<a class="easyui-linkbutton" iconCls="icon-cancel" id="cancelFuncInfoBtn">取消</a>
	</div>	
</div>
