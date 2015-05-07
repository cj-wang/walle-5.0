<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {
		
		function getEditor() {
			return $("#serviceEditorFrame")[0].contentWindow.editor;
		};

		function initEditor() {
			if (getEditor() && getEditor().getSession) {
				getEditor().getSession().setMode("ace/mode/groovy");
			} else {
				setTimeout(initEditor, 500);
			}
		};
		initEditor();
		
		function endEdit() {
			var nodeId = $("#formService").data("serviceId");
			var node = $("#treeServices").tree("find", nodeId);
			if (nodeId && node) {
				$.extend(node.attributes.data, $("#formService").form("getData"));
				node.attributes.data.content = getEditor().getValue();
				node.text = node.attributes.data.serviceName;
				$("#treeServices").tree("update", node);
			}
		};

		//选择service显示信息
		$("#treeServices").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			endEdit();
			$("#formService").form("setData", node.attributes.data);
			getEditor().getSession().setValue(node.attributes.data.content);
			getEditor().navigateFileStart();
			$("#formService").data("serviceId", node.id);
		};
		
		
		//编辑完service name更新text
		$("#inputServiceName").bind("blur", function() {
			endEdit();
		});
		
		var template = 
			'import cn.walle.platform.support.BaseGroovyService;\n' +
			'\n' +
			'class GroovyService extends BaseGroovyService {\n' +
			'\n' +
			'	Object execute(Object p) {\n' +
			'		//\n' +
			'		\n' +
			'		return null;\n' +
			'	}\n' +
			'\n' +
			'}';

		
		//新增
		$("#btnNew").click(function() {
			endEdit();
			$("#treeServices").tree("appendAfterSelected", {
				contentType : "groovy",
				content : template
			});
			$("#inputServiceName").focus();
		});

		//插入
		$("#btnInsert").click(function() {
			endEdit();
			$("#treeServices").tree("insertBeforeSelected", {
				contentType : "groovy",
				content : template
			});
			$("#inputServiceName").focus();
		});

		//新增子节点
		$("#btnNewChild").click(function() {
			endEdit();
			$("#treeServices").tree("addChildToSelected", {
				contentType : "groovy",
				content : template
			});
			$("#inputServiceName").focus();
		});

		//删除
		$("#btnDelete").click(function() {
			$("#formService").form("setData", {});
			$("#formService").removeData("serviceId");
			getEditor().getSession().setValue(null);
			$("#treeServices").tree("removeSelected");
		});

		//上移
		$("#btnUp").click(function() {
			$("#treeServices").tree("moveSelectedUp");
		});

		//下移
		$("#btnDown").click(function() {
			$("#treeServices").tree("moveSelectedDown");
		});

		//左移
		$("#btnLeft").click(function() {
			$("#treeServices").tree("moveSelectedLeft");
		});

		//右移
		$("#btnRight").click(function() {
			$("#treeServices").tree("moveSelectedRight");
		});
		
		
		//保存
		$("#btnSave").click(function() {
			endEdit();
			var services = $("#treeServices").tree("getChanges");
			if (services.length == 0) {
				$.messager.toast("提示", "未修改数据", "warning");
				return;
			}
			GroovyServiceManager.saveTreeData(services, function() {
				$.messager.toast("提示", "保存成功", "info");
				$("#formService").form("setData", {});
				$("#formService").removeData("serviceId");
				getEditor().getSession().setValue(null);
				$("#treeServices").tree("reload");
			});
		});

		//取消
		$("#btnCancel").click(function() {
			endEdit();
			var services = $("#treeServices").tree("getChanges");
			if (services.length > 0) {
				$.messager.confirm("提示", "数据已修改，是否舍弃？", function(flag){
					if (flag) {
						$("#formService").form("setData", {});
						$("#formService").removeData("serviceId");
						getEditor().getSession().setValue(null);
						$("#treeServices").tree("reload");
					}
				});
			}
		});

	});
</script>

<div class="easyui-layout" fit="true">
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<a id="btnNew" class="easyui-linkbutton" iconCls="icon-add">新增</a>
			<a id="btnInsert" class="easyui-linkbutton" iconCls="icon-redo">插入</a>
			<a id="btnNewChild" class="easyui-linkbutton" iconCls="icon-add">新增子节点</a>
			<a id="btnDelete" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
<!-- 			<a id="btnUp" class="easyui-linkbutton" iconCls="icon_up">上移</a> -->
<!-- 			<a id="btnDown" class="easyui-linkbutton" iconCls="icon_down">下移</a> -->
<!-- 			<a id="btnLeft" class="easyui-linkbutton" iconCls="icon_left">左移</a> -->
<!-- 			<a id="btnRight" class="easyui-linkbutton" iconCls="icon_right">右移</a> -->
			<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save">保存</a>
			<a id="btnCancel" class="easyui-linkbutton" iconCls="icon-reload">取消</a>
			<a id="btnRelease" class="easyui-linkbutton" iconCls="icon_active" disabled="disabled">发布</a>
		</div>
	</div>

	<div region="west" title="动态服务" iconCls="icon-tree" style="width:320px;">
		<div class="easyui-layout" fit="true">
		    <div region="center" border="false">
				<ul id="treeServices" class="easyui-tree" queryType="WpDynamicServiceModel" orderBy="seq" 
						idField="uuid" textfield="serviceName" parentField="parentUuid" seqField="seq"
						dnd="true"
						title="可拖动节点到其它节点内改变服务层级关系"/>
		    </div>
		</div>
	</div>
	
	<div region="center" title="服务配置" iconCls="icon-edit">
		<div class="easyui-layout" fit="true">
			<div region="north" border="false">
				<form id="formService" class="easyui-form wp-config" columns="2">
					<input id="inputServiceName" name="serviceName" title="名称" style="width: 300px;"/>
					<input title="备注" style="width: 300px;"/>
				</form>
			</div>
			
			<div region="center">
				<div id="tabs" class="easyui-tabs" fit="true" border="false">
					
					<div title="服务代码" iconCls="myCustomerIcon_searchForm">
						<div class="easyui-layout" fit="true">
							<div region="center">
								<iframe id="serviceEditorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"></iframe>
							</div>
						</div>
					</div>
					
					<div id="test" title="测试" iconCls="icon-preview">
					</div>
					
					<div id="history" title="历史版本" iconCls="icon-convert">
						<div class="easyui-layout" fit="true">
							<div region="north" border="false" style="height:200px;">
								<div class="datagrid-toolbar">
									<a id="btnReloadHis" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
								</div>
						        <table id="gridHis" class="easyui-datagrid wp-config" fit="true" singleSelect="true"
						                queryType="WpDynamicServiceHistoryModel" orderby="createTime desc"
						                queryFields="[{fieldName:'contentType', fieldStringValue:'comment'}, {fieldName:'url', fieldStringValue:'<%= request.getParameter("url") %>'}]">
						        	<thead>
						        		<tr>
						                    <th field="createTime" title="修改时间"/>
						                    <th field="creator" title="修改人" codetype="WP_USERS"/>
						                    <th field="content" title="备注" width="800"/>
						        		</tr>
						        	</thead>
				        		</table>
							</div>
							
							<div region="center">
								<div id="tabsHis" class="easyui-tabs" fit="true" border="false">
									
									<div title="服务代码" iconCls="myCustomerIcon_searchForm">
										<iframe id="staticHtmlHisIFrame" style="width:100%; height:100%; border:none;"></iframe>
									</div>
									
								</div>
							</div>
							
						</div>
					</div>
					
				</div>
			</div>
			
		</div>	
	</div>
			
    <div id="dialogSave" class="easyui-dialog" title="请输入备注信息；" iconCls="icon-save"
        	style="width:560px;padding:10px" closed="true" modal="true">
    	<textarea id="comment" rows="10" cols="80" style="width:98%;"></textarea>
    	<div class="dialog-buttons">
    		<a id="btnDialogSaveOk" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
    		<a id="btnDialogSaveCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
    	</div>
    </div>
    
</div>