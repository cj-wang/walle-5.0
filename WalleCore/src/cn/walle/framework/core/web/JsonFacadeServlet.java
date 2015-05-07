package cn.walle.framework.core.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import cn.walle.framework.core.exception.ApplicationException;
import cn.walle.framework.core.exception.SystemException;
import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.support.FileToDownload;
import cn.walle.framework.core.support.FileUploadHandler;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.core.util.JSONDataUtils;

public class JsonFacadeServlet extends HttpServlet {
	
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream requestInputStream = request.getInputStream();
		OutputStream responseOutputStream = response.getOutputStream();
		JSONObject resultJSONObject = new JSONObject();
		try {
			//文件上传
            if (ServletFileUpload.isMultipartContent(request)) {
                String handlerName = request.getParameter("fileUploadHandler");
                FileUploadHandler handler = ContextUtils.getBeansOfType(FileUploadHandler.class).get(handlerName);
                if (handler == null) {
                	throw new SystemException("fileUploadHandler " + handlerName + " not found");
                }
                
                DiskFileItemFactory dfif = new DiskFileItemFactory();
                ServletFileUpload sfu = new ServletFileUpload(dfif);
                FileItemIterator fii = sfu.getItemIterator(request);
                Map<String, String> parameters = new LinkedHashMap<String, String>();
                while (fii.hasNext()) {
                    FileItemStream fis = fii.next();
                    if (fis.isFormField()) {
                    	parameters.put(fis.getFieldName(), IOUtils.toString(fis.openStream()));
                    } else if (fis.getName().length() > 0) {
                    	handler.uploadFile(parameters, fis.getName(), fis.openStream());
                    }
                }
 				//返回json结果
 				response.setContentType("application/json; charset=utf-8");
 				resultJSONObject.put("result", parameters.get("uuid"));
 				IOUtils.write(resultJSONObject.toString(), responseOutputStream, "UTF-8");
                return;
            }

			//正常json请求
			String requestJSONString = IOUtils.toString(requestInputStream, "UTF-8");
	 		if (requestJSONString.trim().length() == 0) {
	 			requestJSONString = request.getParameter("json_parameters");
	 		} else {
	 			if (! requestJSONString.startsWith("{")) {
	 				requestJSONString = URLDecoder.decode(requestJSONString, "UTF-8");
	 				requestJSONString = requestJSONString.substring(requestJSONString.indexOf("=") + 1);
	 			}
	 		}
	 		JSONObject requestJSONObject = new JSONObject(requestJSONString);
	 		String serviceName = requestJSONObject.getString("serviceName");
	 		String methodName = requestJSONObject.getString("methodName");
	 		JSONObject parametersJSONObject = requestJSONObject.getJSONObject("parameters");
	 		int parametersCount = parametersJSONObject.length();
	 		
	 		Object bean = ContextUtils.getBean(serviceName);
	 		Class<?>[] interfaces = bean.getClass().getInterfaces();
	 		Class<?> beanInterfaceClass = null;
	 		Method beanMethod = null;
	 		for (Class<?> interfaceClass : interfaces) {
				if (BaseManager.class.isAssignableFrom(interfaceClass)) {
					beanInterfaceClass = interfaceClass;
			 		for (Method method : interfaceClass.getMethods()) {
						if (method.getName().equals(methodName) && method.getParameterTypes().length == parametersCount) {
							beanMethod = method;
							break;
						}
					}
			 		break;
				}
			}
	 		
	 		if (beanMethod == null) {
	 			throw new SystemException("Method " + methodName + " not found in service " + serviceName + ", or parameters not matched");
	 		}
	 		
	 		Object[] parameters = JSONDataUtils.parseParametersJSONObject(beanInterfaceClass, beanMethod, parametersJSONObject);
 			Object result;
 			try {
 				result = beanMethod.invoke(bean, parameters);
 			} catch (InvocationTargetException itex) {
 				throw itex.getTargetException();
 			}
 			
 			if (result instanceof FileToDownload) {
 				//文件下载
 				FileToDownload fileToDownload = (FileToDownload) result;
 				response.setHeader("Pragma", "public");
 				response.setHeader("Expires", "0");
 				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
 				response.setHeader("Content-Type", fileToDownload.getContentType());
 				if (fileToDownload.isForceDownload()) {
 	 				response.setHeader("Content-Type", "application/force-download");
	 				String fileName = fileToDownload.getFileName();
	 				if (fileName != null) {
	 					if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
	 						fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
	 					} else {
	 						fileName = URLEncoder.encode(fileName, "UTF-8");
	 					}
	 	 				response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	 				}
 				}
 				IOUtils.copy(fileToDownload.getContent(), responseOutputStream);
 			} else {
 				//返回json结果
 				response.setContentType("application/json; charset=utf-8");
 				resultJSONObject.put("result", JSONDataUtils.buildJSONValue(result));
 				IOUtils.write(resultJSONObject.toString(), responseOutputStream, "UTF-8");
 			}
		} catch (Throwable ex) {
			log.error("JsonFacadeServlet error", ex);
			try {
 				response.setContentType("application/json; charset=utf-8");
				Throwable root = ExceptionUtils.getRootCause(ex);
				if (root == null) {
					root = ex;
				}
				if (root instanceof ApplicationException) {
					resultJSONObject.put("exception", root.getMessage());
				} else {
					resultJSONObject.put("exception", root.toString());
				}
				IOUtils.write(resultJSONObject.toString(), responseOutputStream, "UTF-8");
			} catch (Throwable ex1) {
				log.error("Error returning exception info", ex1);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
