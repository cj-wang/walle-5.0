// Time-stamp: <2011-07-08 16:04:01 dolor@mail.com>
// version: http://jquery-easyui.googlecode.com/svn/r514 
/**
 * jQuery EasyUI 1.2.4
 * 
 * Licensed under the GPL terms
 * To use it on other terms please contact us
 *
 * Copyright(c) 2009-2011 stworthy [ stworthy@gmail.com ] 
 * 
 */
/**
 * draggable - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	function drag(e) {
		var opts = $.data(e.data.target, 'draggable').options;
		var dragData = e.data;
		var left = dragData.startLeft + e.pageX - dragData.startX;
		var top = dragData.startTop + e.pageY - dragData.startY;
		if (opts.deltaX != null && opts.deltaX != undefined) {
			left = e.pageX + opts.deltaX;
		}
		if (opts.deltaY != null && opts.deltaY != undefined) {
			top = e.pageY + opts.deltaY;
		}
		if (e.data.parnet != document.body) {
			if ($.boxModel == true) {
				left += $(e.data.parent).scrollLeft();
				top += $(e.data.parent).scrollTop();
			}
		}
		if (opts.axis == 'h') {
			dragData.left = left;
		} else {
			if (opts.axis == 'v') {
				dragData.top = top;
			} else {
				dragData.left = left;
				dragData.top = top;
			}
		}
	};
	function applyDrag(e) {
		var opts = $.data(e.data.target, 'draggable').options;
		var proxy = $.data(e.data.target, 'draggable').proxy;
		if (proxy) {
			proxy.css('cursor', opts.cursor);
		} else {
			proxy = $(e.data.target);
			$.data(e.data.target, 'draggable').handle.css('cursor', opts.cursor);
		}
		proxy.css({
					left : e.data.left,
					top : e.data.top
				});
	};
	//wcj:
	//In chrome, mouse down will fire doMove just following doDown
	//so omit doMove if the event positions are the same
	function doDown(e) {
		//wcj: save the down pos
		$.data(e.data.target, 'draggable').ePos = e.pageX + "," + e.pageY;
		var opts = $.data(e.data.target, 'draggable').options;
		var droppables = $('.droppable').filter(function() {
					return e.data.target != this;
				}).filter(function() {
					var accept = $.data(this, 'droppable').options.accept;
					if (accept) {
						return $(accept).filter(function() {
									return this == e.data.target;
								}).length > 0;
					} else {
						return true;
					}
				});
		$.data(e.data.target, 'draggable').droppables = droppables;
		var proxy = $.data(e.data.target, 'draggable').proxy;
		if (!proxy) {
			if (opts.proxy) {
				if (opts.proxy == 'clone') {
					proxy = $(e.data.target).clone().insertAfter(e.data.target);
				} else {
					proxy = opts.proxy.call(e.data.target, e.data.target);
				}
				$.data(e.data.target, 'draggable').proxy = proxy;
			} else {
				proxy = $(e.data.target);
			}
		}
		proxy.css('position', 'absolute');
		drag(e);
		applyDrag(e);
		opts.onStartDrag.call(e.data.target, e);
		return false;
	};
	function doMove(e) {
		//wcj: omit the move event if the pos is same as down pos
		if ($.data(e.data.target, 'draggable').ePos == e.pageX + "," + e.pageY) {
			return false;
		}
		drag(e);
		if ($.data(e.data.target, 'draggable').options.onDrag.call(
				e.data.target, e) != false) {
			applyDrag(e);
		}
		var source = e.data.target;
		$.data(e.data.target, 'draggable').droppables.each(function() {
			var dropObj = $(this);
			var p2 = $(this).offset();
			if (e.pageX > p2.left && e.pageX < p2.left + dropObj.outerWidth()
					&& e.pageY > p2.top && e.pageY < p2.top + dropObj.outerHeight()) {
				if (!this.entered) {
					$(this).trigger('_dragenter', [source]);
					this.entered = true;
				}
				$(this).trigger('_dragover', [source]);
			} else {
				if (this.entered) {
					$(this).trigger('_dragleave', [source]);
					this.entered = false;
				}
			}
		});
		return false;
	};
	function doUp(e) {
		drag(e);
		var proxy = $.data(e.data.target, 'draggable').proxy;
		var opts = $.data(e.data.target, 'draggable').options;
		if (opts.revert) {
			if (checkDrop() == true) {
				removeProxy();
				$(e.data.target).css({
							position : e.data.startPosition,
							left : e.data.startLeft,
							top : e.data.startTop
						});
			} else {
				if (proxy) {
					proxy.animate({
								left : e.data.startLeft,
								top : e.data.startTop
							}, function() {
								removeProxy();
							});
				} else {
					$(e.data.target).animate({
								left : e.data.startLeft,
								top : e.data.startTop
							}, function() {
								$(e.data.target).css('position',
										e.data.startPosition);
							});
				}
			}
		} else {
			$(e.data.target).css({
						position : 'absolute',
						left : e.data.left,
						top : e.data.top
					});
			removeProxy();
			checkDrop();
		}
		opts.onStopDrag.call(e.data.target, e);
		function removeProxy() {
			if (proxy) {
				proxy.remove();
			}
			$.data(e.data.target, 'draggable').proxy = null;
		};
		function checkDrop() {
			var dropped = false;
			$.data(e.data.target, 'draggable').droppables.each(function() {
						var dropObj = $(this);
						var p2 = $(this).offset();
						if (e.pageX > p2.left
								&& e.pageX < p2.left + dropObj.outerWidth()
								&& e.pageY > p2.top
								&& e.pageY < p2.top + dropObj.outerHeight()) {
							if (opts.revert) {
								$(e.data.target).css({
											position : e.data.startPosition,
											left : e.data.startLeft,
											top : e.data.startTop
										});
							}
							$(this).trigger('_drop', [e.data.target]);
							dropped = true;
							this.entered = false;
						}
					});
			return dropped;
		};
		$(document).unbind('.draggable');
		return false;
	};
	$.fn.draggable = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.draggable.methods[options](this, param);
		}
		return this.each(function() {
					var opts;
					var state = $.data(this, 'draggable');
					if (state) {
						state.handle.unbind('.draggable');
						opts = $.extend(state.options, options);
					} else {
						opts = $.extend({}, $.fn.draggable.defaults, options || {});
					}
					if (opts.disabled == true) {
						$(this).css('cursor', 'default');
						return;
					}
					var handle = null;
					if (typeof opts.handle == 'undefined' || opts.handle == null) {
						handle = $(this);
					} else {
						handle = (typeof opts.handle == 'string' ? $(opts.handle,
								this) : handle);
					}
					$.data(this, 'draggable', {
								options : opts,
								handle : handle
							});
					handle.bind('mousedown.draggable', {
								target : this
							}, onMouseDown);
					handle.bind('mousemove.draggable', {
								target : this
							}, onMouseMove);
					function onMouseDown(e) {
						if (checkArea(e) == false) {
							return;
						}
						var position = $(e.data.target).position();
						var data = {
							startPosition : $(e.data.target).css('position'),
							startLeft : position.left,
							startTop : position.top,
							left : position.left,
							top : position.top,
							startX : e.pageX,
							startY : e.pageY,
							target : e.data.target,
							parent : $(e.data.target).parent()[0]
						};
						if(opts.onBeforeDrag.call(e.data.target,e)==false){
							return;
						}
						$(document).bind('mousedown.draggable', data, doDown);
						$(document).bind('mousemove.draggable', data, doMove);
						$(document).bind('mouseup.draggable', data, doUp);
					};
					function onMouseMove(e) {
						if (checkArea(e)) {
							$(this).css('cursor', opts.cursor);
						} else {
							$(this).css('cursor', 'default');
						}
					};
					function checkArea(e) {
						var offset = $(handle).offset();
						var width = $(handle).outerWidth();
						var height = $(handle).outerHeight();
						var t = e.pageY - offset.top;
						var r = offset.left + width - e.pageX;
						var b = offset.top + height - e.pageY;
						var l = e.pageX - offset.left;
						return Math.min(t, r, b, l) > opts.edge;
					};
				});
	};
	$.fn.draggable.methods = {
		options : function(jq) {
			return $.data(jq[0], 'draggable').options;
		},
		proxy : function(jq) {
			return $.data(jq[0], 'draggable').proxy;
		},
		enable : function(jq) {
			return jq.each(function() {
						$(this).draggable({
									disabled : false
								});
					});
		},
		disable : function(jq) {
			return jq.each(function() {
						$(this).draggable({
									disabled : true
								});
					});
		}
	};
	$.fn.draggable.defaults = {
		proxy : null,
		revert : false,
		cursor : 'move',
		deltaX : null,
		deltaY : null,
		handle : null,
		disabled : false,
		edge : 0,
		axis : null,
		onBeforeDrag:function(e){
		},
		onStartDrag : function(e) {
		},
		onDrag : function(e) {
		},
		onStopDrag : function(e) {
		}
	};
})(jQuery);

/**
 * droppable - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	function init(target) {
		$(target).addClass('droppable');
		$(target).bind('_dragenter', function(e, source) {
					$.data(target, 'droppable').options.onDragEnter.apply(target, [e,source]);
				});
		$(target).bind('_dragleave', function(e, source) {
					$.data(target, 'droppable').options.onDragLeave.apply(target, [e,source]);
				});
		$(target).bind('_dragover', function(e, source) {
					$.data(target, 'droppable').options.onDragOver.apply(target,[e, source]);
				});
		$(target).bind('_drop', function(e, source) {
					$.data(target, 'droppable').options.onDrop.apply(target, [e, source]);
				});
	};
	$.fn.droppable = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.droppable.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'droppable');
					if (state) {
						$.extend(state.options, options);
					} else {
						init(this);
						$.data(this, 'droppable', {
									options : $.extend({},
											$.fn.droppable.defaults, options)
								});
					}
				});
	};
	$.fn.droppable.methods = {};
	$.fn.droppable.defaults = {
		accept : null,
		onDragEnter : function(e, source) {
		},
		onDragOver : function(e, source) {
		},
		onDragLeave : function(e, source) {
		},
		onDrop : function(e, source) {
		}
	};
})(jQuery);

/**
 * resizable - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	$.fn.resizable = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.resizable.methods[options](this, param);
		}
		function resize(e) {
			var resizeData = e.data;
			var options = $.data(resizeData.target, 'resizable').options;
			if (resizeData.dir.indexOf('e') != -1) {
				var width = resizeData.startWidth + e.pageX - resizeData.startX;
				width = Math.min(Math.max(width, options.minWidth), options.maxWidth);
				resizeData.width = width;
			}
			if (resizeData.dir.indexOf('s') != -1) {
				var height = resizeData.startHeight + e.pageY - resizeData.startY;
				height = Math.min(Math.max(height, options.minHeight), options.maxHeight);
				resizeData.height = height;
			}
			if (resizeData.dir.indexOf('w') != -1) {
				resizeData.width = resizeData.startWidth - e.pageX + resizeData.startX;
				if (resizeData.width >= options.minWidth && resizeData.width <= options.maxWidth) {
					resizeData.left = resizeData.startLeft + e.pageX - resizeData.startX;
				}
			}
			if (resizeData.dir.indexOf('n') != -1) {
				resizeData.height = resizeData.startHeight - e.pageY + resizeData.startY;
				if (resizeData.height >= options.minHeight && resizeData.height <= options.maxHeight) {
					resizeData.top = resizeData.startTop + e.pageY - resizeData.startY;
				}
			}
		};
		function applySize(e) {
			var resizeData = e.data;
			var target = resizeData.target;
			if ($.boxModel == true) {
				$(target).css({
							width : resizeData.width - resizeData.deltaWidth,
							height : resizeData.height - resizeData.deltaHeight,
							left : resizeData.left,
							top : resizeData.top
						});
			} else {
				$(target).css({
							width : resizeData.width,
							height : resizeData.height,
							left : resizeData.left,
							top : resizeData.top
						});
			}
		};
		function doDown(e) {
			$.data(e.data.target, 'resizable').options.onStartResize.call(
					e.data.target, e);
			return false;
		};
		function doMove(e) {
			resize(e);
			if ($.data(e.data.target, 'resizable').options.onResize.call(
					e.data.target, e) != false) {
				applySize(e);
			}
			return false;
		};
		function doUp(e) {
			resize(e, true);
			applySize(e);
			$.data(e.data.target, 'resizable').options.onStopResize.call(
					e.data.target, e);
			$(document).unbind('.resizable');
			$('body').css('cursor', 'default');
			return false;
		};
		return this.each(function() {
					var opts = null;
					var state = $.data(this, 'resizable');
					if (state) {
						$(this).unbind('.resizable');
						opts = $.extend(state.options, options || {});
					} else {
						opts = $.extend({}, $.fn.resizable.defaults, options || {});
						$.data(this, 'resizable', {
									options : opts
								});
					}
					if (opts.disabled == true) {
						return;
					}
					var target = this;
					$(this).bind('mousemove.resizable', onMouseMove).bind(
							'mousedown.resizable', onMouseDown).bind(
							'mouseleave.resizable',onMouseLeave);
					function onMouseMove(e) {
						var dir = getDirection(e);
						if (dir == '') {
							$(target).css('cursor', 'default');
						} else {
							$(target).css('cursor', dir + '-resize');
						}
					};
					function onMouseDown(e) {
						var dir = getDirection(e);
						if (dir == '') {
							return;
						}
						var data = {
							target : this,
							dir : dir,
							startLeft : getCssValue('left'),
							startTop : getCssValue('top'),
							left : getCssValue('left'),
							top : getCssValue('top'),
							startX : e.pageX,
							startY : e.pageY,
							startWidth : $(target).outerWidth(),
							startHeight : $(target).outerHeight(),
							width : $(target).outerWidth(),
							height : $(target).outerHeight(),
							deltaWidth : $(target).outerWidth() - $(target).width(),
							deltaHeight : $(target).outerHeight()
									- $(target).height()
						};
						$(document).bind('mousedown.resizable', data, doDown);
						$(document).bind('mousemove.resizable', data, doMove);
						$(document).bind('mouseup.resizable', data, doUp);
						$('body').css('cursor',dir+'-resize');
					};
					function onMouseLeave(e){
						$(target).css('cursor','default');
					}
					function getDirection(e) {
						var dir = '';
						var offset = $(target).offset();
						var width = $(target).outerWidth();
						var height = $(target).outerHeight();
						var edge = opts.edge;
						if (e.pageY > offset.top && e.pageY < offset.top + edge) {
							dir += 'n';
						} else {
							if (e.pageY < offset.top + height
									&& e.pageY > offset.top + height - edge) {
								dir += 's';
							}
						}
						if (e.pageX > offset.left && e.pageX < offset.left + edge) {
							dir += 'w';
						} else {
							if (e.pageX < offset.left + width
									&& e.pageX > offset.left + width - edge) {
								dir += 'e';
							}
						}
						var handles = opts.handles.split(',');
						for (var i = 0; i < handles.length; i++) {
							var handle = handles[i].replace(/(^\s*)|(\s*$)/g, '');
							if (handle == 'all' || handle == dir) {
								return dir;
							}
						}
						return '';
					};
					function getCssValue(css) {
						var val = parseInt($(target).css(css));
						if (isNaN(val)) {
							return 0;
						} else {
							return val;
						}
					};
				});
	};
	$.fn.resizable.methods = {};
	$.fn.resizable.defaults = {
		disabled : false,
		handles : 'n, e, s, w, ne, se, sw, nw, all',
		minWidth : 10,
		minHeight : 10,
		maxWidth : 10000,
		maxHeight : 10000,
		edge : 5,
		onStartResize : function(e) {
		},
		onResize : function(e) {
		},
		onStopResize : function(e) {
		}
	};
})(jQuery);

/**
 * linkbutton - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	function createButton(target) {
		var opts = $.data(target, 'linkbutton').options;
		$(target).empty();
		$(target).addClass('l-btn');
		if (opts.id) {
			$(target).attr('id', opts.id);
		} else {
			$.fn.removeProp ? $(target).removeProp('id') : $(target)
					.removeAttr('id');
		}
		if (opts.plain) {
			$(target).addClass('l-btn-plain');
		} else {
			$(target).removeClass('l-btn-plain');
		}
		if (opts.text) {
			$(target).html(opts.text).wrapInner('<span class="l-btn-left">'
					+ '<span class="l-btn-text">' + '</span>' + '</span>');
			if (opts.iconCls) {
				$(target).find('.l-btn-text').addClass(opts.iconCls).css(
						'padding-left', '20px');
			}
		} else {
			$(target).html('&nbsp;').wrapInner('<span class="l-btn-left">'
					+ '<span class="l-btn-text">'
					+ '<span class="l-btn-empty"></span>' + '</span>'
					+ '</span>');
			if (opts.iconCls) {
				$(target).find('.l-btn-empty').addClass(opts.iconCls);
			}
		}
		//wcj: key support
		var key = $(target).attr("key");
		if (key) {
			key = key.toUpperCase();
			$(target).children(".l-btn-left").children(".l-btn-text").html(opts.text + 
					" <b>(<u>" + key + "</u>)</b>");
			$(target).attr("key", key);
		}
		setDisabled(target, opts.disabled);
	};
	function setDisabled(target, disabled) {
		var state = $.data(target, 'linkbutton');
		if (disabled) {
			state.options.disabled = true;
			var href = $(target).attr('href');
			if (href) {
				state.href = href;
				$(target).attr('href', 'javascript:void(0)');
			}
			if(target.onclick){
				state.onclick = target.onclick;
				target.onclick = null;
			}
			//wcj: disable events bind by jquery click()
			//use setTimeout to let event bindings run first
			setTimeout(function() {
				setTimeout(function() {
					if ($(target).data("events") && $(target).data("events").click) {
						$(target).data("clickEvents", $(target).data("events").click);
						$(target).data("events").click = null;
					}
				}, 0);
			}, 0);
			$(target).addClass('l-btn-disabled');
		} else {
			state.options.disabled = false;
			if (state.href) {
				$(target).attr('href', state.href);
			}
			if (state.onclick) {
				target.onclick = state.onclick;
			}
			//wcj: recover events bind by jquery click()
			if ($(target).data("clickEvents")) {
				$(target).data("events").click = $(target).data("clickEvents");
			}
			$(target).removeClass('l-btn-disabled');
		}
	};
	$.fn.linkbutton = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.linkbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'linkbutton');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'linkbutton', {
									options : $.extend({},
											$.fn.linkbutton.defaults,
											$.fn.linkbutton.parseOptions(this),
											options)
								});
						$(this).removeAttr('disabled');
					}
					createButton(this);
				});
	};
	$.fn.linkbutton.methods = {
		options : function(jq) {
			return $.data(jq[0], 'linkbutton').options;
		},
		enable : function(jq) {
			return jq.each(function() {
						setDisabled(this, false);
					});
		},
		disable : function(jq) {
			return jq.each(function() {
						setDisabled(this, true);
					});
		}
	};
	$.fn.linkbutton.parseOptions = function(target) {
		var t = $(target);
		return {
			id : t.attr('id'),
			disabled : (t.attr('disabled') ? true : undefined),
			plain : (t.attr('plain') ? t.attr('plain') == 'true' : undefined),
			text : $.trim(t.html()),
			iconCls : (t.attr('icon') || t.attr('iconCls'))
		};
	};
	$.fn.linkbutton.defaults = {
		id : null,
		disabled : false,
		plain : false,
		text : '',
		iconCls : null
	};
})(jQuery);

/**
 * pagination - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	linkbutton
 * 
 */
(function($) {
	function buildToolbar(target) {
		var opts = $.data(target, 'pagination').options;
		var pager = $(target).addClass('pagination').empty();
		var t = $('<table cellspacing="0" cellpadding="0" border="0"><tr></tr></table>')
				.appendTo(pager);
		var tr = $('tr', t);
		if (opts.showPageList) {
			var ps = $('<select class="pagination-page-list"></select>');
			for (var i = 0; i < opts.pageList.length; i++) {
				var pageItem=$('<option></option>').text(opts.pageList[i]).appendTo(ps);
				if(opts.pageList[i]==opts.pageSize){
					pageItem.attr('selected','selected');
				}
			}
			$('<td></td>').append(ps).appendTo(tr);
			opts.pageSize = parseInt(ps.val());
			$('<td><div class="pagination-btn-separator"></div></td>')
					.appendTo(tr);
		}
		//wcj:
		$('<ul class="pagination"><li><a class="p-first" href="#">&laquo;</a></li><li><a class="p-prev" href="#">&lsaquo;</a></li>' +
				'<li><a class="p-next" href="#">&rsaquo;</a></li><li><a class="p-last" href="#">&raquo;</a></li></ul>').appendTo(tr);
		/*
		$('<td><a href="javascript:void(0)" icon="pagination-first"></a></td>')
				.appendTo(tr);
		$('<td><a href="javascript:void(0)" icon="pagination-prev"></a></td>')
				.appendTo(tr);
		$('<td><div class="pagination-btn-separator"></div></td>')
				.appendTo(tr);
		$('<span style="padding-left:6px;"></span>').html(opts.beforePageText)
				.wrap('<td></td>').parent().appendTo(tr);
		$('<td><input class="pagination-num" type="text" value="1" size="2"></td>')
				.appendTo(tr);
		$('<span style="padding-right:6px;"></span>').wrap('<td></td>')
				.parent().appendTo(tr);
		$('<td><div class="pagination-btn-separator"></div></td>')
				.appendTo(tr);
		$('<td><a href="javascript:void(0)" icon="pagination-next"></a></td>')
				.appendTo(tr);
		$('<td><a href="javascript:void(0)" icon="pagination-last"></a></td>')
				.appendTo(tr);
		if (opts.showRefresh) {
			$('<td><div class="pagination-btn-separator"></div></td>')
					.appendTo(tr);
			$('<td><a href="javascript:void(0)" icon="pagination-load"></a></td>')
					.appendTo(tr);
		}
		*/
		if (opts.buttons) {
			$('<td><div class="pagination-btn-separator"></div></td>')
					.appendTo(tr);
			for (var i = 0; i < opts.buttons.length; i++) {
				var btn = opts.buttons[i];
				if (btn == '-') {
					$('<td><div class="pagination-btn-separator"></div></td>')
							.appendTo(tr);
				} else {
					var td = $('<td></td>').appendTo(tr);
					$('<a href="javascript:void(0)"></a>').addClass('l-btn')
							.css('float', 'left').text(btn.text || '').attr(
									'icon', btn.iconCls || '').bind('click',
									eval(btn.handler || function() {
									})).appendTo(td).linkbutton({
										plain : true
									});
				}
			}
		}
		$('<div class="pagination-info"></div>').appendTo(pager);
		$('<div style="clear:both;"></div>').appendTo(pager);
		$('a[icon^=pagination]', pager).linkbutton({
					plain : true
				});
		//wcj: add p-xxx a's
		pager.find('a[icon=pagination-first], a.p-first').unbind('.pagination').bind(
				'click.pagination', function() {
					if (opts.pageNumber > 1) {
						selectPage(target, 1);
					}
				});
		pager.find('a[icon=pagination-prev], a.p-prev').unbind('.pagination').bind(
				'click.pagination', function() {
					if (opts.pageNumber > 1) {
						selectPage(target, opts.pageNumber - 1);
					}
				});
		pager.find('a[icon=pagination-next], a.p-next').unbind('.pagination').bind(
				'click.pagination', function() {
					var pageCount = Math.ceil(opts.total / opts.pageSize);
					if (opts.pageNumber < pageCount) {
						selectPage(target, opts.pageNumber + 1);
					}
				});
		pager.find('a[icon=pagination-last], a.p-last').unbind('.pagination').bind(
				'click.pagination', function() {
					var pageCount = Math.ceil(opts.total / opts.pageSize);
					if (opts.pageNumber < pageCount) {
						selectPage(target, pageCount);
					}
				});
		pager.find('a[icon=pagination-load]').unbind('.pagination').bind(
				'click.pagination', function() {
					if (opts.onBeforeRefresh.call(target, opts.pageNumber, opts.pageSize) != false) {
						selectPage(target, opts.pageNumber);
						opts.onRefresh.call(target, opts.pageNumber, opts.pageSize);
					}
				});
		pager.find('input.pagination-num').unbind('.pagination').bind(
				'keydown.pagination', function(e) {
					if (e.keyCode == 13) {
						var pageNumber = parseInt($(this).val()) || 1;
						selectPage(target, pageNumber);
					}
				});
		pager.find('.pagination-page-list').unbind('.pagination').bind(
				'change.pagination', function() {
					opts.pageSize = $(this).val();
					opts.onChangePageSize.call(target, opts.pageSize);
					var pageCount = Math.ceil(opts.total / opts.pageSize);
					selectPage(target, opts.pageNumber);
				});
		//wcj:
		pager.undelegate('.p-page', 'click').delegate('.p-page', 'click', function() {
			var pageNumber = parseInt($(this).text());
			if (pageNumber && (opts.pageNumber != pageNumber)) {
				selectPage(target, pageNumber);
			}
		});
	};
	function selectPage(target, page) {
		var opts = $.data(target, 'pagination').options;
		var pageCount = Math.ceil(opts.total / opts.pageSize) || 1;
		var pageNumber = page;
		if (page < 1) {
			pageNumber = 1;
		}
		if (page > pageCount) {
			pageNumber = pageCount;
		}
		opts.pageNumber = pageNumber;
		opts.onSelectPage.call(target, pageNumber, opts.pageSize);
		showInfo(target);
	};
	function showInfo(target) {
		var opts = $.data(target, 'pagination').options;
		var pageCount = Math.ceil(opts.total / opts.pageSize) || 1;
		var num = $(target).find('input.pagination-num');
		num.val(opts.pageNumber);
		num.parent().next().find('span').html(opts.afterPageText.replace(
				/{pages}/, pageCount));
		var pinfo = opts.displayMsg;
		pinfo = pinfo.replace(/{from}/, opts.pageSize * (opts.pageNumber - 1) + 1);
		pinfo = pinfo.replace(/{to}/, Math.min(opts.pageSize * (opts.pageNumber),
						opts.total));
		pinfo = pinfo.replace(/{total}/, opts.total);
		$(target).find('.pagination-info').html(pinfo);
		//wcj:
		var current = opts.pageNumber
		var min = Math.max(1, current - 4);
		var max = Math.min(pageCount, current + 4);
		while (max - min >= 5) {
			if (max - current > current - min) {
				max--;
			} else {
				min++;
			}
		}
		if (current < 4) {
			max += 2;
		}
		if (current == 4) {
			max += 1;
		}
		if (current > pageCount - 3) {
			min -= 2;
		}
		if (current == pageCount - 3) {
			min -= 1;
		}
		min = Math.max(min, 1);
		max = Math.min(max, pageCount);
		$('ul.pagination .p-page', target).parent().remove();
		var s = "";
		if (min > 1) {
			s += '<li><a class="p-page" href="#">1</a></li>';
		}
		if (min == 3) {
			s += '<li><a class="p-page" href="#">2</a></li>';
		}
		if (min > 3) {
			s += '<li class="disabled"><span class="p-page">...</span></li>';
		}
		for (var i = min; i <= max; i++) {
			if (i == current) {
				s += '<li class="active"><a class="p-page" href="#">' + i + '</a></li>';
			} else {
				s += '<li><a class="p-page" href="#">' + i + '</a></li>';
			}
		}
		if (max < pageCount - 2) {
			s += '<li class="disabled"><span class="p-page">...</span></li>';
		}
		if (max == pageCount - 2) {
			s += '<li><a class="p-page" href="#">' + (pageCount - 1) + '</a></li>';
		}
		if (max < pageCount) {
			s += '<li><a class="p-page" href="#">' + pageCount + '</a></li>';
		}
		$('ul.pagination>li:eq(1)', target).after(s);
		if (current == 1) {
			$('a.p-first, a.p-prev', target).parent().addClass('disabled');
		} else {
			$('a.p-first, a.p-prev', target).parent().removeClass('disabled');
		}
		if (current == pageCount) {
			$('a.p-next, a.p-last', target).parent().addClass('disabled');
		} else {
			$('a.p-next, a.p-last', target).parent().removeClass('disabled');
		}
		/*
		$('a[icon=pagination-first],a[icon=pagination-prev]', target).linkbutton({
					disabled : (opts.pageNumber == 1)
				});
		$('a[icon=pagination-next],a[icon=pagination-last]', target).linkbutton({
					disabled : (opts.pageNumber == pageCount)
				});
		if (opts.loading) {
			$(target).find('a[icon=pagination-load]').find('.pagination-load')
					.addClass('pagination-loading');
		} else {
			$(target).find('a[icon=pagination-load]').find('.pagination-load')
					.removeClass('pagination-loading');
		}
		*/
	};
	function setLoadStatus(target, loading) {
		var opts = $.data(target, 'pagination').options;
		opts.loading = loading;
		if (opts.loading) {
			$(target).find('a[icon=pagination-load]').find('.pagination-load')
					.addClass('pagination-loading');
		} else {
			$(target).find('a[icon=pagination-load]').find('.pagination-load')
					.removeClass('pagination-loading');
		}
	};
	$.fn.pagination = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.pagination.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var opts;
					var state = $.data(this, 'pagination');
					if (state) {
						opts = $.extend(state.options, options);
					} else {
						opts = $.extend({}, $.fn.pagination.defaults, options);
						$.data(this, 'pagination', {
									options : opts
								});
					}
					buildToolbar(this);
					showInfo(this);
				});
	};
	$.fn.pagination.methods = {
		options : function(jq) {
			return $.data(jq[0], 'pagination').options;
		},
		loading : function(jq) {
			return jq.each(function() {
						setLoadStatus(this, true);
					});
		},
		loaded : function(jq) {
			return jq.each(function() {
						setLoadStatus(this, false);
					});
		}
	};
	$.fn.pagination.defaults = {
		total : 1,
		pageSize : 10,
		pageNumber : 1,
		pageList : [10, 20, 30, 50],
		loading : false,
		buttons : null,
		showPageList : true,
		showRefresh : true,
		onSelectPage : function(pageNumber, pageSize) {
		},
		onBeforeRefresh : function(pageNumber, pageSize) {
		},
		onRefresh : function(pageNumber, pageSize) {
		},
		onChangePageSize : function(pageSize) {
		},
		beforePageText : 'Page',
		afterPageText : 'of {pages}',
		displayMsg : 'Displaying {from} to {to} of {total} items'
	};
})(jQuery);

/**
 * tree - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Node is a javascript object which contains following properties:
 * 1 id: An identity value bind to the node.
 * 2 text: Text to be showed.
 * 3 checked: Indicate whether the node is checked selected.
 * 3 attributes: Custom attributes bind to the node.
 * 4 target: Target DOM object.
 */
(function($) {
	function wrapTree(target) {
		var tree = $(target);
		tree.addClass('tree');
		return tree;
	};
	function getTreeData(target) {
		var list = [];
		collectTreeData(list, $(target));
		function collectTreeData(aa, target) {
			target.children('li').each(function() {
						var node = $(this);
						var nodeData = {};
						nodeData.text = node.children('span').html();
						if (!nodeData.text) {
							nodeData.text = node.html();
						}
						nodeData.id = node.attr('id');
						nodeData.iconCls = node.attr('iconCls') || node.attr('icon');
						nodeData.checked = node.attr('checked') == 'true';
						nodeData.state = node.attr('state') || 'open';
						var children = node.children('ul');
						if (children.length) {
							nodeData.children = [];
							collectTreeData(nodeData.children, children);
						}
						aa.push(nodeData);
					});
		};
		return list;
	};
	function bindTreeEvents(target) {
		var opts = $.data(target, 'tree').options;
		var tree = $.data(target, 'tree').tree;
		$('div.tree-node', tree).unbind('.tree').bind('dblclick.tree',
				function() {
					//wcj: prevent the text selecting
					if (! $(target).parent().is(".combo-panel")) {
						$("<input>").appendTo("body").focus().remove();
					}
			
					select(target, this);
					opts.onDblClick.call(target, getSelected(target));
				}).bind('click.tree', function() {
					select(target, this);
					opts.onClick.call(target, getSelected(target));
				})
				//wcj: use css hover
				/*.bind('mouseenter.tree', function() {
					$(this).addClass('tree-node-hover');
					return false;
				}).bind('mouseleave.tree', function() {
					$(this).removeClass('tree-node-hover');
					return false;
				})*/.bind('contextmenu.tree', function(e) {
					opts.onContextMenu.call(target, e, getNode(target, this));
				});
		$('span.tree-hit', tree).unbind('.tree').bind('click.tree', function() {
					var node = $(this).parent();
					toggle(target, node[0]);
					return false;
				})
				//wcj: use css hover
				/*.bind('mouseenter.tree', function() {
					if ($(this).hasClass('tree-expanded')) {
						$(this).addClass('tree-expanded-hover');
					} else {
						$(this).addClass('tree-collapsed-hover');
					}
				}).bind('mouseleave.tree', function() {
					if ($(this).hasClass('tree-expanded')) {
						$(this).removeClass('tree-expanded-hover');
					} else {
						$(this).removeClass('tree-collapsed-hover');
					}
				})*/.bind('mousedown.tree', function() {
					return false;
				});
		$('span.tree-checkbox', tree).unbind('.tree').bind('click.tree',
				function() {
					var node = $(this).parent();
					setChecked(target, node[0], !$(this).hasClass('tree-checkbox1'));
					return false;
				}).bind('mousedown.tree', function() {
					return false;
				});
	};
	function disableDnd(target) {
		var node = $(target).find('div.tree-node');
		node.draggable('disable');
		node.css('cursor', 'pointer');
	};
	function enableDnd(target) {
		var opts = $.data(target, 'tree').options;
		var tree = $.data(target, 'tree').tree;
		tree.find('div.tree-node').draggable({
			disabled : false,
			revert : true,
			cursor : 'pointer',
			proxy : function(target) {
				var p = $('<div class="tree-node-proxy tree-dnd-no"></div>')
						.appendTo('body');
				p.html($(target).find('.tree-title').html());
				p.hide();
				return p;
			},
			deltaX : 15,
			deltaY : 15,
			onBeforeDrag:function(){
				$(this).next('ul').find('div.tree-node').droppable({accept:'no-accept'});
			},
			onStartDrag : function() {
				$(this).draggable('proxy').css({
							left : -10000,
							top : -10000
						});
			},
			onDrag : function(e) {
				$(this).draggable('proxy').show();
				this.pageY = e.pageY;
			},
			onStopDrag:function(){
				$(this).next('ul').find('div.tree-node').droppable({accept:'div.tree-node'});
			}
		}).droppable({
			accept : 'div.tree-node',
			onDragOver : function(e, source) {
				var pageY = source.pageY;
				var top = $(this).offset().top;
				var height = top + $(this).outerHeight();
				$(source).draggable('proxy').removeClass('tree-dnd-no')
						.addClass('tree-dnd-yes');
				$(this)
						.removeClass('tree-node-append tree-node-top tree-node-bottom');
				if (pageY > top + (height - top) / 2) {
					if (height - pageY < 5) {
						$(this).addClass('tree-node-bottom');
					} else {
						$(this).addClass('tree-node-append');
					}
				} else {
					if (pageY - top < 5) {
						$(this).addClass('tree-node-top');
					} else {
						$(this).addClass('tree-node-append');
					}
				}
			},
			onDragLeave : function(e, source) {
				$(source).draggable('proxy').removeClass('tree-dnd-yes')
						.addClass('tree-dnd-no');
				$(this)
						.removeClass('tree-node-append tree-node-top tree-node-bottom');
			},
			onDrop : function(e, source) {
				var target = this;
				//wcj: fix bug when dnd between two trees
				if ($(source).closest(".tree")[0] != $(target).closest(".tree")[0]) {
					$(target).removeClass('tree-node-append tree-node-top tree-node-bottom');
					return;
				}
				var action, point;
				if ($(this).hasClass('tree-node-append')) {
					action = moveNode;
				} else {
					action = insertNode;
					point = $(this).hasClass('tree-node-top') ? 'top' : 'bottom';
				}
				setTimeout(function() {
							action(source, target, point);
						}, 0);
				$(this)
						.removeClass('tree-node-append tree-node-top tree-node-bottom');
			}
		});
		function moveNode(nodeEl, parent) {
			if (getNode(target, parent).state == 'closed') {
				expand(target, parent, function() {
							doMoveNode();
						});
			} else {
				doMoveNode();
			}
			function doMoveNode() {
				var nodeData = $(target).tree('pop', nodeEl);
				$(target).tree('append', {
							parent : parent,
							data : [nodeData]
						});
				opts.onDrop.call(target, parent, nodeData, 'append');
			};
		};
		function insertNode(nodeEl, parent, point) {
			var param = {};
			if (point == 'top') {
				param.before = parent;
			} else {
				param.after = parent;
			}
			var nodeData = $(target).tree('pop', nodeEl);
			param.data = nodeData;
			$(target).tree('insert', param);
			opts.onDrop.call(target, parent, nodeData, point);
		};
	};
	function setChecked(target, nodeEl, checked) {
		var opts = $.data(target, 'tree').options;
		if (!opts.checkbox) {
			return;
		}
		var node = $(nodeEl);
		//wcj: add event onBeforeCheck
		//wcj: if onBeforeCheck returns false, prevent the check event
		var nodeParam = getNode(target, nodeEl);
		if (opts.onBeforeCheck.call(target, nodeParam, checked) == false) {
			return;
		}
		
		var ck = node.find('.tree-checkbox');
		ck.removeClass('tree-checkbox0 tree-checkbox1 tree-checkbox2');
		if (checked) {
			ck.addClass('tree-checkbox1');
		} else {
			ck.addClass('tree-checkbox0');
		}
		if (opts.cascadeCheck) {
			setParentCheckbox(node);
			setChildCheckbox(node);
		}
		//wcj:
		//var nodeParam = getNode(target, nodeEl);
		opts.onCheck.call(target, nodeParam, checked);
		function setChildCheckbox(nodeEl) {
			var childck = nodeEl.next().find('.tree-checkbox');
			childck.removeClass('tree-checkbox0 tree-checkbox1 tree-checkbox2');
			if (nodeEl.find('.tree-checkbox').hasClass('tree-checkbox1')) {
				childck.addClass('tree-checkbox1');
			} else {
				childck.addClass('tree-checkbox0');
			}
		};
		function setParentCheckbox(nodeEl) {
			var pNode = getParent(target, nodeEl[0]);
			if (pNode) {
				var ck = $(pNode.target).find('.tree-checkbox');
				ck.removeClass('tree-checkbox0 tree-checkbox1 tree-checkbox2');
				if (isAllSelected(nodeEl)) {
					ck.addClass('tree-checkbox1');
				} else {
					if (isAllNull(nodeEl)) {
						ck.addClass('tree-checkbox0');
					} else {
						ck.addClass('tree-checkbox2');
					}
				}
				setParentCheckbox($(pNode.target));
			}
			function isAllSelected(n) {
				var ck = n.find('.tree-checkbox');
				if (ck.hasClass('tree-checkbox0')
						|| ck.hasClass('tree-checkbox2')) {
					return false;
				}
				var b = true;
				n.parent().siblings().each(function() {
					if (!$(this).children('div.tree-node')
							.children('.tree-checkbox')
							.hasClass('tree-checkbox1')) {
						b = false;
					}
				});
				return b;
			};
			function isAllNull(n) {
				var ck = n.find('.tree-checkbox');
				if (ck.hasClass('tree-checkbox1')
						|| ck.hasClass('tree-checkbox2')) {
					return false;
				}
				var b = true;
				n.parent().siblings().each(function() {
					if (!$(this).children('div.tree-node')
							.children('.tree-checkbox')
							.hasClass('tree-checkbox0')) {
						b = false;
					}
				});
				return b;
			};
		};
	};
	function setCheckBoxValue(target, nodeEl) {
		var opts = $.data(target, 'tree').options;
		var node = $(nodeEl);
		if (isLeaf(target, nodeEl)) {
			var ck = node.find('.tree-checkbox');
			if (ck.length) {
				if (ck.hasClass('tree-checkbox1')) {
					setChecked(target, nodeEl, true);
				} else {
					setChecked(target, nodeEl, false);
				}
			} else {
				if (opts.onlyLeafCheck) {
					$('<span class="tree-checkbox tree-checkbox0"></span>')
							.insertBefore(node.find('.tree-title'));
					bindTreeEvents(target);
				}
			}
		} else {
			var ck = node.find('.tree-checkbox');
			if (opts.onlyLeafCheck) {
				ck.remove();
			} else {
				if (ck.hasClass('tree-checkbox1')) {
					setChecked(target, nodeEl, true);
				} else {
					if (ck.hasClass('tree-checkbox2')) {
						var checked = true;
						var unchecked = true;
						var children = getChildren(target, nodeEl);
						for (var i = 0; i < children.length; i++) {
							if (children[i].checked) {
								unchecked = false;
							} else {
								checked = false;
							}
						}
						if (checked) {
							setChecked(target, nodeEl, true);
						}
						if (unchecked) {
							setChecked(target, nodeEl, false);
						}
					}
				}
			}
		}
	};
	function loadData(target, ul, data, isAppend) {
		var opts = $.data(target, 'tree').options;
		//wcj: add loadFilter
		if (! isAppend) {
			data = opts.loadFilter.call(target, data);
		}
		if (!isAppend) {
			$(ul).empty();
		}
		var checkedNodes = [];
		var depth = $(ul).prev('div.tree-node')
				.find('span.tree-indent, span.tree-hit').length;
		appendNodes(ul, data, depth);
		bindTreeEvents(target);
		if (opts.dnd) {
			enableDnd(target);
		} else {
			disableDnd(target);
		}
		for (var i = 0; i < checkedNodes.length; i++) {
			setChecked(target, checkedNodes[i], true);
		}
		var node = null;
		if (target != ul) {
			var prev = $(ul).prev();
			node = getNode(target, prev[0]);
		}
		//wcj: add loadFilter
		if (! isAppend) {
			opts.onLoadSuccess.call(target, node, data);
		}
		function appendNodes(ul, children, depth) {
			for (var i = 0; i < children.length; i++) {
				var li = $('<li></li>').appendTo(ul);
				var item = children[i];
				if (item.state != 'open' && item.state != 'closed') {
					item.state = 'open';
				}
				var node = $('<div class="tree-node"></div>').appendTo(li);
				node.attr('node-id', item.id);
				$.data(node[0], 'tree-node', {
							id : item.id,
							text : item.text,
							iconCls : item.iconCls,
							attributes : item.attributes
						});
				$('<span class="tree-title"></span>').html(item.text)
						.appendTo(node);
				if (opts.checkbox) {
					if (opts.onlyLeafCheck) {
						if (item.state == 'open'
								&& (!item.children || !item.children.length)) {
							if (item.checked) {
								$('<span class="tree-checkbox tree-checkbox1"></span>')
										.prependTo(node);
							} else {
								$('<span class="tree-checkbox tree-checkbox0"></span>')
										.prependTo(node);
							}
						}
					} else {
						if (item.checked) {
							$('<span class="tree-checkbox tree-checkbox1"></span>')
									.prependTo(node);
							checkedNodes.push(node[0]);
						} else {
							$('<span class="tree-checkbox tree-checkbox0"></span>')
									.prependTo(node);
						}
					}
				}
				if (item.children && item.children.length) {
					var subul = $('<ul></ul>').appendTo(li);
					if (item.state == 'open') {
						$('<span class="tree-icon tree-folder tree-folder-open"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-hit tree-expanded"></span>')
								.prependTo(node);
					} else {
						$('<span class="tree-icon tree-folder"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-hit tree-collapsed"></span>')
								.prependTo(node);
						subul.css('display', 'none');
					}
					appendNodes(subul, item.children, depth + 1);
				} else {
					if (item.state == 'closed') {
						$('<span class="tree-icon tree-folder"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-hit tree-collapsed"></span>')
								.prependTo(node);
					} else {
						$('<span class="tree-icon tree-file"></span>')
								.addClass(item.iconCls).prependTo(node);
						$('<span class="tree-indent"></span>').prependTo(node);
					}
				}
				for (var j = 0; j < depth; j++) {
					$('<span class="tree-indent"></span>').prependTo(node);
				}
			}
		};
	};
	function request(target, ul, param, callBack) {
		var opts = $.data(target, 'tree').options;
		param = param || {};
		var node = null;
		if (target != ul) {
			var prev = $(ul).prev();
			node = getNode(target, prev[0]);
		}
		if (opts.onBeforeLoad.call(target, node, param) == false) {
			return;
		}
		if (!opts.url) {
			return;
		}
		var folder = $(ul).prev().children('span.tree-folder');
		folder.addClass('tree-loading');
		$.ajax({
					type : opts.method,
					url : opts.url,
					data : param,
					dataType : 'json',
					success : function(data) {
						folder.removeClass('tree-loading');
						loadData(target, ul, data);
						if (callBack) {
							callBack();
						}
					},
					error : function() {
						folder.removeClass('tree-loading');
						opts.onLoadError.apply(target, arguments);
						if (callBack) {
							callBack();
						}
					}
				});
	};
	function expand(target, nodeEl, callBack) {
		var opts = $.data(target, 'tree').options;
		var hit = $(nodeEl).children('span.tree-hit');
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass('tree-expanded')) {
			return;
		}
		var node = getNode(target, nodeEl);
		if (opts.onBeforeExpand.call(target, node) == false) {
			return;
		}
		hit.removeClass('tree-collapsed tree-collapsed-hover')
				.addClass('tree-expanded');
		hit.next().addClass('tree-folder-open');
		var ul = $(nodeEl).next();
		if (ul.length) {
			if (opts.animate) {
				ul.slideDown('normal', function() {
							opts.onExpand.call(target, node);
							if (callBack) {
								callBack();
							}
						});
			} else {
				ul.css('display', 'block');
				opts.onExpand.call(target, node);
				if (callBack) {
					callBack();
				}
			}
		} else {
			var newNode = $('<ul style="display:none"></ul>').insertAfter(nodeEl);
			request(target, newNode[0], {
						id : node.id
					}, function() {
						if (opts.animate) {
							newNode.slideDown('normal', function() {
										opts.onExpand.call(target, node);
										if (callBack) {
											callBack();
										}
									});
						} else {
							newNode.css('display', 'block');
							opts.onExpand.call(target, node);
							if (callBack) {
								callBack();
							}
						}
					});
		}
	};
	function collapse(target, nodeEl) {
		var opts = $.data(target, 'tree').options;
		var hit = $(nodeEl).children('span.tree-hit');
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass('tree-collapsed')) {
			return;
		}
		var node = getNode(target, nodeEl);
		if (opts.onBeforeCollapse.call(target, node) == false) {
			return;
		}
		hit.removeClass('tree-expanded tree-expanded-hover')
				.addClass('tree-collapsed');
		hit.next().removeClass('tree-folder-open');
		var ul = $(nodeEl).next();
		if (opts.animate) {
			ul.slideUp('normal', function() {
						opts.onCollapse.call(target, node);
					});
		} else {
			ul.css('display', 'none');
			opts.onCollapse.call(target, node);
		}
	};
	function toggle(target, nodeEl) {
		var hit = $(nodeEl).children('span.tree-hit');
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass('tree-expanded')) {
			collapse(target, nodeEl);
		} else {
			expand(target, nodeEl);
		}
	};
	function expandAll(target, nodeEl) {
		var children = getChildren(target, nodeEl);
		if (nodeEl) {
			children.unshift(getNode(target, nodeEl));
		}
		for (var i = 0; i < children.length; i++) {
			expand(target, children[i].target);
		}
	};
	function expandTo(target, nodeEl) {
		var ancestors = [];
		var p = getParent(target, nodeEl);
		while (p) {
			ancestors.unshift(p);
			p = getParent(target, p.target);
		}
		for (var i = 0; i < ancestors.length; i++) {
			expand(target, ancestors[i].target);
		}
	};
	function collapseAll(target, nodeEl) {
		var children = getChildren(target, nodeEl);
		if (nodeEl) {
			children.unshift(getNode(target, nodeEl));
		}
		for (var i = 0; i < children.length; i++) {
			collapse(target, children[i].target);
		}
	};
	function getRoot(target) {
		var roots = getRoots(target);
		if (roots.length) {
			return roots[0];
		} else {
			return null;
		}
	};
	function getRoots(target) {
		var roots = [];
		$(target).children('li').each(function() {
					var node = $(this).children('div.tree-node');
					roots.push(getNode(target, node[0]));
				});
		return roots;
	};
	function getChildren(target, nodeEl) {
		var children = [];
		if (nodeEl) {
			findChildren($(nodeEl));
		} else {
			var roots = getRoots(target);
			for (var i = 0; i < roots.length; i++) {
				children.push(roots[i]);
				findChildren($(roots[i].target));
			}
		}
		function findChildren(node) {
			node.next().find('div.tree-node').each(function() {
						children.push(getNode(target, this));
					});
		};
		return children;
	};
	function getParent(target, nodeEl) {
		var ul = $(nodeEl).parent().parent();
		if (ul[0] == target) {
			return null;
		} else {
			return getNode(target, ul.prev()[0]);
		}
	};
	function getChecked(target) {
		var checkedNodes = [];
		$(target).find('.tree-checkbox1').each(function() {
					var node = $(this).parent();
					checkedNodes.push(getNode(target, node[0]));
				});
		return checkedNodes;
	};
	function getSelected(target) {
		var selectedNodes = $(target).find('div.tree-node-selected');
		if (selectedNodes.length) {
			return getNode(target, selectedNodes[0]);
		} else {
			return null;
		}
	};
	function append(target, param) {
		var parent = $(param.parent);
		var ul;
		if (parent.length == 0) {
			ul = $(target);
		} else {
			ul = parent.next();
			if (ul.length == 0) {
				ul = $('<ul></ul>').insertAfter(parent);
			}
		}
		if (param.data && param.data.length) {
			var icon = parent.find('span.tree-icon');
			if (icon.hasClass('tree-file')) {
				icon.removeClass('tree-file').addClass('tree-folder');
				var hit = $('<span class="tree-hit tree-expanded"></span>')
						.insertBefore(icon);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
		}
		loadData(target, ul[0], param.data, true);
		setCheckBoxValue(target, ul.prev());
	};
	function insert(target, param) {
		var ref = param.before || param.after;
		var pNode = getParent(target, ref);
		var li;
		if (pNode) {
			append(target, {
						parent : pNode.target,
						data : [param.data]
					});
			li = $(pNode.target).next().children('li:last');
		} else {
			append(target, {
						parent : null,
						data : [param.data]
					});
			li = $(target).children('li:last');
		}
		if (param.before) {
			li.insertBefore($(ref).parent());
		} else {
			li.insertAfter($(ref).parent());
		}
	};
	function remove(target, nodeEl) {
		var pNode = getParent(target, nodeEl);
		var node = $(nodeEl);
		var li = node.parent();
		var ul = li.parent();
		li.remove();
		if (ul.children('li').length == 0) {
			var node = ul.prev();
			node.find('.tree-icon').removeClass('tree-folder')
					.addClass('tree-file');
			node.find('.tree-hit').remove();
			$('<span class="tree-indent"></span>').prependTo(node);
			if (ul[0] != target) {
				ul.remove();
			}
		}
		if (pNode) {
			setCheckBoxValue(target, pNode.target);
		}
	};
	function getData(target, nodeEl) {
		function getChildren(aa, ul) {
			ul.children('li').each(function() {
						var nodeEl = $(this).children('div.tree-node');
						var node = getNode(target, nodeEl[0]);
						var sub = $(this).children('ul');
						if (sub.length) {
							node.children = [];
							//wcj: bug, children lost
							//getData(node.children, sub);
							getChildren(node.children, sub);
						}
						aa.push(node);
					});
		};
		if (nodeEl) {
			var node = getNode(target, nodeEl);
			node.children = [];
			getChildren(node.children, $(nodeEl).next());
			return node;
		} else {
			return null;
		}
	};
	function update(target, param) {
		var targetNode = $(param.target);
		var targetNodeData = $.data(param.target, 'tree-node');
		if (targetNodeData.iconCls) {
			targetNode.find('.tree-icon').removeClass(targetNodeData.iconCls);
		}
		$.extend(targetNodeData, param);
		$.data(param.target, 'tree-node', targetNodeData);
		targetNode.attr('node-id', targetNodeData.id);
		targetNode.find('.tree-title').html(targetNodeData.text);
		if (targetNodeData.iconCls) {
			targetNode.find('.tree-icon').addClass(targetNodeData.iconCls);
		}
		var ck = targetNode.find('.tree-checkbox');
		ck.removeClass('tree-checkbox0 tree-checkbox1 tree-checkbox2');
		if (targetNodeData.checked) {
			setChecked(target, param.target, true);
		} else {
			setChecked(target, param.target, false);
		}
	};
	function getNode(target, nodeEl) {
		var node = $.extend({}, $.data(nodeEl, 'tree-node'), {
					target : nodeEl,
					checked : $(nodeEl).find('.tree-checkbox')
							.hasClass('tree-checkbox1')
				});
		if (!isLeaf(target, nodeEl)) {
			node.state = $(nodeEl).find('.tree-hit').hasClass('tree-expanded')
					? 'open'
					: 'closed';
		}
		return node;
	};
	function find(target, id) {
		var node = $(target).find('div.tree-node[node-id=' + id + ']');
		if (node.length) {
			return getNode(target, node[0]);
		} else {
			return null;
		}
	};
	function select(target, nodeEl) {
		var opts = $.data(target, 'tree').options;
		var node = getNode(target, nodeEl);
		if (opts.onBeforeSelect.call(target, node) == false) {
			return;
		}
		$('div.tree-node-selected', target).removeClass('tree-node-selected');
		$(nodeEl).addClass('tree-node-selected');
		opts.onSelect.call(target, node);
	};
	function isLeaf(target, nodeEl) {
		var node = $(nodeEl);
		var hit = node.children('span.tree-hit');
		return hit.length == 0;
	};
	function beginEdit(target, nodeEl) {
		var opts = $.data(target, 'tree').options;
		var node = getNode(target, nodeEl);
		if (opts.onBeforeEdit.call(target, node) == false) {
			return;
		}
		$(nodeEl).css('position', 'relative');
		var nt = $(nodeEl).find('.tree-title');
		var width = nt.outerWidth();
		nt.empty();
		var editor = $('<input class="tree-editor">').appendTo(nt);
		editor.val(node.text).focus();
		editor.width(width + 20);
		editor.height(document.compatMode == 'CSS1Compat' ? (18 - (editor
				.outerHeight() - editor.height())) : 18);
		editor.bind('click', function(e) {
					return false;
				}).bind('mousedown', function(e) {
					e.stopPropagation();
				}).bind('mousemove', function(e) {
					e.stopPropagation();
				}).bind('keydown', function(e) {
					if (e.keyCode == 13) {
						endEdit(target, nodeEl);
						return false;
					} else {
						if (e.keyCode == 27) {
							cancelEdit(target, nodeEl);
							return false;
						}
					}
				}).bind('blur', function(e) {
					e.stopPropagation();
					endEdit(target, nodeEl);
				});
	};
	function endEdit(target, nodeEl) {
		var opts = $.data(target, 'tree').options;
		$(nodeEl).css('position', '');
		var editor = $(nodeEl).find('input.tree-editor');
		var val = editor.val();
		editor.remove();
		var node = getNode(target, nodeEl);
		node.text = val;
		update(target, node);
		opts.onAfterEdit.call(target, node);
	};
	function cancelEdit(target, nodeEl) {
		var opts = $.data(target, 'tree').options;
		$(nodeEl).css('position', '');
		$(nodeEl).find('input.tree-editor').remove();
		var node = getNode(target, nodeEl);
		update(target, node);
		opts.onCancelEdit.call(target, node);
	};
	$.fn.tree = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.tree.methods[options](this, param);
		}
		var options = options || {};
		return this.each(function() {
					var state = $.data(this, 'tree');
					var opts;
					if (state) {
						opts = $.extend(state.options, options);
						state.options = opts;
					} else {
						opts = $.extend({}, $.fn.tree.defaults, $.fn.tree
										.parseOptions(this), options);
						$.data(this, 'tree', {
									options : opts,
									tree : wrapTree(this)
								});
						var data = getTreeData(this);
						loadData(this, this, data);
					}
					if (opts.data) {
						loadData(this, this, opts.data);
					} else {
						if (opts.dnd) {
							enableDnd(this);
						} else {
							disableDnd(this);
						}
					}
					if (opts.url) {
						request(this, this);
					}
				});
	};
	$.fn.tree.methods = {
		options : function(jq) {
			return $.data(jq[0], 'tree').options;
		},
		loadData : function(jq, data) {
			return jq.each(function() {
						loadData(this, this, data);
					});
		},
		getNode : function(jq, target) {
			return getNode(jq[0], target);
		},
		getData : function(jq, target) {
			return getData(jq[0], target);
		},
		reload : function(jq, target) {
			return jq.each(function() {
						if (target) {
							var t = $(target);
							var hit = t.children('span.tree-hit');
							hit.removeClass('tree-expanded tree-expanded-hover')
								.addClass('tree-collapsed');
							t.next().remove();
							expand(this, target);
						} else {
							$(this).empty();
							request(this, this);
						}
					});
		},
		getRoot : function(jq) {
			return getRoot(jq[0]);
		},
		getRoots : function(jq) {
			return getRoots(jq[0]);
		},
		getParent : function(jq, target) {
			return getParent(jq[0], target);
		},
		getChildren : function(jq, target) {
			return getChildren(jq[0], target);
		},
		getChecked : function(jq) {
			return getChecked(jq[0]);
		},
		getSelected : function(jq) {
			return getSelected(jq[0]);
		},
		isLeaf : function(jq, target) {
			return isLeaf(jq[0], target);
		},
		find : function(jq, id) {
			return find(jq[0], id);
		},
		select : function(jq, target) {
			return jq.each(function() {
						select(this, target);
					});
		},
		check : function(jq, target) {
			return jq.each(function() {
						setChecked(this, target, true);
					});
		},
		uncheck : function(jq, target) {
			return jq.each(function() {
						setChecked(this, target, false);
					});
		},
		collapse : function(jq, target) {
			return jq.each(function() {
						collapse(this, target);
					});
		},
		expand : function(jq, target) {
			return jq.each(function() {
						expand(this, target);
					});
		},
		collapseAll : function(jq, target) {
			return jq.each(function() {
						collapseAll(this, target);
					});
		},
		expandAll : function(jq, target) {
			return jq.each(function() {
						expandAll(this, target);
					});
		},
		expandTo : function(jq, target) {
			return jq.each(function() {
						expandTo(this, target);
					});
		},
		toggle : function(jq, target) {
			return jq.each(function() {
						toggle(this, target);
					});
		},
		append : function(jq, param) {
			return jq.each(function() {
						append(this, param);
					});
		},
		insert : function(jq, param) {
			return jq.each(function() {
						insert(this, param);
					});
		},
		remove : function(jq, target) {
			return jq.each(function() {
						remove(this, target);
					});
		},
		pop : function(jq, target) {
			var data = jq.tree('getData', target);
			jq.tree('remove', target);
			return data;
		},
		update : function(jq, param) {
			return jq.each(function() {
						update(this, param);
					});
		},
		enableDnd : function(jq) {
			return jq.each(function() {
						enableDnd(this);
					});
		},
		disableDnd : function(jq) {
			return jq.each(function() {
						disableDnd(this);
					});
		},
		beginEdit : function(jq, nodeEl) {
			return jq.each(function() {
						beginEdit(this, nodeEl);
					});
		},
		endEdit : function(jq, nodeEl) {
			return jq.each(function() {
						endEdit(this, nodeEl);
					});
		},
		cancelEdit : function(jq, nodeEl) {
			return jq.each(function() {
						cancelEdit(this, nodeEl);
					});
		}
	};
	$.fn.tree.parseOptions = function(target) {
		var t = $(target);
		return {
			url : t.attr('url'),
			method : (t.attr('method') ? t.attr('method') : undefined),
			checkbox : (t.attr('checkbox')
					? t.attr('checkbox') == 'true'
					: undefined),
			cascadeCheck : (t.attr('cascadeCheck')
					? t.attr('cascadeCheck') == 'true'
					: undefined),
			onlyLeafCheck : (t.attr('onlyLeafCheck')
					? t.attr('onlyLeafCheck') == 'true'
					: undefined),
			animate : (t.attr('animate')
					? t.attr('animate') == 'true'
					: undefined),
			dnd : (t.attr('dnd') ? t.attr('dnd') == 'true' : undefined)
		};
	};
	$.fn.tree.defaults = {
		url : null,
		method : 'post',
		animate : false,
		checkbox : false,
		cascadeCheck : true,
		onlyLeafCheck : false,
		dnd : false,
		data : null,
		//wcj: add loadFilter
		loadFilter : function(node, data){
			return data;
		},
		onBeforeLoad : function(node, param) {
		},
		onLoadSuccess : function(node, data) {
		},
		onLoadError : function(arguments) {
		},
		onClick : function(node) {
		},
		onDblClick : function(node) {
		},
		onBeforeExpand : function(node) {
		},
		onExpand : function(node) {
		},
		onBeforeCollapse : function(node) {
		},
		onCollapse : function(node) {
		},
		onCheck : function(node, checked) {
		},
		//wcj: add event onBeforeCheck
		onBeforeCheck : function(node, checked) {
		},
		onBeforeSelect : function(node) {
		},
		onSelect : function(node) {
		},
		onContextMenu : function(e, node) {
		},
		onDrop : function(target, source, point) {
		},
		onBeforeEdit : function(node) {
		},
		onAfterEdit : function(node) {
		},
		onCancelEdit : function(node) {
		}
	};
})(jQuery);

/**
 * parser - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($){
	$.parser = {
		auto : true,
		//wcj: onBefore
		onBefore : function(context) {
		},
		onComplete : function(context) {
		},
		//wcj: add combogrid, combo, form
		//wcj: containers first
		/*
		plugins : ['linkbutton', 'menu', 'menubutton', 'splitbutton',
				'progressbar', 'tree',
				'combobox', 'combotree', 'numberbox', 'validatebox', 'searchbox',
				'numberspinner', 'timespinner', 'calendar', 'datebox',
				'datetimebox', 'layout', 'panel', 'datagrid', 'propertygrid',
				'treegrid', 'tabs',
				'accordion', 'window', 'dialog', 'combogrid', 'combo', 'form'],
		*/
		plugins : ['linkbutton', 'menu', 'menubutton', 'splitbutton',
		        'layout', 'tabs', 'accordion', 'panel', 'window', 'dialog', 
				'progressbar', 'tree',
				'combobox', 'combotree', 'numberbox', 'searchbox',
				'numberspinner', 'timespinner', 'calendar', 'datebox',
				'datetimebox', 'datagrid', 'propertygrid',
				'treegrid', 
				'combogrid', 'combo', 'form', 'validatebox'],
		parse : function(context) {
			//cache findings
			var findings = {};
			for (var i = 0; i < $.parser.plugins.length; i++) {
				var name = $.parser.plugins[i];
				var r = $('.easyui-' + name, context);
				findings[name] = r;
			}
			//
			findings.th_combobox = $("th[editor*='combobox']", context);
			findings.th_combogrid = $("th[editor*='combogrid']", context);
			findings.th_combotree = $("th[editor*='combotree']", context);
			findings.th_numberbox = $("th[editor*='numberbox']", context);
			
			//wcj: onBefore
			$.parser.onBefore.call($.parser, context, findings);
			
			var aa = [];
			//wcj: use cached findings
			$.each(findings, function(name, r) {
				if (r.length) {
					if (r[name]) {
						r[name]();
					} else {
						aa.push({
									name : name,
									jq : r
								});
					}
				}
			});
			/*
			for (var i = 0; i < $.parser.plugins.length; i++) {
				var name = $.parser.plugins[i];
				var r = $('.easyui-' + name, context);
				if (r.length) {
					if (r[name]) {
						r[name]();
					} else {
						aa.push({
									name : name,
									jq : r
								});
					}
				}
			}
			*/
			if (aa.length && window.easyloader) {
				var names = [];
				for (var i = 0; i < aa.length; i++) {
					names.push(aa[i].name);
				}
				easyloader.load(names, function() {
							for (var i = 0; i < aa.length; i++) {
								var name = aa[i].name;
								var jq = aa[i].jq;
								jq[name]();
							}
							$.parser.onComplete.call($.parser, context, findings);
						});
			} else {
				$.parser.onComplete.call($.parser, context, findings);
			}
		}
	};
	$(function() {
				if (!window.easyloader && $.parser.auto) {
					$.parser.parse();
				}
			});
})(jQuery);

/**
 * progressbar - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ]
 * 
 */
(function($) {
	function init(target) {
		$(target).addClass('progressbar');
		$(target)
				.html('<div class="progressbar-text"></div><div class="progressbar-value">&nbsp;</div>');
		return $(target);
	};
	function setWidth(target, width) {
		var opts = $.data(target, 'progressbar').options;
		var bar = $.data(target, 'progressbar').bar;
		if (width) {
			opts.width = width;
		}
		if ($.boxModel == true) {
			bar.width(opts.width - (bar.outerWidth() - bar.width()));
		} else {
			bar.width(opts.width);
		}
		bar.find('div.progressbar-text').width(bar.width());
	};
	$.fn.progressbar = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.progressbar.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'progressbar');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'progressbar', {
									options : $
											.extend(
													{},
													$.fn.progressbar.defaults,
													$.fn.progressbar
															.parseOptions(this),
													options),
									bar : init(this)
								});
					}
					$(this).progressbar('setValue', state.options.value);
					setWidth(this);
				});
	};
	$.fn.progressbar.methods = {
		options : function(jq) {
			return $.data(jq[0], 'progressbar').options;
		},
		resize : function(jq, width) {
			return jq.each(function() {
						setWidth(this, width);
					});
		},
		getValue : function(jq) {
			return $.data(jq[0], 'progressbar').options.value;
		},
		setValue : function(jq, value) {
			if (value < 0) {
				value = 0;
			}
			if (value > 100) {
				value = 100;
			}
			return jq.each(function() {
						var opts = $.data(this, 'progressbar').options;
						var text = opts.text.replace(/{value}/, value);
						var oldVal = opts.value;
						opts.value = value;
						$(this).find('div.progressbar-value').width(value + '%');
						$(this).find('div.progressbar-text').html(text);
						if (oldVal != value) {
							opts.onChange.call(this, value, oldVal);
						}
					});
		}
	};
	$.fn.progressbar.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			value : (t.attr('value') ? parseInt(t.attr('value')) : undefined),
			text : t.attr('text')
		};
	};
	$.fn.progressbar.defaults = {
		width : 'auto',
		value : 0,
		text : '{value}%',
		onChange : function(newVal, oldVal) {
		}
	};
})(jQuery);

/**
 * panel - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($) {
	function removeNode(node) {
		node.each(function() {
					$(this).remove();
					if ($.browser.msie) {
						this.outerHTML = '';
					}
				});
	};
	function setSize(target, param) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		var pheader = panel.children('div.panel-header');
		var pbody = panel.children('div.panel-body');
		if (param) {
			if (param.width) {
				opts.width = param.width;
			}
			if (param.height) {
				opts.height = param.height;
			}
			if (param.left != null) {
				opts.left = param.left;
			}
			if (param.top != null) {
				opts.top = param.top;
			}
		}
		if (opts.fit == true) {
			var p = panel.parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		panel.css({
					left : opts.left,
					top : opts.top
				});
		if (!isNaN(opts.width)) {
			if ($.boxModel == true) {
				panel.width(opts.width - (panel.outerWidth() - panel.width()));
			} else {
				panel.width(opts.width);
			}
		} else {
			panel.width('auto');
		}
		if ($.boxModel == true) {
			pheader.width(panel.width() - (pheader.outerWidth() - pheader.width()));
			pbody.width(panel.width() - (pbody.outerWidth() - pbody.width()));
		} else {
			pheader.width(panel.width());
			pbody.width(panel.width());
		}
		if (!isNaN(opts.height)) {
			if ($.boxModel == true) {
				panel.height(opts.height - (panel.outerHeight() - panel.height()));
				pbody.height(panel.height() - pheader.outerHeight()
						- (pbody.outerHeight() - pbody.height()));
			} else {
				panel.height(opts.height);
				pbody.height(panel.height() - pheader.outerHeight());
			}
		} else {
			pbody.height('auto');
		}
		panel.css('height', '');
		opts.onResize.apply(target, [opts.width, opts.height]);
		panel.find('>div.panel-body>div').triggerHandler('_resize');
	};
	function movePanel(target, param) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		if (param) {
			if (param.left != null) {
				opts.left = param.left;
			}
			if (param.top != null) {
				opts.top = param.top;
			}
		}
		panel.css({
					left : opts.left,
					top : opts.top
				});
		opts.onMove.apply(target, [opts.left, opts.top]);
	};
	function wrapPanel(target) {
		var panel = $(target).addClass('panel-body')
				.wrap('<div class="panel"></div>').parent();
		panel.bind('_resize', function() {
					var opts = $.data(target, 'panel').options;
					if (opts.fit == true) {
						setSize(target);
					}
					return false;
				});
		return panel;
	};
	function addHeader(target) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		removeNode(panel.find('>div.panel-header'));
		if (opts.title && !opts.noheader) {
			var header = $('<div class="panel-header"><div class="panel-title">'
					+ opts.title + '</div></div>').prependTo(panel);
			if (opts.iconCls) {
				header.find('.panel-title').addClass('panel-with-icon');
				$('<div class="panel-icon"></div>').addClass(opts.iconCls)
						.appendTo(header);
			}
			//wcj:
			if (opts.iconUrl) {
				header.find('.panel-title').addClass('panel-with-icon');
				$('<div class="panel-icon"><img style="border:0;" src="' + opts.iconUrl + '"/></div>')
						.appendTo(header);
			}
			var tool = $('<div class="panel-tool"></div>').appendTo(header);
			if (opts.closable) {
				//wcj: add x for boot css
				//$('<div class="panel-tool-close"></div>').appendTo(tool).bind(
				$('<div class="panel-tool-close">×</div>').appendTo(tool).bind(
						'click', onClose);
			}
			if (opts.maximizable) {
				$('<div class="panel-tool-max"></div>').appendTo(tool).bind(
						'click', onMax);
			}
			if (opts.minimizable) {
				$('<div class="panel-tool-min"></div>').appendTo(tool).bind(
						'click', onMin);
			}
			if (opts.collapsible) {
				$('<div class="panel-tool-collapse"></div>').appendTo(tool)
						.bind('click', onToggle);
			}
			if (opts.tools) {
				for (var i = opts.tools.length - 1; i >= 0; i--) {
					var t = $('<div></div>').addClass(opts.tools[i].iconCls)
							.appendTo(tool);
					if (opts.tools[i].handler) {
						t.bind('click', eval(opts.tools[i].handler));
					}
				}
			}
			tool.find('div').hover(function() {
						$(this).addClass('panel-tool-over');
					}, function() {
						$(this).removeClass('panel-tool-over');
					});
			panel.find('>div.panel-body').removeClass('panel-body-noheader');
		} else {
			panel.find('>div.panel-body').addClass('panel-body-noheader');
		}
		function onToggle() {
			if (opts.collapsed == true) {
				expandPanel(target, true);
			} else {
				collapsePanel(target, true);
			}
			return false;
		};
		function onMin() {
			minimizePanel(target);
			return false;
		};
		function onMax() {
			if (opts.maximized == true) {
				restorePanel(target);
			} else {
				maximizePanel(target);
			}
			return false;
		};
		function onClose() {
			closePanel(target);
			return false;
		};
	};
	function loadData(target) {
		var state = $.data(target, 'panel');
		if (state.options.href && (!state.isLoaded || !state.options.cache)) {
			state.isLoaded = false;
			var pbody = state.panel.find('>div.panel-body');
			if(state.options.loadingMessage){
				pbody.html($('<div class="panel-loading"></div>')
						.html(state.options.loadingMessage));
			}
			$.ajax({
						url : state.options.href,
						cache : false,
						success : function(data) {
							pbody.html(state.options.extractor.call(target,data));
							//wcj: fix bug in src file
							//pbody.html(data);
							if ($.parser) {
								$.parser.parse(pbody);
							}
							state.options.onLoad.apply(target, arguments);
							//wcj: onLoadAsync
							if (state.options.onLoadAsync) {
								setTimeout(function() {
									state.options.onLoadAsync.apply(target, arguments);
								}, 0);
							}
							state.isLoaded = true;
						}
					});
		}
	};
	function init(target) {
		//wcj: no need?
		return;
		$(target)
				.find('div.panel:visible,div.accordion:visible,div.tabs-container:visible,div.layout:visible')
				.each(function() {
							$(this).triggerHandler('_resize', [true]);
						});
	};
	function openPanel(target, forceOpen) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		if (forceOpen != true) {
			if (opts.onBeforeOpen.call(target) == false) {
				return;
			}
		}
		panel.show();
		opts.closed = false;
		opts.minimized = false;
		opts.onOpen.call(target);
		if (opts.maximized == true) {
			opts.maximized = false;
			maximizePanel(target);
		}
		if (opts.collapsed == true) {
			opts.collapsed = false;
			collapsePanel(target);
		}
		if (!opts.collapsed) {
			loadData(target);
			init(target);
		}
	};
	function closePanel(target, forceClose) {
		//wcj:
		if (! $.data(target, 'panel')) {
			return;
		}
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		if (forceClose != true) {
			if (opts.onBeforeClose.call(target) == false) {
				return;
			}
		}
		panel.hide();
		opts.closed = true;
		opts.onClose.call(target);
	};
	function destroyPanel(target, forceDestroy) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		if (forceDestroy != true) {
			if (opts.onBeforeDestroy.call(target) == false) {
				return;
			}
		}
		removeNode(panel);
		opts.onDestroy.call(target);
	};
	function collapsePanel(target, forceDestroy) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		var body = panel.children('div.panel-body');
		var tool = panel.children('div.panel-header')
				.find('div.panel-tool-collapse');
		if (opts.collapsed == true) {
			return;
		}
		body.stop(true, true);
		if (opts.onBeforeCollapse.call(target) == false) {
			return;
		}
		tool.addClass('panel-tool-expand');
		if (forceDestroy == true) {
			body.slideUp('normal', function() {
						opts.collapsed = true;
						opts.onCollapse.call(target);
					});
		} else {
			body.hide();
			opts.collapsed = true;
			opts.onCollapse.call(target);
		}
	};
	function expandPanel(target, animate) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		var body = panel.children('div.panel-body');
		var tool = panel.children('div.panel-header')
				.find('div.panel-tool-collapse');
		if (opts.collapsed == false) {
			return;
		}
		body.stop(true, true);
		if (opts.onBeforeExpand.call(target) == false) {
			return;
		}
		tool.removeClass('panel-tool-expand');
		if (animate == true) {
			body.slideDown('normal', function() {
						opts.collapsed = false;
						opts.onExpand.call(target);
						loadData(target);
						init(target);
					});
		} else {
			body.show();
			opts.collapsed = false;
			opts.onExpand.call(target);
			loadData(target);
			init(target);
		}
	};
	function maximizePanel(target) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		var tool = panel.children('div.panel-header').find('div.panel-tool-max');
		if (opts.maximized == true) {
			return;
		}
		tool.addClass('panel-tool-restore');
		$.data(target, 'panel').original = {
			width : opts.width,
			height : opts.height,
			left : opts.left,
			top : opts.top,
			fit : opts.fit
		};
		opts.left = 0;
		opts.top = 0;
		opts.fit = true;
		setSize(target);
		opts.minimized = false;
		opts.maximized = true;
		opts.onMaximize.call(target);
	};
	function minimizePanel(target) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		panel.hide();
		opts.minimized = true;
		opts.maximized = false;
		opts.onMinimize.call(target);
	};
	function restorePanel(target) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		var tool = panel.children('div.panel-header').find('div.panel-tool-max');
		if (opts.maximized == false) {
			return;
		}
		panel.show();
		tool.removeClass('panel-tool-restore');
		var original = $.data(target, 'panel').original;
		opts.width = original.width;
		opts.height = original.height;
		opts.left = original.left;
		opts.top = original.top;
		opts.fit = original.fit;
		setSize(target);
		opts.minimized = false;
		opts.maximized = false;
		opts.onRestore.call(target);
	};
	function setBorder(target) {
		var opts = $.data(target, 'panel').options;
		var panel = $.data(target, 'panel').panel;
		if (opts.border == true) {
			panel.children('div.panel-header')
					.removeClass('panel-header-noborder');
			panel.children('div.panel-body').removeClass('panel-body-noborder');
		} else {
			panel.children('div.panel-header').addClass('panel-header-noborder');
			panel.children('div.panel-body').addClass('panel-body-noborder');
		}
		panel.css(opts.style);
		panel.addClass(opts.cls);
		panel.children('div.panel-header').addClass(opts.headerCls);
		panel.children('div.panel-body').addClass(opts.bodyCls);
	};
	function setTitle(target, title) {
		$.data(target, 'panel').options.title = title;
		$(target).panel('header').find('div.panel-title').html(title);
	};
	var TO = false;
	var resized = true;
	$(window).unbind('.panel').bind('resize.panel', function() {
				if (!resized) {
					return;
				}
				if (TO !== false) {
					clearTimeout(TO);
				}
				TO = setTimeout(function() {
							resized = false;
							var layout = $('body.layout');
							if (layout.length) {
								layout.layout('resize');
							} else {
								$('body').children('div.panel,div.accordion,div.tabs-container,div.layout').triggerHandler('_resize');
							}
							resized = true;
							TO = false;
						}, 200);
			});
	$.fn.panel = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.panel.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'panel');
					var opts;
					if (state) {
						opts = $.extend(state.options, options);
					} else {
						opts = $.extend({}, $.fn.panel.defaults, $.fn.panel
										.parseOptions(this), options);
						$(this).attr('title', '');
						state = $.data(this, 'panel', {
									options : opts,
									panel : wrapPanel(this),
									isLoaded : false
								});
					}
					if (opts.content) {
						$(this).html(opts.content);
						if ($.parser) {
							$.parser.parse(this);
						}
					}
					addHeader(this);
					setBorder(this);
					if (opts.doSize == true) {
						state.panel.css('display', 'block');
						setSize(this);
					}
					if (opts.closed == true || opts.minimized == true) {
						state.panel.hide();
					} else {
						openPanel(this);
					}
				});
	};
	$.fn.panel.methods = {
		options : function(jq) {
			return $.data(jq[0], 'panel').options;
		},
		panel : function(jq) {
			return $.data(jq[0], 'panel').panel;
		},
		header : function(jq) {
			return $.data(jq[0], 'panel').panel.find('>div.panel-header');
		},
		body : function(jq) {
			return $.data(jq[0], 'panel').panel.find('>div.panel-body');
		},
		setTitle : function(jq, title) {
			return jq.each(function() {
						setTitle(this, title);
					});
		},
		open : function(jq, param) {
			return jq.each(function() {
						openPanel(this, param);
					});
		},
		close : function(jq, param) {
			return jq.each(function() {
						closePanel(this, param);
					});
		},
		destroy : function(jq, param) {
			return jq.each(function() {
						destroyPanel(this, param);
					});
		},
		refresh : function(jq, url) {
			return jq.each(function() {
						$.data(this, 'panel').isLoaded = false;
						if (url) {
							$.data(this, 'panel').options.href = url;
						}
						loadData(this);
					});
		},
		resize : function(jq, param) {
			return jq.each(function() {
						setSize(this, param);
					});
		},
		move : function(jq, param) {
			return jq.each(function() {
						movePanel(this, param);
					});
		},
		maximize : function(jq) {
			return jq.each(function() {
						maximizePanel(this);
					});
		},
		minimize : function(jq) {
			return jq.each(function() {
						minimizePanel(this);
					});
		},
		restore : function(jq) {
			return jq.each(function() {
						restorePanel(this);
					});
		},
		collapse : function(jq, param) {
			return jq.each(function() {
						collapsePanel(this, param);
					});
		},
		expand : function(jq, param) {
			return jq.each(function() {
						expandPanel(this, param);
					});
		}
	};
	$.fn.panel.parseOptions = function(target) {
		var t = $(target);
		return {
			//wcj: handle 100% or such
			width : (target.style.width && target.style.width.indexOf("%") > 0) ?
					undefined :
					(parseInt(target.style.width) || undefined),
			//wcj: handle 100% or such
			height : (target.style.height && target.style.height.indexOf("%") > 0) ?
					undefined :
					(parseInt(target.style.height) || undefined),
			left : (parseInt(target.style.left) || undefined),
			top : (parseInt(target.style.top) || undefined),
			title : (t.attr('title') || undefined),
			iconCls : (t.attr('iconCls') || t.attr('icon')),
			//wcj:
			iconUrl : t.attr('iconUrl'),
			cls : t.attr('cls'),
			headerCls : t.attr('headerCls'),
			bodyCls : t.attr('bodyCls'),
			href : t.attr('href'),
			loadingMessage : (t.attr('loadingMessage') != undefined ? t
					.attr('loadingMessage') : undefined),
			cache : (t.attr('cache') ? t.attr('cache') == 'true' : undefined),
			fit : (t.attr('fit') ? t.attr('fit') == 'true' : undefined),
			border : (t.attr('border') ? t.attr('border') == 'true' : undefined),
			noheader : (t.attr('noheader')
					? t.attr('noheader') == 'true'
					: undefined),
			collapsible : (t.attr('collapsible')
					? t.attr('collapsible') == 'true'
					: undefined),
			minimizable : (t.attr('minimizable')
					? t.attr('minimizable') == 'true'
					: undefined),
			maximizable : (t.attr('maximizable')
					? t.attr('maximizable') == 'true'
					: undefined),
			closable : (t.attr('closable')
					? t.attr('closable') == 'true'
					: undefined),
			collapsed : (t.attr('collapsed')
					? t.attr('collapsed') == 'true'
					: undefined),
			minimized : (t.attr('minimized')
					? t.attr('minimized') == 'true'
					: undefined),
			maximized : (t.attr('maximized')
					? t.attr('maximized') == 'true'
					: undefined),
			closed : (t.attr('closed') ? t.attr('closed') == 'true' : undefined)
		};
	};
	$.fn.panel.defaults = {
		title : null,
		iconCls : null,
		width : 'auto',
		height : 'auto',
		left : null,
		top : null,
		cls : null,
		headerCls : null,
		bodyCls : null,
		style : {},
		href : null,
		cache : true,
		fit : false,
		border : true,
		doSize : true,
		noheader : false,
		content : null,
		collapsible : false,
		minimizable : false,
		maximizable : false,
		closable : false,
		collapsed : false,
		minimized : false,
		maximized : false,
		closed : false,
		tools : [],
		href : null,
		loadingMessage : 'Loading...',
		extractor : function(data) {
			var bodyRegEx = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
			var found = bodyRegEx.exec(data);
			if (found) {
				return found[1];
			} else {
				return data;
			}
		},
		onLoad : function() {
		},
		onBeforeOpen : function() {
		},
		onOpen : function() {
		},
		onBeforeClose : function() {
		},
		onClose : function() {
		},
		onBeforeDestroy : function() {
		},
		onDestroy : function() {
		},
		onResize : function(width, height) {
		},
		onMove : function(left, top) {
		},
		onMaximize : function() {
		},
		onRestore : function() {
		},
		onMinimize : function() {
		},
		onBeforeCollapse : function() {
		},
		onBeforeExpand : function() {
		},
		onCollapse : function() {
		},
		onExpand : function() {
		}
	};
})(jQuery);

/**
 * window - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 panel
 *   draggable
 *   resizable
 * 
 */
(function($) {
	function setSize(target, param) {
		var opts = $.data(target, 'window').options;
		if (param) {
			if (param.width) {
				opts.width = param.width;
			}
			if (param.height) {
				opts.height = param.height;
			}
			if (param.left != null) {
				opts.left = param.left;
			}
			if (param.top != null) {
				opts.top = param.top;
			}
		}
		$(target).panel('resize', opts);
	};
	function move(target, param) {
		var window = $.data(target, 'window');
		if (param) {
			if (param.left != null) {
				window.options.left = param.left;
			}
			if (param.top != null) {
				window.options.top = param.top;
			}
		}
		$(target).panel('move', window.options);
		if (window.shadow) {
			window.shadow.css({
						left : window.options.left,
						top : window.options.top
					});
		}
	};
	function init(target) {
		var state = $.data(target, 'window');
		var win = $(target).panel($.extend({}, state.options, {
					border : false,
					doSize : true,
					closed : true,
					cls : 'window',
					headerCls : 'window-header',
					bodyCls : 'window-body '+(state.options.noheader?'window-body-noheader':''),
					onBeforeDestroy : function() {
						if (state.options.onBeforeDestroy.call(target) == false) {
							return false;
						}
						if (state.shadow) {
							state.shadow.remove();
						}
						if (state.mask) {
							state.mask.remove();
						}
					},
					onClose : function() {
						if (state.shadow) {
							state.shadow.hide();
						}
						if (state.mask) {
							state.mask.hide();
						}
						state.options.onClose.call(target);
					},
					onOpen : function() {
						if (state.mask) {
							state.mask.css({
										display : 'block',
										zIndex : $.fn.window.defaults.zIndex++
									});
						}
						if (state.shadow) {
							state.shadow.css({
										display : 'block',
										zIndex : $.fn.window.defaults.zIndex++,
										left : state.options.left,
										top : state.options.top,
										width : state.window.outerWidth(),
										height : state.window.outerHeight()
									});
						}
						state.window.css('z-index', $.fn.window.defaults.zIndex++);
						state.options.onOpen.call(target);
					},
					onResize : function(width, height) {
						var opts = $(target).panel('options');
						state.options.width = opts.width;
						state.options.height = opts.height;
						state.options.left = opts.left;
						state.options.top = opts.top;
						if (state.shadow) {
							state.shadow.css({
										left : state.options.left,
										top : state.options.top,
										width : state.window.outerWidth(),
										height : state.window.outerHeight()
									});
						}
						state.options.onResize.call(target, width, height);
					},
					onMinimize : function() {
						if (state.shadow) {
							state.shadow.hide();
						}
						if (state.mask) {
							state.mask.hide();
						}
						state.options.onMinimize.call(target);
					},
					onBeforeCollapse : function() {
						if (state.options.onBeforeCollapse.call(target) == false) {
							return false;
						}
						if (state.shadow) {
							state.shadow.hide();
						}
					},
					onExpand : function() {
						if (state.shadow) {
							state.shadow.show();
						}
						state.options.onExpand.call(target);
					}
				}));
		state.window = win.panel('panel');
		if (state.mask) {
			state.mask.remove();
		}
		if (state.options.modal == true) {
			state.mask = $('<div class="window-mask"></div>')
					.insertAfter(state.window);
			state.mask.css({
						display : 'none'
					});
		}
		if (state.shadow) {
			state.shadow.remove();
		}
		if (state.options.shadow == true) {
			state.shadow = $('<div class="window-shadow"></div>')
					.insertAfter(state.window);
			state.shadow.css({
						display : 'none'
					});
		}
		if (state.options.left == null) {
			var width = state.options.width;
			if (isNaN(width)) {
				width = state.window.outerWidth();
			}
			if (state.options.inline) {
				var parent = state.window.parent();
				state.options.left = (parent.width() - width) / 2 + parent.scrollLeft();
			} else {
				state.options.left = ($(window).width() - width) / 2
						+ $(document).scrollLeft();
			}
		}
		if (state.options.top == null) {
			var height = state.window.height;
			if (isNaN(height)) {
				height = state.window.outerHeight();
			}
			if (state.options.inline) {
				var parent = state.window.parent();
				state.options.top = (parent.height() - height) / 2 + parent.scrollTop();
			} else {
				state.options.top = ($(window).height() - height) / 2
						+ $(document).scrollTop();
			}
		}
		move(target);
		if (state.options.closed == false) {
			win.window('open');
		}
	};
	function setProperties(target) {
		var state = $.data(target, 'window');
		state.window.draggable({
			handle : '>div.panel-header>div.panel-title',
			disabled : state.options.draggable == false,
			onStartDrag : function(e) {
				if (state.mask) {
					state.mask.css('z-index', $.fn.window.defaults.zIndex++);
				}
				if (state.shadow) {
					state.shadow.css('z-index', $.fn.window.defaults.zIndex++);
				}
				state.window.css('z-index', $.fn.window.defaults.zIndex++);
				if (!state.proxy) {
					state.proxy = $('<div class="window-proxy"></div>')
							.insertAfter(state.window);
				}
				state.proxy.css({
							display : 'none',
							zIndex : $.fn.window.defaults.zIndex++,
							left : e.data.left,
							top : e.data.top,
							width : ($.boxModel == true
									? (state.window.outerWidth() - (state.proxy
											.outerWidth() - state.proxy.width()))
									: state.window.outerWidth()),
							height : ($.boxModel == true
									? (state.window.outerHeight() - (state.proxy
											.outerHeight() - state.proxy.height()))
									: state.window.outerHeight())
						});
				setTimeout(function() {
							if (state.proxy) {
								state.proxy.show();
							}
						}, 500);
			},
			onDrag : function(e) {
				state.proxy.css({
							display : 'block',
							left : e.data.left,
							top : e.data.top
						});
				return state.options.noproxy == true;
			},
			onStopDrag : function(e) {
				state.options.left = e.data.left;
				state.options.top = e.data.top;
				$(target).window('move');
				state.proxy.remove();
				state.proxy = null;
			}
		});
		state.window.resizable({
			disabled : state.options.resizable == false,
			onStartResize : function(e) {
				state.pmask=$('<div class="window-proxy-mask"></div>').insertAfter(state.window);
				state.pmask.css({zIndex:$.fn.window.defaults.zIndex++,left:e.data.left,top:e.data.top,width:state.window.outerWidth(),height:state.window.outerHeight()});
				if (!state.proxy) {
					state.proxy = $('<div class="window-proxy"></div>')
							.insertAfter(state.window);
				}
				state.proxy.css({
					zIndex : $.fn.window.defaults.zIndex++,
					left : e.data.left,
					top : e.data.top,
					width : ($.boxModel == true ? (e.data.width - (state.proxy
							.outerWidth() - state.proxy.width())) : e.data.width),
					height : ($.boxModel == true
							? (e.data.height - (state.proxy.outerHeight() - state.proxy
									.height()))
							: e.data.height)
				});
			},
			onResize : function(e) {
				state.proxy.css({
					left : e.data.left,
					top : e.data.top,
					width : ($.boxModel == true ? (e.data.width - (state.proxy
							.outerWidth() - state.proxy.width())) : e.data.width),
					height : ($.boxModel == true
							? (e.data.height - (state.proxy.outerHeight() - state.proxy
									.height()))
							: e.data.height)
				});
				return false;
			},
			onStopResize : function(e) {
				state.options.left = e.data.left;
				state.options.top = e.data.top;
				state.options.width = e.data.width;
				state.options.height = e.data.height;
				setSize(target);
				state.pmask.remove();
				state.pmask=null;
				state.proxy.remove();
				state.proxy = null;
			}
		});
	};
	function getPageArea() {
		if (document.compatMode == 'BackCompat') {
			return {
				width : Math.max(document.body.scrollWidth,
						document.body.clientWidth),
				height : Math.max(document.body.scrollHeight,
						document.body.clientHeight)
			};
		} else {
			return {
				width : Math.max(document.documentElement.scrollWidth,
						document.documentElement.clientWidth),
				height : Math.max(document.documentElement.scrollHeight,
						document.documentElement.clientHeight)
			};
		}
	};
	$(window).resize(function() {
				$('body>div.window-mask').css({
							width : $(window).width(),
							height : $(window).height()
						});
				setTimeout(function() {
							$('body>div.window-mask').css({
										width : getPageArea().width,
										height : getPageArea().height
									});
						}, 50);
			});
	$.fn.window = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.window.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.panel(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'window');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'window', {
									options : $.extend({},
											$.fn.window.defaults, $.fn.window
													.parseOptions(this), options)
								});
						if (!state.options.inline) {
							$(this).appendTo('body');
						}
					}
					init(this);
					setProperties(this);
				});
	};
	$.fn.window.methods = {
		options : function(jq) {
			var opts = jq.panel('options');
			var options = $.data(jq[0], 'window').options;
			return $.extend(options, {
						closed : opts.closed,
						collapsed : opts.collapsed,
						minimized : opts.minimized,
						maximized : opts.maximized
					});
		},
		window : function(jq) {
			return $.data(jq[0], 'window').window;
		},
		resize : function(jq, param) {
			return jq.each(function() {
						setSize(this, param);
					});
		},
		move : function(jq, param) {
			return jq.each(function() {
						move(this, param);
					});
		}
	};
	$.fn.window.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.panel.parseOptions(target), {
			draggable : (t.attr('draggable')
					? t.attr('draggable') == 'true'
					: undefined),
			resizable : (t.attr('resizable')
					? t.attr('resizable') == 'true'
					: undefined),
			shadow : (t.attr('shadow') ? t.attr('shadow') == 'true' : undefined),
			noproxy : (t.attr('noproxy') ? t.attr('noproxy') == 'true' : undefined),
			modal : (t.attr('modal') ? t.attr('modal') == 'true' : undefined),
			inline : (t.attr('inline') ? t.attr('inline') == 'true' : undefined)
		});
	};
	$.fn.window.defaults = $.extend({}, $.fn.panel.defaults, {
				zIndex : 9000,
				draggable : true,
				resizable : true,
				shadow : true,
				modal : false,
				inline : false,
				title : 'New Window',
				collapsible : true,
				minimizable : true,
				maximizable : true,
				closable : true,
				closed : false
			});
})(jQuery);

/**
 * dialog - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 window
 * 
 */
(function($) {
	function wrapDialog(target) {
		var t = $(target);
		t.wrapInner('<div class="dialog-content"></div>');
		var contentPanel = t.children('div.dialog-content');
		contentPanel.attr('style', t.attr('style'));
		t.removeAttr('style').css('overflow','hidden');
		contentPanel.panel({
					border : false,
					doSize : false
				});
		return contentPanel;
	};
	function buildDialog(target) {
		var opts = $.data(target, 'dialog').options;
		var contentPanel = $.data(target, 'dialog').contentPanel;
		if (opts.toolbar) {
			if(typeof opts.toolbar=='string'){
				$(opts.toolbar).addClass('dialog-toolbar').prependTo(target);
				$(opts.toolbar).show();
			}else{
				$(target).find('div.dialog-toolbar').remove();
				var toolbar = $('<div class="dialog-toolbar"></div>').prependTo(target);
				for (var i = 0; i < opts.toolbar.length; i++) {
					var p = opts.toolbar[i];
					if (p == '-') {
						toolbar.append('<div class="dialog-tool-separator"></div>');
					} else {
						var tool = $('<a href="javascript:void(0)"></a>')
								.appendTo(toolbar);
						tool.css('float', 'left');
						tool[0].onclick = eval(p.handler || function() {
						});
						tool.linkbutton($.extend({}, p, {
									plain : true
								}));
					}
				}
				toolbar.append('<div style="clear:both"></div>');
			}
		}else{
			//wcj comment
			//$(target).find('div.dialog-toolbar').remove();
		}
		if (opts.buttons) {
			if(typeof opts.buttons=='string'){
				$(opts.buttons).addClass('dialog-button').appendTo(target);
				$(opts).show();
			}else{
				$(target).find('div.dialog-button').remove();
				var buttons = $('<div class="dialog-button"></div>').appendTo(target);
				for (var i = 0; i < opts.buttons.length; i++) {
					var p = opts.buttons[i];
					var button = $('<a href="javascript:void(0)"></a>').appendTo(buttons);
					if (p.handler) {
						button[0].onclick = p.handler;
					}
					button.linkbutton(p);
				}
			}
		}else{
			//wcj comment
			//$(target).find('div.dialog-button').remove();
		}
		var href = opts.href;
		var content= opts.content;
		opts.href = null;
		opts.content=null;
		$(target).window($.extend({}, opts, {
					onOpen:function(){
						contentPanel.panel('open');
						if(opts.onOpen){
							opts.onOpen.call(target);
						}
					},
					onResize : function(width, height) {
						var wbody = $(target).panel('panel').find('>div.panel-body');
						contentPanel.panel('resize', {
									width : wbody.width(),
									height : (height == 'auto') ? 'auto' : wbody
											.height()
											- wbody.find('>div.dialog-toolbar')
													.outerHeight()
											- wbody.find('>div.dialog-button')
													.outerHeight()
								});
						if (opts.onResize) {
							opts.onResize.call(target, width, height);
						}
					}
				}));
		contentPanel.panel({
					closed:opts.closed,
					href : href,
					content:content,
					onLoad : function() {
						if (opts.height == 'auto') {
							$(target).window('resize');
						}
						opts.onLoad.apply(target, arguments);
					}
				});
		opts.href = href;
	};
	function refresh(target, href) {
		var contentPanel = $.data(target, 'dialog').contentPanel;
		contentPanel.panel('refresh', href);
	};
	$.fn.dialog = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.dialog.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.window(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'dialog');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'dialog', {
									options : $.extend({},
											$.fn.dialog.defaults, $.fn.dialog
													.parseOptions(this), options),
									contentPanel : wrapDialog(this)
								});
					}
					buildDialog(this);
				});
	};
	$.fn.dialog.methods = {
		options : function(jq) {
			var options = $.data(jq[0], 'dialog').options;
			var opts = jq.panel('options');
			$.extend(options, {
						closed : opts.closed,
						collapsed : opts.collapsed,
						minimized : opts.minimized,
						maximized : opts.maximized
					});
			var contentPanel = $.data(jq[0], 'dialog').contentPanel;
			return options;
		},
		dialog : function(jq) {
			return jq.window('window');
		},
		refresh : function(jq, href) {
			return jq.each(function() {
						refresh(this, href);
					});
		}
	};
	$.fn.dialog.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.window.parseOptions(target),
			{toolbar:t.attr('toolbar'),buttons:t.attr('buttons')});
	};
	$.fn.dialog.defaults = $.extend({}, $.fn.window.defaults, {
				title : 'New Dialog',
				collapsible : false,
				minimizable : false,
				maximizable : false,
				resizable : false,
				toolbar : null,
				buttons : null
			});
})(jQuery);

/**
 * messager - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	draggable
 * 	resizable
 * 	linkbutton
 * 	panel
 *  window
 */
(function($) {
	function show(el, type, speed, timeout) {
		var win = $(el).window('window');
		if (!win) {
			return;
		}
		switch (type) {
			case null :
				win.show();
				break;
			case 'slide' :
				win.slideDown(speed);
				break;
			case 'fade' :
				win.fadeIn(speed);
				break;
			case 'show' :
				win.show(speed);
				break;
		}
		var timer = null;
		if (timeout > 0) {
			timer = setTimeout(function() {
						hide(el, type, speed);
					}, timeout);
		}
		win.hover(function() {
					if (timer) {
						clearTimeout(timer);
					}
				}, function() {
					if (timeout > 0) {
						timer = setTimeout(function() {
									hide(el, type, speed);
								}, timeout);
					}
				});
	};
	function hide(el, type, speed) {
		if (el.locked == true) {
			return;
		}
		el.locked = true;
		var win = $(el).window('window');
		if (!win) {
			return;
		}
		switch (type) {
			case null :
				win.hide();
				break;
			case 'slide' :
				win.slideUp(speed);
				break;
			case 'fade' :
				win.fadeOut(speed);
				break;
			case 'show' :
				win.hide(speed);
				break;
		}
		setTimeout(function() {
					$(el).window('destroy');
				}, speed);
	};
	function createDialog(title, content, buttons) {
		var win = $('<div class="messager-body"></div>').appendTo('body');
		win.append(content);
		if (buttons) {
			var tb = $('<div class="messager-button"></div>').appendTo(win);
			for (var label in buttons) {
				//wcj: 
//				$('<a></a>').attr('href', 'javascript:void(0)').text(label).css(
//						'margin-left', 10).bind('click', eval(buttons[label]))
//						.appendTo(tb).linkbutton();
				$('<a></a>').attr('href', 'javascript:void(0)')
						.attr("key", buttons[label].key)
						.text(label).css(
						'margin-left', 10).bind('click', eval(buttons[label].handler))
						.appendTo(tb).linkbutton();
			}
		}
		win.window({
					title : title,
					noheader : (title?false:true),
					width : 300,
					height : 'auto',
					modal : true,
					collapsible : false,
					minimizable : false,
					maximizable : false,
					resizable : false,
					onClose : function() {
						setTimeout(function() {
									win.window('destroy');
								}, 100);
					}
				});
		win.window('window').addClass('messager-window');
		return win;
	};
	$.messager = {
		show : function(options) {
			var opts = $.extend({
						showType : 'slide',
						showSpeed : 600,
						width : 250,
						height : 100,
						msg : '',
						title : '',
						timeout : 4000
					}, options || {});
			var win = $('<div class="messager-body"></div>').html(opts.msg)
					.appendTo('body');
			win.window({
						title : opts.title,
						width : opts.width,
						height : opts.height,
						collapsible : false,
						minimizable : false,
						maximizable : false,
						shadow : false,
						draggable : false,
						resizable : false,
						closed : true,
						onBeforeOpen : function() {
							show(this, opts.showType, opts.showSpeed, opts.timeout);
							return false;
						},
						onBeforeClose : function() {
							hide(this, opts.showType, opts.showSpeed);
							return false;
						}
					});
			win.window('window').css({
				left : '',
				top : '',
				right : 0,
				zIndex : $.fn.window.defaults.zIndex++,
				bottom : -document.body.scrollTop
						- document.documentElement.scrollTop
			});
			win.window('open');
			//wcj
			return win;
		},
		alert : function(title, msg, icon, fn) {
			var content = '<div>' + msg + '</div>';
			switch (icon) {
				case 'error' :
					content = '<div class="messager-icon messager-error"></div>'
							+ content;
					break;
				case 'info' :
					content = '<div class="messager-icon messager-info"></div>'
							+ content;
					break;
				case 'question' :
					content = '<div class="messager-icon messager-question"></div>'
							+ content;
					break;
				case 'warning' :
					content = '<div class="messager-icon messager-warning"></div>'
							+ content;
					break;
			}
			content += '<div style="clear:both;"/>';
			var buttons = {};
			//wcj: useless
			/*
			buttons[$.messager.defaults.ok] = function() {
				win.dialog({
							closed : true
						});
				if (fn) {
					fn();
					return false;
				}
			};
			*/
			//wcj: 
			buttons[$.messager.defaults.ok] = {
					key : "O",
					handler : function() {
						win.window('close');
						if (fn) {
							fn();
							return false;
						}
					}
			};
			var win = createDialog(title, content, buttons);
			//wcj
			return win;
		},
		confirm : function(title, msg, fn) {
			var content = '<div class="messager-icon messager-question"></div>'
					+ '<div>' + msg + '</div>' + '<div style="clear:both;"/>';
			var buttons = {};
			//wcj: 
			buttons[$.messager.defaults.ok] = {
					key : "O",
					handler : function() {
						win.window('close');
						if (fn) {
							fn(true);
							return false;
						}
					}
			};
			buttons[$.messager.defaults.cancel] = {
					key : "I",
					handler : function() {
						win.window('close');
						if (fn) {
							fn(false);
							return false;
						}
					}
			};
			var win = createDialog(title, content, buttons);
			//wcj
			return win;
		},
		prompt : function(title, msg, fn) {
			var content = '<div class="messager-icon messager-question"></div>'
					+ '<div>' + msg + '</div>' + '<br/>'
					+ '<input class="messager-input" type="text"/>'
					+ '<div style="clear:both;"/>';
			var buttons = {};
			//wcj: 
			buttons[$.messager.defaults.ok] = {
					key : "O",
					handler : function() {
						win.window('close');
						if (fn) {
							fn($('.messager-input', win).val());
							return false;
						}
					}
			};
			buttons[$.messager.defaults.cancel] = {
					key : "I",
					handler : function() {
						win.window('close');
						if (fn) {
							fn();
							return false;
						}
					}
			};
			var win = createDialog(title, content, buttons);
			//wcj
			return win;
		},
		progress : function(options) {
			var opts = $.extend({
						title : '',
						msg : '',
						text : undefined,
						interval : 300
					}, options || {});
			var methods = {
				bar : function() {
					return $('body>div.messager-window')
							.find('div.messager-p-bar');
				},
				close : function() {
					var win = $('body>div.messager-window>div.messager-body');
					if (win.length) {
						if (win[0].timer) {
							clearInterval(win[0].timer);
						}
						win.window('close');
					}
				}
			};
			if (typeof options == 'string') {
				var method = methods[options];
				return method();
			}
			var pbar = '<div class="messager-progress"><div class="messager-p-msg"></div><div class="messager-p-bar"></div></div>';
			var win = createDialog(opts.title, pbar, null);
			win.find('div.messager-p-msg').html(opts.msg);
			var bar = win.find('div.messager-p-bar');
			bar.progressbar({
						text : opts.text
					});
			win.window({
						closable : false
					});
			if (opts.interval) {
				win[0].timer = setInterval(function() {
							var v = bar.progressbar('getValue');
							v += 10;
							if (v > 100) {
								v = 0;
							}
							bar.progressbar('setValue', v);
						}, opts.interval);
			}
			//wcj
			return win;
		}
	};
	$.messager.defaults = {
		ok : 'Ok',
		cancel : 'Cancel'
	};
})(jQuery);

/**
 * accordion - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 panel
 * 
 */
(function($) {
	function setSize(container) {
		var opts = $.data(container, 'accordion').options;
		var panels = $.data(container, 'accordion').panels;
		var cc = $(container);
		
		if (opts.fit == true) {
			var p = cc.parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		if (opts.width > 0) {
			cc.width($.boxModel == true ? (opts.width - (cc.outerWidth() - cc
					.width())) : opts.width);
		}
		var panelHeight = 'auto';
		if (opts.height > 0) {
			cc.height($.boxModel == true ? (opts.height - (cc.outerHeight() - cc
					.height())) : opts.height);
			// get the first panel's header height as all the header height
			var headerHeight = panels.length ? panels[0].panel('header').css('height', null)
					.outerHeight() : 'auto';
			var panelHeight = cc.height() - (panels.length - 1) * headerHeight;
		}
		for (var i = 0; i < panels.length; i++) {
			var panel = panels[i];
			var header = panel.panel('header');
			header.height($.boxModel == true ? (headerHeight - (header.outerHeight() - header
					.height())) : headerHeight);
			panel.panel('resize', {
						width : cc.width(),
						height : panelHeight
					});
		}
	};
	function getCurrent(container) {
		var panels = $.data(container, 'accordion').panels;
		for (var i = 0; i < panels.length; i++) {
			var panel = panels[i];
			if (panel.panel('options').collapsed == false) {
				return panel;
			}
		}
		return null;
	};
	function getPanel(container, title, pop) {
		var panels = $.data(container, 'accordion').panels;
		for (var i = 0; i < panels.length; i++) {
			var panel = panels[i];
			if (panel.panel('options').title == title) {
				if (pop) {
					panels.splice(i, 1);
				}
				return panel;
			}
		}
		return null;
	};
	function wrapAccordion(container) {
		var cc = $(container);
		cc.addClass('accordion');
		if (cc.attr('border') == 'false') {
			cc.addClass('accordion-noborder');
		} else {
			cc.removeClass('accordion-noborder');
		}
		var selected=cc.children('div[selected]');
		cc.children('div').not(selected).attr('collapsed', 'true');
		if (selected.length == 0) {
			cc.children('div:first').attr('collapsed', 'false');
		}
		var panels = [];
		cc.children('div').each(function() {
					var pp = $(this);
					panels.push(pp);
					init(container, pp, {});
				});
		cc.bind('_resize', function(e, param) {
					var opts = $.data(container, 'accordion').options;
					if (opts.fit == true || param) {
						setSize(container);
					}
					return false;
				});
		return {
			accordion : cc,
			panels : panels
		};
	};
	function init(container, pp, options) {
		pp.panel($.extend({}, options, {
			collapsible : false,
			minimizable : false,
			maximizable : false,
			closable : false,
			doSize : false,
			collapsed : pp.attr('selected') != 'true',
			tools : [{
						iconCls : 'accordion-collapse',
						handler : function() {
							var opts = $.data(container, 'accordion').options.animate;
							if (pp.panel('options').collapsed) {
								stop(container);
								pp.panel('expand', opts);
							} else {
								stop(container);
								pp.panel('collapse', opts);
							}
							return false;
						}
					}],
			onBeforeExpand : function() {
				var currentPanel = getCurrent(container);
				if (currentPanel) {
					var header = $(currentPanel).panel('header');
					header.removeClass('accordion-header-selected');
					header.find('.accordion-collapse').triggerHandler('click');
				}
				var header = pp.panel('header');
				header.addClass('accordion-header-selected');
				header.find('div.accordion-collapse')
						.removeClass('accordion-expand');
			},
			onExpand : function() {
				var opts = $.data(container, 'accordion').options;
				opts.onSelect.call(container, pp.panel('options').title);
			},
			onBeforeCollapse : function() {
				var header = pp.panel('header');
				header.removeClass('accordion-header-selected');
				header.find('div.accordion-collapse').addClass('accordion-expand');
			}
		}));
		pp.panel('body').addClass('accordion-body');
		pp.panel('header').addClass('accordion-header').click(function() {
					$(this).find('.accordion-collapse').triggerHandler('click');
					return false;
				});
	};
	function select(container, title) {
		var opts = $.data(container, 'accordion').options;
		var panels = $.data(container, 'accordion').panels;
		var currentPanel = getCurrent(container);
		if (currentPanel && currentPanel.panel('options').title == title) {
			return;
		}
		var panel = getPanel(container, title);
		if (panel) {
			panel.panel('header').triggerHandler('click');
		} else {
			if (currentPanel) {
				currentPanel.panel('header').addClass('accordion-header-selected');
				opts.onSelect.call(container, currentPanel.panel('options').title);
			}
		}
	};
	function stop(target){
		var panels=$.data(target,'accordion').panels;
		for(var i=0;i<panels.length;i++){
			panels[i].stop(true,true);
		}
	};
	function add(container, options) {
		var opts = $.data(container, 'accordion').options;
		var panels = $.data(container, 'accordion').panels;
		stop(container);
		var pp = $('<div></div>').appendTo(container);
		panels.push(pp);
		init(container, pp, options);
		setSize(container);
		opts.onAdd.call(container, options.title);
		select(container, options.title);
	};
	function remove(container, title) {
		var opts = $.data(container, 'accordion').options;
		var panels = $.data(container, 'accordion').panels;
		stop(container);
		if (opts.onBeforeRemove.call(container, title) == false) {
			return;
		}
		var panel = getPanel(container, title, true);
		if (panel) {
			panel.panel('destroy');
			if (panels.length) {
				setSize(container);
				var currentPanel = getCurrent(container);
				if (!currentPanel) {
					select(container, panels[0].panel('options').title);
				}
			}
		}
		opts.onRemove.call(container, title);
	};
	$.fn.accordion = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.accordion.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'accordion');
					var opts;
					if (state) {
						opts = $.extend(state.options, options);
						state.opts = opts;
					} else {
						opts = $.extend({}, $.fn.accordion.defaults,
								$.fn.accordion.parseOptions(this), options);
						var r = wrapAccordion(this);
						$.data(this, 'accordion', {
									options : opts,
									accordion : r.accordion,
									panels : r.panels
								});
					}
					setSize(this);
					select(this);
				});
	};
	$.fn.accordion.methods = {
		options : function(jq) {
			return $.data(jq[0], 'accordion').options;
		},
		panels : function(jq) {
			return $.data(jq[0], 'accordion').panels;
		},
		resize : function(jq) {
			return jq.each(function() {
						setSize(this);
					});
		},
		getSelected : function(jq) {
			return getCurrent(jq[0]);
		},
		getPanel : function(jq, title) {
			return getPanel(jq[0], title);
		},
		select : function(jq, title) {
			return jq.each(function() {
						select(this, title);
					});
		},
		add : function(jq, options) {
			return jq.each(function() {
						add(this, options);
					});
		},
		remove : function(jq, title) {
			return jq.each(function() {
						remove(this, title);
					});
		}
	};
	$.fn.accordion.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			height : (parseInt(target.style.height) || undefined),
			fit : (t.attr('fit') ? t.attr('fit') == 'true' : undefined),
			border : (t.attr('border') ? t.attr('border') == 'true' : undefined),
			animate : (t.attr('animate')
					? t.attr('animate') == 'true'
					: undefined)
		};
	};
	$.fn.accordion.defaults = {
		width : 'auto',
		height : 'auto',
		fit : false,
		border : true,
		animate : true,
		onSelect : function(title) {
		},
		onAdd : function(title) {
		},
		onBeforeRemove : function(title) {
		},
		onRemove : function(title) {
		}
	};
})(jQuery);

/**
 * tabs - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	function getMaxScrollWidth(container) {
		var header = $('>div.tabs-header', container);
		var tabsWidth = 0;
		$('ul.tabs li', header).each(function() {
					tabsWidth += $(this).outerWidth(true);
				});
		var wrapWidth = $('div.tabs-wrap', header).width();
		var padding = parseInt($('ul.tabs', header).css('padding-left'));
		return tabsWidth - wrapWidth + padding;
	};
	function setScrollers(container) {
		var opts = $.data(container, 'tabs').options;
		var header = $(container).children('div.tabs-header');
		var tool = header.children('div.tabs-tool');
		var leftScroller = header.children('div.tabs-scroller-left');
		var rightScroller = header.children('div.tabs-scroller-right');
		var wrap = header.children('div.tabs-wrap');
		var height = ($.boxModel == true
				? (header.outerHeight() - (tool.outerHeight() - tool.height()))
				: header.outerHeight());
		if (opts.plain) {
			height -= 2;
		}
		tool.height(height);
		var fullWidth = 0;
		$('ul.tabs li', header).each(function() {
					fullWidth += $(this).outerWidth(true);
				});
		var realWidth = header.width() - tool.outerWidth();
		if (fullWidth > realWidth) {
			leftScroller.show();
			rightScroller.show();
			tool.css('right', rightScroller.outerWidth());
			wrap.css({
						marginLeft : leftScroller.outerWidth(),
						marginRight : rightScroller.outerWidth() + tool.outerWidth(),
						left : 0,
						width : realWidth - leftScroller.outerWidth() - rightScroller.outerWidth()
					});
		} else {
			leftScroller.hide();
			rightScroller.hide();
			tool.css('right', 0);
			wrap.css({
						marginLeft : 0,
						marginRight : tool.outerWidth(),
						left : 0,
						width : realWidth
					});
			wrap.scrollLeft(0);
		}
	};
	function buildButton(container) {
		var opts = $.data(container, 'tabs').options;
		var header = $(container).children('div.tabs-header');
		var tool = header.children('div.tabs-tool');
		tool.remove();
		if (opts.tools) {
			tool = $('<div class="tabs-tool"></div>').appendTo(header);
			for (var i = 0; i < opts.tools.length; i++) {
				var button = $('<a href="javascript:void(0);"></a>')
						.appendTo(tool);
				button[0].onclick = eval(opts.tools[i].handler || function() {
				});
				button.linkbutton($.extend({}, opts.tools[i], {
							plain : true
						}));
			}
		}
	};
	function setSize(container) {
		var opts = $.data(container, 'tabs').options;
		var cc = $(container);
		//wcj: 
		if (! cc.is(":visible")) {
			return;
		}
		if (opts.fit == true) {
			var p = cc.parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		cc.width(opts.width).height(opts.height);
		var header = $('>div.tabs-header', container);
		if ($.boxModel == true) {
			header.width(opts.width - (header.outerWidth() - header.width()));
		} else {
			header.width(opts.width);
		}
		setScrollers(container);
		var panels = $('>div.tabs-panels', container);
		var height = opts.height;
		if (!isNaN(height)) {
			//wcj:
//			if ($.boxModel == true) {
//				var borderHeight = panels.outerHeight() - panels.height();
//				panels.css('height', (height - header.outerHeight() - borderHeight) || 'auto');
//			} else {
//				panels.css('height', height - header.outerHeight());
//			}
			if ($.boxModel == true) {
				var borderHeight = panels.outerHeight() - panels.height();
				panels.css('height', (height - (opts.showHeader ? header.outerHeight() : 0) - borderHeight) || 'auto');
			} else {
				panels.css('height', height - (opts.showHeader ? header.outerHeight() : 0));
			}
		} else {
			panels.height('auto');
		}
		var width = opts.width;
		if (!isNaN(width)) {
			if ($.boxModel == true) {
				panels.width(width - (panels.outerWidth() - panels.width()));
			} else {
				panels.width(width);
			}
		} else {
			panels.width('auto');
		}
	};
	function fitContent(container) {
		var opts = $.data(container, 'tabs').options;
		var tab = getSelected(container);
		//wcj: 
		//if (tab) {
		if (tab && tab.is(":visible")) {
			var panel = $(container).find('>div.tabs-panels');
			var width = opts.width == 'auto' ? 'auto' : panel.width();
			var height = opts.height == 'auto' ? 'auto' : panel.height();
			//wcj: don't resize if no need
			if (width != tab.width() || height != tab.height()) {
				tab.panel('resize', {
					width : width,
					height : height
				});
			}
		}
	};
	function wrapTabs(container) {
		var cc = $(container);
		cc.addClass('tabs-container');
		cc.wrapInner('<div class="tabs-panels"/>');
		$('<div class="tabs-header">'
				+ '<div class="tabs-scroller-left"></div>'
				+ '<div class="tabs-scroller-right"></div>'
				+ '<div class="tabs-wrap">' + '<ul class="tabs"></ul>'
				+ '</div>' + '</div>').prependTo(container);
		var tabs = [];
		var tp = cc.children('div.tabs-panels');
		tp.children('div[selected]').attr('closed', 'false');
		tp.children('div').not('div[selected]').attr('closed', 'true');
		tp.children('div').each(function() {
					var pp = $(this);
					tabs.push(pp);
					createTab(container, pp);
				});
		cc.children('div.tabs-header').find('.tabs-scroller-left, .tabs-scroller-right').hover(function(){
					$(this).addClass('tabs-scroller-over');
				}, function() {
					$(this).removeClass('tabs-scroller-over');
				});
		cc.bind('_resize', function(e, param) {
					var opts = $.data(container, 'tabs').options;
					if (opts.fit == true || param) {
						setSize(container);
						fitContent(container);
					}
					return false;
				});
		return tabs;
	};
	function setProperties(container) {
		var opts = $.data(container, 'tabs').options;
		var header = $('>div.tabs-header', container);
		var panels = $('>div.tabs-panels', container);
		//wcj:
		if (! opts.showHeader) {
			header.hide();
		}
		if (opts.plain == true) {
			header.addClass('tabs-header-plain');
		} else {
			header.removeClass('tabs-header-plain');
		}
		if (opts.border == true) {
			header.removeClass('tabs-header-noborder');
			panels.removeClass('tabs-panels-noborder');
		} else {
			header.addClass('tabs-header-noborder');
			panels.addClass('tabs-panels-noborder');
		}
		$('.tabs-scroller-left', header).unbind('.tabs').bind('click.tabs',
				function() {
					var wrap = $('.tabs-wrap', header);
					var pos = wrap.scrollLeft() - opts.scrollIncrement;
					wrap.animate({
								scrollLeft : pos
							}, opts.scrollDuration);
				});
		$('.tabs-scroller-right', header).unbind('.tabs').bind('click.tabs',
				function() {
					var wrap = $('.tabs-wrap', header);
					var pos = Math.min(wrap.scrollLeft() + opts.scrollIncrement,
							getMaxScrollWidth(container));
					wrap.animate({
								scrollLeft : pos
							}, opts.scrollDuration);
				});
		var tabs = $.data(container, 'tabs').tabs;
		for (var i = 0, len = tabs.length; i < len; i++) {
			var tabPanel = tabs[i];
			var tab = tabPanel.panel('options').tab;
			var title = tabPanel.panel('options').title;
			tab.unbind('.tabs').bind('click.tabs', {
						title : title
					}, function(e) {
						selectTab(container, e.data.title);
					}).bind('contextmenu.tabs', {
						title : title
					}, function(e) {
						opts.onContextMenu.call(container, e, e.data.title);
					});
			tab.find('a.tabs-close').unbind('.tabs').bind('click.tabs', {
						title : title
					}, function(e) {
						closeTab(container, e.data.title);
						return false;
					});
		}
	};
	function createTab(container, pp, options) {
		options = options || {};
		pp.panel($.extend({}, options, {
					border : false,
					noheader : true,
					closed : true,
					doSize : false,
					iconCls : (options.icon ? options.icon : undefined),
					onLoad : function() {
						$.data(container, 'tabs').options.onLoad.call(container, pp);
					}
				}));
		var opts = pp.panel('options');
		var header = $('>div.tabs-header', container);
		var tabs = $('ul.tabs', header);
		var tab = $('<li></li>').appendTo(tabs);
		var tabInner = $('<a href="javascript:void(0)" class="tabs-inner"></a>')
				.appendTo(tab);
		var tabTitle = $('<span class="tabs-title"></span>').html(opts.title)
				.appendTo(tabInner);
		//wcj: key support
		var key = pp.attr("key");
		if (key) {
			key = key.toUpperCase();
			tabInner.children(".tabs-title").html(opts.title + 
					" <b>(<u>" + key + "</u>)</b>");
			tabInner.attr("key", key);
		}
		var tabIcon = $('<span class="tabs-icon"></span>').appendTo(tabInner);
		if (opts.closable) {
			tabTitle.addClass('tabs-closable');
			//wcj: add x for boot css×
			//$('<a href="javascript:void(0)" class="tabs-close"></a>')
			$('<a href="javascript:void(0)" class="tabs-close">×</a>')
					.appendTo(tab);
		}
		if (opts.iconCls) {
			tabTitle.addClass('tabs-with-icon');
			tabIcon.addClass(opts.iconCls);
		}
		//wcj:
		if (opts.iconUrl) {
			tabTitle.addClass('tabs-with-icon');
			tabIcon.append("<img style='border:0;' src='" + opts.iconUrl + "'/>");
		}
		opts.tab = tab;
	};
	function addTab(container, options) {
		var opts = $.data(container, 'tabs').options;
		var tabs = $.data(container, 'tabs').tabs;
		var pp = $('<div></div>').appendTo($('>div.tabs-panels', container));
		tabs.push(pp);
		createTab(container, pp, options);
		opts.onAdd.call(container, options.title);
		setScrollers(container);
		setProperties(container);
		selectTab(container, options.title);
	};
	function update(container, param) {
		var selectHis = $.data(container, 'tabs').selectHis;
		var pp = param.tab;
		var title = pp.panel('options').title;
		pp.panel($.extend({}, param.options, {
					iconCls : (param.options.icon ? param.options.icon : undefined)
				}));
		var opts = pp.panel('options');
		var tab = opts.tab;
		tab.find('span.tabs-icon').attr('class', 'tabs-icon');
		tab.find('a.tabs-close').remove();
		tab.find('span.tabs-title').html(opts.title);
		if (opts.closable) {
			tab.find('span.tabs-title').addClass('tabs-closable');
			//wcj: add x for boot css
			//$('<a href="javascript:void(0)" class="tabs-close"></a>')
			$('<a href="javascript:void(0)" class="tabs-close">×</a>')
					.appendTo(tab);
		} else {
			tab.find('span.tabs-title').removeClass('tabs-closable');
		}
		if (opts.iconCls) {
			tab.find('span.tabs-title').addClass('tabs-with-icon');
			tab.find('span.tabs-icon').addClass(opts.iconCls);
		} else {
			tab.find('span.tabs-title').removeClass('tabs-with-icon');
		}
		//wcj:
		if (opts.iconUrl) {
			tab.find('span.tabs-title').addClass('tabs-with-icon');
		}
		if (title != opts.title) {
			for (var i = 0; i < selectHis.length; i++) {
				if (selectHis[i] == title) {
					selectHis[i] = opts.title;
				}
			}
		}
		setProperties(container);
		$.data(container, 'tabs').options.onUpdate.call(container, opts.title);
	};
	function closeTab(container, title) {
		var opts = $.data(container, 'tabs').options;
		var tabs = $.data(container, 'tabs').tabs;
		var selectHis = $.data(container, 'tabs').selectHis;
		if (!exists(container, title)) {
			return;
		}
		if (opts.onBeforeClose.call(container, title) == false) {
			return;
		}
		var tab = getTab(container, title, true);
		tab.panel('options').tab.remove();
		tab.panel('destroy');
		opts.onClose.call(container, title);
		setScrollers(container);
		for (var i = 0; i < selectHis.length; i++) {
			if (selectHis[i] == title) {
				selectHis.splice(i, 1);
				i--;
			}
		}
		var lastTab = selectHis.pop();
		if (lastTab) {
			selectTab(container, lastTab);
		} else {
			if (tabs.length) {
				selectTab(container, tabs[0].panel('options').title);
			}
		}
	};
	function getTab(container, title, close) {
		var tabs = $.data(container, 'tabs').tabs;
		for (var i = 0; i < tabs.length; i++) {
			var tab = tabs[i];
			if (tab.panel('options').title == title) {
				if (close) {
					tabs.splice(i, 1);
				}
				return tab;
			}
		}
		return null;
	};
	function getSelected(container) {
		var tabs = $.data(container, 'tabs').tabs;
		for (var i = 0; i < tabs.length; i++) {
			var tab = tabs[i];
			if (tab.panel('options').closed == false) {
				return tab;
			}
		}
		return null;
	};
	function initSelectTab(container) {
		var tabs = $.data(container, 'tabs').tabs;
		for (var i = 0; i < tabs.length; i++) {
			var tab = tabs[i];
			if (tab.panel('options').selected) {
				selectTab(container, tab.panel('options').title);
				return;
			}
		}
		if (tabs.length) {
			selectTab(container, tabs[0].panel('options').title);
		}
	};
	function selectTab(container, title) {
		//wcj:
		if (! $.data(container, 'tabs')) {
			return;
		}
		var opts = $.data(container, 'tabs').options;
		var tabs = $.data(container, 'tabs').tabs;
		var selectHis = $.data(container, 'tabs').selectHis;
		if (tabs.length == 0) {
			return;
		}
		var tab = getTab(container, title);
		if (!tab) {
			return;
		}
		var selected = getSelected(container);
		if (selected) {
			selected.panel('close');
			selected.panel('options').tab.removeClass('tabs-selected');
		}
		tab.panel('open');
		var tab = tab.panel('options').tab;
		tab.addClass('tabs-selected');
		var wrap = $(container).find('>div.tabs-header div.tabs-wrap');
		var leftPos = tab.position().left + wrap.scrollLeft();
		var left = leftPos - wrap.scrollLeft();
		var right = left + tab.outerWidth();
		if (left < 0 || right > wrap.innerWidth()) {
			var pos = Math.min(leftPos - (wrap.width() - tab.width()) / 2, getMaxScrollWidth(container));
			wrap.animate({
						scrollLeft : pos
					}, opts.scrollDuration);
		} else {
			var pos = Math.min(wrap.scrollLeft(), getMaxScrollWidth(container));
			wrap.animate({
						scrollLeft : pos
					}, opts.scrollDuration);
		}
		fitContent(container);
		selectHis.push(title);
		opts.onSelect.call(container, title);
	};
	function exists(container, title) {
		return getTab(container, title) != null;
	};
	$.fn.tabs = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.tabs.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'tabs');
					var opts;
					if (state) {
						opts = $.extend(state.options, options);
						state.options = opts;
					} else {
						$.data(this, 'tabs', {
									options : $.extend({}, $.fn.tabs.defaults,
											$.fn.tabs.parseOptions(this), options),
									tabs : wrapTabs(this),
									selectHis : []
								});
					}
					buildButton(this);
					setProperties(this);
					setSize(this);
					initSelectTab(this);
				});
	};
	$.fn.tabs.methods = {
		options : function(jq) {
			return $.data(jq[0], 'tabs').options;
		},
		tabs : function(jq) {
			return $.data(jq[0], 'tabs').tabs;
		},
		resize : function(jq) {
			return jq.each(function() {
						setSize(this);
						fitContent(this);
					});
		},
		add : function(jq, options) {
			return jq.each(function() {
						addTab(this, options);
					});
		},
		close : function(jq, title) {
			return jq.each(function() {
						closeTab(this, title);
					});
		},
		getTab : function(jq, title) {
			return getTab(jq[0], title);
		},
		getSelected : function(jq) {
			return getSelected(jq[0]);
		},
		select : function(jq, title) {
			return jq.each(function() {
						selectTab(this, title);
					});
		},
		exists : function(jq, title) {
			return exists(jq[0], title);
		},
		update : function(jq, param) {
			return jq.each(function() {
						update(this, param);
					});
		}
	};
	$.fn.tabs.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			height : (parseInt(target.style.height) || undefined),
			fit : (t.attr('fit') ? t.attr('fit') == 'true' : undefined),
			border : (t.attr('border') ? t.attr('border') == 'true' : undefined),
			plain : (t.attr('plain') ? t.attr('plain') == 'true' : undefined),
			//wcj:
			showHeader : (t.attr('showHeader') ? t.attr('showHeader') != 'false' : true)
		};
	};
	$.fn.tabs.defaults = {
		width : 'auto',
		height : 'auto',
		plain : false,
		fit : false,
		border : true,
		tools : null,
		scrollIncrement : 100,
		scrollDuration : 400,
		onLoad : function(panel) {
		},
		onSelect : function(title) {
		},
		onBeforeClose : function(title) {
		},
		onClose : function(title) {
		},
		onAdd : function(title) {
		},
		onUpdate : function(title) {
		},
		onContextMenu : function(e, title) {
		}
	};
})(jQuery);

/**
 * layout - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   resizable
 *   panel
 */
(function($) {
	var resizing = false;
	function setSize(container) {
		var opts = $.data(container, 'layout').options;
		var panels = $.data(container, 'layout').panels;
		var cc = $(container);
		if (opts.fit == true) {
			var p = cc.parent();
			cc.width(p.width()).height(p.height());
		}
		var cpos = {
			top : 0,
			left : 0,
			width : cc.width(),
			height : cc.height()
		};
		function setNorthSize(pp) {
			if (pp.length == 0) {
				return;
			}
			//wcj:
			var height = pp.panel('options').height;
			if (isNaN(height)) {
				height = pp.parent().height();
			}
			pp.panel('resize', {
						width : cc.width(),
						//wcj:
						//height : pp.panel('options').height,
						height : height,
						left : 0,
						top : 0
					});
			//wcj:
			//cpos.top += pp.panel('options').height;
			//cpos.height -= pp.panel('options').height;
			cpos.top += height;
			cpos.height -= height;
		};
		if (isVisible(panels.expandNorth)) {
			setNorthSize(panels.expandNorth);
		} else {
			setNorthSize(panels.north);
		}
		function setSouthSize(pp) {
			if (pp.length == 0) {
				return;
			}
			//wcj:
			var height = pp.panel('options').height;
			if (isNaN(height)) {
				height = pp.parent().height();
			}
			pp.panel('resize', {
						width : cc.width(),
						//wcj:
						//height : pp.panel('options').height,
						height : height,
						left : 0,
						//wcj:
						//top : cc.height() - pp.panel('options').height
						top : cc.height() - height
					});
			//wcj:
			//cpos.height -= pp.panel('options').height;
			cpos.height -= height;
		};
		if (isVisible(panels.expandSouth)) {
			setSouthSize(panels.expandSouth);
		} else {
			setSouthSize(panels.south);
		}
		function setEastSize(pp) {
			if (pp.length == 0) {
				return;
			}
			//wcj:
			var width = pp.panel('options').width;
			if (isNaN(width)) {
				width = pp.parent().width();
			}
			pp.panel('resize', {
						//wcj:
						//width : pp.panel('options').width,
						width : width,
						height : cpos.height,
						//wcj:
						//left : cc.width() - pp.panel('options').width,
						left : cc.width() - width,
						top : cpos.top
					});
			//wcj:
			//cpos.width -= pp.panel('options').width;
			cpos.width -= width;
		};
		if (isVisible(panels.expandEast)) {
			setEastSize(panels.expandEast);
		} else {
			setEastSize(panels.east);
		}
		function setWestSize(pp) {
			if (pp.length == 0) {
				return;
			}
			//wcj:
			var width = pp.panel('options').width;
			if (isNaN(width)) {
				width = pp.parent().width();
			}
			pp.panel('resize', {
						//wcj:
						//width : pp.panel('options').width,
						width : width,
						height : cpos.height,
						left : 0,
						top : cpos.top
					});
			//wcj:
			//cpos.left += pp.panel('options').width;
			//cpos.width -= pp.panel('options').width;
			cpos.left += width;
			cpos.width -= width;
		};
		if (isVisible(panels.expandWest)) {
			setWestSize(panels.expandWest);
		} else {
			setWestSize(panels.west);
		}
		panels.center.panel('resize', cpos);
	};
	function init(container) {
		var cc = $(container);
		if (cc[0].tagName == 'BODY') {
			$('html').css({
						height : '100%',
						overflow : 'hidden'
					});
			$('body').css({
						height : '100%',
						overflow : 'hidden',
						border : 'none'
					});
		}
		cc.addClass('layout');
		cc.css({
					margin : 0,
					padding : 0
				});
		function createPanel(dir) {
			var pp = $('>div[region=' + dir + ']', container).addClass('layout-body');
			var toolCls = null;
			if (dir == 'north') {
				toolCls = 'layout-button-up';
			} else {
				if (dir == 'south') {
					toolCls = 'layout-button-down';
				} else {
					if (dir == 'east') {
						toolCls = 'layout-button-right';
					} else {
						if (dir == 'west') {
							toolCls = 'layout-button-left';
						}
					}
				}
			}
			var cls = 'layout-panel layout-panel-' + dir;
			if (pp.attr('split') == 'true') {
				cls += ' layout-split-' + dir;
			}
			pp.panel({
						cls : cls,
						doSize : false,
						border : (pp.attr('border') == 'false' ? false : true),
						//wcj: width and height now correct here, cause header and border not added yet
//						width : (pp.length ? parseInt(pp[0].style.width)
//								|| pp.outerWidth() : 'auto'),
//						height : (pp.length ? parseInt(pp[0].style.height)
//								|| pp.outerHeight() : 'auto'),
						width : (pp.length ? parseInt(pp[0].style.width)
								|| 'auto' : 'auto'),
						height : (pp.length ? parseInt(pp[0].style.height)
								|| 'auto' : 'auto'),
						tools : [{
									iconCls : toolCls,
									handler : function() {
										collapsePanel(container, dir);
									}
								}]
					});
			if (pp.attr('split') == 'true') {
				var panel = pp.panel('panel');
				var handles = '';
				if (dir == 'north') {
					handles = 's';
				}
				if (dir == 'south') {
					handles = 'n';
				}
				if (dir == 'east') {
					handles = 'w';
				}
				if (dir == 'west') {
					handles = 'e';
				}
				panel.resizable({
					handles : handles,
					onStartResize : function(e) {
						resizing = true;
						if (dir == 'north' || dir == 'south') {
							var proxy = $('>div.layout-split-proxy-v', container);
						} else {
							var proxy = $('>div.layout-split-proxy-h', container);
						}
						var top = 0, left = 0, width = 0, height = 0;
						var pos = {
							display : 'block'
						};
						if (dir == 'north') {
							pos.top = parseInt(panel.css('top'))
									+ panel.outerHeight() - proxy.height();
							pos.left = parseInt(panel.css('left'));
							pos.width = panel.outerWidth();
							pos.height = proxy.height();
						} else {
							if (dir == 'south') {
								pos.top = parseInt(panel.css('top'));
								pos.left = parseInt(panel.css('left'));
								pos.width = panel.outerWidth();
								pos.height = proxy.height();
							} else {
								if (dir == 'east') {
									pos.top = parseInt(panel.css('top')) || 0;
									pos.left = parseInt(panel.css('left')) || 0;
									pos.width = proxy.width();
									pos.height = panel.outerHeight();
								} else {
									if (dir == 'west') {
										pos.top = parseInt(panel.css('top')) || 0;
										pos.left = panel.outerWidth()
												- proxy.width();
										pos.width = proxy.width();
										pos.height = panel.outerHeight();
									}
								}
							}
						}
						proxy.css(pos);
						$('<div class="layout-mask"></div>').css({
									left : 0,
									top : 0,
									width : cc.width(),
									height : cc.height()
								}).appendTo(cc);
					},
					onResize : function(e) {
						if (dir == 'north' || dir == 'south') {
							var proxy = $('>div.layout-split-proxy-v', container);
							proxy.css('top', e.pageY - $(container).offset().top
											- proxy.height() / 2);
						} else {
							var proxy = $('>div.layout-split-proxy-h', container);
							proxy.css('left', e.pageX - $(container).offset().left
											- proxy.width() / 2);
						}
						return false;
					},
					onStopResize : function() {
						$('>div.layout-split-proxy-v', container).css('display',
								'none');
						$('>div.layout-split-proxy-h', container).css('display',
								'none');
						var opts = pp.panel('options');
						opts.width = panel.outerWidth();
						opts.height = panel.outerHeight();
						opts.left = panel.css('left');
						opts.top = panel.css('top');
						pp.panel('resize');
						setSize(container);
						resizing = false;
						cc.find('>div.layout-mask').remove();
					}
				});
			}
			return pp;
		};
		$('<div class="layout-split-proxy-h"></div>').appendTo(cc);
		$('<div class="layout-split-proxy-v"></div>').appendTo(cc);
		var panels = {
			center : createPanel('center')
		};
		panels.north = createPanel('north');
		panels.south = createPanel('south');
		panels.east = createPanel('east');
		panels.west = createPanel('west');
		$(container).bind('_resize', function(e, param) {
					var opts = $.data(container, 'layout').options;
					if (opts.fit == true || param) {
						setSize(container);
					}
					return false;
				});
		return panels;
	};
	function collapsePanel(container, region) {
		var panels = $.data(container, 'layout').panels;
		var cc = $(container);
		function createExpandPanel(dir) {
			var icon;
			if (dir == 'east') {
				icon = 'layout-button-left';
			} else {
				if (dir == 'west') {
					icon = 'layout-button-right';
				} else {
					if (dir == 'north') {
						icon = 'layout-button-down';
					} else {
						if (dir == 'south') {
							icon = 'layout-button-up';
						}
					}
				}
			}
			var p = $('<div></div>').appendTo(cc).panel({
						cls : 'layout-expand',
						title : '&nbsp;',
						closed : true,
						doSize : false,
						tools : [{
									iconCls : icon,
									handler : function() {
										expandPanel(container, region);
									}
								}]
					});
			p.panel('panel').hover(function() {
						$(this).addClass('layout-expand-over');
					}, function() {
						$(this).removeClass('layout-expand-over');
					});
			return p;
		};
		if (region == 'east') {
			if (panels.east.panel('options').onBeforeCollapse.call(panels.east) == false) {
				return;
			}
			panels.center.panel('resize', {
						width : panels.center.panel('options').width
								+ panels.east.panel('options').width - 28
					});
			panels.east.panel('panel').animate({
						left : cc.width()
					}, function() {
						panels.east.panel('close');
						panels.expandEast.panel('open').panel('resize', {
									top : panels.east.panel('options').top,
									left : cc.width() - 28,
									width : 28,
									height : panels.east.panel('options').height
								});
						panels.east.panel('options').onCollapse.call(panels.east);
					});
			if (!panels.expandEast) {
				panels.expandEast = createExpandPanel('east');
				panels.expandEast.panel('panel').click(function() {
					panels.east.panel('open').panel('resize', {
								left : cc.width()
							});
					panels.east.panel('panel').animate({
								left : cc.width()
										- panels.east.panel('options').width
							});
					return false;
				});
			}
		} else {
			if (region == 'west') {
				if (panels.west.panel('options').onBeforeCollapse.call(panels.west) == false) {
					return;
				}
				panels.center.panel('resize', {
							width : panels.center.panel('options').width
									+ panels.west.panel('options').width - 28,
							left : 28
						});
				panels.west.panel('panel').animate({
							left : -panels.west.panel('options').width
						}, function() {
							panels.west.panel('close');
							panels.expandWest.panel('open').panel('resize', {
										top : panels.west.panel('options').top,
										left : 0,
										width : 28,
										height : panels.west.panel('options').height
									});
							panels.west.panel('options').onCollapse.call(panels.west);
						});
				if (!panels.expandWest) {
					panels.expandWest = createExpandPanel('west');
					panels.expandWest.panel('panel').click(function() {
								panels.west.panel('open').panel('resize', {
											left : -panels.west.panel('options').width
										});
								panels.west.panel('panel').animate({
											left : 0
										});
								return false;
							});
				}
			} else {
				if (region == 'north') {
					if (panels.north.panel('options').onBeforeCollapse
							.call(panels.north) == false) {
						return;
					}
					var hh = cc.height() - 28;
					if (isVisible(panels.expandSouth)) {
						hh -= panels.expandSouth.panel('options').height;
					} else {
						if (isVisible(panels.south)) {
							hh -= panels.south.panel('options').height;
						}
					}
					panels.center.panel('resize', {
								top : 28,
								height : hh
							});
					panels.east.panel('resize', {
								top : 28,
								height : hh
							});
					panels.west.panel('resize', {
								top : 28,
								height : hh
							});
					if (isVisible(panels.expandEast)) {
						panels.expandEast.panel('resize', {
									top : 28,
									height : hh
								});
					}
					if (isVisible(panels.expandWest)) {
						panels.expandWest.panel('resize', {
									top : 28,
									height : hh
								});
					}
					panels.north.panel('panel').animate({
								top : -panels.north.panel('options').height
							}, function() {
								panels.north.panel('close');
								panels.expandNorth.panel('open').panel('resize', {
											top : 0,
											left : 0,
											width : cc.width(),
											height : 28
										});
								panels.north.panel('options').onCollapse
										.call(panels.north);
							});
					if (!panels.expandNorth) {
						panels.expandNorth = createExpandPanel('north');
						panels.expandNorth.panel('panel').click(function() {
							panels.north.panel('open').panel('resize', {
										top : -panels.north.panel('options').height
									});
							panels.north.panel('panel').animate({
										top : 0
									});
							return false;
						});
					}
				} else {
					if (region == 'south') {
						if (panels.south.panel('options').onBeforeCollapse
								.call(panels.south) == false) {
							return;
						}
						var hh = cc.height() - 28;
						if (isVisible(panels.expandNorth)) {
							hh -= panels.expandNorth.panel('options').height;
						} else {
							if (isVisible(panels.north)) {
								hh -= panels.north.panel('options').height;
							}
						}
						panels.center.panel('resize', {
									height : hh
								});
						panels.east.panel('resize', {
									height : hh
								});
						panels.west.panel('resize', {
									height : hh
								});
						if (isVisible(panels.expandEast)) {
							panels.expandEast.panel('resize', {
										height : hh
									});
						}
						if (isVisible(panels.expandWest)) {
							panels.expandWest.panel('resize', {
										height : hh
									});
						}
						panels.south.panel('panel').animate({
									top : cc.height()
								}, function() {
									panels.south.panel('close');
									panels.expandSouth.panel('open').panel(
											'resize', {
												top : cc.height() - 28,
												left : 0,
												width : cc.width(),
												height : 28
											});
									panels.south.panel('options').onCollapse
											.call(panels.south);
								});
						if (!panels.expandSouth) {
							panels.expandSouth = createExpandPanel('south');
							panels.expandSouth.panel('panel').click(function() {
								panels.south.panel('open').panel('resize', {
											top : cc.height()
										});
								panels.south.panel('panel').animate({
									top : cc.height()
											- panels.south.panel('options').height
								});
								return false;
							});
						}
					}
				}
			}
		}
	};
	function expandPanel(container, region) {
		var panels = $.data(container, 'layout').panels;
		var cc = $(container);
		if (region == 'east' && panels.expandEast) {
			if (panels.east.panel('options').onBeforeExpand.call(panels.east) == false) {
				return;
			}
			panels.expandEast.panel('close');
			panels.east.panel('panel').stop(true, true);
			panels.east.panel('open').panel('resize', {
						left : cc.width()
					});
			panels.east.panel('panel').animate({
						left : cc.width() - panels.east.panel('options').width
					}, function() {
						setSize(container);
						panels.east.panel('options').onExpand.call(panels.east);
					});
		} else {
			if (region == 'west' && panels.expandWest) {
				if (panels.west.panel('options').onBeforeExpand.call(panels.west) == false) {
					return;
				}
				panels.expandWest.panel('close');
				panels.west.panel('panel').stop(true, true);
				panels.west.panel('open').panel('resize', {
							left : -panels.west.panel('options').width
						});
				panels.west.panel('panel').animate({
							left : 0
						}, function() {
							setSize(container);
							panels.west.panel('options').onExpand.call(panels.west);
						});
			} else {
				if (region == 'north' && panels.expandNorth) {
					if (panels.north.panel('options').onBeforeExpand
							.call(panels.north) == false) {
						return;
					}
					panels.expandNorth.panel('close');
					panels.north.panel('panel').stop(true, true);
					panels.north.panel('open').panel('resize', {
								top : -panels.north.panel('options').height
							});
					panels.north.panel('panel').animate({
								top : 0
							}, function() {
								setSize(container);
								panels.north.panel('options').onExpand
										.call(panels.north);
							});
				} else {
					if (region == 'south' && panels.expandSouth) {
						if (panels.south.panel('options').onBeforeExpand
								.call(panels.south) == false) {
							return;
						}
						panels.expandSouth.panel('close');
						panels.south.panel('panel').stop(true, true);
						panels.south.panel('open').panel('resize', {
									top : cc.height()
								});
						panels.south.panel('panel').animate({
							top : cc.height()
									- panels.south.panel('options').height
						}, function() {
							setSize(container);
							panels.south.panel('options').onExpand.call(panels.south);
						});
					}
				}
			}
		}
	};
	function bindEvents(container) {
		var panels = $.data(container, 'layout').panels;
		var cc = $(container);
		if (panels.east.length) {
			panels.east.panel('panel').bind('mouseover', 'east', collapsePanel);
		}
		if (panels.west.length) {
			panels.west.panel('panel').bind('mouseover', 'west', collapsePanel);
		}
		if (panels.north.length) {
			panels.north.panel('panel').bind('mouseover', 'north', collapsePanel);
		}
		if (panels.south.length) {
			panels.south.panel('panel').bind('mouseover', 'south', collapsePanel);
		}
		panels.center.panel('panel').bind('mouseover', 'center', collapsePanel);
		function collapsePanel(e) {
			if (resizing == true) {
				return;
			}
			if (e.data != 'east' && isVisible(panels.east) && isVisible(panels.expandEast)) {
				panels.east.panel('panel').animate({
							left : cc.width()
						}, function() {
							panels.east.panel('close');
						});
			}
			if (e.data != 'west' && isVisible(panels.west) && isVisible(panels.expandWest)) {
				panels.west.panel('panel').animate({
							left : -panels.west.panel('options').width
						}, function() {
							panels.west.panel('close');
						});
			}
			if (e.data != 'north' && isVisible(panels.north) && isVisible(panels.expandNorth)) {
				panels.north.panel('panel').animate({
							top : -panels.north.panel('options').height
						}, function() {
							panels.north.panel('close');
						});
			}
			if (e.data != 'south' && isVisible(panels.south) && isVisible(panels.expandSouth)) {
				panels.south.panel('panel').animate({
							top : cc.height()
						}, function() {
							panels.south.panel('close');
						});
			}
			return false;
		};
	};
	function isVisible(pp) {
		if (!pp) {
			return false;
		}
		if (pp.length) {
			return pp.panel('panel').is(':visible');
		} else {
			return false;
		}
	};
	$.fn.layout = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.layout.methods[options](this, param);
		}
		return this.each(function() {
					var state = $.data(this, 'layout');
					if (!state) {
						var opts = $.extend({}, {
									fit : $(this).attr('fit') == 'true'
								});
						$.data(this, 'layout', {
									options : opts,
									panels : init(this)
								});
						bindEvents(this);
					}
					setSize(this);
				});
	};
	$.fn.layout.methods = {
		resize : function(jq) {
			return jq.each(function() {
						setSize(this);
					});
		},
		panel : function(jq, param) {
			return $.data(jq[0], 'layout').panels[param];
		},
		collapse : function(jq, param) {
			return jq.each(function() {
						collapsePanel(this, param);
					});
		},
		expand : function(jq, param) {
			return jq.each(function() {
						expandPanel(this, param);
					});
		}
	};
})(jQuery);

/**
 * menu - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	function init(target) {
		//wcj: inline
		//$(target).appendTo('body');
		$(target).addClass('menu-top');
		var menus = [];
		adjust($(target));
		var timer = null;
		for (var i = 0; i < menus.length; i++) {
			var menu = menus[i];
			wrapMenu(menu);
			menu.children('div.menu-item').each(function() {
						bindMenuItemEvent(target, $(this));
					});
			//wcj: timeout 500
			menu.bind('mouseenter', function() {
						if (timer) {
							clearTimeout(timer);
							timer = null;
						}
					}).bind('mouseleave', function() {
						timer = setTimeout(function() {
									hideAll(target);
								}, 500);
					});
		}
		function adjust(menu) {
			menus.push(menu);
			menu.find('>div').each(function() {
						var item = $(this);
						var submenu = item.find('>div');
						if (submenu.length) {
							submenu.insertAfter(target);
							item[0].submenu = submenu;
							adjust(submenu);
						}
					});
		};
		function wrapMenu(menu) {
			menu.addClass('menu').find('>div').each(function() {
				var item = $(this);
				if (item.hasClass('menu-sep')) {
					item.html('&nbsp;');
				} else {
					var text = item.addClass('menu-item').html();
					item.empty().append($('<div class="menu-text"></div>')
							.html(text));
					var icon = item.attr('iconCls') || item.attr('icon');
					if (icon) {
						$('<div class="menu-icon"></div>').addClass(icon)
								.appendTo(item);
					}
					if (item[0].submenu) {
						$('<div class="menu-rightarrow"></div>').appendTo(item);
					}
					//wcj: comment
					/*
					if ($.boxModel == true) {
						var height = item.height();
						item.height(height - (item.outerHeight() - item.height()));
					}
					*/
				}
			});
			menu.hide();
		};
	};
	function bindMenuItemEvent(target, item) {
		item.unbind('.menu');
		item.bind('mousedown.menu', function() {
					return false;
				}).bind('click.menu', function() {
					if ($(this).hasClass('menu-item-disabled')) {
						return;
					}
					//wcj: don't hide menu if onClick returns false
					var item = $(target).menu('getItem', this);
					var hideMenu = $.data(target, 'menu').options.onClick.call(target, item);
					if (!this.submenu) {
						//wcj:
						if (hideMenu != false) {
							hideAll(target);
						}
						var href = $(this).attr('href');
						if (href) {
							location.href = href;
						}
					}
					/*
					var item = $(target).menu('getItem', this);
					$.data(target, 'menu').options.onClick.call(target, item);
					*/
				});
		item.bind('mouseenter.menu',function(e){
					item.siblings().each(function() {
								if (this.submenu) {
									hideMenu(this.submenu);
								}
								//wcj: use css hover
//								$(this).removeClass('menu-active');
							});
					//wcj: use css hover
//					item.addClass('menu-active');
					if ($(this).hasClass('menu-item-disabled')) {
						//wcj: use css hover
//						item.addClass('menu-active-disabled');
						return;
					}
					var submenu = item[0].submenu;
					if (submenu) {
						var left = item.offset().left + item.outerWidth() - 2;
						if (left + submenu.outerWidth()+5 > $(window).width()+$(document).scrollLeft()) {
							left = item.offset().left - submenu.outerWidth() + 2;
						}
						var top=item.offset().top-3;
						if(top+submenu.outerHeight()>$(window).height()+$(document).scrollTop()){
							top=$(window).height()+$(document).scrollTop()-submenu.outerHeight()-5;
						}
						showMenu(submenu, {
									left : left,
									top : top
								});
					}
		}).bind("mouseleave.menu",function(e){
			//wcj: use css hover
//			item.removeClass('menu-active menu-active-disabled');
			var submenu = item[0].submenu;
			if (submenu) {
				if (e.pageX >= parseInt(submenu.css('left'))) {
					//wcj: use css hover
//					item.addClass('menu-active');
				} else {
					hideMenu(submenu);
				}
			} else {
				//wcj: use css hover
//				item.removeClass('menu-active');
			}
		});
	};
	function hideAll(target) {
		//wcj:
		if (! $.data(target, 'menu')) {
			return false;
		}
		var opts = $.data(target, 'menu').options;
		hideMenu($(target));
		$(document).unbind('.menu');
		opts.onHide.call(target);
		return false;
	};
	function showTopMenu(target, pos) {
		var opts = $.data(target, 'menu').options;
		if (pos) {
			opts.left = pos.left;
			opts.top = pos.top;
			if(opts.left+$(target).outerWidth()>$(window).width()+$(document).scrollLeft()){
				opts.left=$(window).width()+$(document).scrollLeft()-$(target).outerWidth()-5;
			}
			if(opts.top+$(target).outerHeight()>$(window).height()+$(document).scrollTop()){
				opts.top-=$(target).outerHeight();
			}
		}
		showMenu($(target), {
					left : opts.left,
					top : opts.top
				}, function() {
					$(document).unbind('.menu').bind('mousedown.menu',
							function() {
								hideAll(target);
								$(document).unbind('.menu');
								return false;
							});
					opts.onShow.call(target);
				});
	};
	function showMenu(menu, pos, callback) {
		//wcj: append inline menu to body
		menu.data("parent", menu.parent());
		menu.appendTo('body');
		if (menu[0].shadow) {
			menu[0].shadow.appendTo('body');
		}
		
		if (!menu) {
			return;
		}
		if (pos) {
			menu.css(pos);
		}
		menu.show(0, function() {
					if (!menu[0].shadow) {
						menu[0].shadow = $('<div class="menu-shadow"></div>')
								.insertAfter(menu);
					}
					menu[0].shadow.css({
								display : 'block',
								zIndex : $.fn.menu.defaults.zIndex++,
								left : menu.css('left'),
								top : menu.css('top'),
								width : menu.outerWidth(),
								height : menu.outerHeight()
							});
					menu.css('z-index', $.fn.menu.defaults.zIndex++);
					if (callback) {
						callback();
					}
				});
	};
	function hideMenu(menu) {
		if (!menu) {
			return;
		}
		hideit(menu);
		menu.find('div.menu-item').each(function() {
					if (this.submenu) {
						hideMenu(this.submenu);
					}
					//wcj: use css hover
//					$(this).removeClass('menu-active');
				});
		function hideit(m) {
			m.stop(true, true);
			if (m[0].shadow) {
				m[0].shadow.hide();
			}
			m.hide();
		};

		//wcj: menu inline 
		menu.appendTo(menu.data("parent"));
		if (menu[0].shadow) {
			menu[0].shadow.appendTo(menu.data("parent"));
		}
	};
	function findItem(target, text) {
		var item = null;
		var tmp = $('<div></div>');
		function find(parentMenu) {
			parentMenu.children('div.menu-item').each(function() {
						var menu = $(target).menu('getItem', this);
						var s = tmp.empty().html(menu.text).text();
						if (text == $.trim(s)) {
							item = menu;
						} else {
							if (this.submenu && !item) {
								find(this.submenu);
							}
						}
					});
		};
		
		find($(target));
		tmp.remove();
		return item;
	};
	function setDisabled(target, itemEl, disabled) {
		var t = $(itemEl);
		if (disabled) {
			t.addClass('menu-item-disabled');
			if (itemEl.onclick) {
				itemEl.onclick1 = itemEl.onclick;
				itemEl.onclick = null;
			}
		} else {
			t.removeClass('menu-item-disabled');
			if (itemEl.onclick1) {
				itemEl.onclick = itemEl.onclick1;
				itemEl.onclick1 = null;
			}
		}
	};
	function appendItem(target, param) {
		var parent = $(target);
		if (param.parent) {
			parent = param.parent.submenu;
		}
		//wcj: sep
		if (param.text == '-') {
			$('<div class="menu-sep"></div>').appendTo(parent);
			return;
		}
		var menu = $('<div class="menu-item"></div>').appendTo(parent);
		$('<div class="menu-text"></div>').html(param.text).appendTo(menu);
		if (param.iconCls) {
			$('<div class="menu-icon"></div>').addClass(param.iconCls)
					.appendTo(menu);
		}
		if (param.id) {
			menu.attr('id', param.id);
		}
		if (param.href) {
			menu.attr('href', param.href);
		}
		if (param.onclick) {
			//wcj:
			//menu.attr('onclick', param.onclick);
			menu[0].onclick = param.onclick;
		}
		if(param.handler){
			menu[0].onclick=eval(param.handler);
		}
		bindMenuItemEvent(target, menu);
	};
	function removeItem(target, itemEl) {
		function remove(el) {
			if (el.submenu) {
				el.submenu.children('div.menu-item').each(function() {
							remove(this);
						});
				var shadow = el.submenu[0].shadow;
				if (shadow) {
					shadow.remove();
				}
				el.submenu.remove();
			}
			$(el).remove();
		};
		remove(itemEl);
	};
	function destroy(target) {
		$(target).children('div.menu-item').each(function() {
					removeItem(target, this);
				});
		if (target.shadow) {
			target.shadow.remove();
		}
		$(target).remove();
	};
	$.fn.menu = function(target, param) {
		if (typeof target == 'string') {
			return $.fn.menu.methods[target](this, param);
		}
		target = target || {};
		return this.each(function() {
					var state = $.data(this, 'menu');
					if (state) {
						$.extend(state.options, target);
					} else {
						state = $.data(this, 'menu', {
									options : $.extend({}, $.fn.menu.defaults,
											target)
								});
						init(this);
					}
					$(this).css({
								left : state.options.left,
								top : state.options.top
							});
				});
	};
	$.fn.menu.methods = {
		show : function(jq, pos) {
			return jq.each(function() {
						showTopMenu(this, pos);
					});
		},
		hide : function(jq) {
			return jq.each(function() {
						hideAll(this);
					});
		},
		destroy : function(jq) {
			return jq.each(function() {
						destroy(this);
					});
		},
		setText : function(jq, param) {
			return jq.each(function() {
						$(param.target).children('div.menu-text').html(param.text);
					});
		},
		setIcon : function(jq, param) {
			return jq.each(function() {
						var item = $(this).menu('getItem', param.target);
						if (item.iconCls) {
							$(item.target).children('div.menu-icon')
									.removeClass(item.iconCls)
									.addClass(param.iconCls);
						} else {
							$('<div class="menu-icon"></div>')
									.addClass(param.iconCls).appendTo(param.target);
						}
					});
		},
		getItem : function(jq, itemEl) {
			var item = {
				target : itemEl,
				id : $(itemEl).attr('id'),
				text : $.trim($(itemEl).children('div.menu-text').html()),
				href : $(itemEl).attr('href'),
				onclick : $(itemEl).attr('onclick')
			};
			var submenu = $(itemEl).children('div.menu-icon');
			if (submenu.length) {
				var cc = [];
				var aa = submenu.attr('class').split(' ');
				for (var i = 0; i < aa.length; i++) {
					if (aa[i] != 'menu-icon') {
						cc.push(aa[i]);
					}
				}
				item.iconCls = cc.join(' ');
			}
			return item;
		},
		findItem : function(jq, text) {
			return findItem(jq[0], text);
		},
		appendItem : function(jq, param) {
			return jq.each(function() {
						appendItem(this, param);
					});
		},
		removeItem : function(jq, itemEl) {
			return jq.each(function() {
						removeItem(this, itemEl);
					});
		},
		enableItem : function(jq, itemEl) {
			return jq.each(function() {
						setDisabled(this, itemEl,false);
					});
		},
		disableItem : function(jq, itemEl) {
			return jq.each(function() {
						setDisabled(this, itemEl,true);
					});
		}
	};
	$.fn.menu.defaults = {
		zIndex : 110000,
		left : 0,
		top : 0,
		onShow : function() {
		},
		onHide : function() {
		},
		onClick : function(item) {
		}
	};
})(jQuery);

/**
 * menubutton - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   linkbutton
 *   menu
 */
(function($) {
	//wcj: timer to hide menu when mouse out the btn
	var hideTimer = null;
	function init(target) {
		var opts = $.data(target, 'menubutton').options;
		var btn = $(target);
		btn.removeClass('m-btn-active m-btn-plain-active');
		btn.linkbutton($.extend({}, opts, {
					text : opts.text
							+ '<span class="m-btn-downarrow">&nbsp;</span>'
				}));
		if (opts.menu) {
			$(opts.menu).menu({
				onShow : function() {
					btn.addClass((opts.plain == true)
							? 'm-btn-plain-active'
							: 'm-btn-active');
				},
				onHide : function() {
					btn.removeClass((opts.plain == true)
							? 'm-btn-plain-active'
							: 'm-btn-active');
				}
			});
			//wcj: don't hide if enter menu
			$(opts.menu).bind('mouseenter', function() {
				if (hideTimer) {
					clearTimeout(hideTimer);
				}
			});
		}
		setDisabled(target, opts.disabled);
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, 'menubutton').options;
		opts.disabled = disabled;
		var btn = $(target);
		if (disabled) {
			btn.linkbutton('disable');
			btn.unbind('.menubutton');
		} else {
			btn.linkbutton('enable');
			btn.unbind('.menubutton');
			btn.bind('click.menubutton', function() {
						showMenu();
						return false;
					});
			var timer = null;
			btn.bind('mouseenter.menubutton', function() {
						timer = setTimeout(function() {
									showMenu();
								}, opts.duration);
						return false;
					}).bind('mouseleave.menubutton', function() {
						if (timer) {
							clearTimeout(timer);
						}
						//wcj: timer to hide menu when mouse out
						hideTimer = setTimeout(function() {
							$(opts.menu).menu("hide");
						}, opts.duration);
					});
		}
		function showMenu() {
			if (!opts.menu) {
				return;
			}
			var left = btn.offset().left;
			if (left + $(opts.menu).outerWidth() + 5 > $(window).width()) {
				left = $(window).width() - $(opts.menu).outerWidth() - 5;
			}
			$('body>div.menu-top').menu('hide');
			$(opts.menu).menu('show', {
						left : left,
						top : btn.offset().top + btn.outerHeight()
					});
			btn.blur();
		};
	};
	$.fn.menubutton = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.menubutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, 'menubutton');
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, 'menubutton', {
							options : $.extend({}, $.fn.menubutton.defaults,
									$.fn.menubutton.parseOptions(this), options)
						});
				$(this).removeAttr('disabled');
			}
			init(this);
		});
	};
	$.fn.menubutton.methods = {
		options : function(jq) {
			return $.data(jq[0], 'menubutton').options;
		},
		enable : function(jq) {
			return jq.each(function() {
						setDisabled(this, false);
					});
		},
		disable : function(jq) {
			return jq.each(function() {
						setDisabled(this, true);
					});
		}
	};
	$.fn.menubutton.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.linkbutton.parseOptions(target), {
					menu : t.attr('menu'),
					duration : t.attr('duration')
				});
	};
	$.fn.menubutton.defaults = $.extend({}, $.fn.linkbutton.defaults, {
				plain : true,
				menu : null,
				duration : 100
			});
})(jQuery);

/**
 * splitbutton - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   linkbutton
 *   menu
 */
(function($) {
	function init(target) {
		var opts = $.data(target, 'splitbutton').options;
		var btn = $(target);
		btn.removeClass('s-btn-active s-btn-plain-active');
		btn.linkbutton($.extend({}, opts, {
					text : opts.text
							+ '<span class="s-btn-downarrow">&nbsp;</span>'
				}));
		if (opts.menu) {
			$(opts.menu).menu({
				onShow : function() {
					btn.addClass((opts.plain == true)
							? 's-btn-plain-active'
							: 's-btn-active');
				},
				onHide : function() {
					btn.removeClass((opts.plain == true)
							? 's-btn-plain-active'
							: 's-btn-active');
				}
			});
		}
		setDisabled(target, opts.disabled);
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, 'splitbutton').options;
		opts.disabled = disabled;
		var btn = $(target);
		var menubtn = btn.find('.s-btn-downarrow');
		if (disabled) {
			btn.linkbutton('disable');
			menubtn.unbind('.splitbutton');
		} else {
			btn.linkbutton('enable');
			menubtn.unbind('.splitbutton');
			menubtn.bind('click.splitbutton', function() {
						showMenu();
						return false;
					});
			var timer = null;
			menubtn.bind('mouseenter.splitbutton', function() {
						timer = setTimeout(function() {
									showMenu();
								}, opts.duration);
						return false;
					}).bind('mouseleave.splitbutton', function() {
						if (timer) {
							clearTimeout(timer);
						}
					});
		}
		function showMenu() {
			if (!opts.menu) {
				return;
			}
			var left = btn.offset().left;
			if (left + $(opts.menu).outerWidth() + 5 > $(window).width()) {
				left = $(window).width() - $(opts.menu).outerWidth() - 5;
			}
			$('body>div.menu-top').menu('hide');
			$(opts.menu).menu('show', {
						left : left,
						top : btn.offset().top + btn.outerHeight()
					});
			btn.blur();
		};
	};
	$.fn.splitbutton = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.splitbutton.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
			var state = $.data(this, 'splitbutton');
			if (state) {
				$.extend(state.options, options);
			} else {
				$.data(this, 'splitbutton', {
							options : $.extend({}, $.fn.splitbutton.defaults,
									$.fn.splitbutton.parseOptions(this), options)
						});
				$(this).removeAttr('disabled');
			}
			init(this);
		});
	};
	$.fn.splitbutton.methods = {
		options : function(jq) {
			return $.data(jq[0], 'splitbutton').options;
		},
		enable : function(jq) {
			return jq.each(function() {
						setDisabled(this, false);
					});
		},
		disable : function(jq) {
			return jq.each(function() {
						setDisabled(this, true);
					});
		}
	};
	$.fn.splitbutton.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.linkbutton.parseOptions(target), {
					menu : t.attr('menu'),
					duration : t.attr('duration')
				});
	};
	$.fn.splitbutton.defaults = $.extend({}, $.fn.linkbutton.defaults, {
				plain : true,
				menu : null,
				duration : 100
			});
})(jQuery);

/**
 * searchbox - jQuery EasyUI
 * 
 * Licensed under the GPL: http://www.gnu.org/licenses/gpl.txt
 * 
 * Copyright 2010 stworthy [ stworthy@gmail.com ]
 * 
 * Dependencies: 
 *   menu
 *   menubutton
 *   
 */
(function($) {
	function createBox(target) {
		$(target).hide();
		var searchBox = $('<span class="searchbox"></span>').insertAfter(target);
		var searchText = $('<input type="text" class="searchbox-text">')
				.appendTo(searchBox);
		$('<span><span class="searchbox-button"></span></span>').appendTo(searchBox);
		var name = $(target).attr('name');
		if (name) {
			searchText.attr('name', name);
			$(target).removeAttr('name').attr('searchboxName', name);
		}
		return searchBox;
	};
	function resize(target,width) {
		var opts = $.data(target, 'searchbox').options;
		var sb = $.data(target, 'searchbox').searchbox;
		if (width) {
			opts.width = width;
		}
		sb.appendTo('body');
		if (isNaN(opts.width)) {
			opts.width = sb.find('input.searchbox.text').outerWidth();
		}
		var width = opts.width - sb.find('a.searchbox-menu').outerWidth()
				- sb.find('span.searchbox-button').outerWidth();
		if ($.boxModel == true) {
			width -= sb.outerWidth() - sb.width();
		}
		sb.find('input.searchbox-text').width(width);
		sb.insertAfter(target);
	};
	function handleMenu(target) {
		var searchBox = $.data(target, 'searchbox');
		var opts = searchBox.options;
		if (opts.menu) {
			searchBox.menu = $(opts.menu).menu({
						onClick : function(item) {
							toggleSearchItem(item);
						}
					});
			//var item = searchBox.menu.menu('getItem',searchBox.menu.children('div.menu-item')[0]); // what s this use for?
			searchBox.menu.children('div.menu-item').triggerHandler('click');
		} else {
			searchBox.searchbox.find('a.searchbox-menu').remove();
			searchBox.menu = null;
		}
		function toggleSearchItem(item) {
			searchBox.searchbox.find('a.searchbox-menu').remove();
			var mb = $('<a class="searchbox-menu" href="javascript:void(0)"></a>')
					.html(item.text);
			mb.prependTo(searchBox.searchbox).menubutton({
						menu : searchBox.menu,
						iconCls : item.iconCls
					});
			searchBox.searchbox.find('input.searchbox-text').attr('name',
					$(item.target).attr('name') || item.text);
			resize(target);
		};
	};
	function buildEvent(target) {
		var searchBox = $.data(target, 'searchbox');
		var opts = searchBox.options;
		var searchText = searchBox.searchbox.find('input.searchbox-text');
		var searchButton = searchBox.searchbox.find('.searchbox-button');
		//wcj: use placeholder
		searchText.unbind('.searchbox')/*.bind('blur.searchbox', function(e) {
					opts.value = $(this).val();
					if (opts.value == '') {
						$(this).val(opts.prompt);
						$(this).addClass('searchbox-prompt');
					} else {
						$(this).removeClass('searchbox-prompt');
					}
				}).bind('focus.searchbox', function(e) {
					if ($(this).val() != opts.value) {
						$(this).val(opts.value);
					}
					$(this).removeClass('searchbox-prompt');
				})*/.bind('keydown.searchbox', function(e) {
					if (e.keyCode == 13) {
						e.preventDefault();
						opts.value = $(this).val();
						opts.searcher.call(target, opts.value, searchText.attr('name'));
						return false;
					}
				});
		
		searchButton.unbind('.searchbox').bind('click.searchbox', function() {
					//wcj: bug
					opts.value = searchText.val();
					opts.searcher.call(target, opts.value, searchText.attr('name'));
				}).bind('mouseenter.searchbox', function() {
					$(this).addClass('searchbox-button-hover');
				}).bind('mouseleave.searchbox', function() {
					$(this).removeClass('searchbox-button-hover');
				});
	};
	function fillDefaultValue(target) {
		var searchBox = $.data(target, 'searchbox');
		var opts = searchBox.options;
		var searchText = searchBox.searchbox.find('input.searchbox-text');
		//wcj: use placeholder
		searchText.val(opts.value);
		searchText.attr("placeholder", opts.prompt);
		searchText.placeholder();
		/*
		if (opts.value == '') {
			searchText.val(opts.prompt);
			searchText.addClass('searchbox-prompt');
		} else {
			searchText.val(opts.value);
			searchText.removeClass('searchbox-prompt');
		}
		*/
	};
	$.fn.searchbox = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.searchbox.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'searchbox');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'searchbox', {
									options : $.extend({},
											$.fn.searchbox.defaults,
											$.fn.searchbox.parseOptions(this),
											options),
									searchbox : createBox(this)
								});
					}
					handleMenu(this);
					fillDefaultValue(this);
					buildEvent(this);
					resize(this);
				});
	};
	$.fn.searchbox.methods = {
		options : function(jq) {
			return $.data(jq[0], 'searchbox').options;
		},
		menu : function(jq) {
			return $.data(jq[0], 'searchbox').menu;
		},
		textbox : function(jq) {
			return $.data(jq[0], 'searchbox').searchbox
					.find('input.searchbox-text');
		},
		getValue : function(jq) {
			return $.data(jq[0], 'searchbox').options.value;
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						$(this).searchbox('options').value = value;
						$(this).searchbox('textbox').val(value);
						//wcj:
						//$(this).searchbox('textbox').blur();
					});
		},
		getName : function(jq) {
			return $.data(jq[0], 'searchbox').searchbox
					.find('input.searchbox-text').attr('name');
		},
		destroy : function(jq) {
			return jq.each(function() {
						var menu = $(this).searchbox('menu');
						if (menu) {
							menu.menu('destroy');
						}
						$.data(this, 'searchbox').searchbox.remove();
						$(this).remove();
					});
		},
		resize : function(jq, width) {
			return jq.each(function() {
						resize(this, width);
					});
		}
	};
	$.fn.searchbox.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			//wcj: use placeholder
			prompt : t.attr('prompt') || t.attr('placeholder'),
			value : t.val(),
			menu : t.attr('menu'),
			searcher : (t.attr('searcher')
					? eval(t.attr('searcher'))
					: undefined)
		};
	};
	$.fn.searchbox.defaults = {
		width : 'auto',
		prompt : '',
		value : '',
		menu : null,
		searcher : function(value,name) {
		}
	};
})(jQuery);

/**
 * validatebox - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($) {
	function init(target) {
		$(target).addClass('validatebox-text');
	};
	function destroyBox(target) {
		var data = $.data(target, 'validatebox');
		data.validating=false;
		var tip=data.tip;
		if (tip) {
			tip.remove();
		}
		$(target).unbind();
		$(target).remove();
	};
	function bindEvents(target) {
		//wcj: tooltip will handle mouse events
		var box = $(target);
		var validatebox = $.data(target, 'validatebox');
		validatebox.validating = false;
		box.unbind('.validatebox')/*.bind('focus.validatebox', function() {
					validatebox.validating = true;
					(function(){
						if (validatebox.validating) {
							validate(target);
							setTimeout(arguments.callee, 200);
						}
					})();
				}).bind('blur.validatebox', function() {
					validatebox.validating = false;
					//hideTip(target);
				}).bind('mouseenter.validatebox', function() {
					if (box.hasClass('validatebox-invalid')) {
						showTip(target);
					}
				}).bind('mouseleave.validatebox', function() {
					hideTip(target);
				})*/.bind('keydown.validatebox', function(e) {
					//wcj:
					setTimeout(function() {
						if (e.keyCode == 9) {
							//tab
						} else {
							validate(target);
						}
					}, 0);
				}).bind('focus.validatebox', function() {
					//wcj:
					validate(target);
				}).bind('blur.validatebox', function() {
					//wcj:
					validate(target);
				});
	};
	//wcj:
	function initTip(target) {
		var box = $(target);
		var msg = $.data(target, 'validatebox').message;

		if (! box.attr("tooltip") || box.attr("tooltip").indexOf(msg) < 0) {
			box.data("oldTooltip", box.attr("tooltip"));
			box.attr("tooltip", (box.attr("tooltip") || "") + " " + msg);
		}
		box.tooltip();
		box.data("invalidTip", true);
	}
	function showTip(target) {
		//wcj: use tooltip
		var box = $(target);
		var msg = $.data(target, 'validatebox').message;

		if (! box.attr("tooltip") || box.attr("tooltip").indexOf(msg) < 0) {
			box.data("oldTooltip", box.attr("tooltip"));
			box.attr("tooltip", (box.attr("tooltip") || "") + " " + msg);
		}
		box.tooltip("show");
		box.data("invalidTip", true);
		/*
		var tip = $.data(target, 'validatebox').tip;
		var owner = $(target).attr('id')+'_validate_tip';
		if (!tip) {
			tip = $('<div class="validatebox-tip" id="'+owner+'">'
					+ '<span class="validatebox-tip-content">' + '</span>'
					+ '<span class="validatebox-tip-pointer">' + '</span>'
					+ '</div>').appendTo('body');
			$.data(target, 'validatebox').tip = tip;
		}
		tip.find('.validatebox-tip-content').html(msg);
		var left = box.offset().left + box.outerWidth();
		if(!$(target).is('.easyui-validatebox')){
			left+=20;
		}
		tip.css({
					display : 'block',
					left : left,
					top : box.offset().top
				});
		*/
	};
	function hideTip(target) {
		//wcj: use tooltip
		var box = $(target);
		if (box.hasClass('validatebox-invalid')) {
			box.tooltip("hide");
		} else {
			if (box.data("oldTooltip")) {
				if (box.data("oldTooltip") != box.attr("tooltip")) {
					box.tooltip("destroy");
					box.attr("tooltip", box.data("oldTooltip"));
					box.tooltip("show");
				}
			} else {
				if (box.data("invalidTip")) {
					box.tooltip("destroy");
					box.removeAttr("tooltip");
					box.removeData("invalidTip")
				}
			}
		}
		/*
		var tip = $.data(target, 'validatebox').tip;
		if (tip) {
			tip.remove();
			$.data(target, 'validatebox').tip = null;
		}
		*/
	};
	//wcj: add param toShowTip
	function validate(target, toShowTip) {
		var opts = $.data(target, 'validatebox').options;
		var tip = $.data(target, 'validatebox').tip;
		var box = $(target);
		var value = box.val();
		function setTipMessage(msg) {
			$.data(target, 'validatebox').message = msg;
		};
		var disabled = box.attr('disabled');
		if (disabled == true || disabled == 'true') {
			return true;
		}
		if (opts.required) {
			if (value == '') {
				box.addClass('validatebox-invalid');
				setTipMessage(opts.missingMessage);
				//wcj: toShowTip
				if (toShowTip == false) {
					initTip(target);
				} else {
					showTip(target);
				}
				return false;
			}
		}
		if (opts.validType) {
			var result = /([a-zA-Z_]+)(.*)/.exec(opts.validType);
			var rule = opts.rules[result[1]];
			if (value && rule) {
				var param = eval(result[2]);
				if (!rule['validator'](value, param)) {
					box.addClass('validatebox-invalid');
					var message = rule['message'];
					if (param) {
						for (var i = 0; i < param.length; i++) {
							message = message.replace(
									new RegExp('\\{' + i + '\\}', 'g'), param[i]);
						}
					}
					setTipMessage(opts.invalidMessage || message);
					//wcj: toShowTip
					if (toShowTip == false) {
						initTip(target);
					} else {
						showTip(target);
					}
					return false;
				}
			}
		}
		box.removeClass('validatebox-invalid');
		hideTip(target);
		return true;
	};
	$.fn.validatebox = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.validatebox.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'validatebox');
					if (state) {
						$.extend(state.options, options);
					} else {
						init(this);
						$.data(this, 'validatebox', {
									options : $
											.extend(
													{},
													$.fn.validatebox.defaults,
													$.fn.validatebox
															.parseOptions(this),
													options)
								});
					}
					bindEvents(this);
				});
	};
	$.fn.validatebox.methods = {
		destroy : function(jq) {
			return jq.each(function() {
						destroyBox(this);
					});
		},
		//wcj: add param toShowTip
		validate : function(jq, toShowTip) {
			//wcj: return value
			/*
			return jq.each(function() {
						validate(this);
					});
			*/
			return validate(jq[0], toShowTip);
		},
		isValid : function(jq) {
			return validate(jq[0]);
		}
	};
	$.fn.validatebox.parseOptions = function(target) {
		var t = $(target);
		return {
			required : (t.attr('required') ? (t.attr('required') == 'required'||t.attr('required')=='true' || t
					.attr('required') == true) : undefined),
			validType : (t.attr('validType') || undefined),
			missingMessage : (t.attr('missingMessage') || undefined),
			invalidMessage : (t.attr('invalidMessage') || undefined)
		};
	};
	$.fn.validatebox.defaults = {
		required : false,
		validType : null,
		missingMessage : 'This field is required.',
		invalidMessage : null,
		rules : {
			email : {
				validator : function(value) {
					return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i
							.test(value);
				},
				message : 'Please enter a valid email address.'
			},
			url : {
				validator : function(value) {
					return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
							.test(value);
				},
				message : 'Please enter a valid URL.'
			},
			length : {
				validator : function(value, param) {
					var len = $.trim(value).length;
					return len >= param[0] && len <= param[1];
				},
				message : 'Please enter a value between {0} and {1}.'
			},
			remote : {
				validator : function(value, param) {
					var data = {};
					data[param[1]] = value;
					var isValidate = $.ajax({
								url : param[0],
								dataType : "json",
								data : data,
								async : false,
								cache : false,
								type : "post"
							}).responseText;
					return isValidate == "true";
				},
				message : "Please fix this field."
			}
		}
	};
})(jQuery);

/**
 * form - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 */
(function($) {
	function submitForm(target, options) {
		options = options || {};
		if (options.onSubmit) {
			if (options.onSubmit.call(target) == false) {
				return;
			}
		}
		var form = $(target);
		if (options.url) {
			form.attr('action', options.url);
		}
		var frameId = 'easyui_frame_' + (new Date().getTime());
		var frame = $('<iframe id=' + frameId + ' name=' + frameId + '></iframe>').attr(
				'src',
				window.ActiveXObject ? 'javascript:false' : 'about:blank').css(
				{
					position : 'absolute',
					top : -1000,
					left : -1000
				});
		var t = form.attr('target'), a = form.attr('action');
		form.attr('target', frameId);
		try {
			frame.appendTo('body');
			frame.bind('load', cb);
			form[0].submit();
		} finally {
			form.attr('action', a);
			t ? form.attr('target', t) : form.removeAttr('target');
		}
		var checkCount = 10;
		function cb() {
			frame.unbind();
			var body = $('#' + frameId).contents().find('body');
			var data = body.html();
			if (data == '') {
				if (--checkCount) {
					setTimeout(cb, 100);
					return;
				}
				return;
			}
			var ta = body.find('>textarea');
			if (ta.length) {
				data = ta.val();
			} else {
				var pre = body.find('>pre');
				if (pre.length) {
					data = pre.html();
				}
			}
			if (options.success) {
				options.success(data);
			}
			setTimeout(function() {
						frame.unbind();
						frame.remove();
					}, 100);
		};
	};
	function load(target, data) {
		if (!$.data(target, 'form')) {
			$.data(target, 'form', {
						options : $.extend({}, $.fn.form.defaults)
					});
		}
		var opts = $.data(target, 'form').options;
		if (typeof data == 'string') {
			var param = {};
			if (opts.onBeforeLoad.call(target, param) == false) {
				return;
			}
			$.ajax({
						url : data,
						data : param,
						dataType : 'json',
						success : function(data) {
							_load(data);
						},
						error : function() {
							opts.onLoadError.apply(target, arguments);
						}
					});
		} else {
			_load(data);
		}
		function _load(data) {
			var form = $(target);
			for (var name in data) {
				var val = data[name];
				var rr = $('input[name=' + name + '][type=radio]', form);
				$.fn.prop ? rr.prop('checked', false) : rr.attr('checked',
						false);
				var rv = $('input[name=' + name + '][type=radio][value="' + val
								+ '"]', form);
				$.fn.prop ? rv.prop('checked', true) : rv.attr('checked', true);
				var cc = $('input[name=' + name + '][type=checkbox]', form);
				$.fn.prop ? cc.prop('checked', false) : cc.attr('checked',
						false);
				var cv = $('input[name=' + name + '][type=checkbox][value="'
								+ val + '"]', form);
				$.fn.prop ? cv.prop('checked', true) : cv.attr('checked', true);
				if (!rr.length && !cc.length) {
					$('input[name=' + name + ']', form).val(val);
					$('textarea[name=' + name + ']', form).val(val);
					$('select[name=' + name + ']', form).val(val);
				}
				var cc = ['combo', 'combobox', 'combotree', 'combogrid',
						'datebox', 'datetimebox'];
				for (var i = 0; i < cc.length; i++) {
					setValue(cc[i], name, val);
				}
			}
			opts.onLoadSuccess.call(target, data);
			validate(target);
		};
		function setValue(type, name, val) {
			var form = $(target);
			var c = form.find('.' + type + '-f[comboName=' + name + ']');
			if (c.length && c[type]) {
				if (c[type]('options').multiple) {
					c[type]('setValues', val);
				} else {
					c[type]('setValue', val);
				}
			}
		};
	};
	function clear(target) {
		$(':input', target).each(function() {
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'hidden' || t == 'password'
					|| tag == 'textarea') {
				this.value = '';
			} else {
				if(t=='file'){
					var node=$(this);
					node.after(node.clone().val(''));
					node.remove();
				}else{
					if (t == 'checkbox' || t == 'radio') {
						this.checked = false;
					} else {
						if (tag == 'select') {
							this.selectedIndex = -1;
						}
					}
				}
			}
		});
		if ($.fn.combo) {
			$('.combo-f', target).combo('clear');
		}
		if ($.fn.combobox) {
			$('.combobox-f', target).combobox('clear');
		}
		if ($.fn.combotree) {
			$('.combotree-f', target).combotree('clear');
		}
		if ($.fn.combogrid) {
			$('.combogrid-f', target).combogrid('clear');
		}
	};
	function setForm(target) {
		var opts = $.data(target, 'form').options;
		var form = $(target);
		form.unbind('.form').bind('submit.form', function() {
					setTimeout(function() {
								submitForm(target, opts);
							}, 0);
					return false;
				});
	};
	function validate(target) {
		if ($.fn.validatebox) {
			var box = $('.validatebox-text', target);
			if (box.length) {
				box.validatebox('validate');
				box.trigger('blur');
				var valid = $('.validatebox-invalid:first', target).focus();
				return valid.length == 0;
			}
		}
		return true;
	};
	$.fn.form = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.form.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					if (!$.data(this, 'form')) {
						$.data(this, 'form', {
									options : $.extend({}, $.fn.form.defaults,
											options)
								});
					}
					setForm(this);
				});
	};
	$.fn.form.methods = {
		submit : function(jq, param) {
			return jq.each(function() {
						submitForm(this, $.extend({}, $.fn.form.defaults, param || {}));
					});
		},
		load : function(jq, param) {
			return jq.each(function() {
						load(this, param);
					});
		},
		clear : function(jq) {
			return jq.each(function() {
						clear(this);
					});
		},
		validate : function(jq) {
			return validate(jq[0]);
		}
	};
	$.fn.form.defaults = {
		url : null,
		onSubmit : function() {
		},
		success : function(data) {
		},
		onBeforeLoad : function(param) {
		},
		onLoadSuccess : function(data) {
		},
		onLoadError : function() {
		}
	};
})(jQuery);

/**
 * numberbox - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 validatebox
 * 
 */
(function($) {
	function fixValue(target) {
		var opts = $.data(target, 'numberbox').options;
		var val = parseFloat($(target).val()).toFixed(opts.precision);
		if (isNaN(val)) {
			$(target).val('');
			return;
		}
		if (typeof(opts.min) == 'number' && val < opts.min) {
			$(target).val(opts.min.toFixed(opts.precision));
		} else {
			if (typeof(opts.max) == 'number' && val > opts.max) {
				$(target).val(opts.max.toFixed(opts.precision));
			} else {
				$(target).val(val);
			}
		}
	};
	function bindEvents(target) {
		$(target).unbind('.numberbox');
		$(target).bind('keypress.numberbox', function(e) {
			if (e.which == 45) {
				return true;
			}
			if (e.which == 46) {
				return true;
			} else {
				if ((e.which >= 48 && e.which <= 57 && e.ctrlKey == false && e.shiftKey == false)
						|| e.which == 0 || e.which == 8) {
					return true;
				} else {
					if (e.ctrlKey == true && (e.which == 99 || e.which == 118)) {
						return true;
					} else {
						return false;
					}
				}
			}
		//wcj: allow paste, onBlur will fix the value
		/*
		}).bind('paste.numberbox', function() {
					if (window.clipboardData) {
						var s = clipboardData.getData('text');
						if (!/\D/.test(s)) {
							return true;
						} else {
							return false;
						}
					} else {
						return false;
					}
				*/
				}).bind('dragenter.numberbox', function() {
					return false;
				}).bind('blur.numberbox', function() {
					fixValue(target);
				});
	};
	function validate(target) {
		if ($.fn.validatebox) {
			var opts = $.data(target, 'numberbox').options;
			$(target).validatebox(opts);
		}
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, 'numberbox').options;
		if (disabled) {
			opts.disabled = true;
			$(target).attr('disabled', true);
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
		}
	};
	$.fn.numberbox = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.numberbox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.validatebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'numberbox');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'numberbox', {
									options : $.extend({},
											$.fn.numberbox.defaults,
											$.fn.numberbox.parseOptions(this),
											options)
								});
						$(this).removeAttr('disabled');
						$(this).css({
									imeMode : 'disabled'
								});
					}
					setDisabled(this, state.options.disabled);
					bindEvents(this);
					validate(this);
				});
	};
	$.fn.numberbox.methods = {
		disable : function(jq) {
			return jq.each(function() {
						setDisabled(this, true);
					});
		},
		enable : function(jq) {
			return jq.each(function() {
						setDisabled(this, false);
					});
		},
		fix : function(jq) {
			return jq.each(function() {
						fixValue(this);
					});
		}
	};
	$.fn.numberbox.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.validatebox.parseOptions(target), {
					disabled : (t.attr('disabled') ? true : undefined),
					min : (t.attr('min') == '0' ? 0 : parseFloat(t.attr('min'))
							|| undefined),
					max : (t.attr('max') == '0' ? 0 : parseFloat(t.attr('max'))
							|| undefined),
					precision : (parseInt(t.attr('precision')) || undefined)
				});
	};
	$.fn.numberbox.defaults = $.extend({}, $.fn.validatebox.defaults, {
				disabled : false,
				min : null,
				max : null,
				precision : 0
			});
})(jQuery);

/**
 * calendar - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 */
(function($) {
	function setSize(target) {
		var opts = $.data(target, 'calendar').options;
		var t = $(target);
		if (opts.fit == true) {
			var p = t.parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		var header = t.find('.calendar-header');
		if ($.boxModel == true) {
			t.width(opts.width - (t.outerWidth() - t.width()));
			t.height(opts.height - (t.outerHeight() - t.height()));
		} else {
			t.width(opts.width);
			t.height(opts.height);
		}
		var body = t.find('.calendar-body');
		var height = t.height() - header.outerHeight();
		if ($.boxModel == true) {
			body.height(height - (body.outerHeight() - body.height()));
		} else {
			body.height(height);
		}
	};
	function init(target) {
		$(target)
				.addClass('calendar')
				.wrapInner('<div class="calendar-header">'
						+ '<div class="calendar-prevmonth"></div>'
						+ '<div class="calendar-nextmonth"></div>'
						+ '<div class="calendar-prevyear"></div>'
						+ '<div class="calendar-nextyear"></div>'
						+ '<div class="calendar-title">'
						+ '<span>Aprial 2010</span>'
						+ '</div>'
						+ '</div>'
						+ '<div class="calendar-body">'
						+ '<div class="calendar-menu">'
						+ '<div class="calendar-menu-year-inner">'
						+ '<span class="calendar-menu-prev"></span>'
						+ '<span><input class="calendar-menu-year" type="text"></input></span>'
						+ '<span class="calendar-menu-next"></span>'
						+ '</div>'
						+ '<div class="calendar-menu-month-inner">'
						+ '</div>' + '</div>' + '</div>');
		$(target).find('.calendar-title span').hover(function() {
					$(this).addClass('calendar-menu-hover');
				}, function() {
					$(this).removeClass('calendar-menu-hover');
				}).click(function() {
					var menu = $(target).find('.calendar-menu');
					if (menu.is(':visible')) {
						menu.hide();
					} else {
						showSelectMenus(target);
					}
				});
		$(
				'.calendar-prevmonth,.calendar-nextmonth,.calendar-prevyear,.calendar-nextyear',
				target).hover(function() {
					$(this).addClass('calendar-nav-hover');
				}, function() {
					$(this).removeClass('calendar-nav-hover');
				});
		$(target).find('.calendar-nextmonth').click(function() {
					showMonth(target, 1);
				});
		$(target).find('.calendar-prevmonth').click(function() {
					showMonth(target, -1);
				});
		$(target).find('.calendar-nextyear').click(function() {
					showYear(target, 1);
				});
		$(target).find('.calendar-prevyear').click(function() {
					showYear(target, -1);
				});
		$(target).bind('_resize', function() {
					var opts = $.data(target, 'calendar').options;
					if (opts.fit == true) {
						setSize(target);
					}
					return false;
				});
	};
	function showMonth(target, delta) {
		var opts = $.data(target, 'calendar').options;
		opts.month += delta;
		if (opts.month > 12) {
			opts.year++;
			opts.month = 1;
		} else {
			if (opts.month < 1) {
				opts.year--;
				opts.month = 12;
			}
		}
		show(target);
		var menu = $(target).find('.calendar-menu-month-inner');
		menu.find('td.calendar-selected').removeClass('calendar-selected');
		menu.find('td:eq(' + (opts.month - 1) + ')').addClass('calendar-selected');
	};
	function showYear(target, delta) {
		var opts = $.data(target, 'calendar').options;
		opts.year += delta;
		show(target);
		var menu = $(target).find('.calendar-menu-year');
		menu.val(opts.year);
	};
	function showSelectMenus(target) {
		var opts = $.data(target, 'calendar').options;
		$(target).find('.calendar-menu').show();
		if ($(target).find('.calendar-menu-month-inner').is(':empty')) {
			$(target).find('.calendar-menu-month-inner').empty();
			var t = $('<table></table>').appendTo($(target)
					.find('.calendar-menu-month-inner'));
			var idx = 0;
			for (var i = 0; i < 3; i++) {
				var tr = $('<tr></tr>').appendTo(t);
				for (var j = 0; j < 4; j++) {
					$('<td class="calendar-menu-month"></td>')
							.html(opts.months[idx++]).attr('abbr', idx)
							.appendTo(tr);
				}
			}
			$(target).find('.calendar-menu-prev,.calendar-menu-next').hover(
					function() {
						$(this).addClass('calendar-menu-hover');
					}, function() {
						$(this).removeClass('calendar-menu-hover');
					});
			$(target).find('.calendar-menu-next').click(function() {
						var y = $(target).find('.calendar-menu-year');
						if (!isNaN(y.val())) {
							y.val(parseInt(y.val()) + 1);
						}
					});
			$(target).find('.calendar-menu-prev').click(function() {
						var y = $(target).find('.calendar-menu-year');
						if (!isNaN(y.val())) {
							y.val(parseInt(y.val() - 1));
						}
					});
			$(target).find('.calendar-menu-year').keypress(function(e) {
						if (e.keyCode == 13) {
							setDate();
						}
					});
			$(target).find('.calendar-menu-month').hover(function() {
						$(this).addClass('calendar-menu-hover');
					}, function() {
						$(this).removeClass('calendar-menu-hover');
					}).click(function() {
				var menu = $(target).find('.calendar-menu');
				menu.find('.calendar-selected').removeClass('calendar-selected');
				$(this).addClass('calendar-selected');
				setDate();
			});
		}
		function setDate() {
			var menu = $(target).find('.calendar-menu');
			var year = menu.find('.calendar-menu-year').val();
			var month = menu.find('.calendar-selected').attr('abbr');
			if (!isNaN(year)) {
				opts.year = parseInt(year);
				opts.month = parseInt(month);
				show(target);
			}
			menu.hide();
		};
		var body = $(target).find('.calendar-body');
		var sele = $(target).find('.calendar-menu');
		var seleYear = sele.find('.calendar-menu-year-inner');
		var seleMonth = sele.find('.calendar-menu-month-inner');
		seleYear.find('input').val(opts.year).focus();
		seleYear.find('input').val(opts.year);
		seleMonth.find('td.calendar-selected').removeClass('calendar-selected');
		seleMonth.find('td:eq(' + (opts.month - 1) + ')')
				.addClass('calendar-selected');
		if ($.boxModel == true) {
			sele.width(body.outerWidth() - (sele.outerWidth() - sele.width()));
			sele.height(body.outerHeight() - (sele.outerHeight() - sele.height()));
			seleMonth.height(sele.height() - (seleMonth.outerHeight() - seleMonth.height())
					- seleYear.outerHeight());
		} else {
			sele.width(body.outerWidth());
			sele.height(body.outerHeight());
			seleMonth.height(sele.height() - seleYear.outerHeight());
		}
	};
	function getWeeks(year, month) {
		var dates = [];
		var lastDay = new Date(year, month, 0).getDate();
		for (var i = 1; i <= lastDay; i++) {
			dates.push([year, month, i]);
		}
		// group date by week
		var weeks = [], week = [];
		while (dates.length > 0) {
			var date = dates.shift();
			week.push(date);
			if (new Date(date[0], date[1] - 1, date[2]).getDay() == 6) {
				weeks.push(week);
				week = [];
			}
		}
		if (week.length) {
			weeks.push(week);
		}
		var firstWeek = weeks[0];
		if (firstWeek.length < 7) {
			while (firstWeek.length < 7) {
				var firstDate = firstWeek[0];
				var date = new Date(firstDate[0], firstDate[1] - 1, firstDate[2] - 1);
				firstWeek.unshift([date.getFullYear(), date.getMonth() + 1,
						date.getDate()]);
			}
		} else {
			var firstDate = firstWeek[0];
			var week = [];
			for (var i = 1; i <= 7; i++) {
				var date = new Date(firstDate[0], firstDate[1] - 1, firstDate[2] - i);
				week.unshift([date.getFullYear(), date.getMonth() + 1,
						date.getDate()]);
			}
			weeks.unshift(week);
		}
		var lastWeek = weeks[weeks.length - 1];
		while (lastWeek.length < 7) {
			var lastDate = lastWeek[lastWeek.length - 1];
			var date = new Date(lastDate[0], lastDate[1] - 1, lastDate[2] + 1);
			lastWeek.push([date.getFullYear(), date.getMonth() + 1, date.getDate()]);
		}
		if (weeks.length < 6) {
			var lastDate = lastWeek[lastWeek.length - 1];
			var week = [];
			for (var i = 1; i <= 7; i++) {
				var date = new Date(lastDate[0], lastDate[1] - 1, lastDate[2] + i);
				week
						.push([date.getFullYear(), date.getMonth() + 1,
								date.getDate()]);
			}
			weeks.push(week);
		}
		return weeks;
	};
	function show(target) {
		var opts = $.data(target, 'calendar').options;
		$(target).find('.calendar-title span').html(opts.months[opts.month - 1]
				+ ' ' + opts.year);
		var body = $(target).find('div.calendar-body');
		body.find('>table').remove();
		var t = $('<table cellspacing="0" cellpadding="0" border="0"><thead></thead><tbody></tbody></table>')
				.prependTo(body);
		var tr = $('<tr></tr>').appendTo(t.find('thead'));
		for (var i = 0; i < opts.weeks.length; i++) {
			tr.append('<th>' + opts.weeks[i] + '</th>');
		}
		var weeks = getWeeks(opts.year, opts.month);
		for (var i = 0; i < weeks.length; i++) {
			var week = weeks[i];
			var tr = $('<tr></tr>').appendTo(t.find('tbody'));
			for (var j = 0; j < week.length; j++) {
				var day = week[j];
				$('<td class="calendar-day calendar-other-month"></td>')
						.attr('abbr', day[0] + ',' + day[1] + ',' + day[2])
						.html(day[2]).appendTo(tr);
			}
		}
		t.find('td[abbr^="' + opts.year + ',' + opts.month + '"]')
				.removeClass('calendar-other-month');
		var now = new Date();
		var today = now.getFullYear() + ',' + (now.getMonth() + 1) + ','
				+ now.getDate();
		t.find('td[abbr="' + today + '"]').addClass('calendar-today');
		if (opts.current) {
			t.find('.calendar-selected').removeClass('calendar-selected');
			var current = opts.current.getFullYear() + ','
					+ (opts.current.getMonth() + 1) + ','
					+ opts.current.getDate();
			t.find('td[abbr="' + current + '"]').addClass('calendar-selected');
		}
		t.find('tr').find('td:first').addClass('calendar-sunday');
		t.find('tr').find('td:last').addClass('calendar-saturday');
		t.find('td').hover(function() {
					$(this).addClass('calendar-hover');
				}, function() {
					$(this).removeClass('calendar-hover');
				}).click(function() {
					t.find('.calendar-selected')
							.removeClass('calendar-selected');
					$(this).addClass('calendar-selected');
					var parts = $(this).attr('abbr').split(',');
					opts.current = new Date(parts[0], parseInt(parts[1]) - 1, parts[2]);
					opts.onSelect.call(target, opts.current);
				}).dblclick(function() {
					t.find('.calendar-selected')
							.removeClass('calendar-selected');
					$(this).addClass('calendar-selected');
					var parts = $(this).attr('abbr').split(',');
					opts.current = new Date(parts[0], parseInt(parts[1]) - 1, parts[2]);
					opts.onDblClick.call(target, opts.current);
				});
	};
	$.fn.calendar = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.calendar.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'calendar');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'calendar', {
									options : $.extend({},
											$.fn.calendar.defaults,
											$.fn.calendar.parseOptions(this),
											options)
								});
						init(this);
					}
					if (state.options.border == false) {
						$(this).addClass('calendar-noborder');
					}
					setSize(this);
					show(this);
					$(this).find('div.calendar-menu').hide();
				});
	};
	$.fn.calendar.methods = {
		options : function(jq) {
			return $.data(jq[0], 'calendar').options;
		},
		resize : function(jq) {
			return jq.each(function() {
						setSize(this);
					});
		},
		moveTo : function(jq, date) {
			return jq.each(function() {
						$(this).calendar({
									year : date.getFullYear(),
									month : date.getMonth() + 1,
									current : date
								});
					});
		}
	};
	$.fn.calendar.parseOptions = function(target) {
		var t = $(target);
		return {
			width : (parseInt(target.style.width) || undefined),
			height : (parseInt(target.style.height) || undefined),
			fit : (t.attr('fit') ? t.attr('fit') == 'true' : undefined),
			border : (t.attr('border') ? t.attr('border') == 'true' : undefined)
		};
	};
	$.fn.calendar.defaults = {
		width : 180,
		height : 180,
		fit : false,
		border : true,
		weeks : ['S', 'M', 'T', 'W', 'T', 'F', 'S'],
		months : ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
				'Sep', 'Oct', 'Nov', 'Dec'],
		year : new Date().getFullYear(),
		month : new Date().getMonth() + 1,
		current : new Date(),
		onSelect : function(date) {
		},
		onDblClick : function(date){
		}
	};
})(jQuery);

/**
 * spinner - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   validatebox
 * 
 */
(function($) {
	function init(target) {
		var spinner = $('<span class="spinner">'
				+ '<span class="spinner-arrow">'
				+ '<span class="spinner-arrow-up"></span>'
				+ '<span class="spinner-arrow-down"></span>' + '</span>'
				+ '</span>').insertAfter(target);
		$(target).addClass('spinner-text').prependTo(spinner);
		return spinner;
	};
	function setSize(target, width) {
		var opts = $.data(target, 'spinner').options;
		var spinner = $.data(target, 'spinner').spinner;
		if (width) {
			opts.width = width;
		}
		var tmp = $('<div style="display:none"></div>').insertBefore(spinner);
		spinner.appendTo('body');
		if (isNaN(opts.width)) {
			opts.width = $(target).outerWidth();
		}
		var arrowWidth = spinner.find('.spinner-arrow').outerWidth();
		var width = opts.width - arrowWidth;
		if ($.boxModel == true) {
			width -= spinner.outerWidth() - spinner.width();
		}
		$(target).width(width);
		spinner.insertAfter(tmp);
		tmp.remove();
	};
	function bindEvents(target) {
		var opts = $.data(target, 'spinner').options;
		var spinner = $.data(target, 'spinner').spinner;
		spinner.find('.spinner-arrow-up,.spinner-arrow-down').unbind('.spinner');
		if (!opts.disabled) {
			spinner.find('.spinner-arrow-up').bind('mouseenter.spinner', function() {
						$(this).addClass('spinner-arrow-hover');
					}).bind('mouseleave.spinner', function() {
						$(this).removeClass('spinner-arrow-hover');
					}).bind('click.spinner', function() {
						opts.spin.call(target, false);
						opts.onSpinUp.call(target);
						$(target).validatebox('validate');
					});
			spinner.find('.spinner-arrow-down').bind('mouseenter.spinner',
					function() {
						$(this).addClass('spinner-arrow-hover');
					}).bind('mouseleave.spinner', function() {
						$(this).removeClass('spinner-arrow-hover');
					}).bind('click.spinner', function() {
						opts.spin.call(target, true);
						opts.onSpinDown.call(target);
						$(target).validatebox('validate');
					});
		}
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, 'spinner').options;
		if (disabled) {
			opts.disabled = true;
			$(target).attr('disabled', true);
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
		}
	};
	$.fn.spinner = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.spinner.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.validatebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'spinner');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'spinner', {
									options : $.extend({},
											$.fn.spinner.defaults, $.fn.spinner
													.parseOptions(this), options),
									spinner : init(this)
								});
						$(this).removeAttr('disabled');
					}
					$(this).val(state.options.value);
					$(this).attr('readonly', !state.options.editable);
					setDisabled(this, state.options.disabled);
					setSize(this);
					$(this).validatebox(state.options);
					bindEvents(this);
				});
	};
	$.fn.spinner.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], 'spinner').options;
			return $.extend(opts, {
						value : jq.val()
					});
		},
		destroy : function(jq) {
			return jq.each(function() {
						var spinner = $.data(this, 'spinner').spinner;
						$(this).validatebox('destroy');
						spinner.remove();
					});
		},
		resize : function(jq, width) {
			return jq.each(function() {
						setSize(this, width);
					});
		},
		enable : function(jq) {
			return jq.each(function() {
						setDisabled(this, false);
						bindEvents(this);
					});
		},
		disable : function(jq) {
			return jq.each(function() {
						setDisabled(this, true);
						bindEvents(this);
					});
		},
		getValue : function(jq) {
			return jq.val();
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						var opts = $.data(this, 'spinner').options;
						opts.value = value;
						$(this).val(value);
					});
		},
		clear : function(jq) {
			return jq.each(function() {
						var opts = $.data(this, 'spinner').options;
						opts.value = '';
						$(this).val('');
					});
		}
	};
	$.fn.spinner.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.validatebox.parseOptions(target), {
					width : (parseInt(target.style.width) || undefined),
					value : (t.val() || undefined),
					min : t.attr('min'),
					max : t.attr('max'),
					increment : (parseFloat(t.attr('increment')) || undefined),
					editable : (t.attr('editable')
							? t.attr('editable') == 'true'
							: undefined),
					disabled : (t.attr('disabled') ? true : undefined)
				});
	};
	$.fn.spinner.defaults = $.extend({}, $.fn.validatebox.defaults, {
				width : 'auto',
				value : '',
				min : null,
				max : null,
				increment : 1,
				editable : true,
				disabled : false,
				spin : function(down) {
				},
				onSpinUp : function() {
				},
				onSpinDown : function() {
				}
			});
})(jQuery);

/**
 * numberspinner - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 spinner
 * 	 numberbox
 */
(function($) {
	function create(target) {
		var opts = $.data(target, 'numberspinner').options;
		$(target).spinner(opts).numberbox(opts);
	};
	function doSpin(target, down) {
		var opts = $.data(target, 'numberspinner').options;
		var v = parseFloat($(target).val() || opts.value) || 0;
		if (down == true) {
			v -= opts.increment;
		} else {
			v += opts.increment;
		}
		$(target).val(v).numberbox('fix');
	};
	$.fn.numberspinner = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.numberspinner.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.spinner(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'numberspinner');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'numberspinner', {
									options : $.extend({},
											$.fn.numberspinner.defaults,
											$.fn.numberspinner
													.parseOptions(this), options)
								});
					}
					create(this);
				});
	};
	$.fn.numberspinner.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], 'numberspinner').options;
			return $.extend(opts, {
						value : jq.val()
					});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						$(this).val(value).numberbox('fix');
					});
		}
	};
	$.fn.numberspinner.parseOptions = function(target) {
		return $.extend({}, $.fn.spinner.parseOptions(target), $.fn.numberbox
						.parseOptions(target), {});
	};
	$.fn.numberspinner.defaults = $.extend({}, $.fn.spinner.defaults,
			$.fn.numberbox.defaults, {
				spin : function(down) {
					doSpin(this, down);
				}
			});
})(jQuery);

/**
 * timespinner - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   spinner
 * 
 */
(function($) {
	function create(target) {
		var opts = $.data(target, 'timespinner').options;
		$(target).spinner(opts);
		$(target).unbind('.timespinner');
		$(target).bind('click.timespinner', function() {
					var start = 0;
					if (this.selectionStart != null) {
						start = this.selectionStart;
					} else {
						if (this.createTextRange) {
							var range = target.createTextRange();
							var s = document.selection.createRange();
							s.setEndPoint('StartToStart', range);
							start = s.text.length;
						}
					}
					if (start >= 0 && start <= 2) {
						opts.highlight = 0;
					} else {
						if (start >= 3 && start <= 5) {
							opts.highlight = 1;
						} else {
							if (start >= 6 && start <= 8) {
								opts.highlight = 2;
							}
						}
					}
					highlight(target);
				}).bind('blur.timespinner', function() {
					fixValue(target);
				});

		//wcj: fix input
		function getCaretPos(input) {
			try{
				if (document.selection && document.selection.createRange) {
					var range = document.selection.createRange();
					return range.getBookmark().charCodeAt(2) - 2;
				} else if ( input.setSelectionRange ) {
					return input.selectionStart;
				}
			} catch(e) {
				return 0;
			}
		};
		function setCaretPos(input, pos) {
			if (input.createTextRange) {
				var textRange = input.createTextRange();
				textRange.collapse(true);
				textRange.moveEnd("character", pos);
				textRange.moveStart("character", pos);
				textRange.select();
			} else if (input.setSelectionRange) {
				input.setSelectionRange(pos, pos);
			}
		};
		$(target).bind("keypress.timespinner", function(e) {
			//keycode : 0-9 48-57  - 45  : 58  space 32
			if ((e.which >= 48 && e.which <= 57) || e.which == 58) {
				setTimeout(function() {
					var input = e.target;
					var pos = getCaretPos(input);
					var text = $(input).val();
					if (e.which == 58) {
						if (pos != 3 && pos != 6) {
							text = text.substring(0, pos - 1) + text.substring(pos);
							pos--;
						}
					} else {
						if (pos == 3 || pos == 6) {
							text = text.substring(0, pos - 1) + (text.substring(pos, pos + 1) || ":")
									+ text.substring(pos - 1, pos) + text.substring(pos + 1);
							pos++;
						}
					}
					if (pos == text.length && text.length > (opts.showSeconds ? 8 : 5)) {
						text = text.substring(0, (opts.showSeconds ? 8 : 5));
					} else if (pos < text.length && text.length == (opts.showSeconds ? 9 : 6)) {
						text = text.substring(0, pos) + text.substring(pos + 1);
					}
					if (pos == 2 || (pos == 5 && opts.showSeconds)) {
						if (text.substring(pos, pos + 1) != ":") {
							text = text.substring(0, pos) + ":" + text.substring(pos);
						}
						pos++;
					}
					$(input).val(text);
					setCaretPos(input, pos);
				}, 0);
			} else {
				if ((e.ctrlKey == true && (e.which == 99 || e.which == 118))
						|| e.which == 0 || e.which == 8) {
					return true;
				} else {
					return false;
				}
			}
		});
	};
	function highlight(target) {
		var opts = $.data(target, 'timespinner').options;
		var start = 0, end = 0;
		if (opts.highlight == 0) {
			start = 0;
			end = 2;
		} else {
			if (opts.highlight == 1) {
				start = 3;
				end = 5;
			} else {
				if (opts.highlight == 2) {
					start = 6;
					end = 8;
				}
			}
		}
		if (target.selectionStart != null) {
			target.setSelectionRange(start, end);
		} else {
			if (target.createTextRange) {
				var range = target.createTextRange();
				range.collapse();
				range.moveEnd('character', end);
				range.moveStart('character', start);
				range.select();
			}
		}
		$(target).focus();
	};
	function parseTime(target, value) {
		var opts = $.data(target, 'timespinner').options;
		if (!value) {
			return null;
		}
		var vv = value.split(opts.separator);
		//wcj:
		/*
		for (var i = 0; i < vv.length; i++) {
			if (isNaN(vv[i])) {
				return null;
			}
		}
		while (vv.length < 3) {
			vv.push(0);
		}
		return new Date(1900, 0, 0, vv[0], vv[1], vv[2]);
		 */
		return new Date(1900, 0, 0, vv[0] || 0, vv[1] || 0, vv[2] || 0);
	};
	function fixValue(target) {
		var opts = $.data(target, 'timespinner').options;
		var value = $(target).val();
		var time = parseTime(target, value);
		if (!time) {
			time = parseTime(target, opts.value);
		}
		if (!time) {
			opts.value = '';
			$(target).val('');
			return;
		}
		var minTime = parseTime(target, opts.min);
		var maxTime = parseTime(target, opts.max);
		if (minTime && minTime > time) {
			time = minTime;
		}
		if (maxTime && maxTime < time) {
			time = maxTime;
		}
		var tt = [formatNumber(time.getHours()), formatNumber(time.getMinutes())];
		if (opts.showSeconds) {
			tt.push(formatNumber(time.getSeconds()));
		}
		var val = tt.join(opts.separator);
		opts.value = val;
		$(target).val(val);
		function formatNumber(value) {
			return (value < 10 ? '0' : '') + value;
		};
	};
	function doSpin(target, down) {
		var opts = $.data(target, 'timespinner').options;
		var val = $(target).val();
		if (val == '') {
			val = [0, 0, 0].join(opts.separator);
		}
		var vv = val.split(opts.separator);
		for (var i = 0; i < vv.length; i++) {
			vv[i] = parseInt(vv[i], 10);
		}
		if (down == true) {
			vv[opts.highlight] -= opts.increment;
		} else {
			vv[opts.highlight] += opts.increment;
		}
		$(target).val(vv.join(opts.separator));
		fixValue(target);
		highlight(target);
	};
	$.fn.timespinner = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.timespinner.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.spinner(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'timespinner');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'timespinner', {
									options : $
											.extend(
													{},
													$.fn.timespinner.defaults,
													$.fn.timespinner
															.parseOptions(this),
													options)
								});
						create(this);
					}
				});
	};
	$.fn.timespinner.methods = {
		options : function(jq) {
			var opts = $.data(jq[0], 'timespinner').options;
			return $.extend(opts, {
						value : jq.val()
					});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						$(this).val(value);
						fixValue(this);
					});
		},
		getHours : function(jq) {
			var opts = $.data(jq[0], 'timespinner').options;
			var vv = jq.val().split(opts.separator);
			return parseInt(vv[0], 10) || 0;
		},
		getMinutes : function(jq) {
			var opts = $.data(jq[0], 'timespinner').options;
			var vv = jq.val().split(opts.separator);
			return parseInt(vv[1], 10) || 0;
		},
		getSeconds : function(jq) {
			var opts = $.data(jq[0], 'timespinner').options;
			var vv = jq.val().split(opts.separator);
			return parseInt(vv[2], 10) || 0;
		}
	};
	$.fn.timespinner.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.spinner.parseOptions(target), {
					separator : t.attr('separator'),
					showSeconds : (t.attr('showSeconds') ? t
							.attr('showSeconds') == 'true' : undefined),
					highlight : (parseInt(t.attr('highlight')) || undefined)
				});
	};
	$.fn.timespinner.defaults = $.extend({}, $.fn.spinner.defaults, {
				separator : ':',
				showSeconds : false,
				highlight : 0,
				spin : function(down) {
					doSpin(this, down);
				}
			});
})(jQuery);

/**
 * datagrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	resizable
 * 	linkbutton
 * 	pagination
 * 
 */
(function($) {
	$.extend(Array.prototype, {
				indexOf : function(o) {
					for (var i = 0, len = this.length; i < len; i++) {
						if (this[i] == o) {
							return i;
						}
					}
					return -1;
				},
				remove : function(o) {
					var idx = this.indexOf(o);
					if (idx != -1) {
						this.splice(idx, 1);
					}
					return this;
				},
				removeById : function(field, id) {
					for (var i = 0, len = this.length; i < len; i++) {
						if (this[i][field] == id) {
							this.splice(i, 1);
							return this;
						}
					}
					return this;
				}
			});
	function setSize(target, param) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		if (param) {
			if (param.width) {
				opts.width = param.width;
			}
			if (param.height) {
				opts.height = param.height;
			}
		}
		if (opts.fit == true) {
			var p = panel.panel('panel').parent();
			opts.width = p.width();
			opts.height = p.height();
		}
		panel.panel('resize', {
					width : opts.width,
					height : opts.height
				});
	};
	function fitGridSize(target) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		var width = panel.width();
		var height = panel.height();
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		var gridHeader1 = gridView1.children('div.datagrid-header');
		var gridHeader2 = gridView2.children('div.datagrid-header');
		var gridTable1 = gridHeader1.find('table');
		var gridTable2 = gridHeader2.find('table');
		gridView.width(width);
		var innerHeader = gridHeader1.children('div.datagrid-header-inner').show();
		gridView1.width(innerHeader.find('table').width());
		if (!opts.showHeader) {
			innerHeader.hide();
		}
		gridView2.width(width - gridView1.outerWidth());
		gridView1
				.children('div.datagrid-header,div.datagrid-body,div.datagrid-footer')
				.width(gridView1.width());
		gridView2
				.children('div.datagrid-header,div.datagrid-body,div.datagrid-footer')
				.width(gridView2.width());
		var hh;
		gridHeader1.css('height', '');
		gridHeader2.css('height', '');
		gridTable1.css('height', '');
		gridTable2.css('height', '');
		hh = Math.max(gridTable1.height(), gridTable2.height());
		gridTable1.height(hh);
		gridTable2.height(hh);
		if ($.boxModel == true) {
			gridHeader1.height(hh - (gridHeader1.outerHeight() - gridHeader1.height()));
			gridHeader2.height(hh - (gridHeader2.outerHeight() - gridHeader2.height()));
		} else {
			gridHeader1.height(hh);
			gridHeader2.height(hh);
		}
		if (opts.height != 'auto') {
			var fixedHeight = height
					- gridView2.children('div.datagrid-header').outerHeight(true)
					- gridView2.children('div.datagrid-footer').outerHeight(true)
					- panel.children('div.datagrid-toolbar').outerHeight(true)
					- panel.children('div.datagrid-pager').outerHeight(true);
			gridView1.children('div.datagrid-body').height(fixedHeight);
			gridView2.children('div.datagrid-body').height(fixedHeight);
		}
		gridView.height(gridView2.height());
		gridView2.css('left', gridView1.outerWidth());
	};
	function fixRowHeight(target, rowIndex) {
		//wcj:
		if (! $.data(target, 'datagrid')) {
			return;
		}
		var opts = $.data(target, 'datagrid').options;
		//wcj: use fixed row height
		if (! opts.autoRowHeight) {
			return;
		}
		var rows = $.data(target, 'datagrid').data.rows;
		var panel = $.data(target, 'datagrid').panel;
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		if (!gridView1.find('div.datagrid-body-inner').is(':empty')) {
			if (rowIndex >= 0) {
				alignRowHeight(rowIndex);
			} else {
				for (var i = 0; i < rows.length; i++) {
					alignRowHeight(i);
				}
				if (opts.showFooter) {
					var footerRows = $(target).datagrid('getFooterRows') || [];
					//wcj:
//					var c1 = gridView1.children('div.datagrid-footer');
//					var c2 = gridView2.children('div.datagrid-footer');
					var c1 = gridView1.children('div.datagrid-footer').children('div.datagrid-footer-inner');
					var c2 = gridView2.children('div.datagrid-footer').children('div.datagrid-footer-inner');
					for (var i = 0; i < footerRows.length; i++) {
						alignRowHeight(i, c1, c2);
					}
					fitGridSize(target);
				}
			}
		}
		if (opts.height == 'auto') {
			var gridBody1 = gridView1.children('div.datagrid-body');
			var gridBody2 = gridView2.children('div.datagrid-body');
			var fullHeight = 0;
			var width = 0;
			gridBody2.children().each(function() {
						var c = $(this);
						if (c.is(':visible')) {
							fullHeight += c.outerHeight();
							if (width < c.outerWidth()) {
								width = c.outerWidth();
							}
						}
					});
			if (width > gridBody2.width()) {
				fullHeight += 18;
			}
			gridBody1.height(fullHeight);
			gridBody2.height(fullHeight);
			gridView.height(gridView2.height());
		}
		gridView2.children('div.datagrid-body').triggerHandler('scroll');
		function alignRowHeight(rowIndex, c1, c2) {
			//wcj:
//			c1 = c1 || gridView1;
//			c2 = c2 || gridView2;
			c1 = c1 || gridView1.children('.datagrid-body').children('.datagrid-body-inner');
			c2 = c2 || gridView2.children('.datagrid-body');
			//wcj: use eq
//			var tr1 = c1.find('tr[datagrid-row-index=' + rowIndex + ']');
//			var tr2 = c2.find('tr[datagrid-row-index=' + rowIndex + ']');
			var tr1 = c1.children('table').children('tbody').children('tr:eq(' + rowIndex + ')');
			var tr2 = c2.children('table').children('tbody').children('tr:eq(' + rowIndex + ')');
			tr1.css('height', '');
			tr2.css('height', '');
			var height = Math.max(tr1.height(), tr2.height());
			tr1.css('height', height);
			tr2.css('height', height);
		};
	};
	function wrapGrid(target, rownumbers) {
		function getColumns(thead) {
			var columns = [];
			$('tr', thead).each(function() {
				var cols = [];
				$('th', this).each(function() {
							var th = $(this);
							var col = {
								title : th.html(),
								align : th.attr('align') || 'left',
								sortable : th.attr('sortable') == 'true' || false,
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
		var wrap = $('<div class="datagrid-wrap">'
				//wcj:
				//+ '<div class="datagrid-view">'
				+ '<div class="datagrid-view show-selection">'
				+ '<div class="datagrid-view1">'
				+ '<div class="datagrid-header">'
				+ '<div class="datagrid-header-inner"></div>' + '</div>'
				+ '<div class="datagrid-body">'
				+ '<div class="datagrid-body-inner"></div>' + '</div>'
				+ '<div class="datagrid-footer">'
				+ '<div class="datagrid-footer-inner"></div>' + '</div>'
				+ '</div>' + '<div class="datagrid-view2">'
				+ '<div class="datagrid-header">'
				+ '<div class="datagrid-header-inner"></div>' + '</div>'
				+ '<div class="datagrid-body"></div>'
				+ '<div class="datagrid-footer">'
				+ '<div class="datagrid-footer-inner"></div>' + '</div>'
				+ '</div>' + '<div class="datagrid-resize-proxy"></div>'
				+ '</div>' + '</div>').insertAfter(target);
		wrap.panel({
					doSize : false
				});
		wrap.panel('panel').addClass('datagrid').bind('_resize',
				function(e, param) {
					var opts = $.data(target, 'datagrid').options;
					if (opts.fit == true || param) {
						setSize(target);
						setTimeout(function() {
									if ($.data(target, 'datagrid')) {
										fixColumnSize(target);
									}
								}, 0);
					}
					return false;
				});
		$(target).hide().appendTo(wrap.children('div.datagrid-view'));
		var frozenColumns = getColumns($('thead[frozen=true]', target));
		var columns = getColumns($('thead[frozen!=true]', target));
		return {
			panel : wrap,
			frozenColumns : frozenColumns,
			columns : columns
		};
	};
	function getGridData(target) {
		var data = {
			total : 0,
			rows : []
		};
		var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
		$(target).find('tbody tr').each(function() {
					data.total++;
					var col = {};
					for (var i = 0; i < fields.length; i++) {
						col[fields[i]] = $('td:eq(' + i + ')', this).html();
					}
					data.rows.push(col);
				});
		return data;
	};
	function init(target) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		//wcj: don't init panel again
		//panel.panel($.extend({}, opts, {
		$.extend(panel.panel('options'), opts, {
					doSize : false,
					onResize : function(width, height) {
						setTimeout(function() {
									if ($.data(target, 'datagrid')) {
										fitGridSize(target);
										fitColumns(target);
										opts.onResize.call(panel, width, height);
									}
								}, 0);
					},
					onExpand : function() {
						fitGridSize(target);
						fixRowHeight(target);
						opts.onExpand.call(panel);
					}
				//}));
				});
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		var innerHeader1 = gridView1.children('div.datagrid-header')
				.children('div.datagrid-header-inner');
		var innerHeader2 = gridView2.children('div.datagrid-header')
				.children('div.datagrid-header-inner');
		buildGridHeader(innerHeader1, opts.frozenColumns, true);
		buildGridHeader(innerHeader2, opts.columns, false);
		innerHeader1.css('display', opts.showHeader ? 'block' : 'none');
		innerHeader2.css('display', opts.showHeader ? 'block' : 'none');
		gridView1.find('div.datagrid-footer-inner').css('display',
				opts.showFooter ? 'block' : 'none');
		gridView2.find('div.datagrid-footer-inner').css('display',
				opts.showFooter ? 'block' : 'none');
		//$('div.datagrid-toolbar', panel).remove();
		if (opts.toolbar) {
			if(typeof opts.toolbar == 'string'){
				$(opts.toolbar).addClass('datagrid-toolbar').prependTo(target);
				$(opts.toolbar).show();
			}else{
				$('div.datagrid-toolbar',panel).remove();
				var tb = $('<div class="datagrid-toolbar"></div>').prependTo(panel);
				for (var i = 0; i < opts.toolbar.length; i++) {
					var btn = opts.toolbar[i];
					if (btn == '-') {
						$('<div class="datagrid-btn-separator"></div>')
								.appendTo(tb);
					} else {
					var tool = $('<a href="javascript:void(0)" '+(btn.title?('title="'+btn.title+'"'):'')+'></a>');
						tool[0].onclick = eval(btn.handler || function() {
						});
						tool.css('float', 'left').appendTo(tb).linkbutton($.extend(
								{}, btn, {
									plain : true
								}));
					}
				}
			}
		}else{
			$('div.datagrid-toolbar',panel).remove();
		}
		$('div.datagrid-pager', panel).remove();
		if (opts.pagination) {
			var pager = $('<div class="datagrid-pager"></div>').appendTo(panel);
			//wcj: keep pagination opts in grid options
			pager.pagination($.extend({}, opts, {
						pageNumber : opts.pageNumber,
						pageSize : opts.pageSize,
						pageList : opts.pageList,
						onSelectPage : function(pageNumber, pageSize) {
							opts.pageNumber = pageNumber;
							opts.pageSize = pageSize;
							request(target);
						}
					}));
			opts.pageSize = pager.pagination('options').pageSize;
		}
		function buildGridHeader(header, columns, frozen) {
			if (!columns) {
				return;
			}
			$(header).show();
			$(header).empty();
			var t = $('<table border="0" cellspacing="0" cellpadding="0"><tbody></tbody></table>')
					.appendTo(header);
			for (var i = 0; i < columns.length; i++) {
				var tr = $('<tr></tr>').appendTo($('tbody', t));
				var column = columns[i];
				for (var j = 0; j < column.length; j++) {
					var col = column[j];
					var tdHTML = '';
					if (col.rowspan) {
						tdHTML += 'rowspan="' + col.rowspan + '" ';
					}
					if (col.colspan) {
						tdHTML += 'colspan="' + col.colspan + '" ';
					}
					var td = $('<td ' + tdHTML + '></td>').appendTo(tr);
					if (col.checkbox) {
						td.attr('field', col.field);
						$('<div class="datagrid-header-check"></div>')
								.html('<input type="checkbox"/>')
								.appendTo(td);
					} else {
						if (col.field) {
							td.attr('field', col.field);
							//wcj:
							if (col.sortable) {
								td.addClass("datagrid-sortable");
							}
							td
									.append('<div class="datagrid-cell"><span></span><span class="datagrid-sort-icon"></span></div>');
							//wcj:
							//$('span', td).html(col.title);
							if (col.sortable) {
								$('span', td).html('<a href="#">' + col.title + '</a>');
							} else {
								$('span', td).html(col.title);
							}
							$('span.datagrid-sort-icon', td).html('&nbsp;');
							var cell = td.find('div.datagrid-cell');
							if(col.resizable==false){
								cell.attr('resizable','false');
							}
							col.boxWidth = $.boxModel ? (col.width - (cell
									.outerWidth() - cell.width())) : col.width;
							//wcj:
							//cell.width(col.boxWidth);
							cell.width(col.boxWidth || Math.max(cell.width(), 80));
							cell.css('text-align', (col.align || 'left'));
							//wcj:
							col.header = cell;
						} else {
							$('<div class="datagrid-cell-group"></div>')
									.html(col.title).appendTo(td);
						}
					}
					if (col.hidden) {
						td.hide();
					}
				}
			}
			if (frozen && opts.rownumbers) {
				var td = $('<td rowspan="'
						+ opts.frozenColumns.length
						+ '"><div class="datagrid-header-rownumber"></div></td>');
				if ($('tr', t).length == 0) {
					td.wrap('<tr></tr>').parent().appendTo($('tbody', t));
				} else {
					td.prependTo($('tr:first', t));
				}
			}
		};
	};
	function resetGridEvent(target) {
		var panel = $.data(target, 'datagrid').panel;
		var opts = $.data(target, 'datagrid').options;
		var data = $.data(target, 'datagrid').data;
		//wcj: use children
//		var gridBody = panel.find('div.datagrid-body');
//		gridBody.find('tr[datagrid-row-index]').unbind('.datagrid')/*.bind(
		panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
				.find('>.datagrid-body,>.datagrid-body>.datagrid-body-inner').children('table').children('tbody')
				.children('tr').unbind('.datagrid')/*.bind(
		//wcj: use css hover
				'mouseenter.datagrid', function() {
					var rowIndex = $(this).attr('datagrid-row-index');
					gridBody.find('tr[datagrid-row-index=' + rowIndex + ']')
							.addClass('datagrid-row-over');
				}).bind('mouseleave.datagrid', function() {
			var rowIndex = $(this).attr('datagrid-row-index');
			gridBody.find('tr[datagrid-row-index=' + rowIndex + ']')
					.removeClass('datagrid-row-over');
		})*/.bind('click.datagrid', function() {
					//wcj: int
					var rowIndex = parseInt($(this).attr('datagrid-row-index'));
					if (opts.singleSelect == true) {
						clearSelections(target);
						selectRow(target, rowIndex);
					} else {
						if ($(this).hasClass('datagrid-row-selected')) {
							unselectRow(target, rowIndex);
						} else {
							selectRow(target, rowIndex);
						}
					}
					if (opts.onClickRow) {
						opts.onClickRow.call(target, rowIndex, data.rows[rowIndex]);
					}
				}).bind('dblclick.datagrid', function() {
					//wcj: int
					var rowIndex = parseInt($(this).attr('datagrid-row-index'));
					if (opts.onDblClickRow) {
						opts.onDblClickRow.call(target, rowIndex, data.rows[rowIndex]);
					}
				}).bind('contextmenu.datagrid', function(e) {
					//wcj: int
					var rowIndex = parseInt($(this).attr('datagrid-row-index'));
					if (opts.onRowContextMenu) {
						opts.onRowContextMenu.call(target, e, rowIndex, data.rows[rowIndex]);
					}
				});
		//wcj: use children
		//gridBody.find('td[field]').unbind('.datagrid').bind('click.datagrid',
		panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
				.find('>.datagrid-body,>.datagrid-body>.datagrid-body-inner').children('table').children('tbody')
				.children('tr').children('td').unbind('.datagrid').bind('click.datagrid',
				function() {
					//wcj: int
					var rowIndex = parseInt($(this).parent().attr('datagrid-row-index'));
					var field = $(this).attr("field");
					var value = data.rows[rowIndex][field];
					opts.onClickCell.call(target, rowIndex, field, value);
				}).bind('dblclick.datagrid', function() {
					//wcj: int
					var rowIndex = parseInt($(this).parent().attr('datagrid-row-index'));
					var field = $(this).attr('field');
					var value = data.rows[rowIndex][field];
					opts.onDblClickCell.call(target, rowIndex, field, value);
				});
		//wcj: use children
		//gridBody.find('div.datagrid-cell-check input[type=checkbox]')
		panel.children(".datagrid-view").children(".datagrid-view1") 
				.find('div.datagrid-cell-check input[type=checkbox]')
				.unbind('.datagrid').bind('click.datagrid', function(e) {
					//wcj: wait checkbox to be checked first when click triggered by program
					setTimeout(function() {
						//wcj: int
						var rowIndex = parseInt($(e.target).parent().parent().parent()
								.attr('datagrid-row-index'));
						if (opts.singleSelect) {
							clearSelections(target);
							selectRow(target, rowIndex);
						} else {
							if ($(e.target).attr('checked')) {
								//wcj: for select multi rows
								if (e.shiftKey) {
									$(target).data("shiftKey", true);
								} else {
									$(target).data("shiftKey", false);
								}
								selectRow(target, rowIndex);
								$(target).removeData("shiftKey");
							} else {
								unselectRow(target, rowIndex);
							}
						}
					}, 0);
					e.stopPropagation();
				});
		//wcj:
		panel.children(".datagrid-view").children(".datagrid-view1") 
				.find('div.datagrid-cell-check').parent()
				.unbind('.datagrid').bind('click.datagrid', function(e) {
					$(this).find("input").click();
					e.stopPropagation();
				});
	};
	function setProperties(target) {
		var panel = $.data(target, 'datagrid').panel;
		var opts = $.data(target, 'datagrid').options;
		//wcj:
		//var gridHeader = panel.find('div.datagrid-header');
		var gridHeader = panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
				.children('.datagrid-header');
		gridHeader.find('td:has(div.datagrid-cell)').unbind('.datagrid').bind(
				'mouseenter.datagrid', function() {
					//wcj:
					if (opts.resizing) {
						return;
					}
					$(this).addClass('datagrid-header-over');
				}).bind('mouseleave.datagrid', function() {
					$(this).removeClass('datagrid-header-over');
				}).bind('contextmenu.datagrid', function(e) {
					var field = $(this).attr('field');
					opts.onHeaderContextMenu.call(target, e, field);
				});
		//wcj:
		//gridHeader.find('div.datagrid-cell').unbind('.datagrid').bind(
		gridHeader.find('div.datagrid-cell a').unbind('.datagrid').bind(
				'click.datagrid', function(e) {
					//wcj:
					if (opts.resizing) {
						return;
					}
					var $this = $(this).closest(".datagrid-cell");
					var field = $this.parent().attr('field');
					var opt = getColumnOption(target, field);
					if (!opt.sortable) {
						return;
					}
					//wcj: multi sort
					if (e.ctrlKey) {
						if (! opts.sortCount) {
							opts.sortCount = 0;
							opts.sortName = "";
							opts.sortOrder = "";
							gridHeader.find('div.datagrid-cell')
									.removeClass('datagrid-sort-asc datagrid-sort-desc');
						}
						if ($this.children('.datagrid-sort-count').size() == 0) {
							opts.sortCount++;
							if (opts.sortName) {
								opts.sortName += "," + field;
							} else {
								opts.sortName = field;
							}
							if ($this.hasClass('datagrid-sort-asc')) {
								opts.sortName += ' desc';
								$this.addClass('datagrid-sort-desc');
							} else {
								opts.sortName += ' asc';
								$this.addClass('datagrid-sort-asc');
							}
							$this.append("<span class='datagrid-sort-count'>" + opts.sortCount + "</span>");
						} else {
							opts.sortName = "," + opts.sortName + ",";
							if ($this.hasClass('datagrid-sort-asc')) {
								var s = "," + field + " asc,";
								var i = opts.sortName.indexOf(s);
								opts.sortName = opts.sortName.substring(0, i) + "," + field + " desc,"
										+ opts.sortName.substring(i + s.length);
								$this.removeClass('datagrid-sort-asc').addClass('datagrid-sort-desc');
							} else if ($this.hasClass('datagrid-sort-desc')) {
								var s = "," + field + " desc,";
								var i = opts.sortName.indexOf(s);
								opts.sortName = opts.sortName.substring(0, i + 1)
										+ opts.sortName.substring(i + s.length);
								opts.sortCount--;
								var count = parseInt($this.find(".datagrid-sort-count").html());
								gridHeader.find('.datagrid-sort-count').each(function() {
									if (parseInt($this.html()) > count) {
										$this.html(parseInt($this.html()) - 1);
									}
								});
								$this.removeClass('datagrid-sort-desc');
								$this.find(".datagrid-sort-count").remove();
							}
							opts.sortName = opts.sortName.substring(1, opts.sortName.length - 1);
							if (opts.sortName == ',') {
								opts.sortName = undefined;
							}
						}
					} else {
						//wcj:
						if (opts.sortCount) {
							opts.sortName = "";
							opts.sortOrder = "asc";
							gridHeader.find('div.datagrid-cell')
									.removeClass('datagrid-sort-asc datagrid-sort-desc');
						}
						opts.sortCount = undefined;
						gridHeader.find('.datagrid-sort-count').remove();
						
						opts.sortName = field;
						opts.sortOrder = 'asc';
						var c = 'datagrid-sort-asc';
						if ($this.hasClass('datagrid-sort-asc')) {
							c = 'datagrid-sort-desc';
							opts.sortOrder = 'desc';
						} else if ($this.hasClass('datagrid-sort-desc')) {
							c = '';
							opts.sortName = undefined;
							opts.sortOrder = 'asc';
						}
						gridHeader
						.find('div.datagrid-cell')
						.removeClass('datagrid-sort-asc datagrid-sort-desc');
						$this.addClass(c);
					}
					/*
					opts.sortName = field;
					opts.sortOrder = 'asc';
					var c = 'datagrid-sort-asc';
					if ($(this).hasClass('datagrid-sort-asc')) {
						c = 'datagrid-sort-desc';
						opts.sortOrder = 'desc';
					}
					gridHeader
					.find('div.datagrid-cell')
					.removeClass('datagrid-sort-asc datagrid-sort-desc');
					$(this).addClass(c);
					*/
					if (opts.remoteSort) {
						request(target);
					} else {
						var data = $.data(target, 'datagrid').data;
						renderGrid(target, data);
					}
					if (opts.onSortColumn) {
						opts.onSortColumn.call(target, opts.sortName, opts.sortOrder);
					}
					//wcj: return false
					return false;
				});
		//wcj:
		//gridHeader.find('input[type=checkbox]').unbind('.datagrid').bind(
		panel.children('.datagrid-view').children('.datagrid-view1')
				.children('.datagrid-header').find('input[type=checkbox]')
				.unbind('.datagrid').bind(
				'click.datagrid', function() {
					if (opts.singleSelect) {
						return false;
					}
					if ($(this).attr('checked')) {
						selectAll(target);
					} else {
						unselectAll(target);
					}
				});
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		gridView2.children('div.datagrid-body').unbind('.datagrid').bind(
				'scroll.datagrid', function() {
					gridView1.children('div.datagrid-body').scrollTop($(this)
							.scrollTop());
					gridView2.children('div.datagrid-header').scrollLeft($(this)
							.scrollLeft());
					gridView2.children('div.datagrid-footer').scrollLeft($(this)
							.scrollLeft());
				});
		gridHeader.find('div.datagrid-cell').each(function() {
			$(this).resizable({
				handles : 'e',
				disabled : ($(this).attr('resizable') ? $(this)
						.attr('resizable') == 'false' : false),
				minWidth : 25,
				onStartResize : function(e) {
					//wcj:
					opts.resizing = true;
					gridView.children('div.datagrid-resize-proxy').css({
								left : e.pageX - $(panel).offset().left - 1,
								display : 'block'
							});
					//proxy.css('display', 'block');
				},
				onResize : function(e) {
					gridView.children('div.datagrid-resize-proxy').css({
								display : 'block',
								left : e.pageX - $(panel).offset().left - 1
							});
					return false;
				},
				onStopResize : function(e) {
					var field = $(this).parent().attr('field');
					var col = getColumnOption(target, field);
					col.width = $(this).outerWidth();
					col.boxWidth = $.boxModel == true ? $(this).width() : $(this)
							.outerWidth();
					fixColumnSize(target, field);
					fitColumns(target);
					var gridView2 = panel.find('div.datagrid-view2');
					gridView2.find('div.datagrid-header').scrollLeft(gridView2
							.find('div.datagrid-body').scrollLeft());
					gridView.children('div.datagrid-resize-proxy')
							.css('display', 'none');
					opts.onResizeColumn.call(target, field, col.width);
					//wcj:
					//use setTimetout to let click event run first, it will check resizing status to avoid resorting
					setTimeout(function() {
						opts.resizing = false;
					}, 0);
				}
			});
		});
		gridView1.children('div.datagrid-header').find('div.datagrid-cell').resizable({
			onStopResize : function(e) {
				var field = $(this).parent().attr('field');
				var col = getColumnOption(target, field);
				col.width = $(this).outerWidth();
				col.boxWidth = $.boxModel == true ? $(this).width() : $(this)
						.outerWidth();
				fixColumnSize(target, field);
				var gridView2 = panel.find('div.datagrid-view2');
				gridView2.find('div.datagrid-header').scrollLeft(gridView2
						.find('div.datagrid-body').scrollLeft());
				gridView.children('div.datagrid-resize-proxy')
						.css('display', 'none');
				fitGridSize(target);
				fitColumns(target);
				opts.onResizeColumn.call(target, field, col.width);
			}
		});
	};
	function fitColumns(target) {
		var opts = $.data(target, 'datagrid').options;
		if (!opts.fitColumns) {
			return;
		}
		var panel = $.data(target, 'datagrid').panel;
		var gridHeader2 = panel.find('div.datagrid-view2 div.datagrid-header');
		var visableWidth = 0;
		var visableColumn;
		var fields = getColumnFields(target, false);
		for (var i = 0; i < fields.length; i++) {
			var col = getColumnOption(target, fields[i]);
			if (!col.hidden && !col.checkbox) {
				visableWidth += col.width;
				visableColumn = col;
			}
		}
		var innerHeader = gridHeader2.children('div.datagrid-header-inner').show();
		var fullWidth = gridHeader2.width() - gridHeader2.find('table').width() - opts.scrollbarSize;
		var rate = fullWidth/visableWidth;
		if (!opts.showHeader) {
			innerHeader.hide();
		}
		for (var i = 0; i < fields.length; i++) {
			var col = getColumnOption(target, fields[i]);
			if(!col.hidden&&!col.checkbox){
				var width = Math.floor(col.width * rate);
				fitColumnWidth(col, width);
				fullWidth-=width;
			}
		}
		fixColumnSize(target);
		
		if (fullWidth) {
			fitColumnWidth(visableColumn, fullWidth);
			
			fixColumnSize(target, visableColumn.field);
		}
		function fitColumnWidth(col, width) {
			col.width += width;
			col.boxWidth += width;
			gridHeader2.find('td[field=' + col.field + '] div.datagrid-cell')
					.width(col.boxWidth);
		};
	};
	function fixColumnSize(target, cell) {
		var panel = $.data(target, 'datagrid').panel;
		var footer = panel.find('div.datagrid-body,div.datagrid-footer');
		if (cell) {
			fix(cell);
		} else {
			panel.find('div.datagrid-header td[field]').each(function() {
						fix($(this).attr('field'));
					});
		}
		fixMergedCellsSize(target);
		setTimeout(function() {
					fixRowHeight(target);
					fixEditorSize(target);
				}, 0);
		function fix(cell) {
			var col = getColumnOption(target, cell);
			//wcj: bug fix
			if (! col) {
				return;
			}
			footer.find('td[field=' + cell + ']').each(function() {
						var td = $(this);
						var colspan = td.attr('colspan') || 1;
						if (colspan == 1) {
							td.find('div.datagrid-cell').width(col.boxWidth);
							td.find('div.datagrid-editable').width(col.width);
						}
					});
		};
	};
	function fixMergedCellsSize(target) {
		var panel = $.data(target, 'datagrid').panel;
		var gridHeader = panel.find('div.datagrid-header');
		panel.find('div.datagrid-body td.datagrid-td-merged').each(function() {
					var td = $(this);
					var colspan = td.attr('colspan') || 1;
					var field = td.attr('field');
					var tdEl = gridHeader.find('td[field=' + field + ']');
					var width = tdEl.width();
					for (var i = 1; i < colspan; i++) {
						tdEl = tdEl.next();
						width += tdEl.outerWidth();
					}
					var cell = td.children('div.datagrid-cell');
					if ($.boxModel == true) {
						cell.width(width - (cell.outerWidth() - cell.width()));
					} else {
						cell.width(width);
					}
				});
	};
	function fixEditorSize(target) {
		//wcj: bug when dbl click ajax tab to close
		if (! $.data(target, 'datagrid')) {
			return;
		}
		var panel = $.data(target, 'datagrid').panel;
		panel.find('div.datagrid-editable').each(function() {
					var ed = $.data(this, 'datagrid.editor');
					if (ed.actions.resize) {
						ed.actions.resize(ed.target, $(this).width());
					}
				});
	};
	function getColumnOption(target, field) {
		var opts = $.data(target, 'datagrid').options;
		if (opts.columns) {
			for (var i = 0; i < opts.columns.length; i++) {
				var columns = opts.columns[i];
				for (var j = 0; j < columns.length; j++) {
					var col = columns[j];
					if (col.field == field) {
						return col;
					}
				}
			}
		}
		if (opts.frozenColumns) {
			for (var i = 0; i < opts.frozenColumns.length; i++) {
				var columns = opts.frozenColumns[i];
				for (var j = 0; j < columns.length; j++) {
					var col = columns[j];
					if (col.field == field) {
						return col;
					}
				}
			}
		}
		return null;
	};
	function getColumnFields(target, frozen) {
		var opts = $.data(target, 'datagrid').options;
		var columns = (frozen == true) ? (opts.frozenColumns || [[]]) : opts.columns;
		if (columns.length == 0) {
			return [];
		}
		var fields = [];
		function getFixedColspan(index) {
			var c = 0;
			var i = 0;
			while (true) {
				if (fields[i] == undefined) {
					if (c == index) {
						return i;
					}
					c++;
				}
				i++;
			}
		};
		function findColumnFields(r) {
			var ff = [];
			var c = 0;
			for (var i = 0; i < columns[r].length; i++) {
				var col = columns[r][i];
				if (col.field) {
					ff.push([c, col.field]);
				}
				c += parseInt(col.colspan || '1');
			}
			for (var i = 0; i < ff.length; i++) {
				ff[i][0] = getFixedColspan(ff[i][0]);
			}
			for (var i = 0; i < ff.length; i++) {
				var f = ff[i];
				fields[f[0]] = f[1];
			}
		};
		for (var i = 0; i < columns.length; i++) {
			findColumnFields(i);
		}
		return fields;
	};
	function renderGrid(target, data) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		data = opts.loadFilter.call(target,data);
		if (! data) {
			return;
		}
		var rows = data.rows;
		$.data(target, 'datagrid').data = data;
		if (data.footer) {
			$.data(target, 'datagrid').footer = data.footer;
		}
		if (!opts.remoteSort) {
			var opt = getColumnOption(target, opts.sortName);
			if (opt) {
				var sorter = opt.sorter || function(a, b) {
					return (a > b ? 1 : -1);
				};
				data.rows.sort(function(r1, r2) {
							return sorter(r1[opts.sortName], r2[opts.sortName])
									* (opts.sortOrder == 'asc' ? 1 : -1);
						});
			}
		}
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, target, rows);
		}
		opts.view.render.call(opts.view, target, gridView2.children('div.datagrid-body'),
				false);
		opts.view.render.call(opts.view, target, gridView1.children('div.datagrid-body')
						.children('div.datagrid-body-inner'), true);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, target, gridView2
							.find('div.datagrid-footer-inner'), false);
			opts.view.renderFooter.call(opts.view, target, gridView1
							.find('div.datagrid-footer-inner'), true);
			//wcj:
			fitGridSize(target);
		}
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, target);
		}
		opts.onLoadSuccess.call(target, data);
		var pager = panel.children('div.datagrid-pager');
		if (pager.length) {
			if (pager.pagination('options').total != data.total) {
				pager.pagination({
							total : data.total
						});
			}
		}
		fixRowHeight(target);
		resetGridEvent(target);
		gridView2.children('div.datagrid-body').triggerHandler('scroll');
		if (opts.idField) {
			for (var i = 0; i < rows.length; i++) {
				if (isSelected(rows[i])) {
					selectRecord(target, rows[i][opts.idField]);
				}
			}
		}
		function isSelected(row) {
			for (var i = 0; i < selectedRows.length; i++) {
				if (selectedRows[i][opts.idField] == row[opts.idField]) {
					selectedRows[i] = row;
					return true;
				}
			}
			return false;
		};
	};
	function getRowIndex(target, row) {
		var opts = $.data(target, 'datagrid').options;
		var rows = $.data(target, 'datagrid').data.rows;
		if (typeof row == 'object') {
			return rows.indexOf(row);
		} else {
			for (var i = 0; i < rows.length; i++) {
				if (rows[i][opts.idField] == row) {
					return i;
				}
			}
			return -1;
		}
	};
	function getSelected(target) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		var data = $.data(target, 'datagrid').data;
		if (opts.idField) {
			return $.data(target, 'datagrid').selectedRows;
		} else {
			var rowIndexs = [];
			//wcj: use children
//			$('div.datagrid-view2 div.datagrid-body tr.datagrid-row-selected',
//					panel).each(function() {
			panel.children('.datagrid-view').children('.datagrid-view2')
					.children('.datagrid-body').children('table').children('tbody')
					.children('.datagrid-row-selected').each(function() {
						var rowIndex = parseInt($(this).attr('datagrid-row-index'));
						rowIndexs.push(data.rows[rowIndex]);
					});
			return rowIndexs;
		}
	};
	function clearSelections(target) {
		unselectAll(target);
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		selectedRows.splice(0, selectedRows.length);
	};
	function selectAll(target) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		var data = $.data(target, 'datagrid').data;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		var rows = data.rows;
		var gridBody = panel.find('div.datagrid-body');
		gridBody.find('tr').addClass('datagrid-row-selected');
		var checkbox=gridBody.find('div.datagrid-cell-check input[type=checkbox]');
		$.fn.prop?checkbox.prop('checked',true):checkbox.attr('checked',true);
		for (var rowIndex = 0; rowIndex < rows.length; rowIndex++) {
			if (opts.idField) {
				(function() {
					var row = rows[rowIndex];
					for (var i = 0; i < selectedRows.length; i++) {
						if (selectedRows[i][opts.idField] == row[opts.idField]) {
							return;
						}
					}
					selectedRows.push(row);
				})();
			}
		}
		opts.onSelectAll.call(target, rows);
	};
	function unselectAll(target) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		var data = $.data(target, 'datagrid').data;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		var checkbox=panel.find('div.datagrid-body div.datagrid-cell-check input[type=checkbox]');
		$.fn.prop?checkbox.prop('checked',false):checkbox.attr('checked',false);
		//wcj: use children
//		$('div.datagrid-body tr.datagrid-row-selected',panel).removeClass('datagrid-row-selected');
		panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
				.find('>.datagrid-body,>.datagrid-body>.datagrid-body-inner').children('table').children('tbody')
				.children('.datagrid-row-selected').removeClass('datagrid-row-selected');
		if (opts.idField) {
			for (var rowIndex = 0; rowIndex < data.rows.length; rowIndex++) {
				selectedRows.removeById(opts.idField, data.rows[rowIndex][opts.idField]);
			}
		}
		opts.onUnselectAll.call(target, data.rows);
	};
	function selectRow(target, rowIndex) {
		var panel = $.data(target, 'datagrid').panel;
		var opts = $.data(target, 'datagrid').options;
		var data = $.data(target, 'datagrid').data;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		if (rowIndex < 0 || rowIndex >= data.rows.length) {
			return;
		}
		if (opts.singleSelect == true) {
			clearSelections(target);
		}
		//wcj: use children
		//var tr = $('div.datagrid-body tr[datagrid-row-index=' + rowIndex + ']', panel);
		var $datagridView = panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
		var tr = $datagridView.children('.datagrid-body')
				.children('table').children('tbody')
				.children('tr:eq(' + rowIndex + ')')
				.add($datagridView.children('.datagrid-body').children(".datagrid-body-inner")
						.children('table').children('tbody')
						.children('tr:eq(' + rowIndex + ')'));
		if (!tr.hasClass('datagrid-row-selected')) {
			tr.addClass('datagrid-row-selected');
			//wcj: use children
			//var ck = $('div.datagrid-cell-check input[type=checkbox]', tr);
			var ck = panel.children('.datagrid-view').children('.datagrid-view1')
					.children('.datagrid-body').children('.datagrid-body-inner').children('table').children('tbody')
					.children('tr:eq(' + rowIndex + ')').find('div.datagrid-cell-check input[type=checkbox]');
			$.fn.prop?ck.prop('checked',true):ck.attr('checked',true);
			if (opts.idField) {
				var row = data.rows[rowIndex];
				(function() {
					for (var i = 0; i < selectedRows.length; i++) {
						if (selectedRows[i][opts.idField] == row[opts.idField]) {
							return;
						}
					}
					selectedRows.push(row);
				})();
			}
		}
		opts.onSelect.call(target, rowIndex, data.rows[rowIndex]);
		var gridView2 = panel.find('div.datagrid-view2');
		var height = gridView2.find('div.datagrid-header').outerHeight();
		var gridBody = gridView2.find('div.datagrid-body');
		var top = tr.position().top - height;
		if (top <= 0) {
			gridBody.scrollTop(gridBody.scrollTop() + top);
		} else {
			if (top + tr.outerHeight() > gridBody.height() - 18) {
				gridBody.scrollTop(gridBody.scrollTop() + top + tr.outerHeight()
						- gridBody.height() + 18);
			}
		}
	};
	function selectRecord(target, id) {
		var opts = $.data(target, 'datagrid').options;
		var data = $.data(target, 'datagrid').data;
		if (opts.idField) {
			var index = -1;
			for (var i = 0; i < data.rows.length; i++) {
				if (data.rows[i][opts.idField] == id) {
					index = i;
					break;
				}
			}
			if (index >= 0) {
				selectRow(target, index);
			}
		}
	};
	function unselectRow(target, rowIndex) {
		var opts = $.data(target, 'datagrid').options;
		var panel = $.data(target, 'datagrid').panel;
		var data = $.data(target, 'datagrid').data;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		if (rowIndex < 0 || rowIndex >= data.rows.length) {
			return;
		}
		var gridBody = panel.find('div.datagrid-body');
		//wcj: use children
//		var tr = $('tr[datagrid-row-index=' + rowIndex + ']', gridBody);
//		var ck = $('tr[datagrid-row-index=' + rowIndex
//						+ '] div.datagrid-cell-check input[type=checkbox]', gridBody);
		var $datagridView = panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
		var tr = $datagridView.children('.datagrid-body')
				.children('table').children('tbody')
				.children('tr:eq(' + rowIndex + ')')
				.add($datagridView.children('.datagrid-body').children(".datagrid-body-inner")
						.children('table').children('tbody')
						.children('tr:eq(' + rowIndex + ')'));
		var ck = panel.children('.datagrid-view').children('.datagrid-view1')
				.children('.datagrid-body').children('.datagrid-body-inner').children('table').children('tbody')
				.children('tr:eq(' + rowIndex + ')').find('div.datagrid-cell-check input[type=checkbox]');
		tr.removeClass('datagrid-row-selected');
		$.fn.prop?ck.prop('checked',false):ck.attr('checked',false);
		var row = data.rows[rowIndex];
		if (opts.idField) {
			selectedRows.removeById(opts.idField, row[opts.idField]);
		}
		opts.onUnselect.call(target, rowIndex, row);
	};
	//wcj: override in walle
	function beginEdit(target, rowIndex) {
		return;
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
		createEditor(target, rowIndex);
		fixEditorSize(target);
		tr.find('div.datagrid-editable').each(function() {
					var field = $(this).parent().attr('field');
					var ed = $.data(this, 'datagrid.editor');
					ed.actions.setValue(ed.target, row[field]);
				});
		validateRow(target, rowIndex);
	};
	//wcj: override in walle
	function stopEdit(target, rowIndex, revert) {
		return;
		var opts = $.data(target, 'datagrid').options;
		var updatedRows = $.data(target, 'datagrid').updatedRows;
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		var tr = opts.editConfig.getTr(target, rowIndex);
		var row = opts.editConfig.getRow(target, rowIndex);
		if (!tr.hasClass('datagrid-row-editing')) {
			return;
		}
		if (!revert) {
			if (!validateRow(target, rowIndex)) {
				return;
			}
			var changed = false;
			var newValues = {};
			tr.find('div.datagrid-editable').each(function() {
				var field = $(this).parent().attr('field');
				var ed = $.data(this, 'datagrid.editor');
				var value = ed.actions.getValue(ed.target);
				if (row[field] != value) {
					row[field] = value;
					changed = true;
					newValues[field] = value;
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
		destroyEditor(target, rowIndex);
		$(target).datagrid('refreshRow', rowIndex);
		if (!revert) {
			opts.onAfterEdit.call(target, rowIndex, row, newValues);
		} else {
			opts.onCancelEdit.call(target, rowIndex, row);
		}
	};
	function getEditors(target, rowIndex) {
		var opts=$.data(target,'datagrid').options;
		var tr=opts.editConfig.getTr(target,rowIndex);
		var editors = [];
		tr.children('td').each(function() {
					//wcj: use children for better performance
					var cell = $(this).children('div.datagrid-editable');
					if (cell.length) {
						var ed = $.data(cell[0], 'datagrid.editor');
						editors.push(ed);
					}
				});
		return editors;
	};
	function getEditor(target, options) {
		var editors = getEditors(target, options.index);
		for (var i = 0; i < editors.length; i++) {
			if (editors[i].field == options.field) {
				return editors[i];
			}
		}
		return null;
	};
	//wcj: override in walle
	function createEditor(target, rowIndex) {
		return;
		var opts = $.data(target, 'datagrid').options;
		var tr = opts.editConfig.getTr(target, rowIndex);
		tr.children('td').each(function() {
			var cell = $(this).find('div.datagrid-cell');
			var field = $(this).attr('field');
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
					var html = cell.html();
					var width = cell.outerWidth();
					cell.addClass('datagrid-editable');
					if ($.boxModel == true) {
						cell.width(width - (cell.outerWidth() - cell.width()));
					}
					cell
							.html('<table border="0" cellspacing="0" cellpadding="1"><tr><td></td></tr></table>');
					cell.children('table').attr('align', col.align);
					cell.children('table').bind('click dblclick contextmenu',
						function(e){
							e.stopPropagation();
						});;
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
	//wcj: override in walle
	function destroyEditor(target, rowIndex) {
		return;
		var opts = $.data(target, 'datagrid').options;
		var tr = opts.editConfig.getTr(target, rowIndex);				
		tr.children('td').each(function() {
					var cell = $(this).find('div.datagrid-editable');
					if (cell.length) {
						var ed = $.data(cell[0], 'datagrid.editor');
						if (ed.actions.destroy) {
							ed.actions.destroy(ed.target);
						}
						$.removeData(cell[0], 'datagrid.editor');
						var width = cell.outerWidth();
						cell.removeClass('datagrid-editable');
						if ($.boxModel == true) {
							cell.width(width
									- (cell.outerWidth() - cell.width()));
						}
					}
				});
	};
	//wcj: override in walle
	function validateRow(target, rowIndex) {
		return $(target).datagrid("validateRow", rowIndex);
		var tr = $.data(target, 'datagrid').options.editConfig.getTr(target, rowIndex);
		//wcj:
		if (! tr) {
			return true;
		}
		if (!tr.hasClass('datagrid-row-editing')) {
			return true;
		}
		var vbox = tr.find('.validatebox-text');
		vbox.validatebox('validate');
		//wcj: mouseleave event will hide the tip
		//vbox.trigger('mouseleave');
		var invalid = tr.find('.validatebox-invalid');
		return invalid.length == 0;
	};
	function getChanges(target, type) {
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		var deletedRows = $.data(target, 'datagrid').deletedRows;
		var updatedRows = $.data(target, 'datagrid').updatedRows;
		if (!type) {
			var rows = [];
			rows = rows.concat(insertedRows);
			rows = rows.concat(deletedRows);
			rows = rows.concat(updatedRows);
			return rows;
		} else {
			if (type == 'inserted') {
				return insertedRows;
			} else {
				if (type == 'deleted') {
					return deletedRows;
				} else {
					if (type == 'updated') {
						return updatedRows;
					}
				}
			}
		}
		return [];
	};
	function deleteRow(target, rowIndex) {
		var opts = $.data(target, 'datagrid').options;
		var data = $.data(target, 'datagrid').data;
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		var deletedRows = $.data(target, 'datagrid').deletedRows;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		$(target).datagrid('cancelEdit', rowIndex);
		var row = data.rows[rowIndex];
		if (insertedRows.indexOf(row) >= 0) {
			insertedRows.remove(row);
		} else {
			deletedRows.push(row);
		}
		selectedRows.removeById(opts.idField, row[opts.idField]);
		opts.view.deleteRow.call(opts.view, target, rowIndex);
		if (opts.height == 'auto') {
			fixRowHeight(target);
		}
	};
	function insertRow(target, param) {
		var view = $.data(target, 'datagrid').options.view;
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		view.insertRow.call(view, target, param.index, param.row);
		resetGridEvent(target);
		insertedRows.push(param.row);
	};
	function appendRow(target, row) {
		var view = $.data(target, 'datagrid').options.view;
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		view.insertRow.call(view, target, null, row);
		resetGridEvent(target);
		insertedRows.push(row);
	};
	function resetOperation(target) {
		var data = $.data(target, 'datagrid').data;
		var rows = data.rows;
		var originalRows = [];
		for (var i = 0; i < rows.length; i++) {
			originalRows.push($.extend({}, rows[i]));
		}
		$.data(target, 'datagrid').originalRows = originalRows;
		$.data(target, 'datagrid').updatedRows = [];
		$.data(target, 'datagrid').insertedRows = [];
		$.data(target, 'datagrid').deletedRows = [];
	};
	function acceptChanges(target) {
		var data = $.data(target, 'datagrid').data;
		var ok = true;
		for (var i = 0, len = data.rows.length; i < len; i++) {
			if (validateRow(target, i)) {
				stopEdit(target, i, false);
			} else {
				ok = false;
			}
		}
		if (ok) {
			resetOperation(target);
		}
	};
	function rejectChanges(target) {
		var opts = $.data(target, 'datagrid').options;
		var originalRows = $.data(target, 'datagrid').originalRows;
		var insertedRows = $.data(target, 'datagrid').insertedRows;
		var deletedRows = $.data(target, 'datagrid').deletedRows;
		var selectedRows = $.data(target, 'datagrid').selectedRows;
		var data = $.data(target, 'datagrid').data;
		for (var i = 0; i < data.rows.length; i++) {
			stopEdit(target, i, true);
		}
		var records = [];
		for (var i = 0; i < selectedRows.length; i++) {
			records.push(selectedRows[i][opts.idField]);
		}
		selectedRows.splice(0, selectedRows.length);
		data.total += deletedRows.length - insertedRows.length;
		data.rows = originalRows;
		renderGrid(target, data);
		for (var i = 0; i < records.length; i++) {
			selectRecord(target, records[i]);
		}
		resetOperation(target);
	};
	function request(target, param) {
		var panel = $.data(target, 'datagrid').panel;
		var opts = $.data(target, 'datagrid').options;
		if (param) {
			opts.queryParams = param;
		}
		if (!opts.url) {
			return;
		}
		param = $.extend({}, opts.queryParams);
		if (opts.pagination) {
			$.extend(param, {
						page : opts.pageNumber,
						rows : opts.pageSize
					});
		}
		if (opts.sortName) {
			$.extend(param, {
						sort : opts.sortName,
						order : opts.sortOrder
					});
		}
		if (opts.onBeforeLoad.call(target, param) == false) {
			return;
		}
		//showLoadingMask();
		$(target).datagrid('loading');
		setTimeout(function() {
					doRequest();
				}, 0);
		function doRequest() {
			$.ajax({
						type : opts.method,
						url : opts.url,
						data : param,
						dataType : 'json',
						success : function(data) {
							setTimeout(function() {
										//hideLoadingMask();
										$(target).datagrid('loaded');
									}, 0);
							renderGrid(target, data);
							setTimeout(function() {
										resetOperation(target);
									}, 0);
						},
						error : function() {
							setTimeout(function() {
										//hideLoadingMask();
										$(target).datagrid('loaded');
									}, 0);
							if (opts.onLoadError) {
								opts.onLoadError.apply(target, arguments);
							}
						}
					});
		};
	};
	function mergeCells(target, options) {
		var rows = $.data(target, 'datagrid').data.rows;
		var panel = $.data(target, 'datagrid').panel;
		options.rowspan = options.rowspan || 1;
		options.colspan = options.colspan || 1;
		if (options.index < 0 || options.index >= rows.length) {
			return;
		}
		if (options.rowspan == 1 && options.colspan == 1) {
			return;
		}
		var field = rows[options.index][options.field];
		//wcj: use children
//		var tr = panel.find('div.datagrid-body tr[datagrid-row-index='
//				+ options.index + ']');
		var $datagridView = panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
		var tr = $datagridView.children('.datagrid-body')
				.children('table').children('tbody')
				.children('tr:eq(' + options.index + ')')
				.add($datagridView.children('.datagrid-body').children(".datagrid-body-inner")
						.children('table').children('tbody')
						.children('tr:eq(' + options.index + ')'));
		var td = tr.find('td[field=' + options.field + ']');
		td.attr('rowspan', options.rowspan).attr('colspan', options.colspan);
		td.addClass('datagrid-td-merged');
		for (var i = 1; i < options.colspan; i++) {
			td = td.next();
			td.hide();
			rows[options.index][td.attr('field')] = field;
		}
		for (var i = 1; i < options.rowspan; i++) {
			tr = tr.next();
			var td = tr.find('td[field=' + options.field + ']').hide();
			rows[options.index + i][td.attr('field')] = field;
			for (var j = 1; j < options.colspan; j++) {
				td = td.next();
				td.hide();
				rows[options.index + i][td.attr('field')] = field;
			}
		}
		setTimeout(function() {
					fixMergedCellsSize(target);
				}, 0);
	};
	$.fn.datagrid = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.datagrid.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'datagrid');
					var opts;
					if (state) {
						opts = $.extend(state.options, options);
						state.options = opts;
					} else {
						opts = $.extend({}, $.fn.datagrid.defaults,
								$.fn.datagrid.parseOptions(this), options);
						$(this).css('width', '').css('height', '');
						var gridWrap = wrapGrid(this, opts.rownumbers);
						if (!opts.columns) {
							opts.columns = gridWrap.columns;
						}
						if (!opts.frozenColumns) {
							opts.frozenColumns = gridWrap.frozenColumns;
						}
						$.data(this, 'datagrid', {
									options : opts,
									panel : gridWrap.panel,
									selectedRows : [],
									data : {
										total : 0,
										rows : []
									},
									originalRows : [],
									updatedRows : [],
									insertedRows : [],
									deletedRows : []
								});
					}
					init(this);
					if (!state) {
						var data = getGridData(this);
						if (data.total > 0) {
							renderGrid(this, data);
							resetOperation(this);
						}
					}
					setSize(this);
					//wcj: don't load data when initialed
//					if (opts.url) {
//						request(this);
//					}
					setProperties(this);
				});
	};
	var editors = {
		text : {
			init : function(container, options) {
				var editor = $('<input type="text" class="datagrid-editable-input">')
						.appendTo(container);
				return editor;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, value) {
				$(target).val(value);
			},
			resize : function(target, width) {
				var editor = $(target);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		textarea : {
			init : function(container, options) {
				var editor = $('<textarea class="datagrid-editable-input"></textarea>')
						.appendTo(container);
				return editor;
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, value) {
				$(target).val(value);
			},
			resize : function(target, width) {
				var editor = $(target);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		checkbox : {
			init : function(container, options) {
				var editor = $('<input type="checkbox">').appendTo(container);
				editor.val(options.on);
				editor.attr('offval', options.off);
				return editor;
			},
			getValue : function(target) {
				if ($(target).attr('checked')) {
					return $(target).val();
				} else {
					return $(target).attr('offval');
				}
			},
			setValue : function(target, value) {
				var checked=false;
				if($(target).val()==value){
					checked=true;
				}
				$.fn.prop?$(target).prop('checked',checked):$(target).attr('checked',checked);
			}
		},
		numberbox : {
			init : function(container, options) {
				var editor = $('<input type="text" class="datagrid-editable-input">')
						.appendTo(container);
				editor.numberbox(options);
				return editor;
			},
			destroy : function(target) {
				$(target).numberbox('destroy');
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, value) {
				$(target).val(value);
			},
			resize : function(target, width) {
				var editor = $(target);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		validatebox : {
			init : function(container, options) {
				var editor = $('<input type="text" class="datagrid-editable-input">')
						.appendTo(container);
				editor.validatebox(options);
				return editor;
			},
			destroy : function(target) {
				$(target).validatebox('destroy');
			},
			getValue : function(target) {
				return $(target).val();
			},
			setValue : function(target, value) {
				$(target).val(value);
			},
			resize : function(target, width) {
				var editor = $(target);
				if ($.boxModel == true) {
					editor.width(width - (editor.outerWidth() - editor.width()));
				} else {
					editor.width(width);
				}
			}
		},
		datebox : {
			init : function(container, options) {
				var editor = $('<input type="text">').appendTo(container);
				editor.datebox(options);
				return editor;
			},
			destroy : function(target) {
				$(target).datebox('destroy');
			},
			getValue : function(target) {
				return $(target).datebox('getValue');
			},
			setValue : function(target, value) {
				$(target).datebox('setValue', value);
			},
			resize : function(target, width) {
				$(target).datebox('resize', width);
			}
		},
		combobox : {
			init : function(container, options) {
				var editor = $('<input type="text">').appendTo(container);
				editor.combobox(options || {});
				return editor;
			},
			destroy : function(target) {
				$(target).combobox('destroy');
			},
			getValue : function(target) {
				return $(target).combobox('getValue');
			},
			setValue : function(target, value) {
				$(target).combobox('setValue', value);
			},
			resize : function(target, width) {
				$(target).combobox('resize', width);
			}
		},
		combotree : {
			init : function(container, options) {
				var editor = $('<input type="text">').appendTo(container);
				editor.combotree(options);
				return editor;
			},
			destroy : function(target) {
				$(target).combotree('destroy');
			},
			getValue : function(target) {
				return $(target).combotree('getValue');
			},
			setValue : function(target, value) {
				$(target).combotree('setValue', value);
			},
			resize : function(target, width) {
				$(target).combotree('resize', width);
			}
		}
	};
	$.fn.datagrid.methods = {
		options : function(jq) {
			var gridOpts = $.data(jq[0], 'datagrid').options;
			var panelOpts = $.data(jq[0], 'datagrid').panel.panel('options');
			var opts = $.extend(gridOpts, {
						width : panelOpts.width,
						height : panelOpts.height,
						closed : panelOpts.closed,
						collapsed : panelOpts.collapsed,
						minimized : panelOpts.minimized,
						maximized : panelOpts.maximized
					});
			var pager = jq.datagrid('getPager');
			if (pager.length) {
				var pagerOpts = pager.pagination('options');
				$.extend(opts, {
							pageNumber : pagerOpts.pageNumber,
							pageSize : pagerOpts.pageSize
						});
			}
			return opts;
		},
		getPanel : function(jq) {
			return $.data(jq[0], 'datagrid').panel;
		},
		getPager : function(jq) {
			return $.data(jq[0], 'datagrid').panel.find('div.datagrid-pager');
		},
		getColumnFields : function(jq, frozen) {
			return getColumnFields(jq[0], frozen);
		},
		getColumnOption : function(jq, field) {
			return getColumnOption(jq[0], field);
		},
		resize : function(jq, param) {
			return jq.each(function() {
						setSize(this, param);
					});
		},
		load : function(jq, param) {
			return jq.each(function() {
						var opts = $(this).datagrid('options');
						opts.pageNumber = 1;
						var pager = $(this).datagrid('getPager');
						pager.pagination({
									pageNumber : 1
								});
						request(this, param);
					});
		},
		reload : function(jq, param) {
			return jq.each(function() {
						request(this, param);
					});
		},
		reloadFooter : function(jq, footer) {
			return jq.each(function() {
						var opts = $.data(this, 'datagrid').options;
						var view = $(this).datagrid('getPanel')
								.children('div.datagrid-view');
						var gridView1 = view.children('div.datagrid-view1');
						var gridView2 = view.children('div.datagrid-view2');
						if (footer) {
							$.data(this, 'datagrid').footer = footer;
						}
						if (opts.showFooter) {
							opts.view.renderFooter.call(opts.view, this, gridView2
											.find('div.datagrid-footer-inner'),
									false);
							opts.view.renderFooter.call(opts.view, this, gridView1
											.find('div.datagrid-footer-inner'),
									true);
							if (opts.view.onAfterRender) {
								opts.view.onAfterRender.call(opts.view, this);
							}
							$(this).datagrid('fixRowHeight');
						}
					});
		},
		loading : function(jq) {
			return jq.each(function() {
						var opts = $.data(this, 'datagrid').options;
						$(this).datagrid('getPager').pagination('loading');
						if (opts.loadMsg) {
							//wcj:
							//var wrap = $(this).datagrid('getPanel');
							var wrap = $(this).datagrid('getPanel').children('.datagrid-view');
							$('<div class="datagrid-mask"></div>').css({
										display : 'block',
										width : wrap.width(),
										height : wrap.height()
									}).appendTo(wrap);
							//wcj: don't show msg
							return;
							$('<div class="datagrid-mask-msg"></div>')
									.html(opts.loadMsg).appendTo(wrap).css({
										display : 'block',
										left : (wrap.width() - $(
												'div.datagrid-mask-msg', wrap)
												.outerWidth())
												/ 2,
										top : (wrap.height() - $(
												'div.datagrid-mask-msg', wrap)
												.outerHeight())
												/ 2
									});
						}
					});
		},
		loaded : function(jq) {
			return jq.each(function() {
						$(this).datagrid('getPager').pagination('loaded');
						var wrap = $(this).datagrid('getPanel');
						//wcj:
						//wrap.children('div.datagrid-mask-msg').remove();
						//wrap.children('div.datagrid-mask').remove();
						wrap.children('.datagrid-view').children('div.datagrid-mask-msg').remove();
						wrap.children('.datagrid-view').children('div.datagrid-mask').remove();
					});
		},
		fitColumns : function(jq) {
			return jq.each(function() {
						fitColumns(this);
					});
		},
		fixColumnSize : function(jq) {
			return jq.each(function() {
						fixColumnSize(this);
					});
		},
		fixRowHeight : function(jq, index) {
			return jq.each(function() {
						fixRowHeight(this, index);
					});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
						renderGrid(this, data);
						resetOperation(this);
					});
		},
		getData : function(jq) {
			return $.data(jq[0], 'datagrid').data;
		},
		getRows : function(jq) {
			return $.data(jq[0], 'datagrid').data.rows;
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], 'datagrid').footer;
		},
		getRowIndex : function(jq, id) {
			return getRowIndex(jq[0], id);
		},
		getSelected : function(jq) {
			var rows = getSelected(jq[0]);
			return rows.length > 0 ? rows[0] : null;
		},
		getSelections : function(jq) {
			return getSelected(jq[0]);
		},
		clearSelections : function(jq) {
			return jq.each(function() {
						clearSelections(this);
					});
		},
		selectAll : function(jq) {
			return jq.each(function() {
						selectAll(this);
					});
		},
		unselectAll : function(jq) {
			return jq.each(function() {
						unselectAll(this);
					});
		},
		selectRow : function(jq, index) {
			return jq.each(function() {
						selectRow(this, index);
					});
		},
		selectRecord : function(jq, id) {
			return jq.each(function() {
						selectRecord(this, id);
					});
		},
		unselectRow : function(jq, index) {
			return jq.each(function() {
						unselectRow(this, index);
					});
		},
		beginEdit : function(jq, index) {
			return jq.each(function() {
						beginEdit(this, index);
					});
		},
		endEdit : function(jq, index) {
			return jq.each(function() {
						stopEdit(this, index, false);
					});
		},
		cancelEdit : function(jq, index) {
			return jq.each(function() {
						stopEdit(this, index, true);
					});
		},
		getEditors : function(jq, index) {
			return getEditors(jq[0], index);
		},
		getEditor : function(jq, options) {
			return getEditor(jq[0], options);
		},
		refreshRow : function(jq, index) {
			return jq.each(function() {
						var opts = $.data(this, 'datagrid').options;
						opts.view.refreshRow.call(opts.view, this, index);
					});
		},
		validateRow : function(jq, index) {
			return validateRow(jq[0], index);
		},
		updateRow:function(jq,param){
			return jq.each(function(){
				var opts=$.data(this,'datagrid').options;
				opts.view.updateRow.call(opts.view,this,param.index,param.row);
			});
		},
		appendRow : function(jq, row) {
			return jq.each(function() {
						appendRow(this, row);
					});
		},
		insertRow : function(jq, param) {
			return jq.each(function() {
						insertRow(this, param);
					});
		},
		deleteRow : function(jq, index) {
			return jq.each(function() {
						deleteRow(this, index);
					});
		},
		getChanges : function(jq, type) {
			return getChanges(jq[0], type);
		},
		acceptChanges : function(jq) {
			return jq.each(function() {
						acceptChanges(this);
					});
		},
		rejectChanges : function(jq) {
			return jq.each(function() {
						rejectChanges(this);
					});
		},
		mergeCells : function(jq, options) {
			return jq.each(function() {
						mergeCells(this, options);
					});
		},
		showColumn : function(jq, field) {
			return jq.each(function() {
						var panel = $(this).datagrid('getPanel');
						panel.find('td[field=' + field + ']').show();
						$(this).datagrid('getColumnOption', field).hidden = false;
						$(this).datagrid('fitColumns');
					});
		},
		hideColumn : function(jq, field) {
			return jq.each(function() {
						var panel = $(this).datagrid('getPanel');
						panel.find('td[field=' + field + ']').hide();
						$(this).datagrid('getColumnOption', field).hidden = true;
						$(this).datagrid('fitColumns');
					});
		}
	};
	$.fn.datagrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.panel.parseOptions(target), {
			fitColumns : (t.attr('fitColumns')
					? t.attr('fitColumns') == 'true'
					: undefined),
			striped : (t.attr('striped')
					? t.attr('striped') == 'true'
					: undefined),
			nowrap : (t.attr('nowrap') ? t.attr('nowrap') == 'true' : undefined),
			rownumbers : (t.attr('rownumbers')
					? t.attr('rownumbers') == 'true'
					: undefined),
			singleSelect : (t.attr('singleSelect')
					? t.attr('singleSelect') == 'true'
					: undefined),
			pagination : (t.attr('pagination')
					? t.attr('pagination') == 'true'
					: undefined),
			pageSize : (t.attr('pageSize')
					? parseInt(t.attr('pageSize'))
					: undefined),
			pageList : (t.attr('pageList')
					? eval(t.attr('pageList'))
					: undefined),
			remoteSort : (t.attr('remoteSort')
					? t.attr('remoteSort') == 'true'
					: undefined),
			sortName:t.attr('sortName'),
			sortOrder:t.attr('sortOrder'),
			showHeader : (t.attr('showHeader')
					? t.attr('showHeader') == 'true'
					: undefined),
			showFooter : (t.attr('showFooter')
					? t.attr('showFooter') == 'true'
					: undefined),
			scrollbarSize : (t.attr('scrollbarSize') ? parseInt(t
					.attr('scrollbarSize')) : undefined),
			loadMsg : (t.attr('loadMsg') != undefined
					? t.attr('loadMsg')
					: undefined),
			idField : t.attr('idField'),
			toolbar : t.attr('toolbar'),
			url : t.attr('url')
		});
	};
	var view = {
		render : function(target, container, frozen) {
			var opts = $.data(target, 'datagrid').options;
			var rows = $.data(target, 'datagrid').data.rows;
			var fields = $(target).datagrid('getColumnFields', frozen);
			if (frozen) {
				if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
					return;
				}
			}
			//wcj: prepare columnOptions
			var columnOptions = {};
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i];
				columnOptions[field] = $(target).datagrid('getColumnOption', field);
			}
			var html = ['<table cellspacing="0" cellpadding="0" border="0"><tbody>'];
			for (var i = 0; i < rows.length; i++) {
				var cls = (i % 2 && opts.striped)
						? 'class="datagrid-row-alt"'
						: '';
				var style = opts.rowStyler ? opts.rowStyler.call(target, i,
						rows[i]) : '';
				style = style ? 'style="' + style + '"' : '';
				html.push('<tr datagrid-row-index="' + i + '" ' + cls + ' '
						+ style + '>');
				html.push(this.renderRow.call(this, target, fields, frozen, i,
						rows[i],  columnOptions));
				html.push('</tr>');
			}
			html.push('</tbody></table>');
			$(container).html(html.join(''));
		},
		renderFooter : function(target, container, frozen) {
			var opts = $.data(target, 'datagrid').options;
			var rows = $.data(target, 'datagrid').footer || [];
			var fields = $(target).datagrid('getColumnFields', frozen);
			var html = ['<table cellspacing="0" cellpadding="0" border="0"><tbody>'];
			for (var i = 0; i < rows.length; i++) {
				html.push('<tr datagrid-row-index="' + i + '">');
				html.push(this.renderRow.call(this, target, fields, frozen, i,
						rows[i], null, true));
				html.push('</tr>');
			}
			html.push('</tbody></table>');
			$(container).html(html.join(''));
		},
		//wcj: add param columnOptions, useHeaderWidth
		renderRow : function(target, fields, frozen, rowIndex, rowData, columnOptions, useHeaderWidth) {
			var opts = $.data(target, 'datagrid').options;
			var cc = [];
			if (frozen && opts.rownumbers) {
				var rowNumber = rowIndex + 1;
				if (opts.pagination) {
					rowNumber += (opts.pageNumber - 1) * opts.pageSize;
				}
				cc
						.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">'
								+ rowNumber + '</div></td>');
			}
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i];
				//wcj: use prepared columnOptions
				//var col = $(target).datagrid('getColumnOption', field);
				var col = columnOptions ? columnOptions[field] : $(target).datagrid('getColumnOption', field);
				if (col) {
					var style = col.styler
							? (col.styler(rowData[field], rowData, rowIndex) || '')
							: '';
					style = col.hidden ? 'style="display:none;' + style
							+ '"' : (style ? 'style="' + style + '"' : '');
					cc.push('<td field="' + field + '" ' + style + '>');
					//wcj:
					//style = 'width:' + (col.boxWidth) + 'px;';
					style = 'width:' + (useHeaderWidth && col.header ? col.header.width() : col.boxWidth) + 'px;';
					//wcj:
					if (! col.checkbox) {
						style += 'text-align:' + (col.align || 'left') + ';';
					}
					style += opts.nowrap == false ? 'white-space:normal;' : '';
					cc.push('<div style="' + style + '" ');
					if (col.checkbox) {
						cc.push('class="datagrid-cell-check ');
					} else {
						cc.push('class="datagrid-cell ');
					}
					cc.push('">');
					if (col.checkbox) {
						cc.push('<input type="checkbox"/>');
					} else {
						if (col.formatter) {
							cc.push(col.formatter(rowData[field], rowData, rowIndex));
						} else {
							cc.push(rowData[field]);
						}
					}
					cc.push('</div>');
					cc.push('</td>');
				}
			}
			return cc.join('');
		},
		refreshRow : function(target,rowIndex){
			var row={};
			var fields = $(target).datagrid('getColumnFields',true).concat($(target).datagrid('getColumnFields',false));
			for(var i=0;i<fields.length;i++){
				row[fields[i]]=undefined;
			}
			var rows=$(target).datagrid('getRows');
			$.extend(row,rows[rowIndex]);
			this.updateRow.call(this,target,rowIndex,row);
		},
		updateRow:function(target,rowIndex,row){
			var opts=$.data(target,'datagrid').options;
			var panel=$(target).datagrid('getPanel');
			var rows=$(target).datagrid('getRows');
			//wcj: use children
//			var tr=panel.find('div.datagrid-body tr[datagrid-row-index="'+rowIndex+'"]');
			var $datagridView = panel.children('.datagrid-view').children('.datagrid-view1,.datagrid-view2')
			var tr = $datagridView.children('.datagrid-body')
					.children('table').children('tbody')
					.children('tr:eq(' + rowIndex + ')')
					.add($datagridView.children('.datagrid-body').children(".datagrid-body-inner")
							.children('table').children('tbody')
							.children('tr:eq(' + rowIndex + ')'));
			for(var field in row){
				rows[rowIndex][field]=row[field];
				var td=tr.children('td[field="'+field+'"]');
				var cell=td.find('div.datagrid-cell');
				var col=$(target).datagrid('getColumnOption',field);
				if(col){
					//wcj:
					var hidden = td.css("display") == "none";
					var style=col.styler?col.styler(rows[rowIndex][field],rows[rowIndex],rowIndex):'';
					td.attr('style',style||'');
					//wcj:
					if (hidden) {
						td.css("display", "none");
					}
					if (col.hidden) {
						td.hide();
					}
					if (col.formatter) {
						cell.html(col.formatter(rows[rowIndex][field],
								rows[rowIndex], rowIndex));
					} else {
						cell.html(rows[rowIndex][field]);
					}
				}
			}
			var style = opts.rowStyler ? opts.rowStyler.call(target, rowIndex,
					rows[rowIndex]) : '';
			tr.attr('style', style || '');
			$(target).datagrid('fixRowHeight', rowIndex);
		},
		insertRow : function(target, rowIndex, row) {
			var opts = $.data(target, 'datagrid').options;
			var data = $.data(target, 'datagrid').data;
			var view = $(target).datagrid('getPanel')
					.children('div.datagrid-view');
			var gridView1 = view.children('div.datagrid-view1');
			var gridView2 = view.children('div.datagrid-view2');
			if (rowIndex == undefined || rowIndex == null) {
				rowIndex = data.rows.length;
			}
			if (rowIndex > data.rows.length) {
				rowIndex = data.rows.length;
			}
			for (var i = data.rows.length - 1; i >= rowIndex; i--) {
				//wcj: use children
//				gridView2.children('div.datagrid-body')
//						.find('tr[datagrid-row-index=' + i + ']').attr(
//								'datagrid-row-index', i + 1);
//				var tr = gridView1.children('div.datagrid-body')
//						.find('tr[datagrid-row-index=' + i + ']').attr(
//								'datagrid-row-index', i + 1);
				gridView2.children('.datagrid-body').children('table').children('tbody')
						.children('tr[datagrid-row-index=' + i + ']').attr('datagrid-row-index', i + 1);
				var tr = gridView1.children('.datagrid-body').children('.datagrid-body-inner').children('table').children('tbody')
						.children('tr[datagrid-row-index=' + i + ']').attr('datagrid-row-index', i + 1);
				if (opts.rownumbers) {
					tr.find('div.datagrid-cell-rownumber').html(i + 2);
				}
			}
			var frozenFields = $(target).datagrid('getColumnFields', true);
			var fields = $(target).datagrid('getColumnFields', false);
			var tr1 = '<tr datagrid-row-index="' + rowIndex + '">'
					+ this.renderRow.call(this, target, frozenFields, true, rowIndex, row)
					+ '</tr>';
			var tr2 = '<tr datagrid-row-index="' + rowIndex + '">'
					+ this.renderRow.call(this, target, fields, false, rowIndex, row)
					+ '</tr>';
			if (rowIndex >= data.rows.length) {
				var gridBody1 = gridView1.children('div.datagrid-body')
						.children('div.datagrid-body-inner');
				var gridBody2 = gridView2.children('div.datagrid-body');
				if (data.rows.length) {
					//wcj: use children
//					gridBody1.find('tr:last[datagrid-row-index]').after(tr1);
//					gridBody2.find('tr:last[datagrid-row-index]').after(tr2);
					gridBody1.children('table').children('tbody').children('tr:last[datagrid-row-index]').after(tr1);
					gridBody2.children('table').children('tbody').children('tr:last[datagrid-row-index]').after(tr2);
				} else {
					gridBody1
							.html('<table cellspacing="0" cellpadding="0" border="0"><tbody>'
									+ tr1 + '</tbody></table>');
					gridBody2
							.html('<table cellspacing="0" cellpadding="0" border="0"><tbody>'
									+ tr2 + '</tbody></table>');
				}
			} else {
				//wcj: use children
//				gridView1.children('div.datagrid-body')
//						.find('tr[datagrid-row-index=' + (rowIndex + 1) + ']')
//						.before(tr1);
//				gridView2.children('div.datagrid-body')
//						.find('tr[datagrid-row-index=' + (rowIndex + 1) + ']')
//						.before(tr2);
				gridView1.children('.datagrid-body').children('.datagrid-body-inner').children('table').children('tbody')
						.children('tr[datagrid-row-index=' + (rowIndex + 1) + ']')
						.before(tr1);
				gridView2.children('.datagrid-body').children('table').children('tbody')
						.children('tr[datagrid-row-index=' + (rowIndex + 1) + ']')
						.before(tr2);
			}
			data.total += 1;
			data.rows.splice(rowIndex, 0, row);
			this.refreshRow.call(this, target, rowIndex);
		},
		deleteRow : function(target, rowIndex) {
			var opts = $.data(target, 'datagrid').options;
			var data = $.data(target, 'datagrid').data;
			var view = $(target).datagrid('getPanel')
					.children('div.datagrid-view');
			var gridView1 = view.children('div.datagrid-view1');
			var gridView2 = view.children('div.datagrid-view2');
			//wcj: use children
//			gridView1.children('div.datagrid-body').find('tr[datagrid-row-index='
//					+ rowIndex + ']').remove();
//			gridView2.children('div.datagrid-body').find('tr[datagrid-row-index='
//					+ rowIndex + ']').remove();
			gridView1.children('.datagrid-body').children('.datagrid-body-inner').children('table').children('tbody')
					.children('tr:eq(' + rowIndex + ')').remove();
			gridView2.children('.datagrid-body').children('table').children('tbody')
					.children('tr:eq(' + rowIndex + ')').remove();
			for (var i = rowIndex + 1; i < data.rows.length; i++) {
				//wcj: use children
//				gridView2.children('div.datagrid-body')
//						.find('tr[datagrid-row-index=' + i + ']').attr(
//								'datagrid-row-index', i - 1);
//				var tr = gridView1.children('div.datagrid-body')
//						.find('tr[datagrid-row-index=' + i + ']').attr(
//								'datagrid-row-index', i - 1);
				gridView2.children('.datagrid-body').children('table').children('tbody')
						.children('tr[datagrid-row-index=' + i + ']').attr('datagrid-row-index', i - 1);
				var tr = gridView1.children('.datagrid-body').children('.datagrid-body-inner').children('table').children('tbody')
						.children('tr[datagrid-row-index=' + i + ']').attr('datagrid-row-index', i - 1);
				if (opts.rownumbers) {
					tr.find('div.datagrid-cell-rownumber').html(i);
				}
			}
			data.total -= 1;
			data.rows.splice(rowIndex, 1);
		},
		onBeforeRender : function(target, rows) {
		},
		onAfterRender : function(target) {
			var opts = $.data(target, 'datagrid').options;
			if (opts.showFooter) {
				var footer = $(target).datagrid('getPanel')
						.find('div.datagrid-footer');
				footer
						.find('div.datagrid-cell-rownumber,div.datagrid-cell-check')
						.css('visibility', 'hidden');
			}
		}
	};
	$.fn.datagrid.defaults = $.extend({}, $.fn.panel.defaults, {
				frozenColumns : null,
				columns : null,
				fitColumns : false,
				toolbar : null,
				striped : false,
				method : 'post',
				nowrap : true,
				idField : null,
				url : null,
				loadMsg : 'Processing, please wait ...',
				rownumbers : false,
				singleSelect : false,
				pagination : false,
				pageNumber : 1,
				pageSize : 10,
				pageList : [10, 20, 30, 40, 50],
				queryParams : {},
				sortName : null,
				sortOrder : 'asc',
				remoteSort : true,
				showHeader : true,
				showFooter : false,
				scrollbarSize : 18,
				rowStyler : function(rowIndex, rowData) {
				},
				loadFilter : function(data) {
					if (typeof data.length == 'number'
							&& typeof data.splice == 'function') {
						return {
							total : data.length,
							rows : data
						};
					} else {
						return data;
					}
				},
				editors : editors,
				editConfig : {
					getTr : function(target, rowIndex) {
						//wcj: use children
//						return $(target)
//								.datagrid('getPanel')
//								.find('div.datagrid-body tr[datagrid-row-index='
//										+ rowIndex + ']');
						var $datagridView = $(target).datagrid('getPanel').children('.datagrid-view')
								.children('.datagrid-view1,.datagrid-view2');
						return $datagridView.children('.datagrid-body')
								.children('table').children('tbody')
								.children('tr:eq(' + rowIndex + ')')
								.add($datagridView.children('.datagrid-body').children(".datagrid-body-inner")
										.children('table').children('tbody')
										.children('tr:eq(' + rowIndex + ')'));
					},
					getRow : function(target, rowIndex) {
						return $.data(target, 'datagrid').data.rows[rowIndex];
					}
				},
				view : view,
				onBeforeLoad : function(param) {
				},
				onLoadSuccess : function(data) {
				},
				onLoadError : function() {
				},
				onClickRow : function(rowIndex, rowData) {
				},
				onDblClickRow : function(rowIndex, rowData) {
				},
				onClickCell : function(rowIndex, field, value) {
				},
				onDblClickCell : function(rowIndex, field, value) {
				},
				onSortColumn : function(sort, order) {
				},
				onResizeColumn : function(field, width) {
				},
				onSelect : function(rowIndex, rowData) {
				},
				onUnselect : function(rowIndex, rowData) {
				},
				onSelectAll : function(rows) {
				},
				onUnselectAll : function(rows) {
				},
				onBeforeEdit : function(rowIndex, rowData) {
				},
				onAfterEdit : function(rowIndex, rowData, changes) {
				},
				onCancelEdit : function(rowIndex, rowData) {
				},
				onHeaderContextMenu : function(e, field) {
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
				}
			});
})(jQuery);

/**
 * treegrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 datagrid
 * 
 */
(function($) {
	function initGrid(target) {
		var opts = $.data(target, 'treegrid').options;
		$(target).datagrid($.extend({}, opts, {
					url : null,
					onLoadSuccess : function() {
					},
					onResizeColumn : function(field, width) {
						setRowHeight(target);
						opts.onResizeColumn.call(target, field, width);
					},
					onSortColumn : function(sortName, sortOrder) {
						opts.sortName = sortName;
						opts.sortOrder = sortOrder;
						if (opts.remoteSort) {
							request(target);
						} else {
							var data = $(target).treegrid('getData');
							loadData(target, 0, data);
						}
						opts.onSortColumn.call(target, sortName, sortOrder);
					},
					onBeforeEdit:function(rowIndex,rowData){
						if(opts.onBeforeEdit.call(target,rowData)==false){
							return false;
						}
					},
					onAfterEdit:function(rowIndex,rowData,newValues){
						bindEvents(target);
						opts.onAfterEdit.call(target,rowData,newValues);
					},
					onCancelEdit:function(rowIndex,rowData){
						bindEvents(target);
						opts.onCancelEdit.call(target,rowData);
					}
				}));
		if (opts.pagination) {
			var pager = $(target).datagrid('getPager');
			pager.pagination({
						pageNumber : opts.pageNumber,
						pageSize : opts.pageSize,
						pageList : opts.pageList,
						onSelectPage : function(pageNumber, pageSize) {
							opts.pageNumber = pageNumber;
							opts.pageSize = pageSize;
							request(target);
						}
					});
			opts.pageSize = pager.pagination('options').pageSize;
		}
	};
	function setRowHeight(target, nodeId) {
		var opts = $.data(target, 'datagrid').options;
		//wcj: use fixed row height
		if (! opts.autoRowHeight) {
			return;
		}
		var panel = $.data(target, 'datagrid').panel;
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		if (opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length > 0)) {
			if (nodeId) {
				setHeight(nodeId);
				//wcj: use cache
				//gridView2.find('tr[node-id=' + nodeId + ']').next('tr.treegrid-tr-tree')
				opts.tr2Cache[nodeId].next('tr.treegrid-tr-tree')
						.find('tr[node-id]').each(function() {
									setHeight($(this).attr('node-id'));
								});
			} else {
				gridView2.find('tr[node-id]').each(function() {
							setHeight($(this).attr('node-id'));
						});
				if (opts.showFooter) {
					var footer = $.data(target, 'datagrid').footer || [];
					for (var i = 0; i < footer.length; i++) {
						setHeight(footer[i][opts.idField]);
					}
					$(target).datagrid('resize');
				}
			}
		}
		if (opts.height == 'auto') {
			var gridBody1 = gridView1.children('div.datagrid-body');
			var gridBody2 = gridView2.children('div.datagrid-body');
			var height = 0;
			var lastHeight = 0;
			gridBody2.children().each(function() {
						var c = $(this);
						if (c.is(':visible')) {
							height += c.outerHeight();
							if (lastHeight < c.outerWidth()) {
								lastHeight = c.outerWidth();
							}
						}
					});
			if (lastHeight > gridBody2.width()) {
				height += 18;
			}
			gridBody1.height(height);
			gridBody2.height(height);
			gridView.height(gridView2.height());
		}
		gridView2.children('div.datagrid-body').triggerHandler('scroll');
		function setHeight(nodeId) {
			//wcj: use cache
			//var tr1 = gridView1.find('tr[node-id=' + nodeId + ']');
			//var tr2 = gridView2.find('tr[node-id=' + nodeId + ']');
			var tr1 = opts.tr1Cache[nodeId];
			var tr2 = opts.tr2Cache[nodeId];
			tr1.css('height', '');
			tr2.css('height', '');
			var height = Math.max(tr1.height(), tr2.height());
			tr1.css('height', height);
			tr2.css('height', height);
		};
	};
	function fixRowNumbers(target) {
		var opts = $.data(target, 'treegrid').options;
		if (!opts.rownumbers) {
			return;
		}
		$(target)
				.datagrid('getPanel')
				.find('div.datagrid-view1 div.datagrid-body div.datagrid-cell-rownumber')
				.each(function(i) {
							var num = i + 1;
							$(this).html(num);
						});
	};
	function bindEvents(target) {
		var opts = $.data(target, 'treegrid').options;
		var panel = $(target).datagrid('getPanel');
		//wcj: use cache
//		var gridBody = panel.find('div.datagrid-body');
//		gridBody.find('span.tree-hit').unbind('.treegrid').bind('click.treegrid',
//				function() {
//					var tr = $(this).parent().parent().parent();
//					var id = tr.attr('node-id');
//					toggle(target, id);
//					return false;
//				}).bind('mouseenter.treegrid', function() {
//					if ($(this).hasClass('tree-expanded')) {
//						$(this).addClass('tree-expanded-hover');
//					} else {
//						$(this).addClass('tree-collapsed-hover');
//					}
//				}).bind('mouseleave.treegrid', function() {
//					if ($(this).hasClass('tree-expanded')) {
//						$(this).removeClass('tree-expanded-hover');
//					} else {
//						$(this).removeClass('tree-collapsed-hover');
//					}
//				});
//		//wcj: use css hover
//		gridBody.find('tr[node-id]').unbind('.treegrid')/*.bind('mouseenter.treegrid',
//				function() {
//					var id = $(this).attr('node-id');
//					gridBody.find('tr[node-id=' + id + ']')
//							.addClass('datagrid-row-over');
//				}).bind('mouseleave.treegrid', function() {
//			var id = $(this).attr('node-id');
//			gridBody.find('tr[node-id=' + id + ']').removeClass('datagrid-row-over');
//		})*/.bind('click.treegrid', function() {
//					var id = $(this).attr('node-id');
//					if (opts.singleSelect) {
//						unselectAll(target);
//						select(target, id);
//					} else {
//						if ($(this).hasClass('datagrid-row-selected')) {
//							unselect(target, id);
//						} else {
//							select(target, id);
//						}
//					}
//					opts.onClickRow.call(target, find(target, id));
//				}).bind('dblclick.treegrid', function() {
//					var id = $(this).attr('node-id');
//					opts.onDblClickRow.call(target, find(target, id));
//				}).bind('contextmenu.treegrid', function(e) {
//					var id = $(this).attr('node-id');
//					opts.onContextMenu.call(target, e, find(target, id));
//				});
		$.each(opts.tr2Cache, function(id, tr2) {
			tr2.children('td[field="' + opts.treeField + '"]').children('.datagrid-cell').children('.tree-hit')
			.unbind('.treegrid')
			.bind('click.treegrid', function() {
				var tr = $(this).parent().parent().parent();
				var id = tr.attr('node-id');
				toggle(target, id);
				return false;
			}).bind('mouseenter.treegrid', function() {
				if ($(this).hasClass('tree-expanded')) {
					$(this).addClass('tree-expanded-hover');
				} else {
					$(this).addClass('tree-collapsed-hover');
				}
			}).bind('mouseleave.treegrid', function() {
				if ($(this).hasClass('tree-expanded')) {
					$(this).removeClass('tree-expanded-hover');
				} else {
					$(this).removeClass('tree-collapsed-hover');
				}
			});
			tr2.unbind('.treegrid').bind('click.treegrid', function() {
				var id = $(this).attr('node-id');
				if (opts.singleSelect) {
					unselectAll(target);
					select(target, id);
				} else {
					if ($(this).hasClass('datagrid-row-selected')) {
						unselect(target, id);
					} else {
						select(target, id);
					}
				}
				opts.onClickRow.call(target, find(target, id));
			}).bind('dblclick.treegrid', function() {
				var id = $(this).attr('node-id');
				opts.onDblClickRow.call(target, find(target, id));
			}).bind('contextmenu.treegrid', function(e) {
				var id = $(this).attr('node-id');
				opts.onContextMenu.call(target, e, find(target, id));
			});
		});
		//wcj: only in view1
		//gridBody.find('div.datagrid-cell-check input[type=checkbox]')
		panel.children(".datagrid-view").children(".datagrid-view1") 
		.find('div.datagrid-cell-check input[type=checkbox]')
				.unbind('.treegrid').bind('click.treegrid', function(e) {
					var id = $(this).parent().parent().parent().attr('node-id');
					if (opts.singleSelect) {
						unselectAll(target);
						select(target, id);
					} else {
						if ($(this).attr('checked')) {
							select(target, id);
						} else {
							unselect(target, id);
						}
					}
					e.stopPropagation();
				});
		//wcj:
		//var gridHeader = panel.find('div.datagrid-header');
		var gridHeader = panel.children(".datagrid-view").children(".datagrid-view1").children('div.datagrid-header');
		gridHeader.find('input[type=checkbox]').unbind().bind('click.treegrid',
				function() {
					if (opts.singleSelect) {
						return false;
					}
					if ($(this).is(':checked')) {
						selectAll(target);
					} else {
						unselectAll(target);
					}
				});
	};
	function initSubTree(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		var gridView = $(target).datagrid('getPanel').children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		//wcj: use cache
//		var tr1 = gridView1.children('div.datagrid-body').find('tr[node-id=' + nodeId
//				+ ']');
//		var tr2 = gridView2.children('div.datagrid-body').find('tr[node-id=' + nodeId
//				+ ']');
		var tr1 = opts.tr1Cache[nodeId];
		var tr2 = opts.tr2Cache[nodeId];
		var colspan1 = $(target).datagrid('getColumnFields', true).length
				+ (opts.rownumbers ? 1 : 0);
		var colspan2 = $(target).datagrid('getColumnFields', false).length;
		createSubTree(tr1, colspan1);
		createSubTree(tr2, colspan2);
		function createSubTree(tr, colspan) {
			$('<tr class="treegrid-tr-tree">'
					+ '<td style="border:0px" colspan="' + colspan + '">'
					+ '<div></div>' + '</td>' + '</tr>').insertAfter(tr);
		};
	};
	function loadData(target, nodeId, param, isAppend) {
		var opts = $.data(target, 'treegrid').options;
		param = opts.loadFilter.call(target,param,nodeId);
		var panel = $.data(target, 'datagrid').panel;
		var gridView = panel.children('div.datagrid-view');
		var gridView1 = gridView.children('div.datagrid-view1');
		var gridView2 = gridView.children('div.datagrid-view2');
		var row = find(target, nodeId);
		if (row) {
			//wcj: use cache
//			var tr1 = gridView1.children('div.datagrid-body').find('tr[node-id='
//					+ nodeId + ']');
//			var tr2 = gridView2.children('div.datagrid-body').find('tr[node-id='
//					+ nodeId + ']');
			var tr1 = opts.tr1Cache[nodeId];
			var tr2 = opts.tr2Cache[nodeId];
			var cc1 = tr1.next('tr.treegrid-tr-tree').children('td')
					.children('div');
			var cc2 = tr2.next('tr.treegrid-tr-tree').children('td')
					.children('div');
		} else {
			var cc1 = gridView1.children('div.datagrid-body')
					.children('div.datagrid-body-inner');
			var cc2 = gridView2.children('div.datagrid-body');
		}
		if (!isAppend) {
			$.data(target, 'treegrid').data = [];
			cc1.empty();
			cc2.empty();
		}
		if (opts.view.onBeforeRender) {
			opts.view.onBeforeRender.call(opts.view, target, nodeId, param);
		}
		opts.view.render.call(opts.view, target, cc1, true);
		opts.view.render.call(opts.view, target, cc2, false);
		if (opts.showFooter) {
			opts.view.renderFooter.call(opts.view, target, gridView1
							.find('div.datagrid-footer-inner'), true);
			opts.view.renderFooter.call(opts.view, target, gridView2
							.find('div.datagrid-footer-inner'), false);
		}
		//wcj: cache data and tr
		$.each($.isArray(param) ? param : param.rows, function(index, row) {
			var id = row[opts.idField];
			opts.dataCache[id] = row;
			opts.tr1Cache[id] = cc1.find("tr[node-id='" + id + "']");
			opts.tr2Cache[id] = cc2.find("tr[node-id='" + id + "']");
		});
		if (opts.view.onAfterRender) {
			opts.view.onAfterRender.call(opts.view, target);
		}
		opts.onLoadSuccess.call(target, row, param);
		if (!nodeId && opts.pagination) {
			var total = $.data(target, 'treegrid').total;
			var pager = $(target).datagrid('getPager');
			if (pager.pagination('options').total != total) {
				pager.pagination({
							total : total
						});
			}
		}
		setRowHeight(target);
		fixRowNumbers(target);
		//wcj:
		//fixCheckboxSize();
		bindEvents(target);
		function fixCheckboxSize() {
			var gridHeader = gridView.find('div.datagrid-header');
			var gridBody = gridView.find('div.datagrid-body');
			var headerck = gridHeader.find('div.datagrid-header-check');
			if (headerck.length) {
				var ck = gridBody.find('div.datagrid-cell-check');
				if ($.boxModel) {
					ck.width(headerck.width());
					ck.height(headerck.height());
				} else {
					ck.width(headerck.outerWidth());
					ck.height(headerck.outerHeight());
				}
			}
		};
	};
	function request(target, parentId, param, isAppend, callBack) {
		var opts = $.data(target, 'treegrid').options;
		var body = $(target).datagrid('getPanel').find('div.datagrid-body');
		if (param) {
			opts.queryParams = param;
		}
		var queryParams = $.extend({}, opts.queryParams);
		if (opts.pagination) {
			$.extend(queryParams, {
						page : opts.pageNumber,
						rows : opts.pageSize
					});
		}
		if (opts.sortName) {
			$.extend(queryParams, {
						sort : opts.sortName,
						order : opts.sortOrder
					});
		}
		var row = find(target, parentId);
		if (opts.onBeforeLoad.call(target, row, queryParams) == false) {
			return;
		}
		//wcj: use cache
		//var folder = body.find('tr[node-id=' + parentId + '] span.tree-folder');
		var folder = opts.tr2Cache && opts.tr2Cache[parentId]
				? opts.tr2Cache[parentId].find('span.tree-folder')
				: $("<div>");
		folder.addClass('tree-loading');
		if (!opts.url) {
			if (opts.data) {
				folder.removeClass('tree-loading');
				loadData(target, parentId, opts.data, isAppend);
				if (callBack) {
					callBack();
				}
			}
			return;
		}
		$(target).treegrid('loading');
		$.ajax({
					type : opts.method,
					url : opts.url,
					data : queryParams,
					dataType : 'json',
					success : function(data) {
						folder.removeClass('tree-loading');
						$(target).treegrid('loaded');
						//wcj: reset treegrid cache
						opts.selectedIdCache = {};
						opts.dataCache = {};
						opts.tr1Cache = {};
						opts.tr2Cache = {};
						loadData(target, parentId, data, isAppend);
						if (callBack) {
							callBack();
						}
						//wcj: add resetOperation
						setTimeout(function() {
							resetOperation(target);
						}, 0);
					},
					error : function() {
						folder.removeClass('tree-loading');
						$(target).treegrid('loaded');
						opts.onLoadError.apply(target, arguments);
						if (callBack) {
							callBack();
						}
					}
				});
	};
	function getRoot(target) {
		var roots = getRoots(target);
		if (roots.length) {
			return roots[0];
		} else {
			return null;
		}
	};
	function getRoots(target) {
		return $.data(target, 'treegrid').data;
	};
	function getParent(target, nodeId) {
		var row = find(target, nodeId);
		if (row._parentId) {
			return find(target, row._parentId);
		} else {
			return null;
		}
	};
	function getChildren(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		var body = $(target).datagrid('getPanel')
				.find('div.datagrid-view2 div.datagrid-body');
		var children = [];
		if (nodeId) {
			findChildren(nodeId);
		} else {
			var roots = getRoots(target);
			for (var i = 0; i < roots.length; i++) {
				children.push(roots[i]);
				findChildren(roots[i][opts.idField]);
			}
		}
		function findChildren(nodeId) {
			var node = find(target, nodeId);
			if (node && node.children) {
				for (var i = 0, len = node.children.length; i < len; i++) {
					var child = node.children[i];
					children.push(child);
					findChildren(child[opts.idField]);
				}
			}
		};
		return children;
	};
	function getSelected(target) {
		var selectedRows = getSelections(target);
		if (selectedRows.length) {
			return selectedRows[0];
		} else {
			return null;
		}
	};
	function getSelections(target) {
		var selectedRows = [];
		//wcj: use cache
		var opts = $.data(target, 'treegrid').options;
		if (! opts.selectedIdCache) {
			return [];
		}
		$.each(opts.selectedIdCache, function(index, id) {
			selectedRows.push(opts.dataCache[id]);
		});
		/*
		var panel = $(target).datagrid('getPanel');
		//wcj: don't find in datagrid-toolbar
		//panel
		panel.children('div.datagrid-view')
				.find('div.datagrid-view2 div.datagrid-body tr.datagrid-row-selected')
				.each(function() {
							var id = $(this).attr('node-id');
							selectedRows.push(find(target, id));
						});
		*/
		return selectedRows;
	};
	function getLevel(target, nodeId) {
		if (!nodeId) {
			return 0;
		}
		var opts = $.data(target, 'treegrid').options;
		//wcj: use cache
//		var gridView = $(target).datagrid('getPanel').children('div.datagrid-view');
//		var treeNode = gridView.find('div.datagrid-body tr[node-id=' + nodeId + ']')
//				.children('td[field=' + opts.treeField + ']');
		var treeNode = opts.tr2Cache[nodeId]
				.children('td[field=' + opts.treeField + ']');
		return treeNode.find('span.tree-indent,span.tree-hit').length;
	};
	function find(target, id) {
		//wcj: use cache
		if (! id) {
			return null;
		}
		var opts = $.data(target, 'treegrid').options;
		if (opts.dataCache && opts.dataCache[id]) {
			return opts.dataCache[id];
		}
		var data = $.data(target, 'treegrid').data;
		var cc = [data];
		while (cc.length) {
			var c = cc.shift();
			for (var i = 0; i < c.length; i++) {
				var rowData = c[i];
				if (rowData[opts.idField] == id) {
					return rowData;
				} else {
					if (rowData['children']) {
						cc.push(rowData['children']);
					}
				}
			}
		}
		return null;
	};
	function select(target, nodeId) {
		//wcj: use cache
		var opts = $.data(target, 'treegrid').options;
		if (opts.tr1Cache[nodeId]) {
			opts.tr1Cache[nodeId].addClass('datagrid-row-selected')
					.find('div.datagrid-cell-check input[type=checkbox]').attr('checked', true);
		}
		if (opts.tr2Cache[nodeId]) {
			opts.tr2Cache[nodeId].addClass('datagrid-row-selected');
			opts.selectedIdCache[nodeId] = nodeId;
		}
		/*
		var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
		var tr = gridBody.find('tr[node-id=' + nodeId + ']');
		tr.addClass('datagrid-row-selected');
		tr.find('div.datagrid-cell-check input[type=checkbox]').attr('checked',
				true);
		*/
		//wcj: onSelect
		opts.onSelect.call(target, nodeId);
	};
	function unselect(target, nodeId) {
		//wcj: use cache
		var opts = $.data(target, 'treegrid').options;
		if (opts.tr1Cache[nodeId]) {
			opts.tr1Cache[nodeId].removeClass('datagrid-row-selected')
					.find('div.datagrid-cell-check input[type=checkbox]').attr('checked', false);
		}
		if (opts.tr2Cache[nodeId]) {
			opts.tr2Cache[nodeId].removeClass('datagrid-row-selected');
			delete opts.selectedIdCache[nodeId];
		}
		/*
		var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
		var tr = gridBody.find('tr[node-id=' + nodeId + ']');
		tr.removeClass('datagrid-row-selected');
		tr.find('div.datagrid-cell-check input[type=checkbox]').attr('checked',
				false);
		*/
		//wcj: onUnselect
		opts.onUnselect.call(target, nodeId);
	};
	function selectAll(target) {
		//wcj: use cache
		var opts = $.data(target, 'treegrid').options;
		$.each(opts.tr1Cache, function(id, tr1) {
			tr1.addClass('datagrid-row-selected')
					.find('div.datagrid-cell-check input[type=checkbox]').attr('checked', true);
		});
		$.each(opts.tr2Cache, function(id, tr2) {
			tr2.addClass('datagrid-row-selected');
			opts.selectedIdCache[id] = id;
		});
		/*
		var tr = $(target).datagrid('getPanel')
				.find('div.datagrid-body tr[node-id]');
		tr.addClass('datagrid-row-selected');
		tr.find('div.datagrid-cell-check input[type=checkbox]').attr('checked',
				true);
		*/
	};
	function unselectAll(target) {
		//wcj: use cache
		var opts = $.data(target, 'treegrid').options;
		$.each(opts.selectedIdCache, function(id, _id) {
			if (opts.tr1Cache[id]) {
				opts.tr1Cache[id].removeClass('datagrid-row-selected')
						.find('div.datagrid-cell-check input[type=checkbox]').attr('checked', false);
			}
			if (opts.tr2Cache[id]) {
				opts.tr2Cache[id].removeClass('datagrid-row-selected');
			}
		});
		opts.selectedIdCache = {};
		/*
		//wcj: don't find unselected tr's
		//var tr = $(target).datagrid('getPanel')
		//		.find('div.datagrid-body tr[node-id]');
		var tr = $(target).datagrid('getPanel')
				.find('div.datagrid-body tr.datagrid-row-selected');
		tr.removeClass('datagrid-row-selected');
		tr.find('div.datagrid-cell-check input[type=checkbox]').attr('checked',
				false);
		*/
	};
	function collapse(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
//		var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
		var row = find(target, nodeId);
		//wcj: use cache
//		var tr = gridBody.find('tr[node-id=' + nodeId + ']');
		var tr = opts.tr1Cache[nodeId].add(opts.tr2Cache[nodeId]);
		var hit = tr.find('span.tree-hit');
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass('tree-collapsed')) {
			return;
		}
		if (opts.onBeforeCollapse.call(target, row) == false) {
			return;
		}
		hit.removeClass('tree-expanded tree-expanded-hover')
				.addClass('tree-collapsed');
		hit.next().removeClass('tree-folder-open');
		row.state = 'closed';
		tr = tr.next('tr.treegrid-tr-tree');
		var cc = tr.children('td').children('div');
		if (opts.animate) {
			cc.slideUp('normal', function() {
						setRowHeight(target, nodeId);
						opts.onCollapse.call(target, row);
					});
		} else {
			cc.hide();
			setRowHeight(target, nodeId);
			opts.onCollapse.call(target, row);
		}
	};
	function expand(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		//wcj: use cache
//		var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
//		var tr = gridBody.find('tr[node-id=' + nodeId + ']');
		var tr = opts.tr1Cache[nodeId].add(opts.tr2Cache[nodeId]);
		var hit = tr.find('span.tree-hit');
		var row = find(target, nodeId);
		if (hit.length == 0) {
			return;
		}
		if (hit.hasClass('tree-expanded')) {
			return;
		}
		if (opts.onBeforeExpand.call(target, row) == false) {
			return;
		}
		hit.removeClass('tree-collapsed tree-collapsed-hover')
				.addClass('tree-expanded');
		hit.next().addClass('tree-folder-open');
		var subtree = tr.next('tr.treegrid-tr-tree');
		if (subtree.length) {
			var cc = subtree.children('td').children('div');
			expandSubtree(cc);
		} else {
			initSubTree(target, row[opts.idField]);
			var subtree = tr.next('tr.treegrid-tr-tree');
			var cc = subtree.children('td').children('div');
			cc.hide();
			request(target, row[opts.idField], {
						id : row[opts.idField]
					}, true, function() {
						expandSubtree(cc);
					});
		}
		function expandSubtree(cc) {
			row.state = 'open';
			if (opts.animate) {
				cc.slideDown('normal', function() {
							setRowHeight(target, nodeId);
							opts.onExpand.call(target, row);
						});
			} else {
				cc.show();
				setRowHeight(target, nodeId);
				opts.onExpand.call(target, row);
			}
		};
	};
	function toggle(target, nodeId) {
		//wcj: use cache
//		var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
//		var tr = gridBody.find('tr[node-id=' + nodeId + ']');
		var opts = $.data(target, 'treegrid').options;
		var tr = opts.tr2Cache[nodeId];
		var hit = tr.find('span.tree-hit');
		if (hit.hasClass('tree-expanded')) {
			collapse(target, nodeId);
		} else {
			expand(target, nodeId);
		}
	};
	function collapseAll(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		var children = getChildren(target, nodeId);
		if (nodeId) {
			children.unshift(find(target, nodeId));
		}
		for (var i = 0; i < children.length; i++) {
			collapse(target, children[i][opts.idField]);
		}
	};
	function expandAll(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		var children = getChildren(target, nodeId);
		if (nodeId) {
			children.unshift(find(target, nodeId));
		}
		for (var i = 0; i < children.length; i++) {
			expand(target, children[i][opts.idField]);
		}
	};
	function expandTo(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		var ids = [];
		var p = getParent(target, nodeId);
		while (p) {
			var id = p[opts.idField];
			ids.unshift(id);
			p = getParent(target, id);
		}
		for (var i = 0; i < ids.length; i++) {
			expand(target, ids[i]);
		}
	};
	function append(target, param) {
		var opts = $.data(target, 'treegrid').options;
		if (param.parent) {
			//wcj: use cache
//			var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
//			var tr = gridBody.find('tr[node-id=' + param.parent + ']');
			var tr = opts.tr2Cache[param.parent];
			if (tr.next('tr.treegrid-tr-tree').length == 0) {
				initSubTree(target, param.parent);
			}
			var td = tr.children('td[field=' + opts.treeField + ']')
					.children('div.datagrid-cell');
			var icon = td.children('span.tree-icon');
			if (icon.hasClass('tree-file')) {
				icon.removeClass('tree-file').addClass('tree-folder');
				var hit = $('<span class="tree-hit tree-expanded"></span>')
						.insertBefore(icon);
				if (hit.prev().length) {
					hit.prev().remove();
				}
			}
		}
		loadData(target, param.parent, param.data, true);
	};
	function remove(target, nodeId) {
		var opts = $.data(target, 'treegrid').options;
		//wcj: use cache
//		var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
//		var tr = gridBody.find('tr[node-id=' + nodeId + ']');
		var tr = opts.tr1Cache[nodeId].add(opts.tr2Cache[nodeId]);
		tr.next('tr.treegrid-tr-tree').remove();
		tr.remove();
		//wcj: del from cache
		delete opts.selectedIdCache[nodeId];
		delete opts.dataCache[nodeId];
		delete opts.tr1Cache[nodeId];
		delete opts.tr2Cache[nodeId];
		var parent = del(nodeId);
		if (parent) {
			if (parent.children.length == 0) {
				//wcj: use cache
//				tr = gridBody.find('tr[node-id=' + parent[opts.treeField] + ']');
				tr = opts.tr1Cache[parent[opts.treeField]].add(opts.tr2Cache[parent[opts.treeField]]);
				var cell = tr.children('td[field=' + opts.treeField + ']')
						.children('div.datagrid-cell');
				cell.find('.tree-icon').removeClass('tree-folder')
						.addClass('tree-file');
				cell.find('.tree-hit').remove();
				$('<span class="tree-indent"></span>').prependTo(cell);
			}
		}
		fixRowNumbers(target);
		function del(id) {
			var cc;
			var parent = getParent(target, nodeId);
			if (parent) {
				cc = parent.children;
			} else {
				cc = $(target).treegrid('getData');
			}
			for (var i = 0; i < cc.length; i++) {
				if (cc[i][opts.treeField] == id) {
					cc.splice(i, 1);
					break;
				}
			}
			return parent;
		};
	};
	//wcj: add resetOperation
	function resetOperation(target) {
		//wcj:
		if (! $.data(target, 'datagrid')) {
			return;
		}
		var data = $.data(target, 'datagrid').data;
		var rows = data.rows;
		var originalRows = [];
		for (var i = 0; i < rows.length; i++) {
			originalRows.push($.extend({}, rows[i]));
		}
		$.data(target, 'datagrid').originalRows = originalRows;
		$.data(target, 'datagrid').updatedRows = [];
		$.data(target, 'datagrid').insertedRows = [];
		$.data(target, 'datagrid').deletedRows = [];
	};
	
	$.fn.treegrid = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.treegrid.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.datagrid(options, param);
			}
			return $.fn.treegrid.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'treegrid');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'treegrid', {
									options : $.extend({},
											$.fn.treegrid.defaults,
											$.fn.treegrid.parseOptions(this),
											options),
									data : []
								});
					}
					initGrid(this);
					//wcj: don't load data when initialed
					//request(this);
				});
	};
	$.fn.treegrid.methods = {
		options : function(jq) {
			return $.data(jq[0], 'treegrid').options;
		},
		resize : function(jq, options) {
			return jq.each(function() {
						$(this).datagrid('resize', options);
					});
		},
		fixRowHeight : function(jq, id) {
			return jq.each(function() {
						setRowHeight(this, id);
					});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
						loadData(this, null, data);
						//wcj: add resetOperation
						resetOperation(this);
					});
		},
		reload : function(jq, id) {
			return jq.each(function() {
						if (id) {
							var record = $(this).treegrid('find', id);
							if (record.children) {
								record.children.splice(0, record.children.length);
							}
							//wcj: use cache
//							var gridBody = $(this).datagrid('getPanel')
//									.find('div.datagrid-body');
//							var tr = gridBody.find('tr[node-id=' + id + ']');
							var opts = $.data(this, 'treegrid').options;
							var tr = opts.tr1Cache[id].add(opts.tr2Cache[id]);
							tr.next('tr.treegrid-tr-tree').remove();
							var hit = tr.find('span.tree-hit');
							hit
									.removeClass('tree-expanded tree-expanded-hover')
									.addClass('tree-collapsed');
							expand(this, id);
						} else {
							request(this);
						}
					});
		},
		reloadFooter : function(jq, footer) {
			return jq.each(function() {
						var opts = $.data(this, 'treegrid').options;
						var gridView = $(this).datagrid('getPanel')
								.children('div.datagrid-view');
						var gridView1 = gridView.children('div.datagrid-view1');
						var gridView2 = gridView.children('div.datagrid-view2');
						if (footer) {
							$.data(this, 'treegrid').footer = footer;
						}
						if (opts.showFooter) {
							opts.view.renderFooter.call(opts.view, this, gridView1
											.find('div.datagrid-footer-inner'),
									true);
							opts.view.renderFooter.call(opts.view, this, gridView2
											.find('div.datagrid-footer-inner'),
									false);
							if (opts.view.onAfterRender) {
								opts.view.onAfterRender.call(opts.view, this);
							}
							$(this).treegrid('fixRowHeight');
						}
					});
		},
		loading : function(jq) {
			return jq.each(function() {
						$(this).datagrid('loading');
					});
		},
		loaded : function(jq) {
			return jq.each(function() {
						$(this).datagrid('loaded');
					});
		},
		getData : function(jq) {
			return $.data(jq[0], 'treegrid').data;
		},
		getFooterRows : function(jq) {
			return $.data(jq[0], 'treegrid').footer;
		},
		getRoot : function(jq) {
			return getRoot(jq[0]);
		},
		getRoots : function(jq) {
			return getRoots(jq[0]);
		},
		getParent : function(jq, id) {
			return getParent(jq[0], id);
		},
		getChildren : function(jq, id) {
			return getChildren(jq[0], id);
		},
		getSelected : function(jq) {
			return getSelected(jq[0]);
		},
		getSelections : function(jq) {
			return getSelections(jq[0]);
		},
		getLevel : function(jq, id) {
			return getLevel(jq[0], id);
		},
		find : function(jq, id) {
			return find(jq[0], id);
		},
		select : function(jq, id) {
			return jq.each(function() {
						select(this, id);
					});
		},
		unselect : function(jq, id) {
			return jq.each(function() {
						unselect(this, id);
					});
		},
		selectAll : function(jq) {
			return jq.each(function() {
						selectAll(this);
					});
		},
		unselectAll : function(jq) {
			return jq.each(function() {
						unselectAll(this);
					});
		},
		collapse : function(jq, id) {
			return jq.each(function() {
						collapse(this, id);
					});
		},
		expand : function(jq, id) {
			return jq.each(function() {
						expand(this, id);
					});
		},
		toggle : function(jq, id) {
			return jq.each(function() {
						toggle(this, id);
					});
		},
		collapseAll : function(jq, id) {
			return jq.each(function() {
						collapseAll(this, id);
					});
		},
		expandAll : function(jq, id) {
			return jq.each(function() {
						expandAll(this, id);
					});
		},
		expandTo : function(jq, id) {
			return jq.each(function() {
						expandTo(this, id);
					});
		},
		append : function(jq, param) {
			return jq.each(function() {
						append(this, param);
					});
		},
		remove : function(jq, id) {
			return jq.each(function() {
						remove(this, id);
					});
		},
		refresh : function(jq, id) {
			return jq.each(function() {
						var opts = $.data(this, 'treegrid').options;
						opts.view.refreshRow.call(opts.view, this, id);
					});
		},
		beginEdit:function(jq,id){
			return jq.each(function(){
				$(this).datagrid('beginEdit',id);
				$(this).treegrid('fixRowHeight',id);
			});
		},
		endEdit:function(jq,id){
			return jq.each(function(){
				$(this).datagrid('endEdit',id);
			});
		},
		cancelEdit:function(jq,id){
			return jq.each(function(){
				$(this).datagrid('cancelEdit',id);
			});
		}
	};
	$.fn.treegrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.datagrid.parseOptions(target), {
					treeField : t.attr('treeField'),
					animate : (t.attr('animate')
							? t.attr('animate') == 'true'
							: undefined)
				});
	};
	var view = $.extend({}, $.fn.datagrid.defaults.view, {
		render : function(target, container, frozen) {
			var opts = $.data(target, 'treegrid').options;
			var fields = $(target).datagrid('getColumnFields', frozen);
			var grid = this;
			//wcj: prepare columnOptions
			var columnOptions = {};
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i];
				columnOptions[field] = $(target).datagrid('getColumnOption', field);
			}
			var nodes = buildTreeNodes(frozen, this.treeLevel, this.treeNodes);
			$(container).append(nodes.join(''));
			function buildTreeNodes(frozen, treeLevel, rows) {
				var html = ['<table cellspacing="0" cellpadding="0" border="0"><tbody>'];
				for (var i = 0; i < rows.length; i++) {
					var row = rows[i];
					if (row.state != 'open' && row.state != 'closed') {
						row.state = 'open';
					}
					var style = opts.rowStyler ? opts.rowStyler.call(target, row) : '';
					var attr = style ? 'style="' + style + '"' : '';
					html.push('<tr node-id=' + row[opts.idField] + ' ' + attr
							+ '>');
					html = html.concat(grid.renderRow.call(grid, target, fields, frozen,
							treeLevel, row,  columnOptions));
					html.push('</tr>');
					if (row.children && row.children.length) {
						var tt = buildTreeNodes(frozen, treeLevel + 1, row.children);
						var v = row.state == 'closed' ? 'none' : 'block';
						html
								.push('<tr class="treegrid-tr-tree"><td style="border:0px" colspan='
										+ (fields.length + (opts.rownumbers ? 1 : 0))
										+ '><div style="display:' + v + '">');
						html = html.concat(tt);
						html.push('</div></td></tr>');
					}
				}
				html.push('</tbody></table>');
				return html;
			};
		},
		renderFooter : function(target, grid, frozen) {
			var opts = $.data(target, 'treegrid').options;
			var footer = $.data(target, 'treegrid').footer || [];
			var fields = $(target).datagrid('getColumnFields', frozen);
			var html = ['<table cellspacing="0" cellpadding="0" border="0"><tbody>'];
			for (var i = 0; i < footer.length; i++) {
				var row = footer[i];
				row[opts.idField] = row[opts.idField] || ('foot-row-id' + i);
				html.push('<tr node-id=' + row[opts.idField] + '>');
				html.push(this.renderRow.call(this, target, fields, frozen, 0, row));
				html.push('</tr>');
			}
			html.push('</tbody></table>');
			$(grid).html(html.join(''));
		},
		//wcj: add param columnOptions, useHeaderWidth
		renderRow : function(target, fields, frozen, deepth, row, columnOptions, useHeaderWidth) {
			var opts = $.data(target, 'treegrid').options;
			var cc = [];
			if (frozen && opts.rownumbers) {
				cc
						.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">0</div></td>');
			}
			for (var i = 0; i < fields.length; i++) {
				var field = fields[i];
				//wcj: use prepared columnOptions
				//var col = $(target).datagrid('getColumnOption', field);
				var col = columnOptions ? columnOptions[field] : $(target).datagrid('getColumnOption', field);
				if (col) {
					var style = col.styler
							? (col.styler(row[field], row) || '')
							: '';
					style = col.hidden
							? 'style="display:none;' + style + '"'
							: (style ? 'style="' + style + '"' : '');
					cc.push('<td field="' + field + '" ' + style + '>');
					//wcj:
					//var style = 'width:' + (col.boxWidth) + 'px;';
					var style = 'width:' + (useHeaderWidth && col.header ? col.header.width() : col.boxWidth) + 'px;';
					style += 'text-align:' + (col.align || 'left') + ';';
					style += opts.nowrap == false ? 'white-space:normal;' : '';
					cc.push('<div style="' + style + '" ');
					if (col.checkbox) {
						cc.push('class="datagrid-cell-check ');
					} else {
						cc.push('class="datagrid-cell ');
					}
					cc.push('">');
					if (col.checkbox) {
						if (row.checked) {
							cc
									.push('<input type="checkbox" checked="checked"/>');
						} else {
							cc.push('<input type="checkbox"/>');
						}
					} else {
						var val = null;
						if (col.formatter) {
							val = col.formatter(row[field], row);
						} else {
							val = row[field] || '&nbsp;';
						}
						if (field == opts.treeField) {
							for (var j = 0; j < deepth; j++) {
								cc.push('<span class="tree-indent"></span>');
							}
							if (row.state == 'closed') {
								cc
										.push('<span class="tree-hit tree-collapsed"></span>');
								cc.push('<span class="tree-icon tree-folder '
										+ (row.iconCls ? row.iconCls : '')
										+ '"></span>');
							} else {
								if (row.children && row.children.length) {
									cc
											.push('<span class="tree-hit tree-expanded"></span>');
									cc
											.push('<span class="tree-icon tree-folder tree-folder-open '
													+ (row.iconCls
															? row.iconCls
															: '')
													+ '"></span>');
								} else {
									cc
											.push('<span class="tree-indent"></span>');
									cc
											.push('<span class="tree-icon tree-file '
													+ (row.iconCls
															? row.iconCls
															: '')
													+ '"></span>');
								}
							}
							cc.push('<span class="tree-title">' + val
									+ '</span>');
						} else {
							cc.push(val);
						}
					}
					cc.push('</div>');
					cc.push('</td>');
				}
			}
			return cc.join('');
		},
		refreshRow : function(target, id) {
			var row = $(target).treegrid('find', id);
			var opts = $.data(target, 'treegrid').options;
//			var gridBody = $(target).datagrid('getPanel').find('div.datagrid-body');
			var style = opts.rowStyler ? opts.rowStyler.call(target, row) : '';
			style = style ? 'style="' + style + '"' : '';
			//wcj: use cache
//			var tr = gridBody.find('tr[node-id=' + id + ']');
			var tr = opts.tr1Cache[id].add(opts.tr2Cache[id]);
			tr.attr('style', style);
			tr.children('td').each(function() {
				var cell = $(this).find('div.datagrid-cell');
				var field = $(this).attr('field');
				var col = $(target).datagrid('getColumnOption', field);
				if (col) {
					var style = col.styler
							? (col.styler(row[field], row) || '')
							: '';
					style = col.hidden
							? 'style="display:none;' + style + '"'
							: (style ? 'style="' + style + '"' : '');
					$(this).attr('style', style);
					var val = null;
					if (col.formatter) {
						val = col.formatter(row[field], row);
					} else {
						val = row[field] || '&nbsp;';
					}
					if (field == opts.treeField) {
						cell.children('span.tree-title').html(val);
						var cls = 'tree-icon';
						var icon = cell.children('span.tree-icon');
						if (icon.hasClass('tree-folder')) {
							cls += ' tree-folder';
						}
						if (icon.hasClass('tree-folder-open')) {
							cls += ' tree-folder-open';
						}
						if (icon.hasClass('tree-file')) {
							cls += ' tree-file';
						}
						if (row.iconCls) {
							cls += ' ' + row.iconCls;
						}
						icon.attr('class', cls);
					} else {
						cell.html(val);
					}
				}
			});
			$(target).treegrid('fixRowHeight', id);
		},
		onBeforeRender : function(target, nodeId, param) {
			if (!param) {
				return false;
			}
			var opts = $.data(target, 'treegrid').options;
			if (param.length == undefined) {
				if (param.footer) {
					$.data(target, 'treegrid').footer = param.footer;
				}
				if (param.total) {
					$.data(target, 'treegrid').total = param.total;
				}
				param = this.transfer(target, nodeId, param.rows);
			} else {
				function setParent(param, nodeId) {
					for (var i = 0; i < param.length; i++) {
						var row = param[i];
						row._parentId = nodeId;
						if (row.children && row.children.length) {
							setParent(row.children, row[opts.idField]);
						}
					}
				};
				setParent(param, nodeId);
			}
			var node = find(target, nodeId);
			if (node) {
				if (node.children) {
					node.children = node.children.concat(param);
				} else {
					node.children = param;
				}
			} else {
				$.data(target, 'treegrid').data = $.data(target, 'treegrid').data
						.concat(param);
			}
			if (!opts.remoteSort) {
				this.sort(target, param);
			}
			this.treeNodes = param;
			this.treeLevel = $(target).treegrid('getLevel', nodeId);
		},
		sort : function(target, param) {
			var opts = $.data(target, 'treegrid').options;
			var opt = $(target).treegrid('getColumnOption', opts.sortName);
			if (opt) {
				var sorter = opt.sorter || function(a, b) {
					return (a > b ? 1 : -1);
				};
				sort(param);
			}
			function sort(param) {
				param.sort(function(r1, r2) {
							return sorter(r1[opts.sortName], r2[opts.sortName])
									* (opts.sortOrder == 'asc' ? 1 : -1);
						});
				for (var i = 0; i < param.length; i++) {
					var children = param[i].children;
					if (children && children.length) {
						sort(children);
					}
				}
			};
		},
		transfer : function(target, nodeId, rows) {
			var opts = $.data(target, 'treegrid').options;
			var rowsCopy = [];
			for (var i = 0; i < rows.length; i++) {
				rowsCopy.push(rows[i]);
			}
			//wcj: rows map
			var rowsMap = {};
			for (var i = 0; i < rows.length; i++) {
				rowsMap[rows[i][opts.idField]] = rows[i];
			}
			
			var children = [];
			for (var i = 0; i < rowsCopy.length; i++) {
				var row = rowsCopy[i];
				if (!nodeId) {
					//wcj: find roots
					if (!row._parentId || ! rowsMap[row._parentId]) {
						children.push(row);
						rowsCopy.remove(row);
						i--;
					}
				} else {
					if (row._parentId == nodeId) {
						children.push(row);
						rowsCopy.remove(row);
						i--;
					}
				}
			}
			var childrenCopy = [];
			for (var i = 0; i < children.length; i++) {
				childrenCopy.push(children[i]);
			}
			while (childrenCopy.length) {
				var child = childrenCopy.shift();
				for (var i = 0; i < rowsCopy.length; i++) {
					var row = rowsCopy[i];
					if (row._parentId == child[opts.idField]) {
						if (child.children) {
							child.children.push(row);
						} else {
							child.children = [row];
						}
						childrenCopy.push(row);
						rowsCopy.remove(row);
						i--;
					}
				}
			}
			return children;
		}
	});
	$.fn.treegrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
				treeField : null,
				animate : false,
				singleSelect : true,
				view : view,
				loadFilter:function(data,nodeId){
					return data;
				},
				editConfig:{
					getTr:function(target,id){
						//wcj: use cache
//						return $(target).datagrid('getPanel').find('div.datagrid-body tr[node-id='+id+']');
						return $.data(target, 'treegrid').options.tr2Cache[id];
					},
					getRow:function(target,id){
						return $(target).treegrid('find',id);
					}
				},
				onBeforeLoad : function(row, param) {
				},
				onLoadSuccess : function(row, param) {
				},
				onLoadError : function() {
				},
				onBeforeCollapse : function(row) {
				},
				onCollapse : function(row) {
				},
				onBeforeExpand : function(row) {
				},
				onExpand : function(row) {
				},
				onClickRow : function(row) {
				},
				onDblClickRow : function(row) {
				},
				onContextMenu : function(e, row) {
				},
				onBeforeEdit:function(row){
				},
				onAfterEdit:function(row,newValues){
				},
				onCancelEdit:function(row){
				}
			});
})(jQuery);

/**
 * combo - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   panel
 *   validatebox
 * 
 */
(function($) {
	function setSize(target, width) {
		var state = $.data(target,'combo');
		var opts = state.options;
		var combo = $.data(target, 'combo').combo;
		var panel = $.data(target, 'combo').panel;
		var textbox = combo.children('input.combo-text');
		//wcj:
		if (! width) {
			var $target = $(target);
			var targetWidth = $target.width();
			$target.css('width', '');
			var adjustWidth = targetWidth - $target.width();
			if (adjustWidth != 0) {
				textbox.width(textbox.width() + adjustWidth);
			}
		} else {
			var adjustWidth = width - combo.outerWidth();
			if (adjustWidth != 0) {
				textbox.width(textbox.width() + adjustWidth);
			}
		}
		return;
		
		if (width) {
			opts.width = width;
		}
		//wcj:
		//combo.appendTo('body');
		if (isNaN(opts.width)) {
			//wcj: use children for better performance
			opts.width = combo.children('input.combo-text').outerWidth();
		}
		var arrowWidth = 0;
		if (opts.hasDownArrow) {
			//wcj: use children for better performance
			arrowWidth = combo.children('span').children('.combo-arrow').outerWidth();
		}
		var width = opts.width - arrowWidth;
		if ($.boxModel == true) {
			width -= combo.outerWidth() - combo.width();
		}
		//wcj:
		//combo.find('input.combo-text').width(width);
		//wcj: use children for better performance
		if (combo.closest('.datagrid-editable').size() > 0 && $.boxModel == true) {
			var textboxWidthMinus = textbox.outerWidth() - textbox.width();
			if (textboxWidthMinus > arrowWidth) {
				textboxWidthMinus -= arrowWidth;
			}
			width -= textboxWidthMinus;
		}
		textbox.width(width);
		//wcj: use textbox width as panel width
		/*
		panel.panel('resize', {
			width : (opts.panelWidth ? opts.panelWidth : combo.outerWidth()),
			height : opts.panelHeight
		});
		*/
		//wcj: don't resize until opend
//		panel.panel('resize', {
//			width : (opts.panelWidth ? opts.panelWidth : Math.max(combo.outerWidth(), textbox.outerWidth())),
//			height : opts.panelHeight
//		});
		//wcj:
//		combo.insertAfter(target);
	};
	function initArrow(target) {
		var opts = $.data(target, 'combo').options;
		var combo = $.data(target, 'combo').combo;
		if (opts.hasDownArrow) {
			//wcj: use children for better performance
			combo.children('span').children('.combo-arrow').show();
		} else {
			//wcj: use children for better performance
			combo.children('span').children('.combo-arrow').hide();
		}
	};
	function init(target) {
		$(target).addClass('combo-f').hide();
		var span = $('<span class="combo"></span>').insertAfter(target);
		var input = $('<input type="text" class="combo-text">').appendTo(span);
		//wcl tooltip
	    if($(target).attr("tooltip")){
			input.attr("tooltip", $(target).attr("tooltip"));
			input.tooltip();
		}
	    //wcj: placeholder
	    if($(target).attr("placeholder")){
		    input.attr("placeholder", $(target).attr("placeholder"));
	    	input.placeholder();
	    }
	    
		$('<span><span class="combo-arrow"></span></span>').appendTo(span);
		$('<input type="hidden" class="combo-value">').appendTo(span);
		//wcj: panel inline
		//var panel = $('<div class="combo-panel"></div>').appendTo('body');
		var panel = $('<div class="combo-panel"></div>').appendTo(span);
		panel.panel({
					doSize : false,
					closed : true,
					style : {
						//wcj:
//						position : 'absolute',
						position : 'fixed',
						zIndex : 10
					},
					onOpen : function() {
						$(this).panel('resize');
					}
				});
		var name = $(target).attr('name');
		if (name) {
			span.find('input.combo-value').attr('name', name);
			$(target).removeAttr('name').attr('comboName', name);
		}
		input.attr('autocomplete', 'off');
		return {
			combo : span,
			panel : panel
		};
	};
	function destroy(target) {
		//wcj: use children for better performance
		var input = $.data(target, 'combo').combo.children('input.combo-text');
		input.validatebox('destroy');
		$.data(target, 'combo').panel.panel('destroy');
		$.data(target, 'combo').combo.remove();
		$(target).remove();
	};
	function bindEvents(target) {
		//wcj: fix bug. add var "state". OK in jquery.easyui.min-1.2.4.js
		var state = $.data(target,'combo');
		var $target = $(target);
		//wcj end
		var opts = $.data(target, 'combo').options;
		var combo = $.data(target, 'combo').combo;
		var panel = $.data(target, 'combo').panel;
		//wcj: use children for better performance
		var input = combo.children('.combo-text');
		var arrow = combo.children('span').children('.combo-arrow');
		//wcj
		//Do nothing. onBlur will run
		/*
		$(document).unbind('.combo').bind('mousedown.combo', function(e) {
					$('div.combo-panel').panel('close');
				});
		*/
		combo.unbind('.combo');
		panel.unbind('.combo');
		input.unbind('.combo');
		arrow.unbind('.combo');
		if (!opts.disabled) {
			panel.bind('mousedown.combo', function(e) {
						return false;
					});
			input.bind('mousedown.combo', function(e) {
						e.stopPropagation();
					}).bind('keydown.combo', function(e) {
				if (e.ctrlKey) {
					return;
				}
				switch (e.keyCode) {
					case 38 :  // up
						//wcj:
						e.preventDefault();
						showPanel(target);
						opts.keyHandler.up.call(target);
						break;
					case 40 :  // down
						//wcj:
						e.preventDefault();
						showPanel(target);
						opts.keyHandler.down.call(target);
						break;
					case 13 :  // enter
						e.preventDefault();
						//wcj:
						//opts.keyHandler.enter.call(target);
						checkInput();
						return false;
					case 9 :   // tab
						//wcj
						//Do nothing. onBlur will run
						break;
					case 27 :  // esc
						//wcj: esc reset values
						if ($target.hasClass("combobox-f")) {
							var oldText = $target.data("oldText");
							$target.combobox("setValues", $target.data("oldValues"));
							$target.combobox("setText", oldText);
						} else if ($target.hasClass("combogrid-f")) {
							var oldText = $target.data("oldText");
							$target.combogrid("setValues", $target.data("oldValues"));
							$target.combogrid("setText", oldText);
						}
						hidePanel(target);
						break;
					case 46 : //del
					case 8 : //backspace
						//wcj: use del or back key to clear values for combotree
						if ($target.hasClass("combotree-f") && ! $.data(target, "combo").options.disabled) {
							$target.combotree("setValue", null);
						}
					default :
						//wcj:
						setTimeout(function() {
							if (state.timer && state.previousValue == input.val()) {
								return;
							}
							if (! state.previousValue && ! input.val()) {
								return;
							}
							if (opts.editable) {
								if(state.timer){
									clearTimeout(state.timer);
								}
								state.timer = setTimeout(function() {
											var q = input.val();
											//wcj: trim
											if (state.previousValue != q) {
												state.previousValue = q;
												showPanel(target);
												opts.keyHandler.query.call(target, $.trim(q));
												//wcj:
												//validate(target, true);
											}
											state.timer = null;
										}, opts.delay);
							}
						}, 0);
				}
			});
			//wcj:
			//In FireFox input chinese will not fire 'keydown' event.
			//But 2 'input' events with the same event.target.value will be fired.
			//So we can start the timer at the second time.
			input.bind("input.combo", function(event) {
				//wcj:
				setTimeout(function() {
					if (state.timer && state.previousValue == input.val()) {
						return;
					}
					if (! state.previousValue && ! input.val()) {
						return;
					}
					if (opts.editable) {
						if(state.timer){
							clearTimeout(state.timer);
						}
						state.timer = setTimeout(function() {
							var q = input.val();
							//wcj: trim
							if (state.previousValue != q) {
								state.previousValue = q;
								showPanel(target);
								opts.keyHandler.query.call(target, $.trim(q));
								//wcj:
								//validate(target, true);
							}
							state.timer = null;
						}, opts.delay);
					}
				}, 0);
			});
			arrow.bind('click.combo', function() {
						if (panel.is(":visible")) {
							hidePanel(target);
						} else {
							$("div.combo-panel").panel("close");
							//wcj: focus only if show panel
							input.focus();
							showPanel(target);
						}
					}).bind('mouseenter.combo', function() {
						$(this).addClass('combo-arrow-hover');
					}).bind('mouseleave.combo', function() {
						$(this).removeClass('combo-arrow-hover');
					}).bind('mousedown.combo', function() {
						return false;
					});
			//wcj:
			if (($target.hasClass("combobox-f") || $target.hasClass("combogrid-f"))
					&& ! $target.combo("options").customValuePermitted
					&& ! $target.combo("options").multiple) {
				input.bind("focus.combo", function(event) {
					/*
					if (! $target.attr("disabled")) {
						$target.combo("showPanel");
					}
					*/
					$target.data("oldValues", $target.combo("getValues"));
					$target.data("oldText", $target.combo("getText"));
				}).bind("blur.combo", function(event) {
					setTimeout(blurHandler, 0);
					function blurHandler() {
						var $focus = $(":focus");
						if ($focus[0] == combo[0]
								|| $focus.closest(".combo")[0] == combo[0]
								|| $focus.hasClass("combo-panel")
								|| $focus.closest(".combo-panel").size() > 0) {
							$focus.unbind("blur.combo").bind("blur.combo", function(event) {
								setTimeout(blurHandler, 0);
							});
						} else {
							checkInput();
						}
					};
				});
			} else {
				input.bind("focus.combo", function(event) {
					/*
					if (! $target.attr("disabled")) {
						showPanel(target);
					}
					*/
				}).bind("blur.combo", function(event) {
					setTimeout(blurHandler, 0);
					function blurHandler() {
						var $focus = $(":focus");
						if ($focus[0] == combo[0]
								|| $focus.closest(".combo")[0] == combo[0]
								|| $focus.hasClass("combo-panel")
								|| $focus.closest(".combo-panel").size() > 0) {
							$focus.unbind("blur.combo").bind("blur.combo", function(event) {
								setTimeout(blurHandler, 0);
							});
						} else {
							hidePanel(target);
						}
					};
				});
			}
		}
		function checkInput() {
			if (! input.val()) {
				if ($target.hasClass("combobox-f")) {
					$target.combobox("setValue", null);
				} else if ($target.hasClass("combogrid-f")) {
					$target.combogrid("setValue", null);
				}
				opts.keyHandler.enter.call(target);
				return;
			}
			if ($target.hasClass("combobox-f")) {
				if (panel.find("div.combobox-item-selected").size() == 0) {
					if ($target.data("oldValues")) {
						var oldText = $target.data("oldText");
						$target.combobox("setValues", $target.data("oldValues"));
						$target.combobox("setText", oldText);
					}
				}
				opts.keyHandler.enter.call(target);
			} else if ($target.hasClass("combogrid-f")) {
				function callEnter() {
					if (state.timer || $target.combogrid("grid").data("loading")) {
						setTimeout(function() {
							callEnter();
						}, 100);
					} else {
						if ($target.combogrid("grid").datagrid("getSelectedIndex") == -1) {
							if ($target.data("oldValues")) {
								var oldText = $target.data("oldText");
								$target.combogrid("setValues", $target.data("oldValues"));
								$target.combogrid("setText", oldText);
							}
						}
						opts.keyHandler.enter.call(target);
					}
				};
				callEnter();
			}
		};
	};
	function showPanel(target) {
		var opts = $.data(target, 'combo').options;
		var combo = $.data(target, 'combo').combo;
		var panel = $.data(target, 'combo').panel;
		var textbox = combo.children('input.combo-text');
		//wcj:
		if (panel.is(':visible')) {
			return;
		}
		//wcj:
		var panelWidth = opts.panelWidth ? opts.panelWidth : Math.max(combo.outerWidth(), textbox.outerWidth());
		if (panelWidth != opts._panelWidth) {
			opts._panelWidth = panelWidth;
			panel.panel('resize', {
				width : panelWidth,
				height : opts.panelHeight
			});
		}
		//wcj: move inline panel to body
		panel.parent().appendTo("body");
		if ($.fn.window) {
			panel.panel('panel').css('z-index', $.fn.window.defaults.zIndex++);
		}
		panel.panel('move', {
					left : combo.offset().left,
					top : fixedTop()
				});
		panel.panel('open');
		opts.onShowPanel.call(target);
		(function() {
			if (panel.is(':visible')) {
				panel.panel('move', {
							left : fixedLeft(),
							top : fixedTop()
						});
				setTimeout(arguments.callee, 200);
			}
		})();
		function fixedLeft() {
			var left = combo.offset().left;
			if (left + panel.outerWidth() > $(window).width()
					+ $(document).scrollLeft()) {
				left = $(window).width() + $(document).scrollLeft()
						- panel.outerWidth();
			}
			if (left < 0) {
				left = 0;
			}
			return left;
		};
		function fixedTop() {
			var top = combo.offset().top + combo.outerHeight();
			if (top + panel.outerHeight() > $(window).height()
					+ $(document).scrollTop()) {
				top = combo.offset().top - panel.outerHeight();
			}
			if (top < $(document).scrollTop()) {
				top = combo.offset().top + combo.outerHeight();
			}
			return top;
		};
	};
	function hidePanel(target) {
		var opts = $.data(target, 'combo').options;
		var panel = $.data(target, 'combo').panel;
		//wcj:
		if (! panel.is(':visible')) {
			return;
		}
		panel.panel('close');
		opts.onHidePanel.call(target);
		//wcj: move panel inline
		panel.parent().appendTo($.data(target, 'combo').combo);
	};
	function validate(target, doit) {
		var opts = $.data(target, 'combo').options;
		//wcj: use children for better performance
		var input = $.data(target, 'combo').combo.children('input.combo-text');
		//wcj: init only once
		if (! input.hasClass('validatebox-text')) {
			input.validatebox(opts);
		}
		if (doit) {
			//wcj: return value
			//input.validatebox('validate');
			var result = input.validatebox('validate', false);
			return result;
			//wcj: mouseleave event will hide the tip
			//input.trigger('mouseleave');
		}
	};
	function setDisabled(target, disabled) {
		var opts = $.data(target, 'combo').options;
		var combo = $.data(target, 'combo').combo;
		if (disabled) {
			opts.disabled = true;
			$(target).attr('disabled', true);
			//wcj: use children for better performance
			combo.children('.combo-value').attr('disabled', true);
			combo.children('.combo-text').attr('disabled', true);
		} else {
			opts.disabled = false;
			$(target).removeAttr('disabled');
			//wcj: use children for better performance
			combo.children('.combo-value').removeAttr('disabled');
			combo.children('.combo-text').removeAttr('disabled');
		}
	};
	function clear(target) {
		var opts = $.data(target, 'combo').options;
		var combo = $.data(target, 'combo').combo;
		if (opts.multiple) {
			//wcj: use children for better performance
			combo.children('input.combo-value').remove();
		} else {
			//wcj: use children for better performance
			combo.children('input.combo-value').val('');
		}
		//wcj: use children for better performance
		combo.children('input.combo-text').val('');
	};
	function getText(target) {
		var combo = $.data(target, 'combo').combo;
		//wcj: use children for better performance
		return combo.children('input.combo-text').val();
	};
	function setText(target, text) {
		var combo = $.data(target, 'combo').combo;
		//wcj: use children for better performance
		combo.children('input.combo-text').val(text);
		var $target = $(target);
		//wcj:
		clearTimeout($target.data("validateTimer"));
		setTimeout(function() {
			$target.data("validateTimer", setTimeout(function() {
				validate(target, true);
			}, 0));
		}, 0);
		$.data(target, 'combo').previousValue = text;
		$target.data("oldText", text);
	};
	function getValues(target) {
		var values = [];
		var combo = $.data(target, 'combo').combo;
		//wcj: use children for better performance
		combo.children('input.combo-value').each(function() {
					values.push($(this).val());
				});
		return values;
	};
	function setValues(target, values) {
		var opts = $.data(target, 'combo').options;
		var oldValues = getValues(target);
		var combo = $.data(target, 'combo').combo;
		//wcj: use children for better performance
		combo.children('input.combo-value').remove();
		var name = $(target).attr('comboName');
		for (var i = 0; i < values.length; i++) {
			var input = $('<input type="hidden" class="combo-value">')
					.appendTo(combo);
			if (name) {
				input.attr('name', name);
			}
			input.val(values[i]);
		}
		var tmp = [];
		for (var i = 0; i < oldValues.length; i++) {
			tmp[i] = oldValues[i];
		}
		var aa = [];
		for (var i = 0; i < values.length; i++) {
			for (var j = 0; j < tmp.length; j++) {
				if (values[i] == tmp[j]) {
					aa.push(values[i]);
					tmp.splice(j, 1);
					break;
				}
			}
		}
		if (aa.length != values.length || values.length != oldValues.length) {
			if (opts.multiple) {
				opts.onChange.call(target, values, oldValues);
			} else {
				opts.onChange.call(target, values[0], oldValues[0]);
			}
		}
	};
	function getValue(target) {
		var values = getValues(target);
		return values[0];
	};
	function setValue(target, value) {
		setValues(target, [value]);
	};
	function initValue(target) {
		var opts = $.data(target, 'combo').options;
		var fn = opts.onChange;
		opts.onChange = function() {};
		if (opts.multiple) {
			if (opts.value) {
				if (typeof opts.value == 'object') {
					setValues(target, opts.value);
				} else {
					setValue(target, opts.value);
				}
			} else {
				setValues(target, []);
			}
		} else {
			setValue(target, opts.value);
		}
		opts.onChange = fn;
	};
	$.fn.combo = function(options, param) {
		if (typeof options == 'string') {
			return $.fn.combo.methods[options](this, param);
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'combo');
					if (state) {
						$.extend(state.options, options);
					} else {
						var r = init(this);
						state = $.data(this, 'combo', {
									options : $.extend({}, $.fn.combo.defaults,
											$.fn.combo.parseOptions(this), options),
									combo : r.combo,
									panel : r.panel,
									previousValue : null
								});
						$(this).removeAttr('disabled');
					}
					//wcj:
					//$('input.combo-text', state.combo).attr('readonly',
					//		!state.options.editable);
					state.combo.children('input.combo-text').attr('readonly',
							!state.options.editable);
					initArrow(this);
					setDisabled(this, state.options.disabled);
					setSize(this);
					bindEvents(this);
					validate(this);
					initValue(this);
					//wcj: readonly
					if ($(this).attr("readonly")) {
						$(this).combo("readonly");
					}
					if ($(this).attr("disabled")) {
						$(this).combo("disable");
					}
				});
	};
	$.fn.combo.methods = {
		options : function(jq) {
			return $.data(jq[0], 'combo').options;
		},
		panel : function(jq) {
			return $.data(jq[0], 'combo').panel;
		},
		textbox : function(jq) {
			//wcj: use children for better performance
			return $.data(jq[0], 'combo').combo.children('input.combo-text');
		},
		destroy : function(jq) {
			return jq.each(function() {
						destroy(this);
					});
		},
		resize : function(jq, width) {
			return jq.each(function() {
						setSize(this, width);
					});
		},
		showPanel : function(jq) {
			return jq.each(function() {
						showPanel(this);
					});
		},
		hidePanel : function(jq) {
			return jq.each(function() {
						hidePanel(this);
					});
		},
		disable : function(jq) {
			return jq.each(function() {
						setDisabled(this, true);
						bindEvents(this);
					});
		},
		enable : function(jq) {
			return jq.each(function() {
						setDisabled(this, false);
						bindEvents(this);
					});
		},
		validate : function(jq) {
			//wcj: return value
//			return jq.each(function() {
//						validate(this, true);
//					});
			return validate(jq[0], true);
		},
		isValid : function(jq) {
			//wcj: use children for better performance
			var state = $.data(jq[0], 'combo').combo.children('input.combo-text');
			return state.validatebox('isValid');
		},
		clear : function(jq) {
			return jq.each(function() {
						clear(this);
					});
		},
		getText : function(jq) {
			return getText(jq[0]);
		},
		setText : function(jq, text) {
			return jq.each(function() {
						setText(this, text);
					});
		},
		getValues : function(jq) {
			return getValues(jq[0]);
		},
		setValues : function(jq, values) {
			return jq.each(function() {
						setValues(this, values);
					});
		},
		getValue : function(jq) {
			return getValue(jq[0]);
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						setValue(this, value);
					});
		}
	};
	$.fn.combo.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.validatebox.parseOptions(target), {
					//wcj: handle 100% or such
					width : (target.style.width && target.style.width.indexOf("%") > 0) ?
							undefined :
							(parseInt(target.style.width) || undefined),
					panelWidth : (parseInt(t.attr('panelWidth')) || undefined),
					panelHeight : (t.attr('panelHeight') == 'auto'
							? 'auto'
							: parseInt(t.attr('panelHeight')) || undefined),
					separator : (t.attr('separator') || undefined),
					multiple : (t.attr('multiple')
							? (t.attr('multiple') == 'true' || t
									.attr('multiple') == true)
							: undefined),
					editable : (t.attr('editable')
							? t.attr('editable') == 'true'
							: undefined),
					disabled : (t.attr('disabled') ? true : undefined),
					hasDownArrow : (t.attr('hasDownArrow') ? t
							.attr('hasDownArrow') == 'true' : undefined),
					value : (t.val() || undefined)
				});
	};
	$.fn.combo.defaults = $.extend({}, $.fn.validatebox.defaults, {
				width : 'auto',
				panelWidth : null,
				panelHeight : 200,
				multiple : false,
				separator : ',',
				editable : true,
				disabled : false,
				hasDownArrow : true,
				value : '',
				delay : 200,
				keyHandler : {
					up : function() {
					},
					down : function() {
					},
					enter : function() {
					},
					query : function(q) {
					}
				},
				onShowPanel : function() {
				},
				onHidePanel : function() {
				},
				onChange : function(newVal, oldVal) {
				}
			});
})(jQuery);

/**
 * combobox - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   combo
 * 
 */
(function($) {
	function scrollTo(target, value) {
		var panel = $(target).combo('panel');
		//wcj: fix bug when v has a '+'
		//var item = panel.find('div.combobox-item[value=' + value + ']');
		var item = panel.find('div.combobox-item[value="' + value + '"]');
		if (item.length) {
			if (item.position().top <= 0) {
				var h = panel.scrollTop() + item.position().top;
				panel.scrollTop(h);
			} else {
				if (item.position().top + item.outerHeight() > panel.height()) {
					var h = panel.scrollTop() + item.position().top
							+ item.outerHeight() - panel.height();
					panel.scrollTop(h);
				}
			}
		}
	};
	function selectPrev(target) {
		var panel = $(target).combo('panel');
		var values = $(target).combo('getValues');
		//wcj: fix bug when v has a '+'
		//var item = panel.find('div.combobox-item[value=' + values.pop() + ']');
		var item = panel.find('div.combobox-item[value="' + values.pop() + '"]');
		if (item.length) {
			var prev = item.prev(':visible');
			if (prev.length) {
				item = prev;
			}
		} else {
			item = panel.find('div.combobox-item:visible:last');
		}
		var value = item.attr('value');
		setValues(target, [value]);
		scrollTo(target, value);
	};
	function selectNext(target) {
		var panel = $(target).combo('panel');
		var values = $(target).combo('getValues');
		//wcj: fix bug when v has a '+'
		//var item = panel.find('div.combobox-item[value=' + values.pop() + ']');
		var item = panel.find('div.combobox-item[value="' + values.pop() + '"]');
		if (item.length) {
			var next = item.next(':visible');
			if (next.length) {
				item = next;
			}
		} else {
			item = panel.find('div.combobox-item:visible:first');
		}
		var value = item.attr('value');
		setValues(target, [value]);
		scrollTo(target, value);
	};
	
	function select(target, value) {
		var opts = $.data(target, 'combobox').options;
		var data = $.data(target, 'combobox').data;
		if (opts.multiple) {
			var values = $(target).combo('getValues');
			for (var i = 0; i < values.length; i++) {
				if (values[i] == value) {
					return;
				}
			}
			values.push(value);
			setValues(target, values);
		} else {
			setValues(target, [value]);
		}
		for (var i = 0; i < data.length; i++) {
			if (data[i][opts.valueField] == value) {
				opts.onSelect.call(target, data[i]);
				return;
			}
		}
	};
	function unselect(target, value) {
		var opts = $.data(target, 'combobox').options;
		var data = $.data(target, 'combobox').data;
		var values = $(target).combo('getValues');
		for (var i = 0; i < values.length; i++) {
			if (values[i] == value) {
				values.splice(i, 1);
				setValues(target, values);
				break;
			}
		}
		for (var i = 0; i < data.length; i++) {
			if (data[i][opts.valueField] == value) {
				opts.onUnselect.call(target, data[i]);
				return;
			}
		}
	};
	function setValues(target, values, remainText) {
		var opts = $.data(target, 'combobox').options;
		var data = $.data(target, 'combobox').data;
		//wcj:
		if (data.length == 0 && opts.data) {
			data = opts.data;
		}
		var panel = $(target).combo('panel');
		panel.find('div.combobox-item-selected')
				.removeClass('combobox-item-selected');
		var vv = [], ss = [];
		for (var i = 0; i < values.length; i++) {
			var v = values[i];
			var s = v;
			for (var j = 0; j < data.length; j++) {
				if (data[j][opts.valueField] == v) {
					s = data[j][opts.textField];
					break;
				}
			}
			vv.push(v);
			ss.push(s);
			//wcj: fix bug when v has a '+'
			//panel.find('div.combobox-item[value=' + v + ']')
			panel.find('div.combobox-item[value="' + v + '"]')
					.addClass('combobox-item-selected');
		}
		$(target).combo('setValues', vv);
		if (!remainText) {
			$(target).combo('setText', ss.join(opts.separator));
		}
	};
	function transformData(target) {
		var opts = $.data(target, 'combobox').options;
		var data = [];
		$('>option', target).each(function() {
					var item = {};
					item[opts.valueField] = $(this).attr('value')!=undefined?$(this).attr('value'):$(this).html();
					item[opts.textField] = $(this).html();
					item['selected'] = $(this).attr('selected');
					data.push(item);
				});
		return data;
	};
	function loadData(target, data, remainText) {
		var opts = $.data(target, 'combobox').options;
		var panel = $(target).combo('panel');
		$.data(target, 'combobox').data = data;
		var values = $(target).combobox('getValues');
		panel.empty();
		for (var i = 0; i < data.length; i++) {
			var v = data[i][opts.valueField];
			var s = data[i][opts.textField];
			var item = $('<div class="combobox-item"></div>').appendTo(panel);
			item.attr('value', v);
			if (opts.formatter) {
				item.html(opts.formatter.call(target, data[i]));
			} else {
				item.html(s);
			}
			if (data[i]['selected']) {
				(function() {
					for (var i = 0; i < values.length; i++) {
						if (v == values[i]) {
							return;
						}
					}
					values.push(v);
				})();
			}
		}
		if (opts.multiple) {
			setValues(target, values, remainText);
		} else {
			if (values.length) {
				setValues(target, [values[values.length - 1]], remainText);
			} else {
				setValues(target, [], remainText);
			}
		}
		opts.onLoadSuccess.call(target, data);
		$('.combobox-item', panel)/*
				//wcj: use css hover
				.hover(function() {
					$(this).addClass('combobox-item-hover');
				}, function() {
					$(this).removeClass('combobox-item-hover');
				})*/.click(function() {
					var item = $(this);
					if (opts.multiple) {
						if (item.hasClass('combobox-item-selected')) {
							unselect(target, item.attr('value'));
						} else {
							select(target, item.attr('value'));
						}
					} else {
						select(target, item.attr('value'));
						$(target).combo('hidePanel');
					}
				});
		//wcj:
		if (data.length) {
			opts._dataLoaded = true;
		}
	};
	function request(target, url, param, remainText) {
		var opts = $.data(target, 'combobox').options;
		if (url) {
			opts.url = url;
		}
		if (!opts.url) {
			return;
		}
		param = param || {};
		$.ajax({
					type:opts.method,
					url : opts.url,
					dataType : 'json',
					data : param,
					success : function(data) {
						loadData(target, data, remainText);
					},
					error : function() {
						opts.onLoadError.apply(this, arguments);
					}
				});
	};
	function doQuery(target, q) {
		var opts = $.data(target, 'combobox').options;
		//wcj: don't set values
//		if (opts.multiple && !q) {
//			setValues(target, [], true);
//		} else {
//			setValues(target, [q], true);
//		}
		/*
		if (! q) {
			if (opts.multiple) {
				setValues(target, [], true);
			} else {
				setValues(target, [null], true);
			}
		}
		*/
		//wcj:
		if (q && opts.customValuePermitted) {
			$(target).combo("setValue", q);
		}
		if (opts.mode == 'remote') {
			request(target, null, {
						q : q
					}, true);
		} else {
			var panel = $(target).combo('panel');
			panel.find('div.combobox-item').hide();
			var data = $.data(target, 'combobox').data;
			for (var i = 0; i < data.length; i++) {
				if (opts.filter.call(target, q, data[i])) {
					var v = data[i][opts.valueField];
					var s = data[i][opts.textField];
					//wcj: fix bug when v has a '+'
					//var item = panel.find('div.combobox-item[value=' + v + ']');
					var item = panel.find('div.combobox-item[value="' + v + '"]');
					item.show();
					if (s == q) {
						setValues(target, [v], true);
						item.addClass('combobox-item-selected');
					}
				}
			}
		}
	};
	function create(target) {
		var opts = $.data(target, 'combobox').options;
		$(target).addClass('combobox-f');
		$(target).combo($.extend({}, opts, {
					onShowPanel : function() {
						//wcj: load data here
						if (! opts._dataLoaded) {
							if (opts.data) {
								opts._dataLoaded = true;
								loadData(target, opts.data, true);
							}
						}
						$(target).combo('panel').find('div.combobox-item').show();
						scrollTo(target, $(target).combobox('getValue'));
						opts.onShowPanel.call(target);
					}
				}));
	};
	$.fn.combobox = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.combobox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'combobox');
					if (state) {
						$.extend(state.options, options);
						create(this);
					} else {
						state = $.data(this, 'combobox', {
									options : $.extend({},
											$.fn.combobox.defaults,
											$.fn.combobox.parseOptions(this),
											options)
								});
						create(this);
						loadData(this, transformData(this));
					}
					//wcj: lazyLoad
					if (! state.options.lazyLoad) {
						if (state.options.data) {
							loadData(this, state.options.data);
						}
					}
					request(this);
				});
	};
	$.fn.combobox.methods = {
		options : function(jq) {
			return $.data(jq[0], 'combobox').options;
		},
		getData : function(jq) {
			return $.data(jq[0], 'combobox').data;
		},
		setValues : function(jq, values) {
			return jq.each(function() {
						setValues(this, values);
					});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						setValues(this, [value]);
					});
		},
		clear : function(jq) {
			return jq.each(function() {
						$(this).combo('clear');
						var panel = $(this).combo('panel');
						panel.find('div.combobox-item-selected')
								.removeClass('combobox-item-selected');
					});
		},
		loadData : function(jq, data) {
			return jq.each(function() {
						loadData(this, data);
					});
		},
		reload : function(jq, url) {
			return jq.each(function() {
						request(this, url);
					});
		},
		select : function(jq, value) {
			return jq.each(function() {
						select(this, value);
					});
		},
		unselect : function(jq, value) {
			return jq.each(function() {
						unselect(this, value);
					});
		}
	};
	$.fn.combobox.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.combo.parseOptions(target), {
					valueField : t.attr('valueField'),
					textField : t.attr('textField'),
					mode : t.attr('mode'),
					method:(t.attr('method')?t.attr('method'):undefined),
					url : t.attr('url')
				});
	};
	$.fn.combobox.defaults = $.extend({}, $.fn.combo.defaults, {
				valueField : 'value',
				textField : 'text',
				mode : 'local',
				method:'post',
				url : null,
				data : null,
				keyHandler : {
					up : function() {
						selectPrev(this);
					},
					down : function() {
						selectNext(this);
					},
					enter : function() {
						var values = $(this).combobox('getValues');
						$(this).combobox('setValues', values);
						$(this).combobox('hidePanel');
					},
					query : function(q) {
						doQuery(this, q);
					}
				},
				filter : function(q, row) {
					var opts = $(this).combobox('options');
					return row[opts.textField].indexOf(q) == 0;
				},
				formatter : function(row) {
					var opts = $(this).combobox('options');
					return row[opts.textField];
				},
				onLoadSuccess : function() {
				},
				onLoadError : function() {
				},
				onSelect : function(record) {
				},
				onUnselect : function(record) {
				}
			});
})(jQuery);

/**
 * combotree - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 tree
 * 
 */
(function($) {
	function setSize(target) {
		var opts = $.data(target, 'combotree').options;
		var combo = $.data(target, 'combotree').tree;
		$(target).addClass('combotree-f');
		$(target).combo(opts);
		var panel = $(target).combo('panel');
		if (!combo) {
			combo = $('<ul></ul>').appendTo(panel);
			$.data(target, 'combotree').tree = combo;
		}
		combo.tree($.extend({}, opts, {
					checkbox : opts.multiple,
					onLoadSuccess : function(node, data) {
						var values = $(target).combotree('getValues');
						if (opts.multiple) {
							var checkedNodes = combo.tree('getChecked');
							for (var i = 0; i < checkedNodes.length; i++) {
								var id = checkedNodes[i].id;
								(function(){
									for (var i = 0; i < values.length; i++) {
										if (id == values[i]) {
											return;
										}
									}
									values.push(id);
								})();
							}
						}
						$(target).combotree('setValues', values);
						opts.onLoadSuccess.call(this, node, data);
					},
					onClick : function(node) {
						select(target);
						//wcj: when multiple mode, don't hide panel when click row
						if (! opts.multiple) {
							$(target).combo('hidePanel');
						}
						opts.onClick.call(this, node);
					},
					onCheck : function(node, checked) {
						select(target);
						opts.onCheck.call(this, node, checked);
					}
				}));
	};
	function select(target) {
		var opts = $.data(target, 'combotree').options;
		var combo = $.data(target, 'combotree').tree;
		var vv = [], ss = [];
		if (opts.multiple) {
			var checkedNode = combo.tree('getChecked');
			for (var i = 0; i < checkedNode.length; i++) {
				vv.push(checkedNode[i].id);
				ss.push(checkedNode[i].text);
			}
		} else {
			var selectedNode = combo.tree('getSelected');
			if (selectedNode) {
				vv.push(selectedNode.id);
				ss.push(selectedNode.text);
			}
		}
		$(target).combo('setValues', vv).combo('setText', ss.join(opts.separator));
	};
	function setValues(target, values) {
		var opts = $.data(target, 'combotree').options;
		var combo = $.data(target, 'combotree').tree;
		combo.find('span.tree-checkbox').addClass('tree-checkbox0')
				.removeClass('tree-checkbox1 tree-checkbox2');
		var vv = [], ss = [];
		for (var i = 0; i < values.length; i++) {
			var v = values[i];
			var s = v;
			var node = combo.tree('find', v);
			if (node) {
				s = node.text;
				combo.tree('check', node.target);
				combo.tree('select', node.target);
			}
			vv.push(v);
			ss.push(s);
		}
		$(target).combo('setValues', vv).combo('setText', ss.join(opts.separator));
	};
	$.fn.combotree = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.combotree.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'combotree');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'combotree', {
									options : $.extend({},
											$.fn.combotree.defaults,
											$.fn.combotree.parseOptions(this),
											options)
								});
					}
					setSize(this);
				});
	};
	$.fn.combotree.methods = {
		options : function(jq) {
			return $.data(jq[0], 'combotree').options;
		},
		tree : function(jq) {
			return $.data(jq[0], 'combotree').tree;
		},
		loadData : function(jq, data) {
			return jq.each(function() {
						var opts = $.data(this, 'combotree').options;
						opts.data = data;
						var combo = $.data(this, 'combotree').tree;
						combo.tree('loadData', data);
					});
		},
		reload : function(jq, url) {
			return jq.each(function() {
						var opts = $.data(this, 'combotree').options;
						var combo = $.data(this, 'combotree').tree;
						if (url) {
							opts.url = url;
						}
						combo.tree({
									url : opts.url
								});
					});
		},
		setValues : function(jq, values) {
			return jq.each(function() {
						setValues(this, values);
					});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						setValues(this, [value]);
					});
		},
		clear : function(jq) {
			return jq.each(function() {
						var combo = $.data(this, 'combotree').tree;
						combo.find('div.tree-node-selected')
								.removeClass('tree-node-selected');
						$(this).combo('clear');
					});
		}
	};
	$.fn.combotree.parseOptions = function(target) {
		return $.extend({}, $.fn.combo.parseOptions(target), $.fn.tree
						.parseOptions(target));
	};
	$.fn.combotree.defaults = $.extend({}, $.fn.combo.defaults,
			$.fn.tree.defaults, {
				editable : false
			});
})(jQuery);

/**
 * combogrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   combo
 *   datagrid
 * 
 */
(function($) {
	//wcj: don't init grid when initializing
	function create(target) {
		var opts = $.data(target, 'combogrid').options;
		var grid = $.data(target, 'combogrid').grid;
		$(target).addClass('combogrid-f');
		//wcj:
		//$(target).combo(opts);
		$(target).combo($.extend({}, opts, {
			onShowPanel : function() {
				createGrid(target);
				if (! $.data(target, 'combo').timer) {
					doQuery(target, null);
				}
			}
		}));
	}
	function createGrid(target) {
		var opts = $.data(target, 'combogrid').options;
		var grid = $.data(target, 'combogrid').grid;
		//
		if (grid) {
			return;
		}
//		$(target).addClass('combogrid-f');
//		$(target).combo(opts);
		var panel = $(target).combo('panel');
		if (!grid) {
			grid = $('<table></table>').appendTo(panel);
			$.data(target, 'combogrid').grid = grid;
		}
		grid.datagrid($.extend({}, opts, {
					border : false,
					fit : true,
					singleSelect : (!opts.multiple),
					onLoadSuccess : function(data) {
						//wcj:
						//var remainText = $.data(target, 'combogrid').remainText;
						var remainText = true;
						var values = $(target).combo('getValues');
						setValues(target, values, remainText);
						//$.data(target, 'combogrid').remainText = false;
						opts.onLoadSuccess.apply(target, arguments);
					},
					onClickRow : onClickRow,
					onSelect : function(rowIndex, rowData) {
						retrieveValues();
						opts.onSelect.call(this, rowIndex, rowData);
					},
					onUnselect : function(rowIndex, rowData) {
						retrieveValues();
						opts.onUnselect.call(this, rowIndex, rowData);
					},
					onSelectAll : function(rows) {
						retrieveValues();
						opts.onSelectAll.call(this, rows);
					},
					onUnselectAll : function(rows) {
						if(opts.multiple){
							retrieveValues();
						}
						opts.onUnselectAll.call(this, rows);
					},
					//wcj: don't show these in combogrid
					showPageList : false,
					displayMsg : ""
				}));
		function onClickRow(rowIndex, row) {
			$.data(target, 'combogrid').remainText = false;
			retrieveValues();
			if (!opts.multiple) {
				$(target).combo('hidePanel');
			}
			opts.onClickRow.call(this, rowIndex, row);
		};
		function retrieveValues() {
			var remainText = $.data(target, 'combogrid').remainText;
			var rows = grid.datagrid('getSelections');
			var vv = [], ss = [];
			for (var i = 0; i < rows.length; i++) {
				vv.push(rows[i][opts.idField]);
				ss.push(rows[i][opts.textField]);
			}
			if (!opts.multiple) {
				$(target).combo('setValues', (vv.length ? vv : ['']));
			} else {
				$(target).combo('setValues', vv);
			}
			if (!remainText) {
				$(target).combo('setText', ss.join(opts.separator));
			}
		};
	};
	function selectRow(target, step) {
		var opts = $.data(target, 'combogrid').options;
		//wcj:
		createGrid(target);
		var grid = $.data(target, 'combogrid').grid;
		var length = grid.datagrid('getRows').length;
		$.data(target, 'combogrid').remainText = false;
		var index;
		var rows = grid.datagrid('getSelections');
		if (rows.length) {
			index = grid.datagrid('getRowIndex', rows[rows.length - 1][opts.idField]);
			index += step;
			if (index < 0) {
				index = 0;
			}
			if (index >= length) {
				index = length - 1;
			}
		} else {
			if (step > 0) {
				index = 0;
			} else {
				if (step < 0) {
					index = length - 1;
				} else {
					index = -1;
				}
			}
		}
		if (index >= 0) {
			grid.datagrid('clearSelections');
			grid.datagrid('selectRow', index);
		}
	};
	function setValues(target, values, remainText) {
		var opts = $.data(target, 'combogrid').options;
		var grid = $.data(target, 'combogrid').grid;
		var ss = [];
		//wcj:
		if (grid) {
			var rows = grid.datagrid('getRows');
			//wcj: clear selection
			grid.datagrid("clearSelections");
			for (var i = 0; i < values.length; i++) {
				var index = grid.datagrid('getRowIndex', values[i]);
				if (index >= 0) {
					grid.datagrid('selectRow', index);
					ss.push(rows[index][opts.textField]);
				} else {
					//wcj: use ...
					//ss.push(values[i]);
					ss.push(values[i] ? "..." : null);
				}
			}
		}
		//wcj:
		/*
		if($(target).combo('getValues').join(',')==values.join(',')){
			return;
		}
		*/
		$(target).combo('setValues', values);
		if (!remainText) {
			$(target).combo('setText', ss.join(opts.separator));
		}
	};
	function doQuery(target, q) {
		var opts = $.data(target, 'combogrid').options;
		//wcj:
		createGrid(target);
		var grid = $.data(target, 'combogrid').grid;
		$.data(target, 'combogrid').remainText = true;
		//wcj: don't set values
//		if (opts.multiple && !q) {
//			setValues(target, [], true);
//		} else {
//			setValues(target, [q], true);
//		}
		/*
		if (! q) {
			if (opts.multiple) {
				setValues(target, [], true);
			} else {
				setValues(target, [null], true);
			}
		}
		*/
		//wcj:
		if (q && opts.customValuePermitted) {
			$(target).combo("setValue", q);
		}
		if (opts.mode == 'remote') {
			grid.datagrid('clearSelections');
			grid.datagrid('load', {
						q : q
					});
		} else {
			if (!q) {
				return;
			}
			var rows = grid.datagrid('getRows');
			for (var i = 0; i < rows.length; i++) {
				if (opts.filter.call(target, q, rows[i])) {
					grid.datagrid('clearSelections');
					grid.datagrid('selectRow', i);
					return;
				}
			}
		}
	};
	$.fn.combogrid = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.combogrid.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return $.fn.combo.methods[options](this, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'combogrid');
					if (state) {
						$.extend(state.options, options);
					} else {
						state = $.data(this, 'combogrid', {
									options : $.extend({},
											$.fn.combogrid.defaults,
											$.fn.combogrid.parseOptions(this),
											options)
								});
					}
					create(this);
					//wcj: lazyLoad
					if (! state.options.lazyLoad) {
						createGrid(this);
					}
				});
	};
	$.fn.combogrid.methods = {
		options : function(jq) {
			return $.data(jq[0], 'combogrid').options;
		},
		grid : function(jq) {
			//wcj:
			createGrid(jq[0]);
			return $.data(jq[0], 'combogrid').grid;
		},
		setValues : function(jq, values) {
			return jq.each(function() {
						setValues(this, values);
					});
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						setValues(this, [value]);
					});
		},
		clear : function(jq) {
			return jq.each(function() {
						$(this).combogrid('grid').datagrid('clearSelections');
						$(this).combo('clear');
					});
		}
	};
	$.fn.combogrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.combo.parseOptions(target), $.fn.datagrid
						.parseOptions(target), {
					idField : (t.attr('idField') || undefined),
					textField : (t.attr('textField') || undefined),
					mode : t.attr('mode')
				});
	};
	$.fn.combogrid.defaults = $.extend({}, $.fn.combo.defaults,
			$.fn.datagrid.defaults, {
				loadMsg : null,
				idField : null,
				textField : null,
				mode : 'local',
				keyHandler : {
					up : function() {
						selectRow(this, -1);
					},
					down : function() {
						selectRow(this, 1);
					},
					enter : function() {
						selectRow(this, 0);
						$(this).combo('hidePanel');
					},
					query : function(q) {
						doQuery(this, q);
					}
				},
				filter : function(q, row) {
					var opts = $(this).combogrid('options');
					return row[opts.textField].indexOf(q) == 0;
				}
			});
})(jQuery);

/**
 * datebox - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 calendar
 *   combo
 * 
 */
(function($) {
	function createBox(target) {
		var state = $.data(target, 'datebox');
		var opts = state.options;
		$(target).addClass('datebox-f');
		$(target).combo($.extend({}, opts, {
					onShowPanel : function() {
						//wcj:
						setTimeout(function() {
							if (!state.calendar) {
								createCalendar();
							}
							state.calendar.calendar('resize');
							opts.onShowPanel.call(target);
						}, 0);
					}
				}));
		$(target).combo('textbox').parent().addClass('datebox');
		//wcj:
//		if (!state.calendar) {
//			createCalendar();
//		}
		function createCalendar() {
			var panel = $(target).combo('panel');
			state.calendar = $('<div></div>').appendTo(panel)
					.wrap('<div class="datebox-calendar-inner"></div>');
			state.calendar.calendar({
						fit : true,
						border : false,
						onSelect : function(date) {
							var value = opts.formatter(date);
							setValue(target, value);
							$(target).combo('hidePanel');
							opts.onSelect.call(target, date);
						}
					});
			if (opts.value) {
				setValue(target, opts.value);
			}
			var button = $('<div class="datebox-button"></div>').appendTo(panel);
			$('<a href="javascript:void(0)" class="datebox-current"></a>')
					.html(opts.currentText).appendTo(button);
			$('<a href="javascript:void(0)" class="datebox-close"></a>')
					.html(opts.closeText).appendTo(button);
			button.find('.datebox-current,.datebox-close').hover(function() {
						$(this).addClass('datebox-button-hover');
					}, function() {
						$(this).removeClass('datebox-button-hover');
					});
			button.find('.datebox-current').click(function() {
						state.calendar.calendar({
									year : new Date().getFullYear(),
									month : new Date().getMonth() + 1,
									current : new Date()
								});
					});
			button.find('.datebox-close').click(function() {
						$(target).combo('hidePanel');
					});
		};
		//wcj: fix input
		function getCaretPos(input) {
			try{
				if (document.selection && document.selection.createRange) {
					var range = document.selection.createRange();
					return range.getBookmark().charCodeAt(2) - 2;
				} else if ( input.setSelectionRange ) {
					return input.selectionStart;
				}
			} catch(e) {
				return 0;
			}
		};
		function setCaretPos(input, pos) {
			if (input.createTextRange) {
				var textRange = input.createTextRange();
				textRange.collapse(true);
				textRange.moveEnd("character", pos);
				textRange.moveStart("character", pos);
				textRange.select();
			} else if (input.setSelectionRange) {
				input.setSelectionRange(pos, pos);
			}
		};
		$(target).combo("textbox").bind("keypress.datebox", function(e) {
			//keycode : 0-9 48-57  - 45  : 58  space 32
			if ((e.which >= 48 && e.which <= 57) || e.which == 45) {
				setTimeout(function() {
					var input = e.target;
					var pos = getCaretPos(input);
					var text = $(input).val();
					if (e.which == 45) {
						if (pos != 5 && pos != 8) {
							text = text.substring(0, pos - 1) + text.substring(pos);
							pos--;
						}
					} else {
						if (pos == 5 || pos == 8) {
							text = text.substring(0, pos - 1) + (text.substring(pos, pos + 1) || "-")
									+ text.substring(pos - 1, pos) + text.substring(pos + 1);
							pos++;
						}
					}
					if (pos == text.length && text.length > 10) {
						text = text.substring(0, 10);
					} else if (pos < text.length && text.length == 11) {
						text = text.substring(0, pos) + text.substring(pos + 1);
					}
					if (pos == 4 || pos == 7) {
						if (text.substring(pos, pos + 1) != "-") {
							text = text.substring(0, pos) + "-" + text.substring(pos);
						}
						pos++;
					}
					$(input).val(text);
					setCaretPos(input, pos);
				}, 0);
			} else {
				if ((e.ctrlKey == true && (e.which == 99 || e.which == 118))
						|| e.which == 0 || e.which == 8) {
					return true;
				} else {
					return false;
				}
			}
		}).bind("blur.datebox", function(e) {
			var $focus = $(":focus");
			var combo = $.data(target, 'combo').combo;
			if ($focus[0] == combo[0]
					|| $focus.closest(".combo")[0] == combo[0]
					|| $focus.hasClass("combo-panel")
					|| $focus.closest(".combo-panel").size() > 0) {
				//focus still in the combo panel
			} else {
				if ($(target).datebox("getValue")) {
					try {
						$(target).datebox("setValue", opts.formatter(opts.parser($(target).datebox("getValue"))));
					} catch (e) {
						$(target).datebox("setValue", null);
					}
				}
			}
		});
	};
	function doQuery(target, q) {
		setValue(target, q);
	};
	function doEnter(target) {
		var opts = $.data(target, 'datebox').options;
		var c = $.data(target, 'datebox').calendar;
		//wcj:
		if (! c) {
			return;
		}
		var value = opts.formatter(c.calendar('options').current);
		setValue(target, value);
		$(target).combo('hidePanel');
	};
	function setValue(target, value) {
		var state = $.data(target, 'datebox');
		var opts = state.options;
		//wcj:
		//$(target).combo('setValue', value).combo('setText', value);
		$(target).combo('setValue', value);
		if (value != $(target).combo('textbox').val()) {
			$(target).combo('setText', value);
		}
		//wcj
		if (! state.calendar) {
			opts.value = value;
			return;
		}
		state.calendar.calendar('moveTo', opts.parser(value));
	};
	$.fn.datebox = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.datebox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.combo(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'datebox');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'datebox', {
									options : $.extend({},
											$.fn.datebox.defaults, $.fn.datebox
													.parseOptions(this), options)
								});
					}
					createBox(this);
				});
	};
	$.fn.datebox.methods = {
		options : function(jq) {
			return $.data(jq[0], 'datebox').options;
		},
		calendar : function(jq) {
			return $.data(jq[0], 'datebox').calendar;
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						setValue(this, value);
					});
		}
	};
	$.fn.datebox.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.combo.parseOptions(target), {});
	};
	$.fn.datebox.defaults = $.extend({}, $.fn.combo.defaults, {
				panelWidth : 180,
				//wcj:
				//panelHeight : 'auto',
				panelHeight : 204,
				keyHandler : {
					up : function() {
					},
					down : function() {
					},
					enter : function() {
						doEnter(this);
					},
					query : function(q) {
						doQuery(this, q);
					}
				},
				currentText : 'Today',
				closeText : 'Close',
				okText : 'Ok',
				formatter : function(date) {
					var y = date.getFullYear();
					var m = date.getMonth() + 1;
					var d = date.getDate();
					return m + '/' + d + '/' + y;
				},
				parser : function(s) {
					var t = Date.parse(s);
					if (!isNaN(t)) {
						return new Date(t);
					} else {
						return new Date();
					}
				},
				onSelect : function(date) {
				}
			});
})(jQuery);

/**
 * datetimebox - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	 datebox
 *   timespinner
 * 
 */
(function($) {
	function createBox(target) {
		var state = $.data(target, 'datetimebox');
		var opts = state.options;
		$(target).datebox($.extend({}, opts, {
					//wcj:
					/*
					onShowPanel : function() {
						var value = $(target).datetimebox('getValue');
						setValue(target, value, true);
						opts.onShowPanel.call(target);
					}
					*/
				}));
		//wcj:
		var _onShowPanel = $(target).datebox('options').onShowPanel;
		$(target).datebox('options').onShowPanel = function() {
			//init datebox first
			_onShowPanel.apply(this);
			setTimeout(function() {
				if (!state.datetimeboxInit) {
					fixCalendar();
				}
				var value = $(target).datetimebox('getValue');
				setValue(target, value, true);
				opts.onShowPanel.call(target);
			}, 0);
		};
		$(target).removeClass('datebox-f').addClass('datetimebox-f');
		//wcj:
		function fixCalendar() {
			//wcj:
			state.datetimeboxInit = true;
			$(target).datebox('calendar').calendar({
				onSelect : function(date) {
					opts.onSelect.call(target, date);
				},
				onDblClick : function(date){
					if(opts.dblClick){// enable double click to confirm
						doEnter(target);
					}
				}
			});
			var panel = $(target).datebox('panel');
			if (!state.spinner) {
				var p = $('<div style="padding:2px"><input style="width:80px"></div>')
						.insertAfter(panel.children('div.datebox-calendar-inner'));
				state.spinner = p.children('input');
				state.spinner.timespinner({
							showSeconds : true
						}).bind('mousedown', function(e) {
							e.stopPropagation();
						});
				setValue(target, opts.value, true);
				var button = panel.children('div.datebox-button');
				var ok = $('<a href="javascript:void(0)" class="datebox-ok"></a>')
						.html(opts.okText).appendTo(button);
				ok.hover(function() {
							$(this).addClass('datebox-button-hover');
						}, function() {
							$(this).removeClass('datebox-button-hover');
						}).click(function() {
							doEnter(target);
						});
			}
		}
		//wcj: fix input
		function getCaretPos(input) {
			try{
				if (document.selection && document.selection.createRange) {
					var range = document.selection.createRange();
					return range.getBookmark().charCodeAt(2) - 2;
				} else if ( input.setSelectionRange ) {
					return input.selectionStart;
				}
			} catch(e) {
				return 0;
			}
		};
		function setCaretPos(input, pos) {
			if (input.createTextRange) {
				var textRange = input.createTextRange();
				textRange.collapse(true);
				textRange.moveEnd("character", pos);
				textRange.moveStart("character", pos);
				textRange.select();
			} else if (input.setSelectionRange) {
				input.setSelectionRange(pos, pos);
			}
		};
		$(target).combo("textbox").unbind("keypress.datebox").bind("keypress.datetimebox", function(e) {
			//keycode : 0-9 48-57  - 45  : 58  space 32
			if ((e.which >= 48 && e.which <= 57) || e.which == 45 || e.which == 58 || e.which == 32) {
				setTimeout(function() {
					var input = e.target;
					var pos = getCaretPos(input);
					var text = $(input).val();
					if (e.which == 45) {
						if (pos != 5 && pos != 8) {
							text = text.substring(0, pos - 1) + text.substring(pos);
							pos--;
						}
					} else if (e.which == 32) {
						if (pos != 11) {
							text = text.substring(0, pos - 1) + text.substring(pos);
							pos--;
						}
					} else if (e.which == 58) {
						if (pos != 14 && pos != 17) {
							text = text.substring(0, pos - 1) + text.substring(pos);
							pos--;
						}
					} else {
						if (pos == 5 || pos == 8) {
							text = text.substring(0, pos - 1) + (text.substring(pos, pos + 1) || "-")
									+ text.substring(pos - 1, pos) + text.substring(pos + 1);
							pos++;
						}
						if (pos == 11) {
							text = text.substring(0, pos - 1) + (text.substring(pos, pos + 1) || " ")
									+ text.substring(pos - 1, pos) + text.substring(pos + 1);
							pos++;
						}
						if (pos == 14 || pos == 17) {
							text = text.substring(0, pos - 1) + (text.substring(pos, pos + 1) || ":")
									+ text.substring(pos - 1, pos) + text.substring(pos + 1);
							pos++;
						}
					}
					if (pos == text.length && text.length > 19) {
						text = text.substring(0, 19);
					} else if (pos < text.length && text.length == 20) {
						text = text.substring(0, pos) + text.substring(pos + 1);
					}
					if (pos == 4 || pos == 7) {
						if (text.substring(pos, pos + 1) != "-") {
							text = text.substring(0, pos) + "-" + text.substring(pos);
						}
						pos++;
					}
					if (pos == 10) {
						if (text.substring(pos, pos + 1) != " ") {
							text = text.substring(0, pos) + " " + text.substring(pos);
						}
						pos++;
					}
					if (pos == 13 || pos == 16) {
						if (text.substring(pos, pos + 1) != ":") {
							text = text.substring(0, pos) + ":" + text.substring(pos);
						}
						pos++;
					}
					$(input).val(text);
					setCaretPos(input, pos);
				}, 0);
			} else {
				if ((e.ctrlKey == true && (e.which == 99 || e.which == 118))
						|| e.which == 0 || e.which == 8) {
					return true;
				} else {
					return false;
				}
			}
		});
	};
	function getCurrentDate(target) {
		var c = $(target).datetimebox('calendar');
		var t = $(target).datetimebox('spinner');
		var date = c.calendar('options').current;
		return new Date(date.getFullYear(), date.getMonth(), date.getDate(), t
						.timespinner('getHours'), t.timespinner('getMinutes'),
				t.timespinner('getSeconds'));
	};
	function doQuery(target, q) {
		setValue(target, q, true);
	};
	function doEnter(target) {
		//wcj:
		if (! $(target).datetimebox('calendar')) {
			return;
		}
		var opts = $.data(target, 'datetimebox').options;
		var date = getCurrentDate(target);
		setValue(target, opts.formatter(date));
		$(target).combo('hidePanel');
	};
	function setValue(target, value, remainText) {
		var opts = $.data(target, 'datetimebox').options;
		//wcj: 
		if (value && value.split(" ").length == 1) {
			value += " 00:00:00";
		}
		$(target).combo('setValue', value);
		if (!remainText) {
			if (value) {
				var date = opts.parser(value);
				$(target).combo('setValue', opts.formatter(date));
				$(target).combo('setText', opts.formatter(date));
			} else {
				$(target).combo('setText', value);
			}
		}
		//wcj:
		if (! $(target).datetimebox('calendar')) {
			opts.value = value;
			return;
		}
		//wcj: 
		try {
			var date = opts.parser(value);
			if (date.getTime()) {
				$(target).datetimebox('calendar').calendar('moveTo', opts.parser(value));
				$(target).datetimebox('spinner').timespinner('setValue', getTimeS(date));
			}
		} catch (e) {
		}
		function getTimeS(date) {
			function formatNumber(value) {
				return (value < 10 ? '0' : '') + value;
			};
			var tt = [formatNumber(date.getHours()), formatNumber(date.getMinutes())];
			if (opts.showSeconds) {
				tt.push(formatNumber(date.getSeconds()));
			}
			return tt
					.join($(target).datetimebox('spinner').timespinner('options').separator);
		};
	};
	$.fn.datetimebox = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.datetimebox.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.datebox(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'datetimebox');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'datetimebox', {
									options : $
											.extend(
													{},
													$.fn.datetimebox.defaults,
													$.fn.datetimebox
															.parseOptions(this),
													options)
								});
					}
					createBox(this);
				});
	};
	$.fn.datetimebox.methods = {
		options : function(jq) {
			return $.data(jq[0], 'datetimebox').options;
		},
		spinner : function(jq) {
			return $.data(jq[0], 'datetimebox').spinner;
		},
		setValue : function(jq, value) {
			return jq.each(function() {
						setValue(this, value);
					});
		}
	};
	$.fn.datetimebox.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.datebox.parseOptions(target), {});
	};
	$.fn.datetimebox.defaults = $.extend({}, $.fn.datebox.defaults, {
				//wcj: 
				panelHeight : 230,
				dblClick : true,
				showSeconds : true,
				keyHandler : {
					up : function() {
					},
					down : function() {
					},
					enter : function() {
						doEnter(this);
					},
					query : function(q) {
						doQuery(this, q);
					}
				},
				formatter : function(date) {
					var h = date.getHours();
					var M = date.getMinutes();
					var s = date.getSeconds();
					function formatNumber(value) {
						return (value < 10 ? '0' : '') + value;
					};
					return $.fn.datebox.defaults.formatter(date) + ' ' + formatNumber(h)
							+ ':' + formatNumber(M) + ':' + formatNumber(s);
				},
				parser : function(s) {
					if ($.trim(s) == '') {
						return new Date();
					}
					var dt = s.split(' ');
					var d = $.fn.datebox.defaults.parser(dt[0]);
					var tt = dt[1].split(':');
					//wcj: default to 0
					var hour = parseInt(tt[0] || '0', 10);
					var minute = parseInt(tt[1] || '0', 10);
					var second = parseInt(tt[2] || '0', 10);
					return new Date(d.getFullYear(), d.getMonth(), d.getDate(),
							hour, minute, second);
				}
			});
})(jQuery);

/**
 * propertygrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2010 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	datagrid
 * 
 */
(function($) {
	function init(target) {
		var opts = $.data(target, 'propertygrid').options;
		$(target).datagrid($.extend({}, opts, {
					view : (opts.showGroup ? view : undefined),
					onClickRow : function(rowIndex, rowData) {
						if (opts.editIndex != rowIndex) {
							var valueOpts = $(this).datagrid('getColumnOption',
									'value');
							valueOpts.editor = rowData.editor;
							stopEdit(opts.editIndex);
							$(this).datagrid('beginEdit', rowIndex);
							$(this).datagrid('getEditors', rowIndex)[0].target
									.focus();
							opts.editIndex = rowIndex;
						}
						opts.onClickRow.call(target, rowIndex, rowData);
					}
				}));
		$(target).datagrid('getPanel').panel('panel').addClass('propertygrid');
		$(target).datagrid('getPanel').find('div.datagrid-body')
				.unbind('.propertygrid').bind('mousedown.propertygrid',
						function(e) {
							e.stopPropagation();
						});
		$(document).unbind('.propertygrid').bind('mousedown.propertygrid',
				function() {
					stopEdit(opts.editIndex);
					opts.editIndex = undefined;
				});
		function stopEdit(rowIndex) {
			if (rowIndex == undefined) {
				return;
			}
			var t = $(target);
			if (t.datagrid('validateRow', rowIndex)) {
				t.datagrid('endEdit', rowIndex);
			} else {
				t.datagrid('cancelEdit', rowIndex);
			}
		};
	};
	$.fn.propertygrid = function(options, param) {
		if (typeof options == 'string') {
			var method = $.fn.propertygrid.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		options = options || {};
		return this.each(function() {
					var state = $.data(this, 'propertygrid');
					if (state) {
						$.extend(state.options, options);
					} else {
						$.data(this, 'propertygrid', {
									options : $.extend({},
											$.fn.propertygrid.defaults,
											$.fn.propertygrid
													.parseOptions(this), options)
								});
					}
					init(this);
				});
	};
	$.fn.propertygrid.methods = {};
	$.fn.propertygrid.parseOptions = function(target) {
		var t = $(target);
		return $.extend({}, $.fn.datagrid.parseOptions(target), {
					showGroup : (t.attr('showGroup')
							? t.attr('showGroup') == 'true'
							: undefined)
				});
	};
	var view = $.extend({}, $.fn.datagrid.defaults.view, {
		render : function(target, container, frozen) {
			var opts = $.data(target, 'datagrid').options;
			var rows = $.data(target, 'datagrid').data.rows;
			var fields = $(target).datagrid('getColumnFields', frozen);
			var html = [];
			var rowIndex = 0;
			var groups = this.groups;
			for (var i = 0; i < groups.length; i++) {
				var group = groups[i];
				html
						.push('<div class="datagrid-group" group-index=' + i
								+ '>');
				html
						.push('<table cellspacing="0" cellpadding="0" border="0" style="height:100%"><tbody>');
				html.push('<tr>');
				html.push('<td style="border:0;">');
				if (!frozen) {
					html.push('<span>');
					html.push(opts.groupFormatter.call(target, group.fvalue, group.rows));
					html.push('</span>');
				}
				html.push('</td>');
				html.push('</tr>');
				html.push('</tbody></table>');
				html.push('</div>');
				html
						.push('<table cellspacing="0" cellpadding="0" border="0"><tbody>');
				for (var j = 0; j < group.rows.length; j++) {
					var cls = (rowIndex % 2 && opts.striped)
							? 'class="datagrid-row-alt"'
							: '';
					var style = opts.rowStyler ? opts.rowStyler.call(target, rowIndex,
							group.rows[j]) : '';
					style = style ? 'style="' + style + '"' : '';
					html.push('<tr datagrid-row-index="' + rowIndex + '" ' + cls
							+ ' ' + style + '>');
					html.push(this.renderRow.call(this, target, fields, frozen, rowIndex,
							group.rows[j]));
					html.push('</tr>');
					rowIndex++;
				}
				html.push('</tbody></table>');
			}
			$(container).html(html.join(''));
		},
		onAfterRender : function(target) {
			var opts = $.data(target, 'datagrid').options;
			var gridView = $(target).datagrid('getPanel').find('div.datagrid-view');
			var gridView1 = gridView.children('div.datagrid-view1');
			var gridView2 = gridView.children('div.datagrid-view2');
			$.fn.datagrid.defaults.view.onAfterRender.call(this, target);
			var gridGroup = gridView2.find('div.datagrid-group');
			if (opts.rownumbers || opts.frozenColumns.length) {
				gridGroup = gridView1.find('div.datagrid-group');
			}
			$('<td style="border:0"><div class="datagrid-row-expander datagrid-row-collapse" style="width:25px;height:16px;cursor:pointer"></div></td>')
					.insertBefore(gridGroup.find('td'));
			gridView.find('div.datagrid-group').each(function() {
				var groupIndex = $(this).attr('group-index');
				$(this).find('div.datagrid-row-expander').bind('click', {
							groupIndex : groupIndex
						}, function(e) {
							var group = gridView
									.find('div.datagrid-group[group-index='
											+ e.data.groupIndex + ']');
							if ($(this).hasClass('datagrid-row-collapse')) {
								$(this).removeClass('datagrid-row-collapse')
										.addClass('datagrid-row-expand');
								group.next('table').hide();
							} else {
								$(this).removeClass('datagrid-row-expand')
										.addClass('datagrid-row-collapse');
								group.next('table').show();
							}
							$(target).datagrid('fixRowHeight');
						});
			});
		},
		onBeforeRender : function(target, rows) {
			var opts = $.data(target, 'datagrid').options;
			var groups = [];
			for (var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var group = findGroup(row[opts.groupField]);
				if (!group) {
					group = {
						fvalue : row[opts.groupField],
						rows : [row],
						startRow : i
					};
					groups.push(group);
				} else {
					group.rows.push(row);
				}
			}
			function findGroup(value) {
				for (var i = 0; i < groups.length; i++) {
					var group = groups[i];
					if (group.fvalue == value) {
						return group;
					}
				}
				return null;
			};
			this.groups = groups;
			var newRows = [];
			for (var i = 0; i < groups.length; i++) {
				var group = groups[i];
				for (var j = 0; j < group.rows.length; j++) {
					newRows.push(group.rows[j]);
				}
			}
			$.data(target, 'datagrid').data.rows = newRows;
		}
	});
	$.fn.propertygrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
				singleSelect : true,
				remoteSort : false,
				fitColumns : true,
				loadMsg : '',
				frozenColumns : [[{
							field : 'f',
							width : 16,
							resizable : false
						}]],
				columns : [[{
							field : 'name',
							title : 'Name',
							width : 100,
							sortable : true
						}, {
							field : 'value',
							title : 'Value',
							width : 100,
							resizable : false
						}]],
				showGroup : false,
				groupField : 'group',
				groupFormatter : function(data) {
					return data;
				}
			});
})(jQuery);

/**
 * numberTextBox - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 * 
 * usage: <input class="number-textbox" min="1" max="100" precision="2">
 * The plugin will make the input can only input number chars
 * Options:
 * 	 min: The minimum allowed value
 *   max: The maximum allowed value
 *   precision: The maximum precision to display after the decimal separator
 */
(function($){
	$.fn.numberTextBox = function(){
		function fixValue(target){
			var min = parseFloat($(target).attr('min'));
			var max = parseFloat($(target).attr('max'));
			var precision = $(target).attr('precision') || 0;
			var val = parseFloat($(target).val()).toFixed(precision);
			if (isNaN(val)) {
				$(target).val('');
				return;
			}

			if (min && val < min) {
				$(target).val(min.toFixed(precision));
			} else if (max && val > max) {
				$(target).val(max.toFixed(precision));
			} else {
				$(target).val(val);
			}
		}
		
		return this.each(function(){
			$(this).css({imeMode:'disabled'});
			$(this).keypress(function(e){
				if (e.which == 46) {
					return true;
				}
				else if ((e.which >= 48 && e.which <= 57 && e.ctrlKey == false && e.shiftKey == false) || e.which == 0 || e.which == 8) {
					return true;
				} else if (e.ctrlKey == true && (e.which == 99 || e.which == 118)) {
					return true;
				} else {
					return false;
				}
			}).bind('paste', function(){
				if (window.clipboardData) {
					var s = clipboardData.getData('text');
					if (! /\D/.test(s)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}).bind('dragenter', function(){
				return false;
			}).blur(function(){
				fixValue(this);
			});
		});
	};
	
	$(function(){
		$('.number-textbox').numberTextBox();
	});
})(jQuery);

/**
 * shadow - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 * 
 * options:
 * 	hidden: boolean false to show the shadow and true to hide the shadow 
 * 	fit: boolean true to fit the parent container and false not
 * 	width: integer width The width in pixels of the shadow. Default: 8
 * 
 */
(function($){
	$.fn.shadow = function(options){
		
		return this.each(function(){
			
			// wrap the element and return the jQuery object
			function wrapElem(target) {
				var wraps = [
				             '<div class="shadow">',
				             '<div class="shadow-one">',
				             '<div class="shadow-corner-a"></div>',
				             '<div class="shadow-corner-b"></div>',
				             '<div class="shadow-two">',
				             '	<div class="shadow-three">',
				             '		<div class="shadow-four">',
				             '		</div>',
				             '	</div>',
				             '</div>',
				             '</div>',
				             '</div>'
				             ];
				
				var shadow = $(wraps.join('')).insertAfter($(target));
				$(target).appendTo($('.shadow-four', shadow));
				return shadow;
			}
			
			if ($.data(this, 'shadow')) {
				$.extend($.data(this, 'shadow').options, options || {});
			} else {
				$.data(this, 'shadow', {
					options: $.extend({}, $.fn.shadow.defaults, options || {}),
					shadow: wrapElem(this),
					oldWidth: $(this).width(),	// the element old width and height
					oldHeight: $(this).height()
				});
			}
			
			if (!$.data(this, 'shadow').shadow) {
				$.data(this, 'shadow').shadow = wrapElem(this);
			}
			
			var opts = $.data(this, 'shadow').options;
			var shadow = $.data(this, 'shadow').shadow;
			
			if (opts.hidden == true) {
				$(this).insertAfter(shadow);
				shadow.remove();
				$.data(this, 'shadow').shadow = null;
				return;
			}
			
			$('.shadow-one', shadow).css({
				paddingLeft: opts.width * 2,
				paddingTop: opts.width * 2
			});
			$('.shadow-corner-a', shadow).css({
				width: opts.width * 2,
				height: opts.width * 2
			});
			$('.shadow-corner-b', shadow).css({
				width: opts.width * 2,
				height: opts.width * 2
			});
			$('.shadow-three', shadow).css({
				left: opts.width * -2,
				top: opts.width * -2
			});
			$('.shadow-four', shadow).css({
				left: opts.width,
				top: opts.width
			});
			
			if (opts.fit == true) {
				// make element and shadow fit the parent container
				
				var parent = $(shadow).parent();	// the parent container
				
				if ($.boxModel == true) {
					var delta = $(this).outerWidth(true) - $(this).width();
					$(this).css({
						width: parent.width() - 2*opts.width - delta,
						height: parent.height() - 2*opts.width - delta
					});
					$(shadow).css({
						width: parent.width(),
						height: parent.height()
					});
					$('.shadow-one', shadow).css({
						width: parent.width() - 2*opts.width,
						height: parent.height() - 2*opts.width
					});
				
				} else {
					$(this).css({
						width:'100%',
						height:'100%'
					});
					$(shadow).css({
						width: parent.width(),
						height: parent.height()
					});
					$('.shadow-one', shadow).css({
						width: parent.width(),
						height: parent.height()
					});
				}
			} else {
				// restore the element's width and height
				$(this).width($.data(this, 'shadow').oldWidth)
						.height($.data(this, 'shadow').oldHeight);
				
				$('.shadow-one', shadow).css({
					width:'100%',
					height:'100%'
				});
				
				if ($.boxModel == true) {
					$(shadow).css({
						width: $(this).outerWidth(),
						height: $(this).outerHeight()
					});
				} else {
					$(shadow).css({
						width: $.data(this, 'shadow').oldWidth + 2*opts.width,
						height: $.data(this, 'shadow').oldHeight + 2*opts.width
					});
				}
			}
			
		});
	};
	
	$.fn.shadow.defaults = {
			hidden: false,
			fit: false,
			width: 8
	};
})(jQuery);

/**
 * dmenu - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2009 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 * 	shadow
 * 
 * Options:
 * 	minWidth: integer, the menu minimum width. default 150
 * 	shadow: boolean, true to show shadow and false to hide. default true
 * 	minZIndex: integer, the menu's zIndex value. default 500
 */
(function($){
	$.fn.dmenu = function(options){
		options = $.extend({}, $.fn.dmenu.defaults, options || {});
		
		return this.each(function(){
			
			$('li ul li a', this).each(function(){
				if (/^[u4E00-u9FA5]/.test($(this).html()) == false && $.browser.msie) {
					$(this).css('padding', '7px 20px 5px 30px');
				}
			});
			$('li.dmenu-sep', this).html('&nbsp;');
			
			var mainmenu = $(this);
			var menus = mainmenu.find('ul').parent();
			menus.each(function(i) {
				$(this).css('z-index', options.minZIndex + menus.length - i);
				if (mainmenu[0] != $(this).parent()[0]) {
					if ($('>ul', this).length > 0) {
						$('>a', this).append('<span class="dmenu-right-arrow"></span>');
					}
				} else {
					if ($('>ul', this).length > 0) {
						$('<span></span>').addClass('dmenu-down-arrow')
								.css('top', $(this).height()/2-4)
								.appendTo($('>a', this));
					}
				}
				if (options.shadow) {
					var shadow = $('<div class="dmenu-shadow"><div class="dmenu-shadow-inner"></div></div>');
					shadow.css({
						width:20,
						height:20
					});
					shadow.prependTo(this);
					$('.dmenu-shadow-inner', shadow).shadow({width:5, fit:true, hidden:true});
				}
				
			});
			
			$('a', this).each(function(){
				var icon = $(this).attr('icon');
				if (icon) {
					$('<span></span>').addClass('dmenu-icon').addClass(icon).appendTo(this);
				}
			});
			
			// show the main menu
			$('>li', this).hover(
					function(){
						var menu = $(this).find('ul:eq(0)');
						if (menu.length == 0) return;
						
						$('a', menu).css('width', 'auto');
						var menuWidth = menu.width();
						if (menuWidth < options.minWidth) {
							menuWidth = options.minWidth;
						}
						if ($.boxModel == true) {
							$('>li>a', menu).css('width', menuWidth - 45);
						} else {
							$('>li', menu).css('width', menuWidth);
							$('>li>a', menu).css('width', menuWidth);
						}
						
						var parent = menu.parent();
						if (parent.offset().left + menu.outerWidth() > $(window).width()) {
							var left = menu.offset().left;
							left -= parent.offset().left + menu.outerWidth() - $(window).width() + 5;
							menu.css('left', left);
						}
						$('li:last', menu).css('border-bottom', '0px');
						
						menu.fadeIn('normal');
						$('>div.dmenu-shadow', this).css({
							left: parseInt(menu.css('left')) - 5,
							top: $(this).height(),
							width: menu.outerWidth() + 10,
							height: menu.outerHeight() + 5,
							display: 'block'
						});
						$('.dmenu-shadow-inner', this).shadow({hidden:false});
					},
					
					function(){
						var menu = $(this).find('ul:eq(0)');
						menu.fadeOut('normal');
						$('div.dmenu-shadow', this).css('display', 'none');
					}
			);
			
			// show the sub menu
			$('li ul li', this).hover(
					function(){
						var menu = $(this).find('ul:eq(0)');
						if (menu.length == 0) return;
						
						$('a', menu).css('width', 'auto');
						var menuWidth = menu.width();
						if (menuWidth < options.minWidth) {
							menuWidth = options.minWidth;
						}
						if ($.boxModel == true) {
							$('>li>a', menu).css('width', menuWidth - 45);
						} else {
							$('>li', menu).css('width', menuWidth);
							$('>li>a', menu).css('width', menuWidth);
						}
						
						var parent = menu.parent();
						if (parent.offset().left + parent.outerWidth() + menu.outerWidth() > $(window).width()) {
							menu.css('left', - menu.outerWidth() + 5);
						} else {
							menu.css('left', parent.outerWidth() - 5);
						}
						
						menu.fadeIn('normal');
						$('>div.dmenu-shadow', this).css({
							left: parseInt(menu.css('left')) - 5,
							top: parseInt(menu.css('top')),
							width: menu.outerWidth() + 10,
							height: menu.outerHeight() + 5,
							display: 'block'
						});
						$('.dmenu-shadow-inner', this).shadow({hidden:false});
					},
					
					function(){
						$('>div.dmenu-shadow', this).css('display', 'none');
						$(this).children('ul:first').animate({height:'hide',opacity:'hide'});
					}
			);
		});
	};
	
	$.fn.dmenu.defaults = {
			minWidth:150,
			shadow:true,
			minZIndex:500
	};
	
	$(function(){
		$('ul.dmenu').dmenu();
	});
})(jQuery);

