<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {

		function getEditor() {
			return $("#editorFrame")[0].contentWindow.editor;
		}

		function initEditor() {
			if (getEditor() && getEditor().getSession) {
				getEditor().getSession().setMode("ace/mode/sql");
			} else {
				setTimeout(initEditor, 500);
			}
		}
		initEditor();
		
		function endEdit() {
			var nodeId = $("#formQuery").data("queryId");
			var node = $("#treeQueries").tree("find", nodeId);
			if (nodeId && node) {
				$.extend(node.attributes.data, $("#formQuery").form("getData"));
				node.attributes.data.querySql = getEditor().getValue();
				node.text = node.attributes.data.queryName;
				$("#treeQueries").tree("update", node);
			}
		};
		
		//选择query显示信息
		$("#treeQueries").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			endEdit();
			$("#formQuery").form("setData", node.attributes.data);
			getEditor().getSession().setValue(node.attributes.data.querySql);
			getEditor().navigateFileStart();
			$("#formQuery").data("queryId", node.id);
		};
		
		
		//编辑完query更新text
		$("#inputQueryName").bind("blur", function() {
			endEdit();
		});
		

		//新增
		$("#btnNew").click(function() {
			endEdit();
			$("#treeQueries").tree("appendAfterSelected", {});
			$("#inputQueryName").focus();
		});

		//插入
		$("#btnInsert").click(function() {
			endEdit();
			$("#treeQueries").tree("insertBeforeSelected", {});
			$("#inputQueryName").focus();
		});

		//新增子节点
		$("#btnNewChild").click(function() {
			endEdit();
			$("#treeQueries").tree("addChildToSelected", {});
			$("#inputQueryName").focus();
		});

		//删除
		$("#btnDelete").click(function() {
			$("#formQuery").form("setData", {});
			$("#formQuery").removeData("queryId");
			getEditor().getSession().setValue(null);
			$("#treeQueries").tree("removeSelected");
		});

		//上移
		$("#btnUp").click(function() {
			$("#treeQueries").tree("moveSelectedUp");
		});

		//下移
		$("#btnDown").click(function() {
			$("#treeQueries").tree("moveSelectedDown");
		});

		//左移
		$("#btnLeft").click(function() {
			$("#treeQueries").tree("moveSelectedLeft");
		});

		//右移
		$("#btnRight").click(function() {
			$("#treeQueries").tree("moveSelectedRight");
		});
		
		
		//保存
		$("#btnSave").click(function() {
			endEdit();
			var queries = $("#treeQueries").tree("getChanges");
			if (queries.length == 0) {
				$.messager.toast("提示", "未修改数据", "warning");
				return;
			}
			WpQueryDefinitionManager.saveTreeData(queries, function() {
				$.messager.toast("提示", "保存成功", "info");
				$("#formQuery").form("setData", {});
				$("#formQuery").removeData("queryId");
				getEditor().getSession().setValue(null);
				$("#treeQueries").tree("reload");
			});
		});

		//取消
		$("#btnCancel").click(function() {
			endEdit();
			var queries = $("#treeQueries").tree("getChanges");
			if (queries.length > 0) {
				$.messager.confirm("提示", "数据已修改，是否舍弃？", function(flag){
					if (flag) {
						$("#formQuery").form("setData", {});
						$("#formQuery").removeData("queryId");
						getEditor().getSession().setValue(null);
						$("#treeQueries").tree("reload");
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
	
	<div region="west" title="Queries" iconCls="icon-tree" style="width:300px;">
		<ul id="treeQueries" class="easyui-tree" dnd="true" queryType="WpQueryDefinitionModel", orderBy="seq",
				idField="uuid", textField="queryName", parentField="parentUuid", seqField="seq"/>
	</div>
	
	<div region="center" title="Query属性" iconCls="icon-edit">
		<div class="easyui-layout" fit="true">
			<div region="north">
				<form id="formQuery" class="easyui-form wp-config" columns="2">
					<input id="inputQueryName" name="queryName" title="名称" style="width: 300px;"/>
					<input title="备注" style="width: 300px;"/>
				</form>
			</div>
			
			<div region="center" title="查询SQL" iconCls="icon-edit">
				<iframe id="editorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"/>
			</div>
		</div>
	
	</div>
	
</div>