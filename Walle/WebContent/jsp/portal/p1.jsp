<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<ul style="list-style-type: decimal;">

	<li>
		在 <a href="#" onclick='parent.addTab("主菜单配置", "walle-platform/jsp/system/mainMenuConfig.jsp?grade=S", "icon icon-nav", true, "ajax")'>主菜单配置</a> 
		中配置新的菜单项，"页面类型"选为"动态页面"，url地址不需填写
	</li>
	
	<li>
		通过主菜单进入新配置的功能页面，点击"动态页面配置"按钮，进行页面布局和逻辑的定义
	</li>
	
	<li>
		在布局定义界面，双击模版树中的节点可将布局或者控件模版插入光标所在位置，也可直接添加页面模版
	</li>
	
	<li>
		页面、布局、控件的模版可在  <a href="#" onclick='parent.addTab("界面控件模版配置", "walle-platform/jsp/system/templateTagConfig.jsp", "icon icon-nav", true, "ajax")'>界面控件模版配置</a> 功能中配置
	</li>
	
	<li>
		在控件上使用”wp-config“ class，该控件即为动态配置控件，用户可点击控件右上角的"控件配置"按钮对控件的布局和属性进行动态定义
	</li>

</ul>
