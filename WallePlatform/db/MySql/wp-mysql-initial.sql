

CREATE TABLE `wp_dynamic_page` (
  `UUID` varchar(36) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `CONTENT_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_dynamic_page_history` (
  `UUID` varchar(36) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `CONTENT_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_dynamic_page_release` (
  `UUID` varchar(36) NOT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `CONTENT_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_dynamic_service` (
  `UUID` varchar(36) NOT NULL,
  `SERVICE_NAME` varchar(200) DEFAULT NULL,
  `CONTENT_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `PARENT_UUID` varchar(36) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

INSERT INTO `wp_dynamic_service` (`UUID`,`SERVICE_NAME`,`CONTENT_TYPE`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('0442813c-763a-49c4-822b-2370378512f0','TestService','groovy','import cn.walle.platform.support.BaseGroovyService;\n\nclass GroovyService extends BaseGroovyService {\n\n    Object execute(Object p) {\n		//\n		\n		return \"hello, \" + p.name + \", your full name is \" + p.queryFields[0].fieldValue;\n	}\n\n}','0',1,17,'8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-03-20 10:29:53','8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-05-05 21:11:30');
INSERT INTO `wp_dynamic_service` (`UUID`,`SERVICE_NAME`,`CONTENT_TYPE`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('28d6d938-1d47-4400-8d8d-1130747d8745','commonQueryService','groovy','import cn.walle.platform.support.BaseGroovyService;\n\nclass GroovyService extends BaseGroovyService {\n\n	Object execute(Object p) {\n		//\n		\n		return null;\n	}\n\n}','0',3,4,'8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-05-24 17:24:39','1','2014-11-20 14:38:17');
INSERT INTO `wp_dynamic_service` (`UUID`,`SERVICE_NAME`,`CONTENT_TYPE`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('31ab1d6a-f187-4094-bc7d-f499191c4800','readme','groovy','/*\r\n所有groovy服务都要继承cn.walle.platform.support.BaseGroovyService，\r\n下面的属性和方法在BaseGroovyService中提供，可以直接使用\r\n\r\n    protected final Log log = LogFactory.getLog(getClass());\r\n    protected final ApplicationContext context = ContextUtils.getApplicationContext();\r\n\r\n    protected UniversalDao dao = ContextUtils.getBeanOfType(UniversalDao.class);\r\n    protected MongoTemplate mongoTemplate = ContextUtils.getBeanOfType(MongoTemplate.class);\r\n\r\n    \r\n    protected void setRollbackOnly() {\r\n        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();\r\n    }\r\n\r\n服务模板如下\r\n*/\r\n\r\nimport cn.walle.platform.support.BaseGroovyService;\r\n\r\nclass GroovyService extends BaseGroovyService {\r\n\r\n    Object execute(Object p) {\r\n		//\r\n		\r\n		return null;\r\n	}\r\n\r\n}','0',0,0,'8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-03-31 09:41:04',NULL,NULL);
INSERT INTO `wp_dynamic_service` (`UUID`,`SERVICE_NAME`,`CONTENT_TYPE`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('47c21b1e-a704-4101-b23a-d6093ae06835','TestService2','groovy','import cn.walle.platform.support.BaseGroovyService;\n\nclass GroovyService extends BaseGroovyService {\n    \n    static class TestEntity {\n        String x;\n        String y;\n    }\n\n    Object execute(Object p) {\n    	//\n		TestEntity en = new TestEntity();\n        en.x = \"aaa\";\n        en.y = \"bbb\";\n        return en;\n		//return \"hello, \" + p.get(\"name\") + \", hah\";\n	}\n    \n    Object test(Object p) {\n        Object result = new HashMap();\n        result.x = \"aa\";\n        result.y = \"bb\";\n        return result;\n    }\n}','0',2,5,'8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-03-31 10:08:59','8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-08-21 09:51:07');
INSERT INTO `wp_dynamic_service` (`UUID`,`SERVICE_NAME`,`CONTENT_TYPE`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('fb0d97d6-1de6-4c62-9d2d-23e0bd440a2b','TestGroovyCommonQuery','groovy','import cn.walle.platform.support.BaseGroovyService;\nimport cn.walle.framework.common.support.QueryData;\n\nclass GroovyService extends BaseGroovyService {\n\n	Object query(Object queryInfo) {\n		def queryFields = queryInfo.queryFields;\n        def orderBy = queryInfo.orderBy;\n        def pagingInfo = queryInfo.pagingInfo;\n        \n        QueryData queryData = new QueryData(\n            dataList : [\n                [field3 : \"xxxxx1\", field33 : \"yyyy1\", field4 : \"zzzz1\"],\n                [field3 : \"xxxxx2\", field33 : \"yyyy2\", field4 : \"zzzz2\"],\n                [field3 : \"xxxxx3\", field33 : \"yyyy3\", field4 : \"zzzz3\"],\n                [field3 : \"xxxxx4\", field33 : \"yyyy4\", field4 : \"zzzz4\"],\n            ]\n        );\n        \n		return queryData;\n	}\n\n}','28d6d938-1d47-4400-8d8d-1130747d8745',0,8,'8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-05-24 17:24:58','8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-06-10 14:52:25');



CREATE TABLE `wp_dynamic_service_history` (
  `UUID` varchar(36) NOT NULL,
  `SERVICE_NAME` varchar(200) DEFAULT NULL,
  `CONTENT_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_dynamic_service_release` (
  `UUID` varchar(36) NOT NULL,
  `SERVICE_NAME` varchar(200) DEFAULT NULL,
  `CONTENT_TYPE` varchar(20) DEFAULT NULL,
  `CONTENT` longtext,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_html_tag` (
  `UUID` varchar(36) NOT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `TAG_ID` varchar(50) DEFAULT NULL,
  `TAG_NAME` varchar(50) DEFAULT NULL,
  `PARENT_UUID` varchar(36) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_html_tag_attr` (
  `UUID` varchar(36) NOT NULL,
  `TAG_UUID` varchar(36) NOT NULL,
  `ATTR_NAME` varchar(50) DEFAULT NULL,
  `ATTR_VALUE` varchar(100) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$




CREATE TABLE `wp_html_template_js` (
  `UUID` varchar(36) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CONTENT` longtext,
  `PARENT_UUID` varchar(36) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('05fd6ec1-3288-464e-8e70-c30abf12ea7c','Tree','','0',4,2,'ADMIN','2013-03-20 16:23:35','1','2014-01-28 11:06:59');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('11d6bdbc-28d0-4893-936b-479f1d1a4b84','单表增删查改按钮事件','//查询\n$(\"#btnQuery\").click(function() {\n    $(\"#grid\").datagrid(\"load\");\n});\n\n//重置\n$(\"#btnReset\").click(function() {\n    $(\"#formQuery\").form(\"clear\");\n});\n\n//新增\n$(\"#btnAppend\").click(function() {\n    $(\"#grid\").datagrid(\"appendRow\", {});\n    $(\"#btnEdit\").trigger(\"click\");\n});\n\n//插入\n$(\"#btnInsert\").click(function() {\n    $(\"#grid\").datagrid(\"insertRow\", {\n    	index : $(\"#grid\").datagrid(\"getSelectedIndex\"),\n		row : {}\n	});\n	$(\"#btnEdit\").trigger(\"click\");\n});\n\n//编辑\n$(\"#btnEdit\").click(function() {\n    var row = $(\"#grid\").datagrid(\"endEdit\").datagrid(\"getSelected\");\n    if (row) {\n		$(\"#dialogEdit\").dialog(\"open\");\n		$(\"#formEdit\").form(\"setData\", row);\n	}\n});\n\n//删除\n$(\"#btnDelete\").click(function() {\n    $(\"#grid\").datagrid(\"deleteSelectedRows\");\n});\n\n//保存\n$(\"#btnSave\").click(function() {\n    if (! $(\"#grid\").datagrid(\"validate\")) {\n    	$.messager.alert(\"提示\", \"数据验证错误\", \"warning\");\n		return;\n	}\n	var rows = $(\"#grid\").datagrid(\"getChanges\");\n	if (rows.length === 0) {\n		$.messager.alert(\"提示\", \"未修改数据\", \"warning\");\n		return;\n	}\n	CommonSaveManager.saveAll(rows, function(result) {\n		$(\"#grid\").datagrid(\"refreshSavedData\", result);\n		$.messager.alert(\"提示\", \"保存成功\", \"info\");\n	});\n});\n\n//取消\n$(\"#btnReload\").click(function() {\n    $(\"#grid\").datagrid(\"reload\");\n});\n\n//编辑窗口确定\n$(\"#btnEditFormOk\").click(function() {\n    if (! $(\"#formEdit\").form(\"validate\")) {\n    	$.messager.alert(\"提示\", \"数据验证错误\", \"warning\");\n		return;\n	}\n	$(\"#dialogEdit\").dialog(\"close\");\n	$(\"#grid\").datagrid(\"updateRow\", {\n		index : $(\"#grid\").datagrid(\"getSelectedIndex\"),\n		row : $(\"#formEdit\").form(\"getData\")\n	});\n});\n\n//编辑窗口取消\n$(\"#btnEditFormCancel\").click(function() {\n    $(\"#dialogEdit\").dialog(\"close\");\n});\n\n','801b8849-a05f-416f-9a25-43e4f46cd210',0,1,'ADMIN','2013-03-20 16:06:12','ADMIN','2013-03-20 16:07:22');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('1a0fcad3-0617-414c-b378-9aebc8daea0f','取消','//取消\n$(\"#btnReload\").click(function() {\n    $(\"#grid\").datagrid(\"reload\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',7,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('21f141ae-7583-49c4-9e61-bda5d78033ae','onAfterEdit','//grid编辑完成\n$(\"#grid\").datagrid(\"options\").onAfterEdit = function(rowIndex, rowData, changes) {\n    $.fn.datagrid.defaults.onAfterEdit.apply(this, [rowIndex, rowData, changes]);\n    |\n};\n\n','69481d52-8bd5-4d04-b726-ab13d151df86',3,2,'ADMIN','2013-03-20 16:21:02','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('25a6f67e-4476-4240-9085-c9dca23e3595','删除','//删除\n$(\"#btnDelete\").click(function() {\n    $(\"#grid\").datagrid(\"deleteSelectedRows\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',5,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('3ee48b80-983c-4c3f-b540-ad700cf0ac24','onDblClickRow','//grid双击行\n$(\"#grid\").datagrid(\"options\").onDblClickRow = function(rowIndex, rowData) {\n    $.fn.datagrid.defaults.onDblClickRow.apply(this, [rowIndex, rowData]);\n	|\n};\n\n','69481d52-8bd5-4d04-b726-ab13d151df86',1,2,'ADMIN','2013-03-20 16:15:52','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('493a2c8a-4608-4d4b-a50f-9af2c9ffd519','保存','//保存\n$(\"#btnSave\").click(function() {\n    if (! $(\"#grid\").datagrid(\"validate\")) {\n		$.messager.alert(\"提示\", \"数据验证错误\", \"warning\");\n		return;\n	}\n	var rows = $(\"#grid\").datagrid(\"getChanges\");\n	if (rows.length === 0) {\n		$.messager.alert(\"提示\", \"未修改数据\", \"warning\");\n		return;\n	}\n	CommonSaveManager.saveAll(rows, function(result) {\n		$(\"#grid\").datagrid(\"refreshSavedData\", result);\n		$.messager.alert(\"提示\", \"保存成功\", \"info\");\n	});\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',6,4,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('4d499fdc-2580-42ab-a761-8d5e3e322460','onSelect','//grid单击行\n$(\"#grid\").datagrid(\"options\").onSelect = function(rowIndex, rowData) {\n	$.fn.datagrid.defaults.onSelect.apply(this, [rowIndex, rowData]);\n	|\n};\n\n','69481d52-8bd5-4d04-b726-ab13d151df86',0,2,'ADMIN','2013-03-20 16:15:22','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('59a8d84c-006d-4f9f-a4ee-3449e1e2bed5','onSelect','//tree选择节点\n$(\"#tree\").tree(\"options\").onSelect = function(node) {\n	$.fn.tree.defaults.onSelect.apply(this, [node]);\n	|\n};\n\n','05fd6ec1-3288-464e-8e70-c30abf12ea7c',0,5,'ADMIN','2013-03-20 16:23:35','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('5ac72fcd-1569-40fa-8f0c-21b45b20aef8','onDblClick','//tree双击\n$(\"#tree\").tree(\"options\").onDblClick = function(node) {\n	$.fn.tree.defaults.onDblClick.apply(this, [node]);\n	|\n};\n\n','05fd6ec1-3288-464e-8e70-c30abf12ea7c',1,1,'ADMIN','2013-03-20 16:26:55','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('5d8a82bf-08ac-49f9-93ca-c279c792687b','新增','//新增\n$(\"#btnAppend\").click(function() {\n    $(\"#grid\").datagrid(\"appendRow\", {});\n	$(\"#btnEdit\").trigger(\"click\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',2,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('619cfab0-b92f-44a0-ab6a-fa3fb2b8c313','编辑','//编辑\n$(\"#btnEdit\").click(function() {\n    var row = $(\"#grid\").datagrid(\"endEdit\").datagrid(\"getSelected\");\n	if (row) {\n		$(\"#dialogEdit\").dialog(\"open\");\n		$(\"#formEdit\").form(\"setData\", row);\n	}\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',4,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('621d8860-ec0a-4440-9ab3-b91f6081e664','编辑窗口确定','//编辑窗口确定\n$(\"#btnEditFormOk\").click(function() {\n    if (! $(\"#formEdit\").form(\"validate\")) {\n		$.messager.alert(\"提示\", \"数据验证错误\", \"warning\");\n		return;\n	}\n	$(\"#dialogEdit\").dialog(\"close\");\n	$(\"#grid\").datagrid(\"updateRow\", {\n		index : $(\"#grid\").datagrid(\"getSelectedIndex\"),\n		row : $(\"#formEdit\").form(\"getData\")\n	});\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',8,2,'ADMIN','2013-03-20 16:00:04','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('65de94f7-f9ea-4606-85d9-d543aef70df8','Combogrid','','0',7,4,'ADMIN','2013-03-20 16:33:20','1','2014-01-28 11:06:59');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('6912a5ce-b6c6-4834-a4dd-6589072514e2','Combobox','','0',6,2,'ADMIN','2013-03-20 16:34:28','1','2014-01-28 11:06:59');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('69481d52-8bd5-4d04-b726-ab13d151df86','Grid','','0',3,3,'ADMIN','2013-03-20 16:15:22','1','2014-01-28 11:06:59');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('77a226b7-6e83-4550-8396-209a7fbc3208','wpcc_simgle_edit_js','$(function() {\r\n	\r\n	//查询\r\n	$(\"#btnQuery\").click(function() {\r\n		$(\"#gridResult\").datagrid(\"load\");\r\n	});\r\n	\r\n	//重置\r\n	$(\"#btnReset\").click(function() {\r\n		$(\"#formQuery\").form(\"clear\");\r\n	});\r\n	\r\n	//新增\r\n	$(\"#btnAppend\").click(function() {\r\n		$(\"#gridResult\").datagrid(\"appendRow\", {});\r\n		$(\"#btnEdit\").trigger(\"click\");\r\n	});\r\n	\r\n	//删除\r\n	$(\"#btnDelete\").click(function() {\r\n		$(\"#gridResult\").datagrid(\"deleteSelectedRows\");\r\n	});\r\n	\r\n	//保存\r\n	$(\"#btnSave\").click(function() {\r\n		if (! $(\"#gridResult\").datagrid(\"validate\")) {\r\n			$.messager.alert(\"提示\", \"数据验证错误\", \"warning\");\r\n			return;\r\n		}\r\n		var rows = $(\"#gridResult\").datagrid(\"getChanges\");\r\n		if (rows.length == 0) {\r\n			$.messager.alert(\"提示\", \"未修改数据\", \"warning\");\r\n			return;\r\n		}\r\n		MongoCommonSaveManager.saveAll(rows, function(result) {\r\n			$(\"#gridResult\").datagrid(\"refreshSavedData\", result);\r\n			$.messager.alert(\"提示\", \"保存成功\", \"info\");\r\n		});\r\n	});\r\n	\r\n	//取消\r\n	$(\"#btnReload\").click(function() {\r\n		$(\"#gridResult\").datagrid(\"reload\");\r\n	});\r\n    \r\n    $(\"#gridResult\").datagrid(\"load\");\r\n	\r\n});','0',0,1,'1','2014-01-28 11:06:59','1','2014-01-28 13:36:06');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('801b8849-a05f-416f-9a25-43e4f46cd210','Button click','//\n$(\"#btn\").click(function() {\n	|\n});\n','0',2,1,'ADMIN','2013-03-20 15:51:23','1','2014-01-28 11:06:59');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('a3d8d87d-5dc7-4e0b-b435-8aa4135d5cc0','重置','//重置\n$(\"#btnReset\").click(function() {\n    $(\"#formQuery\").form(\"clear\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',1,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('a4d3efe2-7513-478e-b488-3f7abbf62214','Tabs','','0',5,3,'ADMIN','2013-03-20 16:28:26','1','2014-01-28 11:06:59');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('ac72b40f-410e-479b-b51b-5fa3678d5183','onBeginEdit','//grid开始编辑前\n$(\"#grid\").datagrid(\"options\").onBeginEdit = function(rowIndex, rowData) {\n    $.fn.datagrid.defaults.onBeginEdit.apply(this, [rowIndex, rowData]);\n    |\n};\n\n','69481d52-8bd5-4d04-b726-ab13d151df86',2,1,'ADMIN','2013-03-20 16:19:37','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('adb2fbe1-e399-400b-870a-9b06b8c80fd9','编辑窗口取消','//编辑窗口取消\n$(\"#btnEditFormCancel\").click(function() {\n    $(\"#dialogEdit\").dialog(\"close\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',9,2,'ADMIN','2013-03-20 16:00:04','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('b2103b11-e0a5-4a6a-a6ca-b4435b3d749f','onSelect','//tabs选择\n$(\"#tabs\").tabs(\"options\").onSelect = function(title) {\n    $.fn.tabs.defaults.onSelect.apply(this, [title]);\n	|\n};\n\n','a4d3efe2-7513-478e-b488-3f7abbf62214',0,1,'ADMIN','2013-03-20 16:29:45','ADMIN','2013-03-20 16:30:30');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c47c447f-ca46-4d83-b1c8-751fbe51cfac','onHidePanel','$(\"#combogrid\").combogrid(\"options\").onHidePanel = function() {\n	$.fn.combogrid.defaults.onHidePanel.apply(this);\n    |\n};\n\n','65de94f7-f9ea-4606-85d9-d543aef70df8',0,3,'ADMIN','2013-03-20 16:33:20','ADMIN','2013-03-20 16:38:10');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('e3fd38ff-ff6f-4bb1-b23e-3a6979903a6e','查询','//查询\n$(\"#btnQuery\").click(function() {\n    $(\"#grid\").datagrid(\"load\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',0,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('f2ffac53-9280-4aad-a127-5124142881b3','插入','//插入\n$(\"#btnInsert\").click(function() {\n    $(\"#grid\").datagrid(\"insertRow\", {\n		index : $(\"#grid\").datagrid(\"getSelectedIndex\"),\n		row : {}\n	});\n	$(\"#btnEdit\").trigger(\"click\");\n});\n\n','11d6bdbc-28d0-4893-936b-479f1d1a4b84',3,3,'ADMIN','2013-03-20 15:52:45','ADMIN','2013-03-20 16:06:12');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('f65b0277-cd00-4526-bbe0-b17e6a2d8401','onHidePanel','$(\"#combobox\").combobox(\"options\").onHidePanel = function() {\n    $.fn.combobox.defaults.onHidePanel.apply(this);\n    |\n};\n\n','6912a5ce-b6c6-4834-a4dd-6589072514e2',0,1,'ADMIN','2013-03-20 16:34:28','ADMIN','2013-03-20 16:38:10');
INSERT INTO `wp_html_template_js` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('fdfffab1-4e2c-4569-8609-5b990f2200d5','$(function() {...});','$(function() {\n    \n    |\n    \n});\n','0',1,2,'ADMIN','2013-03-20 15:29:35','1','2014-01-28 11:06:59');



CREATE TABLE `wp_html_template_tag` (
  `UUID` varchar(36) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CONTENT` longtext,
  `PARENT_UUID` varchar(36) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('01ba8788-dfb9-44da-8699-ca5af5a43275','Code Column','<th field=\"xxx\" codetype=\"XXX\"/>\n','e4f79925-e156-4feb-8e5d-5a0a82f43db4',2,2,'ADMIN','2013-03-20 15:49:39','1','2013-04-18 15:40:02');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('027e8094-384e-42f9-b948-b789241f5905','取消','<a id=\"btnReload\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">取消</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',7,3,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:45:01');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('152fa9e8-fa90-4498-8704-da9634a191fa','DateBox Field (End)','<input name=\"xxx\" class=\"easyui-datebox\" operator=\"dateEnd\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',6,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('16facbd3-a207-45be-9e8b-49a7557ea708','Tab','<div title=\"Tab\" iconCls=\"icon-edit\">\r\n	|\r\n</div>\r\n','c4d5f5a5-82e3-4f26-9b05-cbcabda71f30',0,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('174e1e7c-cbd0-4b88-8ba3-485a8d5178f1','Number Column','<th field=\"xxx\" format=\"#,##0.00\" align=\"right\"/>','e4f79925-e156-4feb-8e5d-5a0a82f43db4',1,3,'ADMIN','2013-03-20 15:49:39','ADMIN','2014-08-26 16:28:40');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('1b068fc5-15a0-4e1a-9570-4e71d77a7f7a','Columns','','2fb3cd72-ce4d-4a1f-8cbc-c3622cc8f090',0,0,'1','2013-04-18 15:40:02',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('1dd29d12-0e2f-48d8-b39a-37a8eca6fca7','插入','<a id=\"btnInsert\" class=\"easyui-linkbutton\" iconCls=\"icon-redo\">插入</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',3,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('2b27bbde-3773-4020-bf06-baf12a369eaf','Text Field (=)','<input name=\"xxx\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',0,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('2fb3cd72-ce4d-4a1f-8cbc-c3622cc8f090','Editable Grid','<table id=\"grid\" class=\"easyui-datagrid wp-config\" i18nRoot=\"Xxx\" fit=\"true\"\r\n    	queryType=\"Xxx\" orderby=\"seq\" paramForm=\"formQuery\">\r\n	<thead>\r\n		<tr>\r\n            <th field=\"field1\" editor=\"text\"/>\r\n            <th field=\"field2\" editor=\"text\"/>\r\n            <th field=\"field3\" editor=\"text\"/>\r\n			|\r\n		</tr>\r\n	</thead>\r\n    \r\n</table>\r\n','9934ce6d-f73a-45dc-8e4b-49c58f5931b8',4,3,'1','2013-04-18 15:41:54','1','2013-04-18 15:45:10');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('323d01b6-9b52-451d-9f6b-57c92ea09e86','Text Column','<th field=\"xxx\" editor=\"text\"/>\n','1b068fc5-15a0-4e1a-9570-4e71d77a7f7a',0,2,'ADMIN','2013-03-20 15:49:39','1','2013-04-18 15:40:02');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('3e166ddc-d866-4bf8-941e-a6ed1facbb46','Query Form','<form id=\"formQuery\" class=\"easyui-form wp-config\" columns=\"4\" i18nRoot=\"Xxx\">\r\n	|\r\n</form>\r\n','9934ce6d-f73a-45dc-8e4b-49c58f5931b8',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('3ed340f9-6178-4b96-8fe7-eaf5bd90e323','父子表增删查改','<div class=\"easyui-layout\" fit=\"true\">\r\n\r\n    <div region=\"north\" title=\"查询条件\" iconCls=\"icon-search\">\r\n        <div class=\"datagrid-toolbar\">\r\n			<a id=\"btnQuery\" class=\"easyui-linkbutton\" iconCls=\"icon-search\">查询</a>\r\n			<a id=\"btnReset\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">重置</a>\r\n		</div>\r\n		<form id=\"formQuery\" class=\"easyui-form wp-config\" columns=\"3\" i18nRoot=\"Xxx\">\r\n			<input name=\"号码\" operator=\"ilikeAnywhere\"/>\r\n    		<input name=\"名称\" operator=\"ilikeAnywhere\"/>\r\n    		<input name=\"是否\" class=\"easyui-combobox\" codetype=\"YES_NO\" multiple=\"true\" operator=\"in\"/>\r\n    		<input name=\"选择\" class=\"easyui-combogrid\" codetype=\"YES_NO\"/>\r\n			<input name=\"起始日期\" class=\"easyui-datebox\" operator=\"dateBegin\"/>\r\n			<input name=\"终止日期\" class=\"easyui-datebox\" operator=\"dateEnd\"/>\r\n		</form>\r\n	</div>\r\n	\r\n    <div region=\"center\" border=\"false\">\r\n        <div class=\"easyui-layout\" fit=\"true\">\r\n            \r\n            <div region=\"north\" title=\"主表信息\" iconCls=\"icon-edit\" style=\"height:200px;\">\r\n                <div class=\"datagrid-toolbar\">\r\n        			<a id=\"btnAppend\" class=\"easyui-linkbutton\" iconCls=\"icon-add\">新增</a>\r\n        			<a id=\"btnInsert\" class=\"easyui-linkbutton\" iconCls=\"icon-redo\">插入</a>\r\n        			<a id=\"btnEdit\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\">编辑</a>\r\n        			<a id=\"btnDelete\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\">删除</a>\r\n        			<a id=\"btnSave\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">保存</a>\r\n        			<a id=\"btnReload\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">取消</a>\r\n        		</div>\r\n                <table id=\"grid\" class=\"easyui-datagrid wp-config\" i18nRoot=\"Xxx\" fit=\"true\"\r\n                        queryType=\"Xxx\" orderby=\"seq\" paramForm=\"formQuery\">\r\n        			<thead>\r\n        				<tr>\r\n        					<th field=\"号码\" editor=\"{type:\'validatebox\', options:{required:true}}\"/>\r\n            				<th field=\"名称\" editor=\"{type:\'validatebox\', options:{required:true, validType:\'length[0,20]\'}}\"/>\r\n        					<th field=\"名称\" editor=\"text\"/>\r\n        					<th field=\"是否\" editor=\"combogrid\" codetype=\"YES_NO\"/>\r\n        					<th field=\"选择\" editor=\"{type:\'combobox\', options:{required:true}}\" codetype=\"YES_NO\"/>\r\n        					<th field=\"备注\" editor=\"text\" width=\"200\"/>\r\n        				</tr>\r\n        			</thead>\r\n        		</table>\r\n            </div>\r\n            \r\n            <div region=\"center\" title=\"从表信息\" iconCls=\"icon-edit\">\r\n                <div class=\"datagrid-toolbar\">\r\n        			<a id=\"btnSubAppend\" class=\"easyui-linkbutton\" iconCls=\"icon-add\">新增</a>\r\n        			<a id=\"btnSubInsert\" class=\"easyui-linkbutton\" iconCls=\"icon-redo\">插入</a>\r\n        			<a id=\"btnSubEdit\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\">编辑</a>\r\n        			<a id=\"btnSubDelete\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\">删除</a>\r\n        			<a id=\"btnSubSave\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">保存</a>\r\n        			<a id=\"btnSubReload\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">取消</a>\r\n        		</div>\r\n                <table id=\"gridSub\" class=\"easyui-datagrid wp-config\" i18nRoot=\"Xxx\" fit=\"true\"\r\n                        queryType=\"Xxx\" orderby=\"seq\" paramForm=\"formQuery\">\r\n        			<thead>\r\n        				<tr>\r\n        					<th field=\"号码\" editor=\"{type:\'validatebox\', options:{required:true}}\"/>\r\n            				<th field=\"名称\" editor=\"{type:\'validatebox\', options:{required:true, validType:\'length[0,20]\'}}\"/>\r\n        					<th field=\"名称\" editor=\"text\"/>\r\n        					<th field=\"是否\" editor=\"combogrid\" codetype=\"YES_NO\"/>\r\n        					<th field=\"选择\" editor=\"{type:\'combobox\', options:{required:true}}\" codetype=\"YES_NO\"/>\r\n        					<th field=\"备注\" editor=\"text\" width=\"200\"/>\r\n        				</tr>\r\n        			</thead>\r\n        		</table>\r\n            </div>\r\n            \r\n        </div>\r\n    </div>\r\n    \r\n</div>\r\n','488c5de4-bb9d-4cf8-b5b0-01f0681955ea',1,1,'ADMIN','2013-03-20 14:58:40',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('488c5de4-bb9d-4cf8-b5b0-01f0681955ea','页面模版','','0',0,11,'ADMIN','2013-03-19 17:05:55','1','2014-11-20 14:36:27');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('50bbde5d-fc41-4d45-a45b-f2f01696b413','ToolBar','<div class=\"datagrid-toolbar\">\r\n	|\r\n</div>\r\n','9934ce6d-f73a-45dc-8e4b-49c58f5931b8',0,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('525be346-15d9-42de-8631-f722fe48a4be','Dialog','<div id=\"dialog\" class=\"easyui-dialog\" title=\"Dialog\" iconCls=\"icon-edit\"\r\n		style=\"width:500px;padding:10px\" closed=\"true\" modal=\"true\">\r\n	|\r\n	<div class=\"dialog-buttons\">\r\n		<a id=\"btnDialogOk\" class=\"easyui-linkbutton\" iconCls=\"icon-ok\">确定</a>\r\n		<a id=\"btnDialogCancel\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\">取消</a>\r\n	</div>\r\n</div>\r\n','a457d2eb-fd8c-43c3-95b3-9e195bffcec9',3,1,'ADMIN','2013-03-25 15:24:22',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('5e445eaa-9a95-4de5-a57e-52bd3a493473','ComboBox Field','<input name=\"xxx\" class=\"easyui-combobox\" codetype=\"XXX\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',2,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('5fe77450-e878-4f1c-b47e-1fe63b4ba141','DateBox Field (=)','<input name=\"xxx\" class=\"easyui-datebox\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',4,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('61b3b365-ca59-4026-a5af-9c943219d88c','ComboBox Column','<th field=\"xxx\" editor=\"combobox\" codetype=\"XXX\"/>\n','1b068fc5-15a0-4e1a-9570-4e71d77a7f7a',2,2,'ADMIN','2013-03-20 15:49:39','1','2013-04-18 15:40:02');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('622f71a5-ab14-49f3-b0fd-0d3be4d3b335','ComboGrid Field','<input name=\"xxx\" class=\"easyui-combogrid\" codetype=\"XXX\"/>','c946d37d-541f-4761-a003-2111c9e5f587',2,1,'ADMIN','2013-03-20 15:49:39','',NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('6832f5d3-b157-4979-ae43-9f01ced915f8','Text Column','<th field=\"xxx\"/>','e4f79925-e156-4feb-8e5d-5a0a82f43db4',0,2,'ADMIN','2013-03-20 15:49:39','ADMIN','2013-04-18 15:40:02');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('69ab705c-7bf0-4fc6-a1b8-bee0ec0f2684','Readonly Grid','<table id=\"grid\" class=\"easyui-datagrid wp-config\" i18nRoot=\"Xxx\" fit=\"true\"','9934ce6d-f73a-45dc-8e4b-49c58f5931b8',3,2,'1','2013-04-18 15:36:14','ADMIN','2013-04-18 15:41:54');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('6f305768-f1b2-4ba0-8c18-c20adb2ea55e','Tree','<ul id=\"tree\" class=\"easyui-tree\" queryType=\"Xxx\" orderby=\"seq\"\r\n        idField=\"uuid\" textField=\"name\" parentField=\"parentUuid\" seqField=\"seq\">\r\n    |\r\n</ul>\r\n','9934ce6d-f73a-45dc-8e4b-49c58f5931b8',5,3,'ADMIN','2013-03-20 15:39:12','1','2013-04-18 15:12:14');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('7457a9bc-2b19-422e-918b-fe3ddca3ca4c','LinkButton','<a id=\"btn\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">Button</a>\n','50bbde5d-fc41-4d45-a45b-f2f01696b413',0,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('7a2e15d8-14b1-489e-ad79-fb18350e2105','DateBox Field (Begin)','<input name=\"xxx\" class=\"easyui-datebox\" operator=\"dateBegin\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',5,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('7d9fbb3a-ff01-4924-983a-3917e1d197b5','单表增删查改','<div class=\"easyui-layout\" fit=\"true\">\r\n\r\n	<div region=\"north\" title=\"查询条件\" iconCls=\"icon-search\">\r\n		<div class=\"datagrid-toolbar\">\r\n			<a id=\"btnQuery\" class=\"easyui-linkbutton\" iconCls=\"icon-search\">查询</a>\r\n			<a id=\"btnReset\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">重置</a>\r\n		</div>\r\n		<form id=\"formQuery\" class=\"easyui-form wp-config\" columns=\"3\" i18nRoot=\"Xxx\">\r\n			<input name=\"号码\" operator=\"ilikeAnywhere\"/>\r\n    		<input name=\"名称\" operator=\"ilikeAnywhere\"/>\r\n    		<input name=\"是否\" class=\"easyui-combobox\" codetype=\"YES_NO\" multiple=\"true\" operator=\"in\"/>\r\n    		<input name=\"选择\" class=\"easyui-combogrid\" codetype=\"YES_NO\"/>\r\n			<input name=\"起始日期\" class=\"easyui-datebox\" operator=\"dateBegin\"/>\r\n			<input name=\"终止日期\" class=\"easyui-datebox\" operator=\"dateEnd\"/>\r\n		</form>\r\n	</div>\r\n	\r\n	<div region=\"center\" title=\"查询结果\" iconCls=\"icon-edit\">\r\n		<div class=\"datagrid-toolbar\">\r\n			<a id=\"btnAppend\" class=\"easyui-linkbutton\" iconCls=\"icon-add\">新增</a>\r\n			<a id=\"btnInsert\" class=\"easyui-linkbutton\" iconCls=\"icon-redo\">插入</a>\r\n			<a id=\"btnEdit\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\">编辑</a>\r\n			<a id=\"btnDelete\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\">删除</a>\r\n			<a id=\"btnSave\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">保存</a>\r\n			<a id=\"btnReload\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">取消</a>\r\n		</div>\r\n        <table id=\"grid\" class=\"easyui-datagrid wp-config\" i18nRoot=\"Xxx\" fit=\"true\"\r\n                queryType=\"Xxx\" orderby=\"seq\" paramForm=\"formQuery\">\r\n			<thead>\r\n				<tr>\r\n					<th field=\"号码\" editor=\"{type:\'validatebox\', options:{required:true}}\"/>\r\n    				<th field=\"名称\" editor=\"{type:\'validatebox\', options:{required:true, validType:\'length[0,20]\'}}\"/>\r\n					<th field=\"名称\" editor=\"text\"/>\r\n					<th field=\"是否\" editor=\"combogrid\" codetype=\"YES_NO\"/>\r\n					<th field=\"选择\" editor=\"{type:\'combobox\', options:{required:true}}\" codetype=\"YES_NO\"/>\r\n					<th field=\"备注\" editor=\"text\" width=\"200\"/>\r\n				</tr>\r\n			</thead>\r\n		</table>\r\n	</div>\r\n    \r\n    \r\n    <!-- 弹出窗口编辑 -->\r\n	<div id=\"dialogEdit\" class=\"easyui-dialog\" title=\"编辑\" iconCls=\"icon-edit\"\r\n			style=\"width:500px;padding:10px\" closed=\"true\" modal=\"true\">\r\n		<form id=\"formEdit\" class=\"easyui-form\" columns=\"2\" i18nRoot=\"Xxx\">\r\n			<input name=\"号码\" class=\"easyui-validatebox\" required=\"true\" validType=\"length[0,20]\"/>\r\n			<input name=\"名称\" class=\"easyui-validatebox\" required=\"true\"/>\r\n			<input name=\"email\"/>\r\n			<input name=\"是否\" class=\"easyui-combogrid\" codetype=\"ALL_ORGS\"/>\r\n			<input name=\"选择\" class=\"easyui-combobox\" codetype=\"YES_NO\" required=\"true\"/>\r\n			<div></div>\r\n			<textarea name=\"备注\" rowspan=\"2\" colspan=\"2\"></textarea>\r\n		</form>\r\n		<div class=\"dialog-buttons\">\r\n			<a id=\"btnEditFormOk\" class=\"easyui-linkbutton\" iconCls=\"icon-ok\">确定</a>\r\n			<a id=\"btnEditFormCancel\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\">取消</a>\r\n		</div>\r\n    </div>\r\n    \r\n</div>\r\n\r\n\r\n','488c5de4-bb9d-4cf8-b5b0-01f0681955ea',0,1,'ADMIN','2013-03-20 16:02:37',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('7f7fd96a-9178-4214-9db2-593157a74223','Text Field','<input name=\"xxx\"/>\r\n','c946d37d-541f-4761-a003-2111c9e5f587',0,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('8204e7f9-6dce-4515-914c-474ea166a943','ComboGrid Field','<input name=\"xxx\" class=\"easyui-combogrid\" codetype=\"XXX\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',3,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('839425c2-506f-413a-bb29-de899c473aba','Layout','<div class=\"easyui-layout\" fit=\"true\">\n    \n    <div region=\"north\" title=\"North\" iconCls=\"icon-search\" style=\"height:100px;\">\n        |\n    </div>\n    \n    <div region=\"center\" title=\"Center\" iconCls=\"icon-edit\">\n        \n    </div>\n    \n</div>\n','a457d2eb-fd8c-43c3-95b3-9e195bffcec9',1,2,'ADMIN','2013-03-20 14:58:40','ADMIN','2013-03-25 15:24:22');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('8544ad6c-cbd7-4212-a5c8-1f1b85f59c77','Text Field (Like)','<input name=\"xxx\" operator=\"ilikeAnywhere\"/>\n','3e166ddc-d866-4bf8-941e-a6ed1facbb46',1,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('876d8a85-90df-439b-83fd-8a9140fd4f83','查询','<a id=\"btnQuery\" class=\"easyui-linkbutton\" iconCls=\"icon-search\">查询</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',0,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('98e44939-0c69-4d4c-88bf-adf517659e47','新增','<a id=\"btnAppend\" class=\"easyui-linkbutton\" iconCls=\"icon-add\">新增</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',2,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('9934ce6d-f73a-45dc-8e4b-49c58f5931b8','控件','','0',2,12,'ADMIN','2013-03-19 17:05:55','1','2014-11-20 14:36:27');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('a032e989-b461-46f7-a14c-bdfc68745634','Date Column','<th field=\"xxx\" editor=\"datebox\"/>\n','1b068fc5-15a0-4e1a-9570-4e71d77a7f7a',4,2,'ADMIN','2013-03-20 15:49:39','1','2013-04-18 15:40:02');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('a457d2eb-fd8c-43c3-95b3-9e195bffcec9','布局','','0',1,11,'ADMIN','2013-03-19 17:05:55','1','2014-11-20 14:36:27');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('a45bba36-92b9-4acd-aebe-c0e7b2a2e250','Layout Region East','<div region=\"east\" title=\"East\" iconCls=\"icon-edit\" style=\"width:200px;\">\n    |\n</div>\n','839425c2-506f-413a-bb29-de899c473aba',3,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('ad54ac2d-61a7-49f5-bed2-09c830f8cf8d','Demo Tree Data','<tbody>\n    <tr>\n        <td></td>\n        <td>字段1</td>\n        <td>字段2</td>\n        <td>字段3</td>\n        <td>字段4</td>\n        <td>字段5</td>\n        <td>字段6</td>\n        <td>字段7</td>\n        <td>字段8</td>\n        <td>字段9</td>\n    </tr>\n    <tr>\n        <td></td>\n        <td>字段1</td>\n        <td>字段2</td>\n        <td>字段3</td>\n        <td>字段4</td>\n        <td>字段5</td>\n        <td>字段6</td>\n        <td>字段7</td>\n        <td>字段8</td>\n        <td>字段9</td>\n    </tr>\n</tbody>\n','2fb3cd72-ce4d-4a1f-8cbc-c3622cc8f090',1,0,'1','2013-04-18 15:40:02',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('ad8b8d59-ff92-457c-b61b-0837090828e6','查询统计','<div class=\"easyui-layout\" fit=\"true\">\r\n\r\n    <div region=\"north\" title=\"查询条件\" iconCls=\"icon-search\">\r\n		<div class=\"datagrid-toolbar\">\r\n			<a id=\"btnQuery\" class=\"easyui-linkbutton\" iconCls=\"icon-search\">查询</a>\r\n			<a id=\"btnReset\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">重置</a>\r\n		</div>\r\n		<form id=\"formQuery\" class=\"easyui-form wp-config\" columns=\"3\" i18nRoot=\"Xxx\">\r\n			<input name=\"号码\" operator=\"ilikeAnywhere\"/>\r\n    		<input name=\"名称\" operator=\"ilikeAnywhere\"/>\r\n    		<input name=\"是否\" class=\"easyui-combobox\" codetype=\"YES_NO\" multiple=\"true\" operator=\"in\"/>\r\n    		<input name=\"选择\" class=\"easyui-c wp-configombogrid\" codetype=\"YES_NO\"/>\r\n			<input name=\"起始日期\" class=\"easyui-datebox\" operator=\"dateBegin\"/>\r\n			<input name=\"终止日期\" class=\"easyui-datebox\" operator=\"dateEnd\"/>\r\n		</form>\r\n	</div>\r\n	\r\n	<div region=\"center\" title=\"查询结果\" iconCls=\"icon-edit\">\r\n		<div class=\"datagrid-toolbar\">\r\n			<a id=\"btnExport\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">导出</a>\r\n			<a id=\"btnReload\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">刷新</a>\r\n		</div>\r\n        <table id=\"grid\" class=\"easyui-datagrid wp-config\" i18nRoot=\"Xxx\" fit=\"true\"\r\n                queryType=\"Xxx\" orderby=\"seq\" paramForm=\"formQuery\">\r\n			<thead>\r\n				<tr>\r\n					<th field=\"xxx\" title=\"xxx\"/>\r\n    				<th field=\"xxx\" title=\"xxx\"/>\r\n					<th field=\"xxx\" title=\"xxx\"/>\r\n					<th field=\"xxx\" title=\"xxx\" codetype=\"YES_NO\"/>\r\n					<th field=\"xxx\" title=\"xxx\" codetype=\"YES_NO\"/>\r\n					<th field=\"xxx\" title=\"xxx\" width=\"200\"/>\r\n				</tr>\r\n			</thead>\r\n		</table>\r\n	</div>\r\n    \r\n</div>\r\n','488c5de4-bb9d-4cf8-b5b0-01f0681955ea',2,3,'ADMIN','2013-03-20 15:47:01','ADMIN','2013-03-20 15:47:20');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('b33d4164-d1e6-4879-b81f-8482b9f04c69','Number Column','<th field=\"xxx\" editor=\"numberbox\" format=\"#,##0.00\" align=\"right\"/>\n','1b068fc5-15a0-4e1a-9570-4e71d77a7f7a',1,3,'ADMIN','2013-03-20 15:49:39','8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-08-26 16:28:25');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('b4894aa8-9d73-443d-81b5-8ae900a795ea','Layout Region Center','<div region=\"center\" title=\"Center\" iconCls=\"icon-edit\">\n    |\n</div>\n','839425c2-506f-413a-bb29-de899c473aba',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('bfb77c83-a00f-4997-bf5c-6ab44545f3d7','删除','<a id=\"btnDelete\" class=\"easyui-linkbutton\" iconCls=\"icon-remove\">删除</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',5,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c04309b0-a89c-4f3b-8671-e95a09878802','DateBox Field','<input name=\"xxx\" class=\"easyui-datebox\"/>','c946d37d-541f-4761-a003-2111c9e5f587',3,1,'ADMIN','2013-03-20 15:49:39','ADMIN',NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c39c152c-5ae4-4f6a-970c-fbe11b18da77','重置','<a id=\"btnReset\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\">重置</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',1,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c4d5f5a5-82e3-4f26-9b05-cbcabda71f30','Tabs','<div id=\"tabs\" class=\"easyui-tabs\" fit=\"true\">\r\n    \r\n    <div title=\"Tab1\" iconCls=\"icon-edit\">\r\n        |\r\n    </div>\r\n    \r\n    <div title=\"Tab2\" iconCls=\"icon-edit\">\r\n        \r\n    </div>\r\n    \r\n    <div title=\"Tab3\" iconCls=\"icon-edit\">\r\n        \r\n    </div>\r\n    \r\n</div\r\n','a457d2eb-fd8c-43c3-95b3-9e195bffcec9',2,1,'ADMIN','2013-03-25 15:24:22',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c946d37d-541f-4761-a003-2111c9e5f587','Edit Form','<form id=\"formEdit\" class=\"easyui-form wp-config\" columns=\"4\" i18nRoot=\"Xxx\">\r\n    |\r\n</form>\r\n','9934ce6d-f73a-45dc-8e4b-49c58f5931b8',2,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c9858224-a31b-4f9d-9598-f0b3c643e80d','编辑','<a id=\"btnEdit\" class=\"easyui-linkbutton\" iconCls=\"icon-edit\">编辑</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',4,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('cf4c8652-08f5-47f4-8523-ad75070ed784','Demo Tree Data','<li>\n    <span>目录</span>\n    <ul>\n        <li>节点</li>\n        <li>节点</li>\n    </ul>\n</li>\n<li>\n    <span>目录</span>\n    <ul>\n        <li>节点</li>\n        <li>节点</li>\n    </ul>\n</li>\n<li>节点</li>\n','6f305768-f1b2-4ba0-8c18-c20adb2ea55e',0,1,'1','2013-04-18 15:11:45','1','2013-04-18 15:12:14');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('d2f607b5-bafe-4ae0-801d-d4b0e6f538c8','Layout Region North','<div region=\"north\" title=\"North\" iconCls=\"icon-search\" style=\"height:100px;\">\n    |\n</div>\n','839425c2-506f-413a-bb29-de899c473aba',0,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('dea6c64a-372f-4adb-8df4-d40ecbb28bc8','Layout Region South','<div region=\"south\" title=\"South\" iconCls=\"icon-edit\" style=\"height:100px;\">\n    |\n</div>\n','839425c2-506f-413a-bb29-de899c473aba',4,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('e37a407e-bdea-4bde-8a78-5bb903899935','保存','<a id=\"btnSave\" class=\"easyui-linkbutton\" iconCls=\"icon-save\">保存</a>\n','7457a9bc-2b19-422e-918b-fe3ddca3ca4c',6,2,'ADMIN','2013-03-20 15:41:39','ADMIN','2013-03-20 15:44:48');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('e48440cb-cc84-413b-8313-324ce9b4f4e4','Div','<div style=\"\">\n    |\n</div>\n','a457d2eb-fd8c-43c3-95b3-9e195bffcec9',0,0,'ADMIN','2013-03-25 15:24:22',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('e4f79925-e156-4feb-8e5d-5a0a82f43db4','Columns','','69ab705c-7bf0-4fc6-a1b8-bee0ec0f2684',0,2,'1','2013-04-18 15:37:06','1','2013-04-18 15:41:54');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('ed2eed63-40d9-45ee-8e71-9945d9ea5ba4','ComboGrid Column','<th field=\"xxx\" editor=\"combogrid\" codetype=\"XXX\"/>\n','1b068fc5-15a0-4e1a-9570-4e71d77a7f7a',3,2,'ADMIN','2013-03-20 15:49:39','1','2013-04-18 15:40:02');
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('f223a371-65aa-46a5-a992-1b4219eccc66','ComboBox Field','<input name=\"xxx\" class=\"easyui-combobox\" codetype=\"XXX\"/>\n','c946d37d-541f-4761-a003-2111c9e5f587',1,1,'ADMIN','2013-03-20 15:49:39',NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('f7ab5ddf-3ecb-4f96-9492-98f699eee5f1','Layout Region West','<div region=\"west\" title=\"West\" iconCls=\"icon-tree\" style=\"width:200px;\">\n    |\n</div>\n','839425c2-506f-413a-bb29-de899c473aba',1,0,NULL,NULL,NULL,NULL);
INSERT INTO `wp_html_template_tag` (`UUID`,`NAME`,`CONTENT`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('fabb9229-274a-4d08-b6d8-f3e9bb0c71d2','Demo Grid Data','<tbody>\n    <tr>\n        <td></td>\n        <td>字段1</td>\n        <td>字段2</td>\n        <td>字段3</td>\n        <td>字段4</td>\n        <td>字段5</td>\n        <td>字段6</td>\n        <td>字段7</td>\n        <td>字段8</td>\n        <td>字段9</td>\n    </tr>\n    <tr>\n        <td></td>\n        <td>字段1</td>\n        <td>字段2</td>\n        <td>字段3</td>\n        <td>字段4</td>\n        <td>字段5</td>\n        <td>字段6</td>\n        <td>字段7</td>\n        <td>字段8</td>\n        <td>字段9</td>\n    </tr>\n</tbody>\n','69ab705c-7bf0-4fc6-a1b8-bee0ec0f2684',1,1,'1','2013-04-18 15:36:48','1','2013-04-18 15:40:02');



CREATE TABLE `wp_query_definition` (
  `UUID` varchar(36) NOT NULL,
  `QUERY_NAME` varchar(100) DEFAULT NULL,
  `QUERY_SQL` longtext,
  `PARENT_UUID` varchar(36) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

INSERT INTO `wp_query_definition` (`UUID`,`QUERY_NAME`,`QUERY_SQL`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('14c6919f-f1d0-4e62-a125-5f8448931d57','DemoQuery','select t.* from wp_user t\n','fe0485b4-0c20-4928-9ba7-4d7374da0eb5',0,2,'1','2013-04-26 08:47:52','1','2013-04-26 14:43:04');
INSERT INTO `wp_query_definition` (`UUID`,`QUERY_NAME`,`QUERY_SQL`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('fe0485b4-0c20-4928-9ba7-4d7374da0eb5','模板','','0',0,3,'1','2013-04-26 08:47:52','8bd14143-6d38-4c03-8111-d0f4fcf6c457','2014-03-20 00:27:24');



CREATE TABLE `wp_select_code_definition` (
  `UUID` varchar(36) NOT NULL,
  `CODE_TYPE` varchar(100) DEFAULT NULL,
  `DEFINITION` longtext,
  `PARENT_UUID` varchar(36) DEFAULT NULL,
  `SEQ` double(3,0) DEFAULT NULL,
  `REC_VER` double(20,0) DEFAULT '0',
  `CREATOR` varchar(36) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFIER` varchar(36) DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`UUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8$$

INSERT INTO `wp_select_code_definition` (`UUID`,`CODE_TYPE`,`DEFINITION`,`PARENT_UUID`,`SEQ`,`REC_VER`,`CREATOR`,`CREATE_TIME`,`MODIFIER`,`MODIFY_TIME`) VALUES ('c3699ee9-d3cd-4c9c-8639-59e43f24516b','模板','{\r\n    \"queryType\": \"WlUserModel\",\r\n    \"orderBy\": \"name\",\r\n    \"keyFieldName\": \"userId\",\r\n    \"labelFieldName\": \"name\",\r\n    \"columns\" : [{\r\n        \"field\" : \"code\",\r\n        \"title\" : \"登录名\"\r\n    }, {\r\n        \"field\" : \"name\",\r\n        \"title\" : \"姓名\"\r\n	}]\r\n}','0',0,10,'1','2013-04-26 08:56:35','1','2014-11-20 14:36:02');

