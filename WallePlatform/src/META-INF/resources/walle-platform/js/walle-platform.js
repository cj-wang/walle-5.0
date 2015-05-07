/*
(function($) {
	
	$.parser._parse = $.parser.parse;
	
	$.parser.parse = function(context) {
		var url = getUrl(context);
		if (url) {
			var $context = $(context);
			var $parent = $context.parent();
			$context.detach();
			$parent.addClass("panel-loading").text($.fn.panel.defaults.loadingMessage);
			
			initWpTags(context, url);
			
			$parent.removeClass("panel-loading").text("").append($context);
		}
		$.parser._parse(context);
	};
	
	
	function getUrl(context) {
		if (context) {
			return $(context).panel("options").href;
		}
		if (requestURI) {
			return requestURI.substring(contextPath.length + 1);
		}
		return null;
	};
	
	function initWpTags(context, url) {
		$(".wp-config[id]", context).each(function() {
			var $wpTag = $(this);
			var tagId = $wpTag.attr("id");
			var tagObject = getTagObject(this);
			WpHtmlTagManager.generateTagContent(url, tagId, {
				async : false,
				callback : function(html) {
					var $configButton = $("<a class='easyui-linkbutton' iconCls='myCustomerIcon_searchForm' title='控件配置' plain='true' style='position:absolute; z-index:100; right:0px;'></a>");
					$configButton.click(function() {
						var $configDialog = openWindow(null, "控件配置", "walle-platform/jsp/pageconfig/tagsConfigDesign.jsp?url=" + url + "&tagId=" + tagId, "ajax", {
							width : 1200,
							height : 600,
							maximizable : true,
							resizable : true,
							onClose : function() {
								if ($(this).data("refresh")) {
									if (context) {
										$(context).panel("refresh");
									} else {
										window.location.reload();
									}
								}
							}
						});
						$configDialog.data("originalTag", tagObject);
					});
					$wpTag.before($configButton);
					if (html) {
						$wpTag.replaceWith(html);
						$configButton.css("border", "red dotted thin");
					}
				}
			});
		});
	};
	
	var idSeq = 0;
	
	function getTagObject(tag) {
		var tagObject = {
				uuid : "_" + idSeq++,
				tagName : tag.tagName.toLowerCase(),
				attrs : [],
				children : []
		};
		$.each(tag.attributes, function(index, attr) {
			tagObject.attrs.push({
				attrName : attr.name,
				attrValue : attr.value
			});
		});
		if ($.browser.mozilla) {
			tagObject.attrs.reverse();
		}
		$(tag).contents().each(function(index, child) {
			if (child.tagName) {
				tagObject.children.push(getTagObject(child));
			} else if (child.nodeName == "#text") {
				var text = $.trim(child.textContent);
				if (text) {
					tagObject.children.push({
						uuid : "_" + idSeq++,
						tagName : "TextNode",
						attrs : [{
							attrName : "textContent",
							attrValue : text
						}]
					});
				}
			}
		});
		return tagObject;
	};
	
})(jQuery);
*/

function getDynamicPageUrl(url, param) {
	url = contextPath + "/common/dynamicPageWrapper.jsp?_dynamicPageUrl=" + url
	if (param) {
		url += "&" + param;
	}
	if (devMode) {
		url += "&_dev=true";
	}
	return url;
}

function openDynamicPageConfig(url) {
	openWindow(null, "动态页面配置", "walle-platform/jsp/pageconfig/dynamicPageDesign.jsp?url=" + url, "ajax", {
		width : 1200,
		height : 600,
		maximizable : true,
		resizable : true,
		maximized : true,
		onClose : function() {
			if ($(this).data("refresh")) {
				if (typeof refreshCurrentTab != "undefined") {
					refreshCurrentTab();
				} else {
					window.location.reload();
				}
			}
		}
	});
};
