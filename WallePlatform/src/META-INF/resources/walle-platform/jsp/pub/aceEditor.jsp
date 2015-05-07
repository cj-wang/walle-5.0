<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ACE in Action</title>
<style type="text/css" media="screen">
    #editor { 
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
    }
</style>
</head>

<body>

	<div id="editor" style="width:100%;height:100%;"></div>

	<script type="text/javascript" src="../../../ace-builds-master/src-min-noconflict/ace.js"></script>
	
	<script type="text/javascript">
	
		var editor = ace.edit("editor");
		editor.setTheme("ace/theme/eclipse");
		//editor.getSession().setMode("ace/mode/javascript");
		document.getElementById("editor").style.fontSize="14px";
		
	</script>

</body>
</html>