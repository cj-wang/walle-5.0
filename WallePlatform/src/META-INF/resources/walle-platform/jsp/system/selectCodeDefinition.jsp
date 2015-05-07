<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {

		function getEditor() {
			return $("#editorFrame")[0].contentWindow.editor;
		}

		function initEditor() {
			if (getEditor() && getEditor().getSession) {
				getEditor().getSession().setMode("ace/mode/json");
			} else {
				setTimeout(initEditor, 500);
			}
		}
		initEditor();
		
		function endEdit() {
			var nodeId = $("#formSelectCode").data("selectCodeId");
			var node = $("#treeSelectCodes").tree("find", nodeId);
			if (nodeId && node) {
				$.extend(node.attributes.data, $("#formSelectCode").form("getData"));
				node.attributes.data.definition = getEditor().getValue();
				node.text = node.attributes.data.codeType;
				$("#treeSelectCodes").tree("update", node);
			}
		};
		
		//选择selectCode显示信息
		$("#treeSelectCodes").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			endEdit();
			$("#formSelectCode").form("setData", node.attributes.data);
			getEditor().getSession().setValue(node.attributes.data.definition);
			getEditor().navigateFileStart();
			$("#formSelectCode").data("selectCodeId", node.id);
		};
		
		
		//编辑完selectCode更新text
		$("#inputSelectCodeName").bind("blur", function() {
			endEdit();
		});
		
		var template = 
			'{\n' +
			'	"queryType": "XxxModel",\n' +
			'	"orderBy": "name",\n' +
			'	"keyFieldName": "uuid",\n' +
			'	"labelFieldName": "name",\n' +
			'	"queryFields" : [{\n' +
			'		"fieldName" : "state",\n' +
			'		"fieldValue" : "1"\n' +
			'	}],\n' +
			'	"columns" : [{\n' +
			'		"field" : "name",\n' +
			'		"title" : "名称"\n' +
			'	}, {\n' +
			'		"field" : "type",\n' +
			'		"title" : "类型"\n' +
			'	}]\n' +
			'}\n';
		
		//新增
		$("#btnNew").click(function() {
			endEdit();
			$("#treeSelectCodes").tree("appendAfterSelected", {
				definition : template
			});
			$("#inputSelectCodeName").focus();
		});

		//插入
		$("#btnInsert").click(function() {
			endEdit();
			$("#treeSelectCodes").tree("insertBeforeSelected", {
				definition : template
			});
			$("#inputSelectCodeName").focus();
		});

		//新增子节点
		$("#btnNewChild").click(function() {
			endEdit();
			$("#treeSelectCodes").tree("addChildToSelected", {
				definition : template
			});
			$("#inputSelectCodeName").focus();
		});

		//删除
		$("#btnDelete").click(function() {
			$("#formSelectCode").form("setData", {});
			$("#formSelectCode").removeData("selectCodeId");
			getEditor().getSession().setValue(null);
			$("#treeSelectCodes").tree("removeSelected");
		});

		//上移
		$("#btnUp").click(function() {
			$("#treeSelectCodes").tree("moveSelectedUp");
		});

		//下移
		$("#btnDown").click(function() {
			$("#treeSelectCodes").tree("moveSelectedDown");
		});

		//左移
		$("#btnLeft").click(function() {
			$("#treeSelectCodes").tree("moveSelectedLeft");
		});

		//右移
		$("#btnRight").click(function() {
			$("#treeSelectCodes").tree("moveSelectedRight");
		});
		
		
		//保存
		$("#btnSave").click(function() {
			endEdit();
			var selectCodes = $("#treeSelectCodes").tree("getChanges");
			if (selectCodes.length == 0) {
				$.messager.toast("提示", "未修改数据", "warning");
				return;
			}
			WpSelectCodeDefinitionManager.saveTreeData(selectCodes, function() {
				$("#formSelectCode").form("setData", {});
				$("#formSelectCode").removeData("selectCodeId");
				getEditor().getSession().setValue(null);
				$("#treeSelectCodes").tree("reload");
				//refresh code definition js
				$.getScript(contextPath + "/select_code_definitions_" + locale + ".js", function() {
					for (var key in SELECT_CODE_VALUES) {
						delete SELECT_CODE_VALUES[key];
					}
					for (var key in SELECT_CODE_DATAS) {
						delete SELECT_CODE_DATAS[key];
					}
					$.messager.toast("提示", "保存成功，编码缓存刷新成功", "info");
				});
			});
		});

		//取消
		$("#btnCancel").click(function() {
			endEdit();
			var selectCodes = $("#treeSelectCodes").tree("getChanges");
			if (selectCodes.length > 0) {
				$.messager.confirm("提示", "数据已修改，是否舍弃？", function(flag){
					if (flag) {
						$("#formSelectCode").form("setData", {});
						$("#formSelectCode").removeData("selectCodeId");
						getEditor().getSession().setValue(null);
						$("#treeSelectCodes").tree("reload");
					}
				});
			}
		});

		//刷新服务器编码缓存
		$("#btnRefreshServerCache").click(function() {
			WpSelectCodeDefinitionManager.refreshServerCache(function() {
				$.messager.toast("提示", "服务器编码缓存刷新成功", "info");
			});
		});

		//刷新浏览器编码缓存
		$("#btnRefreshBrowserCache").click(function() {
			$.getScript(contextPath + "/select_code_definitions_" + locale + ".js", function() {
				for (var key in SELECT_CODE_VALUES) {
					delete SELECT_CODE_VALUES[key];
				}
				for (var key in SELECT_CODE_DATAS) {
					delete SELECT_CODE_DATAS[key];
				}
				$.messager.toast("提示", "浏览器编码缓存刷新成功", "info");
			});
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
			<a id="btnRefreshServerCache" class="easyui-linkbutton" iconCls="icon-reload">刷新服务器编码缓存</a>
			<a id="btnRefreshBrowserCache" class="easyui-linkbutton" iconCls="icon-reload">刷新浏览器编码缓存</a>
		</div>
	</div>
	
	<div region="west" title="SelectCodes" iconCls="icon-tree" style="width:300px;">
		<ul id="treeSelectCodes" class="easyui-tree" dnd="true" queryType="WpSelectCodeDefinitionModel", orderBy="seq",
				idField="uuid", textField="codeType", parentField="parentUuid", seqField="seq"/>
	</div>
	
	<div region="center" title="SelectCode属性" iconCls="icon-edit">
		<div class="easyui-layout" fit="true">
			<div region="north">
				<form id="formSelectCode" class="easyui-form wp-config" columns="2">
					<input id="inputSelectCodeName" name="codeType" title="名称" style="width: 300px;"/>
					<input title="备注" style="width: 300px;"/>
				</form>
			</div>
			
			<div region="center" title="SelectCode配置" iconCls="icon-edit">
				<iframe id="editorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"/>
			</div>
		</div>
	
	</div>
	
</div>