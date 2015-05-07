<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {

		function endEdit() {
			var nodeId = $("#formMenu").data("menuId");
			var node = $("#treeMenus").tree("find", nodeId);
			if (nodeId && node) {
				$.extend(node.attributes.data, $("#formMenu").form("getData"));
				node.text = node.attributes.data.name;
				$("#treeMenus").tree("update", node);
			}
		};
		
		//选择menu显示信息
		$("#treeMenus").tree("options").onSelect = function(node) {
			$.fn.tree.defaults.onSelect.apply(this, [node]);
			endEdit();
			$("#formMenu").form("setData", node.attributes.data);
			$("#formMenu").data("menuId", node.id);
		};
		
		//编辑完menu更新tree text
		$("#inputMenuName").bind("blur", function() {
			endEdit();
		});
		
		
		//新增
		$("#btnNew").click(function() {
			endEdit();
			$("#treeMenus").tree("appendAfterSelected", {});
		});

		//插入
		$("#btnInsert").click(function() {
			endEdit();
			$("#treeMenus").tree("insertBeforeSelected", {});
		});

		//新增子节点
		$("#btnNewChild").click(function() {
			endEdit();
			$("#treeMenus").tree("addChildToSelected", {});
		});

		//删除
		$("#btnDelete").click(function() {
			$("#formMenu").form("setData", {});
			$("#formMenu").removeData("menuId");
			$("#treeMenus").tree("removeSelected");
		});

		//上移
		$("#btnUp").click(function() {
			$("#treeMenus").tree("moveSelectedUp");
		});

		//下移
		$("#btnDown").click(function() {
			$("#treeMenus").tree("moveSelectedDown");
		});

		//左移
		$("#btnLeft").click(function() {
			$("#treeMenus").tree("moveSelectedLeft");
		});

		//右移
		$("#btnRight").click(function() {
			$("#treeMenus").tree("moveSelectedRight");
		});
		
		
		//保存
		$("#btnSave").click(function() {
			endEdit();
			var menus = $("#treeMenus").tree("getChanges");
			if (menus.length == 0) {
				$.messager.alert("提示", "未修改数据", "warning");
				return;
			}
			CommonSaveManager.saveTreeData(menus, "uuid", "parentUuid", function() {
				$("#formMenu").form("setData", {});
				$("#formMenu").removeData("menuId");
				$("#treeMenus").tree("reload");
			});
		});

		//取消
		$("#btnCancel").click(function() {
			$("#formMenu").form("setData", {});
			$("#formMenu").removeData("menuId");
			$("#treeMenus").tree("reload");
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
			<a id="btnUp" class="easyui-linkbutton" iconCls="icon_up">上移</a>
			<a id="btnDown" class="easyui-linkbutton" iconCls="icon_down">下移</a>
			<a id="btnLeft" class="easyui-linkbutton" iconCls="icon_left">左移</a>
			<a id="btnRight" class="easyui-linkbutton" iconCls="icon_right">右移</a>
			<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save">保存</a>
			<a id="btnCancel" class="easyui-linkbutton" iconCls="icon-reload">取消</a>
		</div>
	</div>
	
	<div region="center" title="菜单树" iconCls="icon-tree">
		<ul id="treeMenus" class="easyui-tree" dnd="true" queryType="WpMainMenuModel", orderBy="seq",
				idField="uuid", textField="name", parentField="parentUuid", seqField="seq"/>
	</div>
	
	<div region="east" title="菜单属性" iconCls="icon-edit" style="width:800px;">
		<form id="formMenu" class="easyui-form wp-config" columns="2">
			<input id="inputMenuName" name="name" title="名称"/>
			<input name="type" title="页面类型" class="easyui-combobox" codetype="WP_PAGETYPE"/>
			<input name="url" title="URL" colspan="2"/>
		</form>
	</div>
	
</div>