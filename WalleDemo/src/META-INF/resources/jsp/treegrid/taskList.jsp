<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script language="javascript">

	$(function() {
		
		var readonly = true;
		
		$("#taskListFilter").combobox("setValue", "all");

		//初始加载所有task
	 	$("#taskTreegrid").datagrid("options").queryFields = [{
               fieldName : "userId",
               fieldStringValue : currentUser.userId
           }, {
               fieldName : "status",
               operator : "notIn",
               fieldType : "String[]",
               fieldStringValue : "closed,canceled"
           }];
		$("#taskTreegrid").datagrid("commonQuery", {
			queryType : "TaskQuery"
		});

		$("#taskListFilter").combobox("options").onHidePanel = function() {
			$.fn.combobox.defaults.onHidePanel.apply(this);
			var filter = $("#taskListFilter").combobox("getValue");
			if (filter == "individual") {
			 	$("#taskTreegrid").datagrid("options").queryFields = [{
	                fieldName : "status",
	                operator : "notIn",
	                fieldType : "String[]",
	                fieldStringValue : "closed,canceled"
	            }, {
	                fieldName : "userId",
	                fieldStringValue : currentUser.userId
	            }];
				$("#taskTreegrid").datagrid("commonQuery", {
					queryType : "IndividualTaskQuery"
				});
			} else if (filter == "team") {
				//取出用户关联的组，及所有子组的id
				JmsTeamManager.getAllGrantedTeamIds(currentUser.userId, function(teamIds) {
				 	$("#taskTreegrid").datagrid("options").queryFields = [{
		                fieldName : "status",
		                operator : "notIn",
		                fieldType : "String[]",
		                fieldStringValue : "closed,canceled"
		            }, {
		                fieldName : "userId",
		                fieldStringValue : currentUser.userId
		            }, {
		                fieldName : "teamIds",
		                fieldType : "String[]",
		                fieldStringValue : teamIds.join(",")
		            }];
					$("#taskTreegrid").datagrid("commonQuery", {
						queryType : "TeamTaskQuery"
					});
				});
			} else {
				if (filter == "all") {
				 	$("#taskTreegrid").datagrid("options").queryFields = [{
		                fieldName : "userId",
		                fieldStringValue : currentUser.userId
		            }, {
		                fieldName : "status",
		                operator : "notIn",
		                fieldType : "String[]",
		                fieldStringValue : "closed,canceled"
		            }];
				} else if (filter == "created") {
				 	$("#taskTreegrid").datagrid("options").queryFields = [{
		                fieldName : "userId",
		                fieldStringValue : currentUser.userId
		            }, {
		                fieldName : "creator",
		                fieldStringValue : currentUser.userId
		            }];
				} else if (filter == "closed") {
				 	$("#taskTreegrid").datagrid("options").queryFields = [{
		                fieldName : "userId",
		                fieldStringValue : currentUser.userId
		            }, {
		                fieldName : "status",
		                fieldStringValue : "closed"
		            }];
				} else if (filter == "canceled") {
				 	$("#taskTreegrid").datagrid("options").queryFields = [{
		                fieldName : "userId",
		                fieldStringValue : currentUser.userId
		            }, {
		                fieldName : "status",
		                fieldStringValue : "canceled"
		            }];
				} else {
					alert("not supported yet");
				}
				$("#taskTreegrid").datagrid("commonQuery", {
					queryType : "TaskQuery"
				});
			}
		};

		//设置form和btn的状态
		setStatus();
		
		//设置form和btn的状态
		function setStatus() {
			readonly = true;
			$("#taskInfoForm").form("readonly");
			$("#listButtons").children("a, .datagrid-btn-separator").hide();
			$("#editButtons").children().hide();
			//新增、刷新
			$("#refreshBtn, #addTaskBtn, #taskInfoCancelBtn").each(function(index, a) {
				$(a).prev().show();
				$(a).show();
			});
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				if (! task.uuid || task.uuid.indexOf("id_") == 0) {
					//新增后，可编辑
					readonly = false;
					$("#taskInfoForm").form("readonly", false);
					$("#taskInfoSaveBtn").each(function(index, a) {
						$(a).prev().show();
						$(a).show();
					});
				} else {
					//选中了任务节点，可详细信息，打开附件、加备注
					$("#editBtn, #attachmentBtn, #writeRemarkBtn").each(function(index, a) {
						$(a).prev().show();
						$(a).show();
					});
					//当前用户创建的任务，可增加子任务、拆分、编辑，删除
					if (task.creator == currentUser.userId) {
						readonly = false;
						$("#taskInfoForm").form("readonly", false);
						$("#addChildTaskBtn, #splitBtn, #deleteBtn, #taskInfoSaveBtn, #taskInfoCancelBtn").each(function(index, a) {
							$(a).prev().show();
							$(a).show();
						});
					}
					//分配给当前用户的任务
					if (task.operator == currentUser.userId) {
						//未开始的和暂缓的可以开始执行
						if (task.status == "assigned" || task.status == "suspended") {
							$("#taskBeginBtn, #_").each(function(index, a) {
								$(a).prev().show();
								$(a).show();
							});
						}
						//已开始未结束的和验收不通过的可以提请验收
						if (task.status == "started" || task.status == "restarted") {
							$("#checkBtn, #_").each(function(index, a) {
								$(a).prev().show();
								$(a).show();
							});
						}
					}
					//验收人为当前用户，状态为结束的，可以验收通过或者不通过
					if (task.acceptor == currentUser.userId && task.status == "finished") {
						$("#passBtn, #failBtn").each(function(index, a) {
							$(a).prev().show();
							$(a).show();
						});
					}
					//分配给所在组，并且为组长的，可以增加子任务、拆分、开始执行、提请验收
					if (task.isLeader) {
						$("#addChildTaskBtn, #splitBtn").each(function(index, a) {
							$(a).prev().show();
							$(a).show();
						});
						//未开始的和暂缓的可以开始执行
						if (task.status == "assigned" || task.status == "suspended") {
							$("#taskBeginBtn, #_").each(function(index, a) {
								$(a).prev().show();
								$(a).show();
							});
						}
						//已开始未结束的和验收不通过的可以提请验收
						if (task.status == "started" || task.status == "restarted") {
							$("#checkBtn, #_").each(function(index, a) {
								$(a).prev().show();
								$(a).show();
							});
						}
					}
				}
			}
		};
		
		//设置form数据
		function setData() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (! task) {
				task = {};
			}
			$("#taskInfoForm").form("setData", task);
			setPriority();
			
			//显示备注
			$("#remark").html(task.remark || "");

			$("#operatorInput").combotree("tree").tree("options").queryFields = [];
			$("#operationGroupInput").combotree("tree").tree("options").queryFields = [];
			$("#responsibleInput").combotree("tree").tree("options").queryFields = [];
			//如果当前任务父任务分配给某个组，当前任务指派的人员和组必须在那个组内
			var parent = $("#taskTreegrid").treegrid("getParent", task.uuid);
			if (parent) {
				var parentAssignedGroup = parent.operationGroup;
				if (parentAssignedGroup) {
					$("#operatorInput").combotree("tree").tree("options").queryFields = [{
						fieldName : "teamId",
						fieldStringValue : parentAssignedGroup
					}];
					JmsTeamManager.getAllSubTeamIds(parentAssignedGroup, function(subTeamIds) {
						$("#operationGroupInput").combotree("tree").tree("options").queryFields = [{
			                fieldName : "uuid",
			                operator : "in",
			                fieldType : "String[]",
			                fieldStringValue : subTeamIds.join(",")
						}];
						$("#operationGroupInput").combotree("reload");
					});
					$("#responsibleInput").combotree("tree").tree("options").queryFields = [{
						fieldName : "teamId",
						fieldStringValue : parentAssignedGroup
					}];
				}
			}
			if (task.operationGroup) {
				$("#responsibleInput").combotree("tree").tree("options").queryFields = [{
					fieldName : "teamId",
					fieldStringValue : task.operationGroup
				}];
			}
			
			$("#operatorInput").combotree("reload");
			$("#operationGroupInput").combotree("reload");
			$("#responsibleInput").combotree("reload");
		};
		
		//选择执行组后，责任人必须在该组内
		$("#operationGroupInput").combotree("options").onHidePanel = function() {
		    $.fn.combotree.defaults.onHidePanel.apply(this);
			var operationGroup = $("#operationGroupInput").combotree("getValue");
			if (operationGroup) {
				$("#responsibleInput").combotree("tree").tree("options").queryFields = [{
					fieldName : "teamId",
					fieldStringValue : operationGroup
				}];
			} else {
				$("#responsibleInput").combotree("tree").tree("options").queryFields = [];
				var task = $("#taskTreegrid").treegrid("getSelected");
				var parent = $("#taskTreegrid").treegrid("getParent", task.uuid);
				if (parent) {
					var parentAssignedGroup = parent.operationGroup;
					if (parentAssignedGroup) {
						$("#responsibleInput").combotree("tree").tree("options").queryFields = [{
							fieldName : "teamId",
							fieldStringValue : parentAssignedGroup
						}];
					}
				}
			}
			$("#responsibleInput").combotree("reload");
		};
		
		
		//task树     查询
		$("#searchTeam").searchbox("options").searcher = function(value, name){
			 $("#taskTreegrid").treegrid("commonQuery", {
	            queryFields : [{
	                fieldName : "[taskName]",
	                fieldStringValue : value,
	                operator : "ilikeAnywhere"
	            }]
	        });
		};
		
		//选择task控制按钮状态
		$("#taskTreegrid").treegrid("options").onSelect = function(id) {
			$.fn.treegrid.defaults.onSelect.apply(this, [id]);
			setTimeout(function() {
				setStatus();
				if ($("#taskDialog").is(":visible")) {
					setData();
				}
			}, 0);
		};
		
	    //名称链接，查看
	    $("#taskTreegrid").datagrid("getColumnOption", "taskName").formatter = function(value, rowData, rowIndex) {
	        setTimeout(function() {
	            $("#view_" + rowData.uuid).click(function() {
	            	//等待行先被选中
	    			setTimeout(function() {
		    			$("#editBtn").click();
	    			});
	            });
	        }, 0);
	        return "<a id='view_" + rowData.uuid + "' style='text-decoration:underline; cursor:pointer'>" + value + "</a>";
	    };
	    
		
		//刷新
	    $("#refreshBtn, #_").click(function() {
	        $("#taskTreegrid").treegrid("reload");
	        setStatus();
	    });
		
		//任务新增
		$("#addTaskBtn, #_").click(function() {
			$("#taskTreegrid").treegrid("append", {
				data : [{
					taskName : "新任务",
					priority : "medium",
					status : "new",
					acceptor: currentUser.userId,
					assigner: currentUser.userId
				}]
			});
			setTimeout(function() {
				$("#editBtn").click();
			}, 0);
		});
		
		//子任务新增
		$("#addChildTaskBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$("#taskTreegrid").treegrid("append", {
					data : [{
						taskName : task.taskName + " 子任务",
						description : task.description,
						priority : task.priority,
						status : "new",
						parentId : task.uuid,
						acceptor: currentUser.userId,
						assigner: currentUser.userId
					}],
					parent : task.uuid
				});
				setTimeout(function() {
					$("#editBtn").click();
				}, 0);
			}
		});
		
		//删除
		$("#deleteBtn, #_").click(function(){
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$.messager.confirm("提示","是否删除所选择的组？",function(deleteFlag){
					if(deleteFlag){
						$("#taskTreegrid").treegrid("removeSelectedNodes");
						JmsTaskManager.saveAll($("#taskTreegrid").treegrid("getChanges"), function() {
							$("#taskDialog").dialog("close");
				    		$("#taskTreegrid").treegrid("loadData", {
				    			rows : []
				    		});
							$("#taskTreegrid").treegrid("reload");
							setStatus();
						});
					}
				});				
			}
		});

		//详细信息
		$("#editBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$("#taskInfoForm").form("clear");
				$("#taskDialog").dialog("open");
				setData();
			}
		});


		//保存
		$("#taskInfoSaveBtn, #_").click(function() {
			if(!$("#taskInfoForm").form("validate")){
				$.messager.alert("提示","数据验证错误！","warning");
				return;
			}
			var data = $("#taskInfoForm").form("getData");			
			JmsTaskManager.saveTask(data, function(result) {
				$.messager.toast("提示", "保存成功", "info");
	    		$("#taskTreegrid").treegrid("loadData", {
	    			rows : []
	    		});
				$("#taskTreegrid").treegrid("reloadAndSelect", result.uuid);
		        //$("#taskDialog").dialog("close");
			});
		});
		
		//取消
	    $("#taskInfoCancelBtn, #_").click(function() {
	    	if ($("#taskTreegrid").treegrid("getChanges").length > 0) {
	    		$("#taskTreegrid").treegrid("loadData", {
	    			rows : []
	    		});
				$("#taskTreegrid").treegrid("reload");
	    	}
	        $("#taskDialog").dialog("close");
	    });
	    
	  //追加备注
		$("#writeRemarkBtn, #_").click(function() {
			$("#tabs").tabs("select", "备注");
			$("#remarkForm").form("setData", {});
			$("#remarkWin").dialog("open");
		});

		//追加备注Win 确定
		$("#remarkSure, #_").click(function() {
			var data = $("#remarkForm").form("getData");
			var taskData = $("#taskInfoForm").form("getData");
			var currentDate = new Date();
			if(taskData.remark==null) {
				taskData.remark=" ";
			}
			taskData.remark +=" "+currentUser.fullname+ " "+currentDate.toLocaleString()+ "<br>" +data.remark+"<br><br>";
			//显示备注
			$("#remark").html(taskData.remark);
			JmsTaskManager.saveTask(taskData, function(result) {
				$("#taskTreegrid").treegrid("reload");
				$("#remarkWin").dialog("close");
			});
		});

		//追加备注Win 取消
		$("#remarkCancel, #_").click(function() {
			$("#remarkWin").dialog("close");
		});
		
		//开始执行任务
		$("#taskBeginBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$.messager.confirm("提示", "是否开始执行任务？", function(flag) {
					if (flag) {	
						task.status = "started"; 
						JmsTaskManager.saveTask(task, function(result) {
							$("#taskTreegrid").treegrid("reload");
						});
					}
				});
			}
		});

		//提请任务验收
		$("#checkBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$.messager.confirm("提示", "是否提请任务验收？", function(flag) {
					if (flag) {					
						task.status = "finished"; 
						JmsTaskManager.saveTask(task, function(result) {
							$("#taskTreegrid").treegrid("reload");
						});
					}
				});
			}
		});
		
		//验收通过
		$("#passBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$.messager.confirm("提示", "验收通过？", function(flag) {
					if (flag) {	
						task.status = "closed"; 
						JmsTaskManager.saveTask(task, function(result) {
							$("#taskTreegrid").treegrid("reload");
						});
					}
				});
			}
		});
		
		//验收不通过
		$("#failBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				$.messager.confirm("提示", "验收不通过？", function(flag) {
					if (flag) {	
						task.status = "restarted"; 
						JmsTaskManager.saveTask(task, function(result) {
							$("#taskTreegrid").treegrid("reload");
							$("#remarkForm").form("setData", {});
							$("#remarkWin").dialog("open");
						});
					}
				});
			}
		});
		
		//拆分
		$("#splitmm2").click(function() {
			split(2);
		});
		$("#splitmm3").click(function() {
			split(3);
		});
		$("#splitmm4").click(function() {
			split(4);
		});
		$("#splitmm5").click(function() {
			split(5);
		});
		
		function split(count) {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
				JmsTaskManager.split(task, count, function() {
					$("#taskTreegrid").treegrid("reload");
					$("#taskDialog").dialog("close");
				});
			}
		};
		
		//附件
	    $("#attachmentBtn, #_").click(function() {
			var task = $("#taskTreegrid").treegrid("getSelected");
			if (task) {
		        openWindow($("#taskDialog"), "附件", "jsp/attachment/attachment.jsp?_entityUuid=" + task.uuid + "&_edit=" + (! readonly), "ajax", {
		            width : 600,
		            height : 400,
		            iconCls : "icon-save"
		        });
			}
	    });

		function setPriority() {
			priority = $("#priorityInput").combotree("getValue") || "medium";
			$("#priortyIcon").removeClass("icon-priority-urgency icon-priority-prior icon-priority-high icon-priority-medium icon-priority-low");
			$("#priortyIcon").addClass("icon-priority-" + priority);
		}
		
		//显示优先级图标
		$("#priorityInput").combotree("options").onHidePanel = function() {
			$.fn.combotree.defaults.onHidePanel.apply(this);
			setPriority();
		};
		
		$("#taskDialog").dialog("options").onOpen = function() {
			$.fn.dialog.defaults.onOpen.apply(this);
			$("#tabs").tabs("select", "任务信息");
		};
		
	});
</script>

<div id="main" class="easyui-layout" fit="true">

	<div region="center" title="任务列表" iconCls="icon-tree">
	    <div id="listButtons" class="datagrid-toolbar">
			<a id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
			<a id="addTaskBtn" class="easyui-linkbutton" iconCls="icon-add" code="addRootTask">新增</a>
			<a id="editBtn" class="easyui-linkbutton" iconCls="icon-edit">详细信息</a>
			<a id="addChildTaskBtn" class="easyui-linkbutton" iconCls="icon-add">新增子任务</a>
			<a id="splitBtn" class="easyui-menubutton" iconCls="icon-add" menu="#splitmm">拆分</a>
			<a id="deleteBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			<a id="taskBeginBtn" class="easyui-linkbutton" iconCls="icon-ok">开始执行</a>
			<a id="checkBtn" class="easyui-linkbutton" iconCls="icon-ok">提请验收</a>
			<a id="passBtn" class="easyui-linkbutton" iconCls="icon-ok">验收通过</a>
			<a id="failBtn" class="easyui-linkbutton" iconCls="icon-cancel">验收不通过</a>
	    
            <div style="float:right; margin:2px 2px 0px 0px;">
	    		查看：
				<input id="taskListFilter" class="easyui-combobox" codetype="TaskListFilter" />
				<input id="searchTeam" class="easyui-searchbox" prompt='任务名称' style="width:250px" /> 
            </div>
	    </div>
		<table id="taskTreegrid" class="easyui-treegrid" fit="true"  border="false"
				pagination="false" orderBy="createTime"
				idField="uuid" parentField="parentId" treeField="taskName"
				singleSelect="true" > 
			<thead>
				<tr>
					<th field="taskName" title="任务项"/>
					<th field="status"  title="状态" codetype="TaskStatus"/> 
					<th field="operator" title="执行人" codetype="TaskUsers" />
					<th field="operationGroup" title="执行组"  codetype="AllTeams" />
					<th field="responsible" title="责任人"  codetype="TaskUsers" />
					<th field="acceptor"  title="验收人" codetype="TaskUsers" />
					<th field="assigner" title="分配人" codetype="TaskUsers" />
					<th field="expectedPerformanceTime" title="期望完成时间"/>
					<th field="startTime" title="启动时间"/>
					<th field="actualFinishTime" title="实际完成时间"/>
				</tr>
			</thead>
		</table>
	</div>
</div>

<div id="taskDialog" class="easyui-dialog" title="任务信息" iconCls="icon-edit"
		style="width:800px; height:620px;" modal="true" closed="true" >

	<div class="easyui-layout" fit="true">
	    <div id="editButtons" region="north" class="datagrid-toolbar" >
			<a id="addChildTaskBtn" class="easyui-linkbutton" iconCls="icon-add">新增子任务</a>
			<a id="splitBtn" class="easyui-menubutton" iconCls="icon-add" menu="#splitmm">拆分</a>
			<a id="deleteBtn" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
			<a id="taskBeginBtn" class="easyui-linkbutton" iconCls="icon-ok">开始执行</a>
			<a id="checkBtn" class="easyui-linkbutton" iconCls="icon-ok">提请验收</a>
			<a id="passBtn" class="easyui-linkbutton" iconCls="icon-ok">验收通过</a>
			<a id="failBtn" class="easyui-linkbutton" iconCls="icon-cancel">验收不通过</a>
			<a id="attachmentBtn" class="easyui-linkbutton" iconCls="icon-save">附件</a>
			<a id="writeRemarkBtn" class="easyui-linkbutton" iconCls="icon-edit">添加备注</a>
			<a id="taskInfoSaveBtn" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a id="taskInfoCancelBtn" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	    </div>
	    <div region="center" border="false">
	    	<div id="tabs" class="easyui-tabs" fit="true">
	    		<div title="任务信息" iconCls="icon-edit">
   					<form id="taskInfoForm" class="easyui-form" columns="3">
						<input name="taskName"  title="任务项" colspan="2" />
						<div>
							<span id="priortyIcon" class="tree-file icon-priority-medium"
									style="position: absolute; margin-left: 17px; margin-top: 4px;"></span>
							<input id="priorityInput" name="priority"  title="优先级" class="easyui-combotree" codetype="TaskPriority"
									panelWidth="150" panelHeight="120" /> 
						</div>
						
						<input id="operatorInput" name="operator" title="执行人"  class="easyui-combotree" codetype="TaskUsers" />
						<input id="operationGroupInput" name="operationGroup" title="执行组"  class="easyui-combotree" codetype="AllTeams" />
						<input id="responsibleInput" name="responsible" title="责任人"  class="easyui-combotree"  codetype="TaskUsers" />
						
						<input name="acceptor"  title="验收人" class="easyui-combotree" codetype="TaskUsers" /> 		
						<input name="assigner" title="分配人"  class="easyui-combotree" codetype="TaskUsers" />	
						<input name="status"  title="状态" class="easyui-combobox" readonly="true" codetype="TaskStatus"/> 
						
						<input name="expectedPerformanceTime" title="期望完成时间"  class="easyui-datebox"/>
						<input name="startTime" title="启动时间"  class="easyui-datebox" readonly="true" />
						<input name="actualFinishTime" title="实际完成时间"  class="easyui-datebox" readonly="true" />
						
						<textarea name="description" title="内容描述" colspan="3" style="height:130px;" ></textarea>
						<textarea name="acceptanceStandard" title="验收标准" colspan="3" style="height:130px;" ></textarea>
						<textarea name="deliverable" title="交付物" colspan="3" style="height:130px;" ></textarea>
					</form>
	    		</div>
	    		<div title="备注" iconCls="icon-edit"  style="padding: 20px;">
				    <div id ="remark"></div>
	    		</div>
	    	</div>
	    
	    </div>
	</div>

</div>

<!-- 任务备注 -->
<div id="remarkWin" class="easyui-dialog" title="任务备注"
	iconCls="icon-edit" style="width: 550px;" closed="true"
	resizable="false" closable="true" maximizable="false"
	minimizable="false" collapsible="false" modal="true">
	<form id="remarkForm" class="easyui-form" columns="1">
		<textarea name="remark"  title="备注" rows="4" style="width: 450px;"></textarea>
	</form>
	<div class="dialog-buttons">
		<a id="remarkSure" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
		<a id="remarkCancel" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
</div>

<div id="splitmm" style="width:150px;">
	<div id="splitmm2">2个子任务</div>
	<div id="splitmm3">3个子任务</div>
	<div id="splitmm4">4个子任务</div>
	<div id="splitmm5">5个子任务</div>
</div>