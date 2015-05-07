package cn.walle.system.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GetHttpRequest {

	public static HttpServletRequest getHttpRequest()
	  {
	    ServletRequestAttributes a;
	    if ((a = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes()) == null)
	    {
	      return null;
	    }
	    return a.getRequest();
	  }
	
}
