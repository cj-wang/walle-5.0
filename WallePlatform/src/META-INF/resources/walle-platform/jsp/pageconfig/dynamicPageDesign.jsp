<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript">
	
	$(function() {
		if (typeof getCurrentTabTitle != "undefined") {
			$("#main").closest(".window-body").dialog("setTitle", "动态页面配置 - " + getCurrentTabTitle());
		}
		
		var url = "<%= request.getParameter("url") %>";
		if (url.indexOf("_dynamicPageUrl=") >= 0) {
			url = url.substring(url.indexOf("_dynamicPageUrl=") + 16);
		}

		var modelOk = false;
		
		var initialed = false;

		var staticModel = {
				_class : "WpDynamicPageModel",
				url : url,
				contentType : "static"
		};
		var htmlModel = {
				_class : "WpDynamicPageModel",
				url : url,
				contentType : "html"
		};
		var jsModel = {
				_class : "WpDynamicPageModel",
				url : url,
				contentType : "js"
		};
		var commentModel = {
				_class : "WpDynamicPageModel",
				url : url,
				contentType : "comment"
		};
		
		function getStaticEditor() {
			return $("#staticEditorFrame")[0].contentWindow.editor;
		};
		
		function getHtmlEditor() {
			return $("#htmlEditorFrame")[0].contentWindow.editor;
		};
		
		function getJsEditor() {
			return $("#jsEditorFrame")[0].contentWindow.editor;
		};
		
		function getHtmlEditorHis() {
			return $("#htmlEditorFrameHis")[0].contentWindow.editor;
		};
		
		function getJsEditorHis() {
			return $("#jsEditorFrameHis")[0].contentWindow.editor;
		};
		
		CommonQueryManager.query({
			queryType : "WpDynamicPageModel",
			queryFields : [{
				fieldName : "url",
				fieldStringValue : url
			}]
		}, function(result) {
			$.each(result.dataList, function(index, data) {
				if (data.contentType == "static") {
					staticModel = data;
				} else if (data.contentType == "html") {
					htmlModel = data;
				} else if (data.contentType == "js") {
					jsModel = data;
				} else if (data.contentType == "comment") {
					commentModel = data;
				}
			});
			modelOk = true;
		});

		$("#gridHis").datagrid("reload");
		
		function initEditor() {
			if (modelOk 
					&& getStaticEditor() && getStaticEditor().initialed 
					&& getHtmlEditor() && getHtmlEditor().getSession 
					&& getJsEditor() && getJsEditor().getSession
					&& getHtmlEditorHis() && getHtmlEditorHis().getSession 
					&& getJsEditorHis() && getJsEditorHis().getSession) {
				
				getStaticEditor().setData(staticModel.content);
				
				getHtmlEditor().getSession().setMode("ace/mode/html");
				getHtmlEditor().getSession().setValue(htmlModel.content);
				getHtmlEditor().navigateFileStart();
				
				getJsEditor().getSession().setMode("ace/mode/javascript");
				getJsEditor().getSession().setValue(jsModel.content);
				getJsEditor().navigateFileStart();
				
				getHtmlEditorHis().getSession().setMode("ace/mode/html");
				getHtmlEditorHis().setReadOnly(true);
				
				getJsEditorHis().getSession().setMode("ace/mode/javascript");
				getJsEditorHis().setReadOnly(true);
				
				initialed = true;
			} else {
				setTimeout(initEditor, 500);
			}
		};
		initEditor();
		
		function applyTemplate(editor, content) {
			editor.getSelection().selectLineStart();
			var indent = editor.getSession().getTextRange(editor.getSelectionRange());
			if ($.trim(indent) == "") {
				content = content.replace(/\n/gm, "\n" + indent);
				var cursorIndex = content.indexOf("|");
				editor.insert(indent + content);
				if (cursorIndex >= 0) {
					editor.findPrevious({
						needle : "|"
					});
					editor.insert("");
				}
				editor.focus();
			} else {
				editor.navigateLineEnd();
				editor.navigateRight();
				applyTemplate(content);
			}
		};


		//双击插入模版
		$("#treeTemplatesTag").tree("options").onDblClick = function(node) {
			$.fn.tree.defaults.onDblClick.apply(this, [node]);
			var content = node.attributes.data.content;
			if (content) {
				applyTemplate(getHtmlEditor(), content);
			}
		};
		
		//双击插入模版
		$("#treeTemplatesJs").tree("options").onDblClick = function(node) {
			$.fn.tree.defaults.onDblClick.apply(this, [node]);
			var content = node.attributes.data.content;
			if (content) {
				applyTemplate(getJsEditor(), content);
			}
		};
		
		var toClose = false;
		
		//保存
		$("#btnSave").click(function() {
			if (! initialed) {
				return;
			}
			staticModel.content = getStaticEditor().getData();
			htmlModel.content = getHtmlEditor().getValue();
			jsModel.content = getJsEditor().getValue();
			WpDynamicPageManager.saveAll([staticModel, htmlModel, jsModel], function(result) {
				$("#main").closest(".window-body").data("refresh", true);
				$.each(result, function(index, data) {
					if (data.contentType == "static") {
						staticModel = data;
					} else if (data.contentType == "html") {
						htmlModel = data;
					} else if (data.contentType == "js") {
						jsModel = data;
					}
				});
				$.messager.toast("提示", "保存成功", "info");
			});
		});

		//保存并关闭
		$("#btnSaveAndClose").click(function() {
			if (! initialed) {
				return;
			}
			staticModel.content = getStaticEditor().getData();
			htmlModel.content = getHtmlEditor().getValue();
			jsModel.content = getJsEditor().getValue();
			WpDynamicPageManager.saveAll([staticModel, htmlModel, jsModel], function(result) {
				$("#main").closest(".window-body").data("refresh", true);
				$("#main").closest(".window-body").dialog("close");
			});
		});
		
		//保存并在新窗口打开
		$("#btnSaveAndOpenInNewWindow").click(function() {
			if (! initialed) {
				return;
			}
			staticModel.content = getStaticEditor().getData();
			htmlModel.content = getHtmlEditor().getValue();
			jsModel.content = getJsEditor().getValue()
			WpDynamicPageManager.saveAll([staticModel, htmlModel, jsModel], {
				async : false,
				callback : function(result) {
					$("#main").closest(".window-body").data("refresh", true);
					$.each(result, function(index, data) {
						if (data.contentType == "static") {
							staticModel = data;
						} else if (data.contentType == "html") {
							htmlModel = data;
						} else if (data.contentType == "js") {
							jsModel = data;
						}
					});
					$.messager.toast("提示", "保存成功", "info");
					if (typeof openCurrentTabInNewWindow != "undefined") {
						openCurrentTabInNewWindow();
					}
				}
			});
		});
		
		//发布
		$("#btnRelease").click(function() {
			if (! initialed) {
				return;
			}
			$("#dialogSave").dialog("open");
			$("#comment")[0].value = "";
			$("#comment").focus();
			toClose = false;
		});
		
		//关闭
		$("#btnClose").click(function() {
			$(this).closest(".window-body").dialog("close");
		});
		
		//保存窗口确定
		$("#btnDialogSaveOk").click(function() {
			staticModel.content = getStaticEditor().getData();
			htmlModel.content = getHtmlEditor().getValue();
			jsModel.content = getJsEditor().getValue();
			var comment = $("#comment")[0].value;
// 			if (! comment) {
// 				$.messager.toast("提示", "请输入备注信息", "warning");
// 				return;
// 			}
			commentModel.content = comment;
			WpDynamicPageManager.release([staticModel, htmlModel, jsModel, commentModel], function(result) {
				$("#main").closest(".window-body").data("refresh", true);
				if (toClose) {
					$("#main").closest(".window-body").dialog("close");
				} else {
					$.each(result, function(index, data) {
						if (data.contentType == "static") {
							staticModel = data;
						} else if (data.contentType == "html") {
							htmlModel = data;
						} else if (data.contentType == "js") {
							jsModel = data;
						} else if (data.contentType == "comment") {
							commentModel = data;
						}
					});
					$.messager.toast("提示", "发布成功", "info");
					$("#dialogSave").dialog("close");
					$("#gridHis").datagrid("reload");
					$("#staticHtmlHisIFrame")[0].src = "about:blank";
					getHtmlEditorHis().getSession().setValue("");
					getJsEditorHis().getSession().setValue("");
				}
			});
		});
		
		//保存窗口取消
		$("#btnDialogSaveCancel").click(function() {
			$("#dialogSave").dialog("close");
		});
		
		//刷新his
		$("#btnReloadHis").click(function() {
			$("#gridHis").datagrid("reload");
			$("#staticHtmlHisIFrame")[0].src = "about:blank";
			getHtmlEditorHis().getSession().setValue("");
			getJsEditorHis().getSession().setValue("");
		});
		
		//tab切换
		$("#tabs").tabs("options").onSelect = function(title) {
			$.fn.tabs.defaults.onSelect.apply(this, [title]);
			if (title == "预览") {
				var staticHtml = getStaticEditor().getData();
				var staticHtmlIFrame = "";
				if (staticHtml) {
					staticHtmlIFrame = "<iframe id='staticHtmlIFrame' style='width:100%; height:100%; border:0px;'></iframe>";
				}
				var html = "<script type='text/javascript'>\n" +
				getJsEditor().getValue() + "\n" +
				"<" + "/script>\n" + 
				staticHtmlIFrame + "\n" +
				getHtmlEditor().getValue() + "\n";
				var $preview = $("#preview");
				$preview.addClass("panel-loading").text($.fn.panel.defaults.loadingMessage);
				setTimeout(function() {
					html = $.fn.panel.defaults.extractor.apply($preview[0], [html]);
					$.parser.parse($preview.html(html));
					if (staticHtml) {
						var doc = $("#staticHtmlIFrame", $preview)[0].contentWindow.document;
						doc.open();
						doc.write("<html><head>");
						doc.write("<link rel='stylesheet' type='text/css' href='<%=request.getContextPath() %>/ckeditor/contents.css' />");
						doc.write("</head><body class='cke_editable'>");
						doc.write(staticHtml);
						doc.write("</body></html>");
						doc.close();
					}
					$preview.panel("options").onLoadAsync.apply($preview[0]);
					$preview.removeClass("panel-loading");
				}, 0);
			}
		};
		
		//gridHis选中行
		$("#gridHis").datagrid("options").onSelect = function(rowIndex, rowData) {
		    $.fn.datagrid.defaults.onSelect.apply(this, [rowIndex, rowData]);
			$("#staticHtmlHisIFrame")[0].src = "about:blank";
			getHtmlEditorHis().getSession().setValue("");
			getJsEditorHis().getSession().setValue("");
			CommonQueryManager.query({
				queryType : "WpDynamicPageHistoryModel",
				queryFields : [{
					fieldName : "url",
					fieldStringValue : url
				}, {
					fieldName : "createTime",
					fieldStringValue : rowData.createTime
				}]
			}, function(result) {
				$.each(result.dataList, function(index, data) {
					if (data.contentType == "static") {
						var doc = $("#staticHtmlHisIFrame")[0].contentWindow.document;
						doc.open();
						doc.write("<html><head>");
						doc.write("<link rel='stylesheet' type='text/css' href='<%=request.getContextPath() %>/ckeditor/contents.css' />");
						doc.write("</head><body class='cke_editable'>");
						doc.write(data.content);
						doc.write("</body></html>");
						doc.close();
					} else if (data.contentType == "html") {
						getHtmlEditorHis().getSession().setValue(data.content);
						getHtmlEditorHis().navigateFileStart();
					} else if (data.contentType == "js") {
						getJsEditorHis().getSession().setValue(data.content);
						getJsEditorHis().navigateFileStart();
					}
				});
			});
		};
		
		//gridHis取消选中行
		$("#gridHis").datagrid("options").onUnselectAll = function(rows) {
		    $.fn.datagrid.defaults.onUnselectAll.apply(this, [rows]);
			$("#staticHtmlHisIFrame")[0].src = "about:blank";
			getHtmlEditorHis().getSession().setValue("");
			getJsEditorHis().getSession().setValue("");
		};
		
	});
	
</script>

<div id="main" class="easyui-layout" fit="true">
	<div region="north" border="false">
		<div class="datagrid-toolbar">
			<a id="btnSave" class="easyui-linkbutton" iconCls="icon-save">保存</a>
			<a id="btnSaveAndOpenInNewWindow" class="easyui-linkbutton" iconCls="icon-save">保存并调试页面</a>
			<a id="btnSaveAndClose" class="easyui-linkbutton" iconCls="icon-save">保存并返回主页</a>
			<a id="btnRelease" class="easyui-linkbutton" iconCls="icon_active">发布</a>
			<a id="btnClose" class="easyui-linkbutton" iconCls="icon-ok">关闭</a>
		</div>
	</div>
	
	<div region="center">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			
			<div title="静态页面" iconCls="myCustomerIcon_searchForm">
				<div class="easyui-layout" fit="true">
					<div region="center">
						<iframe id="staticEditorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/ckEditor.jsp" style="width:100%; height:100%; border:none;"></iframe>
					</div>
				</div>
			</div>
			
			<div title="页面布局 HTML" iconCls="myCustomerIcon_searchForm">
				<div class="easyui-layout" fit="true">
					<div region="west" title="模版" iconCls="icon-tree" style="width:300px;">
						<ul id="treeTemplatesTag" class="easyui-tree" queryType="WpHtmlTemplateTagModel" orderby="seq"
								idField="uuid" textField="name" parentField="parentUuid" titleField="content"/>
					</div>
					
					<div region="center">
						<iframe id="htmlEditorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"></iframe>
					</div>
				</div>
			</div>
			
			<div title="事件处理 JavaScript" iconCls="icon-edit">
				<div class="easyui-layout" fit="true">
					<div region="west" title="模版" iconCls="icon-tree" style="width:300px;">
						<ul id="treeTemplatesJs" class="easyui-tree" queryType="WpHtmlTemplateJsModel" orderby="seq"
								idField="uuid" textField="name" parentField="parentUuid" titleField="content"/>
					</div>
					
					<div region="center">
						<iframe id="jsEditorFrame" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"></iframe>
					</div>
				</div>
			</div>
			
			<div id="preview" title="预览" iconCls="icon-preview">
			</div>
			
			<div id="history" title="历史版本" iconCls="icon-convert">
				<div class="easyui-layout" fit="true">
					<div region="north" border="false" style="height:200px;">
						<div class="datagrid-toolbar">
							<a id="btnReloadHis" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
						</div>
				        <table id="gridHis" class="easyui-datagrid wp-config" fit="true" singleSelect="true"
				                queryType="WpDynamicPageHistoryModel" orderby="createTime desc"
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
							
							<div title="静态页面 HTML" iconCls="myCustomerIcon_searchForm">
								<iframe id="staticHtmlHisIFrame" style="width:100%; height:100%; border:none;"></iframe>
							</div>
							
							<div title="页面布局 HTML" iconCls="myCustomerIcon_searchForm">
								<iframe id="htmlEditorFrameHis" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"></iframe>
							</div>
							
							<div title="事件处理 JavaScript" iconCls="icon-edit">
								<iframe id="jsEditorFrameHis" src="<%= request.getContextPath() %>/walle-platform/jsp/pub/aceEditor.jsp" style="width:100%; height:100%; border:none;"></iframe>
							</div>
							
<!-- 							<div id="previewHis" title="预览" iconCls="icon-preview"> -->
<!-- 							</div> -->
							
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
