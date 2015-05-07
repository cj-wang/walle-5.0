<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {

		function getEditor() {
			return $("#editorFrame")[0].contentWindow.editor;
		}

		function initEditor() {
			if (getEditor() && getEditor().getSession) {
				getEditor().getSession().setMode("ace/mode/javascript");
			} else {
				setTimeout(initEditor, 500);
			}
		}
		initEditor();
		
		function endEdit() {
			var nodeId = $("#formTemplate").data("templateId");
			var node = $("#treeTemplates").tree("find", nodeId);
			if (nodeId && node) {
				$.extend(node.attributes.data, $("#formTemplate").form("getData"));
				node.attributes.data.content = getEditor().getValue();
				node.text = node.attributes.data.name;
				$("#treeTemplates").tree("update", node);
			}
		};
		
		//选择template显示信息
		$("#treeTemplates").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			endEdit();
			$("#formTemplate").form("setData", node.attributes.data);
			getEditor().getSession().setValue(node.attributes.data.content);
			getEditor().navigateFileStart();
			$("#formTemplate").data("templateId", node.id);
		};
		
		
		//编辑完template更新text
		$("#inputTemplateName").bind("blur", function() {
			endEdit();
		});
		

		//新增
		$("#btnNew").click(function() {
			endEdit();
			$("#treeTemplates").tree("appendAfterSelected", {});
			$("#inputTemplateName").focus();
		});

		//插入
		$("#btnInsert").click(function() {
			endEdit();
			$("#treeTemplates").tree("insertBeforeSelected", {});
			$("#inputTemplateName").focus();
		});

		//新增子节点
		$("#btnNewChild").click(function() {
			endEdit();
			$("#treeTemplates").tree("addChildToSelected", {});
			$("#inputTemplateName").focus();
		});

		//删除
		$("#btnDelete").click(function() {
			$("#formTemplate").form("setData", {});
			$("#formTemplate").removeData("templateId");
			getEditor().getSession().setValue(null);
			$("#treeTemplates").tree("removeSelected");
		});

		//上移
		$("#btnUp").click(function() {
			$("#treeTemplates").tree("moveSelectedUp");
		});

		//下移
		$("#btnDown").click(function() {
			$("#treeTemplates").tree("moveSelectedDown");
		});

		//左移
		$("#btnLeft").click(function() {
			$("#treeTemplates").tree("moveSelectedLeft");
		});

		//右移
		$("#btnRight").click(function() {
			$("#treeTemplates").tree("moveSelectedRight");
		});
		
		
		//保存
		$("#btnSave").click(function() {
			endEdit();
			var templates = $("#treeTemplates").tree("getChanges");
			if (templates.length == 0) {
				$.messager.toast("提示", "未修改数据", "warning");
				return;
			}
			CommonSaveManager.saveTreeData(templates, "uuid", "parentUuid", function() {
				$.messager.toast("提示", "保存成功", "info");
				$("#formTemplate").form("setData", {});
				$("#formTemplate").removeData("templateId");
				getEditor().getSession().setValue(null);
				$("#treeTemplates").tree("reload");
			});
		});

		//取消
		$("#btnCancel").click(function() {
			endEdit();
			var templates = $("#treeTemplates").tree("getChanges");
			if (templates.length > 0) {
				$.messager.confirm("提示", "数据已修改，是否舍弃？", function(flag){
					if (flag) {
						$("#formTemplate").form("setData", {});
						$("#formTemplate").removeData("templateId");
						getEditor().getSession().setValue(null);
						$("#treeTemplates").tree("reload");
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
		</div>
	</div>
	
	<div region="west" title="模版树" iconCls="icon-tree" style="width:300px;">
		<ul id="treeTemplates" class="easyui-tree" dnd="true" queryType="WpHtmlTemplateJsModel", orderBy="seq",
				idField="uuid", textField="name", parentField="parentUuid", seqField="seq"/>
	</div>
	
	<div region="center" title="模版属性" iconCls="icon-edit">
		<div class="easyui-layout" fit="true">
			<div region="north">
				<form id="formTemplate" class="easyui-form wp-config" columns="2">
					<input id="inputTemplateName" name="name" title="名称" style="width: 300px;"/>
					<input title="备注" style="width: 300px;"/>
				</form>
			</div>
			
			<div region="center" title="模版内容" iconCls="icon-edit">
				<iframe id="editorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"/>
			</div>
		</div>
	
	</div>
	
</div>