package com.hhh.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.hhh.core.util.ShiroUtils;
import com.hhh.system.service.LogService;


public class JcjgExceptionHandler implements HandlerExceptionResolver {

	private final static Logger logger = LoggerFactory.getLogger(JcjgExceptionHandler.class);

	private LogService logService;

	public JcjgExceptionHandler(LogService log) {
		logService = log;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse rsp, Object handler, Exception ex) {

		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			logger.error(sw.toString());
			pw.flush();
			sw.flush();
			Map<String, Object> bean = new HashMap<>();
			bean.put("logInfo", ex.getMessage());
			bean.put("systemUrl", req.getRequestURI());
			bean.put("type", 0);
			bean.put("timespan", "0");
			bean.put("appname", "newjc");
//			bean.put("logSource", "");日志来源模块
//			bean.put("reId", "");产品ID
//			bean.put("dpId", "");部署ID
			if (ShiroUtils.getUser() != null) {
				bean.put("customerId", ShiroUtils.getCustomerId());
				bean.put("creatorId", ShiroUtils.getUserId());
			}
			logService.saveRunLog(bean);
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}

		// 视图显示专门的错误页
		ModelAndView modelAndView = new ModelAndView("errorPage");
		modelAndView.addObject("errorMsg", sw.toString());
		return modelAndView;
	}

}
