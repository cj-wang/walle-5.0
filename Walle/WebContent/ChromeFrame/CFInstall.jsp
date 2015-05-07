<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript" src="ChromeFrame/CFInstall.js"></script>
<style>
	.chromeFrameInstallDefaultStyle {
		width : 0px;
		height : 0px;
	}
</style>
<script>
	if (window.attachEvent) {
		window.attachEvent("onload", function() {
			CFInstall.check({
				url : "ChromeFrame/GoogleChromeframeStandaloneEnterprise.msi",
				onmissing : function() {
					if (! confirm("您使用的是旧版IE浏览器，可正常访问系统，但速度会较慢，建议安装 Google Chrome Frame for IE 插件以获得更快的响应速度，是否安装？")) {
						this.preventPrompt = true;
						this.preventInstallDetection = true;
					}
				}
			});
		});
	}
</script>
