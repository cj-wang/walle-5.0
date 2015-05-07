
CFUtils = {
		
		//从Chrome Frame打开IE的window
		openHostWindow : function(url) {
			$("<a href='" + url + "' rel='noreferrer' target='_blank'/>").get(0).click();
		}
		
};