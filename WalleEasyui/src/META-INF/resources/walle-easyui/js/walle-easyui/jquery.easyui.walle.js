

/********** global cache for select codes **********/
if (window.top.document === document) {
	$(document).data("SELECT_CODE_VALUES", {});
}
function getTopWindowSelectCodeValues() {
	if (window.top.document === document) {
		return $(document).data("SELECT_CODE_VALUES");
	} else {
		if (window.top.getTopWindowSelectCodeValues) {
			return window.top.getTopWindowSelectCodeValues();
		} else {
			return {};
		}
	}
};
var SELECT_CODE_VALUES = getTopWindowSelectCodeValues();

if (window.top.document === document) {
	$(document).data("SELECT_CODE_DATAS", {});
}
function getTopWindowSelectCodeDatas() {
	if (window.top.document === document) {
		return $(document).data("SELECT_CODE_DATAS");
	} else {
		if (window.top.getTopWindowSelectCodeDatas) {
			return window.top.getTopWindowSelectCodeDatas();
		} else {
			return {};
		}
	}
};
var SELECT_CODE_DATAS = getTopWindowSelectCodeDatas();

if (typeof SELECT_CODE_DEFINITIONS == "undefined") {
	if (window.top.document === document) {
		$(document).data("SELECT_CODE_DEFINITIONS", {});
	}
	function getTopWindowSelectCodeDefinitions() {
		if (window.top.document === document) {
			return $(document).data("SELECT_CODE_DEFINITIONS");
		} else {
			if (window.top.getTopWindowSelectCodeDefinitions) {
				return window.top.getTopWindowSelectCodeDefinitions();
			} else {
				return {};
			}
		}
	};
	var SELECT_CODE_DEFINITIONS = getTopWindowSelectCodeDefinitions();
}


/********** function authorization **********/
if (window.top.document === document) {
	$(document).data("AUTHORIZED_FUNCTIONS", []);
}
function getTopWindowAuthorizedFunctions() {
	if (window.top.document === document) {
		return $(document).data("AUTHORIZED_FUNCTIONS");
	} else {
		if (window.top.getTopWindowAuthorizedFunctions) {
			return window.top.getTopWindowAuthorizedFunctions();
		} else {
			return [];
		}
	}
};
var AUTHORIZED_FUNCTIONS = getTopWindowAuthorizedFunctions();

function checkFunctionAuthorization(funcCode) {
	return AUTHORIZED_FUNCTIONS.indexOf(funcCode) >= 0;
}


/********** PREVENT_REINIT_PLUGINS **********/
var PREVENT_REINIT_PLUGINS = false;

$(function() {
	$.each($.parser.plugins, function(index, plugin) {
		$.fn["__" + plugin] = $.fn[plugin];
		$.fn[plugin] = function(options, param) {
			if (PREVENT_REINIT_PLUGINS && typeof options != "string") {
				return this.each(function() {
					var state = $.data(this, plugin);
					if (state) {
						if (options) {
							$.extend(state.options, options);
							if ($.data(this, "combo")) {
								$.extend($.data(this, "combo").options, options);
							};
						}
					} else {
						$.fn["__" + plugin].apply($(this), [options, param]);
					}
				});
			} else {
				return $.fn["__" + plugin].apply(this, [options, param]);
			}
		};
		$.extend($.fn[plugin], $.fn["__" + plugin]);
	});
});


(function($) {

	/********** DWR **********/
	dwr.message = {
			reLoginMsg : "Error invoking service. Please re-login and try again."
	};
	
	dwr.util.useLoadingMessage();
	
	dwr.engine.setErrorHandler(function(message, ex) {
		dwr.engine._debug("Error: " + ex.name + ", " + ex.message, true);
		if (message == null || message == "") {
			$.messager.alert("Message", "A server error has occured.", "warning");
		} else if (message.indexOf("0x80040111") != -1) {
			// Ignore NS_ERROR_NOT_AVAILABLE if Mozilla is being narky
			dwr.engine._debug(message);
		} else {
			$.messager.alert("Message", message, "warning");
		}
	});
	
	dwr.engine.setTextHtmlHandler(function() {
		$.messager.confirm("Message", dwr.message.reLoginMsg, function(b) {
			if (b) {
				window.location.reload();
			}
		});
	});

	
	/********** global shortcuts **********/
	//8:BackSpace; 9:Tab; 13:Return;
	//16:Shift; 17:Ctrl; 18:Alt;
	//27:Esc; 32:Space;
	//37:Left; 38:Up; 39:Right; 40:Down;
	//65-90:A-Z;
	document.onkeydown = function(event) {
		if (! event) {
			event = window.event;
		}
		if (event.keyCode == 8) {
			if (event.target && event.target.type != "text"
					&& event.target.type != "textarea"
					&& event.target.type != "password"
					&& event.target.contentEditable != "true") {
				return false;
			}
			if (event.srcElement && event.srcElement.type != "text"
					&& event.srcElement.type != "textarea"
					&& event.srcElement.type != "password"
					&& event.srcElement.contentEditable != "true") {
				return false;
			}
		}
		if (event.altKey && (event.keyCode == 37 || event.keyCode == 39)) {
			return false;
		}
		if (event.ctrlKey && event.keyCode >= 65 && event.keyCode <= 90) {
			var keyChar = String.fromCharCode(event.keyCode);
			if (keyChar != "C" && keyChar != "X" && keyChar != "V" && keyChar != "Z") {
				var $topWindow = $("body");
				$("div.panel[style*='z-index']:visible").each(function() {
					if ($(this).css("z-index") > ($topWindow.css("z-index") == "auto" ? 0 : $topWindow.css("z-index"))) {
						$topWindow = $(this);
					}
				});
				$topWindow.find("a[key='" + keyChar + "']:visible:first").click();
				return false;
			}
		}
		if (event.ctrlKey && event.keyCode >= 37 && event.keyCode <= 40) {
			var $focus = $(":focus");
			if ($focus.closest(".datagrid-editable").size() > 0) {
				event.preventDefault();
				switch (event.keyCode) {
				case 37 : //left
				case 39 : //right
					var $inputs = $focus.closest(".datagrid-row-editing").find("input:visible");
					var index = $inputs.index($focus);
					if (event.keyCode == 37) {
						index--;
					} else {
						index++;
					}
					if (index >= $inputs.size()) {
						index -= $inputs.size();
					}
					$inputs.eq(index).focus();
					break;
				case 38 : //up
				case 40 : //down
					var columnIndex = $focus.closest("td[field]").index();
					var $rows = $focus.closest("tr.datagrid-row-editing").parent().children();
					var rowIndex = $focus.closest("tr.datagrid-row-editing").index();
					if (event.keyCode == 38) {
						rowIndex--;
					} else {
						rowIndex++;
					}
					if (rowIndex >= $rows.size()) {
						rowIndex -= $rows.size();
					}
					$rows.eq(rowIndex).children()[columnIndex].click();
					break;
				}
			}
		}
	};
	
	function textSelected() {
		if ($.browser.msie) {
			var selection = document.selection;
			if (selection.type == "Text") {
				return true;
			}
		} else if ($.browser.safari) {
			var selection = window.getSelection();
			if (selection.type == "Range") {
				return true;
			}
		} else if ($.browser.mozilla) {
			var selection = window.getSelection();
			if (selection.focusNode && selection.focusNode.nodeName == "#text" && ! selection.isCollapsed) {
				return true;
			}
		}
		return false;
	}

	$(function() {
//		$(document).find("input:visible:first").focus();
		
		$("body").bind("contextmenu", function(e) {
			if ($(e.target).is("input:not([type]), input[type='text'], textarea, img")) {
				return true;
			}
			if (textSelected()) {
				return true;
			}
			return false;
		});
	});
	
	
	/********** add a bind event method for each plugin **********/
	$.each($.parser.plugins, function(index, plugin) {
		$.fn[plugin].methods.bind = function(jq, eventHandlers) {
			return jq.each(function() {
				var _this = this;
				$.each(eventHandlers, function(event, handler) {
					$.data(_this, plugin).options[event] = function() {
						$.fn[plugin].defaults[event].apply(_this, arguments);
						return handler.apply(_this, arguments);
					};
					if ($.data(_this, "combo")) {
						$.data(_this, "combo").options[event] = function() {
							$.fn["combo"].defaults[event].apply(_this, arguments);
							return handler.apply(_this, arguments);
						};
					}
				});
			});
		};
	});

	
	/********** parser **********/
	$.parser.onBefore = function(context, findings) {
		
		//disable unwanted scrolling bars in Chrome
		$("[fit='true']", context).parent().css("overflow", "hidden");

		$("[tooltip]", context).tooltip();
		$("[placeholder]", context).placeholder();
		initToolbars($(".datagrid-toolbar", context));

		initForms(findings.form);
		initDatagrids(findings.datagrid.add(findings.treegrid));
		delete findings.datagrid;
		delete findings.treegrid;
		initTrees(findings.tree);
		delete findings.tree;
		initComboMultis(findings.combobox.add(findings.combogrid).add(findings.combotree));
		initComboboxes(findings.combobox, findings.th_combobox);
		delete findings.combobox;
		initCombogrids(findings.combogrid, findings.th_combogrid);
		delete findings.combogrid;
		initCombotrees(findings.combotree, findings.th_combotree);
		delete findings.combotree;
	};

	$.parser.onComplete = function(context, findings) {
		initDialogs(findings.dialog);
		initLinkbuttons(findings.linkbutton);
//		initSearchboxes(findings.searchbox);
		initNumberboxes(findings.numberbox, findings.th_numberbox);
	};
	
	
	/********** datagrid **********/
	var datagridDefaults = {
		pagination : true,
		rownumbers : true,
		singleSelect : false,
		url : contextPath + "/JsonFacadeServlet",
		parameters : {
			parameters : {
				queryInfo : {}
			}
		},
		
		refreshText : "Refresh",
		resetSortText : "Default Sort Order",
		exportExcelCurrentPageText : "Export Excel (Current Page)",
		exportExcelAllText : "Export Excel (All)",
		showColumnsText : "Show Columns",
		exportExcelErrorMsg : "Can not export Excel for this table.",
		dataChangedMsg : "Data changed. Discard changes?",
		loadDataErrorMsg : "Error loading data.",
		reLoginMsg : "Error loading data. Please re-login and try again.",
		
		//query parameters
		onBeforeLoad : function(row, param) {
			//datagrid.onBeforeLoad(param)
			//treegrid.onBeforeLoad(row, param)
			if (! param) {
				param = row;
			}
			var $datagrid = $(this);
			//end edit before reload
			$datagrid.datagrid("forceEndEdit");
			if ($datagrid.datagrid("getChanges").length > 0) {
				if (confirm($.fn.datagrid.defaults.dataChangedMsg) == false) {
					return false;
				}
			}
			//query parameters
			var options = $datagrid.datagrid("options");
			var parameters = options.parameters;
			if (options.url == datagridDefaults.url) {
				if (! parameters) {
					return false;
				}
				if (! parameters.parameters.queryInfo.queryType) {
					$datagrid.datagrid("rejectChanges");
					return false;
				}
			} else {
				if (! parameters.parameters.queryInfo.queryType) {
					return;
				}
			}
			var queryFields = [];
			if (options.queryFields) {
				queryFields = queryFields.concat(options.queryFields);
			}
			if (options.commonQueryFields) {
				queryFields = queryFields.concat(options.commonQueryFields);
			}
			if (options.paramForm) {
				var $parent = $datagrid.parent();
				var $paramForm = null;
				while (true) {
					$paramForm = $parent.find("#" + options.paramForm);
					if ($paramForm.size() > 0) {
						break;
					} else {
						$parent = $parent.parent();
						if ($parent.size() == 0) {
							break;
						}
					}
				}
				if ($paramForm) {
					var paramFormQueryFields = $paramForm.form("getQueryFields");
					if (paramFormQueryFields) {
						queryFields = queryFields.concat(paramFormQueryFields);
					}
				}
			}
			var pagingInfo = null;
			if (param.rows) {
				pagingInfo = {
					pageSize : param.rows,
					currentPage : param.page
				};
			}
			$.extend(parameters.parameters.queryInfo, {
				queryFields : queryFields,
				orderBy : (param.sort ? param.sort + " " + param.order : (options.orderBy || "")),
				pagingInfo : pagingInfo
			});
			for (var key in param) {
				delete param[key];
			}
			param.json_parameters = $.toJSON(parameters);
		},
		
		//query result
		loadFilter : function(data) {
			if (data.exception) {
				$.messager.alert("Message", $.fn.datagrid.defaults.loadDataErrorMsg + data.exception, "warning");
				return null;
			}
			if (data.result) {
				$.extend(true, SELECT_CODE_VALUES, data.result.selectCodeValues);
				$.extend(data, {
					total : data.result.pagingInfo ? data.result.pagingInfo.totalRows : undefined,
					rows : data.result.dataList
				});
			}
			var $datagrid = $(this);
			if ($datagrid.hasClass("easyui-treegrid") && data.rows) {
				var options = $datagrid.treegrid("options");
				var parentField = options.parentField || "parentId";
				var iconClsField = options.iconClsField || "iconCls";
				$.each(data.rows, function(index, row) {
					if (row[parentField] && row[parentField] != "0") {
						row._parentId = row[parentField];
					}
					if (row[iconClsField]) {
						row.iconCls = row[iconClsField];
					}
				});
			}
			return data;
		},
		
		onLoadSuccess : function(row, data) {
			//datagrid.onLoadSuccess(data)
			//treegrid.onLoadSuccess(row, data)
			var $datagrid = $(this);
			if (! data) {
				data = row;
			}
			$(this).datagrid("setCurrentRow", undefined);
			$(this).data("lastSelectedIndex", null);
			if (! data) {
				return;
			}
			groupDatagrid(this, data.rows);
			$(this).datagrid("refreshFooter");
			if ($datagrid.hasClass("easyui-treegrid")) {
				var previousSelectedId = $datagrid.data("selectedId");
				if (previousSelectedId) {
					$datagrid.treegrid("select", previousSelectedId);
				}
			}
		},
		
		onLoadError : function() {
			$.messager.confirm("Message", $.fn.datagrid.defaults.reLoginMsg, function(b) {
				if (b) {
					window.location.reload();
				}
			});
		},

		//resize cached editor cell or viewer cell on column resizing
		onResizeColumn : function(field, width) {
			resizeDatagridEditor(this, field, width);
		},

		//fit columns width
		view : {
			_onAfterRender : $.fn.datagrid.defaults.view.onAfterRender,
			
			onAfterRender : function(target) {
				$.fn.datagrid.defaults.view._onAfterRender(target);
//				setTimeout(function() {
					fitColumnWidth(target);
//				}, 0);
			}
		},
		
		//formatters
		formatter : function (value, rowData, rowIndex) {
			if (this.content) {
				if (this.content.indexOf("$value") >= 0) {
					if (value) {
						return this.content.replace(/\$value/g, value);
					} else {
						return null;
					}
				} else {
					return this.content;
				}
			}
			if (this.codeType && value) {
				var field = this.field;
				var codeType = this.codeType;
				if (SELECT_CODE_VALUES[codeType] && SELECT_CODE_VALUES[codeType][value]) {
					return SELECT_CODE_VALUES[codeType][value];
				} else {
					var selectCodeKeys = {};
					selectCodeKeys[codeType] = value;
					SelectCodeManager.getSelectCodeValuesByKeys(selectCodeKeys, function(result) {
						$.extend(true, SELECT_CODE_VALUES, result);
						value = value + "";
						if (value.split(",").length > 1) {
							var values = value.split(",");
							var texts = [];
							for (var i = 0; i < values.length; i++) {
								texts.push(result[codeType][values[i]] || values[i]);
							}
							SELECT_CODE_VALUES[codeType][value] = texts.join(",");
						}
						$("#" + field + "_" + value.replace(/,/g, "_").replace(/ /g, "_") + ", #_").replaceWith(SELECT_CODE_VALUES[codeType][value] || value);
					});
					return "<div id='" + field + "_" + value.replace(/,/g, "_").replace(/ /g, "_") + "'>...</div>";
				}
			}
			if (this.format && value != null && value !== "" && value != undefined) {
				value = $.formatNumber(value, {
					format : this.format
				});
				return "<span style='float:right;'>" + value + "</span>";
			}
			return value;
		},
		
		//editors
		editors : {
			numberbox : {
				init : function(container, options) {
					var editor = $("<input type='text' class='datagrid-editable-input' style='text-align:right;'>")
							.appendTo(container);
					editor.numberbox(options);
					return editor;
				},
				getValue : function(target) {
					return $(target).numberbox("getValue");
				},
				setValue : function(target, value) {
					$(target).numberbox("setValue", value);
				}
			},
			datetimebox : {
				init : function(container, options) {
					var editor = $("<input type='text'>").appendTo(container);
					editor.datetimebox(options);
					return editor;
				},
				destroy : function(target) {
					$(target).datetimebox("destroy");
				},
				getValue : function(target) {
					return $(target).datetimebox("getValue");
				},
				setValue : function(target, value) {
					$(target).datetimebox("setValue", value);
				},
				resize : function(target, width) {
					$(target).datetimebox("resize", width);
				}
			},
			combogrid : {
				init : function(container, options) {
					var editor = $("<input type='text'>").appendTo(container);
					editor.combogrid(options);
					return editor;
				},
				destroy : function(target) {
					$(target).combogrid("destroy");
				},
				getValue : function(target) {
					return $(target).combogrid("getValue");
				},
				setValue : function(target, value) {
					$(target).combogrid("setValue", value);
				},
				resize : function(target, width) {
					$(target).combogrid("resize", width);
				}
			}
		},
		
		onClickRow : function(rowIndex, rowData) {
			var $datagrid = $(this);
			//datagrid.onClickRow(rowIndex, rowData)
			//treegrid.onClickRow(row)
			//clicking only select one row when singleSelect not defined
			if ($datagrid.attr("singleSelect") == undefined) {
				if ($datagrid.hasClass("easyui-treegrid")) {
					//treegrid
					rowData = rowIndex;
					var idField = $datagrid.treegrid("options").idField;
					var rowId = rowData[idField];
					var selectionsId = $datagrid.treegrid("getSelectionsId");
					$.each(selectionsId, function(index, selectedId) {
						if (selectedId != rowId) {
							$datagrid.treegrid("unselect", selectedId);
						}
					});
					if (selectionsId.indexOf(rowId) == -1) {
						$datagrid.treegrid("select", rowId);
					}
				} else {
					//datagrid
					var selectionsIndex = $datagrid.datagrid("getSelectionsIndex");
					$.each(selectionsIndex, function(index, selectedIndex) {
						if (selectedIndex != rowIndex) {
							$datagrid.datagrid("unselectRow", selectedIndex);
						}
					});
					if (selectionsIndex.indexOf(rowIndex) == -1) {
						$datagrid.datagrid("selectRow", rowIndex);
					}
				}
			}
		},
		
		onClickCell : function(rowIndex, field, value) {
			var $datagrid = $(this);
			$datagrid.data("focusField", field);
		},
		
		onSelect : function(rowIndex, rowData) {
			var $datagrid = $(this);
			var options = $datagrid.datagrid("options");
			//datagrid.onSelect(rowIndex, rowData)
			//treegrid.onSelect(id)
			if ($datagrid.hasClass("easyui-treegrid")) {
				var id = rowIndex;
				$datagrid.data("selectedId", id);
			} else {
				//use shift key to multi select rows
				if (! $datagrid.datagrid("options").singleSelect) {
					if ($datagrid.data("shiftKey")) {
						$datagrid.removeData("shiftKey");
						var lastSelectedIndex = $datagrid.data("lastSelectedIndex");
						if (lastSelectedIndex != undefined) {
							if (lastSelectedIndex < rowIndex) {
								for (var i = lastSelectedIndex; i < rowIndex; i++) {
									$datagrid.datagrid("selectRow", i);
								}
							} else {
								for (var i = rowIndex; i < lastSelectedIndex; i++) {
									$datagrid.datagrid("selectRow", i);
								}
							}
						}
					}
					$datagrid.data("lastSelectedIndex", rowIndex);
				}
			}
			if (! options.readonly) {
				if (options.unselectTimer) {
					clearTimeout(options.unselectTimer);
				}
				//begin edit when selected
				var currentRow = $datagrid.datagrid("getCurrentRow");
				if (currentRow != rowIndex) {
					if ($datagrid.datagrid("endEditWithReturn", currentRow)) {
						$datagrid.datagrid("beginEdit", rowIndex);
					} else {
						$datagrid.datagrid("unselectRow", rowIndex);
						$datagrid.datagrid("selectRow", currentRow);
					}
				}
			}
		},
		
		//unselect ends edit
		onUnselect : function(rowIndex, rowData) {
			var $datagrid = $(this);
			var options = $datagrid.datagrid("options");
			//datagrid.onUnselect(rowIndex, rowData)
			//treegrid.onUnselect(id)
			if (! options.readonly) {
				options.unselectTimer = setTimeout(function() {
					if ($datagrid.datagrid("getCurrentRow") == rowIndex) {
						if ($datagrid.datagrid("validateRow", rowIndex)) {
							$datagrid.datagrid("endEdit", rowIndex);
						} else {
							$datagrid.datagrid("selectRow", rowIndex);
						}
					}
					/*
					if ($datagrid.hasClass("easyui-treegrid")) {
						//
					} else {
						//
					}
					*/
				}, 0);
			}
		},
		
		onHeaderContextMenu : function(e, field) {
			$.fn.datagrid.defaults.onRowContextMenu.apply(this, [e, null, null]);
		},
		
		//datagrid.onRowContextMenu
		onRowContextMenu : function(e, rowIndex, rowData) {
			if (textSelected()) {
				return;
			}
			e.preventDefault();
			var $datagrid = $(this);
			var options = $datagrid.datagrid("options");
			if (options.contextMenu) {
				options.contextMenu.menu("show", {
					left : e.pageX,
					top : e.pageY
				});
				return;
			}
			var menu = "<div class='datagrid-context-menu' style='width:200px;'>" +
					"<div id='Refresh'>" + $.fn.datagrid.defaults.refreshText + "</div>" +
					"<div id='ResetSort'>" + $.fn.datagrid.defaults.resetSortText + "</div>" +
					"<div class='menu-sep'></div>" +
					"<div id='ExportExcelCurrentPage'>" + $.fn.datagrid.defaults.exportExcelCurrentPageText + "</div>" +
					"<div id='ExportExcelAll'>" + $.fn.datagrid.defaults.exportExcelAllText + "</div>" +
					"<div class='menu-sep'></div>" +
					"<div><span>" + $.fn.datagrid.defaults.showColumnsText + "</span><div style='width:180px;'>";
			$.each($datagrid.datagrid("getColumnFields"), function(index, field) {
				var columnOption = $datagrid.datagrid("getColumnOption", field);
				if ($.trim(columnOption.title)) {
					menu += "<div id='showColumn_" + field + "'>√ " + columnOption.title + "</div>";
				}
			});
			menu += "</div></div></div>";
			var $menu = $(menu).appendTo($datagrid.closest(".datagrid"));
			options.contextMenu = $menu;
			$menu.menu({
				left : e.pageX,
				top : e.pageY,
				onClick : function(item) {
					switch (item.id) {
					case "Refresh" :
						if ($datagrid.hasClass("easyui-treegrid")) {
							$datagrid.treegrid("reload");
						} else {
							$datagrid.datagrid("reload");
						}
						break;
					case "ResetSort" :
						$datagrid.datagrid("resetSort");
						break;
					case "ExportExcelCurrentPage" :
						$datagrid.datagrid("exportExcel", true);
						break;
					case "ExportExcelAll" :
						$datagrid.datagrid("exportExcel");
						break;
					default :
						if (item.id.indexOf("showColumn_") == 0) {
							var field = item.id.substring(11);
							var $item = $(item.target);
							var $text = $item.children(".menu-text");
							if ($item.data("hidding")) {
								$datagrid.datagrid("showColumn", field);
								$item.data("hidding", false);
								$text.text("√ " + $text.text().substring(1));
							} else {
								$datagrid.datagrid("hideColumn", field);
								$item.data("hidding", true);
								$text.text("　" + $text.text().substring(2));
							}
							return false;
						}
					} 
				}
			}).menu("show");
		},
		
		//treegrid.onContextMenu
		onContextMenu : function(e, row) {
			$.fn.datagrid.defaults.onRowContextMenu.apply(this, [e, null, row]);
		},
		
		onAfterEdit : function(rowIndex, rowData, changes) {
			if (! $.isEmptyObject(changes)) {
				$(this).datagrid("refreshFooter");
			}
		},
		
		onBeginEdit : function(rowIndex, rowData) {
			var $datagrid = $(this);
			var editor;
			if ($datagrid.data("focusField")) {
				editor = $datagrid.datagrid("getColumnEditor", $datagrid.data("focusField"));
				$datagrid.removeData("focusField");
			} else {
				var editors = $datagrid.datagrid("getEditors", rowIndex);
				editor = editors ? editors[0].target : null;
			}
			if (editor) {
				if (editor.hasClass("combo-f")) {
					editor.combo("textbox").focus();
				} else {
					editor.focus();
				}
			}
		}
		
	};
	
	$.extend(true, $.fn.datagrid.defaults, datagridDefaults);
	
	$.extend(true, $.fn.treegrid.defaults, datagridDefaults, {
		view : {
			_render : $.fn.treegrid.defaults.view.render,
			
			render : function(target, container, frozen) {
				if (this.treeNodes) {
					this._render(target, container, frozen);
				} else {
					$(container).append("<e cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>");
				}
			}
		}
	});

	//use cached editors
	for (var editorType in $.fn.datagrid.defaults.editors) {
		var editor = $.fn.datagrid.defaults.editors[editorType];
		$.extend(editor, {
			_init : editor.init,
			init : function(container, options) {
				var $datagrid = container.closest(".datagrid-view").children("table.easyui-datagrid,table.easyui-treegrid");
				var field = container.closest("td[field]").attr("field");
				var editorTargets = $datagrid.data("editorTargets");
				$.each(editorTargets[field], function(index, $element) {
					$element.appendTo(container);
				});
				return editorTargets[field][0];
			},
			destroy : function(target) {
				$(target).parent().children().detach();
			},
			_getValue : editor.getValue,
			getValue : function(target) {
				var value = this._getValue(target);
				if ($.trim(value) === "") {
					return null;
				}
				return value;
			},
			_resize : editor.resize,
			resize : function(target, width) {
				var $target = $(target);
//				if (! $target.hasClass("combo-f") && ! $target.is(":visible")) {
//					return;
//				}
//				if ($target.hasClass("combo-f") && ! $target.combo("textbox").is(":visible")) {
//					return;
//				}
				if (this._resize && $target.data("width") != width && $target.width() != width) {
					$target.data("width", width);
					this._resize(target, width);
				}
			}
		});
	}

	//datagrid methods
	$.extend($.fn.datagrid.methods, {
		//commonQuery
		commonQuery : function(jq, queryInfo) {
			return jq.each(function() {
				var $datagrid = $(this);
				var options = $datagrid.datagrid("options");
				options.commonQueryFields = null;
				if (queryInfo) {
					if (queryInfo.paramForm) {
						options.paramForm = queryInfo.paramForm;
					}
					if (queryInfo.queryFields) {
						options.commonQueryFields = queryInfo.queryFields;
						delete queryInfo.queryFields;
					}
					$.extend(options.parameters.parameters.queryInfo, queryInfo);
				}
				if ($datagrid.hasClass("easyui-treegrid")) {
					$datagrid.treegrid("reload");
				} else {
					$datagrid.datagrid("load");
				}
			});
		},
		
		setQueryFields : function(jq, queryFields) {
			return jq.each(function() {
				$(this).datagrid("options").queryFields = queryFields;
			});
		},
		
		fitColumnWidth : function(jq) {
			return jq.each(function() {
				fitColumnWidth(this);
			});
		},
		
		getSelectedIndex : function(jq) {
			var $datagrid = $(jq[0]);
			return $datagrid.datagrid("getRowIndex", $datagrid.datagrid("getSelected"));
		},
		
		getSelectionsIndex : function(jq) {
			var $datagrid = $(jq[0]);
			var rows = $datagrid.datagrid("getSelections");
			var selectionsIndex = [];
			for (var i = 0; i < rows.length; i++) {
				selectionsIndex.push($datagrid.datagrid("getRowIndex", rows[i]));
			}
			return selectionsIndex;
		},
		
		setCurrentRow : function(jq, index) {
			return jq.each(function() {
				if (index == undefined) {
					$(this).removeData("currentRow");
				} else {
					$(this).data("currentRow", index);
				}
			});
		},
		
		getCurrentRow : function(jq) {
			return $(jq[0]).data("currentRow");
		},
		
		//override methods to cache datagrid editors
		_beginEdit : function(jq, index) {
			return jq.each(function() {
				beginEdit(this, index);
			});
		},
		_endEdit : function(jq, index) {
			return jq.each(function() {
				stopEdit(this, index, false);
			});
		},
		_endEditWithReturn : function(jq, index) {
			return stopEdit(jq[0], index, false);
		},
		_cancelEdit : function(jq, index) {
			return jq.each(function() {
				stopEdit(this, index, true);
			});
		},

		beginEdit : function(jq, index) {
			return jq.each(function() {
				var $datagrid = $(this);
				var options = $datagrid.datagrid("options");
				if (options.readonly) {
					return;
				}
				if ($datagrid.datagrid("getCurrentRow") == undefined) {
					if ($datagrid.data("editorsInitOk")) {
						$datagrid.datagrid("setCurrentRow", index);
						$datagrid.datagrid("_beginEdit", index);
						if (options.onBeginEdit) {
							options.onBeginEdit.apply(this, [index, $datagrid.datagrid("getRows")[index]]);
						}
					} else {
						$datagrid.data("toStartEdit", index);
					}
				}
			});
		},
		
		endEdit : function(jq, index) {
			return jq.each(function() {
				var $datagrid = $(this);
				if (index == undefined) {
					index = $datagrid.datagrid("getCurrentRow");
				}
				if ($datagrid.datagrid("_endEditWithReturn", index)) {
					$datagrid.datagrid("setCurrentRow", undefined);
				}
				return jq;
			});
		},
		
		endEditWithReturn : function(jq, index) {
			var $datagrid = $(jq[0]);
			if (index == undefined) {
				index = $datagrid.datagrid("getCurrentRow");
			}
			if ($datagrid.datagrid("_endEditWithReturn", index)) {
				$datagrid.datagrid("setCurrentRow", undefined);
				return true;
			} else {
				return false;
			}
		},
		
		cancelEdit : function(jq, index) {
			return jq.each(function() {
				var $datagrid = $(this);
				if (index == undefined) {
					index = $datagrid.datagrid("getCurrentRow");
				}
				$datagrid.datagrid("_cancelEdit", index);
				$datagrid.datagrid("setCurrentRow", undefined);
				return jq;
			});
		},
		
		forceEndEdit : function(jq, index) {
			return jq.each(function() {
				var $datagrid = $(this);
				if (index == undefined) {
					index = $datagrid.datagrid("getCurrentRow");
				}
				$datagrid.datagrid("setCurrentRow", undefined);
				if (index == undefined) {
					return jq;
				}
				if (! $datagrid.datagrid("_endEditWithReturn", index)) {
					$datagrid.datagrid("_cancelEdit", index);
				}
				return jq;
			});
		},
		
		validateRow : function(jq, index) {
			return validateRow(jq[0], index);
		},
		
		readonly : function(jq, readonly) {
			return jq.each(function() {
				readonly = readonly == undefined ? true : readonly;
				$(this).datagrid("options").readonly = readonly;
			});
		},
		
		validate : function(jq) {
			var $datagrid = $(jq[0]);
			var index = $datagrid.datagrid("getCurrentRow");
			if (index != undefined) {
				return $datagrid.datagrid("validateRow", index);
			}
			return true;
		},
		
		_appendRow : $.fn.datagrid.methods.appendRow,
		
		appendRow : function(jq, row) {
			if (! this.endEditWithReturn(jq)) {
				return jq;
			}
			return jq.each(function() {
				var $datagrid = $(this);
				$datagrid.datagrid("_appendRow", row);
				$datagrid.datagrid("fitColumnWidth");
				var rowcount = $datagrid.datagrid("getRows").length;
				//select and edit
				var $tr = $datagrid.datagrid("options").editConfig.getTr(this, rowcount - 1)[0];
				if ($tr) {
					$tr.click();
				}
			});
		},
		
		_insertRow : $.fn.datagrid.methods.insertRow,
		
		insertRow : function(jq, param) {
			if (! this.endEditWithReturn(jq)) {
				return jq;
			}
			if (param.index == undefined) {
				param.index = 0;
			}
			if (param.index == -1) {
				param.index = 0;
			}
			return jq.each(function() {
				var $datagrid = $(this);
				$datagrid.datagrid("_insertRow", param);
				$datagrid.datagrid("fitColumnWidth");
				//select and edit
				var $tr = $datagrid.datagrid("options").editConfig.getTr(this, param.index)[0];
				if ($tr) {
					$tr.click();
				}
			});
		},
		
		_updateRow : $.fn.datagrid.methods.updateRow,
		
		updateRow : function(jq, param) {
			this.forceEndEdit(jq);
			var target = jq[0];
			var opts = $.data(target, "datagrid").options;
			var insertedRows = $.data(target, "datagrid").insertedRows;
			var updatedRows = $.data(target, "datagrid").updatedRows;
			if (insertedRows.indexOf(param.row) == -1) {
				var oldRow = opts.editConfig.getRow(target, param.index);
				if (insertedRows.indexOf(oldRow) == -1) {
					if (oldRow == param.row) {
						//no way to check if the row has been changed
						updatedRows.push(param.row);
					} else {
						var changed = false;
						for (var field in oldRow) {
							if (oldRow[field] != param.row[field]) {
								changed = true;
								break;
							}
						};
						$.extend(oldRow, param.row);
						param.row = oldRow;
						if (changed) {
							if (updatedRows.indexOf(param.row) == -1) {
								updatedRows.push(param.row);
							}
						}
					}
				}
			}
			this._updateRow(jq, param);
			this.refreshFooter(jq);
			return jq;
		},
		
		_deleteRow : $.fn.datagrid.methods.deleteRow,
		
		deleteRow : function(jq, index) {
			this.forceEndEdit(jq);
			this._deleteRow(jq, index);
			this.refreshFooter(jq);
			return jq;
		},
		
		deleteSelectedRows : function(jq) {
			this.forceEndEdit(jq);
			return jq.each(function() {
				var $datagrid = $(this);
				$.each($datagrid.datagrid("getSelectionsIndex").reverse(), function(index, selectedIndex) {
					$datagrid.datagrid("_deleteRow", selectedIndex);
				});
				$datagrid.datagrid("refreshFooter");
			});
		},
		
		_loadData : $.fn.datagrid.methods.loadData,
		
		loadData : function(jq, data) {
			this.forceEndEdit(jq);
			return this._loadData(jq, data);
		},
		
		_getData : $.fn.datagrid.methods.getData,
		
		getData : function(jq) {
			this.forceEndEdit(jq);
			return this._getData(jq);
		},
		
		_getChanges : $.fn.datagrid.methods.getChanges,
		
		getChanges : function(jq, type) {
			var $datagrid = $(jq[0]);
			this.forceEndEdit(jq);
			var insertedRows = this._getChanges(jq, "inserted");
			var updatedRows = this._getChanges(jq, "updated");
			var deletedRows = this._getChanges(jq, "deleted");
			$.each(insertedRows, function(index, row) {
				row.rowState = "Added";
			});
			$.each(updatedRows, function(index, row) {
				row.rowState = "Modified";
			});
			$.each(deletedRows, function(index, row) {
				row.rowState = "Deleted";
			});
			var data = [];
			if (! type) {
				data = data.concat(insertedRows);
				data = data.concat(updatedRows);
				data = data.concat(deletedRows);
			} else {
				switch (type) {
				case "inserted" :
					data = insertedRows;
				case "updated" :
					data = updatedRows;
				case "deleted" :
					data = deletedRows;
				}
			}
			var queryType = $datagrid.datagrid("options").parameters.parameters.queryInfo.queryType;
			if (queryType) {
				$.each(data, function(index, dataItem) {
					dataItem._class = dataItem._class || queryType;
				});
			}
			return data;
		},
		
		refreshSavedData : function(jq, savedRows) {
			return jq.each(function() {
				var $datagrid = $(this);
				var insertedRows = $datagrid.datagrid("_getChanges", "inserted");
				var updatedRows = $datagrid.datagrid("_getChanges", "updated");
				var refreshRows = [].concat(insertedRows).concat(updatedRows);
				for (var i = 0; i < refreshRows.length; i++) {
					$.each($datagrid.datagrid("getColumnFields", true).concat($datagrid.datagrid("getColumnFields", false)), function(colIndex, colName) {
						savedRows[i][colName] = savedRows[i][colName] || undefined;
					});
					var index = $datagrid.datagrid("getRowIndex", refreshRows[i]);
					$datagrid.datagrid("_updateRow", {
						index : index,
						row : savedRows[i]
					});
				}
				$datagrid.datagrid("acceptChanges");
				$datagrid.datagrid("refreshFooter");
			});
		},
		
		getColumnEditor : function(jq, field) {
			return $(jq[0]).data("editorTargets")[field] ? $(jq[0]).data("editorTargets")[field][0] : null;
		},
		
		setGroupField : function(jq, groupField) {
			return jq.each(function() {
				var $datagrid = $(this);
				$datagrid.datagrid("options").groupField = groupField;
			});
		},
		
		resetSort : function(jq) {
			return jq.each(function() {
				var $datagrid = $(this);
				if ($datagrid.hasClass("easyui-treegrid")) {
					var options = $datagrid.treegrid("options");
				} else {
					var options = $datagrid.datagrid("options");
				}
				$datagrid.datagrid("getPanel").children(".datagrid-view")
				.children(".datagrid-view1,.datagrid-view2").children(".datagrid-header")
				.find(".datagrid-cell")
				.removeClass("datagrid-sort-asc datagrid-sort-desc")
				.find(".datagrid-sort-count").remove();
				options.sortName = undefined;
				options.sortOrder = undefined;
				options.sortCount = undefined;
				if ($datagrid.hasClass("easyui-treegrid")) {
					$datagrid.treegrid("reload");
				} else {
					$datagrid.datagrid("reload");
				}
			});
		},
		
		exportExcel : function(jq, currentPageOnly) {
			return jq.each(function() {
				var $datagrid = $(this);
				var queryParameters = $datagrid.datagrid("options").parameters;
				if (! queryParameters.parameters.queryInfo.queryType) {
					$.messager.alert("Message", $.fn.datagrid.defaults.exportExcelErrorMsg, "warning");
					return;
				}
				var exportParameters = {
						serviceName : "commonQueryManager",
						methodName : "exportExcel",
						parameters : {
							title : "DATA",
							queryInfo : $.extend(true, {}, queryParameters.parameters.queryInfo),
							fieldDefinitions : []
						}
				};
				if (! currentPageOnly) {
					exportParameters.parameters.queryInfo.pagingInfo = null;
				}
				var $headerTr = $datagrid.datagrid("getPanel").children(".datagrid-view")
				.children(".datagrid-view1,.datagrid-view2").children(".datagrid-header").find("tr"); 
				$.each($datagrid.datagrid("getColumnFields", true).concat($datagrid.datagrid("getColumnFields", false)), function(index, field) {
					var columnOption = $datagrid.datagrid("getColumnOption", field);
					if (! columnOption.title) {
						return;
					}
					exportParameters.parameters.fieldDefinitions.push({
						fieldName : columnOption.field,
						label : columnOption.title,
						width : $headerTr.find("td[field='" + columnOption.field + "']").width() + 10
					});
				});
				var json_parameters = $.toJSON(exportParameters);
				//send request
				var form = document.createElement("form");
				form.setAttribute("method", "post");
				form.setAttribute("action", contextPath + "/JsonFacadeServlet");
				var hiddenField = document.createElement("input");
				hiddenField.setAttribute("type", "hidden");
				hiddenField.setAttribute("name", "json_parameters");
				hiddenField.setAttribute("value", json_parameters);
				form.appendChild(hiddenField);
				document.body.appendChild(form);
				form.submit();
				document.body.removeChild(form);
			});
		},
		
		refreshFooter : function(jq) {
			return jq.each(function() {
				var $datagrid = $(this);
				if (! $datagrid.attr("showFooter")) {
					return;
				}
				$datagrid.datagrid("forceEndEdit");
				var footerRows = [];
				$datagrid.find("tfoot tr").each(function(indextf, tr) {
					var $tr = $(tr);
					var footerRow = {};
					$tr.find("td").each(function(indextd, td) {
						var $td = $(td);
						var field = $td.attr("field");
						var value = 0;
						var footerType = $td.attr("footerType");
						switch (footerType) {
						case "count" :
							value = $datagrid.datagrid("getRows").length;
							break;
						case "sum" :
							$.each($datagrid.datagrid("getRows"), function(rowIndex, row) {
								value += isNaN(+row[field]) ? 0 : +row[field];
							});
							break;
						case "average" :
							var count = $datagrid.datagrid("getRows").length;
							if (count == 0) {
								value = 0;
							} else {
								$.each($datagrid.datagrid("getRows"), function(rowIndex, row) {
									value += isNaN(+row[field]) ? 0 : +row[field];
								});
								value = value / count;
							}
							break;
						default :
							value = $td.html();
						}
						footerRow[field] = value;
					});
					footerRows.push(footerRow);
				});
				if (footerRows.length) {
					$datagrid.datagrid("reloadFooter", footerRows);
				}
			});
		}
		
	});

	//treegrid methods
	$.extend($.fn.treegrid.methods, {
		
		getSelectedId : function(jq) {
			var $datagrid = $(jq[0]);
			var selected = $datagrid.treegrid("getSelected");
			if (selected) {
				var idField = $datagrid.treegrid("options").idField;
				return $datagrid.treegrid("getSelected")[idField];
			} else {
				return null;
			}
		},
		
		getSelectionsId : function(jq) {
			var $datagrid = $(jq[0]);
			var idField = $datagrid.treegrid("options").idField;
			var rows = $datagrid.treegrid("getSelections");
			var selectionsId = [];
			for (var i = 0; i < rows.length; i++) {
				selectionsId.push(rows[i][idField]);
			}
			return selectionsId;
		},
		
		forceEndEdit : $.fn.datagrid.methods.forceEndEdit,
		
		_append : $.fn.treegrid.methods.append,
		
		append : function(jq, param) {
			return jq.each(function() {
				var $datagrid = $(this);
				if (! $datagrid.treegrid("endEditWithReturn")) {
					return;
				}
				var idField = $datagrid.treegrid("options").idField;
				var parentField = $datagrid.treegrid("options").parentField;
				var treeField = $datagrid.treegrid("options").treeField;
				if (! $.isArray(param.data)) {
					param.data = [param.data];
				}
				$.each(param.data, function(index, row) {
					row[parentField] = param.parent;
					if (! row[idField]) {
						row[idField] = "id_" + new Date().getTime();
					}
					if (! row[treeField]) {
						row[treeField] = "new";
					}
				});
				$datagrid.treegrid("_append", param);
				//select and edit
				var $tr = $datagrid.treegrid("options").editConfig.getTr(this, param.data[0][idField])[0];
				if ($tr) {
					$tr.click();
				}
				$.data(this, "datagrid").insertedRows.push(param.data[0]);
			});
		},
		
		updateRow : function(jq, param) {
			var $datagrid = $(jq[0]);
			$datagrid.treegrid("forceEndEdit");
			var target = jq[0];
			var opts = $.data(target, "datagrid").options;
			var insertedRows = $.data(target, "datagrid").insertedRows;
			var updatedRows = $.data(target, "datagrid").updatedRows;
			if (insertedRows.indexOf(param.row) == -1) {
				var oldRow = opts.editConfig.getRow(target, param.id);
				if (oldRow == param.row) {
					//no way to check if the row has been changed
					updatedRows.push(param.row);
				} else {
					var changed = false;
					for (var field in oldRow) {
						if (oldRow[field] != param.row[field]) {
							changed = true;
							break;
						}
					};
					$.extend(oldRow, param.row);
					param.row = oldRow;
					if (changed) {
						if (updatedRows.indexOf(param.row) == -1) {
							updatedRows.push(param.row);
						}
					}
				}
			}
			return $datagrid.treegrid("refresh", param.id);
		},

		__remove : $.fn.treegrid.methods.remove,
		
		_remove : function(jq, id) {
			return jq.each(function() {
				var $datagrid = $(this);
				var insertedRows = $datagrid.treegrid("_getChanges", "inserted");
				var deletedRows = $datagrid.treegrid("_getChanges", "deleted");
				var row = $datagrid.treegrid("find", id);
				markDeletedRows(row);
				function markDeletedRows(row) {
					if (insertedRows.indexOf(row) >= 0) {
						insertedRows.remove(row);
					} else {
						if (deletedRows.indexOf(row) == -1) {
							deletedRows.push(row);
						}
					}
					if (row.children) {
						$.each(row.children, function(index, row) {
							markDeletedRows(row);
						});
					}
				};
				$datagrid.treegrid("__remove", id);
			});
		},
		
		remove : function(jq, id) {
			return jq.each(function() {
				var $datagrid = $(this);
				$datagrid.treegrid("forceEndEdit").treegrid("_remove", id);
			});
		},
		
		removeSelectedNodes : function(jq) {
			return jq.each(function() {
				var $datagrid = $(this);
				$datagrid.treegrid("forceEndEdit");
				$.each($datagrid.treegrid("getSelectionsId"), function(index, selectedId) {
					$datagrid.treegrid("_remove", selectedId);
				});
			});
		},
		
		_loadData : $.fn.treegrid.methods.loadData,
		
		loadData : function(jq, data) {
			return jq.each(function() {
				var $datagrid = $(this);
				$datagrid.treegrid("forceEndEdit");
				$datagrid.treegrid("_loadData", data);
			});
		},
		
		getChanges : function(jq, type) {
			var $datagrid = $(jq[0]);
			var idField = $datagrid.treegrid("options").idField;
			var parentField = $datagrid.treegrid("options").parentField;
			var rows = $datagrid.datagrid("getChanges", type);
			$.each(rows, function(index, row) {
				fixChildrenParentId(row);
			});
			function fixChildrenParentId(row) {
				if (row.children) {
					$.each(row.children, function(childIndex, childRow) {
						childRow[parentField] = row[idField];
						fixChildrenParentId(childRow);
					});
				}
			};
			var queryType = $datagrid.treegrid("options").parameters.parameters.queryInfo.queryType;
			if (queryType) {
				$.each(rows, function(index, dataItem) {
					dataItem._class = dataItem._class || queryType;
				});
			}
			return rows;
		},
		
		refreshSavedData : function(jq, savedRows) {
			return jq.each(function() {
				var $datagrid = $(this);
				var idField = $datagrid.treegrid("options").idField;
				var insertedRows = $datagrid.treegrid("_getChanges", "inserted");
				var updatedRows = $datagrid.treegrid("_getChanges", "updated");
				var refreshRows = [].concat(insertedRows).concat(updatedRows);
				for (var i = 0; i < refreshRows.length; i++) {
					$datagrid.treegrid("options").editConfig.getTr(this, refreshRows[i][idField]).attr("node-id", savedRows[i][idField]);
					$.extend(refreshRows[i], savedRows[i]);
					$datagrid.treegrid("refresh", savedRows[i][idField]);
				}
				$datagrid.datagrid("acceptChanges");
			});
		},
		
		reloadAndSelect : function(jq, id) {
			return jq.each(function() {
				var $treegrid = $(this);
				$treegrid.data("selectedId", id);
				$treegrid.treegrid("reload");
			});
		}
		
	});
	
	//init datagrids
	function initDatagrids(jq) {
		jq.each(function() {
			var $datagrid = $(this);
			var frozenColumns = getColumns($("thead[frozen=true]", this));
			var columns = getColumns($("thead[frozen!=true]", this));
			var allColumns = [];
			for (var i = 0; i < frozenColumns.length; i++) {
				allColumns = allColumns.concat(frozenColumns[i]);
			}
			for (var i = 0; i < columns.length; i++) {
				allColumns = allColumns.concat(columns[i]);
			}
			var fieldCodeTypes = {};
			var readonly = true;
			for (var i = 0; i < allColumns.length; i++) {
				var columnOption = allColumns[i];
				var $th = $datagrid.find("th[field='" + columnOption.field + "']");
				$.extend(columnOption, {
					title : getI18nTitle($datagrid.attr("i18nRoot"), columnOption.field, $th.attr("title")),
					formatter : (columnOption.formatter || $.fn.datagrid.defaults.formatter),
					codeType : $th.attr("codeType"),
					format : $th.attr("format"),
					content : $.trim($th.html())
				});
				if (! columnOption.title) {
					columnOption.sortable = false;
				}
				if (columnOption.codeType) {
					fieldCodeTypes[columnOption.field] = columnOption.codeType;
				}
				if ($th.attr("editor")) {
					readonly = false;
				}
			}
			if (frozenColumns.length == 0) {
				frozenColumns = [[]];
			}
			if ($datagrid.attr("checkbox") != "false") {
				frozenColumns[0].unshift({field:"ck", checkbox:true});
			}
			var options = $.extend({}, $.fn.datagrid.parseOptions(this), {
				frozenColumns : frozenColumns,
				columns : columns,
				parameters : {
					serviceName : "commonQueryManager",
					methodName : "query",
					parameters : {
						queryInfo : {
							queryType : $datagrid.attr("queryType"),
							orderBy : $datagrid.attr("orderBy"),
							fieldCodeTypes : fieldCodeTypes
						}
					}
				},
				paramForm : $datagrid.attr("paramForm"),
				orderBy : $datagrid.attr("orderBy"),
				groupField : $datagrid.attr("groupField"),
				parentField : $datagrid.attr("parentField"),
				iconClsField : $datagrid.attr("iconClsField"),
				autoRowHeight : $datagrid.attr("autoRowHeight") == "true" ? true : false,
				readonly : $datagrid.attr("readonly") ? true : readonly,
				showSelection : $datagrid.attr("showSelection") == "false" ? false : true
			});
			//if use static data, do not query
			if ($datagrid.find("tbody").size() > 0 && $datagrid.find("tbody").children().size() > 0) {
				options.parameters.parameters.queryInfo.queryType = null;
			}
			if ($datagrid.attr("queryFields")) {
				options.queryFields = eval($datagrid.attr("queryFields"));
			}
			if ($datagrid.hasClass("easyui-treegrid")) {
				$datagrid.treegrid(options);
			} else {
				$datagrid.datagrid(options);
			}
			//showSelection?
			if (! options.showSelection) {
				$datagrid.datagrid("getPanel").children(".datagrid-view").removeClass("show-selection");
			}
			//click blank area to end edit
			$datagrid.datagrid("getPanel").children(".datagrid-view")
			.children(".datagrid-view1,.datagrid-view2").find(">.datagrid-body,>.datagrid-body>.datagrid-body-inner")
			.bind("click", function(e) {
				if ($(e.target).hasClass("datagrid-body")) {
					if ($datagrid.datagrid("endEditWithReturn")) {
						$datagrid.datagrid("unselectAll");
					}
				}
			});
			//move the toolbar into gridpanel
			$datagrid.closest(".datagrid").parent().children(".datagrid-toolbar").prependTo($datagrid.parent().parent());
			//$datagrid.datagrid("fitColumnWidth");
			$datagrid.datagrid("refreshFooter");
			setTimeout(function() {
				setTimeout(function() {
					initDatagridEditors($datagrid);
				}, 0);
			}, 0);
		});
	};


	/********** tree **********/
	var treeDefaults = {
			url : contextPath + "/JsonFacadeServlet",
			parameters : {
				parameters : {
					queryInfo : {}
				}
			},

			loadDataErrorMsg : "Error loading data.",

			//query parameters
			onBeforeLoad : function(node, param) {
				var $tree = $(this);
				//query parameters
				var options = $tree.tree("options");
				var parameters = options.parameters;
				if (options.url == treeDefaults.url) {
					if (! parameters) {
						return false;
					}
					if (! parameters.parameters.queryInfo.queryType) {
						return false;
					}
				} else {
					if (! parameters.parameters.queryInfo.queryType) {
						return;
					}
				}
				var queryFields = [];
				if (options.queryFields) {
					queryFields = queryFields.concat(options.queryFields);
				}
				if (options.commonQueryFields) {
					queryFields = queryFields.concat(options.commonQueryFields);
				}
				$.extend(parameters.parameters.queryInfo, {
					queryFields : queryFields
				});
				var parentQueryField = null;
				if (param.id) {
					parentQueryField = {
							fieldName : "parentId",
							fieldStringValue : param.id
					};
					if (! parameters.parameters.queryInfo.queryFields) {
						parameters.parameters.queryInfo.queryFields = [];
					}
					parameters.parameters.queryInfo.queryFields.push(parentQueryField);
				}
				for (var key in param) {
					delete param[key];
				}
				param.json_parameters = $.toJSON(parameters);
				if (parentQueryField) {
					parameters.parameters.queryInfo.queryFields.remove(parentQueryField);
				}
			},

			//query result
			loadFilter : function(data) {
				if (data.exception) {
					$.messager.alert("Message", $.fn.tree.defaults.loadDataErrorMsg + data.exception, "warning");
					return null;
				}
				var $tree = $(this);
				var options = $tree.tree("options");
				var idField = options.idField || "id";
				var textField = options.textField || "text";
				var parentField = options.parentField || "parentId";
				var checkedField = options.checkedField || "checked";
				var stateField = options.stateField || "state";
				var iconClsField = options.iconClsField || "iconCls";
				if (data.result) {
					//returned from commonquery
					var allNodeMap = {};
					var allNodeArray = [];
					$.each(data.result.dataList, function(index, dataItem) {
						var node = {
								id : dataItem[idField],
								text : dataItem[textField],
								checked : dataItem[checkedField],
								state : dataItem[stateField],
								iconCls : dataItem[iconClsField],
								attributes : {
									data : dataItem
								}
						};
						if ($tree.data("collapsedIds") && $tree.data("collapsedIds").indexOf(node.id) >= 0) {
							node.state = "closed";
						}
						allNodeMap[node.id] = node;
						allNodeArray.push(node);
					});
					var result = [];
					$.each(allNodeArray, function(index, node) {
						if (node.attributes.data[parentField]) {
							var parent = allNodeMap[node.attributes.data[parentField]];
							if (parent) {
								if (! parent.children) {
									parent.children = [];
								}
								parent.children.push(node);
							} else {
								result.push(node);
							}
						} else {
							result.push(node);
						}
					});
					return result;
				}
				if ($.isArray(data)) {
					function bindAttributes(node) {
						$.extend(true, node, {
							id : node.id || node[idField],
							text : node.text || node[textField],
							checked : node.checked || node[checkedField],
							state : node.state || node[stateField],
							iconCls : node.iconCls || node[iconClsField],
							attributes : {
								data : node
							}
						});
						if ($tree.data("collapsedIds") && $tree.data("collapsedIds").indexOf(node.id) >= 0) {
							node.state = "closed";
						}
						$.extend(true, node.attributes, node.attributes.data.attributes);
						if (node.children) {
							$.each(node.children, function(index, child) {
								bindAttributes(child);
							});
						}
					};
					$.each(data, function(index, node) {
						bindAttributes(node);
					});
				}
				return data;
			},

			onLoadSuccess : function(node, data) {
				var $tree = $(this);
				var options = $tree.tree("options");
				$tree.data("deletedData", []);
				function handleNode(node) {
					node.attributes.oldJsonValue = $.toJSON(node.attributes.data);
					if (options.titleField) {
						$(node.target).attr("title", node.attributes.data[options.titleField]);
					}
					$.each($tree.tree("getData", node.target).children, function(index, child) {
						handleNode(child);
					});
				}
				$.each($tree.tree("getRoots"), function(index, root) {
					handleNode(root);
				});
				var previousSelectedId = $tree.data("selectedId");
				if (previousSelectedId) {
					var previousSelectedNode = $tree.tree("find", previousSelectedId);
					if (previousSelectedNode) {
						$tree.tree("select", previousSelectedNode.target);
						return;
					}
				}
				var previousSelectedPosition = $tree.data("selectedPosition");
				if (previousSelectedPosition != undefined) {
					$tree.tree("select", $tree.find(".tree-node:visible")[previousSelectedPosition]);
				}
			},

			onLoadError : function() {
				$.messager.confirm("Message", $.fn.datagrid.defaults.reLoginMsg, function(b) {
					if (b) {
						window.location.reload();
					}
				});
			}

	};
	
	$.extend(true, $.fn.tree.defaults, treeDefaults);

	$.extend($.fn.tree.methods, {
		
		_reload : $.fn.tree.methods.reload,
		
		reload : function(jq, target) {
			return jq.each(function() {
				var $tree = $(this);
				//rememeber collapse status
				var collapsedIds = [];
				$.each($tree.find(".tree-collapsed").parent(), function(index, node) {
					collapsedIds.push($(node).attr("node-id"));
				});
				$tree.data("collapsedIds", collapsedIds);
				//remember selectd node
				var selected = $tree.tree("getSelected");
				if (selected) {
					$tree.data("selectedId", selected.id);
					$tree.data("selectedPosition", $tree.find(".tree-node:visible").index(selected.target));
				}
				$tree.tree("_reload", target);
			});
		},
		
		//commonQuery
		commonQuery : function(jq, queryInfo) {
			return jq.each(function() {
				var $tree = $(this);
				var options = $tree.tree("options");
				options.commonQueryFields = null;
				if (queryInfo) {
					if (queryInfo.queryFields) {
						options.commonQueryFields = queryInfo.queryFields;
						delete queryInfo.queryFields;
					}
					$.extend(options.parameters.parameters.queryInfo, queryInfo);
				}
				$tree.tree("reload");
			});
		},
		
		setQueryFields : function(jq, queryFields) {
			return jq.each(function() {
				$(this).tree("options").queryFields = queryFields;
			});
		},
		
		getChanges : function(jq) {
			var $tree = $(jq[0]);
			var options = $tree.tree("options");
			var idField = options.idField;
			var parentField = options.parentField;
			var seqField = options.seqField;
			var data = [];
			function addData(node, parentId, seq) {
				var nodeData = node.attributes.data;
				nodeData[idField] = node.id;
				nodeData[parentField] = parentId;
				if (seqField) {
					nodeData[seqField] = seq;
				}
				var oldJsonValue = node.attributes.oldJsonValue;
				if (oldJsonValue) {
					if (oldJsonValue != $.toJSON(nodeData)) {
						nodeData.rowState = "Modified";
						data.push(nodeData);
					}
				} else {
					if (nodeData.rowState == "Deleted") {
						nodeData.rowState = "Modified";
					} else {
						nodeData.rowState = "Added";
					}
					data.push(nodeData);
				}
				$.each($tree.tree("getData", node.target).children, function(index, child) {
					addData(child, nodeData[idField], index);
				});
			}
			$.each($tree.tree("getRoots"), function(index, root) {
				addData(root, "0", index);
			});
			$.each($tree.data("deletedData"), function(index, deletedData) {
				if (data.indexOf(deletedData) == -1 && ! $tree.tree("find", deletedData[idField])) {
					deletedData.rowState = "Deleted";
					data.push(deletedData);
				}
			});
			var queryType = options.parameters.parameters.queryInfo.queryType;
			if (queryType) {
				$.each(data, function(index, dataItem) {
					dataItem._class = dataItem._class || queryType;
				});
			}
			return data;
		},
		
		getHalfChecked : function(jq) {
			var target = jq[0];
			var checkedNodes = [];
			$(target).find('.tree-checkbox2').each(function() {
						var node = $(this).parent();
						checkedNodes.push($(target).tree("getNode", node[0]));
					});
			return checkedNodes;
		},
		
		appendAfterSelected : function(jq, node) {
			return jq.each(function() {
				var $tree = $(this);
				$.extend(true, node, {
					id : node.id || $.now(),
					text : node.text || "",
					attributes : {
						data : node
					}
				});
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					$tree.tree("append", {
						data : [node]
					});
				} else {
					$tree.tree("insert", {
						after : selectedNode.target,
						data : node
					});
				}
				node = $tree.tree("find", node.id);
				$tree.tree("select", node.target);
			});
		},
		
		insertBeforeSelected : function(jq, node) {
			return jq.each(function() {
				var $tree = $(this);
				$.extend(true, node, {
					id : node.id || $.now(),
					text : node.text || "",
					attributes : {
						data : node
					}
				});
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				$tree.tree("insert", {
					before : selectedNode.target,
					data : node
				});
				node = $tree.tree("find", node.id);
				$tree.tree("select", node.target);
			});
		},
		
		addChildToSelected : function(jq, node) {
			return jq.each(function() {
				var $tree = $(this);
				$.extend(true, node, {
					id : node.id || $.now(),
					text : node.text || "",
					attributes : {
						data : node
					}
				});
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				$tree.tree("append", {
					parent : selectedNode.target,
					data : [node]
				});
				node = $tree.tree("find", node.id);
				$tree.tree("select", node.target);
			});
		},
		
		_remove : $.fn.tree.methods.remove,
		
		remove : function(jq, target) {
			jq.each(function() {
				var $tree = $(this);
				var deletedData = $tree.data("deletedData");
				function addDeletedData(node) {
					var nodeData = node.attributes.data;
					deletedData.push(nodeData);
					$.each($tree.tree("getData", node.target).children, function(index, child) {
						addDeletedData(child);
					});
				}
				addDeletedData($tree.tree("getData", target));
			});
			return this._remove(jq, target);
		},
		
		removeSelected : function(jq) {
			return jq.each(function() {
				var $tree = $(this);
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				var $next = $(selectedNode.target).parent().next();
				if ($next.size() > 0) {
					$tree.tree("select", $next.children().get(0));
				} else {
					var $prev = $(selectedNode.target).parent().prev();
					if ($prev.size() > 0) {
						$tree.tree("select", $prev.children().get(0));
					} else {
						var parentNode = $tree.tree("getParent", selectedNode.target);
						if (parentNode != null) {
							$tree.tree("select", parentNode.target);
						}
					}
				}
				$tree.tree("remove", selectedNode.target);
			});
		},
		
		moveSelectedUp : function(jq) {
			return jq.each(function() {
				var $tree = $(this);
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				var $prev = $(selectedNode.target).parent().prev();
				if ($prev.size() > 0) {
					$tree.tree("insert", {
						before : $prev.children().get(0),
						data : $tree.tree("pop", selectedNode.target)
					});
					$tree.tree("select", $tree.tree("find", selectedNode.id).target);
				}
			});
		},
		
		moveSelectedDown : function(jq) {
			return jq.each(function() {
				var $tree = $(this);
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				var $next = $(selectedNode.target).parent().next();
				if ($next.size() > 0) {
					$tree.tree("insert", {
						after : $next.children().get(0),
						data : $tree.tree("pop", selectedNode.target)
					});
					$tree.tree("select", $tree.tree("find", selectedNode.id).target);
				}
			});
		},
		
		moveSelectedLeft : function(jq) {
			return jq.each(function() {
				var $tree = $(this);
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				var parentNode = $tree.tree("getParent", selectedNode.target);
				if (parentNode != null) {
					$tree.tree("insert", {
						after : parentNode.target,
						data : $tree.tree("pop", selectedNode.target)
					});
					$tree.tree("select", $tree.tree("find", selectedNode.id).target);
				}
			});
		},
		
		moveSelectedRight : function(jq) {
			return jq.each(function() {
				var $tree = $(this);
				var selectedNode = $tree.tree("getSelected");
				if (selectedNode == null) {
					return;
				}
				var $prev = $(selectedNode.target).parent().prev();
				if ($prev.size() > 0) {
					$tree.tree("append", {
						parent : $prev.children().get(0),
						data : [$tree.tree("pop", selectedNode.target)]
					});
					$tree.tree("select", $tree.tree("find", selectedNode.id).target);
				}
			});
		},
		
		reloadAndSelect : function(jq, id) {
			return jq.each(function() {
				var $tree = $(this);
				$tree.data("selectedId", id);
				$tree.tree("reload");
			});
		}
		
	});
	
	//init trees
	function initTrees(jq) {
		jq.each(function() {
			var $tree = $(this);
			var options = $.extend({}, $.fn.tree.parseOptions(this), {
				parameters : {
					serviceName : "commonQueryManager",
					methodName : "query",
					parameters : {
						queryInfo : {
							queryType : $tree.attr("queryType"),
							orderBy : $tree.attr("orderBy")
						}
					}
				},
				idField : $tree.attr("idField"),
				textField : $tree.attr("textField"),
				parentField : $tree.attr("parentField"),
				seqField : $tree.attr("seqField"),
				titleField : $tree.attr("titleField"),
				checkedField : $tree.attr("checkedField"),
				stateField : $tree.attr("stateField"),
				iconClsField : $tree.attr("iconClsField")
			});
			//if use static data, do not query
			if ($tree.children().size() > 0) {
				options.parameters.parameters.queryInfo.queryType = null;
			}
			if ($tree.attr("queryFields")) {
				options.queryFields = eval($tree.attr("queryFields"));
			}
			$tree.tree(options);
		});
	};

	
	/********** combo **********/
	$.fn._combo = $.fn.combo;

	$.fn.combo = function(options, param) {
		if (typeof options == "string") {
			this.each(function() {
				if (! $.data(this, "combo")) {
					$(this).combo({});
				}
			});
		}
		return $.fn._combo.apply(this, [options, param]);
	};
	
	$.fn.combo.methods = $.fn._combo.methods;
	$.fn.combo.parseOptions = $.fn._combo.parseOptions;
	$.fn.combo.defaults = $.fn._combo.defaults;
	
	$.fn.combo._parseOptions = $.fn.combo.parseOptions;
	//fix bug in easyui-1.2.4, attr 'multiple' value is 'multiple', not 'true'
	$.fn.combo.parseOptions = function(target) {
		var options = $.fn.combo._parseOptions(target);
		var t = $(target);
		return $.extend(options, {
			multiple : (t.attr("multiple") ? (t.attr("multiple") == "true" || t.attr("multiple") == true || t.attr("multiple") == "multiple") : undefined),
			customValuePermitted : (t.attr("customValuePermitted") ? (t.attr("customValuePermitted") == "true" || t.attr("customValuePermitted") == true) : undefined)
		});
	};
	
	$.extend($.fn.combo.methods, {
		_disable : $.fn.combo.methods.disable,

		disable : function(jq) {
			return jq.each(function() {
				var $combo = $(this);
				$combo.attr("disabled", "disabled");
				$combo.combo("_disable");
				$combo.combo("textbox").attr("_disabled", "_disabled");
			});
		},
		
		_enable : $.fn.combo.methods.enable,

		enable : function(jq) {
			return jq.each(function() {
				var $combo = $(this);
				$combo.removeAttr("disabled");
				$combo.combo("_enable");
				$combo.combo("textbox").removeAttr("_disabled");
			});
		},
		
		readonly : function(jq, readonly) {
			return jq.each(function() {
				var $combo = $(this);
				readonly = readonly == undefined ? true : readonly;
				if (readonly) {
					$combo.attr("readonly", "readonly");
					$combo.combo("disable");
					$combo.combo("textbox").removeAttr("disabled").attr("readonly", readonly);
					$combo.removeAttr("disabled");
				} else {
					$combo.removeAttr("readonly");
					$combo.combo("enable");
					if (! $combo.hasClass("combotree-f")) {
						$combo.combo("textbox").removeAttr("readonly");
					}
				}
			});
		},
		
		_getValue : $.fn.combo.methods.getValue,
		
		getValue : function(jq) {
			var $combo = $(jq[0]);
			var value = $combo.combo("getValues").join(",");
			if ($combo.combo("options").customValuePermitted) {
				var codeType = $combo.combo("options").codeType;
				if (codeType && SELECT_CODE_VALUES[codeType] && ! SELECT_CODE_VALUES[codeType][value]) {
					SELECT_CODE_VALUES[codeType][value] = value;
				}
			}
			return value;
		},
		
		getComboMultiValues : function(jq, values) {
			var $combo = $(jq[0]);
			if ($combo.hasClass("combo-multi")) {
				var values = [];
				$.each($combo.parent().children(".combo-multi-item"), function(index, item) {
					values.push($(item).attr("id"));
				});
				return values;
			} else {
				return $combo.combo("getValues");
			}
		},
		
		setComboMultiValues : function(jq, values) {
			return jq.each(function() {
				var $combo = $(this);
				if ($combo.hasClass("combo-multi")) {
					var $comboMulti = $combo.closest(".combo-multi-wrapper");
					$comboMulti.children(".combo-multi-item").remove();
					$.each(values, function(index, value) {
						addComboMultiItem($combo, value);
					});
				} else {
					$combo.combo("setValues", values);
				}
			});
		}
	});
	
	
	/********** combo-multi **********/
	function initComboMultis(jq) {
		jq.filter(".combo-multi").each(function() {
			var $combo = $(this);
			var $comboMulti = $("<div class='combo-multi-wrapper'>").insertBefore($combo);
			$combo.appendTo($comboMulti);
			
			$combo.data("onReady", function() {
				$combo.combo("bind", {
					onHidePanel : function() {
						$combo.combo("textbox").focus();
						$comboMulti.width($comboMulti.parent().width());
						addComboMultiItem($combo, $combo.combo("_getValue"));
						if ($combo.hasClass("combobox-f")) {
							$combo.combobox("setValue", null);
						} else if ($combo.hasClass("combogrid-f")) {
							$combo.combogrid("setValue", null);
						} else if ($combo.hasClass("combotree-f")) {
							$combo.combotree("setValue", null);
						} else {
							$combo.combo("setValue", null);
						}
					}
				});
			});
			
		});
	};
	
	function addComboMultiItem($combo, value) {
		var $comboMulti = $combo.closest(".combo-multi-wrapper");
		if (! value) {
			return;
		}
		if ($comboMulti.children("#" + value).size() > 0) {
			return;
		}
		var codeType = $combo.attr("codeType");
		var text;
		if (SELECT_CODE_VALUES[codeType] && SELECT_CODE_VALUES[codeType][value]) {
			text = SELECT_CODE_VALUES[codeType][value];
		} else {
			text = "...";
			var selectCodeKeys = {};
			selectCodeKeys[codeType] = value;
			SelectCodeManager.getSelectCodeValuesByKeys(selectCodeKeys, function(result) {
				$.extend(true, SELECT_CODE_VALUES, result);
				value = value + "";
				if (value.split(",").length > 1) {
					var values = value.split(",");
					var texts = [];
					for (var i = 0; i < values.length; i++) {
						texts.push(result[codeType][values[i]] || values[i]);
					}
					SELECT_CODE_VALUES[codeType][value] = texts.join(",");
				}
				$comboMulti.children("#" + value).find(".combo-multi-item-text").html(SELECT_CODE_VALUES[codeType][value] || value);
			});
		}
		var $item = $("<span id='" + value + "' class='combo-multi-item'>"
				+ "<div class='combo-multi-item-text'>"+ text + "</div>"
				+ "<div class='clear-input'/>"
				+ "</span>").insertBefore($combo);
		$item.find(".clear-input").click(function() {
			if ($combo.attr("disabled") || $combo.attr("readonly")) {
				return;
			}
			$(this).closest(".combo-multi-item").remove();
		});
		
		$item.draggable({
			handle : ".combo-multi-item-text",
			proxy : "clone",
            revert : true,
            onStartDrag : function(e) {
            	$item.css("visibility", "hidden");
            },
            onStopDrag : function(e) {
            	$item.css("visibility", "");
				$.data(this, "draggable").proxy.hide();
            }
        }).droppable({
            onDragOver : function(e, source){
            	if ($(this).closest(".combo-multi-wrapper")[0] != $(source).closest(".combo-multi-wrapper")[0]) {
            		return;
            	}
				if ($(this).index() < $(source).index()) {
					$(source).insertBefore(this);
				} else {
					$(source).insertAfter(this);
				}
            }
        });
	};
	
	
	/********** combobox **********/
	$.fn._combobox = $.fn.combobox;

	$.fn.combobox = function(options, param) {
		if (typeof options == "string") {
			this.each(function() {
				if (! $.data(this, "combobox")) {
					$(this).combobox({});
				}
			});
		}
		return $.fn._combobox.apply(this, [options, param]);
	};

	$.fn.combobox.methods = $.fn._combobox.methods;
	$.fn.combobox.parseOptions = $.fn._combobox.parseOptions;
	$.fn.combobox.defaults = $.fn._combobox.defaults;
	
	$.extend(true, $.fn.combobox.defaults, {
		width : 155,
		lazyLoad : true,
		
		keyHandler : {
			
			_query : $.fn.combobox.defaults.keyHandler.query,
			
			query : function(q) {
				$.fn.combobox.defaults.keyHandler._query.apply(this, [q]);
				//if exactly one row is matched, select it.
				var $combo = $(this);
				var $item = $combo.combo("panel").find("div.combobox-item:visible");
				if ($item.size() == 1) {
					$combo.combo("setValues", [$item.attr("value")]);
					$item.addClass("combobox-item-selected");
				}
			}
		},
		
		filter : function(q, row) {
			var opts = $(this).combobox("options");
			return row[opts.textField].toUpperCase().indexOf(q.toUpperCase()) >= 0;
		}
	});

	$.extend($.fn.combobox.methods, {
		
		_setValue : $.fn.combobox.methods.setValue,
		
		setValue : function(jq, value) {
			return jq.each(function() {
				var $combobox = $(this);
				$combobox.combobox("setValues", value ? (value + "").split(",") : []);
				$combobox.data("oldValues", $combobox.combobox("getValues"));
				$combobox.data("oldText", $combobox.combobox("getText"));
			});
		}

	});

	//init comboboxes
	function initComboboxes(jqInput, jqTh) {
		jqInput.each(function() {
			var $combobox = $(this);
			$combobox.attr("comboname", $combobox.attr("name"));
		});
		var codeTypes = {};
		jqInput.add(jqTh).each(function() {
			var $combobox = $(this);
			var codeType = $combobox.attr("codeType");
			if (codeType && ! codeTypes[codeType]) {
				codeTypes[codeType] = {queryType : codeType};
			}
		});
		if ($.isEmptyObject(codeTypes)) {
			return;
		}
		var readyCodeTypes = [];
		var queryInfos = [];
		for (var codeType in codeTypes) {
			if (SELECT_CODE_DATAS[codeType]) {
				readyCodeTypes.push(codeType);
			} else {
				queryInfos.push(codeTypes[codeType]);
			}
		}
		if (readyCodeTypes.length > 0) {
			setTimeout(function() {
				$.each(readyCodeTypes, function(i, codeType) {
					initComboboxesByCodeType(jqInput, jqTh, codeType, SELECT_CODE_DATAS[codeType]);
				});
			}, 0);
		}
		if (queryInfos.length > 0) {
			SelectCodeManager.getSelectCodeDatas(queryInfos, function(result) {
				setTimeout(function() {
					for (var codeType in result) {
						var selectCodeData = result[codeType];
						SELECT_CODE_DATAS[codeType] = selectCodeData;
						var selectCodeDataObject = {};
						for (var i = 0; i < selectCodeData.dataList.length; i++) {
							selectCodeDataObject[selectCodeData.dataList[i][selectCodeData.keyFieldName]] = selectCodeData.dataList[i][selectCodeData.labelFieldName];
						}
						var selectCodeValuesObject = {};
						selectCodeValuesObject[codeType] = selectCodeDataObject;
						$.extend(true, SELECT_CODE_VALUES, selectCodeValuesObject);
						initComboboxesByCodeType(jqInput, jqTh, codeType, selectCodeData);
					}
				}, 0);
			});
		}
	};

	function initComboboxesByCodeType(jqInput, jqTh, codeType, selectCodeData) {
		var options = {
				valueField : selectCodeData.keyFieldName,
				textField : selectCodeData.labelFieldName,
				data : selectCodeData.dataList,
				panelHeight : Math.min(selectCodeData.dataList.length, 10) * 25 + 2
		};
		jqInput.filter("[codeType='" + codeType + "']").each(function() {
			var $combobox = $(this);
			var value = $.data(this, "combobox") ? $combobox.combobox("getValue") : $(this).val();
			if ($.data(this, "combobox")) {
				$combobox.__combobox($.extend(true, $combobox.combobox("options"), options));
			} else if ($.data(this, "combo")) {
				$combobox.__combobox($.extend(true, $combobox.combo("options"), $.fn.combobox.parseOptions(this), options));
			} else {
				$combobox.combobox($.extend(true, $.fn.combobox.parseOptions(this), options));
			}
			if (value) {
				$combobox.combobox("setValue", value);
			}
			if ($combobox.combobox("options").onReady) {
				$combobox.combobox("options").onReady.apply($combobox[0]);
			}
			if ($combobox.data("onReady")) {
				$combobox.data("onReady").apply($combobox[0]);
			}
		});
		jqTh.filter("[codeType='" + codeType + "']").each(function() {
			var $th = $(this);
			var $datagrid = $th.closest("table");
			var columnOption = $datagrid.datagrid("getColumnOption", $th.attr("field"));
			columnOption.editor = {
					type : "combobox",
					init : true,
					options : columnOption.editor.options ? $.extend(columnOption.editor.options, options) : options
			};
		});
	};
	

	/********** combogrid **********/
	$.fn._combogrid = $.fn.combogrid;

	$.fn.combogrid = function(options, param) {
		if (typeof options == "string") {
			this.each(function() {
				if (! $.data(this, "combogrid")) {
					$(this).combogrid({});
				}
			});
		}
		return $.fn._combogrid.apply(this, [options, param]);
	};

	$.fn.combogrid.methods = $.fn._combogrid.methods;
	$.fn.combogrid.parseOptions = $.fn._combogrid.parseOptions;
	$.fn.combogrid.defaults = $.fn._combogrid.defaults;
	
	$.extend(true, $.fn.combogrid.defaults, {
		width : 155,
		lazyLoad : true,
		panelWidth : 500,
		panelHeight : 25 * 10 + 90,
		pagination : true,
		rownumbers : true,
		mode : "remote",
		url : contextPath + "/JsonFacadeServlet",
		
		//query parameters
		onBeforeLoad : function(param) {
			var $datagrid = $(this);
			$datagrid.data("loading", true);
			var options = $datagrid.datagrid("options");
			var parameters = options.parameters;
			if (options.url == datagridDefaults.url) {
				if (! parameters) {
					return false;
				}
				if (! parameters.parameters.queryInfo.queryType) {
					$datagrid.datagrid("rejectChanges");
					return false;
				}
			} else {
				if (! parameters.parameters.queryInfo.queryType) {
					return;
				}
			}
			var queryFields = [];
			if (options.queryFields) {
				queryFields = queryFields.concat(options.queryFields);
			}
			if (param.q) {
				queryFields.push({
					fieldName : "[" + $datagrid.datagrid("getColumnFields").join(",") + "]",
					fieldValue : param.q,
					operator : "ilikeAnywhere"
				});
			}
			var pagingInfo = null;
			if (param.rows) {
				pagingInfo = {
					pageSize : param.rows,
					currentPage : param.page
				};
			}
			$.extend(parameters.parameters.queryInfo, {
				queryFields : queryFields,
				orderBy : (param.sort ? param.sort + " " + param.order : (options.orderBy || "")),
				pagingInfo : pagingInfo
			});
			for (var key in param) {
				delete param[key];
			}
			param.json_parameters = $.toJSON(parameters);
		},

		//query result
		loadFilter : $.fn.datagrid.defaults.loadFilter,

		onLoadSuccess : function(data) {
			var $combogrid = $(this);
			//cache selectCodeValues
			var codeType = $combogrid.combogrid("options").codeType;
			var selectCodeData = data.result;
			var selectCodeDataObject = {};
			if (selectCodeData) {
				for (var i = 0; i < selectCodeData.dataList.length; i++) {
					selectCodeDataObject[selectCodeData.dataList[i][selectCodeData.keyFieldName]] = selectCodeData.dataList[i][selectCodeData.labelFieldName];
				}
				var selectCodeValuesObject = {};
				selectCodeValuesObject[codeType] = selectCodeDataObject;
				$.extend(true, SELECT_CODE_VALUES, selectCodeValuesObject);
			}
			
			var $datagrid = $combogrid.combogrid("grid");

			//if exactly one row is matched, select it.
			if (! $combogrid.combo("options").customValuePermitted
						&& ! $combogrid.combo("options").multiple) {
				if ($datagrid.datagrid("getRows").length == 1) {
					$datagrid.datagrid("selectRow", 0);
				} else {
					var q = $combogrid.combogrid("getText").toUpperCase();
					var fields = $datagrid.datagrid("getColumnFields");
					$.each($datagrid.datagrid("getRows"), function(rowIndex, rowData) {
						for (var i = 0; i < fields.length; i++) {
							if (rowData[fields[i]] && (rowData[fields[i]] + "").toUpperCase() == q) {
								$datagrid.datagrid("selectRow", rowIndex);
								return false;
							}
						}
					});
				}
			}
			
			$datagrid.removeData("loading");
		},
		
		onLoadError : $.fn.datagrid.defaults.onLoadError

	});

	//combogrid methods
	$.extend($.fn.combogrid.methods, {
		
		setQueryFields : function(jq, queryFields) {
			return jq.each(function() {
				$(this).combogrid("grid").datagrid("options").queryFields = queryFields;
			});
		},
		
		//override setValue, set text at the same time
		_setValue : $.fn.combogrid.methods.setValue,
		setValue : function(jq, value) {
			return jq.each(function() {
				var $combogrid = $(this);
				$combogrid.combogrid("setValues", value ? (value + "").split(",") : []);
				$combogrid.data("oldValues", $combogrid.combogrid("getValues"));
				$combogrid.data("oldText", $combogrid.combogrid("getText"));
				if (! value) {
					return;
				}
				var codeType = $combogrid.combogrid("options").codeType;
				if (! codeType) {
					return;
				}
				if (SELECT_CODE_VALUES[codeType] && SELECT_CODE_VALUES[codeType][value]) {
					$combogrid.combogrid("setText", SELECT_CODE_VALUES[codeType][value]);
					$combogrid.data("oldText", $combogrid.combogrid("getText"));
				} else {
					var selectCodeKeys = {};
					selectCodeKeys[codeType] = value;
					SelectCodeManager.getSelectCodeValuesByKeys(selectCodeKeys, function(result) {
						$.extend(true, SELECT_CODE_VALUES, result);
						value = value + "";
						if (value.split(",").length > 1) {
							var values = value.split(",");
							var texts = [];
							for (var i = 0; i < values.length; i++) {
								texts.push(result[codeType][values[i]] || values[i]);
							}
							SELECT_CODE_VALUES[codeType][value] = texts.join(",");
						}
						$combogrid.combogrid("setText", SELECT_CODE_VALUES[codeType][value] || value);
						$combogrid.data("oldText", $combogrid.combogrid("getText"));
					});
				}
			});
		}
	});

	//init combogrids
	function initCombogrids(jqInput, jqTh) {
		jqInput.each(function() {
			var $combogrid = $(this);
			$combogrid.attr("comboname", $combogrid.attr("name"));
		});
		var codeTypes = {};
		jqInput.add(jqTh).each(function() {
			var $combogrid = $(this);
			var codeType = $combogrid.attr("codeType");
			if (codeType && ! codeTypes[codeType]) {
				codeTypes[codeType] = {};
			}
		});
		if ($.isEmptyObject(codeTypes)) {
			return;
		}
		var readyCodeTypes = [];
		var codeTypeList = [];
		for (var codeType in codeTypes) {
			if (SELECT_CODE_DEFINITIONS[codeType]) {
				readyCodeTypes.push(codeType);
			} else {
				codeTypeList.push(codeType);
			}
		}
		if (readyCodeTypes.length > 0) {
			setTimeout(function() {
				$.each(readyCodeTypes, function(i, codeType) {
					initCombogridsByCodeType(jqInput, jqTh, codeType, SELECT_CODE_DEFINITIONS[codeType]);
				});
			}, 0);
		}
		if (codeTypeList.length > 0) {
			SelectCodeManager.getSelectCodeDefinitions(codeTypeList, function(result) {
				setTimeout(function() {
					for (var codeType in result) {
						var selectCodeDefinition = result[codeType];
						SELECT_CODE_DEFINITIONS[codeType] = selectCodeDefinition;
						initCombogridsByCodeType(jqInput, jqTh, codeType, selectCodeDefinition);
					}
				}, 0);
			});
		}
	};

	function initCombogridsByCodeType(jqInput, jqTh, codeType, selectCodeDefinition) {
		var combogridOptions;
		if (COMBOGRID_OPTIONS && COMBOGRID_OPTIONS[codeType]) {
			combogridOptions = COMBOGRID_OPTIONS[codeType];
		} else {
			combogridOptions = SELECT_CODE_DEFINITIONS[codeType];
		}
		var columns = combogridOptions.columns;
		if (! columns) {
			columns = [{field : selectCodeDefinition.keyFieldName}, {field : selectCodeDefinition.labelFieldName}];
		}
		var fieldCodeTypes = {};
		for (var i = 0; i < columns.length; i++) {
			var columnOption = columns[i];
			$.extend(columnOption, {
				title : getI18nTitle(combogridOptions.i18nRoot, columnOption.field, columnOption.title),
				sortable : columnOption.sortable == undefined ? true : 
					(columnOption.sortable == true || columnOption.sortable == "true"),
				formatter : columnOption.formatter || $.fn.datagrid.defaults.formatter
			});
			if (columnOption.codeType) {
				fieldCodeTypes[columnOption.field] = columnOption.codeType;
			}
		}
		var options = {
				codeType : codeType,
				idField : selectCodeDefinition.keyFieldName,
				textField : selectCodeDefinition.labelFieldName,
				columns : [columns],
				parameters : {
					serviceName : "selectCodeManager",
					methodName : "getSelectCodeData",
					parameters : {
						queryInfo : {
							queryType : codeType,
							fieldCodeTypes : fieldCodeTypes
						}
					}
				},
				queryFields : selectCodeDefinition.queryFields
		};
		jqInput.filter("[codeType='" + codeType + "']").each(function() {
			var $combogrid = $(this);
			var value = $.data(this, "combogrid") ? $combogrid.combogrid("getValue") : $(this).val();
			if ($.data(this, "combogrid")) {
				$combogrid.__combogrid($.extend(true, $combogrid.combogrid("options"), options));
			} else if ($.data(this, "combo")) {
				$combogrid.__combogrid($.extend(true, $combogrid.combo("options"), $.fn.combogrid.parseOptions(this), options));
			} else {
				$combogrid.combogrid($.extend(true, $.fn.combogrid.parseOptions(this), options));
			}
			if (value) {
				$combogrid.combogrid("setValue", value);
			}
			if ($combogrid.combogrid("options").onReady) {
				$combogrid.combogrid("options").onReady.apply($combogrid[0]);
			}
			if ($combogrid.data("onReady")) {
				$combogrid.data("onReady").apply($combogrid[0]);
			}
		});
		jqTh.filter("[codeType='" + codeType + "']").each(function() {
			var $th = $(this);
			var $datagrid = $th.closest("table");
			var columnOption = $datagrid.datagrid("getColumnOption", $th.attr("field"));
			columnOption.editor = {
					type : "combogrid",
					init : true,
					options : columnOption.editor.options ? $.extend(columnOption.editor.options, options) : options
			};
		});
	};
	

	/********** combotree **********/
	$.fn._combotree = $.fn.combotree;

	$.fn.combotree = function(options, param) {
		if (typeof options == "string") {
			this.each(function() {
				if (! $.data(this, "combotree")) {
					$(this).combotree({});
				}
			});
		}
		return $.fn._combotree.apply(this, [options, param]);
	};

	$.fn.combotree.methods = $.fn._combotree.methods;
	$.fn.combotree.parseOptions = $.fn._combotree.parseOptions;
	$.fn.combotree.defaults = $.fn._combotree.defaults;
	
	$.extend(true, $.fn.combotree.defaults, treeDefaults, {
		panelWidth : 250,
		panelHeight : 300,
		width : 155,
		
		onLoadSuccess : function(node, data) {
			var $combotree = $(this);
			//cache selectCodeValues
			var codeType = $combotree.tree("options").codeType;
			var selectCodeDataObject = {};
			function handleNode(node) {
				selectCodeDataObject[node.id] = node.text;
				if (node.children) {
					$.each(node.children, function(index, child) {
						handleNode(child);
					});
				}
			};
			$.each(data, function(index, node) {
				handleNode(node);
			});
			var selectCodeValuesObject = {};
			selectCodeValuesObject[codeType] = selectCodeDataObject;
			$.extend(true, SELECT_CODE_VALUES, selectCodeValuesObject);
		}
	});

	$.extend($.fn.combotree.methods, {
		
		_setValue : $.fn.combotree.methods.setValue,
		
		setValue : function(jq, value) {
			return jq.each(function() {
				var $combotree = $(this);
				$combotree.combotree("setValues", value ? (value + "").split(",") : []);
			});
		}

	});

	//init combotrees
	function initCombotrees(jqInput, jqTh) {
		jqInput.each(function() {
			var $combotree = $(this);
			$combotree.attr("comboname", $combotree.attr("name"));
		});
		var codeTypes = {};
		jqInput.add(jqTh).each(function() {
			var $combotree = $(this);
			var codeType = $combotree.attr("codeType");
			if (codeType && ! codeTypes[codeType]) {
				codeTypes[codeType] = {};
			}
		});
		if ($.isEmptyObject(codeTypes)) {
			return;
		}
		var readyCodeTypes = [];
		var codeTypeList = [];
		for (var codeType in codeTypes) {
			if (SELECT_CODE_DEFINITIONS[codeType]) {
				readyCodeTypes.push(codeType);
			} else {
				codeTypeList.push(codeType);
			}
		}
		if (readyCodeTypes.length > 0) {
			setTimeout(function() {
				$.each(readyCodeTypes, function(i, codeType) {
					initCombotreesByCodeType(jqInput, jqTh, codeType, SELECT_CODE_DEFINITIONS[codeType]);
				});
			}, 0);
		}
		if (codeTypeList.length > 0) {
			SelectCodeManager.getSelectCodeDefinitions(codeTypeList, function(result) {
				setTimeout(function() {
					for (var codeType in result) {
						var selectCodeDefinition = result[codeType];
						SELECT_CODE_DEFINITIONS[codeType] = selectCodeDefinition;
						initCombotreesByCodeType(jqInput, jqTh, codeType, selectCodeDefinition);
					}
				}, 0);
			});
		}
	};

	function initCombotreesByCodeType(jqInput, jqTh, codeType, selectCodeDefinition) {
		var options = {
				codeType : codeType,
				idField : selectCodeDefinition.keyFieldName,
				textField : selectCodeDefinition.labelFieldName,
				parentField : selectCodeDefinition.parentFieldName,
				parameters : {
					serviceName : "commonQueryManager",
					methodName : "query",
					parameters : {
						queryInfo : {
							queryType : selectCodeDefinition.queryType,
							orderBy : selectCodeDefinition.orderBy
						}
					}
				},
				queryFields : selectCodeDefinition.queryFields
		};
		jqInput.filter("[codeType='" + codeType + "']").each(function() {
			var $combotree = $(this);
			var value = $.data(this, "combotree") ? $combotree.combotree("getValue") : $(this).val();
			if ($.data(this, "combotree")) {
				$combotree.__combotree($.extend(true, $combotree.combotree("options"), options));
			} else if ($.data(this, "combo")) {
				$combotree.__combotree($.extend(true, $combotree.combo("options"), $.fn.combotree.parseOptions(this), options));
			} else {
				$combotree.combotree($.extend(true, $.fn.combotree.parseOptions(this), options));
			}
			if (value) {
				$combotree.combotree("setValue", value);
			}
			if ($combotree.combotree("options").onReady) {
				$combotree.combotree("options").onReady.apply($combotree[0]);
			}
			if ($combotree.data("onReady")) {
				$combotree.data("onReady").apply($combotree[0]);
			}
		});
		jqTh.filter("[codeType='" + codeType + "']").each(function() {
			var $th = $(this);
			var $datagrid = $th.closest("table");
			var columnOption = $datagrid.datagrid("getColumnOption", $th.attr("field"));
			columnOption.editor = {
					type : "combotree",
					init : true,
					options : columnOption.editor.options ? $.extend(columnOption.editor.options, options) : options
			};
		});
	};
	

	/********** datebox **********/
	$.extend(true, $.fn.datebox.defaults, {
		width : 155,
		
		formatter : function(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
		},
		
		parser : function(s){
			if (!s) return new Date();
			var ss = s.split('-');
			var y = parseInt(ss[0] || '0',10);
			var m = parseInt(ss[1] || '1',10);
			var d = parseInt(ss[2] || '1',10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
	});

	
	/********** numberbox **********/
	$.fn._numberbox = $.fn.numberbox;

	$.fn.numberbox = function(options, param) {
		var result = $.fn._numberbox.apply(this, [options, param]);
		if (typeof options != "string") {
			this.each(function() {
				var target = this;
				$(target).unbind("blur.numberbox").bind("blur.numberbox", function() {
					$(target).numberbox("fix");
				});
			});
		}
		return result;
	};

	$.fn.numberbox.methods = $.fn._numberbox.methods;
	$.fn.numberbox.defaults = $.fn._numberbox.defaults;
	$.fn.numberbox.parseOptions = $.fn._numberbox.parseOptions;

	$.extend($.fn.numberbox.methods, {
		_fix : $.fn.numberbox.methods.fix,
		
		fix : function(jq) {
			return jq.each(function() {
				var $numberbox = $(this);
				$numberbox.val($numberbox.numberbox("getValue"));
				$numberbox.numberbox("_fix");
				$numberbox.numberbox("setValue", $numberbox.numberbox("getValue"));
			});
		},
		
		getValue : function(jq) {
			var $numberbox = $(jq[0]);
			var format = $.data(jq[0], "numberbox").options.format;
			var value = $numberbox.val();
			if (! value) {
				return value;
			}
			if (format) {
				value = $.parseNumber(value, {
					format : format
				});
			}
			return parseFloat(value);
		},
		
		setValue : function(jq, value) {
			return jq.each(function() {
				var $numberbox = $(this);
				var format = $.data(this, "numberbox").options.format;
				if (format && (value || value === 0)) {
					value = $.formatNumber(value, {
						format : format
					});
				}
				$numberbox.val(value);
			});
		}
	});
	
	function initNumberboxes(jqInput, jqTh) {
		jqInput.each(function() {
			var $numberbox = $(this);
			var options = $.data(this, "numberbox").options;
			$.extend(options, {
				format : $numberbox.attr("format") ? $numberbox.attr("format") : null,
				precision : $numberbox.attr("format") ? 10 : options.precision
			});
		});
		jqTh.each(function() {
			var $th = $(this);
			var $datagrid = $th.closest("table");
			var columnOption = $datagrid.datagrid("getColumnOption", $th.attr("field"));
			var options = columnOption.editor.options || {};
			$.extend(options, {
				format : $th.attr("format") ? $th.attr("format") : options.format,
				precision : $th.attr("format") || options.format ? 10 : options.precision
			});
			columnOption.editor = {
					type : "numberbox",
					options : options
			};
		});
	};
	
	
	/********** form **********/
	$.extend($.fn.form.methods, {
		setData : function(jq, data) {
			return jq.each(function() {
				var $form = $(this);
				$form.data("data", data);
				$form.find("input[name]:not(.combo-value), input[comboname], textarea[name]").each(function() {
					var $input = $(this);
					var name = $input.attr("name") || $input.attr("comboname");
					var value = data[name] === 0 ? 0 : data[name] || "";
					if ($input.hasClass("combo-multi")) {
						$input.combo("setComboMultiValues", value.split(","));
					} else if ($input.hasClass("easyui-combobox")) {
						$input.combobox("setValue", value);
					} else if ($input.hasClass("easyui-combogrid")) {
						$input.combogrid("setValue", value);
					} else if ($input.hasClass("easyui-combotree")) {
						$input.combotree("setValue", value);
					} else if ($input.hasClass("easyui-datebox")) {
						$input.datebox("setValue", value);
					} else if ($input.hasClass("easyui-datetimebox")) {
						$input.datetimebox("setValue", value);
					} else if ($input.hasClass("easyui-numberbox")) {
						$input.numberbox("setValue", value);
					} else if ($input.attr("type") == "checkbox") {
						var values = value.split(",");
						this.checked = values.indexOf($input.attr("on")) >= 0 || values.indexOf($input.attr("value")) >= 0;
					} else if ($input.attr("type") == "radio") {
						this.checked = value == $input.attr("value");
					} else {
						$input.val(value);
					}
					if ($input.hasClass("validatebox-text") && ! $input.hasClass("combo-f")) {
						clearTimeout($input.data("validateTimer"));
						setTimeout(function() {
							$input.data("validateTimer", setTimeout(function() {
								$input.validatebox("validate", false);
							}, 0));
						}, 0);
					}
				});
				$form.data("oldData", $form.form("getData"));
			});
		},
		
		getData : function(jq) {
			var $form = $(jq[0]);
			var data = {};
			$form.find("input[name]:not(.combo-value), input[comboname], textarea[name]").each(function() {
				var $input = $(this);
				var name = $input.attr("name") || $input.attr("comboname");
				var value;
				if ($input.hasClass("combo-multi")) {
					value = $input.combo("getComboMultiValues").join(",");
				} else if ($input.hasClass("combo-f")) {
					value = $input.combo("getValue");
				} else if ($input.hasClass("easyui-numberbox")) {
					value = $input.numberbox("getValue");
				} else if ($input.attr("type") == "checkbox") {
					value = this.checked ? ($input.attr("on") || $input.attr("value")) : $input.attr("off");
					if (value) {
						if (data[name]) {
							value = data[name] + "," + value;
						}
					} else {
						value = data[name];
					}
				} else if ($input.attr("type") == "radio") {
					value = this.checked ? $input.attr("value") : data[name];
				} else {
					value = $input.val();
				}
				if (value === "") {
					value = null;
				}
				data[name] = value;
			});
			return $.extend({}, $form.data("data"), data);;
		},
		
		clear : function(jq) {
			var $form = $(jq[0]);
			$form.form("setData", {});
		},
		
		getQueryFields : function(jq) {
			var $form = $(jq[0]);
			var queryFields = [];
			var handledMultiples = [];
			$form.find("input[name],textarea").each(function() {
				var $input = $(this);
				var name = $input.attr("name");
				var value = $input.val();
				if (! value || handledMultiples.indexOf(name) >= 0) {
					return;
				}
				var fieldType;
				var operator = $input.attr("operator");
				if ($input.hasClass("combo-value")) {
					var $comboInput = $input.parent().prev();
					if ($comboInput.hasClass("easyui-datebox") || $comboInput.hasClass("easyui-datetimebox")) {
						fieldType = "Date";
					}
					operator = $comboInput.attr("operator");
					if ($comboInput.attr("multiple")) {
						var values = [];
						$form.find("input[name='" + name + "']").each(function() {
							values.push(this.value);
						});
						queryFields.push({
							fieldName : name,
							fieldType : "String[]",
							fieldStringValue : values.join(","),
							operator : operator
						});
						handledMultiples.push(name);
						return;
					}
				}
				if ($input.attr("type") == "checkbox") {
					operator = $input.closest("[operator]").attr("operator");
					var values = [];
					$form.find("input[name='" + name + "']:checked").each(function() {
						values.push($(this).attr("on") || $(this).attr("value"));
					});
					queryFields.push({
						fieldName : name,
						fieldType : "String[]",
						fieldStringValue : values.join(","),
						operator : operator
					});
					handledMultiples.push(name);
					return;
				}
				if ($input.attr("type") == "radio") {
					operator = $input.closest("[operator]").attr("operator");
					value = $form.find("input[name='" + name + "']:checked").attr("value");
					queryFields.push({
						fieldName : name,
						fieldStringValue : value,
						operator : operator
					});
					handledMultiples.push(name);
					return;
				}
				if ($input.hasClass("easyui-numberbox")) {
					value = $input.numberbox("getValue");
				}
				queryFields.push({
					fieldName : name,
					fieldType : fieldType,
					fieldStringValue : value + "",
					operator : operator
				});
			});
			$form.find("input.combo-multi[comboname]").each(function() {
				var $input = $(this);
				var name = $input.attr("comboname");
				var values = $input.combo("getComboMultiValues");
				if (! values) {
					return;
				}
				var operator = $input.attr("operator");
				queryFields.push({
					fieldName : name,
					fieldType : "String[]",
					fieldStringValue : values.join(","),
					operator : operator
				});
			});
			return queryFields;
		},
		
		dataChanged : function(jq) {
			var $form = $(jq[0]);
			return $.toJSON($form.data("oldData")) != $.toJSON($form.form("getData"));
		},
		
		disable : function(jq) {
			return jq.each(function() {
				var $form = $(this);
				$form.find("input[name]:not(.combo-value):not([disabled_fixed],[readonly_fixed])").attr("disabled", "disabled");
				$form.find("textarea[name]:not([disabled_fixed],[readonly_fixed])").attr("disabled", "disabled");
				$form.find("input.combo-f:not([disabled_fixed],[readonly_fixed])").combo("disable");
			});
		},
		
		enable : function(jq) {
			return jq.each(function() {
				var $form = $(this);
				$form.find("input[name]:not(.combo-value):not([disabled_fixed],[readonly_fixed])").removeAttr("disabled");
				$form.find("textarea[name]:not([disabled_fixed],[readonly_fixed])").removeAttr("disabled");
				$form.find("input.combo-f:not([disabled_fixed],[readonly_fixed])").combo("enable");
			});
		},
		
		readonly : function(jq, readonly) {
			return jq.each(function() {
				var $form = $(this);
				readonly = readonly == undefined ? true : readonly;
				$form.find("input[name]:not(.combo-value):not([disabled_fixed],[readonly_fixed])").attr("readonly", readonly);
				$form.find("textarea[name]:not([disabled_fixed],[readonly_fixed])").attr("readonly", readonly);
				$form.find("input.combo-f:not([disabled_fixed],[readonly_fixed])").combo("readonly", readonly);
			});
		},
		
		fitHeight : function(jq) {
			return jq.each(function() {
				var $form = $(this);
				if ($form.data("fittedHeight")) {
					return;
				}
				if ($form.find("table").size() == 0) {
					return;
				}
				if ($form.closest(".panel:hidden").size() != 0) {
					return;
				}
				var $panel = $form.closest("div[region='north'], div[region='south']");
				if ($panel.size() > 0) {
					var $div = $("<div/>");
					$div.append($panel.children()).appendTo($panel);
					var height = $div.height() + 4;
					if (! $panel.panel("options").noheader && $panel.panel("options").title) {
						height += 26;
					}
					$panel.panel("resize", {height : height});
					$panel.closest(".easyui-layout").layout("resize");
				}
				$form.data("fittedHeight", true);
			});
		}

	});
	

	//init forms
	function initForms(jq) {
		//mark the disabled and readonly fields
		$("input[disabled]", jq).attr("disabled_fixed", true);
		$("textarea[disabled]", jq).attr("disabled_fixed", true);
		$("input[readonly]", jq).attr("readonly_fixed", true);
		$("textarea[readonly]", jq).attr("readonly_fixed", true);
		//add validatebox class for required input
		//$("input[required]:not(.easyui-validatebox)", jq).validatebox();
		//auto format form items into columns
		jq.filter("[columns]").each(function() {
			var $form = $(this);
			var $div = $("<div/>").insertBefore($form);
			var columns = $form.attr("columns");
			if (! columns) {
				columns = 3;
			}
			var vertical = $form.attr("orientation") == "vertical";
			$form.find("textarea").each(function(index, textarea) {
				var $textarea = $(textarea);
				if ($textarea.css("display") == "none") {
					$textarea.attr("type", "hidden");
				}
			});
			var $inputs = $form.children(":not(.combo):not([type=hidden])");
			$form.detach();
			if (vertical) {
				var rows = Math.floor($inputs.size() / columns);
				if ($inputs.length % columns > 0) {
					rows++;
				}
				var verticalInputs = [];
				for (var i = 0; i < rows; i++) {
					for (var j = 0; j < columns; j++) {
						verticalInputs.push($inputs[i + j * rows]);
					}
				}
				$inputs = $(verticalInputs);
				$form.find("input").each(function(index, input) {
					$(input).attr("tabindex", index + 1);
				});
			}
			var $table = $("<table/>").appendTo($form);
			var $tr;
			var indexInRow = 0;
			$inputs.each(function(index, input) {
				var $input = $(input);
				if (indexInRow == 0 || indexInRow >= columns) {
					$tr = $("<tr/>").appendTo($table);
					indexInRow = 0;
				}
				if (! input) {
					return;
				}
				var $next = $input.next();
				var i18nKey = $input.attr("name");
				if (! i18nKey) {
					i18nKey = $input.find("[name]").attr("name");
				}
				if (! i18nKey && $next.hasClass("combo")) {
					i18nKey = $next.find("[name]").attr("name");
				}
				if (! i18nKey) {
					i18nKey = $input.attr("comboname");
				}
				if (! i18nKey) {
					i18nKey = $input.find("[comboname]").attr("comboname");
				}
				var title = $input.attr("title");
				if (title) {
					$input.removeAttr("title");
				} else {
					title = $input.find("[name]").attr("title");
					if (title) {
						$input.find("[name]").removeAttr("title");
					}
				}
				var colspan = $input.attr("colspan");
				if (! colspan) {
					colspan = $input.find("[name]").attr("colspan");
				}
				var label = getI18nTitle($form.attr("i18nRoot"), i18nKey, title);
				if (label == "NONE") {
					//
				} else if (label) {
					if ($input.attr("required") || $input.find("[required]").size() > 0) {
						label = "<span style='color: red;'>* </span>" + label;
					}
					$tr.append("<td class='form-label' align='right'> " + label + "</td>");
				} else {
					$tr.append("<td class='form-label' />");
				}
				var $td = $("<td/>").appendTo($tr).append($input);
				if ($next.hasClass("combo")) {
					$td.append($next);
				}
				if ($input.attr("rowspan")) {
					$td.attr("rowspan", $input.attr("rowspan"));
				}
				indexInRow++;
				if ((colspan && ! vertical) || label == "NONE") {
					colspan = parseInt(colspan) || 1;
					indexInRow += colspan - 1;
					colspan = colspan * 2 - 1;
					if (label == "NONE") {
						colspan++;
					}
					$td.attr("colspan", colspan);
					$input.css("width", "100%");
				}
			});
			$form.insertAfter($div);
			$div.detach();
		});
	};


	/********** linkbuttons **********/
	$.extend($.fn.linkbutton.methods, {
		forceDisable : function(jq) {
			return jq.each(function() {
				var $linkbutton = $(this);
				$linkbutton.attr("forceDisable", true);
				$linkbutton.linkbutton("disable");
			});
		},
		
		_enable : $.fn.linkbutton.methods.enable,
		
		enable : function(jq) {
			return jq.each(function() {
				var $linkbutton = $(this);
				if (! $linkbutton.attr("forceDisable")) {
					$linkbutton.linkbutton("_enable");
				}
			});
		}
	});
	
	
	//init linkbuttons
	function initLinkbuttons(jq) {
		jq.filter("[code]").each(function() {
			var $linkbutton = $(this);
			var code = $linkbutton.attr("code");
			if (! checkFunctionAuthorization(code)) {
				$linkbutton.linkbutton("forceDisable");
			}
		});
	}
	
	
	/********** messager **********/
	$.extend($.messager.defaults, {
		toastTimeout : 1000
	});
	
	$.messager.toast = function(title, msg, icon, fn) {
		var $win = $.messager.alert(title, msg, icon, fn);
		$win.parent().find(".window-header").hide();
		$win.find(".messager-button").hide();
		$win.parent().next().height($win.parent().height() + 12);
		setTimeout(function() {
			$win.window("close");
		}, $.messager.defaults.toastTimeout);
	};
	
	
	/********** panels **********/
	$.extend($.fn.panel.defaults, {
		reLoginMsg : "Session timeout. Please re-login and try again.",
		setDefaultContextErrorMsg : "JQuery selectors detected in the page. But default context can't be set automatically cause '$(function(){...})' can't be located. Please fix the page source code!",
		
		extractor : function(data) {
			if (/<!--LoginPage-->/im.exec(data)) {
				$.messager.confirm("Message", $.fn.panel.defaults.reLoginMsg, function(b) {
					if (b) {
						window.location.reload();
					}
				});
				return "";
			}
			var index1 = data.indexOf("$(function(");
			if (index1 >= 0) {
				index2 = data.indexOf("{", index1);
				if (index2 >= 0) {
					var contextid = $.now();
					$(this).attr("_contextid", contextid);
					data = data.substring(0, index1) +
					//"var __onLoad = $('[_contextid=" + contextid + "]').panel('options').onLoad;\n" +
					"($('[_contextid=" + contextid + "]').panel('options').onLoadAsync = " +
					data.substring(index1 + 2, index2 + 1) + "\n\n" +
					"var _pageContext = jQuery('[_contextid=" + contextid + "]');\n" +
					"var $ = function(selector, context) {\n" +
					"	if (context) {\n" +
					"		return jQuery(selector, context);\n" +
					"	} else {\n" +
					"		return jQuery(selector, _pageContext);\n" +
					"	}\n" +
					"};\n" +
					"jQuery.extend($, jQuery);\n" +
					"PREVENT_REINIT_PLUGINS = true;\n" +
					"setTimeout(function() {\n" +
					"	PREVENT_REINIT_PLUGINS = false;\n" +
					"}, 0);\n" +
					//"__onLoad.apply(this);\n" +
					data.substring(index2 + 1);
					return data;
				}
			}
			if (data.indexOf("$(") >= 0) {
				$.messager.alert("Message", $.fn.panel.defaults.setDefaultContextErrorMsg, "error");
			}
			return data;
		}
	});
	
	$.extend($.fn.panel.methods, {
		loading : function(jq) {
			return jq.each(function() {
				var wrap = $(this);
				$("<div class='datagrid-mask panel' style='z-index: 10000;'></div>").css({
					display : "block",
					width : wrap.width(),
					height : wrap.height()
				}).appendTo(wrap);
				$("<div class='datagrid-mask-msg panel' style='z-index: 10000;'></div>")
				.html($.fn.datagrid.defaults.loadMsg).appendTo(wrap).css({
					display : "block",
					left : (wrap.width() - $("div.datagrid-mask-msg", wrap).outerWidth()) / 2,
					top : (wrap.height() - $("div.datagrid-mask-msg", wrap).outerHeight()) / 2
				});
			});
		},
		
		loaded : function(jq) {
			return jq.each(function() {
				var wrap = $(this);
				wrap.children("div.datagrid-mask-msg").remove();
				wrap.children("div.datagrid-mask").remove();
			});
		}
	});
	
	
	/********** searchboxes **********/
	function initSearchboxes(jq) {
		jq.each(function() {
			var $searchbox = $(this);
			var $resetBtn = $("<span><span class='clear-input'></span></span>");
			$resetBtn.click(function() {
				$searchbox.searchbox("setValue", null);
				$(this).parent().find(".searchbox-button").click();
			});
			$searchbox.searchbox("textbox").parent().append($resetBtn);
		});
	};
	
	
	/********** toolbar **********/
	function initToolbars(jq) {
		jq.each(function() {
			$(this).find(".easyui-linkbutton").each(function(index) {
				var $linkbutton = $(this);
				$linkbutton.attr("plain", "true");
				if (index > 0) {
					$linkbutton.before($("<div class='datagrid-btn-separator' style='float:none; display:inline;'/>"));
				}
			});
		});
	};


	/********** window & dialog **********/
	$.extend($.fn.window.defaults, {
		inline : true,
		
		onOpen : function() {
			var $window = $(this);
			$window.window("resize");
			var position = $window.parent().position();
			var offset = $window.parent().offset();
			var left = (window.document.body.clientWidth - $window.parent().outerWidth()) / 2;
			var top = (window.document.body.clientHeight - $window.parent().outerHeight()) / 2;
			$window.window("move", {
				left : position.left - (offset.left - left),
				top : position.top - (offset.top - top)
			});
			$window.data("preFocus", $("*:focus"));
			$window.find("a").unbind("keydown").bind("keydown", function(e) {
				//13:Return; 32:Space; 27:Esc;
				if (e.keyCode == 13 || e.keyCode == 32) {
					e.target.click();
					e.preventDefault();
				} else if (e.keyCode == 27) {
					$(this).closest(".window").find(".panel-tool-close").click();
					e.preventDefault();
				}
			});
			$window.find("a:first").focus();
			$window.find("input:visible:first").focus();
		},
		
		onBeforeClose : function() {
			var $window = $(this);
			if ($window.data("preFocus")) {
				$window.data("preFocus").focus();
			}
		},
		
		onMove : function(left, top) {
			var $window = $(this);
			var offset = $window.parent().offset();
			if (offset.left < 0 || offset.top < 0) {
				$window.window("move", {
					left : left - Math.min(offset.left, 0),
					top : top - Math.min(offset.top, 0)
				});
			}
		}
	});

	$.extend($.fn.dialog.defaults, {
		inline : true,
		
		onOpen : $.fn.window.defaults.onOpen,
		onBeforeClose : $.fn.window.defaults.onBeforeClose,
		onMove : $.fn.window.defaults.onMove
	});

	function initDialogs(jq) {
		jq.each(function() {
			var $dialog = $(this);
			var $content = $dialog.children().children();
			$content.children(".dialog-buttons").addClass("dialog-button").appendTo($dialog);
			$content.children(".dialog-toolbars").addClass("dialog-toolbar").prependTo($dialog);
			$content.children(".dialog-button").appendTo($dialog);
			$content.children(".dialog-toolbar").prependTo($dialog);
		});
	};


	/********** menu **********/
	$.extend($.fn.menu.defaults, {
		onShow : function() {
			var $menu = $(this);
			$menu.data("originalTop", $menu.position().top);
			$menu.menu("fixPosition");
		}
	});

	$.extend($.fn.menu.methods, {
		fixPosition : function(jq) {
			return jq.each(function() {
				var $menu = $(this);
				if ($menu.position().top + $menu.outerHeight() > $("body").height() - 5) {
					var top = $menu.data("originalTop") - $menu.outerHeight() - 2;
					if (top < 0) {
						top = $("body").height() - $menu.outerHeight() - 5;
					}
					$menu.css("top", top + "px");
				}
			});
		}
	});


	/********** private methods **********/
	
	function getI18nTitle(i18nRoot, i18nKey, defaultTitle) {
		if (defaultTitle != undefined) {
			return defaultTitle;
		}
		if (! i18nRoot) {
			return i18nKey;
		}
		var i18nRoots = i18nRoot.split(",");
		for (var i = 0; i < i18nRoots.length; i++) {
			var root = "i18n." + $.trim(i18nRoots[i]);
			if (eval(root) && eval(root)[i18nKey]) {
				return eval(root)[i18nKey];
			}
		}
		return i18nKey;
	};
	
	function initDatagridEditors($datagrid) {
		var options = $datagrid.datagrid("options");

		//check if editor options are ok
		var editorOptionsOk = true;
		$.each(options.frozenColumns.concat(options.columns), function(index, columnOptions) {
			$.each(columnOptions, function(index, columnOption) {
				if (! columnOption.editor) {
					return;
				}
				if (columnOption.editor == "combobox"
						|| columnOption.editor == "combogrid"
						|| columnOption.editor == "combotree") {
					editorOptionsOk = false;
					return false;
				}
				if ((columnOption.editor.type == "combobox"
						|| columnOption.editor.type == "combogrid"
						|| columnOption.editor.type == "combotree")
						&& columnOption.codeType && ! columnOption.editor.init ) {
					editorOptionsOk = false;
					return false;
				}
			});
		});
		if (! editorOptionsOk) {
			setTimeout(function() {
				initDatagridEditors($datagrid);
			}, 100);
			return;
		}
		
		var $headerTr = $datagrid.datagrid("getPanel").children(".datagrid-view")
		.children(".datagrid-view1,.datagrid-view2").children(".datagrid-header").find("tr");
		//editor cells
		var editorCells = {};
		$datagrid.data("editorCells", editorCells);
		$.each(options.frozenColumns.concat(options.columns), function(index, columnOptions) {
			$.each(columnOptions, function(index, columnOption) {
				if (! columnOption.editor) {
					return;
				}
				var editorCell = editorCells[columnOption.field];
				if (! editorCell) {
					editorCell = $("<div class='datagrid-cell datagrid-editable'/>");
					editorCells[columnOption.field] = editorCell;
					editorCell.html("<table border='0' cellspacing='0' cellpadding='1'><tr><td></td></tr></table>");
					editorCell.children("table").attr("align", columnOption.align);
					editorCell.children("table").bind("click dblclick contextmenu", function(e) {
						e.stopPropagation();
					});
				}
				editorCell.width(columnOption.width || $headerTr.find("td[field='" + columnOption.field + "']").width());
			});
		});
		//editor targets
		if ($datagrid.data("editorTargets")) {
			return;
		}
		var editorTargets = {};
		$datagrid.data("editorTargets", editorTargets);
		$.each(options.frozenColumns.concat(options.columns), function(index, columnOptions) {
			$.each(columnOptions, function(index, columnOption) {
				if (! columnOption.editor) {
					return;
				}
				var type, editorOptions;
				if (typeof columnOption.editor == "string") {
					type = columnOption.editor;
				} else {
					type = columnOption.editor.type;
					editorOptions = columnOption.editor.options;
				}
				var editor = options.editors[type];
				var container = $("<div/>");
				var target = editor._init(container, editorOptions);
				editorTargets[columnOption.field] = [];
				container.children().each(function() {
					editorTargets[columnOption.field].push($(this));
				});
				//editor.resize(target, columnOption.width || $headerTr.find("td[field='" + columnOption.field + "']").width());
			});
		});
		$datagrid.data("editorsInitOk", true);
		if ($datagrid.data("toStartEdit") != undefined) {
			$datagrid.datagrid("beginEdit", $datagrid.data("toStartEdit"));
		}
	};
	
	function resizeDatagridEditor(target, field, width) {
		var $datagrid = $(target);
		var editorCells = $datagrid.data("editorCells");
		if (editorCells) {
			var editorCell = editorCells[field];
			if (editorCell) {
				if (editorCell.parent().length == 0) {
					editorCell.width(width);
				} else {
					var viewerCell = editorCell.closest("td").data("viewerCell");
					if (viewerCell) {
						viewerCell.width(width - 8);
					}
				}
			}
		}
	};
	
	function fitColumnWidth(target) {
		var $datagrid = $(target);
		if ($datagrid.find("th:not([width])").size() == 0
				&& $datagrid.closest(".combo-panel").size() == 0) {
			return;
		}
		var $datagridView = $datagrid.datagrid("getPanel").children(".datagrid-view")
		.children(".datagrid-view1,.datagrid-view2");
		if ($datagridView.find(">.datagrid-body,>.datagrid-body>.datagrid-body-inner").children("table")
				.children("tbody").children("tr.datagrid-row-editing").size() > 0) {
			return;
		}
		var $headerTds = $datagrid.datagrid("getPanel").children(".datagrid-view")
		.children(".datagrid-view1,.datagrid-view2").children(".datagrid-header").find("td > div");
		if ($datagrid.hasClass("easyui-treegrid")) {
			var $trs = $();
			if ($datagrid.treegrid("options").tr2Cache) {
				$.each($datagrid.treegrid("options").tr2Cache, function(id, tr2) {
					$trs = $trs.add(tr2);
				});
			}
			$headerTds.each(function(index) {
				var $headerTd = $(this);
				if (! $headerTd.hasClass("datagrid-cell")) {
					return;
				}
				var field = $headerTd.parent().attr("field");
				var columnOptions = $datagrid.datagrid("getColumnOption", field);
				if (columnOptions.width) {
					return;
				}
				var headerWidth;
				if ($headerTd.is(":visible")) {
					headerWidth = $headerTd.width();
				} else {
					var $clone = $headerTd.clone().appendTo("body").css("display", "inline");
					headerWidth = $clone.width();
					$clone.detach();
				}
				var $bodyTds = $trs.children("td[field='" + field + "']").children("div");
				var bodyWidth = 0;
				$bodyTds.each(function(index, bodyTd) {
					var $bodyTd = $(bodyTd);
					if ($bodyTd.is(":visible")) {
						bodyWidth = Math.max(bodyWidth, $(bodyTd).width());
					} else {
						var $clone = $bodyTd.clone().appendTo("body").css("display", "inline");
						bodyWidth = Math.max(bodyWidth, $clone.width());
						$clone.detach();
					}
				});
				var width = Math.max(headerWidth, bodyWidth);
				if (width < 80) {
					width = 80;
				}
				if (! columnOptions.extraWidthAdded) {
					var editor = columnOptions.editor;
					if (editor) {
						if (typeof editor != "string") {
							editor = editor.type;
						}
						if (editor.indexOf("combo") >= 0 || editor.indexOf("date") >= 0) {
							width += 30;
						}
					}
					columnOptions.extraWidthAdded = true;
				}
//				if ($bodyTds.size() == 0) {
//					columnOptions.boxWidth = width;
//				}
				$headerTd.width(width);
				$bodyTds.width(width);
//				var $footerTds = $datagridView.children(".datagrid-footer").find("td[field='" + field + "'] > div");
//				$footerTds.width(width);
				resizeDatagridEditor(target, field, width + 8);
			});
		} else {
			$headerTds.each(function(index) {
				var $headerTd = $(this);
				if (! $headerTd.hasClass("datagrid-cell")) {
					return;
				}
				var field = $headerTd.parent().attr("field");
				var columnOptions = $datagrid.datagrid("getColumnOption", field);
				if (columnOptions.width) {
					return;
				}
				var headerWidth;
				if ($headerTd.is(":visible")) {
					headerWidth = $headerTd.width();
				} else {
					var $clone = $headerTd.clone().appendTo("body").css("display", "inline");
					headerWidth = $clone.width();
					$clone.detach();
				}
				var $bodyTds = $datagridView.find(">.datagrid-body,>.datagrid-body>.datagrid-body-inner")
				.children("table").children("tbody").children("tr").children("td[field='" + field + "']").children("div");
				var bodyWidth = 0;
				if ($bodyTds.size() > 0) {
					var $bodyTd = $($bodyTds.get(0));
					if ($bodyTd.is(":visible")) {
						bodyWidth = $bodyTd.parent().width() - ($bodyTd.outerWidth() - $bodyTd.width());
					} else {
						var $clone = $bodyTd.clone().appendTo("body").css("display", "inline");
						bodyWidth = $clone.width();
						$clone.detach();
					}
				}
				var width = Math.max(headerWidth, bodyWidth);
				if (width < 80) {
					width = 80;
				}
				if (! columnOptions.extraWidthAdded) {
					var editor = columnOptions.editor;
					if (editor) {
						if (typeof editor != "string") {
							editor = editor.type;
						}
						if (editor.indexOf("combo") >= 0 || editor.indexOf("date") >= 0) {
							width += 30;
						}
					}
					columnOptions.extraWidthAdded = true;
				}
//				if ($bodyTds.size() == 0) {
//					columnOptions.boxWidth = width;
//				}
				$headerTd.width(width);
				$bodyTds.width(width);
//				var $footerTds = $datagridView.children(".datagrid-footer").find("td[field='" + field + "'] > div");
//				$footerTds.width(width);
				resizeDatagridEditor(target, field, width + 8);
			});
		}
	};

	function groupDatagrid(target, rows) {
		var $datagrid = $(target);
		var groupFields = $datagrid.datagrid("options").groupField;
		if (! groupFields) {
			return;
		}
		if (typeof groupFields == "string") {
			groupFields = groupFields.split(",");
		}
		for (var i = 0; i < groupFields.length; i++) {
			groupFields[i] = $.trim(groupFields[i]);
		}
		$datagrid.datagrid("options").groupFields = groupFields;
		groupDatagridOneColumn($datagrid, groupFields, groupFields[0], rows, 0, rows.length - 1);
		function groupDatagridOneColumn($datagrid, groupFields, field, rows, beginIndex, endIndex) {
			var lastValue = undefined;
			var lastIndex = beginIndex;
			for (var i = beginIndex; i <= endIndex; i++) {
				var value = rows[i][field];
				if (i > 0 && value == lastValue) {
					if (i == endIndex) {
						$datagrid.datagrid("mergeCells", {
							index : lastIndex,
							field : field,
							rowspan : i - lastIndex + 1
						});
						if (groupFields.indexOf(field) < groupFields.length - 1) {
							groupDatagridOneColumn($datagrid, groupFields, groupFields[groupFields.indexOf(field) + 1], rows, lastIndex, i);
						}
					}
				} else {
					if (i - lastIndex > 1) {
						$datagrid.datagrid("mergeCells", {
							index : lastIndex,
							field : field,
							rowspan : i - lastIndex
						});
						if (groupFields.indexOf(field) < groupFields.length - 1) {
							groupDatagridOneColumn($datagrid, groupFields, groupFields[groupFields.indexOf(field) + 1], rows, lastIndex, i - 1);
						}
					}
					lastValue = value;
					lastIndex = i;
				}
			}
		};
	};
	

	//override methods to cache datagrid editors
	function beginEdit(target, rowIndex) {
		var opts = $.data(target, 'datagrid').options;
		var tr = opts.editConfig.getTr(target, rowIndex);
		var row = opts.editConfig.getRow(target, rowIndex);
		if (tr.hasClass('datagrid-row-editing')) {
			return;
		}
		if (opts.onBeforeEdit.call(target, rowIndex, row) == false) {
			return;
		}
		tr.addClass('datagrid-row-editing');
		createEditor(target, rowIndex, tr);
		fixEditorSize(target, rowIndex, tr);
		tr.children('td').children('div.datagrid-editable').each(function() {
					var field = $(this).parent().attr('field');
					var ed = $.data(this, 'datagrid.editor');
					ed.actions.setValue(ed.target, row[field]);
					//wcj: validate
					var editor = $(ed.target);
					if (editor.hasClass("validatebox-text")) {
						setTimeout(function() {
							editor.validatebox("validate", false);
						}, 0);
					}
				});
		//validateRow(target, rowIndex);
	};
	function stopEdit(target, rowIndex, revert) {
		//wcj: add return value. true : success; false : validate failed
		var opts = $.data(target, 'datagrid').options;
		var updatedRows = $.data(target, 'datagrid').updatedRows;
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		var tr = opts.editConfig.getTr(target, rowIndex);
		//wcj:
		if (! tr) {
			return true;
		}
		var row = opts.editConfig.getRow(target, rowIndex);
		if (!tr.hasClass('datagrid-row-editing')) {
			return true;
		}
		if (!revert) {
			if (!validateRow(target, rowIndex, tr)) {
				return false;
			}
			var changed = false;
			var newValues = {};
			tr.children('td').children('div.datagrid-editable').each(function() {
				var field = $(this).parent().attr('field');
				var ed = $.data(this, 'datagrid.editor');
				//wcj: hide panel
				if (ed.target.hasClass("combo-f")) {
					ed.target.combo("hidePanel");
				}
				var value = ed.actions.getValue(ed.target);
				if (row[field] != value) {
					row[field] = value;
					changed = true;
					newValues[field] = value;
					//wcj:
					$(this).parent().addClass("datagrid-cell-modified");
				}
			});
			if (changed) {
				if (insertedRows.indexOf(row) == -1) {
					if (updatedRows.indexOf(row) == -1) {
						updatedRows.push(row);
					}
				}
			}
		}
		tr.removeClass('datagrid-row-editing');
		destroyEditor(target, rowIndex, tr);
		$(target).datagrid('refreshRow', rowIndex);
		if (!revert) {
			opts.onAfterEdit.call(target, rowIndex, row, newValues);
		} else {
			opts.onCancelEdit.call(target, rowIndex, row);
		}
		return true;
	};
	function createEditor(target, rowIndex, tr) {
		var opts = $.data(target, 'datagrid').options;
		tr.children('td').each(function() {
			//wcj: use children
			var cell = $(this).children('div.datagrid-cell');
			if (cell.size() == 0) {
				return;
			}
			var field = $(this).attr('field');
			//wcj: can not edit group fields
			if (opts.groupFields && opts.groupFields.indexOf(field) >= 0) {
				return;
			}
			var col = getColumnOption(target, field);
			if (col && col.editor) {
				var type, editorOpts;
				if (typeof col.editor == 'string') {
					type = col.editor;
				} else {
					type = col.editor.type;
					editorOpts = col.editor.options;
				}
				var editor = opts.editors[type];
				if (editor) {
//					var html = cell.html();
//					var width = cell.outerWidth();
//					cell.addClass('datagrid-editable');
//					if ($.boxModel == true) {
//						cell.width(width - (cell.outerWidth() - cell.width()));
//					}
//					cell
//							.html('<table border="0" cellspacing="0" cellpadding="1"><tr><td></td></tr></table>');
//					cell.children('table').attr('align', col.align);
//					cell.children('table').bind('click dblclick contextmenu',
//						function(e){
//							e.stopPropagation();
//						});;
//					$.data(cell[0], 'datagrid.editor', {
//								actions : editor,
//								target : editor.init(cell.find('td'), editorOpts),
//								field : field,
//								type : type,
//								oldHtml : html
//							});
					//use cached editor cell
					var html = cell.html();
					var $datagrid = $(target);
					var $td = $(this);
					$td.data("viewerCell", cell);
					cell.detach();
					cell = $datagrid.data("editorCells")[field];
					cell.appendTo($td);
					$.data(cell[0], 'datagrid.editor', {
								actions : editor,
								target : editor.init(cell.find('td'), editorOpts),
								field : field,
								type : type,
								oldHtml : html
							});
				}
			}
		});
		fixRowHeight(target, rowIndex);
	};
	function destroyEditor(target, rowIndex, tr) {
		var opts = $.data(target, 'datagrid').options;
		tr.children('td').each(function() {
					//wcj: use children
					var cell = $(this).children('div.datagrid-editable');
					if (cell.length) {
						var ed = $.data(cell[0], 'datagrid.editor');
						if (ed.actions.destroy) {
							ed.actions.destroy(ed.target);
						}
						$.removeData(cell[0], 'datagrid.editor');
//						var width = cell.outerWidth();
//						cell.removeClass('datagrid-editable');
//						if ($.boxModel == true) {
//							cell.width(width
//									- (cell.outerWidth() - cell.width()));
//						}
						//use cached viewer cell
						var $td = $(this);
						cell.detach();
						$td.data("viewerCell").appendTo($td);
					}
				});
	};
	function fixEditorSize(target, rowIndex, tr) {
		tr.children('td').children('div.datagrid-editable').each(function() {
					var ed = $.data(this, 'datagrid.editor');
					if (ed.actions.resize) {
						ed.actions.resize(ed.target, $(this).width());
					}
				});
	};
	function validateRow(target, rowIndex, tr) {
		if (! tr) {
			tr = $.data(target, 'datagrid').options.editConfig.getTr(target, rowIndex);
		}
		//wcj:
		if (! tr) {
			return true;
		}
		if (!tr.hasClass('datagrid-row-editing')) {
			return true;
		}
		var $datagrid = $(target);
		var fields = $datagrid.datagrid("getColumnFields", true).concat($datagrid.datagrid("getColumnFields", false));
		for (var i = 0; i < fields.length; i++) {
			var editor = $datagrid.datagrid("getColumnEditor", fields[i]);
			if (editor) {
				if (editor.hasClass("validatebox-text")) {
					if (! editor.validatebox("validate")) {
						editor.focus();
						return false;
					}
				}
				if (editor.hasClass("combo-f")) {
					if (! editor.combo("validate")) {
						editor.combo("textbox").focus();
						return false;
					}
				}
			}
		}
		return true;
	};
	function getColumnOption(target, field) {
		return $(target).datagrid("getColumnOption", field);
	};
	function fixRowHeight(target, rowIndex) {
		return $(target).datagrid("fixRowHeight", rowIndex);
	};

	function getColumns(thead) {
		var columns = [];
		$('tr', thead).each(function() {
			var cols = [];
			$('th', this).each(function() {
						var th = $(this);
						var col = {
							title : th.html(),
							align : th.attr('align') || 'left',
							sortable : th.attr('sortable') ? (th.attr('sortable') == 'true') : true,
							checkbox : th.attr('checkbox') == 'true' || false
						};
						if (th.attr('field')) {
							col.field = th.attr('field');
						}
						if (th.attr('formatter')) {
							col.formatter = eval(th.attr('formatter'));
						}
						if (th.attr('styler')) {
							col.styler = eval(th.attr('styler'));
						}
						if (th.attr('editor')) {
							var s = $.trim(th.attr('editor'));
							if (s.substr(0, 1) == '{') {
								col.editor = eval('(' + s + ')');
							} else {
								col.editor = s;
							}
							//wcj: required
							if (th.attr("required")) {
								if (typeof col.editor == "string") {
									col.editor = {
											type : col.editor
									};
								}
								if (col.editor.type == "text") {
									col.editor.type = "validatebox";
								}
								if (! col.editor.options) {
									col.editor.options = {};
								}
								col.editor.options.required = true;
							}
						}
						if (th.attr('rowspan')) {
							col.rowspan = parseInt(th.attr('rowspan'));
						}
						if (th.attr('colspan')) {
							col.colspan = parseInt(th.attr('colspan'));
						}
						if (th.attr('width')) {
							col.width = parseInt(th.attr('width'));
						}
						if (th.attr('hidden')) {
							col.hidden = th.attr('hidden') == 'true';
						}
						if(th.attr('resizable')){
							col.resizable=th.attr('resizable')=='true';
						}
						cols.push(col);
					});
			columns.push(cols);
		});
		return columns;
	};

})(jQuery);
