package cn.walle.framework.httpclient;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.json.JSONObject;

public class WalleHttpClient {
	
	private HttpClient httpClient;
	private String cookies;

	private String baseUrl = "http://localhost:8080/Walle";
	private String loginUrl = "/login.action";
	private String usernameParameter = "j_username";
	private String passwordParameter = "j_password";
	private String loginSuccessUrl = "/";
	private String loginFailureUrl = "/login.jsp";
	private String logoutUrl = "/logout.action";
	private String jsonFacadeUrl = "/JsonFacadeServlet";
	
	public WalleHttpClient() {
		this.httpClient = new HttpClient();
		this.httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
	}

	public void login(String username, String password) {
		PostMethod postMethod = new PostMethod(this.baseUrl + this.loginUrl);
		NameValuePair[] data = {
				new NameValuePair(this.usernameParameter, username),
				new NameValuePair(this.passwordParameter, password)
		};
		postMethod.setRequestBody(data);
		
		int result;
		try {
			result = this.httpClient.executeMethod(postMethod);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		if (result != 302) {
			throw new RuntimeException("登录失败，返回值错误：" + result);
		}
		
		String location = postMethod.getResponseHeader("Location").getValue();
		if (location.startsWith(this.baseUrl + this.loginFailureUrl)) {
			throw new RuntimeException("登录失败，用户密码错误");
		}
		if (! location.startsWith(this.baseUrl + this.loginSuccessUrl)) {
			throw new RuntimeException("登录失败，返回地址错误：" + location);
		}
		
		Cookie[] cookies = this.httpClient.getState().getCookies();
		this.cookies = "";
		for(Cookie c:cookies){
			this.cookies += c.getName() + "=\"" + c.getValue() + "\";";
		}
	}
	
	public void logout() {
		PostMethod postMethod = new PostMethod(this.baseUrl + this.logoutUrl);
		postMethod.setRequestHeader("cookie", this.cookies);
		int result;
		try {
			result = this.httpClient.executeMethod(postMethod);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		if (result != 302) {
			throw new RuntimeException("请求失败，返回值错误：" + result);
		}
	}
	
	public String request(String url) {
		PostMethod postMethod = new PostMethod(this.baseUrl + url);
		postMethod.setRequestHeader("cookie", this.cookies);
		int result;
		try {
			result = this.httpClient.executeMethod(postMethod);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		if (result == 302) {
			throw new RuntimeException("请求失败，未登录");
		}
		if (result != 200) {
			throw new RuntimeException("请求失败，返回值错误：" + result);
		}
		try {
			return postMethod.getResponseBodyAsString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public JSONObject requestJsonFacade(String serviceName, String methodName, JSONObject parameters) {
		try {
			if(parameters==null) {
				parameters = new JSONObject("{}");
			}
			PostMethod postMethod = new PostMethod(this.baseUrl + this.jsonFacadeUrl);
			postMethod.setRequestHeader("cookie", this.cookies);
			postMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("serviceName", serviceName);
			jsonParam.put("methodName", methodName);
			jsonParam.put("parameters", parameters);
			NameValuePair[] data = {
					new NameValuePair("json_parameters", jsonParam.toString())
			};
			postMethod.setRequestBody(data);
			int result = this.httpClient.executeMethod(postMethod);
			if (result == 302) {
				throw new RuntimeException("请求失败，未登录");
			}
			if (result != 200) {
				throw new RuntimeException("请求失败，返回值错误：" + result);
			}
			String responseBodyString = new String(postMethod.getResponseBody(),"UTF-8");
			JSONObject resultJsonObject = new JSONObject(responseBodyString);
			if(resultJsonObject.has("exception")) {
				throw new RuntimeException("服务异常：" + resultJsonObject.getString("exception"));
			}
			return resultJsonObject;			
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public String uploadFile(String fileUploadHandler,
			String fileName, File file, Map<String, String> parameters) {
		try {
			if(parameters==null) {
				parameters = new HashMap<String, String>();
			}
			PostMethod postMethod = new PostMethod(this.baseUrl + this.jsonFacadeUrl + "?fileUploadHandler=" + fileUploadHandler);
			postMethod.setRequestHeader("cookie", this.cookies);
			List<Part> parts = new ArrayList<Part>();
			for (String key : parameters.keySet()) {
				String value = parameters.get(key);
				if (value != null) {
					parts.add(new StringPart(key, value));
				}
			}
			parts.add(new FilePart(fileName, file, parameters.get("contentType"), parameters.get("charset")));
			MultipartRequestEntity data = new MultipartRequestEntity(parts.toArray(new Part[0]), postMethod.getParams());
			postMethod.setRequestEntity(data);
			int result = this.httpClient.executeMethod(postMethod);
			if (result == 302) {
				throw new RuntimeException("请求失败，未登录");
			}
			if (result != 200) {
				throw new RuntimeException("请求失败，返回值错误：" + result);
			}
			String responseBodyString = new String(postMethod.getResponseBody(),"UTF-8");
			JSONObject resultJsonObject = new JSONObject(responseBodyString);
			if(resultJsonObject.has("exception")) {
				throw new RuntimeException("服务异常：" + resultJsonObject.getString("exception"));
			}
			return resultJsonObject.getString("result");			
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public InputStream downloadFile(String serviceName, String methodName, JSONObject parameters) {
		try {
			if(parameters==null) {
				parameters = new JSONObject("{}");
			}
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("serviceName", serviceName);
			jsonParam.put("methodName", methodName);
			jsonParam.put("parameters", parameters);
			GetMethod getMethod = new GetMethod(this.baseUrl + this.jsonFacadeUrl + "?json_parameters=" + jsonParam.toString());
			getMethod.setRequestHeader("cookie", this.cookies);
			getMethod.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
			int result = this.httpClient.executeMethod(getMethod);
			if (result == 302) {
				throw new RuntimeException("请求失败，未登录");
			}
			if (result != 200) {
				throw new RuntimeException("请求失败，返回值错误：" + result);
			}
			return getMethod.getResponseBodyAsStream();
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getUsernameParameter() {
		return usernameParameter;
	}

	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}

	public String getPasswordParameter() {
		return passwordParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}

	public String getLoginSuccessUrl() {
		return loginSuccessUrl;
	}

	public void setLoginSuccessUrl(String loginSuccessUrl) {
		this.loginSuccessUrl = loginSuccessUrl;
	}

	public String getLoginFailureUrl() {
		return loginFailureUrl;
	}

	public void setLoginFailureUrl(String loginFailureUrl) {
		this.loginFailureUrl = loginFailureUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getJsonFacadeUrl() {
		return jsonFacadeUrl;
	}

	public void setJsonFacadeUrl(String jsonFacadeUrl) {
		this.jsonFacadeUrl = jsonFacadeUrl;
	}
	
}
