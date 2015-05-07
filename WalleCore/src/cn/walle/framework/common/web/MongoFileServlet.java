package cn.walle.framework.common.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="MongoFileServlet", urlPatterns={"/MongoFileServlet"})
public class MongoFileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		String uuid = req.getParameter("uuid");
		String url = null;
		if ("view".equals(action)) {
			url = "/JsonFacadeServlet?json_parameters={serviceName:\"mongoCommonFileManager\",methodName:\"viewFile\",parameters:{uuid:\"" + uuid + "\"}}";
		} else if ("download".equals(action)) {
			url = "/JsonFacadeServlet?json_parameters={serviceName:\"mongoCommonFileManager\",methodName:\"downloadFile\",parameters:{uuid:\"" + uuid + "\"}}";
		}
		if (url != null) {
			req.getRequestDispatcher(url).forward(req, resp);
		}
	}
	
}
