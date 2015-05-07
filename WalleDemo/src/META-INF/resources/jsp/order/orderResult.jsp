<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/include.jsp" %>

<script type="text/javascript" src="<%=contextPath%>/ChromeFrame/CFUtils.js"></script>

<script type="text/javascript">

	function AgentError() {
		alert("Error");
	}
	
</script>
</head>

<body>
	aabb <input />
	<a id="_a" href="orderResult.jsp" rel="noreferrer" target="_blank">open IE window</a>
	<button onclick="CFUtils.openHostWindow('orderResult.jsp')">open IE window</button>

	<object style="visibility:hidden" id="MSAgent" classid="CLSID:D45FD31B-5C6E-11D1-9EC1-00C04FD7081F" onerror="AgentError()"></object>
	<script LANGUAGE="JavaScript" for="window" even="onload">
		var msAgent = null;
		if(MSAgent!=null){
			var agent_id = "Merlin";//"Merlin";
			var agent_acs = "Merlin.acs";
			MSAgent.Connected = true;
			AgentLoadRequest = MSAgent.Characters.Load(agent_id, agent_acs);
			msAgent = MSAgent.Characters.Character(agent_id);
			//m_character.LanguageID = 0x0804;  // 简体中文
			msAgent.Balloon.Style = msAgent.Balloon.Style & 4294967291;
			msAgent.MoveTo(window.screen.availWidth-170, window.screen.availHeight - 180, 0);
			msAgent.Hide();
			//msAgent.Play("RestPose");  // 复位
		}
		
		//欢迎仪式
		function welcome(){
			if(msAgent!=null){
				msAgent.Show();
				msAgent.Play("Announce");
				var day=new Date()
		    	var hr=day.getHours()
		    	if ((hr>=0)&&(hr<4))
		      		mess1="凌晨好"
		      	if ((hr>=4)&&(hr<7))
		        	mess1="清晨好"
		        if ((hr>=7)&&(hr<12))
		        	mess1="早上好"
		        if ((hr>=12)&&(hr<14))
		        	mess1="中午好"
		        if ((hr>=14)&&(hr<19))
		        	mess1="下午好 "
		        if ((hr>=19)&&(hr<23))
		        	mess1="晚上好 "
		        if ((hr>=23)&&(hr<24))
		        	mess1="深夜好 "
				msAgent.Speak(mess1+"，欢迎使用！祝愉快!");
				msAgent.Play("RestPose");
				//msAgent.Hide();
				setTimeout("msAgent.Play('wave');msAgent.Hide();", 15000);  // 15秒后自动隐藏
			}
		}
		welcome();
	</script>
</body>
</html>