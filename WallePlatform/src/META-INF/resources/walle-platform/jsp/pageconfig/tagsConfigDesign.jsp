<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {

		var url = "<%= request.getParameter("url") %>";
		var tagId = "<%= request.getParameter("tagId") %>";
		
		//选择tag显示attrs
		$("#treeTags").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			$("#gridAttrs").datagrid("endEdit").datagrid("loadData", {
				rows : node.attributes.data.attrs
			});
			$("#gridAttrs").data("tagNode", node);
		};
		
		//编辑完attrs更新tag text
		$("#gridAttrs").datagrid("options").onAfterEdit = function(rowIndex, rowData, changes) {
			$.fn.datagrid.defaults.onAfterEdit.apply(this, [rowIndex, rowData, changes]);
			var node = $("#gridAttrs").data("tagNode");
			if (node) {
				node.text = getTagText(node.attributes.data);
				$("#treeTags").tree("update", node);
			}
			updatePreview();
		}
		
		
		//双击tag开始编辑
		$("#treeTags").tree("options").onDblClick = function(node) {
			$.fn.tree.defaults.onDblClick.apply(this, [node]);
			$("#treeTags").tree("beginEdit", node.target);
		};
		
		//编辑tag只编辑tag name
		$("#treeTags").tree("options").onBeforeEdit = function(node) {
			$.fn.tree.defaults.onBeforeEdit.apply(this, [node]);
			if (node.attributes.data.tagName == "TextNode") {
				node.text = node.attributes.data.attrs[0] ? node.attributes.data.attrs[0].attrValue : "";
			} else {
				node.text = node.attributes.data.tagName;
			}
		};
		
		//编辑tag完成后显示tag text
		$("#treeTags").tree("options").onAfterEdit = function(node) {
			$.fn.tree.defaults.onAfterEdit.apply(this, [node]);
			if (node.attributes.data.tagName == "TextNode") {
				if (! node.attributes.data.attrs[0]) {
					node.attributes.data.attrs.push({
						attrName : "textContent",
						attrValue : node.text
					});
				} else {
					node.attributes.data.attrs[0].attrValue = node.text;
				}
			} else {
				node.attributes.data.tagName = node.text;
			}
			node.text = getTagText(node.attributes.data);
			$("#treeTags").tree("update", node);
			updatePreview();
		};
		
		//拖放后刷新预览视图
		$("#treeTags").tree("options").onDrop = function(target, source, point) {
			$.fn.tree.defaults.onDrop.apply(this, [target, source, point]);
			updatePreview();
		};
		
		
		//加载数据
		WpHtmlTagManager.getTags(url, tagId, function(result) {
			if (result.length == 0) {
				result = [$("#main").closest(".window-body").data("originalTag")];
			}
			function bindAttributes(node) {
				node.id = node.uuid;
				node.text = getTagText(node);
				if (node.children) {
					for (var i = 0; i < node.children.length; i++) {
						bindAttributes(node.children[i]);
					}
				}
			};
			for (var i = 0; i < result.length; i++) {
				bindAttributes(result[i]);
			}
			$("#treeTags").tree("loadData", result);
			$("#treeTags").tree("select", $("#treeTags").tree("getRoot").target);
			updatePreview();
		});
		
		function getTagText(tag) {
			if (tag.tagName == "TextNode") {
				return tag.attrs[0] ? tag.attrs[0].attrValue : "";
			} else {
				var text = "&lt;" + (tag.tagName || "_");
				for (var i = 0; i < tag.attrs.length; i++) {
					text += " " + (tag.attrs[i].attrName || "_") + "=\"" + (tag.attrs[i].attrValue || "") + "\"";
				}
				text += "&gt;";
				return text;
			}
		}
		
		function updatePreview() {
			function generateTagContent(node) {
				var tag = node.attributes.data;
				if (tag.tagName == "TextNode") {
					return tag.attrs[0] ? tag.attrs[0].attrValue : "";
				} else {
					if (! tag.tagName) {
						return "";
					}
					var html = "<" + tag.tagName;
					for (var i = 0; i < tag.attrs.length; i++) {
						if (! tag.attrs[i].attrName) {
							continue;
						}
						html += " " + tag.attrs[i].attrName + "=\"" + tag.attrs[i].attrValue + "\"";
					}
					html += ">\n";
					var children = $("#treeTags").tree("getData", node.target).children;
					for (var i = 0; i < children.length; i++) {
						html += generateTagContent(children[i]);
					}
					html += "</" + tag.tagName + ">\n";
					return html;
				}
			}
			var roots = $("#treeTags").tree("getRoots");
			var html = "";
			for (var i = 0; i < roots.length; i++) {
				html += generateTagContent(roots[i]);
			}
			setTimeout(function() {
				$.parser.parse($("#preview").html(html))
			}, 0);
		}
		

		//新增
		$("#btnNew").click(function() {
			$("#treeTags").tree("appendAfterSelected", {
				text : "&lt;&gt;",
				attributes : {
					data : {
						tagName : "",
						attrs : []
					}
				}
			});
		});

		//插入
		$("#btnInsert").click(function() {
			$("#treeTags").tree("insertBeforeSelected", {
				text : "&lt;&gt;",
				attributes : {
					data : {
						tagName : "",
						attrs : []
					}
				}
			});
		});

		//新增子节点
		$("#btnNewChild").click(function() {
			$("#treeTags").tree("addChildToSelected", {
				text : "&lt;&gt;",
				attributes : {
					data : {
						tagName : "",
						attrs : []
					}
				}
			});
		});
		
		//新增文本
		$("#btnNewText").click(function() {
			$("#treeTags").tree("appendAfterSelected", {
				attributes : {
					data : {
						tagName : "TextNode",
						attrs : [{
							attrName : "textContent",
							attrValue : ""
						}]
					}
				}
			});
		});

		//插入文本
		$("#btnInsertText").click(function() {
			$("#treeTags").tree("insertBeforeSelected", {
				attributes : {
					data : {
						tagName : "TextNode",
						attrs : [{
							attrName : "textContent",
							attrValue : ""
						}]
					}
				}
			});
		});

		//新增文本子节点
		$("#btnNewChildText").click(function() {
			$("#treeTags").tree("addChildToSelected", {
				attributes : {
					data : {
						tagName : "TextNode",
						attrs : [{
							attrName : "textContent",
							attrValue : ""
						}]
					}
				}
			});
		});

		//编辑
		$("#btnEdit").click(function() {
			var selectedNode = $("#treeTags").tree("getSelected");
			if (selectedNode == null) {
				return;
			}
			$("#treeTags").tree("beginEdit", selectedNode.target);
		});

		//删除
		$("#btnDelete").click(function() {
			$("#gridAttrs").datagrid("loadData", {
				rows : []
			});
			$("#treeTags").tree("removeSelected");
			updatePreview();
		});

		//上移
		$("#btnUp").click(function() {
			$("#treeTags").tree("moveSelectedUp");
			updatePreview();
		});

		//下移
		$("#btnDown").click(function() {
			$("#treeTags").tree("moveSelectedDown");
			updatePreview();
		});

		//左移
		$("#btnLeft").click(function() {
			$("#treeTags").tree("moveSelectedLeft");
			updatePreview();
		});

		//右移
		$("#btnRight").click(function() {
			$("#treeTags").tree("moveSelectedRight");
			updatePreview();
		});
		
		
		//属性新增
		$("#btnAttrNew").click(function() {
			$("#gridAttrs").datagrid("appendRow", {});
		});
		
		//属性插入
		$("#btnAttrInsert").click(function() {
			$("#gridAttrs").datagrid("insertRow", {
				index : $("#gridAttrs").datagrid("getSelectedIndex"),
				row : {}
			});
		});
		
		//属性删除
		$("#btnAttrDelete").click(function() {
			$("#gridAttrs").datagrid("deleteSelectedRows");
			updatePreview();
		});
		
		//属性上移
		$("#btnAttrUp").click(function() {
			var selectedIndex = $("#gridAttrs").datagrid("getSelectedIndex");
			if (selectedIndex >= 1) {
				var selectedRow = $("#gridAttrs").datagrid("getSelected");
				$("#gridAttrs").datagrid("deleteRow", selectedIndex);
				$("#gridAttrs").datagrid("insertRow", {
					index : selectedIndex - 1,
					row : selectedRow
				});
				updatePreview();
			}
		});
		
		//属性下移
		$("#btnAttrDown").click(function() {
			var selectedIndex = $("#gridAttrs").datagrid("getSelectedIndex");
			if (selectedIndex >= 0 && selectedIndex < $("#gridAttrs").datagrid("getRows").length - 1) {
				var selectedRow = $("#gridAttrs").datagrid("getSelected");
				$("#gridAttrs").datagrid("deleteRow", selectedIndex);
				$("#gridAttrs").datagrid("insertRow", {
					index : selectedIndex + 1,
					row : selectedRow
				});
				updatePreview();
			}
		});
		
		
		//保存
		$("#btnSave").click(function() {
			$("#gridAttrs").datagrid("endEdit");
			function getNodeData(node) {
				var data = node.attributes.data;
				data.children = [];
				var children = $("#treeTags").tree("getData", node.target).children;
				for (var i = 0; i < children.length; i++) {
					data.children.push(getNodeData(children[i]));
				}
				return data;
			}
			var roots = $("#treeTags").tree("getRoots");
			for (var i = 0; i < roots.length; i++) {
				roots[i] = getNodeData(roots[i]);
			}
			WpHtmlTagManager.saveTags(url, tagId, roots, function(result) {
				$("#main").closest(".window-body").data("refresh", true).dialog("close");
			});
		});
		
		//取消
		$("#btnCancel").click(function() {
			$(this).closest(".window-body").dialog("close");
		});
		
	});
	
</script>

<div id="main" class="easyui-layout" fit="true">
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save">保存</a>
			<a id="btnCancel" class="easyui-linkbutton" iconCls="icon-reload">取消</a>
		</div>
		<div class="datagrid-toolbar">
			<a id="btnNew" class="easyui-linkbutton" iconCls="icon-add">新增</a>
			<a id="btnInsert" class="easyui-linkbutton" iconCls="icon-redo">插入</a>
			<a id="btnNewChild" class="easyui-linkbutton" iconCls="icon-add">新增子节点</a>
			<a id="btnNewText" class="easyui-linkbutton" iconCls="icon-add">新增文本</a>
			<a id="btnInsertText" class="easyui-linkbutton" iconCls="icon-redo">插入文本</a>
			<a id="btnNewChildText" class="easyui-linkbutton" iconCls="icon-add">新增文本子节点</a>
			<a id="btnEdit" class="easyui-linkbutton" iconCls="icon-edit">编辑</a>
			<a id="btnDelete" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			<a id="btnUp" class="easyui-linkbutton" iconCls="icon_up">上移</a>
			<a id="btnDown" class="easyui-linkbutton" iconCls="icon_down">下移</a>
			<a id="btnLeft" class="easyui-linkbutton" iconCls="icon_left">左移</a>
			<a id="btnRight" class="easyui-linkbutton" iconCls="icon_right">右移</a>
		</div>
	</div>
	
	<div region="center" title="控件树" iconCls="icon-tree">
		<ul id="treeTags" class="easyui-tree" dnd="true"/>
	</div>
	
	<div region="east" title="控件属性" iconCls="icon-edit" style="width:400px;">
		<div class="datagrid-toolbar">
			<a id="btnAttrNew" class="easyui-linkbutton" iconCls="icon-add">新增</a>
			<a id="btnAttrInsert" class="easyui-linkbutton" iconCls="icon-redo">插入</a>
			<a id="btnAttrDelete" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			<a id="btnAttrUp" class="easyui-linkbutton" iconCls="icon_up">上移</a>
			<a id="btnAttrDown" class="easyui-linkbutton" iconCls="icon_down">下移</a>
		</div>
		<table id="gridAttrs" class="easyui-datagrid" fit="true" pagination="false">
			<thead>
				<tr>
					<th field="attrName" title="属性" editor="text" width="100"/>
					<th field="attrValue" title="值" editor="text" width="200"/>
				</tr>
			</thead>
		</table>
	</div>
	
	<div region="south" title="预览" iconCls="icon-preview" style="height:150px;">
		<div id="preview" class="easyui-panel" fit="true">
		</div>
	</div>
	
</div>