package cn.walle.framework.core.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import cn.walle.framework.core.util.ContextUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageValidationCodeServlet extends HttpServlet {

    private static final String validationCodeParameter = "j_validation_code";
    
    private Log log = LogFactory.getLog(this.getClass());
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	Captcha captcha = ContextUtils.getBeanOfType(CaptchaEngine.class).getNextCaptcha();
            HttpSession session = request.getSession();
            session.setAttribute(validationCodeParameter, captcha);
            BufferedImage image = (BufferedImage) captcha.getChallenge();

            OutputStream outputStream = null;
            try {
                outputStream = response.getOutputStream();
                // render the captcha challenge as a JPEG image in the response
                response.setHeader("Cache-Control", "no-store");
                response.setHeader("Pragma", "no-cache");
                response.setDateHeader("Expires", 0);
                response.setContentType("image/jpeg");

                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);
                encoder.encode(image);
            } finally {
                captcha.disposeChallenge();
            }
        } catch (Exception ex) {
        	log.error("Captcha error", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}