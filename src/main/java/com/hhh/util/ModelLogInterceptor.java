package com.hhh.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hhh.core.util.DateUtil;
import com.hhh.core.util.ShiroPrincipal;
import com.hhh.core.util.ShiroUtils;
import com.hhh.core.util.StringUtil;
import com.hhh.core.util.log.HHHLog;
import com.hhh.system.entity.JcLogOperation;
import com.hhh.system.service.LogService;


@Service
public class ModelLogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LogService logService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ShiroPrincipal principal = ShiroUtils.getPrincipal();
		if (principal == null) {
			// 如果判断是 AJAX 请求,直接设置为session超时
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
				response.setHeader("sessionstatus", "timeout");
			}
		}
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		long startTime = (Long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();
		float executeTime = (endTime - startTime) / 1000f;

		if (handler instanceof HandlerMethod) {
			HandlerMethod h = (HandlerMethod) handler;
			String controllerName = h.getBean().getClass().getName();
			Method method = h.getMethod();
			String methodName = method.getName();
			String Params = getParamString(request.getParameterMap());
			String URI = request.getRequestURI();
			System.out.println("请求Controller:" + controllerName);
			System.out.println("请求Method:" + methodName);
			System.out.println("请求Params:" + Params);
			System.out.println("请求URI:" + URI);
			System.out.println("执行耗时:" + executeTime + "ms");
			System.out.println("-------------------------------------------------------------------------------");

			StringBuilder logInfo = new StringBuilder();
			//logInfo.append("请求参数:").append(Params).append("\n");
			logInfo.append("请求URI:").append(URI).append("\n");

			Map<String, Object> bean = new HashMap<>();
			bean.put("logInfo", logInfo.toString());
			bean.put("systemUrl", request.getRequestURI());
			bean.put("type", 2);
			if (URI.contains("admin/login")) {
				bean.put("type", 3);
			} else if (URI.contains("inter/")) {
				bean.put("type", 4);
			}
			bean.put("timespan", String.valueOf(executeTime));
			bean.put("appname", "newjc");
//			bean.put("logSource", "");日志来源模块
//			bean.put("reId", "");产品ID
//			bean.put("dpId", "");部署ID
			if (ShiroUtils.getUser() != null) {
				bean.put("customerId", ShiroUtils.getCustomerId());
				bean.put("creatorId", ShiroUtils.getUserId());
			}
			logService.saveRunLog(bean);
			
			//操作日志
			HHHLog hhhLog = method.getAnnotation(HHHLog.class);
			if (hhhLog != null) {
				System.out.println("操作描述:" + hhhLog.moduleFirst() + "--" + hhhLog.moduleSecond()+ "--" + hhhLog.moduleThird()+ "--" + hhhLog.info());
			}
			if (ShiroUtils.getUser() != null && hhhLog != null) {
				String hostId = StringUtil.getIpAddress(request);
				JcLogOperation operation = new JcLogOperation();
				operation.setCustomerId(ShiroUtils.getCustomerId());
				operation.setCreateTime(DateUtil.getCurrentDateTime());
				operation.setCreatorId(ShiroUtils.getUserId());
				operation.setModuleFirst(hhhLog.moduleFirst());
				operation.setModuleSecond(hhhLog.moduleSecond());
				operation.setModuleThird(hhhLog.moduleThird());
				operation.setOpContent(hhhLog.info());
//				operation.setOpResult(opResult);
//				operation.setType(type);
				operation.setAppname("newjc");
				operation.setOpId(hostId);
				logService.saveOperationLog(operation);
			}
		}
	}

	private String getParamString(Map<String, String[]> map) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String[]> e : map.entrySet()) {
			sb.append(e.getKey()).append("=");
			String[] value = e.getValue();
			if (value != null && value.length == 1) {
				sb.append(value[0]).append("\t");
			} else {
				sb.append(Arrays.toString(value)).append("\t");
			}
		}
		return sb.toString();
	}

}
